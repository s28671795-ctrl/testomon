/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.jvm;

import java.util.regex.Pattern;
import liquibase.database.jvm.ConnectionPatterns;

public class JdbcConnectionPatterns
extends ConnectionPatterns {
    private static final String FILTER_CREDS_PW_TO_BLANK = "(?i)[?&:;]password=[^;&]*";
    private static final String FILTER_CREDS_USER_TO_BLANK = "(?i)[?&:;]user(.*?)=(.+)[^;&]";
    private static final String FILTER_CREDS_PRIVATE_KEY_TO_BLANK = "(?i)[?&:;]private_key_file(.*?)=[^;&]*";
    private static final String FILTER_CREDS_PASSWORD = "(?i)(.+?)password=([^;&?]+)[;&]*?(.*?)$";
    private static final String FILTER_CREDS_USER = "(?i)(.+?)user[name]*?=([^;&?]+)[;&]*?(.*?)$";
    private static final String FILTER_CREDS_PRIVATE_KEY_FILE = "(?i)(.+?)private_key_file=([^;&?]+)[;&]*?(.*?)$";
    private static final String FILTER_ACCOUNT_KEY = "(?i)(^cosmosdb:\\/\\/.+?):(.*?)@.*?(.*?)$";
    private static final String FILTER_CREDS_PRIVATE_KEY_FILE_PWD = "(?i)(.+?)private_key_file_pwd=([^;&?]+)[;&]*?(.*?)$";
    private static final String FILTER_CREDS = "(?i)/(.*)((?=@))";
    private static final String FILTER_CREDS_MYSQL_TO_OBFUSCATE = "(?i).+://(.*?)([:])(.*?)((?=@))";
    private static final String FILTER_CREDS_ORACLE_TO_OBFUSCATE = "(?i)jdbc:oracle:thin:(.*?)([/])(.*?)((?=@))";

    public JdbcConnectionPatterns() {
        this.addJdbcBlankPatterns(ConnectionPatterns.PatternPair.of(Pattern.compile("(?i)(.*)"), Pattern.compile(FILTER_CREDS_PW_TO_BLANK)));
        this.addJdbcBlankPatterns(ConnectionPatterns.PatternPair.of(Pattern.compile("(?i)(.*)"), Pattern.compile(FILTER_CREDS_USER_TO_BLANK)));
        this.addJdbcBlankPatterns(ConnectionPatterns.PatternPair.of(Pattern.compile("(?i)(.*)"), Pattern.compile(FILTER_CREDS_PRIVATE_KEY_TO_BLANK)));
        this.addJdbcBlankPatterns(ConnectionPatterns.PatternPair.of(Pattern.compile("(?i)jdbc:oracle:thin(.*)"), Pattern.compile(FILTER_CREDS)));
        this.addJdbcBlankPatterns(ConnectionPatterns.PatternPair.of(Pattern.compile("(?i)jdbc:mysql(.*)"), Pattern.compile(FILTER_CREDS)));
        this.addJdbcBlankPatterns(ConnectionPatterns.PatternPair.of(Pattern.compile("(?i)jdbc:mariadb(.*)"), Pattern.compile(FILTER_CREDS)));
        this.addJdbcBlankToObfuscatePatterns(ConnectionPatterns.PatternPair.of(Pattern.compile("(?i)jdbc:oracle:thin(.*)"), Pattern.compile(FILTER_CREDS_ORACLE_TO_OBFUSCATE)));
        this.addJdbcBlankToObfuscatePatterns(ConnectionPatterns.PatternPair.of(Pattern.compile("(?i)jdbc:mysql(.*)"), Pattern.compile(FILTER_CREDS_MYSQL_TO_OBFUSCATE)));
        this.addJdbcBlankToObfuscatePatterns(ConnectionPatterns.PatternPair.of(Pattern.compile("(?i)jdbc:mariadb(.*)"), Pattern.compile(FILTER_CREDS_MYSQL_TO_OBFUSCATE)));
        this.addJdbcObfuscatePatterns(ConnectionPatterns.PatternPair.of(Pattern.compile("(?i)(.*)"), Pattern.compile(FILTER_CREDS_PASSWORD)));
        this.addJdbcObfuscatePatterns(ConnectionPatterns.PatternPair.of(Pattern.compile("(?i)(.*)"), Pattern.compile(FILTER_CREDS_USER)));
        this.addJdbcObfuscatePatterns(ConnectionPatterns.PatternPair.of(Pattern.compile("(?i)(.*)"), Pattern.compile(FILTER_CREDS_PRIVATE_KEY_FILE)));
        this.addJdbcObfuscatePatterns(ConnectionPatterns.PatternPair.of(Pattern.compile("(?i)(.*)"), Pattern.compile(FILTER_CREDS_PRIVATE_KEY_FILE_PWD)));
        this.addJdbcObfuscatePatterns(ConnectionPatterns.PatternPair.of(Pattern.compile("(?i)^cosmosdb:\\/\\/.*"), Pattern.compile(FILTER_ACCOUNT_KEY)));
    }
}

