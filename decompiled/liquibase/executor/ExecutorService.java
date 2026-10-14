/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import liquibase.database.Database;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.executor.Executor;
import liquibase.plugin.AbstractPluginFactory;

public class ExecutorService
extends AbstractPluginFactory<Executor> {
    private final Map<Key, Executor> executors = new ConcurrentHashMap<Key, Executor>();

    private ExecutorService() {
    }

    @Override
    protected Class<Executor> getPluginClass() {
        return Executor.class;
    }

    @Override
    protected int getPriority(Executor executor2, Object ... args2) {
        String name = (String)args2[0];
        Database database = (Database)args2[1];
        if (name.equals(executor2.getName()) && executor2.supports(database)) {
            return executor2.getPriority();
        }
        return -1;
    }

    private Key createKey(String executorName, Database database) {
        return new Key(executorName, database);
    }

    private Executor getExecutorValue(String executorName, Database database) throws UnexpectedLiquibaseException {
        Executor plugin = (Executor)this.getPlugin(executorName.toLowerCase(), database);
        try {
            return (Executor)plugin.getClass().newInstance();
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public Executor getExecutor(String name, Database database) {
        return this.executors.computeIfAbsent(this.createKey(name, database), db -> {
            try {
                Executor executor2 = this.getExecutorValue(name, database);
                executor2.setDatabase(database);
                return executor2;
            }
            catch (Exception e) {
                throw new UnexpectedLiquibaseException(e);
            }
        });
    }

    public boolean executorExists(String name, Database database) {
        return this.executors.containsKey(this.createKey(name, database));
    }

    public Executor getExecutor(Database database) {
        return this.getExecutor("jdbc", database);
    }

    public void setExecutor(Database database, Executor executor2) {
        this.setExecutor("jdbc", database, executor2);
    }

    public void setExecutor(String name, Database database, Executor executor2) {
        this.executors.put(this.createKey(name, database), executor2);
    }

    public void clearExecutor(Database database) {
        this.clearExecutor("jdbc", database);
    }

    public void clearExecutor(String name, Database database) {
        this.executors.remove(this.createKey(name, database));
    }

    public void reset() {
        this.executors.clear();
    }

    private static class Key {
        private final String executorName;
        private final Database database;

        Key(String executorName, Database database) {
            this.executorName = this.normalizeExecutorName(executorName);
            this.database = database;
        }

        private String normalizeExecutorName(String executorName) {
            return executorName.toLowerCase();
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            Key key = (Key)o;
            return Objects.equals(this.executorName, key.executorName) && this.database == key.database;
        }

        public int hashCode() {
            return Objects.hash(this.executorName, System.identityHashCode(this.database));
        }

        public String toString() {
            return "Key{executorName='" + this.executorName + '\'' + ", database=" + this.database + '}';
        }
    }
}

