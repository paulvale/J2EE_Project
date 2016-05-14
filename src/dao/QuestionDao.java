package dao;

import java.util.List;

import beans.Question;
import beans.Survey;

public interface QuestionDao 
{
    void create( Question question ) throws DAOException;
    
    void modify( Question question ) throws DAOException;

    Question find( Long lId ) throws DAOException;

    List<Question> lister(Long lSurveyId) throws DAOException;
    
    List<Question> lister() throws DAOException;

    void delete( Question question ) throws DAOException;
}