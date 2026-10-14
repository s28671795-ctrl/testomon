/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.database.Database;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.ChangeGenerator;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.diff.output.changelog.ChangeGeneratorComparator;
import liquibase.diff.output.changelog.ChangedObjectChangeGenerator;
import liquibase.diff.output.changelog.MissingObjectChangeGenerator;
import liquibase.diff.output.changelog.UnexpectedObjectChangeGenerator;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.structure.DatabaseObject;

public class ChangeGeneratorFactory {
    private static ChangeGeneratorFactory instance;
    private final List<ChangeGenerator> generators = new ArrayList<ChangeGenerator>();

    private ChangeGeneratorFactory() {
        try {
            for (ChangeGenerator generator : Scope.getCurrentScope().getServiceLocator().findInstances(ChangeGenerator.class)) {
                this.register(generator);
            }
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public static synchronized ChangeGeneratorFactory getInstance() {
        if (instance == null) {
            instance = new ChangeGeneratorFactory();
        }
        return instance;
    }

    public static synchronized void reset() {
        instance = new ChangeGeneratorFactory();
    }

    public void register(ChangeGenerator generator) {
        this.generators.add(generator);
    }

    public void unregister(ChangeGenerator generator) {
        this.generators.remove(generator);
    }

    public void unregister(Class generatorClass) {
        ChangeGenerator toRemove = null;
        for (ChangeGenerator existingGenerator : this.generators) {
            if (!existingGenerator.getClass().equals(generatorClass)) continue;
            toRemove = existingGenerator;
        }
        this.unregister(toRemove);
    }

    protected SortedSet<ChangeGenerator> getGenerators(Class<? extends ChangeGenerator> generatorType, Class<? extends DatabaseObject> objectType, Database database) {
        TreeSet<ChangeGenerator> validGenerators = new TreeSet<ChangeGenerator>(new ChangeGeneratorComparator(objectType, database));
        for (ChangeGenerator generator : this.generators) {
            if (!generatorType.isAssignableFrom(generator.getClass()) || generator.getPriority(objectType, database) <= 0) continue;
            validGenerators.add(generator);
        }
        return validGenerators;
    }

    private ChangeGeneratorChain createGeneratorChain(Class<? extends ChangeGenerator> generatorType, Class<? extends DatabaseObject> objectType, Database database) {
        SortedSet<ChangeGenerator> generators = this.getGenerators(generatorType, objectType, database);
        if (generators == null || generators.isEmpty()) {
            return null;
        }
        return new ChangeGeneratorChain(generators);
    }

    public Change[] fixMissing(DatabaseObject missingObject, DiffOutputControl control, Database referenceDatabase, Database comparisionDatabase) {
        if (!control.shouldOutput(missingObject, comparisionDatabase)) {
            return null;
        }
        ChangeGeneratorChain chain = this.createGeneratorChain(MissingObjectChangeGenerator.class, missingObject.getClass(), referenceDatabase);
        if (chain == null) {
            return null;
        }
        return chain.fixMissing(missingObject, control, referenceDatabase, comparisionDatabase);
    }

    public Change[] fixUnexpected(DatabaseObject unexpectedObject, DiffOutputControl control, Database referenceDatabase, Database comparisionDatabase) {
        if (!control.shouldOutput(unexpectedObject, comparisionDatabase)) {
            return null;
        }
        ChangeGeneratorChain chain = this.createGeneratorChain(UnexpectedObjectChangeGenerator.class, unexpectedObject.getClass(), referenceDatabase);
        if (chain == null) {
            return null;
        }
        return chain.fixUnexpected(unexpectedObject, control, referenceDatabase, comparisionDatabase);
    }

    public Change[] fixChanged(DatabaseObject changedObject, ObjectDifferences differences, DiffOutputControl control, Database referenceDatabase, Database comparisionDatabase) {
        if (!control.shouldOutput(changedObject, comparisionDatabase)) {
            return null;
        }
        ChangeGeneratorChain chain = this.createGeneratorChain(ChangedObjectChangeGenerator.class, changedObject.getClass(), referenceDatabase);
        if (chain == null) {
            return null;
        }
        return chain.fixChanged(changedObject, differences, control, referenceDatabase, comparisionDatabase);
    }

    public Set<Class<? extends DatabaseObject>> runAfterTypes(Class<? extends DatabaseObject> objectType, Database database, Class<? extends ChangeGenerator> changeGeneratorType) {
        HashSet<Class<? extends DatabaseObject>> returnTypes = new HashSet<Class<? extends DatabaseObject>>();
        SortedSet<ChangeGenerator> generators = this.getGenerators(changeGeneratorType, objectType, database);
        for (ChangeGenerator generator : generators) {
            Class<? extends DatabaseObject>[] types = generator.runAfterTypes();
            if (types == null) continue;
            returnTypes.addAll(Arrays.asList(types));
        }
        return returnTypes;
    }

    public Set<Class<? extends DatabaseObject>> runBeforeTypes(Class<? extends DatabaseObject> objectType, Database database, Class<? extends ChangeGenerator> changeGeneratorType) {
        HashSet<Class<? extends DatabaseObject>> returnTypes = new HashSet<Class<? extends DatabaseObject>>();
        SortedSet<ChangeGenerator> generators = this.getGenerators(changeGeneratorType, objectType, database);
        for (ChangeGenerator generator : generators) {
            Class<? extends DatabaseObject>[] types = generator.runBeforeTypes();
            if (types == null) continue;
            returnTypes.addAll(Arrays.asList(types));
        }
        return returnTypes;
    }
}

