/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.bus;

import net.engio.mbassy.bus.AbstractPubSubSupport;
import net.engio.mbassy.bus.IMessagePublication;
import net.engio.mbassy.bus.MessagePublication;
import net.engio.mbassy.bus.common.ErrorHandlingSupport;
import net.engio.mbassy.bus.common.GenericMessagePublicationSupport;
import net.engio.mbassy.bus.common.PubSubSupport;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.bus.config.Feature;
import net.engio.mbassy.bus.config.IBusConfiguration;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;
import net.engio.mbassy.bus.error.PublicationError;
import net.engio.mbassy.bus.publication.IPublicationCommand;

public class SyncMessageBus<T>
extends AbstractPubSubSupport<T>
implements PubSubSupport<T>,
ErrorHandlingSupport,
GenericMessagePublicationSupport<T, SyncPostCommand> {
    public SyncMessageBus() {
        super(new BusConfiguration().addFeature(Feature.SyncPubSub.Default()));
    }

    public SyncMessageBus(IPublicationErrorHandler errorHandler) {
        super(new BusConfiguration().addFeature(Feature.SyncPubSub.Default()).addPublicationErrorHandler(errorHandler));
    }

    public SyncMessageBus(IBusConfiguration configuration) {
        super(configuration);
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
    public SyncPostCommand post(T message) {
        return new SyncPostCommand(message);
    }

    public class SyncPostCommand
    implements IPublicationCommand {
        private T message;

        public SyncPostCommand(T message) {
            this.message = message;
        }

        @Override
        public IMessagePublication now() {
            return SyncMessageBus.this.publish(this.message);
        }
    }
}

