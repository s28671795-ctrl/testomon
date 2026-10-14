/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.messageinterpolation.el;

import javax.el.ArrayELResolver;
import javax.el.CompositeELResolver;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.ListELResolver;
import javax.el.MapELResolver;
import javax.el.ResourceBundleELResolver;
import javax.el.StandardELContext;
import org.hibernate.validator.internal.engine.messageinterpolation.el.BeanPropertiesELResolver;
import org.hibernate.validator.internal.engine.messageinterpolation.el.RootResolver;

public class BeanPropertiesElContext
extends StandardELContext {
    private static final ELResolver DEFAULT_RESOLVER = new CompositeELResolver(){
        {
            this.add(new RootResolver());
            this.add(new ArrayELResolver(true));
            this.add(new ListELResolver(true));
            this.add(new MapELResolver(true));
            this.add(new ResourceBundleELResolver());
            this.add(new BeanPropertiesELResolver());
        }
    };

    public BeanPropertiesElContext(ExpressionFactory expressionFactory) {
        super(expressionFactory);
        this.putContext(ExpressionFactory.class, expressionFactory);
    }

    @Override
    public void addELResolver(ELResolver cELResolver) {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " does not support addELResolver.");
    }

    @Override
    public ELResolver getELResolver() {
        return DEFAULT_RESOLVER;
    }
}

