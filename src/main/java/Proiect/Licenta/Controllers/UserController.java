package Proiect.Licenta.Controllers;

import Proiect.Licenta.Models.*;
import Proiect.Licenta.Models.DTOS.DTOraspunsAlgoritm;
import Proiect.Licenta.Models.Serializeaza.SerializePachetPromotional;
import Proiect.Licenta.Services.*;
import Proiect.Licenta.security.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin()
@RestController
public class UserController {
    private final JwtProvider jwtProvider;
    private final OfertatoServiceService ofertatoServiceService;
    private final ServiceSv serviceSv;
    private final ReviewServices reviewServices;
    private final AccountService accountService;
    private final OfertaService ofertaService;



    public UserController(JwtProvider jwtProvider, OfertatoServiceService ofertatoService, ServiceSv serviceSv, ReviewServices reviewServices, AccountService accountService, OfertaService ofertaService) {
        this.jwtProvider = jwtProvider;
        this.ofertatoServiceService = ofertatoService;
        this.serviceSv = serviceSv;
        this.reviewServices = reviewServices;
        this.accountService = accountService;
        this.ofertaService = ofertaService;
    }

    @GetMapping("/oferte")
    public ResponseEntity<List<Oferta>> vizualizeazaoferte(@RequestParam("serviceid") int id) {
        System.out.println("request vizualizeaza oferte facut");

        ServiceModel serviceModel = serviceSv.findbyid(id).get();
        if (serviceModel != null) {
            List<Oferta> ofertaf = ofertatoServiceService.get_oferte_service(serviceModel.getService_id());

            return new ResponseEntity<>(ofertaf, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @GetMapping("/oferteString")
    public ResponseEntity<List<String>> getoferteString() {
        List<String> string=new ArrayList<>();
        for(Oferta i:ofertaService.toate_of()){
            if(!string.contains(i.getOferta()))
            string.add(i.getOferta());
        }

        return new ResponseEntity<>(string,HttpStatus.OK);
    }

    @GetMapping("/pachete")
    public ResponseEntity<List<PachetPromotional>> getPachete(@RequestParam("serviceid") int id) {
        System.out.println("request vizualizeaza pachete facut");

        ServiceModel serviceModel = serviceSv.findbyid(id).get();
        SerializePachetPromotional x=new SerializePachetPromotional();
        if (serviceModel != null) {
            List<PachetPromotional> pkt = x.deserializeaza().get(serviceModel.getService_id());

            return new ResponseEntity<>(pkt, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @GetMapping("/rating")
    public ResponseEntity<Float> getRating(@RequestParam("serviceid") int id) {
        System.out.println("request vizualizeaza pachete facut");

        ServiceModel serviceModel = serviceSv.findbyid(id).get();


        if (serviceModel != null) {
            Float response=serviceModel.getRating();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/lasareview")
    public ResponseEntity<String> lasareview(@RequestBody Review review, @RequestParam("bearer") String bearer,@RequestParam("serviceid") int id){
        String idd = jwtProvider.getUsername(bearer);
        System.out.println("suntem in adaugare review");
        Account user=accountService.getaccbyid(Integer.valueOf(idd));
        review.setAccount(user);

        ServiceModel serviceModel = serviceSv.findbyid(id).get();
        review.setSv(serviceModel);
        reviewServices.save(review);
        return new ResponseEntity<>("\"OK\"",HttpStatus.OK);
    }

    @GetMapping("/getreviews")
    public ResponseEntity<List<Review>> getreviews(@RequestParam("serviceid") int id){
        List<Review> listret=reviewServices.getReviewByServiceId(id);
        System.out.println("Get reviews request");
        return new ResponseEntity<>(listret,HttpStatus.OK);
    }

    @PostMapping("/bestprice")
    public ResponseEntity<List<DTOraspunsAlgoritm>> getbestprice(@RequestBody List<String> oferte){
        //List<Oferta> of=ofertaService.toate_of();
        List<DTOraspunsAlgoritm> raspuns=new ArrayList<>();
        AlgoritmBestPrice x=new AlgoritmBestPrice(ofertaService.toate_of(),oferte);
        List<ClasaAjutatoare> sol=new ArrayList<>();

        for(int i=0;i<oferte.size();i++){
            sol.add(new ClasaAjutatoare(0));
        }
        x.back(0,sol);
        SerializePachetPromotional pkt=new SerializePachetPromotional();
        HashMap<Integer,List<PachetPromotional>> hashMap=pkt.deserializeaza();
        PachetPromotional pachet=null;
        for(ClasaAjutatoare i:x.getSolutie_finala())
        {   System.out.println(i.getId()+" "+i.getPret());}
        for(ClasaAjutatoare i:x.getSolutie_finala())
        {   //System.out.println(i.getId()+" "+i.getPret());
            if(i.getIdsv()==0){
                ServiceModel s=ofertatoServiceService.getServiceByOfId(i.getId());
                Oferta o=ofertaService.getbyId(i.getId());
                raspuns.add(new DTOraspunsAlgoritm("oferta",o.getOferta(),i.getPret(),s.getNume(),s.getTelefon()));
            }

            if(i.getIdsv()!=0){
                ServiceModel s=serviceSv.findbyid(i.getIdsv()).get();
                List<PachetPromotional> listpack=hashMap.get(i.getIdsv());
                for(PachetPromotional pk:listpack){
                    if(pk.getId()==i.getId() && pk.getPret()==i.getPret())
                         pachet=pk;}
                String reparatii="";
                for(Oferta oo:pachet.getOferte_pachet()){
                    reparatii+=oo.getOferta()+" ";
                }
                raspuns.add(new DTOraspunsAlgoritm("pachet",reparatii,i.getPret(),s.getNume(),s.getTelefon()));
            }
        }
        return new ResponseEntity<>(raspuns,HttpStatus.OK);
    }




}
