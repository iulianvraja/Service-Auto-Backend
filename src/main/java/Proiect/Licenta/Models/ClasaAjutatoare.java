package Proiect.Licenta.Models;

import java.util.Arrays;
import java.util.Objects;

public class ClasaAjutatoare {
    private int id;
    private int idsv;
    private float pret;
    int [] arr;
    private int isSet;

    public ClasaAjutatoare(int id, int idsv, float pret,int[] arr) {
        this.id = id;
        this.idsv = idsv;
        this.pret = pret;
        this.arr=arr;
    }
    public ClasaAjutatoare(int isSet){
        this.isSet=isSet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdsv() {
        return idsv;
    }

    public void setIdsv(int idsv) {
        this.idsv = idsv;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public int[] getArr() {
        return arr;
    }

    public void setArr(int[] arr) {
        this.arr = arr;
    }

    @Override
    public String toString() {
        return "ClasaAjutatoare{" +
                "id=" + id +
                ", idsv=" + idsv +
                ", pret=" + pret +
                '}';
    }

    public int getIsSet() {
        return isSet;
    }

    public void setIsSet(int isSet) {
        this.isSet = isSet;
    }

    public boolean equals2(ClasaAjutatoare x) {

        return id == x.id && idsv == x.idsv && Float.compare(x.pret, pret) == 0;
    }



}
