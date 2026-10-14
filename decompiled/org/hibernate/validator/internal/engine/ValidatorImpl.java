/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.ElementKind;
import javax.validation.Path;
import javax.validation.TraversableResolver;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.validation.groups.Default;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;
import org.hibernate.validator.internal.engine.ValidatorFactoryScopedContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManager;
import org.hibernate.validator.internal.engine.groups.Group;
import org.hibernate.validator.internal.engine.groups.GroupWithInheritance;
import org.hibernate.validator.internal.engine.groups.Sequence;
import org.hibernate.validator.internal.engine.groups.ValidationOrder;
import org.hibernate.validator.internal.engine.groups.ValidationOrderGenerator;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.engine.resolver.TraversableResolvers;
import org.hibernate.validator.internal.engine.validationcontext.BaseBeanValidationContext;
import org.hibernate.validator.internal.engine.validationcontext.ExecutableValidationContext;
import org.hibernate.validator.internal.engine.validationcontext.ValidationContextBuilder;
import org.hibernate.validator.internal.engine.validationcontext.ValidatorScopedContext;
import org.hibernate.validator.internal.engine.valuecontext.BeanValueContext;
import org.hibernate.validator.internal.engine.valuecontext.ValueContext;
import org.hibernate.validator.internal.engine.valuecontext.ValueContexts;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorHelper;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.BeanMetaDataManager;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaData;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaData;
import org.hibernate.validator.internal.metadata.aggregated.ContainerCascadingMetaData;
import org.hibernate.validator.internal.metadata.aggregated.ExecutableMetaData;
import org.hibernate.validator.internal.metadata.aggregated.ParameterMetaData;
import org.hibernate.validator.internal.metadata.aggregated.PropertyMetaData;
import org.hibernate.validator.internal.metadata.aggregated.ReturnValueMetaData;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.facets.Cascadable;
import org.hibernate.validator.internal.metadata.facets.Validatable;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.TypeHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.logging.Messages;

public class ValidatorImpl
implements Validator,
ExecutableValidator {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private static final Collection<Class<?>> DEFAULT_GROUPS = Collections.singletonList(Default.class);
    private final transient ValidationOrderGenerator validationOrderGenerator;
    private final ConstraintValidatorFactory constraintValidatorFactory;
    private final TraversableResolver traversableResolver;
    private final BeanMetaDataManager beanMetaDataManager;
    private final ConstraintValidatorManager constraintValidatorManager;
    private final ValueExtractorManager valueExtractorManager;
    private final ValidatorScopedContext validatorScopedContext;
    private final HibernateConstraintValidatorInitializationContext constraintValidatorInitializationContext;

    public ValidatorImpl(ConstraintValidatorFactory constraintValidatorFactory, BeanMetaDataManager beanMetaDataManager, ValueExtractorManager valueExtractorManager, ConstraintValidatorManager constraintValidatorManager, ValidationOrderGenerator validationOrderGenerator, ValidatorFactoryScopedContext validatorFactoryScopedContext) {
        this.constraintValidatorFactory = constraintValidatorFactory;
        this.beanMetaDataManager = beanMetaDataManager;
        this.valueExtractorManager = valueExtractorManager;
        this.constraintValidatorManager = constraintValidatorManager;
        this.validationOrderGenerator = validationOrderGenerator;
        this.validatorScopedContext = new ValidatorScopedContext(validatorFactoryScopedContext);
        this.traversableResolver = validatorFactoryScopedContext.getTraversableResolver();
        this.constraintValidatorInitializationContext = validatorFactoryScopedContext.getConstraintValidatorInitializationContext();
    }

    @Override
    public final <T> Set<ConstraintViolation<T>> validate(T object, Class<?> ... groups2) {
        Contracts.assertNotNull(object, Messages.MESSAGES.validatedObjectMustNotBeNull());
        this.sanityCheckGroups(groups2);
        Class<?> rootBeanClass = object.getClass();
        BeanMetaData<?> rootBeanMetaData = this.beanMetaDataManager.getBeanMetaData(rootBeanClass);
        if (!rootBeanMetaData.hasConstraints()) {
            return Collections.emptySet();
        }
        BaseBeanValidationContext<?> validationContext = this.getValidationContextBuilder().forValidate(rootBeanClass, rootBeanMetaData, object);
        ValidationOrder validationOrder = this.determineGroupValidationOrder(groups2);
        BeanValueContext valueContext = ValueContexts.getLocalExecutionContextForBean(this.validatorScopedContext.getParameterNameProvider(), object, validationContext.getRootBeanMetaData(), PathImpl.createRootPath());
        return this.validateInContext(validationContext, valueContext, validationOrder);
    }

    @Override
    public final <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?> ... groups2) {
        Contracts.assertNotNull(object, Messages.MESSAGES.validatedObjectMustNotBeNull());
        this.sanityCheckPropertyPath(propertyName);
        this.sanityCheckGroups(groups2);
        Class<?> rootBeanClass = object.getClass();
        BeanMetaData<?> rootBeanMetaData = this.beanMetaDataManager.getBeanMetaData(rootBeanClass);
        if (!rootBeanMetaData.hasConstraints()) {
            return Collections.emptySet();
        }
        PathImpl propertyPath = PathImpl.createPathFromString(propertyName);
        BaseBeanValidationContext<?> validationContext = this.getValidationContextBuilder().forValidateProperty(rootBeanClass, rootBeanMetaData, object, propertyPath);
        BeanValueContext valueContext = this.getValueContextForPropertyValidation(validationContext, propertyPath);
        if (valueContext.getCurrentBean() == null) {
            throw LOG.getUnableToReachPropertyToValidateException(validationContext.getRootBean(), propertyPath);
        }
        ValidationOrder validationOrder = this.determineGroupValidationOrder(groups2);
        return this.validateInContext(validationContext, valueContext, validationOrder);
    }

    @Override
    public final <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value, Class<?> ... groups2) {
        Contracts.assertNotNull(beanType, Messages.MESSAGES.beanTypeCannotBeNull());
        this.sanityCheckPropertyPath(propertyName);
        this.sanityCheckGroups(groups2);
        BeanMetaData<T> rootBeanMetaData = this.beanMetaDataManager.getBeanMetaData(beanType);
        if (!rootBeanMetaData.hasConstraints()) {
            return Collections.emptySet();
        }
        PathImpl propertyPath = PathImpl.createPathFromString(propertyName);
        BaseBeanValidationContext<T> validationContext = this.getValidationContextBuilder().forValidateValue(beanType, rootBeanMetaData, propertyPath);
        ValidationOrder validationOrder = this.determineGroupValidationOrder(groups2);
        return this.validateValueInContext(validationContext, value, propertyPath, validationOrder);
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateParameters(T object, Method method2, Object[] parameterValues, Class<?> ... groups2) {
        Contracts.assertNotNull(object, Messages.MESSAGES.validatedObjectMustNotBeNull());
        Contracts.assertNotNull(method2, Messages.MESSAGES.validatedMethodMustNotBeNull());
        Contracts.assertNotNull(parameterValues, Messages.MESSAGES.validatedParameterArrayMustNotBeNull());
        return this.validateParameters(object, (Executable)method2, parameterValues, groups2);
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateConstructorParameters(Constructor<? extends T> constructor, Object[] parameterValues, Class<?> ... groups2) {
        Contracts.assertNotNull(constructor, Messages.MESSAGES.validatedConstructorMustNotBeNull());
        Contracts.assertNotNull(parameterValues, Messages.MESSAGES.validatedParameterArrayMustNotBeNull());
        return this.validateParameters(null, constructor, parameterValues, groups2);
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateConstructorReturnValue(Constructor<? extends T> constructor, T createdObject, Class<?> ... groups2) {
        Contracts.assertNotNull(constructor, Messages.MESSAGES.validatedConstructorMustNotBeNull());
        Contracts.assertNotNull(createdObject, Messages.MESSAGES.validatedConstructorCreatedInstanceMustNotBeNull());
        return this.validateReturnValue(null, constructor, createdObject, groups2);
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateReturnValue(T object, Method method2, Object returnValue, Class<?> ... groups2) {
        Contracts.assertNotNull(object, Messages.MESSAGES.validatedObjectMustNotBeNull());
        Contracts.assertNotNull(method2, Messages.MESSAGES.validatedMethodMustNotBeNull());
        return this.validateReturnValue(object, (Executable)method2, returnValue, groups2);
    }

    private <T> Set<ConstraintViolation<T>> validateParameters(T object, Executable executable, Object[] parameterValues, Class<?> ... groups2) {
        this.sanityCheckGroups(groups2);
        Class<?> rootBeanClass = object != null ? object.getClass() : executable.getDeclaringClass();
        BeanMetaData<?> rootBeanMetaData = this.beanMetaDataManager.getBeanMetaData(rootBeanClass);
        if (!rootBeanMetaData.hasConstraints()) {
            return Collections.emptySet();
        }
        ExecutableValidationContext<?> validationContext = this.getValidationContextBuilder().forValidateParameters(rootBeanClass, rootBeanMetaData, object, executable, parameterValues);
        ValidationOrder validationOrder = this.determineGroupValidationOrder(groups2);
        this.validateParametersInContext(validationContext, parameterValues, validationOrder);
        return validationContext.getFailingConstraints();
    }

    private <T> Set<ConstraintViolation<T>> validateReturnValue(T object, Executable executable, Object returnValue, Class<?> ... groups2) {
        this.sanityCheckGroups(groups2);
        Class<?> rootBeanClass = object != null ? object.getClass() : executable.getDeclaringClass();
        BeanMetaData<?> rootBeanMetaData = this.beanMetaDataManager.getBeanMetaData(rootBeanClass);
        if (!rootBeanMetaData.hasConstraints()) {
            return Collections.emptySet();
        }
        ExecutableValidationContext<?> validationContext = this.getValidationContextBuilder().forValidateReturnValue(rootBeanClass, rootBeanMetaData, object, executable, returnValue);
        ValidationOrder validationOrder = this.determineGroupValidationOrder(groups2);
        this.validateReturnValueInContext(validationContext, object, returnValue, validationOrder);
        return validationContext.getFailingConstraints();
    }

    @Override
    public final BeanDescriptor getConstraintsForClass(Class<?> clazz) {
        return this.beanMetaDataManager.getBeanMetaData(clazz).getBeanDescriptor();
    }

    @Override
    public final <T> T unwrap(Class<T> type) {
        if (type.isAssignableFrom(Validator.class)) {
            return type.cast(this);
        }
        throw LOG.getTypeNotSupportedForUnwrappingException(type);
    }

    @Override
    public ExecutableValidator forExecutables() {
        return this;
    }

    private ValidationContextBuilder getValidationContextBuilder() {
        return new ValidationContextBuilder(this.constraintValidatorManager, this.constraintValidatorFactory, this.validatorScopedContext, TraversableResolvers.wrapWithCachingForSingleValidation(this.traversableResolver, this.validatorScopedContext.isTraversableResolverResultCacheEnabled()), this.constraintValidatorInitializationContext);
    }

    private void sanityCheckPropertyPath(String propertyName) {
        if (propertyName == null || propertyName.length() == 0) {
            throw LOG.getInvalidPropertyPathException();
        }
    }

    private void sanityCheckGroups(Class<?>[] groups2) {
        Contracts.assertNotNull(groups2, Messages.MESSAGES.groupMustNotBeNull());
        for (Class<?> clazz : groups2) {
            if (clazz != null) continue;
            throw new IllegalArgumentException(Messages.MESSAGES.groupMustNotBeNull());
        }
    }

    private ValidationOrder determineGroupValidationOrder(Class<?>[] groups2) {
        Collection<Class<?>> resultGroups = groups2.length == 0 ? DEFAULT_GROUPS : Arrays.asList(groups2);
        return this.validationOrderGenerator.getValidationOrder(resultGroups);
    }

    private <T, U> Set<ConstraintViolation<T>> validateInContext(BaseBeanValidationContext<T> validationContext, BeanValueContext<U, Object> valueContext, ValidationOrder validationOrder) {
        Group group;
        if (valueContext.getCurrentBean() == null) {
            return Collections.emptySet();
        }
        BeanMetaData<U> beanMetaData = valueContext.getCurrentBeanMetaData();
        if (beanMetaData.isDefaultGroupSequenceRedefined()) {
            validationOrder.assertDefaultGroupSequenceIsExpandable(beanMetaData.getDefaultGroupSequence(valueContext.getCurrentBean()));
        }
        Iterator<Group> groupIterator = validationOrder.getGroupIterator();
        while (groupIterator.hasNext()) {
            group = groupIterator.next();
            valueContext.setCurrentGroup(group.getDefiningClass());
            this.validateConstraintsForCurrentGroup(validationContext, valueContext);
            if (!this.shouldFailFast(validationContext)) continue;
            return validationContext.getFailingConstraints();
        }
        groupIterator = validationOrder.getGroupIterator();
        while (groupIterator.hasNext()) {
            group = groupIterator.next();
            valueContext.setCurrentGroup(group.getDefiningClass());
            this.validateCascadedConstraints(validationContext, valueContext);
            if (!this.shouldFailFast(validationContext)) continue;
            return validationContext.getFailingConstraints();
        }
        Iterator<Sequence> sequenceIterator = validationOrder.getSequenceIterator();
        block2: while (sequenceIterator.hasNext()) {
            Sequence sequence2 = sequenceIterator.next();
            for (GroupWithInheritance groupOfGroups : sequence2) {
                int numberOfViolations = validationContext.getFailingConstraints().size();
                for (Group group2 : groupOfGroups) {
                    valueContext.setCurrentGroup(group2.getDefiningClass());
                    this.validateConstraintsForCurrentGroup(validationContext, valueContext);
                    if (this.shouldFailFast(validationContext)) {
                        return validationContext.getFailingConstraints();
                    }
                    this.validateCascadedConstraints(validationContext, valueContext);
                    if (!this.shouldFailFast(validationContext)) continue;
                    return validationContext.getFailingConstraints();
                }
                if (validationContext.getFailingConstraints().size() <= numberOfViolations) continue;
                continue block2;
            }
        }
        return validationContext.getFailingConstraints();
    }

    private void validateConstraintsForCurrentGroup(BaseBeanValidationContext<?> validationContext, BeanValueContext<?, Object> valueContext) {
        if (!valueContext.validatingDefault()) {
            this.validateConstraintsForNonDefaultGroup(validationContext, valueContext);
        } else {
            this.validateConstraintsForDefaultGroup(validationContext, valueContext);
        }
    }

    private <U> void validateConstraintsForDefaultGroup(BaseBeanValidationContext<?> validationContext, BeanValueContext<U, Object> valueContext) {
        BeanMetaData<U> beanMetaData = valueContext.getCurrentBeanMetaData();
        HashMap validatedInterfaces = new HashMap();
        for (Class<U> clazz : beanMetaData.getClassHierarchy()) {
            BeanMetaData<U> hostingBeanMetaData = this.beanMetaDataManager.getBeanMetaData(clazz);
            boolean defaultGroupSequenceIsRedefined = hostingBeanMetaData.isDefaultGroupSequenceRedefined();
            if (defaultGroupSequenceIsRedefined) {
                Iterator<Sequence> defaultGroupSequence = hostingBeanMetaData.getDefaultValidationSequence(valueContext.getCurrentBean());
                Set<MetaConstraint<?>> metaConstraints = hostingBeanMetaData.getMetaConstraints();
                block1: while (defaultGroupSequence.hasNext()) {
                    for (GroupWithInheritance groupOfGroups : defaultGroupSequence.next()) {
                        boolean validationSuccessful = true;
                        for (Group defaultSequenceMember : groupOfGroups) {
                            validationSuccessful = this.validateConstraintsForSingleDefaultGroupElement(validationContext, valueContext, validatedInterfaces, clazz, metaConstraints, defaultSequenceMember) && validationSuccessful;
                        }
                        validationContext.markCurrentBeanAsProcessed(valueContext);
                        if (validationSuccessful) continue;
                        continue block1;
                    }
                }
            } else {
                Set<MetaConstraint<?>> metaConstraints = hostingBeanMetaData.getDirectMetaConstraints();
                this.validateConstraintsForSingleDefaultGroupElement(validationContext, valueContext, validatedInterfaces, clazz, metaConstraints, Group.DEFAULT_GROUP);
                validationContext.markCurrentBeanAsProcessed(valueContext);
            }
            if (!defaultGroupSequenceIsRedefined) continue;
            break;
        }
    }

    private <U> boolean validateConstraintsForSingleDefaultGroupElement(BaseBeanValidationContext<?> validationContext, ValueContext<U, Object> valueContext, Map<Class<?>, Class<?>> validatedInterfaces, Class<? super U> clazz, Set<MetaConstraint<?>> metaConstraints, Group defaultSequenceMember) {
        boolean validationSuccessful = true;
        valueContext.setCurrentGroup(defaultSequenceMember.getDefiningClass());
        for (MetaConstraint<?> metaConstraint : metaConstraints) {
            Class<?> declaringClass = metaConstraint.getLocation().getDeclaringClass();
            if (declaringClass.isInterface()) {
                Class<?> validatedForClass = validatedInterfaces.get(declaringClass);
                if (validatedForClass != null && !validatedForClass.equals(clazz)) continue;
                validatedInterfaces.put(declaringClass, clazz);
            }
            boolean tmp = this.validateMetaConstraint(validationContext, valueContext, valueContext.getCurrentBean(), metaConstraint);
            if (this.shouldFailFast(validationContext)) {
                return false;
            }
            validationSuccessful = validationSuccessful && tmp;
        }
        return validationSuccessful;
    }

    private void validateConstraintsForNonDefaultGroup(BaseBeanValidationContext<?> validationContext, BeanValueContext<?, Object> valueContext) {
        this.validateMetaConstraints(validationContext, valueContext, valueContext.getCurrentBean(), valueContext.getCurrentBeanMetaData().getMetaConstraints());
        validationContext.markCurrentBeanAsProcessed(valueContext);
    }

    private void validateMetaConstraints(BaseBeanValidationContext<?> validationContext, ValueContext<?, Object> valueContext, Object parent, Iterable<MetaConstraint<?>> constraints) {
        for (MetaConstraint<?> metaConstraint : constraints) {
            this.validateMetaConstraint(validationContext, valueContext, parent, metaConstraint);
            if (!this.shouldFailFast(validationContext)) continue;
            break;
        }
    }

    private boolean validateMetaConstraint(BaseBeanValidationContext<?> validationContext, ValueContext<?, Object> valueContext, Object parent, MetaConstraint<?> metaConstraint) {
        ValueContext.ValueState<Object> originalValueState = valueContext.getCurrentValueState();
        valueContext.appendNode(metaConstraint.getLocation());
        boolean success = true;
        if (this.isValidationRequired(validationContext, valueContext, metaConstraint)) {
            if (parent != null) {
                valueContext.setCurrentValidatedValue(valueContext.getValue(parent, metaConstraint.getLocation()));
            }
            success = metaConstraint.validateConstraint(validationContext, valueContext);
            validationContext.markConstraintProcessed(valueContext.getCurrentBean(), valueContext.getPropertyPath(), metaConstraint);
        }
        valueContext.resetValueState(originalValueState);
        return success;
    }

    private void validateCascadedConstraints(BaseBeanValidationContext<?> validationContext, ValueContext<?, Object> valueContext) {
        Validatable validatable = valueContext.getCurrentValidatable();
        ValueContext.ValueState<Object> originalValueState = valueContext.getCurrentValueState();
        for (Cascadable cascadable : validatable.getCascadables()) {
            valueContext.appendNode(cascadable);
            if (this.isCascadeRequired(validationContext, valueContext.getCurrentBean(), valueContext.getPropertyPath(), cascadable.getConstraintLocationKind())) {
                Object value = this.getCascadableValue(validationContext, valueContext.getCurrentBean(), cascadable);
                CascadingMetaData cascadingMetaData = cascadable.getCascadingMetaData();
                if (value != null) {
                    ContainerCascadingMetaData containerCascadingMetaData;
                    CascadingMetaData effectiveCascadingMetaData = cascadingMetaData.addRuntimeContainerSupport(this.valueExtractorManager, value.getClass());
                    if (effectiveCascadingMetaData.isCascading()) {
                        this.validateCascadedAnnotatedObjectForCurrentGroup(value, validationContext, valueContext, effectiveCascadingMetaData);
                    }
                    if (effectiveCascadingMetaData.isContainer() && (containerCascadingMetaData = effectiveCascadingMetaData.as(ContainerCascadingMetaData.class)).hasContainerElementsMarkedForCascading()) {
                        this.validateCascadedContainerElementsForCurrentGroup(value, validationContext, valueContext, containerCascadingMetaData.getContainerElementTypesCascadingMetaData());
                    }
                }
            }
            valueContext.resetValueState(originalValueState);
        }
    }

    private void validateCascadedAnnotatedObjectForCurrentGroup(Object value, BaseBeanValidationContext<?> validationContext, ValueContext<?, Object> valueContext, CascadingMetaData cascadingMetaData) {
        Class<?> originalGroup = valueContext.getCurrentGroup();
        Class<?> currentGroup = cascadingMetaData.convertGroup(originalGroup);
        if (validationContext.isBeanAlreadyValidated(value, currentGroup, valueContext.getPropertyPath()) || this.shouldFailFast(validationContext)) {
            return;
        }
        ValidationOrder validationOrder = this.validationOrderGenerator.getValidationOrder(currentGroup, currentGroup != originalGroup);
        BeanValueContext<?, Object> cascadedValueContext = this.buildNewLocalExecutionContext(valueContext, value);
        this.validateInContext(validationContext, cascadedValueContext, validationOrder);
    }

    private void validateCascadedContainerElementsForCurrentGroup(Object value, BaseBeanValidationContext<?> validationContext, ValueContext<?, ?> valueContext, List<ContainerCascadingMetaData> containerElementTypesCascadingMetaData) {
        for (ContainerCascadingMetaData cascadingMetaData : containerElementTypesCascadingMetaData) {
            if (!cascadingMetaData.isMarkedForCascadingOnAnnotatedObjectOrContainerElements()) continue;
            ValueExtractorDescriptor extractor = this.valueExtractorManager.getMaximallySpecificAndRuntimeContainerElementCompliantValueExtractor(cascadingMetaData.getEnclosingType(), cascadingMetaData.getTypeParameter(), value.getClass(), cascadingMetaData.getValueExtractorCandidates());
            if (extractor == null) {
                throw LOG.getNoValueExtractorFoundForTypeException(cascadingMetaData.getEnclosingType(), cascadingMetaData.getTypeParameter(), value.getClass());
            }
            CascadingValueReceiver receiver = new CascadingValueReceiver(validationContext, valueContext, cascadingMetaData);
            ValueExtractorHelper.extractValues(extractor, value, receiver);
        }
    }

    private void validateCascadedContainerElementsInContext(Object value, BaseBeanValidationContext<?> validationContext, ValueContext<?, ?> valueContext, ContainerCascadingMetaData cascadingMetaData, ValidationOrder validationOrder) {
        Iterator<Group> groupIterator = validationOrder.getGroupIterator();
        while (groupIterator.hasNext()) {
            Group group = groupIterator.next();
            valueContext.setCurrentGroup(group.getDefiningClass());
            this.validateCascadedContainerElementsForCurrentGroup(value, validationContext, valueContext, cascadingMetaData.getContainerElementTypesCascadingMetaData());
            if (!this.shouldFailFast(validationContext)) continue;
            return;
        }
        Iterator<Sequence> sequenceIterator = validationOrder.getSequenceIterator();
        block1: while (sequenceIterator.hasNext()) {
            Sequence sequence2 = sequenceIterator.next();
            for (GroupWithInheritance groupOfGroups : sequence2) {
                int numberOfViolations = validationContext.getFailingConstraints().size();
                for (Group group : groupOfGroups) {
                    valueContext.setCurrentGroup(group.getDefiningClass());
                    this.validateCascadedContainerElementsForCurrentGroup(value, validationContext, valueContext, cascadingMetaData.getContainerElementTypesCascadingMetaData());
                    if (!this.shouldFailFast(validationContext)) continue;
                    return;
                }
                if (validationContext.getFailingConstraints().size() <= numberOfViolations) continue;
                continue block1;
            }
        }
    }

    private BeanValueContext<?, Object> buildNewLocalExecutionContext(ValueContext<?, ?> valueContext, Object value) {
        Contracts.assertNotNull(value, "value cannot be null");
        BeanMetaData<?> beanMetaData = this.beanMetaDataManager.getBeanMetaData(value.getClass());
        BeanValueContext<Object, Object> newValueContext = ValueContexts.getLocalExecutionContextForBean(this.validatorScopedContext.getParameterNameProvider(), value, beanMetaData, valueContext.getPropertyPath());
        newValueContext.setCurrentValidatedValue(value);
        return newValueContext;
    }

    private <T> Set<ConstraintViolation<T>> validateValueInContext(BaseBeanValidationContext<T> validationContext, Object value, PathImpl propertyPath, ValidationOrder validationOrder) {
        BeanValueContext<T, Object> valueContext = this.getValueContextForValueValidation(validationContext.getRootBeanClass(), propertyPath);
        valueContext.setCurrentValidatedValue(value);
        BeanMetaData<T> beanMetaData = valueContext.getCurrentBeanMetaData();
        if (beanMetaData.isDefaultGroupSequenceRedefined()) {
            validationOrder.assertDefaultGroupSequenceIsExpandable(beanMetaData.getDefaultGroupSequence(null));
        }
        Iterator<Group> groupIterator = validationOrder.getGroupIterator();
        while (groupIterator.hasNext()) {
            Group group = groupIterator.next();
            valueContext.setCurrentGroup(group.getDefiningClass());
            this.validateConstraintsForCurrentGroup(validationContext, valueContext);
            if (!this.shouldFailFast(validationContext)) continue;
            return validationContext.getFailingConstraints();
        }
        Iterator<Sequence> sequenceIterator = validationOrder.getSequenceIterator();
        block1: while (sequenceIterator.hasNext()) {
            Sequence sequence2 = sequenceIterator.next();
            for (GroupWithInheritance groupOfGroups : sequence2) {
                int numberOfConstraintViolationsBefore = validationContext.getFailingConstraints().size();
                for (Group group : groupOfGroups) {
                    valueContext.setCurrentGroup(group.getDefiningClass());
                    this.validateConstraintsForCurrentGroup(validationContext, valueContext);
                    if (!this.shouldFailFast(validationContext)) continue;
                    return validationContext.getFailingConstraints();
                }
                if (validationContext.getFailingConstraints().size() <= numberOfConstraintViolationsBefore) continue;
                continue block1;
            }
        }
        return validationContext.getFailingConstraints();
    }

    private <T> void validateParametersInContext(ExecutableValidationContext<T> validationContext, Object[] parameterValues, ValidationOrder validationOrder) {
        BeanMetaData beanMetaData = validationContext.getRootBeanMetaData();
        Optional<ExecutableMetaData> executableMetaDataOptional = validationContext.getExecutableMetaData();
        if (!executableMetaDataOptional.isPresent()) {
            return;
        }
        ExecutableMetaData executableMetaData = executableMetaDataOptional.get();
        if (parameterValues.length != executableMetaData.getParameterTypes().length) {
            throw LOG.getInvalidParameterCountForExecutableException(ExecutableHelper.getExecutableAsString(executableMetaData.getType().toString() + "#" + executableMetaData.getName(), executableMetaData.getParameterTypes()), executableMetaData.getParameterTypes().length, parameterValues.length);
        }
        if (beanMetaData.isDefaultGroupSequenceRedefined()) {
            validationOrder.assertDefaultGroupSequenceIsExpandable(beanMetaData.getDefaultGroupSequence(validationContext.getRootBean()));
        }
        Iterator<Group> groupIterator = validationOrder.getGroupIterator();
        while (groupIterator.hasNext()) {
            this.validateParametersForGroup(validationContext, executableMetaData, parameterValues, groupIterator.next());
            if (!this.shouldFailFast(validationContext)) continue;
            return;
        }
        ValueContext cascadingValueContext = ValueContexts.getLocalExecutionContextForExecutable(this.validatorScopedContext.getParameterNameProvider(), parameterValues, executableMetaData.getValidatableParametersMetaData(), PathImpl.createPathForExecutable(executableMetaData));
        groupIterator = validationOrder.getGroupIterator();
        while (groupIterator.hasNext()) {
            Group group = groupIterator.next();
            cascadingValueContext.setCurrentGroup(group.getDefiningClass());
            this.validateCascadedConstraints(validationContext, cascadingValueContext);
            if (!this.shouldFailFast(validationContext)) continue;
            return;
        }
        Iterator<Sequence> sequenceIterator = validationOrder.getSequenceIterator();
        block2: while (sequenceIterator.hasNext()) {
            Sequence sequence2 = sequenceIterator.next();
            for (GroupWithInheritance groupOfGroups : sequence2) {
                int numberOfViolations = validationContext.getFailingConstraints().size();
                for (Group group : groupOfGroups) {
                    this.validateParametersForGroup(validationContext, executableMetaData, parameterValues, group);
                    if (this.shouldFailFast(validationContext)) {
                        return;
                    }
                    cascadingValueContext.setCurrentGroup(group.getDefiningClass());
                    this.validateCascadedConstraints(validationContext, cascadingValueContext);
                    if (!this.shouldFailFast(validationContext)) continue;
                    return;
                }
                if (validationContext.getFailingConstraints().size() <= numberOfViolations) continue;
                continue block2;
            }
        }
    }

    private <T> void validateParametersForGroup(ExecutableValidationContext<T> validationContext, ExecutableMetaData executableMetaData, Object[] parameterValues, Group group) {
        Contracts.assertNotNull(executableMetaData, "executableMetaData may not be null");
        if (group.isDefaultGroup()) {
            Iterator<Sequence> defaultGroupSequence = validationContext.getRootBeanMetaData().getDefaultValidationSequence(validationContext.getRootBean());
            while (defaultGroupSequence.hasNext()) {
                Sequence sequence2 = defaultGroupSequence.next();
                int numberOfViolations = validationContext.getFailingConstraints().size();
                for (GroupWithInheritance expandedGroup : sequence2) {
                    for (Group defaultGroupSequenceElement : expandedGroup) {
                        this.validateParametersForSingleGroup(validationContext, parameterValues, executableMetaData, defaultGroupSequenceElement.getDefiningClass());
                        if (!this.shouldFailFast(validationContext)) continue;
                        return;
                    }
                    if (validationContext.getFailingConstraints().size() <= numberOfViolations) continue;
                    return;
                }
            }
        } else {
            this.validateParametersForSingleGroup(validationContext, parameterValues, executableMetaData, group.getDefiningClass());
        }
    }

    private <T> void validateParametersForSingleGroup(ExecutableValidationContext<T> validationContext, Object[] parameterValues, ExecutableMetaData executableMetaData, Class<?> currentValidatedGroup) {
        ValueContext valueContext;
        if (!executableMetaData.getCrossParameterConstraints().isEmpty()) {
            valueContext = this.getExecutableValueContext(validationContext.getRootBean(), executableMetaData, executableMetaData.getValidatableParametersMetaData(), currentValidatedGroup);
            this.validateMetaConstraints(validationContext, valueContext, parameterValues, executableMetaData.getCrossParameterConstraints());
            if (this.shouldFailFast(validationContext)) {
                return;
            }
        }
        valueContext = this.getExecutableValueContext(validationContext.getRootBean(), executableMetaData, executableMetaData.getValidatableParametersMetaData(), currentValidatedGroup);
        for (int i = 0; i < parameterValues.length; ++i) {
            ParameterMetaData parameterMetaData = executableMetaData.getParameterMetaData(i);
            Object value = parameterValues[i];
            if (value != null) {
                Class<?> valueType = value.getClass();
                if (parameterMetaData.getType() instanceof Class && ((Class)parameterMetaData.getType()).isPrimitive()) {
                    valueType = ReflectionHelper.unBoxedType(valueType);
                }
                if (!TypeHelper.isAssignable(TypeHelper.getErasedType(parameterMetaData.getType()), valueType)) {
                    throw LOG.getParameterTypesDoNotMatchException(valueType, parameterMetaData.getType(), i, validationContext.getExecutable());
                }
            }
            this.validateMetaConstraints(validationContext, valueContext, parameterValues, parameterMetaData);
            if (!this.shouldFailFast(validationContext)) continue;
            return;
        }
    }

    private <T> ValueContext<T, Object> getExecutableValueContext(T object, ExecutableMetaData executableMetaData, Validatable validatable, Class<?> group) {
        ValueContext valueContext = ValueContexts.getLocalExecutionContextForExecutable(this.validatorScopedContext.getParameterNameProvider(), object, validatable, PathImpl.createPathForExecutable(executableMetaData));
        valueContext.setCurrentGroup(group);
        return valueContext;
    }

    private <V, T> void validateReturnValueInContext(ExecutableValidationContext<T> validationContext, T bean, V value, ValidationOrder validationOrder) {
        boolean isCascadingRequired;
        BeanMetaData<T> beanMetaData = validationContext.getRootBeanMetaData();
        Optional<ExecutableMetaData> executableMetaDataOptional = validationContext.getExecutableMetaData();
        if (!executableMetaDataOptional.isPresent()) {
            return;
        }
        ExecutableMetaData executableMetaData = executableMetaDataOptional.get();
        if (beanMetaData.isDefaultGroupSequenceRedefined()) {
            validationOrder.assertDefaultGroupSequenceIsExpandable(beanMetaData.getDefaultGroupSequence(bean));
        }
        Iterator<Group> groupIterator = validationOrder.getGroupIterator();
        while (groupIterator.hasNext()) {
            this.validateReturnValueForGroup(validationContext, executableMetaData, bean, value, groupIterator.next());
            if (!this.shouldFailFast(validationContext)) continue;
            return;
        }
        ValueContext cascadingValueContext = null;
        boolean bl = isCascadingRequired = value != null && executableMetaData.isCascading();
        if (isCascadingRequired) {
            cascadingValueContext = ValueContexts.getLocalExecutionContextForExecutable(this.validatorScopedContext.getParameterNameProvider(), value, executableMetaData.getReturnValueMetaData(), PathImpl.createPathForExecutable(executableMetaData));
            groupIterator = validationOrder.getGroupIterator();
            while (groupIterator.hasNext()) {
                Group group = groupIterator.next();
                cascadingValueContext.setCurrentGroup(group.getDefiningClass());
                this.validateCascadedConstraints(validationContext, cascadingValueContext);
                if (!this.shouldFailFast(validationContext)) continue;
                return;
            }
        }
        Iterator<Sequence> sequenceIterator = validationOrder.getSequenceIterator();
        block2: while (sequenceIterator.hasNext()) {
            Sequence sequence2 = sequenceIterator.next();
            for (GroupWithInheritance groupOfGroups : sequence2) {
                int numberOfFailingConstraintsBeforeGroup = validationContext.getFailingConstraints().size();
                for (Group group : groupOfGroups) {
                    this.validateReturnValueForGroup(validationContext, executableMetaData, bean, value, group);
                    if (this.shouldFailFast(validationContext)) {
                        return;
                    }
                    if (!isCascadingRequired) continue;
                    cascadingValueContext.setCurrentGroup(group.getDefiningClass());
                    this.validateCascadedConstraints(validationContext, cascadingValueContext);
                    if (!this.shouldFailFast(validationContext)) continue;
                    return;
                }
                if (validationContext.getFailingConstraints().size() <= numberOfFailingConstraintsBeforeGroup) continue;
                continue block2;
            }
        }
    }

    private <T> void validateReturnValueForGroup(BaseBeanValidationContext<T> validationContext, ExecutableMetaData executableMetaData, T bean, Object value, Group group) {
        Contracts.assertNotNull(executableMetaData, "executableMetaData may not be null");
        if (group.isDefaultGroup()) {
            Iterator<Sequence> defaultGroupSequence = validationContext.getRootBeanMetaData().getDefaultValidationSequence(bean);
            while (defaultGroupSequence.hasNext()) {
                Sequence sequence2 = defaultGroupSequence.next();
                int numberOfViolations = validationContext.getFailingConstraints().size();
                for (GroupWithInheritance expandedGroup : sequence2) {
                    for (Group defaultGroupSequenceElement : expandedGroup) {
                        this.validateReturnValueForSingleGroup(validationContext, executableMetaData, bean, value, defaultGroupSequenceElement.getDefiningClass());
                        if (!this.shouldFailFast(validationContext)) continue;
                        return;
                    }
                    if (validationContext.getFailingConstraints().size() <= numberOfViolations) continue;
                    return;
                }
            }
        } else {
            this.validateReturnValueForSingleGroup(validationContext, executableMetaData, bean, value, group.getDefiningClass());
        }
    }

    private <T> void validateReturnValueForSingleGroup(BaseBeanValidationContext<T> validationContext, ExecutableMetaData executableMetaData, T bean, Object value, Class<?> oneGroup) {
        ValueContext<Object, Object> valueContext = this.getExecutableValueContext(executableMetaData.getKind() == ElementKind.CONSTRUCTOR ? value : bean, executableMetaData, executableMetaData.getReturnValueMetaData(), oneGroup);
        ReturnValueMetaData returnValueMetaData = executableMetaData.getReturnValueMetaData();
        this.validateMetaConstraints(validationContext, valueContext, value, returnValueMetaData);
    }

    private <V> BeanValueContext<?, V> getValueContextForPropertyValidation(BaseBeanValidationContext<?> validationContext, PathImpl propertyPath) {
        Class<?> clazz = validationContext.getRootBeanClass();
        BeanMetaData<?> beanMetaData = validationContext.getRootBeanMetaData();
        Object value = validationContext.getRootBean();
        PropertyMetaData propertyMetaData = null;
        Iterator<Path.Node> propertyPathIter = propertyPath.iterator();
        while (propertyPathIter.hasNext()) {
            NodeImpl propertyPathNode = (NodeImpl)propertyPathIter.next();
            propertyMetaData = this.getBeanPropertyMetaData(beanMetaData, propertyPathNode);
            if (!propertyPathIter.hasNext()) continue;
            if (!propertyMetaData.isCascading()) {
                throw LOG.getInvalidPropertyPathException(validationContext.getRootBeanClass(), propertyPath.asString());
            }
            if ((value = this.getCascadableValue(validationContext, value, propertyMetaData.getCascadables().iterator().next())) == null) {
                throw LOG.getUnableToReachPropertyToValidateException(validationContext.getRootBean(), propertyPath);
            }
            clazz = value.getClass();
            if (propertyPathNode.isIterable()) {
                propertyPathNode = (NodeImpl)propertyPathIter.next();
                if (propertyPathNode.getIndex() != null) {
                    value = ReflectionHelper.getIndexedValue(value, propertyPathNode.getIndex());
                } else if (propertyPathNode.getKey() != null) {
                    value = ReflectionHelper.getMappedValue(value, propertyPathNode.getKey());
                } else {
                    throw LOG.getPropertyPathMustProvideIndexOrMapKeyException();
                }
                if (value == null) {
                    throw LOG.getUnableToReachPropertyToValidateException(validationContext.getRootBean(), propertyPath);
                }
                clazz = value.getClass();
                beanMetaData = this.beanMetaDataManager.getBeanMetaData(clazz);
                propertyMetaData = this.getBeanPropertyMetaData(beanMetaData, propertyPathNode);
                continue;
            }
            beanMetaData = this.beanMetaDataManager.getBeanMetaData(clazz);
        }
        if (propertyMetaData == null) {
            throw LOG.getInvalidPropertyPathException(clazz, propertyPath.asString());
        }
        propertyPath.removeLeafNode();
        return ValueContexts.getLocalExecutionContextForBean(this.validatorScopedContext.getParameterNameProvider(), value, beanMetaData, propertyPath);
    }

    private <V> BeanValueContext<?, V> getValueContextForValueValidation(Class<?> rootBeanClass, PathImpl propertyPath) {
        Class<?> clazz = rootBeanClass;
        BeanMetaData<?> beanMetaData = null;
        PropertyMetaData propertyMetaData = null;
        Iterator<Path.Node> propertyPathIter = propertyPath.iterator();
        while (propertyPathIter.hasNext()) {
            NodeImpl propertyPathNode = (NodeImpl)propertyPathIter.next();
            beanMetaData = this.beanMetaDataManager.getBeanMetaData(clazz);
            propertyMetaData = this.getBeanPropertyMetaData(beanMetaData, propertyPathNode);
            if (!propertyPathIter.hasNext()) continue;
            if (propertyPathNode.isIterable()) {
                propertyPathNode = (NodeImpl)propertyPathIter.next();
                clazz = ReflectionHelper.getClassFromType(ReflectionHelper.getCollectionElementType(propertyMetaData.getType()));
                beanMetaData = this.beanMetaDataManager.getBeanMetaData(clazz);
                propertyMetaData = this.getBeanPropertyMetaData(beanMetaData, propertyPathNode);
                continue;
            }
            clazz = ReflectionHelper.getClassFromType(propertyMetaData.getType());
        }
        if (propertyMetaData == null) {
            throw LOG.getInvalidPropertyPathException(clazz, propertyPath.asString());
        }
        propertyPath.removeLeafNode();
        return ValueContexts.getLocalExecutionContextForValueValidation(this.validatorScopedContext.getParameterNameProvider(), beanMetaData, propertyPath);
    }

    private boolean isValidationRequired(BaseBeanValidationContext<?> validationContext, ValueContext<?, ?> valueContext, MetaConstraint<?> metaConstraint) {
        if (!validationContext.appliesTo(metaConstraint)) {
            return false;
        }
        if (validationContext.hasMetaConstraintBeenProcessed(valueContext.getCurrentBean(), valueContext.getPropertyPath(), metaConstraint)) {
            return false;
        }
        if (!metaConstraint.getGroupList().contains(valueContext.getCurrentGroup())) {
            return false;
        }
        return this.isReachable(validationContext, valueContext.getCurrentBean(), valueContext.getPropertyPath(), metaConstraint.getConstraintLocationKind());
    }

    private boolean isReachable(BaseBeanValidationContext<?> validationContext, Object traversableObject, PathImpl path, ConstraintLocation.ConstraintLocationKind constraintLocationKind) {
        if (this.needToCallTraversableResolver(path, constraintLocationKind)) {
            return true;
        }
        PathImpl pathToObject = PathImpl.createCopyWithoutLeafNode(path);
        try {
            return validationContext.getTraversableResolver().isReachable(traversableObject, path.getLeafNode(), validationContext.getRootBeanClass(), pathToObject, constraintLocationKind.getElementType());
        }
        catch (RuntimeException e) {
            throw LOG.getErrorDuringCallOfTraversableResolverIsReachableException(e);
        }
    }

    private boolean needToCallTraversableResolver(PathImpl path, ConstraintLocation.ConstraintLocationKind constraintLocationKind) {
        return this.isClassLevelConstraint(constraintLocationKind) || this.isCrossParameterValidation(path) || this.isParameterValidation(path) || this.isReturnValueValidation(path);
    }

    private boolean isCascadeRequired(BaseBeanValidationContext<?> validationContext, Object traversableObject, PathImpl path, ConstraintLocation.ConstraintLocationKind constraintLocationKind) {
        if (this.needToCallTraversableResolver(path, constraintLocationKind)) {
            return true;
        }
        boolean isReachable = this.isReachable(validationContext, traversableObject, path, constraintLocationKind);
        if (!isReachable) {
            return false;
        }
        PathImpl pathToObject = PathImpl.createCopyWithoutLeafNode(path);
        try {
            return validationContext.getTraversableResolver().isCascadable(traversableObject, path.getLeafNode(), validationContext.getRootBeanClass(), pathToObject, constraintLocationKind.getElementType());
        }
        catch (RuntimeException e) {
            throw LOG.getErrorDuringCallOfTraversableResolverIsCascadableException(e);
        }
    }

    private boolean isClassLevelConstraint(ConstraintLocation.ConstraintLocationKind constraintLocationKind) {
        return ConstraintLocation.ConstraintLocationKind.TYPE.equals((Object)constraintLocationKind);
    }

    private boolean isCrossParameterValidation(PathImpl path) {
        return path.getLeafNode().getKind() == ElementKind.CROSS_PARAMETER;
    }

    private boolean isParameterValidation(PathImpl path) {
        return path.getLeafNode().getKind() == ElementKind.PARAMETER;
    }

    private boolean isReturnValueValidation(PathImpl path) {
        return path.getLeafNode().getKind() == ElementKind.RETURN_VALUE;
    }

    private boolean shouldFailFast(BaseBeanValidationContext<?> validationContext) {
        return validationContext.isFailFastModeEnabled() && !validationContext.getFailingConstraints().isEmpty();
    }

    private PropertyMetaData getBeanPropertyMetaData(BeanMetaData<?> beanMetaData, Path.Node propertyNode) {
        if (!ElementKind.PROPERTY.equals((Object)propertyNode.getKind())) {
            throw LOG.getInvalidPropertyPathException(beanMetaData.getBeanClass(), propertyNode.getName());
        }
        return beanMetaData.getMetaDataFor(propertyNode.getName());
    }

    private Object getCascadableValue(BaseBeanValidationContext<?> validationContext, Object object, Cascadable cascadable) {
        return cascadable.getValue(object);
    }

    private class CascadingValueReceiver
    implements ValueExtractor.ValueReceiver {
        private final BaseBeanValidationContext<?> validationContext;
        private final ValueContext<?, ?> valueContext;
        private final ContainerCascadingMetaData cascadingMetaData;

        public CascadingValueReceiver(BaseBeanValidationContext<?> validationContext, ValueContext<?, ?> valueContext, ContainerCascadingMetaData cascadingMetaData) {
            this.validationContext = validationContext;
            this.valueContext = valueContext;
            this.cascadingMetaData = cascadingMetaData;
        }

        @Override
        public void value(String nodeName, Object value) {
            this.doValidate(value, nodeName);
        }

        @Override
        public void iterableValue(String nodeName, Object value) {
            this.valueContext.markCurrentPropertyAsIterable();
            this.doValidate(value, nodeName);
        }

        @Override
        public void indexedValue(String nodeName, int index, Object value) {
            this.valueContext.markCurrentPropertyAsIterableAndSetIndex(index);
            this.doValidate(value, nodeName);
        }

        @Override
        public void keyedValue(String nodeName, Object key, Object value) {
            this.valueContext.markCurrentPropertyAsIterableAndSetKey(key);
            this.doValidate(value, nodeName);
        }

        private void doValidate(Object value, String nodeName) {
            Class<?> originalGroup = this.valueContext.getCurrentGroup();
            Class<?> currentGroup = this.cascadingMetaData.convertGroup(originalGroup);
            if (value == null || this.validationContext.isBeanAlreadyValidated(value, currentGroup, this.valueContext.getPropertyPath()) || ValidatorImpl.this.shouldFailFast(this.validationContext)) {
                return;
            }
            ValidationOrder validationOrder = ValidatorImpl.this.validationOrderGenerator.getValidationOrder(currentGroup, currentGroup != originalGroup);
            BeanValueContext cascadedValueContext = ValidatorImpl.this.buildNewLocalExecutionContext(this.valueContext, value);
            if (this.cascadingMetaData.getDeclaredContainerClass() != null) {
                cascadedValueContext.setTypeParameter(this.cascadingMetaData.getDeclaredContainerClass(), this.cascadingMetaData.getDeclaredTypeParameterIndex());
            }
            if (this.cascadingMetaData.isCascading()) {
                ValidatorImpl.this.validateInContext(this.validationContext, cascadedValueContext, validationOrder);
            }
            if (this.cascadingMetaData.hasContainerElementsMarkedForCascading()) {
                BeanValueContext cascadedTypeArgumentValueContext = ValidatorImpl.this.buildNewLocalExecutionContext(this.valueContext, value);
                if (this.cascadingMetaData.getTypeParameter() != null) {
                    cascadedValueContext.setTypeParameter(this.cascadingMetaData.getDeclaredContainerClass(), this.cascadingMetaData.getDeclaredTypeParameterIndex());
                }
                if (nodeName != null) {
                    cascadedTypeArgumentValueContext.appendTypeParameterNode(nodeName);
                }
                ValidatorImpl.this.validateCascadedContainerElementsInContext(value, this.validationContext, cascadedTypeArgumentValueContext, this.cascadingMetaData, validationOrder);
            }
        }
    }
}

