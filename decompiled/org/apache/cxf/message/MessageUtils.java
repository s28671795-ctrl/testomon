/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.message;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.FaultMode;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.invoker.MethodDispatcher;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.w3c.dom.Node;

public final class MessageUtils {
    private static final Logger LOG = LogUtils.getL7dLogger(MessageUtils.class);

    private MessageUtils() {
    }

    public static boolean isOutbound(Message message) {
        if (message == null) {
            return false;
        }
        Exchange exchange = message.getExchange();
        return exchange != null && (message == exchange.getOutMessage() || message == exchange.getOutFaultMessage());
    }

    public static boolean isFault(Message message) {
        return message != null && message.getExchange() != null && (message == message.getExchange().getInFaultMessage() || message == message.getExchange().getOutFaultMessage());
    }

    public static FaultMode getFaultMode(Message message) {
        if (message != null && message.getExchange() != null && message == message.getExchange().getOutFaultMessage()) {
            FaultMode mode = message.get(FaultMode.class);
            if (null != mode) {
                return mode;
            }
            return FaultMode.RUNTIME_FAULT;
        }
        return null;
    }

    public static boolean isRequestor(Message message) {
        if (message != null) {
            Boolean requestor = (Boolean)message.get("org.apache.cxf.client");
            return requestor != null && requestor != false;
        }
        return false;
    }

    public static boolean isPartialResponse(Message message) {
        return message != null && Boolean.TRUE.equals(message.get("org.apache.cxf.partial.response"));
    }

    public static boolean isEmptyPartialResponse(Message message) {
        return message != null && Boolean.TRUE.equals(message.get("org.apache.cxf.partial.response.empty"));
    }

    @Deprecated
    public static boolean isTrue(Object value) {
        return PropertyUtils.isTrue(value);
    }

    public static boolean getContextualBoolean(Message m4, String key) {
        return MessageUtils.getContextualBoolean(m4, key, false);
    }

    public static boolean getContextualBoolean(Message m4, String key, boolean defaultValue) {
        Object o;
        if (m4 != null && (o = m4.getContextualProperty(key)) != null) {
            return PropertyUtils.isTrue(o);
        }
        return defaultValue;
    }

    public static int getContextualInteger(Message m4, String key, int defaultValue) {
        Object o;
        if (m4 != null && (o = m4.getContextualProperty(key)) instanceof String) {
            try {
                int i = Integer.parseInt((String)o);
                if (i > 0) {
                    return i;
                }
            }
            catch (NumberFormatException ex) {
                LOG.warning("Incorrect integer value of " + o + " specified for: " + key);
            }
        }
        return defaultValue;
    }

    public static Object getContextualProperty(Message m4, String propPreferred, String propDefault) {
        Object prop = null;
        if (m4 != null && (prop = m4.getContextualProperty(propPreferred)) == null && propDefault != null) {
            prop = m4.getContextualProperty(propDefault);
        }
        return prop;
    }

    public static boolean isDOMPresent(Message m4) {
        return m4 != null && m4.getContent(Node.class) != null;
    }

    public static Optional<Method> getTargetMethod(Message m4) {
        Method method2;
        BindingOperationInfo bop = m4.getExchange().getBindingOperationInfo();
        if (bop != null) {
            MethodDispatcher md = (MethodDispatcher)m4.getExchange().getService().get(MethodDispatcher.class.getName());
            method2 = md.getMethod(bop);
        } else {
            method2 = (Method)m4.get("org.apache.cxf.resource.method");
        }
        return Optional.ofNullable(method2);
    }
}

