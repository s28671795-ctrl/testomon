/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.client.finder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.client.finder.ClientFinder;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSecurityClientFinder
implements ClientFinder {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSecurityClientFinder.class);
    private String clientNameParameter = "force_client";

    @Override
    public List<Client> find(Clients clients, WebContext context, String clientNames) {
        ArrayList<Client> result = new ArrayList<Client>();
        String securityClientNames = clientNames;
        logger.debug("Provided clientNames: {}", (Object)securityClientNames);
        if (securityClientNames == null) {
            securityClientNames = clients.getDefaultSecurityClients();
            logger.debug("Default security clients: {}", (Object)securityClientNames);
            if (securityClientNames == null && clients.findAllClients().size() == 1) {
                securityClientNames = clients.getClients().get(0).getName();
                logger.debug("Only client: {}", (Object)securityClientNames);
            }
        }
        if (CommonHelper.isNotBlank(securityClientNames)) {
            List<String> names = Arrays.asList(securityClientNames.split(","));
            Optional<String> clientOnRequest = context.getRequestParameter(this.clientNameParameter);
            logger.debug("clientNameOnRequest: {}", (Object)clientOnRequest);
            if (clientOnRequest.isPresent()) {
                Optional<Client> client = clients.findClient(clientOnRequest.get());
                if (client.isPresent()) {
                    String nameFound = client.get().getName();
                    for (String name : names) {
                        if (!CommonHelper.areEqualsIgnoreCaseAndTrim(name, nameFound)) continue;
                        result.add(client.get());
                        break;
                    }
                }
            } else {
                for (String name : names) {
                    Optional<Client> client = clients.findClient(name);
                    if (!client.isPresent()) continue;
                    result.add(client.get());
                }
            }
        }
        logger.debug("result: {}", (Object)result.stream().map(Client::getName).collect(Collectors.toList()));
        return result;
    }

    public String getClientNameParameter() {
        return this.clientNameParameter;
    }

    public void setClientNameParameter(String clientNameParameter) {
        this.clientNameParameter = clientNameParameter;
    }
}

