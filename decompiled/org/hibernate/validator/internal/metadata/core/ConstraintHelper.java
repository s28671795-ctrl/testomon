/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.core;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.Constraint;
import javax.validation.ConstraintTarget;
import javax.validation.ConstraintValidator;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Negative;
import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import javax.validation.constraintvalidation.ValidationTarget;
import org.hibernate.validator.constraints.CodePointLength;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Currency;
import org.hibernate.validator.constraints.EAN;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.LuhnCheck;
import org.hibernate.validator.constraints.Mod10Check;
import org.hibernate.validator.constraints.Mod11Check;
import org.hibernate.validator.constraints.ModCheck;
import org.hibernate.validator.constraints.Normalized;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.hibernate.validator.constraints.pl.NIP;
import org.hibernate.validator.constraints.pl.PESEL;
import org.hibernate.validator.constraints.pl.REGON;
import org.hibernate.validator.constraints.ru.INN;
import org.hibernate.validator.constraints.time.DurationMax;
import org.hibernate.validator.constraints.time.DurationMin;
import org.hibernate.validator.internal.constraintvalidators.bv.AssertFalseValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.AssertTrueValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.DigitsValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.bv.DigitsValidatorForNumber;
import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.NotNullValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.NullValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.PatternValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.money.CurrencyValidatorForMonetaryAmount;
import org.hibernate.validator.internal.constraintvalidators.bv.money.DecimalMaxValidatorForMonetaryAmount;
import org.hibernate.validator.internal.constraintvalidators.bv.money.DecimalMinValidatorForMonetaryAmount;
import org.hibernate.validator.internal.constraintvalidators.bv.money.DigitsValidatorForMonetaryAmount;
import org.hibernate.validator.internal.constraintvalidators.bv.money.MaxValidatorForMonetaryAmount;
import org.hibernate.validator.internal.constraintvalidators.bv.money.MinValidatorForMonetaryAmount;
import org.hibernate.validator.internal.constraintvalidators.bv.money.NegativeOrZeroValidatorForMonetaryAmount;
import org.hibernate.validator.internal.constraintvalidators.bv.money.NegativeValidatorForMonetaryAmount;
import org.hibernate.validator.internal.constraintvalidators.bv.money.PositiveOrZeroValidatorForMonetaryAmount;
import org.hibernate.validator.internal.constraintvalidators.bv.money.PositiveValidatorForMonetaryAmount;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForArray;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForArraysOfBoolean;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForArraysOfByte;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForArraysOfChar;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForArraysOfDouble;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForArraysOfFloat;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForArraysOfInt;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForArraysOfLong;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForArraysOfShort;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForCollection;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForMap;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MaxValidatorForBigDecimal;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MaxValidatorForBigInteger;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MaxValidatorForByte;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MaxValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MaxValidatorForDouble;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MaxValidatorForFloat;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MaxValidatorForInteger;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MaxValidatorForLong;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MaxValidatorForNumber;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MaxValidatorForShort;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MinValidatorForBigDecimal;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MinValidatorForBigInteger;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MinValidatorForByte;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MinValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MinValidatorForDouble;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MinValidatorForFloat;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MinValidatorForInteger;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MinValidatorForLong;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MinValidatorForNumber;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MinValidatorForShort;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMaxValidatorForBigDecimal;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMaxValidatorForBigInteger;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMaxValidatorForByte;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMaxValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMaxValidatorForDouble;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMaxValidatorForFloat;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMaxValidatorForInteger;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMaxValidatorForLong;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMaxValidatorForNumber;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMaxValidatorForShort;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMinValidatorForBigDecimal;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMinValidatorForBigInteger;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMinValidatorForByte;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMinValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMinValidatorForDouble;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMinValidatorForFloat;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMinValidatorForInteger;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMinValidatorForLong;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMinValidatorForNumber;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMinValidatorForShort;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeOrZeroValidatorForBigDecimal;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeOrZeroValidatorForBigInteger;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeOrZeroValidatorForByte;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeOrZeroValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeOrZeroValidatorForDouble;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeOrZeroValidatorForFloat;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeOrZeroValidatorForInteger;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeOrZeroValidatorForLong;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeOrZeroValidatorForNumber;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeOrZeroValidatorForShort;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeValidatorForBigDecimal;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeValidatorForBigInteger;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeValidatorForByte;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeValidatorForDouble;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeValidatorForFloat;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeValidatorForInteger;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeValidatorForLong;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeValidatorForNumber;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.NegativeValidatorForShort;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveOrZeroValidatorForBigDecimal;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveOrZeroValidatorForBigInteger;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveOrZeroValidatorForByte;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveOrZeroValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveOrZeroValidatorForDouble;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveOrZeroValidatorForFloat;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveOrZeroValidatorForInteger;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveOrZeroValidatorForLong;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveOrZeroValidatorForNumber;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveOrZeroValidatorForShort;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveValidatorForBigDecimal;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveValidatorForBigInteger;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveValidatorForByte;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveValidatorForDouble;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveValidatorForFloat;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveValidatorForInteger;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveValidatorForLong;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveValidatorForNumber;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveValidatorForShort;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForArray;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForArraysOfBoolean;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForArraysOfByte;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForArraysOfChar;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForArraysOfDouble;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForArraysOfFloat;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForArraysOfInt;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForArraysOfLong;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForArraysOfShort;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForCollection;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForMap;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForCalendar;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForHijrahDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForInstant;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForJapaneseDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForLocalDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForLocalDateTime;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForLocalTime;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForMinguoDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForMonthDay;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForOffsetDateTime;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForOffsetTime;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForReadableInstant;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForReadablePartial;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForThaiBuddhistDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForYear;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForYearMonth;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForZonedDateTime;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForCalendar;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForHijrahDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForInstant;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForJapaneseDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForLocalDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForLocalDateTime;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForLocalTime;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForMinguoDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForMonthDay;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForOffsetDateTime;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForOffsetTime;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForReadableInstant;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForReadablePartial;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForThaiBuddhistDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForYear;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForYearMonth;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForZonedDateTime;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForCalendar;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForHijrahDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForInstant;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForJapaneseDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForLocalDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForLocalDateTime;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForLocalTime;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForMinguoDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForMonthDay;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForOffsetDateTime;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForOffsetTime;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForReadableInstant;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForReadablePartial;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForThaiBuddhistDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForYear;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForYearMonth;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForZonedDateTime;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForCalendar;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForHijrahDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForInstant;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForJapaneseDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForLocalDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForLocalDateTime;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForLocalTime;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForMinguoDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForMonthDay;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForOffsetDateTime;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForOffsetTime;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForReadableInstant;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForReadablePartial;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForThaiBuddhistDate;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForYear;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForYearMonth;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForZonedDateTime;
import org.hibernate.validator.internal.constraintvalidators.hv.CodePointLengthValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.EANValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.ISBNValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.LengthValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.LuhnCheckValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.Mod10CheckValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.Mod11CheckValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.ModCheckValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.NormalizedValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.ParameterScriptAssertValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.ScriptAssertValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.URLValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.UniqueElementsValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.pl.NIPValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.pl.PESELValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.pl.REGONValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.ru.INNValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.time.DurationMaxValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.time.DurationMinValidator;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorDescriptor;
import org.hibernate.validator.internal.metadata.core.BuiltinConstraint;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.logging.Messages;
import org.hibernate.validator.internal.util.privilegedactions.GetAnnotationAttribute;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredMethods;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;
import org.hibernate.validator.internal.util.privilegedactions.IsClassPresent;

public class ConstraintHelper {
    public static final String GROUPS = "groups";
    public static final String PAYLOAD = "payload";
    public static final String MESSAGE = "message";
    public static final String VALIDATION_APPLIES_TO = "validationAppliesTo";
    private static final List<String> SUPPORTED_VALID_METHODS = Collections.singletonList("validationAppliesTo");
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private static final String JODA_TIME_CLASS_NAME = "org.joda.time.ReadableInstant";
    private static final String JAVA_MONEY_CLASS_NAME = "javax.money.MonetaryAmount";
    private final Map<Class<? extends Annotation>, List<? extends ConstraintValidatorDescriptor<?>>> enabledBuiltinConstraints;
    private final ConcurrentMap<Class<? extends Annotation>, Boolean> externalConstraints = new ConcurrentHashMap<Class<? extends Annotation>, Boolean>();
    private final ConcurrentMap<Class<? extends Annotation>, Boolean> multiValueConstraints = new ConcurrentHashMap<Class<? extends Annotation>, Boolean>();
    private final ValidatorDescriptorMap validatorDescriptors = new ValidatorDescriptorMap();
    private Boolean javaMoneyInClasspath;
    private Boolean jodaTimeInClassPath;

    public static ConstraintHelper forAllBuiltinConstraints() {
        return new ConstraintHelper(new HashSet<BuiltinConstraint>(Arrays.asList(BuiltinConstraint.values())));
    }

    public static ConstraintHelper forBuiltinConstraints(Set<String> enabledConstraints) {
        return new ConstraintHelper(BuiltinConstraint.resolve(enabledConstraints));
    }

    private ConstraintHelper(Set<BuiltinConstraint> enabledBuiltinConstraints) {
        if (enabledBuiltinConstraints.isEmpty()) {
            this.enabledBuiltinConstraints = Collections.emptyMap();
            return;
        }
        HashMap tmpConstraints = new HashMap();
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_ASSERT_FALSE)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, AssertFalse.class, AssertFalseValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_ASSERT_TRUE)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, AssertTrue.class, AssertTrueValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_DECIMAL_MAX)) {
            ArrayList decimalMaxValidators = new ArrayList();
            decimalMaxValidators.add(DecimalMaxValidatorForBigDecimal.class);
            decimalMaxValidators.add(DecimalMaxValidatorForBigInteger.class);
            decimalMaxValidators.add(DecimalMaxValidatorForByte.class);
            decimalMaxValidators.add(DecimalMaxValidatorForDouble.class);
            decimalMaxValidators.add(DecimalMaxValidatorForFloat.class);
            decimalMaxValidators.add(DecimalMaxValidatorForLong.class);
            decimalMaxValidators.add(DecimalMaxValidatorForInteger.class);
            decimalMaxValidators.add(DecimalMaxValidatorForNumber.class);
            decimalMaxValidators.add(DecimalMaxValidatorForShort.class);
            decimalMaxValidators.add(DecimalMaxValidatorForCharSequence.class);
            if (this.isJavaMoneyInClasspath()) {
                decimalMaxValidators.add(DecimalMaxValidatorForMonetaryAmount.class);
            }
            ConstraintHelper.putBuiltinConstraints(tmpConstraints, DecimalMax.class, decimalMaxValidators);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_DECIMAL_MIN)) {
            ArrayList decimalMinValidators = new ArrayList();
            decimalMinValidators.add(DecimalMinValidatorForBigDecimal.class);
            decimalMinValidators.add(DecimalMinValidatorForBigInteger.class);
            decimalMinValidators.add(DecimalMinValidatorForByte.class);
            decimalMinValidators.add(DecimalMinValidatorForDouble.class);
            decimalMinValidators.add(DecimalMinValidatorForFloat.class);
            decimalMinValidators.add(DecimalMinValidatorForLong.class);
            decimalMinValidators.add(DecimalMinValidatorForInteger.class);
            decimalMinValidators.add(DecimalMinValidatorForNumber.class);
            decimalMinValidators.add(DecimalMinValidatorForShort.class);
            decimalMinValidators.add(DecimalMinValidatorForCharSequence.class);
            if (this.isJavaMoneyInClasspath()) {
                decimalMinValidators.add(DecimalMinValidatorForMonetaryAmount.class);
            }
            ConstraintHelper.putBuiltinConstraints(tmpConstraints, DecimalMin.class, decimalMinValidators);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_DIGITS)) {
            ArrayList digitsValidators = new ArrayList();
            digitsValidators.add(DigitsValidatorForCharSequence.class);
            digitsValidators.add(DigitsValidatorForNumber.class);
            if (this.isJavaMoneyInClasspath()) {
                digitsValidators.add(DigitsValidatorForMonetaryAmount.class);
            }
            ConstraintHelper.putBuiltinConstraints(tmpConstraints, Digits.class, digitsValidators);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_EMAIL)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, javax.validation.constraints.Email.class, org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_FUTURE)) {
            ArrayList futureValidators = new ArrayList(18);
            futureValidators.add(FutureValidatorForCalendar.class);
            futureValidators.add(FutureValidatorForDate.class);
            if (this.isJodaTimeInClasspath()) {
                futureValidators.add(FutureValidatorForReadableInstant.class);
                futureValidators.add(FutureValidatorForReadablePartial.class);
            }
            futureValidators.add(FutureValidatorForHijrahDate.class);
            futureValidators.add(FutureValidatorForInstant.class);
            futureValidators.add(FutureValidatorForJapaneseDate.class);
            futureValidators.add(FutureValidatorForLocalDate.class);
            futureValidators.add(FutureValidatorForLocalDateTime.class);
            futureValidators.add(FutureValidatorForLocalTime.class);
            futureValidators.add(FutureValidatorForMinguoDate.class);
            futureValidators.add(FutureValidatorForMonthDay.class);
            futureValidators.add(FutureValidatorForOffsetDateTime.class);
            futureValidators.add(FutureValidatorForOffsetTime.class);
            futureValidators.add(FutureValidatorForThaiBuddhistDate.class);
            futureValidators.add(FutureValidatorForYear.class);
            futureValidators.add(FutureValidatorForYearMonth.class);
            futureValidators.add(FutureValidatorForZonedDateTime.class);
            ConstraintHelper.putBuiltinConstraints(tmpConstraints, Future.class, futureValidators);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_FUTURE_OR_PRESENT)) {
            ArrayList futureOrPresentValidators = new ArrayList(18);
            futureOrPresentValidators.add(FutureOrPresentValidatorForCalendar.class);
            futureOrPresentValidators.add(FutureOrPresentValidatorForDate.class);
            if (this.isJodaTimeInClasspath()) {
                futureOrPresentValidators.add(FutureOrPresentValidatorForReadableInstant.class);
                futureOrPresentValidators.add(FutureOrPresentValidatorForReadablePartial.class);
            }
            futureOrPresentValidators.add(FutureOrPresentValidatorForHijrahDate.class);
            futureOrPresentValidators.add(FutureOrPresentValidatorForInstant.class);
            futureOrPresentValidators.add(FutureOrPresentValidatorForJapaneseDate.class);
            futureOrPresentValidators.add(FutureOrPresentValidatorForLocalDate.class);
            futureOrPresentValidators.add(FutureOrPresentValidatorForLocalDateTime.class);
            futureOrPresentValidators.add(FutureOrPresentValidatorForLocalTime.class);
            futureOrPresentValidators.add(FutureOrPresentValidatorForMinguoDate.class);
            futureOrPresentValidators.add(FutureOrPresentValidatorForMonthDay.class);
            futureOrPresentValidators.add(FutureOrPresentValidatorForOffsetDateTime.class);
            futureOrPresentValidators.add(FutureOrPresentValidatorForOffsetTime.class);
            futureOrPresentValidators.add(FutureOrPresentValidatorForThaiBuddhistDate.class);
            futureOrPresentValidators.add(FutureOrPresentValidatorForYear.class);
            futureOrPresentValidators.add(FutureOrPresentValidatorForYearMonth.class);
            futureOrPresentValidators.add(FutureOrPresentValidatorForZonedDateTime.class);
            ConstraintHelper.putBuiltinConstraints(tmpConstraints, FutureOrPresent.class, futureOrPresentValidators);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_MAX)) {
            ArrayList maxValidators = new ArrayList();
            maxValidators.add(MaxValidatorForBigDecimal.class);
            maxValidators.add(MaxValidatorForBigInteger.class);
            maxValidators.add(MaxValidatorForByte.class);
            maxValidators.add(MaxValidatorForDouble.class);
            maxValidators.add(MaxValidatorForFloat.class);
            maxValidators.add(MaxValidatorForInteger.class);
            maxValidators.add(MaxValidatorForLong.class);
            maxValidators.add(MaxValidatorForNumber.class);
            maxValidators.add(MaxValidatorForShort.class);
            maxValidators.add(MaxValidatorForCharSequence.class);
            if (this.isJavaMoneyInClasspath()) {
                maxValidators.add(MaxValidatorForMonetaryAmount.class);
            }
            ConstraintHelper.putBuiltinConstraints(tmpConstraints, Max.class, maxValidators);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_MIN)) {
            ArrayList minValidators = new ArrayList();
            minValidators.add(MinValidatorForBigDecimal.class);
            minValidators.add(MinValidatorForBigInteger.class);
            minValidators.add(MinValidatorForByte.class);
            minValidators.add(MinValidatorForDouble.class);
            minValidators.add(MinValidatorForFloat.class);
            minValidators.add(MinValidatorForInteger.class);
            minValidators.add(MinValidatorForLong.class);
            minValidators.add(MinValidatorForNumber.class);
            minValidators.add(MinValidatorForShort.class);
            minValidators.add(MinValidatorForCharSequence.class);
            if (this.isJavaMoneyInClasspath()) {
                minValidators.add(MinValidatorForMonetaryAmount.class);
            }
            ConstraintHelper.putBuiltinConstraints(tmpConstraints, Min.class, minValidators);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_NEGATIVE)) {
            ArrayList negativeValidators = new ArrayList();
            negativeValidators.add(NegativeValidatorForBigDecimal.class);
            negativeValidators.add(NegativeValidatorForBigInteger.class);
            negativeValidators.add(NegativeValidatorForDouble.class);
            negativeValidators.add(NegativeValidatorForFloat.class);
            negativeValidators.add(NegativeValidatorForLong.class);
            negativeValidators.add(NegativeValidatorForInteger.class);
            negativeValidators.add(NegativeValidatorForShort.class);
            negativeValidators.add(NegativeValidatorForByte.class);
            negativeValidators.add(NegativeValidatorForNumber.class);
            negativeValidators.add(NegativeValidatorForCharSequence.class);
            if (this.isJavaMoneyInClasspath()) {
                negativeValidators.add(NegativeValidatorForMonetaryAmount.class);
            }
            ConstraintHelper.putBuiltinConstraints(tmpConstraints, Negative.class, negativeValidators);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_NEGATIVE_OR_ZERO)) {
            ArrayList negativeOrZeroValidators = new ArrayList();
            negativeOrZeroValidators.add(NegativeOrZeroValidatorForBigDecimal.class);
            negativeOrZeroValidators.add(NegativeOrZeroValidatorForBigInteger.class);
            negativeOrZeroValidators.add(NegativeOrZeroValidatorForDouble.class);
            negativeOrZeroValidators.add(NegativeOrZeroValidatorForFloat.class);
            negativeOrZeroValidators.add(NegativeOrZeroValidatorForLong.class);
            negativeOrZeroValidators.add(NegativeOrZeroValidatorForInteger.class);
            negativeOrZeroValidators.add(NegativeOrZeroValidatorForShort.class);
            negativeOrZeroValidators.add(NegativeOrZeroValidatorForByte.class);
            negativeOrZeroValidators.add(NegativeOrZeroValidatorForNumber.class);
            negativeOrZeroValidators.add(NegativeOrZeroValidatorForCharSequence.class);
            if (this.isJavaMoneyInClasspath()) {
                negativeOrZeroValidators.add(NegativeOrZeroValidatorForMonetaryAmount.class);
            }
            ConstraintHelper.putBuiltinConstraints(tmpConstraints, NegativeOrZero.class, negativeOrZeroValidators);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_NOT_BLANK)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, javax.validation.constraints.NotBlank.class, NotBlankValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_NOT_EMPTY)) {
            ArrayList notEmptyValidators = new ArrayList(11);
            notEmptyValidators.add(NotEmptyValidatorForCharSequence.class);
            notEmptyValidators.add(NotEmptyValidatorForCollection.class);
            notEmptyValidators.add(NotEmptyValidatorForArray.class);
            notEmptyValidators.add(NotEmptyValidatorForMap.class);
            notEmptyValidators.add(NotEmptyValidatorForArraysOfBoolean.class);
            notEmptyValidators.add(NotEmptyValidatorForArraysOfByte.class);
            notEmptyValidators.add(NotEmptyValidatorForArraysOfChar.class);
            notEmptyValidators.add(NotEmptyValidatorForArraysOfDouble.class);
            notEmptyValidators.add(NotEmptyValidatorForArraysOfFloat.class);
            notEmptyValidators.add(NotEmptyValidatorForArraysOfInt.class);
            notEmptyValidators.add(NotEmptyValidatorForArraysOfLong.class);
            notEmptyValidators.add(NotEmptyValidatorForArraysOfShort.class);
            ConstraintHelper.putBuiltinConstraints(tmpConstraints, NotEmpty.class, notEmptyValidators);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_NOT_NULL)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, NotNull.class, NotNullValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_NULL)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, Null.class, NullValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_PAST)) {
            ArrayList pastValidators = new ArrayList(18);
            pastValidators.add(PastValidatorForCalendar.class);
            pastValidators.add(PastValidatorForDate.class);
            if (this.isJodaTimeInClasspath()) {
                pastValidators.add(PastValidatorForReadableInstant.class);
                pastValidators.add(PastValidatorForReadablePartial.class);
            }
            pastValidators.add(PastValidatorForHijrahDate.class);
            pastValidators.add(PastValidatorForInstant.class);
            pastValidators.add(PastValidatorForJapaneseDate.class);
            pastValidators.add(PastValidatorForLocalDate.class);
            pastValidators.add(PastValidatorForLocalDateTime.class);
            pastValidators.add(PastValidatorForLocalTime.class);
            pastValidators.add(PastValidatorForMinguoDate.class);
            pastValidators.add(PastValidatorForMonthDay.class);
            pastValidators.add(PastValidatorForOffsetDateTime.class);
            pastValidators.add(PastValidatorForOffsetTime.class);
            pastValidators.add(PastValidatorForThaiBuddhistDate.class);
            pastValidators.add(PastValidatorForYear.class);
            pastValidators.add(PastValidatorForYearMonth.class);
            pastValidators.add(PastValidatorForZonedDateTime.class);
            ConstraintHelper.putBuiltinConstraints(tmpConstraints, Past.class, pastValidators);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_PAST_OR_PRESENT)) {
            ArrayList pastOrPresentValidators = new ArrayList(18);
            pastOrPresentValidators.add(PastOrPresentValidatorForCalendar.class);
            pastOrPresentValidators.add(PastOrPresentValidatorForDate.class);
            if (this.isJodaTimeInClasspath()) {
                pastOrPresentValidators.add(PastOrPresentValidatorForReadableInstant.class);
                pastOrPresentValidators.add(PastOrPresentValidatorForReadablePartial.class);
            }
            pastOrPresentValidators.add(PastOrPresentValidatorForHijrahDate.class);
            pastOrPresentValidators.add(PastOrPresentValidatorForInstant.class);
            pastOrPresentValidators.add(PastOrPresentValidatorForJapaneseDate.class);
            pastOrPresentValidators.add(PastOrPresentValidatorForLocalDate.class);
            pastOrPresentValidators.add(PastOrPresentValidatorForLocalDateTime.class);
            pastOrPresentValidators.add(PastOrPresentValidatorForLocalTime.class);
            pastOrPresentValidators.add(PastOrPresentValidatorForMinguoDate.class);
            pastOrPresentValidators.add(PastOrPresentValidatorForMonthDay.class);
            pastOrPresentValidators.add(PastOrPresentValidatorForOffsetDateTime.class);
            pastOrPresentValidators.add(PastOrPresentValidatorForOffsetTime.class);
            pastOrPresentValidators.add(PastOrPresentValidatorForThaiBuddhistDate.class);
            pastOrPresentValidators.add(PastOrPresentValidatorForYear.class);
            pastOrPresentValidators.add(PastOrPresentValidatorForYearMonth.class);
            pastOrPresentValidators.add(PastOrPresentValidatorForZonedDateTime.class);
            ConstraintHelper.putBuiltinConstraints(tmpConstraints, PastOrPresent.class, pastOrPresentValidators);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_PATTERN)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, Pattern.class, PatternValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_POSITIVE)) {
            ArrayList positiveValidators = new ArrayList();
            positiveValidators.add(PositiveValidatorForBigDecimal.class);
            positiveValidators.add(PositiveValidatorForBigInteger.class);
            positiveValidators.add(PositiveValidatorForDouble.class);
            positiveValidators.add(PositiveValidatorForFloat.class);
            positiveValidators.add(PositiveValidatorForLong.class);
            positiveValidators.add(PositiveValidatorForInteger.class);
            positiveValidators.add(PositiveValidatorForShort.class);
            positiveValidators.add(PositiveValidatorForByte.class);
            positiveValidators.add(PositiveValidatorForNumber.class);
            positiveValidators.add(PositiveValidatorForCharSequence.class);
            if (this.isJavaMoneyInClasspath()) {
                positiveValidators.add(PositiveValidatorForMonetaryAmount.class);
            }
            ConstraintHelper.putBuiltinConstraints(tmpConstraints, Positive.class, positiveValidators);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_POSITIVE_OR_ZERO)) {
            ArrayList positiveOrZeroValidators = new ArrayList();
            positiveOrZeroValidators.add(PositiveOrZeroValidatorForBigDecimal.class);
            positiveOrZeroValidators.add(PositiveOrZeroValidatorForBigInteger.class);
            positiveOrZeroValidators.add(PositiveOrZeroValidatorForDouble.class);
            positiveOrZeroValidators.add(PositiveOrZeroValidatorForFloat.class);
            positiveOrZeroValidators.add(PositiveOrZeroValidatorForLong.class);
            positiveOrZeroValidators.add(PositiveOrZeroValidatorForInteger.class);
            positiveOrZeroValidators.add(PositiveOrZeroValidatorForShort.class);
            positiveOrZeroValidators.add(PositiveOrZeroValidatorForByte.class);
            positiveOrZeroValidators.add(PositiveOrZeroValidatorForNumber.class);
            positiveOrZeroValidators.add(PositiveOrZeroValidatorForCharSequence.class);
            if (this.isJavaMoneyInClasspath()) {
                positiveOrZeroValidators.add(PositiveOrZeroValidatorForMonetaryAmount.class);
            }
            ConstraintHelper.putBuiltinConstraints(tmpConstraints, PositiveOrZero.class, positiveOrZeroValidators);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.JAVAX_VALIDATION_CONSTRAINTS_SIZE)) {
            ArrayList sizeValidators = new ArrayList(11);
            sizeValidators.add(SizeValidatorForCharSequence.class);
            sizeValidators.add(SizeValidatorForCollection.class);
            sizeValidators.add(SizeValidatorForArray.class);
            sizeValidators.add(SizeValidatorForMap.class);
            sizeValidators.add(SizeValidatorForArraysOfBoolean.class);
            sizeValidators.add(SizeValidatorForArraysOfByte.class);
            sizeValidators.add(SizeValidatorForArraysOfChar.class);
            sizeValidators.add(SizeValidatorForArraysOfDouble.class);
            sizeValidators.add(SizeValidatorForArraysOfFloat.class);
            sizeValidators.add(SizeValidatorForArraysOfInt.class);
            sizeValidators.add(SizeValidatorForArraysOfLong.class);
            sizeValidators.add(SizeValidatorForArraysOfShort.class);
            ConstraintHelper.putBuiltinConstraints(tmpConstraints, Size.class, sizeValidators);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_BR_CNPJ)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, CNPJ.class, CNPJValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_BR_CPF)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, CPF.class, CPFValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_CURRENCY) && this.isJavaMoneyInClasspath()) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, Currency.class, CurrencyValidatorForMonetaryAmount.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_CREDIT_CARD_NUMBER)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, CreditCardNumber.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_TIME_DURATION_MAX)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, DurationMax.class, DurationMaxValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_TIME_DURATION_MIN)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, DurationMin.class, DurationMinValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_EAN)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, EAN.class, EANValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_EMAIL)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, Email.class, EmailValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_ISBN)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, ISBN.class, ISBNValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_LENGTH)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, Length.class, LengthValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_CODE_POINT_LENGTH)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, CodePointLength.class, CodePointLengthValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_LUHN_CHECK)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, LuhnCheck.class, LuhnCheckValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_MOD_CHECK)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, ModCheck.class, ModCheckValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_MOD10_CHECK)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, Mod10Check.class, Mod10CheckValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_MOD11_CHECK)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, Mod11Check.class, Mod11CheckValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_NORMALIZED)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, Normalized.class, NormalizedValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_PL_NIP)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, NIP.class, NIPValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_NOT_BLANK)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, NotBlank.class, org.hibernate.validator.internal.constraintvalidators.hv.NotBlankValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_NOT_EMPTY)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, org.hibernate.validator.constraints.NotEmpty.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_PARAMETER_SCRIPT_ASSERT)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, ParameterScriptAssert.class, ParameterScriptAssertValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_PL_PESEL)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, PESEL.class, PESELValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_RANGE)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, Range.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_PL_REGON)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, REGON.class, REGONValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_RU_INN)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, INN.class, INNValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_SCRIPT_ASSERT)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, ScriptAssert.class, ScriptAssertValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_BR_TITULO_ELEITORAL)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, TituloEleitoral.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_UNIQUE_ELEMENTS)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, UniqueElements.class, UniqueElementsValidator.class);
        }
        if (enabledBuiltinConstraints.contains((Object)BuiltinConstraint.ORG_HIBERNATE_VALIDATOR_CONSTRAINTS_URL)) {
            ConstraintHelper.putBuiltinConstraint(tmpConstraints, URL.class, URLValidator.class);
        }
        this.enabledBuiltinConstraints = Collections.unmodifiableMap(tmpConstraints);
    }

    private static <A extends Annotation> void putBuiltinConstraint(Map<Class<? extends Annotation>, List<ConstraintValidatorDescriptor<?>>> validators, Class<A> constraintType) {
        validators.put(constraintType, Collections.emptyList());
    }

    private static <A extends Annotation> void putBuiltinConstraint(Map<Class<? extends Annotation>, List<ConstraintValidatorDescriptor<?>>> validators, Class<A> constraintType, Class<? extends ConstraintValidator<A, ?>> validatorType) {
        validators.put(constraintType, Collections.singletonList(ConstraintValidatorDescriptor.forBuiltinClass(validatorType, constraintType)));
    }

    private static <A extends Annotation> void putBuiltinConstraints(Map<Class<? extends Annotation>, List<ConstraintValidatorDescriptor<?>>> validators, Class<A> constraintType, List<Class<? extends ConstraintValidator<A, ?>>> validatorTypes) {
        ArrayList<ConstraintValidatorDescriptor<A>> descriptors = new ArrayList<ConstraintValidatorDescriptor<A>>(validatorTypes.size());
        for (Class<ConstraintValidator<A, ?>> clazz : validatorTypes) {
            descriptors.add(ConstraintValidatorDescriptor.forBuiltinClass(clazz, constraintType));
        }
        validators.put(constraintType, CollectionHelper.toImmutableList(descriptors));
    }

    private boolean isBuiltinConstraint(Class<? extends Annotation> annotationType) {
        return BuiltinConstraint.isBuiltin(annotationType.getName());
    }

    public static Set<String> getBuiltinConstraints() {
        return BuiltinConstraint.set();
    }

    public <A extends Annotation> List<ConstraintValidatorDescriptor<A>> getAllValidatorDescriptors(Class<A> annotationType) {
        Contracts.assertNotNull(annotationType, Messages.MESSAGES.classCannotBeNull());
        return this.validatorDescriptors.computeIfAbsent(annotationType, a -> this.getDefaultValidatorDescriptors((Class)a));
    }

    public <A extends Annotation> List<ConstraintValidatorDescriptor<A>> findValidatorDescriptors(Class<A> annotationType, ValidationTarget validationTarget) {
        return this.getAllValidatorDescriptors(annotationType).stream().filter(d -> this.supportsValidationTarget((ConstraintValidatorDescriptor<?>)d, validationTarget)).collect(Collectors.toList());
    }

    private boolean supportsValidationTarget(ConstraintValidatorDescriptor<?> validatorDescriptor, ValidationTarget target) {
        return validatorDescriptor.getValidationTargets().contains((Object)target);
    }

    public <A extends Annotation> void putValidatorDescriptors(Class<A> annotationType, List<ConstraintValidatorDescriptor<A>> validatorDescriptors, boolean keepExistingClasses) {
        List<ConstraintValidatorDescriptor<A>> existingvalidatorDescriptors;
        ArrayList<ConstraintValidatorDescriptor<A>> validatorDescriptorsToAdd = new ArrayList<ConstraintValidatorDescriptor<A>>();
        if (keepExistingClasses && (existingvalidatorDescriptors = this.getAllValidatorDescriptors(annotationType)) != null) {
            validatorDescriptorsToAdd.addAll(0, existingvalidatorDescriptors);
        }
        validatorDescriptorsToAdd.addAll(validatorDescriptors);
        this.validatorDescriptors.put(annotationType, CollectionHelper.toImmutableList(validatorDescriptorsToAdd));
    }

    public boolean isMultiValueConstraint(Class<? extends Annotation> annotationType) {
        if (this.isJdkAnnotation(annotationType)) {
            return false;
        }
        return this.multiValueConstraints.computeIfAbsent(annotationType, a -> {
            Class<?> returnType;
            boolean isMultiValueConstraint = false;
            Method method2 = ConstraintHelper.run(GetMethod.action(a, "value"));
            if (method2 != null && (returnType = method2.getReturnType()).isArray() && returnType.getComponentType().isAnnotation()) {
                Class<?> componentType = returnType.getComponentType();
                isMultiValueConstraint = this.isConstraintAnnotation(componentType) ? Boolean.TRUE.booleanValue() : Boolean.FALSE.booleanValue();
            }
            return isMultiValueConstraint;
        });
    }

    public <A extends Annotation> List<Annotation> getConstraintsFromMultiValueConstraint(A multiValueConstraint) {
        Annotation[] annotations = ConstraintHelper.run(GetAnnotationAttribute.action(multiValueConstraint, "value", Annotation[].class));
        return Arrays.asList(annotations);
    }

    public boolean isConstraintAnnotation(Class<? extends Annotation> annotationType) {
        if (this.isBuiltinConstraint(annotationType)) {
            return true;
        }
        if (annotationType.getAnnotation(Constraint.class) == null) {
            return false;
        }
        return this.externalConstraints.computeIfAbsent(annotationType, a -> {
            this.assertMessageParameterExists((Class<? extends Annotation>)a);
            this.assertGroupsParameterExists((Class<? extends Annotation>)a);
            this.assertPayloadParameterExists((Class<? extends Annotation>)a);
            this.assertValidationAppliesToParameterSetUpCorrectly((Class<? extends Annotation>)a);
            this.assertNoParameterStartsWithValid((Class<? extends Annotation>)a);
            return Boolean.TRUE;
        });
    }

    private void assertNoParameterStartsWithValid(Class<? extends Annotation> annotationType) {
        Method[] methods;
        for (Method m4 : methods = ConstraintHelper.run(GetDeclaredMethods.action(annotationType))) {
            if (!m4.getName().startsWith("valid") || SUPPORTED_VALID_METHODS.contains(m4.getName())) continue;
            throw LOG.getConstraintParametersCannotStartWithValidException();
        }
    }

    private void assertPayloadParameterExists(Class<? extends Annotation> annotationType) {
        try {
            Method method2 = ConstraintHelper.run(GetMethod.action(annotationType, PAYLOAD));
            if (method2 == null) {
                throw LOG.getConstraintWithoutMandatoryParameterException(PAYLOAD, annotationType.getName());
            }
            Class[] defaultPayload = (Class[])method2.getDefaultValue();
            if (defaultPayload == null || defaultPayload.length != 0) {
                throw LOG.getWrongDefaultValueForPayloadParameterException(annotationType.getName());
            }
        }
        catch (ClassCastException e) {
            throw LOG.getWrongTypeForPayloadParameterException(annotationType.getName(), e);
        }
    }

    private void assertGroupsParameterExists(Class<? extends Annotation> annotationType) {
        try {
            Method method2 = ConstraintHelper.run(GetMethod.action(annotationType, GROUPS));
            if (method2 == null) {
                throw LOG.getConstraintWithoutMandatoryParameterException(GROUPS, annotationType.getName());
            }
            Class[] defaultGroups = (Class[])method2.getDefaultValue();
            if (defaultGroups == null || defaultGroups.length != 0) {
                throw LOG.getWrongDefaultValueForGroupsParameterException(annotationType.getName());
            }
        }
        catch (ClassCastException e) {
            throw LOG.getWrongTypeForGroupsParameterException(annotationType.getName(), e);
        }
    }

    private void assertMessageParameterExists(Class<? extends Annotation> annotationType) {
        Method method2 = ConstraintHelper.run(GetMethod.action(annotationType, MESSAGE));
        if (method2 == null) {
            throw LOG.getConstraintWithoutMandatoryParameterException(MESSAGE, annotationType.getName());
        }
        if (method2.getReturnType() != String.class) {
            throw LOG.getWrongTypeForMessageParameterException(annotationType.getName());
        }
    }

    private void assertValidationAppliesToParameterSetUpCorrectly(Class<? extends Annotation> annotationType) {
        boolean hasGenericValidators = !this.findValidatorDescriptors(annotationType, ValidationTarget.ANNOTATED_ELEMENT).isEmpty();
        boolean hasCrossParameterValidator = !this.findValidatorDescriptors(annotationType, ValidationTarget.PARAMETERS).isEmpty();
        Method method2 = ConstraintHelper.run(GetMethod.action(annotationType, VALIDATION_APPLIES_TO));
        if (hasGenericValidators && hasCrossParameterValidator) {
            if (method2 == null) {
                throw LOG.getGenericAndCrossParameterConstraintDoesNotDefineValidationAppliesToParameterException(annotationType);
            }
            if (method2.getReturnType() != ConstraintTarget.class) {
                throw LOG.getValidationAppliesToParameterMustHaveReturnTypeConstraintTargetException(annotationType);
            }
            ConstraintTarget defaultValue = (ConstraintTarget)((Object)method2.getDefaultValue());
            if (defaultValue != ConstraintTarget.IMPLICIT) {
                throw LOG.getValidationAppliesToParameterMustHaveDefaultValueImplicitException(annotationType);
            }
        } else if (method2 != null) {
            throw LOG.getValidationAppliesToParameterMustNotBeDefinedForNonGenericAndCrossParameterConstraintException(annotationType);
        }
    }

    public boolean isConstraintComposition(Class<? extends Annotation> annotationType) {
        return annotationType == ConstraintComposition.class;
    }

    public boolean isJdkAnnotation(Class<? extends Annotation> annotation) {
        Package pakkage = annotation.getPackage();
        return pakkage != null && pakkage.getName() != null && (pakkage.getName().startsWith("java.") || pakkage.getName().startsWith("jdk.internal"));
    }

    public void clear() {
        this.externalConstraints.clear();
        this.multiValueConstraints.clear();
    }

    private boolean isJodaTimeInClasspath() {
        if (this.jodaTimeInClassPath == null) {
            this.jodaTimeInClassPath = ConstraintHelper.isClassPresent(JODA_TIME_CLASS_NAME);
        }
        return this.jodaTimeInClassPath;
    }

    private boolean isJavaMoneyInClasspath() {
        if (this.javaMoneyInClasspath == null) {
            this.javaMoneyInClasspath = ConstraintHelper.isClassPresent(JAVA_MONEY_CLASS_NAME);
        }
        return this.javaMoneyInClasspath;
    }

    private <A extends Annotation> List<ConstraintValidatorDescriptor<A>> getDefaultValidatorDescriptors(Class<A> annotationType) {
        List<ConstraintValidatorDescriptor<A>> builtInValidators = this.enabledBuiltinConstraints.get(annotationType);
        if (builtInValidators != null) {
            return builtInValidators;
        }
        Class<? extends ConstraintValidator<?, ?>>[] validatedBy = annotationType.getAnnotation(Constraint.class).validatedBy();
        return Stream.of(validatedBy).map(c -> ConstraintValidatorDescriptor.forClass(c, annotationType)).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionHelper::toImmutableList));
    }

    private static boolean isClassPresent(String className) {
        return ConstraintHelper.run(IsClassPresent.action(className, ConstraintHelper.class.getClassLoader()));
    }

    private static <T> T run(PrivilegedAction<T> action) {
        return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
    }

    private static class ValidatorDescriptorMap {
        private final ConcurrentMap<Class<? extends Annotation>, List<? extends ConstraintValidatorDescriptor<?>>> constraintValidatorDescriptors = new ConcurrentHashMap();

        private ValidatorDescriptorMap() {
        }

        private <A extends Annotation> void put(Class<A> annotationType, List<ConstraintValidatorDescriptor<A>> validatorDescriptors) {
            this.constraintValidatorDescriptors.put(annotationType, validatorDescriptors);
        }

        private <A extends Annotation> List<ConstraintValidatorDescriptor<A>> computeIfAbsent(Class<A> annotationType, Function<? super Class<A>, List<ConstraintValidatorDescriptor<A>>> mappingFunction) {
            return this.constraintValidatorDescriptors.computeIfAbsent(annotationType, mappingFunction);
        }
    }
}

