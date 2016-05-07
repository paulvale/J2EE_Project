package forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import beans.Utilisateur;
import dao.UtilisateurDao;
import dao.DAOException;


public final class CreationUtilisateurForm {
    private static final String CHAMP_NOM       = "nomUtilisateur";
    private static final String CHAMP_PRENOM    = "prenomUtilisateur";
    private static final String CHAMP_COMPANY   = "companyUtilisateur";
    private static final String CHAMP_EMAIL     = "emailUtilisateur";
    private static final String CHAMP_ACTIF		= "actifUtilisateur";
    private static final String CHAMP_ADMIN		= "adminUtilisateur";
    private static final String CHAMP_PHONE		= "phoneUtilisateur";
    private static final String CHAMP_PASS		= "passwordUtilisateur";
    

    private String              resultat;
    private Map<String, String> erreurs         = new HashMap<String, String>();
    private UtilisateurDao           utilisateurDao;

    public CreationUtilisateurForm( UtilisateurDao utilisateurDao ) {
        this.utilisateurDao = utilisateurDao;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }

    public Utilisateur creerUtilisateur( HttpServletRequest request) {
        String nom = getValeurChamp( request, CHAMP_NOM );
        String prenom = getValeurChamp( request, CHAMP_PRENOM );
        String company = getValeurChamp( request, CHAMP_COMPANY );
        String email = getValeurChamp( request, CHAMP_EMAIL );
        String admin = getValeurChamp( request, CHAMP_ADMIN );
        String actif = getValeurChamp( request, CHAMP_ACTIF );
        String phone = getValeurChamp( request, CHAMP_PHONE );
        String password = getValeurChamp( request, CHAMP_PASS);
        

        Utilisateur utilisateur = new Utilisateur();

        traiterNom( nom, utilisateur );
        traiterPrenom( prenom, utilisateur );
        traiterPhone( phone, utilisateur );
        traiterEmail( email, utilisateur );
        traiterActif( actif, utilisateur );
        traiterAdmin( admin, utilisateur);
        traiterCompany( company, utilisateur);
        traiterPassword( password, utilisateur);
        
        
        try {
            if ( erreurs.isEmpty() ) {
                utilisateurDao.creer( utilisateur );
                resultat = "Succès de la création de l'utilisateur.";
            } else {
                resultat = "Échec de la création de l'utilisateur.";
            }
        } catch ( DAOException e ) {
            setErreur( "imprévu", "Erreur imprévue lors de la création." );
            resultat = "Échec de la création de l'utilisateur : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }

        return utilisateur;
    }

    private void traiterNom( String nom, Utilisateur utilisateur ) {
        try {
            validationNom( nom );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_NOM, e.getMessage() );
        }
        utilisateur.setNom( nom );
    }

    private void traiterPrenom( String prenom, Utilisateur utilisateur ) {
        try {
            validationPrenom( prenom );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_PRENOM, e.getMessage() );
        }
        utilisateur.setPrenom( prenom );
    }
    
    private void traiterPassword( String password, Utilisateur utilisateur ) {
        try {
            validationPrenom( password );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_PASS, e.getMessage() );
        }
        utilisateur.setMotDePasse(password);
    }

    private void traiterPhone( String telephone, Utilisateur utilisateur ) {
        try {
            validationPhone( telephone );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_PHONE, e.getMessage() );
        }
        utilisateur.setPhone( telephone );
    }

    private void traiterEmail( String email, Utilisateur utilisateur ) {
        try {
            validationEmail( email );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }
        utilisateur.setEmail( email );
    }
    
    private void traiterActif( String actif, Utilisateur utilisateur ) {
    	Boolean activation = false;
    	if(actif != null){
    		if(actif.equals("actif")){
            	activation = true;
            }
    	}

        utilisateur.setActif( activation );
    }
    
    private void traiterAdmin( String admin, Utilisateur utilisateur ) {
    	Boolean administrateur = false;
    	
    	if(admin != null){
    		if(admin.equals("admin")){
            	administrateur = true;
            }
    	}    
        
        utilisateur.setAdmin( administrateur );
    }
    
    private void traiterCompany( String company, Utilisateur utilisateur ) {        
        utilisateur.setCompany( company );
    }
    
    
    private void validationNom( String nom ) throws FormValidationException {
        if ( nom != null ) {
            if ( nom.length() < 2 ) {
                throw new FormValidationException( "Le nom d'utilisateur doit contenir au moins 2 caractères." );
            }
        } else {
            throw new FormValidationException( "Merci d'entrer un nom d'utilisateur." );
        }
    }
    
    private void validationPassword( String password ) throws FormValidationException {
        if ( password != null && password.length() < 6 ) {
            throw new FormValidationException( "Le mot de passe d'utilisateur doit contenir au moins 6 caractères." );
        }
    }

    private void validationPrenom( String prenom ) throws FormValidationException {
        if ( prenom != null && prenom.length() < 2 ) {
            throw new FormValidationException( "Le prénom d'utilisateur doit contenir au moins 2 caractères." );
        }
    }

    private void validationPhone( String telephone ) throws FormValidationException {
        if ( telephone != null ) {
            if ( !telephone.matches( "^\\d+$" ) ) {
                throw new FormValidationException( "Le numéro de téléphone doit uniquement contenir des chiffres." );
            } else if ( telephone.length() < 4 ) {
                throw new FormValidationException( "Le numéro de téléphone doit contenir au moins 4 chiffres." );
            }
        } else {
            throw new FormValidationException( "Merci d'entrer un numéro de téléphone." );
        }
    }

    private void validationEmail( String email ) throws FormValidationException {
        if ( email != null && !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
            throw new FormValidationException( "Merci de saisir une adresse mail valide." );
        }
    }
    


    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
}