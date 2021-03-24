package service;

import Utills.FieldUtills;
import model.Client;
import model.builder.ClientBuilder;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.ClientRepository;

public class ClientManagementService {

    private final ClientRepository clientRepository;

    public ClientManagementService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Notification<Boolean> addClient(String name, String address, String password) {
        Notification<Boolean> notification = new Notification<>();
        Client client = new ClientBuilder()
                .setName(name)
                .setPassword(password)
                .setAddress(address)
                .build();

        ClientValidator clientValidator = new ClientValidator(client);

        if (!clientValidator.validate()) {
            clientValidator.getErrors().forEach(notification::addError);
            notification.setResult(false);

        } else {
            client.setPassword(FieldUtills.encodePassword(password));
            notification.setResult(clientRepository.save(client));
        }
        return notification;
    }


    public Notification<Boolean> updateClient(String name, String newPassword, String newAddress) {

        Notification<Boolean> notification = new Notification<>();
        Notification<Client> findClient = clientRepository.findClient(name);

        if (!findClient.hasErrors()) {
            return clientRepository.update(name, FieldUtills.encodePassword(newPassword), newAddress);

        } else {
            notification.addError(findClient.getFormattedErrors());
        }

        return notification;
    }


    public Notification<String> retrieveAllClients() {
        return clientRepository.retrieve();
    }
}
