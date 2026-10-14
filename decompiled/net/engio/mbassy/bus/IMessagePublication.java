/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.bus;

import net.engio.mbassy.bus.error.PublicationError;

public interface IMessagePublication {
    public void execute();

    public boolean isFinished();

    public boolean isRunning();

    public boolean isScheduled();

    public boolean hasError();

    public PublicationError getError();

    public boolean isDeadMessage();

    public boolean isFilteredMessage();

    public Object getMessage();
}

