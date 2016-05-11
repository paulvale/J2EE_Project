package beans;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

import beans.Question;

public class Survey 
{
	private Long m_lId;
	private String 	m_sSubject;
	private boolean	m_bActive;
	private DateTime m_dtCreationDate;
	
	
	public Long getId()
	{
		return m_lId;
	}
	public void setId(Long lId)
	{
		m_lId = lId;
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
	
	public DateTime getCreationDate()
	{
		return m_dtCreationDate;
	}
	public void setCreationDate(DateTime dtCreationDate)
	{
		m_dtCreationDate = dtCreationDate;
	}
	
	public String getSubject()
	{
		return m_sSubject;
	}
	public void setSubject(String sSubject)
	{
		m_sSubject = sSubject;
	}
	
}
