package controllers.utilisateurs;

import controllers.Application;
import controllers.CRUD;
import controllers.Secure;
import models.Commandes.Client;
import models.restaurants.Agent;
import models.restaurants.Categorie;
import models.restaurants.Plat;
import models.utilisateurs.Utilisateur;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.mvc.Before;

import java.util.List;

import static play.data.validation.Validation.*;

public class Utilisateurs extends CRUD{
    public static void compte() {
        render();
    }

    public static void addUtilisateur(
            @Required(message = "Type d'utilisateur obligatoire") String utilisateur,
            @Required(message = "Email obligatoire") String emailEng,
            @Required(message = "Login obligatoire") String loginEng,
            @Required(message = "Mot de passe obligatoire") String passwordEng) {
        if (hasErrors()) {
            render("Utilisateur/compte.html", utilisateur, emailEng, loginEng, passwordEng);
        }else
        if (utilisateur.equals("Client")) try {
            new Client(loginEng, passwordEng, emailEng, utilisateur).save();
            flash.success("Bienvenue %s", loginEng);
            render("utilisateurs/Utilisateurs/compte.html");
        } catch (Exception e) {
            addError("Login déja utilisé", loginEng);
            render("utilisateurs/Utilisateurs/compte.html");
        }
        else
        if (utilisateur.equals("Agent")) try {
            new Agent(loginEng, passwordEng, emailEng, utilisateur).save();
            flash.success("Bienvenue %s", loginEng);
            render("utilisateurs/Utilisateurs/compte.html");
        } catch (Exception e) {
            addError("Login déja", loginEng);
            render("utilisateurs/Utilisateurs/compte.html");
        }
    }

    @Before
    static void setConnectedUser() {
        if(Secure.Security.isConnected()) {
            Utilisateur user =Utilisateur.find("byEmail", Secure.Security.connected()).first();
            renderArgs.put("user", user.getNomUtilisateur());
        }
    }

    public static void index() {
        render();
    }

    static void onDisconnected() {
        Application.index();
    }
//TODO verifier si la methode creer dans le modèle a sa place
    static void onAuthenticated() {
       Utilisateur.index();
    }

    public static void crudAgent(){
        List<Categorie> categorieList = Categorie.findAll();
        List<Plat> plats = Plat.findAll();
        render(categorieList, plats);
    }

}


