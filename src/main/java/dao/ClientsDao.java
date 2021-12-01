package dao;

import models.ClientsModel;

import java.util.List;

public interface ClientsDao {
    List<ClientsModel> getAllClients();
    ClientsModel getOneClient(Integer client_id);
    void createClient(ClientsModel client);
    Integer updateClient(Integer client_id);
    Integer deleteClient(Integer client_id);
}
