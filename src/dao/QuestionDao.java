package dao;

import java.util.List;

import beans.Question;

public interface QuestionDao 
{
    void create( Question question ) throws DAOException;

    Question find( String sText ) throws DAOException;

    List<Question> lister() throws DAOException;

    void supprimer( Question question ) throws DAOException;
}