package dao;

import java.util.List;

import beans.Survey;

public interface SurveyDao 
{
    void create( Survey survey ) throws DAOException;
    
    void modify( Survey survey ) throws DAOException;

    Survey find( long lId ) throws DAOException;

    List<Survey> lister() throws DAOException;

    void delete( Survey survey ) throws DAOException;
}