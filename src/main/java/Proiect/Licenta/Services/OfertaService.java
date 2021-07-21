package Proiect.Licenta.Services;

import Proiect.Licenta.Models.Oferta;
import Proiect.Licenta.Repositories.OfertaRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfertaService {
    private final OfertaRepo repo_oferta;

    public OfertaService(OfertaRepo repo_oferta) {
        this.repo_oferta = repo_oferta;
    }
    public Oferta insert(Oferta p){
        return repo_oferta.save(p);
    }
    public void delete(Oferta p){
        repo_oferta.delete(p);
    }

    public Oferta getbyId(int id){
        System.out.println("id trimis la cautare:"+id);
        return repo_oferta.findById(id).get();
    }
    public List<Oferta> toate_of(){
        return repo_oferta.findAll();
    }
}
