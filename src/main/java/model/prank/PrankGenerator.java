/**************************************************************
 * HEIG-VD
 *
 * File       : PrankGenerator.java
 * Authors    : Gabriel Catel Torres & Pierrick Muller
 * Created on : 10.04.2019
 *
 * Description  : Gestion et création des différents prank d'une session
 *
 * Remarque    : Ce fichier est basé sur l'implémentation proposée
 *               par Mr Olivier Liechti dans le cadre du cours
 *               RES de la HEIG-VD
 *
 **************************************************************/

package model.prank;
import model.mail.Group;
import model.mail.Person;

import config.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class PrankGenerator {
    private ConfigurationManager configurationManager;

    /**
     * Constructeur PrankGenerator.
     * <p>
     * Description : Récupération du configurationManager
     * </p>
     *
     * @param {ConfigurationManager} cm - Gestionnaire de configuration,
     */
    public PrankGenerator(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    /**
     * Nom : generatePranks
     * But : generation des differents pranks
     * <p>
     * Description : Permet de genere les different pranks en fonction
     * du nombre de groupe, des personnes et des messages
     * </p>
     *
     * @return {List<Prank>} liste des pranks générés
     */
    public List<Prank> generatePranks() {
        //Récupération des valeurs numériques
        List<Prank> pranks = new ArrayList<Prank>();
        List<String> messages = configurationManager.getMessages();
        int messageIndex = 0;

        int numberOfGroups = configurationManager.getNumberOfGroups();
        int numberOfVictims = configurationManager.getVictims().size();

        //Gestion du nombre de groupe max
        if(numberOfVictims / numberOfGroups < 3) {
            numberOfGroups = numberOfVictims / 3;
            LOG.warning("There are not enough victimes to generate the desired number of groups.");
        }

        //Generation des groupes
        List<Group>  groups = generateGroups(configurationManager.getVictims(), numberOfGroups);

        //Generation des pranks pour chaque groupe
        for(Group group : groups) {
            Prank prank = new Prank();

            List<Person> victims = group.getMembers();
            Collections.shuffle(victims);
            Person sender = victims.remove(0);
            prank.setVictimSender(sender);
            prank.addVictimRecipients(victims);

            prank.addWitnessRecipients(configurationManager.getWitnessesToCC());

            String message = messages.get(messageIndex);
            messageIndex = (messageIndex + 1) % messages.size();
            prank.setMessage(message);

            pranks.add(prank);
        }
        return pranks;
    }
    private static final Logger LOG = Logger.getAnonymousLogger();

    /**
     * Nom : generateGroups
     * But : generation des differents groupes
     * <p>
     * Description : Permet de genere les different groupes en fonction
     * du nombre de groupe et des personnes
     * </p>
     *
     * @return {List<Group>} liste des groupes générés
     */
    public List<Group> generateGroups(List<Person> victims, int numberOfGroups) {
        List<Person> availableVictims = new ArrayList<Person>(victims);
        Collections.shuffle(availableVictims);
        List<Group> groups = new ArrayList<Group>();
        for(int i = 0; i < numberOfGroups; i++){
            Group group = new Group();
            groups.add(group);
        }
        int turn = 0;
        Group targetGroup;
        while(availableVictims.size() > 0) {
            targetGroup = groups.get(turn);
            turn  = (turn + 1) % groups.size();
            Person victim = availableVictims.remove(0);
            targetGroup.addMember(victim);
        }
        return groups;
    }
}
