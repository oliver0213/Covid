package com.example.codejava;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("hc")
public class AppointmentApi {

    @Autowired
    private AppointmentHcRepository repository;

    @Autowired
    private TestCenterRepository centerRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("appointment")
    public String index(@RequestParam("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "HC_Appt";
    }

    @GetMapping("appointments/{id}")
    @ResponseBody
    public List<AppointmentHc> findAll(@PathVariable("id") Long id) {
        return centerRepository.findById(id)
                .map((c) -> repository.findByTestCenter(c.getName()))
                .orElse(new ArrayList<>());
    }


    @PostMapping("appointments")
    @ResponseBody
    public ResponseEntity<Boolean> add(@RequestBody AppointmentHc appointment) {
        appointment.buildRecord();
        centerRepository.findById(appointment.getCenterId()).ifPresent((cen) -> {
            appointment.setTestCenter(cen.getName());
            if (appointment.getAppointmentId() == null) {
                repository
                        .findByTestCenterAndDateAndTimeAndType(appointment.getTestCenter(),
                                appointment.getDate(),
                                appointment.getTime(), appointment.getType())
                        .ifPresent(a -> {
                            appointment.setAppointmentId(a.getAppointmentId());
                            appointment.setInvitedCount(appointment.getInvitedCount() + a.getInvitedCount());
                        });
            }
            repository.save(appointment);
        });
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("appointments/{id}")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        repository.findById(id).ifPresent(p -> {
            final List<Transaction> transactions =
                    transactionRepository.findByTestCenterAndDateAndTimeslot(p.getTestCenter(),p.getDate(),
                            p.getTime());
            transactions.forEach(t -> {
                try {
                    System.err.println("send email to" + t.getPatientId());
                    final var user = t.getUser();
                    System.err.println("Sent successfully to" + t.getPatientId());

                    sendEmail(user.getEmail(), user.getFullname(), t.getDate(),
                            t.getTimeslot());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            int num =
                    transactionRepository.deleteByTestCenterAndDateAndTimeslot(p.getTestCenter(),p.getDate(),p.getTime());
            repository.deleteById(id);
        });


        return ResponseEntity.ok(StringUtils.EMPTY);
    }


    public void sendEmail(String email, String name, String date,
                          String time) throws UnsupportedEncodingException,
            MessagingException {
        String fromAddress = "covidule@gmail.com";
        String subject = "You Appointment Has Canceled";
        String senderName = "Covidule Team";
        String mailContent = "<p>Dear " + name + ",</p>";
        mailContent += "<p>You Appointment at " + date + "-" + time + " Has Canceled:</p>";


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(mailContent, true);
        mailSender.send(message);
    }
}
