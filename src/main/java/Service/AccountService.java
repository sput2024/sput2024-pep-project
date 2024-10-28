package Service;

import DAO.AccountDAO;
import Model.Account;
import io.javalin.Javalin;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

public class AccountService {
    public AccountDAO accountDAO;

    /**
     * No-args constructor for AccountService which creates a AccountDAO.
     * There is no need to change this constructor.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
}

/**
     * Constructor for a AccountService when a AccountDAO is provided.
     * This is used for when a mock AccountDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AccountService independently of AccountDAO.
     * There is no need to modify this constructor.
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

     /**
     * TODO: Use the accountDAO to retrieve all accounts.
     * @return all accounts.
     */
  
   public Account addAccount(Account account) { 
        //the account object does not contain an account_Id
       if (account.getUsername().isEmpty()==false && account.getPassword().length()>=4 && accountDAO.getAccountByUsername(account.username).isEmpty()){
             
           return accountDAO.addAccount(account);
             
        }  
       
        return null;
       
      
   }


   public Account verifyLogin(Account account) { 
     
       
    if (accountDAO.verifyLogin(account.username, account.password).isEmpty()==false){
            return accountDAO.verifyLogin(account.username, account.password).get(0);
    }
     return null;
    
   
}


}