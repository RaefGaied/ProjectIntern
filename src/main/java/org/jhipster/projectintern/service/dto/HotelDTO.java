package org.jhipster.projectintern.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.jhipster.projectintern.domain.Hotel} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HotelDTO implements Serializable {

    private Long id;

    private String nom;

    private String adresse;

    private Integer numeroTelephone;

    private String pays;

    private String ville;

    private String vueS;

    private Integer capacite;

    private String notation;

    private String lienUnique;

    private UIConfigurationDTO uiConfigurations;

    private EmailTemplateConfigurationDTO emailConfig;

    private AuthentificationConfigurationDTO authConfig;

    private HotelAdministrateurDTO hotelAdministrateur;
    private String hotelAdministrateurNom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(Integer numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getVueS() {
        return vueS;
    }

    public void setVueS(String vueS) {
        this.vueS = vueS;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    public String getLienUnique() {
        return lienUnique;
    }

    public void setLienUnique(String lienUnique) {
        this.lienUnique = lienUnique;
    }

    public UIConfigurationDTO getUiConfigurations() {
        return uiConfigurations;
    }

    public void setUiConfigurations(UIConfigurationDTO uiConfigurations) {
        this.uiConfigurations = uiConfigurations;
    }

    public EmailTemplateConfigurationDTO getEmailConfig() {
        return emailConfig;
    }

    public void setEmailConfig(EmailTemplateConfigurationDTO emailConfig) {
        this.emailConfig = emailConfig;
    }

    public AuthentificationConfigurationDTO getAuthConfig() {
        return authConfig;
    }

    public void setAuthConfig(AuthentificationConfigurationDTO authConfig) {
        this.authConfig = authConfig;
    }

    public HotelAdministrateurDTO getHotelAdministrateur() {
        return hotelAdministrateur;
    }

    public void setHotelAdministrateur(HotelAdministrateurDTO hotelAdministrateur) {
        this.hotelAdministrateur = hotelAdministrateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HotelDTO)) {
            return false;
        }

        HotelDTO hotelDTO = (HotelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hotelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, adresse, numeroTelephone, pays, ville, vueS, capacite, notation, lienUnique, hotelAdministrateur);
    }



    // prettier-ignore
    @Override
    public String toString() {
        return "HotelDTO{" +
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
            ", uiConfigurations=" + getUiConfigurations() +
            ", emailConfig=" + getEmailConfig() +
            ", authConfig=" + getAuthConfig() +
            ", hotelAdministrateur=" + getHotelAdministrateur() +
            "}";
    }


    public void setHotelAdministrateurNom(String hotelAdministrateurNom) {
        this.hotelAdministrateurNom=hotelAdministrateurNom;

    }

}
/*return Objects.equals(nom, hotelDTO.nom) &&
    Objects.equals(adresse, hotelDTO.adresse) &&
    Objects.equals(numeroTelephone, hotelDTO.numeroTelephone) &&
    Objects.equals(pays, hotelDTO.pays) &&
    Objects.equals(ville, hotelDTO.ville) &&
    Objects.equals(vueS, hotelDTO.vueS) &&
    Objects.equals(capacite, hotelDTO.capacite) &&
    Objects.equals(notation, hotelDTO.notation) &&
    Objects.equals(lienUnique, hotelDTO.lienUnique) &&
    Objects.equals(hotelAdministrateur, hotelDTO.hotelAdministrateur);*/
