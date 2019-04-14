/**************************************************************
 * HEIG-VD
 *
 * File       : Group.java
 * Authors    : Gabriel Catel Torres & Pierrick Muller
 * Created on : 10.04.2019
 *
 * Description  : Gestion de la structure et des données d'un groupe
 *
 * Remarque    : Ce fichier est basé sur l'implémentation proposée
 *               par Mr Olivier Liechti dans le cadre du cours
 *               RES de la HEIG-VD
 *
 **************************************************************/

package model.mail;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private final List<Person> members = new ArrayList<Person>();

    public void addMember(Person person) {
        members.add(person);
    }

    public List<Person> getMembers() {
        return members;
    }
}
