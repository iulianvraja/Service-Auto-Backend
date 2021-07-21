package Proiect.Licenta.Controllers;

import Proiect.Licenta.FormRegister.ServiceRegisterForm;
import Proiect.Licenta.Models.*;
import Proiect.Licenta.Models.DTOS.ServiceDTO;
import Proiect.Licenta.Services.AccountService;
import Proiect.Licenta.Services.OfertatoServiceService;
import Proiect.Licenta.Services.ServiceSv;
import Proiect.Licenta.Services.ServicetoAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin("*")
@RestController

public class IndexController {
    private final ServiceSv sv;
    private final OfertatoServiceService oftoserv;
    private final AccountService accservice;
    private final ServicetoAccService servicetoaccservice;
 @Autowired
    public IndexController(ServiceSv sv, OfertatoServiceService oftoserv, AccountService accservice, ServicetoAccService servicetoaccservice) {
        this.sv = sv;
        this.oftoserv = oftoserv;
        this.accservice = accservice;
        this.servicetoaccservice = servicetoaccservice;
    }


    @RequestMapping(value = "/")
    public ResponseEntity<List<ServiceDTO>> indexpage() throws IOException {
        List<ServiceDTO> svret=new ArrayList<>();
        for(ServiceDTO i:sv.findALL())
            if(i.getApprove()==1)
                svret.add(i);
       // System.out.println(svret.get(0).getSigla());
        return new ResponseEntity<>(svret,HttpStatus.OK);
    }

    @RequestMapping(value = "/serviceregister",consumes ={MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> registerService(
                                          @RequestParam(value = "nume") String numeservice,
                                          @RequestParam(value = "email") String email,
                                          @RequestParam(value = "adresa") String adresa,
                                          @RequestParam(value = "telefon") String telefon,
                                          @RequestParam(value = "sigla",required = false) MultipartFile file1,
                                          @RequestParam(value = "acte",required = false) MultipartFile file2,
                                          @RequestParam(value = "username") String username,
                                          @RequestParam(value = "password") String pass)
    {


     ServiceModel x2=new ServiceModel(numeservice,email,5,0, adresa, telefon, file1,file2);
   // System.out.println("email:" +x2.getEmail());
    String accountexist="account alrdeary exist";
        System.out.println("telefon:" +x2.getTelefon());
        if(accservice.getAccountByName(username)!=null){
            return new ResponseEntity<>(accountexist,HttpStatus.OK);
        }
        Account ac=new Account(username,pass,"MANAGER");
        try {
            ServicetoAccount x3=new ServicetoAccount();
            ServiceModel x4=sv.save(x2);
            x3.setSv(x4);
            x3.setIdacc(accservice.addAccount(ac.getUsername(),ac.getPassword(),ac.getRole()));
            servicetoaccservice.save(x3);
            return new ResponseEntity<>("OK",HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("NOK",HttpStatus.OK);
        }


    }
    @RequestMapping(value = "/vizualizeazaservice")
    public ResponseEntity<List<Oferta>> vizualizeazaService(@RequestBody int id) {
        return new ResponseEntity<List<Oferta>>(oftoserv.get_oferte_service(id),HttpStatus.OK);
    }


    @RequestMapping(value="/bestprice")
    public ResponseEntity<List<OfertatoService>> getbestPrice(@RequestParam List<String>nume_oferte){
        List<OfertatoService> off=oftoserv.findall();
        List<OfertatoService> offreturn=new ArrayList<>();


        for(String i:nume_oferte){
            OfertatoService best=null;
            float pretmax=999999;
            for(OfertatoService j:off){
                if(j.getOferta().getOferta().equals(i) && j.getOferta().getPret()<pretmax){
                  best=j;
                  pretmax=j.getOferta().getPret();
                }
            }
            offreturn.add(best);
        }
        return new ResponseEntity<List<OfertatoService>>(offreturn,HttpStatus.OK);
    }


    @RequestMapping(value = "/reclamaService")
    public ResponseEntity<String> reclamaservice(ServiceModel x){
        x.setApprove(0);
        try {
            sv.save(x);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Cererea va fi procesata",HttpStatus.OK);
    }
}
