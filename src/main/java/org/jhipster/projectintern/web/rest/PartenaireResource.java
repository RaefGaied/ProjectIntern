package org.jhipster.projectintern.web.rest;

import org.jhipster.projectintern.repository.PartenaireRepository;
import org.jhipster.projectintern.service.PartenaireService;
import org.jhipster.projectintern.service.dto.PartenaireDTO;
import org.jhipster.projectintern.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link org.jhipster.projectintern.domain.Partenaire}.
 */
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_ADMIN')")
@RequestMapping("/api/partenaires")
public class PartenaireResource {

    private static final Logger log = LoggerFactory.getLogger(PartenaireResource.class);

    private static final String ENTITY_NAME = "partenaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartenaireService partenaireService;

    private final PartenaireRepository partenaireRepository;

    public PartenaireResource(PartenaireService partenaireService, PartenaireRepository partenaireRepository) {
        this.partenaireService = partenaireService;
        this.partenaireRepository = partenaireRepository;
    }

    /**
     * {@code POST  /partenaires} : Create a new partenaire.
     *
     * @param partenaireDTO the partenaireDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partenaireDTO, or with status {@code 400 (Bad Request)} if the partenaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<?> createPartenaire(@RequestBody PartenaireDTO partenaireDTO) throws URISyntaxException {
        log.debug("REST request to save Partenaire : {}", partenaireDTO);

        try {
            if (partenaireDTO.getId() != null) {
                throw new BadRequestAlertException("A new partenaire cannot already have an ID", ENTITY_NAME, "idexists");
            }

            PartenaireDTO result = partenaireService.save(partenaireDTO);

            return ResponseEntity.created(new URI("/api/partenaires/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);

        } catch (IllegalArgumentException e) {
            // Return 400 Bad Request with the validation message
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Return 500 Internal Server Error with a custom message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unable to save Partenaire, please fill in all fields.");
        }
    }


    /**
     * {@code PUT  /partenaires/:id} : Updates an existing partenaire.
     *
     * @param id the id of the partenaireDTO to save.
     * @param partenaireDTO the partenaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partenaireDTO,
     * or with status {@code 400 (Bad Request)} if the partenaireDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partenaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PartenaireDTO> updatePartenaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartenaireDTO partenaireDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Partenaire : {}, {}", id, partenaireDTO);
        if (partenaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partenaireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partenaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        partenaireDTO = partenaireService.update(partenaireDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partenaireDTO.getId().toString()))
            .body(partenaireDTO);
    }

    /**
     * {@code PATCH  /partenaires/:id} : Partial updates given fields of an existing partenaire, field will ignore if it is null
     *
     * @param id the id of the partenaireDTO to save.
     * @param partenaireDTO the partenaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partenaireDTO,
     * or with status {@code 400 (Bad Request)} if the partenaireDTO is not valid,
     * or with status {@code 404 (Not Found)} if the partenaireDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the partenaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartenaireDTO> partialUpdatePartenaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartenaireDTO partenaireDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Partenaire partially : {}, {}", id, partenaireDTO);
        if (partenaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partenaireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partenaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartenaireDTO> result = partenaireService.partialUpdate(partenaireDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partenaireDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /partenaires} : get all the partenaires.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partenaires in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PartenaireDTO>> getAllPartenaires(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Partenaires");
        Page<PartenaireDTO> page = partenaireService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /partenaires/:id} : get the "id" partenaire.
     *
     * @param id the id of the partenaireDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partenaireDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PartenaireDTO> getPartenaire(@PathVariable("id") Long id) {
        log.debug("REST request to get Partenaire : {}", id);
        Optional<PartenaireDTO> partenaireDTO = partenaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partenaireDTO);
    }

    /**
     * {@code DELETE  /partenaires/:id} : delete the "id" partenaire.
     *
     * @param id the id of the partenaireDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartenaire(@PathVariable("id") Long id) {
        log.debug("REST request to delete Partenaire : {}", id);
        partenaireService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
