/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema;

import java.lang.reflect.Constructor;
import org.ldaptive.LdapUtils;
import org.ldaptive.schema.AbstractSchemaElement;
import org.ldaptive.schema.AttributeType;
import org.ldaptive.schema.DITContentRule;
import org.ldaptive.schema.DITStructureRule;
import org.ldaptive.schema.MatchingRule;
import org.ldaptive.schema.MatchingRuleUse;
import org.ldaptive.schema.NameForm;
import org.ldaptive.schema.ObjectClass;
import org.ldaptive.schema.SchemaElement;
import org.ldaptive.schema.SchemaFunction;
import org.ldaptive.schema.SchemaParseException;
import org.ldaptive.schema.Syntax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SchemaParser {
    private static final String SCHEMA_FUNCTION_PROPERTY = "org.ldaptive.schema.function";
    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaParser.class);
    private static final SchemaFunction SCHEMA_FUNCTION = SchemaParser.getSchemaFunction();
    private static final Constructor<?> SCHEMA_FUNCTION_CONSTRUCTOR = LdapUtils.createConstructorFromProperty("org.ldaptive.schema.function");

    private SchemaParser() {
    }

    public static SchemaFunction getSchemaFunction() {
        if (SCHEMA_FUNCTION_CONSTRUCTOR != null) {
            try {
                return (SchemaFunction)SCHEMA_FUNCTION_CONSTRUCTOR.newInstance(new Object[0]);
            }
            catch (Exception e) {
                LOGGER.error("Error creating new schema function instance with {}", (Object)SCHEMA_FUNCTION_CONSTRUCTOR, (Object)e);
                throw new IllegalStateException(e);
            }
        }
        return new DefaultSchemaFunction();
    }

    public static <T extends SchemaElement> T parse(Class<T> type, String definition) throws SchemaParseException {
        return SCHEMA_FUNCTION.parse(type, definition);
    }

    static {
        if (SCHEMA_FUNCTION_CONSTRUCTOR != null) {
            LOGGER.info("Setting schema definition function to {}", (Object)SCHEMA_FUNCTION_CONSTRUCTOR);
        }
    }

    public static class RegexSchemaFunction
    implements SchemaFunction {
        private static final Syntax.RegexDefinitionFunction SYNTAX_FUNCTION = new Syntax.RegexDefinitionFunction();
        private static final AttributeType.RegexDefinitionFunction ATTRIBUTE_TYPE_FUNCTION = new AttributeType.RegexDefinitionFunction();
        private static final DITStructureRule.RegexDefinitionFunction DIT_STRUCTURE_RULE_FUNCTION = new DITStructureRule.RegexDefinitionFunction();
        private static final MatchingRuleUse.RegexDefinitionFunction MATCHING_RULE_USE_FUNCTION = new MatchingRuleUse.RegexDefinitionFunction();
        private static final ObjectClass.RegexDefinitionFunction OBJECT_CLASS_FUNCTION = new ObjectClass.RegexDefinitionFunction();
        private static final NameForm.RegexDefinitionFunction NAME_FORM_FUNCTION = new NameForm.RegexDefinitionFunction();
        private static final DITContentRule.RegexDefinitionFunction DIT_CONTENT_RULE_FUNCTION = new DITContentRule.RegexDefinitionFunction();
        private static final MatchingRule.RegexDefinitionFunction MATCHING_RULE_FUNCTION = new MatchingRule.RegexDefinitionFunction();

        @Override
        public <T extends SchemaElement> T parse(Class<? extends T> type, String definition) throws SchemaParseException {
            AbstractSchemaElement element;
            if (Syntax.class == type) {
                element = SYNTAX_FUNCTION.parse(definition);
            } else if (AttributeType.class == type) {
                element = ATTRIBUTE_TYPE_FUNCTION.parse(definition);
            } else if (DITStructureRule.class == type) {
                element = DIT_STRUCTURE_RULE_FUNCTION.parse(definition);
            } else if (MatchingRuleUse.class == type) {
                element = MATCHING_RULE_USE_FUNCTION.parse(definition);
            } else if (ObjectClass.class == type) {
                element = OBJECT_CLASS_FUNCTION.parse(definition);
            } else if (NameForm.class == type) {
                element = NAME_FORM_FUNCTION.parse(definition);
            } else if (DITContentRule.class == type) {
                element = DIT_CONTENT_RULE_FUNCTION.parse(definition);
            } else if (MatchingRule.class == type) {
                element = MATCHING_RULE_FUNCTION.parse(definition);
            } else {
                throw new IllegalStateException("Unknown schema element " + type);
            }
            return (T)element;
        }
    }

    public static class DefaultSchemaFunction
    implements SchemaFunction {
        private static final Syntax.DefaultDefinitionFunction SYNTAX_FUNCTION = new Syntax.DefaultDefinitionFunction();
        private static final AttributeType.DefaultDefinitionFunction ATTRIBUTE_TYPE_FUNCTION = new AttributeType.DefaultDefinitionFunction();
        private static final DITStructureRule.DefaultDefinitionFunction DIT_STRUCTURE_RULE_FUNCTION = new DITStructureRule.DefaultDefinitionFunction();
        private static final MatchingRuleUse.DefaultDefinitionFunction MATCHING_RULE_USE_FUNCTION = new MatchingRuleUse.DefaultDefinitionFunction();
        private static final ObjectClass.DefaultDefinitionFunction OBJECT_CLASS_FUNCTION = new ObjectClass.DefaultDefinitionFunction();
        private static final NameForm.DefaultDefinitionFunction NAME_FORM_FUNCTION = new NameForm.DefaultDefinitionFunction();
        private static final DITContentRule.DefaultDefinitionFunction DIT_CONTENT_RULE_FUNCTION = new DITContentRule.DefaultDefinitionFunction();
        private static final MatchingRule.DefaultDefinitionFunction MATCHING_RULE_FUNCTION = new MatchingRule.DefaultDefinitionFunction();

        @Override
        public <T extends SchemaElement> T parse(Class<? extends T> type, String definition) throws SchemaParseException {
            AbstractSchemaElement element;
            if (Syntax.class == type) {
                element = SYNTAX_FUNCTION.parse(definition);
            } else if (AttributeType.class == type) {
                element = ATTRIBUTE_TYPE_FUNCTION.parse(definition);
            } else if (DITStructureRule.class == type) {
                element = DIT_STRUCTURE_RULE_FUNCTION.parse(definition);
            } else if (MatchingRuleUse.class == type) {
                element = MATCHING_RULE_USE_FUNCTION.parse(definition);
            } else if (ObjectClass.class == type) {
                element = OBJECT_CLASS_FUNCTION.parse(definition);
            } else if (NameForm.class == type) {
                element = NAME_FORM_FUNCTION.parse(definition);
            } else if (DITContentRule.class == type) {
                element = DIT_CONTENT_RULE_FUNCTION.parse(definition);
            } else if (MatchingRule.class == type) {
                element = MATCHING_RULE_FUNCTION.parse(definition);
            } else {
                throw new IllegalStateException("Unknown schema element " + type);
            }
            return (T)element;
        }
    }
}

