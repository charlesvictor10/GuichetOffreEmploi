package sn.graim.web;
import java.io.File;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sn.graim.dao.CandidatRepository;
import sn.graim.dao.CritereRepository;
import sn.graim.dao.MetiersRepository;
import sn.graim.dao.NewlettersRepository;
import sn.graim.dao.OffresRepository;
import sn.graim.dao.RecruteurRepository;
import sn.graim.dao.RegionRepository;
import sn.graim.dao.SecteursRepository;
import sn.graim.dao.SuiviRepository;
import sn.graim.dao.UtilisateurRepository;
import sn.graim.entities.Candidat;
import sn.graim.entities.Critere;
import sn.graim.entities.Metiers;
import sn.graim.entities.Newletters;
import sn.graim.entities.Offres;
import sn.graim.entities.Recruteur;
import sn.graim.entities.Region;
import sn.graim.entities.Secteurs;
import sn.graim.entities.Suivi;
import sn.graim.entities.Utilisateur;
import sn.graim.exception.ResourceNotFoundException;
import sn.graim.jutil.FileUploadUtil;
import sn.graim.service.ProfilServiceImpl;

@Controller
public class EmploiController {
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private SecteursRepository secteurRepository;
	@Autowired
	private RecruteurRepository recruteurRepository;
	@Autowired
	private CandidatRepository candidatRepository;
	@Autowired
	private OffresRepository offreRepository;
	@Autowired
	private MetiersRepository metierRepository;
	@Autowired
	private CritereRepository critereRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private SuiviRepository suiviRepository;
	@Autowired
	private NewlettersRepository newlettersRepository;
	@Autowired
    private JavaMailSender emailSender;
	@Autowired
	private ProfilServiceImpl service;
		
	public static String titre = null;
	public static String email = null;
	
	@PostConstruct
	public void init() {
		System.out.println("Initialisation du contrôleur des emplois");
	}
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index(Model model, @RequestParam(name="motCle", defaultValue="") String mc, @RequestParam(name="page", defaultValue="0") int p, @RequestParam(name="size", defaultValue="8") int s) {
		List<Region> regions = regionRepository.findAll();
		List<Metiers> metiers = metierRepository.findAll();
		
		Page<Secteurs> secs = secteurRepository.recherche("%"+mc+"%", PageRequest.of(p,s));
		model.addAttribute("secteurs", secs.getContent());
		int[] pages = new int[secs.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		
		Page<Offres> os = offreRepository.rechercheParTitre("%"+mc+"%", PageRequest.of(p,s));
		model.addAttribute("offres", os.getContent());
		int[] page = new int[secs.getTotalPages()];
		model.addAttribute("pages", page);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		
		model.addAttribute("regions", regions);
		model.addAttribute("metiers", metiers);
		model.addAttribute("nombres", offreRepository.nombreOffres());
		model.addAttribute("newletters", new Newletters());
		return "index";
	}
	
	@RequestMapping(value="/recherche", method=RequestMethod.GET)
	public String rechercheOffre(Model model, @RequestParam(name="titre", defaultValue="") String titre,
			@RequestParam(name="region", defaultValue="") String region,
			@RequestParam(name="metier", defaultValue="") String metier,
			@RequestParam(name="page", defaultValue="0") int p,
			@RequestParam(name="size", defaultValue="8") int s) {		
		List<Region> regions = regionRepository.findAll();
		List<Metiers> metiers = metierRepository.findAll();
		Page<Offres> moteurRecherche = offreRepository.moteurRecherche("%"+titre+"%",region,metier,PageRequest.of(p,s));
		model.addAttribute("moteurRecherche", moteurRecherche.getContent());
		int[] pages = new int[moteurRecherche.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("titre", titre);
		model.addAttribute("regions", regions);
		model.addAttribute("metiers", metiers);
		model.addAttribute("nombres", offreRepository.nombreOffres());
		return "rechercheOffres";
	}
		
	@RequestMapping(value="/formRecruteur", method=RequestMethod.GET)
	public String form(Model model) {
		model.addAttribute("regions", regionRepository.findAll());
		List<Secteurs> secteurs = (List<Secteurs>) secteurRepository.findAll();
		model.addAttribute("secteurs", secteurs);
		model.addAttribute("recruteur", new Recruteur());
		return "recruteur";
	}
	
	@RequestMapping(value="/SaveRecruteur", method=RequestMethod.POST)
	public String Save(@Valid Recruteur r, BindingResult bindingResult, @RequestParam(name="picture") MultipartFile file) throws Exception {
		if(bindingResult.hasErrors()) {
			return "recruteur";
		}
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		r.setLogo(fileName);
		
		Recruteur rec = recruteurRepository.save(r);
		
		String uploadDir = "logo_entreprise/"+rec.getId();
		FileUploadUtil.saveFile(uploadDir, fileName, file);
		
		return "redirect:/entreprises";
	}
		
	@RequestMapping(value="/entreprises", method=RequestMethod.GET)
	public String listeEntreprises(Model model, @RequestParam(name="motCle", defaultValue="") String mc, @RequestParam(name="page", defaultValue="0") int p, @RequestParam(name="size", defaultValue="8") int s) {
		Page<Recruteur> recs = recruteurRepository.chercherRecruteurs("%"+mc+"%", PageRequest.of(p,s));
		model.addAttribute("listeRecruteurs", recs.getContent());
		int[] pages = new int[recs.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		return "entreprises";
	}
	
	@RequestMapping(value="/formEmploi", method=RequestMethod.GET)
	public String formOffre(Model model) {
		List<Recruteur> recruteurs = (List<Recruteur>) recruteurRepository.findAll();
		model.addAttribute("recruteurs", recruteurs);
		List<Metiers> metiers = (List<Metiers>) metierRepository.findAll();
		model.addAttribute("metiers", metiers);
		List<Region> regions = (List<Region>) regionRepository.findAll();
		model.addAttribute("regions", regions);
		model.addAttribute("offres", new Offres());
		return "emploi";
	}
	
	@RequestMapping(value="/SaveEmploi", method=RequestMethod.POST)
	public String Save(@Valid Offres o, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "emploi";
		}
				
		offreRepository.save(o);
				
		return "redirect:/listeEmplois";
	}
		
	@RequestMapping(value="/candidats", method=RequestMethod.GET)
	public String listeCandidats(Model model, @RequestParam(name="motCle", defaultValue="") String mc, @RequestParam(name="page", defaultValue="0") int p, @RequestParam(name="size", defaultValue="8") int s) {
		List<Candidat> candidats = candidatRepository.findAll();
		Page<Candidat> cands = candidatRepository.chercherCandidats("%"+mc+"%", PageRequest.of(p,s));
		model.addAttribute("listeCandidats", cands.getContent());
		int[] pages = new int[cands.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		model.addAttribute("candidats", candidats);
		return "candidats";
	}
	
	@RequestMapping(value="/listeEmplois", method=RequestMethod.GET)
	public String listeOffres(Model model, @RequestParam(name="motCle", defaultValue="") String mc, @RequestParam(name="page", defaultValue="0") int p, @RequestParam(name="size", defaultValue="8") int s) {
		Page<Offres> emps = offreRepository.rechercheParTitre("%"+mc+"%", PageRequest.of(p,s));
		model.addAttribute("listeEmplois", emps.getContent());
		int[] pages = new int[emps.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		model.addAttribute("nombres", offreRepository.nombreOffres());
		return "listeEmploi";
	}
		
	@RequestMapping(value="/detailPoste", method=RequestMethod.GET)
	public String detail(Long idOffre, Model model) {
		Offres o = offreRepository.findById(idOffre)
				.orElseThrow(() -> new ResourceNotFoundException("L'offre avec l'identifiant n°",idOffre," est introuvable ou inexistant."));
		model.addAttribute("offre", o);
		return "detailOffre";
	}
			
	@RequestMapping(value="/listeCategorie", method=RequestMethod.GET)
	public String listeCategorie(String nom, Model model, @RequestParam(name="motCle", defaultValue="") String mc, @RequestParam(name="page", defaultValue="0") int p, @RequestParam(name="size", defaultValue="8") int s) {
		Page<Secteurs> secs = secteurRepository.recherche("%"+mc+"%", PageRequest.of(p,s));
		model.addAttribute("listeCategorie", secs.getContent());
		int[] pages = new int[secs.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		return "listeCategorie";
	}
		
	@RequestMapping(value="/detailSecteur", method=RequestMethod.GET)
	public String detailSecteur(Long idSecteur, Model model) {
		Secteurs s = secteurRepository.findById(idSecteur)
				.orElseThrow(() -> new ResourceNotFoundException("Le secteur avec l'identifiant n°",idSecteur," est introuvable ou inexistant."));
		List<Offres> o = offreRepository.offresBySecteur(s.getNom());
		model.addAttribute("secteur", s);
		model.addAttribute("offres", o);
		return "detailSecteur";
	}
		
	@RequestMapping(value="/postuler", method=RequestMethod.GET)
	public String postuler(Long idOffre, Principal principal, Model model) {
		Offres o = offreRepository.findById(idOffre)
				.orElseThrow(() -> new ResourceNotFoundException("L'offre avec l'identifiant n°",idOffre," est introuvable ou inexistant."));
		String username = principal.getName();
		Utilisateur u = utilisateurRepository.findByUsername(username);
		Candidat c = candidatRepository.findByUtilisateur(u);
		model.addAttribute("offre", o);
		model.addAttribute("candidat", c);
		titre = o.getTitre();
		email = o.getEmail();
		return "postuler";
	}
	
	@ResponseBody
    @RequestMapping(value="/sendEmail", method=RequestMethod.POST)
    public String sendEmail(Principal principal, BindingResult result) throws MessagingException {
		if(result.hasErrors()) {
			return "postuler";
		}
		
		String username = principal.getName();
		Utilisateur u = utilisateurRepository.findByUsername(username);
		Candidat c = candidatRepository.findByUtilisateur(u);
		if(c == null) {
			result.rejectValue("email", null, "Aucun compte n'est associé à cet adresse email.");
			return "postuler";
		}
		
    	Suivi s = new Suivi();
        s.setCandidat(c);
        s.setTitre(titre);
        s.setEnvoie(new Date());
        s.setStatut("envoyer");
        suiviRepository.save(s);
    	
        MimeMessage message = emailSender.createMimeMessage();
        
        boolean multipart = true;
 
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart);
 
        helper.setTo(email);
        helper.setSubject("Candidature au poste de "+titre);
         
        String path = "../cv/"+c.getCv()+""+c.getId();
        System.out.println(path);
 
        // Attachment 1
        FileSystemResource file = new FileSystemResource(new File(path));
        helper.addAttachment("CV", file);
  
        emailSender.send(message);
        
        return "redirect:/postuler?success";
    }
		
	@RequestMapping(value="/info", method=RequestMethod.GET)
	public String complement(Principal principal, Model model) {
		String username = principal.getName();
		Utilisateur u = utilisateurRepository.findByUsername(username);
		Candidat c = candidatRepository.findByUtilisateur(u);
		model.addAttribute("candidat", c);
		model.addAttribute("candidat", service.newCandidat());
		return "information";
	}
	
	@RequestMapping(value="/addFormation", method=RequestMethod.POST)
	public String newFormation(Candidat c) {
		service.addFormation(c);
		return "information::formations";
	}
	
	@RequestMapping(value="/removeFormation", method=RequestMethod.POST)
	public String removeFormation(Candidat c, @RequestParam("removeFormation") Integer formationIndex) {
		service.removeFormation(c, formationIndex);
		return "information::formations";
	}
	
	@RequestMapping(value="/addExperience", method=RequestMethod.POST)
	public String newExperience(Candidat c) {
		service.addExperience(c);
		return "information::experiences";
	}
	
	@RequestMapping(value="/removeExperience", method=RequestMethod.POST)
	public String removeExperience(Candidat c, @RequestParam("removeExperience") Integer experienceIndex) {
		service.removeExperience(c, experienceIndex);
		return "information::experiences";
	}
	
	@RequestMapping(value="/saveCandidat", method=RequestMethod.POST)
	public String saveCandidat(@Valid Candidat c, Principal principal, BindingResult bindingResult, @RequestParam(name="file") MultipartFile file, @RequestParam(name="picture") MultipartFile files, Model model) throws Exception {
		if(bindingResult.hasErrors()) {
			model.addAttribute("error", "Une erreur est survenue lors de l'enregistrement du candidat. Veuillez réessayer svp!!!");
			return "information";
		}
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		c.setCv(fileName);
		
		String imageName = StringUtils.cleanPath(files.getOriginalFilename());
		c.setPhoto(imageName);
				
		Utilisateur u = new Utilisateur();
		u.setUsername(principal.getName());
		c.setUtilisateur(u);
		c.setInsertAt(new Date());
		candidatRepository.save(c);
		model.addAttribute("success", "L'enregistrement des informations du candidat a réussi!!!");
		
		String uploadDir = "cv/"+c.getId();
		FileUploadUtil.saveFile(uploadDir, fileName, file);
		
		String uploadDirect = "profil/"+c.getId();
		FileUploadUtil.saveFile(uploadDirect, imageName, files);
		
		return "redirect:/critere";
	}
		
	@RequestMapping(value="/critere", method=RequestMethod.GET)
	public String criteres(Model model) {
		List<Region> regions = (List<Region>) regionRepository.findAll();
		List<Metiers> metiers = (List<Metiers>) metierRepository.findAll();
		List<Secteurs> secteurs = (List<Secteurs>) secteurRepository.findAll();
		model.addAttribute("regions", regions);
		model.addAttribute("metiers", metiers);
		model.addAttribute("secteurs", secteurs);
		model.addAttribute("critere", new Critere());
		return "critere";
	}
	
	@RequestMapping(value="/saveCritere", method=RequestMethod.POST)
	public String saveCritere(@Valid Critere c, Principal principal, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "critere";
		}
		
		Utilisateur u = utilisateurRepository.findByUsername(principal.getName());
		Candidat a = candidatRepository.findByUtilisateur(u);
		c.setCandidat(a);
		critereRepository.save(c);
		
		return "redirect:/profil";
	}
	
	@RequestMapping(value="/suivi", method=RequestMethod.GET)
	public String suivi(Principal principal, Model model) {
		String username = principal.getName();
		Utilisateur u = utilisateurRepository.findByUsername(username);
		Candidat c = candidatRepository.findByUtilisateur(u);
		if(c == null) {
			return "redirect:/complement";
		}
		List<Suivi> s = suiviRepository.findByCandidat(c);
		model.addAttribute("candidat", c);
		model.addAttribute("suivi", s);
		return "suivi";
	}
	
	@RequestMapping(value="/deleteSuivi")
	public String supprimerSuivi(Long idSuivi) {
		suiviRepository.deleteById(idSuivi);
		return "redirect:/suivi";
	}
	
	@RequestMapping(value="/profil", method=RequestMethod.GET)
	public String profil(Principal principal, Model model) {
		String username = principal.getName();
		Utilisateur u = utilisateurRepository.findByUsername(username);
		Candidat c = candidatRepository.findByUtilisateur(u);
		Critere r = critereRepository.findByCandidat(c);
		if(c == null  || r == null) {
			return "redirect:/info";
		}
		
		model.addAttribute("regions", r.getRegions());
		model.addAttribute("metiers", r.getMetiers());
		model.addAttribute("secteurs", r.getSecteurs());
		model.addAttribute("formations", c.getFormations());
		model.addAttribute("experiences", c.getExperiences());
		model.addAttribute("candidat", c);
		model.addAttribute("critere", r);
		return "profil";
	}
	
	@RequestMapping(value="/profilParCandidat", method=RequestMethod.GET)
	public String profilCandidat(Long idCandidat, Model model) {
		Candidat c = candidatRepository.findById(idCandidat)
				.orElseThrow(() -> new ResourceNotFoundException("Le profil du candidat n°",idCandidat," est  introuvable ou inexistant."));
		Critere s = critereRepository.findByCandidat(c);
		model.addAttribute("candidat", c);
		model.addAttribute("critere", s);
		model.addAttribute("regions", s.getRegions());
		model.addAttribute("metiers", s.getMetiers());
		model.addAttribute("secteurs", s.getSecteurs());
		model.addAttribute("formations", c.getFormations());
		model.addAttribute("experiences", c.getExperiences());
		return "profilCandidat";
	}
	
	@RequestMapping(value="/editProfil", method=RequestMethod.GET)
	public String editeProfil(Long idCandidat, Model model) {
		Candidat c = candidatRepository.findById(idCandidat)
				.orElseThrow(() -> new ResourceNotFoundException("Le profil du candidat n°",idCandidat," est  introuvable ou inexistant."));
		model.addAttribute("candidat", c);
		return "editProfil";
	}
	
	@RequestMapping(value="/updateProfil", method=RequestMethod.POST)
	public String updateCandidat(@Valid Candidat c, Principal principal, BindingResult bindingResult, @RequestParam(name="file") MultipartFile file, @RequestParam(name="picture") MultipartFile files) throws Exception {
		if(bindingResult.hasErrors()) {
			return "editProfil";
		}
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		c.setCv(fileName);
		
		String imageName = StringUtils.cleanPath(file.getOriginalFilename());
		c.setPhoto(imageName);
		
		Utilisateur u = new Utilisateur();
		u.setUsername(principal.getName());
		c.setUtilisateur(u);
		candidatRepository.save(c);
		
		String uploadDir = "cv/"+c.getId();
		FileUploadUtil.saveFile(uploadDir, fileName, file);
		
		String uploadDirect = "profil/"+c.getId();
		FileUploadUtil.saveFile(uploadDirect, fileName, file);
		
		return "redirect:/profil";
	}
	
	@RequestMapping(value="/signin", method=RequestMethod.POST)
	public String signin(@Valid Newletters n, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "index";
		}
		
		n.setDate_inscription(new Date());
		newlettersRepository.save(n);
		return "redirect:/index";
	}
	
	@PreDestroy
	public void destroy() {
		System.out.println("Destuction du contrôleur des emplois");
	}
}
