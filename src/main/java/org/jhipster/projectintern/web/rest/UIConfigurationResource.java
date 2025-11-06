package org.jhipster.projectintern.web.rest;

import org.jhipster.projectintern.repository.UIConfigurationRepository;
import org.jhipster.projectintern.service.HotelService;
import org.jhipster.projectintern.service.UIConfigurationService;
import org.jhipster.projectintern.service.dto.UIConfigurationDTO;
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
 * REST controller for managing {@link org.jhipster.projectintern.domain.UIConfiguration}.
 */
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_ADMIN')")
@RequestMapping("/api/ui-configurations")
public class UIConfigurationResource {

    private static final Logger log = LoggerFactory.getLogger(UIConfigurationResource.class);

    private static final String ENTITY_NAME = "uIConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UIConfigurationService uIConfigurationService;

    private final UIConfigurationRepository uIConfigurationRepository;
    private final HotelService hotelService;

    public UIConfigurationResource(UIConfigurationService uIConfigurationService, UIConfigurationRepository uIConfigurationRepository, HotelService hotelService) {
        this.uIConfigurationService = uIConfigurationService;
        this.uIConfigurationRepository = uIConfigurationRepository;
        this.hotelService = hotelService;
    }

    /**
     * {@code POST  /ui-configurations} : Create a new uIConfiguration.
     *
     * @param uIConfigurationDTO the uIConfigurationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uIConfigurationDTO, or with status {@code 400 (Bad Request)} if the uIConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<?> createUIConfiguration(@RequestBody UIConfigurationDTO uIConfigurationDTO)
        throws URISyntaxException {
        log.debug("REST request to save UIConfiguration : {}", uIConfigurationDTO);

        try {
            if (uIConfigurationDTO.getId() != null) {
                throw new BadRequestAlertException("A new UIConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
            }
            // Save the UIConfiguration, which will now be associated with the specified Hotel
            UIConfigurationDTO result = uIConfigurationService.save(uIConfigurationDTO);
            // Return the created response with a link to the newly created UIConfiguration
            return ResponseEntity.created(new URI("/api/ui-configurations/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (IllegalArgumentException e) {
            // Handle validation errors and return a bad request response with the error message
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Handle any other exceptions and return an internal server error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unable to save UIConfiguration, please fill in all fields.");
        }
    }


    /**
     * {@code PUT  /ui-configurations/:id} : Updates an existing uIConfiguration.
     *
     * @param id the id of the uIConfigurationDTO to save.
     * @param uIConfigurationDTO the uIConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uIConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the uIConfigurationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uIConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UIConfigurationDTO> updateUIConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UIConfigurationDTO uIConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UIConfiguration : {}, {}", id, uIConfigurationDTO);
        if (uIConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uIConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uIConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        uIConfigurationDTO = uIConfigurationService.update(uIConfigurationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uIConfigurationDTO.getId().toString()))
            .body(uIConfigurationDTO);
    }

    /**
     * {@code PATCH  /ui-configurations/:id} : Partial updates given fields of an existing uIConfiguration, field will ignore if it is null
     *
     * @param id the id of the uIConfigurationDTO to save.
     * @param uIConfigurationDTO the uIConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uIConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the uIConfigurationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the uIConfigurationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the uIConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UIConfigurationDTO> partialUpdateUIConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UIConfigurationDTO uIConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UIConfiguration partially : {}, {}", id, uIConfigurationDTO);
        if (uIConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!Objects.equals(id, uIConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uIConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UIConfigurationDTO> result = uIConfigurationService.partialUpdate(uIConfigurationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uIConfigurationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ui-configurations} : get all the uIConfigurations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uIConfigurations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<UIConfigurationDTO>> getAllUIConfigurations(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of UIConfigurations");
        Page<UIConfigurationDTO> page = uIConfigurationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ui-configurations/:id} : get the "id" uIConfiguration.
     *
     * @param id the id of the uIConfigurationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uIConfigurationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UIConfigurationDTO> getUIConfiguration(@PathVariable("id") Long id) {
        log.debug("REST request to get UIConfiguration : {}", id);
        Optional<UIConfigurationDTO> uIConfigurationDTO = uIConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uIConfigurationDTO);
    }

    /**
     * {@code DELETE  /ui-configurations/:id} : delete the "id" uIConfiguration.
     *
     * @param id the id of the uIConfigurationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUIConfiguration(@PathVariable("id") Long id) {
        log.debug("REST request to delete UIConfiguration : {}", id);
        uIConfigurationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }


   /* @GetMapping("/images/{id}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable Long id) {
        byte[] imageData = hotelService.findImageById(id);

        if (imageData == null) {
            return ResponseEntity.notFound().build();
        }

        // Determine the image type based on the byte array or file extension stored in your entity.
        String imageType = determineImageType(imageData); // Implement this method to detect the image type

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(imageType))
            .body(new ByteArrayResource(imageData));
    }*/


}
