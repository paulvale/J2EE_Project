package forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import dao.UtilisateurDao;
import dao.DAOException;

import forms.FormValidationException;

import beans.Utilisateur;

public final class ConnexionForm {
    private static final String CHAMP_EMAIL  = "email";
    private static final String CHAMP_PASS   = "motdepasse";

    private String              resultat;
    private Map<String, String> erreurs      = new HashMap<String, String>();
    private UtilisateurDao      utilisateurDao;

    public ConnexionForm( UtilisateurDao utilisateurDao ) {
        this.utilisateurDao = utilisateurDao;
    }

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public Utilisateur connecterUtilisateur( HttpServletRequest request ) {
        /* Récupération des champs du formulaire */
        String email = getValeurChamp( request, CHAMP_EMAIL );
        String motDePasse = getValeurChamp( request, CHAMP_PASS );

        Utilisateur utilisateur = new Utilisateur();
        
        traiterEmail( email, utilisateur );
        traiterMotDePasse( motDePasse, utilisateur );
        

        /* Si les erreurs du formulaire sont vides alors on va questionner la BDD pour retrouver notre utilisateur
         * grâce a son email puis on comparera les 2 mot de passe ...
         */
        try {
            if ( erreurs.isEmpty() ) {
                Utilisateur utilisateurTrouveBDD = utilisateurDao.trouver( email );
                if(utilisateurTrouveBDD.getMotDePasse().equals(motDePasse)){
                	resultat = "Succès de la connexion";
                	utilisateur = utilisateurTrouveBDD;
                } else {
                	setErreur("erreur","Erreur lors de la connexion");
                	resultat = "Échec connexion utilisateur : mot de passe ou email incorrect";
                }
            } else {
                resultat = "Échec de la connexion.";
            }
        } catch ( DAOException e ) {
            setErreur( "imprévu", "Erreur imprévue lors de la création." );
            resultat = "Échec de la connexion : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }

        return utilisateur;
    }
    
    private void traiterEmail( String email, Utilisateur utilisateur ) {
        try {
            validationEmail( email );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }
        utilisateur.setEmail( email );
    }
    
    private void traiterMotDePasse( String motDePasse, Utilisateur utilisateur ) {
        try {
            validationMotDePasse( motDePasse );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_PASS, e.getMessage() );
        }
        utilisateur.setMotDePasse( motDePasse );
    }
    
    

    /**
     * Valide l'adresse email saisie.
     */
    private void validationEmail( String email ) throws FormValidationException {
        if ( email != null && !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
            throw new FormValidationException( "Merci de saisir une adresse mail valide." );
        }
    }

    /**
     * Valide le mot de passe saisi.
     */
    private void validationMotDePasse( String motDePasse ) throws FormValidationException {
        if ( motDePasse != null ) {
            if ( motDePasse.length() < 6 ) {
                throw new FormValidationException( "Le mot de passe doit contenir au moins 6 caractères." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir votre mot de passe." );
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