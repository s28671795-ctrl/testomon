/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.clients;

import com.google.inject.Inject;
import com.incoresoft.clients.Client;
import com.incoresoft.clients.exceptions.CannotDeleteClientWithActiveUsersException;
import com.incoresoft.database.ClientsRepository;
import com.incoresoft.middleware.model.clients.ClientId;
import com.incoresoft.middleware.model.user.UsersManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ClientsManager {
    private final ClientsRepository repository;
    private final UsersManager usersManager;

    public boolean exists(int clientId) {
        return this.repository.exists(clientId);
    }

    public Optional<Client> get(int id) {
        return this.repository.get(id);
    }

    public List<Client> getClients() {
        ArrayList<Client> clients = new ArrayList<Client>();
        clients.add(Client.DEFAULT);
        clients.addAll(this.repository.getAll());
        return Collections.unmodifiableList(clients);
    }

    public Client add(Client client) throws IllegalArgumentException {
        if (Client.DEFAULT.getId().equals(client.getId())) {
            throw new IllegalArgumentException("Cannot add or modify default client.");
        }
        return this.repository.add(client);
    }

    public Client update(Client client) throws IllegalArgumentException {
        if (Client.DEFAULT.getId().equals(client.getId())) {
            throw new IllegalArgumentException("Cannot add or modify default client.");
        }
        return this.repository.update(client);
    }

    public void delete(Integer clientId) {
        if (Client.DEFAULT.getId().equals(clientId)) {
            throw new IllegalArgumentException("Cannot delete default client.");
        }
        if (this.clientHasActiveUsers(clientId)) {
            throw new CannotDeleteClientWithActiveUsersException();
        }
        this.repository.delete(clientId);
    }

    public boolean clientHasActiveUsers(int clientId) {
        return this.usersManager.getAll().stream().mapToInt(ClientId::getClientId).anyMatch(it -> it == clientId);
    }

    @Inject
    public ClientsManager(ClientsRepository repository, UsersManager usersManager) {
        this.repository = repository;
        this.usersManager = usersManager;
    }
}

