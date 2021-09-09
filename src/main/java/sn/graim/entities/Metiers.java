package sn.graim.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Metiers implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attributs de la class metiers
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_metier")
	private Long id;
	@Column(name = "nom_metier", length = 254, nullable = false, unique = true)
	private String nom;
	/**
	 * Relation entre Metiers et Secteurs
	 */
	@ManyToOne
	@JoinColumn(name="id_secteur", nullable=false)
	private Secteurs secteur;
	/**
	 * Relation entre Metiers et Offres
	 */
	@OneToMany(mappedBy="metiers", fetch = FetchType.LAZY)
	private Collection<Offres> offres;
	
	/**
	 * Constructeurs de la class metiers
	 */
	public Metiers() {
		super();
	}

	public Metiers(String nom, Secteurs secteur) {
		super();
		this.nom = nom;
		this.secteur = secteur;
	}

	public Metiers(String nom) {
		super();
		this.nom = nom;
	}

	/**
	 * Generation des getters et setters
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Secteurs getSecteur() {
		return secteur;
	}

	public void setSecteur(Secteurs secteur) {
		this.secteur = secteur;
	}

	@JsonIgnore
	public Collection<Offres> getOffres() {
		return offres;
	}

	public void setOffres(Collection<Offres> offres) {
		this.offres = offres;
	}

	@Override
	public String toString() {
		return "Metiers [id=" + id + ", nom=" + nom + ", secteur=" + secteur + ", offres=" + offres + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Metiers other = (Metiers) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
