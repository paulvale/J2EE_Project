package beans;

public class Resultat {
	private Long 	m_lId;
	private float    m_lScore;
	
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
}
