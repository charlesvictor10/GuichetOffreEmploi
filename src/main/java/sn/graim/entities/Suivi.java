package sn.graim.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Suivi implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Attribut de la class Suivi
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titre;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date envoie;
	private String statut;
	/**
	 * Relation entre Suivi et Utilisateur
	 */
	@ManyToOne
	@JoinColumn(name="id_candidat", nullable=false)
	private Candidat candidat;
	
	/**
	 * Constructeurs de la class Suivi
	 */
	public Suivi() {
		super();
	}

	public Suivi(String titre, Date envoie, String statut, Candidat candidat) {
		super();
		this.titre = titre;
		this.envoie = envoie;
		this.statut = statut;
		this.candidat = candidat;
	}

	/**
	 * Generation des getters et setters
	 * @return
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Date getEnvoie() {
		return envoie;
	}

	public void setEnvoie(Date envoie) {
		this.envoie = envoie;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public Candidat getCandidat() {
		return candidat;
	}

	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}
}
