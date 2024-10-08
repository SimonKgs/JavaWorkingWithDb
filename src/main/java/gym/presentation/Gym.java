package gym.presentation;

import gym.data.DAOClient;
import gym.data.IDAOClient;
import gym.domain.Client;

import java.util.List;
import java.util.Scanner;

public class Gym {

    public static void main(String[] args) {
        gymService();
    }

    private static void gymService() {
        boolean exit = false;
        Scanner scan = new Scanner(System.in);
        // the service
        IDAOClient daoClient = new DAOClient();

        while (!exit) {
            try {
                int option = showMenu(scan);
                exit = executeOptions(option, scan, daoClient);
            } catch (Exception e) {
                System.out.println("There was an error: " + e.getMessage());
            } finally {
                System.out.println();
            }
        }
    }

    private static int showMenu(Scanner scan) {

        System.out.println("""
                ====================
                1. See clients
                2. Search client
                3. Add client
                4. Update client
                5. Delete client
                6. Exit
                ====================
                Select an option:\s
                """);
        // read and return selected option
        return Integer.parseInt(scan.nextLine());
    }


    private static boolean executeOptions(int option, Scanner scan, IDAOClient daoClient) {
        boolean exit = false;
        switch (option) {
            case 1 -> displayClientList();
            case 2 -> searchClientById(daoClient, scan);
            case 3 -> addNewClient(daoClient, scan);
            case 4 -> updateClient(daoClient, scan);
            case 5 -> deleteClient(daoClient, scan);
            case 6 -> {
                System.out.println("Bye");
                exit = true;
            }
        }
        return exit;
    }

    private static void displayClientList() {
        // testing get list of clients
        System.out.println(" List of clients ");
        IDAOClient daoClient = new DAOClient();
        List<Client> clients = daoClient.listClients();
        clients.forEach(System.out::println);
    }


    private static void searchClientById(IDAOClient daoClient, Scanner scan) {

        System.out.println("=====Searching client========");
        System.out.println("Insert client id:");
        int clientId = Integer.parseInt(scan.nextLine());

        Client client = new Client(clientId);

        boolean clientExists = daoClient.searchClientById(client);

        if (clientExists)
            System.out.println("Client found: " + client);
        else
            System.out.println("Client not found id: " + client.getId());


    }


    private static void addNewClient(IDAOClient daoClient, Scanner scan) {
        System.out.println("======== Adding new client ========");
        System.out.println("Insert name: ");
        String name = scan.nextLine();

        System.out.println("Insert lastname: ");
        String lastname = scan.nextLine();

        System.out.println("Insert membership number: ");
        int membership = Integer.parseInt(scan.nextLine());

        Client clientToAdd = new Client(name, lastname, membership);

        boolean clientWasAdded = daoClient.addNewClient(clientToAdd);

        if (clientWasAdded)
            System.out.println("Client added successfully: " + clientToAdd);
        else
            System.out.println("the client was not added");
    }


    private static void updateClient(IDAOClient daoClient, Scanner scan) {
        System.out.println("======== Updating client ========");
        System.out.println("Insert Id of the client to update");
        int clientId = Integer.parseInt(scan.nextLine());

        System.out.println("Insert name: ");
        String name = scan.nextLine();

        System.out.println("Insert lastname: ");
        String lastname = scan.nextLine();

        System.out.println("Insert membership number: ");
        int membership = Integer.parseInt(scan.nextLine());

        Client clientToUpdate = new Client(clientId,name, lastname, membership);

        boolean clientWasUpdated = daoClient.updateClient(clientToUpdate);

        if (clientWasUpdated)
            System.out.println("Client updated successfully: " + clientToUpdate);
        else
            System.out.println("the client was not updated");

    }

    private static void deleteClient(IDAOClient daoClient, Scanner scan) {
        System.out.println("=====Deleting client========");
        System.out.println("Insert the id to delete the client:");
        int clientId = Integer.parseInt(scan.nextLine());

        Client clientToDelete = new Client(clientId);

        boolean clientWasDeleted = daoClient.deleteClient(clientToDelete);

        if (clientWasDeleted) {
            System.out.println("Client deleted with id " + clientToDelete.getId());
        } else {
            System.out.println("Client not found, id:" + clientToDelete.getId());
        }
    }
}






