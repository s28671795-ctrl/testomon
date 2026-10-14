/*
 * Decompiled with CFR 0.152.
 */
package org.joda.time.tz;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.MutableDateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.chrono.LenientChronology;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.tz.DateTimeZoneBuilder;
import org.joda.time.tz.ZoneInfoLogger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ZoneInfoCompiler {
    static DateTimeOfYear cStartOfYear;
    static Chronology cLenientISO;
    private Map<String, RuleSet> iRuleSets = new HashMap<String, RuleSet>();
    private List<Zone> iZones = new ArrayList<Zone>();
    private List<String> iGoodLinks = new ArrayList<String>();
    private List<String> iBackLinks = new ArrayList<String>();

    public static void main(String[] stringArray) throws Exception {
        int n;
        if (stringArray.length == 0) {
            ZoneInfoCompiler.printUsage();
            return;
        }
        File file = null;
        File file2 = null;
        boolean bl = false;
        for (n = 0; n < stringArray.length; ++n) {
            try {
                if ("-src".equals(stringArray[n])) {
                    file = new File(stringArray[++n]);
                    continue;
                }
                if ("-dst".equals(stringArray[n])) {
                    file2 = new File(stringArray[++n]);
                    continue;
                }
                if ("-verbose".equals(stringArray[n])) {
                    bl = true;
                    continue;
                }
                if (!"-?".equals(stringArray[n])) break;
                ZoneInfoCompiler.printUsage();
                return;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                ZoneInfoCompiler.printUsage();
                return;
            }
        }
        if (n >= stringArray.length) {
            ZoneInfoCompiler.printUsage();
            return;
        }
        File[] fileArray = new File[stringArray.length - n];
        int n2 = 0;
        while (n < stringArray.length) {
            fileArray[n2] = file == null ? new File(stringArray[n]) : new File(file, stringArray[n]);
            ++n;
            ++n2;
        }
        ZoneInfoLogger.set(bl);
        ZoneInfoCompiler zoneInfoCompiler = new ZoneInfoCompiler();
        zoneInfoCompiler.compile(file2, fileArray);
    }

    private static void printUsage() {
        System.out.println("Usage: java org.joda.time.tz.ZoneInfoCompiler <options> <source files>");
        System.out.println("where possible options include:");
        System.out.println("  -src <directory>    Specify where to read source files");
        System.out.println("  -dst <directory>    Specify where to write generated files");
        System.out.println("  -verbose            Output verbosely (default false)");
    }

    static DateTimeOfYear getStartOfYear() {
        if (cStartOfYear == null) {
            cStartOfYear = new DateTimeOfYear();
        }
        return cStartOfYear;
    }

    static Chronology getLenientISOChronology() {
        if (cLenientISO == null) {
            cLenientISO = LenientChronology.getInstance(ISOChronology.getInstanceUTC());
        }
        return cLenientISO;
    }

    static void writeZoneInfoMap(DataOutputStream dataOutputStream, Map<String, DateTimeZone> map) throws IOException {
        String string;
        HashMap<String, Short> hashMap = new HashMap<String, Short>(map.size());
        TreeMap<Short, String> treeMap = new TreeMap<Short, String>();
        short s2 = 0;
        for (Map.Entry<String, DateTimeZone> entry : map.entrySet()) {
            Short s3;
            string = entry.getKey();
            if (!hashMap.containsKey(string)) {
                s3 = s2;
                hashMap.put(string, s3);
                treeMap.put(s3, string);
                s2 = (short)(s2 + 1);
                if (s2 == 0) {
                    throw new InternalError("Too many time zone ids");
                }
            }
            if (hashMap.containsKey(string = entry.getValue().getID())) continue;
            s3 = s2;
            hashMap.put(string, s3);
            treeMap.put(s3, string);
            if ((s2 = (short)(s2 + 1)) != 0) continue;
            throw new InternalError("Too many time zone ids");
        }
        dataOutputStream.writeShort(treeMap.size());
        for (Map.Entry<String, DateTimeZone> entry : treeMap.values()) {
            dataOutputStream.writeUTF((String)((Object)entry));
        }
        dataOutputStream.writeShort(map.size());
        for (Map.Entry<String, DateTimeZone> entry : map.entrySet()) {
            string = entry.getKey();
            dataOutputStream.writeShort(((Short)hashMap.get(string)).shortValue());
            string = entry.getValue().getID();
            dataOutputStream.writeShort(((Short)hashMap.get(string)).shortValue());
        }
    }

    static int parseYear(String string, int n) {
        if ((string = string.toLowerCase()).equals("minimum") || string.equals("min")) {
            return Integer.MIN_VALUE;
        }
        if (string.equals("maximum") || string.equals("max")) {
            return Integer.MAX_VALUE;
        }
        if (string.equals("only")) {
            return n;
        }
        return Integer.parseInt(string);
    }

    static int parseMonth(String string) {
        DateTimeField dateTimeField = ISOChronology.getInstanceUTC().monthOfYear();
        return dateTimeField.get(dateTimeField.set(0L, string, Locale.ENGLISH));
    }

    static int parseDayOfWeek(String string) {
        DateTimeField dateTimeField = ISOChronology.getInstanceUTC().dayOfWeek();
        return dateTimeField.get(dateTimeField.set(0L, string, Locale.ENGLISH));
    }

    static String parseOptional(String string) {
        return string.equals("-") ? null : string;
    }

    static int parseTime(String string) {
        int n;
        DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.hourMinuteSecondFraction();
        MutableDateTime mutableDateTime = new MutableDateTime(0L, ZoneInfoCompiler.getLenientISOChronology());
        int n2 = 0;
        if (string.startsWith("-")) {
            n2 = 1;
        }
        if ((n = dateTimeFormatter.parseInto(mutableDateTime, string, n2)) == ~n2) {
            throw new IllegalArgumentException(string);
        }
        int n3 = (int)mutableDateTime.getMillis();
        if (n2 == 1) {
            n3 = -n3;
        }
        return n3;
    }

    static char parseZoneChar(char c) {
        switch (c) {
            case 'S': 
            case 's': {
                return 's';
            }
            case 'G': 
            case 'U': 
            case 'Z': 
            case 'g': 
            case 'u': 
            case 'z': {
                return 'u';
            }
        }
        return 'w';
    }

    static boolean test(String string, DateTimeZone dateTimeZone) {
        long l;
        long l2;
        if (!string.equals(dateTimeZone.getID())) {
            return true;
        }
        long l3 = ISOChronology.getInstanceUTC().year().set(0L, 1850);
        long l4 = ISOChronology.getInstanceUTC().year().set(0L, 2050);
        int n = dateTimeZone.getOffset(l3);
        String string2 = dateTimeZone.getNameKey(l3);
        ArrayList<Long> arrayList = new ArrayList<Long>();
        while ((l2 = dateTimeZone.nextTransition(l3)) != l3 && l2 <= l4) {
            l3 = l2;
            int n2 = dateTimeZone.getOffset(l3);
            String string3 = dateTimeZone.getNameKey(l3);
            if (n == n2 && string2.equals(string3)) {
                System.out.println("*d* Error in " + dateTimeZone.getID() + " " + new DateTime(l3, (Chronology)ISOChronology.getInstanceUTC()));
                return false;
            }
            if (string3 == null || string3.length() < 3 && !"??".equals(string3)) {
                System.out.println("*s* Error in " + dateTimeZone.getID() + " " + new DateTime(l3, (Chronology)ISOChronology.getInstanceUTC()) + ", nameKey=" + string3);
                return false;
            }
            arrayList.add(l3);
            n = n2;
            string2 = string3;
        }
        l3 = ISOChronology.getInstanceUTC().year().set(0L, 2050);
        l4 = ISOChronology.getInstanceUTC().year().set(0L, 1850);
        int n3 = arrayList.size();
        while (--n3 >= 0 && (l = dateTimeZone.previousTransition(l3)) != l3 && l >= l4) {
            l3 = l;
            long l5 = (Long)arrayList.get(n3);
            if (l5 - 1L == l3) continue;
            System.out.println("*r* Error in " + dateTimeZone.getID() + " " + new DateTime(l3, (Chronology)ISOChronology.getInstanceUTC()) + " != " + new DateTime(l5 - 1L, (Chronology)ISOChronology.getInstanceUTC()));
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Map<String, DateTimeZone> compile(File file, File[] fileArray) throws IOException {
        Object object;
        Object object2;
        Object object3;
        Object object4;
        int n;
        Object object5;
        if (fileArray != null) {
            for (int i = 0; i < fileArray.length; ++i) {
                object5 = new BufferedReader(new FileReader(fileArray[i]));
                this.parseDataFile((BufferedReader)object5, "backward".equals(fileArray[i].getName()));
                ((BufferedReader)object5).close();
            }
        }
        if (file != null) {
            if (!file.exists() && !file.mkdirs()) {
                throw new IOException("Destination directory doesn't exist and cannot be created: " + file);
            }
            if (!file.isDirectory()) {
                throw new IOException("Destination is not a directory: " + file);
            }
        }
        TreeMap<String, DateTimeZone> treeMap = new TreeMap<String, DateTimeZone>();
        object5 = new TreeMap();
        System.out.println("Writing zoneinfo files");
        for (n = 0; n < this.iZones.size(); ++n) {
            object4 = this.iZones.get(n);
            object3 = new DateTimeZoneBuilder();
            ((Zone)object4).addToBuilder((DateTimeZoneBuilder)object3, this.iRuleSets);
            object2 = ((DateTimeZoneBuilder)object3).toDateTimeZone(((Zone)object4).iName, true);
            if (!ZoneInfoCompiler.test(((DateTimeZone)object2).getID(), (DateTimeZone)object2)) continue;
            treeMap.put(((DateTimeZone)object2).getID(), (DateTimeZone)object2);
            object5.put(((DateTimeZone)object2).getID(), object4);
            if (file == null) continue;
            this.writeZone(file, (DateTimeZoneBuilder)object3, (DateTimeZone)object2);
        }
        for (n = 0; n < this.iGoodLinks.size(); n += 2) {
            object4 = this.iGoodLinks.get(n);
            object3 = this.iGoodLinks.get(n + 1);
            object2 = (Zone)object5.get(object4);
            if (object2 == null) {
                System.out.println("Cannot find source zone '" + (String)object4 + "' to link alias '" + (String)object3 + "' to");
                continue;
            }
            object = new DateTimeZoneBuilder();
            ((Zone)object2).addToBuilder((DateTimeZoneBuilder)object, this.iRuleSets);
            DateTimeZone dateTimeZone = ((DateTimeZoneBuilder)object).toDateTimeZone((String)object3, true);
            if (ZoneInfoCompiler.test(dateTimeZone.getID(), dateTimeZone)) {
                treeMap.put(dateTimeZone.getID(), dateTimeZone);
                if (file != null) {
                    this.writeZone(file, (DateTimeZoneBuilder)object, dateTimeZone);
                }
            }
            treeMap.put(dateTimeZone.getID(), dateTimeZone);
            if (!ZoneInfoLogger.verbose()) continue;
            System.out.println("Good link: " + (String)object3 + " -> " + (String)object4 + " revived");
        }
        for (n = 0; n < 2; ++n) {
            for (int i = 0; i < this.iBackLinks.size(); i += 2) {
                object3 = this.iBackLinks.get(i);
                object2 = this.iBackLinks.get(i + 1);
                object = (DateTimeZone)treeMap.get(object3);
                if (object == null) {
                    if (n <= 0) continue;
                    System.out.println("Cannot find time zone '" + (String)object3 + "' to link alias '" + object2 + "' to");
                    continue;
                }
                treeMap.put((String)object2, (DateTimeZone)object);
                if (!ZoneInfoLogger.verbose()) continue;
                System.out.println("Back link: " + object2 + " -> " + ((DateTimeZone)object).getID());
            }
        }
        if (file != null) {
            System.out.println("Writing ZoneInfoMap");
            File file2 = new File(file, "ZoneInfoMap");
            if (!file2.getParentFile().exists()) {
                file2.getParentFile().mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            object3 = new DataOutputStream(fileOutputStream);
            try {
                object2 = new TreeMap<String, DateTimeZone>(String.CASE_INSENSITIVE_ORDER);
                object2.putAll(treeMap);
                ZoneInfoCompiler.writeZoneInfoMap((DataOutputStream)object3, object2);
            }
            finally {
                ((FilterOutputStream)object3).close();
            }
        }
        return treeMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void writeZone(File file, DateTimeZoneBuilder dateTimeZoneBuilder, DateTimeZone dateTimeZone) throws IOException {
        File file2;
        if (ZoneInfoLogger.verbose()) {
            System.out.println("Writing " + dateTimeZone.getID());
        }
        if (!(file2 = new File(file, dateTimeZone.getID())).getParentFile().exists()) {
            file2.getParentFile().mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        try {
            dateTimeZoneBuilder.writeTo(dateTimeZone.getID(), fileOutputStream);
        }
        finally {
            ((OutputStream)fileOutputStream).close();
        }
        FileInputStream fileInputStream = new FileInputStream(file2);
        DateTimeZone dateTimeZone2 = DateTimeZoneBuilder.readFrom(fileInputStream, dateTimeZone.getID());
        ((InputStream)fileInputStream).close();
        if (!dateTimeZone.equals(dateTimeZone2)) {
            System.out.println("*e* Error in " + dateTimeZone.getID() + ": Didn't read properly from file");
        }
    }

    public void parseDataFile(BufferedReader bufferedReader, boolean bl) throws IOException {
        String string;
        Zone zone = null;
        while ((string = bufferedReader.readLine()) != null) {
            Object object;
            Object object2;
            String string2 = string.trim();
            if (string2.length() == 0 || string2.charAt(0) == '#') continue;
            int n = string.indexOf(35);
            if (n >= 0) {
                string = string.substring(0, n);
            }
            StringTokenizer stringTokenizer = new StringTokenizer(string, " \t");
            if (Character.isWhitespace(string.charAt(0)) && stringTokenizer.hasMoreTokens()) {
                if (zone == null) continue;
                zone.chain(stringTokenizer);
                continue;
            }
            if (zone != null) {
                this.iZones.add(zone);
            }
            zone = null;
            if (!stringTokenizer.hasMoreTokens()) continue;
            String string3 = stringTokenizer.nextToken();
            if (string3.equalsIgnoreCase("Rule")) {
                object2 = new Rule(stringTokenizer);
                object = this.iRuleSets.get(((Rule)object2).iName);
                if (object == null) {
                    object = new RuleSet((Rule)object2);
                    this.iRuleSets.put(((Rule)object2).iName, (RuleSet)object);
                    continue;
                }
                ((RuleSet)object).addRule((Rule)object2);
                continue;
            }
            if (string3.equalsIgnoreCase("Zone")) {
                zone = new Zone(stringTokenizer);
                continue;
            }
            if (string3.equalsIgnoreCase("Link")) {
                object2 = stringTokenizer.nextToken();
                object = stringTokenizer.nextToken();
                if (bl || ((String)object).equals("US/Pacific-New") || ((String)object).startsWith("Etc/") || ((String)object).equals("GMT")) {
                    this.iBackLinks.add((String)object2);
                    this.iBackLinks.add((String)object);
                    continue;
                }
                this.iGoodLinks.add((String)object2);
                this.iGoodLinks.add((String)object);
                continue;
            }
            System.out.println("Unknown line: " + string);
        }
        if (zone != null) {
            this.iZones.add(zone);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class Zone {
        public final String iName;
        public final int iOffsetMillis;
        public final String iRules;
        public final String iFormat;
        public final int iUntilYear;
        public final DateTimeOfYear iUntilDateTimeOfYear;
        private Zone iNext;

        Zone(StringTokenizer stringTokenizer) {
            this(stringTokenizer.nextToken(), stringTokenizer);
        }

        private Zone(String string, StringTokenizer stringTokenizer) {
            this.iName = string.intern();
            this.iOffsetMillis = ZoneInfoCompiler.parseTime(stringTokenizer.nextToken());
            this.iRules = ZoneInfoCompiler.parseOptional(stringTokenizer.nextToken());
            this.iFormat = stringTokenizer.nextToken().intern();
            int n = Integer.MAX_VALUE;
            DateTimeOfYear dateTimeOfYear = ZoneInfoCompiler.getStartOfYear();
            if (stringTokenizer.hasMoreTokens()) {
                n = Integer.parseInt(stringTokenizer.nextToken());
                if (stringTokenizer.hasMoreTokens()) {
                    dateTimeOfYear = new DateTimeOfYear(stringTokenizer);
                }
            }
            this.iUntilYear = n;
            this.iUntilDateTimeOfYear = dateTimeOfYear;
        }

        void chain(StringTokenizer stringTokenizer) {
            if (this.iNext != null) {
                this.iNext.chain(stringTokenizer);
            } else {
                this.iNext = new Zone(this.iName, stringTokenizer);
            }
        }

        public void addToBuilder(DateTimeZoneBuilder dateTimeZoneBuilder, Map<String, RuleSet> map) {
            Zone.addToBuilder(this, dateTimeZoneBuilder, map);
        }

        private static void addToBuilder(Zone zone, DateTimeZoneBuilder dateTimeZoneBuilder, Map<String, RuleSet> map) {
            while (zone != null) {
                dateTimeZoneBuilder.setStandardOffset(zone.iOffsetMillis);
                if (zone.iRules == null) {
                    dateTimeZoneBuilder.setFixedSavings(zone.iFormat, 0);
                } else {
                    try {
                        int n = ZoneInfoCompiler.parseTime(zone.iRules);
                        dateTimeZoneBuilder.setFixedSavings(zone.iFormat, n);
                    }
                    catch (Exception exception) {
                        RuleSet ruleSet = map.get(zone.iRules);
                        if (ruleSet == null) {
                            throw new IllegalArgumentException("Rules not found: " + zone.iRules);
                        }
                        ruleSet.addRecurring(dateTimeZoneBuilder, zone.iFormat);
                    }
                }
                if (zone.iUntilYear == Integer.MAX_VALUE) break;
                zone.iUntilDateTimeOfYear.addCutover(dateTimeZoneBuilder, zone.iUntilYear);
                zone = zone.iNext;
            }
        }

        public String toString() {
            String string = "[Zone]\nName: " + this.iName + "\n" + "OffsetMillis: " + this.iOffsetMillis + "\n" + "Rules: " + this.iRules + "\n" + "Format: " + this.iFormat + "\n" + "UntilYear: " + this.iUntilYear + "\n" + this.iUntilDateTimeOfYear;
            if (this.iNext == null) {
                return string;
            }
            return string + "...\n" + this.iNext.toString();
        }
    }

    private static class RuleSet {
        private List<Rule> iRules = new ArrayList<Rule>();

        RuleSet(Rule rule) {
            this.iRules.add(rule);
        }

        void addRule(Rule rule) {
            if (!rule.iName.equals(this.iRules.get((int)0).iName)) {
                throw new IllegalArgumentException("Rule name mismatch");
            }
            this.iRules.add(rule);
        }

        public void addRecurring(DateTimeZoneBuilder dateTimeZoneBuilder, String string) {
            for (int i = 0; i < this.iRules.size(); ++i) {
                Rule rule = this.iRules.get(i);
                rule.addRecurring(dateTimeZoneBuilder, string);
            }
        }
    }

    private static class Rule {
        public final String iName;
        public final int iFromYear;
        public final int iToYear;
        public final String iType;
        public final DateTimeOfYear iDateTimeOfYear;
        public final int iSaveMillis;
        public final String iLetterS;

        Rule(StringTokenizer stringTokenizer) {
            if (stringTokenizer.countTokens() < 6) {
                throw new IllegalArgumentException("Attempting to create a Rule from an incomplete tokenizer");
            }
            this.iName = stringTokenizer.nextToken().intern();
            this.iFromYear = ZoneInfoCompiler.parseYear(stringTokenizer.nextToken(), 0);
            this.iToYear = ZoneInfoCompiler.parseYear(stringTokenizer.nextToken(), this.iFromYear);
            if (this.iToYear < this.iFromYear) {
                throw new IllegalArgumentException();
            }
            this.iType = ZoneInfoCompiler.parseOptional(stringTokenizer.nextToken());
            this.iDateTimeOfYear = new DateTimeOfYear(stringTokenizer);
            this.iSaveMillis = ZoneInfoCompiler.parseTime(stringTokenizer.nextToken());
            this.iLetterS = ZoneInfoCompiler.parseOptional(stringTokenizer.nextToken());
        }

        public void addRecurring(DateTimeZoneBuilder dateTimeZoneBuilder, String string) {
            String string2 = this.formatName(string);
            this.iDateTimeOfYear.addRecurring(dateTimeZoneBuilder, string2, this.iSaveMillis, this.iFromYear, this.iToYear);
        }

        private String formatName(String string) {
            int n = string.indexOf(47);
            if (n > 0) {
                if (this.iSaveMillis == 0) {
                    return string.substring(0, n).intern();
                }
                return string.substring(n + 1).intern();
            }
            n = string.indexOf("%s");
            if (n < 0) {
                return string;
            }
            String string2 = string.substring(0, n);
            String string3 = string.substring(n + 2);
            String string4 = this.iLetterS == null ? string2.concat(string3) : string2 + this.iLetterS + string3;
            return string4.intern();
        }

        public String toString() {
            return "[Rule]\nName: " + this.iName + "\n" + "FromYear: " + this.iFromYear + "\n" + "ToYear: " + this.iToYear + "\n" + "Type: " + this.iType + "\n" + this.iDateTimeOfYear + "SaveMillis: " + this.iSaveMillis + "\n" + "LetterS: " + this.iLetterS + "\n";
        }
    }

    static class DateTimeOfYear {
        public final int iMonthOfYear;
        public final int iDayOfMonth;
        public final int iDayOfWeek;
        public final boolean iAdvanceDayOfWeek;
        public final int iMillisOfDay;
        public final char iZoneChar;

        DateTimeOfYear() {
            this.iMonthOfYear = 1;
            this.iDayOfMonth = 1;
            this.iDayOfWeek = 0;
            this.iAdvanceDayOfWeek = false;
            this.iMillisOfDay = 0;
            this.iZoneChar = (char)119;
        }

        DateTimeOfYear(StringTokenizer stringTokenizer) {
            int n = 1;
            int n2 = 1;
            int n3 = 0;
            int n4 = 0;
            boolean bl = false;
            int n5 = 119;
            if (stringTokenizer.hasMoreTokens()) {
                n = ZoneInfoCompiler.parseMonth(stringTokenizer.nextToken());
                if (stringTokenizer.hasMoreTokens()) {
                    String string = stringTokenizer.nextToken();
                    if (string.startsWith("last")) {
                        n2 = -1;
                        n3 = ZoneInfoCompiler.parseDayOfWeek(string.substring(4));
                        bl = false;
                    } else {
                        try {
                            n2 = Integer.parseInt(string);
                            n3 = 0;
                            bl = false;
                        }
                        catch (NumberFormatException numberFormatException) {
                            int n6 = string.indexOf(">=");
                            if (n6 > 0) {
                                n2 = Integer.parseInt(string.substring(n6 + 2));
                                n3 = ZoneInfoCompiler.parseDayOfWeek(string.substring(0, n6));
                                bl = true;
                            }
                            n6 = string.indexOf("<=");
                            if (n6 > 0) {
                                n2 = Integer.parseInt(string.substring(n6 + 2));
                                n3 = ZoneInfoCompiler.parseDayOfWeek(string.substring(0, n6));
                                bl = false;
                            }
                            throw new IllegalArgumentException(string);
                        }
                    }
                    if (stringTokenizer.hasMoreTokens()) {
                        string = stringTokenizer.nextToken();
                        n5 = ZoneInfoCompiler.parseZoneChar(string.charAt(string.length() - 1));
                        if (string.equals("24:00")) {
                            if (n == 12 && n2 == 31) {
                                n4 = ZoneInfoCompiler.parseTime("23:59:59.999");
                            } else {
                                LocalDate localDate = n2 == -1 ? new LocalDate(2001, n, 1).plusMonths(1) : new LocalDate(2001, n, n2).plusDays(1);
                                bl = n2 != -1 && n3 != 0;
                                n = localDate.getMonthOfYear();
                                n2 = localDate.getDayOfMonth();
                                if (n3 != 0) {
                                    n3 = (n3 - 1 + 1) % 7 + 1;
                                }
                            }
                        } else {
                            n4 = ZoneInfoCompiler.parseTime(string);
                        }
                    }
                }
            }
            this.iMonthOfYear = n;
            this.iDayOfMonth = n2;
            this.iDayOfWeek = n3;
            this.iAdvanceDayOfWeek = bl;
            this.iMillisOfDay = n4;
            this.iZoneChar = (char)n5;
        }

        public void addRecurring(DateTimeZoneBuilder dateTimeZoneBuilder, String string, int n, int n2, int n3) {
            dateTimeZoneBuilder.addRecurringSavings(string, n, n2, n3, this.iZoneChar, this.iMonthOfYear, this.iDayOfMonth, this.iDayOfWeek, this.iAdvanceDayOfWeek, this.iMillisOfDay);
        }

        public void addCutover(DateTimeZoneBuilder dateTimeZoneBuilder, int n) {
            dateTimeZoneBuilder.addCutover(n, this.iZoneChar, this.iMonthOfYear, this.iDayOfMonth, this.iDayOfWeek, this.iAdvanceDayOfWeek, this.iMillisOfDay);
        }

        public String toString() {
            return "MonthOfYear: " + this.iMonthOfYear + "\n" + "DayOfMonth: " + this.iDayOfMonth + "\n" + "DayOfWeek: " + this.iDayOfWeek + "\n" + "AdvanceDayOfWeek: " + this.iAdvanceDayOfWeek + "\n" + "MillisOfDay: " + this.iMillisOfDay + "\n" + "ZoneChar: " + this.iZoneChar + "\n";
        }
    }
}

