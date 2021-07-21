package Proiect.Licenta.Controllers;

import Proiect.Licenta.Models.Oferta;
import Proiect.Licenta.Models.OfertatoService;
import Proiect.Licenta.Models.PachetPromotional;
import Proiect.Licenta.Models.Serializeaza.SerializePachetPromotional;
import Proiect.Licenta.Models.ServiceModel;
import Proiect.Licenta.Services.OfertaService;
import Proiect.Licenta.Services.OfertatoServiceService;
import Proiect.Licenta.Services.ServicetoAccService;
import Proiect.Licenta.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin()
@PreAuthorize("hasAuthority('MANAGER')")
@RequestMapping("/servicemanager")
public class ManagerServiceController {
    private final JwtProvider jwtProvider;
    private final OfertatoServiceService ofertatoServiceService;
    private final OfertaService ofertaService;
    private final ServicetoAccService servicetoAccService;

    @Autowired
    public ManagerServiceController(JwtProvider jwtProvider, OfertatoServiceService ofertatoService, OfertaService ofertaService, ServicetoAccService servicetoAccService) {
        this.jwtProvider = jwtProvider;
        this.ofertatoServiceService = ofertatoService;
        this.ofertaService = ofertaService;

        this.servicetoAccService = servicetoAccService;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping("/getOferteString")
    public ResponseEntity<List<String>> getStringarrayOferte() {
        System.out.println("Request facut get oferteString");
        List<String> oferte = new ArrayList<>();
        for (Oferta i : ofertaService.toate_of()) {
            oferte.add(i.getOferta());
        }
        return new ResponseEntity<>(oferte, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/oferteserviceString")
    public ResponseEntity<List<String>> oferteService(@RequestParam("bearer") String bearer) {
        System.out.println("request vizualizeaza oferte facut");
        String id = jwtProvider.getUsername(bearer);
        ServiceModel serviceModel = servicetoAccService.get_Service_id(Integer.valueOf(id));
        if (serviceModel != null) {
            List<Oferta> ofertaf = ofertatoServiceService.get_oferte_service(serviceModel.getService_id());
            List<String> oferte = new ArrayList<>();
            for (Oferta i : ofertaf ) {
                oferte.add(i.getOferta());
            }
            return new ResponseEntity<>(oferte, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/oferte")
    public ResponseEntity<List<Oferta>> vizualizeazaoferte(@RequestParam("bearer") String bearer) {
        System.out.println("request vizualizeaza oferte facut");
        String id = jwtProvider.getUsername(bearer);
        ServiceModel serviceModel = servicetoAccService.get_Service_id(Integer.valueOf(id));
        if (serviceModel != null) {
            List<Oferta> ofertaf = ofertatoServiceService.get_oferte_service(serviceModel.getService_id());

            return new ResponseEntity<>(ofertaf, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/addoferta")
    public ResponseEntity<String> adaugaOferta(@RequestBody Oferta of, @RequestParam("bearer") String bearer) {

        OfertatoService ofertatoService;
        String id = jwtProvider.getUsername(bearer);
        ServiceModel serviceModel = servicetoAccService.get_Service_id(Integer.valueOf(id));
        if (serviceModel != null && of.getOferta() != null) {

            if (of.getOferta_id() == 0) {
                Oferta of1 = ofertaService.insert(of);
                ofertatoService = new OfertatoService();
                ofertatoService.setOferta(of1);
                ofertatoService.setSv(serviceModel);
            } else {
                ofertatoService = ofertatoServiceService.getOftoServ(serviceModel.getService_id(), of.getOferta_id());
                Oferta of1 = ofertaService.insert(of);
                ofertatoService.setOferta(of1);
            }
            ofertatoServiceService.save(ofertatoService);
            return new ResponseEntity("\"true\"", HttpStatus.OK);
        }
        return new ResponseEntity("\"false\"", HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/deleteoferta{bearer}")
    public ResponseEntity<String> deleteOferta(@RequestParam("id") int ofid, @RequestParam("bearer") String bearer) {
        String id = jwtProvider.getUsername(bearer);
        System.out.println("suntem in delete");
        System.out.println(ofid);
        ServiceModel serviceModel = servicetoAccService.get_Service_id(Integer.valueOf(id));
        Oferta oferta = ofertaService.getbyId(ofid);
        System.out.println("oferta este gasita:" + oferta.getOferta());
        System.out.println("service-u; este gasit:" + serviceModel.getId());

        if (serviceModel != null && oferta != null) {
            OfertatoService ofertatoService = ofertatoServiceService.getOftoServ(serviceModel.getService_id(), oferta.getOferta_id());
            ofertatoServiceService.delete_oferta_from_service(ofertatoService);
            ofertaService.delete(ofertaService.getbyId(ofid));
            return new ResponseEntity("\"true\"", HttpStatus.OK);
        }
        return new ResponseEntity("\"false\"", HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/getofertaByName{bearer}")
    public ResponseEntity<Oferta> deleteOferta(@RequestParam("numeoferta") String nume_oferta, @RequestParam("bearer") String bearer) {
        String id = jwtProvider.getUsername(bearer);
        System.out.println("suntem in getofertabyname :" + nume_oferta);
        ServiceModel serviceModel = servicetoAccService.get_Service_id(Integer.valueOf(id));
        for (Oferta i : ofertatoServiceService.get_oferte_service(serviceModel.getService_id())) {
            if (i.getOferta().equals(nume_oferta)) {
                System.out.println("am intrat in if " + i.getOferta());
                return new ResponseEntity<>(i, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/adaugaPachetPromotional")
    public ResponseEntity adaugapachet(@RequestBody PachetPromotional pachetPromotional, @RequestParam("bearer") String bearer) {
        SerializePachetPromotional x = new SerializePachetPromotional();
        String id = jwtProvider.getUsername(bearer);
        System.out.println("suntem in adaugare pachet");

        ServiceModel serviceModel = servicetoAccService.get_Service_id(Integer.valueOf(id));
        List<PachetPromotional> pkt;
        HashMap<Integer, List<PachetPromotional>> hashmap = x.deserializeaza();
        if(hashmap==null){
            pkt=new ArrayList<>();
            pachetPromotional.setId(1);
            pkt.add(pachetPromotional);
            hashmap=new HashMap<>();
            hashmap.put(serviceModel.getService_id(),pkt);
            x.serializeaza(hashmap);
        }
        else
        {
            pkt=hashmap.get(serviceModel.getService_id());
            int lastid=1;
            if(pkt!=null)
                lastid=pkt.get(pkt.size()-1).getId()+1;
            else
                pkt=new ArrayList<>();
            pachetPromotional.setId(lastid);
            pkt.add(pachetPromotional);
            if(hashmap.get(serviceModel.getService_id())!=null)
                hashmap.replace(serviceModel.getService_id(),pkt);
            else
            {
                pkt=new ArrayList<>();
                pachetPromotional.setId(1);
                pkt.add(pachetPromotional);
                hashmap.put(serviceModel.getService_id(),pkt);
            }
            x.serializeaza(hashmap);
        }
    return new ResponseEntity(HttpStatus.OK);
}

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/pachete")
    public ResponseEntity<List<PachetPromotional>> getPachete(@RequestParam("bearer") String bearer) {
        System.out.println("request vizualizeaza pachete facut");
        String id = jwtProvider.getUsername(bearer);
        ServiceModel serviceModel = servicetoAccService.get_Service_id(Integer.valueOf(id));
        SerializePachetPromotional x=new SerializePachetPromotional();
        if (serviceModel != null) {
            List<PachetPromotional> pkt = x.deserializeaza().get(serviceModel.getService_id());
            for(PachetPromotional i:pkt){
                System.out.println("Suntem in Pachet");
                System.out.println(i.getDurata_reparatie()+ i.getPret());
            }
            return new ResponseEntity<>(pkt, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/deletepachet{bearer}")
    public ResponseEntity<String> deletepachet(@RequestParam("id") int pachetid, @RequestParam("bearer") String bearer) {
        String id = jwtProvider.getUsername(bearer);
        System.out.println("suntem in delete pachet");
        System.out.println(pachetid);
        ServiceModel serviceModel = servicetoAccService.get_Service_id(Integer.valueOf(id));
        SerializePachetPromotional x=new SerializePachetPromotional();
        HashMap<Integer,List<PachetPromotional>> hashmap;
        if (serviceModel != null) {
            hashmap=x.deserializeaza();
            List<PachetPromotional> pkt=hashmap.get(serviceModel.getService_id());
            for(PachetPromotional i:pkt){
                if(i.getId()==pachetid){
                    pkt.remove(i);
                    hashmap.replace(serviceModel.getService_id(),pkt);
                    break;}
            }

            x.serializeaza(hashmap);

            return new ResponseEntity("\"true\"", HttpStatus.OK);
        }
        return new ResponseEntity("\"false\"", HttpStatus.OK);
    }



}
