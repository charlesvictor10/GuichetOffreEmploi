package sn.graim.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.core.style.ToStringCreator;

@Entity
public class Experience implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attributs de la class Experience
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="debut_experience", nullable=false)
	private String debut;
	@Column(name="fin_experience", nullable=false)
	private String fin;
	@Column(name="intitule", nullable=false)
	private String intitule;
	@Column(name="entreprise", nullable=false)
	private String entreprise;
	@Column(name="description", length=5000, nullable=false)
	private String description;
	
	/**
	 * Constructeurs de la class Experience
	 */
	public Experience() {
		super();
	}
	
	public Experience(String debut, String fin, String intitule, String entreprise, String description) {
		super();
		this.debut = debut;
		this.fin = fin;
		this.intitule = intitule;
		this.entreprise = entreprise;
		this.description = description;
	}
	
	/**
	 * Genertaion des getters et setters
	 * @return
	 */
	public Long getId() {
		return id;
	}
	
	public String getDebut() {
		return debut;
	}
	
	public String getFin() {
		return fin;
	}
	
	public String getIntitule() {
		return intitule;
	}
	
	public String getEntreprise() {
		return entreprise;
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
	
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	
	public void setEntreprise(String entreprise) {
		this.entreprise = entreprise;
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
				.append("intitule", this.getIntitule())
				.append("entreprise", this.getEntreprise())
				.append("description", this.getDescription())
				.toString();
	}
}
