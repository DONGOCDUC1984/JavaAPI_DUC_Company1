package JavaAPI_DUC_Company1.repository.auth;

import JavaAPI_DUC_Company1.model.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository
        extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
