/*
 * Decompiled with CFR 0.152.
 */
package org.h2.schema;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.h2.command.ddl.CreateSynonymData;
import org.h2.command.ddl.CreateTableData;
import org.h2.constraint.Constraint;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.DbSettings;
import org.h2.engine.Right;
import org.h2.engine.RightOwner;
import org.h2.engine.SessionLocal;
import org.h2.engine.SysProperties;
import org.h2.index.Index;
import org.h2.message.DbException;
import org.h2.schema.Constant;
import org.h2.schema.Domain;
import org.h2.schema.FunctionAlias;
import org.h2.schema.SchemaObject;
import org.h2.schema.Sequence;
import org.h2.schema.TriggerObject;
import org.h2.schema.UserAggregate;
import org.h2.schema.UserDefinedFunction;
import org.h2.table.MaterializedView;
import org.h2.table.MetaTable;
import org.h2.table.Table;
import org.h2.table.TableLink;
import org.h2.table.TableSynonym;
import org.h2.util.Utils;

public class Schema
extends DbObject {
    private RightOwner owner;
    private final boolean system;
    private ArrayList<String> tableEngineParams;
    private final ConcurrentHashMap<String, Table> tablesAndViews;
    private final ConcurrentHashMap<String, Domain> domains;
    private final ConcurrentHashMap<String, TableSynonym> synonyms;
    private final ConcurrentHashMap<String, Index> indexes;
    private final ConcurrentHashMap<String, Sequence> sequences;
    private final ConcurrentHashMap<String, TriggerObject> triggers;
    private final ConcurrentHashMap<String, Constraint> constraints;
    private final ConcurrentHashMap<String, Constant> constants;
    private final ConcurrentHashMap<String, UserDefinedFunction> functionsAndAggregates;
    private final HashSet<String> temporaryUniqueNames = new HashSet();

    public Schema(Database database, int n, String string, RightOwner rightOwner, boolean bl) {
        super(database, n, string, 8);
        this.tablesAndViews = database.newConcurrentStringMap();
        this.domains = database.newConcurrentStringMap();
        this.synonyms = database.newConcurrentStringMap();
        this.indexes = database.newConcurrentStringMap();
        this.sequences = database.newConcurrentStringMap();
        this.triggers = database.newConcurrentStringMap();
        this.constraints = database.newConcurrentStringMap();
        this.constants = database.newConcurrentStringMap();
        this.functionsAndAggregates = database.newConcurrentStringMap();
        this.owner = rightOwner;
        this.system = bl;
    }

    public boolean canDrop() {
        return !this.system;
    }

    @Override
    public String getCreateSQL() {
        if (this.system) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder("CREATE SCHEMA IF NOT EXISTS ");
        this.getSQL(stringBuilder, 0).append(" AUTHORIZATION ");
        this.owner.getSQL(stringBuilder, 0);
        return stringBuilder.toString();
    }

    @Override
    public int getType() {
        return 10;
    }

    public boolean isEmpty() {
        return this.tablesAndViews.isEmpty() && this.domains.isEmpty() && this.synonyms.isEmpty() && this.indexes.isEmpty() && this.sequences.isEmpty() && this.triggers.isEmpty() && this.constraints.isEmpty() && this.constants.isEmpty() && this.functionsAndAggregates.isEmpty();
    }

    @Override
    public ArrayList<DbObject> getChildren() {
        ArrayList<DbObject> arrayList = Utils.newSmallArrayList();
        ArrayList<Right> arrayList2 = this.database.getAllRights();
        for (Right right : arrayList2) {
            if (right.getGrantedObject() != this) continue;
            arrayList.add(right);
        }
        return arrayList;
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        this.removeChildrenFromMap(sessionLocal, this.triggers);
        this.removeChildrenFromMap(sessionLocal, this.constraints);
        boolean bl = true;
        while (!this.tablesAndViews.isEmpty()) {
            boolean bl2 = false;
            for (Table table : this.tablesAndViews.values()) {
                if (table.getName() == null) continue;
                Table table2 = this.database.getDependentTable(table, table);
                if (table2 == null) {
                    this.database.removeSchemaObject(sessionLocal, table);
                    bl2 = true;
                    continue;
                }
                if (table2.getSchema() != this) {
                    throw DbException.get(90107, table.getTraceSQL(), table2.getTraceSQL());
                }
                if (bl) continue;
                table2.removeColumnExpressionsDependencies(sessionLocal);
                table2.setModified();
                this.database.updateMeta(sessionLocal, table2);
            }
            bl = bl2;
        }
        this.removeChildrenFromMap(sessionLocal, this.domains);
        this.removeChildrenFromMap(sessionLocal, this.indexes);
        this.removeChildrenFromMap(sessionLocal, this.sequences);
        this.removeChildrenFromMap(sessionLocal, this.constants);
        this.removeChildrenFromMap(sessionLocal, this.functionsAndAggregates);
        for (Right right : this.database.getAllRights()) {
            if (right.getGrantedObject() != this) continue;
            this.database.removeDatabaseObject(sessionLocal, right);
        }
        this.database.removeMeta(sessionLocal, this.getId());
        this.owner = null;
        this.invalidate();
    }

    private void removeChildrenFromMap(SessionLocal sessionLocal, ConcurrentHashMap<String, ? extends SchemaObject> concurrentHashMap) {
        if (!concurrentHashMap.isEmpty()) {
            for (SchemaObject schemaObject : concurrentHashMap.values()) {
                if (!schemaObject.isValid()) continue;
                this.database.removeSchemaObject(sessionLocal, schemaObject);
            }
        }
    }

    public RightOwner getOwner() {
        return this.owner;
    }

    public ArrayList<String> getTableEngineParams() {
        return this.tableEngineParams;
    }

    public void setTableEngineParams(ArrayList<String> arrayList) {
        this.tableEngineParams = arrayList;
    }

    private Map<String, SchemaObject> getMap(int n) {
        ConcurrentHashMap<String, SchemaObject> concurrentHashMap;
        switch (n) {
            case 0: {
                concurrentHashMap = this.tablesAndViews;
                break;
            }
            case 12: {
                concurrentHashMap = this.domains;
                break;
            }
            case 15: {
                concurrentHashMap = this.synonyms;
                break;
            }
            case 3: {
                concurrentHashMap = this.sequences;
                break;
            }
            case 1: {
                concurrentHashMap = this.indexes;
                break;
            }
            case 4: {
                concurrentHashMap = this.triggers;
                break;
            }
            case 5: {
                concurrentHashMap = this.constraints;
                break;
            }
            case 11: {
                concurrentHashMap = this.constants;
                break;
            }
            case 9: 
            case 14: {
                concurrentHashMap = this.functionsAndAggregates;
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + n);
            }
        }
        return concurrentHashMap;
    }

    public void add(SchemaObject schemaObject) {
        if (schemaObject.getSchema() != this) {
            throw DbException.getInternalError("wrong schema");
        }
        String string = schemaObject.getName();
        Map<String, SchemaObject> map = this.getMap(schemaObject.getType());
        if (map.putIfAbsent(string, schemaObject) != null) {
            throw DbException.getInternalError("object already exists: " + string);
        }
        this.freeUniqueName(string);
    }

    public void rename(SchemaObject schemaObject, String string) {
        int n = schemaObject.getType();
        Map<String, SchemaObject> map = this.getMap(n);
        if (SysProperties.CHECK) {
            if (!map.containsKey(schemaObject.getName()) && !(schemaObject instanceof MetaTable)) {
                throw DbException.getInternalError("not found: " + schemaObject.getName());
            }
            if (schemaObject.getName().equals(string) || map.containsKey(string)) {
                throw DbException.getInternalError("object already exists: " + string);
            }
        }
        schemaObject.checkRename();
        map.remove(schemaObject.getName());
        this.freeUniqueName(schemaObject.getName());
        schemaObject.rename(string);
        map.put(string, schemaObject);
        this.freeUniqueName(string);
    }

    public Table findTableOrView(SessionLocal sessionLocal, String string) {
        Table table = this.tablesAndViews.get(string);
        if (table == null && sessionLocal != null) {
            table = sessionLocal.findLocalTempTable(string);
        }
        return table;
    }

    public Table resolveTableOrView(SessionLocal sessionLocal, String string) {
        return this.resolveTableOrView(sessionLocal, string, true);
    }

    public Table resolveTableOrView(SessionLocal sessionLocal, String string, boolean bl) {
        SchemaObject schemaObject;
        Table table = this.findTableOrView(sessionLocal, string);
        if (table == null && (schemaObject = this.synonyms.get(string)) != null) {
            return ((TableSynonym)schemaObject).getSynonymFor();
        }
        if (bl && table instanceof MaterializedView) {
            schemaObject = (MaterializedView)table;
            return ((MaterializedView)schemaObject).getUnderlyingTable();
        }
        return table;
    }

    public TableSynonym getSynonym(String string) {
        return this.synonyms.get(string);
    }

    public Domain findDomain(String string) {
        return this.domains.get(string);
    }

    public Index findIndex(SessionLocal sessionLocal, String string) {
        Index index = this.indexes.get(string);
        if (index == null) {
            index = sessionLocal.findLocalTempTableIndex(string);
        }
        return index;
    }

    public TriggerObject findTrigger(String string) {
        return this.triggers.get(string);
    }

    public Sequence findSequence(String string) {
        return this.sequences.get(string);
    }

    public Constraint findConstraint(SessionLocal sessionLocal, String string) {
        Constraint constraint = this.constraints.get(string);
        if (constraint == null) {
            constraint = sessionLocal.findLocalTempTableConstraint(string);
        }
        return constraint;
    }

    public Constant findConstant(String string) {
        return this.constants.get(string);
    }

    public FunctionAlias findFunction(String string) {
        UserDefinedFunction userDefinedFunction = this.findFunctionOrAggregate(string);
        return userDefinedFunction instanceof FunctionAlias ? (FunctionAlias)userDefinedFunction : null;
    }

    public UserAggregate findAggregate(String string) {
        UserDefinedFunction userDefinedFunction = this.findFunctionOrAggregate(string);
        return userDefinedFunction instanceof UserAggregate ? (UserAggregate)userDefinedFunction : null;
    }

    public UserDefinedFunction findFunctionOrAggregate(String string) {
        return this.functionsAndAggregates.get(string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void reserveUniqueName(String string) {
        if (string != null) {
            HashSet<String> hashSet = this.temporaryUniqueNames;
            synchronized (hashSet) {
                this.temporaryUniqueNames.add(string);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void freeUniqueName(String string) {
        if (string != null) {
            HashSet<String> hashSet = this.temporaryUniqueNames;
            synchronized (hashSet) {
                this.temporaryUniqueNames.remove(string);
            }
        }
    }

    private String getUniqueName(DbObject dbObject, Map<String, ? extends SchemaObject> map, String string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        String string2 = Integer.toHexString(dbObject.getName().hashCode());
        HashSet<String> hashSet = this.temporaryUniqueNames;
        synchronized (hashSet) {
            int n;
            int n2 = string2.length();
            for (n = 0; n < n2; ++n) {
                char c = string2.charAt(n);
                String string3 = stringBuilder.append(c >= 'a' ? (char)(c - 32) : c).toString();
                if (map.containsKey(string3) || !this.temporaryUniqueNames.add(string3)) continue;
                return string3;
            }
            n = stringBuilder.append('_').length();
            n2 = 0;
            while (true) {
                String string4;
                if (!map.containsKey(string4 = stringBuilder.append(n2).toString()) && this.temporaryUniqueNames.add(string4)) {
                    return string4;
                }
                stringBuilder.setLength(n);
                ++n2;
            }
        }
    }

    public String getUniqueConstraintName(SessionLocal sessionLocal, Table table) {
        AbstractMap abstractMap = table.isTemporary() && !table.isGlobalTemporary() ? sessionLocal.getLocalTempTableConstraints() : this.constraints;
        return this.getUniqueName(table, abstractMap, "CONSTRAINT_");
    }

    public String getUniqueDomainConstraintName(SessionLocal sessionLocal, Domain domain) {
        return this.getUniqueName(domain, this.constraints, "CONSTRAINT_");
    }

    public String getUniqueIndexName(SessionLocal sessionLocal, Table table, String string) {
        AbstractMap abstractMap = table.isTemporary() && !table.isGlobalTemporary() ? sessionLocal.getLocalTempTableIndexes() : this.indexes;
        return this.getUniqueName(table, abstractMap, string);
    }

    public Table getTableOrView(SessionLocal sessionLocal, String string) {
        Table table = this.tablesAndViews.get(string);
        if (table == null) {
            if (sessionLocal != null) {
                table = sessionLocal.findLocalTempTable(string);
            }
            if (table == null) {
                throw DbException.get(42102, string);
            }
        }
        return table;
    }

    public Domain getDomain(String string) {
        Domain domain = this.domains.get(string);
        if (domain == null) {
            throw DbException.get(90120, string);
        }
        return domain;
    }

    public Index getIndex(String string) {
        Index index = this.indexes.get(string);
        if (index == null) {
            throw DbException.get(42112, string);
        }
        return index;
    }

    public Constraint getConstraint(String string) {
        Constraint constraint = this.constraints.get(string);
        if (constraint == null) {
            throw DbException.get(90057, string);
        }
        return constraint;
    }

    public Constant getConstant(String string) {
        Constant constant = this.constants.get(string);
        if (constant == null) {
            throw DbException.get(90115, string);
        }
        return constant;
    }

    public Sequence getSequence(String string) {
        Sequence sequence2 = this.sequences.get(string);
        if (sequence2 == null) {
            throw DbException.get(90036, string);
        }
        return sequence2;
    }

    public ArrayList<SchemaObject> getAll(ArrayList<SchemaObject> arrayList) {
        if (arrayList == null) {
            arrayList = Utils.newSmallArrayList();
        }
        arrayList.addAll(this.tablesAndViews.values());
        arrayList.addAll(this.domains.values());
        arrayList.addAll(this.synonyms.values());
        arrayList.addAll(this.sequences.values());
        arrayList.addAll(this.indexes.values());
        arrayList.addAll(this.triggers.values());
        arrayList.addAll(this.constraints.values());
        arrayList.addAll(this.constants.values());
        arrayList.addAll(this.functionsAndAggregates.values());
        return arrayList;
    }

    public void getAll(int n, ArrayList<SchemaObject> arrayList) {
        arrayList.addAll(this.getMap(n).values());
    }

    public Collection<Domain> getAllDomains() {
        return this.domains.values();
    }

    public Collection<Constraint> getAllConstraints() {
        return this.constraints.values();
    }

    public Collection<Constant> getAllConstants() {
        return this.constants.values();
    }

    public Collection<Sequence> getAllSequences() {
        return this.sequences.values();
    }

    public Collection<TriggerObject> getAllTriggers() {
        return this.triggers.values();
    }

    public Collection<Table> getAllTablesAndViews(SessionLocal sessionLocal) {
        return this.tablesAndViews.values();
    }

    public Collection<Index> getAllIndexes() {
        return this.indexes.values();
    }

    public Collection<TableSynonym> getAllSynonyms() {
        return this.synonyms.values();
    }

    public Collection<UserDefinedFunction> getAllFunctionsAndAggregates() {
        return this.functionsAndAggregates.values();
    }

    public Table getTableOrViewByName(SessionLocal sessionLocal, String string) {
        return this.tablesAndViews.get(string);
    }

    public void remove(SchemaObject schemaObject) {
        String string = schemaObject.getName();
        Map<String, SchemaObject> map = this.getMap(schemaObject.getType());
        if (map.remove(string) == null) {
            throw DbException.getInternalError("not found: " + string);
        }
        this.freeUniqueName(string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Table createTable(CreateTableData createTableData) {
        Database database = this.database;
        synchronized (database) {
            if (!createTableData.temporary || createTableData.globalTemporary) {
                this.database.lockMeta(createTableData.session);
            }
            createTableData.schema = this;
            String string = createTableData.tableEngine;
            if (string == null) {
                DbSettings dbSettings = this.database.getSettings();
                string = dbSettings.defaultTableEngine;
                if (string == null) {
                    return this.database.getStore().createTable(createTableData);
                }
                createTableData.tableEngine = string;
            }
            if (createTableData.tableEngineParams == null) {
                createTableData.tableEngineParams = this.tableEngineParams;
            }
            return this.database.getTableEngine(string).createTable(createTableData);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TableSynonym createSynonym(CreateSynonymData createSynonymData) {
        Database database = this.database;
        synchronized (database) {
            this.database.lockMeta(createSynonymData.session);
            createSynonymData.schema = this;
            return new TableSynonym(createSynonymData);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TableLink createTableLink(int n, String string, String string2, String string3, String string4, String string5, String string6, String string7, boolean bl, boolean bl2) {
        Database database = this.database;
        synchronized (database) {
            return new TableLink(this, n, string, string2, string3, string4, string5, string6, string7, bl, bl2);
        }
    }
}

