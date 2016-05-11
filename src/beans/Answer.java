package beans;

public class Answer 
{
	private String 		m_sText;
	private boolean 	m_bStatus;
	private Question 	m_question;
	
	public String getText()
	{
		return m_sText;
	}
	public void setText(String sText)
	{
		m_sText = sText;
	}
	
	public boolean getStatus()
	{
		return m_bStatus;
	}
	public void setStatus(boolean bStatus)
	{
		m_bStatus = bStatus;
	}
	
	public Question getQuestion()
	{
		return m_question;
	}
	public void setStatus(Question question)
	{
		m_question = question;
	}
}
