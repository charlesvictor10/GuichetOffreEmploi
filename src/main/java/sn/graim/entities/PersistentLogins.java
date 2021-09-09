package sn.graim.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class PersistentLogins implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attribut de la class PersistentLogins
	 */
	@Id
	private String series;
	private String username;
	private String token;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime lastUsed;
	
	/**
	 * Constructeurs de la class PersistentLogins
	 */
	public PersistentLogins() {
		super();
	}

	public PersistentLogins(String series, String username, String token, LocalDateTime lastUsed) {
		super();
		this.series = series;
		this.username = username;
		this.token = token;
		this.lastUsed = lastUsed;
	}

	/**
	 * Generation des getters et setters
	 * @return
	 */
	public String getSeries() {
		return series;
	}

	public String getUsername() {
		return username;
	}

	public String getToken() {
		return token;
	}

	public LocalDateTime getLastUsed() {
		return lastUsed;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setLastUsed(LocalDateTime lastUsed) {
		this.lastUsed = lastUsed;
	}
}
