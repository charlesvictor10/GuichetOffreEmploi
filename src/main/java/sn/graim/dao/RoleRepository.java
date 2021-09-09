package sn.graim.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.graim.entities.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
	public Role findByNom(String nom);
	@Query("select r from Role r where r.nom = :x or r.nom = :y")
	public List<Role> listeRole(@Param("x") String x, @Param("y") String y);
}
