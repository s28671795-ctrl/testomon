/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.eclipse.jetty.util.component.LifeCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateLifeCycleListener
implements LifeCycle.Listener {
    private static final Logger LOG = LoggerFactory.getLogger(StateLifeCycleListener.class);
    private final Path stateFile;

    public StateLifeCycleListener(String filename) throws IOException {
        this.stateFile = Paths.get(filename, new String[0]).toAbsolutePath();
        if (LOG.isDebugEnabled()) {
            LOG.debug("State File: {}", (Object)this.stateFile);
        }
        Files.deleteIfExists(this.stateFile);
        Files.writeString(this.stateFile, (CharSequence)("INIT " + this + "\n"), StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
    }

    private void appendStateChange(String action, Object obj) {
        try (BufferedWriter out = Files.newBufferedWriter(this.stateFile, StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.APPEND);){
            String entry = String.format("%s %s\n", action, obj);
            if (LOG.isDebugEnabled()) {
                LOG.debug("appendEntry to {}: {}", (Object)this.stateFile, (Object)entry);
            }
            out.append(entry);
        }
        catch (IOException e) {
            LOG.warn("Unable to append to state file: " + this.stateFile, e);
        }
    }

    @Override
    public void lifeCycleStarting(LifeCycle event) {
        this.appendStateChange("STARTING", event);
    }

    @Override
    public void lifeCycleStarted(LifeCycle event) {
        this.appendStateChange("STARTED", event);
    }

    @Override
    public void lifeCycleFailure(LifeCycle event, Throwable cause) {
        this.appendStateChange("FAILED", event);
    }

    @Override
    public void lifeCycleStopping(LifeCycle event) {
        this.appendStateChange("STOPPING", event);
    }

    @Override
    public void lifeCycleStopped(LifeCycle event) {
        this.appendStateChange("STOPPED", event);
    }

    public String toString() {
        return String.format("%s@%h", this.getClass().getSimpleName(), this);
    }
}

