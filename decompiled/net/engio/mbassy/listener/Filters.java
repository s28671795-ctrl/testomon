/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.listener;

import net.engio.mbassy.listener.IMessageFilter;
import net.engio.mbassy.listener.MessageHandler;
import net.engio.mbassy.subscription.SubscriptionContext;

public class Filters {

    public static final class SubtypesOnly
    implements IMessageFilter {
        public boolean accepts(Object message, SubscriptionContext context) {
            MessageHandler metadata = context.getHandler();
            for (Class acceptedClasses : metadata.getHandledMessages()) {
                if (!acceptedClasses.isAssignableFrom(message.getClass()) || acceptedClasses.equals(message.getClass())) continue;
                return true;
            }
            return false;
        }
    }

    public static final class RejectSubtypes
    implements IMessageFilter {
        public boolean accepts(Object event, SubscriptionContext context) {
            MessageHandler metadata = context.getHandler();
            for (Class handledMessage : metadata.getHandledMessages()) {
                if (!handledMessage.equals(event.getClass())) continue;
                return true;
            }
            return false;
        }
    }
}

