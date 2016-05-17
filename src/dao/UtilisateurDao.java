package dao;

import java.util.List;

import dao.DAOException;

import beans.Utilisateur;

public interface UtilisateurDao {
	void creer( Utilisateur utilisateur ) throws DAOException;

    Utilisateur trouver( String email ) throws DAOException;
    
    List<Utilisateur> lister() throws DAOException;
    
    List<Utilisateur> trouverParPrenom(String prenom) throws DAOException;
    
    List<Utilisateur> trouverParCompany(String company) throws DAOException;
    
    void modificationStatus( String email, boolean actif, boolean admin) throws DAOException;
    
    void supprimer( int id) throws DAOException;
    
    
}
