package dao;

import java.util.List;


import beans.Answer;
import javafx.util.Pair;

public interface AnswerDao 
{
    void create( Answer question ) throws DAOException;

    Answer find( Pair<String, String> questionAnswerPair ) throws DAOException;

    List<Answer> lister() throws DAOException;

    void supprimer( Answer question ) throws DAOException;
}