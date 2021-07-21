package Proiect.Licenta.Models;

import javax.persistence.*;
@Entity
@Table(name = "OfertatoService")
public class OfertatoService {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "service_id")
    private ServiceModel sv;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "oferta_id")
    private Oferta oferta;

    public OfertatoService(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ServiceModel getSv() {
        return sv;
    }

    public void setSv(ServiceModel sv) {
        this.sv = sv;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }
}
