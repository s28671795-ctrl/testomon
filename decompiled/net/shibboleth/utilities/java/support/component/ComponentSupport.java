/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.DestroyedComponentException;
import net.shibboleth.utilities.java.support.component.DestructableComponent;
import net.shibboleth.utilities.java.support.component.IdentifiedComponent;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import net.shibboleth.utilities.java.support.component.UninitializedComponentException;
import net.shibboleth.utilities.java.support.component.UnmodifiableComponentException;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public final class ComponentSupport {
    private ComponentSupport() {
    }

    public static void destroy(@Nullable Object obj) {
        DestructableComponent destructable;
        if (obj == null) {
            return;
        }
        if (obj instanceof DestructableComponent && !(destructable = (DestructableComponent)obj).isDestroyed()) {
            destructable.destroy();
        }
    }

    public static void initialize(@Nullable Object obj) throws ComponentInitializationException {
        InitializableComponent initializable;
        if (obj == null) {
            return;
        }
        if (obj instanceof InitializableComponent && !(initializable = (InitializableComponent)obj).isInitialized()) {
            initializable.initialize();
        }
    }

    public static void ifDestroyedThrowDestroyedComponentException(@Nonnull DestructableComponent component) {
        Constraint.isNotNull(component, "Component cannot be null");
        if (component.isDestroyed()) {
            if (component instanceof IdentifiedComponent) {
                throw new DestroyedComponentException("Component '" + StringSupport.trimOrNull(((IdentifiedComponent)((Object)component)).getId()) + "' has already been destroyed and can no longer be used.");
            }
            throw new DestroyedComponentException("Component has already been destroyed and can no longer be used");
        }
    }

    public static void ifNotInitializedThrowUninitializedComponentException(@Nonnull InitializableComponent component) {
        Constraint.isNotNull(component, "Component cannot be null");
        if (!component.isInitialized()) {
            if (component instanceof IdentifiedComponent) {
                throw new UninitializedComponentException("Component '" + StringSupport.trimOrNull(((IdentifiedComponent)((Object)component)).getId()) + "' has not yet been initialized and cannot be used.");
            }
            throw new UninitializedComponentException("Component has not yet been initialized and cannot be used.");
        }
    }

    public static void ifInitializedThrowUnmodifiabledComponentException(@Nonnull InitializableComponent component) {
        Constraint.isNotNull(component, "Component cannot be null");
        if (component.isInitialized()) {
            if (component instanceof IdentifiedComponent) {
                throw new UnmodifiableComponentException("Component '" + StringSupport.trimOrNull(((IdentifiedComponent)((Object)component)).getId()) + "' has already been initialized and can no longer be modified");
            }
            throw new UnmodifiableComponentException("Component has already been initialized and can no longer be modified");
        }
    }
}

