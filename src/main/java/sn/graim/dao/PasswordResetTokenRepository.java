package sn.graim.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.graim.entities.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	public PasswordResetToken findByToken(String token);
}
