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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Region implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attributs de la class Region
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_region")
	private Long id;
	@Column(name = "nom_region", length = 200, nullable = false, unique = true)
	private String nom;
    /**
     * Relation entre Region et Recruteur
     */
    @OneToMany(mappedBy="region", fetch = FetchType.LAZY)
    private Collection<Recruteur> recruteurs;
    /**
     * Relation entre Region et Offres
     */
    @OneToMany(mappedBy="region", fetch = FetchType.LAZY)
    private Collection<Offres> offres;
	
	/**
	 * Constructeurs de la class Region
	 */
	public Region() {
		super();
	}
	
	/**
	 * Generation des getters et setters
	 */
	public Region(String nom) {
		super();
		this.nom = nom;
	}

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
	
	@JsonIgnore
	public Collection<Recruteur> getRecruteurs() {
		return recruteurs;
	}

	public void setRecruteurs(Collection<Recruteur> recruteurs) {
		this.recruteurs = recruteurs;
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
		return "Region [id=" + id + ", nom=" + nom + "]";
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
		Region other = (Region) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
