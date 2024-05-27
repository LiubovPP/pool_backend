package de.ait.pool.repository;

import de.ait.pool.models.ConfirmationCode;
import de.ait.pool.dto.userDto.UserDto;
import de.ait.pool.models.ConfirmationCode;
import de.ait.pool.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findFirstByCodesContains(ConfirmationCode code);
}
