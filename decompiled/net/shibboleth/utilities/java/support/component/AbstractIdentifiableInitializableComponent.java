/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.component;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiedInitializableComponent;
import net.shibboleth.utilities.java.support.component.IdentifiableComponent;

public abstract class AbstractIdentifiableInitializableComponent
extends AbstractIdentifiedInitializableComponent
implements IdentifiableComponent {
    @Override
    public void setId(@Nonnull @NotEmpty String componentId) {
        super.setId(componentId);
    }
}

