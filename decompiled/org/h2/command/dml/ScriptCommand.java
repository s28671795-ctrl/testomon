/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.h2.command.dml.ScriptBase;
import org.h2.command.dml.SetTypes;
import org.h2.constraint.Constraint;
import org.h2.engine.Comment;
import org.h2.engine.Constants;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.Right;
import org.h2.engine.RightOwner;
import org.h2.engine.Role;
import org.h2.engine.SessionLocal;
import org.h2.engine.Setting;
import org.h2.engine.User;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.index.Cursor;
import org.h2.index.Index;
import org.h2.message.DbException;
import org.h2.mvstore.DataUtils;
import org.h2.result.LocalResult;
import org.h2.result.ResultInterface;
import org.h2.result.Row;
import org.h2.schema.Constant;
import org.h2.schema.Domain;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;
import org.h2.schema.Sequence;
import org.h2.schema.TriggerObject;
import org.h2.schema.UserDefinedFunction;
import org.h2.table.Column;
import org.h2.table.PlanItem;
import org.h2.table.Table;
import org.h2.table.TableType;
import org.h2.util.IOUtils;
import org.h2.util.MathUtils;
import org.h2.util.StringUtils;
import org.h2.util.Utils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueVarchar;

public class ScriptCommand
extends ScriptBase {
    private static final Comparator<? super DbObject> BY_NAME_COMPARATOR = (dbObject, dbObject2) -> {
        int n;
        if (dbObject instanceof SchemaObject && dbObject2 instanceof SchemaObject && (n = ((SchemaObject)dbObject).getSchema().getName().compareTo(((SchemaObject)dbObject2).getSchema().getName())) != 0) {
            return n;
        }
        return dbObject.getName().compareTo(dbObject2.getName());
    };
    private Charset charset = StandardCharsets.UTF_8;
    private Set<String> schemaNames;
    private Collection<Table> tables;
    private boolean passwords;
    private boolean data;
    private boolean settings;
    private boolean drop;
    private boolean simple;
    private boolean withColumns;
    private boolean version = true;
    private LocalResult result;
    private String lineSeparatorString;
    private byte[] lineSeparator;
    private byte[] buffer;
    private boolean tempLobTableCreated;
    private int nextLobId;
    private int lobBlockSize = 4096;

    public ScriptCommand(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    @Override
    public boolean isQuery() {
        return true;
    }

    public void setSchemaNames(Set<String> set) {
        this.schemaNames = set;
    }

    public void setTables(Collection<Table> collection) {
        this.tables = collection;
    }

    public void setData(boolean bl) {
        this.data = bl;
    }

    public void setPasswords(boolean bl) {
        this.passwords = bl;
    }

    public void setSettings(boolean bl) {
        this.settings = bl;
    }

    public void setLobBlockSize(long l) {
        this.lobBlockSize = MathUtils.convertLongToInt(l);
    }

    public void setDrop(boolean bl) {
        this.drop = bl;
    }

    @Override
    public ResultInterface queryMeta() {
        LocalResult localResult = this.createResult();
        localResult.done();
        return localResult;
    }

    private LocalResult createResult() {
        return new LocalResult(this.session, new Expression[]{new ExpressionColumn(this.getDatabase(), new Column("SCRIPT", TypeInfo.TYPE_VARCHAR))}, 1, 1);
    }

    @Override
    public ResultInterface query(long l) {
        Object object;
        this.session.getUser().checkAdmin();
        this.reset();
        Database database = this.getDatabase();
        if (this.schemaNames != null) {
            for (String object22 : this.schemaNames) {
                Schema schema = database.findSchema(object22);
                if (schema != null) continue;
                throw DbException.get(90079, object22);
            }
        }
        try {
            Object object2;
            Object object32;
            this.result = this.createResult();
            this.deleteStore();
            this.openOutput();
            if (this.out != null) {
                this.buffer = new byte[4096];
            }
            if (this.version) {
                this.add("-- H2 " + Constants.VERSION, true);
            }
            if (this.settings) {
                for (Setting setting : database.getAllSettings()) {
                    if (setting.getName().equals(SetTypes.getTypeName(28))) continue;
                    this.add(setting.getCreateSQL(), false);
                }
            }
            if (this.out != null) {
                this.add("", true);
            }
            object = database.getAllUsersAndRoles().toArray(new RightOwner[0]);
            Arrays.sort(object, (rightOwner, rightOwner2) -> {
                boolean bl = rightOwner instanceof User;
                if (bl != rightOwner2 instanceof User) {
                    return bl ? -1 : 1;
                }
                if (bl && (bl = ((User)rightOwner).isAdmin()) != ((User)rightOwner2).isAdmin()) {
                    return bl ? -1 : 1;
                }
                return rightOwner.getName().compareTo(rightOwner2.getName());
            });
            for (Object object32 : object) {
                if (object32 instanceof User) {
                    this.add(((User)object32).getCreateSQL(this.passwords), false);
                    continue;
                }
                this.add(((Role)object32).getCreateSQL(true), false);
            }
            ArrayList<Schema> arrayList = new ArrayList<Schema>();
            for (Schema schema : database.getAllSchemas()) {
                if (this.excludeSchema(schema)) continue;
                arrayList.add(schema);
                this.add(schema.getCreateSQL(), false);
            }
            this.dumpDomains(arrayList);
            for (Schema schema : arrayList) {
                for (Constant constant : (Constant[])ScriptCommand.sorted(schema.getAllConstants(), Constant.class)) {
                    this.add(constant.getCreateSQL(), false);
                }
            }
            ArrayList<Table> arrayList2 = database.getAllTablesAndViews();
            arrayList2.sort(Comparator.comparingInt(DbObject::getId));
            Iterator<Object> iterator2 = arrayList2.iterator();
            while (iterator2.hasNext()) {
                object32 = (Table)iterator2.next();
                if (this.excludeSchema(((SchemaObject)object32).getSchema()) || this.excludeTable((Table)object32) || ((Table)object32).isHidden()) continue;
                ((Table)object32).lock(this.session, 0);
                String iterator3 = ((DbObject)object32).getCreateSQL();
                if (iterator3 == null || !this.drop) continue;
                this.add(((DbObject)object32).getDropSQL(), false);
            }
            for (Object object32 : arrayList) {
                for (UserDefinedFunction userDefinedFunction : (UserDefinedFunction[])ScriptCommand.sorted(((Schema)object32).getAllFunctionsAndAggregates(), UserDefinedFunction.class)) {
                    if (this.drop) {
                        this.add(userDefinedFunction.getDropSQL(), false);
                    }
                    this.add(userDefinedFunction.getCreateSQL(), false);
                }
            }
            for (Object object32 : arrayList) {
                for (Sequence sequence2 : (Sequence[])ScriptCommand.sorted(((Schema)object32).getAllSequences(), Sequence.class)) {
                    if (sequence2.getBelongsToTable()) continue;
                    if (this.drop) {
                        this.add(sequence2.getDropSQL(), false);
                    }
                    this.add(sequence2.getCreateSQL(), false);
                }
            }
            int n = 0;
            object32 = arrayList2.iterator();
            while (object32.hasNext()) {
                Table table = (Table)object32.next();
                if (this.excludeSchema(table.getSchema()) || this.excludeTable(table) || table.isHidden()) continue;
                table.lock(this.session, 0);
                String comment = table.getCreateSQL();
                if (comment == null) continue;
                TableType tableType = table.getTableType();
                this.add(comment, false);
                ArrayList<Constraint> arrayList3 = table.getConstraints();
                if (arrayList3 != null) {
                    for (Constraint constraint : arrayList3) {
                        if (Constraint.Type.PRIMARY_KEY != constraint.getConstraintType()) continue;
                        this.add(constraint.getCreateSQLWithoutIndexes(), false);
                    }
                }
                if (TableType.TABLE == tableType) {
                    if (table.canGetRowCount(this.session)) {
                        object2 = new StringBuilder("-- ").append(table.getRowCountApproximation(this.session)).append(" +/- SELECT COUNT(*) FROM ");
                        table.getSQL((StringBuilder)object2, 3);
                        this.add(((StringBuilder)object2).toString(), false);
                    }
                    if (this.data) {
                        n = this.generateInsertValues(n, table);
                    }
                }
                object2 = table.getIndexes();
                for (int i = 0; object2 != null && i < ((ArrayList)object2).size(); ++i) {
                    Index index = (Index)((ArrayList)object2).get(i);
                    if (index.getIndexType().getBelongsToConstraint()) continue;
                    this.add(index.getCreateSQL(), false);
                }
            }
            if (this.tempLobTableCreated) {
                this.add("DROP TABLE IF EXISTS SYSTEM_LOB_STREAM", true);
                this.add("DROP ALIAS IF EXISTS SYSTEM_COMBINE_CLOB", true);
                this.add("DROP ALIAS IF EXISTS SYSTEM_COMBINE_BLOB", true);
                this.tempLobTableCreated = false;
            }
            object32 = new ArrayList();
            for (Schema schema : arrayList) {
                for (Constraint constraint : schema.getAllConstraints()) {
                    if (this.excludeTable(constraint.getTable()) || (object2 = constraint.getConstraintType()) != Constraint.Type.DOMAIN && constraint.getTable().isHidden() || object2 == Constraint.Type.PRIMARY_KEY) continue;
                    ((ArrayList)object32).add(constraint);
                }
            }
            ((ArrayList)object32).sort(null);
            Iterator<DbObject> iterator3 = ((ArrayList)object32).iterator();
            while (iterator3.hasNext()) {
                Constraint constraint = (Constraint)((Object)iterator3.next());
                this.add(constraint.getCreateSQLWithoutIndexes(), false);
            }
            for (Schema schema : arrayList) {
                for (TriggerObject triggerObject : schema.getAllTriggers()) {
                    if (this.excludeTable(triggerObject.getTable())) continue;
                    this.add(triggerObject.getCreateSQL(), false);
                }
            }
            this.dumpRights(database);
            for (Comment comment : database.getAllComments()) {
                this.add(comment.getCreateSQL(), false);
            }
            if (this.out != null) {
                this.out.close();
            }
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, this.getFileName());
        }
        finally {
            this.closeIO();
        }
        this.result.done();
        object = this.result;
        this.reset();
        return object;
    }

    private void dumpDomains(ArrayList<Schema> arrayList) throws IOException {
        TreeMap<DbObject, TreeSet<? super DbObject>> treeMap = new TreeMap<DbObject, TreeSet<? super DbObject>>(BY_NAME_COMPARATOR);
        Object object = new TreeSet<DbObject>(BY_NAME_COMPARATOR);
        for (Schema object2 : arrayList) {
            for (Domain domain3 : (Domain[])ScriptCommand.sorted(object2.getAllDomains(), Domain.class)) {
                Domain domain2 = domain3.getDomain();
                if (domain2 == null) {
                    this.addDomain(domain3);
                    continue;
                }
                TreeSet<? super DbObject> treeSet = (TreeSet<? super DbObject>)treeMap.get(domain2);
                if (treeSet == null) {
                    treeSet = new TreeSet<DbObject>(BY_NAME_COMPARATOR);
                    treeMap.put(domain2, treeSet);
                }
                treeSet.add(domain3);
                if (domain2.getDomain() != null && arrayList.contains(domain2.getSchema())) continue;
                ((TreeSet)object).add(domain2);
            }
        }
        while (!treeMap.isEmpty()) {
            TreeSet<? super DbObject> treeSet = new TreeSet<DbObject>(BY_NAME_COMPARATOR);
            Iterator<? super DbObject> iterator2 = ((TreeSet)object).iterator();
            while (iterator2.hasNext()) {
                Domain domain = (Domain)iterator2.next();
                TreeSet treeSet2 = (TreeSet)treeMap.remove(domain);
                if (treeSet2 == null) continue;
                for (Domain domain3 : treeSet2) {
                    this.addDomain(domain3);
                    treeSet.add(domain3);
                }
            }
            object = treeSet;
        }
    }

    private void dumpRights(Database database) throws IOException {
        Right[] rightArray = database.getAllRights().toArray(new Right[0]);
        Arrays.sort(rightArray, (right, right2) -> {
            Role role;
            Role role2 = right.getGrantedRole();
            if (role2 == null != ((role = right2.getGrantedRole()) == null)) {
                return role2 == null ? -1 : 1;
            }
            if (role2 == null) {
                DbObject dbObject;
                DbObject dbObject2 = right.getGrantedObject();
                if (dbObject2 == null != ((dbObject = right2.getGrantedObject()) == null)) {
                    return dbObject2 == null ? -1 : 1;
                }
                if (dbObject2 != null) {
                    if (dbObject2 instanceof Schema != dbObject instanceof Schema) {
                        return dbObject2 instanceof Schema ? -1 : 1;
                    }
                    int n = dbObject2.getName().compareTo(dbObject.getName());
                    if (n != 0) {
                        return n;
                    }
                }
            } else {
                int n = role2.getName().compareTo(role.getName());
                if (n != 0) {
                    return n;
                }
            }
            return right.getGrantee().getName().compareTo(right2.getGrantee().getName());
        });
        for (Right right3 : rightArray) {
            Table table;
            DbObject dbObject = right3.getGrantedObject();
            if (dbObject != null && (dbObject instanceof Schema ? this.excludeSchema((Schema)dbObject) : dbObject instanceof Table && (this.excludeSchema((table = (Table)dbObject).getSchema()) || this.excludeTable(table)))) continue;
            this.add(right3.getCreateSQL(), false);
        }
    }

    private void addDomain(Domain domain) throws IOException {
        if (this.drop) {
            this.add(domain.getDropSQL(), false);
        }
        this.add(domain.getCreateSQL(), false);
    }

    private static <T extends DbObject> T[] sorted(Collection<T> collection, Class<T> clazz) {
        DbObject[] dbObjectArray = collection.toArray((DbObject[])Array.newInstance(clazz, 0));
        Arrays.sort(dbObjectArray, BY_NAME_COMPARATOR);
        return dbObjectArray;
    }

    private int generateInsertValues(int n, Table table) throws IOException {
        PlanItem planItem = table.getBestPlanItem(this.session, null, null, -1, null, null);
        Index index = planItem.getIndex();
        Cursor cursor = index.find(this.session, null, null);
        Column[] columnArray = table.getColumns();
        boolean bl = false;
        boolean bl2 = false;
        for (Column column : columnArray) {
            if (!column.isGeneratedAlways()) continue;
            if (column.isIdentity()) {
                bl2 = true;
                continue;
            }
            bl = true;
        }
        Object object = new StringBuilder("INSERT INTO ");
        table.getSQL((StringBuilder)object, 0);
        if (bl || bl2 || this.withColumns) {
            ((StringBuilder)object).append('(');
            int n2 = 0;
            for (Column column : columnArray) {
                if (column.isGenerated()) continue;
                if (n2 != 0) {
                    ((StringBuilder)object).append(", ");
                }
                n2 = 1;
                column.getSQL((StringBuilder)object, 0);
            }
            ((StringBuilder)object).append(')');
            if (bl2) {
                ((StringBuilder)object).append(" OVERRIDING SYSTEM VALUE");
            }
        }
        ((StringBuilder)object).append(" VALUES");
        if (!this.simple) {
            ((StringBuilder)object).append('\n');
        }
        ((StringBuilder)object).append('(');
        String string = ((StringBuilder)object).toString();
        object = null;
        int n3 = columnArray.length;
        while (cursor.next()) {
            Row row = cursor.get();
            if (object == null) {
                object = new StringBuilder(string);
            } else {
                ((StringBuilder)object).append(",\n(");
            }
            int n4 = 0;
            for (int i = 0; i < n3; ++i) {
                if (columnArray[i].isGenerated()) continue;
                if (n4 != 0) {
                    ((StringBuilder)object).append(", ");
                }
                n4 = 1;
                Value value = row.getValue(i);
                if (value.getType().getPrecision() > (long)this.lobBlockSize) {
                    int n5;
                    if (value.getValueType() == 3) {
                        n5 = this.writeLobStream(value);
                        ((StringBuilder)object).append("SYSTEM_COMBINE_CLOB(").append(n5).append(')');
                        continue;
                    }
                    if (value.getValueType() == 7) {
                        n5 = this.writeLobStream(value);
                        ((StringBuilder)object).append("SYSTEM_COMBINE_BLOB(").append(n5).append(')');
                        continue;
                    }
                    value.getSQL((StringBuilder)object, 4);
                    continue;
                }
                value.getSQL((StringBuilder)object, 4);
            }
            ((StringBuilder)object).append(')');
            if ((++n & 0x7F) == 0) {
                this.checkCanceled();
            }
            if (!this.simple && ((StringBuilder)object).length() <= 4096) continue;
            this.add(((StringBuilder)object).toString(), true);
            object = null;
        }
        if (object != null) {
            this.add(((StringBuilder)object).toString(), true);
        }
        return n;
    }

    /*
     * Unable to fully structure code
     */
    private int writeLobStream(Value var1_1) throws IOException {
        if (!this.tempLobTableCreated) {
            this.add("CREATE CACHED LOCAL TEMPORARY TABLE IF NOT EXISTS SYSTEM_LOB_STREAM(ID INT NOT NULL, PART INT NOT NULL, CDATA VARCHAR, BDATA VARBINARY)", true);
            this.add("ALTER TABLE SYSTEM_LOB_STREAM ADD CONSTRAINT SYSTEM_LOB_STREAM_PRIMARY_KEY PRIMARY KEY(ID, PART)", true);
            var2_2 = this.getClass().getName();
            this.add("CREATE ALIAS IF NOT EXISTS SYSTEM_COMBINE_CLOB FOR '" + var2_2 + ".combineClob'", true);
            this.add("CREATE ALIAS IF NOT EXISTS SYSTEM_COMBINE_BLOB FOR '" + var2_2 + ".combineBlob'", true);
            this.tempLobTableCreated = true;
        }
        var2_3 = this.nextLobId++;
        switch (var1_1.getValueType()) {
            case 7: {
                var3_4 = new byte[this.lobBlockSize];
                var4_6 = var1_1.getInputStream();
                var5_8 = null;
                var6_10 = 0;
                while (true) {
                    var7_16 = new StringBuilder(this.lobBlockSize * 2);
                    var7_16.append("INSERT INTO SYSTEM_LOB_STREAM VALUES(").append(var2_3).append(", ").append(var6_10).append(", NULL, X'");
                    var8_18 = IOUtils.readFully(var4_6, var3_4, this.lobBlockSize);
                    if (var8_18 <= 0) break;
                    StringUtils.convertBytesToHex(var7_16, var3_4, var8_18).append("')");
                    var9_20 = var7_16.toString();
                    this.add(var9_20, true);
                    ++var6_10;
                }
                if (var4_6 == null) break;
                if (var5_8 == null) ** GOTO lbl36
                try {
                    var4_6.close();
                }
                catch (Throwable var6_11) {
                    var5_8.addSuppressed(var6_11);
                }
                break;
lbl36:
                // 1 sources

                var4_6.close();
                break;
                catch (Throwable var6_12) {
                    try {
                        var5_8 = var6_12;
                        throw var6_12;
                    }
                    catch (Throwable var10_22) {
                        if (var4_6 != null) {
                            if (var5_8 != null) {
                                try {
                                    var4_6.close();
                                }
                                catch (Throwable var11_23) {
                                    var5_8.addSuppressed(var11_23);
                                }
                            } else {
                                var4_6.close();
                            }
                        }
                        throw var10_22;
                    }
                }
            }
            case 3: {
                var3_5 = new char[this.lobBlockSize];
                var4_7 = var1_1.getReader();
                var5_9 = null;
                var6_13 = 0;
                while (true) {
                    var7_17 = new StringBuilder(this.lobBlockSize * 2);
                    var7_17.append("INSERT INTO SYSTEM_LOB_STREAM VALUES(").append(var2_3).append(", ").append(var6_13).append(", ");
                    var8_19 = IOUtils.readFully(var4_7, var3_5, this.lobBlockSize);
                    if (var8_19 == 0) break;
                    StringUtils.quoteStringSQL(var7_17, new String(var3_5, 0, var8_19)).append(", NULL)");
                    var9_21 = var7_17.toString();
                    this.add(var9_21, true);
                    ++var6_13;
                }
                if (var4_7 == null) break;
                if (var5_9 == null) ** GOTO lbl79
                try {
                    var4_7.close();
                }
                catch (Throwable var6_14) {
                    var5_9.addSuppressed(var6_14);
                }
                break;
lbl79:
                // 1 sources

                var4_7.close();
                break;
                catch (Throwable var6_15) {
                    try {
                        var5_9 = var6_15;
                        throw var6_15;
                    }
                    catch (Throwable var12_24) {
                        if (var4_7 != null) {
                            if (var5_9 != null) {
                                try {
                                    var4_7.close();
                                }
                                catch (Throwable var13_25) {
                                    var5_9.addSuppressed(var13_25);
                                }
                            } else {
                                var4_7.close();
                            }
                        }
                        throw var12_24;
                    }
                }
            }
            default: {
                throw DbException.getInternalError("type:" + var1_1.getValueType());
            }
        }
        return var2_3;
    }

    public static InputStream combineBlob(Connection connection, int n) throws SQLException {
        if (n < 0) {
            return null;
        }
        final ResultSet resultSet = ScriptCommand.getLobStream(connection, "BDATA", n);
        return new InputStream(){
            private InputStream current;
            private boolean closed;

            @Override
            public int read() throws IOException {
                try {
                    while (true) {
                        int n;
                        if (this.current == null) {
                            if (this.closed) {
                                return -1;
                            }
                            if (!resultSet.next()) {
                                this.close();
                                return -1;
                            }
                            this.current = resultSet.getBinaryStream(1);
                            this.current = new BufferedInputStream(this.current);
                        }
                        if ((n = this.current.read()) >= 0) {
                            return n;
                        }
                        this.current = null;
                    }
                }
                catch (SQLException sQLException) {
                    throw DataUtils.convertToIOException(sQLException);
                }
            }

            @Override
            public void close() throws IOException {
                if (this.closed) {
                    return;
                }
                this.closed = true;
                try {
                    resultSet.close();
                }
                catch (SQLException sQLException) {
                    throw DataUtils.convertToIOException(sQLException);
                }
            }
        };
    }

    public static Reader combineClob(Connection connection, int n) throws SQLException {
        if (n < 0) {
            return null;
        }
        final ResultSet resultSet = ScriptCommand.getLobStream(connection, "CDATA", n);
        return new Reader(){
            private Reader current;
            private boolean closed;

            @Override
            public int read() throws IOException {
                try {
                    while (true) {
                        int n;
                        if (this.current == null) {
                            if (this.closed) {
                                return -1;
                            }
                            if (!resultSet.next()) {
                                this.close();
                                return -1;
                            }
                            this.current = resultSet.getCharacterStream(1);
                            this.current = new BufferedReader(this.current);
                        }
                        if ((n = this.current.read()) >= 0) {
                            return n;
                        }
                        this.current = null;
                    }
                }
                catch (SQLException sQLException) {
                    throw DataUtils.convertToIOException(sQLException);
                }
            }

            @Override
            public void close() throws IOException {
                if (this.closed) {
                    return;
                }
                this.closed = true;
                try {
                    resultSet.close();
                }
                catch (SQLException sQLException) {
                    throw DataUtils.convertToIOException(sQLException);
                }
            }

            @Override
            public int read(char[] cArray, int n, int n2) throws IOException {
                int n3;
                if (n2 == 0) {
                    return 0;
                }
                int n4 = this.read();
                if (n4 == -1) {
                    return -1;
                }
                cArray[n] = (char)n4;
                for (n3 = 1; n3 < n2 && (n4 = this.read()) != -1; ++n3) {
                    cArray[n + n3] = (char)n4;
                }
                return n3;
            }
        };
    }

    private static ResultSet getLobStream(Connection connection, String string, int n) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + string + " FROM SYSTEM_LOB_STREAM WHERE ID=? ORDER BY PART");
        preparedStatement.setInt(1, n);
        return preparedStatement.executeQuery();
    }

    private void reset() {
        this.result = null;
        this.buffer = null;
        this.lineSeparatorString = System.lineSeparator();
        this.lineSeparator = this.lineSeparatorString.getBytes(this.charset);
    }

    private boolean excludeSchema(Schema schema) {
        if (this.schemaNames != null && !this.schemaNames.contains(schema.getName())) {
            return true;
        }
        if (this.tables != null) {
            for (Table table : schema.getAllTablesAndViews(this.session)) {
                if (!this.tables.contains(table)) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean excludeTable(Table table) {
        return this.tables != null && !this.tables.contains(table);
    }

    private void add(String string, boolean bl) throws IOException {
        if (string == null) {
            return;
        }
        if (this.lineSeparator.length > 1 || this.lineSeparator[0] != 10) {
            string = StringUtils.replaceAll(string, "\n", this.lineSeparatorString);
        }
        string = string + ";";
        if (this.out != null) {
            int n;
            byte[] byArray = string.getBytes(this.charset);
            int n2 = MathUtils.roundUpInt(byArray.length + this.lineSeparator.length, 16);
            this.buffer = Utils.copy(byArray, this.buffer);
            if (n2 > this.buffer.length) {
                this.buffer = new byte[n2];
            }
            System.arraycopy(byArray, 0, this.buffer, 0, byArray.length);
            for (n = byArray.length; n < n2 - this.lineSeparator.length; ++n) {
                this.buffer[n] = 32;
            }
            n = 0;
            int n3 = n2 - this.lineSeparator.length;
            while (n3 < n2) {
                this.buffer[n3] = this.lineSeparator[n];
                ++n3;
                ++n;
            }
            this.out.write(this.buffer, 0, n2);
            if (!bl) {
                this.result.addRow(ValueVarchar.get(string));
            }
        } else {
            this.result.addRow(ValueVarchar.get(string));
        }
    }

    public void setSimple(boolean bl) {
        this.simple = bl;
    }

    public void setWithColumns(boolean bl) {
        this.withColumns = bl;
    }

    public void setVersion(boolean bl) {
        this.version = bl;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    @Override
    public int getType() {
        return 65;
    }
}

