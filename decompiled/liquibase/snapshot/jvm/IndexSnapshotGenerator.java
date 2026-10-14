/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot.jvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.DB2Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.H2Database;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.exception.DatabaseException;
import liquibase.snapshot.CachedRow;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.JdbcDatabaseSnapshot;
import liquibase.snapshot.jvm.JdbcSnapshotGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.structure.core.UniqueConstraint;
import liquibase.structure.core.View;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;

public class IndexSnapshotGenerator
extends JdbcSnapshotGenerator {
    public IndexSnapshotGenerator() {
        super(Index.class, new Class[]{Table.class, View.class, ForeignKey.class, UniqueConstraint.class});
    }

    @Override
    protected void addTo(DatabaseObject foundObject, DatabaseSnapshot snapshot) throws DatabaseException, InvalidExampleException {
        Index exampleIndex;
        if (!snapshot.getSnapshotControl().shouldInclude(Index.class) || !snapshot.getDatabase().supports(Index.class)) {
            return;
        }
        if (foundObject instanceof Table || foundObject instanceof View) {
            if (foundObject instanceof View && !this.addToViews(snapshot.getDatabase())) {
                return;
            }
            Relation relation = (Relation)foundObject;
            Database database = snapshot.getDatabase();
            Schema schema = relation.getSchema();
            List<CachedRow> rs = null;
            JdbcDatabaseSnapshot.CachingDatabaseMetaData databaseMetaData = null;
            try {
                databaseMetaData = ((JdbcDatabaseSnapshot)snapshot).getMetaDataFromCache();
                rs = databaseMetaData.getIndexInfo(((AbstractJdbcDatabase)database).getJdbcCatalogName(schema), ((AbstractJdbcDatabase)database).getJdbcSchemaName(schema), relation.getName(), null);
                HashMap<String, Index> foundIndexes = new HashMap<String, Index>();
                for (CachedRow row : rs) {
                    String ascOrDesc;
                    String indexName = row.getString("INDEX_NAME");
                    if (indexName == null || database instanceof AbstractDb2Database && "SYSIBM".equals(row.getString("INDEX_QUALIFIER"))) continue;
                    Index index = (Index)foundIndexes.get(indexName);
                    if (index == null) {
                        index = new Index();
                        index.setName(indexName);
                        index.setRelation(relation);
                        short type = row.getShort("TYPE");
                        if (type == 1) {
                            index.setClustered(true);
                        } else if (database instanceof MSSQLDatabase) {
                            index.setClustered(false);
                        }
                        foundIndexes.put(indexName, index);
                    }
                    Boolean descending = "D".equals(ascOrDesc = database instanceof Db2zDatabase ? row.getString("ORDER") : row.getString("ASC_OR_DESC")) ? Boolean.TRUE : ("A".equals(ascOrDesc) ? Boolean.FALSE : null);
                    index.addColumn(new Column(row.getString("COLUMN_NAME")).setComputed(false).setDescending(descending).setRelation(index.getRelation()));
                }
                ArrayList<Index> stillToAdd = new ArrayList<Index>();
                for (Index exampleIndex2 : foundIndexes.values()) {
                    if (exampleIndex2.getClustered() != null && exampleIndex2.getClustered().booleanValue()) {
                        relation.getIndexes().add(exampleIndex2);
                        continue;
                    }
                    stillToAdd.add(exampleIndex2);
                }
                for (Index exampleIndex2 : stillToAdd) {
                    boolean alreadyAddedSimilar = false;
                    for (Index index : relation.getIndexes()) {
                        if (!DatabaseObjectComparatorFactory.getInstance().isSameObject(index, exampleIndex2, null, database)) continue;
                        alreadyAddedSimilar = true;
                    }
                    if (alreadyAddedSimilar) continue;
                    relation.getIndexes().add(exampleIndex2);
                }
            }
            catch (Exception e) {
                throw new DatabaseException(e);
            }
        }
        if (foundObject instanceof UniqueConstraint && ((UniqueConstraint)foundObject).getBackingIndex() == null && !(snapshot.getDatabase() instanceof DB2Database) && !(snapshot.getDatabase() instanceof DerbyDatabase)) {
            exampleIndex = new Index().setRelation(((UniqueConstraint)foundObject).getRelation());
            exampleIndex.getColumns().addAll(((UniqueConstraint)foundObject).getColumns());
            ((UniqueConstraint)foundObject).setBackingIndex(exampleIndex);
        }
        if (foundObject instanceof ForeignKey && ((ForeignKey)foundObject).getBackingIndex() == null) {
            exampleIndex = new Index().setRelation(((ForeignKey)foundObject).getForeignKeyTable());
            exampleIndex.getColumns().addAll(((ForeignKey)foundObject).getForeignKeyColumns());
            ((ForeignKey)foundObject).setBackingIndex(exampleIndex);
        }
    }

    @Override
    protected DatabaseObject snapshotObject(DatabaseObject example, DatabaseSnapshot snapshot) throws DatabaseException, InvalidExampleException {
        Database database = snapshot.getDatabase();
        Relation exampleIndex = ((Index)example).getRelation();
        String tableName = null;
        Schema schema = null;
        if (exampleIndex != null) {
            tableName = exampleIndex.getName();
            schema = exampleIndex.getSchema();
        }
        if (schema == null) {
            schema = new Schema(database.getDefaultCatalogName(), database.getDefaultSchemaName());
        }
        for (int i = 0; i < ((Index)example).getColumns().size(); ++i) {
            ((Index)example).getColumns().set(i, ((Index)example).getColumns().get(i));
        }
        String exampleName = example.getName();
        if (exampleName != null) {
            exampleName = database.correctObjectName(exampleName, Index.class);
        }
        LinkedHashMap<String, Index> foundIndexes = new LinkedHashMap<String, Index>();
        JdbcDatabaseSnapshot.CachingDatabaseMetaData databaseMetaData = null;
        List<CachedRow> rs = null;
        try {
            databaseMetaData = ((JdbcDatabaseSnapshot)snapshot).getMetaDataFromCache();
            rs = databaseMetaData.getIndexInfo(((AbstractJdbcDatabase)database).getJdbcCatalogName(schema), ((AbstractJdbcDatabase)database).getJdbcSchemaName(schema), tableName, exampleName);
            for (CachedRow row : rs) {
                Index returnIndex;
                String potentialColumnExpression;
                OracleDatabase oracle;
                String rawIndexName = row.getString("INDEX_NAME");
                String indexName = this.cleanNameFromDatabase(rawIndexName, database);
                String correctedIndexName = database.correctObjectName(indexName, Index.class);
                if (indexName == null || exampleName != null && !exampleName.equals(correctedIndexName) || database instanceof InformixDatabase && indexName.startsWith(" ")) continue;
                short type = row.getShort("TYPE");
                Boolean nonUnique = row.getBoolean("NON_UNIQUE");
                if (nonUnique == null) {
                    nonUnique = true;
                }
                String columnName = this.cleanNameFromDatabase(row.getString("COLUMN_NAME"), database);
                short position = row.getShort("ORDINAL_POSITION");
                String definition = StringUtil.trimToNull(row.getString("FILTER_CONDITION"));
                if (definition != null && !(database instanceof OracleDatabase)) {
                    definition = definition.replaceAll("\"", "");
                }
                if (columnName == null && definition == null || !(database instanceof H2Database) && type == 0) continue;
                if (database instanceof OracleDatabase && definition != null && columnName != null && (oracle = (OracleDatabase)database).isValidOracleIdentifier(potentialColumnExpression = definition.replaceFirst("^\"?(.*?)\"?$", "$1"), Index.class) && !oracle.isFunction(potentialColumnExpression)) {
                    columnName = potentialColumnExpression;
                    definition = null;
                }
                if ((returnIndex = (Index)foundIndexes.get(correctedIndexName)) == null) {
                    returnIndex = new Index();
                    Relation relation = new Table();
                    if ("V".equals(row.getString("INTERNAL_OBJECT_TYPE"))) {
                        relation = new View();
                    }
                    returnIndex.setRelation(((Relation)relation).setName(row.getString("TABLE_NAME")).setSchema(schema));
                    returnIndex.setName(indexName);
                    returnIndex.setUnique(nonUnique == false);
                    String tablespaceName = row.getString("TABLESPACE_NAME");
                    if (tablespaceName != null && database.supportsTablespaces()) {
                        returnIndex.setTablespace(tablespaceName);
                    }
                    if (type == 1) {
                        returnIndex.setClustered(true);
                    } else if (database instanceof MSSQLDatabase) {
                        returnIndex.setClustered(false);
                    }
                    if (database instanceof MSSQLDatabase) {
                        Boolean recompute = (Boolean)row.get("NO_RECOMPUTE");
                        if (recompute != null) {
                            recompute = recompute == false;
                        }
                        returnIndex.setAttribute("padIndex", row.get("IS_PADDED"));
                        returnIndex.setAttribute("fillFactor", row.get("FILL_FACTOR"));
                        returnIndex.setAttribute("ignoreDuplicateKeys", row.get("IGNORE_DUP_KEY"));
                        returnIndex.setAttribute("recomputeStatistics", recompute);
                        returnIndex.setAttribute("incrementalStatistics", row.get("IS_INCREMENTAL"));
                        returnIndex.setAttribute("allowRowLocks", row.get("ALLOW_ROW_LOCKS"));
                        returnIndex.setAttribute("allowPageLocks", row.get("ALLOW_PAGE_LOCKS"));
                    }
                    foundIndexes.put(correctedIndexName, returnIndex);
                }
                if (database instanceof MSSQLDatabase && ObjectUtil.defaultIfNull((Boolean)row.get("IS_INCLUDED_COLUMN"), false).booleanValue()) {
                    ArrayList<String> includedColumns = (ArrayList<String>)((Object)returnIndex.getAttribute("includedColumns", List.class));
                    if (includedColumns == null) {
                        includedColumns = new ArrayList<String>();
                        returnIndex.setAttribute("includedColumns", includedColumns);
                    }
                    includedColumns.add(columnName);
                    continue;
                }
                if (position == 0) continue;
                for (int i = returnIndex.getColumns().size(); i < position; ++i) {
                    returnIndex.getColumns().add(null);
                }
                if (definition == null || database instanceof PostgresDatabase || database instanceof MSSQLDatabase) {
                    String ascOrDesc = database instanceof Db2zDatabase ? row.getString("ORDER") : row.getString("ASC_OR_DESC");
                    Boolean descending = "D".equals(ascOrDesc) ? Boolean.TRUE : ("A".equals(ascOrDesc) ? Boolean.FALSE : null);
                    returnIndex.getColumns().set(position - 1, new Column(columnName).setDescending(descending).setRelation(returnIndex.getRelation()));
                    continue;
                }
                returnIndex.getColumns().set(position - 1, new Column().setRelation(returnIndex.getRelation()).setName(definition, true));
            }
        }
        catch (Exception e) {
            throw new DatabaseException(e);
        }
        if (exampleName != null) {
            return (DatabaseObject)foundIndexes.get(exampleName);
        }
        ArrayList<Index> nonClusteredIndexes = new ArrayList<Index>();
        for (Index index : foundIndexes.values()) {
            if (!DatabaseObjectComparatorFactory.getInstance().isSameObject(index.getRelation(), exampleIndex, snapshot.getSchemaComparisons(), database)) continue;
            boolean actuallyMatches = false;
            if (database.isCaseSensitive()) {
                if (index.getColumnNames().equals(((Index)example).getColumnNames())) {
                    actuallyMatches = true;
                }
            } else if (index.getColumnNames().equalsIgnoreCase(((Index)example).getColumnNames())) {
                actuallyMatches = true;
            }
            if (!actuallyMatches) continue;
            if (index.getClustered() != null && index.getClustered().booleanValue()) {
                return this.finalizeIndex(schema, tableName, index, snapshot);
            }
            nonClusteredIndexes.add(index);
        }
        if (!nonClusteredIndexes.isEmpty()) {
            return this.finalizeIndex(schema, tableName, (Index)nonClusteredIndexes.get(0), snapshot);
        }
        return null;
    }

    protected Index finalizeIndex(Schema schema, String tableName, Index index, DatabaseSnapshot snapshot) {
        if (index.isUnique() == null || !index.isUnique().booleanValue()) {
            List<Column> columns = index.getColumns();
            PrimaryKey tablePK = new PrimaryKey(null, schema.getCatalogName(), schema.getName(), tableName, columns.toArray(new Column[index.getColumns().size()]));
            if (snapshot.get(tablePK) != null) {
                index.setUnique(true);
            }
        }
        return index;
    }

    protected boolean addToViews(Database database) {
        return database instanceof MSSQLDatabase;
    }
}

