package in.starmaven.wealthwise.repository;

import in.starmaven.wealthwise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;//to do CRUD operations on user table
import java.util.List;
// import org.springframework.stereotype.Repository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   Optional<User> findByEmail(String email);//finding by email
   List<User> findByFamilyId(Long familyId);
   boolean existsByEmail(String email);
}
