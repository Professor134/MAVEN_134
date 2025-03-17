package in.starmaven.wealthwise.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import in.starmaven.wealthwise.repository.FamilyRepository;

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

    // public void setPassword(String rawPassword) {
    //     this.password = new BCryptPasswordEncoder().encode(rawPassword);
    // }

    // public boolean isPasswordMatch(String rawPassword) {
    //     return new BCryptPasswordEncoder().matches(rawPassword, this.password);
    // }

    // public void setFamilyName (String family_name, FamilyRepository familyRepository)  {
    //     this.family_name = family_name;
    //     this.family = familyRepository.findByname(family_name).orElseThrow(() -> new RuntimeException("Family not found: " + family_name));
    //     // this.family = foundFamily;
    // }
}