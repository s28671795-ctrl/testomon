/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import liquibase.util.StringUtil;
import liquibase.util.Validate;

public class StringClauses {
    private final String separator;
    private final String start;
    private final String end;
    private final Random random = new SecureRandom();
    private LinkedHashMap<String, Object> clauses = new LinkedHashMap();

    public StringClauses() {
        this(null);
    }

    public StringClauses(String separator) {
        this(null, separator, null);
    }

    public StringClauses(String start2, String separator, String end) {
        if (start2 == null) {
            start2 = "";
        }
        if (separator == null) {
            separator = " ";
        }
        if (end == null) {
            end = "";
        }
        this.start = start2;
        this.end = end;
        this.separator = separator;
    }

    private String uniqueKey(String key) {
        boolean generateOne = false;
        if ((key = StringUtil.trimToNull(key)) == null) {
            generateOne = true;
        } else if (this.clauses.containsKey(key.toLowerCase())) {
            generateOne = true;
        }
        while (generateOne) {
            key = StringUtil.leftPad(new BigInteger(50, this.random).toString(32), 6).replace(" ", "0").substring(0, 6);
            generateOne = this.clauses.containsKey(key);
        }
        return key;
    }

    public StringClauses append(String key, String clause) {
        Validate.notNull(StringUtil.trimToNull(key), "key must be a non-null, non-empty value");
        key = StringUtil.trimToEmpty(key).toLowerCase();
        clause = StringUtil.trimToEmpty(clause);
        if (this.clauses.containsKey(key)) {
            throw new IllegalArgumentException("Cannot add clause with key '" + key + "' because it is already defined");
        }
        this.clauses.put(key, clause.trim());
        return this;
    }

    public StringClauses append(String key, StringClauses subclauses) {
        Validate.notNull(StringUtil.trimToNull(key), "key must be a non-null, non-empty value");
        key = StringUtil.trimToEmpty(key).toLowerCase();
        if (this.clauses.containsKey(key)) {
            throw new IllegalArgumentException("Cannot add clause with key '" + key + "' because it is already defined");
        }
        this.clauses.put(key, subclauses);
        return this;
    }

    public StringClauses append(String clause) {
        return this.append(this.uniqueKey(clause), clause);
    }

    public StringClauses append(Enum name, StringClauses subclauses) {
        return this.append(name.name(), subclauses);
    }

    public StringClauses append(Enum name, String clause) {
        return this.append(name.name(), clause);
    }

    public StringClauses append(LiteralClause literal) {
        if (literal != null) {
            this.clauses.put(literal.getClass().getName().toLowerCase() + " #" + this.clauses.size(), literal);
        }
        return this;
    }

    public StringClauses prepend(LiteralClause literal) {
        if (literal != null) {
            LinkedHashMap<String, Object> newMap = new LinkedHashMap<String, Object>();
            newMap.put(this.uniqueKey(literal.getClass().getName().toLowerCase()), literal);
            newMap.putAll(this.clauses);
            this.clauses = newMap;
        }
        return this;
    }

    public StringClauses prepend(String key, String clause) {
        return this.prependImpl(key, StringUtil.trimToEmpty(clause));
    }

    public StringClauses prepend(String key, StringClauses clause) {
        return this.prependImpl(key, clause);
    }

    public StringClauses prepend(String clause) {
        if (StringUtil.trimToNull(clause) == null) {
            return this.prepend(new Whitespace(clause));
        }
        return this.prepend(this.uniqueKey(clause), clause);
    }

    public StringClauses prepend(Enum key, String clause) {
        return this.prepend(key.name(), clause);
    }

    public StringClauses prepend(Enum key, StringClauses clause) {
        return this.prepend(key.name(), clause);
    }

    private StringClauses prependImpl(String key, Object clause) throws IllegalArgumentException {
        Validate.notNull(StringUtil.trimToNull(key), "key must be a non-null, non-empty value");
        key = StringUtil.trimToEmpty(key).toLowerCase();
        if (this.clauses.containsKey(key)) {
            throw new IllegalArgumentException("Cannot add clause with key '" + key + "' because it is already defined");
        }
        LinkedHashMap<String, Object> newMap = new LinkedHashMap<String, Object>();
        newMap.put(key, clause);
        newMap.putAll(this.clauses);
        this.clauses = newMap;
        return this;
    }

    public StringClauses remove(String key) {
        this.clauses.remove(key.toLowerCase());
        return this;
    }

    public StringClauses remove(Enum key) {
        return this.remove(key.name());
    }

    public StringClauses replace(String key, String newValue) throws IllegalArgumentException {
        return this.replaceImpl(key, StringUtil.trimToEmpty(newValue));
    }

    public boolean contains(String key) {
        return this.clauses.containsKey(key.toLowerCase());
    }

    public StringClauses replace(String key, StringClauses newValue) {
        return this.replaceImpl(key, newValue);
    }

    public StringClauses replace(Enum key, StringClauses newValue) {
        return this.replace(key.name(), newValue);
    }

    public StringClauses replace(Enum key, String newValue) {
        return this.replace(key.name(), newValue);
    }

    private StringClauses replaceImpl(String key, Object newValue) {
        if (!this.clauses.containsKey(key = StringUtil.trimToEmpty(key).toLowerCase())) {
            throw new IllegalArgumentException("Key '" + key + "' is not defined");
        }
        LinkedHashMap<String, Object> newMap = new LinkedHashMap<String, Object>();
        for (Map.Entry<String, Object> entry : this.clauses.entrySet()) {
            if (entry.getKey().equals(key)) {
                newMap.put(key, newValue);
                continue;
            }
            newMap.put(entry.getKey(), entry.getValue());
        }
        this.clauses = newMap;
        return this;
    }

    public StringClauses insertBefore(String existingKey, String newKey, String newValue) throws IllegalArgumentException {
        return this.insertBeforeImpl(existingKey, newKey, newValue);
    }

    public StringClauses insertBefore(String existingKey, String newKey, StringClauses newValue) {
        return this.insertBeforeImpl(existingKey, newKey, newValue);
    }

    public StringClauses insertBefore(String existingKey, String newValue) {
        return this.insertBefore(existingKey, newValue, StringUtil.trimToNull(newValue));
    }

    public StringClauses insertBefore(Enum existingKey, String newValue) {
        return this.insertBefore(existingKey.name(), newValue);
    }

    public StringClauses insertBefore(Enum existingKey, Enum newKey, String newValue) {
        return this.insertBefore(existingKey.name(), newKey.name(), newValue);
    }

    private StringClauses insertBeforeImpl(String existingKey, String newKey, Object newValue) {
        Validate.notNull(StringUtil.trimToNull(newKey), "key must be a non-null, non-empty value");
        existingKey = StringUtil.trimToEmpty(existingKey).toLowerCase();
        newKey = StringUtil.trimToEmpty(newKey).toLowerCase();
        if (!this.clauses.containsKey(existingKey)) {
            throw new IllegalArgumentException("Existing key '" + existingKey + "' does not exist");
        }
        if ("".equals(newKey)) {
            throw new IllegalArgumentException("New key cannot be null or empty");
        }
        if (this.clauses.containsKey(newKey)) {
            throw new IllegalArgumentException("Cannot add clause with key '" + newKey + "' because it is already defined");
        }
        LinkedHashMap<String, Object> newMap = new LinkedHashMap<String, Object>();
        for (Map.Entry<String, Object> entry : this.clauses.entrySet()) {
            if (entry.getKey().equals(existingKey)) {
                newMap.put(newKey, newValue);
            }
            newMap.put(entry.getKey(), entry.getValue());
        }
        this.clauses = newMap;
        return this;
    }

    public StringClauses insertAfter(String existingKey, String newKey, String newValue) {
        return this.insertAfterImpl(existingKey, newKey, newValue);
    }

    public StringClauses insertAfter(String existingKey, String newKey, StringClauses newValue) {
        return this.insertAfterImpl(existingKey, newKey, newValue);
    }

    public StringClauses insertAfter(Enum existingKey, String newValue) {
        return this.insertAfter(existingKey.name(), newValue);
    }

    public StringClauses insertAfter(String existingKey, String newValue) {
        return this.insertAfter(existingKey, newValue, StringUtil.trimToEmpty(newValue));
    }

    private StringClauses insertAfterImpl(String existingKey, String newKey, Object newValue) {
        Validate.notNull(StringUtil.trimToNull(existingKey), "key must be a non-null, non-empty value");
        existingKey = StringUtil.trimToEmpty(existingKey).toLowerCase();
        newKey = StringUtil.trimToEmpty(newKey).toLowerCase();
        if (!this.clauses.containsKey(existingKey)) {
            throw new IllegalArgumentException("Existing key '" + existingKey + "' does not exist");
        }
        if (this.clauses.containsKey(newKey)) {
            throw new IllegalArgumentException("Cannot add clause with key '" + newKey + "' because it is already defined");
        }
        LinkedHashMap<String, Object> newMap = new LinkedHashMap<String, Object>();
        for (Map.Entry<String, Object> entry : this.clauses.entrySet()) {
            newMap.put(entry.getKey(), entry.getValue());
            if (!entry.getKey().equals(existingKey)) continue;
            newMap.put(newKey, newValue);
        }
        this.clauses = newMap;
        return this;
    }

    public String get(String exitingKey) {
        Object clauses = this.getImpl(exitingKey = StringUtil.trimToEmpty(exitingKey).toLowerCase());
        if (clauses == null) {
            return null;
        }
        return clauses.toString();
    }

    private Object getImpl(String exitingKey) {
        Object o = this.clauses.get(exitingKey);
        if (o == null) {
            for (Object obj : this.clauses.values()) {
                Object childObj;
                if (!(obj instanceof StringClauses) || (childObj = ((StringClauses)obj).getImpl(exitingKey)) == null) continue;
                return childObj;
            }
        }
        return o;
    }

    public String get(Enum exitingKey) {
        return this.get(exitingKey.name());
    }

    public StringClauses getSubclause(String exitingKey) {
        Object clauses = this.getImpl(exitingKey = StringUtil.trimToEmpty(exitingKey).toLowerCase());
        if (clauses == null) {
            return null;
        }
        if (clauses instanceof String) {
            return new StringClauses().append((String)clauses);
        }
        return (StringClauses)clauses;
    }

    public StringClauses getSubclause(Enum exitingKey) {
        return this.getSubclause(exitingKey.name());
    }

    public ClauseIterator getClauseIterator() {
        return new ClauseIterator(this.clauses);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        StringClauses that = (StringClauses)o;
        return Objects.equals(this.separator, that.separator) && Objects.equals(this.start, that.start) && Objects.equals(this.end, that.end) && Objects.equals(this.clauses, that.clauses);
    }

    public int hashCode() {
        return Objects.hash(this.separator, this.start, this.end, this.clauses);
    }

    public String toString() {
        if (this.clauses.isEmpty()) {
            return "";
        }
        ArrayList<Object> finalList = new ArrayList<Object>(this.clauses.values());
        finalList.removeIf(next -> next == null || "".equals(next.toString()));
        return this.start + StringUtil.join(finalList, this.separator, (StringUtil.StringUtilFormatter)new StringUtil.ToStringFormatter()) + this.end;
    }

    public Object[] toArray(boolean stringify) {
        Object[] returnArray = new Object[this.clauses.size()];
        ArrayList<Object> currentValues = new ArrayList<Object>(this.clauses.values());
        for (int i = 0; i < currentValues.size(); ++i) {
            returnArray[i] = stringify ? currentValues.get(i).toString() : currentValues.get(i);
        }
        return returnArray;
    }

    public boolean isEmpty() {
        if (this.clauses.isEmpty()) {
            return true;
        }
        for (Object clause : this.clauses.values()) {
            if ("".equals(clause.toString().trim())) continue;
            return false;
        }
        return true;
    }

    public static class Whitespace
    implements LiteralClause {
        private final String value;

        public Whitespace(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    public static interface LiteralClause {
        public String toString();
    }

    public static class ClauseIterator
    implements Iterator {
        private final LinkedHashMap<String, Object> clauses;
        private final ListIterator<String> keyIterator;

        public ClauseIterator(LinkedHashMap<String, Object> clauses) {
            this.keyIterator = new ArrayList<String>(clauses.keySet()).listIterator();
            this.clauses = clauses;
        }

        @Override
        public boolean hasNext() {
            return this.keyIterator.hasNext();
        }

        public Object next() {
            return this.clauses.get(this.keyIterator.next());
        }

        public Object nextNonWhitespace() {
            while (this.hasNext()) {
                Object next = this.clauses.get(this.keyIterator.next());
                if (next instanceof Whitespace || next instanceof Comment) continue;
                return next;
            }
            return null;
        }

        @Override
        public void remove() {
            this.clauses.remove(this.keyIterator.previous());
            this.keyIterator.remove();
        }

        public void replace(Object newClause) {
            this.keyIterator.previous();
            String keyToReplace = this.keyIterator.next();
            this.clauses.put(keyToReplace, newClause);
        }
    }

    public static class Comment
    implements LiteralClause {
        private final String value;

        public Comment(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    public static class Delimiter
    implements LiteralClause {
        private final String value;

        public Delimiter(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }
}

