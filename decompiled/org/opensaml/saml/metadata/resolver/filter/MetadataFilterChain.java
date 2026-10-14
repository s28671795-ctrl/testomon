/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.filter;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.Live;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataFilterChain
implements MetadataFilter {
    @Nonnull
    private Logger log = LoggerFactory.getLogger(MetadataFilterChain.class);
    @Nonnull
    @NonnullElements
    private List<MetadataFilter> filters = Collections.emptyList();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Nullable
    public final XMLObject filter(@Nullable XMLObject xmlObject) throws FilterException {
        if (xmlObject == null) {
            return null;
        }
        List<MetadataFilter> list = this.filters;
        synchronized (list) {
            if (this.filters == null || this.filters.isEmpty()) {
                this.log.debug("No filters configured, nothing to do");
                return xmlObject;
            }
            XMLObject current = xmlObject;
            for (MetadataFilter filter2 : this.filters) {
                if (current == null) {
                    return null;
                }
                this.log.debug("Applying filter {}", (Object)filter2.getClass().getName());
                current = filter2.filter(current);
            }
            return current;
        }
    }

    @Nonnull
    @NonnullElements
    @Live
    public List<MetadataFilter> getFilters() {
        return this.filters;
    }

    public void setFilters(@Nonnull @NonnullElements List<MetadataFilter> newFilters) {
        Constraint.isNotNull(newFilters, "Filter collection cannot be null");
        this.filters = new ArrayList<MetadataFilter>(Collections2.filter(newFilters, Predicates.notNull()));
    }
}

