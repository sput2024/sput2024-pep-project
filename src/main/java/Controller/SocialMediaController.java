package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */


import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import io.javalin.Javalin;
import io.javalin.http.Context;

import static org.mockito.ArgumentMatchers.nullable;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    AccountService AccountService;
    MessageService MessageService;

    public SocialMediaController(){
        this.AccountService = new AccountService();
        this.MessageService = new MessageService();
    }
    public Javalin startAPI() {                    //endpoints
        Javalin app = Javalin.create();
    //   // app.get("example-endpoint", this::exampleHandler);
        app.post( "/register", this::registerHandler);
        app.post("/login", this::verifyLoginHandler);
        app.post("/messages",this::processNewMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
          app.get( "/accounts/{account_id}/messages", this::getMessagesByUserHandler);
      //  app.start(8080);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    //private void exampleHandler(Context context) {
    //    context.json("sample text");
   // }
  

private void registerHandler(Context ctx) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    Account account = mapper.readValue(ctx.body(), Account.class);
    Account addedAccount = AccountService.addAccount(account);
    
    if(addedAccount!=null){
        ctx.json(mapper.writeValueAsString(addedAccount));
      //  ctx.status(200);
    }else{
        ctx.status(400);
    }
}

private void verifyLoginHandler(Context ctx) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    Account account = mapper.readValue(ctx.body(), Account.class);
   
    Account loginSuccess = AccountService.verifyLogin(account);
  
    if(loginSuccess!=null){
       
        ctx.json(mapper.writeValueAsString(loginSuccess));
        ctx.status(200);
    }
    if(loginSuccess==null){
        ctx.status(401);
    }
    

}

private void processNewMessagesHandler(Context ctx) throws JsonProcessingException {
    ObjectMapper om = new ObjectMapper();
    Message message = om.readValue(ctx.body(), Message.class);
   
    Message processNewMessage = MessageService.processNewMessages(message);
    
    if(processNewMessage!=null){
       
        ctx.json(om.writeValueAsString(processNewMessage));
        ctx.status(200);
    }
    if(processNewMessage==null){
        ctx.status(400);
    }
}

/*** @param ctx the context object handles information HTTP requests and generates responses within Javalin. 
 * The Jackson ObjectMapper will automatically convert the JSON of the POST request into a Book object. */

private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {

    List<Message> getAllMessages = new ArrayList<>();
    getAllMessages = MessageService.getAllMessages();
    ctx.json(getAllMessages);

}

private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
   String val = ctx.pathParam("message_id");

   int val1= Integer.parseInt(val);
    Message getAllMessages = MessageService.getAllMessagesById(val1);
  
 //  System.out.println(getAllMessages);

    
    if(getAllMessages!=null){
     ctx.json(getAllMessages);
     ctx.status(200);
    
  }
  if(getAllMessages==null){
      ctx.status(200);
   }

}

private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {
    String val = ctx.pathParam("message_id");
 
    int val1= Integer.parseInt(val);
     Message getAllMessages = MessageService.deleteMessageById(val1);
   
 
     
     if(getAllMessages!=null){
      ctx.json(getAllMessages);
      ctx.status(200);
     
   }
   if(getAllMessages==null){
       ctx.status(200);
    }
 
 }


 private void getMessagesByUserHandler(Context ctx) throws JsonProcessingException {
    String val = ctx.pathParam("account_id");

    if (val==null){
        ctx.status(200);
       }
    List<Message> getAllMessages = new ArrayList<>();
    if(val!=null){
        int val1= Integer.parseInt(val);
    
        getAllMessages = MessageService.getAllMessagesByUser(val1);
        System.out.println("testing");
        if(getAllMessages!=null){
            ctx.json(getAllMessages);
            ctx.status(200);
           
         }
         if(getAllMessages==null){
             ctx.status(200);
          }
     }

}

private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException {
    ObjectMapper om = new ObjectMapper();
    Message message1 = om.readValue(ctx.body(), Message.class);

    String val = ctx.pathParam("message_id");

    if (val==null){
        ctx.status(400);
       }
   
    if(val!=null){
        int val1= Integer.parseInt(val);
    
        Message getAllMessages = MessageService.updateMessageById(val1,message1.message_text);
      
        if(getAllMessages!=null){
            ctx.json(getAllMessages);
            ctx.status(200);
           
         }
         if(getAllMessages==null){
             ctx.status(400);
          }
     }

}


}


