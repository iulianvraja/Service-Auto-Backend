package Proiect.Licenta.Models;

import javax.persistence.*;

@Entity
@Table(name="Oferta")
public class Oferta implements  java.io.Serializable {
    @Id
    @Column(name = "oferta_id")
    @GeneratedValue
    private int oferta_id;
    @Column(name="ofertaname")
    private String oferta;
    @Column(name="pret")
    private float pret;
    private String cod_reparatie;//cod 0 reparatie usoara cod 1 reparatie medie cod 2 reparatie grea
    @Column(name="durata_reparatie")
    private String durata_reparatie;


    public Oferta(int oferta_id, String oferta, float pret, String cod_reparatie, String durata_reparatie) {
        this.oferta_id = oferta_id;
        this.oferta = oferta;
        this.pret = pret;
        this.cod_reparatie = cod_reparatie;
        this.durata_reparatie = durata_reparatie;
    }

    public Oferta(){}



    public String getOferta() {
        return oferta;
    }

    public void setOferta(String oferta) {
        this.oferta = oferta;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public String getDurata_reparatie() {
        return durata_reparatie;
    }

    public void setDurata_reparatie(String durata_reparatie) {
        this.durata_reparatie = durata_reparatie;
    }


    public String getCod_reparatie() {
        return cod_reparatie;
    }

    public void setCod_reparatie(String cod_reparatie) {
        this.cod_reparatie = cod_reparatie;
    }

    public int getOferta_id() {
        return oferta_id;
    }

    public void setOferta_id(int oferta_id) {
        this.oferta_id = oferta_id;
    }
}
