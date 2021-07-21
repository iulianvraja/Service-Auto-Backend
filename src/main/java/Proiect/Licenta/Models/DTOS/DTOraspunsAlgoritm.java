package Proiect.Licenta.Models.DTOS;

public class DTOraspunsAlgoritm {
    private String type;
    private String reparatii;
    private float pret;
    private String servicename;
    private String telefon;

    public DTOraspunsAlgoritm(String type, String reparatii, float pret, String servicename, String telefon) {
        this.type = type;
        this.reparatii = reparatii;
        this.pret = pret;
        this.servicename = servicename;
        this.telefon = telefon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReparatii() {
        return reparatii;
    }

    public void setReparatii(String reparatii) {
        this.reparatii = reparatii;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
