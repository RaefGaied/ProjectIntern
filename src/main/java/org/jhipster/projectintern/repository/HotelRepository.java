package org.jhipster.projectintern.repository;

import org.jhipster.projectintern.domain.Hotel;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Hotel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findByNom(String nom);


    @Query("SELECT h FROM Hotel h WHERE h.ville = :location AND h.capacite >= :totalGuests")
    List<Hotel> searchHotels(
        @Param("location") String location,
        @Param("totalGuests") int totalGuests
    );
    @Query("SELECT h FROM Hotel h LEFT JOIN FETCH h.services WHERE h.id = :id")
    Optional<Hotel> findOneWithServices(@Param("id") Long id);



}
