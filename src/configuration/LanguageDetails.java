package configuration;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import generator.parser.JsonUtility;
import java.util.HashMap;

/**
 *
 * @author Mamisoa
 */
public class LanguageDetails {
    HashMap<String, LanguageProperties> languages;

    //GETTERS & SETTERS

    public HashMap<String, LanguageProperties> getLanguages() {
        return languages;
    }

    public void setLanguages(HashMap<String, LanguageProperties> languages) {
        this.languages = languages;
    }

    //CONSTRUCTOR
    public LanguageDetails() throws Exception{
//        read();
    }

    //METHODS
    public void read() throws Exception{
        String path = "./ressources/languageDetails.json";
        String confFile = System.getProperty("user.dir")+"/ressources/languageDetails.json";
        System.out.println(confFile);
        LanguageDetails temp = JsonUtility.parseJson(path, this.getClass());
        this.setLanguages(temp.getLanguages());
    }
}
