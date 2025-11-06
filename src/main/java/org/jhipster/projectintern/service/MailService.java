package org.jhipster.projectintern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.jhipster.projectintern.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import tech.jhipster.config.JHipsterProperties;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * Service for sending emails asynchronously.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service

public class MailService {

    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;
     ;

    public MailService(
        JHipsterProperties jHipsterProperties,
        JavaMailSender javaMailSender,
        MessageSource messageSource,
        SpringTemplateEngine templateEngine
    ) {
        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        this.sendEmailSync(to, subject, content, isMultipart, isHtml);
    }

    private void sendEmailSync(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug(
            "Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart,
            isHtml,
            to,
            subject,
            content
        );

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        this.sendEmailFromTemplateSync(user, templateName, titleKey);
    }

    private void sendEmailFromTemplateSync(User user, String templateName, String titleKey) {
        if (user.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", user.getLogin());
            return;
        }

        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        this.sendSimpleMessage(user.getEmail(), subject, content);
    }

    @Async
    public void sendHotelUniqueLinkEmail(User hotelAdministrateur, String lienUnique) {
        log.debug("Sending unique link email to '{}'", hotelAdministrateur.getEmail());

        if (hotelAdministrateur.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", hotelAdministrateur.getLogin());
            return;
        }

        Locale locale = Locale.forLanguageTag(hotelAdministrateur.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, hotelAdministrateur);
        context.setVariable("lienUnique", lienUnique);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process("mail/hotelUniqueLinkEmail", context);
        String subject = messageSource.getMessage("email.hotel.uniqueLink.title", null, locale);

        this.sendSimpleMessage(hotelAdministrateur.getEmail(), subject, content);
    }


    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        this.sendEmailFromTemplateSync(user, "mail/activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        this.sendEmailFromTemplateSync(user, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        this.sendEmailFromTemplateSync(user, "mail/passwordResetEmail", "email.reset.title");
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            String cleanTo = to.trim();

            if (!isValidEmail(cleanTo)) {
                throw new IllegalArgumentException("Invalid email address: " + cleanTo);
            }

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setTo(cleanTo);
            helper.setSubject(subject);
            helper.setText(text, true);
            message.setFrom("WanderLux@Booking.com");

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // Handle invalid email format case
            e.printStackTrace();
        }
    }

    // Optional: Method to validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$|^[\\w-\\.]+@(yahoo|localhost)$";
        return email.matches(emailRegex);
    }

    public void sendReservationEmail(User user, String hotelName, String roomType, String checkInDate, String checkOutDate, String totalPrice) {
        Context context = new Context(Locale.getDefault());
        context.setVariable("user", user);
        context.setVariable("hotelName", hotelName);
        context.setVariable("roomType", roomType);
        context.setVariable("checkInDate", checkInDate);
        context.setVariable("checkOutDate", checkOutDate);
        context.setVariable("totalPrice", totalPrice);

        String content = templateEngine.process("mail/reservationEmail", context);
        String subject = "Reservation Confirmation";

        this.sendSimpleMessage(user.getEmail(), subject, content);
    }

/*public void SendEmailAsync(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("example@example.com");
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }*/


}
