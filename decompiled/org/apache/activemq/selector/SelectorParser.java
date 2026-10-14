/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.selector;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import javax.jms.InvalidSelectorException;
import org.apache.activemq.filter.ArithmeticExpression;
import org.apache.activemq.filter.BooleanExpression;
import org.apache.activemq.filter.ComparisonExpression;
import org.apache.activemq.filter.ConstantExpression;
import org.apache.activemq.filter.Expression;
import org.apache.activemq.filter.FunctionCallExpression;
import org.apache.activemq.filter.LogicExpression;
import org.apache.activemq.filter.PropertyExpression;
import org.apache.activemq.filter.UnaryExpression;
import org.apache.activemq.selector.ParseException;
import org.apache.activemq.selector.SelectorParserConstants;
import org.apache.activemq.selector.SelectorParserTokenManager;
import org.apache.activemq.selector.SimpleCharStream;
import org.apache.activemq.selector.Token;
import org.apache.activemq.util.LRUCache;

public class SelectorParser
implements SelectorParserConstants {
    private static final Map cache = Collections.synchronizedMap(new LRUCache(100));
    private static final String CONVERT_STRING_EXPRESSIONS_PREFIX = "convert_string_expressions:";
    private String sql;
    public SelectorParserTokenManager token_source;
    SimpleCharStream jj_input_stream;
    public Token token;
    public Token jj_nt;
    private int jj_ntk;
    private Token jj_scanpos;
    private Token jj_lastpos;
    private int jj_la;
    private final LookaheadSuccess jj_ls = new LookaheadSuccess();

    public static BooleanExpression parse(String sql) throws InvalidSelectorException {
        Object result = cache.get(sql);
        if (result instanceof InvalidSelectorException) {
            throw (InvalidSelectorException)result;
        }
        if (result instanceof BooleanExpression) {
            return (BooleanExpression)result;
        }
        boolean convertStringExpressions = false;
        if (sql.startsWith(CONVERT_STRING_EXPRESSIONS_PREFIX)) {
            convertStringExpressions = true;
            sql = sql.substring(CONVERT_STRING_EXPRESSIONS_PREFIX.length());
        }
        if (convertStringExpressions) {
            ComparisonExpression.CONVERT_STRING_EXPRESSIONS.set(true);
        }
        try {
            BooleanExpression e = new SelectorParser(sql).parse();
            cache.put(sql, e);
            BooleanExpression booleanExpression = e;
            return booleanExpression;
        }
        catch (InvalidSelectorException t2) {
            cache.put(sql, t2);
            throw t2;
        }
        finally {
            if (convertStringExpressions) {
                ComparisonExpression.CONVERT_STRING_EXPRESSIONS.remove();
            }
        }
    }

    public static void clearCache() {
        cache.clear();
    }

    protected SelectorParser(String sql) {
        this(new StringReader(sql));
        this.sql = sql;
    }

    protected BooleanExpression parse() throws InvalidSelectorException {
        try {
            return this.JmsSelector();
        }
        catch (Throwable e) {
            throw (InvalidSelectorException)new InvalidSelectorException(this.sql).initCause(e);
        }
    }

    private BooleanExpression asBooleanExpression(Expression value) throws ParseException {
        if (value instanceof BooleanExpression) {
            return (BooleanExpression)value;
        }
        if (value instanceof PropertyExpression) {
            return UnaryExpression.createBooleanCast(value);
        }
        throw new ParseException("Expression will not result in a boolean value: " + value);
    }

    public final BooleanExpression JmsSelector() throws ParseException {
        Expression left = null;
        left = this.orExpression();
        this.jj_consume_token(0);
        return this.asBooleanExpression(left);
    }

    public final Expression orExpression() throws ParseException {
        Expression left = this.andExpression();
        block3: while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 10: {
                    break;
                }
                default: {
                    break block3;
                }
            }
            this.jj_consume_token(10);
            Expression right = this.andExpression();
            left = LogicExpression.createOR(this.asBooleanExpression(left), this.asBooleanExpression(right));
        }
        return left;
    }

    public final Expression andExpression() throws ParseException {
        Expression left = this.equalityExpression();
        block3: while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 9: {
                    break;
                }
                default: {
                    break block3;
                }
            }
            this.jj_consume_token(9);
            Expression right = this.equalityExpression();
            left = LogicExpression.createAND(this.asBooleanExpression(left), this.asBooleanExpression(right));
        }
        return left;
    }

    public final Expression equalityExpression() throws ParseException {
        Expression left;
        block12: {
            left = this.comparisonExpression();
            block10: while (true) {
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 15: 
                    case 28: 
                    case 29: {
                        break;
                    }
                    default: {
                        break block12;
                    }
                }
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 28: {
                        this.jj_consume_token(28);
                        Expression right = this.comparisonExpression();
                        left = ComparisonExpression.createEqual(left, right);
                        continue block10;
                    }
                    case 29: {
                        this.jj_consume_token(29);
                        Expression right = this.comparisonExpression();
                        left = ComparisonExpression.createNotEqual(left, right);
                        continue block10;
                    }
                }
                if (this.jj_2_1(2)) {
                    this.jj_consume_token(15);
                    this.jj_consume_token(18);
                    left = ComparisonExpression.createIsNull(left);
                    continue;
                }
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 15: {
                        this.jj_consume_token(15);
                        this.jj_consume_token(8);
                        this.jj_consume_token(18);
                        left = ComparisonExpression.createIsNotNull(left);
                        continue block10;
                    }
                }
                break;
            }
            this.jj_consume_token(-1);
            throw new ParseException();
        }
        return left;
    }

    public final Expression comparisonExpression() throws ParseException {
        Expression left;
        block33: {
            left = this.addExpression();
            block28: while (true) {
                ArrayList<String> list;
                Expression high;
                Expression low;
                String t2;
                String u;
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 8: 
                    case 11: 
                    case 12: 
                    case 14: 
                    case 30: 
                    case 31: 
                    case 32: 
                    case 33: {
                        break;
                    }
                    default: {
                        break block33;
                    }
                }
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 30: {
                        this.jj_consume_token(30);
                        Expression right = this.addExpression();
                        left = ComparisonExpression.createGreaterThan(left, right);
                        continue block28;
                    }
                    case 31: {
                        this.jj_consume_token(31);
                        Expression right = this.addExpression();
                        left = ComparisonExpression.createGreaterThanEqual(left, right);
                        continue block28;
                    }
                    case 32: {
                        this.jj_consume_token(32);
                        Expression right = this.addExpression();
                        left = ComparisonExpression.createLessThan(left, right);
                        continue block28;
                    }
                    case 33: {
                        this.jj_consume_token(33);
                        Expression right = this.addExpression();
                        left = ComparisonExpression.createLessThanEqual(left, right);
                        continue block28;
                    }
                    case 12: {
                        u = null;
                        this.jj_consume_token(12);
                        t2 = this.stringLitteral();
                        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                            case 13: {
                                this.jj_consume_token(13);
                                u = this.stringLitteral();
                                break;
                            }
                        }
                        left = ComparisonExpression.createLike(left, t2, u);
                        continue block28;
                    }
                }
                if (this.jj_2_2(2)) {
                    u = null;
                    this.jj_consume_token(8);
                    this.jj_consume_token(12);
                    t2 = this.stringLitteral();
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 13: {
                            this.jj_consume_token(13);
                            u = this.stringLitteral();
                            break;
                        }
                    }
                    left = ComparisonExpression.createNotLike(left, t2, u);
                    continue;
                }
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 11: {
                        this.jj_consume_token(11);
                        low = this.addExpression();
                        this.jj_consume_token(9);
                        high = this.addExpression();
                        left = ComparisonExpression.createBetween(left, low, high);
                        continue block28;
                    }
                }
                if (this.jj_2_3(2)) {
                    this.jj_consume_token(8);
                    this.jj_consume_token(11);
                    low = this.addExpression();
                    this.jj_consume_token(9);
                    high = this.addExpression();
                    left = ComparisonExpression.createNotBetween(left, low, high);
                    continue;
                }
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 14: {
                        this.jj_consume_token(14);
                        this.jj_consume_token(34);
                        t2 = this.stringLitteral();
                        list = new ArrayList<String>();
                        list.add(t2);
                        block29: while (true) {
                            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                                case 35: {
                                    break;
                                }
                                default: {
                                    break block29;
                                }
                            }
                            this.jj_consume_token(35);
                            t2 = this.stringLitteral();
                            list.add(t2);
                        }
                        this.jj_consume_token(36);
                        left = ComparisonExpression.createInFilter(left, list);
                        continue block28;
                    }
                }
                if (!this.jj_2_4(2)) break;
                this.jj_consume_token(8);
                this.jj_consume_token(14);
                this.jj_consume_token(34);
                t2 = this.stringLitteral();
                list = new ArrayList();
                list.add(t2);
                block30: while (true) {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 35: {
                            break;
                        }
                        default: {
                            break block30;
                        }
                    }
                    this.jj_consume_token(35);
                    t2 = this.stringLitteral();
                    list.add(t2);
                }
                this.jj_consume_token(36);
                left = ComparisonExpression.createNotInFilter(left, list);
            }
            this.jj_consume_token(-1);
            throw new ParseException();
        }
        return left;
    }

    public final Expression addExpression() throws ParseException {
        Expression left = this.multExpr();
        block4: while (this.jj_2_5(Integer.MAX_VALUE)) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 37: {
                    this.jj_consume_token(37);
                    Expression right = this.multExpr();
                    left = ArithmeticExpression.createPlus(left, right);
                    continue block4;
                }
                case 38: {
                    this.jj_consume_token(38);
                    Expression right = this.multExpr();
                    left = ArithmeticExpression.createMinus(left, right);
                    continue block4;
                }
            }
            this.jj_consume_token(-1);
            throw new ParseException();
        }
        return left;
    }

    public final Expression multExpr() throws ParseException {
        Expression left;
        block9: {
            left = this.unaryExpr();
            block8: while (true) {
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 39: 
                    case 40: 
                    case 41: {
                        break;
                    }
                    default: {
                        break block9;
                    }
                }
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 39: {
                        this.jj_consume_token(39);
                        Expression right = this.unaryExpr();
                        left = ArithmeticExpression.createMultiply(left, right);
                        continue block8;
                    }
                    case 40: {
                        this.jj_consume_token(40);
                        Expression right = this.unaryExpr();
                        left = ArithmeticExpression.createDivide(left, right);
                        continue block8;
                    }
                    case 41: {
                        this.jj_consume_token(41);
                        Expression right = this.unaryExpr();
                        left = ArithmeticExpression.createMod(left, right);
                        continue block8;
                    }
                }
                break;
            }
            this.jj_consume_token(-1);
            throw new ParseException();
        }
        return left;
    }

    public final Expression unaryExpr() throws ParseException {
        String s2 = null;
        Expression left = null;
        if (this.jj_2_6(Integer.MAX_VALUE)) {
            this.jj_consume_token(37);
            left = this.unaryExpr();
        } else {
            block0 : switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 38: {
                    this.jj_consume_token(38);
                    left = this.unaryExpr();
                    left = UnaryExpression.createNegate(left);
                    break;
                }
                case 8: {
                    this.jj_consume_token(8);
                    left = this.unaryExpr();
                    left = UnaryExpression.createNOT(this.asBooleanExpression(left));
                    break;
                }
                case 19: {
                    this.jj_consume_token(19);
                    s2 = this.stringLitteral();
                    left = UnaryExpression.createXPath(s2);
                    break;
                }
                case 20: {
                    this.jj_consume_token(20);
                    s2 = this.stringLitteral();
                    left = UnaryExpression.createXQuery(s2);
                    break;
                }
                default: {
                    if (this.jj_2_7(Integer.MAX_VALUE)) {
                        left = this.functionCallExpr();
                        break;
                    }
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 16: 
                        case 17: 
                        case 18: 
                        case 21: 
                        case 22: 
                        case 23: 
                        case 24: 
                        case 26: 
                        case 27: 
                        case 34: {
                            left = this.primaryExpr();
                            break block0;
                        }
                    }
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
        return left;
    }

    public final Expression functionCallExpr() throws ParseException {
        Object func_call = null;
        Expression arg = null;
        ArrayList<Expression> arg_list = new ArrayList<Expression>();
        Token func_name = this.jj_consume_token(27);
        this.jj_consume_token(34);
        arg = this.unaryExpr();
        arg_list.add(arg);
        block5: while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 35: {
                    break;
                }
                default: {
                    break block5;
                }
            }
            this.jj_consume_token(35);
            arg = this.unaryExpr();
            arg_list.add(arg);
        }
        this.jj_consume_token(36);
        try {
            return FunctionCallExpression.createFunctionCall(func_name.image, arg_list);
        }
        catch (FunctionCallExpression.invalidFunctionExpressionException inv_exc) {
            throw new Error("invalid function call expression", inv_exc);
        }
    }

    public final Expression primaryExpr() throws ParseException {
        Expression left = null;
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 16: 
            case 17: 
            case 18: 
            case 21: 
            case 22: 
            case 23: 
            case 24: 
            case 26: {
                left = this.literal();
                break;
            }
            case 27: {
                left = this.variable();
                break;
            }
            case 34: {
                this.jj_consume_token(34);
                left = this.orExpression();
                this.jj_consume_token(36);
                break;
            }
            default: {
                this.jj_consume_token(-1);
                throw new ParseException();
            }
        }
        return left;
    }

    public final ConstantExpression literal() throws ParseException {
        ConstantExpression left = null;
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 26: {
                String s2 = this.stringLitteral();
                left = new ConstantExpression(s2);
                break;
            }
            case 21: {
                Token t2 = this.jj_consume_token(21);
                left = ConstantExpression.createFromDecimal(t2.image);
                break;
            }
            case 22: {
                Token t3 = this.jj_consume_token(22);
                left = ConstantExpression.createFromHex(t3.image);
                break;
            }
            case 23: {
                Token t4 = this.jj_consume_token(23);
                left = ConstantExpression.createFromOctal(t4.image);
                break;
            }
            case 24: {
                Token t5 = this.jj_consume_token(24);
                left = ConstantExpression.createFloat(t5.image);
                break;
            }
            case 16: {
                this.jj_consume_token(16);
                left = ConstantExpression.TRUE;
                break;
            }
            case 17: {
                this.jj_consume_token(17);
                left = ConstantExpression.FALSE;
                break;
            }
            case 18: {
                this.jj_consume_token(18);
                left = ConstantExpression.NULL;
                break;
            }
            default: {
                this.jj_consume_token(-1);
                throw new ParseException();
            }
        }
        return left;
    }

    public final String stringLitteral() throws ParseException {
        StringBuffer rc = new StringBuffer();
        boolean first = true;
        Token t2 = this.jj_consume_token(26);
        String image = t2.image;
        for (int i = 1; i < image.length() - 1; ++i) {
            char c = image.charAt(i);
            if (c == '\'') {
                ++i;
            }
            rc.append(c);
        }
        return rc.toString();
    }

    public final PropertyExpression variable() throws ParseException {
        PropertyExpression left = null;
        Token t2 = this.jj_consume_token(27);
        left = new PropertyExpression(t2.image);
        return left;
    }

    private boolean jj_2_1(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            return !this.jj_3_1();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
    }

    private boolean jj_2_2(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            return !this.jj_3_2();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
    }

    private boolean jj_2_3(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            return !this.jj_3_3();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
    }

    private boolean jj_2_4(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            return !this.jj_3_4();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
    }

    private boolean jj_2_5(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            return !this.jj_3_5();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
    }

    private boolean jj_2_6(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            return !this.jj_3_6();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
    }

    private boolean jj_2_7(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            return !this.jj_3_7();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
    }

    private boolean jj_3R_11() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_13()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_14()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_15()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_16()) {
                        this.jj_scanpos = xsp;
                        if (this.jj_3R_17()) {
                            this.jj_scanpos = xsp;
                            if (this.jj_3R_18()) {
                                this.jj_scanpos = xsp;
                                if (this.jj_3R_19()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_59() {
        Token xsp;
        if (this.jj_scan_token(14)) {
            return true;
        }
        if (this.jj_scan_token(34)) {
            return true;
        }
        if (this.jj_3R_23()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_64());
        this.jj_scanpos = xsp;
        return this.jj_scan_token(36);
    }

    private boolean jj_3R_49() {
        if (this.jj_scan_token(28)) {
            return true;
        }
        return this.jj_3R_45();
    }

    private boolean jj_3R_46() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_49()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_50()) {
                this.jj_scanpos = xsp;
                if (this.jj_3_1()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_51()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_31() {
        return this.jj_scan_token(27);
    }

    private boolean jj_3_3() {
        if (this.jj_scan_token(8)) {
            return true;
        }
        if (this.jj_scan_token(11)) {
            return true;
        }
        if (this.jj_3R_47()) {
            return true;
        }
        if (this.jj_scan_token(9)) {
            return true;
        }
        return this.jj_3R_47();
    }

    private boolean jj_3R_29() {
        if (this.jj_scan_token(34)) {
            return true;
        }
        if (this.jj_3R_32()) {
            return true;
        }
        return this.jj_scan_token(36);
    }

    private boolean jj_3R_28() {
        return this.jj_3R_31();
    }

    private boolean jj_3R_22() {
        if (this.jj_scan_token(41)) {
            return true;
        }
        return this.jj_3R_11();
    }

    private boolean jj_3R_27() {
        return this.jj_3R_30();
    }

    private boolean jj_3R_58() {
        if (this.jj_scan_token(11)) {
            return true;
        }
        if (this.jj_3R_47()) {
            return true;
        }
        if (this.jj_scan_token(9)) {
            return true;
        }
        return this.jj_3R_47();
    }

    private boolean jj_3R_43() {
        Token xsp;
        if (this.jj_3R_45()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_46());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_21() {
        if (this.jj_scan_token(40)) {
            return true;
        }
        return this.jj_3R_11();
    }

    private boolean jj_3R_25() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_27()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_28()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_29()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean jj_3R_62() {
        if (this.jj_scan_token(13)) {
            return true;
        }
        return this.jj_3R_23();
    }

    private boolean jj_3_2() {
        if (this.jj_scan_token(8)) {
            return true;
        }
        if (this.jj_scan_token(12)) {
            return true;
        }
        if (this.jj_3R_23()) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_63()) {
            this.jj_scanpos = xsp;
        }
        return false;
    }

    private boolean jj_3R_12() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_20()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_21()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_22()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean jj_3R_20() {
        if (this.jj_scan_token(39)) {
            return true;
        }
        return this.jj_3R_11();
    }

    private boolean jj_3R_23() {
        return this.jj_scan_token(26);
    }

    private boolean jj_3R_44() {
        if (this.jj_scan_token(9)) {
            return true;
        }
        return this.jj_3R_43();
    }

    private boolean jj_3R_10() {
        Token xsp;
        if (this.jj_3R_11()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_12());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_57() {
        if (this.jj_scan_token(12)) {
            return true;
        }
        if (this.jj_3R_23()) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_62()) {
            this.jj_scanpos = xsp;
        }
        return false;
    }

    private boolean jj_3R_41() {
        Token xsp;
        if (this.jj_3R_43()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_44());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_26() {
        if (this.jj_scan_token(35)) {
            return true;
        }
        return this.jj_3R_11();
    }

    private boolean jj_3R_56() {
        if (this.jj_scan_token(33)) {
            return true;
        }
        return this.jj_3R_47();
    }

    private boolean jj_3R_40() {
        return this.jj_scan_token(18);
    }

    private boolean jj_3R_61() {
        if (this.jj_scan_token(38)) {
            return true;
        }
        return this.jj_3R_10();
    }

    private boolean jj_3_5() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(37)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(38)) {
                return true;
            }
        }
        return this.jj_3R_10();
    }

    private boolean jj_3R_55() {
        if (this.jj_scan_token(32)) {
            return true;
        }
        return this.jj_3R_47();
    }

    private boolean jj_3R_60() {
        if (this.jj_scan_token(37)) {
            return true;
        }
        return this.jj_3R_10();
    }

    private boolean jj_3R_39() {
        return this.jj_scan_token(17);
    }

    private boolean jj_3R_42() {
        if (this.jj_scan_token(10)) {
            return true;
        }
        return this.jj_3R_41();
    }

    private boolean jj_3R_54() {
        if (this.jj_scan_token(31)) {
            return true;
        }
        return this.jj_3R_47();
    }

    private boolean jj_3R_52() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_60()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_61()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3_7() {
        if (this.jj_scan_token(27)) {
            return true;
        }
        return this.jj_scan_token(34);
    }

    private boolean jj_3R_24() {
        Token xsp;
        if (this.jj_scan_token(27)) {
            return true;
        }
        if (this.jj_scan_token(34)) {
            return true;
        }
        if (this.jj_3R_11()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_26());
        this.jj_scanpos = xsp;
        return this.jj_scan_token(36);
    }

    private boolean jj_3R_53() {
        if (this.jj_scan_token(30)) {
            return true;
        }
        return this.jj_3R_47();
    }

    private boolean jj_3R_48() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_53()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_54()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_55()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_56()) {
                        this.jj_scanpos = xsp;
                        if (this.jj_3R_57()) {
                            this.jj_scanpos = xsp;
                            if (this.jj_3_2()) {
                                this.jj_scanpos = xsp;
                                if (this.jj_3R_58()) {
                                    this.jj_scanpos = xsp;
                                    if (this.jj_3_3()) {
                                        this.jj_scanpos = xsp;
                                        if (this.jj_3R_59()) {
                                            this.jj_scanpos = xsp;
                                            if (this.jj_3_4()) {
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_38() {
        return this.jj_scan_token(16);
    }

    private boolean jj_3R_47() {
        Token xsp;
        if (this.jj_3R_10()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_52());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_32() {
        Token xsp;
        if (this.jj_3R_41()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_42());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_37() {
        return this.jj_scan_token(24);
    }

    private boolean jj_3R_65() {
        if (this.jj_scan_token(35)) {
            return true;
        }
        return this.jj_3R_23();
    }

    private boolean jj_3R_18() {
        return this.jj_3R_24();
    }

    private boolean jj_3R_19() {
        return this.jj_3R_25();
    }

    private boolean jj_3R_36() {
        return this.jj_scan_token(23);
    }

    private boolean jj_3R_45() {
        Token xsp;
        if (this.jj_3R_47()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_48());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_17() {
        if (this.jj_scan_token(20)) {
            return true;
        }
        return this.jj_3R_23();
    }

    private boolean jj_3R_35() {
        return this.jj_scan_token(22);
    }

    private boolean jj_3R_16() {
        if (this.jj_scan_token(19)) {
            return true;
        }
        return this.jj_3R_23();
    }

    private boolean jj_3R_63() {
        if (this.jj_scan_token(13)) {
            return true;
        }
        return this.jj_3R_23();
    }

    private boolean jj_3_4() {
        Token xsp;
        if (this.jj_scan_token(8)) {
            return true;
        }
        if (this.jj_scan_token(14)) {
            return true;
        }
        if (this.jj_scan_token(34)) {
            return true;
        }
        if (this.jj_3R_23()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_65());
        this.jj_scanpos = xsp;
        return this.jj_scan_token(36);
    }

    private boolean jj_3_6() {
        if (this.jj_scan_token(37)) {
            return true;
        }
        return this.jj_3R_11();
    }

    private boolean jj_3R_34() {
        return this.jj_scan_token(21);
    }

    private boolean jj_3R_15() {
        if (this.jj_scan_token(8)) {
            return true;
        }
        return this.jj_3R_11();
    }

    private boolean jj_3R_64() {
        if (this.jj_scan_token(35)) {
            return true;
        }
        return this.jj_3R_23();
    }

    private boolean jj_3R_51() {
        if (this.jj_scan_token(15)) {
            return true;
        }
        if (this.jj_scan_token(8)) {
            return true;
        }
        return this.jj_scan_token(18);
    }

    private boolean jj_3R_14() {
        if (this.jj_scan_token(38)) {
            return true;
        }
        return this.jj_3R_11();
    }

    private boolean jj_3R_33() {
        return this.jj_3R_23();
    }

    private boolean jj_3_1() {
        if (this.jj_scan_token(15)) {
            return true;
        }
        return this.jj_scan_token(18);
    }

    private boolean jj_3R_13() {
        if (this.jj_scan_token(37)) {
            return true;
        }
        return this.jj_3R_11();
    }

    private boolean jj_3R_30() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_33()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_34()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_35()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_36()) {
                        this.jj_scanpos = xsp;
                        if (this.jj_3R_37()) {
                            this.jj_scanpos = xsp;
                            if (this.jj_3R_38()) {
                                this.jj_scanpos = xsp;
                                if (this.jj_3R_39()) {
                                    this.jj_scanpos = xsp;
                                    if (this.jj_3R_40()) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_50() {
        if (this.jj_scan_token(29)) {
            return true;
        }
        return this.jj_3R_45();
    }

    public SelectorParser(InputStream stream) {
        this(stream, null);
    }

    public SelectorParser(InputStream stream, String encoding) {
        try {
            this.jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        this.token_source = new SelectorParserTokenManager(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
    }

    public void ReInit(InputStream stream) {
        this.ReInit(stream, null);
    }

    public void ReInit(InputStream stream, String encoding) {
        try {
            this.jj_input_stream.ReInit(stream, encoding, 1, 1);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        this.token_source.ReInit(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
    }

    public SelectorParser(Reader stream) {
        this.jj_input_stream = new SimpleCharStream(stream, 1, 1);
        this.token_source = new SelectorParserTokenManager(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
    }

    public void ReInit(Reader stream) {
        this.jj_input_stream.ReInit(stream, 1, 1);
        this.token_source.ReInit(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
    }

    public SelectorParser(SelectorParserTokenManager tm) {
        this.token_source = tm;
        this.token = new Token();
        this.jj_ntk = -1;
    }

    public void ReInit(SelectorParserTokenManager tm) {
        this.token_source = tm;
        this.token = new Token();
        this.jj_ntk = -1;
    }

    private Token jj_consume_token(int kind) throws ParseException {
        Token oldToken = this.token;
        this.token = oldToken.next != null ? this.token.next : (this.token.next = this.token_source.getNextToken());
        this.jj_ntk = -1;
        if (this.token.kind == kind) {
            return this.token;
        }
        this.token = oldToken;
        throw this.generateParseException();
    }

    private boolean jj_scan_token(int kind) {
        if (this.jj_scanpos == this.jj_lastpos) {
            --this.jj_la;
            if (this.jj_scanpos.next == null) {
                this.jj_scanpos = this.jj_scanpos.next = this.token_source.getNextToken();
                this.jj_lastpos = this.jj_scanpos.next;
            } else {
                this.jj_lastpos = this.jj_scanpos = this.jj_scanpos.next;
            }
        } else {
            this.jj_scanpos = this.jj_scanpos.next;
        }
        if (this.jj_scanpos.kind != kind) {
            return true;
        }
        if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            throw this.jj_ls;
        }
        return false;
    }

    public final Token getNextToken() {
        this.token = this.token.next != null ? this.token.next : (this.token.next = this.token_source.getNextToken());
        this.jj_ntk = -1;
        return this.token;
    }

    public final Token getToken(int index) {
        Token t2 = this.token;
        for (int i = 0; i < index; ++i) {
            t2 = t2.next != null ? t2.next : (t2.next = this.token_source.getNextToken());
        }
        return t2;
    }

    private int jj_ntk() {
        this.jj_nt = this.token.next;
        if (this.jj_nt == null) {
            this.token.next = this.token_source.getNextToken();
            this.jj_ntk = this.token.next.kind;
            return this.jj_ntk;
        }
        this.jj_ntk = this.jj_nt.kind;
        return this.jj_ntk;
    }

    public ParseException generateParseException() {
        Token errortok = this.token.next;
        int line = errortok.beginLine;
        int column = errortok.beginColumn;
        String mess = errortok.kind == 0 ? tokenImage[0] : errortok.image;
        return new ParseException("Parse error at line " + line + ", column " + column + ".  Encountered: " + mess);
    }

    public final void enable_tracing() {
    }

    public final void disable_tracing() {
    }

    private static final class LookaheadSuccess
    extends Error {
        private LookaheadSuccess() {
        }
    }
}

