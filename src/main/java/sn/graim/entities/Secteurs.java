package sn.graim.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Secteurs implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attributs de la class Secteurs
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_secteur")
	private Long id;
	@Column(name = "nom_secteur", length = 254, nullable = false, unique = true)
	private String nom;
	@Column(name="photo", nullable=false, length = 64)
	private String photo;
	/**
	 * Relation entre Secteurs et Metiers
	 */
	@OneToMany(mappedBy="secteur", fetch = FetchType.LAZY)
	private Collection<Metiers> metiers;
	
	/**
	 * Constructeurs de la class Secteurs
	 */
	public Secteurs() {
		super();
	}

	public Secteurs(String nom, String photo) {
		super();
		this.nom = nom;
		this.photo = photo;
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
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@JsonIgnore
	public Collection<Metiers> getMetiers() {
		return metiers;
	}

	public void setMetiers(Collection<Metiers> metiers) {
		this.metiers = metiers;
	}
	
	@Transient
	public String getPhotoPath() {
		if(photo == null || id == null)
			return null;
		
		return "/logo_secteur/"+id+"/"+photo;
	}

	@Override
	public String toString() {
		return "Secteurs [nom=" + nom + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
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
		Secteurs other = (Secteurs) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}	
}
