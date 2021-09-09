package sn.graim.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class PasswordResetToken implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Attributs de la class PasswordResetToken
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String token;
	@OneToOne(targetEntity = Utilisateur.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "username", nullable = false)
	private Utilisateur utilisateur;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date expiryDate;
	
	/**
	 * Constructeurs de la class PasswordResetToken
	 */
	public PasswordResetToken() {
		super();
	}

	public PasswordResetToken(String token, Utilisateur utilisateur) {
		super();
		this.token = token;
		this.utilisateur = utilisateur;
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
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	public Date getExpiryDate() {
		return expiryDate;
	}
	
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public void setExpiryDate(int heures) {
		Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR, heures);
        this.expiryDate = now.getTime();
	}
	
	public boolean isExpired() {
		return new Date().after(this.expiryDate);
	}
}
