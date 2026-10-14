/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.visitor;

import java.util.HashMap;
import java.util.Map;
import liquibase.change.visitor.AddColumnChangeVisitor;
import liquibase.change.visitor.ChangeVisitor;

public class ChangeVisitorFactory {
    private final Map<String, Class> tagToClassMap = new HashMap<String, Class>();
    private static final ChangeVisitorFactory instance = new ChangeVisitorFactory();

    private ChangeVisitorFactory() {
        Class[] visitors = new Class[]{AddColumnChangeVisitor.class};
        try {
            for (Class visitorClass : visitors) {
                ChangeVisitor visitor = (ChangeVisitor)visitorClass.getConstructor(new Class[0]).newInstance(new Object[0]);
                this.tagToClassMap.put(visitor.getName(), visitorClass);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ChangeVisitorFactory getInstance() {
        return instance;
    }

    public ChangeVisitor create(String tagName) {
        Class aClass = this.tagToClassMap.get(tagName);
        if (aClass == null) {
            return null;
        }
        try {
            return (ChangeVisitor)aClass.getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

