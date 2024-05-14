package de.ait.pool.repository;

import de.ait.pool.dto.userDto.UserDto;
import de.ait.pool.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);


    boolean existsByEmail(String email);
}
