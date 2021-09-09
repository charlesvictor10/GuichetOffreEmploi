package sn.graim.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.graim.entities.Recruteur;

public interface RecruteurRepository extends JpaRepository<Recruteur, Long> {
	@Query("select r from Recruteur r where r.entreprise like :x")
	public Page<Recruteur> chercherRecruteurs(@Param("x") String e, Pageable pageable);
}
