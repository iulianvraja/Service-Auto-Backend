package Proiect.Licenta.Models;

import javax.persistence.*;

@Table(name="Review")
@Entity
public class Review implements java.io.Serializable{
    @Id
    @Column(name = "id")
    @GeneratedValue
    int id;
    @Column(name = "review")
    private String review;
    @Column(name = "nota")
    private float nota;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "service_id")
    private ServiceModel sv;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "acc_id")
    private Account account;
    public Review(){}

    public Review(String review, float nota) {
        this.review = review;
        this.nota = nota;
    }

    public Review(int id, String review, float nota, ServiceModel sv, Account idacc) {
        this.id = id;
        this.review = review;
        this.nota = nota;
        this.sv = sv;
        this.account = idacc;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ServiceModel getSv() {
        return sv;
    }

    public void setSv(ServiceModel sv) {
        this.sv = sv;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
