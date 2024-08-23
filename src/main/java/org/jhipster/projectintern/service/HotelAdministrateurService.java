package org.jhipster.projectintern.service;

import org.jhipster.projectintern.domain.Hotel;
import org.jhipster.projectintern.domain.HotelAdministrateur;
import org.jhipster.projectintern.exception.OurException;
import org.jhipster.projectintern.repository.HotelAdministrateurRepository;
import org.jhipster.projectintern.repository.HotelRepository;
import org.jhipster.projectintern.service.dto.HotelAdministrateurDTO;
import org.jhipster.projectintern.service.mapper.HotelAdministrateurMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

/**
 * Service Implementation for managing {@link org.jhipster.projectintern.domain.HotelAdministrateur}.
 */
@Service
@Transactional
public class HotelAdministrateurService {

    private static final Logger log = LoggerFactory.getLogger(HotelAdministrateurService.class);
    private final HotelAdministrateurRepository hotelAdministrateurRepository;
    private final HotelAdministrateurMapper hotelAdministrateurMapper;
    private HotelRepository hotelRepository;

    public HotelAdministrateurService(
        HotelAdministrateurRepository hotelAdministrateurRepository,
        HotelAdministrateurMapper hotelAdministrateurMapper
    ) {
        this.hotelAdministrateurRepository = hotelAdministrateurRepository;
        this.hotelAdministrateurMapper = hotelAdministrateurMapper;
    }

    /**
     * Save a hotelAdministrateur.
     *
     * @param hotelAdministrateurDTO the entity to save.
     * @return the persisted entity.
     */
    public HotelAdministrateurDTO save(HotelAdministrateurDTO hotelAdministrateurDTO) {
        log.debug("Request to save HotelAdministrateur : {}", hotelAdministrateurDTO);
        try {
            if (hotelAdministrateurDTO.getId() != null) {
                hotelAdministrateurDTO.setId(hotelAdministrateurDTO.getId());
            }
            if (hotelAdministrateurDTO.getNom() == null || hotelAdministrateurDTO.getNom().isEmpty()){
                throw new IllegalArgumentException("HotelAdministrateur Name cannot be empty");
            }
            if (hotelAdministrateurDTO.getEmail() == null || hotelAdministrateurDTO.getEmail().isEmpty()){
                throw new IllegalArgumentException("HotelAdministrateur Name cannot be empty");
            }
            if (hotelAdministrateurDTO.getMotDePasse() == null || hotelAdministrateurDTO.getMotDePasse().isEmpty()){
                throw new IllegalArgumentException("HotelAdministrateur Name cannot be empty");
            }
            HotelAdministrateur hotelAdministrateur = hotelAdministrateurMapper.toEntity(hotelAdministrateurDTO);
            hotelAdministrateur = hotelAdministrateurRepository.save(hotelAdministrateur);

            return hotelAdministrateurMapper.toDto(hotelAdministrateur);
        }catch (Exception e) {
            log.error("Error saving Hotel: ", e);
            throw new RuntimeException("Unable to save hotelAdministrateur,Please fill in all fields", e);
        }

    }

    /**
     * Update a hotelAdministrateur.
     *
     * @param hotelAdministrateurDTO the entity to save.
     * @return the persisted entity.
     */
    public HotelAdministrateurDTO update(HotelAdministrateurDTO hotelAdministrateurDTO) {
        log.debug("Request to update HotelAdministrateur : {}", hotelAdministrateurDTO);
        HotelAdministrateur hotelAdministrateur = hotelAdministrateurMapper.toEntity(hotelAdministrateurDTO);
        hotelAdministrateur = hotelAdministrateurRepository.save(hotelAdministrateur);
        return hotelAdministrateurMapper.toDto(hotelAdministrateur);
    }

    /**
     * Partially update a hotelAdministrateur.
     *
     * @param hotelAdministrateurDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HotelAdministrateurDTO> partialUpdate(HotelAdministrateurDTO hotelAdministrateurDTO) {
        log.debug("Request to partially update HotelAdministrateur : {}", hotelAdministrateurDTO);

        return hotelAdministrateurRepository
            .findById(hotelAdministrateurDTO.getId())
            .map(existingHotelAdministrateur -> {
                hotelAdministrateurMapper.partialUpdate(existingHotelAdministrateur, hotelAdministrateurDTO);

                return existingHotelAdministrateur;
            })
            .map(hotelAdministrateurRepository::save)
            .map(hotelAdministrateurMapper::toDto);
    }

    /**
     * Get all the hotelAdministrateurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HotelAdministrateurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HotelAdministrateurs");
        return hotelAdministrateurRepository.findAll(pageable).map(hotelAdministrateurMapper::toDto);
    }

    /**
     * Get one hotelAdministrateur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HotelAdministrateurDTO> findOne(Long id) {
        log.debug("Request to get HotelAdministrateur : {}", id);
        return hotelAdministrateurRepository.findById(id).map(hotelAdministrateurMapper::toDto);
    }

    /**
     * Delete the hotelAdministrateur by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HotelAdministrateur : {}", id);
        hotelAdministrateurRepository.deleteById(id);
    }
    /**
     * Get one HotelAdministrateur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HotelAdministrateurDTO> getHotelAdministrateurById(Long id) {
        log.debug("Request to get HotelAdministrateur : {}", id);
        return hotelAdministrateurRepository.findById(id)
            .map(hotelAdministrateurMapper::toDto);
    }

    public ResponseEntity<?> associateHotelToAdmin(Long hotelId, Long adminId) {
        try {
            Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new OurException("Hotel Not Found"));
            HotelAdministrateur admin = hotelAdministrateurRepository.findById(adminId)
                .orElseThrow(() -> new OurException("Admin Not Found"));

            if (admin.getHotels() != null && !admin.getHotels().isEmpty()) {
                throw new OurException("Admin is already associated with another hotel");
            }
            admin.setHotels(Collections.singleton(hotel));
            hotelAdministrateurRepository.save(admin);
            return new ResponseEntity<>("Hotel successfully associated with the Admin.", HttpStatus.OK);

        } catch (OurException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error associating hotel with admin: ", e);
            return new ResponseEntity<>("Error associating hotel with admin: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
