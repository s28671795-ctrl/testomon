/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.resolver;

import com.google.common.base.Predicate;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CriterionPredicateRegistry<T> {
    private Logger log = LoggerFactory.getLogger(CriterionPredicateRegistry.class);
    private Map<Class<? extends Criterion>, Class<? extends Predicate<T>>> registry = new HashMap<Class<? extends Criterion>, Class<? extends Predicate<T>>>();

    @Nullable
    public Predicate<T> getPredicate(@Nonnull Criterion criterion) throws ResolverException {
        Constraint.isNotNull(criterion, "Criterion to map cannot be null");
        Class<Predicate<T>> predicateClass = this.lookup(criterion.getClass());
        if (predicateClass != null) {
            this.log.debug("Registry located Predicate class {} for Criterion class {}", (Object)predicateClass.getName(), (Object)criterion.getClass().getName());
            try {
                Constructor<Predicate<T>> constructor = predicateClass.getConstructor(criterion.getClass());
                return constructor.newInstance(criterion);
            }
            catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                this.log.error("Error instantiating new Predicate instance", e);
                throw new ResolverException("Could not create new Predicate instance", e);
            }
        }
        this.log.debug("Registry did not locate Predicate implementation registered for Criterion class {}", (Object)criterion.getClass().getName());
        return null;
    }

    @Nullable
    protected Class<? extends Predicate<T>> lookup(@Nonnull Class<? extends Criterion> clazz) {
        Constraint.isNotNull(clazz, "Criterion class to lookup cannot be null");
        return this.registry.get(clazz);
    }

    public void register(@Nonnull Class<? extends Criterion> criterionClass, @Nonnull Class<? extends Predicate<T>> predicateClass) {
        Constraint.isNotNull(criterionClass, "Criterion class to register cannot be null");
        Constraint.isNotNull(predicateClass, "Predicate class to register cannot be null");
        this.log.debug("Registering class {} as Predicate for Criterion class {}", (Object)predicateClass.getName(), (Object)criterionClass.getName());
        this.registry.put(criterionClass, predicateClass);
    }

    public void deregister(@Nonnull Class<? extends Criterion> criterionClass) {
        Constraint.isNotNull(criterionClass, "Criterion class to unregister cannot be null");
        this.log.debug("Deregistering Predicate for Criterion class {}", (Object)criterionClass.getName());
        this.registry.remove(criterionClass);
    }

    public void clearRegistry() {
        this.log.debug("Clearing Criterion Predicate registry");
        this.registry.clear();
    }

    public void loadMappings(@Nonnull String classpathResource) {
        String resource = Constraint.isNotNull(StringSupport.trimOrNull(classpathResource), "Classpath resource was null or empty");
        try (InputStream inStream = this.getClass().getResourceAsStream(resource);){
            if (inStream == null) {
                this.log.error("Could not open resource stream from resource '{}'", (Object)resource);
                return;
            }
            Properties mappings = new Properties();
            mappings.load(inStream);
            this.loadMappings(mappings);
        }
        catch (IOException e) {
            this.log.error("Error load mappings from resource '{}'", (Object)resource, (Object)e);
            return;
        }
    }

    public void loadMappings(@Nonnull Properties mappings) {
        Constraint.isNotNull(mappings, "Mappings to load cannot be null");
        for (Object key : mappings.keySet()) {
            if (!(key instanceof String)) {
                this.log.error("Properties key was not an instance of String, was '{}', skipping...", (Object)key.getClass().getName());
                continue;
            }
            String criterionName = (String)key;
            String predicateName = mappings.getProperty(criterionName);
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class<?> criterionClass = null;
            try {
                criterionClass = classLoader.loadClass(criterionName);
            }
            catch (ClassNotFoundException e) {
                this.log.error("Could not find Criterion class '{}', skipping registration", (Object)criterionName);
                continue;
            }
            Class<?> predicateClass = null;
            try {
                predicateClass = classLoader.loadClass(predicateName);
            }
            catch (ClassNotFoundException e) {
                this.log.error("Could not find Predicate class '{}', skipping registration", (Object)criterionName);
                continue;
            }
            this.register(criterionClass, predicateClass);
        }
    }
}

