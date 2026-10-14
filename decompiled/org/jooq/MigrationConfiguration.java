/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public final class MigrationConfiguration {
    private final boolean alterTableAddMultiple;
    private final boolean alterTableDropMultiple;
    private final boolean dropSchemaCascade;
    private final boolean dropTableCascade;
    private final boolean alterTableDropCascade;
    private final boolean createOrReplaceView;
    private final boolean respectColumnOrder;

    public MigrationConfiguration() {
        this(false, false, false, false, false, false, false);
    }

    private MigrationConfiguration(boolean alterTableAddMultiple, boolean alterTableDropMultiple, boolean dropSchemaCascade, boolean dropTableCascade, boolean alterTableDropCascade, boolean createOrReplaceView, boolean respectColumnOrder) {
        this.alterTableAddMultiple = alterTableAddMultiple;
        this.alterTableDropMultiple = alterTableDropMultiple;
        this.dropSchemaCascade = dropSchemaCascade;
        this.dropTableCascade = dropTableCascade;
        this.alterTableDropCascade = alterTableDropCascade;
        this.createOrReplaceView = createOrReplaceView;
        this.respectColumnOrder = respectColumnOrder;
    }

    public final boolean alterTableAddMultiple() {
        return this.alterTableAddMultiple;
    }

    public final MigrationConfiguration alterTableAddMultiple(boolean newAlterTableAddMultiple) {
        return new MigrationConfiguration(newAlterTableAddMultiple, this.alterTableDropMultiple, this.dropSchemaCascade, this.dropTableCascade, this.alterTableDropCascade, this.createOrReplaceView, this.respectColumnOrder);
    }

    public final boolean alterTableDropMultiple() {
        return this.alterTableDropMultiple;
    }

    public final MigrationConfiguration alterTableDropMultiple(boolean newAlterTableDropMultiple) {
        return new MigrationConfiguration(this.alterTableAddMultiple, newAlterTableDropMultiple, this.dropSchemaCascade, this.dropTableCascade, this.alterTableDropCascade, this.createOrReplaceView, this.respectColumnOrder);
    }

    public final boolean dropSchemaCascade() {
        return this.dropSchemaCascade;
    }

    public final MigrationConfiguration dropSchemaCascade(boolean newDropSchemaCascade) {
        return new MigrationConfiguration(this.alterTableAddMultiple, this.alterTableDropMultiple, newDropSchemaCascade, this.dropTableCascade, this.alterTableDropCascade, this.createOrReplaceView, this.respectColumnOrder);
    }

    public final boolean dropTableCascade() {
        return this.dropTableCascade;
    }

    public final MigrationConfiguration dropTableCascade(boolean newDropTableCascade) {
        return new MigrationConfiguration(this.alterTableAddMultiple, this.alterTableDropMultiple, this.dropSchemaCascade, newDropTableCascade, this.alterTableDropCascade, this.createOrReplaceView, this.respectColumnOrder);
    }

    public final boolean alterTableDropCascade() {
        return this.alterTableDropCascade;
    }

    public final MigrationConfiguration alterTableDropCascade(boolean newAlterTableDropCascade) {
        return new MigrationConfiguration(this.alterTableAddMultiple, this.alterTableDropMultiple, this.dropSchemaCascade, this.dropTableCascade, newAlterTableDropCascade, this.createOrReplaceView, this.respectColumnOrder);
    }

    public final boolean createOrReplaceView() {
        return this.createOrReplaceView;
    }

    public final MigrationConfiguration createOrReplaceView(boolean newCreateOrReplaceView) {
        return new MigrationConfiguration(this.alterTableAddMultiple, this.alterTableDropMultiple, this.dropSchemaCascade, this.dropTableCascade, this.alterTableDropCascade, newCreateOrReplaceView, this.respectColumnOrder);
    }

    public final boolean respectColumnOrder() {
        return this.respectColumnOrder;
    }

    public final MigrationConfiguration respectColumnOrder(boolean newRespectColumnOrder) {
        return new MigrationConfiguration(this.alterTableAddMultiple, this.alterTableDropMultiple, this.dropSchemaCascade, this.dropTableCascade, this.alterTableDropCascade, this.createOrReplaceView, newRespectColumnOrder);
    }
}

