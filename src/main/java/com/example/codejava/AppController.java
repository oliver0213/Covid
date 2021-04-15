package com.example.codejava;


import org.hibernate.query.criteria.internal.expression.function.CurrentTimestampFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import net.bytebuddy.utility.RandomString;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Objects;


/* This is a Test Change */

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TransactionAppRepository apptrepo;

    @Autowired
    private TestCenterRepository tcrepo;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private CustomUserDetailsService service;

	@Autowired
	private JavaMailSender mailSender;


	@GetMapping("/login")
	public String login()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
		{
			return "PLogin.html";
		}

		else
		{
			return "Patient_HomePage";
		}

	}

	@GetMapping("/")
	public String landingPage()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
		{
			return "LandingPage.html";
		}

		else
		{
			return "Patient_HomePage";
		}

	}


	@GetMapping("/Appointments")
	public String Appointments(Model model, @AuthenticationPrincipal CustomUserDetails userna)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
		{
			return "PLogin.html";
		}

		else
		{
			List<TransactionApp> listAppsPast = apptrepo.findByNamePastAppointments(userna.getUsername());
			List<TransactionApp> listAppsFuture = apptrepo.findByNameFutureAppointments(userna.getUsername());

			model.addAttribute("listAppsPast", listAppsPast);
			model.addAttribute("listAppsFuture", listAppsFuture);
			model.addAttribute("apptselec", new TransactionApp());

			return "Appointments";
		}


	}



	@GetMapping("/Vaccine")
	public String Vaccine(Model model,  @AuthenticationPrincipal CustomUserDetails userna)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
		{
			return "PLogin.html";
		}

		else
		{
			TransactionApp checkVaccineappt = apptrepo.findByVaccineFutureAppointments(userna.getUsername());
			boolean isexist;

			if(checkVaccineappt == null)
			{
				isexist = false;
			}
			else
			{
				isexist = true;
			}
			model.addAttribute("isexist", isexist);

			List<TestCenter> tstcntr = tcrepo.findAll();

			model.addAttribute("tstcntr", tstcntr);

			model.addAttribute("appt", new TransactionApp());

			return "PVaccine.html";
		}


	}

	@GetMapping("/Test")
	public String Test(Model model, @AuthenticationPrincipal CustomUserDetails userna)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
		{
			return "PLogin.html";
		}

		else
		{
			TransactionApp checkApptExist = apptrepo.findByTestFutureAppointments(userna.getUsername());
			boolean isexist;


			/* If there is an no future Appointment */
			if( checkApptExist == null)
			{
				isexist = false;
			}
			/* If there is an future Appointment */
			else
			{
				isexist = true;
			}

			model.addAttribute("isexist", isexist);

			List<TestCenter> tests = tcrepo.findAll();

			model.addAttribute("tests", tests);

			model.addAttribute("appt", new TransactionApp());
			return "PTest.html";

		}

	}

	@RequestMapping(value = "/UserValidity", method = RequestMethod.GET)
	public @ResponseBody
	boolean findalreadyexistinguser(@RequestParam(value = "US", required = true) String emailID)
	{
		boolean emailexits = false;
		if(userRepo.findByEmail(emailID) != null)
		{
			emailexits = true;
		}
		return emailexits;
	}


	@RequestMapping(value = "/Test/Date", method = RequestMethod.GET)
	public @ResponseBody
	List<String> findAllDates(@RequestParam(value = "TC", required = true) String TestCenter)
	{
		System.out.println(TestCenter);
		List<String> Datesset = appointmentRepository.findDistinctDate(TestCenter);
		System.out.println(Datesset);
		return Datesset;
	}

	@RequestMapping(value = "/Test/Time", method = RequestMethod.GET)
	public @ResponseBody
	List<String> findAllTimes(@RequestParam(value = "TC", required = true) String TestCenter, @RequestParam(value = "TD", required = true)  String TestDate)
	{
		System.out.println("TC" + TestCenter);
		System.out.println("TD" +  TestDate);
		List<String> Timesetfinal= new ArrayList<>();
		int numberofslots;



		List<String> time = appointmentRepository.findDistinctTestTime(TestCenter, TestDate);
		System.out.println(time);
		System.out.println(time.get(0));

		long sl[] = new long[time.size()];

		for (int i = 0; i < time.size(); i++)
		{
			sl[i] = appointmentRepository.findDistinctTestSlot(TestCenter, TestDate,time.get(i));
			numberofslots = apptrepo.CountSlots(TestCenter, TestDate,time.get(i));

			if(numberofslots < sl[i])
			{
				Timesetfinal.add(time.get(i));
			}
			else
			{

			}

		}
		return Timesetfinal;
	}


	@RequestMapping(value = "/vaccine/Date", method = RequestMethod.GET)
	public @ResponseBody
	List<String> findAllvacDates(@RequestParam(value = "TC", required = true) String TestCenter)
	{
		List<String> vacDatesset = appointmentRepository.findDistinctVacDate(TestCenter);
		System.out.println(vacDatesset);
		return vacDatesset;
	}


	@RequestMapping(value = "/vaccine/Time", method = RequestMethod.GET)
	public @ResponseBody
	List<String> findAllVacTimes(@RequestParam(value = "TC", required = true) String TestCenter, @RequestParam(value = "TD", required = true)  String TestDate)
	{
		System.out.println("TCV" + TestCenter);
		System.out.println("VD" +  TestDate);
		List<String> Timesetfinal= new ArrayList<>();
		int numberofslots;

		List<String> time = appointmentRepository.findDistinctvacTime(TestCenter, TestDate);
		System.out.println(time);
		System.out.println(time.get(0));

		long sl[] = new long[time.size()];

		for (int i = 0; i < time.size(); i++)
		{
			sl[i] = appointmentRepository.findDistinctVaccineSlot(TestCenter, TestDate,time.get(i));
			numberofslots = apptrepo.CountSlotsVac(TestCenter, TestDate,time.get(i));

			if(numberofslots < sl[i])
			{
				Timesetfinal.add(time.get(i));
			}
			else
			{

			}

		}
		return Timesetfinal;
	}


	@GetMapping("/register")
	public String showRegistrationForm(Model model)
	{
		model.addAttribute("user", new User());

		return "Registration.html";
	}


	@PostMapping("/process_register")
	public String processRegister(User user, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {

		service.registerCustomer(user, getSiteURL(request));

		return "register_success";
	}

	private String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}


	@GetMapping("/verify")
	public String verifyUser(@Param("code") String code) {
		if (service.verify(code))
		{
			return "verify_success";
		}
		else
		{
			return "verify_fail";
		}
	}

    @GetMapping("login-success")
    public String loginSuccess(Model model)
	{
        final CustomUserDetails details =  (com.example.codejava.CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final var user = details.getUser();
        model.addAttribute("AUser", user);
        if (Objects.equals(user.getType(), "Patient")) {
            return "Patient_HomePage";
        } else {
            return "HC_Home";
        }
    }

	@GetMapping("/users")
	public String listUsers(Model model)
	{
		List<User> listUsers = userRepo.findAll();
		model.addAttribute("listUsers", listUsers);

		return "users";
	}
	@PostMapping("/Test_confirm")
	public String testAppointment(TransactionApp appt , @AuthenticationPrincipal CustomUserDetails userna) throws UnsupportedEncodingException, MessagingException {
		appt.setPatient_id(userna.getUsername());
		appt.setType("Test");
		appt.setStatus("Scheduled");

		apptrepo.save(appt);

		sendTestSuccessEmail(appt,userna);

		return "TestApptSuccess.html";
	}



	private void sendTestSuccessEmail(TransactionApp appt ,@AuthenticationPrincipal CustomUserDetails userna) throws UnsupportedEncodingException, MessagingException {
		String toAddress = userna.getUsername();
		String testcenter = appt.getTest_center();
		String testDate = appt.getDate();
		String testTime = appt.getTimeslot();
		String fromAddress = "covidule@gmail.com";
		String subject = "Covid Test Confirmation";
		String senderName = "Covidule Team";
		String mailContent = "<p>Dear " + userna.getFullName() + ",</p>";
		mailContent += "<p>This email confirms you have successfully scheduled for the Covid Test.</p>";
		mailContent  += "<B><p>COVID TEST DETAILS:</p></B>";
		mailContent += "<p>  Location: " + testcenter +"</p>";
		mailContent += "<p>  Test Date: " + testDate +"</p>";
		mailContent += "<p>  Time: " + testTime +"</p>";
		mailContent += "<p>Please check your appointment portal for further information.</p>";
		mailContent += "<p>Thank you<br>The Covidule Team. </p>";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);
		helper.setText(mailContent, true);
		mailSender.send(message);
	}


	@PostMapping("/Vac_confirm")
	public String VacAppointment(TransactionApp appt , @AuthenticationPrincipal CustomUserDetails userna) throws UnsupportedEncodingException, MessagingException {
		appt.setPatient_id(userna.getUsername());
		appt.setType("Vaccination");
		appt.setStatus("Scheduled");

		apptrepo.save(appt);
		sendVaccineSuccessEmail(appt,userna);
		return "VaccApptSuccess.html";
	}

	private void sendVaccineSuccessEmail(TransactionApp appt, @AuthenticationPrincipal CustomUserDetails userna) throws MessagingException, UnsupportedEncodingException {
		String toAddress = userna.getUsername();
		String testcenter = appt.getTest_center();
		String testDate = appt.getDate();
		String testTime = appt.getTimeslot();
		String fromAddress = "covidule@gmail.com";
		String subject = "Covid Vaccine Confirmation";
		String senderName = "Covidule Team";
		String mailContent = "<p>Dear " + userna.getFullName() + ",</p>";
		mailContent += "<p>This email confirms you have successfully scheduled for the Covid Vaccination.</p>";
		mailContent  += "<B><p>COVID VACCINATION DETAILS:</p></B>";
		mailContent += "<p> Location: " + testcenter +"</p>";
		mailContent += "<p> Vaccination Date: " + testDate +"</p>";
		mailContent += "<p> Time: " + testTime +"</p>";
		mailContent += "<p>Please check your appointment portal for further information.</p>";
		mailContent += "<p>Thank you<br>The Covidule Team. </p>";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);
		helper.setText(mailContent, true);
		mailSender.send(message);
	}

	@PostMapping("/patientindscrn")
	public String patientindscrn(Model model, TransactionApp apptselec)
	{

		TransactionApp listappdetail = apptrepo.findBytransactionId(apptselec.getId());
		System.out.println(apptselec.getId());

		model.addAttribute("listappdetail", listappdetail);
		return "Patient_IndividualAppointments.html";
	}


	@GetMapping("/PHome")
	public String PatientHome(Model model, @AuthenticationPrincipal CustomUserDetails userna)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
		{
			return "PLogin.html";
		}
		else
		{
			model.addAttribute("user", new User());

			User AUser = userRepo.findByEmail(userna.getUsername());

			model.addAttribute("AUser", AUser);

			return "PHome";
		}

	}

	@GetMapping("/Patient_HomePage")
	public String PatientHomePage()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
		{
			return "PLogin.html";
		}

		else
		{
			return "Patient_HomePage";
		}

	}

	@PostMapping("/update_register")
	public String updateRegister(User user, Model model, @AuthenticationPrincipal CustomUserDetails userna)
	{

		User AUser = userRepo.findByEmail(userna.getUsername());

		String nm = user.getName();
		String em = user.getEmail();
		String ph = user.getPhone();
		String ad = user.getAddress();
		Long id = user.getId();

		AUser.setName(nm);
		AUser.setEmail(em);
		AUser.setPhone(ph);
		AUser.setAddress(ad);

		System.out.println(user.getName());
		userRepo.updateUser(nm,em,ph,ad,id);

		model.addAttribute("AUser", AUser);
		model.addAttribute("user", new User());


		return "PHome";
	}

	@GetMapping("/delete_appointment")
	public String deleteAppointment(TransactionApp trans, Model model, @AuthenticationPrincipal CustomUserDetails userna)
	{

		System.out.println(trans.getId());
		System.out.println(trans.getReport());
		apptrepo.deleteBytransactionId(trans.getId());


		List<TransactionApp> listAppsPast = apptrepo.findByNamePastAppointments(userna.getUsername());
		List<TransactionApp> listAppsFuture = apptrepo.findByNameFutureAppointments(userna.getUsername());

		model.addAttribute("listAppsPast", listAppsPast);
		model.addAttribute("listAppsFuture", listAppsFuture);
		model.addAttribute("apptselec", new TransactionApp());
		return "Appointments";
	}

    @Autowired
    private TestCenterRepository centerRepository;

    @GetMapping("/hc/test-center")
    public String home() {
        return "HC_Home";
    }

    @GetMapping("/hc/test-centers")
    @ResponseBody
    public List<TestCenter> getCenters() {
        return centerRepository.findAll();
    }
}
