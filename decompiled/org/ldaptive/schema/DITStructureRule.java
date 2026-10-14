/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ldaptive.LdapUtils;
import org.ldaptive.schema.AbstractDefaultDefinitionFunction;
import org.ldaptive.schema.AbstractNamedSchemaElement;
import org.ldaptive.schema.AbstractRegexDefinitionFunction;
import org.ldaptive.schema.Extensions;
import org.ldaptive.schema.SchemaParseException;
import org.ldaptive.schema.SchemaParser;
import org.ldaptive.schema.SchemaUtils;

public class DITStructureRule
extends AbstractNamedSchemaElement {
    private static final int HASH_CODE_SEED = 1153;
    private final int id;
    private String nameForm;
    private int[] superiorRules;

    public DITStructureRule(int i) {
        this.id = i;
    }

    public DITStructureRule(int id, String[] names, String description, boolean obsolete, String nameForm, int[] superiorRules, Extensions extensions) {
        this(id);
        this.setNames(names);
        this.setDescription(description);
        this.setObsolete(obsolete);
        this.setNameForm(nameForm);
        this.setSuperiorRules(superiorRules);
        this.setExtensions(extensions);
    }

    public int getID() {
        return this.id;
    }

    public String getNameForm() {
        return this.nameForm;
    }

    public void setNameForm(String s2) {
        this.nameForm = s2;
    }

    public int[] getSuperiorRules() {
        return this.superiorRules;
    }

    public void setSuperiorRules(int[] i) {
        this.superiorRules = i;
    }

    public static DITStructureRule parse(String definition) throws SchemaParseException {
        return SchemaParser.parse(DITStructureRule.class, definition);
    }

    @Override
    public String format() {
        StringBuilder sb = new StringBuilder("( ");
        sb.append(this.id).append(" ");
        if (this.getNames() != null && this.getNames().length > 0) {
            sb.append("NAME ");
            sb.append(SchemaUtils.formatDescriptors(this.getNames()));
        }
        if (this.getDescription() != null) {
            sb.append("DESC ");
            sb.append(SchemaUtils.formatDescriptors(this.getDescription()));
        }
        if (this.isObsolete()) {
            sb.append("OBSOLETE ");
        }
        if (this.nameForm != null) {
            sb.append("FORM ").append(this.nameForm).append(" ");
        }
        if (this.superiorRules != null && this.superiorRules.length > 0) {
            sb.append("SUP ");
            sb.append(SchemaUtils.formatNumbers(this.superiorRules));
        }
        if (this.getExtensions() != null) {
            sb.append(this.getExtensions().format());
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof DITStructureRule) {
            DITStructureRule v = (DITStructureRule)o;
            return LdapUtils.areEqual(this.id, v.id) && LdapUtils.areEqual(this.getNames(), v.getNames()) && LdapUtils.areEqual(this.getDescription(), v.getDescription()) && LdapUtils.areEqual(this.isObsolete(), v.isObsolete()) && LdapUtils.areEqual(this.nameForm, v.nameForm) && LdapUtils.areEqual(this.superiorRules, v.superiorRules) && LdapUtils.areEqual(this.getExtensions(), v.getExtensions());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(1153, this.id, this.getNames(), this.getDescription(), this.isObsolete(), this.nameForm, this.superiorRules, this.getExtensions());
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "id=" + this.id + ", " + "names=" + Arrays.toString(this.getNames()) + ", " + "description=" + this.getDescription() + ", " + "obsolete=" + this.isObsolete() + ", " + "nameForm=" + this.nameForm + ", " + "superiorRules=" + Arrays.toString(this.superiorRules) + ", " + "extensions=" + this.getExtensions() + "]";
    }

    public static class RegexDefinitionFunction
    extends AbstractRegexDefinitionFunction<DITStructureRule> {
        private static final Pattern DEFINITION_PATTERN = Pattern.compile("[ ]*\\([ ]*(\\p{Digit}+)[ ]*(?:NAME[ ]+(?:'([^']+)'|\\(([^\\)]+)\\)))?[ ]*(?:DESC[ ]+'([^']*)')?[ ]*(OBSOLETE)?[ ]*(?:FORM[ ]+([^ ]+))?[ ]*(?:SUP[ ]+(?:([^ ]+)|\\(([^\\)]+)\\)))?[ ]*(?:(X-[^ ]+.*))?[ ]*\\)[ ]*");

        @Override
        public DITStructureRule parse(String definition) throws SchemaParseException {
            Matcher m4 = DEFINITION_PATTERN.matcher(definition);
            if (!m4.matches()) {
                throw new SchemaParseException("Invalid DIT structure rule definition: " + definition);
            }
            DITStructureRule dsrd = new DITStructureRule(Integer.parseInt(m4.group(1).trim()));
            if (m4.group(2) != null) {
                dsrd.setNames(SchemaUtils.parseDescriptors(m4.group(2).trim()));
            } else if (m4.group(3) != null) {
                dsrd.setNames(SchemaUtils.parseDescriptors(m4.group(3).trim()));
            }
            dsrd.setDescription(m4.group(4) != null ? m4.group(4).trim() : null);
            dsrd.setObsolete(m4.group(5) != null);
            dsrd.setNameForm(m4.group(6) != null ? m4.group(6).trim() : null);
            if (m4.group(7) != null) {
                dsrd.setSuperiorRules(SchemaUtils.parseNumbers(m4.group(7).trim()));
            } else if (m4.group(8) != null) {
                dsrd.setSuperiorRules(SchemaUtils.parseNumbers(m4.group(8).trim()));
            }
            if (m4.group(9) != null) {
                dsrd.setExtensions(this.parseExtensions(m4.group(9).trim()));
            }
            return dsrd;
        }
    }

    public static class DefaultDefinitionFunction
    extends AbstractDefaultDefinitionFunction<DITStructureRule> {
        @Override
        public DITStructureRule parse(String definition) throws SchemaParseException {
            CharBuffer buffer = this.validate(definition);
            this.skipSpaces(buffer);
            DITStructureRule dsr = new DITStructureRule(this.readRuleID(buffer));
            Extensions exts = new Extensions();
            block16: while (buffer.hasRemaining()) {
                this.skipSpaces(buffer);
                String token = this.readUntilSpace(buffer);
                this.skipSpaces(buffer);
                switch (token) {
                    case "NAME": {
                        dsr.setNames(this.readQDStrings(buffer));
                        continue block16;
                    }
                    case "DESC": {
                        dsr.setDescription(this.readQDString(buffer));
                        continue block16;
                    }
                    case "OBSOLETE": {
                        dsr.setObsolete(true);
                        continue block16;
                    }
                    case "FORM": {
                        dsr.setNameForm(this.readUntilSpace(buffer));
                        continue block16;
                    }
                    case "SUP": {
                        dsr.setSuperiorRules(this.readRuleIDs(buffer));
                        continue block16;
                    }
                    case "": {
                        continue block16;
                    }
                }
                if (!token.startsWith("X-")) {
                    throw new SchemaParseException("Definition '" + definition + "' contains invalid extension '" + token + "'");
                }
                this.skipSpaces(buffer);
                exts.addExtension(token, List.of(this.readQDStrings(buffer)));
            }
            if (!exts.isEmpty()) {
                dsr.setExtensions(exts);
            }
            return dsr;
        }
    }
}

