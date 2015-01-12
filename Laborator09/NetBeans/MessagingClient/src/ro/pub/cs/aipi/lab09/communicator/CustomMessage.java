package ro.pub.cs.aipi.lab09.communicator;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class CustomMessage implements Serializable {

    private static final long serialVersionUID = 2014L;

    private String senderUserName;
    private String messageContent;
    private GregorianCalendar messageDateTime;

    public CustomMessage(String senderUserName, String messageContent, GregorianCalendar messageDateTime) {
        this.senderUserName = senderUserName;
        this.messageContent = messageContent;
        this.messageDateTime = messageDateTime;
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public GregorianCalendar getMessageDateTime() {
        return messageDateTime;
    }

    public void setMessageDateTime(GregorianCalendar messageDateTime) {
        this.messageDateTime = messageDateTime;
    }

}
