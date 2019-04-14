/**************************************************************
 * HEIG-VD
 *
 * File       : Prank.java
 * Authors    : Gabriel Catel Torres & Pierrick Muller
 * Created on : 10.04.2019
 *
 * Description  : Mise en place et generation des messages de prank
 *
 * Remarque    : Ce fichier est basé sur l'implémentation proposée
 *               par Mr Olivier Liechti dans le cadre du cours
 *               RES de la HEIG-VD
 *
 **************************************************************/

package model.prank;

import model.mail.Message;
import model.mail.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Prank {
    private Person victimSender;
    private final List<Person> victimRecipients = new ArrayList<Person>();
    private final List<Person> witnessRecipients = new ArrayList<Person>();
    private String message;

    //Recuperation de la victime qui envoie le prank
    public Person getVictimSender() {
        return victimSender;
    }

    //Mise en place de la victime qui envoie le prank
    public void setVictimSender(Person victimSender) {
        this.victimSender = victimSender;
    }

    //Recuperation du message du prank
    public String getMessage() {
        return message;
    }

    //Mise en place du message du prank
    public void setMessage(String message) {
        this.message = message;
    }

    //Ajout d'une/de plusieurs victime au prank
    public void addVictimRecipients(List<Person> victims) {
        victimRecipients.addAll(victims);
    }

    //Ajout d'un/de plusieurs témoins
    public void addWitnessRecipients(List<Person> witnesses) {
        witnessRecipients.addAll(witnesses);
    }

    //Recuperation des victimes
    public List<Person> getVictimRecipients() {
        return new ArrayList(victimRecipients);
    }

    //recuperation des temoins
    public List<Person> getWitnessRecipients() {
        return new ArrayList(witnessRecipients);
    }

    /**
     * Nom : generateMailMessage
     * But : generation du message du prank
     * <p>
     * Description : Generation du message du prank
     * </p>
     *
     * @return {Message} Le message du prank
     */
    public Message generateMailMessage(){
        Message msg = new Message();

        msg.setBody(this.message + "\r\n" + victimSender.getFirstName());

        String[] to = victimRecipients
                .stream()
                .map(p->p.getAddress())
                .collect(Collectors.toList())
                .toArray(new String[]{});
        msg.setTo(to);

        String[] cc = witnessRecipients
                .stream()
                .map(p->p.getAddress())
                .collect(Collectors.toList())
                .toArray(new String[]{});
        msg.setCc(cc);

        msg.setFrom(victimSender.getAddress());

        return msg;
    }

}
