package sn.graim.service;

import sn.graim.entities.Candidat;

public interface ProfilService {
	public Candidat newCandidat();
	public Candidat saveCandidat(Candidat c);
	public void addFormation(Candidat c);
	public void addExperience(Candidat c);
	public void removeFormation(Candidat c, Integer formationIndex);
	public void removeExperience(Candidat c, Integer experienceIndex);
}
