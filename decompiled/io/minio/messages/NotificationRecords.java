/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.Event;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@SuppressFBWarnings(value={"UwF"}, justification="Everything in this class is initialized by JSON unmarshalling.")
public class NotificationRecords {
    @JsonProperty(value="Records")
    private List<Event> events;

    public List<Event> events() {
        return Collections.unmodifiableList(this.events == null ? new LinkedList() : this.events);
    }
}

