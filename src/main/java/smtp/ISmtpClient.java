package smtp;
import java.io.IOException;
import java.net.InetAddress;
import model.mail.Message;


public interface ISmtpClient {
    public abstract void sendMessage(Message message) throws IOException;
}
