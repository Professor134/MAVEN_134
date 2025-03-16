package in.starmaven.wealthwise.service;

import in.starmaven.wealthwise.entity.Family;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.starmaven.wealthwise.repository.FamilyRepository;
import java.util.Optional;

@Service("familyService")
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    private FamilyRepository familyRepository;

    @Override
    public Family save(Family family) {
        // Check if family with the same name already exists
        Optional<Family> existingFamily = familyRepository.findByname(family.getName());
        if (existingFamily.isPresent()) {
            throw new RuntimeException("Family with this name is already exist...");
        }
        return familyRepository.save(family);
    }
}