/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletRequest
 */
package net.shibboleth.utilities.java.support.security;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.servlet.ServletRequest;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiableInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.security.AccessControl;
import net.shibboleth.utilities.java.support.security.AccessControlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicAccessControlService
extends AbstractIdentifiableInitializableComponent
implements AccessControlService {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(BasicAccessControlService.class);
    @Nonnull
    @NonnullElements
    private Map<String, AccessControl> policyMap = Collections.emptyMap();

    public void setPolicyMap(@Nonnull @NonnullElements Map<String, AccessControl> map) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        Constraint.isNotNull(map, "Policy map cannot be null");
        this.policyMap = new HashMap<String, AccessControl>(map.size());
        for (Map.Entry<String, AccessControl> entry : map.entrySet()) {
            String trimmed = StringSupport.trimOrNull(entry.getKey());
            if (trimmed == null || entry.getValue() == null) continue;
            this.policyMap.put(trimmed, entry.getValue());
        }
    }

    @Override
    @Nonnull
    public AccessControl getInstance(@Nonnull String name) {
        AccessControl ac = this.policyMap.get(name);
        if (ac != null) {
            return ac;
        }
        this.log.warn("Access Control Service {}: No policy named '{}' found, returning default denial policy", (Object)this.getId(), (Object)name);
        return new AccessControl(){

            @Override
            public boolean checkAccess(ServletRequest request, String operation, String resource) {
                return false;
            }
        };
    }
}

