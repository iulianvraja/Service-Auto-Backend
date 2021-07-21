package Proiect.Licenta.Models;




import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="ServiceAuto")

public class ServiceModel implements Serializable {
    @Id
    @Column(name = "service_id")
    @GeneratedValue
    private int service_id;
    @Column(name="nume")
    private String nume;
    @Column(name="email")
    private String email;
    @Column(name="adresa")
    private String adresa;
    private float rating;
    @Column(name = "approve")
    private int approve;
    @Column(name="telefon")
    private String telefon;

    @Transient
    private MultipartFile sigla;
    @Transient
    private MultipartFile acte;



    public ServiceModel(String nume,String email, float rating, int approve,String adresa, String telefon, MultipartFile sigla, MultipartFile acte) {
        this.nume=nume;
        this.email = email;
        this.rating = rating;
        this.approve = approve;
        this.telefon = telefon;
        this.sigla = sigla;
        this.acte=acte;
        this.adresa=adresa;

    }
    public ServiceModel(){}



    public String getEmail() {
        return email;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }



    public String getTelefon() {
        return telefon;
    }

    public int getId() {
        return service_id;
    }

    public void setId(int id) {
        this.service_id = id;
    }



    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getApprove() {
        return approve;
    }

    public void setApprove(int approve) {
        this.approve = approve;
    }

    public MultipartFile getSigla() {
        return sigla;
    }

    public void setSigla(MultipartFile sigla) {
        this.sigla = sigla;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public MultipartFile getActe() {
        return acte;
    }

    public void setActe(MultipartFile acte) {
        this.acte = acte;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}
