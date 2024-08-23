package org.jhipster.projectintern.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Hotel.
 */
@Entity
@Table(name = "hotel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "numero_telephone")
    private Integer numeroTelephone;

    @Column(name = "pays")
    private String pays;

    @Column(name = "ville")
    private String ville;

    @Column(name = "vue_s")
    private String vueS;

    @Column(name = "capacite")
    private Integer capacite;

    @Column(name = "notation")
    private String notation;

    @Column(name = "lien_unique")
    private String lienUnique;

    @JsonIgnoreProperties(value = { "hotel" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private UIConfiguration uiConfigurations;

    @JsonIgnoreProperties(value = { "hotel" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private EmailTemplateConfiguration emailConfig;

    @JsonIgnoreProperties(value = { "hotel" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private AuthentificationConfiguration authConfig;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "hotel", "partenaire", "reservation" }, allowSetters = true)
    private Set<Service> services = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "hotels" }, allowSetters = true)
    private HotelAdministrateur hotelAdministrateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Hotel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Hotel nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Hotel adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getNumeroTelephone() {
        return this.numeroTelephone;
    }

    public Hotel numeroTelephone(Integer numeroTelephone) {
        this.setNumeroTelephone(numeroTelephone);
        return this;
    }

    public void setNumeroTelephone(Integer numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getPays() {
        return this.pays;
    }

    public Hotel pays(String pays) {
        this.setPays(pays);
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return this.ville;
    }

    public Hotel ville(String ville) {
        this.setVille(ville);
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getVueS() {
        return this.vueS;
    }

    public Hotel vueS(String vueS) {
        this.setVueS(vueS);
        return this;
    }

    public void setVueS(String vueS) {
        this.vueS = vueS;
    }

    public Integer getCapacite() {
        return this.capacite;
    }

    public Hotel capacite(Integer capacite) {
        this.setCapacite(capacite);
        return this;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public String getNotation() {
        return this.notation;
    }

    public Hotel notation(String notation) {
        this.setNotation(notation);
        return this;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    public String getLienUnique() {
        return this.lienUnique;
    }

    public Hotel lienUnique(String lienUnique) {
        this.setLienUnique(lienUnique);
        return this;
    }

    public void setLienUnique(String lienUnique) {
        this.lienUnique = lienUnique;
    }

    public UIConfiguration getUiConfigurations() {
        return this.uiConfigurations;
    }

    public void setUiConfigurations(UIConfiguration uIConfiguration) {
        this.uiConfigurations = uIConfiguration;
    }

    public Hotel uiConfigurations(UIConfiguration uIConfiguration) {
        this.setUiConfigurations(uIConfiguration);
        return this;
    }

    public EmailTemplateConfiguration getEmailConfig() {
        return this.emailConfig;
    }

    public void setEmailConfig(EmailTemplateConfiguration emailTemplateConfiguration) {
        this.emailConfig = emailTemplateConfiguration;
    }

    public Hotel emailConfig(EmailTemplateConfiguration emailTemplateConfiguration) {
        this.setEmailConfig(emailTemplateConfiguration);
        return this;
    }

    public AuthentificationConfiguration getAuthConfig() {
        return this.authConfig;
    }

    public void setAuthConfig(AuthentificationConfiguration authentificationConfiguration) {
        this.authConfig = authentificationConfiguration;
    }

    public Hotel authConfig(AuthentificationConfiguration authentificationConfiguration) {
        this.setAuthConfig(authentificationConfiguration);
        return this;
    }

    public Set<Service> getServices() {
        return this.services;
    }

    public void setServices(Set<Service> services) {
        if (this.services != null) {
            this.services.forEach(i -> i.setHotel(null));
        }
        if (services != null) {
            services.forEach(i -> i.setHotel(this));
        }
        this.services = services;
    }

    public Hotel services(Set<Service> services) {
        this.setServices(services);
        return this;
    }

    public Hotel addServices(Service service) {
        this.services.add(service);
        service.setHotel(this);
        return this;
    }

    public Hotel removeServices(Service service) {
        this.services.remove(service);
        service.setHotel(null);
        return this;
    }

    public HotelAdministrateur getHotelAdministrateur() {
        return this.hotelAdministrateur;
    }

    public void setHotelAdministrateur(HotelAdministrateur hotelAdministrateur) {
        this.hotelAdministrateur = hotelAdministrateur;
    }

    public Hotel hotelAdministrateur(HotelAdministrateur hotelAdministrateur) {
        this.setHotelAdministrateur(hotelAdministrateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hotel)) {
            return false;
        }
        return getId() != null && getId().equals(((Hotel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hotel{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", numeroTelephone=" + getNumeroTelephone() +
            ", pays='" + getPays() + "'" +
            ", ville='" + getVille() + "'" +
            ", vueS='" + getVueS() + "'" +
            ", capacite=" + getCapacite() +
            ", notation='" + getNotation() + "'" +
            ", lienUnique='" + getLienUnique() + "'" +
            "}";
    }
}
