/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.component;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.Component;

public interface IdentifiedComponent
extends Component {
    @Nullable
    public String getId();
}

