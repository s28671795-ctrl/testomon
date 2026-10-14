/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition;

import java.util.HashMap;
import java.util.Map;
import liquibase.Scope;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.precondition.Precondition;

public class PreconditionFactory {
    private final Map<String, Class<? extends Precondition>> preconditions = new HashMap<String, Class<? extends Precondition>>();
    private static PreconditionFactory instance;

    private PreconditionFactory() {
        try {
            for (Precondition precondition : Scope.getCurrentScope().getServiceLocator().findInstances(Precondition.class)) {
                this.register(precondition);
            }
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public static synchronized PreconditionFactory getInstance() {
        if (instance == null) {
            instance = new PreconditionFactory();
        }
        return instance;
    }

    public static synchronized void reset() {
        instance = new PreconditionFactory();
    }

    public Map<String, Class<? extends Precondition>> getPreconditions() {
        return this.preconditions;
    }

    public void register(Precondition precondition) {
        try {
            this.preconditions.put(precondition.getName(), precondition.getClass());
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public void unregister(String name) {
        this.preconditions.remove(name);
    }

    public Precondition create(String tagName) {
        Class<? extends Precondition> aClass = this.preconditions.get(tagName);
        if (aClass == null) {
            return null;
        }
        try {
            return aClass.getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

