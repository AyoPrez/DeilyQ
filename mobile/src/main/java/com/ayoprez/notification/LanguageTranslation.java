package com.ayoprez.notification;

import java.util.ArrayList;

/**
 * Created by AyoPrez on 21/08/15.
 */
public class LanguageTranslation {

    private static LanguageTranslation ourInstance = new LanguageTranslation();

    public static LanguageTranslation getInstance() {
        return ourInstance;
    }

    private LanguageTranslation() {
    }

    public String translateLanguage(String language){

        if(getArrayEnglish().contains(language.toLowerCase())){
            return "english";
        }else if(getArraySpanish().contains(language.toLowerCase())){
            return "spanish";
        }else if(getArrayGerman().contains(language.toLowerCase())){
            return "german";
        }else if(getArrayItalian().contains(language.toLowerCase())){
            return "italian";
        }else if(getArrayFrench().contains(language.toLowerCase())){
            return "french";
        }else{
            return language;
        }

    }

    private ArrayList<String> getArraySpanish(){
        ArrayList<String> spanish = new ArrayList<>();

        spanish.add("español");
        spanish.add("spanisch");
        spanish.add("spagnolo");
        spanish.add("espagnol");
        spanish.add("spanish");

        return spanish;
    }

    private ArrayList<String> getArrayEnglish(){
        ArrayList<String> english = new ArrayList<>();

        english.add("inglés");
        english.add("englisch");
        english.add("inglese");
        english.add("anglais");
        english.add("english");

        return english;
    }

    private ArrayList<String> getArrayGerman(){
        ArrayList<String> german = new ArrayList<>();

        german.add("alemán");
        german.add("deutsch");
        german.add("tedesco");
        german.add("allemand");
        german.add("german");
        return german;
    }

    private ArrayList<String> getArrayItalian(){
        ArrayList<String> italian = new ArrayList<>();

        italian.add("italiano");
        italian.add("italienisch");
        italian.add("italien");
        italian.add("italian");
        return italian;
    }

    private ArrayList<String> getArrayFrench(){
        ArrayList<String> french = new ArrayList<>();

        french.add("francés");
        french.add("französisch");
        french.add("francese");
        french.add("francese");
        french.add("french");
        return french;
    }
}