package sn.graim.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import sn.graim.entities.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
class RoleDaoTest {
	@Autowired
	private RoleRepository roleRepository;

	@Test
	@Rollback(false)
	@DisplayName(value = "ajoutRoleTest")
	@Order(1)
	public void testCreateRole() {
		Role r = new Role("ADMIN", "");
		Role savedRole = roleRepository.save(r);
		assertNotNull(savedRole);
	}

	@Test
	@DisplayName(value = "selectRoleExistTest")
	@Order(2)
	public void testFindRoleByNomExist() {
		String nom = "CANDIDAT";
		Role r = roleRepository.findByNom(nom);
		assertEquals(r.getNom(), nom);
	}
	
	@Test
	@DisplayName(value = "selectRoleNotExistTest")
	@Order(3)
	public void testFindRoleByNomNotExist() {
		String nom = "TESTEUR";
		Role r = roleRepository.findByNom(nom);
		assertNull(r);
	}
	
	@Test
	@Rollback(false)
	@DisplayName(value = "modifierRoleTest")
	@Order(4)
	public void testUpdateRole() {
		String description = "Chargé de l'administration de l'application";
		Role r = new Role("ADMIN", description);
		r.setNom("ADMIN");
		roleRepository.save(r);
		Role updatedRole = roleRepository.findByNom("ADMIN");
		assertEquals(updatedRole.getNom(), "ADMIN");
	}
	
	@Test
	@DisplayName(value = "listeRoleTest")
	@Order(5)
	public void testListRoles() {
		List<Role> roles = roleRepository.findAll();
		for(Role r: roles) {
			System.out.println(r);
		}
		assertNotEquals(roles.size(), 0);
	}
	
	@Test
	@Rollback(false)
	@DisplayName(value = "supprimerRoleTest")
	@Order(6)
	public void testDeleteRole() {
		String nom = "ADMIN";
		Role r = roleRepository.findByNom(nom);
		if(r != null) {
			roleRepository.delete(r);
		}
		assertNull(r);
	}
}
