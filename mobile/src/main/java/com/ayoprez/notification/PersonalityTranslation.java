package com.ayoprez.notification;

import java.util.ArrayList;

/**
 * Created by AyoPrez on 22/08/15.
 */
public class PersonalityTranslation {

    private static PersonalityTranslation ourInstance = new PersonalityTranslation();

    public static PersonalityTranslation getInstance() {
        return ourInstance;
    }

    private PersonalityTranslation() {
    }

    public String translatePersonality(String personality) {
        //Spanish, German, Italian, French
        if(getArrayStudent().contains(personality)){
            return "student";
        }else if(getArrayLifeLover().contains(personality)){
            return "life lover";
        }else if(getArrayComediant().contains(personality)){
            return "comedian";
        }else if(getArrayRighteous().contains(personality)){
            return "righteous";
        }else if(getArrayParent().contains(personality)){
            return "parent";
        }else if(getArraySpitirual().contains(personality)){
            return "spiritual";
        }else if(getArrayLonely().contains(personality)){
            return "lonely";
        }else if(getArrayInLove().contains(personality)){
            return "in love";
        }else if(getArrayEntrepreneur().contains(personality)){
            return "entrepreneur";
        }else{
            return "all";
        }
    }

    private ArrayList<String> getArrayStudent(){
        ArrayList<String> student = new ArrayList<>();

        student.add("Estudiante");
        student.add("Student");
        student.add("Studente");
        student.add("Étudiant");
        return student;
    }

    private ArrayList<String> getArrayLifeLover(){
        ArrayList<String> life = new ArrayList<>();

        life.add("Amante de la vida");
        life.add("Leben Liebhaber");
        life.add("Amante della vita");
        life.add("Amoureux de la vie");
        life.add("Life lover");

        return life;
    }

    private ArrayList<String> getArrayComediant(){
        ArrayList<String> comedian = new ArrayList<>();

        comedian.add("Comediante/a");
        comedian.add("Komiker");
        comedian.add("Comico");
        comedian.add("Comédien/ne");
        comedian.add("Comediant");

        return comedian;
    }

    private ArrayList<String> getArrayRighteous(){
        ArrayList<String> justice = new ArrayList<>();

        justice.add("Justiciero/a");
        justice.add("Rechtschaffen");
        justice.add("Giusto");
        justice.add("Vertueux");
        justice.add("Righteous");

        return justice;
    }

    private ArrayList<String> getArrayParent(){
        ArrayList<String> parent = new ArrayList<>();

        parent.add("Padre/Madre");
        parent.add("Vater/Mutter");
        parent.add("Padre/Madre");
        parent.add("Père/Mère");
        parent.add("Father/Mother");

        return parent;
    }

    private ArrayList<String> getArraySpitirual(){
        ArrayList<String> spirit = new ArrayList<>();

        spirit.add("Espiritual");
        spirit.add("Spiritual");
        spirit.add("Spirituale");
        spirit.add("Spirituel");

        return spirit;
    }

    private ArrayList<String> getArrayLonely(){
        ArrayList<String> alone = new ArrayList<>();

        alone.add("Solitario/a");
        alone.add("Einzelgänger/in");
        alone.add("Solitario");
        alone.add("Solitaire");
        alone.add("Lonely");

        return alone;
    }

    private ArrayList<String> getArrayInLove(){
        ArrayList<String> love = new ArrayList<>();

        love.add("Enamorado/a");
        love.add("Verliebt");
        love.add("Innamorato");
        love.add("Amoureux");
        love.add("In Love");

        return love;
    }

    private ArrayList<String> getArrayEntrepreneur(){
        ArrayList<String> business = new ArrayList<>();

        business.add("Emprendedor/a");
        business.add("Unternehmer");
        business.add("Imprenditore");
        business.add("Entrepreneur");

        return business;
    }

}