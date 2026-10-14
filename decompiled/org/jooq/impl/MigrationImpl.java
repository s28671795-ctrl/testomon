/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.jooq.Commit;
import org.jooq.Commits;
import org.jooq.Configuration;
import org.jooq.ContextTransactionalRunnable;
import org.jooq.Field;
import org.jooq.Files;
import org.jooq.Meta;
import org.jooq.Migration;
import org.jooq.MigrationListener;
import org.jooq.Name;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.Record1;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.conf.InterpreterSearchSchema;
import org.jooq.conf.MigrationSchema;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataMigrationException;
import org.jooq.exception.DataMigrationValidationException;
import org.jooq.impl.AbstractScope;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultMigrationContext;
import org.jooq.impl.EnumConverter;
import org.jooq.impl.Internal;
import org.jooq.impl.MigrationListeners;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.impl.ThreadLocalTransactionProvider;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StopWatch;

final class MigrationImpl
extends AbstractScope
implements Migration {
    private static final JooqLogger log = JooqLogger.getLogger(Migration.class);
    private static final JooqMigrationsChangelog CHANGELOG = JooqMigrationsChangelog.JOOQ_MIGRATIONS_CHANGELOG;
    private final Commit to;
    private Commit from;
    private Queries queries;
    private Commits commits;

    MigrationImpl(Configuration configuration, Commit to) {
        super(configuration.derive(new ThreadLocalTransactionProvider(configuration.systemConnectionProvider())));
        this.to = to;
    }

    @Override
    public final Commit from() {
        if (this.from == null) {
            this.from = this.currentCommit();
        }
        return this.from;
    }

    @Override
    public final Commit to() {
        return this.to;
    }

    @Override
    public final Queries queries() {
        if (this.queries == null) {
            Files files = this.from().migrateTo(this.to());
            this.queries = files.from().migrateTo(files.to());
        }
        return this.queries;
    }

    private final Commits commits() {
        if (this.commits == null) {
            this.commits = this.configuration().commitProvider().provide();
        }
        return this.commits;
    }

    @Override
    public final void validate() {
        this.validate0(this.migrationContext());
    }

    private final void validate0(DefaultMigrationContext ctx) {
        Commit currentCommit;
        JooqMigrationsChangelogRecord currentRecord = this.currentChangelogRecord();
        if (currentRecord != null && (currentCommit = this.commits().get(currentRecord.getMigratedTo())) == null) {
            throw new DataMigrationValidationException("Version currently installed is not available from CommitProvider: " + currentRecord.getMigratedTo());
        }
        this.validateCommitProvider(ctx, this.from());
        this.validateCommitProvider(ctx, this.to());
        this.revertUntracked(ctx, null, currentRecord);
    }

    private final void validateCommitProvider(DefaultMigrationContext ctx, Commit commit) {
        if (this.commits().get(commit.id()) == null) {
            throw new DataMigrationValidationException("Commit is not available from CommitProvider: " + commit.id());
        }
        for (Schema schema : this.lookup(commit.meta().getSchemas())) {
            if (ctx.migratedSchemas().contains(schema)) continue;
            throw new DataMigrationValidationException("Schema is referenced from commit, but not configured for migration: " + String.valueOf(schema));
        }
    }

    private final Collection<Schema> lookup(List<Schema> schemas) {
        Collection<Schema> result = schemas;
        List<InterpreterSearchSchema> searchPath = this.dsl().settings().getInterpreterSearchPath();
        if (!searchPath.isEmpty()) {
            result = new HashSet();
            Schema defaultSchema = DSL.schema(DSL.name(searchPath.get(0).getCatalog(), searchPath.get(0).getSchema()));
            for (Schema schema : schemas) {
                if (schema.getQualifiedName().empty()) {
                    result.add(defaultSchema);
                    continue;
                }
                result.add(schema);
            }
        }
        return result;
    }

    private final Queries revertUntrackedQueries(Set<Schema> includedSchemas) {
        Commit currentCommit = this.currentCommit();
        Meta currentMeta = currentCommit.meta();
        Meta existingMeta = this.dsl().meta().filterSchemas(includedSchemas::contains);
        HashSet<Schema> expectedSchemas = new HashSet<Schema>();
        expectedSchemas.addAll(this.lookup(this.from().meta().getSchemas()));
        expectedSchemas.addAll(this.lookup(this.to().meta().getSchemas()));
        expectedSchemas.retainAll(includedSchemas);
        for (Schema schema : existingMeta.getSchemas()) {
            if (!includedSchemas.contains(schema)) continue;
            existingMeta = existingMeta.apply(DSL.dropTableIfExists(schema.getQualifiedName().append(CHANGELOG.getUnqualifiedName())).cascade());
            if (!expectedSchemas.contains(schema)) {
                existingMeta = existingMeta.apply(DSL.dropSchemaIfExists(schema).cascade());
                continue;
            }
            currentMeta = currentMeta.apply(DSL.createSchemaIfNotExists(schema));
        }
        return existingMeta.migrateTo(currentMeta);
    }

    private final void revertUntracked(DefaultMigrationContext ctx, MigrationListener listener, JooqMigrationsChangelogRecord currentRecord) {
        if (ctx.revertUntrackedQueries.queries().length > 0) {
            if (!Boolean.TRUE.equals(this.dsl().settings().isMigrationRevertUntracked())) {
                throw new DataMigrationValidationException("Non-empty difference between actual schema and migration from schema: " + String.valueOf(ctx.revertUntrackedQueries) + (currentRecord == null ? "\n\nUse Settings.migrationAutoBaseline to automatically set a baseline" : ""));
            }
            if (listener != null) {
                this.execute(ctx, listener, ctx.revertUntrackedQueries);
            }
        }
    }

    private final DefaultMigrationContext migrationContext() {
        Set<Schema> schemas = this.schemas();
        return new DefaultMigrationContext(this.configuration(), schemas, this.from(), this.to(), this.queries(), this.revertUntrackedQueries(schemas));
    }

    private final Set<Schema> schemas() {
        LinkedHashSet<Schema> set = new LinkedHashSet<Schema>();
        for (MigrationSchema schema : this.configuration.settings().getMigrationSchemata()) {
            set.addAll(this.lookup(Arrays.asList(DSL.schema(DSL.name(schema.getCatalog(), schema.getSchema())))));
        }
        return set;
    }

    @Override
    public final void execute() {
        this.run(new ContextTransactionalRunnable(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void run() {
                DefaultMigrationContext ctx = MigrationImpl.this.migrationContext();
                MigrationListeners listener = new MigrationListeners(MigrationImpl.this.configuration);
                if (!Boolean.FALSE.equals(MigrationImpl.this.dsl().settings().isMigrationAutoValidation())) {
                    MigrationImpl.this.validate0(ctx);
                }
                try {
                    listener.migrationStart(ctx);
                    if (MigrationImpl.this.from().equals(MigrationImpl.this.to())) {
                        log.info((Object)"jOOQ Migrations", "Version " + MigrationImpl.this.to().id() + " is already installed as the current version.");
                        return;
                    }
                    log.info((Object)"jOOQ Migrations", "Version " + MigrationImpl.this.from().id() + " is migrated to " + MigrationImpl.this.to().id());
                    StopWatch watch = new StopWatch();
                    if (log.isDebugEnabled()) {
                        for (Query query : MigrationImpl.this.queries()) {
                            log.debug((Object)"jOOQ Migrations", MigrationImpl.this.dsl().renderInlined(query));
                        }
                    }
                    JooqMigrationsChangelogRecord record = this.createRecord(Status.STARTING);
                    try {
                        this.log(watch, record, Status.REVERTING);
                        MigrationImpl.this.revertUntracked(ctx, listener, record);
                        this.log(watch, record, Status.MIGRATING);
                        MigrationImpl.this.execute(ctx, listener, MigrationImpl.this.queries());
                        this.log(watch, record, Status.SUCCESS);
                    }
                    catch (DataAccessException e) {
                        this.log(watch, record, Status.FAILURE);
                        throw e;
                    }
                }
                finally {
                    listener.migrationEnd(ctx);
                }
            }

            private final JooqMigrationsChangelogRecord createRecord(Status status) {
                JooqMigrationsChangelogRecord record = MigrationImpl.this.dsl().newRecord(CHANGELOG);
                record.setJooqVersion("3.18.7").setMigratedAt(new Timestamp(MigrationImpl.this.dsl().configuration().clock().instant().toEpochMilli())).setMigratedFrom(MigrationImpl.this.from().id()).setMigratedTo(MigrationImpl.this.to().id()).setMigrationTime(0L).setSql(MigrationImpl.this.queries().toString()).setSqlCount(MigrationImpl.this.queries().queries().length).setStatus(status).insert();
                return record;
            }

            private final void log(StopWatch watch, JooqMigrationsChangelogRecord record, Status status) {
                record.setMigrationTime(watch.split() / 1000000L).setStatus(status).update();
            }
        });
    }

    private final void execute(DefaultMigrationContext ctx, MigrationListener listener, Queries q) {
        listener.queriesStart(ctx);
        for (Query query : q.queries()) {
            ctx.query(query);
            listener.queryStart(ctx);
            query.execute();
            listener.queryEnd(ctx);
            ctx.query(null);
        }
        listener.queriesEnd(ctx);
    }

    public final void init() {
        if (!this.existsChangelog()) {
            this.dsl().meta(CHANGELOG).ddl().executeBatch();
        }
    }

    private final boolean existsChangelog() {
        try {
            this.dsl().fetchExists(CHANGELOG);
            return true;
        }
        catch (DataAccessException dataAccessException) {
            return false;
        }
    }

    private final JooqMigrationsChangelogRecord currentChangelogRecord() {
        return this.existsChangelog() ? (JooqMigrationsChangelogRecord)this.dsl().selectFrom(CHANGELOG).where(MigrationImpl.CHANGELOG.STATUS.eq(DSL.inline(Status.SUCCESS))).orderBy(MigrationImpl.CHANGELOG.MIGRATED_AT.desc(), MigrationImpl.CHANGELOG.ID.desc()).limit(1).fetchOne() : null;
    }

    private final Commit currentCommit() {
        JooqMigrationsChangelogRecord currentRecord = this.currentChangelogRecord();
        if (currentRecord == null) {
            Commit result;
            Commit commit = result = Boolean.TRUE.equals(this.settings().isMigrationAutoBaseline()) ? this.to() : (Commit)this.to().root();
            if (result == null) {
                throw new DataMigrationValidationException("CommitProvider did not provide a root version for " + this.to().id());
            }
            return result;
        }
        Commit result = this.commits().get(currentRecord.getMigratedTo());
        if (result == null) {
            throw new DataMigrationValidationException("CommitProvider did not provide a version for " + currentRecord.getMigratedTo());
        }
        return result;
    }

    private final void run(ContextTransactionalRunnable runnable2) {
        try {
            this.init();
            this.dsl().transaction(runnable2);
        }
        catch (DataMigrationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new DataMigrationException("Exception during migration", e);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-- Migration\n--   From: ").append(this.from().id()).append("\n").append("--   To  : ").append(this.to().id()).append("\n").append(this.queries());
        return sb.toString();
    }

    static class JooqMigrationsChangelogRecord
    extends UpdatableRecordImpl<JooqMigrationsChangelogRecord> {
        public JooqMigrationsChangelogRecord setId(Long value) {
            this.set(0, value);
            return this;
        }

        public Long getId() {
            return (Long)this.get(0);
        }

        public JooqMigrationsChangelogRecord setMigratedFrom(String value) {
            this.set(1, value);
            return this;
        }

        public String getMigratedFrom() {
            return (String)this.get(1);
        }

        public JooqMigrationsChangelogRecord setMigratedTo(String value) {
            this.set(2, value);
            return this;
        }

        public String getMigratedTo() {
            return (String)this.get(2);
        }

        public JooqMigrationsChangelogRecord setMigratedAt(Timestamp value) {
            this.set(3, value);
            return this;
        }

        public Timestamp getMigratedAt() {
            return (Timestamp)this.get(3);
        }

        public JooqMigrationsChangelogRecord setMigrationTime(Long value) {
            this.set(4, value);
            return this;
        }

        public Long getMigrationTime() {
            return (Long)this.get(4);
        }

        public JooqMigrationsChangelogRecord setJooqVersion(String value) {
            this.set(5, value);
            return this;
        }

        public String getJooqVersion() {
            return (String)this.get(5);
        }

        public JooqMigrationsChangelogRecord setSql(String value) {
            this.set(6, value);
            return this;
        }

        public String getSql() {
            return (String)this.get(6);
        }

        public JooqMigrationsChangelogRecord setSqlCount(Integer value) {
            this.set(7, value);
            return this;
        }

        public Integer getSqlCount() {
            return (Integer)this.get(7);
        }

        public JooqMigrationsChangelogRecord setStatus(Status value) {
            this.set(8, (Object)value);
            return this;
        }

        public Status getStatus() {
            return (Status)((Object)this.get(8));
        }

        @Override
        public Record1<Long> key() {
            return (Record1)super.key();
        }

        public JooqMigrationsChangelogRecord() {
            super(JooqMigrationsChangelog.JOOQ_MIGRATIONS_CHANGELOG);
        }

        public JooqMigrationsChangelogRecord(Long id, String migratedFrom, String migratedTo, Timestamp migratedAt, Long migrationTime, String jooqVersion, String sql, String status) {
            super(JooqMigrationsChangelog.JOOQ_MIGRATIONS_CHANGELOG);
            this.set(0, id);
            this.set(1, migratedFrom);
            this.set(2, migratedTo);
            this.set(3, migratedAt);
            this.set(4, migrationTime);
            this.set(5, jooqVersion);
            this.set(6, sql);
            this.set(7, status);
        }
    }

    static class JooqMigrationsChangelog
    extends TableImpl<JooqMigrationsChangelogRecord> {
        public static final JooqMigrationsChangelog JOOQ_MIGRATIONS_CHANGELOG = new JooqMigrationsChangelog();
        public final TableField<JooqMigrationsChangelogRecord, Long> ID = JooqMigrationsChangelog.createField(DSL.name("ID"), SQLDataType.BIGINT.nullable(false).identity(true), this, "The database version ID.");
        public final TableField<JooqMigrationsChangelogRecord, String> MIGRATED_FROM = JooqMigrationsChangelog.createField(DSL.name("MIGRATED_FROM"), SQLDataType.VARCHAR(255).nullable(false), this, "The previous database version ID.");
        public final TableField<JooqMigrationsChangelogRecord, String> MIGRATED_TO = JooqMigrationsChangelog.createField(DSL.name("MIGRATED_TO"), SQLDataType.VARCHAR(255).nullable(false), this, "");
        public final TableField<JooqMigrationsChangelogRecord, Timestamp> MIGRATED_AT = JooqMigrationsChangelog.createField(DSL.name("MIGRATED_AT"), SQLDataType.TIMESTAMP.precision(6).nullable(false), this, "The date/time when the database version was migrated to.");
        public final TableField<JooqMigrationsChangelogRecord, Long> MIGRATION_TIME = JooqMigrationsChangelog.createField(DSL.name("MIGRATION_TIME"), SQLDataType.BIGINT, this, "The time in milliseconds it took to migrate to this database version.");
        public final TableField<JooqMigrationsChangelogRecord, String> JOOQ_VERSION = JooqMigrationsChangelog.createField(DSL.name("JOOQ_VERSION"), SQLDataType.VARCHAR(50).nullable(false), this, "The jOOQ version used to migrate to this database version.");
        public final TableField<JooqMigrationsChangelogRecord, String> SQL = JooqMigrationsChangelog.createField(DSL.name("SQL"), SQLDataType.CLOB, this, "The SQL statements that were run to install this database version.");
        public final TableField<JooqMigrationsChangelogRecord, Integer> SQL_COUNT = JooqMigrationsChangelog.createField(DSL.name("SQL_COUNT"), SQLDataType.INTEGER, this, "The number of SQL statements that were run to install this database version.");
        public final TableField<JooqMigrationsChangelogRecord, Status> STATUS = JooqMigrationsChangelog.createField(DSL.name("STATUS"), SQLDataType.VARCHAR(10).nullable(false).asConvertedDataType(new EnumConverter<String, Status>(String.class, Status.class)), this, "The database version installation status.");

        @Override
        public Class<JooqMigrationsChangelogRecord> getRecordType() {
            return JooqMigrationsChangelogRecord.class;
        }

        public JooqMigrationsChangelog() {
            this(DSL.name("JOOQ_MIGRATIONS_CHANGELOG"), (Table<JooqMigrationsChangelogRecord>)null);
        }

        public JooqMigrationsChangelog(String alias) {
            this(DSL.name(alias), JOOQ_MIGRATIONS_CHANGELOG);
        }

        public JooqMigrationsChangelog(Name alias) {
            this(alias, JOOQ_MIGRATIONS_CHANGELOG);
        }

        private JooqMigrationsChangelog(Name alias, Table<JooqMigrationsChangelogRecord> aliased) {
            this(alias, aliased, null);
        }

        private JooqMigrationsChangelog(Name alias, Table<JooqMigrationsChangelogRecord> aliased, Field<?>[] parameters) {
            super(alias, null, aliased, parameters, DSL.comment("The migration log of jOOQ Migrations."));
        }

        @Override
        public UniqueKey<JooqMigrationsChangelogRecord> getPrimaryKey() {
            return Internal.createUniqueKey(JOOQ_MIGRATIONS_CHANGELOG, "JOOQ_MIGRATIONS_CHANGELOG_PK", JooqMigrationsChangelog.JOOQ_MIGRATIONS_CHANGELOG.ID);
        }
    }

    static enum Status {
        STARTING,
        REVERTING,
        MIGRATING,
        SUCCESS,
        FAILURE;

    }
}

