/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.bus.publication;

import java.util.concurrent.TimeUnit;
import net.engio.mbassy.bus.IMessagePublication;
import net.engio.mbassy.bus.publication.IPublicationCommand;

public interface ISyncAsyncPublicationCommand
extends IPublicationCommand {
    public IMessagePublication asynchronously();

    public IMessagePublication asynchronously(long var1, TimeUnit var3);
}

