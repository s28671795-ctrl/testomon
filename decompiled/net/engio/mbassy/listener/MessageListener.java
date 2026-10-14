/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.engio.mbassy.common.IPredicate;
import net.engio.mbassy.common.ReflectionUtils;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.MessageHandler;
import net.engio.mbassy.listener.References;

public class MessageListener<T> {
    private ArrayList<MessageHandler> handlers = new ArrayList();
    private Class<T> listenerDefinition;
    private Listener listenerAnnotation;

    public static IPredicate<MessageHandler> ForMessage(final Class<?> messageType) {
        return new IPredicate<MessageHandler>(){

            @Override
            public boolean apply(MessageHandler target) {
                return target.handlesMessage(messageType);
            }
        };
    }

    public MessageListener(Class<T> listenerDefinition) {
        this.listenerDefinition = listenerDefinition;
        this.listenerAnnotation = ReflectionUtils.getAnnotation(listenerDefinition, Listener.class);
    }

    public boolean isFromListener(Class listener) {
        return this.listenerDefinition.equals(listener);
    }

    public boolean useStrongReferences() {
        return this.listenerAnnotation != null && this.listenerAnnotation.references().equals((Object)References.Strong);
    }

    public MessageListener addHandlers(Collection<? extends MessageHandler> c) {
        this.handlers.addAll(c);
        return this;
    }

    public boolean addHandler(MessageHandler messageHandler) {
        return this.handlers.add(messageHandler);
    }

    public MessageHandler[] getHandlers() {
        MessageHandler[] asArray = new MessageHandler[this.handlers.size()];
        return this.handlers.toArray(asArray);
    }

    public List<MessageHandler> getHandlers(IPredicate<MessageHandler> filter2) {
        ArrayList<MessageHandler> matching = new ArrayList<MessageHandler>();
        for (MessageHandler handler : this.handlers) {
            if (!filter2.apply(handler)) continue;
            matching.add(handler);
        }
        return matching;
    }

    public boolean handles(Class<?> messageType) {
        return !this.getHandlers(MessageListener.ForMessage(messageType)).isEmpty();
    }

    public Class<T> getListerDefinition() {
        return this.listenerDefinition;
    }
}

