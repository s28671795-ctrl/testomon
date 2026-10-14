/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.util.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import org.pac4j.core.authorization.authorizer.Authorizer;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.engine.SecurityLogic;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.http.adapter.HttpActionAdapter;
import org.pac4j.core.matching.matcher.Matcher;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.security.SecurityEndpoint;

public class SecurityEndpointBuilder {
    private static final AtomicInteger internalNumber = new AtomicInteger(1);

    /*
     * WARNING - void declaration
     */
    public static void buildConfig(SecurityEndpoint endpoint, Object ... parameters) {
        void var10_15;
        Config config = null;
        boolean configProvided = false;
        for (Object parameter : parameters) {
            if (!(parameter instanceof Config)) continue;
            if (config != null) {
                throw new TechnicalException("Only one Config can be used");
            }
            config = (Config)parameter;
            configProvided = true;
        }
        if (config == null) {
            config = new Config();
        }
        String clients = "";
        String authorizers = "";
        String matchers = "";
        ArrayList<Object> paramList = new ArrayList<Object>();
        Object[] objectArray = parameters;
        int n = objectArray.length;
        boolean bl = false;
        while (var10_15 < n) {
            Object parameter = objectArray[var10_15];
            if (parameter instanceof Collection) {
                ((Collection)parameter).forEach(element -> paramList.add(element));
            } else if (parameter instanceof Object[]) {
                Arrays.stream((Object[])parameter).forEach(element -> paramList.add(element));
            } else {
                paramList.add(parameter);
            }
            ++var10_15;
        }
        int numString = 0;
        for (Object e : paramList) {
            String internalName;
            if (e instanceof String) {
                if (!configProvided) {
                    throw new TechnicalException("Cannot accept strings without a provided Config");
                }
                if (numString == 0) {
                    clients = (String)e;
                } else if (numString == 1) {
                    authorizers = (String)e;
                } else if (numString == 2) {
                    matchers = (String)e;
                } else {
                    throw new TechnicalException("Too many strings used in constructor");
                }
                ++numString;
                continue;
            }
            if (e instanceof Client) {
                Client client = (Client)e;
                String clientName = client.getName();
                Clients configClients = config.getClients();
                if (configClients.findClient(clientName).isEmpty()) {
                    configClients.addClient(client);
                }
                clients = SecurityEndpointBuilder.addElement(clients, clientName);
                continue;
            }
            if (e instanceof Authorizer) {
                internalName = "$int_authorizer" + internalNumber.getAndIncrement();
                config.addAuthorizer(internalName, (Authorizer)e);
                authorizers = SecurityEndpointBuilder.addElement(authorizers, internalName);
                continue;
            }
            if (e instanceof Matcher) {
                internalName = "$int_matcher" + internalNumber.getAndIncrement();
                config.addMatcher(internalName, (Matcher)e);
                matchers = SecurityEndpointBuilder.addElement(matchers, internalName);
                continue;
            }
            if (e instanceof HttpActionAdapter) {
                endpoint.setHttpActionAdapter((HttpActionAdapter)e);
                continue;
            }
            if (e instanceof SecurityLogic) {
                endpoint.setSecurityLogic((SecurityLogic)e);
                continue;
            }
            if (e instanceof Config) continue;
            throw new TechnicalException("Unsupported parameter type: " + e);
        }
        endpoint.setConfig(config);
        if (CommonHelper.isNotBlank(clients)) {
            endpoint.setClients(clients);
        }
        if (CommonHelper.isNotBlank(authorizers)) {
            endpoint.setAuthorizers(authorizers);
        }
        if (CommonHelper.isNotBlank(matchers)) {
            endpoint.setMatchers(matchers);
        }
    }

    private static String addElement(String elements, String element) {
        if (CommonHelper.isNotBlank(elements)) {
            return elements + "," + element;
        }
        return element;
    }
}

