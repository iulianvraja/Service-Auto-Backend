package Proiect.Licenta.Repositories;

import Proiect.Licenta.Models.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ServiceRepo extends JpaRepository<ServiceModel,Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE ServiceModel SET approve = :ap WHERE service_id = :id")
    void aproba(int ap,int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ServiceModel SET rating = :ratingg WHERE service_id = :idd")
    void setrating(float ratingg,int idd);
}
