package sn.graim.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class PasswordForgotDto {
	@Email
	@NotEmpty
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
