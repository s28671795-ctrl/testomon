/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog;

import java.util.Arrays;
import liquibase.change.Change;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.output.changelog.ChangeGenerator;
import liquibase.diff.output.changelog.UnexpectedObjectChangeGenerator;
import liquibase.util.ObjectUtil;

public abstract class AbstractChangeGenerator
implements ChangeGenerator {
    private boolean respectSchemaAndCatalogCase = false;

    @Override
    public Change[] fixSchema(Change[] changes, CompareControl.SchemaComparison[] schemaComparisons) {
        if (changes == null || this instanceof UnexpectedObjectChangeGenerator) {
            return changes;
        }
        for (Change change : changes) {
            for (String field2 : change.getSerializableFields()) {
                String newValue;
                Object value;
                if (!field2.toLowerCase().contains("schemaname") && !field2.toLowerCase().contains("catalogname") || !((value = change.getSerializableFieldValue(field2)) instanceof String) || (newValue = CompareControl.SchemaComparison.convertSchema((String)value, schemaComparisons)) == null || newValue.equalsIgnoreCase((String)value)) continue;
                ObjectUtil.setProperty((Object)change, field2, newValue);
            }
        }
        return changes;
    }

    @Override
    public Change[] fixOutputAsSchema(Change[] changes, CompareControl.SchemaComparison[] schemaComparisons) {
        if (changes == null) {
            return null;
        }
        for (Change change : changes) {
            for (String field2 : change.getSerializableFields()) {
                if (!field2.toLowerCase().contains("schemaname") && !field2.toLowerCase().contains("catalogname")) continue;
                Object value = change.getSerializableFieldValue(field2);
                if (schemaComparisons == null || !(value instanceof String)) continue;
                for (CompareControl.SchemaComparison comparison : schemaComparisons) {
                    if (!this.respectSchemaAndCatalogCase) {
                        this.setPropertyIgnoreSchemaAndCatalogCase(change, field2, (String)value, comparison);
                        continue;
                    }
                    this.setProperty(change, field2, (String)value, comparison);
                }
            }
        }
        return changes;
    }

    private String valueToSet(String field2, String value) {
        if (!field2.toLowerCase().contains("schemaname") && !field2.toLowerCase().contains("catalogname")) {
            return value;
        }
        String doctored = this.doctor(value);
        if (!doctored.contains(".")) {
            return value;
        }
        String[] parts = doctored.split("\\.");
        String valueToSet = field2.toLowerCase().contains("schemaname") ? parts[1].replaceAll("#\\|#", ".") : parts[0].replaceAll("#\\|#", ".");
        return valueToSet;
    }

    private String doctor(String stringToDoctor) {
        boolean startProp = false;
        int pos = 0;
        char[] chars = stringToDoctor.toCharArray();
        char[] output = new char[chars.length];
        for (int i = 0; i < chars.length; ++i) {
            if (chars[i] == '$' && chars[i + 1] == '{') {
                startProp = true;
                output[pos] = 36;
                output[pos + 1] = 123;
                ++i;
                pos += 2;
                continue;
            }
            if (startProp) {
                if (chars[i] == '}') {
                    startProp = false;
                } else if (chars[i] == '.') {
                    char[] newOutput = Arrays.copyOf(output, output.length + 2);
                    newOutput[pos] = 35;
                    newOutput[pos + 1] = 124;
                    newOutput[pos + 2] = 35;
                    pos += 3;
                    output = newOutput;
                    continue;
                }
            }
            output[pos] = chars[i];
            ++pos;
        }
        return new String(output);
    }

    private void setPropertyIgnoreSchemaAndCatalogCase(Change change, String field2, String value, CompareControl.SchemaComparison comparison) {
        if (comparison.getOutputSchemaAs() != null && comparison.getComparisonSchema() != null && (comparison.getComparisonSchema().getSchemaName().equalsIgnoreCase(value) || comparison.getComparisonSchema().getCatalogName().equalsIgnoreCase(value))) {
            String newValue = this.valueToSet(field2, comparison.getOutputSchemaAs());
            if (field2.toLowerCase().equalsIgnoreCase("catalogname")) {
                if (!newValue.equalsIgnoreCase(comparison.getOutputSchemaAs())) {
                    ObjectUtil.setProperty((Object)change, field2, newValue);
                }
            } else {
                ObjectUtil.setProperty((Object)change, field2, newValue);
            }
        }
    }

    private void setProperty(Change change, String field2, String value, CompareControl.SchemaComparison comparison) {
        if (comparison.getOutputSchemaAs() != null && comparison.getComparisonSchema() != null && (comparison.getComparisonSchema().getSchemaName().equals(value) || comparison.getComparisonSchema().getCatalogName().equals(value))) {
            String newValue = this.valueToSet(field2, comparison.getOutputSchemaAs());
            if (field2.toLowerCase().equalsIgnoreCase("catalogname")) {
                if (!newValue.equals(comparison.getOutputSchemaAs())) {
                    ObjectUtil.setProperty((Object)change, field2, newValue);
                }
            } else {
                ObjectUtil.setProperty((Object)change, field2, newValue);
            }
        }
    }

    public void setRespectSchemaAndCatalogCase(boolean respectSchemaAndCatalogCase) {
        this.respectSchemaAndCatalogCase = respectSchemaAndCatalogCase;
    }
}

