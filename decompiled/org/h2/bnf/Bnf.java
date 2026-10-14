/*
 * Decompiled with CFR 0.152.
 */
package org.h2.bnf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import org.h2.bnf.BnfVisitor;
import org.h2.bnf.Rule;
import org.h2.bnf.RuleElement;
import org.h2.bnf.RuleExtension;
import org.h2.bnf.RuleFixed;
import org.h2.bnf.RuleHead;
import org.h2.bnf.RuleList;
import org.h2.bnf.RuleOptional;
import org.h2.bnf.RuleRepeat;
import org.h2.bnf.Sentence;
import org.h2.bnf.context.DbContextRule;
import org.h2.command.dml.Help;
import org.h2.tools.Csv;
import org.h2.util.StringUtils;
import org.h2.util.Utils;

public class Bnf {
    private final HashMap<String, RuleHead> ruleMap = new HashMap();
    private String syntax;
    private String currentToken;
    private String[] tokens;
    private char firstChar;
    private int index;
    private Rule lastRepeat;
    private ArrayList<RuleHead> statements;
    private String currentTopic;

    public static Bnf getInstance(Reader reader) throws SQLException, IOException {
        Bnf bnf = new Bnf();
        if (reader == null) {
            byte[] byArray = Utils.getResource("/org/h2/res/help.csv");
            reader = new InputStreamReader((InputStream)new ByteArrayInputStream(byArray), StandardCharsets.UTF_8);
        }
        bnf.parse(reader);
        return bnf;
    }

    public void addAlias(String string, String string2) {
        RuleHead ruleHead = this.ruleMap.get(string2);
        this.ruleMap.put(string, ruleHead);
    }

    private void addFixedRule(String string, int n) {
        RuleFixed ruleFixed = new RuleFixed(n);
        this.addRule(string, "Fixed", ruleFixed);
    }

    private RuleHead addRule(String string, String string2, Rule rule) {
        RuleHead ruleHead = new RuleHead(string2, string, rule);
        String string3 = StringUtils.toLowerEnglish(string.trim().replace(' ', '_'));
        if (this.ruleMap.putIfAbsent(string3, ruleHead) != null) {
            throw new AssertionError((Object)("already exists: " + string));
        }
        return ruleHead;
    }

    private void parse(Reader reader) throws SQLException, IOException {
        Object object;
        Rule rule = null;
        this.statements = new ArrayList();
        Csv csv = new Csv();
        csv.setLineCommentCharacter('#');
        ResultSet resultSet = csv.read(reader, null);
        while (resultSet.next()) {
            object = resultSet.getString("SECTION").trim();
            if (((String)object).startsWith("System")) continue;
            String string = resultSet.getString("TOPIC");
            this.syntax = Help.stripAnnotationsFromSyntax(resultSet.getString("SYNTAX"));
            this.currentTopic = object;
            this.tokens = this.tokenize();
            this.index = 0;
            Rule rule2 = this.parseRule();
            if (((String)object).startsWith("Command")) {
                rule2 = new RuleList(rule2, new RuleElement(";\n\n", this.currentTopic), false);
            }
            RuleHead ruleHead = this.addRule(string, (String)object, rule2);
            if (((String)object).startsWith("Function")) {
                if (rule == null) {
                    rule = rule2;
                    continue;
                }
                rule = new RuleList(rule2, rule, true);
                continue;
            }
            if (!((String)object).startsWith("Commands")) continue;
            this.statements.add(ruleHead);
        }
        this.addRule("@func@", "Function", rule);
        this.addFixedRule("@ymd@", 0);
        this.addFixedRule("@hms@", 1);
        this.addFixedRule("@nanos@", 2);
        this.addFixedRule("anything_except_single_quote", 3);
        this.addFixedRule("single_character", 3);
        this.addFixedRule("anything_except_double_quote", 4);
        this.addFixedRule("anything_until_end_of_line", 5);
        this.addFixedRule("anything_until_comment_start_or_end", 6);
        this.addFixedRule("anything_except_two_dollar_signs", 8);
        this.addFixedRule("anything", 7);
        this.addFixedRule("@hex_start@", 9);
        this.addFixedRule("@octal_start@", 10);
        this.addFixedRule("@binary_start@", 11);
        this.addFixedRule("@concat@", 12);
        this.addFixedRule("@az_@", 13);
        this.addFixedRule("@af@", 14);
        this.addFixedRule("@digit@", 15);
        this.addFixedRule("@open_bracket@", 16);
        this.addFixedRule("@close_bracket@", 17);
        this.addFixedRule("json_text", 18);
        object = this.ruleMap.get("digit").getRule();
        this.ruleMap.get("number").setRule(new RuleList((Rule)object, new RuleOptional(new RuleRepeat((Rule)object, false)), false));
    }

    public void visit(BnfVisitor bnfVisitor, String string) {
        this.syntax = string;
        this.tokens = this.tokenize();
        this.index = 0;
        Rule rule = this.parseRule();
        rule.setLinks(this.ruleMap);
        rule.accept(bnfVisitor);
    }

    public static boolean startWithSpace(String string) {
        return string.length() > 0 && Character.isWhitespace(string.charAt(0));
    }

    public static String getRuleMapKey(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (Character.isUpperCase(c)) {
                stringBuilder.append('_').append(Character.toLowerCase(c));
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public RuleHead getRuleHead(String string) {
        return this.ruleMap.get(string);
    }

    private Rule parseRule() {
        this.read();
        return this.parseOr();
    }

    private Rule parseOr() {
        Rule rule = this.parseList();
        if (this.firstChar == '|') {
            this.read();
            rule = new RuleList(rule, this.parseOr(), true);
        }
        this.lastRepeat = rule;
        return rule;
    }

    private Rule parseList() {
        Rule rule = this.parseToken();
        if (this.firstChar != '|' && this.firstChar != ']' && this.firstChar != '}' && this.firstChar != '\u0000') {
            rule = new RuleList(rule, this.parseList(), false);
        }
        this.lastRepeat = rule;
        return rule;
    }

    private RuleExtension parseExtension(boolean bl) {
        Rule rule;
        this.read();
        if (this.firstChar == '[') {
            this.read();
            rule = this.parseOr();
            rule = new RuleOptional(rule);
            if (this.firstChar != ']') {
                throw new AssertionError((Object)("expected ], got " + this.currentToken + " syntax:" + this.syntax));
            }
        } else if (this.firstChar == '{') {
            this.read();
            rule = this.parseOr();
            if (this.firstChar != '}') {
                throw new AssertionError((Object)("expected }, got " + this.currentToken + " syntax:" + this.syntax));
            }
        } else {
            rule = this.parseOr();
        }
        return new RuleExtension(rule, bl);
    }

    private Rule parseToken() {
        Rule rule;
        if (this.firstChar >= 'A' && this.firstChar <= 'Z' || this.firstChar >= 'a' && this.firstChar <= 'z') {
            rule = new RuleElement(this.currentToken, this.currentTopic);
        } else if (this.firstChar == '[') {
            this.read();
            rule = this.parseOr();
            rule = new RuleOptional(rule);
            if (this.firstChar != ']') {
                throw new AssertionError((Object)("expected ], got " + this.currentToken + " syntax:" + this.syntax));
            }
        } else if (this.firstChar == '{') {
            this.read();
            rule = this.parseOr();
            if (this.firstChar != '}') {
                throw new AssertionError((Object)("expected }, got " + this.currentToken + " syntax:" + this.syntax));
            }
        } else if (this.firstChar == '@') {
            if ("@commaDots@".equals(this.currentToken)) {
                rule = new RuleList(new RuleElement(",", this.currentTopic), this.lastRepeat, false);
                rule = new RuleRepeat(rule, true);
            } else {
                rule = "@dots@".equals(this.currentToken) ? new RuleRepeat(this.lastRepeat, false) : ("@c@".equals(this.currentToken) ? this.parseExtension(true) : ("@h2@".equals(this.currentToken) ? this.parseExtension(false) : new RuleElement(this.currentToken, this.currentTopic)));
            }
        } else {
            rule = new RuleElement(this.currentToken, this.currentTopic);
        }
        this.lastRepeat = rule;
        this.read();
        return rule;
    }

    private void read() {
        if (this.index < this.tokens.length) {
            this.currentToken = this.tokens[this.index++];
            this.firstChar = this.currentToken.charAt(0);
        } else {
            this.currentToken = "";
            this.firstChar = '\u0000';
        }
    }

    public String toString() {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        for (n = 0; n < this.index; ++n) {
            stringBuilder.append(this.tokens[n]).append(' ');
        }
        stringBuilder.append("[*]");
        for (n = this.index; n < this.tokens.length; ++n) {
            stringBuilder.append(' ').append(this.tokens[n]);
        }
        return stringBuilder.toString();
    }

    private String[] tokenize() {
        ArrayList<String> arrayList = new ArrayList<String>();
        this.syntax = StringUtils.replaceAll(this.syntax, "yyyy-MM-dd", "@ymd@");
        this.syntax = StringUtils.replaceAll(this.syntax, "hh:mm:ss", "@hms@");
        this.syntax = StringUtils.replaceAll(this.syntax, "hh:mm", "@hms@");
        this.syntax = StringUtils.replaceAll(this.syntax, "mm:ss", "@hms@");
        this.syntax = StringUtils.replaceAll(this.syntax, "nnnnnnnnn", "@nanos@");
        this.syntax = StringUtils.replaceAll(this.syntax, "function", "@func@");
        this.syntax = StringUtils.replaceAll(this.syntax, "0x", "@hexStart@");
        this.syntax = StringUtils.replaceAll(this.syntax, "0o", "@octalStart@");
        this.syntax = StringUtils.replaceAll(this.syntax, "0b", "@binaryStart@");
        this.syntax = StringUtils.replaceAll(this.syntax, ",...", "@commaDots@");
        this.syntax = StringUtils.replaceAll(this.syntax, "...", "@dots@");
        this.syntax = StringUtils.replaceAll(this.syntax, "||", "@concat@");
        this.syntax = StringUtils.replaceAll(this.syntax, "a-z|_", "@az_@");
        this.syntax = StringUtils.replaceAll(this.syntax, "A-Z|_", "@az_@");
        this.syntax = StringUtils.replaceAll(this.syntax, "A-F", "@af@");
        this.syntax = StringUtils.replaceAll(this.syntax, "0-9", "@digit@");
        this.syntax = StringUtils.replaceAll(this.syntax, "'['", "@openBracket@");
        this.syntax = StringUtils.replaceAll(this.syntax, "']'", "@closeBracket@");
        StringTokenizer stringTokenizer = Bnf.getTokenizer(this.syntax);
        while (stringTokenizer.hasMoreTokens()) {
            String string = stringTokenizer.nextToken();
            if ((string = StringUtils.cache(string)).length() == 1 && " \r\n".indexOf(string.charAt(0)) >= 0) continue;
            arrayList.add(string);
        }
        return arrayList.toArray(new String[0]);
    }

    public HashMap<String, String> getNextTokenList(String string) {
        Sentence sentence = new Sentence();
        sentence.setQuery(string);
        try {
            for (RuleHead ruleHead : this.statements) {
                if (!ruleHead.getSection().startsWith("Commands")) continue;
                sentence.start();
                if (!ruleHead.getRule().autoComplete(sentence)) continue;
                break;
            }
        }
        catch (IllegalStateException illegalStateException) {
            // empty catch block
        }
        return sentence.getNext();
    }

    public void linkStatements() {
        for (RuleHead ruleHead : this.ruleMap.values()) {
            ruleHead.getRule().setLinks(this.ruleMap);
        }
    }

    public void updateTopic(String string, DbContextRule dbContextRule) {
        RuleHead ruleHead = this.ruleMap.get(string = StringUtils.toLowerEnglish(string));
        if (ruleHead == null) {
            ruleHead = new RuleHead("db", string, dbContextRule);
            this.ruleMap.put(string, ruleHead);
            this.statements.add(ruleHead);
        } else {
            ruleHead.setRule(dbContextRule);
        }
    }

    public ArrayList<RuleHead> getStatements() {
        return this.statements;
    }

    public static StringTokenizer getTokenizer(String string) {
        return new StringTokenizer(string, " [](){}|.,\r\n<>:-+*/=\"!'$", true);
    }
}

