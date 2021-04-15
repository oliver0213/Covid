package com.example.codejava;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm()
    {

        return "Forgot_Password_Screen";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model)
    {

        String email = request.getParameter("email");
        String token = RandomString.make(30);

        try
        {
            customUserDetailsService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        }
        catch (UsernameNotFoundException ex)
        {
            model.addAttribute("error", ex.getMessage());
        }
        catch (UnsupportedEncodingException | MessagingException e)
        {
            model.addAttribute("error", "Error while sending email");
        }

        return "Forgot_Password_Screen";
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("covidule@gmail.com", "Covidule Team");
        helper.setTo(recipientEmail);

        String subject = " Link to reset your password";

        String content =   "<p>Hello ,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><b><a href=\"" + link + "\">Reset my password</a></b></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>"
                + "<p>Thank you,<br>The Covidule Team </p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }


    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model)
    {
        User user = customUserDetailsService.getByResetPasswordToken(token);


        if (user == null)
        {
            model.addAttribute("title", "Reset your password");
            model.addAttribute("message", "Invalid Token");
            return "message";
        }
        model.addAttribute("token", token);
        model.addAttribute("pageTitle", "Reset your password");
        return "ResetPassword";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model)
    {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = customUserDetailsService.getByResetPasswordToken(token);

        if (user == null)
        {
            model.addAttribute("title", "Reset your password");
            model.addAttribute("message", "Invalid Token");
            return "message";
        }
        else
        {
            customUserDetailsService.updatePassword(user, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "message";
    }
    }
