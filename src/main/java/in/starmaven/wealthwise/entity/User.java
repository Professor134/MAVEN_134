package in.starmaven.wealthwise.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity //it marks this class is database table
@Table(name = "user")
@Data
public class User {

    @Id //for primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;

    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private String lastName;

    //for full name
    public String fullName() {
        return (firstName+" "+(middleName != null ? middleName + " ": "")+lastName);
    }

    @Column(unique = true,nullable = false) 
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, length = 15)  
    private String contactNumber;

    @Column(nullable = false)
    private String role = "USER"; // User role (ADMIN / USER)

    @ManyToOne  //many user has one family it is Foreign key
    @JoinColumn(name = "family_id", nullable = true)
    private Family family;

    @Column // This field is not stored in the database
    private String family_name;

}