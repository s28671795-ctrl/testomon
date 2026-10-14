/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.S3OutputLocation;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="OutputLocation")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class OutputLocation {
    @Element(name="S3")
    private S3OutputLocation s3OutputLocation;

    public OutputLocation(@Nonnull S3OutputLocation s3OutputLocation) {
        this.s3OutputLocation = Objects.requireNonNull(s3OutputLocation, "S3OutputLocation must not be null");
    }
}

