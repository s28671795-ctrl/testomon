/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;
import liquibase.Scope;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.UnknownChangeLogParameterException;
import liquibase.parser.ChangeLogParserConfiguration;

class ExpressionExpander {
    private final boolean enableEscaping = ChangeLogParserConfiguration.SUPPORT_PROPERTY_ESCAPING.getCurrentValue();
    private final ChangeLogParameters parameters;
    private static final Map<String, Object> expandedParameters = new LinkedHashMap<String, Object>();

    public ExpressionExpander(ChangeLogParameters parameters) {
        this.parameters = parameters;
    }

    public String expandExpressions(String text, DatabaseChangeLog changeLog) throws UnknownChangeLogParameterException {
        if (text == null) {
            return null;
        }
        String expressions = this.expandExpressions(new StringReader(text), changeLog, false);
        if (!expandedParameters.isEmpty()) {
            Scope.getCurrentScope().addMdcValue("changelogProperties", expandedParameters);
        }
        return expressions;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String expandExpressions(StringReader reader, DatabaseChangeLog changeLog, boolean inExpression) throws UnknownChangeLogParameterException {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            int nextChar = reader.read();
            while (nextChar != -1) {
                block20: {
                    if (nextChar == 36) {
                        reader.mark(1);
                        if (reader.read() == 123) {
                            Object paramValue;
                            String paramExpression = this.expandExpressions(reader, changeLog, true);
                            if (paramExpression.startsWith("${")) {
                                paramValue = paramExpression;
                            } else if (paramExpression.startsWith(":") && this.enableEscaping) {
                                paramValue = "${" + paramExpression.substring(1).trim() + "}";
                            } else {
                                paramValue = this.parameters.getValue(paramExpression.trim(), changeLog);
                                if (paramValue == null) {
                                    ChangeLogParserConfiguration.MissingPropertyMode missingPropertyMode = ChangeLogParserConfiguration.MISSING_PROPERTY_MODE.getCurrentValue();
                                    switch (missingPropertyMode) {
                                        case EMPTY: {
                                            paramValue = "";
                                            break;
                                        }
                                        case ERROR: {
                                            throw new UnknownChangeLogParameterException("Could not resolve expression `${" + paramExpression + "}` in file " + changeLog.getPhysicalFilePath());
                                        }
                                        case PRESERVE: {
                                            paramValue = "${" + paramExpression + "}";
                                            break;
                                        }
                                        default: {
                                            throw new UnexpectedLiquibaseException("Unknown MissingPropertyMode: " + (Object)((Object)missingPropertyMode));
                                        }
                                    }
                                } else if (paramValue instanceof String) {
                                    paramValue = this.expandExpressions((String)paramValue, changeLog);
                                    expandedParameters.put(paramExpression, String.valueOf(paramValue));
                                }
                            }
                            stringBuilder.append(paramValue);
                            break block20;
                        } else {
                            stringBuilder.append("$");
                            reader.reset();
                            nextChar = reader.read();
                            continue;
                        }
                    }
                    if (nextChar == 125 && inExpression) {
                        return stringBuilder.toString();
                    }
                    stringBuilder.append((char)nextChar);
                }
                nextChar = reader.read();
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        if (inExpression) {
            return "${" + stringBuilder;
        }
        return stringBuilder.toString();
    }
}

