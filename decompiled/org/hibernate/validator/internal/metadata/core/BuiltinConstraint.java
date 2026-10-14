/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

enum BuiltinConstraint {
    JAVAX_VALIDATION_CONSTRAINTS_ASSERT_FALSE("javax.validation.constraints.AssertFalse"),
    JAVAX_VALIDATION_CONSTRAINTS_ASSERT_TRUE("javax.validation.constraints.AssertTrue"),
    JAVAX_VALIDATION_CONSTRAINTS_DECIMAL_MAX("javax.validation.constraints.DecimalMax"),
    JAVAX_VALIDATION_CONSTRAINTS_DECIMAL_MIN("javax.validation.constraints.DecimalMin"),
    JAVAX_VALIDATION_CONSTRAINTS_DIGITS("javax.validation.constraints.Digits"),
    JAVAX_VALIDATION_CONSTRAINTS_EMAIL("javax.validation.constraints.Email"),
    JAVAX_VALIDATION_CONSTRAINTS_FUTURE("javax.validation.constraints.Future"),
    JAVAX_VALIDATION_CONSTRAINTS_FUTURE_OR_PRESENT("javax.validation.constraints.FutureOrPresent"),
    JAVAX_VALIDATION_CONSTRAINTS_MIN("javax.validation.constraints.Min"),
    JAVAX_VALIDATION_CONSTRAINTS_MAX("javax.validation.constraints.Max"),
    JAVAX_VALIDATION_CONSTRAINTS_NEGATIVE("javax.validation.constraints.Negative"),
    JAVAX_VALIDATION_CONSTRAINTS_NEGATIVE_OR_ZERO("javax.validation.constraints.NegativeOrZero"),
    JAVAX_VALIDATION_CONSTRAINTS_NOT_BLANK("javax.validation.constraints.NotBlank"),
    JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY("javax.validation.constraints.NotEmpty"),
    JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL("javax.validation.constraints.NotNull"),
    JAVAX_VALIDATION_CONSTRAINTS_NULL("javax.validation.constraints.Null"),
    JAVAX_VALIDATION_CONSTRAINTS_PAST("javax.validation.constraints.Past"),
    JAVAX_VALIDATION_CONSTRAINTS_PAST_OR_PRESENT("javax.validation.constraints.PastOrPresent"),
    JAVAX_VALIDATION_CONSTRAINTS_PATTERN("javax.validation.constraints.Pattern"),
    JAVAX_VALIDATION_CONSTRAINTS_POSITIVE("javax.validation.constraints.Positive"),
    JAVAX_VALIDATION_CONSTRAINTS_POSITIVE_OR_ZERO("javax.validation.constraints.PositiveOrZero"),
    JAVAX_VALIDATION_CONSTRAINTS_SIZE("javax.validation.constraints.Size"),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_CODE_POINT_LENGTH("org.hibernate.validator.constraints.CodePointLength"),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_CURRENCY("org.hibernate.validator.constraints.Currency"),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_EMAIL("org.hibernate.validator.constraints.Email", Arrays.asList(JAVAX_VALIDATION_CONSTRAINTS_PATTERN)),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_ISBN("org.hibernate.validator.constraints.ISBN"),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_LENGTH("org.hibernate.validator.constraints.Length"),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_LUHN_CHECK("org.hibernate.validator.constraints.LuhnCheck"),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_CREDIT_CARD_NUMBER("org.hibernate.validator.constraints.CreditCardNumber", Arrays.asList(ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_LUHN_CHECK)),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_MOD10_CHECK("org.hibernate.validator.constraints.Mod10Check"),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_MOD11_CHECK("org.hibernate.validator.constraints.Mod11Check"),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_MOD_CHECK("org.hibernate.validator.constraints.ModCheck"),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_NORMALIZED("org.hibernate.validator.constraints.Normalized"),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_EAN("org.hibernate.validator.constraints.EAN", Arrays.asList(ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_MOD10_CHECK)),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_NOT_BLANK("org.hibernate.validator.constraints.NotBlank", Arrays.asList(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL)),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_NOT_EMPTY("org.hibernate.validator.constraints.NotEmpty", Arrays.asList(JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL, JAVAX_VALIDATION_CONSTRAINTS_SIZE)),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_PARAMETER_SCRIPT_ASSERT("org.hibernate.validator.constraints.ParameterScriptAssert"),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_RANGE("org.hibernate.validator.constraints.Range", Arrays.asList(JAVAX_VALIDATION_CONSTRAINTS_MIN, JAVAX_VALIDATION_CONSTRAINTS_MAX)),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_SCRIPT_ASSERT("org.hibernate.validator.constraints.ScriptAssert"),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_URL("org.hibernate.validator.constraints.URL", Arrays.asList(JAVAX_VALIDATION_CONSTRAINTS_PATTERN)),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_UNIQUE_ELEMENTS("org.hibernate.validator.constraints.UniqueElements"),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_BR_CNPJ("org.hibernate.validator.constraints.br.CNPJ", Arrays.asList(JAVAX_VALIDATION_CONSTRAINTS_PATTERN)),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_BR_CPF("org.hibernate.validator.constraints.br.CPF", Arrays.asList(JAVAX_VALIDATION_CONSTRAINTS_PATTERN)),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_BR_TITULO_ELEITORAL("org.hibernate.validator.constraints.br.TituloEleitoral", Arrays.asList(JAVAX_VALIDATION_CONSTRAINTS_PATTERN, ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_MOD11_CHECK)),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_PL_NIP("org.hibernate.validator.constraints.pl.NIP"),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_PL_PESEL("org.hibernate.validator.constraints.pl.PESEL"),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_PL_REGON("org.hibernate.validator.constraints.pl.REGON"),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_RU_INN("org.hibernate.validator.constraints.ru.INN"),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_TIME_DURATION_MAX("org.hibernate.validator.constraints.time.DurationMax"),
    ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_TIME_DURATION_MIN("org.hibernate.validator.constraints.time.DurationMin");

    private static final Map<String, Set<BuiltinConstraint>> CONSTRAINT_MAPPING;
    private String annotationClassName;
    private List<BuiltinConstraint> constraintDependencies;

    private BuiltinConstraint(String constraint) {
        this(constraint, Collections.emptyList());
    }

    private BuiltinConstraint(String constraint, List<BuiltinConstraint> composingConstraints) {
        this.annotationClassName = constraint;
        this.constraintDependencies = composingConstraints;
    }

    static Set<BuiltinConstraint> resolve(Set<String> constraints) {
        HashSet<BuiltinConstraint> resolvedConstraints = new HashSet<BuiltinConstraint>();
        for (String constraint : constraints) {
            Set<BuiltinConstraint> builtinConstraints = CONSTRAINT_MAPPING.get(constraint);
            if (builtinConstraints == null) continue;
            resolvedConstraints.addAll(builtinConstraints);
        }
        return resolvedConstraints;
    }

    static boolean isBuiltin(String constraint) {
        return CONSTRAINT_MAPPING.containsKey(constraint);
    }

    static Set<String> set() {
        return CONSTRAINT_MAPPING.keySet();
    }

    static {
        CONSTRAINT_MAPPING = new HashMap<String, Set<BuiltinConstraint>>();
        for (BuiltinConstraint constraint : BuiltinConstraint.values()) {
            if (constraint.constraintDependencies.isEmpty()) {
                CONSTRAINT_MAPPING.put(constraint.annotationClassName, Collections.singleton(constraint));
                continue;
            }
            HashSet<BuiltinConstraint> constraints = new HashSet<BuiltinConstraint>();
            constraints.add(constraint);
            constraints.addAll(constraint.constraintDependencies);
            CONSTRAINT_MAPPING.put(constraint.annotationClassName, constraints);
        }
    }
}

