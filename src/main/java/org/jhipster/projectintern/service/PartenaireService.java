package org.jhipster.projectintern.service;

import org.jhipster.projectintern.domain.Partenaire;
import org.jhipster.projectintern.repository.PartenaireRepository;
import org.jhipster.projectintern.service.dto.PartenaireDTO;
import org.jhipster.projectintern.service.mapper.PartenaireMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link org.jhipster.projectintern.domain.Partenaire}.
 */
@Service
@Transactional
public class PartenaireService {

    private static final Logger log = LoggerFactory.getLogger(PartenaireService.class);

    private final PartenaireRepository partenaireRepository;

    private final PartenaireMapper partenaireMapper;

    public PartenaireService(PartenaireRepository partenaireRepository, PartenaireMapper partenaireMapper) {
        this.partenaireRepository = partenaireRepository;
        this.partenaireMapper = partenaireMapper;
    }

    /**
     * Save a partenaire.
     *
     * @param partenaireDTO the entity to save.
     * @return the persisted entity.
     */
    public PartenaireDTO save(PartenaireDTO partenaireDTO) {
        log.debug("Request to save Partenaire : {}", partenaireDTO);
        try {
            if (partenaireDTO.getId() != null) {
                partenaireDTO.setId(partenaireDTO.getId());
            }
            if (partenaireDTO.getNom() == null || partenaireDTO.getNom().isEmpty()) {
                throw new IllegalArgumentException("Partenaire Name cannot be empty");
            }
            if (partenaireDTO.getDescription() == null || partenaireDTO.getDescription().isEmpty()) {
                throw new IllegalArgumentException("Partenaire Description cannot be empty");
            }
            if (partenaireDTO.getContact() == null || partenaireDTO.getContact().isEmpty()) {
                throw new IllegalArgumentException("Partenaire Contact cannot be empty");
            }
            if (partenaireDTO.getAdresse() == null || partenaireDTO.getAdresse().isEmpty()) {
                throw new IllegalArgumentException("Partenaire Adresse cannot be empty");
            }
            if (partenaireDTO.getTypePartenaire() == null || partenaireDTO.getTypePartenaire().isEmpty()) {
                throw new IllegalArgumentException("TypePartenaire cannot be empty");
            }

            Partenaire partenaire = partenaireMapper.toEntity(partenaireDTO);
            partenaire = partenaireRepository.save(partenaire);
            return partenaireMapper.toDto(partenaire);

        }catch (Exception e) {
            log.error("Error saving  authentificationConfiguration: ", e);
            throw new RuntimeException("Unable to save authentificationConfiguration,Please fill in all fields", e);}

    }

    /**
     * Update a partenaire.
     *
     * @param partenaireDTO the entity to save.
     * @return the persisted entity.
     */
    public PartenaireDTO update(PartenaireDTO partenaireDTO) {
        log.debug("Request to update Partenaire : {}", partenaireDTO);
        Partenaire partenaire = partenaireMapper.toEntity(partenaireDTO);
        partenaire = partenaireRepository.save(partenaire);
        return partenaireMapper.toDto(partenaire);
    }

    /**
     * Partially update a partenaire.
     *
     * @param partenaireDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PartenaireDTO> partialUpdate(PartenaireDTO partenaireDTO) {
        log.debug("Request to partially update Partenaire : {}", partenaireDTO);

        return partenaireRepository
            .findById(partenaireDTO.getId())
            .map(existingPartenaire -> {
                partenaireMapper.partialUpdate(existingPartenaire, partenaireDTO);

                return existingPartenaire;
            })
            .map(partenaireRepository::save)
            .map(partenaireMapper::toDto);
    }

    /**
     * Get all the partenaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PartenaireDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Partenaires");
        return partenaireRepository.findAll(pageable).map(partenaireMapper::toDto);
    }

    /**
     * Get one partenaire by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PartenaireDTO> findOne(Long id) {
        log.debug("Request to get Partenaire : {}", id);
        return partenaireRepository.findById(id).map(partenaireMapper::toDto);
    }

    /**
     * Delete the partenaire by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Partenaire : {}", id);
        partenaireRepository.deleteById(id);
    }
}
