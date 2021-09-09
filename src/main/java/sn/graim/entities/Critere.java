package sn.graim.entities;

import java.io.Serializable;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Critere implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attributs de la class Critere
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="disponibilite", nullable=false)
	private String disponibilite;
	@Column(name="type_contrat", nullable=false)
	private String type_contrat;
	@Column(name="renumeration", nullable=false)
	private String renumeration;
	/** 
	 * Relation entre Critere et Metiers
	 */
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name="criteres_metiers", nullable=false)
	private Set<Metiers> metiers = new HashSet<Metiers>(); 
	/** 
	 * Relation entre Critere et Secteurs
	 */
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name="criteres_secteurs", nullable=false)
	private Set<Secteurs> secteurs = new HashSet<Secteurs>(); 
	/** 
	 * Relation entre Critere et Regions
	 */
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name="criteres_regions", nullable=false)
	private Set<Region> regions = new HashSet<Region>(); 
	/**
	 * Relation entre Critere et Candidat
	 */
	@ManyToOne
	@JoinColumn(name="id_candidat", nullable=false)
	private Candidat candidat;
	
	/**
	 * Constructeurs de la class Critere
	 */
	public Critere() {
		super();
	}

	public Critere(String disponibilite, String type_contrat, String renumeration, Set<Metiers> metiers,
			Set<Secteurs> secteurs, Set<Region> regions) {
		super();
		this.disponibilite = disponibilite;
		this.type_contrat = type_contrat;
		this.renumeration = renumeration;
		this.metiers = metiers;
		this.secteurs = secteurs;
		this.regions = regions;
	}

	/**
	 * Generation des getters et setters
	 * @return
	 */
	public Long getId() {
		return id;
	}

	public String getDisponibilite() {
		return disponibilite;
	}

	public String getType_contrat() {
		return type_contrat;
	}

	public String getRenumeration() {
		return renumeration;
	}

	public Set<Metiers> getMetiers() {
		return metiers;
	}

	public Set<Secteurs> getSecteurs() {
		return secteurs;
	}

	public Set<Region> getRegions() {
		return regions;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDisponibilite(String disponibilite) {
		this.disponibilite = disponibilite;
	}

	public void setType_contrat(String type_contrat) {
		this.type_contrat = type_contrat;
	}

	public void setRenumeration(String renumeration) {
		this.renumeration = renumeration;
	}

	public void setMetiers(Set<Metiers> metiers) {
		this.metiers = metiers;
	}

	public void setSecteurs(Set<Secteurs> secteurs) {
		this.secteurs = secteurs;
	}

	public void setRegions(Set<Region> regions) {
		this.regions = regions;
	}

	public Candidat getCandidat() {
		return candidat;
	}

	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}
}
