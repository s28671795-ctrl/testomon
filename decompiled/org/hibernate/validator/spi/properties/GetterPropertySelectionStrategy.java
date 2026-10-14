/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.spi.properties;

import java.util.Optional;
import java.util.Set;
import org.hibernate.validator.Incubating;
import org.hibernate.validator.spi.properties.ConstrainableExecutable;

@Incubating
public interface GetterPropertySelectionStrategy {
    public Optional<String> getProperty(ConstrainableExecutable var1);

    public Set<String> getGetterMethodNameCandidates(String var1);
}

