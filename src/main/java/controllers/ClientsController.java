package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import models.ClientsModel;
import services.ClientsService;

import javax.xml.bind.ValidationEvent;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ClientsController {

    static ClientsService clientsService = new ClientsService();

    public static void getAllTodos(Context context) throws JsonProcessingException{
        context.contentType("application/json");

        List<ClientsModel> clientsList = clientsService.getAllClients();

        String jsonString = new ObjectMapper().writeValueAsString(clientsList);

        context.result(jsonString);
    }

    public static void getOneClient(Context context) throws JsonProcessingException{

        Integer client_id = Integer.parseInt(context.pathParam("client_id"));
        context.contentType("application/json");

        ClientsModel client = clientsService.getOneClient(client_id);

        if(client == null){
            context.status(404);
        }
        else{
            context.result(new ObjectMapper().writeValueAsString(client));
        }
    }

    public static void createClient(Context context) throws JsonProcessingException{

        ClientsModel client = context.bodyAsClass(ClientsModel.class);

        context.status(201);
        clientsService.createClient(client);
        context.result("Client Has been created");

    }

    public static void updateClient(Context context){
        Integer client_id = Integer.parseInt(context.pathParam("client_id"));

        if(clientsService.updateClient(client_id) == 0){
            context.status(404);
        }
        else
        {
            clientsService.updateClient(client_id);
            context.result("Client with id " + client_id.toString() + " active status has changed");
        }
    }

    public static void deleteClient(Context context){
        Integer client_id = Integer.parseInt(context.pathParam("client_id"));

        if(clientsService.deleteClient(client_id) == 0){
            context.status(404);
        }
        else
        {
            context.status(205);
            clientsService.deleteClient(client_id);
            context.result("Deleted client with id " + client_id.toString() + " if exists");
        }


    }


}
