package model.mail;

public class Message {
    private String from;
    private String to[] = new String[0];
    private String Cc[] = new String[0];
    private String Bcc[] = new String[0];
    private String subject;
    private String body;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String[] getCc() {
        return Cc;
    }

    public void setCc(String[] cc) {
        Cc = cc;
    }

    public String[] getBcc() {
        return Bcc;
    }

    public void setBcc(String[] bcc) {
        Bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
