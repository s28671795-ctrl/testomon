/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.JsonType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="JSON")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class JsonInputSerialization {
    @Element(name="Type", required=false)
    private JsonType type;

    public JsonInputSerialization(JsonType type) {
        this.type = type;
    }
}

