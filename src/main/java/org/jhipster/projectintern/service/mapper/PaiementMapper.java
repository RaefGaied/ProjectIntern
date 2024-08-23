package org.jhipster.projectintern.service.mapper;

import org.jhipster.projectintern.domain.Paiement;
import org.jhipster.projectintern.domain.Reservation;
import org.jhipster.projectintern.service.dto.PaiementDTO;
import org.jhipster.projectintern.service.dto.ReservationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Paiement} and its DTO {@link PaiementDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaiementMapper extends EntityMapper<PaiementDTO, Paiement> {
    @Mapping(target = "reservation", source = "reservation", qualifiedByName = "reservationId")
    PaiementDTO toDto(Paiement s);

    @Named("reservationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReservationDTO toDtoReservationId(Reservation reservation);
}
