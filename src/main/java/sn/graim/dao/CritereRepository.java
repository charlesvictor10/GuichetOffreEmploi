package sn.graim.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.graim.entities.Candidat;
import sn.graim.entities.Critere;

public interface CritereRepository extends JpaRepository<Critere, Long> {
	public Critere findByCandidat(Candidat c);
}
