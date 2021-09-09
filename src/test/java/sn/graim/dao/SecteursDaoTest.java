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

import sn.graim.entities.Secteurs;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
class SecteursDaoTest {
	@Autowired
	private SecteursRepository secteursRepository;
	
	@Test
	@Rollback(false)
	@DisplayName(value = "ajoutSecteurTest")
	@Order(1)
	public void testCreateSecteur() {
		Secteurs s = new Secteurs("Aéronotique",null);
		Secteurs savedSecteur = secteursRepository.save(s);
		assertNotNull(savedSecteur);
	}

	@Test
	@DisplayName(value = "selectSecteurExistTest")
	@Order(2)
	public void testFindSecteurByNameExist() {
		String nom = "Aéronotique";
		Secteurs s = secteursRepository.findByNom(nom);
		assertEquals(s.getNom(), nom);
	}
	
	@Test
	@DisplayName(value = "selectSecteurNotExistTest")
	@Order(3)
	public void testFindSecteurByNameNotExist() {
		String nom = "Comptabilité";
		Secteurs s = secteursRepository.findByNom(nom);
		assertNull(s);
	}
	
	@Test
	@Rollback(false)
	@DisplayName(value = "modifierSecteurTest")
	@Order(4)
	public void testUpdateSecteur() {
		String nom = "Electrique";
		Secteurs s = new Secteurs(nom,null);
		s.setId(2L);
		secteursRepository.save(s);
		Secteurs updateSecteur = secteursRepository.findByNom(nom);
		assertEquals(updateSecteur.getNom(), nom);
	}
	
	@Test
	@DisplayName(value = "listeSecteursTest")
	@Order(5)
	public void testListSecteurs() {
		List<Secteurs> secteurs = (List<Secteurs>) secteursRepository.findAll();
		for(Secteurs s: secteurs) {
			System.out.println(s);
		}
		
		assertNotEquals(secteurs.size(), 0);
	}
	
	@Test
	@Rollback(false)
	@DisplayName(value = "supprimerSecteurTest")
	@Order(6)
	public void testDeleteSecteur() {
		Long id = 2L;
		boolean isExistBeforeDelete = secteursRepository.findById(id).isPresent();
		secteursRepository.deleteById(id);
		boolean notExistBeforeDelete = secteursRepository.findById(id).isPresent();
		assertTrue(isExistBeforeDelete);
		assertFalse(notExistBeforeDelete);
	}
}
