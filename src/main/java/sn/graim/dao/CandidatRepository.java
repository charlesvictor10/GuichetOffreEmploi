package sn.graim.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.graim.entities.Candidat;
import sn.graim.entities.Utilisateur;

public interface CandidatRepository extends JpaRepository<Candidat, Long> {
	@Query("select c from Candidat c where c.experience like :x")
	public Page<Candidat> chercherCandidats(@Param("x") String mc, Pageable pageable);
	public Candidat findByUtilisateur(Utilisateur u);
}
