/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.DeleteMarkerReplication;
import io.minio.messages.DeleteReplication;
import io.minio.messages.ExistingObjectReplication;
import io.minio.messages.PrefixConverter;
import io.minio.messages.ReplicationDestination;
import io.minio.messages.RuleFilter;
import io.minio.messages.SourceSelectionCriteria;
import io.minio.messages.Status;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

@Root(name="Rule")
public class ReplicationRule {
    @Element(name="DeleteMarkerReplication", required=false)
    private DeleteMarkerReplication deleteMarkerReplication;
    @Element(name="Destination")
    private ReplicationDestination destination;
    @Element(name="ExistingObjectReplication", required=false)
    private ExistingObjectReplication existingObjectReplication;
    @Element(name="Filter", required=false)
    private RuleFilter filter;
    @Element(name="ID", required=false)
    private String id;
    @Element(name="Prefix", required=false)
    @Convert(value=PrefixConverter.class)
    private String prefix;
    @Element(name="Priority", required=false)
    private Integer priority;
    @Element(name="SourceSelectionCriteria", required=false)
    private SourceSelectionCriteria sourceSelectionCriteria;
    @Element(name="DeleteReplication", required=false)
    private DeleteReplication deleteReplication;
    @Element(name="Status")
    private Status status;

    public ReplicationRule(@Nullable @Element(name="DeleteMarkerReplication", required=false) DeleteMarkerReplication deleteMarkerReplication, @Nonnull @Element(name="Destination") ReplicationDestination destination, @Nullable @Element(name="ExistingObjectReplication", required=false) ExistingObjectReplication existingObjectReplication, @Nullable @Element(name="Filter", required=false) RuleFilter filter2, @Nullable @Element(name="ID", required=false) String id, @Nullable @Element(name="Prefix", required=false) String prefix, @Nullable @Element(name="Priority", required=false) Integer priority, @Nullable @Element(name="SourceSelectionCriteria", required=false) SourceSelectionCriteria sourceSelectionCriteria, @Nullable @Element(name="DeleteReplication", required=false) DeleteReplication deleteReplication, @Nonnull @Element(name="Status") Status status) {
        if (filter2 != null && deleteMarkerReplication == null) {
            deleteMarkerReplication = new DeleteMarkerReplication(null);
        }
        if (id != null) {
            if ((id = id.trim()).isEmpty()) {
                throw new IllegalArgumentException("ID must be non-empty string");
            }
            if (id.length() > 255) {
                throw new IllegalArgumentException("ID must be exceed 255 characters");
            }
        }
        this.deleteMarkerReplication = deleteMarkerReplication;
        this.destination = Objects.requireNonNull(destination, "Destination must not be null");
        this.existingObjectReplication = existingObjectReplication;
        this.filter = filter2;
        this.id = id;
        this.prefix = prefix;
        this.priority = priority;
        this.sourceSelectionCriteria = sourceSelectionCriteria;
        this.deleteReplication = deleteReplication;
        this.status = Objects.requireNonNull(status, "Status must not be null");
    }

    public ReplicationRule(@Nullable @Element(name="DeleteMarkerReplication", required=false) DeleteMarkerReplication deleteMarkerReplication, @Nonnull @Element(name="Destination") ReplicationDestination destination, @Nullable @Element(name="ExistingObjectReplication", required=false) ExistingObjectReplication existingObjectReplication, @Nullable @Element(name="Filter", required=false) RuleFilter filter2, @Nullable @Element(name="ID", required=false) String id, @Nullable @Element(name="Prefix", required=false) String prefix, @Nullable @Element(name="Priority", required=false) Integer priority, @Nullable @Element(name="SourceSelectionCriteria", required=false) SourceSelectionCriteria sourceSelectionCriteria, @Nonnull @Element(name="Status") Status status) {
        this(deleteMarkerReplication, destination, existingObjectReplication, filter2, id, prefix, priority, sourceSelectionCriteria, null, status);
    }

    public DeleteMarkerReplication deleteMarkerReplication() {
        return this.deleteMarkerReplication;
    }

    public ReplicationDestination destination() {
        return this.destination;
    }

    public ExistingObjectReplication existingObjectReplication() {
        return this.existingObjectReplication;
    }

    public RuleFilter filter() {
        return this.filter;
    }

    public String id() {
        return this.id;
    }

    public String prefix() {
        return this.prefix;
    }

    public Integer priority() {
        return this.priority;
    }

    public SourceSelectionCriteria sourceSelectionCriteria() {
        return this.sourceSelectionCriteria;
    }

    public DeleteReplication deleteReplication() {
        return this.deleteReplication;
    }

    public Status status() {
        return this.status;
    }
}

