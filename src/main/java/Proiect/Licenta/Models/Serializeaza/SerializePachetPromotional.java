package Proiect.Licenta.Models.Serializeaza;

import Proiect.Licenta.Models.PachetPromotional;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class SerializePachetPromotional {

    public void serializeaza(HashMap<Integer, List<PachetPromotional>> hashpachet){
        String filename = "pachete_promotionale.ser";
        try
        {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream("pachete_promotionale.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(hashpachet);

            out.close();
            file.close();

            System.out.println("Object has been serialized");

        }

        catch(IOException ex)
        {   ex.printStackTrace();
            System.out.println("IOException is caught");
        }

    }

    public HashMap<Integer, List<PachetPromotional>> deserializeaza(){
        String filename = "pachete_promotionale.ser";
        HashMap<Integer, List<PachetPromotional>> object1=null;
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
        object1 = (HashMap<Integer, List<PachetPromotional>> )in.readObject();

            in.close();
            file.close();

            System.out.println("Object has been deserialized ");

        }

        catch(Exception ex){
            System.out.println("avem o exceptie");
        }
        return object1;
    }
}
