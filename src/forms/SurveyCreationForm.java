package forms;

import beans.Survey;
import beans.Question;
import beans.Answer;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;

import dao.SurveyDao;
import dao.DAOException;

public class SurveyCreationForm 
{
	private static final String SUBJECTFIELD       	= "subjectField";
	private static final String QUESTIONFIELD      	= "questionField";
	private static final String ANSWERFIELD        	= "answerField";
	private static final String STATUSFIELD    		= "statusField";
	private static final String IDFIELD       		= "idField";
	private static final String DATEFIELD       	= "dateField";
    
    private static final String STATUSVALUE    		= "active";
	
	private static final String SURVEYPARAMETER    	= "surveyParam";
	
	private static final String SESSION_SURVEY		= "sessionSurvey";
			
	private String m_sResult;
	private Map<String, String> m_mapErrors 	   	= new HashMap<String, String>();
	private SurveyDao m_surveyDao;
	
	public SurveyCreationForm(  ) 
	{

    }
	
	public SurveyCreationForm( SurveyDao surveyDao ) 
	{
        m_surveyDao = surveyDao;
    }
	
	public Survey setNewSurvey(HttpServletRequest request)
	{
		String sSubject = getFieldValue( request, SUBJECTFIELD );
		String sStatus 	= getFieldValue( request, STATUSFIELD );
		String sId 	= getFieldValue( request, IDFIELD );
		Long lId = 0L;
		if(sId != null)
		{
			lId = Long.parseLong(sId);
		}

		Survey newSurvey = new Survey();
		
		try 
		{
	        subjectValidation( sSubject );
	    } 
		catch ( Exception e ) 
		{
			System.out.println(e.getMessage());
	        setErrors( SUBJECTFIELD, e.getMessage() );
	    }
		newSurvey.setSubject(sSubject);
		
		boolean bStatus = false; 
		if(sStatus.equals(STATUSVALUE))
			bStatus = true;
		newSurvey.setActive(bStatus);
		
		newSurvey.setId(lId);
		
		DateTime dtCreationDate = new DateTime();
		newSurvey.setCreationDate(dtCreationDate);
		
		return newSurvey;
	}
	
	public Survey createSurvey(HttpServletRequest request)
	{
		Survey newSurvey = setNewSurvey(request);
		
		if ( m_mapErrors.isEmpty() ) 
	    {
			m_surveyDao.create(newSurvey);
			m_sResult = "Succès de la création du questionnaire";
	    } 
	    else 
	    {
	    	setErrors( "imprévu", "Erreur imprévue lors de la création." );
	    	m_sResult = "Echec de la création du questionnaire.";
	    }
		
		return newSurvey;
	}
	
	public Survey modifySurvey(HttpServletRequest request)
	{
		
		Survey newSurvey = setNewSurvey(request);
		
		String sCreationDate = getFieldValue( request, DATEFIELD );
		DateTime dtCreationDate = new DateTime(sCreationDate);
		newSurvey.setCreationDate(dtCreationDate);
		
		if ( m_mapErrors.isEmpty() ) 
	    {
			m_surveyDao.modify(newSurvey);
			m_sResult = "Succès de la modification du questionnaire";
	    } 
	    else 
	    {
	    	setErrors( "imprévu", "Erreur imprévue lors de la modification." );
	    	m_sResult = "Echec de la modification du questionnaire.";
	    }
		
		return newSurvey;
	}
	
	private void subjectValidation(String sSubject ) throws Exception 
    {
        if ( sSubject != null ) 
        {
            if ( sSubject.length() < 2 ) 
            {
                throw new Exception( "le sujet du questionnaire doit au moins contenir 2 caractères." );
            }
            
        } 
        else 
        {
            throw new Exception( "Merci d'entrer le sujet du questionnaire." );
        }
    }
	
	public String getResult() 
	{
        return m_sResult;
    }
	
	private void setErrors(String sField, String sMessage)
    {
    	m_mapErrors.put(sField, sMessage);
    }
    public Map<String, String> getErrors()
    {
    	return m_mapErrors;
    }
    
    private static String getFieldValue( HttpServletRequest request, String sField ) 
    {
	    String sValue = request.getParameter( sField );
	    if ( sValue == null || sValue.trim().length() == 0 ) 
	    {
	        return null;
	    } 
	    else 
	    {
	        return sValue;
	    }
    }

}
