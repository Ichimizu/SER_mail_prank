/**************************************************************
 * HEIG-VD
 *
 * File       : MailRobot.java
 * Authors    : Gabriel Catel Torres & Pierrick Muller
 * Created on : 10.04.2019
 *
 * Description  : Classe principale de l'application MailRobot
 *                permettant d'envoyer une serie de pranks par mail
 *                a differentes victimes en leur faisant croire qu'une
 *                autre victime l'a envoyé
 *
 * Remarque    : Ce fichier est basé sur l'implémentation proposée
 *               par Mr Olivier Liechti dans le cadre du cours
 *               RES de la HEIG-VD
 *
 **************************************************************/

import config.ConfigurationManager;
import model.prank.*;
import smtp.SmtpClient;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;


public class MailRobot {
    private static final Logger LOG = Logger.getAnonymousLogger();

    public static void main(String[] args) throws IOException
    {
        //Setup de l'application
        LOG.info("Bienvenu ! Creation des groupes...");
        ConfigurationManager cm = new ConfigurationManager();
        PrankGenerator pg = new PrankGenerator(cm);
        List<Prank> pranks = pg.generatePranks();
        SmtpClient sc = new SmtpClient(cm);

        //Envoi des mails forgés
        LOG.info("Debut de l'envoi des mails...");
        for (Prank tmp : pranks)
        {
            sc.sendMessage(tmp.generateMailMessage());
        }
        LOG.info("Fin de l'envoi des mails, merci.");
    }
}

