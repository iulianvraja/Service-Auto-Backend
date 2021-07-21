package Proiect.Licenta.Services;

import Proiect.Licenta.Models.DTOS.ServiceDTO;
import Proiect.Licenta.Models.ServiceModel;
import Proiect.Licenta.Repositories.ServiceRepo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceSv {
    private final ServiceRepo repo;
    @Autowired
    public ServiceSv(ServiceRepo repo) {
        this.repo = repo;
    }

    public ServiceModel save(ServiceModel s) throws IOException {

        ServiceModel s2=repo.save(s);
        File file = new File("C:\\Proiect_licenta_backend\\src\\main\\resources\\Acte\\"+s2.getId()+".pdf");
        File file2 = new File("C:\\Proiect_licenta_backend\\src\\main\\resources\\Sigle\\"+s2.getId()+".png");
        s.getSigla().transferTo(file2);
        s.getActe().transferTo(file);
        return s2;
    }

    public void delete(ServiceModel s){
        repo.delete(s);
    }


    public void approve(int id){
        repo.aproba(1,id);
    }

    public List<ServiceModel> findall(){
        return repo.findAll();
    }
    public Optional<ServiceModel> findbyid(int id){
        return repo.findById(id);
    }

public List<ServiceDTO> findALL() throws IOException {
    List<ServiceDTO> retserv=new ArrayList<>();
    List<ServiceModel> serviceModels=repo.findAll();
    for(ServiceModel i:serviceModels){
    ServiceDTO dt=new ServiceDTO(i.getService_id(),i.getNume(),i.getEmail(),i.getAdresa(),i.getRating(), i.getTelefon(),getmultipart(i.getService_id()), i.getApprove());
   retserv.add(dt);
        System.out.println(i.getService_id());
    }
        return retserv;
}

public String getmultipart(int id) throws IOException {
    BufferedImage bi = ImageIO.read(new File("C:\\Proiect_licenta_backend\\src\\main\\resources\\Sigle\\"+id+".png"));

    // convert BufferedImage to byte[]
    byte[] bytes = toByteArray(bi, "png");
    String bytesBase64 = Base64.encodeBase64String(bytes);


    return bytesBase64;
}

    public String getmultipartActe(int id) throws IOException {
        File file=new File("C:\\Proiect_licenta_backend\\src\\main\\resources\\Acte\\"+id+".pdf");
        byte [] bytes = Files.readAllBytes(file.toPath());
        String bytesBase64 = Base64.encodeBase64String(bytes);


        return bytesBase64;
    }

    private static byte[] toByteArray(BufferedImage bi, String format)
            throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;

    }

}
