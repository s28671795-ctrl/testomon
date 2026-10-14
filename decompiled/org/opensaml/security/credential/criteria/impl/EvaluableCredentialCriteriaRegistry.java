/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential.criteria.impl;

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
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.criteria.impl.EvaluableCredentialCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EvaluableCredentialCriteriaRegistry {
    public static final String DEFAULT_MAPPINGS_FILE = "/credential-criteria-registry.properties";
    private static Map<Class<? extends Criterion>, Class<? extends EvaluableCredentialCriterion>> registry;
    private static boolean initialized;

    private EvaluableCredentialCriteriaRegistry() {
    }

    @Nullable
    public static EvaluableCredentialCriterion getEvaluator(@Nonnull Criterion criteria) throws SecurityException {
        Constraint.isNotNull(criteria, "Criteria to map cannot be null");
        Logger log = EvaluableCredentialCriteriaRegistry.getLogger();
        Class<? extends EvaluableCredentialCriterion> clazz = EvaluableCredentialCriteriaRegistry.lookup(criteria.getClass());
        if (clazz != null) {
            log.debug("Registry located evaluable criteria class {} for criteria class {}", (Object)clazz.getName(), (Object)criteria.getClass().getName());
            try {
                Constructor<? extends EvaluableCredentialCriterion> constructor = clazz.getConstructor(criteria.getClass());
                return constructor.newInstance(criteria);
            }
            catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | java.lang.SecurityException | InvocationTargetException e) {
                log.error("Error instantiating new EvaluableCredentialCriterion instance", e);
                throw new SecurityException("Could not create new EvaluableCredentialCriterion", e);
            }
        }
        log.debug("Registry could not locate evaluable criteria for criteria class {}", (Object)criteria.getClass().getName());
        return null;
    }

    @Nullable
    public static synchronized Class<? extends EvaluableCredentialCriterion> lookup(@Nonnull Class<? extends Criterion> clazz) {
        Constraint.isNotNull(clazz, "Criterion class to lookup cannot be null");
        return registry.get(clazz);
    }

    public static synchronized void register(@Nonnull Class<? extends Criterion> criteriaClass, @Nonnull Class<? extends EvaluableCredentialCriterion> evaluableClass) {
        Constraint.isNotNull(criteriaClass, "Criterion class to register cannot be null");
        Constraint.isNotNull(evaluableClass, "Evaluable class to register cannot be null");
        Logger log = EvaluableCredentialCriteriaRegistry.getLogger();
        log.debug("Registering class {} as evaluator for class {}", (Object)evaluableClass.getName(), (Object)criteriaClass.getName());
        registry.put(criteriaClass, evaluableClass);
    }

    public static synchronized void deregister(@Nonnull Class<? extends Criterion> criteriaClass) {
        Constraint.isNotNull(criteriaClass, "Criterion class to unregister cannot be null");
        Logger log = EvaluableCredentialCriteriaRegistry.getLogger();
        log.debug("Deregistering evaluator for class {}", (Object)criteriaClass.getName());
        registry.remove(criteriaClass);
    }

    public static synchronized void clearRegistry() {
        Logger log = EvaluableCredentialCriteriaRegistry.getLogger();
        log.debug("Clearing evaluable criteria registry");
        registry.clear();
    }

    public static synchronized boolean isInitialized() {
        return initialized;
    }

    public static synchronized void init() {
        if (EvaluableCredentialCriteriaRegistry.isInitialized()) {
            return;
        }
        registry = new HashMap<Class<? extends Criterion>, Class<? extends EvaluableCredentialCriterion>>();
        EvaluableCredentialCriteriaRegistry.loadDefaultMappings();
        initialized = true;
    }

    public static synchronized void loadDefaultMappings() {
        Logger log = EvaluableCredentialCriteriaRegistry.getLogger();
        log.debug("Loading default evaluable credential criteria mappings");
        InputStream inStream = EvaluableCredentialCriteriaRegistry.class.getResourceAsStream(DEFAULT_MAPPINGS_FILE);
        if (inStream == null) {
            log.error("Could not open resource stream from default mappings file '{}'", (Object)DEFAULT_MAPPINGS_FILE);
            return;
        }
        Properties defaultMappings = new Properties();
        try {
            defaultMappings.load(inStream);
        }
        catch (IOException e) {
            log.error("Error loading properties file from resource stream", e);
            return;
        }
        EvaluableCredentialCriteriaRegistry.loadMappings(defaultMappings);
    }

    public static synchronized void loadMappings(@Nonnull Properties mappings) {
        Constraint.isNotNull(mappings, "Mappings to load cannot be null");
        Logger log = EvaluableCredentialCriteriaRegistry.getLogger();
        for (Object key : mappings.keySet()) {
            if (!(key instanceof String)) {
                log.error("Properties key was not an instance of String, was '{}', skipping...", (Object)key.getClass().getName());
                continue;
            }
            String criteriaName = (String)key;
            String evaluatorName = mappings.getProperty(criteriaName);
            ClassLoader classLoader = XMLObjectProviderRegistrySupport.class.getClassLoader();
            Class<?> criteriaClass = null;
            try {
                criteriaClass = classLoader.loadClass(criteriaName);
            }
            catch (ClassNotFoundException e) {
                log.error("Could not find criteria class '{}', skipping registration", (Object)criteriaName);
                continue;
            }
            Class<?> evaluableClass = null;
            try {
                evaluableClass = classLoader.loadClass(evaluatorName);
            }
            catch (ClassNotFoundException e) {
                log.error("Could not find evaluator class '{}', skipping registration", (Object)criteriaName);
                continue;
            }
            EvaluableCredentialCriteriaRegistry.register(criteriaClass, evaluableClass);
        }
    }

    @Nonnull
    private static Logger getLogger() {
        return LoggerFactory.getLogger(EvaluableCredentialCriteriaRegistry.class);
    }

    static {
        EvaluableCredentialCriteriaRegistry.init();
    }
}

