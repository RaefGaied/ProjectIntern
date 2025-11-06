package org.jhipster.projectintern.web.rest;

import org.jhipster.projectintern.service.MailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailTestingController {

    private final MailService mailService;


    public EmailTestingController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send-test-email")
    public String sendTestEmail() {
      //  mailService.SendEmailAsync("raefgaied@gmail.com", "Email Testing from SpringBoot","this is a test email");
        mailService.sendEmail("khalil.g.fendri@gmail.com", "Email Testing from SpringBoot","this is a test email",false,false);
        return "Email Testing Successfully Sent";
    }


}
