package sn.graim.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.graim.entities.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
	public Region findByNom(String nom);
	@Query("select r from Region r where r.nom like :x")
	public Page<Region> recherche(@Param("x") String mc, Pageable pageable);
}
