package sn.graim.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Newletters implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attribut de la class Newletters
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Email
	@Column(name="email", length=254, nullable=false, unique=true)
	private String email;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date date_inscription;
	
	/**
	 * Constructeurs de la class Newletters
	 */
	public Newletters() {
		super();
	}

	public Newletters(String email, Date date_inscription) {
		super();
		this.email = email;
		this.date_inscription = date_inscription;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDate_inscription() {
		return date_inscription;
	}

	public void setDate_inscription(Date date_inscription) {
		this.date_inscription = date_inscription;
	}	
}
