/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import java.util.HashSet;
import org.h2.engine.Database;
import org.h2.mode.ModeFunction;

public final class BuiltinFunctions {
    private static final HashSet<String> FUNCTIONS;

    public static boolean isBuiltinFunction(Database database, String string) {
        return FUNCTIONS.contains(string) || ModeFunction.getFunction(database, string) != null;
    }

    private BuiltinFunctions() {
    }

    static {
        String[] stringArray = new String[]{"ABS", "MOD", "FLOOR", "CEIL", "ROUND", "ROUNDMAGIC", "SIGN", "TRUNC", "TRUNCATE", "SIN", "COS", "TAN", "COT", "SINH", "COSH", "TANH", "ASIN", "ACOS", "ATAN", "LOG10", "LN", "EXP", "SQRT", "DEGREES", "RADIANS", "ATAN2", "LOG", "POWER", "BITAND", "BITOR", "BITXOR", "BITNOT", "BITNAND", "BITNOR", "BITXNOR", "BITGET", "BITCOUNT", "LSHIFT", "RSHIFT", "ULSHIFT", "URSHIFT", "ROTATELEFT", "ROTATERIGHT", "EXTRACT", "DATE_TRUNC", "DATEADD", "DATEDIFF", "TIMESTAMPADD", "TIMESTAMPDIFF", "FORMATDATETIME", "PARSEDATETIME", "DAYNAME", "MONTHNAME", "CARDINALITY", "ARRAY_MAX_CARDINALITY", "LOCATE", "INSERT", "REPLACE", "LPAD", "RPAD", "TRANSLATE", "UPPER", "LOWER", "ASCII", "CHAR", "CHR", "STRINGENCODE", "STRINGDECODE", "STRINGTOUTF8", "UTF8TOSTRING", "HEXTORAW", "RAWTOHEX", "SPACE", "QUOTE_IDENT", "REPEAT", "SUBSTRING", "TO_CHAR", "CHAR_LENGTH", "CHARACTER_LENGTH", "LENGTH", "OCTET_LENGTH", "BIT_LENGTH", "TRIM", "REGEXP_LIKE", "REGEXP_REPLACE", "REGEXP_SUBSTR", "XMLATTR", "XMLCDATA", "XMLCOMMENT", "XMLNODE", "XMLSTARTDOC", "XMLTEXT", "TRIM_ARRAY", "ARRAY_CONTAINS", "ARRAY_SLICE", "COMPRESS", "EXPAND", "SOUNDEX", "DIFFERENCE", "JSON_OBJECT", "JSON_ARRAY", "ENCRYPT", "DECRYPT", "COALESCE", "GREATEST", "LEAST", "NULLIF", "CONCAT", "CONCAT_WS", "HASH", "ORA_HASH", "RAND", "RANDOM", "SECURE_RAND", "RANDOM_UUID", "UUID", "ABORT_SESSION", "CANCEL_SESSION", "AUTOCOMMIT", "DATABASE_PATH", "H2VERSION", "LOCK_MODE", "LOCK_TIMEOUT", "MEMORY_FREE", "MEMORY_USED", "READONLY", "SESSION_ID", "TRANSACTION_ID", "DISK_SPACE_USED", "ESTIMATED_ENVELOPE", "FILE_READ", "FILE_WRITE", "DATA_TYPE_SQL", "DB_OBJECT_ID", "DB_OBJECT_SQL", "CSVWRITE", "SIGNAL", "TRUNCATE_VALUE", "CURRVAL", "NEXTVAL", "ZERO", "PI", "UNNEST", "TABLE_DISTINCT", "CSVREAD", "LINK_SCHEMA"};
        HashSet<String> hashSet = new HashSet<String>(128);
        for (String string : stringArray) {
            hashSet.add(string);
        }
        FUNCTIONS = hashSet;
    }
}

