package dao;

import dao.ClientsDao;
import dao.ClientsDaoImpl;
import models.ClientsModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.ClientUtil;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class ClientsDaoImplTest {

    ClientsDao clientsDao;

    public ClientsDaoImplTest() { this.clientsDao = new ClientsDaoImpl(ClientUtil.url, ClientUtil.username, ClientUtil.password);}

    @BeforeEach
    void setUp() {
        ClientUtil.createTable();
    }

    @AfterEach
    void tearDown() {
        ClientUtil.dropTable();
    }

    @Test
    void getAllClientsIT() {
        //arrange
        List<ClientsModel> expectedResult = new ArrayList<>();
        expectedResult.add(new ClientsModel(1,"Joe", "Si", true));
        expectedResult.add(new ClientsModel(2,"Je", "i", true));
        expectedResult.add(new ClientsModel(3,"Jo", "S", true));
        clientsDao.createClient(expectedResult.get(0));
        clientsDao.createClient(expectedResult.get(1));
        clientsDao.createClient(expectedResult.get(2));

        //act
        List<ClientsModel> actualResult = clientsDao.getAllClients();

        //assert
        assertEquals(expectedResult,actualResult);
    }

    @Test
    void getOneClientIT(){
        //arrange
        List<ClientsModel> clientlist = new ArrayList<>();
        clientlist.add(new ClientsModel(1,"Joe", "Si", true));
        clientlist.add(new ClientsModel(2,"Je", "i", true));
        clientlist.add(new ClientsModel(3,"Jo", "S", true));
        clientsDao.createClient(clientlist.get(0));
        clientsDao.createClient(clientlist.get(1));
        clientsDao.createClient(clientlist.get(2));
        ClientsModel expectedResult = clientlist.get(0);

        //act
        ClientsModel actualResult = clientsDao.getOneClient(1);

        //assert
        assertEquals(expectedResult,actualResult);

    }

    @Test
    void createClientIT() {

        List<ClientsModel> expectedResult = new ArrayList<>();
        expectedResult.add(new ClientsModel(1,"Joe", "Si", true));
        expectedResult.add(new ClientsModel(2,"Je", "i", true));
        expectedResult.add(new ClientsModel(3,"Jo", "S", true));
        clientsDao.createClient(expectedResult.get(0));
        clientsDao.createClient(expectedResult.get(1));
        clientsDao.createClient(expectedResult.get(2));

        Integer actualResult = clientsDao.getAllClients().size();

        assertEquals(expectedResult.size(),actualResult);

    }

    @Test
    void updateClientIT() {

        ClientsModel clientToPass = new ClientsModel(1,"Joe", "Si", true);
        ClientsModel expectedResult = new ClientsModel(1,"Joe", "Si", false);
        clientsDao.createClient(clientToPass);

        clientsDao.updateClient(clientToPass.getClient_id());
        ClientsModel actualResult = clientsDao.getOneClient(clientToPass.getClient_id());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void deleteClientIT() {

        List<ClientsModel> expectedResult = new ArrayList<>();
        expectedResult.add(new ClientsModel(1,"Joe", "Si", true));
        expectedResult.add(new ClientsModel(2,"Je", "i", true));
        expectedResult.add(new ClientsModel(3,"Jo", "S", true));
        clientsDao.createClient(expectedResult.get(0));
        clientsDao.createClient(expectedResult.get(1));
        clientsDao.createClient(expectedResult.get(2));

        clientsDao.deleteClient(2);
        expectedResult.remove(1);

        List<ClientsModel> actualResult = clientsDao.getAllClients();

        assertEquals(expectedResult, actualResult);
    }

}
