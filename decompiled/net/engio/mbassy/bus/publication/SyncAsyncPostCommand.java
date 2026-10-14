/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.bus.publication;

import java.util.concurrent.TimeUnit;
import net.engio.mbassy.bus.IMessagePublication;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.publication.ISyncAsyncPublicationCommand;

public class SyncAsyncPostCommand<T>
implements ISyncAsyncPublicationCommand {
    private T message;
    private MBassador<T> mBassador;

    public SyncAsyncPostCommand(MBassador<T> mBassador, T message) {
        this.mBassador = mBassador;
        this.message = message;
    }

    @Override
    public IMessagePublication now() {
        return this.mBassador.publish(this.message);
    }

    @Override
    public IMessagePublication asynchronously() {
        return this.mBassador.publishAsync(this.message);
    }

    @Override
    public IMessagePublication asynchronously(long timeout2, TimeUnit unit) {
        return this.mBassador.publishAsync(this.message, timeout2, unit);
    }
}

