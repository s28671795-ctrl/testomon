/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.filter.impl;

import com.google.common.base.Function;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.metadata.AffiliationDescriptor;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;

public class BasicDynamicTrustedNamesStrategy
implements Function<XMLObject, Set<String>> {
    @Override
    @Nonnull
    @NonnullElements
    public Set<String> apply(@Nullable XMLObject input) {
        if (input == null) {
            return Collections.emptySet();
        }
        Set<String> rawResult = null;
        if (input instanceof EntityDescriptor) {
            rawResult = Collections.singleton(((EntityDescriptor)input).getEntityID());
        } else if (input instanceof EntitiesDescriptor) {
            rawResult = Collections.singleton(((EntitiesDescriptor)input).getName());
        } else if (input instanceof RoleDescriptor) {
            XMLObject parent = input.getParent();
            if (parent instanceof EntityDescriptor) {
                rawResult = Collections.singleton(((EntityDescriptor)parent).getEntityID());
            }
        } else if (input instanceof AffiliationDescriptor) {
            rawResult = new HashSet<String>();
            rawResult.add(((AffiliationDescriptor)input).getOwnerID());
            XMLObject parent = input.getParent();
            if (parent instanceof EntityDescriptor) {
                rawResult.add(((EntityDescriptor)parent).getEntityID());
            }
        }
        if (rawResult != null) {
            return new HashSet<String>(StringSupport.normalizeStringCollection(rawResult));
        }
        return Collections.emptySet();
    }
}

