package sn.graim.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.graim.entities.Candidat;
import sn.graim.entities.Suivi;

public interface SuiviRepository extends JpaRepository<Suivi, Long> {
	public List<Suivi> findByCandidat(Candidat c);
}
