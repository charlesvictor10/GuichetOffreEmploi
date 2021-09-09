package sn.graim.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.graim.entities.Secteurs;

public interface SecteursRepository extends JpaRepository<Secteurs, Long> {
	public Secteurs findByNom(String nom);
	@Query("select s from Secteurs s where s.nom like :x")
	public Page<Secteurs> recherche(@Param("x") String mc, Pageable pageable);
}
