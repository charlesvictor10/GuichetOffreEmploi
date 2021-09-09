package sn.graim.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Role implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attributs de la class Role
	 */
	@Id
	@Column(name = "nom_role", length = 200, unique = true, nullable = false)
	private String nom;
	@Column(name = "description", length = 5000)
	private String description;
	
	/**
	 * Constructeurs de la class Role
	 */
	public Role() {
		super();
	}

	public Role(String nom, String description) {
		super();
		this.nom = nom;
		this.description = description;
	}

	/**
	 * Generation des getters et setters
	 */
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Role [nom=" + nom + ", description=" + description + "]";
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
		Role other = (Role) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}
}
