package dao;

import static dao.DAOUtility.silentClosures;
import static dao.DAOUtilitaire.fermeturesSilencieuses;
import static dao.DAOUtilitaire.initialisationRequetePreparee;
import static dao.DAOUtility.PreparedRequestInitialization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import beans.Answer;
import beans.Result;
import beans.Resultat;
import beans.Survey;
import beans.Utilisateur;

public class ResultDaoImpl implements ResultDao {
	
	private static final String REPONSEACTIVE	  			= "active";
	private static final String REPONSEVALIDE	  			= "valide";
	
	private static final String SQL_INSERT                  = "INSERT INTO result (idUser,idQuestionnaire, idQuestion, intitule, ordre, actif, valide) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_INSERT_RESULTAT         = "INSERT INTO score  (idUser, idQuestionnaire, score) VALUES (?, ?, ?)";
	private static final String SQL_SELECT_SCORE            = "SELECT *    FROM   score WHERE idUser = ? ORDER BY  idScore";
	
	private DAOFactory daoFactory;

	ResultDaoImpl( DAOFactory daoFactory )  {
        this.daoFactory = daoFactory;
    }
	
	public void create( Answer reponse , Long lId_Utilisateur) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet AutoGeneratedValues = null;
        
        try {
        	connection = daoFactory.getConnection();
        	boolean bActive = false;
        	boolean bValide = false;
        	String  sStatus_A = reponse.getActive();
        	String  sStatus_V = reponse.getValide();
        	if(sStatus_A.equals( REPONSEACTIVE )) {
        		bActive = true;
        	}
        	
        	if(sStatus_V.equals( REPONSEVALIDE )) {
        		bValide = true;
        	}
        	
            preparedStatement = PreparedRequestInitialization( connection, SQL_INSERT, true,lId_Utilisateur, reponse.getIdQuestionnaireFK(), reponse.getIdQuestionFK(),       
            		 reponse.getText(), reponse.getOrder(), bActive, bValide);
            int status = preparedStatement.executeUpdate();
            if ( status == 0 ) {
                throw new DAOException( "Echec de la création de la question, aucune ligne ajoutée dans la table." );
            }
            //Peut être utile pour les questions et les rêponses */
            AutoGeneratedValues = preparedStatement.getGeneratedKeys();
            if ( AutoGeneratedValues.next() ) {
            	reponse.setId( AutoGeneratedValues.getLong( 1 ) );
            } else {
                throw new DAOException( "Echec de la création du client en base, aucun ID auto-généré retourné." );
            }
            
        } 
        catch ( SQLException e )  {
            throw new DAOException( e );
        } finally {
            silentClosures( AutoGeneratedValues, preparedStatement, connection );
        }
        
    }	
	
	
	//il faut deux attributs parce qu' on doit savoir ce score est pour quelle questionnaire. 
	//Dans le tableau score, idScore indique le numéro de questionnaire. 
	public void conserveScore( float score, Long lId, Long lId_Utilisateur ) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet AutoGeneratedValues = null;

        
        try {
        	connection = daoFactory.getConnection();
        
        	
            preparedStatement = PreparedRequestInitialization( connection, SQL_INSERT_RESULTAT, true,lId_Utilisateur, lId, score);
            int status = preparedStatement.executeUpdate();
            if ( status == 0 )  {
                throw new DAOException( "Echec de la création de la question, aucune ligne ajoutée dans la table." );
            }   
        } 
        catch ( SQLException e )  {
            throw new DAOException( e );
        } finally {
            silentClosures( AutoGeneratedValues, preparedStatement, connection );
        }
    }
	
	 public List<Resultat> listerScore(Long lId_Utilisateur) throws DAOException {
	        Connection connection = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;
	        List<Resultat> resultats = new ArrayList<Resultat>();

	        try {
	            connection = daoFactory.getConnection();
	            preparedStatement = initialisationRequetePreparee( connection, SQL_SELECT_SCORE,false, lId_Utilisateur);
	            resultSet = preparedStatement.executeQuery();
	            while ( resultSet.next() ) {
	                resultats.add( map( resultSet ) );
	            }
	            
	        } catch ( SQLException e ) {
	            throw new DAOException( e );
	        } finally {
	            fermeturesSilencieuses( resultSet, preparedStatement, connection );
	        }

	        return resultats;
	    }
	 
	 private static Resultat map( ResultSet resultSet ) throws SQLException {
		 Resultat resultat = new Resultat();
	     resultat.setId(resultSet.getLong("idScore"));
	     resultat.setScore(resultSet.getFloat("score"));
	     resultat.setIdUser(resultSet.getLong("idUser"));
	     resultat.setIdQuestionnaire(resultSet.getLong("idQuestionnaire"));
	     return resultat;
	 }
}
