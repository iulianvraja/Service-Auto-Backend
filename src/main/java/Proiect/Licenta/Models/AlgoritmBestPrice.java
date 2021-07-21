package Proiect.Licenta.Models;

import Proiect.Licenta.Models.DTOS.DTOraspunsAlgoritm;
import Proiect.Licenta.Models.Serializeaza.SerializePachetPromotional;

import java.util.*;
import java.util.stream.IntStream;

public class AlgoritmBestPrice {

    List<ClasaAjutatoare> solutie_finala;
    List<ClasaAjutatoare> multimeaSolutiilor;
    int[] array1;
    int max_depth;
    public AlgoritmBestPrice(List<Oferta> off,List<String>nume_oferte){
        array1=new int[nume_oferte.size()];
        max_depth=nume_oferte.size()-1;
        multimeaSolutiilor=get_id_for_best_off(off,nume_oferte);
        intializeaza_arrayul();
    }
    void intializeaza_arrayul(){
        for(int i=0;i<array1.length;i++){
            array1[i]=0;
        }
    }

    public ClasaAjutatoare contineArr(List<ClasaAjutatoare> cls,int[] arr){
        int [] arr1;

        int nm=0;
        for(ClasaAjutatoare i:cls){
            nm=0;
            arr1=i.getArr();
            for(int j=0;j<arr1.length;j++){
                if(arr[j]==arr1[j]){
                    System.out.println(arr[j] +" "+arr1[j]);
                    nm++;}
            }
            System.out.println();
            if(nm==arr.length)
                return i;
        }
        return null;
    }



    public List<ClasaAjutatoare> get_id_for_best_off(List<Oferta> off,List<String>nume_oferte){
        //prima data luam cele mai bune oferte din service-uri
        List<ClasaAjutatoare> arr_idpachet=new ArrayList<>();

        int []arr;
       int k=1;
       //preprocesare oferte

        for(String i: nume_oferte){
            arr=new int[nume_oferte.size()];
            for(int ii = 0; ii < arr.length; ii++) {
                arr[ii] = 0;
            }
            arr[k-1]=1;

            for(Oferta j:off){
                if(j.getOferta().equals(i)){
                    ClasaAjutatoare x=contineArr(arr_idpachet,arr);
                    if(x==null){
                        arr_idpachet.add(new ClasaAjutatoare(j.getOferta_id(),0,j.getPret(),arr));
                       }
                    else{
                            float pret=x.getPret();
                            if(j.getPret()<pret){
                            arr_idpachet.set(arr_idpachet.indexOf(x),new ClasaAjutatoare(j.getOferta_id(),0,j.getPret(),arr));

                            }
                    }
                    }
                //printList(arr_idpachet);


            }
           k++;
        }

       // System.out.println("show next");
        //printList(arr_idpachet);
       // printmap(arr_idpachet);
        //preprocesare pachete
        SerializePachetPromotional x=new SerializePachetPromotional();
        HashMap<Integer,List<PachetPromotional>> hashMap=x.deserializeaza();
        Iterator it = hashMap.entrySet().iterator();
        int[] arrpack;

        while (it.hasNext()) {
            Map.Entry elm = (Map.Entry) it.next();
            List<PachetPromotional> lista = (List<PachetPromotional>) elm.getValue();
            for(PachetPromotional pkt :lista){

            arrpack=get_arr_pachet(pkt,nume_oferte);
                ClasaAjutatoare x2=contineArr(arr_idpachet,arrpack);
                if(x2==null && IntStream.of(arrpack).sum()!=0){
                    arr_idpachet.add(new ClasaAjutatoare(pkt.getId(), (Integer)elm.getKey(),pkt.getPret(),arrpack));
                   }

                else
                if(IntStream.of(arrpack).sum()!=0 && x2!=null){
                    float pret=x2.getPret();

                    if(pkt.getPret()<pret){

                        arr_idpachet.set(arr_idpachet.indexOf(x2),new ClasaAjutatoare(pkt.getId(), (Integer)elm.getKey(),pkt.getPret(),arrpack));

                    }
                }
            }
            //printList(arr_idpachet);

            }
        System.out.println("show next");
        //printmap(arr_idpachet);
       // printList(arr_idpachet);


        return arr_idpachet;
    }

    private int[] get_arr_pachet(PachetPromotional off,List<String> ofertestring) {
        int[] array = new int[ofertestring.size()];
        for (int ii = 0; ii < array.length; ii++) {
            array[ii] = 0;
        }
        for (Oferta i : off.getOferte_pachet()) {
            int index=0;
           for(String j:ofertestring){
               if(i.getOferta().equals(j)) {
                   array[index] = 1;
                   break;
               }
               index++;
           }

        }


        return  array;
    }


    int valid(int k,List<ClasaAjutatoare> solutie)
    {
        if(k==0)
            return 1;
        int[]array=new int[solutie.get(0).getArr().length];
        int [] arr=solutie.get(k).getArr();
       for(int i=0;i<k;i++){
           aduna_arr(array,solutie.get(i).getArr());
        }

        for(int i=0;i<array.length;i++){
            if(arr[i]==1 && array[i]==0){

                return 1;}
        }

                return 0;
    }

    void alege_solutia(List<ClasaAjutatoare> solutie,int k)
    { float pret1,pret2;

    List<ClasaAjutatoare> sol_partiala=new ArrayList<>();

    for(int i=0;i<=k;i++)
    {
        sol_partiala.add(solutie.get(i));
    }

        if(solutie_finala==null)
            solutie_finala=new ArrayList<>(sol_partiala);
        else
        {
            pret1=calculeaza_pret(sol_partiala);
            pret2=calculeaza_pret(solutie_finala);
            if(pret2>pret1)
                solutie_finala=new ArrayList<>(sol_partiala);
        }

    }

    private boolean amgasitsolutia(int k, List<ClasaAjutatoare> sol){
        int[]array=new int[sol.get(0).getArr().length];

        for(int i=0;i<=k;i++){
            aduna_arr(array,sol.get(i).getArr());

        }

        for(int i=0;i<array.length;i++){
            if(array[i]==0)
                return false;
        }

            return true;
    }


   public void back(int k,List<ClasaAjutatoare> solutie)
    {
       for(ClasaAjutatoare i:multimeaSolutiilor) {

            if(k>max_depth)
                break;

                solutie.set(k,i);

            if(valid(k,solutie)==1){
                if(amgasitsolutia(k,solutie)){
                    alege_solutia(solutie,k);
                    break;}
                else back(k+1,solutie);}

        }

    }
    float calculeaza_pret(List<ClasaAjutatoare> ls){
        float pret=0;
        for(ClasaAjutatoare i:ls)
            pret+=i.getPret();
        return pret;
    }

    void aduna_arr(int []array,int[] arr2){
        for(int i=0;i<array.length;i++){
            array[i]+=arr2[i];
        }
    }
    void stegrge_arr(int []array,int[] arr2){
        for(int i=0;i<array.length;i++){
            array[i]-=arr2[i];
        }
    }



    public int[] getArray1() {
        return array1;
    }

    void printList(int dimensiune,List<ClasaAjutatoare> cls){
String k;
       for(int i=0;i<=dimensiune;i++){
           k="";
           //System.out.println(cls.get(i).getPret());
           int []array=cls.get(i).getArr();
           for(int j=0;j<array.length;j++){
               k+=array[j];
           }
           System.out.println(k+" "+cls.get(i).getPret());
       }
    }

    public List<ClasaAjutatoare> getSolutie_finala() {
        return solutie_finala;
    }


}

