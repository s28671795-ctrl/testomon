/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.dispatch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.engio.mbassy.bus.MessagePublication;
import net.engio.mbassy.bus.error.PublicationError;
import net.engio.mbassy.dispatch.HandlerInvocation;
import net.engio.mbassy.subscription.SubscriptionContext;

public class ReflectiveHandlerInvocation
extends HandlerInvocation {
    public ReflectiveHandlerInvocation(SubscriptionContext context) {
        super(context);
    }

    @Override
    public void invoke(Object listener, Object message, MessagePublication publication) {
        Method handler = this.getContext().getHandler().getMethod();
        try {
            handler.invoke(listener, message);
        }
        catch (IllegalAccessException e) {
            this.handlePublicationError(publication, new PublicationError(e, "Error during invocation of message handler. The class or method is not accessible", handler, listener, publication));
        }
        catch (IllegalArgumentException e) {
            this.handlePublicationError(publication, new PublicationError(e, "Error during invocation of message handler. Wrong arguments passed to method. Was: " + message.getClass() + "Expected: " + handler.getParameterTypes()[0], handler, listener, publication));
        }
        catch (InvocationTargetException e) {
            this.handlePublicationError(publication, new PublicationError(e, "Error during invocation of message handler. There might be an access rights problem. Do you use non public inner classes?", handler, listener, publication));
        }
        catch (Throwable e) {
            this.handlePublicationError(publication, new PublicationError(e, "Error during invocation of message handler. The handler code threw an exception", handler, listener, publication));
        }
    }
}

