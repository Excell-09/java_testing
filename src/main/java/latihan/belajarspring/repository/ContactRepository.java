package latihan.belajarspring.repository;

import latihan.belajarspring.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {
    Optional<List<Contact>> findByUser_Username(String username);
}
