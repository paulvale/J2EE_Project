package dao;

import java.util.Map;

import beans.Answer;
import beans.Resultat;

public interface ResultDao {
	//C'est pour conserver les r®¶ponses de l'utilisateur
	void create(Answer reponse) throws DAOException;
		
	//C'est pour conserver le score d'une questionnaire de l'utilisateur
	void conserveScore(float score, Long lId) throws DAOException;
    
	//C'est pour trouver tous les scores de questionnaires que l'utilisateur est d®®j®§ finit.
    public Map<Long,Resultat> listerScore() throws DAOException;
}
