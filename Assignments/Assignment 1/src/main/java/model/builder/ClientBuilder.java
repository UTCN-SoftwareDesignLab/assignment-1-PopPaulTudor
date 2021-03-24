package model.builder;

import model.Client;

public class ClientBuilder {

    private final Client client;

    public ClientBuilder() {
        this.client = new Client();
    }


    public ClientBuilder setAddress(String address) {
        client.setAddress(address);
        return this;
    }

    public ClientBuilder setName(String name) {
        client.setName(name);
        return this;
    }

    public ClientBuilder setId(long id) {
        client.setId(id);
        return this;
    }

    public ClientBuilder setPassword(String password) {
        client.setPassword(password);
        return this;
    }

    public Client build() {
        return client;
    }

}
