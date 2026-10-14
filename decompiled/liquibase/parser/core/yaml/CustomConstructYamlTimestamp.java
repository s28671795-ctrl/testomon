/*
 * Decompiled with CFR 0.152.
 */
package liquibase.parser.core.yaml;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class CustomConstructYamlTimestamp
extends SafeConstructor.ConstructYamlTimestamp {
    private static final Pattern TIMESTAMP_REGEXP = Pattern.compile("^([0-9][0-9][0-9][0-9])-([0-9][0-9]?)-([0-9][0-9]?)(?:(?:[Tt]|[ \t]+)([0-9][0-9]?):([0-9][0-9]):([0-9][0-9])(?:\\.([0-9]*))?(?:[ \t]*(?:Z|([-+][0-9][0-9]?)(?::([0-9][0-9])?)?))?)?$");
    private static final Pattern YMD_REGEXP = Pattern.compile("^([0-9][0-9][0-9][0-9])-([0-9][0-9]?)-([0-9][0-9]?)$");
    private Calendar calendar;

    @Override
    public Object construct(Node node) {
        ScalarNode scalar = (ScalarNode)node;
        String nodeValue = scalar.getValue();
        Matcher match = YMD_REGEXP.matcher(nodeValue);
        if (match.matches()) {
            String year_s = match.group(1);
            String month_s = match.group(2);
            String day_s = match.group(3);
            this.calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            this.calendar.clear();
            this.calendar.set(1, Integer.parseInt(year_s));
            this.calendar.set(2, Integer.parseInt(month_s) - 1);
            this.calendar.set(5, Integer.parseInt(day_s));
            return this.calendar.getTime();
        }
        match = TIMESTAMP_REGEXP.matcher(nodeValue);
        if (!match.matches()) {
            throw new YAMLException("Unexpected timestamp: " + nodeValue);
        }
        String year_s = match.group(1);
        String month_s = match.group(2);
        String day_s = match.group(3);
        String hour_s = match.group(4);
        String min_s = match.group(5);
        String seconds = match.group(6);
        String millis = match.group(7);
        if (millis != null) {
            seconds = seconds + "." + millis;
        }
        double fractions = Double.parseDouble(seconds);
        int sec_s = (int)Math.round(Math.floor(fractions));
        int usec = (int)Math.round((fractions - (double)sec_s) * 1000.0);
        String timezoneh_s = match.group(8);
        String timezonem_s = match.group(9);
        TimeZone timeZone = null;
        if (timezoneh_s != null) {
            String time = timezonem_s != null ? ":" + timezonem_s : "00";
            timeZone = TimeZone.getTimeZone("GMT" + timezoneh_s + time);
            this.calendar = Calendar.getInstance(timeZone);
        } else {
            this.calendar = Calendar.getInstance();
        }
        this.calendar.set(1, Integer.parseInt(year_s));
        this.calendar.set(2, Integer.parseInt(month_s) - 1);
        this.calendar.set(5, Integer.parseInt(day_s));
        this.calendar.set(11, Integer.parseInt(hour_s));
        this.calendar.set(12, Integer.parseInt(min_s));
        this.calendar.set(13, sec_s);
        this.calendar.set(14, usec);
        if (timeZone == null) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.calendar.getTime());
        }
        return this.calendar.getTime().toString();
    }
}

