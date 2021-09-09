package sn.graim.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.graim.entities.Metiers;

public interface MetiersRepository extends JpaRepository<Metiers, Long> {
	public Metiers findByNom(String nom);
	@Query("select m from Metiers m where m.nom like :x")
	public Page<Metiers> recherche(@Param("x") String mc, Pageable pageable);
}
