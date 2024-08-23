package org.jhipster.projectintern.web.rest;

import org.jhipster.projectintern.repository.HotelRepository;
import org.jhipster.projectintern.service.HotelService;
import org.jhipster.projectintern.service.dto.HotelDTO;
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
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link org.jhipster.projectintern.domain.Hotel}.
 */
@RestController
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/api/hotels")
public class HotelResource {

    private static final Logger log = LoggerFactory.getLogger(HotelResource.class);

    private static final String ENTITY_NAME = "hotel";

    private String uniqueLink;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HotelService hotelService;

    private final HotelRepository hotelRepository;

    public HotelResource(HotelService hotelService, HotelRepository hotelRepository) {
        this.hotelService = hotelService;
        this.hotelRepository = hotelRepository;
    }

    /**
     * {@code POST  /hotels} : Create a new hotel.
     *
     * @param hotelDTO the hotelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hotelDTO, or with status {@code 400 (Bad Request)} if the hotel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */


    @PostMapping("")
    public ResponseEntity<?> createHotel(@RequestBody HotelDTO hotelDTO) throws URISyntaxException {
        log.debug("REST request to save Hotel : {}", hotelDTO);
        try {
            if (hotelDTO.getId() != null) {
                throw new BadRequestAlertException("A new hotel cannot already have an ID", ENTITY_NAME, "idexists");
            }
            HotelDTO result = hotelService.save(hotelDTO);
            return ResponseEntity.created(new URI("/api/hotels/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unable to save Hotel, please fill in all fields.");
        }
    }


    /**
     * {@code PUT  /hotels/:id} : Updates an existing hotel.
     *
     * @param id the id of the hotelDTO to save.
     * @param hotelDTO the hotelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotelDTO,
     * or with status {@code 400 (Bad Request)} if the hotelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hotelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HotelDTO> updateHotel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HotelDTO hotelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Hotel : {}, {}", id, hotelDTO);
        if (hotelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        hotelDTO = hotelService.update(hotelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hotelDTO.getId().toString()))
            .body(hotelDTO);
    }

    /**
     * {@code PATCH  /hotels/:id} : Partial updates given fields of an existing hotel, field will ignore if it is null
     *
     * @param id the id of the hotelDTO to save.
     * @param hotelDTO the hotelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotelDTO,
     * or with status {@code 400 (Bad Request)} if the hotelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hotelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hotelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HotelDTO> partialUpdateHotel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HotelDTO hotelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Hotel partially : {}, {}", id, hotelDTO);
        if (hotelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HotelDTO> result = hotelService.partialUpdate(hotelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hotelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hotels} : get all the hotels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hotels in body.
     */
    @GetMapping("")
    public ResponseEntity<List<HotelDTO>> getAllHotels(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Hotels");
        Page<HotelDTO> page = hotelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hotels/:id} : get the "id" hotel.
     *
     * @param id the id of the hotelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hotelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> getHotel(@PathVariable("id") Long id) {
        log.debug("REST request to get Hotel : {}", id);
        Optional<HotelDTO> hotelDTO = hotelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hotelDTO);
    }

    /**
     * {@code DELETE  /hotels/:id} : delete the "id" hotel.
     *
     * @param id the id of the hotelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Hotel : {}", id);
        hotelService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/hotel/{id}")
    public ResponseEntity<String> getHotelByLink(
        @PathVariable("id") Long id) {

        // Here you can implement logic to verify the UUID and return the hotel details or link
        // For now, let's assume you're simply returning the full link with the UUID
        String link = "http://localhost:8080/api/hotels/" + id;

        return ResponseEntity.ok(link);
    }


}
