/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.conf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;
import org.jooq.Configuration;
import org.jooq.conf.BackslashEscaping;
import org.jooq.conf.ExecuteWithoutWhere;
import org.jooq.conf.FetchIntermediateResult;
import org.jooq.conf.MigrationSchema;
import org.jooq.conf.ParamType;
import org.jooq.conf.ParseSearchSchema;
import org.jooq.conf.QueryPoolable;
import org.jooq.conf.RenderFormatting;
import org.jooq.conf.RenderKeywordCase;
import org.jooq.conf.RenderKeywordStyle;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.RenderNameStyle;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.RenderTable;
import org.jooq.conf.Settings;
import org.jooq.conf.StatementType;
import org.jooq.conf.TransformUnneededArithmeticExpressions;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;
import org.jooq.util.jaxb.tools.MiniJAXB;

public final class SettingsTools {
    private static final Settings DEFAULT_SETTINGS;
    private static final JooqLogger log;

    public static final ParamType getParamType(Settings settings) {
        ParamType result;
        if (SettingsTools.executeStaticStatements(settings)) {
            return ParamType.INLINED;
        }
        if (settings != null && (result = settings.getParamType()) != null) {
            return result;
        }
        return ParamType.INDEXED;
    }

    public static final StatementType getStatementType(Settings settings) {
        StatementType result;
        if (settings != null && (result = settings.getStatementType()) != null) {
            return result;
        }
        return StatementType.PREPARED_STATEMENT;
    }

    public static final BackslashEscaping getBackslashEscaping(Settings settings) {
        BackslashEscaping result;
        if (settings != null && (result = settings.getBackslashEscaping()) != null) {
            return result;
        }
        return BackslashEscaping.DEFAULT;
    }

    public static final boolean executePreparedStatements(Settings settings) {
        return SettingsTools.getStatementType(settings) == StatementType.PREPARED_STATEMENT;
    }

    public static final boolean executeStaticStatements(Settings settings) {
        return SettingsTools.getStatementType(settings) == StatementType.STATIC_STATEMENT;
    }

    public static final boolean updatablePrimaryKeys(Settings settings) {
        return StringUtils.defaultIfNull(settings.isUpdatablePrimaryKeys(), false);
    }

    public static final boolean reflectionCaching(Settings settings) {
        return StringUtils.defaultIfNull(settings.isReflectionCaching(), true);
    }

    public static final boolean recordMapperCaching(Settings settings) {
        return StringUtils.defaultIfNull(settings.isCacheRecordMappers(), true);
    }

    public static final boolean parsingConnectionCaching(Settings settings) {
        return StringUtils.defaultIfNull(settings.isCacheParsingConnection(), true);
    }

    public static final Locale locale(Settings settings) {
        return StringUtils.defaultIfNull(settings.getLocale(), Locale.getDefault());
    }

    public static final Locale renderLocale(Settings settings) {
        return StringUtils.defaultIfNull(settings.getRenderLocale(), SettingsTools.locale(settings));
    }

    public static final Locale parseLocale(Settings settings) {
        return StringUtils.defaultIfNull(settings.getParseLocale(), SettingsTools.locale(settings));
    }

    public static final Locale interpreterLocale(Settings settings) {
        return StringUtils.defaultIfNull(settings.getInterpreterLocale(), SettingsTools.locale(settings));
    }

    public static final RenderTable getRenderTable(Settings settings) {
        if (settings.getRenderTable() == null) {
            settings.setRenderTable(RenderTable.ALWAYS);
        }
        return settings.getRenderTable();
    }

    public static final RenderMapping getRenderMapping(Settings settings) {
        if (settings.getRenderMapping() == null) {
            settings.setRenderMapping(new RenderMapping());
        }
        return settings.getRenderMapping();
    }

    public static final RenderKeywordCase getRenderKeywordCase(Settings settings) {
        RenderKeywordCase result;
        block6: {
            block7: {
                result = settings.getRenderKeywordCase();
                if (result != null && result != RenderKeywordCase.AS_IS) break block6;
                RenderKeywordStyle style = settings.getRenderKeywordStyle();
                if (style == null) break block7;
                switch (style) {
                    case AS_IS: {
                        result = RenderKeywordCase.AS_IS;
                        break block6;
                    }
                    case LOWER: {
                        result = RenderKeywordCase.LOWER;
                        break block6;
                    }
                    case UPPER: {
                        result = RenderKeywordCase.UPPER;
                        break block6;
                    }
                    case PASCAL: {
                        result = RenderKeywordCase.PASCAL;
                        break block6;
                    }
                    default: {
                        throw new UnsupportedOperationException("Unsupported style: " + String.valueOf((Object)style));
                    }
                }
            }
            result = RenderKeywordCase.AS_IS;
        }
        return result;
    }

    public static final RenderNameCase getRenderNameCase(Settings settings) {
        RenderNameCase result = settings.getRenderNameCase();
        if (result == null || result == RenderNameCase.AS_IS) {
            RenderNameStyle style = settings.getRenderNameStyle();
            result = style == RenderNameStyle.LOWER ? RenderNameCase.LOWER : (style == RenderNameStyle.UPPER ? RenderNameCase.UPPER : RenderNameCase.AS_IS);
        }
        return result;
    }

    public static final RenderQuotedNames getRenderQuotedNames(Settings settings) {
        RenderQuotedNames result = settings.getRenderQuotedNames();
        if (result == null || result == RenderQuotedNames.EXPLICIT_DEFAULT_QUOTED) {
            RenderNameStyle style = settings.getRenderNameStyle();
            result = style == null || style == RenderNameStyle.QUOTED ? RenderQuotedNames.EXPLICIT_DEFAULT_QUOTED : RenderQuotedNames.NEVER;
        }
        return result;
    }

    public static final ExecuteWithoutWhere getExecuteUpdateWithoutWhere(Settings settings) {
        ExecuteWithoutWhere result = settings.getExecuteUpdateWithoutWhere();
        return result == null ? ExecuteWithoutWhere.LOG_DEBUG : result;
    }

    public static final ExecuteWithoutWhere getExecuteDeleteWithoutWhere(Settings settings) {
        ExecuteWithoutWhere result = settings.getExecuteDeleteWithoutWhere();
        return result == null ? ExecuteWithoutWhere.LOG_DEBUG : result;
    }

    public static final TransformUnneededArithmeticExpressions getTransformUnneededArithmeticExpressions(Settings settings) {
        return settings.getTransformUnneededArithmeticExpressions() != null ? settings.getTransformUnneededArithmeticExpressions() : TransformUnneededArithmeticExpressions.NEVER;
    }

    public static final Settings defaultSettings() {
        return SettingsTools.clone(DEFAULT_SETTINGS);
    }

    public static final Settings clone(Settings settings) {
        Settings result = (Settings)settings.clone();
        if (result.renderFormatting != null) {
            result.renderFormatting = (RenderFormatting)result.renderFormatting.clone();
        }
        if (result.parseSearchPath != null) {
            result.parseSearchPath = new ArrayList<ParseSearchSchema>(result.parseSearchPath);
        }
        if (result.migrationSchemata != null) {
            result.migrationSchemata = new ArrayList<MigrationSchema>(result.migrationSchemata);
        }
        return result;
    }

    public static final int getQueryTimeout(int timeout2, Settings settings) {
        return timeout2 != 0 ? timeout2 : (settings.getQueryTimeout() != null ? settings.getQueryTimeout() : 0);
    }

    public static final QueryPoolable getQueryPoolable(QueryPoolable poolable, Settings settings) {
        return poolable != null && poolable != QueryPoolable.DEFAULT ? poolable : (settings.getQueryPoolable() != null ? settings.getQueryPoolable() : QueryPoolable.DEFAULT);
    }

    public static final int getMaxRows(int maxRows, Settings settings) {
        return maxRows != 0 ? maxRows : (settings.getMaxRows() != null ? settings.getMaxRows() : 0);
    }

    public static final boolean fetchIntermediateResult(Configuration configuration) {
        switch (StringUtils.defaultIfNull(configuration.settings().getFetchIntermediateResult(), FetchIntermediateResult.WHEN_RESULT_REQUESTED)) {
            case ALWAYS: {
                return true;
            }
            case WHEN_EXECUTE_LISTENERS_PRESENT: {
                return configuration.executeListenerProviders().length > 0;
            }
            case WHEN_RESULT_REQUESTED: {
                return false;
            }
        }
        throw new IllegalStateException("Unhandled FetchIntermediateResult: " + String.valueOf((Object)configuration.settings().getFetchIntermediateResult()));
    }

    public static final int getFetchSize(int fetchSize, Settings settings) {
        return fetchSize != 0 ? fetchSize : (settings.getFetchSize() != null ? settings.getFetchSize() : 0);
    }

    public static final int getBatchSize(Settings settings) {
        return settings.getBatchSize() != null ? settings.getBatchSize() : 0;
    }

    public static final int getFetchServerOutputSize(int fetchServerOutputSize, Settings settings) {
        return fetchServerOutputSize != 0 ? fetchServerOutputSize : (settings.getFetchServerOutputSize() != null ? settings.getFetchServerOutputSize() : 0);
    }

    public static final boolean returnAnyOnUpdatableRecord(Settings settings) {
        return !Boolean.FALSE.equals(settings.isReturnIdentityOnUpdatableRecord()) || SettingsTools.returnAnyNonIdentityOnUpdatableRecord(settings);
    }

    public static final boolean returnAnyNonIdentityOnUpdatableRecord(Settings settings) {
        return Boolean.TRUE.equals(settings.isReturnAllOnUpdatableRecord()) || Boolean.TRUE.equals(settings.isReturnDefaultOnUpdatableRecord()) || Boolean.TRUE.equals(settings.isReturnComputedOnUpdatableRecord());
    }

    static {
        InputStream i;
        InputStream in;
        log = JooqLogger.getLogger(SettingsTools.class);
        Settings settings = null;
        String property = System.getProperty("org.jooq.settings");
        if (property != null) {
            log.warn((Object)"DEPRECATION", "Loading system wide default settings via org.jooq.settings system properties has been deprecated. Please use explicit Settings in your Configuration references, instead.");
            in = SettingsTools.class.getResourceAsStream(property);
            if (in != null) {
                try {
                    i = in;
                    try {
                        settings = MiniJAXB.unmarshal(i, Settings.class);
                    }
                    finally {
                        if (i != null) {
                            i.close();
                        }
                    }
                }
                catch (IOException e) {
                    log.error("Error while reading settings: " + String.valueOf(e));
                }
            } else {
                settings = MiniJAXB.unmarshal(new File(property), Settings.class);
            }
        }
        if (settings == null && (in = SettingsTools.class.getResourceAsStream("/jooq-settings.xml")) != null) {
            try {
                i = in;
                try {
                    log.warn((Object)"DEPRECATION", "Loading system wide default settings via the classpath /jooq-settings.xml resource has been deprecated. Please use explicit Settings in your Configuration references, instead.");
                    settings = MiniJAXB.unmarshal(i, Settings.class);
                }
                finally {
                    if (i != null) {
                        i.close();
                    }
                }
            }
            catch (IOException e) {
                log.error("Error while reading settings: " + String.valueOf(e));
            }
        }
        if (settings == null) {
            settings = new Settings();
        }
        DEFAULT_SETTINGS = settings;
    }
}

