/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.binding.artifact;

import java.io.IOException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.saml.common.SAMLObject;

public interface SAMLArtifactMap {
    public boolean contains(@Nonnull @NotEmpty String var1) throws IOException;

    public void put(@Nonnull @NotEmpty String var1, @Nonnull @NotEmpty String var2, @Nonnull @NotEmpty String var3, @Nonnull SAMLObject var4) throws IOException;

    @Nullable
    public SAMLArtifactMapEntry get(@Nonnull @NotEmpty String var1) throws IOException;

    public void remove(@Nonnull @NotEmpty String var1) throws IOException;

    public static interface SAMLArtifactMapEntryFactory {
        @Nonnull
        public SAMLArtifactMapEntry newEntry(@Nonnull @NotEmpty String var1, @Nonnull @NotEmpty String var2, @Nonnull @NotEmpty String var3, @Nonnull SAMLObject var4);
    }

    public static interface SAMLArtifactMapEntry {
        @Nonnull
        @NotEmpty
        public String getArtifact();

        @Nonnull
        @NotEmpty
        public String getIssuerId();

        @Nonnull
        @NotEmpty
        public String getRelyingPartyId();

        @Nonnull
        public SAMLObject getSamlMessage();
    }
}

