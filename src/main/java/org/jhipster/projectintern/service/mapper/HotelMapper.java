package org.jhipster.projectintern.service.mapper;

import org.jhipster.projectintern.domain.AuthentificationConfiguration;
import org.jhipster.projectintern.domain.EmailTemplateConfiguration;
import org.jhipster.projectintern.domain.Hotel;
import org.jhipster.projectintern.domain.HotelAdministrateur;
import org.jhipster.projectintern.domain.UIConfiguration;
import org.jhipster.projectintern.service.dto.AuthentificationConfigurationDTO;
import org.jhipster.projectintern.service.dto.EmailTemplateConfigurationDTO;
import org.jhipster.projectintern.service.dto.HotelAdministrateurDTO;
import org.jhipster.projectintern.service.dto.HotelDTO;
import org.jhipster.projectintern.service.dto.UIConfigurationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Hotel} and its DTO {@link HotelDTO}.
 */
@Mapper(componentModel = "spring")
public interface HotelMapper extends EntityMapper<HotelDTO, Hotel> {
    @Mapping(target = "uiConfigurations", source = "uiConfigurations", qualifiedByName = "uIConfigurationId")
    @Mapping(target = "emailConfig", source = "emailConfig", qualifiedByName = "emailTemplateConfigurationId")
    @Mapping(target = "authConfig", source = "authConfig", qualifiedByName = "authentificationConfigurationId")
    @Mapping(target = "hotelAdministrateur", source = "hotelAdministrateur", qualifiedByName = "hotelAdministrateurId")
    @Mapping(source = "hotelAdministrateur.nom", target = "hotelAdministrateurNom")
    HotelDTO toDto(Hotel s);

    @Named("uIConfigurationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UIConfigurationDTO toDtoUIConfigurationId(UIConfiguration uIConfiguration);

    @Named("emailTemplateConfigurationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmailTemplateConfigurationDTO toDtoEmailTemplateConfigurationId(EmailTemplateConfiguration emailTemplateConfiguration);

    @Named("authentificationConfigurationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AuthentificationConfigurationDTO toDtoAuthentificationConfigurationId(AuthentificationConfiguration authentificationConfiguration);

    @Named("hotelAdministrateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")

    HotelAdministrateurDTO toDtoHotelAdministrateurId(HotelAdministrateur hotelAdministrateur);

    @Named("hotelAdministrateurNom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "nom", source = "nom")
    HotelAdministrateur toDtoHotelAdministrateurNom(HotelAdministrateur hotelAdministrateur);
}
