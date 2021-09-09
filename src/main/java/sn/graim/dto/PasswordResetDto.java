package sn.graim.dto;

import javax.validation.constraints.NotEmpty;

//@FieldMatch(first = "password", second = "confirmPassword", message = "Les deux mots de passe sont différents")
public class PasswordResetDto {
	@NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;
    @NotEmpty
    private String token;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
