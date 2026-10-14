/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPluginSupport;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.command.ActiveMQDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DestinationsPlugin
extends BrokerPluginSupport {
    private static Logger LOG = LoggerFactory.getLogger(DestinationsPlugin.class);
    HashSet<ActiveMQDestination> destinations = new HashSet();
    File location;

    @Override
    public Destination addDestination(ConnectionContext context, ActiveMQDestination destination, boolean createIfTemporary) throws Exception {
        this.destinations.add(destination);
        return super.addDestination(context, destination, createIfTemporary);
    }

    @Override
    public void removeDestination(ConnectionContext context, ActiveMQDestination destination, long timeout2) throws Exception {
        super.removeDestination(context, destination, timeout2);
        this.destinations.remove(destination);
    }

    @Override
    public void start() throws Exception {
        super.start();
        if (this.location == null) {
            this.location = new File(this.getBrokerService().getBrokerDataDirectory(), "destinations");
        }
        this.importDestinations();
        this.destinations.addAll(Arrays.asList(this.getBrokerService().getBroker().getDestinations()));
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        this.exportDestinations();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void importDestinations() throws Exception {
        try (BufferedReader reader = null;){
            if (this.location.exists()) {
                String destination;
                reader = new BufferedReader(new FileReader(this.location));
                Broker broker = this.getBrokerService().getBroker();
                while ((destination = reader.readLine()) != null) {
                    broker.addDestination(this.getBrokerService().getAdminConnectionContext(), ActiveMQDestination.createDestination(destination, (byte)1), true);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void exportDestinations() throws Exception {
        try (PrintWriter pw = null;){
            this.location.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(this.location);
            pw = new PrintWriter(fos);
            for (ActiveMQDestination destination : this.destinations) {
                pw.println(destination);
            }
        }
    }

    public File getLocation() {
        return this.location;
    }

    public void setLocation(File location) {
        this.location = location;
    }
}

