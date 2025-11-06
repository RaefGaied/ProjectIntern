package org.jhipster.projectintern.service.dto;

import org.jhipster.projectintern.domain.enumeration.Act;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link org.jhipster.projectintern.domain.EmailTemplateConfiguration} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmailTemplateConfigurationDTO implements Serializable {

    private Long id;

    private String nomTemplate;

    private String corps;

    private ZonedDateTime datedeCreation;

    private ZonedDateTime datedeModification;

    private Act activeStatus;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomTemplate() {
        return nomTemplate;
    }

    public void setNomTemplate(String nomTemplate) {
        this.nomTemplate = nomTemplate;
    }

    public String getCorps() {
        return corps;
    }

    public void setCorps(String corps) {
        this.corps = corps;
    }

    public ZonedDateTime getDatedeCreation() {
        return datedeCreation;
    }

    public void setDatedeCreation(ZonedDateTime datedeCreation) {
        this.datedeCreation = datedeCreation;
    }

    public ZonedDateTime getDatedeModification() {
        return datedeModification;
    }

    public void setDatedeModification(ZonedDateTime datedeModification) {
        this.datedeModification = datedeModification;
    }

    public Act getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Act activeStatus) {
        this.activeStatus = activeStatus;
    }

    /*public HotelDTO getHotel() {
        return hotel;
    }

    public void setHotel(HotelDTO hotel) {
        this.hotel = hotel;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailTemplateConfigurationDTO)) {
            return false;
        }

        EmailTemplateConfigurationDTO emailTemplateConfigurationDTO = (EmailTemplateConfigurationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, emailTemplateConfigurationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailTemplateConfigurationDTO{" +
            "id=" + getId() +
            ", nomTemplate='" + getNomTemplate() + "'" +
            ", corps='" + getCorps() + "'" +
            ", datedeCreation='" + getDatedeCreation() + "'" +
            ", datedeModification='" + getDatedeModification() + "'" +
            ", activeStatus='" + getActiveStatus() + "'" +
            "}";
    }






}
