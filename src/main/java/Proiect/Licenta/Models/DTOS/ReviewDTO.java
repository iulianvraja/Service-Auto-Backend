package Proiect.Licenta.Models.DTOS;

public class ReviewDTO {
    String review;
    float nota;

    public ReviewDTO(){}

    public ReviewDTO(String review, float nota) {
        this.review = review;
        this.nota = nota;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }
}
