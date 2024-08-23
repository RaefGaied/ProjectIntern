package org.jhipster.projectintern.service;
import org.jhipster.projectintern.domain.Hotel;
import org.jhipster.projectintern.domain.Partenaire;
import org.jhipster.projectintern.domain.Reservation;
import org.jhipster.projectintern.repository.HotelRepository;
import org.jhipster.projectintern.repository.PartenaireRepository;
import org.jhipster.projectintern.repository.ReservationRepository;
import org.jhipster.projectintern.repository.ServiceRepository;
import org.jhipster.projectintern.service.dto.ServiceDTO;
import org.jhipster.projectintern.service.mapper.ServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link org.jhipster.projectintern.domain.Service}.
 */
@Service
@Transactional
public class ServiceService {

    private static final Logger log = LoggerFactory.getLogger(ServiceService.class);

    private final ServiceRepository serviceRepository;

    private final ServiceMapper serviceMapper;
    private final HotelRepository hotelRepository;
    private final PartenaireRepository partenaireRepository;
    private final ReservationRepository reservationRepository;

    public ServiceService(ServiceRepository serviceRepository, ServiceMapper serviceMapper, HotelRepository hotelRepository, PartenaireRepository partenaireRepository, ReservationRepository reservationRepository) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
        this.hotelRepository = hotelRepository;
        this.partenaireRepository = partenaireRepository;
        this.reservationRepository = reservationRepository;
    }

    /**
     * Save a service.
     *
     * @param serviceDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceDTO save(ServiceDTO serviceDTO) {
        log.debug("Request to save Service : {}", serviceDTO);

        try {
            if (serviceDTO.getId() != null) {
                serviceDTO.setId(serviceDTO.getId());
            }
            if (serviceDTO.getNom()==null || serviceDTO.getNom().isEmpty()) {
                throw new IllegalArgumentException(" Service Name  cannot be empty");
            }
            if (serviceDTO.getDescription() == null || serviceDTO.getDescription().isEmpty()) {
                throw new IllegalArgumentException(" Description cannot be empty");
            }
            if (serviceDTO.getPrix() == null || serviceDTO.getPrix()==0){
                throw new IllegalArgumentException(" Prix cannot be empty");
            }
            if (serviceDTO.getDisponibilite() == null || serviceDTO.getDisponibilite().isEmpty()) {
                throw new IllegalArgumentException(" Disponibilite cannot be empty");
            }
            if (serviceDTO.getCapacite() == null || serviceDTO.getCapacite()==0){
                throw new IllegalArgumentException(" Capacite cannot be empty");
            }
            if (serviceDTO.getTypeService()==null || serviceDTO.getTypeService().isEmpty()) {
                throw new IllegalArgumentException(" Type Service cannot be empty");
            }

            Hotel hotel = hotelRepository.findById(serviceDTO.getHotel().getId())
                .orElseThrow(() -> new IllegalArgumentException("Hotel does not exist"));
            Partenaire partenaire = partenaireRepository.findById(serviceDTO.getPartenaire().getId())
                .orElseThrow(() -> new IllegalArgumentException("Partenaire does not exist"));
            Reservation reservation = reservationRepository.findById(serviceDTO.getReservation().getId())
                .orElseThrow(() -> new IllegalArgumentException("Reservation does not exist"));

            org.jhipster.projectintern.domain.Service service = serviceMapper.toEntity(serviceDTO);
            service.setHotel(hotel);
            service.setPartenaire(partenaire);
            service.setReservation(reservation);

            service = serviceRepository.save(service);
            return serviceMapper.toDto(service);
        }catch (Exception e) {
            log.error("Error saving Service: ", e);
            throw new RuntimeException("Unable to save Service,Please fill in all fields", e);}
    }

    /**
     * Update a service.
     *
     * @param serviceDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceDTO update(ServiceDTO serviceDTO) {
        log.debug("Request to update Service : {}", serviceDTO);
        org.jhipster.projectintern.domain.Service service = serviceMapper.toEntity(serviceDTO);
        service = serviceRepository.save(service);
        return serviceMapper.toDto(service);
    }

    /**
     * Partially update a service.
     *
     * @param serviceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ServiceDTO> partialUpdate(ServiceDTO serviceDTO) {
        log.debug("Request to partially update Service : {}", serviceDTO);

        return serviceRepository
            .findById(serviceDTO.getId())
            .map(existingService -> {
                serviceMapper.partialUpdate(existingService, serviceDTO);

                return existingService;
            })
            .map(serviceRepository::save)
            .map(serviceMapper::toDto);
    }

    /**
     * Get all the services.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Services");
        return serviceRepository.findAll(pageable).map(serviceMapper::toDto);
    }

    /**
     * Get one service by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceDTO> findOne(Long id) {
        log.debug("Request to get Service : {}", id);
        return serviceRepository.findById(id).map(serviceMapper::toDto);
    }

    /**
     * Delete the service by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Service : {}", id);
        serviceRepository.deleteById(id);
    }
}
