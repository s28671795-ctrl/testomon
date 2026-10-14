/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Function;
import java.io.IOException;
import java.util.Timer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.persist.XMLObjectLoadSaveManager;
import org.opensaml.saml.metadata.resolver.impl.AbstractDynamicMetadataResolver;
import org.opensaml.saml.metadata.resolver.impl.EntityIDDigestGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalDynamicMetadataResolver
extends AbstractDynamicMetadataResolver {
    private Logger log = LoggerFactory.getLogger(LocalDynamicMetadataResolver.class);
    @Nonnull
    private XMLObjectLoadSaveManager<XMLObject> sourceManager;
    @Nonnull
    private Function<CriteriaSet, String> sourceKeyGenerator;

    public LocalDynamicMetadataResolver(@Nonnull XMLObjectLoadSaveManager<XMLObject> manager) {
        this(null, manager, null);
    }

    public LocalDynamicMetadataResolver(@Nonnull XMLObjectLoadSaveManager<XMLObject> manager, @Nullable Function<CriteriaSet, String> keyGenerator) {
        this(null, manager, keyGenerator);
    }

    public LocalDynamicMetadataResolver(@Nullable Timer backgroundTaskTimer, @Nonnull XMLObjectLoadSaveManager<XMLObject> manager, @Nullable Function<CriteriaSet, String> keyGenerator) {
        super(backgroundTaskTimer);
        this.sourceManager = Constraint.isNotNull(manager, "Local source manager was null");
        this.sourceKeyGenerator = keyGenerator;
        if (this.sourceKeyGenerator == null) {
            this.sourceKeyGenerator = new EntityIDDigestGenerator();
        }
    }

    @Override
    protected XMLObject fetchFromOriginSource(CriteriaSet criteria) throws IOException {
        String key = this.sourceKeyGenerator.apply(criteria);
        if (key != null) {
            this.log.trace("{} Attempting to load from local source manager with generated key '{}'", (Object)this.getLogPrefix(), (Object)key);
            XMLObject result = this.sourceManager.load(key);
            if (result != null) {
                this.log.trace("{} Successfully loaded target from local source manager source with key '{}' of type: ", this.getLogPrefix(), key, result.getElementQName());
            } else {
                this.log.trace("{} Found no target in local source manager with key '{}'", (Object)this.getLogPrefix(), (Object)key);
            }
            return result;
        }
        this.log.trace("{} Could not generate source key from criteria, can not resolve", (Object)this.getLogPrefix());
        return null;
    }
}

