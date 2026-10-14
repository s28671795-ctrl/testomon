/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.event;

import com.hierynomus.smbj.event.SMBEvent;
import net.engio.mbassy.bus.SyncMessageBus;
import net.engio.mbassy.bus.common.PubSubSupport;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;
import net.engio.mbassy.bus.error.PublicationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMBEventBus {
    private static final Logger logger = LoggerFactory.getLogger(SMBEventBus.class);
    private PubSubSupport<SMBEvent> wrappedBus;

    public SMBEventBus() {
        this(new SyncMessageBus<SMBEvent>(new IPublicationErrorHandler(){

            @Override
            public void handleError(PublicationError error) {
                if (error.getCause() != null) {
                    logger.error(error.toString(), error.getCause());
                } else {
                    logger.error(error.toString());
                }
            }
        }));
    }

    public SMBEventBus(PubSubSupport<SMBEvent> wrappedBus) {
        this.wrappedBus = wrappedBus;
    }

    public void subscribe(Object listener) {
        this.wrappedBus.subscribe(listener);
    }

    public boolean unsubscribe(Object listener) {
        return this.wrappedBus.unsubscribe(listener);
    }

    public void publish(SMBEvent message) {
        this.wrappedBus.publish(message);
    }
}

