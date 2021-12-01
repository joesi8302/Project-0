package frontcontroller;

import controllers.AccountsController;
import controllers.ClientsController;
import io.javalin.Javalin;

import javax.servlet.DispatcherType;

public class Dispatcher {

    public Dispatcher(Javalin app){

        app.get("/clients", ClientsController::getAllTodos );

        app.get("/clients/{client_id}", ClientsController::getOneClient);

        app.post("/clients", ClientsController::createClient);

        app.put("/clients/{client_id}", ClientsController::updateClient);

        app.delete("/clients/{client_id}", ClientsController::deleteClient);


        app.get("/clients/{client_id}/accounts", AccountsController::getAllAccounts);

        app.get("/clients/{client_id}/accounts/{account_id}", AccountsController::getOneAccount);

        app.post("/clients/{client_id}/accounts", AccountsController::createAccount);

        app.put("/clients/{client_id}/accounts/{account_id}", AccountsController::updateAccount);

        app.delete("/clients/{client_id}/accounts/{account_id}", AccountsController::deleteAccount);

        app.patch("/clients/{client_id}/accounts/{account_id}", AccountsController::changeBalance);

        app.patch("/clients/{client_id}/accounts/{account_id}/transfer/{account_id2}", AccountsController::transfer);




    }

}
