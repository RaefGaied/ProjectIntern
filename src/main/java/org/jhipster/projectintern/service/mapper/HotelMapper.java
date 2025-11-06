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

import java.time.ZonedDateTime;

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
    @Mapping(target = "colorSchema", source = "colorSchema")
    @Mapping(target = "logo", source = "logo")
    @Mapping(target = "banner", source = "banner")
    @Mapping(target = "dateCreation", source = "dateCreation")
    @Mapping(target = "dateModify", source = "dateModify")
    UIConfigurationDTO toDtoUIConfigurationId(UIConfiguration uIConfiguration);

    @Named("emailTemplateConfigurationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nomTemplate", source = "nomTemplate")
    @Mapping(target = "corps", source = "corps")
    @Mapping(target = "datedeCreation", source = "datedeCreation")
    @Mapping(target = "datedeModification", source = "datedeModification")
    @Mapping(target = "activeStatus", source = "activeStatus")
    EmailTemplateConfigurationDTO toDtoEmailTemplateConfigurationId(EmailTemplateConfiguration emailTemplateConfiguration);

    @Named("authentificationConfigurationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "twoFactorEnabled", source = "twoFactorEnabled")
    @Mapping(target = "loginPageCustomization", source = "loginPageCustomization")
    AuthentificationConfigurationDTO toDtoAuthentificationConfigurationId(AuthentificationConfiguration authentificationConfiguration);

    @Named("hotelAdministrateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "motDePasse", source = "motDePasse")
    HotelAdministrateurDTO toDtoHotelAdministrateurId(HotelAdministrateur hotelAdministrateur);

    @Named("hotelAdministrateurNom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "nom", source = "nom")
    HotelAdministrateur toDtoHotelAdministrateurNom(HotelAdministrateur hotelAdministrateur);
}
