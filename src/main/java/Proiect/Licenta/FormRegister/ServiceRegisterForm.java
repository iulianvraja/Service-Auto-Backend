package Proiect.Licenta.FormRegister;


import org.springframework.web.multipart.MultipartFile;



public class ServiceRegisterForm{
    private String email;
    private String adresa;
    private String telefon;
    private MultipartFile acte;



    private String username;

    private String password;

    public ServiceRegisterForm(String email, String adresa, String telefon, MultipartFile acte, String username, String password) {
        this.email = email;
        this.adresa = adresa;
        this.telefon = telefon;
        this.acte = acte;
        this.username = username;
        this.password = password;
    }

    public ServiceRegisterForm() {
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

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public MultipartFile getActe() {
        return acte;
    }

    public void setActe(MultipartFile acte) {
        this.acte = acte;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
