/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.component;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.IdentifiedComponent;

public interface IdentifiableComponent
extends IdentifiedComponent {
    public void setId(@Nonnull @NotEmpty String var1);
}

