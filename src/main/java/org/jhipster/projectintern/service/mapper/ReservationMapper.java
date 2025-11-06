package org.jhipster.projectintern.service.mapper;

import org.jhipster.projectintern.domain.Hotel;
import org.jhipster.projectintern.domain.Reservation;
import org.jhipster.projectintern.domain.User;
import org.jhipster.projectintern.service.dto.HotelDTO;
import org.jhipster.projectintern.service.dto.ReservationDTO;
import org.jhipster.projectintern.service.dto.UserDTO;
import org.mapstruct.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Mapper for the entity {@link Reservation} and its DTO {@link ReservationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReservationMapper extends EntityMapper<ReservationDTO, Reservation> {
    @Mapping(target = "hotel", source = "hotel", qualifiedByName = "hotelId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    ReservationDTO toDto(Reservation s);

    @Named("hotelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HotelDTO toDtoHotelId(Hotel hotel);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    UserDTO toDtoUserId(User user);


}
