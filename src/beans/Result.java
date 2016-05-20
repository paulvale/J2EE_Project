package beans;

public class Result {
	
	//Ce bean est pour conserver les réponses de l'utilisateur
	private Long		m_lId;
	private String 		m_sText;
	private Long        m_lIdQuestionFK;    //pour réaliser la réponse, on utilise tous les informations de answer.
	private Long        m_lIdQuestionnaireFK;
	private Long 		m_lOrder;
	private boolean 	m_bActive;
	private boolean 	m_bValide;
	private Question 	m_question;
	
	public Long getId() {
		return m_lId;
	}
	public void setId(Long lId){
		m_lId = lId;
	}
	
	public Long getIdQuestionFK(){
		return m_lIdQuestionFK;
	}
	public void setIdQuestionFK(Long lId){
		m_lIdQuestionFK = lId;
	}
	
	public Long getIdQuestionnaireFK(){
		return m_lIdQuestionnaireFK;
	}
	
	public void setIdQuestionnaireFK(Long lId){
		m_lIdQuestionnaireFK = lId;
	}
	
	
	
	public String getText(){
		return m_sText;
	}
	
	public void setText(String sText){
		m_sText = sText;
	}
	
	public Long getOrder(){
		return m_lOrder;
	}
	
	public void setOrder(Long lOrder){
		m_lOrder = lOrder;
	}
	
	public String getActive(){
		if(m_bActive)
			return "active";
		else
			return "inactive";
	}
	
	public void setActive(boolean bActive){
		m_bActive = bActive;
	}
	
	public String getValide(){
		if(m_bValide)
			return "valide";
		else
			return "invalide";
	}
	
	public void setValide(boolean bValide){
		m_bValide = bValide;
	}
	
	public Question getQuestion(){
		return m_question;
	}
	
	public void setQuestion(Question question){
		m_question = question;
	}

}
