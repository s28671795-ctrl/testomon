/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot.jvm;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import liquibase.CatalogAndSchema;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.core.CockroachDatabase;
import liquibase.database.core.DB2Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.FirebirdDatabase;
import liquibase.database.core.H2Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MariaDBDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.executor.ExecutorService;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.SnapshotIdService;
import liquibase.snapshot.jvm.JdbcSnapshotGenerator;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RawParameterizedSqlStatement;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Sequence;

public class SequenceSnapshotGenerator
extends JdbcSnapshotGenerator {
    private static final String COMMON_PG_SEQUENCE_QUERY = "JOIN pg_namespace ns on c.relnamespace = ns.oid LEFT JOIN pg_depend d ON c.oid = d.objid WHERE c.relkind = 'S' AND ns.nspname = 'SCHEMA_NAME' AND (c.oid not in (select ds.objid FROM pg_depend ds where ds.refobjsubid > 0)OR  (   d.deptype = 'a' AND EXISTS (       select 1 from pg_attribute a         JOIN pg_class t ON t.oid = d.refobjid AND a.attrelid=t.oid AND a.attnum=d.refobjsubid         LEFT JOIN pg_catalog.pg_attrdef ad ON ad.adrelid = a.attrelid AND ad.adnum = a.attnum        WHERE a.atthasdef = false or not (pg_get_expr(ad.adbin, ad.adrelid) ilike '%' || c.relname || '%'))   ))";

    public SequenceSnapshotGenerator() {
        super(Sequence.class, new Class[]{Schema.class});
    }

    @Override
    protected void addTo(DatabaseObject foundObject, DatabaseSnapshot snapshot) throws DatabaseException, InvalidExampleException {
        if (!(foundObject instanceof Schema) || !snapshot.getDatabase().supports(Sequence.class)) {
            return;
        }
        Schema schema = (Schema)foundObject;
        Database database = snapshot.getDatabase();
        List<Map<String, ?>> sequences = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database).queryForList(this.getSelectSequenceStatement(schema, database));
        if (sequences != null) {
            for (Map<String, ?> sequence2 : sequences) {
                schema.addDatabaseObject(this.mapToSequence(sequence2, (Schema)foundObject, database));
            }
        }
    }

    @Override
    protected DatabaseObject snapshotObject(DatabaseObject example, DatabaseSnapshot snapshot) throws DatabaseException {
        if (example.getSnapshotId() != null) {
            return example;
        }
        Database database = snapshot.getDatabase();
        if (database instanceof Db2zDatabase) {
            List<Map<String, ?>> sequences = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database).queryForList(this.getSelectSequenceStatement(example.getSchema(), database));
            return this.getSequences(example, database, sequences);
        }
        if (example.getAttribute("liquibase-complete", false).booleanValue()) {
            example.setSnapshotId(SnapshotIdService.getInstance().generateId());
            example.setAttribute("liquibase-complete", null);
            return example;
        }
        if (!database.supports(Sequence.class)) {
            return null;
        }
        List<Map<String, ?>> sequences = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database).queryForList(this.getSelectSequenceStatement(example.getSchema(), database));
        return this.getSequences(example, database, sequences);
    }

    private DatabaseObject getSequences(DatabaseObject example, Database database, List<Map<String, ?>> sequences) {
        for (Map<String, ?> sequenceRow : sequences) {
            String name = this.cleanNameFromDatabase((String)sequenceRow.get("SEQUENCE_NAME"), database);
            if ((!database.isCaseSensitive() || !name.equals(example.getName())) && (database.isCaseSensitive() || !name.equalsIgnoreCase(example.getName()))) continue;
            return this.mapToSequence(sequenceRow, example.getSchema(), database);
        }
        return null;
    }

    private Sequence mapToSequence(Map<String, ?> sequenceRow, Schema schema, Database database) {
        String name = this.cleanNameFromDatabase((String)sequenceRow.get("SEQUENCE_NAME"), database);
        Sequence seq = new Sequence();
        seq.setName(name);
        seq.setSchema(schema);
        seq.setStartValue(this.toBigInteger(sequenceRow.get("START_VALUE"), database));
        seq.setMinValue(this.toBigInteger(sequenceRow.get("MIN_VALUE"), database));
        seq.setMaxValue(this.toBigInteger(sequenceRow.get("MAX_VALUE"), database));
        seq.setCacheSize(this.toBigInteger(sequenceRow.get("CACHE_SIZE"), database));
        seq.setIncrementBy(this.toBigInteger(sequenceRow.get("INCREMENT_BY"), database));
        seq.setWillCycle(this.toBoolean(sequenceRow.get("WILL_CYCLE"), database));
        seq.setOrdered(this.toBoolean(sequenceRow.get("IS_ORDERED"), database));
        if (!(database instanceof CockroachDatabase)) {
            seq.setDataType((String)sequenceRow.get("SEQ_TYPE"));
        }
        seq.setAttribute("liquibase-complete", true);
        return seq;
    }

    protected Boolean toBoolean(Object value, Database database) {
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean) {
            return (Boolean)value;
        }
        String valueAsString = value.toString();
        if ("true".equalsIgnoreCase(valueAsString = valueAsString.replace("'", "")) || "'true'".equalsIgnoreCase(valueAsString) || "y".equalsIgnoreCase(valueAsString) || "1".equalsIgnoreCase(valueAsString) || "t".equalsIgnoreCase(valueAsString)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    protected BigInteger toBigInteger(Object value, Database database) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigInteger) {
            return (BigInteger)value;
        }
        return new BigInteger(value.toString());
    }

    protected SqlStatement getSelectSequenceStatement(Schema schema, Database database) {
        if (database instanceof DB2Database) {
            if (database.getDatabaseProductName().startsWith("DB2 UDB for AS/400")) {
                return new RawSqlStatement("SELECT SEQNAME AS SEQUENCE_NAME FROM QSYS2.SYSSEQUENCES WHERE SEQSCHEMA = '" + schema.getCatalogName() + "'");
            }
            return new RawParameterizedSqlStatement("SELECT SEQNAME AS SEQUENCE_NAME FROM SYSCAT.SEQUENCES WHERE SEQTYPE='S' AND SEQSCHEMA = ?", schema.getCatalogName());
        }
        if (database instanceof Db2zDatabase) {
            return new RawParameterizedSqlStatement("SELECT NAME AS SEQUENCE_NAME, START AS START_VALUE, MINVALUE AS MIN_VALUE, MAXVALUE AS MAX_VALUE, CACHE AS CACHE_SIZE, INCREMENT AS INCREMENT_BY, CYCLE AS WILL_CYCLE, ORDER AS IS_ORDERED FROM SYSIBM.SYSSEQUENCES WHERE SEQTYPE = 'S' AND SCHEMA = ?", schema.getCatalogName());
        }
        if (database instanceof DerbyDatabase) {
            return new RawSqlStatement("SELECT   seq.SEQUENCENAME AS SEQUENCE_NAME FROM   SYS.SYSSEQUENCES seq,   SYS.SYSSCHEMAS sch WHERE   sch.SCHEMANAME = '" + new CatalogAndSchema(null, schema.getName()).customize(database).getSchemaName() + "' AND   sch.SCHEMAID = seq.SCHEMAID");
        }
        if (database instanceof FirebirdDatabase) {
            return new RawSqlStatement("SELECT TRIM(RDB$GENERATOR_NAME) AS SEQUENCE_NAME FROM RDB$GENERATORS WHERE RDB$SYSTEM_FLAG IS NULL OR RDB$SYSTEM_FLAG = 0");
        }
        if (database instanceof H2Database) {
            try {
                if (database.getDatabaseMajorVersion() <= 1) {
                    return new RawSqlStatement("SELECT SEQUENCE_NAME FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_SCHEMA = '" + schema.getName() + "' AND IS_GENERATED=FALSE");
                }
            }
            catch (DatabaseException e) {
                Scope.getCurrentScope().getLog(this.getClass()).fine("Cannot determine h2 version in order to generate sequence snapshot query");
            }
            return new RawSqlStatement("SELECT SEQUENCE_NAME FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_SCHEMA = '" + schema.getName() + "'");
        }
        if (database instanceof HsqlDatabase) {
            return new RawSqlStatement("SELECT SEQUENCE_NAME FROM INFORMATION_SCHEMA.SYSTEM_SEQUENCES WHERE SEQUENCE_SCHEMA = '" + schema.getName() + "'");
        }
        if (database instanceof InformixDatabase) {
            return new RawSqlStatement("SELECT tabname AS SEQUENCE_NAME FROM systables t, syssequences s WHERE s.tabid = t.tabid AND t.owner = '" + schema.getName() + "'");
        }
        if (database instanceof OracleDatabase) {
            String catalogName = schema.getCatalogName();
            if (catalogName == null || catalogName.isEmpty()) {
                catalogName = database.getDefaultCatalogName();
            }
            return new RawSqlStatement("SELECT sequence_name, \nCASE WHEN increment_by > 0 \n     THEN CASE WHEN min_value=1 THEN NULL ELSE min_value END\n     ELSE CASE WHEN min_value=(-999999999999999999999999999) THEN NULL else min_value END\nEND AS min_value, \nCASE WHEN increment_by > 0 \n     THEN CASE WHEN max_value=999999999999999999999999999 THEN NULL ELSE max_value END\n     ELSE CASE WHEN max_value=last_number THEN NULL else max_value END \nEND  AS max_value, \nCASE WHEN increment_by = 1 THEN NULL ELSE increment_by END AS increment_by, \nCASE WHEN cycle_flag = 'N' THEN NULL ELSE cycle_flag END AS will_cycle, \nCASE WHEN order_flag = 'N' THEN NULL ELSE order_flag END AS is_ordered, \nLAST_NUMBER as START_VALUE, \nCASE WHEN cache_size = 20 THEN NULL ELSE cache_size END AS cache_size \nFROM ALL_SEQUENCES WHERE SEQUENCE_OWNER = '" + catalogName + "'");
        }
        if (database instanceof PostgresDatabase) {
            int version = 9;
            try {
                version = database.getDatabaseMajorVersion();
            }
            catch (Exception ignore) {
                Scope.getCurrentScope().getLog(this.getClass()).warning("Failed to retrieve database version: " + ignore);
            }
            String schemaName = schema.getName();
            if (schemaName == null) {
                schemaName = database.getDefaultSchemaName();
            }
            String pgSequenceQuery = COMMON_PG_SEQUENCE_QUERY.replace("SCHEMA_NAME", schemaName);
            if (version < 10) {
                return new RawSqlStatement("SELECT c.relname AS \"SEQUENCE_NAME\" FROM pg_class c " + pgSequenceQuery);
            }
            return new RawSqlStatement("SELECT c.relname AS \"SEQUENCE_NAME\",   s.seqmin AS \"MIN_VALUE\", s.seqmax AS \"MAX_VALUE\", s.seqincrement AS \"INCREMENT_BY\",   s.seqcycle AS \"WILL_CYCLE\", s.seqstart AS \"START_VALUE\", s.seqcache AS \"CACHE_SIZE\",   pg_catalog.format_type(s.seqtypid, NULL) AS \"SEQ_TYPE\" FROM pg_class c JOIN pg_sequence s on c.oid = s.seqrelid " + pgSequenceQuery);
        }
        if (database instanceof MSSQLDatabase) {
            return new RawSqlStatement("SELECT SEQUENCE_NAME, cast(START_VALUE AS BIGINT) AS START_VALUE, cast(MINIMUM_VALUE AS BIGINT) AS MIN_VALUE, cast(MAXIMUM_VALUE AS BIGINT) AS MAX_VALUE, CAST(INCREMENT AS BIGINT) AS INCREMENT_BY, CYCLE_OPTION AS WILL_CYCLE FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_SCHEMA = '" + schema.getName() + "'");
        }
        if (database instanceof MariaDBDatabase) {
            StringJoiner j = new StringJoiner(" \n UNION\n");
            try {
                List<Map<String, ?>> res = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database).queryForList(new RawSqlStatement("select table_name AS SEQUENCE_NAME from information_schema.TABLES where TABLE_SCHEMA = '" + schema.getName() + "' and TABLE_TYPE = 'SEQUENCE' order by table_name;"));
                if (res.size() == 0) {
                    return new RawSqlStatement("SELECT 'name' AS SEQUENCE_NAME from dual WHERE 1=0");
                }
                for (Map<String, ?> e : res) {
                    String seqName = (String)e.get("SEQUENCE_NAME");
                    j.add(String.format("SELECT '%s' AS SEQUENCE_NAME, START_VALUE AS START_VALUE, MINIMUM_VALUE AS MIN_VALUE, MAXIMUM_VALUE AS MAX_VALUE, INCREMENT AS INCREMENT_BY, CYCLE_OPTION AS WILL_CYCLE FROM %s ", seqName, seqName));
                }
            }
            catch (DatabaseException e) {
                throw new UnexpectedLiquibaseException("Could not get list of schemas ", e);
            }
            return new RawSqlStatement(j.toString());
        }
        if (database instanceof SybaseASADatabase) {
            return new RawSqlStatement("SELECT SEQUENCE_NAME, START_WITH AS START_VALUE, MIN_VALUE, MAX_VALUE, INCREMENT_BY, CYCLE AS WILL_CYCLE FROM SYS.SYSSEQUENCE s JOIN SYS.SYSUSER u ON s.OWNER = u.USER_ID WHERE u.USER_NAME = '" + schema.getName() + "'");
        }
        if (database.getClass().getName().contains("MaxDB")) {
            return new RawSqlStatement("SELECT SEQUENCE_NAME, MIN_VALUE, MAX_VALUE, INCREMENT_BY, CYCLE_FLAG AS WILL_CYCLE FROM sequences WHERE SCHEMANAME = '" + schema.getName() + "'");
        }
        throw new UnexpectedLiquibaseException("Don't know how to query for sequences on " + database);
    }
}

