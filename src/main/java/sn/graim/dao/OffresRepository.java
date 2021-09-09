package sn.graim.dao;

import sn.graim.entities.Offres;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OffresRepository extends JpaRepository<Offres, Long> {
	@Query("select o from Offres o where o.titre like :x")
	public Page<Offres> rechercheParTitre(@Param("x") String mc, Pageable pageable);
	@Query("select count(o) from Offres o")
	public Long nombreOffres();
	@Query("select o from Offres o where o.metiers.secteur.nom = :x")
	public List<Offres> offresBySecteur(@Param("x") String nom);
	@Query("select o from Offres o where o.titre like :x and o.region.nom =:y and o.metiers.nom =:z")
	public Page<Offres> moteurRecherche(@Param("x") String titre, @Param("y") String region, @Param("z") String metier, Pageable pageable);
}
