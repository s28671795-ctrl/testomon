/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.dispatch.el;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import net.engio.mbassy.bus.error.PublicationError;
import net.engio.mbassy.dispatch.el.StandardELResolutionContext;
import net.engio.mbassy.listener.IMessageFilter;
import net.engio.mbassy.listener.MessageHandler;
import net.engio.mbassy.subscription.SubscriptionContext;

public class ElFilter
implements IMessageFilter {
    public static final boolean isELAvailable() {
        return ExpressionFactoryHolder.ELFactory != null;
    }

    public static final ExpressionFactory ELFactory() {
        return ExpressionFactoryHolder.ELFactory;
    }

    public boolean accepts(Object message, SubscriptionContext context) {
        MessageHandler metadata = context.getHandler();
        String expression = metadata.getCondition();
        StandardELResolutionContext resolutionContext = new StandardELResolutionContext(message);
        return this.evalExpression(expression, resolutionContext, context, message);
    }

    private boolean evalExpression(String expression, StandardELResolutionContext resolutionContext, SubscriptionContext context, Object message) {
        ValueExpression ve = ElFilter.ELFactory().createValueExpression(resolutionContext, expression, Boolean.class);
        try {
            return (Boolean)ve.getValue(resolutionContext);
        }
        catch (Throwable exception) {
            PublicationError publicationError = new PublicationError(exception, "Error while evaluating EL expression on message", context).setPublishedMessage(message);
            context.handleError(publicationError);
            return false;
        }
    }

    public static final class ExpressionFactoryHolder {
        public static final ExpressionFactory ELFactory = ExpressionFactoryHolder.getELFactory();

        private static final ExpressionFactory getELFactory() {
            try {
                return ExpressionFactory.newInstance();
            }
            catch (RuntimeException e) {
                return null;
            }
        }
    }
}

