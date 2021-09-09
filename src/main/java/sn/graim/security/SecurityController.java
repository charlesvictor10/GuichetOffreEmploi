package sn.graim.security;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sn.graim.dao.PasswordResetTokenRepository;
import sn.graim.dao.RoleRepository;
import sn.graim.dao.UtilisateurRepository;
import sn.graim.dto.PasswordForgotDto;
import sn.graim.dto.PasswordResetDto;
import sn.graim.entities.PasswordResetToken;
import sn.graim.entities.Role;
import sn.graim.entities.Utilisateur;
import sn.graim.jutil.Mail;
import sn.graim.mail.EmailService;
import sn.graim.mail.MyConstants;

@Controller
public class SecurityController {
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	@Autowired
    public EmailService emailService;
		
	@PostConstruct
	public void init() {
		System.out.println("Initialisation du contrôleur d'authentification");
	}
	
	@RequestMapping(value={"/", "/index/**"}, method = RequestMethod.GET)
	public String index(Model model) {
		return "redirect:/index";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.GET)
	public String formInscription(Model model) {
		String x = "RECRUTEUR";
		String y = "CANDIDAT";
		List<Role> roles = roleRepository.listeRole(x, y);
		model.addAttribute("roles", roles);
		model.addAttribute("utilisateur", new Utilisateur());
		return "inscription";
	}
	
	@RequestMapping(value="/SaveUtilisateur", method=RequestMethod.POST)
	public String Save(@Valid Utilisateur u, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "inscription";
		}
		
		u.setActive(true);
		u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
		utilisateurRepository.save(u);
				
		return "redirect:/connexion";
	}
		
	@RequestMapping(value = "/connexion", method=RequestMethod.GET)
	public String login(Model model) {
		return "connexion";
	}
	
	@RequestMapping(value="/logoutSuccessful", method=RequestMethod.GET)
	public String logoutSuccessful(Model model) {
		return "redirect:/connexion";
	}
		
	@RequestMapping(value="/userInfo", method=RequestMethod.GET)
	public String userInfo(Model model, Principal principal) {
		String userName = principal.getName();
		System.out.println("User name:" +userName);
		User loginUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginUser);
		model.addAttribute("userInfo", userInfo);
		return "";
	}
		
	@RequestMapping(value="/getLogedUser", method=RequestMethod.GET)
	public Map<String, Object> getLogedUser(HttpSession session){
		SecurityContext context = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username = context.getAuthentication().getName();
		List<String> roles = new ArrayList<String>();
		for(GrantedAuthority ga: context.getAuthentication().getAuthorities()) {
			roles.add(ga.getAuthority());
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("roles", roles);
		return params;
	}
				
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accesDenied(Model model, Principal principal) {
		if(principal!=null) {
			User loginUser = (User) ((Authentication) principal).getPrincipal();
			String userInfo = WebUtils.toString(loginUser);
			model.addAttribute("userInfo", userInfo);
			String message = "Désolé, "+principal.getName()+" vous n'avez pas le droit d'accéder à cette page";
			model.addAttribute("message", message);
		}
		return "403";
	}
		
	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String notFoundPage(Model model) {
		return "404";
	}
	
	@ModelAttribute("forgotPasswordForm")
	public PasswordForgotDto forgotPasswordDto() {
		return new PasswordForgotDto();
	}
	
	@RequestMapping(value="/forgot-password", method=RequestMethod.GET)
	public String reinitialiser(Model model) {
		model.addAttribute("utilisateur", new Utilisateur());
		return "reinitialiser";
	}
	
	@RequestMapping(value="/envoieLien", method=RequestMethod.POST)
	public String processForgotPasswordForm(@ModelAttribute("forgotPasswordForm") @Valid PasswordForgotDto form, BindingResult result, HttpServletRequest request) {
		if(result.hasErrors()) {
			return "reinitialiser";
		}
		
		Utilisateur u = utilisateurRepository.findByUsername(form.getUsername());
		if(u == null) {
			result.rejectValue("email", null, "Aucun compte n'est associé à cet adresse email.");
			return "reinitialiser";
		}
		
		PasswordResetToken token = new PasswordResetToken();
		token.setToken(UUID.randomUUID().toString());
		token.setUtilisateur(u);
		token.setExpiryDate(24);
		passwordResetTokenRepository.save(token);
		
		Mail mail = new Mail();
		mail.setFrom(MyConstants.MY_EMAIL);
		mail.setTo(u.getUsername());
		mail.setSubject("Réinitialisation du mot de passe");
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("token", token);
		model.put("utilisateur", u);
		model.put("signature", "https://guichetemploi.com");
		String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		model.put("resetUrl", url+"/reset-password?token="+token.getToken());
		mail.setModel(model);
		emailService.sendForgotPasswordMail(mail);
		return "redirect:/forgot-password?success";
	}
	
	@ModelAttribute("passwordResetForm")
    public PasswordResetDto passwordReset() {
        return new PasswordResetDto();
    }

	@RequestMapping(value="/reset-password", method=RequestMethod.GET)
	public String displayResetPasswordPage(@RequestParam(required = false) String token, Model model) {
		PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
		if(resetToken == null) {
			model.addAttribute("error", "Lien de réinitialisation invalide");
		} else if(resetToken.isExpired()) {
			model.addAttribute("error", "Le lien à expiré, veuillez demander un nouveau lien svp");
		} else {
			model.addAttribute("token", resetToken.getToken());
		}
		return "reset-password";
	}
	
	@RequestMapping(value="/saveResetPassword", method=RequestMethod.POST)
	@Transactional
	public String handlePasswordReset(@ModelAttribute("passwordResetForm") @Valid PasswordResetDto form, BindingResult result, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			redirectAttributes.addFlashAttribute(BindingResult.class.getName()+".passwordResetForm", result);
            redirectAttributes.addFlashAttribute("passwordResetForm", form);
            return "redirect:/reset-password?token="+form.getToken();
		}
		
		PasswordResetToken token = passwordResetTokenRepository.findByToken(form.getToken());
		Utilisateur u = token.getUtilisateur();
		String updatedPassword = new BCryptPasswordEncoder().encode(form.getPassword());
		utilisateurRepository.updatePassword(updatedPassword, u.getUsername());
		passwordResetTokenRepository.delete(token);
		return "redirect:/connexion?resetsuccess";
	}
	
	@PreDestroy
	public void destroy() {
		System.out.println("Destuction du contrôleur d'authentification");
	}
}
