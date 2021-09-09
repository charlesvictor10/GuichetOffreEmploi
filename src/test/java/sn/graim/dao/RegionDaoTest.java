package sn.graim.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import sn.graim.entities.Region;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
class RegionDaoTest {
	@Autowired
	private RegionRepository regionrepository;

	@Test
	@Rollback(false)
	@DisplayName(value = "ajoutRegionTest")
	@Order(1)
	public void testCreateRegion() {
		Region r = new Region("Dakar");
		Region savedRegion = regionrepository.save(r);
		assertNotNull(savedRegion);
	}

	@Test
	@DisplayName(value = "selectRegionExistTest")
	@Order(2)
	public void testFindRegionByNameExist() {
		String nom = "Thiès";
		Region r = regionrepository.findByNom(nom);
		assertEquals(r.getNom(), nom);
	}
	
	@Test
	@DisplayName(value = "selectRegionNotExistTest")
	@Order(3)
	public void testFindRegionByNameNotExist() {
		String nom = "Saint Louis";
		Region r = regionrepository.findByNom(nom);
		assertNull(r);
	}
	
	@Test
	@Rollback(false)
	@DisplayName(value = "modifierRegionTest")
	@Order(4)
	public void testUpdateRegion() {
		String nom = "Kaolack";
		Region r = new Region(nom);
		r.setId(1L);
		regionrepository.save(r);
		Region updateRegion = regionrepository.findByNom(nom);
		assertEquals(updateRegion.getNom(), nom);
	}
	
	@Test
	@DisplayName(value = "listeRegionsTest")
	@Order(5)
	public void testListRegions() {
		List<Region> regions = (List<Region>) regionrepository.findAll();
		for(Region r: regions) {
			System.out.println(r);
		}
		
		assertNotEquals(regions.size(), 0);
	}
	
	@Test
	@Rollback(false)
	@DisplayName(value = "supprimerRegionTest")
	@Order(6)
	public void testDeleteRegion() {
		Long id = 1L;
		boolean isExistBeforeDelete = regionrepository.findById(id).isPresent();
		regionrepository.deleteById(id);
		boolean notExistBeforeDelete = regionrepository.findById(id).isPresent();
		assertTrue(isExistBeforeDelete);
		assertFalse(notExistBeforeDelete);
	}
}
