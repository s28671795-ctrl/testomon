/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.pac4j.core.authorization.generator.AuthorizationGenerator;
import org.pac4j.core.client.BaseClient;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.IndirectClient;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.http.ajax.AjaxRequestResolver;
import org.pac4j.core.http.callback.CallbackUrlResolver;
import org.pac4j.core.http.url.UrlResolver;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.InitializableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Clients
extends InitializableObject {
    private static final Logger LOGGER = LoggerFactory.getLogger(Clients.class);
    private volatile List<Client> clients = new ArrayList<Client>();
    private Map<String, Client> clientsMap;
    private volatile Integer oldClientsHash;
    private String callbackUrl;
    private AjaxRequestResolver ajaxRequestResolver;
    private UrlResolver urlResolver;
    private CallbackUrlResolver callbackUrlResolver;
    private List<AuthorizationGenerator> authorizationGenerators = new ArrayList<AuthorizationGenerator>();
    private String defaultSecurityClients;

    public Clients() {
    }

    public Clients(String callbackUrl, List<Client> clients) {
        this.setCallbackUrl(callbackUrl);
        this.setClients(clients);
    }

    public Clients(String callbackUrl, Client ... clients) {
        this.setCallbackUrl(callbackUrl);
        this.setClients(clients);
    }

    public Clients(List<Client> clients) {
        this.setClients(clients);
    }

    public Clients(Client ... clients) {
        this.setClients(clients);
    }

    @Override
    protected boolean shouldInitialize(boolean forceReinit) {
        if (forceReinit) {
            return true;
        }
        return this.oldClientsHash == null || this.oldClientsHash.intValue() != this.clients.hashCode();
    }

    @Override
    protected void internalInit(boolean forceReinit) {
        this.clientsMap = new HashMap<String, Client>();
        for (Client client : this.clients) {
            String name = client.getName();
            CommonHelper.assertNotBlank("name", name);
            String lowerTrimmedName = name.toLowerCase().trim();
            if (this.clientsMap.containsKey(lowerTrimmedName)) {
                throw new TechnicalException("Duplicate name in clients: " + name);
            }
            this.clientsMap.put(lowerTrimmedName, client);
            if (client instanceof IndirectClient) {
                IndirectClient indirectClient = (IndirectClient)client;
                if (this.callbackUrl != null && indirectClient.getCallbackUrl() == null) {
                    indirectClient.setCallbackUrl(this.callbackUrl);
                }
                if (this.urlResolver != null && indirectClient.getUrlResolver() == null) {
                    indirectClient.setUrlResolver(this.urlResolver);
                }
                if (this.callbackUrlResolver != null && indirectClient.getCallbackUrlResolver() == null) {
                    indirectClient.setCallbackUrlResolver(this.callbackUrlResolver);
                }
                if (this.ajaxRequestResolver != null && indirectClient.getAjaxRequestResolver() == null) {
                    indirectClient.setAjaxRequestResolver(this.ajaxRequestResolver);
                }
            }
            BaseClient baseClient = (BaseClient)client;
            if (this.authorizationGenerators.isEmpty()) continue;
            baseClient.addAuthorizationGenerators(this.authorizationGenerators);
        }
        this.oldClientsHash = this.clients.hashCode();
    }

    public Optional<Client> findClient(String name) {
        CommonHelper.assertNotBlank("name", name);
        this.init();
        Client foundClient = this.clientsMap.get(name.toLowerCase().trim());
        LOGGER.debug("Found client: {} for name: {}", (Object)foundClient, (Object)name);
        return Optional.ofNullable(foundClient);
    }

    @Deprecated
    public <C extends Client> Optional<C> findClient(Class<C> clazz) {
        CommonHelper.assertNotNull("clazz", clazz);
        this.init();
        Client foundClient = null;
        for (Client client : this.getClients()) {
            if (!clazz.isAssignableFrom(client.getClass())) continue;
            foundClient = client;
            break;
        }
        LOGGER.debug("Found client: {} for class: {}", (Object)foundClient, (Object)clazz);
        return Optional.ofNullable(foundClient);
    }

    public List<Client> findAllClients() {
        this.init();
        return this.getClients();
    }

    public String getCallbackUrl() {
        return this.callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public void addClient(Client client) {
        this.clients.add(client);
    }

    public void setClients(List<Client> clients) {
        CommonHelper.assertNotNull("clients", clients);
        this.clients = clients;
    }

    public void setClients(Client ... clients) {
        CommonHelper.assertNotNull("clients", clients);
        this.setClients(new ArrayList<Client>(Arrays.asList(clients)));
    }

    public List<Client> getClients() {
        return this.clients;
    }

    public AjaxRequestResolver getAjaxRequestResolver() {
        return this.ajaxRequestResolver;
    }

    public void setAjaxRequestResolver(AjaxRequestResolver ajaxRequestResolver) {
        this.ajaxRequestResolver = ajaxRequestResolver;
    }

    public CallbackUrlResolver getCallbackUrlResolver() {
        return this.callbackUrlResolver;
    }

    public void setCallbackUrlResolver(CallbackUrlResolver callbackUrlResolver) {
        this.callbackUrlResolver = callbackUrlResolver;
    }

    public List<AuthorizationGenerator> getAuthorizationGenerators() {
        return this.authorizationGenerators;
    }

    public void setAuthorizationGenerators(List<AuthorizationGenerator> authorizationGenerators) {
        CommonHelper.assertNotNull("authorizationGenerators", authorizationGenerators);
        this.authorizationGenerators = authorizationGenerators;
    }

    public void setAuthorizationGenerators(AuthorizationGenerator ... authorizationGenerators) {
        CommonHelper.assertNotNull("authorizationGenerators", authorizationGenerators);
        this.authorizationGenerators = Arrays.asList(authorizationGenerators);
    }

    public void setAuthorizationGenerator(AuthorizationGenerator authorizationGenerator) {
        this.addAuthorizationGenerator(authorizationGenerator);
    }

    public void addAuthorizationGenerator(AuthorizationGenerator authorizationGenerator) {
        CommonHelper.assertNotNull("authorizationGenerator", authorizationGenerator);
        this.authorizationGenerators.add(authorizationGenerator);
    }

    public String getDefaultSecurityClients() {
        return this.defaultSecurityClients;
    }

    public void setDefaultSecurityClients(String defaultSecurityClients) {
        this.defaultSecurityClients = defaultSecurityClients;
    }

    public UrlResolver getUrlResolver() {
        return this.urlResolver;
    }

    public void setUrlResolver(UrlResolver urlResolver) {
        this.urlResolver = urlResolver;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "callbackUrl", this.callbackUrl, "clients", this.getClients(), "ajaxRequestResolver", this.ajaxRequestResolver, "callbackUrlResolver", this.callbackUrlResolver, "authorizationGenerators", this.authorizationGenerators, "defaultSecurityClients", this.defaultSecurityClients, "urlResolver", this.urlResolver);
    }
}

