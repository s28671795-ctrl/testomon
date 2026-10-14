/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.constraintvalidation;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintTree;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManager;
import org.hibernate.validator.internal.engine.constraintvalidation.SimpleConstraintTree;
import org.hibernate.validator.internal.engine.validationcontext.ValidationContext;
import org.hibernate.validator.internal.engine.valuecontext.ValueContext;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

class ComposingConstraintTree<B extends Annotation>
extends ConstraintTree<B> {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private final List<ConstraintTree<?>> children;

    public ComposingConstraintTree(ConstraintValidatorManager constraintValidatorManager, ConstraintDescriptorImpl<B> descriptor, Type validatedValueType) {
        super(constraintValidatorManager, descriptor, validatedValueType);
        this.children = descriptor.getComposingConstraintImpls().stream().map(desc -> this.createConstraintTree(constraintValidatorManager, (ConstraintDescriptorImpl)desc)).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionHelper::toImmutableList));
    }

    private <U extends Annotation> ConstraintTree<U> createConstraintTree(ConstraintValidatorManager constraintValidatorManager, ConstraintDescriptorImpl<U> composingDescriptor) {
        if (composingDescriptor.getComposingConstraintImpls().isEmpty()) {
            return new SimpleConstraintTree<U>(constraintValidatorManager, composingDescriptor, this.getValidatedValueType());
        }
        return new ComposingConstraintTree<U>(constraintValidatorManager, composingDescriptor, this.getValidatedValueType());
    }

    @Override
    protected void validateConstraints(ValidationContext<?> validationContext, ValueContext<?, ?> valueContext, Collection<ConstraintValidatorContextImpl> violatedConstraintValidatorContexts) {
        Optional<ConstraintValidatorContextImpl> violatedLocalConstraintValidatorContext;
        CompositionResult compositionResult = this.validateComposingConstraints(validationContext, valueContext, violatedConstraintValidatorContexts);
        if (this.mainConstraintNeedsEvaluation(validationContext, violatedConstraintValidatorContexts)) {
            if (LOG.isTraceEnabled()) {
                LOG.tracef("Validating value %s against constraint defined by %s.", valueContext.getCurrentValidatedValue(), (Object)this.descriptor);
            }
            ConstraintValidator validator = this.getInitializedConstraintValidator(validationContext, valueContext);
            ConstraintValidatorContextImpl constraintValidatorContext = validationContext.createConstraintValidatorContextFor(this.descriptor, valueContext.getPropertyPath());
            violatedLocalConstraintValidatorContext = this.validateSingleConstraint(valueContext, constraintValidatorContext, validator);
            if (!violatedLocalConstraintValidatorContext.isPresent()) {
                compositionResult.setAtLeastOneTrue(true);
            } else {
                compositionResult.setAllTrue(false);
            }
        } else {
            violatedLocalConstraintValidatorContext = Optional.empty();
        }
        if (!this.passesCompositionTypeRequirement(violatedConstraintValidatorContexts, compositionResult)) {
            this.prepareFinalConstraintViolations(validationContext, valueContext, violatedConstraintValidatorContexts, violatedLocalConstraintValidatorContext);
        }
    }

    private boolean mainConstraintNeedsEvaluation(ValidationContext<?> validationContext, Collection<ConstraintValidatorContextImpl> violatedConstraintValidatorContexts) {
        if (!this.descriptor.getComposingConstraints().isEmpty() && this.descriptor.getMatchingConstraintValidatorDescriptors().isEmpty()) {
            return false;
        }
        if (violatedConstraintValidatorContexts.isEmpty()) {
            return true;
        }
        if (this.descriptor.isReportAsSingleViolation() && this.descriptor.getCompositionType() == CompositionType.AND) {
            return false;
        }
        return !validationContext.isFailFastModeEnabled();
    }

    private void prepareFinalConstraintViolations(ValidationContext<?> validationContext, ValueContext<?, ?> valueContext, Collection<ConstraintValidatorContextImpl> violatedConstraintValidatorContexts, Optional<ConstraintValidatorContextImpl> localConstraintValidatorContext) {
        if (this.reportAsSingleViolation()) {
            violatedConstraintValidatorContexts.clear();
            if (!localConstraintValidatorContext.isPresent()) {
                violatedConstraintValidatorContexts.add(validationContext.createConstraintValidatorContextFor(this.descriptor, valueContext.getPropertyPath()));
            }
        }
        if (localConstraintValidatorContext.isPresent()) {
            violatedConstraintValidatorContexts.add(localConstraintValidatorContext.get());
        }
    }

    private CompositionResult validateComposingConstraints(ValidationContext<?> validationContext, ValueContext<?, ?> valueContext, Collection<ConstraintValidatorContextImpl> violatedConstraintValidatorContexts) {
        CompositionResult compositionResult = new CompositionResult(true, false);
        for (ConstraintTree<?> tree : this.children) {
            ArrayList<ConstraintValidatorContextImpl> tmpConstraintValidatorContexts = new ArrayList<ConstraintValidatorContextImpl>(5);
            tree.validateConstraints(validationContext, valueContext, tmpConstraintValidatorContexts);
            violatedConstraintValidatorContexts.addAll(tmpConstraintValidatorContexts);
            if (tmpConstraintValidatorContexts.isEmpty()) {
                compositionResult.setAtLeastOneTrue(true);
                if (this.descriptor.getCompositionType() != CompositionType.OR) continue;
                break;
            }
            compositionResult.setAllTrue(false);
            if (this.descriptor.getCompositionType() != CompositionType.AND || !validationContext.isFailFastModeEnabled() && !this.descriptor.isReportAsSingleViolation()) continue;
            break;
        }
        return compositionResult;
    }

    private boolean passesCompositionTypeRequirement(Collection<?> constraintViolations, CompositionResult compositionResult) {
        CompositionType compositionType = this.getDescriptor().getCompositionType();
        boolean passedValidation = false;
        switch (compositionType) {
            case OR: {
                passedValidation = compositionResult.isAtLeastOneTrue();
                break;
            }
            case AND: {
                passedValidation = compositionResult.isAllTrue();
                break;
            }
            case ALL_FALSE: {
                boolean bl = passedValidation = !compositionResult.isAtLeastOneTrue();
            }
        }
        assert (!passedValidation || compositionType != CompositionType.AND || constraintViolations.isEmpty());
        if (passedValidation) {
            constraintViolations.clear();
        }
        return passedValidation;
    }

    private boolean reportAsSingleViolation() {
        return this.getDescriptor().isReportAsSingleViolation() || this.getDescriptor().getCompositionType() == CompositionType.ALL_FALSE;
    }

    private static final class CompositionResult {
        private boolean allTrue;
        private boolean atLeastOneTrue;

        CompositionResult(boolean allTrue, boolean atLeastOneTrue) {
            this.allTrue = allTrue;
            this.atLeastOneTrue = atLeastOneTrue;
        }

        public boolean isAllTrue() {
            return this.allTrue;
        }

        public boolean isAtLeastOneTrue() {
            return this.atLeastOneTrue;
        }

        public void setAllTrue(boolean allTrue) {
            this.allTrue = allTrue;
        }

        public void setAtLeastOneTrue(boolean atLeastOneTrue) {
            this.atLeastOneTrue = atLeastOneTrue;
        }
    }
}

