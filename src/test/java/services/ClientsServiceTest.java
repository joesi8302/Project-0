package services;

import dao.ClientsDao;
import models.ClientsModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientsServiceTest {

    ClientsDao clientsDao = Mockito.mock(ClientsDao.class);

    ClientsService clientsService;

    public ClientsServiceTest(){ this.clientsService = new ClientsService(clientsDao);}

    @Test
    void getAllClients() {

        List<ClientsModel> clients = new ArrayList<>();
        clients.add(new ClientsModel(1,"Joe", "Si", true));
        clients.add(new ClientsModel(2,"Je", "S", true));
        clients.add(new ClientsModel(3,"Jo", "i", true));

        List<ClientsModel> expectedResult = clients;
        Mockito.when(clientsDao.getAllClients()).thenReturn(clients);

        List<ClientsModel> actualResult = clientsService.getAllClients();

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void getOneClient() {

        ClientsModel expectedResult = new ClientsModel(1,"Joe", "Si", true);
        Mockito.when(clientsDao.getOneClient(expectedResult.getClient_id())).thenReturn(expectedResult);

        ClientsModel actualResult = clientsService.getOneClient(expectedResult.getClient_id());

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void createClient() {

        ClientsModel clientToTest = new ClientsModel(1,"Joe", "Si", true);

        clientsService.createClient(clientToTest);

        Mockito.verify(clientsDao, Mockito.times(1)).createClient(clientToTest);

    }

    @Test
    void updateClient() {

        ClientsModel client = new ClientsModel(1,"Joe", "Si", true);
        Mockito.when(clientsDao.updateClient(client.getClient_id())).thenReturn(1);
        Integer expectedResult = 1;

        Integer actualResult = clientsService.updateClient(client.getClient_id());

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void deleteClient() {

        ClientsModel client = new ClientsModel(1,"Joe", "Si", true);
        Mockito.when(clientsDao.deleteClient(client.getClient_id())).thenReturn(1);
        Integer expectedResult = 1;

        Integer actualResult = clientsService.deleteClient(client.getClient_id());

        assertEquals(expectedResult,actualResult);

    }
}