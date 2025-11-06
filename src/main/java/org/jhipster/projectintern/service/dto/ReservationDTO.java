package org.jhipster.projectintern.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import org.jhipster.projectintern.domain.enumeration.Statut;

/**
 * A DTO for the {@link org.jhipster.projectintern.domain.Reservation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReservationDTO implements Serializable {

    private Long id;

    private ZonedDateTime dateDebut;

    private String roomType;

    private ZonedDateTime dateFin;

    private Integer nombrePersonnes;

    private Float totalPrix;

    private Statut statutPaiement;

    private HotelDTO hotel;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ZonedDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public Integer getNombrePersonnes() {
        return nombrePersonnes;
    }

    public void setNombrePersonnes(Integer nombrePersonnes) {
        this.nombrePersonnes = nombrePersonnes;
    }

    public Float getTotalPrix() {
        return totalPrix;
    }

    public void setTotalPrix(Float totalPrix) {
        this.totalPrix = totalPrix;
    }

    public Statut getStatutPaiement() {
        return statutPaiement;
    }

    public void setStatutPaiement(Statut statutPaiement) {
        this.statutPaiement = statutPaiement;
    }

    public HotelDTO getHotel() {
        return hotel;
    }

    public void setHotel(HotelDTO hotel) {
        this.hotel = hotel;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
    public String getRoomType() { // Add getter for roomType
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservationDTO)) {
            return false;
        }

        ReservationDTO reservationDTO = (ReservationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reservationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReservationDTO{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", nombrePersonnes=" + getNombrePersonnes() +
            ", totalPrix=" + getTotalPrix() +
            ", statutPaiement='" + getStatutPaiement() + "'" +
            ", hotel=" + getHotel() +
            ", user=" + getUser() +
            "}";
    }
}
