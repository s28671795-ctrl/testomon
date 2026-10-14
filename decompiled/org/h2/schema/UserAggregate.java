/*
 * Decompiled with CFR 0.152.
 */
package org.h2.schema;

import java.sql.Connection;
import java.sql.SQLException;
import org.h2.api.Aggregate;
import org.h2.api.AggregateFunction;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.schema.UserDefinedFunction;
import org.h2.util.JdbcUtils;
import org.h2.util.StringUtils;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;

public final class UserAggregate
extends UserDefinedFunction {
    private Class<?> javaClass;

    public UserAggregate(Schema schema, int n, String string, String string2, boolean bl) {
        super(schema, n, string, 3);
        this.className = string2;
        if (!bl) {
            this.getInstance();
        }
    }

    public Aggregate getInstance() {
        if (this.javaClass == null) {
            this.javaClass = JdbcUtils.loadUserClass(this.className);
        }
        try {
            Object obj = this.javaClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            Aggregate aggregate = obj instanceof Aggregate ? (Aggregate)obj : new AggregateWrapper((AggregateFunction)obj);
            return aggregate;
        }
        catch (Exception exception) {
            throw DbException.convert(exception);
        }
    }

    @Override
    public String getDropSQL() {
        StringBuilder stringBuilder = new StringBuilder("DROP AGGREGATE IF EXISTS ");
        return this.getSQL(stringBuilder, 0).toString();
    }

    @Override
    public String getCreateSQL() {
        StringBuilder stringBuilder = new StringBuilder("CREATE FORCE AGGREGATE ");
        this.getSQL(stringBuilder, 0).append(" FOR ");
        return StringUtils.quoteStringSQL(stringBuilder, this.className).toString();
    }

    @Override
    public int getType() {
        return 14;
    }

    @Override
    public synchronized void removeChildrenAndResources(SessionLocal sessionLocal) {
        this.database.removeMeta(sessionLocal, this.getId());
        this.className = null;
        this.javaClass = null;
        this.invalidate();
    }

    private static class AggregateWrapper
    implements Aggregate {
        private final AggregateFunction aggregateFunction;

        AggregateWrapper(AggregateFunction aggregateFunction) {
            this.aggregateFunction = aggregateFunction;
        }

        @Override
        public void init(Connection connection) throws SQLException {
            this.aggregateFunction.init(connection);
        }

        @Override
        public int getInternalType(int[] nArray) throws SQLException {
            int[] nArray2 = new int[nArray.length];
            for (int i = 0; i < nArray.length; ++i) {
                nArray2[i] = DataType.convertTypeToSQLType(TypeInfo.getTypeInfo(nArray[i]));
            }
            return DataType.convertSQLTypeToValueType(this.aggregateFunction.getType(nArray2));
        }

        @Override
        public void add(Object object) throws SQLException {
            this.aggregateFunction.add(object);
        }

        @Override
        public Object getResult() throws SQLException {
            return this.aggregateFunction.getResult();
        }
    }
}

