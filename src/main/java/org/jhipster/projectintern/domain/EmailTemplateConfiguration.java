package org.jhipster.projectintern.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jhipster.projectintern.domain.enumeration.Act;

/**
 * A EmailTemplateConfiguration.
 */
@Entity
@Table(name = "email_template_configuration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmailTemplateConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_template")
    private String nomTemplate;

    @Column(name = "corps")
    private String corps;

    @Column(name = "datede_creation")
    private ZonedDateTime datedeCreation;

    @Column(name = "datede_modification")
    private ZonedDateTime datedeModification;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_status")
    private Act activeStatus;

    @JsonIgnoreProperties(
        value = { "uiConfigurations", "emailConfig", "authConfig", "services", "hotelAdministrateur" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "emailConfig")
    private Hotel hotel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmailTemplateConfiguration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomTemplate() {
        return this.nomTemplate;
    }

    public EmailTemplateConfiguration nomTemplate(String nomTemplate) {
        this.setNomTemplate(nomTemplate);
        return this;
    }

    public void setNomTemplate(String nomTemplate) {
        this.nomTemplate = nomTemplate;
    }

    public String getCorps() {
        return this.corps;
    }

    public EmailTemplateConfiguration corps(String corps) {
        this.setCorps(corps);
        return this;
    }

    public void setCorps(String corps) {
        this.corps = corps;
    }

    public ZonedDateTime getDatedeCreation() {
        return this.datedeCreation;
    }

    public EmailTemplateConfiguration datedeCreation(ZonedDateTime datedeCreation) {
        this.setDatedeCreation(datedeCreation);
        return this;
    }

    public void setDatedeCreation(ZonedDateTime datedeCreation) {
        this.datedeCreation = datedeCreation;
    }

    public ZonedDateTime getDatedeModification() {
        return this.datedeModification;
    }

    public EmailTemplateConfiguration datedeModification(ZonedDateTime datedeModification) {
        this.setDatedeModification(datedeModification);
        return this;
    }

    public void setDatedeModification(ZonedDateTime datedeModification) {
        this.datedeModification = datedeModification;
    }

    public Act getActiveStatus() {
        return this.activeStatus;
    }

    public EmailTemplateConfiguration activeStatus(Act activeStatus) {
        this.setActiveStatus(activeStatus);
        return this;
    }

    public void setActiveStatus(Act activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Hotel getHotel() {
        return this.hotel;
    }

    public void setHotel(Hotel hotel) {
        if (this.hotel != null) {
            this.hotel.setEmailConfig(null);
        }
        if (hotel != null) {
            hotel.setEmailConfig(this);
        }
        this.hotel = hotel;
    }

    public EmailTemplateConfiguration hotel(Hotel hotel) {
        this.setHotel(hotel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailTemplateConfiguration)) {
            return false;
        }
        return getId() != null && getId().equals(((EmailTemplateConfiguration) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailTemplateConfiguration{" +
            "id=" + getId() +
            ", nomTemplate='" + getNomTemplate() + "'" +
            ", corps='" + getCorps() + "'" +
            ", datedeCreation='" + getDatedeCreation() + "'" +
            ", datedeModification='" + getDatedeModification() + "'" +
            ", activeStatus='" + getActiveStatus() + "'" +
            "}";
    }
}
