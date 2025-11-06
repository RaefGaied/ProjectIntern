package org.jhipster.projectintern.repository;

import java.util.List;
import java.util.Optional;

import org.jhipster.projectintern.domain.Reservation;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Reservation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("select reservation from Reservation reservation where reservation.user.login = ?#{authentication.name}")
    List<Reservation> findByUserIsCurrentUser();
    @Query("SELECT r FROM Reservation r JOIN FETCH r.user WHERE r.id = :id")
    Optional<Reservation> findByIdWithUser(@Param("id") Long id);
}
