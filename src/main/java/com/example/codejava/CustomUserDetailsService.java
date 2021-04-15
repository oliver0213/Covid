package com.example.codejava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private HpcRepository hpcRepository;

    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        final var type = request.getParameter("usertype");
        if (Objects.equals(type, "Patient")) {
            User user = userRepo.findByEmail(email);
            if (null == user)
                throw new UsernameNotFoundException("User not found");
            user.setType(type);
            return new CustomUserDetails(user);
        } else {
            Hcp hcp = hpcRepository.findByEmail(email);
            if (null == hcp)
                throw new UsernameNotFoundException("User not found");
            User user = new User();
            user.setType(type);
            user.setEnabled(true);
            user.setPassword(hcp.getPassword());
            user.setId(hcp.getId());
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("hc"));
            final var details = new CustomUserDetails(user);
            details.setAuthorities(authorities);
            return details;
        }


    }

    public boolean verify(String verificationCode) {
        User user = userRepo.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepo.save(user);

            return true;
        }

    }

    public void registerCustomer(User user, String siteURL) throws UnsupportedEncodingException, MessagingException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String randomcode = RandomString.make(64);
        user.setVerificationCode(randomcode);
        user.setCreated_time(new Date());
        user.setEnabled(false);

        userRepo.save(user);
        sendVerificationEmail(user, siteURL);

    }

    public void sendVerificationEmail(User user, String siteURL) throws UnsupportedEncodingException, MessagingException {
        String toAddress = user.getEmail();
        String fromAddress = "covidule@gmail.com";
        String subject = "Please verify your registration";
        String senderName = "Covidule Team";
        String mailContent = "<p>Dear " + user.getName() + ",</p>";
        mailContent += "<p>Please click on the link below to verify your registration:</p>";
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
        mailContent += "<h3><a href=\"" + verifyURL + "\">VERIFY</a></h3>";
        mailContent += "<p>Thank you<br>The Covidule Team </p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(mailContent, true);
        mailSender.send(message);
    }

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepo.save(user);
        } else {
            throw new UsernameNotFoundException("Could not find any customer with the email " + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepo.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepo.save(user);
    }
}
