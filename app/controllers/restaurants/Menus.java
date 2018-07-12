package controllers.restaurants;

import controllers.CRUD;
import models.restaurants.Menu;
import models.restaurants.TypeMenu;
import play.data.validation.Validation;

import java.util.Calendar;
import java.util.List;

public class Menus extends CRUD {

    public static void addMenu(String libelleEng, TypeMenu typeMenuEng){
        try{
           Menu menu = new Menu(libelleEng,  typeMenuEng).save();
            indexMenu();
            flash.success("SUCCES %s", "Enregistremnt Bien Éffectué");

        } catch (Exception e) {
            Validation.addError("échec", "Erreur d'enregistrement");
            render("client/show.html");
        }
    }
    public static void indexMenu(){
        List<Menu> menus = Menu.findAll();
        render(menus);
    }

    public static void suppMenu(Long idSupp){
        Menu menu = Menu.findById(idSupp);
        try {
            menu.delete();
            flash.success("SUCCES %s", "Enregistremnt Bien Éffectué");
            indexMenu();
        }catch (Exception e){
            Validation.addError("échec", "Erreur d'enregistrement");
        }
    }
}
