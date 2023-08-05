package latihan.belajarspring.service;


import latihan.belajarspring.entity.Contact;
import latihan.belajarspring.entity.User;
import latihan.belajarspring.model.ContactRequest;
import latihan.belajarspring.repository.ContactRepository;
import latihan.belajarspring.repository.UserRepository;
import latihan.belajarspring.utils.RequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RequestValidator requestValidator;

    @Transactional
    public Contact create(User user, ContactRequest request){
        requestValidator.<ContactRequest>validate(request);

        Contact contact = new Contact();
        contact.setPhone(request.getPhone());
        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setEmail(request.getEmail());
        contact.setUser(user);

        contact = contactRepository.save(contact);

        return contact;
    }

    @Transactional
    public Contact update(User user, ContactRequest request,Long id){
        requestValidator.<ContactRequest>validate(request);

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Contact not found"));

        contact.setPhone(request.getPhone());
        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setEmail(request.getEmail());
        contact.setUser(user);

        contact = contactRepository.save(contact);

        return contact;
    }

    @Transactional(readOnly = true)
    public List<Contact> view(User user){
        List<Contact> contacts = contactRepository.findByUser_Username(user.getUsername())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"user not found"));

        return contacts;
    }

    @Transactional
    public void delete(User user,Long id){
        contactRepository.deleteById(id);
    }


}
