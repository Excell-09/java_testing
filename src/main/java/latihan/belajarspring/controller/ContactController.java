package latihan.belajarspring.controller;

import latihan.belajarspring.entity.Contact;
import latihan.belajarspring.entity.User;
import latihan.belajarspring.model.ContactResponse;
import latihan.belajarspring.model.ContactRequest;
import latihan.belajarspring.model.WebResponse;
import latihan.belajarspring.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    public WebResponse<ContactResponse> toResponseContact(Contact contact){
        ContactResponse contactResponse = new ContactResponse();
        contactResponse.setId(contact.getId());
        contactResponse.setPhone(contact.getPhone());
        contactResponse.setEmail(contact.getEmail());
        contactResponse.setFirstName(contact.getFirstName());
        contactResponse.setLastName(contact.getLastName());

        return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }

    @PostMapping(path = "/api/contact",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ContactResponse> create(User user, @RequestBody ContactRequest request){
        Contact contact = contactService.create(user,request);
        return toResponseContact(contact);
    }

    @PatchMapping(path = "/api/contact/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ContactResponse> update(
            User user,
            @RequestBody ContactRequest request,
            @PathVariable Long id
    ){
        Contact contact = contactService.update(user,request,id);
        return toResponseContact(contact);
    }

    @GetMapping(path = "/api/contact",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<ContactResponse>> view(
            User user
    ){
        List<Contact> contacts = contactService.view(user);

        List<ContactResponse> contactsResponse = new ArrayList<>();

        for (Contact contact : contacts){
            ContactResponse contactResponse = new ContactResponse();
            contactResponse.setId(contact.getId());
            contactResponse.setEmail(contact.getEmail());
            contactResponse.setPhone(contact.getPhone());
            contactResponse.setFirstName(contact.getFirstName());
            contactResponse.setLastName(contact.getLastName());
            contactsResponse.add(contactResponse);
        }

        return WebResponse.<List<ContactResponse>>builder()
                .data(contactsResponse)
                .build();
    }

    @DeleteMapping(path = "/api/contact/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> delete(
            User user,
            @PathVariable Long id
    ){
        contactService.delete(user,id);
        return WebResponse.<String>builder()
                .data("Contact deleted")
                .build();
    }


}
