/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.profile.impl;

import com.google.common.base.Predicates;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.ListMultimap;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.annotation.constraint.NullableElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.saml.common.SAMLException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.profile.FormatSpecificNameIdentifierGenerator;
import org.opensaml.saml.common.profile.NameIdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainingNameIdentifierGenerator<NameIdType extends SAMLObject>
implements NameIdentifierGenerator<NameIdType> {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(ChainingNameIdentifierGenerator.class);
    @Nonnull
    @NonnullElements
    private ListMultimap<String, NameIdentifierGenerator<NameIdType>> nameIdGeneratorMap = ArrayListMultimap.create();
    @Nullable
    private NameIdentifierGenerator<NameIdType> defaultNameIdGenerator;

    public void setGenerators(@Nonnull @NullableElements List<NameIdentifierGenerator<NameIdType>> generators) {
        Constraint.isNotNull(generators, "NameIdentifierGenerator list cannot be null");
        this.nameIdGeneratorMap.clear();
        for (NameIdentifierGenerator<NameIdType> generator : Collections2.filter(generators, Predicates.notNull())) {
            if (generator instanceof FormatSpecificNameIdentifierGenerator) {
                this.nameIdGeneratorMap.put(((FormatSpecificNameIdentifierGenerator)generator).getFormat(), generator);
                continue;
            }
            this.log.warn("Unable to install NameIdentifierGenerator of type {}, not format-specific", (Object)generator.getClass().getName());
        }
    }

    public void setDefaultGenerator(@Nullable NameIdentifierGenerator<NameIdType> generator) {
        this.defaultNameIdGenerator = generator;
    }

    @Override
    @Nullable
    public NameIdType generate(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull @NotEmpty String format) throws SAMLException {
        this.log.debug("Trying to generate identifier with Format {}", (Object)format);
        List<NameIdentifierGenerator<NameIdType>> generators = this.nameIdGeneratorMap.get((Object)format);
        if (generators.isEmpty() && this.defaultNameIdGenerator != null) {
            this.log.debug("No generators installed for Format {}, trying default/fallback method", (Object)format);
            generators = Collections.singletonList(this.defaultNameIdGenerator);
        }
        for (NameIdentifierGenerator nameIdentifierGenerator : generators) {
            try {
                Object nameIdentifier = nameIdentifierGenerator.generate(profileRequestContext, format);
                if (nameIdentifier == null) continue;
                this.log.debug("Successfully generated identifier with Format {}", (Object)format);
                return nameIdentifier;
            }
            catch (SAMLException e) {
                this.log.error("Error while generating identifier", e);
            }
        }
        return null;
    }
}

