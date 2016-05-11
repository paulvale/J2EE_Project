package beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;

import org.joda.time.DateTime;





public class Question 
{
	private Long 	m_lId;
	private String 	m_sText;
	private boolean m_bActive;
	private Long m_lOrder;
	private Survey 	m_survey;
	private List<Pair<String, Integer>> m_lstAnswers;
	
	
	public Long getId()
	{
		return m_lId;
	}
	public void setId(Long lId)
	{
		m_lId = lId;
	}
	
	public String getText()
	{
		return m_sText;
	}
	public void setText(String sText)
	{
		m_sText = sText;
	}
	
	
	public String getActive()
	{
		if(m_bActive)
			return "active";
		else
			return "inactive";
	}
	public void setActive(boolean bActive)
	{
		m_bActive = bActive;
	}
	
	public Long getOrder()
	{
		return m_lOrder;
	}
	public void setOrder(Long lOrder)
	{
		m_lOrder = lOrder;
	}
	
	public Survey getSurvey()
	{
		return m_survey;
	}
	public void setSurvey(Survey survey)
	{
		m_survey = survey;
	}
}
