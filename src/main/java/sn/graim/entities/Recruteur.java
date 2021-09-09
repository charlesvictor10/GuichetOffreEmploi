package sn.graim.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import sn.graim.validator.ExtendedEmailValidator;

@Entity
public class Recruteur implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attributs de la class Recruteur
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_recruteur")
	private Long id;
	@Column(name = "nom_entreprise", unique = true, nullable = false, length = 254)
	private String entreprise;
	@Column(name="adresse", nullable=false, length=300)
	private String adresse;
	@Column(name="code_postal", nullable=false, length=9)
	private String code_postal;
	/**
     * Relation entre Recruteur et Region
     */
	@ManyToOne
    @JoinColumn(name = "id_region", nullable=false)
    private Region region;
    @Column(name = "site_web", length = 254, unique = true)
	private String site_web;
    @Column(name="logo_entreprise", length = 64, nullable = true)
    private String logo;
    @Column(name = "tel_fixe", unique = true, length = 20)
    private String tel;
    @ExtendedEmailValidator
    @Column(name = "email_entreprise", unique = true, nullable = false, length = 200)
    private String email;
	/**
     * Relation entre Recruteur et Secteurs
     */
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "recruteurs_secteurs")
    private Set<Secteurs> secteurs = new HashSet<>();	
    @Column(name="description", length=5000)
	private String description;
	/**
	 * Relation entre Recruteur et Offres
	 */
	@OneToMany(mappedBy="recruteur", fetch = FetchType.LAZY)
	private Collection<Offres> offres;
		
	/**
	 * Constructeur de la class Recruteur
	 */
	public Recruteur() {
		super();
	}
	
	public Recruteur(String entreprise, String adresse, String code_postal, Region region, String site_web, String logo,
			String tel, String email, Set<Secteurs> secteurs, String description, Collection<Offres> offres) {
		super();
		this.entreprise = entreprise;
		this.adresse = adresse;
		this.code_postal = code_postal;
		this.region = region;
		this.site_web = site_web;
		this.logo = logo;
		this.tel = tel;
		this.email = email;
		this.secteurs = secteurs;
		this.description = description;
		this.offres = offres;
	}

	/**
	 * Generation des getters et setters
	 */
	public Long getId() {
		return id;
	}
	
	public String getEntreprise() {
		return entreprise;
	}

	public String getAdresse() {
		return adresse;
	}

	public String getCode_postal() {
		return code_postal;
	}

	public Region getRegion() {
		return region;
	}

	public String getSite_web() {
		return site_web;
	}

	public String getLogo() {
		return logo;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Set<Secteurs> getSecteurs() {
		return secteurs;
	}

	public void setSecteurs(Set<Secteurs> secteurs) {
		this.secteurs = secteurs;
	}

	public String getDescription() {
		return description;
	}

	public void setEntreprise(String entreprise) {
		this.entreprise = entreprise;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public void setSite_web(String site_web) {
		this.site_web = site_web;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTel() {
		return tel;
	}

	public String getEmail() {
		return email;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@JsonIgnore
	public Collection<Offres> getOffres() {
		return offres;
	}

	public void setOffres(Collection<Offres> offres) {
		this.offres = offres;
	}	
	
	@Transient
	public String getLogoPath() {
		if(logo == null || id == null)
			return null;
		
		return "/logo_entreprise/"+id+"/"+logo;
	}
}
