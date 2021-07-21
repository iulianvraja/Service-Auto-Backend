package Proiect.Licenta.Models;

import javax.persistence.*;

@Table(name="ServicetoAccount")
@Entity
public class ServicetoAccount {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "service_id")
    private ServiceModel sv;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "acc_id")
    private Account idacc;

    public ServicetoAccount(int id, ServiceModel sv, Account idacc) {
        this.id = id;
        this.sv = sv;
        this.idacc = idacc;
    }

    public ServicetoAccount(){}

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

    public Account getIdacc() {
        return idacc;
    }

    public void setIdacc(Account idacc) {
        this.idacc = idacc;
    }
}
