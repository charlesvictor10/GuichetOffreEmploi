package sn.graim.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.style.ToStringCreator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Candidat implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attributs de la class Candidat
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_candidat")
	private Long id;
	@Column(name="cv", nullable=false, length=64)
	private String cv;
	@Column(name="photo", nullable=false, length=64)
	private String photo;
	@Column(name="etude", nullable=false)
	private String etude;
	@Column(name="experience", nullable=false)
	private String experience;
	@Column(name="langues", nullable=false)
	@Value("#{'${listOfCharacters}'.split(',')}")
	private String[] langue;
	@Column(name="niveaux_langues", nullable=false)
	@Value("#{'${listOfCharacters}'.split(',')}")
	private String[] niveauLangue;
	@Column(name="date_creation", nullable=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@CreatedDate
	private Date insertAt;
	@Column(name="date_modification")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@LastModifiedDate
	private Date updatedAt;
	/**
	 * Relation entre Candidat et Utilisateur
	 */
	@OneToOne
	@JoinColumn(name="username")
	private Utilisateur utilisateur;
	/**
	 * Relation entre Candidat et Formation
	 */
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="id_candidat", nullable=false)
	private List<Formation> formations = new ArrayList<>();
	/**
	 * Relation entre Candidat et Experience
	 */
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="id_candidat", nullable=false)
	private List<Experience> experiences = new ArrayList<>();
	/**
	 * Relation entre Candidat et Critere
	 */
	@OneToMany(mappedBy="candidat", cascade=CascadeType.ALL)
	private Set<Critere> criteres;
	/**
	 * Relation entre Candidat et Suivi
	 */
	@OneToMany(mappedBy="candidat", cascade=CascadeType.ALL)
	private Set<Suivi> suivis;
		
	/**
	 * Constructeurs de la class Candidat
	 */
	public Candidat() {
		super();
	}
	
	public Candidat(String cv, String photo, String etude, String experience, String[] langue, String[] niveauLangue) {
		super();
		this.cv = cv;
		this.photo = photo;
		this.etude = etude;
		this.experience = experience;
		this.langue = langue;
		this.niveauLangue = niveauLangue;
	}

	/**
	 * Generation des getters et setters
	 */
	public Long getId() {
		return id;
	}

	public String getCv() {
		return cv;
	}

	public String getPhoto() {
		return photo;
	}

	public String getEtude() {
		return etude;
	}

	public String getExperience() {
		return experience;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public Date getInsertAt() {
		return insertAt;
	}

	public void setInsertAt(Date insertAt) {
		this.insertAt = insertAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setEtude(String etude) {
		this.etude = etude;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String[] getLangue() {
		return langue;
	}

	public String[] getNiveauLangue() {
		return niveauLangue;
	}

	public void setLangue(String[] langue) {
		this.langue = langue;
	}

	public void setNiveauLangue(String[] niveauLangue) {
		this.niveauLangue = niveauLangue;
	}

	public List<Formation> getFormations() {
		return formations;
	}

	public void setFormations(List<Formation> formations) {
		this.formations = formations;
	}

	public List<Experience> getExperiences() {
		return experiences;
	}

	public void setExperiences(List<Experience> experiences) {
		this.experiences = experiences;
	}

	public Set<Critere> getCriteres() {
		return criteres;
	}

	public void setCriteres(Set<Critere> criteres) {
		this.criteres = criteres;
	}

	public Set<Suivi> getSuivis() {
		return suivis;
	}

	public void setSuivis(Set<Suivi> suivis) {
		this.suivis = suivis;
	}
	
	@Transient
	public String getPhotoPath() {
		if(photo == null || id == null)
			return null;
		
		return "/profil/"+id+"/"+photo;
	}
	
	@Transient
	public String getCvPath() {
		if(cv == null || id == null)
			return null;
		
		return "/cv/"+id+"/"+cv;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("id", this.getId())
				.append("cv", this.getCv())
				.append("photo", this.getPhoto())
				.append("etude", this.getEtude())
				.append("experience", this.getExperience())
				.append("langue", this.getLangue())
				.append("niveauLangue", this.getNiveauLangue())
				.append("insertAt", this.getInsertAt())
				.append("updatedAt", this.getUpdatedAt())
				.toString();
	}
}
