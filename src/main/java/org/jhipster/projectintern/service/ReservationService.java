package org.jhipster.projectintern.service;

import org.jhipster.projectintern.domain.Hotel;
import org.jhipster.projectintern.domain.Reservation;
import org.jhipster.projectintern.domain.User;
import org.jhipster.projectintern.repository.HotelRepository;
import org.jhipster.projectintern.repository.ReservationRepository;
import org.jhipster.projectintern.repository.UserRepository;
import org.jhipster.projectintern.service.dto.ReservationDTO;
import org.jhipster.projectintern.service.mapper.ReservationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link org.jhipster.projectintern.domain.Reservation}.
 */
@Service
@Transactional
public class ReservationService {

    private static final Logger log = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationMapper reservationMapper, HotelRepository hotelRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a reservation.
     *
     * @param reservationDTO the entity to save.
     * @return the persisted entity.
     */
    public ReservationDTO save(ReservationDTO reservationDTO) {
        log.debug("Request to save Reservation : {}", reservationDTO);

        try {
            // Validate fields
            if (reservationDTO.getId() == null) {
                reservationDTO.setId(reservationDTO.getId());
            }
            if (reservationDTO.getNombrePersonnes() == null || reservationDTO.getNombrePersonnes() == 0) {
                throw new IllegalArgumentException("Reservation PeopleNumber cannot be empty");
            }
            if (reservationDTO.getTotalPrix() == null || reservationDTO.getTotalPrix() == 0) {
                throw new IllegalArgumentException("Reservation TotalPrix cannot be empty");
            }
            if (reservationDTO.getStatutPaiement() == null) {
                throw new IllegalArgumentException("Reservation StatutPaiement cannot be empty");
            }

            // Check if the user is associated with the reservation
            if (reservationDTO.getUser().getId() == null) {
                throw new IllegalArgumentException("Reservation must be associated with a user");
            }

            // Check if the hotel is associated with the reservation
            if (reservationDTO.getHotel().getId() == null) {
                throw new IllegalArgumentException("Reservation must be associated with a hotel");
            }

            // Fetch user and hotel entities (assuming you have repositories for User and Hotel)
            User user = userRepository.findById(reservationDTO.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User does not exist"));
            Hotel hotel = hotelRepository.findById(reservationDTO.getHotel().getId())
                .orElseThrow(() -> new IllegalArgumentException("Hotel does not exist"));

            // Map DTO to entity and save
            Reservation reservation = reservationMapper.toEntity(reservationDTO);
            reservation.setUser(user); // Associate the user
            reservation.setHotel(hotel); // Associate the hotel

            reservation = reservationRepository.save(reservation);
            return reservationMapper.toDto(reservation);

        } catch (Exception e) {
            log.error("Error saving Reservation: ", e);
            throw new RuntimeException("Unable to save Reservation, please fill in all fields", e);
        }
    }



    /**
     * Update a reservation.
     *
     * @param reservationDTO the entity to save.
     * @return the persisted entity.
     */
    public ReservationDTO update(ReservationDTO reservationDTO) {
        log.debug("Request to update Reservation : {}", reservationDTO);
            Reservation reservation = reservationMapper.toEntity(reservationDTO);
            reservation = reservationRepository.save(reservation);
            return reservationMapper.toDto(reservation);

    }

    /**
     * Partially update a reservation.
     *
     * @param reservationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ReservationDTO> partialUpdate(ReservationDTO reservationDTO) {
        log.debug("Request to partially update Reservation : {}", reservationDTO);

        return reservationRepository
            .findById(reservationDTO.getId())
            .map(existingReservation -> {
                reservationMapper.partialUpdate(existingReservation, reservationDTO);

                return existingReservation;
            })
            .map(reservationRepository::save)
            .map(reservationMapper::toDto);
    }

    /**
     * Get all the reservations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReservationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Reservations");
        return reservationRepository.findAll(pageable).map(reservationMapper::toDto);
    }

    /**
     *  Get all the reservations where Paiement is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ReservationDTO> findAllWherePaiementIsNull() {
        log.debug("Request to get all reservations where Paiement is null");
        return StreamSupport.stream(reservationRepository.findAll().spliterator(), false)
            .filter(reservation -> reservation.getPaiement() == null)
            .map(reservationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one reservation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReservationDTO> findOne(Long id) {
        log.debug("Request to get Reservation : {}", id);
        return reservationRepository.findById(id).map(reservationMapper::toDto);
    }

    /**
     * Delete the reservation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Reservation : {}", id);
        reservationRepository.deleteById(id);
    }
}
