package Proiect.Licenta.Controllers;

import Proiect.Licenta.Models.Account;
import Proiect.Licenta.Models.DTOS.ServiceDTO;
import Proiect.Licenta.Models.PachetPromotional;
import Proiect.Licenta.Models.Serializeaza.SerializePachetPromotional;
import Proiect.Licenta.Models.ServiceModel;
import Proiect.Licenta.Services.*;
import Proiect.Licenta.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@CrossOrigin()
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping(value = "/admin")
public class AdministratorController {
    private final ServiceSv serviceRepo;
    private final JwtProvider jwtProvider;
    private final AccountService accountService;
    private final OfertatoServiceService ofertatoServiceService;
    private final ServicetoAccService servicetoAccService;
    private final ReviewServices reviewServices;

    @Autowired
    public AdministratorController(ServiceSv serviceRepo, JwtProvider jwtProvider, AccountService accountService, OfertatoServiceService ofertatoServiceService, ServicetoAccService servicetoAccService, ReviewServices reviewServices) {
        this.serviceRepo = serviceRepo;
        this.jwtProvider = jwtProvider;
        this.accountService = accountService;
        this.ofertatoServiceService = ofertatoServiceService;
        this.servicetoAccService = servicetoAccService;
        this.reviewServices = reviewServices;

    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getaccounts")
    public ResponseEntity<List<Account>> viewaccounts() throws IOException {

        return new ResponseEntity<>(accountService.getall(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAccount")
    public ResponseEntity<Account> getaccbyId(@RequestParam("id") int id) throws IOException {

        return new ResponseEntity<>(accountService.getaccbyid(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/updateAccount")
    public ResponseEntity<String> updateaccount(@RequestBody Account acc) throws IOException {
        System.out.println(acc.getAccount_id()+" "+acc.getUsername()+acc.getPassword());
        accountService.updateaccount(acc);
        return new ResponseEntity<>("\"APROBAT\"", HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteAccount")
    public ResponseEntity<String> deleteAccount(@RequestParam("id") int id) throws IOException {
        Account acc=accountService.getaccbyid(id);
        if(acc.getRole().equals("USER")){
            reviewServices.deleteAllUserReview(id);
            accountService.delete(id);
        }
        if(acc.getRole().equals("MANAGER")){
            SerializePachetPromotional seri=new SerializePachetPromotional();
            HashMap<Integer,List<PachetPromotional>> hashMap=seri.deserializeaza();

            ServiceModel x=servicetoAccService.get_Service_id(id);
            hashMap.remove(x.getId());
            ofertatoServiceService.delete(x.getService_id());
            servicetoAccService.delete(x.getService_id());
            serviceRepo.delete(serviceRepo.findbyid(x.getService_id()).get());

            accountService.delete(id);
            seri.serializeaza(hashMap);
        }

        return new ResponseEntity<>("\"APROBAT\"", HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<ServiceDTO>> viewService() throws IOException {
        List<ServiceDTO> sv = new ArrayList<>();
        for (ServiceDTO i : serviceRepo.findALL()) {
            if (i.getApprove() == 1) {
                sv.add(i);
            }
        }
        return new ResponseEntity<>(sv, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/unregistredservice")
    public ResponseEntity<List<ServiceDTO>> viewunregistredService() throws IOException {
        List<ServiceDTO> sv = new ArrayList<>();
        for (ServiceDTO i : serviceRepo.findALL()) {
            if (i.getApprove() == 0) {
                sv.add(i);
            }
        }
        //System.out.println(sv.get(0).getOferte());
        return new ResponseEntity<>(sv, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/acteService")
    public ResponseEntity<String> viewacteService(@RequestParam("service") int id) throws IOException {
        System.out.println("Request facut id:" + id);
        return new ResponseEntity<>("\"" + serviceRepo.getmultipartActe(id) + "\"", HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/approve")
    public ResponseEntity<String> aproveservice(@RequestParam("service") int id) {
        System.out.println("Am primit id-ul:" + id);
        serviceRepo.approve(id);

        return new ResponseEntity<>("\"APROBAT\"", HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/delete")
    public ResponseEntity<String> deleteservice(@RequestParam("service") int id) {
        SerializePachetPromotional seri=new SerializePachetPromotional();
        HashMap<Integer,List<PachetPromotional>> hashMap=seri.deserializeaza();
        hashMap.remove(id);
        Account x=servicetoAccService.getAccc(id);
        ofertatoServiceService.delete(id);
        servicetoAccService.delete(id);
        serviceRepo.delete(serviceRepo.findbyid(id).get());
        accountService.delete(x.getAccount_id());
        seri.serializeaza(hashMap);
        return new ResponseEntity<>("\"Sters\"", HttpStatus.OK);
    }



}
