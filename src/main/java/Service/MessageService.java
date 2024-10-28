package Service;
import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

import io.javalin.Javalin;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;



public class MessageService {
 public MessageDAO messageDAO;

    /**
     * No-args constructor for MessageService which creates a MessageDAO.
     * There is no need to change this constructor.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
}




public MessageService(MessageDAO messageDAO){
    this.messageDAO = messageDAO;
}


   public Message processNewMessages(Message message) { 
     
       
    if (message.message_text.isEmpty()==false && message.message_text.length() <=255 && AccountDAO.getAccountByAccount_id(message.posted_by).isEmpty()==false){
            return messageDAO.processNewMessage(message);
    }
     return null; 
}

public List<Message> getAllMessages() { 
     
       
    return messageDAO.getAllMessages();
     
}

public Message getAllMessagesById(int message_id) { 
     
       if (message_id>=0){
    return messageDAO.getAllMessagesById(message_id);}
    else
    return null;
     
}

public Message deleteMessageById(int message_id) { 
     
    if (messageDAO.getAllMessagesById(message_id)!=null){
        messageDAO.deleteMessageById(message_id);
    return messageDAO.getAllMessagesById(message_id);}

 if (messageDAO.getAllMessagesById(message_id)==null){
    return null;}

 else
 return null;
  
}

public List<Message> getAllMessagesByUser(int posted_by) { 
     
   
       return messageDAO.getAllMessagesByUser(posted_by);
  
  
}




public Message updateMessageById(int message_id, String message1) { 
     
    if (messageDAO.getAllMessagesById(message_id)!=null&&message1.length()<=255&&message1.length()>0){    //verify if message_id exists
 
        messageDAO.updateMessageById(message_id, message1);
        return messageDAO.getAllMessagesById(message_id);

}
 else
 return null;
  
}





}
