/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import java.util.ArrayList;
import java.util.HashSet;
import org.h2.command.CommandContainer;
import org.h2.command.Parser;
import org.h2.command.ParserBase;
import org.h2.command.Prepared;
import org.h2.command.ddl.CommandWithColumns;
import org.h2.command.ddl.CreateTableData;
import org.h2.constraint.Constraint;
import org.h2.constraint.ConstraintReferential;
import org.h2.constraint.ConstraintUnique;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.index.Index;
import org.h2.index.IndexType;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;
import org.h2.schema.Sequence;
import org.h2.schema.TriggerObject;
import org.h2.table.Column;
import org.h2.table.Table;
import org.h2.table.TableBase;
import org.h2.table.TableView;
import org.h2.util.Utils;

public class AlterTableAlterColumn
extends CommandWithColumns {
    private String tableName;
    private Column oldColumn;
    private Column newColumn;
    private int type;
    private Expression defaultExpression;
    private Expression newSelectivity;
    private Expression usingExpression;
    private boolean addFirst;
    private String addBefore;
    private String addAfter;
    private boolean ifTableExists;
    private boolean ifNotExists;
    private ArrayList<Column> columnsToAdd;
    private ArrayList<Column> columnsToRemove;
    private boolean booleanFlag;

    public AlterTableAlterColumn(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setIfTableExists(boolean bl) {
        this.ifTableExists = bl;
    }

    public void setTableName(String string) {
        this.tableName = string;
    }

    public void setOldColumn(Column column) {
        this.oldColumn = column;
    }

    public void setAddFirst() {
        this.addFirst = true;
    }

    public void setAddBefore(String string) {
        this.addBefore = string;
    }

    public void setAddAfter(String string) {
        this.addAfter = string;
    }

    @Override
    public long update() {
        ArrayList<Sequence> arrayList;
        Database database = this.getDatabase();
        Table table = this.getSchema().resolveTableOrView(this.session, this.tableName);
        if (table == null) {
            if (this.ifTableExists) {
                return 0L;
            }
            throw DbException.get(42102, this.tableName);
        }
        this.session.getUser().checkTableRight(table, 32);
        table.checkSupportAlter();
        table.lock(this.session, 2);
        if (this.newColumn != null) {
            AlterTableAlterColumn.checkDefaultReferencesTable(table, this.newColumn.getDefaultExpression());
            this.checkClustering(this.newColumn);
        }
        if (this.columnsToAdd != null) {
            arrayList = this.columnsToAdd.iterator();
            while (arrayList.hasNext()) {
                Column column = arrayList.next();
                AlterTableAlterColumn.checkDefaultReferencesTable(table, column.getDefaultExpression());
                this.checkClustering(column);
            }
        }
        switch (this.type) {
            case 8: {
                if (this.oldColumn == null || !this.oldColumn.isNullable()) break;
                this.checkNoNullValues(table);
                this.oldColumn.setNullable(false);
                database.updateMeta(this.session, table);
                break;
            }
            case 9: {
                if (this.oldColumn == null || this.oldColumn.isNullable()) break;
                this.checkNullable(table);
                this.oldColumn.setNullable(true);
                database.updateMeta(this.session, table);
                break;
            }
            case 10: 
            case 98: {
                if (this.oldColumn == null || this.oldColumn.isIdentity()) break;
                if (this.defaultExpression != null) {
                    if (this.oldColumn.isGenerated()) break;
                    AlterTableAlterColumn.checkDefaultReferencesTable(table, this.defaultExpression);
                    this.oldColumn.setDefaultExpression(this.session, this.defaultExpression);
                } else {
                    if (this.type == 98 != this.oldColumn.isGenerated()) break;
                    this.oldColumn.setDefaultExpression(this.session, null);
                }
                database.updateMeta(this.session, table);
                break;
            }
            case 99: {
                if (this.oldColumn == null || (arrayList = this.oldColumn.getSequence()) == null) break;
                this.oldColumn.setSequence(null, false);
                this.removeSequence(table, (Sequence)((Object)arrayList));
                database.updateMeta(this.session, table);
                break;
            }
            case 90: {
                if (this.oldColumn == null) break;
                if (this.defaultExpression != null) {
                    if (this.oldColumn.isIdentity() || this.oldColumn.isGenerated()) break;
                    AlterTableAlterColumn.checkDefaultReferencesTable(table, this.defaultExpression);
                    this.oldColumn.setOnUpdateExpression(this.session, this.defaultExpression);
                } else {
                    this.oldColumn.setOnUpdateExpression(this.session, null);
                }
                database.updateMeta(this.session, table);
                break;
            }
            case 11: {
                if (this.oldColumn == null) break;
                if (this.oldColumn.isWideningConversion(this.newColumn) && this.usingExpression == null) {
                    this.convertIdentityColumn(table, this.oldColumn, this.newColumn);
                    this.oldColumn.copy(this.newColumn);
                    database.updateMeta(this.session, table);
                } else {
                    this.oldColumn.setSequence(null, false);
                    this.oldColumn.setDefaultExpression(this.session, null);
                    if (this.oldColumn.isNullable() && !this.newColumn.isNullable()) {
                        this.checkNoNullValues(table);
                    } else if (!this.oldColumn.isNullable() && this.newColumn.isNullable()) {
                        this.checkNullable(table);
                    }
                    if (this.oldColumn.getVisible() ^ this.newColumn.getVisible()) {
                        this.oldColumn.setVisible(this.newColumn.getVisible());
                    }
                    this.convertIdentityColumn(table, this.oldColumn, this.newColumn);
                    this.copyData(table, null, true);
                }
                table.setModified();
                break;
            }
            case 7: {
                if (this.ifNotExists && this.columnsToAdd != null && this.columnsToAdd.size() == 1 && table.doesColumnExist(this.columnsToAdd.get(0).getName())) break;
                arrayList = this.generateSequences(this.columnsToAdd, false);
                if (this.columnsToAdd != null) {
                    this.changePrimaryKeysToNotNull(this.columnsToAdd);
                }
                this.copyData(table, arrayList, true);
                break;
            }
            case 12: {
                if (table.getColumns().length - this.columnsToRemove.size() < 1) {
                    throw DbException.get(90084, this.columnsToRemove.get(0).getTraceSQL());
                }
                table.dropMultipleColumnsConstraintsAndIndexes(this.session, this.columnsToRemove);
                this.copyData(table, null, false);
                break;
            }
            case 13: {
                if (this.oldColumn == null) break;
                int n = this.newSelectivity.optimize(this.session).getValue(this.session).getInt();
                this.oldColumn.setSelectivity(n);
                database.updateMeta(this.session, table);
                break;
            }
            case 87: {
                if (this.oldColumn == null || this.oldColumn.getVisible() == this.booleanFlag) break;
                this.oldColumn.setVisible(this.booleanFlag);
                table.setModified();
                database.updateMeta(this.session, table);
                break;
            }
            case 100: {
                if (this.oldColumn == null || this.oldColumn.isDefaultOnNull() == this.booleanFlag) break;
                this.oldColumn.setDefaultOnNull(this.booleanFlag);
                table.setModified();
                database.updateMeta(this.session, table);
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.type);
            }
        }
        return 0L;
    }

    private static void checkDefaultReferencesTable(Table table, Expression expression) {
        if (expression == null) {
            return;
        }
        HashSet<DbObject> hashSet = new HashSet<DbObject>();
        ExpressionVisitor expressionVisitor = ExpressionVisitor.getDependenciesVisitor(hashSet);
        expression.isEverything(expressionVisitor);
        if (hashSet.contains(table)) {
            throw DbException.get(90083, expression.getTraceSQL());
        }
    }

    private void checkClustering(Column column) {
        if (!"''".equals(this.getDatabase().getCluster()) && column.hasIdentityOptions()) {
            throw DbException.getUnsupportedException("CLUSTERING && identity columns");
        }
    }

    private void convertIdentityColumn(Table table, Column column, Column column2) {
        if (column2.hasIdentityOptions()) {
            if (column2.isPrimaryKey() && !column.isPrimaryKey()) {
                this.addConstraintCommand(Parser.newPrimaryKeyConstraintCommand(this.session, table.getSchema(), table.getName(), column2));
            }
            int n = this.getObjectId();
            column2.initializeSequence(this.session, this.getSchema(), n, table.isTemporary());
        }
    }

    private void removeSequence(Table table, Sequence sequence2) {
        if (sequence2 != null) {
            table.removeSequence(sequence2);
            sequence2.setBelongsToTable(false);
            Database database = this.getDatabase();
            database.removeSchemaObject(this.session, sequence2);
        }
    }

    private void copyData(Table table, ArrayList<Sequence> arrayList, boolean bl) {
        String string;
        if (table.isTemporary()) {
            throw DbException.getUnsupportedException("TEMP TABLE");
        }
        Database database = this.getDatabase();
        String string2 = table.getName();
        String string3 = database.getTempTableName(string2, this.session);
        Column[] columnArray = table.getColumns();
        ArrayList<Column> arrayList3 = new ArrayList<Column>(columnArray.length);
        Table table2 = this.cloneTableStructure(table, columnArray, database, string3, arrayList3);
        if (arrayList != null) {
            for (Sequence arrayList22 : arrayList) {
                table.addSequence(arrayList22);
            }
        }
        try {
            this.checkViews(table, table2);
        }
        catch (DbException dbException) {
            StringBuilder stringBuilder = new StringBuilder("DROP TABLE ");
            table2.getSQL(stringBuilder, 0);
            this.execute(stringBuilder.toString());
            throw dbException;
        }
        String string4 = table.getName();
        ArrayList<TableView> arrayList2 = new ArrayList<TableView>(table.getDependentViews());
        for (TableView tableView : arrayList2) {
            table.removeDependentView(tableView);
        }
        StringBuilder stringBuilder = new StringBuilder("DROP TABLE ");
        table.getSQL(stringBuilder, 0).append(" IGNORE");
        this.execute(stringBuilder.toString());
        database.renameSchemaObject(this.session, table2, string4);
        for (DbObject dbObject : table2.getChildren()) {
            if (dbObject instanceof Sequence || (string = dbObject.getName()) == null || dbObject.getCreateSQL() == null || !string.startsWith(string3 + "_")) continue;
            string = string.substring(string3.length() + 1);
            SchemaObject schemaObject = (SchemaObject)dbObject;
            if (schemaObject instanceof Constraint) {
                if (schemaObject.getSchema().findConstraint(this.session, string) != null) {
                    string = schemaObject.getSchema().getUniqueConstraintName(this.session, table2);
                }
            } else if (schemaObject instanceof Index && schemaObject.getSchema().findIndex(this.session, string) != null) {
                string = schemaObject.getSchema().getUniqueIndexName(this.session, table2, string);
            }
            database.renameSchemaObject(this.session, schemaObject, string);
        }
        if (bl) {
            this.createConstraints();
        }
        for (TableView tableView : arrayList2) {
            string = tableView.getCreateSQL(true, true);
            this.execute(string);
        }
    }

    private Table cloneTableStructure(Table table, Column[] columnArray, Database database, String string, ArrayList<Column> arrayList) {
        Object object;
        for (Column object32 : columnArray) {
            arrayList.add(object32.getClone());
        }
        switch (this.type) {
            case 12: {
                for (Column column : this.columnsToRemove) {
                    Column column2 = null;
                    for (Column column3 : arrayList) {
                        if (!column3.getName().equals(column.getName())) continue;
                        column2 = column3;
                        break;
                    }
                    if (column2 == null) {
                        throw DbException.getInternalError(column.getCreateSQL());
                    }
                    arrayList.remove(column2);
                }
                break;
            }
            case 7: {
                int n = this.addFirst ? 0 : (this.addBefore != null ? table.getColumn(this.addBefore).getColumnId() : (this.addAfter != null ? table.getColumn(this.addAfter).getColumnId() + 1 : columnArray.length));
                if (this.columnsToAdd == null) break;
                for (Column column : this.columnsToAdd) {
                    arrayList.add(n++, column);
                }
                break;
            }
            case 11: {
                arrayList.set(this.oldColumn.getColumnId(), this.newColumn);
            }
        }
        int n = database.allocateObjectId();
        CreateTableData createTableData = new CreateTableData();
        createTableData.tableName = string;
        createTableData.id = n;
        createTableData.columns = arrayList;
        createTableData.temporary = table.isTemporary();
        createTableData.persistData = table.isPersistData();
        createTableData.persistIndexes = table.isPersistIndexes();
        createTableData.isHidden = table.isHidden();
        createTableData.session = this.session;
        Table table2 = this.getSchema().createTable(createTableData);
        table2.setComment(table.getComment());
        String string2 = table2.getCreateSQLForMeta();
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        block15: for (Column column : arrayList) {
            if (column.isGenerated()) continue;
            switch (this.type) {
                case 7: {
                    if (this.columnsToAdd == null || !this.columnsToAdd.contains(column)) break;
                    if (this.usingExpression == null) continue block15;
                    this.usingExpression.getUnenclosedSQL(AlterTableAlterColumn.addColumn(column, stringBuilder, stringBuilder2), 0);
                    continue block15;
                }
                case 11: {
                    if (!column.equals(this.newColumn) || this.usingExpression == null) break;
                    this.usingExpression.getUnenclosedSQL(AlterTableAlterColumn.addColumn(column, stringBuilder, stringBuilder2), 0);
                    continue block15;
                }
            }
            column.getSQL(AlterTableAlterColumn.addColumn(column, stringBuilder, stringBuilder2), 0);
        }
        String string3 = table2.getName();
        Schema schema = table2.getSchema();
        table2.removeChildrenAndResources(this.session);
        this.execute(string2);
        table2 = schema.getTableOrView(this.session, string3);
        ArrayList<Object> arrayList2 = Utils.newSmallArrayList();
        ArrayList<Object> arrayList3 = Utils.newSmallArrayList();
        boolean bl = false;
        for (DbObject dbObject : table.getChildren()) {
            SchemaObject schemaObject;
            String string4;
            Index index;
            if (dbObject instanceof Sequence || dbObject instanceof Index && (index = (Index)dbObject).getIndexType().getBelongsToConstraint() || (string4 = dbObject.getCreateSQL()) == null || dbObject instanceof TableView) continue;
            if (dbObject.getType() == 0) {
                throw DbException.getInternalError();
            }
            object = ParserBase.quoteIdentifier(string + "_" + dbObject.getName(), 0);
            String string5 = null;
            if (dbObject instanceof ConstraintReferential && ((Constraint)(schemaObject = (ConstraintReferential)dbObject)).getTable() != table) {
                string5 = ((ConstraintReferential)schemaObject).getCreateSQLForCopy(((Constraint)schemaObject).getTable(), table2, (String)object, false);
            }
            if (string5 == null) {
                string5 = dbObject.getCreateSQLForCopy(table2, (String)object);
            }
            if (string5 == null) continue;
            if (dbObject instanceof TriggerObject) {
                arrayList3.add(string5);
                continue;
            }
            if (!bl) {
                schemaObject = null;
                if (dbObject instanceof ConstraintUnique) {
                    ConstraintUnique constraintUnique = (ConstraintUnique)dbObject;
                    if (constraintUnique.getConstraintType() == Constraint.Type.PRIMARY_KEY) {
                        schemaObject = constraintUnique.getIndex();
                    }
                } else if (dbObject instanceof Index) {
                    schemaObject = (Index)dbObject;
                }
                if (schemaObject != null && TableBase.getMainIndexColumn(((Index)schemaObject).getIndexType(), ((Index)schemaObject).getIndexColumns()) != -1) {
                    this.execute(string5);
                    bl = true;
                    continue;
                }
            }
            arrayList2.add(string5);
        }
        Object object2 = table2.getSQL(new StringBuilder(128).append("INSERT INTO "), 0).append('(').append((CharSequence)stringBuilder).append(") OVERRIDING SYSTEM VALUE SELECT ");
        if (stringBuilder2.length() == 0) {
            ((StringBuilder)object2).append('*');
        } else {
            ((StringBuilder)object2).append((CharSequence)stringBuilder2);
        }
        table.getSQL(((StringBuilder)object2).append(" FROM "), 0);
        try {
            this.execute(((StringBuilder)object2).toString());
        }
        catch (Throwable throwable) {
            object2 = new StringBuilder("DROP TABLE ");
            table2.getSQL((StringBuilder)object2, 0);
            this.execute(((StringBuilder)object2).toString());
            throw throwable;
        }
        for (String string6 : arrayList2) {
            this.execute(string6);
        }
        table.setModified();
        for (Column column : arrayList) {
            object = column.getSequence();
            if (object == null) continue;
            table.removeSequence((Sequence)object);
            column.setSequence(null, false);
        }
        for (String string7 : arrayList3) {
            this.execute(string7);
        }
        return table2;
    }

    private static StringBuilder addColumn(Column column, StringBuilder stringBuilder, StringBuilder stringBuilder2) {
        if (stringBuilder.length() > 0) {
            stringBuilder.append(", ");
        }
        column.getSQL(stringBuilder, 0);
        if (stringBuilder2.length() > 0) {
            stringBuilder2.append(", ");
        }
        return stringBuilder2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void checkViews(SchemaObject schemaObject, SchemaObject schemaObject2) {
        String string = schemaObject.getName();
        String string2 = schemaObject2.getName();
        Database database = schemaObject.getDatabase();
        String string3 = database.getTempTableName(string, this.session);
        database.renameSchemaObject(this.session, schemaObject, string3);
        try {
            database.renameSchemaObject(this.session, schemaObject2, string);
            this.checkViewsAreValid(schemaObject);
        }
        finally {
            try {
                database.renameSchemaObject(this.session, schemaObject2, string2);
            }
            finally {
                database.renameSchemaObject(this.session, schemaObject, string);
            }
        }
    }

    private void checkViewsAreValid(DbObject dbObject) {
        for (DbObject dbObject2 : dbObject.getChildren()) {
            if (!(dbObject2 instanceof TableView)) continue;
            String string = ((TableView)dbObject2).getQuerySQL();
            try {
                this.session.prepare(string);
            }
            catch (DbException dbException) {
                throw DbException.get(90083, dbException, dbObject2.getTraceSQL());
            }
            this.checkViewsAreValid(dbObject2);
        }
    }

    private void execute(String string) {
        Prepared prepared = this.session.prepare(string);
        CommandContainer commandContainer = new CommandContainer(this.session, string, prepared);
        commandContainer.executeUpdate(null);
    }

    private void checkNullable(Table table) {
        if (this.oldColumn.isIdentity()) {
            throw DbException.get(90023, this.oldColumn.getName());
        }
        for (Index index : table.getIndexes()) {
            IndexType indexType;
            if (index.getColumnIndex(this.oldColumn) < 0 || !(indexType = index.getIndexType()).isPrimaryKey()) continue;
            throw DbException.get(90023, this.oldColumn.getName());
        }
    }

    private void checkNoNullValues(Table table) {
        StringBuilder stringBuilder = new StringBuilder("SELECT COUNT(*) FROM ");
        table.getSQL(stringBuilder, 0).append(" WHERE ");
        this.oldColumn.getSQL(stringBuilder, 0).append(" IS NULL");
        String string = stringBuilder.toString();
        Prepared prepared = this.session.prepare(string);
        ResultInterface resultInterface = prepared.query(0L);
        resultInterface.next();
        if (resultInterface.currentRow()[0].getInt() > 0) {
            throw DbException.get(90081, this.oldColumn.getTraceSQL());
        }
    }

    public void setType(int n) {
        this.type = n;
    }

    public void setSelectivity(Expression expression) {
        this.newSelectivity = expression;
    }

    public void setDefaultExpression(Expression expression) {
        this.defaultExpression = expression;
    }

    public void setUsingExpression(Expression expression) {
        this.usingExpression = expression;
    }

    public void setNewColumn(Column column) {
        this.newColumn = column;
    }

    @Override
    public int getType() {
        return this.type;
    }

    public void setIfNotExists(boolean bl) {
        this.ifNotExists = bl;
    }

    @Override
    public void addColumn(Column column) {
        if (this.columnsToAdd == null) {
            this.columnsToAdd = new ArrayList();
        }
        this.columnsToAdd.add(column);
    }

    public void setColumnsToRemove(ArrayList<Column> arrayList) {
        this.columnsToRemove = arrayList;
    }

    public void setBooleanFlag(boolean bl) {
        this.booleanFlag = bl;
    }
}

