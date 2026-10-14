/*
 * Decompiled with CFR 0.152.
 */
package org.h2.bnf.context;

import java.util.HashMap;
import java.util.HashSet;
import org.h2.bnf.Bnf;
import org.h2.bnf.BnfVisitor;
import org.h2.bnf.Rule;
import org.h2.bnf.RuleElement;
import org.h2.bnf.RuleHead;
import org.h2.bnf.RuleList;
import org.h2.bnf.Sentence;
import org.h2.bnf.context.DbColumn;
import org.h2.bnf.context.DbContents;
import org.h2.bnf.context.DbProcedure;
import org.h2.bnf.context.DbSchema;
import org.h2.bnf.context.DbTableOrView;
import org.h2.message.DbException;
import org.h2.util.ParserUtil;
import org.h2.util.StringUtils;

public class DbContextRule
implements Rule {
    public static final int COLUMN = 0;
    public static final int TABLE = 1;
    public static final int TABLE_ALIAS = 2;
    public static final int NEW_TABLE_ALIAS = 3;
    public static final int COLUMN_ALIAS = 4;
    public static final int SCHEMA = 5;
    public static final int PROCEDURE = 6;
    private final DbContents contents;
    private final int type;
    private String columnType;

    public DbContextRule(DbContents dbContents, int n) {
        this.contents = dbContents;
        this.type = n;
    }

    public void setColumnType(String string) {
        this.columnType = string;
    }

    @Override
    public void setLinks(HashMap<String, RuleHead> hashMap) {
    }

    @Override
    public void accept(BnfVisitor bnfVisitor) {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public boolean autoComplete(Sentence sentence) {
        String string;
        String string2 = string = sentence.getQuery();
        String string3 = sentence.getQueryUpper();
        switch (this.type) {
            case 5: {
                DbSchema[] dbSchemaArray = this.contents.getSchemas();
                String object2 = null;
                Object object3 = null;
                for (DbSchema dbSchema : dbSchemaArray) {
                    String string4 = StringUtils.toUpperEnglish(dbSchema.name);
                    if (string3.startsWith(string4)) {
                        if (object2 != null && string4.length() <= object2.length()) continue;
                        object2 = string4;
                        object3 = dbSchema;
                        continue;
                    }
                    if (string2.length() != 0 && !string4.startsWith(string3) || string2.length() >= string4.length()) continue;
                    sentence.add(string4, string4.substring(string2.length()), this.type);
                    sentence.add(dbSchema.quotedName + ".", dbSchema.quotedName.substring(string2.length()) + ".", 0);
                }
                if (object2 == null) break;
                sentence.setLastMatchedSchema((DbSchema)object3);
                string2 = string2.substring(object2.length());
                break;
            }
            case 1: {
                void var8_17;
                void var11_28;
                DbSchema dbSchema = sentence.getLastMatchedSchema();
                if (dbSchema == null) {
                    dbSchema = this.contents.getDefaultSchema();
                }
                DbTableOrView[] dbTableOrViewArray = dbSchema.getTables();
                Object object3 = null;
                Object var8_16 = null;
                DbTableOrView[] dbTableOrViewArray2 = dbTableOrViewArray;
                int n = dbTableOrViewArray2.length;
                boolean bl = false;
                while (var11_28 < n) {
                    DbTableOrView dbTableOrView = dbTableOrViewArray2[var11_28];
                    String string5 = string3;
                    String string6 = StringUtils.toUpperEnglish(dbTableOrView.getName());
                    if (dbTableOrView.getQuotedName().length() > string6.length()) {
                        string6 = dbTableOrView.getQuotedName();
                        string5 = string;
                    }
                    if (string5.startsWith(string6)) {
                        if (object3 == null || string6.length() > ((String)object3).length()) {
                            object3 = string6;
                            DbTableOrView dbTableOrView2 = dbTableOrView;
                        }
                    } else if ((string2.length() == 0 || string6.startsWith(string5)) && string2.length() < string6.length()) {
                        sentence.add(dbTableOrView.getQuotedName(), dbTableOrView.getQuotedName().substring(string2.length()), 0);
                    }
                    ++var11_28;
                }
                if (object3 == null) break;
                sentence.setLastMatchedTable((DbTableOrView)var8_17);
                sentence.addTable((DbTableOrView)var8_17);
                string2 = string2.substring(((String)object3).length());
                break;
            }
            case 3: {
                string2 = DbContextRule.autoCompleteTableAlias(sentence, true);
                break;
            }
            case 2: {
                string2 = DbContextRule.autoCompleteTableAlias(sentence, false);
                break;
            }
            case 4: {
                String string6;
                char c;
                int n;
                if (string.indexOf(32) < 0) break;
                for (n = 0; n < string3.length() && ((c = string3.charAt(n)) == '_' || Character.isLetterOrDigit(c)); ++n) {
                }
                if (n == 0 || ParserUtil.isKeyword(string6 = string3.substring(0, n), false)) break;
                string2 = string2.substring(string6.length());
                break;
            }
            case 0: {
                HashSet<DbTableOrView> hashSet = sentence.getTables();
                String string7 = null;
                Object object3 = sentence.getLastMatchedTable();
                if (object3 != null && ((DbTableOrView)object3).getColumns() != null) {
                    for (DbColumn dbColumn : ((DbTableOrView)object3).getColumns()) {
                        void var12_34;
                        String string8 = string3;
                        String string9 = StringUtils.toUpperEnglish(dbColumn.getName());
                        if (dbColumn.getQuotedName().length() > string9.length()) {
                            string9 = dbColumn.getQuotedName();
                            String string10 = string;
                        }
                        if (!var12_34.startsWith(string9) || !this.testColumnType(dbColumn)) continue;
                        String string11 = string2.substring(string9.length());
                        if (string7 == null || string11.length() < string7.length()) {
                            string7 = string11;
                            continue;
                        }
                        if (string2.length() != 0 && !string9.startsWith((String)var12_34) || string2.length() >= string9.length()) continue;
                        sentence.add(dbColumn.getName(), dbColumn.getName().substring(string2.length()), 0);
                    }
                }
                for (DbSchema dbSchema : this.contents.getSchemas()) {
                    for (DbTableOrView dbTableOrView : dbSchema.getTables()) {
                        if (dbTableOrView != object3 && hashSet != null && !hashSet.contains(dbTableOrView) || dbTableOrView == null || dbTableOrView.getColumns() == null) continue;
                        for (DbColumn dbColumn : dbTableOrView.getColumns()) {
                            String string12 = StringUtils.toUpperEnglish(dbColumn.getName());
                            if (!this.testColumnType(dbColumn)) continue;
                            if (string3.startsWith(string12)) {
                                String string13 = string2.substring(string12.length());
                                if (string7 != null && string13.length() >= string7.length()) continue;
                                string7 = string13;
                                continue;
                            }
                            if (string2.length() != 0 && !string12.startsWith(string3) || string2.length() >= string12.length()) continue;
                            sentence.add(dbColumn.getName(), dbColumn.getName().substring(string2.length()), 0);
                        }
                    }
                }
                if (string7 == null) break;
                string2 = string7;
                break;
            }
            case 6: {
                this.autoCompleteProcedure(sentence);
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.type);
            }
        }
        if (!string2.equals(string)) {
            while (Bnf.startWithSpace(string2)) {
                string2 = string2.substring(1);
            }
            sentence.setQuery(string2);
            return true;
        }
        return false;
    }

    private boolean testColumnType(DbColumn dbColumn) {
        if (this.columnType == null) {
            return true;
        }
        String string = dbColumn.getDataType();
        if (this.columnType.contains("CHAR") || this.columnType.contains("CLOB")) {
            return string.contains("CHAR") || string.contains("CLOB");
        }
        if (this.columnType.contains("BINARY") || this.columnType.contains("BLOB")) {
            return string.contains("BINARY") || string.contains("BLOB");
        }
        return string.contains(this.columnType);
    }

    private void autoCompleteProcedure(Sentence sentence) {
        String string;
        DbSchema dbSchema = sentence.getLastMatchedSchema();
        if (dbSchema == null) {
            dbSchema = this.contents.getDefaultSchema();
        }
        String string2 = string = sentence.getQueryUpper();
        int n = string.indexOf(40);
        if (n != -1) {
            string2 = StringUtils.trimSubstring(string, 0, n);
        }
        RuleElement ruleElement = new RuleElement("(", "Function");
        RuleElement ruleElement2 = new RuleElement(")", "Function");
        RuleElement ruleElement3 = new RuleElement(",", "Function");
        for (DbProcedure dbProcedure : dbSchema.getProcedures()) {
            String string3 = dbProcedure.getName();
            if (!string3.startsWith(string2)) continue;
            RuleElement ruleElement4 = new RuleElement(string3, "Function");
            RuleList ruleList = new RuleList(ruleElement4, ruleElement, false);
            if (string.contains("(")) {
                for (DbColumn dbColumn : dbProcedure.getParameters()) {
                    if (dbColumn.getPosition() > 1) {
                        ruleList = new RuleList(ruleList, ruleElement3, false);
                    }
                    DbContextRule dbContextRule = new DbContextRule(this.contents, 0);
                    String string4 = dbColumn.getDataType();
                    if (string4.contains("(")) {
                        string4 = string4.substring(0, string4.indexOf(40));
                    }
                    dbContextRule.setColumnType(string4);
                    ruleList = new RuleList(ruleList, dbContextRule, false);
                }
                ruleList = new RuleList(ruleList, ruleElement2, false);
            }
            ruleList.autoComplete(sentence);
        }
    }

    private static String autoCompleteTableAlias(Sentence sentence, boolean bl) {
        HashMap<String, DbTableOrView> hashMap;
        char c;
        int n;
        String string = sentence.getQuery();
        String string2 = sentence.getQueryUpper();
        for (n = 0; n < string2.length() && ((c = string2.charAt(n)) == '_' || Character.isLetterOrDigit(c)); ++n) {
        }
        if (n == 0) {
            return string;
        }
        String string3 = string2.substring(0, n);
        if ("SET".equals(string3) || ParserUtil.isKeyword(string3, false)) {
            return string;
        }
        if (bl) {
            sentence.addAlias(string3, sentence.getLastTable());
        }
        if ((hashMap = sentence.getAliases()) != null && hashMap.containsKey(string3) || sentence.getLastTable() == null) {
            if (bl && string.length() == string3.length()) {
                return string;
            }
            if ((string = string.substring(string3.length())).length() == 0) {
                sentence.add(string3 + ".", ".", 0);
            }
            return string;
        }
        HashSet<DbTableOrView> hashSet = sentence.getTables();
        if (hashSet != null) {
            String string4 = null;
            for (DbTableOrView dbTableOrView : hashSet) {
                String string5 = StringUtils.toUpperEnglish(dbTableOrView.getName());
                if (string3.startsWith(string5) && (string4 == null || string5.length() > string4.length())) {
                    sentence.setLastMatchedTable(dbTableOrView);
                    string4 = string5;
                    continue;
                }
                if (string.length() != 0 && !string5.startsWith(string3)) continue;
                sentence.add(string5 + ".", string5.substring(string.length()) + ".", 0);
            }
            if (string4 != null) {
                if ((string = string.substring(string4.length())).length() == 0) {
                    sentence.add(string3 + ".", ".", 0);
                }
                return string;
            }
        }
        return string;
    }
}

