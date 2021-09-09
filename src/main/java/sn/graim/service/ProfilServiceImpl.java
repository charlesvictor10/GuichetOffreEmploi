package sn.graim.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.graim.dao.CandidatRepository;
import sn.graim.entities.Candidat;
import sn.graim.entities.Experience;
import sn.graim.entities.Formation;

@Service
public class ProfilServiceImpl implements ProfilService {
	@Autowired
	private CandidatRepository candidatRepository;

	@Override
	public Candidat newCandidat() {
		return new Candidat();
	}

	@Override
	public Candidat saveCandidat(Candidat c) {
		return candidatRepository.save(c);
	}

	@Override
	public void addFormation(Candidat c) {
		c.getFormations().add(new Formation());
	}

	@Override
	public void addExperience(Candidat c) {
		c.getExperiences().add(new Experience());
	}

	@Override
	public void removeFormation(Candidat c, Integer formationIndex) {
		c.getFormations().remove(formationIndex.intValue());
	}

	@Override
	public void removeExperience(Candidat c, Integer experienceIndex) {
		c.getExperiences().remove(experienceIndex.intValue());
	}
}
