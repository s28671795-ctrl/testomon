/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.jvm;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public abstract class ConnectionPatterns {
    private final Set<Map.Entry<Pattern, Pattern>> patternJdbcBlank = new HashSet<Map.Entry<Pattern, Pattern>>();
    private final Set<Map.Entry<Pattern, Pattern>> patternJdbcBlankToObfuscate = new HashSet<Map.Entry<Pattern, Pattern>>();
    private final Set<Map.Entry<Pattern, Pattern>> patternJdbcObfuscate = new HashSet<Map.Entry<Pattern, Pattern>>();

    public Set<Map.Entry<Pattern, Pattern>> getJdbcBlankPatterns() {
        return this.patternJdbcBlank;
    }

    public Set<Map.Entry<Pattern, Pattern>> getJdbcBlankToObfuscatePatterns() {
        return this.patternJdbcBlankToObfuscate;
    }

    public Set<Map.Entry<Pattern, Pattern>> getJdbcObfuscatePatterns() {
        return this.patternJdbcObfuscate;
    }

    public void addJdbcBlankPatterns(Map.Entry<Pattern, Pattern> jdbcBlankPatterns) {
        this.patternJdbcBlank.add(jdbcBlankPatterns);
    }

    public void addJdbcBlankToObfuscatePatterns(Map.Entry<Pattern, Pattern> patternJdbcBlankToObfuscate) {
        this.patternJdbcBlankToObfuscate.add(patternJdbcBlankToObfuscate);
    }

    public void addJdbcObfuscatePatterns(Map.Entry<Pattern, Pattern> jdbcObfuscatePatterns) {
        this.patternJdbcObfuscate.add(jdbcObfuscatePatterns);
    }

    public static class PatternPair {
        private PatternPair() {
        }

        public static <T, U> Map.Entry<T, U> of(T first, U second) {
            return new AbstractMap.SimpleEntry<T, U>(first, second);
        }
    }
}

