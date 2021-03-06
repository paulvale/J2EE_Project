package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;
import static dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;



import beans.Utilisateur;

public class UtilisateurDaoImpl implements UtilisateurDao {
	private static final String SQL_SELECT 			  = "SELECT id, email, password, company, firstname,lastname, phone,actif,admin,createdAt FROM Utilisateur ORDER BY id";
	private static final String SQL_SELECT_PAR_EMAIL  = "SELECT id, email, password, company, firstname,lastname, phone,actif,admin,createdAt FROM Utilisateur WHERE email = ?";
	private static final String SQL_SELECT_PAR_PRENOM = "SELECT id, email, password, company, firstname,lastname, phone,actif,admin,createdAt FROM Utilisateur WHERE firstname LIKE ? ORDER BY id";
	private static final String SQL_SELECT_PAR_COMPANY= "SELECT id, email, password, company, firstname,lastname, phone,actif,admin,createdAt FROM Utilisateur WHERE company LIKE ? ORDER BY id";
	private static final String SQL_INSERT 			  = "INSERT INTO Utilisateur (email, password, firstname, lastname, phone,company,actif,admin,createdAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";
	private static final String	SQL_DELETE_PAR_ID	  = "DELETE FROM Utilisateur WHERE id = ?";
	private static final String	SQL_UPDATE_STATUS     = "UPDATE Utilisateur SET actif = ? , admin = ? WHERE email = ?";
	
	private DAOFactory          daoFactory;

    UtilisateurDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
	
    /* Implémentation de la méthode trouver() définie dans l'interface UtilisateurDao */
    @Override
    public Utilisateur trouver( String email ) throws DAOException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Utilisateur utilisateur = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_EMAIL, false, email );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
            if ( resultSet.next() ) {
                utilisateur = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return utilisateur;
    }

    /* Implémentation de la méthode creer() définie dans l'interface UtilisateurDao */
    @Override
    public void creer( Utilisateur utilisateur ) throws IllegalArgumentException, DAOException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, utilisateur.getEmail(), utilisateur.getMotDePasse(), utilisateur.getPrenom(), utilisateur.getNom(), utilisateur.getPhone(), utilisateur.getCompany(), utilisateur.getActif(), utilisateur.getActif() );
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
            }
            /* Récupération de l'id auto-généré par la requête d'insertion */
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
                utilisateur.setId( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException( "Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }
    
    /* Implémentation de la méthode définie dans l'interface ClientDao */
    @Override
    public List<Utilisateur> lister() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Utilisateur> clients = new ArrayList<Utilisateur>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement( SQL_SELECT );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                clients.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return clients;
    }
    
    @Override
    public List<Utilisateur> trouverParPrenom(String prenom) throws DAOException {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Utilisateur> clients = new ArrayList<Utilisateur>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connection, SQL_SELECT_PAR_PRENOM,false, '%'+prenom+'%');
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                clients.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }
        

        return clients;
    }
    
    @Override
    public List<Utilisateur> trouverParCompany(String company) throws DAOException {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Utilisateur> clients = new ArrayList<Utilisateur>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connection, SQL_SELECT_PAR_COMPANY,false, '%'+company+'%');
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                clients.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }
        

        return clients;
    }
    
    @Override 
    public void supprimer(int id) throws DAOException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_PAR_ID, false,id );
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la suppression de l'utilisateur" );
            }

        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    	
    }
    
    @Override
    public void modificationStatus( String email, boolean actif, boolean admin) throws DAOException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_UPDATE_STATUS, false,actif, admin,email);
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la modification de status de l'utilisateur" );
            }

        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    	
    }
    
    
    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des utilisateurs (un
     * ResultSet) et un bean Utilisateur.
     */
    private static Utilisateur map( ResultSet resultSet ) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId( resultSet.getLong( "id" ) );
        utilisateur.setEmail( resultSet.getString( "email" ) );
        utilisateur.setMotDePasse( resultSet.getString( "password" ) );
        utilisateur.setActif(resultSet.getInt("actif") == 0 ? false : true);
        utilisateur.setAdmin(resultSet.getInt("admin") == 0 ? false : true);
        utilisateur.setCompany(resultSet.getString("company") );
        utilisateur.setNom(resultSet.getString("lastname"));
        utilisateur.setPrenom(resultSet.getString("firstname"));
        utilisateur.setPhone(resultSet.getString("phone"));
        return utilisateur;
    }
}
