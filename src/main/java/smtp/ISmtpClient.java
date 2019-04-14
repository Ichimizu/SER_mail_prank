/**************************************************************
 * HEIG-VD
 *
 * File       : ISmtpClient.java
 * Authors    : Gabriel Catel Torres & Pierrick Muller
 * Created on : 10.04.2019
 *
 * Description  : interface representant les besoins d'une classe client
 *                 SMTP
 *
 * Remarque    : Ce fichier est basé sur l'implémentation proposée
 *               par Mr Olivier Liechti dans le cadre du cours
 *               RES de la HEIG-VD
 *
 **************************************************************/

package smtp;
import java.io.IOException;
import java.net.InetAddress;
import model.mail.Message;


public interface ISmtpClient {
    public abstract void sendMessage(Message message) throws IOException;
}
