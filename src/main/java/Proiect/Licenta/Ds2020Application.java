package Proiect.Licenta;

import Proiect.Licenta.Models.AlgoritmBestPrice;
import Proiect.Licenta.Models.ClasaAjutatoare;
import Proiect.Licenta.Models.Oferta;
import Proiect.Licenta.Models.PachetPromotional;
import Proiect.Licenta.Models.Serializeaza.SerializePachetPromotional;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@SpringBootApplication
@Validated
public class Ds2020Application extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Ds2020Application.class);
    }

    public static void main(String[] args) {
       //TimeZone.setDefault(TimeZone.getTimeZone("UTC"));


      SpringApplication.run(Ds2020Application.class, args);

        /*List<Oferta> xx=new ArrayList<>();
        List<String> ofete=new ArrayList<>();
        ofete.add("Schimbare Ambreiaj");
        ofete.add("Schimbare Senzor");

        AlgoritmBestPrice x=new AlgoritmBestPrice(xx,ofete);
        x.back(0,x.getArray1());
        for(ClasaAjutatoare i:x.getSolutie())
            System.out.println(i.getPret());*/


    }
}
