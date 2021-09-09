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

import sn.graim.entities.Metiers;
import sn.graim.entities.Secteurs;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
class MetiersDaoTest {
	@Autowired
	private SecteursRepository secteursRepository;
	@Autowired
	private MetiersRepository metiersRepository;

	@Test
	@Rollback(false)
	@DisplayName(value = "ajoutMetierTest")
	@Order(1)
	public void testCreateMetier() {
		Long idSec = 1L;
		Secteurs secteur = secteursRepository.getOne(idSec);
		Metiers metier = new Metiers("Analyste", secteur);
		Metiers savedMetier = metiersRepository.save(metier);
		assertNotNull(savedMetier);
	}

	@Test
	@DisplayName(value = "selectMetierExistTest")
	@Order(2)
	public void testFindMetierByNameExist() {
		String nom = "Analyste";
		Metiers m = metiersRepository.findByNom(nom);
		assertEquals(m.getNom(), nom);
	}
	
	@Test
	@DisplayName(value = "selectMetierNotExistTest")
	@Order(3)
	public void testFindMetierByNameNotExist() {
		String nom = "Administrateur base de données";
		Metiers m = metiersRepository.findByNom(nom);
		assertNull(m);
	}
	
	@Test
	@Rollback(false)
	@DisplayName(value = "modifierMetierTest")
	@Order(4)
	public void testUpdateMetier() {
		String nom = "Juriste";
		Metiers m = new Metiers(nom);
		m.setId(1L);
		metiersRepository.save(m);
		Metiers updateMetier = metiersRepository.findByNom(nom);
		assertEquals(updateMetier.getNom(), nom);
	}
	
	@Test
	@DisplayName(value = "listeMetiersTest")
	@Order(5)
	public void testListMetiers() {
		List<Metiers> metiers = (List<Metiers>) metiersRepository.findAll();
		for(Metiers m: metiers) {
			System.out.println(m);
		}
		
		assertNotEquals(metiers.size(), 0);
	}
	
	@Test
	@Rollback(false)
	@DisplayName(value = "supprimerMetierTest")
	@Order(6)
	public void testDeleteMetier() {
		Long id = 1L;
		boolean isExistBeforeDelete = metiersRepository.findById(id).isPresent();
		metiersRepository.deleteById(id);
		boolean notExistBeforeDelete = metiersRepository.findById(id).isPresent();
		assertTrue(isExistBeforeDelete);
		assertFalse(notExistBeforeDelete);
	}
}
