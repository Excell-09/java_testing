package latihan.belajarspring.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;

    private String password;

    private String name;

    private String token;

    @Column(name = "token_expired_at")
    private Long tokenExpiredAt;

    @OneToMany(mappedBy = "user")
    private List<Contact> contacts;
}
