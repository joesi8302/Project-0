package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import models.AccountsModel;
import models.BalanceModel;
import services.AccountsService;

import java.util.List;

public class AccountsController {

    static AccountsService accountsService = new AccountsService();

    /*public static void getAllAccounts(Context context) throws JsonProcessingException{

        Integer client_id = Integer.parseInt(context.pathParam("client_id"));
        context.contentType("application/json");

        List<AccountsModel> accountsList = accountsService.getAllAccounts(client_id);

        if(accountsList.toString().equals("[]")){
            context.status(404);
        }
        else {
            String jsonString = new ObjectMapper().writeValueAsString(accountsList);

            context.result(jsonString);
        }

    }*/

    public static void getOneAccount(Context context) throws JsonProcessingException{

        Integer client_id = Integer.parseInt(context.pathParam("client_id"));
        Integer account_id = Integer.parseInt(context.pathParam("account_id"));
        context.contentType("application/json");

        AccountsModel account = accountsService.getOneAccount(client_id,account_id);

        if(account == null){
            context.status(404);
        }
        else{
            context.result(new ObjectMapper().writeValueAsString(account));
        }
    }

    public static void createAccount(Context context) throws JsonProcessingException{

        Integer client_id = Integer.parseInt(context.pathParam("client_id"));

        AccountsModel account = context.bodyAsClass(AccountsModel.class);

        context.status(201);
        accountsService.createAccount(account, client_id);
        context.result("Account with client id: " + client_id + " has been created");

    }

    public static void updateAccount(Context context) throws JsonProcessingException{
        Integer client_id = Integer.parseInt(context.pathParam("client_id"));
        Integer account_id = Integer.parseInt(context.pathParam("account_id"));

        if(accountsService.updateAccount(client_id, account_id) == 0){
            context.status(404);
        }
        else
        {
            accountsService.updateAccount(client_id,account_id);
            context.result("Account with client id: " + client_id.toString() + " open status has changed");
        }
    }

    public static void deleteAccount(Context context) throws JsonProcessingException{
        Integer client_id = Integer.parseInt(context.pathParam("client_id"));
        Integer account_id = Integer.parseInt(context.pathParam("account_id"));

        Integer check = accountsService.deleteAccount(client_id, account_id);

        if(check == 0){
            context.status(404);
        }
        else
        {
            context.result("Account with client id: " + client_id.toString() + " has been deleted");
        }
    }

    public static void getAllAccounts(Context context) throws JsonProcessingException{
        Integer client_id = Integer.parseInt(context.pathParam("client_id"));
        List<AccountsModel> accountsList = accountsService.getAllAccounts(client_id);
        if(context.queryParam("amountLessThan") == null || context.queryParam("amountGreaterThan") == null){
            accountsList = accountsService.getAllAccounts(client_id);
        }
        else {
            Integer amountLessThan = Integer.parseInt(context.queryParam("amountLessThan"));
            Integer amountGreaterThan = Integer.parseInt(context.queryParam("amountGreaterThan"));
            accountsList = accountsService.getAllAccounts(client_id, amountLessThan, amountGreaterThan);
        }
        context.contentType("application/json");

        if(accountsList.toString().equals("[]")){
            context.status(404);
        }
        else {
            String jsonString = new ObjectMapper().writeValueAsString(accountsList);

            context.result(jsonString);
        }
    }

    public static void changeBalance(Context context) throws JsonProcessingException {

        Integer client_id = Integer.parseInt(context.pathParam("client_id"));
        Integer account_id = Integer.parseInt(context.pathParam("account_id"));
        BalanceModel balance = context.bodyAsClass(BalanceModel.class);
        context.contentType("application/json");

        int check = accountsService.changeBalance(client_id, account_id, balance);

        if(check == -1){
            context.status(422);
        }
        else if (check == 0){
            context.status(404);
        }
        else{
            context.status(200);
        }

    }

    public static void transfer(Context context) throws JsonProcessingException {

        Integer client_id = Integer.parseInt(context.pathParam("client_id"));
        Integer account_id = Integer.parseInt(context.pathParam("account_id"));
        Integer account_id2 = Integer.parseInt(context.pathParam("account_id2"));
        BalanceModel balance = context.bodyAsClass(BalanceModel.class);
        context.contentType("application/json");

        int check = accountsService.transfer(client_id, account_id, account_id2, balance);
        if(check == -1){
            context.status(422);
        }
        else if (check == 0){
            context.status(404);
        }
        else{
            context.status(200);
        }

    }




}
