package Proiect.Licenta.Repositories;

import Proiect.Licenta.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoReview extends JpaRepository<Review,Integer> {
}
