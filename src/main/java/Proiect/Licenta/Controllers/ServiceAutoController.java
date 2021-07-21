package Proiect.Licenta.Controllers;

import Proiect.Licenta.Models.Oferta;
import Proiect.Licenta.Models.OfertatoService;
import Proiect.Licenta.Models.ServiceModel;
import Proiect.Licenta.Services.OfertaService;
import Proiect.Licenta.Services.OfertatoServiceService;
import Proiect.Licenta.Services.ServicetoAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/managerservice")
@PreAuthorize("hasAuthority('MANAGER')")
public class ServiceAutoController {
    private final OfertatoServiceService sv;
    private final ServicetoAccService svtoacc;
    private final OfertaService ofservice;
    @Autowired
    public ServiceAutoController(OfertatoServiceService sv, ServicetoAccService svtoacc, OfertaService ofservice) {
        this.sv = sv;
        this.svtoacc = svtoacc;
        this.ofservice = ofservice;
    }
    @PostMapping("/addoferta{id}")
    public ResponseEntity add(@Validated @PathVariable("id") int id, @Validated @RequestBody Oferta of){
        ServiceModel svmodel=svtoacc.get_Service_id(id);
        OfertatoService x=new OfertatoService();
        x.setOferta(ofservice.insert(of));
        x.setSv(svmodel);
        try{sv.save(x);
            return new ResponseEntity(HttpStatus.OK);

        }
        catch(Exception e){
            System.out.println("Nu am putut salva oferta");
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }
}
