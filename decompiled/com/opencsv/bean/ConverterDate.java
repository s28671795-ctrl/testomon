/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Chronology;
import java.time.chrono.Era;
import java.time.chrono.HijrahEra;
import java.time.chrono.IsoEra;
import java.time.chrono.JapaneseEra;
import java.time.chrono.MinguoEra;
import java.time.chrono.ThaiBuddhistEra;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.BiFunction;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang3.StringUtils;

public class ConverterDate
extends AbstractCsvConverter {
    private static final String CSVDATE_NOT_DATE = "csvdate.not.date";
    private final SimpleDateFormat readSdf;
    private final SimpleDateFormat writeSdf;
    private final DateTimeFormatter readDtf;
    private final DateTimeFormatter writeDtf;
    private final BiFunction<DateTimeFormatter, String, TemporalAccessor> readTemporalConversionFunction;
    private final BiFunction<DateTimeFormatter, TemporalAccessor, String> writeTemporalConversionFunction;

    public ConverterDate(Class<?> type, String locale, String writeLocale, Locale errorLocale, String readFormat, String writeFormat, String readChronology, String writeChronology) {
        super(type, locale, writeLocale, errorLocale);
        DateTimeFormatter dtfWithoutChronology;
        Chronology readChrono = this.getChronology(readChronology, this.locale);
        Chronology writeChrono = this.getChronology(writeChronology, this.writeLocale);
        try {
            if (TemporalAccessor.class.isAssignableFrom(type)) {
                this.readSdf = null;
                dtfWithoutChronology = this.setDateTimeFormatter(readFormat, this.locale);
                this.readDtf = dtfWithoutChronology.withChronology(readChrono);
                this.readTemporalConversionFunction = this.determineReadTemporalConversionFunction(type);
            } else {
                this.readDtf = null;
                this.readTemporalConversionFunction = null;
                this.readSdf = this.setDateFormat(readFormat, this.locale);
            }
        }
        catch (IllegalArgumentException e) {
            CsvBadConverterException csve = new CsvBadConverterException(this.getClass(), String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("invalid.date.format.string"), readFormat));
            csve.initCause(e);
            throw csve;
        }
        try {
            if (TemporalAccessor.class.isAssignableFrom(type)) {
                this.writeSdf = null;
                dtfWithoutChronology = this.setDateTimeFormatter(writeFormat, this.writeLocale);
                this.writeDtf = dtfWithoutChronology.withChronology(writeChrono);
                this.writeTemporalConversionFunction = this.determineWriteTemporalConversionFunction(type);
            } else {
                this.writeDtf = null;
                this.writeTemporalConversionFunction = null;
                this.writeSdf = this.setDateFormat(writeFormat, this.writeLocale);
            }
        }
        catch (IllegalArgumentException e) {
            CsvBadConverterException csve = new CsvBadConverterException(this.getClass(), String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("invalid.date.format.string"), writeFormat));
            csve.initCause(e);
            throw csve;
        }
    }

    private BiFunction<DateTimeFormatter, TemporalAccessor, String> determineWriteTemporalConversionFunction(Class<?> type) {
        if (Instant.class.equals(type)) {
            return (writeDtf, value) -> {
                LocalDateTime ldt = LocalDateTime.ofInstant((Instant)value, ZoneId.of("UTC"));
                return writeDtf.format(ldt);
            };
        }
        return (writeDtf, value) -> writeDtf.format((TemporalAccessor)value);
    }

    private BiFunction<DateTimeFormatter, String, TemporalAccessor> determineReadTemporalConversionFunction(Class<?> type) {
        if (TemporalAccessor.class.equals(type)) {
            return DateTimeFormatter::parse;
        }
        if (ChronoLocalDateTime.class.equals(type) || LocalDateTime.class.equals(type)) {
            return (readDtf, s2) -> readDtf.parse((CharSequence)s2, LocalDateTime::from);
        }
        if (ChronoZonedDateTime.class.equals(type) || ZonedDateTime.class.equals(type)) {
            return (readDtf, s2) -> readDtf.parse((CharSequence)s2, ZonedDateTime::from);
        }
        if (Temporal.class.equals(type)) {
            return (readDtf, s2) -> readDtf.parseBest((CharSequence)s2, ZonedDateTime::from, OffsetDateTime::from, Instant::from, LocalDateTime::from, LocalDate::from, OffsetTime::from, LocalTime::from);
        }
        if (Era.class.equals(type) || IsoEra.class.equals(type)) {
            return (readDtf, s2) -> IsoEra.of(readDtf.parse((CharSequence)s2).get(ChronoField.ERA));
        }
        if (DayOfWeek.class.equals(type)) {
            return (readDtf, s2) -> readDtf.parse((CharSequence)s2, DayOfWeek::from);
        }
        if (HijrahEra.class.equals(type)) {
            return (readDtf, s2) -> HijrahEra.of(readDtf.parse((CharSequence)s2).get(ChronoField.ERA));
        }
        if (Instant.class.equals(type)) {
            return (readDtf, s2) -> readDtf.parse((CharSequence)s2, Instant::from);
        }
        if (ChronoLocalDate.class.isAssignableFrom(type)) {
            return (readDtf, s2) -> readDtf.parse((CharSequence)s2, ChronoLocalDate::from);
        }
        if (JapaneseEra.class.equals(type)) {
            return (readDtf, s2) -> JapaneseEra.of(readDtf.parse((CharSequence)s2).get(ChronoField.ERA));
        }
        if (LocalTime.class.equals(type)) {
            return (readDtf, s2) -> readDtf.parse((CharSequence)s2, LocalTime::from);
        }
        if (MinguoEra.class.equals(type)) {
            return (readDtf, s2) -> MinguoEra.of(readDtf.parse((CharSequence)s2).get(ChronoField.ERA));
        }
        if (Month.class.equals(type)) {
            return (readDtf, s2) -> readDtf.parse((CharSequence)s2, Month::from);
        }
        if (MonthDay.class.equals(type)) {
            return (readDtf, s2) -> readDtf.parse((CharSequence)s2, MonthDay::from);
        }
        if (OffsetDateTime.class.equals(type)) {
            return (readDtf, s2) -> readDtf.parse((CharSequence)s2, OffsetDateTime::from);
        }
        if (OffsetTime.class.equals(type)) {
            return (readDtf, s2) -> readDtf.parse((CharSequence)s2, OffsetTime::from);
        }
        if (ThaiBuddhistEra.class.equals(type)) {
            return (readDtf, s2) -> ThaiBuddhistEra.of(readDtf.parse((CharSequence)s2).get(ChronoField.ERA));
        }
        if (Year.class.equals(type)) {
            return (readDtf, s2) -> readDtf.parse((CharSequence)s2, Year::from);
        }
        if (YearMonth.class.equals(type)) {
            return (readDtf, s2) -> readDtf.parse((CharSequence)s2, YearMonth::from);
        }
        if (ZoneOffset.class.equals(type)) {
            return (readDtf, s2) -> readDtf.parse((CharSequence)s2, ZoneOffset::from);
        }
        throw new CsvBadConverterException(this.getClass(), String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString(CSVDATE_NOT_DATE), type));
    }

    private SimpleDateFormat setDateFormat(String format, Locale formatLocale) {
        if (formatLocale != null) {
            return new SimpleDateFormat(format, formatLocale);
        }
        return new SimpleDateFormat(format);
    }

    private DateTimeFormatter setDateTimeFormatter(String format, Locale formatLocale) {
        if (this.writeLocale != null) {
            return DateTimeFormatter.ofPattern(format, formatLocale);
        }
        return DateTimeFormatter.ofPattern(format);
    }

    private Chronology getChronology(String readChronology, Locale locale2) {
        Chronology readChrono;
        try {
            readChrono = StringUtils.isNotBlank(readChronology) ? Chronology.of(readChronology) : Chronology.ofLocale(locale2);
        }
        catch (DateTimeException e) {
            CsvBadConverterException csve = new CsvBadConverterException(this.getClass(), String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("chronology.not.found"), readChronology));
            csve.initCause(e);
            throw csve;
        }
        return readChrono;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object convertToRead(String value) throws CsvDataTypeMismatchException {
        Object returnValue = null;
        if (StringUtils.isNotBlank(value)) {
            if (Date.class.isAssignableFrom(this.type)) {
                try {
                    Date d;
                    SimpleDateFormat simpleDateFormat = this.readSdf;
                    synchronized (simpleDateFormat) {
                        d = this.readSdf.parse(value);
                    }
                    returnValue = this.type.getConstructor(Long.TYPE).newInstance(d.getTime());
                }
                catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException | ParseException e) {
                    CsvDataTypeMismatchException csve = new CsvDataTypeMismatchException((Object)value, this.type);
                    csve.initCause(e);
                    throw csve;
                }
            }
            if (TemporalAccessor.class.isAssignableFrom(this.type)) {
                try {
                    returnValue = this.type.cast(this.readTemporalConversionFunction.apply(this.readDtf, value));
                }
                catch (ArithmeticException | DateTimeException e) {
                    CsvDataTypeMismatchException csve = new CsvDataTypeMismatchException((Object)value, this.type);
                    csve.initCause(e);
                    throw csve;
                }
            }
            if (Calendar.class.isAssignableFrom(this.type) || XMLGregorianCalendar.class.isAssignableFrom(this.type)) {
                Date d;
                try {
                    SimpleDateFormat csve = this.readSdf;
                    synchronized (csve) {
                        d = this.readSdf.parse(value);
                    }
                }
                catch (ParseException e) {
                    CsvDataTypeMismatchException csve = new CsvDataTypeMismatchException((Object)value, this.type);
                    csve.initCause(e);
                    throw csve;
                }
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(d);
                if (this.type == XMLGregorianCalendar.class) {
                    try {
                        returnValue = this.type.cast(DatatypeFactory.newInstance().newXMLGregorianCalendar(gc));
                    }
                    catch (DatatypeConfigurationException e) {
                        CsvDataTypeMismatchException ex = new CsvDataTypeMismatchException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("xmlgregoriancalendar.impossible"));
                        ex.initCause(e);
                        throw ex;
                    }
                } else {
                    returnValue = this.type.cast(gc);
                }
            } else {
                throw new CsvDataTypeMismatchException(value, this.type, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString(CSVDATE_NOT_DATE), this.type));
            }
        }
        return returnValue;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String convertToWrite(Object value) throws CsvDataTypeMismatchException {
        String returnValue = null;
        if (value != null) {
            if (Date.class.isAssignableFrom(this.type)) {
                SimpleDateFormat simpleDateFormat = this.writeSdf;
                synchronized (simpleDateFormat) {
                    returnValue = this.writeSdf.format((Date)value);
                }
            } else if (TemporalAccessor.class.isAssignableFrom(this.type)) {
                try {
                    returnValue = this.writeTemporalConversionFunction.apply(this.writeDtf, (TemporalAccessor)value);
                }
                catch (ArithmeticException | DateTimeException e) {
                    CsvDataTypeMismatchException csve = new CsvDataTypeMismatchException(value, this.type);
                    csve.initCause(e);
                    throw csve;
                }
            } else if (Calendar.class.isAssignableFrom(this.type) || XMLGregorianCalendar.class.isAssignableFrom(this.type)) {
                Calendar c = value instanceof XMLGregorianCalendar ? ((XMLGregorianCalendar)value).toGregorianCalendar() : (Calendar)value;
                SimpleDateFormat simpleDateFormat = this.writeSdf;
                synchronized (simpleDateFormat) {
                    returnValue = this.writeSdf.format(c.getTime());
                }
            } else {
                throw new CsvDataTypeMismatchException(value, this.type, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString(CSVDATE_NOT_DATE), this.type));
            }
        }
        return returnValue;
    }
}

