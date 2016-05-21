package forms;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beans.Answer;
import beans.Result;

import dao.ResultDao;

public class ResultForm {

	public static final String 	CONF_DAO_FACTORY 		= "daofactory";
	private static final String STATUSVALUE    			= "active";
    private static final String VALIDITYVALUE		 	= "valide";
    
    private String				m_sResult;
    private Map<String, String> m_mapErrors 			= new HashMap<String, String>();
    private ResultDao m_resultDao;
    
    
    
    public ResultForm(  ) {
    }
    
    public ResultForm( ResultDao resultDao ) {
    	m_resultDao = resultDao;
    }
    
    public String getResult() {
        return m_sResult;
    }
	
	private void setErrors(String sField, String sMessage){
    	m_mapErrors.put(sField, sMessage);
    }
    public Map<String, String> getErrors(){
    	return m_mapErrors;
    }
    
    
    public void createResult(List<Answer> reponses, Long lId_Utilisateur){
    	Answer reponse = new Answer();
    	List<Result> results = new ArrayList<Result>();
    	Result result = new Result();
    	
    	for (int i=0; i < reponses.size(); i++){
    		reponse=reponses.get(i);
    		m_resultDao.create(reponse, lId_Utilisateur);	
    	}
    	//return results;
    }
    
    public float calculResultat(List<Answer> reponses, Long lId, Long lId_Utilisateur){
    	float total = reponses.size();
    	float correct = 0;
    	float faux    = 0;
    	float score = 0;
    	Long lId_survey =lId;
    	
    	//Utiliser une boucle pour parcourir la liste reponses et calculer le nombre de r®¶ponse correcte.
    	for(int i=0; i<reponses.size(); i++){
    		if(reponses.get(i).getValide().equals(VALIDITYVALUE)){
    			correct=correct+1;
    		} else {
    			faux=faux+1;
    		}
    	}
    	score=(correct/total)*100;
    	m_resultDao.conserveScore(score, lId_survey, lId_Utilisateur);
    	
    	return score;
    }
    
}
