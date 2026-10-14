/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.annotation.InternalApi;
import akka.io.dns.internal.MessageFlags$;
import java.io.Serializable;
import scala.Enumeration;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\tud!\u0002\u001d:\u0005f\n\u0005\u0002\u0003-\u0001\u0005+\u0007I\u0011A-\t\u0011u\u0003!\u0011#Q\u0001\niCQA\u0018\u0001\u0005\u0002}CQa\u0019\u0001\u0005\u0002\u0011DQ\u0001\u001b\u0001\u0005\u0002\u0011DQ!\u001b\u0001\u0005\u0002)DQa\u001d\u0001\u0005\u0002\u0011DQ\u0001\u001e\u0001\u0005\u0002\u0011DQ!\u001e\u0001\u0005\u0002\u0011DQA\u001e\u0001\u0005\u0002\u0011DQa\u001e\u0001\u0005\u0002aDQ! \u0001\u0005ByD\u0011\"a\u0004\u0001\u0003\u0003%\t!!\u0005\t\u0013\u0005U\u0001!%A\u0005\u0002\u0005]\u0001\"CA\u0017\u0001\u0005\u0005I\u0011IA\u0018\u0011%\ty\u0004AA\u0001\n\u0003\t\t\u0005C\u0005\u0002J\u0001\t\t\u0011\"\u0001\u0002L!I\u0011q\u000b\u0001\u0002\u0002\u0013\u0005\u0013\u0011\f\u0005\n\u0003O\u0002\u0011\u0011!C\u0001\u0003SB\u0011\"!\u001c\u0001\u0003\u0003%\t%a\u001c\t\u0013\u0005M\u0004!!A\u0005B\u0005U\u0004\"CA<\u0001\u0005\u0005I\u0011IA=\u000f!\tI)\u000fE\u0001s\u0005-ea\u0002\u001d:\u0011\u0003I\u0014Q\u0012\u0005\u0007=b!\t!!(\t\u000f\u0005}\u0005\u0004\"\u0001\u0002\"\"I\u00111\u0018\r\u0012\u0002\u0013\u0005\u0011Q\u0018\u0005\n\u0003\u0003D\u0012\u0013!C\u0001\u0003\u0007D\u0011\"a2\u0019#\u0003%\t!!0\t\u0013\u0005%\u0007$%A\u0005\u0002\u0005u\u0006\"CAf1E\u0005I\u0011AA_\u0011%\ti\rGI\u0001\n\u0003\ti\fC\u0005\u0002Pb\t\n\u0011\"\u0001\u0002R\"I\u0011q\u0014\r\u0002\u0002\u0013\u0005\u0015Q\u001b\u0005\n\u00033D\u0012\u0011!CA\u00037D\u0011\"a:\u0019\u0003\u0003%I!!;\t\u000f\u0005E\b\u0004\"\u0002\u0002t\"9\u0011\u0011 \r\u0005\u0006\u0005m\bbBA\u00001\u0011\u0015!\u0011\u0001\u0005\b\u0005\u000bABQ\u0001B\u0004\u0011\u001d\u0011Y\u0001\u0007C\u0003\u0005\u001bAqA!\u0005\u0019\t\u000b\u0011\u0019\u0002C\u0004\u0003\u0018a!)A!\u0007\t\u000f\tu\u0001\u0004\"\u0002\u0003 !9!1\u0005\r\u0005\u0006\t\u0015\u0002\"\u0003B\u00151\u0005\u0005IQ\u0001B\u0016\u0011%\u0011\u0019\u0004GI\u0001\n\u000b\u0011)\u0004C\u0005\u0003:a\t\t\u0011\"\u0002\u0003<!I!q\b\r\u0002\u0002\u0013\u0015!\u0011\t\u0005\n\u0005\u000bB\u0012\u0011!C\u0003\u0005\u000fB\u0011Ba\u0014\u0019\u0003\u0003%)A!\u0015\t\u0013\tU\u0003$!A\u0005\u0006\t]\u0003\"\u0003B01\u0005\u0005IQ\u0001B1\u0011%\u0011I\u0007GA\u0001\n\u000b\u0011Y\u0007C\u0005\u0003pa\t\t\u0011\"\u0002\u0003r\taQ*Z:tC\u001e,g\t\\1hg*\u0011!hO\u0001\tS:$XM\u001d8bY*\u0011A(P\u0001\u0004I:\u001c(B\u0001 @\u0003\tIwNC\u0001A\u0003\u0011\t7n[1\u0014\t\u0001\u0011\u0005j\u0013\t\u0003\u0007\u001ak\u0011\u0001\u0012\u0006\u0002\u000b\u0006)1oY1mC&\u0011q\t\u0012\u0002\u0007\u0003:Lh+\u00197\u0011\u0005\rK\u0015B\u0001&E\u0005\u001d\u0001&o\u001c3vGR\u0004\"\u0001T+\u000f\u00055\u001bfB\u0001(S\u001b\u0005y%B\u0001)R\u0003\u0019a$o\\8u}\r\u0001\u0011\"A#\n\u0005Q#\u0015a\u00029bG.\fw-Z\u0005\u0003-^\u0013AbU3sS\u0006d\u0017N_1cY\u0016T!\u0001\u0016#\u0002\u000b\u0019d\u0017mZ:\u0016\u0003i\u0003\"aQ.\n\u0005q#%!B*i_J$\u0018A\u00024mC\u001e\u001c\b%\u0001\u0004=S:LGO\u0010\u000b\u0003A\n\u0004\"!\u0019\u0001\u000e\u0003eBQ\u0001W\u0002A\u0002i\u000bq![:Rk\u0016\u0014\u00180F\u0001f!\t\u0019e-\u0003\u0002h\t\n9!i\\8mK\u0006t\u0017\u0001C5t\u0003:\u001cx/\u001a:\u0002\r=\u00048i\u001c3f+\u0005Y\u0007C\u00017p\u001d\t\tW.\u0003\u0002os\u00051q\n]\"pI\u0016L!\u0001]9\u0003\u000bY\u000bG.^3\n\u0005I$%aC#ok6,'/\u0019;j_:\fQ#[:BkRDwN]5uCRLg/Z!og^,'/A\u0006jgR\u0013XO\\2bi\u0016$\u0017AE5t%\u0016\u001cWO]:j_:$Um]5sK\u0012\fA#[:SK\u000e,(o]5p]\u00063\u0018-\u001b7bE2,\u0017\u0001\u0004:fgB|gn]3D_\u0012,W#A=\u0011\u0005i|gBA1|\u0013\ta\u0018(\u0001\u0007SKN\u0004xN\\:f\u0007>$W-\u0001\u0005u_N#(/\u001b8h)\u0005y\b\u0003BA\u0001\u0003\u0013qA!a\u0001\u0002\u0006A\u0011a\nR\u0005\u0004\u0003\u000f!\u0015A\u0002)sK\u0012,g-\u0003\u0003\u0002\f\u00055!AB*ue&twMC\u0002\u0002\b\u0011\u000bAaY8qsR\u0019\u0001-a\u0005\t\u000fak\u0001\u0013!a\u00015\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nTCAA\rU\rQ\u00161D\u0016\u0003\u0003;\u0001B!a\b\u0002*5\u0011\u0011\u0011\u0005\u0006\u0005\u0003G\t)#A\u0005v]\u000eDWmY6fI*\u0019\u0011q\u0005#\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002,\u0005\u0005\"!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006i\u0001O]8ek\u000e$\bK]3gSb,\"!!\r\u0011\t\u0005M\u0012QH\u0007\u0003\u0003kQA!a\u000e\u0002:\u0005!A.\u00198h\u0015\t\tY$\u0001\u0003kCZ\f\u0017\u0002BA\u0006\u0003k\tA\u0002\u001d:pIV\u001cG/\u0011:jif,\"!a\u0011\u0011\u0007\r\u000b)%C\u0002\u0002H\u0011\u00131!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$B!!\u0014\u0002TA\u00191)a\u0014\n\u0007\u0005ECIA\u0002B]fD\u0011\"!\u0016\u0012\u0003\u0003\u0005\r!a\u0011\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\tY\u0006\u0005\u0004\u0002^\u0005\r\u0014QJ\u0007\u0003\u0003?R1!!\u0019E\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003K\nyF\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dGcA3\u0002l!I\u0011QK\n\u0002\u0002\u0003\u0007\u0011QJ\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u00022\u0005E\u0004\"CA+)\u0005\u0005\t\u0019AA\"\u0003!A\u0017m\u001d5D_\u0012,GCAA\"\u0003\u0019)\u0017/^1mgR\u0019Q-a\u001f\t\u0013\u0005Uc#!AA\u0002\u00055\u0003f\u0001\u0001\u0002\u0000A!\u0011\u0011QAC\u001b\t\t\u0019IC\u0002\u0002(}JA!a\"\u0002\u0004\nY\u0011J\u001c;fe:\fG.\u00119j\u00031iUm]:bO\u00164E.Y4t!\t\t\u0007dE\u0003\u0019\u0003\u001f\u000b)\nE\u0002D\u0003#K1!a%E\u0005\u0019\te.\u001f*fMB!\u0011qSAN\u001b\t\tIJC\u0002?\u0003sI1AVAM)\t\tY)A\u0003baBd\u0017\u0010F\ba\u0003G\u000b9+!+\u0002.\u0006E\u0016QWA]\u0011!\t)K\u0007I\u0001\u0002\u0004)\u0017AB1og^,'\u000fC\u0004j5A\u0005\t\u0019A6\t\u0011\u0005-&\u0004%AA\u0002\u0015\f1#Y;uQ>\u0014\u0018\u000e^1uSZ,\u0017I\\:xKJD\u0001\"a,\u001b!\u0003\u0005\r!Z\u0001\niJ,hnY1uK\u0012D\u0001\"a-\u001b!\u0003\u0005\r!Z\u0001\u0011e\u0016\u001cWO]:j_:$Um]5sK\u0012D\u0001\"a.\u001b!\u0003\u0005\r!Z\u0001\u0013e\u0016\u001cWO]:j_:\fe/Y5mC\ndW\rC\u0004x5A\u0005\t\u0019A=\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIE*\"!a0+\u0007\u0015\fY\"A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00133+\t\t)MK\u0002l\u00037\tq\"\u00199qYf$C-\u001a4bk2$HeM\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%i\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$S'A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00137\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012:TCAAjU\rI\u00181\u0004\u000b\u0004A\u0006]\u0007\"\u0002-#\u0001\u0004Q\u0016aB;oCB\u0004H.\u001f\u000b\u0005\u0003;\f\u0019\u000f\u0005\u0003D\u0003?T\u0016bAAq\t\n1q\n\u001d;j_:D\u0001\"!:$\u0003\u0003\u0005\r\u0001Y\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAAv!\u0011\t\u0019$!<\n\t\u0005=\u0018Q\u0007\u0002\u0007\u001f\nTWm\u0019;\u0002#%\u001c\u0018+^3ss\u0012*\u0007\u0010^3og&|g\u000eF\u0002f\u0003kDa!a>&\u0001\u0004\u0001\u0017!\u0002\u0013uQ&\u001c\u0018AE5t\u0003:\u001cx/\u001a:%Kb$XM\\:j_:$2!ZA\u007f\u0011\u0019\t9P\na\u0001A\u0006\u0001r\u000e]\"pI\u0016$S\r\u001f;f]NLwN\u001c\u000b\u0004W\n\r\u0001BBA|O\u0001\u0007\u0001-A\u0010jg\u0006+H\u000f[8sSR\fG/\u001b<f\u0003:\u001cx/\u001a:%Kb$XM\\:j_:$2!\u001aB\u0005\u0011\u0019\t9\u0010\u000ba\u0001A\u0006)\u0012n\u001d+sk:\u001c\u0017\r^3eI\u0015DH/\u001a8tS>tGcA3\u0003\u0010!1\u0011q_\u0015A\u0002\u0001\fA$[:SK\u000e,(o]5p]\u0012+7/\u001b:fI\u0012*\u0007\u0010^3og&|g\u000eF\u0002f\u0005+Aa!a>+\u0001\u0004\u0001\u0017AH5t%\u0016\u001cWO]:j_:\fe/Y5mC\ndW\rJ3yi\u0016t7/[8o)\r)'1\u0004\u0005\u0007\u0003o\\\u0003\u0019\u00011\u0002-I,7\u000f]8og\u0016\u001cu\u000eZ3%Kb$XM\\:j_:$2!\u001fB\u0011\u0011\u0019\t9\u0010\fa\u0001A\u0006\u0011Bo\\*ue&tw\rJ3yi\u0016t7/[8o)\rq(q\u0005\u0005\u0007\u0003ol\u0003\u0019\u00011\u0002\u001d\r|\u0007/\u001f\u0013fqR,gn]5p]R!!Q\u0006B\u0019)\r\u0001'q\u0006\u0005\b1:\u0002\n\u00111\u0001[\u0011\u0019\t9P\fa\u0001A\u0006A2m\u001c9zI\u0011,g-Y;mi\u0012\nD%\u001a=uK:\u001c\u0018n\u001c8\u0015\t\u0005e!q\u0007\u0005\u0007\u0003o|\u0003\u0019\u00011\u0002/A\u0014x\u000eZ;diB\u0013XMZ5yI\u0015DH/\u001a8tS>tG\u0003BA\u0019\u0005{Aa!a>1\u0001\u0004\u0001\u0017A\u00069s_\u0012,8\r^!sSRLH%\u001a=uK:\u001c\u0018n\u001c8\u0015\t\u0005\r#1\t\u0005\u0007\u0003o\f\u0004\u0019\u00011\u00021A\u0014x\u000eZ;di\u0016cW-\\3oi\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0003J\t5C\u0003BA'\u0005\u0017B\u0011\"!\u00163\u0003\u0003\u0005\r!a\u0011\t\r\u0005](\u00071\u0001a\u0003e\u0001(o\u001c3vGRLE/\u001a:bi>\u0014H%\u001a=uK:\u001c\u0018n\u001c8\u0015\t\u0005m#1\u000b\u0005\u0007\u0003o\u001c\u0004\u0019\u00011\u0002%\r\fg.R9vC2$S\r\u001f;f]NLwN\u001c\u000b\u0005\u00053\u0012i\u0006F\u0002f\u00057B\u0011\"!\u00165\u0003\u0003\u0005\r!!\u0014\t\r\u0005]H\u00071\u0001a\u0003q\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3%Kb$XM\\:j_:$BAa\u0019\u0003hQ!\u0011\u0011\u0007B3\u0011%\t)&NA\u0001\u0002\u0004\t\u0019\u0005\u0003\u0004\u0002xV\u0002\r\u0001Y\u0001\u0013Q\u0006\u001c\bnQ8eK\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002v\t5\u0004BBA|m\u0001\u0007\u0001-\u0001\tfcV\fGn\u001d\u0013fqR,gn]5p]R!!1\u000fB<)\r)'Q\u000f\u0005\n\u0003+:\u0014\u0011!a\u0001\u0003\u001bBa!a>8\u0001\u0004\u0001\u0007f\u0001\r\u0002\u0000!\u001aq#a ")
@InternalApi
public final class MessageFlags
implements Product,
Serializable {
    private final short flags;

    public static boolean equals$extension(short $this, Object x$1) {
        return MessageFlags$.MODULE$.equals$extension($this, x$1);
    }

    public static int hashCode$extension(short $this) {
        return MessageFlags$.MODULE$.hashCode$extension($this);
    }

    public static String productElementName$extension(short $this, int x$1) {
        return MessageFlags$.MODULE$.productElementName$extension($this, x$1);
    }

    public static boolean canEqual$extension(short $this, Object x$1) {
        return MessageFlags$.MODULE$.canEqual$extension($this, x$1);
    }

    public static Iterator<Object> productIterator$extension(short $this) {
        return MessageFlags$.MODULE$.productIterator$extension($this);
    }

    public static Object productElement$extension(short $this, int x$1) {
        return MessageFlags$.MODULE$.productElement$extension($this, x$1);
    }

    public static int productArity$extension(short $this) {
        return MessageFlags$.MODULE$.productArity$extension($this);
    }

    public static String productPrefix$extension(short $this) {
        return MessageFlags$.MODULE$.productPrefix$extension($this);
    }

    public static short copy$default$1$extension(short $this) {
        return MessageFlags$.MODULE$.copy$default$1$extension($this);
    }

    public static short copy$extension(short $this, short flags) {
        return MessageFlags$.MODULE$.copy$extension($this, flags);
    }

    public static String toString$extension(short $this) {
        return MessageFlags$.MODULE$.toString$extension($this);
    }

    public static Enumeration.Value responseCode$extension(short $this) {
        return MessageFlags$.MODULE$.responseCode$extension($this);
    }

    public static boolean isRecursionAvailable$extension(short $this) {
        return MessageFlags$.MODULE$.isRecursionAvailable$extension($this);
    }

    public static boolean isRecursionDesired$extension(short $this) {
        return MessageFlags$.MODULE$.isRecursionDesired$extension($this);
    }

    public static boolean isTruncated$extension(short $this) {
        return MessageFlags$.MODULE$.isTruncated$extension($this);
    }

    public static boolean isAuthoritativeAnswer$extension(short $this) {
        return MessageFlags$.MODULE$.isAuthoritativeAnswer$extension($this);
    }

    public static Enumeration.Value opCode$extension(short $this) {
        return MessageFlags$.MODULE$.opCode$extension($this);
    }

    public static boolean isAnswer$extension(short $this) {
        return MessageFlags$.MODULE$.isAnswer$extension($this);
    }

    public static boolean isQuery$extension(short $this) {
        return MessageFlags$.MODULE$.isQuery$extension($this);
    }

    public static Option<Object> unapply(short x$0) {
        return MessageFlags$.MODULE$.unapply(x$0);
    }

    public static short apply(short flags) {
        return MessageFlags$.MODULE$.apply(flags);
    }

    public static Enumeration.Value apply$default$7() {
        return MessageFlags$.MODULE$.apply$default$7();
    }

    public static boolean apply$default$6() {
        return MessageFlags$.MODULE$.apply$default$6();
    }

    public static boolean apply$default$5() {
        return MessageFlags$.MODULE$.apply$default$5();
    }

    public static boolean apply$default$4() {
        return MessageFlags$.MODULE$.apply$default$4();
    }

    public static boolean apply$default$3() {
        return MessageFlags$.MODULE$.apply$default$3();
    }

    public static Enumeration.Value apply$default$2() {
        return MessageFlags$.MODULE$.apply$default$2();
    }

    public static boolean apply$default$1() {
        return MessageFlags$.MODULE$.apply$default$1();
    }

    public static short apply(boolean answer, Enumeration.Value opCode, boolean authoritativeAnswer, boolean truncated, boolean recursionDesired, boolean recursionAvailable, Enumeration.Value responseCode) {
        return MessageFlags$.MODULE$.apply(answer, opCode, authoritativeAnswer, truncated, recursionDesired, recursionAvailable, responseCode);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public short flags() {
        return this.flags;
    }

    public boolean isQuery() {
        return MessageFlags$.MODULE$.isQuery$extension(this.flags());
    }

    public boolean isAnswer() {
        return MessageFlags$.MODULE$.isAnswer$extension(this.flags());
    }

    public Enumeration.Value opCode() {
        return MessageFlags$.MODULE$.opCode$extension(this.flags());
    }

    public boolean isAuthoritativeAnswer() {
        return MessageFlags$.MODULE$.isAuthoritativeAnswer$extension(this.flags());
    }

    public boolean isTruncated() {
        return MessageFlags$.MODULE$.isTruncated$extension(this.flags());
    }

    public boolean isRecursionDesired() {
        return MessageFlags$.MODULE$.isRecursionDesired$extension(this.flags());
    }

    public boolean isRecursionAvailable() {
        return MessageFlags$.MODULE$.isRecursionAvailable$extension(this.flags());
    }

    public Enumeration.Value responseCode() {
        return MessageFlags$.MODULE$.responseCode$extension(this.flags());
    }

    public String toString() {
        return MessageFlags$.MODULE$.toString$extension(this.flags());
    }

    public short copy(short flags) {
        return MessageFlags$.MODULE$.copy$extension(this.flags(), flags);
    }

    public short copy$default$1() {
        return MessageFlags$.MODULE$.copy$default$1$extension(this.flags());
    }

    @Override
    public String productPrefix() {
        return MessageFlags$.MODULE$.productPrefix$extension(this.flags());
    }

    @Override
    public int productArity() {
        return MessageFlags$.MODULE$.productArity$extension(this.flags());
    }

    @Override
    public Object productElement(int x$1) {
        return MessageFlags$.MODULE$.productElement$extension(this.flags(), x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return MessageFlags$.MODULE$.productIterator$extension(this.flags());
    }

    @Override
    public boolean canEqual(Object x$1) {
        return MessageFlags$.MODULE$.canEqual$extension(this.flags(), x$1);
    }

    @Override
    public String productElementName(int x$1) {
        return MessageFlags$.MODULE$.productElementName$extension(this.flags(), x$1);
    }

    public int hashCode() {
        return MessageFlags$.MODULE$.hashCode$extension(this.flags());
    }

    @Override
    public boolean equals(Object x$1) {
        return MessageFlags$.MODULE$.equals$extension(this.flags(), x$1);
    }

    public MessageFlags(short flags) {
        this.flags = flags;
        Product.$init$(this);
    }
}

