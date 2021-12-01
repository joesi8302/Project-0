package services;

import dao.ClientsDao;
import dao.ClientsDaoImpl;
import models.ClientsModel;

import java.util.List;

public class ClientsService {

    ClientsDao clientsDao;

    public ClientsService(ClientsDao clientsDao){ this.clientsDao = clientsDao;}

    public ClientsService(){this.clientsDao = new ClientsDaoImpl();}

    public List<ClientsModel> getAllClients() { return clientsDao.getAllClients();}

    public ClientsModel getOneClient(Integer client_id){
        return clientsDao.getOneClient(client_id);
    }

    public void createClient(ClientsModel client){ clientsDao.createClient(client); }

    public Integer updateClient(Integer client_id){ return clientsDao.updateClient(client_id);}

    public Integer deleteClient(Integer client_id){ return clientsDao.deleteClient(client_id);}

}
