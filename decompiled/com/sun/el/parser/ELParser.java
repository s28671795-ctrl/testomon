/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.parser.AstAnd;
import com.sun.el.parser.AstAssign;
import com.sun.el.parser.AstBracketSuffix;
import com.sun.el.parser.AstChoice;
import com.sun.el.parser.AstCompositeExpression;
import com.sun.el.parser.AstConcat;
import com.sun.el.parser.AstDeferredExpression;
import com.sun.el.parser.AstDiv;
import com.sun.el.parser.AstDotSuffix;
import com.sun.el.parser.AstDynamicExpression;
import com.sun.el.parser.AstEmpty;
import com.sun.el.parser.AstEqual;
import com.sun.el.parser.AstFalse;
import com.sun.el.parser.AstFloatingPoint;
import com.sun.el.parser.AstFunction;
import com.sun.el.parser.AstGreaterThan;
import com.sun.el.parser.AstGreaterThanEqual;
import com.sun.el.parser.AstIdentifier;
import com.sun.el.parser.AstInteger;
import com.sun.el.parser.AstLambdaExpression;
import com.sun.el.parser.AstLambdaParameters;
import com.sun.el.parser.AstLessThan;
import com.sun.el.parser.AstLessThanEqual;
import com.sun.el.parser.AstListData;
import com.sun.el.parser.AstLiteralExpression;
import com.sun.el.parser.AstMapData;
import com.sun.el.parser.AstMapEntry;
import com.sun.el.parser.AstMethodArguments;
import com.sun.el.parser.AstMinus;
import com.sun.el.parser.AstMod;
import com.sun.el.parser.AstMult;
import com.sun.el.parser.AstNegative;
import com.sun.el.parser.AstNot;
import com.sun.el.parser.AstNotEqual;
import com.sun.el.parser.AstNull;
import com.sun.el.parser.AstOr;
import com.sun.el.parser.AstPlus;
import com.sun.el.parser.AstSemiColon;
import com.sun.el.parser.AstString;
import com.sun.el.parser.AstTrue;
import com.sun.el.parser.AstValue;
import com.sun.el.parser.ELParserConstants;
import com.sun.el.parser.ELParserTokenManager;
import com.sun.el.parser.ELParserTreeConstants;
import com.sun.el.parser.JJTELParserState;
import com.sun.el.parser.Node;
import com.sun.el.parser.ParseException;
import com.sun.el.parser.SimpleCharStream;
import com.sun.el.parser.Token;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.el.ELException;

public class ELParser
implements ELParserTreeConstants,
ELParserConstants {
    protected JJTELParserState jjtree = new JJTELParserState();
    public ELParserTokenManager token_source;
    SimpleCharStream jj_input_stream;
    public Token token;
    public Token jj_nt;
    private int jj_ntk;
    private Token jj_scanpos;
    private Token jj_lastpos;
    private int jj_la;
    private int jj_gen;
    private final int[] jj_la1 = new int[53];
    private static int[] jj_la1_0;
    private static int[] jj_la1_1;
    private final JJCalls[] jj_2_rtns = new JJCalls[5];
    private boolean jj_rescan = false;
    private int jj_gc = 0;
    private final LookaheadSuccess jj_ls = new LookaheadSuccess();
    private List<int[]> jj_expentries = new ArrayList<int[]>();
    private int[] jj_expentry;
    private int jj_kind = -1;
    private int[] jj_lasttokens = new int[100];
    private int jj_endpos;

    public static Node parse(String ref) throws ELException {
        try {
            return new ELParser(new StringReader(ref)).CompositeExpression();
        }
        catch (ParseException pe) {
            throw new ELException(pe.getMessage());
        }
    }

    public final AstCompositeExpression CompositeExpression() throws ParseException {
        AstCompositeExpression jjtn000 = new AstCompositeExpression(0);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        try {
            block19: {
                block13: while (true) {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 1: 
                        case 2: 
                        case 3: {
                            break;
                        }
                        default: {
                            this.jj_la1[0] = this.jj_gen;
                            break block19;
                        }
                    }
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 3: {
                            this.DeferredExpression();
                            continue block13;
                        }
                        case 2: {
                            this.DynamicExpression();
                            continue block13;
                        }
                        case 1: {
                            this.LiteralExpression();
                            continue block13;
                        }
                    }
                    break;
                }
                this.jj_la1[1] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
            }
            this.jj_consume_token(0);
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            AstCompositeExpression astCompositeExpression = jjtn000;
            return astCompositeExpression;
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void LiteralExpression() throws ParseException {
        AstLiteralExpression jjtn000 = new AstLiteralExpression(1);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        Token t2 = null;
        try {
            t2 = this.jj_consume_token(1);
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            jjtn000.setImage(t2.image);
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    public final void DeferredExpression() throws ParseException {
        AstDeferredExpression jjtn000 = new AstDeferredExpression(2);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        try {
            this.jj_consume_token(3);
            this.Expression();
            this.jj_consume_token(10);
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    public final void DynamicExpression() throws ParseException {
        AstDynamicExpression jjtn000 = new AstDynamicExpression(3);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        try {
            this.jj_consume_token(2);
            this.Expression();
            this.jj_consume_token(10);
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    public final void Expression() throws ParseException {
        this.SemiColon();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void SemiColon() throws ParseException {
        this.Assignment();
        while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 26: {
                    break;
                }
                default: {
                    this.jj_la1[2] = this.jj_gen;
                    return;
                }
            }
            this.jj_consume_token(26);
            AstSemiColon jjtn001 = new AstSemiColon(5);
            boolean jjtc001 = true;
            this.jjtree.openNodeScope(jjtn001);
            try {
                this.Assignment();
                continue;
            }
            catch (Throwable jjte001) {
                if (jjtc001) {
                    this.jjtree.clearNodeScope(jjtn001);
                    jjtc001 = false;
                } else {
                    this.jjtree.popNode();
                }
                if (jjte001 instanceof RuntimeException) {
                    throw (RuntimeException)jjte001;
                }
                if (!(jjte001 instanceof ParseException)) throw (Error)jjte001;
                throw (ParseException)jjte001;
            }
            finally {
                if (!jjtc001) continue;
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                continue;
            }
            break;
        }
    }

    public final void Assignment() throws ParseException {
        if (this.jj_2_1(3)) {
            this.LambdaExpression();
        } else {
            block2 : switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 9: 
                case 11: 
                case 12: 
                case 14: 
                case 16: 
                case 17: 
                case 18: 
                case 20: 
                case 22: 
                case 39: 
                case 40: 
                case 45: 
                case 49: 
                case 58: {
                    this.Choice();
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 56: {
                            this.jj_consume_token(56);
                            AstAssign jjtn001 = new AstAssign(6);
                            boolean jjtc001 = true;
                            this.jjtree.openNodeScope(jjtn001);
                            try {
                                this.Assignment();
                                break block2;
                            }
                            catch (Throwable jjte001) {
                                if (jjtc001) {
                                    this.jjtree.clearNodeScope(jjtn001);
                                    jjtc001 = false;
                                } else {
                                    this.jjtree.popNode();
                                }
                                if (jjte001 instanceof RuntimeException) {
                                    throw (RuntimeException)jjte001;
                                }
                                if (jjte001 instanceof ParseException) {
                                    throw (ParseException)jjte001;
                                }
                                throw (Error)jjte001;
                            }
                            finally {
                                if (jjtc001) {
                                    this.jjtree.closeNodeScope((Node)jjtn001, 2);
                                }
                            }
                        }
                        default: {
                            this.jj_la1[3] = this.jj_gen;
                            break;
                        }
                    }
                    break;
                }
                default: {
                    this.jj_la1[4] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void LambdaExpression() throws ParseException {
        AstLambdaExpression jjtn000 = new AstLambdaExpression(7);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        try {
            this.LambdaParameters();
            this.jj_consume_token(57);
            if (this.jj_2_2(3)) {
                this.LambdaExpression();
                return;
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 9: 
                case 11: 
                case 12: 
                case 14: 
                case 16: 
                case 17: 
                case 18: 
                case 20: 
                case 22: 
                case 39: 
                case 40: 
                case 45: 
                case 49: 
                case 58: {
                    this.Choice();
                    return;
                }
                default: {
                    this.jj_la1[5] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (!(jjte000 instanceof ParseException)) throw (Error)jjte000;
            throw (ParseException)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void LambdaParameters() throws ParseException {
        AstLambdaParameters jjtn000 = new AstLambdaParameters(8);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        try {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 58: {
                    this.Identifier();
                    return;
                }
                case 20: {
                    this.jj_consume_token(20);
                    block6 : switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 58: {
                            this.Identifier();
                            while (true) {
                                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                                    case 25: {
                                        break;
                                    }
                                    default: {
                                        this.jj_la1[6] = this.jj_gen;
                                        break block6;
                                    }
                                }
                                this.jj_consume_token(25);
                                this.Identifier();
                            }
                        }
                        default: {
                            this.jj_la1[7] = this.jj_gen;
                        }
                    }
                    this.jj_consume_token(21);
                    return;
                }
                default: {
                    this.jj_la1[8] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (!(jjte000 instanceof ParseException)) throw (Error)jjte000;
            throw (ParseException)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    public final void Choice() throws ParseException {
        this.Or();
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 50: {
                this.jj_consume_token(50);
                this.Choice();
                this.jj_consume_token(24);
                AstChoice jjtn001 = new AstChoice(9);
                boolean jjtc001 = true;
                this.jjtree.openNodeScope(jjtn001);
                try {
                    this.Choice();
                    break;
                }
                catch (Throwable jjte001) {
                    if (jjtc001) {
                        this.jjtree.clearNodeScope(jjtn001);
                        jjtc001 = false;
                    } else {
                        this.jjtree.popNode();
                    }
                    if (jjte001 instanceof RuntimeException) {
                        throw (RuntimeException)jjte001;
                    }
                    if (jjte001 instanceof ParseException) {
                        throw (ParseException)jjte001;
                    }
                    throw (Error)jjte001;
                }
                finally {
                    if (jjtc001) {
                        this.jjtree.closeNodeScope((Node)jjtn001, 3);
                    }
                }
            }
            default: {
                this.jj_la1[9] = this.jj_gen;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void Or() throws ParseException {
        this.And();
        while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 43: 
                case 44: {
                    break;
                }
                default: {
                    this.jj_la1[10] = this.jj_gen;
                    return;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 43: {
                    this.jj_consume_token(43);
                    break;
                }
                case 44: {
                    this.jj_consume_token(44);
                    break;
                }
                default: {
                    this.jj_la1[11] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            AstOr jjtn001 = new AstOr(10);
            boolean jjtc001 = true;
            this.jjtree.openNodeScope(jjtn001);
            try {
                this.And();
                continue;
            }
            catch (Throwable jjte001) {
                if (jjtc001) {
                    this.jjtree.clearNodeScope(jjtn001);
                    jjtc001 = false;
                } else {
                    this.jjtree.popNode();
                }
                if (jjte001 instanceof RuntimeException) {
                    throw (RuntimeException)jjte001;
                }
                if (!(jjte001 instanceof ParseException)) throw (Error)jjte001;
                throw (ParseException)jjte001;
            }
            finally {
                if (!jjtc001) continue;
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                continue;
            }
            break;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void And() throws ParseException {
        this.Equality();
        while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 41: 
                case 42: {
                    break;
                }
                default: {
                    this.jj_la1[12] = this.jj_gen;
                    return;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 41: {
                    this.jj_consume_token(41);
                    break;
                }
                case 42: {
                    this.jj_consume_token(42);
                    break;
                }
                default: {
                    this.jj_la1[13] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            AstAnd jjtn001 = new AstAnd(11);
            boolean jjtc001 = true;
            this.jjtree.openNodeScope(jjtn001);
            try {
                this.Equality();
                continue;
            }
            catch (Throwable jjte001) {
                if (jjtc001) {
                    this.jjtree.clearNodeScope(jjtn001);
                    jjtc001 = false;
                } else {
                    this.jjtree.popNode();
                }
                if (jjte001 instanceof RuntimeException) {
                    throw (RuntimeException)jjte001;
                }
                if (!(jjte001 instanceof ParseException)) throw (Error)jjte001;
                throw (ParseException)jjte001;
            }
            finally {
                if (!jjtc001) continue;
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                continue;
            }
            break;
        }
    }

    public final void Equality() throws ParseException {
        this.Compare();
        block25: while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 35: 
                case 36: 
                case 37: 
                case 38: {
                    break;
                }
                default: {
                    this.jj_la1[14] = this.jj_gen;
                    break block25;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 35: 
                case 36: {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 35: {
                            this.jj_consume_token(35);
                            break;
                        }
                        case 36: {
                            this.jj_consume_token(36);
                            break;
                        }
                        default: {
                            this.jj_la1[15] = this.jj_gen;
                            this.jj_consume_token(-1);
                            throw new ParseException();
                        }
                    }
                    AstEqual jjtn001 = new AstEqual(12);
                    boolean jjtc001 = true;
                    this.jjtree.openNodeScope(jjtn001);
                    try {
                        this.Compare();
                        continue block25;
                    }
                    catch (Throwable jjte001) {
                        if (jjtc001) {
                            this.jjtree.clearNodeScope(jjtn001);
                            jjtc001 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte001 instanceof RuntimeException) {
                            throw (RuntimeException)jjte001;
                        }
                        if (jjte001 instanceof ParseException) {
                            throw (ParseException)jjte001;
                        }
                        throw (Error)jjte001;
                    }
                    finally {
                        if (!jjtc001) continue block25;
                        this.jjtree.closeNodeScope((Node)jjtn001, 2);
                        continue block25;
                    }
                }
                case 37: 
                case 38: {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 37: {
                            this.jj_consume_token(37);
                            break;
                        }
                        case 38: {
                            this.jj_consume_token(38);
                            break;
                        }
                        default: {
                            this.jj_la1[16] = this.jj_gen;
                            this.jj_consume_token(-1);
                            throw new ParseException();
                        }
                    }
                    AstNotEqual jjtn002 = new AstNotEqual(13);
                    boolean jjtc002 = true;
                    this.jjtree.openNodeScope(jjtn002);
                    try {
                        this.Compare();
                        continue block25;
                    }
                    catch (Throwable jjte002) {
                        if (jjtc002) {
                            this.jjtree.clearNodeScope(jjtn002);
                            jjtc002 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte002 instanceof RuntimeException) {
                            throw (RuntimeException)jjte002;
                        }
                        if (jjte002 instanceof ParseException) {
                            throw (ParseException)jjte002;
                        }
                        throw (Error)jjte002;
                    }
                    finally {
                        if (!jjtc002) continue block25;
                        this.jjtree.closeNodeScope((Node)jjtn002, 2);
                        continue block25;
                    }
                }
                default: {
                    this.jj_la1[17] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            break;
        }
    }

    public final void Compare() throws ParseException {
        this.Concatenation();
        block45: while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 27: 
                case 28: 
                case 29: 
                case 30: 
                case 31: 
                case 32: 
                case 33: 
                case 34: {
                    break;
                }
                default: {
                    this.jj_la1[18] = this.jj_gen;
                    break block45;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 29: 
                case 30: {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 29: {
                            this.jj_consume_token(29);
                            break;
                        }
                        case 30: {
                            this.jj_consume_token(30);
                            break;
                        }
                        default: {
                            this.jj_la1[19] = this.jj_gen;
                            this.jj_consume_token(-1);
                            throw new ParseException();
                        }
                    }
                    AstLessThan jjtn001 = new AstLessThan(14);
                    boolean jjtc001 = true;
                    this.jjtree.openNodeScope(jjtn001);
                    try {
                        this.Concatenation();
                        continue block45;
                    }
                    catch (Throwable jjte001) {
                        if (jjtc001) {
                            this.jjtree.clearNodeScope(jjtn001);
                            jjtc001 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte001 instanceof RuntimeException) {
                            throw (RuntimeException)jjte001;
                        }
                        if (jjte001 instanceof ParseException) {
                            throw (ParseException)jjte001;
                        }
                        throw (Error)jjte001;
                    }
                    finally {
                        if (!jjtc001) continue block45;
                        this.jjtree.closeNodeScope((Node)jjtn001, 2);
                        continue block45;
                    }
                }
                case 27: 
                case 28: {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 27: {
                            this.jj_consume_token(27);
                            break;
                        }
                        case 28: {
                            this.jj_consume_token(28);
                            break;
                        }
                        default: {
                            this.jj_la1[20] = this.jj_gen;
                            this.jj_consume_token(-1);
                            throw new ParseException();
                        }
                    }
                    AstGreaterThan jjtn002 = new AstGreaterThan(15);
                    boolean jjtc002 = true;
                    this.jjtree.openNodeScope(jjtn002);
                    try {
                        this.Concatenation();
                        continue block45;
                    }
                    catch (Throwable jjte002) {
                        if (jjtc002) {
                            this.jjtree.clearNodeScope(jjtn002);
                            jjtc002 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte002 instanceof RuntimeException) {
                            throw (RuntimeException)jjte002;
                        }
                        if (jjte002 instanceof ParseException) {
                            throw (ParseException)jjte002;
                        }
                        throw (Error)jjte002;
                    }
                    finally {
                        if (!jjtc002) continue block45;
                        this.jjtree.closeNodeScope((Node)jjtn002, 2);
                        continue block45;
                    }
                }
                case 33: 
                case 34: {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 33: {
                            this.jj_consume_token(33);
                            break;
                        }
                        case 34: {
                            this.jj_consume_token(34);
                            break;
                        }
                        default: {
                            this.jj_la1[21] = this.jj_gen;
                            this.jj_consume_token(-1);
                            throw new ParseException();
                        }
                    }
                    AstLessThanEqual jjtn003 = new AstLessThanEqual(16);
                    boolean jjtc003 = true;
                    this.jjtree.openNodeScope(jjtn003);
                    try {
                        this.Concatenation();
                        continue block45;
                    }
                    catch (Throwable jjte003) {
                        if (jjtc003) {
                            this.jjtree.clearNodeScope(jjtn003);
                            jjtc003 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte003 instanceof RuntimeException) {
                            throw (RuntimeException)jjte003;
                        }
                        if (jjte003 instanceof ParseException) {
                            throw (ParseException)jjte003;
                        }
                        throw (Error)jjte003;
                    }
                    finally {
                        if (!jjtc003) continue block45;
                        this.jjtree.closeNodeScope((Node)jjtn003, 2);
                        continue block45;
                    }
                }
                case 31: 
                case 32: {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 31: {
                            this.jj_consume_token(31);
                            break;
                        }
                        case 32: {
                            this.jj_consume_token(32);
                            break;
                        }
                        default: {
                            this.jj_la1[22] = this.jj_gen;
                            this.jj_consume_token(-1);
                            throw new ParseException();
                        }
                    }
                    AstGreaterThanEqual jjtn004 = new AstGreaterThanEqual(17);
                    boolean jjtc004 = true;
                    this.jjtree.openNodeScope(jjtn004);
                    try {
                        this.Concatenation();
                        continue block45;
                    }
                    catch (Throwable jjte004) {
                        if (jjtc004) {
                            this.jjtree.clearNodeScope(jjtn004);
                            jjtc004 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte004 instanceof RuntimeException) {
                            throw (RuntimeException)jjte004;
                        }
                        if (jjte004 instanceof ParseException) {
                            throw (ParseException)jjte004;
                        }
                        throw (Error)jjte004;
                    }
                    finally {
                        if (!jjtc004) continue block45;
                        this.jjtree.closeNodeScope((Node)jjtn004, 2);
                        continue block45;
                    }
                }
                default: {
                    this.jj_la1[23] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            break;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void Concatenation() throws ParseException {
        this.Math();
        while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 55: {
                    break;
                }
                default: {
                    this.jj_la1[24] = this.jj_gen;
                    return;
                }
            }
            this.jj_consume_token(55);
            AstConcat jjtn001 = new AstConcat(18);
            boolean jjtc001 = true;
            this.jjtree.openNodeScope(jjtn001);
            try {
                this.Math();
                continue;
            }
            catch (Throwable jjte001) {
                if (jjtc001) {
                    this.jjtree.clearNodeScope(jjtn001);
                    jjtc001 = false;
                } else {
                    this.jjtree.popNode();
                }
                if (jjte001 instanceof RuntimeException) {
                    throw (RuntimeException)jjte001;
                }
                if (!(jjte001 instanceof ParseException)) throw (Error)jjte001;
                throw (ParseException)jjte001;
            }
            finally {
                if (!jjtc001) continue;
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                continue;
            }
            break;
        }
    }

    public final void Math() throws ParseException {
        this.Multiplication();
        block17: while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 48: 
                case 49: {
                    break;
                }
                default: {
                    this.jj_la1[25] = this.jj_gen;
                    break block17;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 48: {
                    this.jj_consume_token(48);
                    AstPlus jjtn001 = new AstPlus(19);
                    boolean jjtc001 = true;
                    this.jjtree.openNodeScope(jjtn001);
                    try {
                        this.Multiplication();
                        continue block17;
                    }
                    catch (Throwable jjte001) {
                        if (jjtc001) {
                            this.jjtree.clearNodeScope(jjtn001);
                            jjtc001 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte001 instanceof RuntimeException) {
                            throw (RuntimeException)jjte001;
                        }
                        if (jjte001 instanceof ParseException) {
                            throw (ParseException)jjte001;
                        }
                        throw (Error)jjte001;
                    }
                    finally {
                        if (!jjtc001) continue block17;
                        this.jjtree.closeNodeScope((Node)jjtn001, 2);
                        continue block17;
                    }
                }
                case 49: {
                    this.jj_consume_token(49);
                    AstMinus jjtn002 = new AstMinus(20);
                    boolean jjtc002 = true;
                    this.jjtree.openNodeScope(jjtn002);
                    try {
                        this.Multiplication();
                        continue block17;
                    }
                    catch (Throwable jjte002) {
                        if (jjtc002) {
                            this.jjtree.clearNodeScope(jjtn002);
                            jjtc002 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte002 instanceof RuntimeException) {
                            throw (RuntimeException)jjte002;
                        }
                        if (jjte002 instanceof ParseException) {
                            throw (ParseException)jjte002;
                        }
                        throw (Error)jjte002;
                    }
                    finally {
                        if (!jjtc002) continue block17;
                        this.jjtree.closeNodeScope((Node)jjtn002, 2);
                        continue block17;
                    }
                }
                default: {
                    this.jj_la1[26] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            break;
        }
    }

    public final void Multiplication() throws ParseException {
        this.Unary();
        block31: while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 47: 
                case 51: 
                case 52: 
                case 53: 
                case 54: {
                    break;
                }
                default: {
                    this.jj_la1[27] = this.jj_gen;
                    break block31;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 47: {
                    this.jj_consume_token(47);
                    AstMult jjtn001 = new AstMult(21);
                    boolean jjtc001 = true;
                    this.jjtree.openNodeScope(jjtn001);
                    try {
                        this.Unary();
                        continue block31;
                    }
                    catch (Throwable jjte001) {
                        if (jjtc001) {
                            this.jjtree.clearNodeScope(jjtn001);
                            jjtc001 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte001 instanceof RuntimeException) {
                            throw (RuntimeException)jjte001;
                        }
                        if (jjte001 instanceof ParseException) {
                            throw (ParseException)jjte001;
                        }
                        throw (Error)jjte001;
                    }
                    finally {
                        if (!jjtc001) continue block31;
                        this.jjtree.closeNodeScope((Node)jjtn001, 2);
                        continue block31;
                    }
                }
                case 51: 
                case 52: {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 51: {
                            this.jj_consume_token(51);
                            break;
                        }
                        case 52: {
                            this.jj_consume_token(52);
                            break;
                        }
                        default: {
                            this.jj_la1[28] = this.jj_gen;
                            this.jj_consume_token(-1);
                            throw new ParseException();
                        }
                    }
                    AstDiv jjtn002 = new AstDiv(22);
                    boolean jjtc002 = true;
                    this.jjtree.openNodeScope(jjtn002);
                    try {
                        this.Unary();
                        continue block31;
                    }
                    catch (Throwable jjte002) {
                        if (jjtc002) {
                            this.jjtree.clearNodeScope(jjtn002);
                            jjtc002 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte002 instanceof RuntimeException) {
                            throw (RuntimeException)jjte002;
                        }
                        if (jjte002 instanceof ParseException) {
                            throw (ParseException)jjte002;
                        }
                        throw (Error)jjte002;
                    }
                    finally {
                        if (!jjtc002) continue block31;
                        this.jjtree.closeNodeScope((Node)jjtn002, 2);
                        continue block31;
                    }
                }
                case 53: 
                case 54: {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 53: {
                            this.jj_consume_token(53);
                            break;
                        }
                        case 54: {
                            this.jj_consume_token(54);
                            break;
                        }
                        default: {
                            this.jj_la1[29] = this.jj_gen;
                            this.jj_consume_token(-1);
                            throw new ParseException();
                        }
                    }
                    AstMod jjtn003 = new AstMod(23);
                    boolean jjtc003 = true;
                    this.jjtree.openNodeScope(jjtn003);
                    try {
                        this.Unary();
                        continue block31;
                    }
                    catch (Throwable jjte003) {
                        if (jjtc003) {
                            this.jjtree.clearNodeScope(jjtn003);
                            jjtc003 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte003 instanceof RuntimeException) {
                            throw (RuntimeException)jjte003;
                        }
                        if (jjte003 instanceof ParseException) {
                            throw (ParseException)jjte003;
                        }
                        throw (Error)jjte003;
                    }
                    finally {
                        if (!jjtc003) continue block31;
                        this.jjtree.closeNodeScope((Node)jjtn003, 2);
                        continue block31;
                    }
                }
                default: {
                    this.jj_la1[30] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            break;
        }
    }

    public final void Unary() throws ParseException {
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 49: {
                this.jj_consume_token(49);
                AstNegative jjtn001 = new AstNegative(24);
                boolean jjtc001 = true;
                this.jjtree.openNodeScope(jjtn001);
                try {
                    this.Unary();
                    break;
                }
                catch (Throwable jjte001) {
                    if (jjtc001) {
                        this.jjtree.clearNodeScope(jjtn001);
                        jjtc001 = false;
                    } else {
                        this.jjtree.popNode();
                    }
                    if (jjte001 instanceof RuntimeException) {
                        throw (RuntimeException)jjte001;
                    }
                    if (jjte001 instanceof ParseException) {
                        throw (ParseException)jjte001;
                    }
                    throw (Error)jjte001;
                }
                finally {
                    if (jjtc001) {
                        this.jjtree.closeNodeScope((Node)jjtn001, true);
                    }
                }
            }
            case 39: 
            case 40: {
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 39: {
                        this.jj_consume_token(39);
                        break;
                    }
                    case 40: {
                        this.jj_consume_token(40);
                        break;
                    }
                    default: {
                        this.jj_la1[31] = this.jj_gen;
                        this.jj_consume_token(-1);
                        throw new ParseException();
                    }
                }
                AstNot jjtn002 = new AstNot(25);
                boolean jjtc002 = true;
                this.jjtree.openNodeScope(jjtn002);
                try {
                    this.Unary();
                    break;
                }
                catch (Throwable jjte002) {
                    if (jjtc002) {
                        this.jjtree.clearNodeScope(jjtn002);
                        jjtc002 = false;
                    } else {
                        this.jjtree.popNode();
                    }
                    if (jjte002 instanceof RuntimeException) {
                        throw (RuntimeException)jjte002;
                    }
                    if (jjte002 instanceof ParseException) {
                        throw (ParseException)jjte002;
                    }
                    throw (Error)jjte002;
                }
                finally {
                    if (jjtc002) {
                        this.jjtree.closeNodeScope((Node)jjtn002, true);
                    }
                }
            }
            case 45: {
                this.jj_consume_token(45);
                AstEmpty jjtn003 = new AstEmpty(26);
                boolean jjtc003 = true;
                this.jjtree.openNodeScope(jjtn003);
                try {
                    this.Unary();
                    break;
                }
                catch (Throwable jjte003) {
                    if (jjtc003) {
                        this.jjtree.clearNodeScope(jjtn003);
                        jjtc003 = false;
                    } else {
                        this.jjtree.popNode();
                    }
                    if (jjte003 instanceof RuntimeException) {
                        throw (RuntimeException)jjte003;
                    }
                    if (jjte003 instanceof ParseException) {
                        throw (ParseException)jjte003;
                    }
                    throw (Error)jjte003;
                }
                finally {
                    if (jjtc003) {
                        this.jjtree.closeNodeScope((Node)jjtn003, true);
                    }
                }
            }
            case 9: 
            case 11: 
            case 12: 
            case 14: 
            case 16: 
            case 17: 
            case 18: 
            case 20: 
            case 22: 
            case 58: {
                this.Value();
                break;
            }
            default: {
                this.jj_la1[32] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
            }
        }
    }

    public final void Value() throws ParseException {
        AstValue jjtn001 = new AstValue(27);
        boolean jjtc001 = true;
        this.jjtree.openNodeScope(jjtn001);
        try {
            this.ValuePrefix();
            block7: while (true) {
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 19: 
                    case 22: {
                        break;
                    }
                    default: {
                        this.jj_la1[33] = this.jj_gen;
                        break block7;
                    }
                }
                this.ValueSuffix();
            }
            if (jjtc001) {
                this.jjtree.closeNodeScope((Node)jjtn001, this.jjtree.nodeArity() > 1);
            }
        }
        catch (Throwable jjte001) {
            try {
                if (jjtc001) {
                    this.jjtree.clearNodeScope(jjtn001);
                    jjtc001 = false;
                } else {
                    this.jjtree.popNode();
                }
                if (jjte001 instanceof RuntimeException) {
                    throw (RuntimeException)jjte001;
                }
                if (jjte001 instanceof ParseException) {
                    throw (ParseException)jjte001;
                }
                throw (Error)jjte001;
            }
            catch (Throwable throwable) {
                if (jjtc001) {
                    this.jjtree.closeNodeScope((Node)jjtn001, this.jjtree.nodeArity() > 1);
                }
                throw throwable;
            }
        }
    }

    public final void ValuePrefix() throws ParseException {
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 11: 
            case 12: 
            case 14: 
            case 16: 
            case 17: 
            case 18: {
                this.Literal();
                break;
            }
            case 9: 
            case 20: 
            case 22: 
            case 58: {
                this.NonLiteral();
                break;
            }
            default: {
                this.jj_la1[34] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
            }
        }
    }

    public final void ValueSuffix() throws ParseException {
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 19: {
                this.DotSuffix();
                break;
            }
            case 22: {
                this.BracketSuffix();
                break;
            }
            default: {
                this.jj_la1[35] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void DotSuffix() throws ParseException {
        AstDotSuffix jjtn000 = new AstDotSuffix(28);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        Token t2 = null;
        try {
            this.jj_consume_token(19);
            t2 = this.jj_consume_token(58);
            jjtn000.setImage(t2.image);
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 20: {
                    this.MethodArguments();
                    return;
                }
                default: {
                    this.jj_la1[36] = this.jj_gen;
                    return;
                }
            }
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (!(jjte000 instanceof ParseException)) throw (Error)jjte000;
            throw (ParseException)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void BracketSuffix() throws ParseException {
        AstBracketSuffix jjtn000 = new AstBracketSuffix(29);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        try {
            this.jj_consume_token(22);
            this.Expression();
            this.jj_consume_token(23);
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 20: {
                    this.MethodArguments();
                    return;
                }
                default: {
                    this.jj_la1[37] = this.jj_gen;
                    return;
                }
            }
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (!(jjte000 instanceof ParseException)) throw (Error)jjte000;
            throw (ParseException)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    public final void MethodArguments() throws ParseException {
        AstMethodArguments jjtn000 = new AstMethodArguments(30);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        try {
            this.jj_consume_token(20);
            block2 : switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 9: 
                case 11: 
                case 12: 
                case 14: 
                case 16: 
                case 17: 
                case 18: 
                case 20: 
                case 22: 
                case 39: 
                case 40: 
                case 45: 
                case 49: 
                case 58: {
                    this.Expression();
                    while (true) {
                        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                            case 25: {
                                break;
                            }
                            default: {
                                this.jj_la1[38] = this.jj_gen;
                                break block2;
                            }
                        }
                        this.jj_consume_token(25);
                        this.Expression();
                    }
                }
                default: {
                    this.jj_la1[39] = this.jj_gen;
                }
            }
            this.jj_consume_token(21);
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    public final void LambdaExpressionOrCall() throws ParseException {
        block19: {
            AstLambdaExpression jjtn000 = new AstLambdaExpression(7);
            boolean jjtc000 = true;
            this.jjtree.openNodeScope(jjtn000);
            try {
                this.jj_consume_token(20);
                this.LambdaParameters();
                this.jj_consume_token(57);
                if (this.jj_2_3(3)) {
                    this.LambdaExpression();
                } else {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 9: 
                        case 11: 
                        case 12: 
                        case 14: 
                        case 16: 
                        case 17: 
                        case 18: 
                        case 20: 
                        case 22: 
                        case 39: 
                        case 40: 
                        case 45: 
                        case 49: 
                        case 58: {
                            this.Choice();
                            break;
                        }
                        default: {
                            this.jj_la1[40] = this.jj_gen;
                            this.jj_consume_token(-1);
                            throw new ParseException();
                        }
                    }
                }
                this.jj_consume_token(21);
                while (true) {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 20: {
                            break;
                        }
                        default: {
                            this.jj_la1[41] = this.jj_gen;
                            break block19;
                        }
                    }
                    this.MethodArguments();
                }
            }
            catch (Throwable jjte000) {
                if (jjtc000) {
                    this.jjtree.clearNodeScope(jjtn000);
                    jjtc000 = false;
                } else {
                    this.jjtree.popNode();
                }
                if (jjte000 instanceof RuntimeException) {
                    throw (RuntimeException)jjte000;
                }
                if (jjte000 instanceof ParseException) {
                    throw (ParseException)jjte000;
                }
                throw (Error)jjte000;
            }
            finally {
                if (jjtc000) {
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                }
            }
        }
    }

    public final void NonLiteral() throws ParseException {
        if (this.jj_2_4(4)) {
            this.LambdaExpressionOrCall();
        } else {
            block0 : switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 20: {
                    this.jj_consume_token(20);
                    this.Expression();
                    this.jj_consume_token(21);
                    break;
                }
                default: {
                    this.jj_la1[42] = this.jj_gen;
                    if (this.jj_2_5(4)) {
                        this.Function();
                        break;
                    }
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 58: {
                            this.Identifier();
                            break block0;
                        }
                        case 9: {
                            this.MapData();
                            break block0;
                        }
                        case 22: {
                            this.ListData();
                            break block0;
                        }
                    }
                    this.jj_la1[43] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
    }

    public final void MapData() throws ParseException {
        AstMapData jjtn000 = new AstMapData(31);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        try {
            this.jj_consume_token(9);
            block2 : switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 9: 
                case 11: 
                case 12: 
                case 14: 
                case 16: 
                case 17: 
                case 18: 
                case 20: 
                case 22: 
                case 39: 
                case 40: 
                case 45: 
                case 49: 
                case 58: {
                    this.MapEntry();
                    while (true) {
                        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                            case 25: {
                                break;
                            }
                            default: {
                                this.jj_la1[44] = this.jj_gen;
                                break block2;
                            }
                        }
                        this.jj_consume_token(25);
                        this.MapEntry();
                    }
                }
                default: {
                    this.jj_la1[45] = this.jj_gen;
                }
            }
            this.jj_consume_token(10);
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void MapEntry() throws ParseException {
        AstMapEntry jjtn000 = new AstMapEntry(32);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        try {
            this.Expression();
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 24: {
                    this.jj_consume_token(24);
                    this.Expression();
                    return;
                }
                default: {
                    this.jj_la1[46] = this.jj_gen;
                    return;
                }
            }
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (!(jjte000 instanceof ParseException)) throw (Error)jjte000;
            throw (ParseException)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    public final void ListData() throws ParseException {
        AstListData jjtn000 = new AstListData(33);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        try {
            this.jj_consume_token(22);
            block2 : switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 9: 
                case 11: 
                case 12: 
                case 14: 
                case 16: 
                case 17: 
                case 18: 
                case 20: 
                case 22: 
                case 39: 
                case 40: 
                case 45: 
                case 49: 
                case 58: {
                    this.Expression();
                    while (true) {
                        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                            case 25: {
                                break;
                            }
                            default: {
                                this.jj_la1[47] = this.jj_gen;
                                break block2;
                            }
                        }
                        this.jj_consume_token(25);
                        this.Expression();
                    }
                }
                default: {
                    this.jj_la1[48] = this.jj_gen;
                }
            }
            this.jj_consume_token(23);
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void Identifier() throws ParseException {
        AstIdentifier jjtn000 = new AstIdentifier(34);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        Token t2 = null;
        try {
            t2 = this.jj_consume_token(58);
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            jjtn000.setImage(t2.image);
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    public final void Function() throws ParseException {
        AstFunction jjtn000 = new AstFunction(35);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        Token t0 = null;
        Token t1 = null;
        try {
            t0 = this.jj_consume_token(58);
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 24: {
                    this.jj_consume_token(24);
                    t1 = this.jj_consume_token(58);
                    break;
                }
                default: {
                    this.jj_la1[49] = this.jj_gen;
                }
            }
            if (t1 != null) {
                jjtn000.setPrefix(t0.image);
                jjtn000.setLocalName(t1.image);
            } else {
                jjtn000.setLocalName(t0.image);
            }
            block11: while (true) {
                this.MethodArguments();
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 20: {
                        continue block11;
                    }
                }
                break;
            }
            this.jj_la1[50] = this.jj_gen;
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    public final void Literal() throws ParseException {
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 16: 
            case 17: {
                this.Boolean();
                break;
            }
            case 12: {
                this.FloatingPoint();
                break;
            }
            case 11: {
                this.Integer();
                break;
            }
            case 14: {
                this.String();
                break;
            }
            case 18: {
                this.Null();
                break;
            }
            default: {
                this.jj_la1[51] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void Boolean() throws ParseException {
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 16: {
                AstTrue jjtn001 = new AstTrue(36);
                boolean jjtc001 = true;
                this.jjtree.openNodeScope(jjtn001);
                try {
                    this.jj_consume_token(16);
                    break;
                }
                finally {
                    if (jjtc001) {
                        this.jjtree.closeNodeScope((Node)jjtn001, true);
                    }
                }
            }
            case 17: {
                AstFalse jjtn002 = new AstFalse(37);
                boolean jjtc002 = true;
                this.jjtree.openNodeScope(jjtn002);
                try {
                    this.jj_consume_token(17);
                    break;
                }
                finally {
                    if (jjtc002) {
                        this.jjtree.closeNodeScope((Node)jjtn002, true);
                    }
                }
            }
            default: {
                this.jj_la1[52] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void FloatingPoint() throws ParseException {
        AstFloatingPoint jjtn000 = new AstFloatingPoint(38);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        Token t2 = null;
        try {
            t2 = this.jj_consume_token(12);
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            jjtn000.setImage(t2.image);
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void Integer() throws ParseException {
        AstInteger jjtn000 = new AstInteger(39);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        Token t2 = null;
        try {
            t2 = this.jj_consume_token(11);
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            jjtn000.setImage(t2.image);
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void String() throws ParseException {
        AstString jjtn000 = new AstString(40);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        Token t2 = null;
        try {
            t2 = this.jj_consume_token(14);
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            jjtn000.setImage(t2.image);
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void Null() throws ParseException {
        AstNull jjtn000 = new AstNull(41);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        try {
            this.jj_consume_token(18);
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean jj_2_1(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_1();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(0, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean jj_2_2(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_2();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(1, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean jj_2_3(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_3();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(2, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean jj_2_4(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_4();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(3, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean jj_2_5(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_5();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(4, xla);
        }
    }

    private boolean jj_3R_89() {
        if (this.jj_scan_token(22)) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_102()) {
            this.jj_scanpos = xsp;
        }
        return this.jj_scan_token(23);
    }

    private boolean jj_3R_33() {
        return this.jj_scan_token(25);
    }

    private boolean jj_3R_31() {
        Token xsp;
        if (this.jj_3R_34()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_49());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_47() {
        return this.jj_scan_token(50);
    }

    private boolean jj_3R_103() {
        return this.jj_3R_35();
    }

    private boolean jj_3R_101() {
        return this.jj_3R_103();
    }

    private boolean jj_3R_27() {
        if (this.jj_3R_31()) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_47()) {
            this.jj_scanpos = xsp;
        }
        return false;
    }

    private boolean jj_3R_79() {
        return this.jj_3R_89();
    }

    private boolean jj_3R_88() {
        if (this.jj_scan_token(9)) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_101()) {
            this.jj_scanpos = xsp;
        }
        return this.jj_scan_token(10);
    }

    private boolean jj_3R_78() {
        return this.jj_3R_88();
    }

    private boolean jj_3R_77() {
        return this.jj_3R_29();
    }

    private boolean jj_3_5() {
        return this.jj_3R_19();
    }

    private boolean jj_3R_30() {
        Token xsp;
        if (this.jj_3R_29()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_33());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_76() {
        if (this.jj_scan_token(20)) {
            return true;
        }
        return this.jj_3R_35();
    }

    private boolean jj_3R_36() {
        return this.jj_scan_token(25);
    }

    private boolean jj_3_4() {
        return this.jj_3R_18();
    }

    private boolean jj_3R_69() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3_4()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_76()) {
                this.jj_scanpos = xsp;
                if (this.jj_3_5()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_77()) {
                        this.jj_scanpos = xsp;
                        if (this.jj_3R_78()) {
                            this.jj_scanpos = xsp;
                            if (this.jj_3R_79()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_26() {
        if (this.jj_scan_token(20)) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_30()) {
            this.jj_scanpos = xsp;
        }
        return this.jj_scan_token(21);
    }

    private boolean jj_3R_20() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_25()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_26()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_25() {
        return this.jj_3R_29();
    }

    private boolean jj_3R_45() {
        return this.jj_scan_token(56);
    }

    private boolean jj_3_2() {
        return this.jj_3R_17();
    }

    private boolean jj_3_3() {
        return this.jj_3R_17();
    }

    private boolean jj_3R_17() {
        if (this.jj_3R_20()) {
            return true;
        }
        if (this.jj_scan_token(57)) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3_2()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_21()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_32() {
        Token xsp;
        if (this.jj_3R_35()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_36());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_41() {
        return this.jj_scan_token(26);
    }

    private boolean jj_3R_18() {
        if (this.jj_scan_token(20)) {
            return true;
        }
        if (this.jj_3R_20()) {
            return true;
        }
        if (this.jj_scan_token(57)) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3_3()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_22()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_43() {
        if (this.jj_3R_27()) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_45()) {
            this.jj_scanpos = xsp;
        }
        return false;
    }

    private boolean jj_3R_40() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3_1()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_43()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3_1() {
        return this.jj_3R_17();
    }

    private boolean jj_3R_28() {
        if (this.jj_scan_token(20)) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_32()) {
            this.jj_scanpos = xsp;
        }
        return this.jj_scan_token(21);
    }

    private boolean jj_3R_38() {
        Token xsp;
        if (this.jj_3R_40()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_41());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_100() {
        return this.jj_scan_token(22);
    }

    private boolean jj_3R_35() {
        return this.jj_3R_38();
    }

    private boolean jj_3R_98() {
        return this.jj_3R_100();
    }

    private boolean jj_3R_99() {
        return this.jj_scan_token(19);
    }

    private boolean jj_3R_97() {
        return this.jj_3R_99();
    }

    private boolean jj_3R_96() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_97()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_98()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_95() {
        return this.jj_3R_96();
    }

    private boolean jj_3R_62() {
        return this.jj_3R_69();
    }

    private boolean jj_3R_57() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_61()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_62()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_61() {
        return this.jj_3R_68();
    }

    private boolean jj_3R_55() {
        Token xsp;
        if (this.jj_3R_57()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_95());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_53() {
        return this.jj_3R_55();
    }

    private boolean jj_3R_52() {
        if (this.jj_scan_token(45)) {
            return true;
        }
        return this.jj_3R_48();
    }

    private boolean jj_3R_51() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(39)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(40)) {
                return true;
            }
        }
        return this.jj_3R_48();
    }

    private boolean jj_3R_87() {
        return this.jj_scan_token(18);
    }

    private boolean jj_3R_48() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_50()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_51()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_52()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_53()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_50() {
        if (this.jj_scan_token(49)) {
            return true;
        }
        return this.jj_3R_48();
    }

    private boolean jj_3R_86() {
        return this.jj_scan_token(14);
    }

    private boolean jj_3R_92() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(53)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(54)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_91() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(51)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(52)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_90() {
        return this.jj_scan_token(47);
    }

    private boolean jj_3R_80() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_90()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_91()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_92()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean jj_3R_85() {
        return this.jj_scan_token(11);
    }

    private boolean jj_3R_46() {
        Token xsp;
        if (this.jj_3R_48()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_80());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_84() {
        return this.jj_scan_token(12);
    }

    private boolean jj_3R_82() {
        return this.jj_scan_token(49);
    }

    private boolean jj_3R_70() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_81()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_82()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_81() {
        return this.jj_scan_token(48);
    }

    private boolean jj_3R_94() {
        return this.jj_scan_token(17);
    }

    private boolean jj_3R_93() {
        return this.jj_scan_token(16);
    }

    private boolean jj_3R_83() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_93()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_94()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_44() {
        Token xsp;
        if (this.jj_3R_46()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_70());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_63() {
        return this.jj_scan_token(55);
    }

    private boolean jj_3R_75() {
        return this.jj_3R_87();
    }

    private boolean jj_3R_74() {
        return this.jj_3R_86();
    }

    private boolean jj_3R_73() {
        return this.jj_3R_85();
    }

    private boolean jj_3R_72() {
        return this.jj_3R_84();
    }

    private boolean jj_3R_42() {
        Token xsp;
        if (this.jj_3R_44()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_63());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_67() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(31)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(32)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_68() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_71()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_72()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_73()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_74()) {
                        this.jj_scanpos = xsp;
                        if (this.jj_3R_75()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_71() {
        return this.jj_3R_83();
    }

    private boolean jj_3R_66() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(33)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(34)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_23() {
        if (this.jj_scan_token(24)) {
            return true;
        }
        return this.jj_scan_token(58);
    }

    private boolean jj_3R_65() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(27)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(28)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_58() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_64()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_65()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_66()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_67()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_64() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(29)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(30)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_24() {
        return this.jj_3R_28();
    }

    private boolean jj_3R_39() {
        Token xsp;
        if (this.jj_3R_42()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_58());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_60() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(37)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(38)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_19() {
        if (this.jj_scan_token(58)) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_23()) {
            this.jj_scanpos = xsp;
        }
        if (this.jj_3R_24()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_24());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_56() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_59()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_60()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_59() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(35)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(36)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_54() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(41)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(42)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_37() {
        Token xsp;
        if (this.jj_3R_39()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_56());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_29() {
        return this.jj_scan_token(58);
    }

    private boolean jj_3R_34() {
        Token xsp;
        if (this.jj_3R_37()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_54());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_102() {
        return this.jj_3R_35();
    }

    private boolean jj_3R_21() {
        return this.jj_3R_27();
    }

    private boolean jj_3R_49() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(43)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(44)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_22() {
        return this.jj_3R_27();
    }

    private static void jj_la1_init_0() {
        jj_la1_0 = new int[]{14, 14, 0x4000000, 0, 5724672, 5724672, 0x2000000, 0, 0x100000, 0, 0, 0, 0, 0, 0, 0, 0, 0, -134217728, 0x60000000, 0x18000000, 0, Integer.MIN_VALUE, -134217728, 0, 0, 0, 0, 0, 0, 0, 0, 5724672, 0x480000, 5724672, 0x480000, 0x100000, 0x100000, 0x2000000, 5724672, 5724672, 0x100000, 0x100000, 0x400200, 0x2000000, 5724672, 0x1000000, 0x2000000, 5724672, 0x1000000, 0x100000, 481280, 196608};
    }

    private static void jj_la1_init_1() {
        jj_la1_1 = new int[]{0, 0, 0, 0x1000000, 67248512, 67248512, 0, 0x4000000, 0x4000000, 262144, 6144, 6144, 1536, 1536, 120, 24, 96, 120, 7, 0, 0, 6, 1, 7, 0x800000, 196608, 196608, 0x788000, 0x180000, 0x600000, 0x788000, 384, 67248512, 0, 0x4000000, 0, 0, 0, 0, 67248512, 67248512, 0, 0, 0x4000000, 0, 67248512, 0, 0, 67248512, 0, 0, 0, 0};
    }

    public ELParser(InputStream stream) {
        this(stream, null);
    }

    public ELParser(InputStream stream, String encoding) {
        int i;
        try {
            this.jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        this.token_source = new ELParserTokenManager(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;
        for (i = 0; i < 53; ++i) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    public void ReInit(InputStream stream) {
        this.ReInit(stream, null);
    }

    public void ReInit(InputStream stream, String encoding) {
        int i;
        try {
            this.jj_input_stream.ReInit(stream, encoding, 1, 1);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        this.token_source.ReInit(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jjtree.reset();
        this.jj_gen = 0;
        for (i = 0; i < 53; ++i) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    public ELParser(Reader stream) {
        int i;
        this.jj_input_stream = new SimpleCharStream(stream, 1, 1);
        this.token_source = new ELParserTokenManager(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;
        for (i = 0; i < 53; ++i) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    public void ReInit(Reader stream) {
        int i;
        this.jj_input_stream.ReInit(stream, 1, 1);
        this.token_source.ReInit(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jjtree.reset();
        this.jj_gen = 0;
        for (i = 0; i < 53; ++i) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    public ELParser(ELParserTokenManager tm) {
        int i;
        this.token_source = tm;
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;
        for (i = 0; i < 53; ++i) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    public void ReInit(ELParserTokenManager tm) {
        int i;
        this.token_source = tm;
        this.token = new Token();
        this.jj_ntk = -1;
        this.jjtree.reset();
        this.jj_gen = 0;
        for (i = 0; i < 53; ++i) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    private Token jj_consume_token(int kind) throws ParseException {
        Token oldToken = this.token;
        this.token = oldToken.next != null ? this.token.next : (this.token.next = this.token_source.getNextToken());
        this.jj_ntk = -1;
        if (this.token.kind == kind) {
            ++this.jj_gen;
            if (++this.jj_gc > 100) {
                this.jj_gc = 0;
                for (int i = 0; i < this.jj_2_rtns.length; ++i) {
                    JJCalls c = this.jj_2_rtns[i];
                    while (c != null) {
                        if (c.gen < this.jj_gen) {
                            c.first = null;
                        }
                        c = c.next;
                    }
                }
            }
            return this.token;
        }
        this.token = oldToken;
        this.jj_kind = kind;
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
        if (this.jj_rescan) {
            int i = 0;
            Token tok = this.token;
            while (tok != null && tok != this.jj_scanpos) {
                ++i;
                tok = tok.next;
            }
            if (tok != null) {
                this.jj_add_error_token(kind, i);
            }
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
        ++this.jj_gen;
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

    private void jj_add_error_token(int kind, int pos) {
        if (pos >= 100) {
            return;
        }
        if (pos == this.jj_endpos + 1) {
            this.jj_lasttokens[this.jj_endpos++] = kind;
        } else if (this.jj_endpos != 0) {
            this.jj_expentry = new int[this.jj_endpos];
            for (int i = 0; i < this.jj_endpos; ++i) {
                this.jj_expentry[i] = this.jj_lasttokens[i];
            }
            block1: for (int[] oldentry : this.jj_expentries) {
                if (oldentry.length != this.jj_expentry.length) continue;
                for (int i = 0; i < this.jj_expentry.length; ++i) {
                    if (oldentry[i] != this.jj_expentry[i]) continue block1;
                }
                this.jj_expentries.add(this.jj_expentry);
                break;
            }
            if (pos != 0) {
                this.jj_endpos = pos;
                this.jj_lasttokens[this.jj_endpos - 1] = kind;
            }
        }
    }

    public ParseException generateParseException() {
        int i;
        this.jj_expentries.clear();
        boolean[] la1tokens = new boolean[63];
        if (this.jj_kind >= 0) {
            la1tokens[this.jj_kind] = true;
            this.jj_kind = -1;
        }
        for (i = 0; i < 53; ++i) {
            if (this.jj_la1[i] != this.jj_gen) continue;
            for (int j = 0; j < 32; ++j) {
                if ((jj_la1_0[i] & 1 << j) != 0) {
                    la1tokens[j] = true;
                }
                if ((jj_la1_1[i] & 1 << j) == 0) continue;
                la1tokens[32 + j] = true;
            }
        }
        for (i = 0; i < 63; ++i) {
            if (!la1tokens[i]) continue;
            this.jj_expentry = new int[1];
            this.jj_expentry[0] = i;
            this.jj_expentries.add(this.jj_expentry);
        }
        this.jj_endpos = 0;
        this.jj_rescan_token();
        this.jj_add_error_token(0, 0);
        int[][] exptokseq = new int[this.jj_expentries.size()][];
        for (int i2 = 0; i2 < this.jj_expentries.size(); ++i2) {
            exptokseq[i2] = this.jj_expentries.get(i2);
        }
        return new ParseException(this.token, exptokseq, tokenImage);
    }

    public final void enable_tracing() {
    }

    public final void disable_tracing() {
    }

    private void jj_rescan_token() {
        this.jj_rescan = true;
        for (int i = 0; i < 5; ++i) {
            try {
                JJCalls p = this.jj_2_rtns[i];
                do {
                    if (p.gen <= this.jj_gen) continue;
                    this.jj_la = p.arg;
                    this.jj_lastpos = this.jj_scanpos = p.first;
                    switch (i) {
                        case 0: {
                            this.jj_3_1();
                            break;
                        }
                        case 1: {
                            this.jj_3_2();
                            break;
                        }
                        case 2: {
                            this.jj_3_3();
                            break;
                        }
                        case 3: {
                            this.jj_3_4();
                            break;
                        }
                        case 4: {
                            this.jj_3_5();
                        }
                    }
                } while ((p = p.next) != null);
                continue;
            }
            catch (LookaheadSuccess lookaheadSuccess) {
                // empty catch block
            }
        }
        this.jj_rescan = false;
    }

    private void jj_save(int index, int xla) {
        JJCalls p = this.jj_2_rtns[index];
        while (p.gen > this.jj_gen) {
            if (p.next == null) {
                p = p.next = new JJCalls();
                break;
            }
            p = p.next;
        }
        p.gen = this.jj_gen + xla - this.jj_la;
        p.first = this.token;
        p.arg = xla;
    }

    static {
        ELParser.jj_la1_init_0();
        ELParser.jj_la1_init_1();
    }

    static final class JJCalls {
        int gen;
        Token first;
        int arg;
        JJCalls next;

        JJCalls() {
        }
    }

    private static final class LookaheadSuccess
    extends Error {
        private LookaheadSuccess() {
        }
    }
}

