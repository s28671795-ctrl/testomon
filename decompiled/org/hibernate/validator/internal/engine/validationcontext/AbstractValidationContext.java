/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.validationcontext;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.TraversableResolver;
import javax.validation.ValidationException;
import javax.validation.metadata.ConstraintDescriptor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;
import org.hibernate.validator.internal.engine.MessageInterpolatorContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManager;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintViolationCreationContext;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.engine.validationcontext.BaseBeanValidationContext;
import org.hibernate.validator.internal.engine.validationcontext.ValidatorScopedContext;
import org.hibernate.validator.internal.engine.valuecontext.ValueContext;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaData;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;

abstract class AbstractValidationContext<T>
implements BaseBeanValidationContext<T> {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private final ConstraintValidatorManager constraintValidatorManager;
    private final T rootBean;
    private final Class<T> rootBeanClass;
    private final BeanMetaData<T> rootBeanMetaData;
    private final ConstraintValidatorFactory constraintValidatorFactory;
    protected final ValidatorScopedContext validatorScopedContext;
    private final TraversableResolver traversableResolver;
    private final HibernateConstraintValidatorInitializationContext constraintValidatorInitializationContext;
    private final boolean disableAlreadyValidatedBeanTracking;
    private Set<BeanPathMetaConstraintProcessedUnit> processedPathUnits;
    private Set<BeanGroupProcessedUnit> processedGroupUnits;
    private Map<Object, Set<PathImpl>> processedPathsPerBean;
    private Set<ConstraintViolation<T>> failingConstraintViolations;

    protected AbstractValidationContext(ConstraintValidatorManager constraintValidatorManager, ConstraintValidatorFactory constraintValidatorFactory, ValidatorScopedContext validatorScopedContext, TraversableResolver traversableResolver, HibernateConstraintValidatorInitializationContext constraintValidatorInitializationContext, T rootBean, Class<T> rootBeanClass, BeanMetaData<T> rootBeanMetaData, boolean disableAlreadyValidatedBeanTracking) {
        this.constraintValidatorManager = constraintValidatorManager;
        this.validatorScopedContext = validatorScopedContext;
        this.constraintValidatorFactory = constraintValidatorFactory;
        this.traversableResolver = traversableResolver;
        this.constraintValidatorInitializationContext = constraintValidatorInitializationContext;
        this.rootBean = rootBean;
        this.rootBeanClass = rootBeanClass;
        this.rootBeanMetaData = rootBeanMetaData;
        this.disableAlreadyValidatedBeanTracking = disableAlreadyValidatedBeanTracking;
    }

    @Override
    public T getRootBean() {
        return this.rootBean;
    }

    @Override
    public Class<T> getRootBeanClass() {
        return this.rootBeanClass;
    }

    @Override
    public BeanMetaData<T> getRootBeanMetaData() {
        return this.rootBeanMetaData;
    }

    @Override
    public TraversableResolver getTraversableResolver() {
        return this.traversableResolver;
    }

    @Override
    public boolean isFailFastModeEnabled() {
        return this.validatorScopedContext.isFailFast();
    }

    @Override
    public ConstraintValidatorManager getConstraintValidatorManager() {
        return this.constraintValidatorManager;
    }

    @Override
    public HibernateConstraintValidatorInitializationContext getConstraintValidatorInitializationContext() {
        return this.constraintValidatorInitializationContext;
    }

    @Override
    public ConstraintValidatorFactory getConstraintValidatorFactory() {
        return this.constraintValidatorFactory;
    }

    @Override
    public boolean isBeanAlreadyValidated(Object value, Class<?> group, PathImpl path) {
        if (this.disableAlreadyValidatedBeanTracking) {
            return false;
        }
        boolean alreadyValidated = this.isAlreadyValidatedForCurrentGroup(value, group);
        if (alreadyValidated) {
            alreadyValidated = this.isAlreadyValidatedForPath(value, path);
        }
        return alreadyValidated;
    }

    @Override
    public void markCurrentBeanAsProcessed(ValueContext<?, ?> valueContext) {
        if (this.disableAlreadyValidatedBeanTracking) {
            return;
        }
        this.markCurrentBeanAsProcessedForCurrentGroup(valueContext.getCurrentBean(), valueContext.getCurrentGroup());
        this.markCurrentBeanAsProcessedForCurrentPath(valueContext.getCurrentBean(), valueContext.getPropertyPath());
    }

    @Override
    public Set<ConstraintViolation<T>> getFailingConstraints() {
        if (this.failingConstraintViolations == null) {
            return Collections.emptySet();
        }
        return this.failingConstraintViolations;
    }

    @Override
    public void addConstraintFailure(ValueContext<?, ?> valueContext, ConstraintViolationCreationContext constraintViolationCreationContext, ConstraintDescriptor<?> descriptor) {
        String messageTemplate = constraintViolationCreationContext.getMessage();
        String interpolatedMessage = this.interpolate(messageTemplate, constraintViolationCreationContext.getExpressionLanguageFeatureLevel(), constraintViolationCreationContext.isCustomViolation(), valueContext.getCurrentValidatedValue(), descriptor, constraintViolationCreationContext.getPath(), constraintViolationCreationContext.getMessageParameters(), constraintViolationCreationContext.getExpressionVariables());
        PathImpl path = PathImpl.createCopy(constraintViolationCreationContext.getPath());
        this.getInitializedFailingConstraintViolations().add(this.createConstraintViolation(messageTemplate, interpolatedMessage, path, descriptor, valueContext, constraintViolationCreationContext));
    }

    protected abstract ConstraintViolation<T> createConstraintViolation(String var1, String var2, Path var3, ConstraintDescriptor<?> var4, ValueContext<?, ?> var5, ConstraintViolationCreationContext var6);

    @Override
    public boolean hasMetaConstraintBeenProcessed(Object bean, Path path, MetaConstraint<?> metaConstraint) {
        if (metaConstraint.isDefinedForOneGroupOnly()) {
            return false;
        }
        return this.getInitializedProcessedPathUnits().contains(new BeanPathMetaConstraintProcessedUnit(bean, path, metaConstraint));
    }

    @Override
    public void markConstraintProcessed(Object bean, Path path, MetaConstraint<?> metaConstraint) {
        if (metaConstraint.isDefinedForOneGroupOnly()) {
            return;
        }
        this.getInitializedProcessedPathUnits().add(new BeanPathMetaConstraintProcessedUnit(bean, path, metaConstraint));
    }

    @Override
    public ConstraintValidatorContextImpl createConstraintValidatorContextFor(ConstraintDescriptorImpl<?> constraintDescriptor, PathImpl path) {
        return new ConstraintValidatorContextImpl(this.validatorScopedContext.getClockProvider(), path, constraintDescriptor, this.validatorScopedContext.getConstraintValidatorPayload(), this.validatorScopedContext.getConstraintExpressionLanguageFeatureLevel(), this.validatorScopedContext.getCustomViolationExpressionLanguageFeatureLevel());
    }

    public abstract String toString();

    private String interpolate(String messageTemplate, ExpressionLanguageFeatureLevel expressionLanguageFeatureLevel, boolean customViolation, Object validatedValue, ConstraintDescriptor<?> descriptor, Path path, Map<String, Object> messageParameters, Map<String, Object> expressionVariables) {
        MessageInterpolatorContext context = new MessageInterpolatorContext(descriptor, validatedValue, this.getRootBeanClass(), path, messageParameters, expressionVariables, expressionLanguageFeatureLevel, customViolation);
        try {
            return this.validatorScopedContext.getMessageInterpolator().interpolate(messageTemplate, context);
        }
        catch (ValidationException ve) {
            throw ve;
        }
        catch (Exception e) {
            throw LOG.getExceptionOccurredDuringMessageInterpolationException(e);
        }
    }

    private boolean isAlreadyValidatedForPath(Object value, PathImpl path) {
        Set<PathImpl> pathSet = this.getInitializedProcessedPathsPerBean().get(value);
        if (pathSet == null) {
            return false;
        }
        for (PathImpl p : pathSet) {
            if (!path.isRootPath() && !p.isRootPath() && !this.isSubPathOf(path, p) && !this.isSubPathOf(p, path)) continue;
            return true;
        }
        return false;
    }

    private boolean isSubPathOf(Path p1, Path p2) {
        Iterator p1Iter = p1.iterator();
        Iterator p2Iter = p2.iterator();
        while (p1Iter.hasNext()) {
            Path.Node p1Node = (Path.Node)p1Iter.next();
            if (!p2Iter.hasNext()) {
                return false;
            }
            Path.Node p2Node = (Path.Node)p2Iter.next();
            if (p1Node.equals(p2Node)) continue;
            return false;
        }
        return true;
    }

    private boolean isAlreadyValidatedForCurrentGroup(Object value, Class<?> group) {
        return this.getInitializedProcessedGroupUnits().contains(new BeanGroupProcessedUnit(value, group));
    }

    private void markCurrentBeanAsProcessedForCurrentPath(Object bean, PathImpl path) {
        Map<Object, Set<PathImpl>> processedPathsPerBean = this.getInitializedProcessedPathsPerBean();
        Set<PathImpl> processedPaths = processedPathsPerBean.get(bean);
        if (processedPaths == null) {
            processedPaths = new HashSet<PathImpl>();
            processedPathsPerBean.put(bean, processedPaths);
        }
        processedPaths.add(PathImpl.createCopy(path));
    }

    private void markCurrentBeanAsProcessedForCurrentGroup(Object bean, Class<?> group) {
        this.getInitializedProcessedGroupUnits().add(new BeanGroupProcessedUnit(bean, group));
    }

    private Set<BeanPathMetaConstraintProcessedUnit> getInitializedProcessedPathUnits() {
        if (this.processedPathUnits == null) {
            this.processedPathUnits = new HashSet<BeanPathMetaConstraintProcessedUnit>();
        }
        return this.processedPathUnits;
    }

    private Set<BeanGroupProcessedUnit> getInitializedProcessedGroupUnits() {
        if (this.processedGroupUnits == null) {
            this.processedGroupUnits = new HashSet<BeanGroupProcessedUnit>();
        }
        return this.processedGroupUnits;
    }

    private Map<Object, Set<PathImpl>> getInitializedProcessedPathsPerBean() {
        if (this.processedPathsPerBean == null) {
            this.processedPathsPerBean = new IdentityHashMap<Object, Set<PathImpl>>();
        }
        return this.processedPathsPerBean;
    }

    private Set<ConstraintViolation<T>> getInitializedFailingConstraintViolations() {
        if (this.failingConstraintViolations == null) {
            this.failingConstraintViolations = new HashSet<ConstraintViolation<T>>();
        }
        return this.failingConstraintViolations;
    }

    private static final class BeanPathMetaConstraintProcessedUnit {
        private Object bean;
        private Path path;
        private MetaConstraint<?> metaConstraint;
        private int hashCode;

        BeanPathMetaConstraintProcessedUnit(Object bean, Path path, MetaConstraint<?> metaConstraint) {
            this.bean = bean;
            this.path = path;
            this.metaConstraint = metaConstraint;
            this.hashCode = this.createHashCode();
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            BeanPathMetaConstraintProcessedUnit that = (BeanPathMetaConstraintProcessedUnit)o;
            if (this.bean != that.bean) {
                return false;
            }
            if (this.metaConstraint != that.metaConstraint) {
                return false;
            }
            return this.path.equals(that.path);
        }

        public int hashCode() {
            return this.hashCode;
        }

        private int createHashCode() {
            int result = System.identityHashCode(this.bean);
            result = 31 * result + this.path.hashCode();
            result = 31 * result + System.identityHashCode(this.metaConstraint);
            return result;
        }
    }

    private static final class BeanGroupProcessedUnit {
        private Object bean;
        private Class<?> group;
        private int hashCode;

        BeanGroupProcessedUnit(Object bean, Class<?> group) {
            this.bean = bean;
            this.group = group;
            this.hashCode = this.createHashCode();
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            BeanGroupProcessedUnit that = (BeanGroupProcessedUnit)o;
            if (this.bean != that.bean) {
                return false;
            }
            return this.group.equals(that.group);
        }

        public int hashCode() {
            return this.hashCode;
        }

        private int createHashCode() {
            int result = System.identityHashCode(this.bean);
            result = 31 * result + this.group.hashCode();
            return result;
        }
    }
}

