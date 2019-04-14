/**************************************************************
 * HEIG-VD
 *
 * File       : ConfigurationManager.java
 * Author     : Gabriel Catel Torres & Pierrick Muller
 * Created on : 10.04.2019
 *
 * Description  : Fichier permettant la gestion des fichiers de
 *                configurations pour l'application MailRobot
 *
 * Remarque    : Ce fichier est basé sur l'implémentation proposée
 *               par Mr Olivier Liechti dans le cadre du cours
 *               RES de la HEIG-VD
 *
 **************************************************************/


package config;

import model.mail.Person;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigurationManager {
    private String smtpServerAdress;
    private int smtpServerPort;
    private final List<Person> victims;
    private final List<String> messages;
    private int numberOfGroups;
    private List<Person> witnessesToCC;

    /**
     * Constructeur ConfigurationManager.
     * <p>
     * Description : Recuperation de la config
     * </p>
     */
    public ConfigurationManager() throws IOException {
        victims = loadAddressesFromFile("../config/victims.utf8");
        messages = loadMessagesFromFile("../config/messages.utf8");
        loadProperties("../config/config.properties");
    }

    /**
     * Nom : loadProperties
     * But : Récuperation des propriete
     * <p>
     * Description : Recuperation des propriete de l'app
     * </p>
     *
     * @return {String} filename - Nom du fichier contenant les propriete
     */
    private void loadProperties(String filename) throws IOException {
        FileInputStream fis = new FileInputStream(filename);
        Properties properties = new Properties();
        properties.load(fis);
        this.smtpServerAdress = properties.getProperty("smtpServerAdress");
        this.smtpServerPort = Integer.parseInt(properties.getProperty("smtpServerPort"));
        this.numberOfGroups = Integer.parseInt(properties.getProperty("numberOfGroups"));

        this.witnessesToCC = new ArrayList<Person>();
        String witnesses = properties.getProperty("witnessesToCC");
        String[] witnessesAddresses = witnesses.split(",");
        for(String address : witnessesAddresses) {
            this.witnessesToCC.add(new Person(address));
        }
    }

    /**
     * Nom : loadAddressesFromFile
     * But : Récuperation des addresse mails
     * <p>
     * Description : Récuperation des addresse mails de l'app
     * </p>
     *
     * @return {String} filename - Nom du fichier contenant les
     * addresses mails
     */
    private List<Person> loadAddressesFromFile(String fileName) throws IOException {
        List<Person> result;
        try(FileInputStream fis = new FileInputStream(fileName)) {
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            try(BufferedReader reader = new BufferedReader(isr)){
                result = new ArrayList<>();
                String address = reader.readLine();
                while(address != null) {
                    result.add(new Person(address));
                    address = reader.readLine();
                }
            }
        }
        return result;
    }

    /**
     * Nom : loadMessagesFromFile
     * But : Récuperation des messages
     * <p>
     * Description : Récuperation des messages de l'app
     * </p>
     *
     * @return {String} filename - Nom du fichier contenant les
     * messages
     */
    private List<String> loadMessagesFromFile(String fileName) throws IOException {
        List<String> result;
        try(FileInputStream fis = new FileInputStream(fileName)) {
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            try (BufferedReader reader = new BufferedReader(isr)) {
                result = new ArrayList<>();
                String line = reader.readLine();
                while (line != null) {
                    StringBuilder body = new StringBuilder();
                    while((line != null) && (!line.equals("=="))){
                        body.append(line);
                        body.append("\r\n");
                        line = reader.readLine();
                    }
                    result.add(body.toString());
                    line = reader.readLine();
                }
            }
        }
        return result;
    }

    public List<Person> getVictims() {
        return victims;
    }

    public List<String> getMessages() {
        return messages;
    }

    public int getNumberOfGroups() {
        return numberOfGroups;
    }

    public List<Person> getWitnessesToCC() {
        return witnessesToCC;
    }

    public String getSmtpServerAdress(){return smtpServerAdress;}

    public int getSmtpServerPort(){return smtpServerPort;}
}
