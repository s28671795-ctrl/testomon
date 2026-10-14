/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.security;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiableInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.security.AccessControl;
import net.shibboleth.utilities.java.support.security.AccessControlService;
import net.shibboleth.utilities.java.support.service.ReloadableService;
import net.shibboleth.utilities.java.support.service.ServiceableComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelegatingAccessControlService
extends AbstractIdentifiableInitializableComponent
implements AccessControlService {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(DelegatingAccessControlService.class);
    private final ReloadableService<AccessControlService> service;

    public DelegatingAccessControlService(@Nonnull ReloadableService<AccessControlService> acService) {
        this.service = Constraint.isNotNull(acService, "AccessControlService cannot be null");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public AccessControl getInstance(@Nonnull String name) {
        block5: {
            ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
            ServiceableComponent<AccessControlService> component = null;
            try {
                component = this.service.getServiceableComponent();
                if (null == component) {
                    this.log.error("AccessControlService '{}': Error accessing underlying component: Invalid configuration.", (Object)this.getId());
                    break block5;
                }
                AccessControlService svc = component.getComponent();
                AccessControl accessControl = svc.getInstance(name);
                return accessControl;
            }
            finally {
                if (null != component) {
                    component.unpinComponent();
                }
            }
        }
        return null;
    }
}

