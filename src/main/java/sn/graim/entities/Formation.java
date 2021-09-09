package sn.graim.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.core.style.ToStringCreator;

@Entity
public class Formation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attributs de la class Formation
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="debut_formation", nullable=false)
	private String debut;
	@Column(name="fin_formation", nullable=false)
	private String fin;
	@Column(name="titre", nullable=false)
	private String titre;
	@Column(name="etablissement", nullable=false)
	private String etablissement;
	@Column(name="description", length=5000, nullable=false)
	private String description;
		
	/**
	 * Constructeurs de la class Formation
	 */
	public Formation() {
		super();
	}
	
	public Formation(String debut, String fin, String titre, String etablissement, String description) {
		super();
		this.debut = debut;
		this.fin = fin;
		this.titre = titre;
		this.etablissement = etablissement;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public String getDebut() {
		return debut;
	}

	public String getFin() {
		return fin;
	}

	public String getTitre() {
		return titre;
	}

	public String getEtablissement() {
		return etablissement;
	}

	public String getDescription() {
		return description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDebut(String debut) {
		this.debut = debut;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public void setEtablissement(String etablissement) {
		this.etablissement = etablissement;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("id", this.getId())
				.append("debut", this.getDebut())
				.append("fin", this.getFin())
				.append("titre", this.getTitre())
				.append("etablissement", this.getEtablissement())
				.append("description", this.getDescription())
				.toString();
	}
}
