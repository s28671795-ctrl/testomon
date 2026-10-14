/*
 * Decompiled with CFR 0.152.
 */
package org.h2.schema;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import org.h2.Driver;
import org.h2.engine.CastDataProvider;
import org.h2.engine.Mode;
import org.h2.engine.SessionLocal;
import org.h2.expression.Alias;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.jdbc.JdbcConnection;
import org.h2.message.DbException;
import org.h2.result.LocalResult;
import org.h2.result.ResultInterface;
import org.h2.schema.Schema;
import org.h2.schema.UserDefinedFunction;
import org.h2.table.Column;
import org.h2.util.JdbcUtils;
import org.h2.util.SourceCompiler;
import org.h2.util.StringUtils;
import org.h2.util.Utils;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueNull;
import org.h2.value.ValueToObjectConverter;
import org.h2.value.ValueToObjectConverter2;

public final class FunctionAlias
extends UserDefinedFunction {
    private String methodName;
    private String source;
    private JavaMethod[] javaMethods;
    private boolean deterministic;

    private FunctionAlias(Schema schema, int n, String string) {
        super(schema, n, string, 3);
    }

    public static FunctionAlias newInstance(Schema schema, int n, String string, String string2, boolean bl) {
        FunctionAlias functionAlias = new FunctionAlias(schema, n, string);
        int n2 = string2.indexOf(40);
        int n3 = string2.lastIndexOf(46, n2 < 0 ? string2.length() : n2);
        if (n3 < 0) {
            throw DbException.get(42000, string2);
        }
        functionAlias.className = string2.substring(0, n3);
        functionAlias.methodName = string2.substring(n3 + 1);
        functionAlias.init(bl);
        return functionAlias;
    }

    public static FunctionAlias newInstanceFromSource(Schema schema, int n, String string, String string2, boolean bl) {
        FunctionAlias functionAlias = new FunctionAlias(schema, n, string);
        functionAlias.source = string2;
        functionAlias.init(bl);
        return functionAlias;
    }

    private void init(boolean bl) {
        block2: {
            try {
                this.load();
            }
            catch (DbException dbException) {
                if (bl) break block2;
                throw dbException;
            }
        }
    }

    private synchronized void load() {
        if (this.javaMethods != null) {
            return;
        }
        if (this.source != null) {
            this.loadFromSource();
        } else {
            this.loadClass();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadFromSource() {
        SourceCompiler sourceCompiler;
        SourceCompiler sourceCompiler2 = sourceCompiler = this.database.getCompiler();
        synchronized (sourceCompiler2) {
            String string = "org.h2.dynamic." + this.getName();
            sourceCompiler.setSource(string, this.source);
            try {
                Method method2 = sourceCompiler.getMethod(string);
                JavaMethod javaMethod = new JavaMethod(method2, 0);
                this.javaMethods = new JavaMethod[]{javaMethod};
            }
            catch (DbException dbException) {
                throw dbException;
            }
            catch (Exception exception) {
                throw DbException.get(42000, exception, this.source);
            }
        }
    }

    private void loadClass() {
        Class clazz = JdbcUtils.loadUserClass(this.className);
        Method[] methodArray = clazz.getMethods();
        ArrayList<JavaMethod> arrayList = new ArrayList<JavaMethod>(1);
        int n = methodArray.length;
        for (int i = 0; i < n; ++i) {
            Method method2 = methodArray[i];
            if (!Modifier.isStatic(method2.getModifiers()) || !method2.getName().equals(this.methodName) && !FunctionAlias.getMethodSignature(method2).equals(this.methodName)) continue;
            JavaMethod javaMethod = new JavaMethod(method2, i);
            for (JavaMethod javaMethod2 : arrayList) {
                if (javaMethod2.getParameterCount() != javaMethod.getParameterCount()) continue;
                throw DbException.get(90073, javaMethod2.toString(), javaMethod.toString());
            }
            arrayList.add(javaMethod);
        }
        if (arrayList.isEmpty()) {
            throw DbException.get(90139, this.methodName + " (" + this.className + ")");
        }
        this.javaMethods = arrayList.toArray(new JavaMethod[0]);
        Arrays.sort(this.javaMethods);
    }

    private static String getMethodSignature(Method method2) {
        StringBuilder stringBuilder = new StringBuilder(method2.getName());
        stringBuilder.append('(');
        Class<?>[] classArray = method2.getParameterTypes();
        int n = classArray.length;
        for (int i = 0; i < n; ++i) {
            Class<?> clazz;
            if (i > 0) {
                stringBuilder.append(',');
            }
            if ((clazz = classArray[i]).isArray()) {
                stringBuilder.append(clazz.getComponentType().getName()).append("[]");
                continue;
            }
            stringBuilder.append(clazz.getName());
        }
        return stringBuilder.append(')').toString();
    }

    @Override
    public String getDropSQL() {
        return this.getSQL(new StringBuilder("DROP ALIAS IF EXISTS "), 0).toString();
    }

    @Override
    public String getCreateSQL() {
        StringBuilder stringBuilder = new StringBuilder("CREATE FORCE ALIAS ");
        this.getSQL(stringBuilder, 0);
        if (this.deterministic) {
            stringBuilder.append(" DETERMINISTIC");
        }
        if (this.source != null) {
            StringUtils.quoteStringSQL(stringBuilder.append(" AS "), this.source);
        } else {
            StringUtils.quoteStringSQL(stringBuilder.append(" FOR "), this.className + '.' + this.methodName);
        }
        return stringBuilder.toString();
    }

    @Override
    public int getType() {
        return 9;
    }

    @Override
    public synchronized void removeChildrenAndResources(SessionLocal sessionLocal) {
        this.database.removeMeta(sessionLocal, this.getId());
        this.className = null;
        this.methodName = null;
        this.javaMethods = null;
        this.invalidate();
    }

    public JavaMethod findJavaMethod(Expression[] expressionArray) {
        this.load();
        int n = expressionArray.length;
        for (JavaMethod javaMethod : this.javaMethods) {
            int n2 = javaMethod.getParameterCount();
            if (n2 != n && (!javaMethod.isVarArgs() || n2 > n + 1)) continue;
            return javaMethod;
        }
        throw DbException.get(90087, this.getName() + " (" + this.className + ", parameter count: " + n + ")");
    }

    public String getJavaMethodName() {
        return this.methodName;
    }

    public JavaMethod[] getJavaMethods() {
        this.load();
        return this.javaMethods;
    }

    public void setDeterministic(boolean bl) {
        this.deterministic = bl;
    }

    public boolean isDeterministic() {
        return this.deterministic;
    }

    public String getSource() {
        return this.source;
    }

    public static class JavaMethod
    implements Comparable<JavaMethod> {
        private final int id;
        private final Method method;
        private final TypeInfo dataType;
        private boolean hasConnectionParam;
        private boolean varArgs;
        private Class<?> varArgClass;
        private int paramCount;

        JavaMethod(Method method2, int n) {
            Class<?> clazz;
            this.method = method2;
            this.id = n;
            Class<?>[] classArray = method2.getParameterTypes();
            this.paramCount = classArray.length;
            if (this.paramCount > 0 && Connection.class.isAssignableFrom(clazz = classArray[0])) {
                this.hasConnectionParam = true;
                --this.paramCount;
            }
            if (this.paramCount > 0 && (clazz = classArray[classArray.length - 1]).isArray() && method2.isVarArgs()) {
                this.varArgs = true;
                this.varArgClass = clazz.getComponentType();
            }
            this.dataType = ResultSet.class.isAssignableFrom(clazz = method2.getReturnType()) ? null : ValueToObjectConverter2.classToType(clazz);
        }

        public String toString() {
            return this.method.toString();
        }

        public boolean hasConnectionParam() {
            return this.hasConnectionParam;
        }

        public Value getValue(SessionLocal sessionLocal, Expression[] expressionArray, boolean bl) {
            Object object = this.execute(sessionLocal, expressionArray, bl);
            if (Value.class.isAssignableFrom(this.method.getReturnType())) {
                return (Value)object;
            }
            return ValueToObjectConverter.objectToValue(sessionLocal, object, this.dataType.getValueType()).convertTo(this.dataType, (CastDataProvider)sessionLocal);
        }

        public ResultInterface getTableValue(SessionLocal sessionLocal, Expression[] expressionArray, boolean bl) {
            Object object = this.execute(sessionLocal, expressionArray, bl);
            if (object == null) {
                throw DbException.get(90000, this.method.getName());
            }
            if (ResultInterface.class.isAssignableFrom(this.method.getReturnType())) {
                return (ResultInterface)object;
            }
            return JavaMethod.resultSetToResult(sessionLocal, (ResultSet)object, bl ? 0 : Integer.MAX_VALUE);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static ResultInterface resultSetToResult(SessionLocal sessionLocal, ResultSet resultSet, int n) {
            try (ResultSet resultSet2 = resultSet;){
                Value[] valueArray;
                ResultSetMetaData resultSetMetaData = resultSet2.getMetaData();
                int n2 = resultSetMetaData.getColumnCount();
                Expression[] expressionArray = new Expression[n2];
                for (int i = 0; i < n2; ++i) {
                    String string = resultSetMetaData.getColumnLabel(i + 1);
                    valueArray = resultSetMetaData.getColumnName(i + 1);
                    String string2 = resultSetMetaData.getColumnTypeName(i + 1);
                    int n3 = DataType.convertSQLTypeToValueType(resultSetMetaData.getColumnType(i + 1), string2);
                    int n4 = resultSetMetaData.getPrecision(i + 1);
                    int n5 = resultSetMetaData.getScale(i + 1);
                    TypeInfo typeInfo = n3 == 40 && string2.endsWith(" ARRAY") ? TypeInfo.getTypeInfo(40, -1L, 0, TypeInfo.getTypeInfo(DataType.getTypeByName((String)string2.substring((int)0, (int)(string2.length() - 6)), (Mode)sessionLocal.getMode()).type)) : TypeInfo.getTypeInfo(n3, n4, n5, null);
                    Expression expression = new ExpressionColumn(sessionLocal.getDatabase(), new Column((String)valueArray, typeInfo));
                    if (!string.equals(valueArray)) {
                        expression = new Alias(expression, string, false);
                    }
                    expressionArray[i] = expression;
                }
                LocalResult localResult = new LocalResult(sessionLocal, expressionArray, n2, n2);
                for (int i = 0; i < n && resultSet2.next(); ++i) {
                    valueArray = new Value[n2];
                    for (int j = 0; j < n2; ++j) {
                        valueArray[j] = ValueToObjectConverter.objectToValue(sessionLocal, resultSet2.getObject(j + 1), expressionArray[j].getType().getValueType());
                    }
                    localResult.addRow(valueArray);
                }
                localResult.done();
                LocalResult localResult2 = localResult;
                return localResult2;
            }
            catch (SQLException sQLException) {
                throw DbException.convert(sQLException);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * WARNING - void declaration
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private Object execute(SessionLocal sessionLocal, Expression[] expressionArray, boolean bl) {
            int n;
            Object object;
            Object object2;
            Class<?> clazz;
            boolean bl2;
            void var9_11;
            Class<?>[] classArray = this.method.getParameterTypes();
            Object[] objectArray = new Object[classArray.length];
            int n2 = 0;
            JdbcConnection jdbcConnection = sessionLocal.createConnection(bl);
            if (this.hasConnectionParam && objectArray.length > 0) {
                objectArray[n2++] = jdbcConnection;
            }
            Object object3 = null;
            if (this.varArgs) {
                int n3 = expressionArray.length - objectArray.length + 1 + (this.hasConnectionParam ? 1 : 0);
                objectArray[objectArray.length - 1] = object3 = Array.newInstance(this.varArgClass, n3);
            }
            boolean bl3 = false;
            int n4 = expressionArray.length;
            while (var9_11 < n4) {
                bl2 = this.varArgs && n2 >= classArray.length - 1;
                clazz = bl2 ? this.varArgClass : classArray[n2];
                object2 = expressionArray[var9_11].getValue(sessionLocal);
                if (Value.class.isAssignableFrom(clazz)) {
                    object = object2;
                } else {
                    n = clazz.isPrimitive();
                    if (object2 == ValueNull.INSTANCE) {
                        if (n != 0) {
                            if (!bl) return null;
                            object = DataType.getDefaultForPrimitiveType(clazz);
                        } else {
                            object = null;
                        }
                    } else {
                        object = ValueToObjectConverter.valueToObject(n != 0 ? Utils.getNonPrimitiveClass(clazz) : clazz, (Value)object2, jdbcConnection);
                    }
                }
                if (bl2) {
                    Array.set(object3, n2 - objectArray.length + 1, object);
                } else {
                    objectArray[n2] = object;
                }
                ++var9_11;
                ++n2;
            }
            boolean bl4 = sessionLocal.getAutoCommit();
            Value value = sessionLocal.getLastIdentity();
            bl2 = sessionLocal.getDatabase().getSettings().defaultConnection;
            try {
                sessionLocal.setAutoCommit(false);
                try {
                    if (bl2) {
                        Driver.setDefaultConnection(sessionLocal.createConnection(bl));
                    }
                    if ((clazz = this.method.invoke(null, objectArray)) == null) {
                        object2 = null;
                        return object2;
                    }
                }
                catch (InvocationTargetException invocationTargetException) {
                    object = new StringBuilder(this.method.getName()).append('(');
                    int n5 = objectArray.length;
                    for (n = 0; n < n5; ++n) {
                        if (n > 0) {
                            ((StringBuilder)object).append(", ");
                        }
                        ((StringBuilder)object).append(objectArray[n]);
                    }
                    ((StringBuilder)object).append(')');
                    throw DbException.convertInvocation(invocationTargetException, ((StringBuilder)object).toString());
                }
                catch (Exception exception) {
                    throw DbException.convert(exception);
                }
                object2 = clazz;
                return object2;
            }
            finally {
                sessionLocal.setLastIdentity(value);
                sessionLocal.setAutoCommit(bl4);
                if (bl2) {
                    Driver.setDefaultConnection(null);
                }
            }
        }

        public Class<?>[] getColumnClasses() {
            return this.method.getParameterTypes();
        }

        public TypeInfo getDataType() {
            return this.dataType;
        }

        public int getParameterCount() {
            return this.paramCount;
        }

        public boolean isVarArgs() {
            return this.varArgs;
        }

        @Override
        public int compareTo(JavaMethod javaMethod) {
            if (this.varArgs != javaMethod.varArgs) {
                return this.varArgs ? 1 : -1;
            }
            if (this.paramCount != javaMethod.paramCount) {
                return this.paramCount - javaMethod.paramCount;
            }
            if (this.hasConnectionParam != javaMethod.hasConnectionParam) {
                return this.hasConnectionParam ? 1 : -1;
            }
            return this.id - javaMethod.id;
        }
    }
}

