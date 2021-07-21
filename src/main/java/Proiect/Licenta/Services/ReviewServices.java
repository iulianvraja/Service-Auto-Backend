package Proiect.Licenta.Services;

import Proiect.Licenta.Models.Review;
import Proiect.Licenta.Repositories.RepoReview;
import Proiect.Licenta.Repositories.ServiceRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServices {
    private final RepoReview repoReview;
    private final ServiceRepo serviceRepo;

    public ReviewServices(RepoReview repoReview, ServiceRepo serviceRepo) {
        this.repoReview = repoReview;
        this.serviceRepo = serviceRepo;
    }

    public Review save(Review x){
        List<Review> lista=repoReview.findAll();
        //calculam media aritmetica:
        float suma=5+x.getNota();
        int idsv=x.getSv().getId();
        float nrreview=2;
        float rating;
        if(lista!=null)
        {
            for(Review i:lista){
                if(i.getSv().getId()==idsv){
                suma+=i.getNota();
                nrreview+=1;}
            }
        }
            rating=suma/nrreview;
            System.out.println(rating);
            serviceRepo.setrating(rating,x.getSv().getService_id());


        return repoReview.save(x);
    }

    public void  delete(int id){
        repoReview.delete(repoReview.findById(id).get());
    }

    public List<Review> getReviewByServiceId(int id){
        List<Review> reviews=new ArrayList<>();
        for(Review i:repoReview.findAll()){
            if(i.getSv().getId()==id)
                reviews.add(i);
        }
        return reviews;
    }

    public void deleteAllUserReview(int user_id){
        List<Review> reviews=new ArrayList<>();
        for(Review i:repoReview.findAll()){
            if(i.getAccount().getAccount_id()==user_id){
                repoReview.delete(i);

            }
        }
    }

}
