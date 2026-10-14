/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sql.visitor;

import java.util.HashMap;
import java.util.Map;
import liquibase.sql.visitor.AppendSqlVisitor;
import liquibase.sql.visitor.PrependSqlVisitor;
import liquibase.sql.visitor.RegExpReplaceSqlVisitor;
import liquibase.sql.visitor.ReplaceSqlVisitor;
import liquibase.sql.visitor.SqlVisitor;

public class SqlVisitorFactory {
    private final Map<String, Class> tagToClassMap = new HashMap<String, Class>();
    private static final SqlVisitorFactory instance = new SqlVisitorFactory();

    private SqlVisitorFactory() {
        Class[] visitors = new Class[]{PrependSqlVisitor.class, AppendSqlVisitor.class, RegExpReplaceSqlVisitor.class, ReplaceSqlVisitor.class};
        try {
            for (Class visitorClass : visitors) {
                SqlVisitor visitor = (SqlVisitor)visitorClass.getConstructor(new Class[0]).newInstance(new Object[0]);
                this.tagToClassMap.put(visitor.getName(), visitorClass);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static SqlVisitorFactory getInstance() {
        return instance;
    }

    public SqlVisitor create(String tagName) {
        Class aClass = this.tagToClassMap.get(tagName);
        if (aClass == null) {
            return null;
        }
        try {
            return (SqlVisitor)aClass.getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

