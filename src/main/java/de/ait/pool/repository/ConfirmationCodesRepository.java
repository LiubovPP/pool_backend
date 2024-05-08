package de.ait.pool.repository;

import de.ait.pool.models.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode,Long> {
}
