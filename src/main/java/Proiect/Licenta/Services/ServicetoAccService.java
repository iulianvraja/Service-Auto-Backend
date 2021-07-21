package Proiect.Licenta.Services;

import Proiect.Licenta.Models.Account;
import Proiect.Licenta.Models.ServiceModel;
import Proiect.Licenta.Models.ServicetoAccount;
import Proiect.Licenta.Repositories.AccountRepository;
import Proiect.Licenta.Repositories.ServicetoAccRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicetoAccService {
    private final ServicetoAccRepo repo;
    private final AccountRepository repoacc;

    @Autowired
    public ServicetoAccService(ServicetoAccRepo repo, AccountRepository repoacc) {
        this.repo = repo;
        this.repoacc = repoacc;
    }

    public ServicetoAccount save(ServicetoAccount s){
        return repo.save(s);
    }

    public ServiceModel get_Service_id(int idac){
        int id=-1;
        for(ServicetoAccount i:repo.findAll()){
            if(i.getIdacc().getAccount_id()==idac){
               return i.getSv();
            }
        }
        return null;
    }

    public Account getAccc(int service_id){
        int id=-1;
        for(ServicetoAccount i:repo.findAll()){
            if(i.getSv().getId()==service_id){
                return i.getIdacc();
            }
        }
        return null;
    }

    public void delete(int idservice){
        Account x;
        for(ServicetoAccount i:repo.findAll()) {
            if (i.getSv().getId() == idservice){
                x=i.getIdacc();
                repo.delete(i);
                repoacc.delete(x);
            }
        }
    }
}
