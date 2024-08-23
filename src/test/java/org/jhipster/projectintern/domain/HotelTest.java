package org.jhipster.projectintern.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.projectintern.domain.AuthentificationConfigurationTestSamples.*;
import static org.jhipster.projectintern.domain.EmailTemplateConfigurationTestSamples.*;
import static org.jhipster.projectintern.domain.HotelAdministrateurTestSamples.*;
import static org.jhipster.projectintern.domain.HotelTestSamples.*;
import static org.jhipster.projectintern.domain.ServiceTestSamples.*;
import static org.jhipster.projectintern.domain.UIConfigurationTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.jhipster.projectintern.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HotelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hotel.class);
        Hotel hotel1 = getHotelSample1();
        Hotel hotel2 = new Hotel();
        assertThat(hotel1).isNotEqualTo(hotel2);

        hotel2.setId(hotel1.getId());
        assertThat(hotel1).isEqualTo(hotel2);

        hotel2 = getHotelSample2();
        assertThat(hotel1).isNotEqualTo(hotel2);
    }

    @Test
    void uiConfigurationsTest() {
        Hotel hotel = getHotelRandomSampleGenerator();
        UIConfiguration uIConfigurationBack = getUIConfigurationRandomSampleGenerator();

        hotel.setUiConfigurations(uIConfigurationBack);
        assertThat(hotel.getUiConfigurations()).isEqualTo(uIConfigurationBack);

        hotel.uiConfigurations(null);
        assertThat(hotel.getUiConfigurations()).isNull();
    }

    @Test
    void emailConfigTest() {
        Hotel hotel = getHotelRandomSampleGenerator();
        EmailTemplateConfiguration emailTemplateConfigurationBack = getEmailTemplateConfigurationRandomSampleGenerator();

        hotel.setEmailConfig(emailTemplateConfigurationBack);
        assertThat(hotel.getEmailConfig()).isEqualTo(emailTemplateConfigurationBack);

        hotel.emailConfig(null);
        assertThat(hotel.getEmailConfig()).isNull();
    }

    @Test
    void authConfigTest() {
        Hotel hotel = getHotelRandomSampleGenerator();
        AuthentificationConfiguration authentificationConfigurationBack = getAuthentificationConfigurationRandomSampleGenerator();

        hotel.setAuthConfig(authentificationConfigurationBack);
        assertThat(hotel.getAuthConfig()).isEqualTo(authentificationConfigurationBack);

        hotel.authConfig(null);
        assertThat(hotel.getAuthConfig()).isNull();
    }

    @Test
    void servicesTest() {
        Hotel hotel = getHotelRandomSampleGenerator();
        Service serviceBack = getServiceRandomSampleGenerator();

        hotel.addServices(serviceBack);
        assertThat(hotel.getServices()).containsOnly(serviceBack);
        assertThat(serviceBack.getHotel()).isEqualTo(hotel);

        hotel.removeServices(serviceBack);
        assertThat(hotel.getServices()).doesNotContain(serviceBack);
        assertThat(serviceBack.getHotel()).isNull();

        hotel.services(new HashSet<>(Set.of(serviceBack)));
        assertThat(hotel.getServices()).containsOnly(serviceBack);
        assertThat(serviceBack.getHotel()).isEqualTo(hotel);

        hotel.setServices(new HashSet<>());
        assertThat(hotel.getServices()).doesNotContain(serviceBack);
        assertThat(serviceBack.getHotel()).isNull();
    }

    @Test
    void hotelAdministrateurTest() {
        Hotel hotel = getHotelRandomSampleGenerator();
        HotelAdministrateur hotelAdministrateurBack = getHotelAdministrateurRandomSampleGenerator();

        hotel.setHotelAdministrateur(hotelAdministrateurBack);
        assertThat(hotel.getHotelAdministrateur()).isEqualTo(hotelAdministrateurBack);

        hotel.hotelAdministrateur(null);
        assertThat(hotel.getHotelAdministrateur()).isNull();
    }
}
