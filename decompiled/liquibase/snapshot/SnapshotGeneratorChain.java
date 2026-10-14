/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import liquibase.exception.DatabaseException;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.SnapshotGenerator;
import liquibase.snapshot.SnapshotIdService;
import liquibase.structure.DatabaseObject;

public class SnapshotGeneratorChain {
    private Iterator<SnapshotGenerator> snapshotGenerators;
    private final Set<Class<? extends SnapshotGenerator>> replacedGenerators = new HashSet<Class<? extends SnapshotGenerator>>();
    private final SnapshotIdService snapshotIdService = SnapshotIdService.getInstance();

    public SnapshotGeneratorChain(SortedSet<SnapshotGenerator> snapshotGenerators) {
        if (snapshotGenerators != null) {
            this.snapshotGenerators = snapshotGenerators.iterator();
            for (SnapshotGenerator generator : snapshotGenerators) {
                Class<? extends SnapshotGenerator>[] replaces = generator.replaces();
                if (replaces == null || replaces.length <= 0) continue;
                this.replacedGenerators.addAll(Arrays.asList(replaces));
            }
        }
    }

    public <T extends DatabaseObject> T snapshot(T example, DatabaseSnapshot snapshot) throws DatabaseException, InvalidExampleException {
        if (example == null) {
            return null;
        }
        if (snapshot.getDatabase().isSystemObject(example)) {
            return null;
        }
        if (!snapshot.getSnapshotControl().shouldInclude(example.getClass())) {
            return null;
        }
        SnapshotGenerator next = this.getNextValidGenerator();
        if (next == null) {
            return null;
        }
        T obj = next.snapshot(example, snapshot, this);
        if (obj != null && obj.getSnapshotId() == null) {
            obj.setSnapshotId(this.snapshotIdService.generateId());
        }
        return obj;
    }

    public SnapshotGenerator getNextValidGenerator() {
        if (this.snapshotGenerators == null) {
            return null;
        }
        if (!this.snapshotGenerators.hasNext()) {
            return null;
        }
        SnapshotGenerator next = this.snapshotGenerators.next();
        for (Class<? extends SnapshotGenerator> removedGenerator : this.replacedGenerators) {
            if (!removedGenerator.equals(next.getClass())) continue;
            return this.getNextValidGenerator();
        }
        return next;
    }
}

