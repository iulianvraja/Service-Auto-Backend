package Proiect.Licenta.Repositories;

import Proiect.Licenta.Models.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OfertaRepo extends JpaRepository<Oferta,Integer> {

    @Query(
            value = "SELECT p FROM Oferta p WHERE p.oferta_id=:id"
            )
    Oferta findofertabyid(int id);

}
