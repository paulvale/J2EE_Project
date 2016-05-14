package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import dao.UtilisateurDao;
import dao.UtilisateurDaoImpl;

public class DAOFactory {

    private static final String FICHIER_PROPERTIES       = "dao/dao.properties";
    private static final String PROPERTY_URL             = "url";
    private static final String PROPERTY_DRIVER          = "driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "username";
    private static final String PROPERTY_MOT_DE_PASSE    = "password";

    private String              url;
    private String              username;
    private String              password;

    /* package */
    DAOFactory( String url, String username, String password ) 
    {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /*
     * Méthode chargée de récupérer les informations de connexion à la base de
     * données, charger le driver JDBC et retourner une instance de la Factory
     */
    public static DAOFactory getInstance() throws DAOConfigurationException 
    {
        Properties properties = new Properties();
        String url;
        String driver;
        String userName;
        String password;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );

        if ( fichierProperties == null ) 
        {
            throw new DAOConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
        }

        try 
        {
            properties.load( fichierProperties );
            url = properties.getProperty( PROPERTY_URL );
            driver = properties.getProperty( PROPERTY_DRIVER );
            userName = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
            password = properties.getProperty( PROPERTY_MOT_DE_PASSE );
        } 
        catch ( FileNotFoundException e ) 
        {
            throw new DAOConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable.", e );
        } 
        catch ( IOException e ) 
        {
            throw new DAOConfigurationException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
        }

        try {
            Class.forName( driver );
        } catch ( ClassNotFoundException e ) {
            throw new DAOConfigurationException( "Le driver est introuvable dans le classpath.", e );
        }

        DAOFactory instance = new DAOFactory( url, userName, password );
        return instance;
    }

    /* Méthode chargée de fournir une connexion à la base de données */
    /* package */
    Connection getConnection() throws SQLException 
    {
        return DriverManager.getConnection( url, username, password );
    }

    /*
     * Méthodes de récupération de l'implémentation des différents DAO
     * (uniquement deux dans le cadre de ce TP)
     */
    public SurveyDao getSurveyDao() 
    {
        return new SurveyDaoImpl( this );
    }

    public QuestionDao getQuestionDao() 
    {
        return new QuestionDaoImpl( this );
    }
    
    
    public AnswerDao getAnswerDao() 
    {
        return new AnswerDaoImpl( this );
    }
    
    
    public UtilisateurDao getUtilisateurDao() 
    {
        return new UtilisateurDaoImpl( this );
    }
}