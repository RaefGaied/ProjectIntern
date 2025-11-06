package org.jhipster.projectintern.service;

import org.jhipster.projectintern.domain.AuthentificationConfiguration;
import org.jhipster.projectintern.domain.Hotel;
import org.jhipster.projectintern.repository.AuthentificationConfigurationRepository;
import org.jhipster.projectintern.repository.HotelRepository;
import org.jhipster.projectintern.service.dto.AuthentificationConfigurationDTO;
import org.jhipster.projectintern.service.mapper.AuthentificationConfigurationMapper;
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
 * Service Implementation for managing {@link org.jhipster.projectintern.domain.AuthentificationConfiguration}.
 */
@Service
@Transactional
public class AuthentificationConfigurationService {

    private static final Logger log = LoggerFactory.getLogger(AuthentificationConfigurationService.class);

    private final AuthentificationConfigurationRepository authentificationConfigurationRepository;

    private final AuthentificationConfigurationMapper authentificationConfigurationMapper;
    private final HotelRepository hotelRepository;

    public AuthentificationConfigurationService(
        AuthentificationConfigurationRepository authentificationConfigurationRepository,
        AuthentificationConfigurationMapper authentificationConfigurationMapper, HotelRepository hotelRepository
    ) {
        this.authentificationConfigurationRepository = authentificationConfigurationRepository;
        this.authentificationConfigurationMapper = authentificationConfigurationMapper;
        this.hotelRepository = hotelRepository;
    }

    /**
     * Save a authentificationConfiguration.
     *
     * @param authentificationConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    public AuthentificationConfigurationDTO save(AuthentificationConfigurationDTO authentificationConfigurationDTO) {
        log.debug("Request to save AuthentificationConfiguration : {}", authentificationConfigurationDTO);

        try {

            if (authentificationConfigurationDTO.getId() != null) {
                authentificationConfigurationDTO.setId(authentificationConfigurationDTO.getId());
            }
            if (authentificationConfigurationDTO.getLoginPageCustomization()==null || authentificationConfigurationDTO.getLoginPageCustomization().isEmpty()) {
                throw new IllegalArgumentException(" AuthentificationConfiguration LoginPageCustomization  cannot be empty");
            }



            AuthentificationConfiguration authentificationConfiguration = authentificationConfigurationMapper.toEntity(authentificationConfigurationDTO);
            authentificationConfiguration = authentificationConfigurationRepository.save(authentificationConfiguration);
            return authentificationConfigurationMapper.toDto(authentificationConfiguration);

        } catch (Exception e) {
            log.error("Error saving AuthentificationConfiguration: ", e);
            throw new RuntimeException("Unable to save AuthentificationConfiguration, please fill in all fields", e);
        }
    }

    /**
     * Update a authentificationConfiguration.
     *
     * @param authentificationConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    public AuthentificationConfigurationDTO update(AuthentificationConfigurationDTO authentificationConfigurationDTO) {
        log.debug("Request to update AuthentificationConfiguration : {}", authentificationConfigurationDTO);
        AuthentificationConfiguration authentificationConfiguration = authentificationConfigurationMapper.toEntity(
            authentificationConfigurationDTO
        );
        authentificationConfiguration = authentificationConfigurationRepository.save(authentificationConfiguration);
        return authentificationConfigurationMapper.toDto(authentificationConfiguration);
    }

    /**
     * Partially update a authentificationConfiguration.
     *
     * @param authentificationConfigurationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AuthentificationConfigurationDTO> partialUpdate(AuthentificationConfigurationDTO authentificationConfigurationDTO) {
        log.debug("Request to partially update AuthentificationConfiguration : {}", authentificationConfigurationDTO);

        return authentificationConfigurationRepository
            .findById(authentificationConfigurationDTO.getId())
            .map(existingAuthentificationConfiguration -> {
                authentificationConfigurationMapper.partialUpdate(existingAuthentificationConfiguration, authentificationConfigurationDTO);

                return existingAuthentificationConfiguration;
            })
            .map(authentificationConfigurationRepository::save)
            .map(authentificationConfigurationMapper::toDto);
    }

    /**
     * Get all the authentificationConfigurations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AuthentificationConfigurationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AuthentificationConfigurations");
        return authentificationConfigurationRepository.findAll(pageable).map(authentificationConfigurationMapper::toDto);
    }

    /**
     *  Get all the authentificationConfigurations where Hotel is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AuthentificationConfigurationDTO> findAllWhereHotelIsNull() {
        log.debug("Request to get all authentificationConfigurations where Hotel is null");
        return StreamSupport.stream(authentificationConfigurationRepository.findAll().spliterator(), false)
            .filter(authentificationConfiguration -> authentificationConfiguration.getHotel() == null)
            .map(authentificationConfigurationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one authentificationConfiguration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AuthentificationConfigurationDTO> findOne(Long id) {
        log.debug("Request to get AuthentificationConfiguration : {}", id);
        return authentificationConfigurationRepository.findById(id).map(authentificationConfigurationMapper::toDto);
    }

    /**
     * Delete the authentificationConfiguration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AuthentificationConfiguration : {}", id);
        authentificationConfigurationRepository.deleteById(id);
    }
}
