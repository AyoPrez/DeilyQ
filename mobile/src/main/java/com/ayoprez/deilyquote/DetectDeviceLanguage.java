package com.ayoprez.deilyquote;

import java.util.Locale;

/**
 * Created by AyoPrez on 10/09/15.
 */
public class DetectDeviceLanguage {

    public String getISO3Language(){
        return Locale.getDefault().getISO3Language();
    }

    public Locale getLocale(String language){
        if(language.length() == 3){
           return getLocaleFromISO3(language);
        }else{
           return getLocaleFromString(language);
        }
    }

    private Locale getLocaleFromString(String language){

        switch (language.toLowerCase()) {
            case "spanish":
                return new Locale("es");
            case "german":
                return new Locale("de");
            case "italian":
                return new Locale("it");
            case "french":
                return new Locale("fr");
            case "english":
                return new Locale("en");
            default:
                return new Locale("en");
        }
    }

    //TODO revisar que las letras sean las correctas
    private Locale getLocaleFromISO3(String ISO3){
        switch (ISO3.toLowerCase()){
            case "spa":
                return new Locale("es");
            case "ger":
                return new Locale("de");
            case "ita":
                return new Locale("it");
            case "fre":
                return new Locale("fr");
            case "eng":
                return new Locale("en");
            default:
                return new Locale("en");
        }
    }
}