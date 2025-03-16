package in.starmaven.wealthwise.repository;

import in.starmaven.wealthwise.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;//to do CRUD operations on family table
import java.util.Optional;

public interface FamilyRepository extends JpaRepository<Family, Long> {
    Optional<Family> findByname(String familyName);
}
