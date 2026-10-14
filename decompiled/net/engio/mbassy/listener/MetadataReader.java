/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.listener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import net.engio.mbassy.common.IPredicate;
import net.engio.mbassy.common.ReflectionUtils;
import net.engio.mbassy.listener.Enveloped;
import net.engio.mbassy.listener.Filter;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.IMessageFilter;
import net.engio.mbassy.listener.MessageHandler;
import net.engio.mbassy.listener.MessageListener;
import net.engio.mbassy.subscription.MessageEnvelope;

public class MetadataReader {
    private static final IPredicate<Method> AllMessageHandlers = new IPredicate<Method>(){

        @Override
        public boolean apply(Method target) {
            return ReflectionUtils.getAnnotation(target, Handler.class) != null;
        }
    };
    private final Map<Class<? extends IMessageFilter>, IMessageFilter> filterCache = new HashMap<Class<? extends IMessageFilter>, IMessageFilter>();

    private IMessageFilter[] getFilter(Handler subscription) {
        if (subscription.filters().length == 0) {
            return null;
        }
        IMessageFilter[] filters = new IMessageFilter[subscription.filters().length];
        int i = 0;
        for (Filter filterDef : subscription.filters()) {
            IMessageFilter filter2 = this.filterCache.get(filterDef.value());
            if (filter2 == null) {
                try {
                    filter2 = filterDef.value().newInstance();
                    this.filterCache.put(filterDef.value(), filter2);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            filters[i] = filter2;
            ++i;
        }
        return filters;
    }

    public MessageListener getMessageListener(Class target) {
        MessageListener listenerMetadata = new MessageListener(target);
        Method[] allHandlers = ReflectionUtils.getMethods(AllMessageHandlers, target);
        int length = allHandlers.length;
        for (int i = 0; i < length; ++i) {
            Handler handlerConfig;
            Method handler = allHandlers[i];
            if (ReflectionUtils.containsOverridingMethod(allHandlers, handler) || !(handlerConfig = ReflectionUtils.getAnnotation(handler, Handler.class)).enabled() || !this.isValidMessageHandler(handler)) continue;
            Method overriddenHandler = ReflectionUtils.getOverridingMethod(handler, target);
            Map<String, Object> handlerProperties = MessageHandler.Properties.Create(overriddenHandler == null ? handler : overriddenHandler, handlerConfig, this.getFilter(handlerConfig), listenerMetadata);
            MessageHandler handlerMetadata = new MessageHandler(handlerProperties);
            listenerMetadata.addHandler(handlerMetadata);
        }
        return listenerMetadata;
    }

    private boolean isValidMessageHandler(Method handler) {
        if (handler == null || ReflectionUtils.getAnnotation(handler, Handler.class) == null) {
            return false;
        }
        if (handler.getParameterTypes().length != 1) {
            System.out.println("Found no or more than one parameter in messageHandler [" + handler.getName() + "]. A messageHandler must define exactly one parameter");
            return false;
        }
        Enveloped envelope = ReflectionUtils.getAnnotation(handler, Enveloped.class);
        if (envelope != null && !MessageEnvelope.class.isAssignableFrom(handler.getParameterTypes()[0])) {
            System.out.println("Message envelope configured but no subclass of MessageEnvelope found as parameter");
            return false;
        }
        if (envelope != null && envelope.messages().length == 0) {
            System.out.println("Message envelope configured but message types defined for handler");
            return false;
        }
        return true;
    }
}

