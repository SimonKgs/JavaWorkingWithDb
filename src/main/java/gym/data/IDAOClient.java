package gym.data;

import gym.domain.Client;

import java.util.List;

public interface IDAOClient {

    List<Client> listClients();
    boolean searchClientById(Client client);
    boolean addNewClient(Client client);
    boolean updateClient(Client client);
    boolean deleteClient(Client client);

}
