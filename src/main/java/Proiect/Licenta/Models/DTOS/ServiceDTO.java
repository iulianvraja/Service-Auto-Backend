package Proiect.Licenta.Models.DTOS;



public class ServiceDTO {
    private int service_id;

    private String nume;

    private String email;

    private String adresa;

    private float rating;

    private String telefon;

    private  String sigla;
    private int approve;

    public ServiceDTO(int service_id, String nume, String email, String adresa, float rating, String telefon, String sigla,int approve) {
        this.service_id = service_id;
        this.nume = nume;
        this.email = email;
        this.adresa = adresa;
        this.rating = rating;
        this.telefon = telefon;
        this.sigla = sigla;
        this.approve=approve;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public int getApprove() {
        return approve;
    }

    public void setApprove(int approve) {
        this.approve = approve;
    }
}
