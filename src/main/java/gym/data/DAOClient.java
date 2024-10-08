package gym.data;

import gym.dbconection.DB;
import gym.domain.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOClient implements IDAOClient{
    @Override
    public List<Client> listClients() {
        List<Client> clients = new ArrayList<>();
        // to prepare sentences
        PreparedStatement ps;
        // to receive the answer of the query
        ResultSet rs;
        // making the connection
        Connection con = DB.getConnection();
        // query
        String query = "SELECT * FROM client ORDER BY id";
        // execution
        try {
            // preparing the query
            ps = con.prepareStatement(query);
            // executing and getting values
            rs = ps.executeQuery();
            // iterating over the result values, until there is no more values
            while (rs.next()){
                /*
                   THis is an example adding values line by line
                   but I think this is better to create the client using the constructor of all
                    Client client = new Client();
                    client.setId(rs.getInt("id"));
                    client.setName(rs.getString("name"));
                    client.setLastname(rs.getString("lastname"));
                    client.setMembership(rs.getInt("membership"));
                */
                Client client = new Client(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getInt("membership")
                );
                clients.add(client);
            }

        } catch (Exception e) {
            System.out.println("There was an error retrieving client list: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing the connection: " + e.getMessage());
            }
        }

        return clients;
    }

    @Override
    public boolean searchClientById(Client client) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = DB.getConnection();
        // ? symbol is a placeholder that later I will substitute with a value
        // to do that I need to use ps.setInt(index, value)
        // setType accept any type expected
        // this is good practice to prevents sql injections
        String query = "SELECT * FROM client WHERE id = ?";

        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, client.getId());
            rs = ps.executeQuery();

            if (rs.next()) {
                client.setName(rs.getString("name"));
                client.setLastname(rs.getString("lastname"));
                client.setMembership(rs.getInt("membership"));
                return true;
            }

        } catch (Exception e) {
            System.out.println("There was an error getting the client by id: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e ) {
                System.out.println("Error closing the connection: " + e.getMessage());
            }
        }

        return false;
    }

    @Override
    public boolean addNewClient(Client client) {
        PreparedStatement ps;
        Connection con = DB.getConnection();
        String query = "INSERT INTO client (name, lastname, membership) VALUES (?, ?, ?);";

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, client.getName());
            ps.setString(2, client.getLastname());
            ps.setInt(3, client.getMembership());

            ps.execute();

            System.out.println("Client added");
            return true;

        } catch (Exception e) {
            System.out.println("There was an error adding the client: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e ) {
                System.out.println("Error closing the connection: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean updateClient(Client client) {
        PreparedStatement ps;
        Connection con = DB.getConnection();
        String query = "Update client set name=?, lastname=?, membership=? where id = ?;";

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, client.getName());
            ps.setString(2, client.getLastname());
            ps.setInt(3, client.getMembership());
            ps.setInt(4, client.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Client Updated");
                return true;
            } else {
                System.out.println("No client found with the given ID.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("There updating client: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e ) {
                System.out.println("Error closing the connection: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean deleteClient(Client client) {
        PreparedStatement ps;
        Connection con = DB.getConnection();
        String query = "DELETE FROM client WHERE id = ?;";

        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, client.getId());
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Client Deleted");
                return true;
            } else {
                System.out.println("No client found with the given ID.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("There was an error deleting the client: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e ) {
                System.out.println("Error closing the connection: " + e.getMessage());
            }
        }

        return false;
    }

    // local test
    public static void main(String[] args) {
        // testing get list of clients
        System.out.println(" List of clients ");
        IDAOClient daoClient = new DAOClient();

        List<Client> clients = daoClient.listClients();
        clients.forEach(System.out::println);

        // testing get client by id
        Client client1 = new Client(1);
        System.out.println("client1 before search = " + client1);

        boolean clientExists = daoClient.searchClientById(client1);

        if (clientExists) {
            System.out.println("Client found");
            System.out.println("Client after search = " + client1);
        } else {
            System.out.println("Client not found, id:" + client1.getId());
        }

        // testing add new client
        Client clientToAdd = new Client("Juna", "Suarez", 121);

        boolean clientWasAdded = daoClient.addNewClient(clientToAdd);

        if (clientWasAdded)
            System.out.println("Client added successfully: " + clientToAdd);
        else
            System.out.println("the client was not added");

        // testing updateClient
        Client clientToUpdate = new Client(5,"Luna", "Castro", 181);

        boolean clientWasUpdated = daoClient.updateClient(clientToUpdate);

        if (clientWasUpdated)
            System.out.println("Client updated successfully: " + clientToUpdate);
        else
            System.out.println("the client was not updated");

        // testing delete client
        Client clientToDelete = new Client(9);

        boolean clientWasDeleted = daoClient.deleteClient(clientToDelete);

        if (clientWasDeleted) {
            System.out.println("Client deleted with id " + clientToDelete.getId());
        } else {
            System.out.println("Client not found, id:" + clientToDelete.getId());
        }
    }
}
