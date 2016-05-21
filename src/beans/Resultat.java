package beans;

public class Resultat {
	private Long 	m_lId;
	private float   m_lScore;
	private Long	m_lIdUser;
	private Long	m_lIdQuestionnaire;
	
	public Long getId(){
		return m_lId;
	}
	
	public void setId(Long lId) {
		m_lId = lId;
	}
	
	
	public float getScore(){
		return m_lScore;
	}
	
	public void setScore(float lScore){
		m_lScore = lScore;
	}
	
	public Long getIdUser(){
		return m_lIdUser;
	}
	
	public void setIdUser(Long lIdUser) {
		m_lIdUser = lIdUser;
	}
	
	public Long getIdQuestionnaire(){
		return m_lIdQuestionnaire;
	}
	
	public void setIdQuestionnaire(Long lIdQuestionnaire) {
		m_lIdQuestionnaire = lIdQuestionnaire;
	}
}
