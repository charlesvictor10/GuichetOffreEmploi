package sn.graim.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.graim.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {
	public Utilisateur findByUsername(String username);
	@Modifying
    @Query("update Utilisateur u set u.password = :password where u.username = :username")
    void updatePassword(@Param("password") String password, @Param("username") String username);
}
