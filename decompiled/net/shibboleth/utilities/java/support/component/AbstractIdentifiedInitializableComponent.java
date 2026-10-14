/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.component.IdentifiedComponent;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public abstract class AbstractIdentifiedInitializableComponent
extends AbstractInitializableComponent
implements IdentifiedComponent {
    @Nullable
    @NonnullAfterInit
    private String id;

    @Override
    @Nullable
    @NonnullAfterInit
    public String getId() {
        return this.id;
    }

    protected void setId(@Nonnull @NotEmpty String componentId) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.id = Constraint.isNotNull(StringSupport.trimOrNull(componentId), "Component ID can not be null or empty");
    }

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.getId() == null) {
            throw new ComponentInitializationException("Component identifier can not be null");
        }
    }
}

