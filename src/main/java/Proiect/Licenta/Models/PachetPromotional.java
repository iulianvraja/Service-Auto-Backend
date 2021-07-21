package Proiect.Licenta.Models;

import java.util.List;

public class PachetPromotional implements java.io.Serializable{
    int id;
    private String durata_reparatie;
    private List<Oferta> oferte_pachet;
    private float pret;

    public PachetPromotional(){}

    public PachetPromotional(String durata_reparatie,List<Oferta> oferte_pachet, float pret) {
        this.durata_reparatie=durata_reparatie;
        this.oferte_pachet = oferte_pachet;
        this.pret = pret;
    }

    public List<Oferta> getOferte_pachet() {
        return oferte_pachet;
    }

    public void setOferte_pachet(List<Oferta> oferte_pachet) {
        this.oferte_pachet = oferte_pachet;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDurata_reparatie() {
        return durata_reparatie;
    }

    public void setDurata_reparatie(String durata_reparatie) {
        this.durata_reparatie = durata_reparatie;
    }
}
