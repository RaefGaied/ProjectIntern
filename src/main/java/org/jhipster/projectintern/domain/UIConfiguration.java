package org.jhipster.projectintern.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UIConfiguration.
 */
@Entity
@Table(name = "ui_configuration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UIConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Lob
    private byte[] imageData;  // Field to store the image data

    private String imageType;  // Field to store the image type (e.g., "image/png", "image/jpeg")

    // Getters and setters
    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "color_schema")
    private String colorSchema;

    @Column(name = "logo")
    private String logo;

    @Column(name = "banner")
    private String banner;

    @Column(name = "date_creation")
    private ZonedDateTime dateCreation;

    @Column(name = "date_modify")
    private ZonedDateTime dateModify;

    @JsonIgnoreProperties(
        value = { "uiConfigurations", "emailConfig", "authConfig", "services", "hotelAdministrateur" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "uiConfigurations")
    private Hotel hotel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UIConfiguration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColorSchema() {
        return this.colorSchema;
    }

    public UIConfiguration colorSchema(String colorSchema) {
        this.setColorSchema(colorSchema);
        return this;
    }

    public void setColorSchema(String colorSchema) {
        this.colorSchema = colorSchema;
    }

    public String getLogo() {
        return this.logo;
    }

    public UIConfiguration logo(String logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBanner() {
        return this.banner;
    }

    public UIConfiguration banner(String banner) {
        this.setBanner(banner);
        return this;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public ZonedDateTime getDateCreation() {
        return this.dateCreation;
    }

    public UIConfiguration dateCreation(ZonedDateTime dateCreation) {
        this.setDateCreation(dateCreation);
        return this;
    }

    public void setDateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public ZonedDateTime getDateModify() {
        return this.dateModify;
    }

    public UIConfiguration dateModify(ZonedDateTime dateModify) {
        this.setDateModify(dateModify);
        return this;
    }

    public void setDateModify(ZonedDateTime dateModify) {
        this.dateModify = dateModify;
    }

    public Hotel getHotel() {
        return this.hotel;
    }

    public void setHotel(Hotel hotel) {
        if (this.hotel != null) {
            this.hotel.setUiConfigurations(null);
        }
        if (hotel != null) {
            hotel.setUiConfigurations(this);
        }
        this.hotel = hotel;
    }

    public UIConfiguration hotel(Hotel hotel) {
        this.setHotel(hotel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UIConfiguration)) {
            return false;
        }
        return getId() != null && getId().equals(((UIConfiguration) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UIConfiguration{" +
            "id=" + getId() +
            ", colorSchema='" + getColorSchema() + "'" +
            ", logo='" + getLogo() + "'" +
            ", banner='" + getBanner() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateModify='" + getDateModify() + "'" +
            "}";
    }
}
