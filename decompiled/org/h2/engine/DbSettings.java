/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import java.util.HashMap;
import org.h2.engine.SettingsBase;
import org.h2.message.DbException;

public class DbSettings
extends SettingsBase {
    static final int TABLE_SIZE = 64;
    public static final DbSettings DEFAULT = new DbSettings(new HashMap<String, String>(64));
    public final int analyzeAuto = this.get("ANALYZE_AUTO", 2000);
    public final int analyzeSample = this.get("ANALYZE_SAMPLE", 10000);
    public final int autoCompactFillRate = this.get("AUTO_COMPACT_FILL_RATE", 90);
    public final boolean databaseToLower;
    public final boolean databaseToUpper;
    public final boolean caseInsensitiveIdentifiers = this.get("CASE_INSENSITIVE_IDENTIFIERS", false);
    public final boolean defaultConnection = this.get("DEFAULT_CONNECTION", false);
    public final String defaultEscape = this.get("DEFAULT_ESCAPE", "\\");
    public final boolean defragAlways = this.get("DEFRAG_ALWAYS", false);
    public final boolean dropRestrict = this.get("DROP_RESTRICT", true);
    public final int estimatedFunctionTableRows = this.get("ESTIMATED_FUNCTION_TABLE_ROWS", 1000);
    public final int lobTimeout = this.get("LOB_TIMEOUT", 300000);
    public final int maxCompactTime = this.get("MAX_COMPACT_TIME", 200);
    public final int maxQueryTimeout = this.get("MAX_QUERY_TIMEOUT", 0);
    public final boolean optimizeDistinct = this.get("OPTIMIZE_DISTINCT", true);
    public final boolean optimizeEvaluatableSubqueries = this.get("OPTIMIZE_EVALUATABLE_SUBQUERIES", true);
    public final boolean optimizeInsertFromSelect = this.get("OPTIMIZE_INSERT_FROM_SELECT", true);
    public final boolean optimizeInList = this.get("OPTIMIZE_IN_LIST", true);
    public final boolean optimizeInSelect = this.get("OPTIMIZE_IN_SELECT", true);
    public final boolean optimizeOr = this.get("OPTIMIZE_OR", true);
    public final boolean optimizeTwoEquals = this.get("OPTIMIZE_TWO_EQUALS", true);
    public final boolean optimizeSimpleSingleRowSubqueries = this.get("OPTIMIZE_SIMPLE_SINGLE_ROW_SUBQUERIES", true);
    public final int queryCacheSize = this.get("QUERY_CACHE_SIZE", 8);
    public final boolean recompileAlways = this.get("RECOMPILE_ALWAYS", false);
    public final boolean reuseSpace = this.get("REUSE_SPACE", true);
    public final boolean shareLinkedConnections = this.get("SHARE_LINKED_CONNECTIONS", true);
    public final String defaultTableEngine = this.get("DEFAULT_TABLE_ENGINE", null);
    public final boolean mvStore = this.get("MV_STORE", true);
    public final boolean compressData = this.get("COMPRESS", false);
    public final boolean ignoreCatalogs = this.get("IGNORE_CATALOGS", false);
    public final boolean zeroBasedEnums = this.get("ZERO_BASED_ENUMS", false);

    private DbSettings(HashMap<String, String> hashMap) {
        super(hashMap);
        boolean bl = this.get("DATABASE_TO_LOWER", false);
        boolean bl2 = this.containsKey("DATABASE_TO_UPPER");
        boolean bl3 = this.get("DATABASE_TO_UPPER", true);
        if (bl && bl3) {
            if (bl2) {
                throw DbException.get(90021, "DATABASE_TO_LOWER & DATABASE_TO_UPPER");
            }
            bl3 = false;
        }
        this.databaseToLower = bl;
        this.databaseToUpper = bl3;
        HashMap<String, String> hashMap2 = this.getSettings();
        hashMap2.put("DATABASE_TO_LOWER", Boolean.toString(bl));
        hashMap2.put("DATABASE_TO_UPPER", Boolean.toString(bl3));
    }

    static DbSettings getInstance(HashMap<String, String> hashMap) {
        return new DbSettings(hashMap);
    }
}

