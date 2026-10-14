/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.bus;

import java.util.concurrent.TimeUnit;
import net.engio.mbassy.bus.AbstractSyncAsyncMessageBus;
import net.engio.mbassy.bus.IMessagePublication;
import net.engio.mbassy.bus.MessagePublication;
import net.engio.mbassy.bus.common.IMessageBus;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.bus.config.Feature;
import net.engio.mbassy.bus.config.IBusConfiguration;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;
import net.engio.mbassy.bus.error.PublicationError;
import net.engio.mbassy.bus.publication.SyncAsyncPostCommand;

public class MBassador<T>
extends AbstractSyncAsyncMessageBus<T, SyncAsyncPostCommand<T>>
implements IMessageBus<T, SyncAsyncPostCommand<T>> {
    public MBassador() {
        this(new BusConfiguration().addFeature(Feature.SyncPubSub.Default()).addFeature(Feature.AsynchronousHandlerInvocation.Default()).addFeature(Feature.AsynchronousMessageDispatch.Default()));
    }

    public MBassador(IPublicationErrorHandler errorHandler) {
        super(new BusConfiguration().addFeature(Feature.SyncPubSub.Default()).addFeature(Feature.AsynchronousHandlerInvocation.Default()).addFeature(Feature.AsynchronousMessageDispatch.Default()).addPublicationErrorHandler(errorHandler));
    }

    public MBassador(IBusConfiguration configuration) {
        super(configuration);
    }

    public IMessagePublication publishAsync(T message) {
        return this.addAsynchronousPublication(this.createMessagePublication(message));
    }

    public IMessagePublication publishAsync(T message, long timeout2, TimeUnit unit) {
        return this.addAsynchronousPublication(this.createMessagePublication(message), timeout2, unit);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public IMessagePublication publish(T message) {
        MessagePublication publication = this.createMessagePublication(message);
        try {
            publication.execute();
        }
        catch (Throwable e) {
            this.handlePublicationError(new PublicationError().setMessage("Error during publication of message").setCause(e).setPublication(publication));
        }
        finally {
            return publication;
        }
    }

    @Override
    public SyncAsyncPostCommand<T> post(T message) {
        return new SyncAsyncPostCommand<T>(this, message);
    }
}

