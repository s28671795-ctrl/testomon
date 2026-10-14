/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.Tier;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="GlacierJobParameters")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class GlacierJobParameters {
    @Element(name="Tier")
    private Tier tier;

    public GlacierJobParameters(@Nonnull Tier tier) {
        this.tier = Objects.requireNonNull(tier, "Tier must not be null");
    }
}

