/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.util;

import com.opencsv.bean.AbstractMappingStrategy;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvBindAndJoinByPosition;
import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.exceptionhandler.CsvExceptionHandler;
import com.opencsv.bean.util.OrderedObject;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvChainedException;
import com.opencsv.exceptions.CsvException;
import java.util.Collections;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

public final class OpencsvUtils {
    private OpencsvUtils() {
    }

    public static <T> MappingStrategy<T> determineMappingStrategy(Class<? extends T> type, Locale errorLocale, String profile) {
        boolean positionAnnotationsPresent = Stream.of(FieldUtils.getAllFields(type)).anyMatch(f -> f.isAnnotationPresent(CsvBindByPosition.class) || f.isAnnotationPresent(CsvBindAndSplitByPosition.class) || f.isAnnotationPresent(CsvBindAndJoinByPosition.class) || f.isAnnotationPresent(CsvCustomBindByPosition.class));
        AbstractMappingStrategy mappingStrategy = positionAnnotationsPresent ? new ColumnPositionMappingStrategy() : new HeaderColumnNameMappingStrategy();
        mappingStrategy.setErrorLocale(errorLocale);
        mappingStrategy.setProfile(profile);
        mappingStrategy.setType(type);
        return mappingStrategy;
    }

    public static <E> void queueRefuseToAcceptDefeat(BlockingQueue<E> queue, E object) {
        boolean interrupted = true;
        while (interrupted) {
            try {
                queue.put(object);
                interrupted = false;
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    public static synchronized void handleException(CsvException e, long lineNumber, CsvExceptionHandler exceptionHandler, BlockingQueue<OrderedObject<CsvException>> queue) {
        e.setLineNumber(lineNumber);
        CsvException capturedException = null;
        List<CsvException> exceptionList = e instanceof CsvChainedException ? Collections.unmodifiableList(((CsvChainedException)e).getExceptionChain()) : Collections.singletonList(e);
        for (CsvException iteratedException : exceptionList) {
            try {
                capturedException = exceptionHandler.handleException(iteratedException);
            }
            catch (CsvException csve) {
                capturedException = csve;
                throw new RuntimeException(csve);
            }
            finally {
                if (capturedException == null) continue;
                OpencsvUtils.queueRefuseToAcceptDefeat(queue, new OrderedObject<CsvException>(lineNumber, capturedException));
            }
        }
    }

    public static Pattern compilePatternAtLeastOneGroup(String regex2, int regexFlags, Class<?> callingClass, Locale errorLocale) throws CsvBadConverterException {
        Matcher m4;
        Locale exceptionLocale;
        Pattern tempPattern = OpencsvUtils.compilePattern(regex2, regexFlags, callingClass, errorLocale);
        Locale locale = exceptionLocale = errorLocale == null ? Locale.getDefault() : errorLocale;
        if (tempPattern != null && (m4 = tempPattern.matcher("")).groupCount() < 1) {
            throw new CsvBadConverterException(callingClass, String.format(ResourceBundle.getBundle("opencsv", exceptionLocale).getString("regex.without.capture.group"), regex2));
        }
        return tempPattern;
    }

    public static Pattern compilePattern(String regex2, int regexFlags, Class<?> callingClass, Locale errorLocale) throws CsvBadConverterException {
        Locale exceptionLocale;
        Pattern tempPattern = null;
        Locale locale = exceptionLocale = errorLocale == null ? Locale.getDefault() : errorLocale;
        if (StringUtils.isNotEmpty(regex2)) {
            try {
                tempPattern = Pattern.compile(regex2, regexFlags);
            }
            catch (PatternSyntaxException e) {
                CsvBadConverterException csve = new CsvBadConverterException(callingClass, String.format(ResourceBundle.getBundle("opencsv", exceptionLocale).getString("invalid.regex"), regex2));
                csve.initCause(e);
                throw csve;
            }
        }
        return tempPattern;
    }

    public static void verifyFormatString(String format, Class<?> callingClass, Locale errorLocale) {
        Locale exceptionLocale = errorLocale == null ? Locale.getDefault() : errorLocale;
        try {
            if (StringUtils.isNotEmpty(format)) {
                String string = String.format(format, " ");
            }
        }
        catch (IllegalFormatException e) {
            CsvBadConverterException csve = new CsvBadConverterException(callingClass, String.format(ResourceBundle.getBundle("opencsv", exceptionLocale).getString("invalid.one.parameter.format.string"), format));
            csve.initCause(e);
            throw csve;
        }
    }
}

