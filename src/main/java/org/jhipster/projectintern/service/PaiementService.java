package org.jhipster.projectintern.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.jhipster.projectintern.domain.Paiement;
import org.jhipster.projectintern.domain.Reservation;
import org.jhipster.projectintern.domain.enumeration.PAIEMENT;
import org.jhipster.projectintern.repository.PaiementRepository;
import org.jhipster.projectintern.repository.ReservationRepository;
import org.jhipster.projectintern.service.dto.PaiementDTO;
import org.jhipster.projectintern.service.mapper.PaiementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service Implementation for managing {@link org.jhipster.projectintern.domain.Paiement}.
 */
@Service
@Transactional
public class PaiementService {

    private static final Logger log = LoggerFactory.getLogger(PaiementService.class);

    private final PaiementRepository paiementRepository;

    private final PaiementMapper paiementMapper;
    private final ReservationRepository resationRepository;

    public PaiementService(PaiementRepository paiementRepository, PaiementMapper paiementMapper, ReservationRepository resationRepository) {
        this.paiementRepository = paiementRepository;
        this.paiementMapper = paiementMapper;

        this.resationRepository = resationRepository;
    }
    public PaymentIntent createPaymentIntent(PaiementDTO paiementDTO) throws StripeException {
        List<String> paymentMethodTypes = Collections.singletonList("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", (long) (paiementDTO.getMontant() * 100)); // Convert amount to cents
        params.put("currency", "usd"); // Use the currency you want
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", paiementDTO.getDescription()); // Optional: description for the payment

        return PaymentIntent.create(params);
    }
    /*public ResponseEntity<String> stripePayment(Long paiementId) throws Exception {
        Optional<Paiement> paymentOpt = paiementRepository.findById(paiementId);

        if (!paymentOpt.isPresent()) {
            throw new Exception("Payment information is missing");
        }

        Paiement payment = paymentOpt.get();

        // Convert the amount to cents as Stripe requires it to be in the smallest currency unit
        long amountInCents = (long) (payment.getMontant() * 100);

        // Create a PaymentIntent for Stripe
        PaymentIntent paymentIntent;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("amount", amountInCents);
            params.put("currency", "usd"); // Ensure the currency is set correctly
            params.put("payment_method_types", Collections.singletonList("card"));
            paymentIntent = PaymentIntent.create(params);

            // Store Stripe's paymentIntent ID in the transactionId field
            payment.setTransactionId(paymentIntent.getId());
            payment.setStatutPaiement(PAIEMENT.EN_COURS); // Update the payment status to pending

        } catch (StripeException e) {
            throw new Exception("Stripe payment processing failed: " + e.getMessage(), e);
        }

        // Save the updated payment information
        paiementRepository.save(payment);

        // Return a successful response
        return ResponseEntity.ok("PaymentIntent created successfully with ID: " + paymentIntent.getId());
    }*/
    /**
     * Save a paiement.
     *
     * @param paiementDTO the entity to save.
     * @return the persisted entity.
     */
    public PaiementDTO save(PaiementDTO paiementDTO) {
        log.debug("Request to save Paiement : {}", paiementDTO);
        try {
            // Validate the necessary fields
            if (paiementDTO.getMontant() == null || paiementDTO.getMontant() == 0) {
                throw new IllegalArgumentException("Paiement Amount cannot be empty");
            }
            if (paiementDTO.getDescription() == null || paiementDTO.getDescription().isEmpty()) {
                throw new IllegalArgumentException("Paiement Description cannot be empty");
            }
            if (paiementDTO.getMethodePaiement() == null || paiementDTO.getMethodePaiement().isEmpty()) {
                throw new IllegalArgumentException("Paiement Methode cannot be empty");
            }
            if (paiementDTO.getToken() == null || paiementDTO.getToken().isEmpty()) {
                throw new IllegalArgumentException("Paiement Token cannot be empty");
            }
            if (paiementDTO.getTransactionId() == null || paiementDTO.getTransactionId().isEmpty()) {
                throw new IllegalArgumentException("Paiement TransactionId cannot be empty");
            }
            if (paiementDTO.getStatutPaiement() == null) {
                throw new IllegalArgumentException("Paiement Statut cannot be empty");
            }

            Paiement paiement = paiementMapper.toEntity(paiementDTO);

            // Associate the reservation with the payment
            if (paiementDTO.getReservation().getId() != null) {
                Reservation reservation = resationRepository.findById(paiementDTO.getReservation().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Reservation ID"));
                paiement.setReservation(reservation);
            }

            paiement = paiementRepository.save(paiement);
            return paiementMapper.toDto(paiement);

        } catch (Exception e) {
            log.error("Error saving Paiement: ", e);
            throw new RuntimeException("Unable to save Payment, please fill in all fields", e);
        }
    }

    /**
     * Update a paiement.
     *
     * @param paiementDTO the entity to save.
     * @return the persisted entity.
     */
    public PaiementDTO update(PaiementDTO paiementDTO) {
        log.debug("Request to update Paiement : {}", paiementDTO);
        Paiement paiement = paiementMapper.toEntity(paiementDTO);
        paiement = paiementRepository.save(paiement);
        return paiementMapper.toDto(paiement);
    }

    /**
     * Partially update a paiement.
     *
     * @param paiementDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PaiementDTO> partialUpdate(PaiementDTO paiementDTO) {
        log.debug("Request to partially update Paiement : {}", paiementDTO);

        return paiementRepository
            .findById(paiementDTO.getId())
            .map(existingPaiement -> {
                paiementMapper.partialUpdate(existingPaiement, paiementDTO);

                return existingPaiement;
            })
            .map(paiementRepository::save)
            .map(paiementMapper::toDto);
    }

    /**
     * Get all the paiements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaiementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Paiements");
        return paiementRepository.findAll(pageable).map(paiementMapper::toDto);
    }

    /**
     * Get one paiement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaiementDTO> findOne(Long id) {
        log.debug("Request to get Paiement : {}", id);
        return paiementRepository.findById(id).map(paiementMapper::toDto);
    }

    /**
     * Delete the paiement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Paiement : {}", id);
        paiementRepository.deleteById(id);
    }
}
