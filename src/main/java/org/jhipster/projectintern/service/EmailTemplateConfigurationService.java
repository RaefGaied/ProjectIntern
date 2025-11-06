package org.jhipster.projectintern.service;

import org.jhipster.projectintern.domain.EmailTemplateConfiguration;
import org.jhipster.projectintern.domain.Hotel;
import org.jhipster.projectintern.domain.Partenaire;
import org.jhipster.projectintern.repository.EmailTemplateConfigurationRepository;
import org.jhipster.projectintern.repository.HotelAdministrateurRepository;
import org.jhipster.projectintern.repository.HotelRepository;
import org.jhipster.projectintern.service.dto.EmailTemplateConfigurationDTO;
import org.jhipster.projectintern.service.dto.HotelDTO;
import org.jhipster.projectintern.service.mapper.EmailTemplateConfigurationMapper;
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
 * Service Implementation for managing {@link org.jhipster.projectintern.domain.EmailTemplateConfiguration}.
 */
@Service
@Transactional
public class EmailTemplateConfigurationService {

    private static final Logger log = LoggerFactory.getLogger(EmailTemplateConfigurationService.class);

    private final EmailTemplateConfigurationRepository emailTemplateConfigurationRepository;

    private final EmailTemplateConfigurationMapper emailTemplateConfigurationMapper;
    private final HotelService hotelService;
    private HotelRepository hotelRepository;
    private HotelAdministrateurRepository hotelAdministrateurRepository;

    private HotelDTO mapHotelToDTO(Hotel hotel) {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setId(hotel.getId());
        hotelDTO.setNom(hotel.getNom());
        return hotelDTO;
    }

    public EmailTemplateConfigurationService(
        EmailTemplateConfigurationRepository emailTemplateConfigurationRepository,
        EmailTemplateConfigurationMapper emailTemplateConfigurationMapper, HotelService hotelService, HotelRepository hotelRepository, HotelAdministrateurRepository hotelAdministrateurRepository
    ) {
        this.emailTemplateConfigurationRepository = emailTemplateConfigurationRepository;
        this.emailTemplateConfigurationMapper = emailTemplateConfigurationMapper;
        this.hotelService = hotelService;
        this.hotelRepository = hotelRepository;
        this.hotelAdministrateurRepository = hotelAdministrateurRepository;
    }


    /**
     * Save a emailTemplateConfiguration.
     *
     * @param emailTemplateConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    public EmailTemplateConfigurationDTO save(EmailTemplateConfigurationDTO emailTemplateConfigurationDTO) {
        log.debug("Request to save EmailTemplateConfiguration : {}", emailTemplateConfigurationDTO);
        try {
            // Validate fields
            if (emailTemplateConfigurationDTO.getNomTemplate() == null || emailTemplateConfigurationDTO.getNomTemplate().isEmpty()) {
                throw new IllegalArgumentException("EmailTemplate Name cannot be empty");
            }
            if (emailTemplateConfigurationDTO.getCorps() == null || emailTemplateConfigurationDTO.getCorps().isEmpty()) {
                throw new IllegalArgumentException("EmailTemplate Corps cannot be empty");
            }


            EmailTemplateConfiguration emailTemplateConfiguration = emailTemplateConfigurationMapper.toEntity(emailTemplateConfigurationDTO);
            emailTemplateConfiguration = emailTemplateConfigurationRepository.save(emailTemplateConfiguration);
            return emailTemplateConfigurationMapper.toDto(emailTemplateConfiguration);

        } catch (Exception e) {
            log.error("Error saving EmailTemplateConfiguration: ", e);
            throw new RuntimeException("Unable to save EmailTemplateConfiguration, please fill in all fields", e);
        }
    }

    /**
     * Update a emailTemplateConfiguration.
     *
     * @param emailTemplateConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    public EmailTemplateConfigurationDTO update(EmailTemplateConfigurationDTO emailTemplateConfigurationDTO) {
        log.debug("Request to update EmailTemplateConfiguration : {}", emailTemplateConfigurationDTO);
        EmailTemplateConfiguration emailTemplateConfiguration = emailTemplateConfigurationMapper.toEntity(emailTemplateConfigurationDTO);
        emailTemplateConfiguration = emailTemplateConfigurationRepository.save(emailTemplateConfiguration);
        return emailTemplateConfigurationMapper.toDto(emailTemplateConfiguration);
    }

    /**
     * Partially update a emailTemplateConfiguration.
     *
     * @param emailTemplateConfigurationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmailTemplateConfigurationDTO> partialUpdate(EmailTemplateConfigurationDTO emailTemplateConfigurationDTO) {
        log.debug("Request to partially update EmailTemplateConfiguration : {}", emailTemplateConfigurationDTO);

        return emailTemplateConfigurationRepository
            .findById(emailTemplateConfigurationDTO.getId())
            .map(existingEmailTemplateConfiguration -> {
                emailTemplateConfigurationMapper.partialUpdate(existingEmailTemplateConfiguration, emailTemplateConfigurationDTO);

                return existingEmailTemplateConfiguration;
            })
            .map(emailTemplateConfigurationRepository::save)
            .map(emailTemplateConfigurationMapper::toDto);
    }

    /**
     * Get all the emailTemplateConfigurations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmailTemplateConfigurationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmailTemplateConfigurations");
        return emailTemplateConfigurationRepository.findAll(pageable).map(emailTemplateConfigurationMapper::toDto);
    }

    /**
     *  Get all the emailTemplateConfigurations where Hotel is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmailTemplateConfigurationDTO> findAllWhereHotelIsNull() {
        log.debug("Request to get all emailTemplateConfigurations where Hotel is null");
        return StreamSupport.stream(emailTemplateConfigurationRepository.findAll().spliterator(), false)
            .filter(emailTemplateConfiguration -> emailTemplateConfiguration.getHotel() == null)
            .map(emailTemplateConfigurationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one emailTemplateConfiguration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmailTemplateConfigurationDTO> findOne(Long id) {
        log.debug("Request to get EmailTemplateConfiguration : {}", id);
        return emailTemplateConfigurationRepository.findById(id).map(emailTemplateConfigurationMapper::toDto);
    }

    /**
     * Delete the emailTemplateConfiguration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmailTemplateConfiguration : {}", id);
        emailTemplateConfigurationRepository.deleteById(id);
    }

}
