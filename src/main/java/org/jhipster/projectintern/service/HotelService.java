package org.jhipster.projectintern.service;

import org.jhipster.projectintern.domain.*;
import org.jhipster.projectintern.repository.*;
import org.jhipster.projectintern.service.dto.HotelDTO;
import org.jhipster.projectintern.service.mapper.HotelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link org.jhipster.projectintern.domain.Hotel}.
 */
@Service
@Transactional
public class HotelService {

    private static final Logger log = LoggerFactory.getLogger(HotelService.class);

    private final HotelRepository hotelRepository;

    private final HotelMapper hotelMapper;
    private final HotelAdministrateurRepository hotelAdministrateurRepository;
    private final UIConfigurationRepository uiConfigurationRepository;
    private final EmailTemplateConfigurationRepository emailTemplateConfigurationRepository;
    private final AuthentificationConfigurationRepository authentificationConfigurationRepository;

    public HotelService(HotelRepository hotelRepository, HotelMapper hotelMapper, HotelAdministrateurRepository hotelAdministrateurRepository, UIConfigurationRepository uiConfigurationRepository, EmailTemplateConfigurationRepository emailTemplateConfigurationRepository, AuthentificationConfigurationRepository authentificationConfigurationRepository) {
        this.hotelRepository = hotelRepository;
        this.hotelMapper = hotelMapper;
        this.hotelAdministrateurRepository = hotelAdministrateurRepository;
        this.uiConfigurationRepository = uiConfigurationRepository;
        this.emailTemplateConfigurationRepository = emailTemplateConfigurationRepository;
        this.authentificationConfigurationRepository = authentificationConfigurationRepository;
    }

    /**
     * Save a hotel.
     *
     * @param hotelDTO the entity to save.
     * @return the persisted entity.
     */
   /* public HotelDTO save(HotelDTO hotelDTO) {
        log.debug("Request to save Hotel : {}", hotelDTO);
        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        hotel = hotelRepository.save(hotel);
        return hotelMapper.toDto(hotel);
    }*/
    public HotelDTO save(HotelDTO hotelDTO) {
        log.debug("Request to save Hotel : {}", hotelDTO);

        try {
            validateHotelDetails(hotelDTO);
            HotelAdministrateur admin = hotelAdministrateurRepository.findById(hotelDTO.getHotelAdministrateur().getId())
                .orElseThrow(() -> new IllegalArgumentException("Hotel administrator does not exist"));
            UIConfiguration ui = uiConfigurationRepository.findById(hotelDTO.getUiConfigurations().getId())
                .orElseThrow(() -> new IllegalArgumentException("UI configuration does not exist"));
            EmailTemplateConfiguration emailcon = emailTemplateConfigurationRepository.findById(hotelDTO.getEmailConfig().getId())
                .orElseThrow(() -> new IllegalArgumentException("Email template configuration does not exist"));
            AuthentificationConfiguration auth = authentificationConfigurationRepository.findById(hotelDTO.getAuthConfig().getId())
                .orElseThrow(() -> new IllegalArgumentException("Auth configuration does not exist"));
            Hotel hotel = hotelMapper.toEntity(hotelDTO);
            hotel.setHotelAdministrateur(admin); // Associate the admin
            hotel.setUiConfigurations(ui);
            hotel.setEmailConfig(emailcon);
            hotel.setAuthConfig(auth);

            hotel = hotelRepository.save(hotel);

            if (hotel.getLienUnique() == null || hotel.getLienUnique().isEmpty()) {
                String generatedLink = generateLinkForHotel(hotel.getId());
                hotel.setLienUnique(generatedLink);
                hotelRepository.save(hotel);
            }

            HotelDTO result = hotelMapper.toDto(hotel);
            result.setHotelAdministrateurNom(admin.getNom()); // Set the admin name in the DTO

            return result;
        } catch (Exception e) {
            log.error("Error saving Hotel: ", e);
            throw new RuntimeException("Internal Server Error: Unable to save hotel.", e);
        }
    }

    private void validateHotelDetails(HotelDTO hotelDTO) {
        if (hotelDTO.getNom() == null || hotelDTO.getNom().isEmpty()) {
            throw new IllegalArgumentException("Hotel name cannot be empty");
        }
        if (hotelDTO.getAdresse() == null || hotelDTO.getAdresse().isEmpty()) {
            throw new IllegalArgumentException("Hotel address cannot be empty");
        }
        if (hotelDTO.getVille() == null || hotelDTO.getVille().isEmpty()) {
            throw new IllegalArgumentException("Hotel city cannot be empty");
        }
        if (hotelDTO.getCapacite() == null || hotelDTO.getCapacite() == 0) {
            throw new IllegalArgumentException("Hotel capacity cannot be empty");
        }
        if (hotelDTO.getNumeroTelephone() == null || hotelDTO.getNumeroTelephone() == 0) {
            throw new IllegalArgumentException("Hotel phone number cannot be empty");
        }
        if (hotelDTO.getVueS() == null || hotelDTO.getVueS().isEmpty()) {
            throw new IllegalArgumentException("Hotel view cannot be empty");
        }
        if (hotelDTO.getNotation() == null || hotelDTO.getNotation().isEmpty()) {
            throw new IllegalArgumentException("Hotel rating cannot be empty");
        }
        if (hotelDTO.getLienUnique() == null || hotelDTO.getLienUnique().isEmpty()) {
            throw new IllegalArgumentException("Hotel unique link cannot be empty");
        }
    }

    /**
     * Update a hotel.
     *
     * @param hotelDTO the entity to save.
     * @return the persisted entity.
     */

    public HotelDTO update(HotelDTO hotelDTO) {
        log.debug("Request to update Hotel : {}", hotelDTO);
        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        hotel = hotelRepository.save(hotel);
        return hotelMapper.toDto(hotel);
    }


    /**
     * Partially update a hotel.
     *
     * @param hotelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HotelDTO> partialUpdate(HotelDTO hotelDTO) {
        log.debug("Request to partially update Hotel : {}", hotelDTO);

        return hotelRepository
            .findById(hotelDTO.getId())
            .map(existingHotel -> {
                hotelMapper.partialUpdate(existingHotel, hotelDTO);

                return existingHotel;
            })
            .map(hotelRepository::save)
            .map(hotelMapper::toDto);
    }

    /**
     * Get all the hotels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HotelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Hotels");
        return hotelRepository.findAll(pageable).map(hotelMapper::toDto);
    }

    /**
     * Get one hotel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HotelDTO> findOne(Long id) {
        log.debug("Request to get Hotel : {}", id);
        return hotelRepository.findById(id).map(hotelMapper::toDto);
    }

    /**
     * Delete the hotel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Hotel : {}", id);
        hotelRepository.deleteById(id);
    }


    public String generateLinkForHotel(long hotelId) {


        // Create the unique link using the hotelId
        String uniqueLink = "http://localhost:8080/api/hotels/" + hotelId;

        return uniqueLink;
    }

    public Optional<Hotel> findHotelByName(String name) {
        // Implementation of finding a hotel by name
        return hotelRepository.findByNom(name);
    }

    /*public ResponseEntity<?> associateHotelToAdmin(Long hotelId, Long adminId) {
        try {
            // Fetch the hotel and admin from the repositories
            Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new OurException("Hotel Not Found"));
            HotelAdministrateur admin = hotelAdministrateurRepository.findById(adminId)
                .orElseThrow(() -> new OurException("Admin Not Found"));

            // Check if the admin is already associated with another hotel
            if (admin.getHotels() != null) {
                throw new OurException("Admin is already associated with another hotel");
            }

            // Associate the hotel with the admin
            admin.setHotels(Collections.singleton(hotel));
            hotelAdministrateurRepository.save(admin);

            // Return a successful response
            return new ResponseEntity<>("Hotel successfully associated with the Admin.", HttpStatus.OK);

        } catch (OurException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error associating hotel with admin: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
    public List<HotelDTO> searchHotels(String location, String checkInDate, String checkOutDate, int adults, int children, int rooms) {
        int totalGuests = adults + children; // Total guests calculation

        // Call repository method to search for hotels
        return hotelRepository.searchHotels(location, totalGuests)
            .stream()
            .map(hotelMapper::toDto) // Use mapper for conversion
            .collect(Collectors.toList());

   /* public byte[] findImageById(Long id) {
        // Fetch the image bytes from the repository, you need to replace this with actual repository code
        Optional<UIConfiguration> uiConfigOpt = uiConfigurationRepository.findById(id);
        return uiConfigOpt.map(UIConfiguration::getImageData).orElse(null);  // Assuming getImageData() returns byte[]
    }*/
  /* public byte[] findImageById(Long id) {
       // Fetch the image bytes from the repository, assuming the image is stored in UIConfiguration or Hotel entity
       Optional<UIConfiguration> uiConfigOpt = uiConfigurationRepository.findById(id);

       if (uiConfigOpt.isPresent() && uiConfigOpt.get().getImageData() != null) {
           return uiConfigOpt.get().getImageData();  // Assuming `getImageData()` returns byte[]
       }

       // If no image found, return null
       return null;*/


    /*private String determineImageType(byte[] imageData) {
        if (imageData == null || imageData.length < 4) {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE; // Default to binary if the type is unknown
        }

        // Simple signature checks for common image types
        if (imageData[0] == (byte) 0xFF && imageData[1] == (byte) 0xD8) {
            return MediaType.IMAGE_JPEG_VALUE;
        } else if (imageData[0] == (byte) 0x89 && imageData[1] == (byte) 0x50) {
            return MediaType.IMAGE_PNG_VALUE;
        } else if (imageData[0] == (byte) 0x47 && imageData[1] == (byte) 0x49) {
            return MediaType.IMAGE_GIF_VALUE;
        }
        // Add more types if needed

        return MediaType.APPLICATION_OCTET_STREAM_VALUE; // Default if type is unknown
    }*/


    }
}
