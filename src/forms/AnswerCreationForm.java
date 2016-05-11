package forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.Answer;
import beans.Question;
import beans.Survey;

public class AnswerCreationForm 
{
	private static final String ANSWERFIELD       	= "answerField";
    private static final String STATUSFIELD    		= "statusField";
    
    private static final String STATUSVALUE    		= "active";
    
    private static final String CHOICEQUESTIONFIELD	= "choiceNewSurvey";
    private static final String QUESTIONLISTFIELD	= "surveyListField";
    private static final String OLDQUESTION	        = "oldSurvey";
    private static final String QUESTIONSESSION     = "survey";
    
    private String				m_sResult;
    private Map<String, String> m_mapErrors 		= new HashMap<String, String>();
    
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
    
    private void answerValidation( String sAnswer ) throws Exception 
    {
        if ( null == sAnswer ) 
        {
        	throw new Exception( "La réponse ne doit pas être vide." );
        } 
    }
    
    public Answer createAnswer(HttpServletRequest request)
	{
    	Question question;
    	
    	String choiceNewQuestion = getFieldValue( request, CHOICEQUESTIONFIELD );
    	if ( OLDQUESTION.equals( choiceNewQuestion ) ) 
    	{
            /* Récupération du nom du sujet du questionnaire choisi */
            String oldQuestion = getFieldValue( request, QUESTIONLISTFIELD );
            /* Récupération de l'objet survey correspondant dans la session */
            HttpSession session = request.getSession();
            question = ( (Map<String, Question>) session.getAttribute( QUESTIONSESSION ) ).get( oldQuestion );
        }
    	else
    	{
    		QuestionCreationForm questionForm = new QuestionCreationForm();
            question = questionForm.createQuestion(request);
            m_mapErrors = questionForm.getErrors();
    	}
		
		String sAnswer = getFieldValue( request, ANSWERFIELD );
		String sStatus = getFieldValue( request, STATUSFIELD );
		
		Answer newAnswer = new Answer();
		
		try 
		{
	        answerValidation( sAnswer );
	    } 
		catch ( Exception e ) 
		{
	        setErrors( ANSWERFIELD, e.getMessage() );
	    }
		newAnswer.setText(sAnswer);
		
		boolean bStatus = false; 
		if(STATUSVALUE == sStatus)
			bStatus = true;
		newAnswer.setStatus(bStatus);
		
		if ( m_mapErrors.isEmpty() ) 
	    {
			m_sResult = "Succès de la création de la réponse.";
	    } 
	    else 
	    {
	    	m_sResult = "Echec de la création du questionnaire.";
	    }
		
		return newAnswer;
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
