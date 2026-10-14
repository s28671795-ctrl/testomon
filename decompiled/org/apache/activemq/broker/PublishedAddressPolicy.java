/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.util.InetAddressUtil;

public class PublishedAddressPolicy {
    private String clusterClientUriQuery;
    private PublishedHostStrategy publishedHostStrategy = PublishedHostStrategy.DEFAULT;
    private Map<Integer, Integer> portMapping = new HashMap<Integer, Integer>();
    private Map<String, String> hostMapping = new HashMap<String, String>();

    public URI getPublishableConnectURI(TransportConnector connector) throws Exception {
        int port;
        URI connectorURI = connector.getConnectUri();
        if (connectorURI == null) {
            return null;
        }
        String scheme2 = connectorURI.getScheme();
        if ("vm".equals(scheme2)) {
            return connectorURI;
        }
        String userInfo = this.getPublishedUserInfoValue(connectorURI.getUserInfo());
        String host2 = this.getPublishedHostValue(connectorURI.getHost());
        if (this.hostMapping.containsKey(host2)) {
            host2 = this.hostMapping.get(host2);
        }
        if (this.portMapping.containsKey(port = connectorURI.getPort())) {
            port = this.portMapping.get(port);
        }
        String path = this.getPublishedPathValue(connectorURI.getPath());
        String fragment = this.getPublishedFragmentValue(connectorURI.getFragment());
        URI publishedURI = new URI(scheme2, userInfo, host2, port, path, this.getClusterClientUriQuery(), fragment);
        return publishedURI;
    }

    public String getPublishableConnectString(TransportConnector connector) throws Exception {
        return this.getPublishableConnectURI(connector).toString();
    }

    protected String getPublishedHostValue(String uriHostEntry) throws UnknownHostException {
        String result = uriHostEntry;
        if (this.publishedHostStrategy.equals((Object)PublishedHostStrategy.IPADDRESS)) {
            InetAddress address = InetAddress.getByName(uriHostEntry);
            result = address.getHostAddress();
        } else if (this.publishedHostStrategy.equals((Object)PublishedHostStrategy.HOSTNAME)) {
            InetAddress address = InetAddress.getByName(uriHostEntry);
            result = address.isAnyLocalAddress() ? InetAddressUtil.getLocalHostName() : address.getHostName();
        } else if (this.publishedHostStrategy.equals((Object)PublishedHostStrategy.FQDN)) {
            InetAddress address = InetAddress.getByName(uriHostEntry);
            result = address.isAnyLocalAddress() ? InetAddressUtil.getLocalHostName() : address.getCanonicalHostName();
        }
        return result;
    }

    protected String getPublishedPathValue(String uriPathEntry) {
        return uriPathEntry;
    }

    protected String getPublishedFragmentValue(String uriFragmentEntry) {
        return uriFragmentEntry;
    }

    protected String getPublishedUserInfoValue(String uriUserInfoEntry) {
        return uriUserInfoEntry;
    }

    public String getClusterClientUriQuery() {
        return this.clusterClientUriQuery;
    }

    public void setClusterClientUriQuery(String clusterClientUriQuery) {
        this.clusterClientUriQuery = clusterClientUriQuery;
    }

    public PublishedHostStrategy getPublishedHostStrategy() {
        return this.publishedHostStrategy;
    }

    public void setPublishedHostStrategy(PublishedHostStrategy strategy) {
        this.publishedHostStrategy = strategy;
    }

    public void setPublishedHostStrategy(String strategy) {
        this.publishedHostStrategy = PublishedHostStrategy.getValue(strategy);
    }

    public void setPortMapping(Map<Integer, Integer> portMapping) {
        this.portMapping = portMapping;
    }

    public Map<Integer, Integer> getPortMapping() {
        return this.portMapping;
    }

    public void setHostMapping(Map<String, String> hostMapping) {
        this.hostMapping = hostMapping;
    }

    public Map<String, String> getHostMapping() {
        return this.hostMapping;
    }

    public static enum PublishedHostStrategy {
        DEFAULT,
        IPADDRESS,
        HOSTNAME,
        FQDN;


        public static PublishedHostStrategy getValue(String value) {
            return PublishedHostStrategy.valueOf(value.toUpperCase(Locale.ENGLISH));
        }
    }
}

