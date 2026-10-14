/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.listener;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import net.engio.mbassy.common.ReflectionUtils;
import net.engio.mbassy.dispatch.HandlerInvocation;
import net.engio.mbassy.dispatch.el.ElFilter;
import net.engio.mbassy.listener.Enveloped;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.IMessageFilter;
import net.engio.mbassy.listener.Invoke;
import net.engio.mbassy.listener.MessageListener;
import net.engio.mbassy.listener.Synchronized;

public class MessageHandler {
    private final Method handler;
    private final IMessageFilter[] filter;
    private final String condition;
    private final int priority;
    private final Class<? extends HandlerInvocation> invocation;
    private final Invoke invocationMode;
    private final boolean isEnvelope;
    private final Class[] handledMessages;
    private final boolean acceptsSubtypes;
    private final MessageListener listenerConfig;
    private final boolean isSynchronized;

    public MessageHandler(Map<String, Object> properties) {
        this.validate(properties);
        this.handler = (Method)properties.get("handler");
        this.filter = (IMessageFilter[])properties.get("filter");
        this.condition = (String)properties.get("condition");
        this.priority = (Integer)properties.get("priority");
        this.invocation = (Class)properties.get("invocation");
        this.invocationMode = (Invoke)((Object)properties.get("invocationMode"));
        this.isEnvelope = (Boolean)properties.get("envelope");
        this.acceptsSubtypes = (Boolean)properties.get("subtypes");
        this.listenerConfig = (MessageListener)properties.get("listener");
        this.isSynchronized = (Boolean)properties.get("synchronized");
        this.handledMessages = (Class[])properties.get("messages");
    }

    private void validate(Map<String, Object> properties) {
        Object[][] expectedProperties;
        for (Object[] property : expectedProperties = new Object[][]{{"handler", Method.class}, {"priority", Integer.class}, {"invocation", Class.class}, {"filter", IMessageFilter[].class}, {"condition", String.class}, {"envelope", Boolean.class}, {"messages", Class[].class}, {"synchronized", Boolean.class}, {"listener", MessageListener.class}, {"subtypes", Boolean.class}}) {
            if (properties.get(property[0]) != null && ((Class)property[1]).isAssignableFrom(properties.get(property[0]).getClass())) continue;
            throw new IllegalArgumentException("Property " + property[0] + " was expected to be not null and of type " + property[1] + " but was: " + properties.get(property[0]));
        }
    }

    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return ReflectionUtils.getAnnotation(this.handler, annotationType);
    }

    public boolean isSynchronized() {
        return this.isSynchronized;
    }

    public boolean useStrongReferences() {
        return this.listenerConfig.useStrongReferences();
    }

    public boolean isFromListener(Class listener) {
        return this.listenerConfig.isFromListener(listener);
    }

    public boolean isAsynchronous() {
        return this.invocationMode.equals((Object)Invoke.Asynchronously);
    }

    public boolean isFiltered() {
        return this.filter.length > 0 || this.condition != null && this.condition.trim().length() > 0;
    }

    public int getPriority() {
        return this.priority;
    }

    public Method getMethod() {
        return this.handler;
    }

    public IMessageFilter[] getFilter() {
        return this.filter;
    }

    public String getCondition() {
        return this.condition;
    }

    public Class[] getHandledMessages() {
        return this.handledMessages;
    }

    public boolean isEnveloped() {
        return this.isEnvelope;
    }

    public Class<? extends HandlerInvocation> getHandlerInvocation() {
        return this.invocation;
    }

    public boolean handlesMessage(Class<?> messageType) {
        for (Class handledMessage : this.handledMessages) {
            if (handledMessage.equals(messageType)) {
                return true;
            }
            if (!handledMessage.isAssignableFrom(messageType) || !this.acceptsSubtypes()) continue;
            return true;
        }
        return false;
    }

    public boolean acceptsSubtypes() {
        return this.acceptsSubtypes;
    }

    public static final class Properties {
        public static final String HandlerMethod = "handler";
        public static final String InvocationMode = "invocationMode";
        public static final String Filter = "filter";
        public static final String Condition = "condition";
        public static final String Enveloped = "envelope";
        public static final String HandledMessages = "messages";
        public static final String IsSynchronized = "synchronized";
        public static final String Listener = "listener";
        public static final String AcceptSubtypes = "subtypes";
        public static final String Priority = "priority";
        public static final String Invocation = "invocation";

        public static final Map<String, Object> Create(Method handler, Handler handlerConfig, IMessageFilter[] filter2, MessageListener listenerConfig) {
            Enveloped enveloped;
            if (handler == null) {
                throw new IllegalArgumentException("The message handler configuration may not be null");
            }
            if (filter2 == null) {
                filter2 = new IMessageFilter[]{};
            }
            Class[] handledMessages = (enveloped = ReflectionUtils.getAnnotation(handler, Enveloped.class)) != null ? enveloped.messages() : handler.getParameterTypes();
            handler.setAccessible(true);
            HashMap<String, Object> properties = new HashMap<String, Object>();
            properties.put(HandlerMethod, handler);
            if (handlerConfig.condition().length() > 0) {
                if (!ElFilter.isELAvailable()) {
                    throw new IllegalStateException("A handler uses an EL filter but no EL implementation is available.");
                }
                IMessageFilter[] expandedFilter = new IMessageFilter[filter2.length + 1];
                for (int i = 0; i < filter2.length; ++i) {
                    expandedFilter[i] = filter2[i];
                }
                expandedFilter[filter2.length] = new ElFilter();
                filter2 = expandedFilter;
            }
            properties.put(Filter, filter2);
            properties.put(Condition, Properties.cleanEL(handlerConfig.condition()));
            properties.put(Priority, handlerConfig.priority());
            properties.put(Invocation, handlerConfig.invocation());
            properties.put(InvocationMode, (Object)handlerConfig.delivery());
            properties.put(Enveloped, enveloped != null);
            properties.put(AcceptSubtypes, !handlerConfig.rejectSubtypes());
            properties.put(Listener, listenerConfig);
            properties.put(IsSynchronized, ReflectionUtils.getAnnotation(handler, Synchronized.class) != null);
            properties.put(HandledMessages, handledMessages);
            return properties;
        }

        private static String cleanEL(String expression) {
            if (!expression.trim().startsWith("${") && !expression.trim().startsWith("#{")) {
                expression = "${" + expression + "}";
            }
            return expression;
        }
    }
}

