package forms;

import beans.Survey;
import beans.Question;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import dao.QuestionDao;

public class QuestionCreationForm 
{
	public static final String 	CONF_DAO_FACTORY 		= "daofactory";

	private static final String QUESTIONFIELD       	= "questionField";
    private static final String STATUSFIELD    			= "statusQuestionField";
    private static final String ORDERFIELD    			= "orderField";
    private static final String IDFIELD       			= "idField";
    
    private static final String STATUSVALUE    			= "active";
    
    private static final String SURVEYLISTFIELD	    	= "surveysList";
    private static final String SURVEYS_SESSION       	= "surveys";
    
    
    private String				m_sResult;
    private Map<String, String> m_mapErrors 			= new HashMap<String, String>();
    private QuestionDao m_questionDao;
    
    public QuestionCreationForm(  ) 
	{
    }
    
    public QuestionCreationForm( QuestionDao questionDao ) 
	{
        m_questionDao = questionDao;
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
    
    private void questionValidation( String sQuestion ) throws Exception 
    {
        if ( sQuestion == null ) 
        {
        	throw new Exception( "La question ne doit pas être vide." );
        } 
    }
    
    private Long surveyIdValidation( String sSurveyId ) throws Exception 
    {
    	Long lSurveyId = null;
        if ( sSurveyId == null ) 
        {
        	throw new Exception( "La question doit être associée à un questionnaire (veuillez créer le questionnaire avant la question)." );
        } 
        else
        {
        	lSurveyId = Long.parseLong(sSurveyId);
        }
        return lSurveyId;
    }
    
    private Long orderValidation( String sOrder ) throws Exception 
    {
    	Long lOrder;
        if ( sOrder == null ) 
        {
        	throw new Exception( "L'ordre ne doit pas être vide." );
        }
        else
        {
        	lOrder = Long.parseLong(sOrder);
        }
        return lOrder;
    }
    
    
    public Question setNewQuestion(HttpServletRequest request)
	{
		String sQuestion = getFieldValue( request, QUESTIONFIELD );
		String sStatus 	= getFieldValue( request, STATUSFIELD );
		String sId 	= getFieldValue( request, IDFIELD );
		String sOrder = getFieldValue( request, ORDERFIELD );
		Long lOrder = 0L;
		Long lId = 0L;
		if(sId != null)
		{
			lId = Long.parseLong(sId);
		}

		Question newQuestion = new Question();
		
		try 
		{
	        questionValidation( sQuestion );
	    } 
		catch ( Exception e ) 
		{
			System.out.println(e.getMessage());
			setErrors( QUESTIONFIELD, e.getMessage() );
	    }
		newQuestion.setText(sQuestion);
		
		try 
		{
			lOrder = orderValidation( sOrder );
	    } 
		catch ( Exception e ) 
		{
	        setErrors( ORDERFIELD, e.getMessage() );
	    }
		newQuestion.setOrder(lOrder);
		
		boolean bStatus = false; 
		if(sStatus.equals(STATUSVALUE))
			bStatus = true;
		newQuestion.setActive(bStatus);
		
		newQuestion.setId(lId);
		
		return newQuestion;
	}
    
    public Question createQuestion(HttpServletRequest request)
	{
		
    	Survey survey;
    	

        /* Récupération du nom du sujet du questionnaire choisi */
        String sSurveyId = getFieldValue( request, SURVEYLISTFIELD );
        Long lSurveyId = null;
        try 
		{
	        lSurveyId = surveyIdValidation( sSurveyId );
	    } 
		catch ( Exception e ) 
		{
	        setErrors( SURVEYLISTFIELD, e.getMessage() );
	    }
        
        /* Récupération de l'objet survey correspondant dans la session */
        HttpSession session = request.getSession();
        
        survey = ( (Map<String, Survey>) session.getAttribute( SURVEYS_SESSION ) ).get( lSurveyId );

        
    	
		Question newQuestion = setNewQuestion(request);
		newQuestion.setSurvey(survey);
		
		if ( m_mapErrors.isEmpty() ) 
	    {
			m_questionDao.create(newQuestion);
			m_sResult = "Succès de la création de la question. ";
	    } 
	    else 
	    {
	    	m_sResult = "Echec de la création de la question.";
	    }
		
		return newQuestion;
	}
    
    private ServletRequest getServletContext() {
		// TODO Auto-generated method stub
		return null;
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
