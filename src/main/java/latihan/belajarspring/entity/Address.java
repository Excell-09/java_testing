package latihan.belajarspring.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    private String id;
    private String street;
    private String city;
    private String province;
    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "contact_id",referencedColumnName = "id")
    private Contact contact;
}
