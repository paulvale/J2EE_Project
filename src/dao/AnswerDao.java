package dao;

import java.util.List;


import beans.Answer;
import javafx.util.Pair;

public interface AnswerDao 
{
    void create( Answer question ) throws DAOException;

    Answer find( Long lId ) throws DAOException;

    List<Answer> lister(Long lQuestionId) throws DAOException;
    
    public void modify( Answer answer ) throws DAOException;

    void delete( Answer question ) throws DAOException;
}