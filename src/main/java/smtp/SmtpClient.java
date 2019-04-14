/**************************************************************
 * HEIG-VD
 *
 * File       : SmtpClient.java
 * Authors    : Gabriel Catel Torres & Pierrick Muller
 * Created on : 10.04.2019
 *
 * Description  : Gestion de l'envoi des messages au client SMTP
 *
 * Remarque    : Ce fichier est basé sur l'implémentation proposée
 *               par Mr Olivier Liechti dans le cadre du cours
 *               RES de la HEIG-VD
 *
 **************************************************************/


package smtp;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;
import model.mail.Message;
import config.ConfigurationManager;


public class SmtpClient implements ISmtpClient {
    private static final Logger LOG = Logger.getAnonymousLogger();

    //VARIABLES UTILITAIRES
    private String smtpServerAddress;
    private int smtpServerPort = 25;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    /**
     * Constructeur SmtpClient.
     * <p>
     * Description : Initialisation des variables relatives à la connexion
     * au serveur SMTP
     * </p>
     *
     * @param {ConfigurationManager} cm - Gestionnaire de configuration,
     * contenant nottament le numéro de port et l'adresse du serveur
     */
    public SmtpClient(ConfigurationManager cm){
        this.smtpServerAddress = cm.getSmtpServerAdress();
        this.smtpServerPort = cm.getSmtpServerPort();
    }

    /**
     * Nom : sendMessage
     * But : Envoi de messages au serveur
     * <p>
     * Description : Permet d'envoyer un message au serveur STMP
     * </p>
     *
     * @param {Message} message - Message à envoyer au serveur
     *
     * @throws IOException
     */
    public void sendMessage(Message message) throws IOException {
        LOG.info("Sending message via SMTP");
        //Connection au serveur
        Socket socket = new Socket(smtpServerAddress, smtpServerPort);
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        String line = reader.readLine();
        LOG.info(line);
        writer.printf("EHLO localhost\r\n");
        line = reader.readLine();
        LOG.info(line);

        //Gestion des exceptions
        if(!line.startsWith("250")) {
            throw new IOException("SMTP error : " + line);
        }
        while (line.startsWith("250-")) {
            line = reader.readLine();
            LOG.info(line);
        }

        //Debut de la communication, ajout de la victime qui envoie
        writer.write("MAIL FROM:");
        writer.write(message.getFrom());
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);

        //Ajout des victimes qui reçoivent
        for(String to : message.getTo()) {
            writer.write("RCPT TO:");
            writer.write(to);
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            LOG.info(line);
        }

        //Gestion de la personne en copie
        for(String to : message.getCc()) {
            writer.write("RCPT TO:");
            writer.write(to);
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            LOG.info(line);
        }

        //Ajout de la copie carbone invisible (Dans une optique
        //d'amelioration future du programme)
        for(String to : message.getBcc()) {
            writer.write("RCPT TO:");
            writer.write(to);
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            LOG.info(line);
        }

        //Début du corps du message
        writer.write("DATA");
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);
        writer.write("Content-Type: text/plain; charset=\"utf-8\"\r\n");
        //Ajout de la victime qui envoie
        writer.write("From: " + message.getFrom() + "\r\n");

        //Ajout des victimes qui recoivent
        writer.write("To: " + message.getTo()[0]);
        for(int i = 1; i < message.getTo().length; i++){
            writer.write(", " +message.getTo()[i]);
        }
        writer.write("\r\n");

        //Gestion de la copie carbone
        if(message.getCc()[0].length() >4 )
        {
            writer.write("Cc: " + message.getCc()[0]);
            for(int i = 1; i < message.getCc().length; i++){
                writer.write(", " +message.getCc()[i]);
            }
            writer.write("\r\n");
        }

        //Ajout du message en lui même
        writer.flush();
        LOG.info(message.getBody());
        writer.write(message.getBody());
        writer.write("\r\n");
        writer.write(".");
        writer.write("\r\n");
        writer.flush();

        //Finitions et fermeture des IOs
        line = reader.readLine();
        LOG.info(line);
        writer.write("QUIT\r\n");
        writer.flush();
        reader.close();
        writer.close();
        socket.close();
    }


}
