package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DAOUtility {

    /*
     * Constructeur caché par défaut (car c'est une classe finale utilitaire,
     * contenant uniquement des méthode appelées de manière statique)
     */
    private DAOUtility() 
    {
    }

    /* Fermeture silencieuse du resultset */
    public static void silentClosure( ResultSet resultSet ) 
    {
        if ( resultSet != null ) 
        {
            try 
            {
                resultSet.close();
            } 
            catch ( SQLException e ) 
            {
                System.out.println( "échec de la fermeture du ResultSet : " + e.getMessage() );
            }
        }
    }

    /* Fermeture silencieuse du statement */
    public static void silentClosure( Statement statement ) 
    {
        if ( statement != null ) 
        {
            try 
            {
                statement.close();
            } 
            catch ( SQLException e ) 
            {
                System.out.println( "échec de la fermeture du Statement : " + e.getMessage() );
            }
        }
    }

    /* Fermeture silencieuse de la connexion */
    public static void silentClosure( Connection connection ) 
    {
        if ( connection != null ) 
        {
            try 
            {
            	connection.close();
            } 
            catch ( SQLException e ) 
            {
                System.out.println( "échec de la fermeture de la connexion : " + e.getMessage() );
            }
        }
    }

    /* Fermetures silencieuses du statement et de la connexion */
    public static void silentClosures( Statement statement, Connection connexion ) 
    {
    	silentClosure( statement );
    	silentClosure( connexion );
    }

    /* Fermetures silencieuses du resultset, du statement et de la connexion */
    public static void silentClosures( ResultSet resultSet, Statement statement, Connection connexion ) 
    {
    	silentClosure( resultSet );
    	silentClosure( statement );
        silentClosure( connexion );
    }

    /*
     * Initialise la requête préparée basée sur la connexion passée en argument,
     * avec la requête SQL et les objets données.
     */
    public static PreparedStatement PreparedRequestInitialization( Connection connexion, String sql, 
    		boolean returnGeneratedKeys, Object... objets ) throws SQLException 
    {
        PreparedStatement preparedStatement = connexion.prepareStatement( 
        		sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
        for ( int i = 0; i < objets.length; i++ ) 
        {
            preparedStatement.setObject( i + 1, objets[i] );
        }
        return preparedStatement;
    }
}