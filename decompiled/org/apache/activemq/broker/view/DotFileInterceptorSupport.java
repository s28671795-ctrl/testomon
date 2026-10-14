/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.view;

import java.io.FileWriter;
import java.io.PrintWriter;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DotFileInterceptorSupport
extends BrokerFilter {
    private final Logger log = LoggerFactory.getLogger(DotFileInterceptorSupport.class);
    private String file;

    public DotFileInterceptorSupport(Broker next, String file) {
        super(next);
        this.file = file;
    }

    protected void generateFile() throws Exception {
        this.log.debug("Creating DOT file at: {}", (Object)this.file);
        try (PrintWriter writer = new PrintWriter(new FileWriter(this.file));){
            this.generateFile(writer);
        }
    }

    protected abstract void generateFile(PrintWriter var1) throws Exception;
}

