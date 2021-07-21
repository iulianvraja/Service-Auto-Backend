package Proiect.Licenta.Services;

import Proiect.Licenta.Models.Oferta;
import Proiect.Licenta.Models.OfertatoService;
import Proiect.Licenta.Models.ServiceModel;
import Proiect.Licenta.Repositories.OfertatoServiceRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfertatoServiceService {
    private final OfertatoServiceRepo repo;
    private final OfertaService ofservice;

    public OfertatoServiceService(OfertatoServiceRepo repo, OfertaService ofservice) {
        this.repo = repo;
        this.ofservice = ofservice;
    }

    public void save(OfertatoService x){
        repo.save(x);
    }

    public List<Oferta> get_oferte_service(int service_id){
    List<OfertatoService> of=repo.findAll();
    List<Oferta> x=new ArrayList<>();
    for(OfertatoService i: of){
        if(i.getSv().getId()==service_id){
            x.add(i.getOferta());
        }
    }
    return x;
    }

    public OfertatoService getOftoServ(int serviceid,int ofertaid){
        for(OfertatoService i: repo.findAll())
            if(i.getSv().getId()==serviceid && i.getOferta().getOferta_id()==ofertaid){
               return i;
            }
            return null;
    }

    public ServiceModel getServiceByOfId(int ofertaid){
        for(OfertatoService i: repo.findAll())
            if(i.getOferta().getOferta_id()==ofertaid){
                return i.getSv();
            }
        return null;
    }

    public void delete(int idservice){
        Oferta x;
        for(OfertatoService i:repo.findAll()) {
            if (i.getSv().getId() == idservice){
                x=i.getOferta();
                repo.delete(i);
                ofservice.delete(x);

            }
        }
    }

    public void delete_oferta_from_service(OfertatoService x){
        repo.delete(x);
    }

    public void deleteOfertafromService(int id){
        Oferta x;
        for(OfertatoService i:repo.findAll()) {
            if (i.getOferta().getOferta_id() == id){
                x=i.getOferta();
                repo.delete(i);
                ofservice.delete(x);

            }
        }
    }

    public List<OfertatoService> findall(){
        return repo.findAll();
    }
}
