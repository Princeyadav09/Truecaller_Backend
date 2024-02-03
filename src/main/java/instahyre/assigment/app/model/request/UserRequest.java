package instahyre.assigment.app.model.request;

import instahyre.assigment.app.model.Contact;
import lombok.Data;

import java.util.List;

@Data
public class UserRequest {

     private String name;
     private String phoneNumber;
     private String password;
     private String email;
     private List<Contact> contacts;
}
