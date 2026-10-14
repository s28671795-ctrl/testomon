/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.client.finder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.client.IndirectClient;
import org.pac4j.core.client.finder.ClientFinder;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCallbackClientFinder
implements ClientFinder {
    private static final Logger logger = LoggerFactory.getLogger(DefaultCallbackClientFinder.class);

    @Override
    public List<Client> find(Clients clients, WebContext context, String clientNames) {
        Optional<Client> defaultClient;
        ArrayList<Client> result = new ArrayList<Client>();
        ArrayList<Client> indirectClients = new ArrayList<Client>();
        for (Client client : clients.findAllClients()) {
            if (!(client instanceof IndirectClient)) continue;
            IndirectClient indirectClient = (IndirectClient)client;
            indirectClients.add(client);
            indirectClient.init();
            if (!indirectClient.getCallbackUrlResolver().matches(indirectClient.getName(), context)) continue;
            result.add(indirectClient);
        }
        logger.debug("result: {}", (Object)result.stream().map(Client::getName).collect(Collectors.toList()));
        if (result.isEmpty() && CommonHelper.isNotBlank(clientNames) && (defaultClient = clients.findClient(clientNames)).isPresent()) {
            logger.debug("Defaulting to the configured client: {}", (Object)defaultClient);
            result.add(defaultClient.get());
        }
        if (result.isEmpty() && indirectClients.size() == 1) {
            logger.debug("Defaulting to the only client: {}", indirectClients.get(0));
            result.addAll(indirectClients);
        }
        return result;
    }
}

