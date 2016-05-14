package forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.Answer;
import beans.Question;
import beans.Survey;
import dao.AnswerDao;

public class AnswerCreationForm 
{
	public static final String 	CONF_DAO_FACTORY 		= "daofactory";

	private static final String ANSWERFIELD       		= "answerField";
    private static final String STATUSFIELD    			= "statusAnswerField";
    private static final String VALIDITYFIELD    		= "validityField";
    private static final String ORDERFIELD    			= "orderField";
    public static final String 	ID_QUESTION 			= "idQuestion";
    public static final String 	ID_ANSWER 				= "idAnswer";
    
    private static final String STATUSVALUE    			= "active";
    private static final String VALIDITYVALUE		 	= "valide";
    
    private static final String QUESTIONLISTFIELD	    = "questionsList";
    private static final String ALL_QUESTIONS_SESSION   = "allQuestions";
    
    
    private String				m_sResult;
    private Map<String, String> m_mapErrors 			= new HashMap<String, String>();
    private AnswerDao m_answerDao;
    
    
    public AnswerCreationForm( AnswerDao answerDao ) 
	{
    	m_answerDao = answerDao;
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
    
    private void answerValidation( String sQuestion ) throws Exception 
    {
        if ( sQuestion == null ) 
        {
        	throw new Exception( "La réponse ne doit pas être vide." );
        } 
    }
    
    private Long questionIdValidation( String sQuestionId ) throws Exception 
    {
    	Long lQuestionId = null;
        if ( sQuestionId == null ) 
        {
        	throw new Exception( "La réponse doit être associée à une question (veuillez créer la question avant la réponse)." );
        } 
        else
        {
        	lQuestionId = Long.parseLong(sQuestionId);
        }
        return lQuestionId;
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
    
    
    public Answer setNewAnswer(HttpServletRequest request)
	{
		String sAnswer = getFieldValue( request, ANSWERFIELD );
		String sStatus 	= getFieldValue( request, STATUSFIELD );
		String sId 	= getFieldValue( request, ID_ANSWER );
		String sOrder = getFieldValue( request, ORDERFIELD );
		String sValidity = getFieldValue(request, VALIDITYFIELD);
		Long lOrder = 0L;
		Long lId = 0L;
		if(sId != null)
		{
			lId = Long.parseLong(sId);
		}

		Answer newAnswer = new Answer();
		
		try 
		{
	        answerValidation( sAnswer );
	    } 
		catch ( Exception e ) 
		{
			System.out.println(e.getMessage());
			setErrors( ANSWERFIELD, e.getMessage() );
	    }
		newAnswer.setText(sAnswer);
		
		try 
		{
			lOrder = orderValidation( sOrder );
	    } 
		catch ( Exception e ) 
		{
	        setErrors( ORDERFIELD, e.getMessage() );
	    }
		newAnswer.setOrder(lOrder);
		
		boolean bStatus = false; 
		if(sStatus.equals(STATUSVALUE))
			bStatus = true;
		newAnswer.setActive(bStatus);
		
		boolean bValidity = false; 
		if(sValidity.equals(VALIDITYVALUE))
			bValidity = true;
		newAnswer.setValide(bValidity);
		
		newAnswer.setId(lId);
		
		return newAnswer;
	}
    
    public Answer createAnswer(HttpServletRequest request)
	{
		
    	Question question;
    	

        /* Récupération du nom du sujet du questionnaire choisi */
        String sQuestionId = getFieldValue( request, QUESTIONLISTFIELD );
        Long lQuestionId = null;
        try 
		{
        	lQuestionId = questionIdValidation( sQuestionId );
	    } 
		catch ( Exception e ) 
		{
	        setErrors( QUESTIONLISTFIELD, e.getMessage() );
	    }
        
        /* Récupération de l'objet survey correspondant dans la session */
        HttpSession session = request.getSession();
        
        question = ( (Map<String, Question>) session.getAttribute( ALL_QUESTIONS_SESSION ) ).get( lQuestionId );

        
    	
		Answer newAnswer = setNewAnswer(request);
		newAnswer.setQuestion(question);
		
		if ( m_mapErrors.isEmpty() ) 
	    {
			m_answerDao.create(newAnswer);
			m_sResult = "Succès de la création de la question. ";
	    } 
	    else 
	    {
	    	m_sResult = "Echec de la création de la question.";
	    }
		
		return newAnswer;
	}
    
    public Answer modifyAnswer(HttpServletRequest request)
	{
		
    	Question question;
    	

        /* Récupération du nom du sujet du questionnaire choisi */
        String sQuestionId = getFieldValue( request, QUESTIONLISTFIELD );
        Long lQuestionId = null;
        try 
		{
        	lQuestionId = questionIdValidation( sQuestionId );
	    } 
		catch ( Exception e ) 
		{
	        setErrors( QUESTIONLISTFIELD, e.getMessage() );
	    }
        
        /* Récupération de l'objet survey correspondant dans la session */
        HttpSession session = request.getSession();
        
        question = ( (Map<Long, Question>) session.getAttribute( ALL_QUESTIONS_SESSION ) ).get( lQuestionId );

        
    	
		Answer newAnswer = setNewAnswer(request);
		newAnswer.setQuestion(question);
		
		if ( m_mapErrors.isEmpty() ) 
	    {
			m_answerDao.modify(newAnswer);
			m_sResult = "Succès de la modification de la réponse. ";
	    } 
	    else 
	    {
	    	m_sResult = "Echec de la modification de la réponse.";
	    }
		
		return newAnswer;
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
