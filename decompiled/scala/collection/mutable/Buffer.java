/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.View;
import scala.collection.mutable.Buffer$;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.Seq;
import scala.collection.mutable.Shrinkable;
import scala.math.Integral;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\t}ea\u0002\u0012$!\u0003\r\tA\u000b\u0005\u0006\u001d\u0002!\ta\u0014\u0005\u0006'\u0002!\t\u0005\u0016\u0005\u00061\u0002!\t%\u0017\u0005\u0006;\u00021\tA\u0018\u0005\u0006E\u0002!)a\u0019\u0005\u0006E\u0002!)!\u001b\u0005\u0006u\u0002!)a\u001f\u0005\b\u0003\u000b\u0001AQAA\u0004\u0011\u001d\ti\u0001\u0001C\u0001\u0003\u001fAa!\u0018\u0001\u0005\u0006\u0005M\u0001bBA\u0010\u0001\u0011\u0015\u0011\u0011\u0005\u0005\b\u0003O\u0001a\u0011AA\u0015\u0011\u001d\tY\t\u0001D\u0001\u0003\u001bCq!a(\u0001\r\u0003\t\t\u000bC\u0004\u0002 \u00021\t!!-\t\u000f\u0005e\u0007\u0001\"\u0001\u0002\\\"9\u0011\u0011\u001d\u0001\u0005\u0002\u0005\r\bbBAz\u0001\u0011\u0005\u0011Q\u001f\u0005\b\u0003\u007f\u0004a\u0011\u0001B\u0001\u0011\u001d\u0011y\u0001\u0001C\u0001\u0005#AqA!\u0006\u0001\t\u0003\u00119\u0002C\u0004\u0003\u001c\u0001!\tA!\b\t\u000f\t\u0005\u0002\u0001\"\u0001\u0003$!9!q\u0005\u0001\u0005\u0002\t%\u0002b\u0002B\u001a\u0001\u0011%!Q\u0007\u0005\b\u0005s\u0001A\u0011\u0001B\u001e\u0011\u001d\u0011i\u0005\u0001C\u0001\u0005\u001fBqAa\u0015\u0001\t\u0003\u0011)\u0006\u0003\u0005\u0003^\u0001\u0001K\u0011\u000bB0\u000f\u001d\u0011yg\tE\u0001\u0005c2aAI\u0012\t\u0002\tM\u0004b\u0002BB?\u0011\u0005!Q\u0011\u0005\n\u0005\u000f{\u0012\u0011!C\u0005\u0005\u0013\u0013aAQ;gM\u0016\u0014(B\u0001\u0013&\u0003\u001diW\u000f^1cY\u0016T!AJ\u0014\u0002\u0015\r|G\u000e\\3di&|gNC\u0001)\u0003\u0015\u00198-\u00197b\u0007\u0001)\"a\u000b\u001c\u0014\u000f\u0001a\u0003g\u0010#H\u0015B\u0011QFL\u0007\u0002O%\u0011qf\n\u0002\u0007\u0003:L(+\u001a4\u0011\u0007E\u0012D'D\u0001$\u0013\t\u00194EA\u0002TKF\u0004\"!\u000e\u001c\r\u0001\u0011)q\u0007\u0001b\u0001q\t\t\u0011)\u0005\u0002:yA\u0011QFO\u0005\u0003w\u001d\u0012qAT8uQ&tw\r\u0005\u0002.{%\u0011ah\n\u0002\u0004\u0003:L\b#B\u0019Ai\t\u001b\u0015BA!$\u0005\u0019\u0019V-](qgB\u0011\u0011\u0007\u0001\t\u0004c\u0001!\u0004cA\u0019Fi%\u0011ai\t\u0002\t\u000fJ|w/\u00192mKB\u0019\u0011\u0007\u0013\u001b\n\u0005%\u001b#AC*ie&t7.\u00192mKB!1\n\u0014\u001bC\u001b\u0005)\u0013BA'&\u0005]IE/\u001a:bE2,g)Y2u_JLH)\u001a4bk2$8/\u0001\u0004%S:LG\u000f\n\u000b\u0002!B\u0011Q&U\u0005\u0003%\u001e\u0012A!\u00168ji\u0006y\u0011\u000e^3sC\ndWMR1di>\u0014\u00180F\u0001V!\rYeKQ\u0005\u0003/\u0016\u0012!bU3r\r\u0006\u001cGo\u001c:z\u0003%Ygn\\<o'&TX-F\u0001[!\ti3,\u0003\u0002]O\t\u0019\u0011J\u001c;\u0002\u000fA\u0014X\r]3oIR\u0011q\fY\u0007\u0002\u0001!)\u0011\r\u0002a\u0001i\u0005!Q\r\\3n\u0003\u0019\t\u0007\u000f]3oIR\u0011q\f\u001a\u0005\u0006C\u0016\u0001\r\u0001\u000e\u0015\u0003\u000b\u0019\u0004\"!L4\n\u0005!<#AB5oY&tW\r\u0006\u0002`U\")1N\u0002a\u0001Y\u0006)Q\r\\3ngB\u0019Q&\u001c\u001b\n\u00059<#A\u0003\u001fsKB,\u0017\r^3e}!\u0012aA\u001a\u0015\u0007\rE$Xo\u001e=\u0011\u00055\u0012\u0018BA:(\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\u00051\u0018!F+tK\u0002\n\u0007\u000f]3oI\u0006cG\u000eI5ogR,\u0017\rZ\u0001\u0006g&t7-Z\u0011\u0002s\u00061!GL\u00194]A\n\u0011\"\u00199qK:$\u0017\t\u001c7\u0015\u0005}c\b\"B?\b\u0001\u0004q\u0018A\u0001=t!\rYu\u0010N\u0005\u0004\u0003\u0003)#\u0001D%uKJ\f'\r\\3P]\u000e,\u0007FA\u0004g\u00039!\u0003\u000f\\;tI\u0015\fHeY8m_:$2aXA\u0005\u0011\u0015\t\u0007\u00021\u00015Q\tAa-\u0001\u0006qe\u0016\u0004XM\u001c3BY2$2aXA\t\u0011\u0015Y\u0017\u00021\u0001\u007f)\ry\u0016Q\u0003\u0005\u0006W*\u0001\r\u0001\u001c\u0015\u0003\u0015\u0019DsAC9u\u000379\b0\t\u0002\u0002\u001e\u00051Rk]3!aJ,\u0007/\u001a8e\u00032d\u0007%\u001b8ti\u0016\fG-A\n%a2,8\u000f\n9mkN$S-\u001d\u0013d_2|g\u000eF\u0002`\u0003GAQa[\u0006A\u0002yD#a\u00034\u0002\r%t7/\u001a:u)\u0015\u0001\u00161FA\u0018\u0011\u0019\ti\u0003\u0004a\u00015\u0006\u0019\u0011\u000e\u001a=\t\u000b\u0005d\u0001\u0019\u0001\u001b)\u000b1\t\u0019$!\u0015\u0011\u000b5\n)$!\u000f\n\u0007\u0005]rE\u0001\u0004uQJ|wo\u001d\t\u0005\u0003w\tYE\u0004\u0003\u0002>\u0005\u001dc\u0002BA \u0003\u000bj!!!\u0011\u000b\u0007\u0005\r\u0013&\u0001\u0004=e>|GOP\u0005\u0002Q%\u0019\u0011\u0011J\u0014\u0002\u000fA\f7m[1hK&!\u0011QJA(\u0005eIe\u000eZ3y\u001fV$xJ\u001a\"pk:$7/\u0012=dKB$\u0018n\u001c8\u000b\u0007\u0005%s%M\u0004\u001f\u0003'\n\u0019'!#\u0011\t\u0005U\u0013Q\f\b\u0005\u0003/\nI\u0006E\u0002\u0002@\u001dJ1!a\u0017(\u0003\u0019\u0001&/\u001a3fM&!\u0011qLA1\u0005\u0019\u0019FO]5oO*\u0019\u00111L\u00142\u0013\r\n)'!\u001c\u0002\u0000\u0005=T\u0003BA4\u0003S*\"!a\u0015\u0005\u000f\u0005-\u0014F1\u0001\u0002v\t\tA+\u0003\u0003\u0002p\u0005E\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0013GC\u0002\u0002t\u001d\na\u0001\u001e5s_^\u001c\u0018cA\u001d\u0002xA!\u0011\u0011PA>\u001d\ri\u0013qI\u0005\u0005\u0003{\nyEA\u0005UQJ|w/\u00192mKFJ1%!!\u0002\u0004\u0006\u0015\u00151\u000f\b\u0004[\u0005\r\u0015bAA:OE*!%L\u0014\u0002\b\n)1oY1mCF\u001aa%!\u000f\u0002\u0013%t7/\u001a:u\u00032dG#\u0002)\u0002\u0010\u0006E\u0005BBA\u0017\u001b\u0001\u0007!\fC\u0003l\u001b\u0001\u0007a\u0010K\u0003\u000e\u0003g\t)*M\u0004\u001f\u0003'\n9*!(2\u0013\r\n)'!\u001c\u0002\u001a\u0006=\u0014'C\u0012\u0002\u0002\u0006\r\u00151TA:c\u0015\u0011SfJADc\r1\u0013\u0011H\u0001\u0007e\u0016lwN^3\u0015\u0007Q\n\u0019\u000b\u0003\u0004\u0002.9\u0001\rA\u0017\u0015\u0006\u001d\u0005M\u0012qU\u0019\b=\u0005M\u0013\u0011VAXc%\u0019\u0013QMA7\u0003W\u000by'M\u0005$\u0003\u0003\u000b\u0019)!,\u0002tE*!%L\u0014\u0002\bF\u001aa%!\u000f\u0015\u000bA\u000b\u0019,!.\t\r\u00055r\u00021\u0001[\u0011\u0019\t9l\u0004a\u00015\u0006)1m\\;oi\"*q\"a/\u0002DB)Q&!\u000e\u0002>B!\u00111HA`\u0013\u0011\t\t-a\u0014\u00031%cG.Z4bY\u0006\u0013x-^7f]R,\u0005pY3qi&|g.M\u0004\u001f\u0003'\n)-a32\u0013\r\n)'!\u001c\u0002H\u0006=\u0014'C\u0012\u0002\u0002\u0006\r\u0015\u0011ZA:c\u0015\u0011SfJADc\r1\u0013Q\u0018\u0015\u0006\u001f\u0005M\u0012qZ\u0019\b=\u0005M\u0013\u0011[Alc%\u0019\u0013QMA7\u0003'\fy'M\u0005$\u0003\u0003\u000b\u0019)!6\u0002tE*!%L\u0014\u0002\bF\u001aa%!\u000f\u0002\u0017M,(\r\u001e:bGR|e.\u001a\u000b\u0004?\u0006u\u0007BBAp!\u0001\u0007A'A\u0001y\u0003%!(/[7Ti\u0006\u0014H\u000fF\u0002Q\u0003KDa!a:\u0012\u0001\u0004Q\u0016!\u00018)\u0011E\tH/a;x\u0003_\f#!!<\u0002/U\u001cX\r\t3s_BLe\u000e\u00157bG\u0016\u0004\u0013N\\:uK\u0006$\u0017EAAy\u0003\u0019\u0011d&M\u001a/i\u00059AO]5n\u000b:$Gc\u0001)\u0002x\"1\u0011q\u001d\nA\u0002iC\u0003BE9u\u0003w<\u0018q^\u0011\u0003\u0003{\fA$^:fA\u0011\u0014x\u000e\u001d*jO\"$\u0018J\u001c)mC\u000e,\u0007%\u001b8ti\u0016\fG-\u0001\u0007qCR\u001c\u0007.\u00138QY\u0006\u001cW\rF\u0004`\u0005\u0007\u00119Aa\u0003\t\r\t\u00151\u00031\u0001[\u0003\u00111'o\\7\t\r\t%1\u00031\u0001\u007f\u0003\u0015\u0001\u0018\r^2i\u0011\u0019\u0011ia\u0005a\u00015\u0006A!/\u001a9mC\u000e,G-A\u0006ee>\u0004\u0018J\u001c)mC\u000e,GcA0\u0003\u0014!1\u0011q\u001d\u000bA\u0002i\u000b\u0001\u0003\u001a:paJKw\r\u001b;J]Bc\u0017mY3\u0015\u0007}\u0013I\u0002\u0003\u0004\u0002hV\u0001\rAW\u0001\fi\u0006\\W-\u00138QY\u0006\u001cW\rF\u0002`\u0005?Aa!a:\u0017\u0001\u0004Q\u0016\u0001\u0005;bW\u0016\u0014\u0016n\u001a5u\u0013:\u0004F.Y2f)\ry&Q\u0005\u0005\u0007\u0003O<\u0002\u0019\u0001.\u0002\u0019Md\u0017nY3J]Bc\u0017mY3\u0015\u000b}\u0013YCa\f\t\r\t5\u0002\u00041\u0001[\u0003\u0015\u0019H/\u0019:u\u0011\u0019\u0011\t\u0004\u0007a\u00015\u0006\u0019QM\u001c3\u0002\u00159|'/\\1mSj,G\rF\u0002[\u0005oAa!a:\u001a\u0001\u0004Q\u0016\u0001\u00053s_B<\u0006.\u001b7f\u0013:\u0004F.Y2f)\ry&Q\b\u0005\b\u0005\u007fQ\u0002\u0019\u0001B!\u0003\u0005\u0001\bCB\u0017\u0003DQ\u00129%C\u0002\u0003F\u001d\u0012\u0011BR;oGRLwN\\\u0019\u0011\u00075\u0012I%C\u0002\u0003L\u001d\u0012qAQ8pY\u0016\fg.\u0001\tuC.,w\u000b[5mK&s\u0007\u000b\\1dKR\u0019qL!\u0015\t\u000f\t}2\u00041\u0001\u0003B\u0005a\u0001/\u00193U_&s\u0007\u000b\\1dKR)qLa\u0016\u0003\\!1!\u0011\f\u000fA\u0002i\u000b1\u0001\\3o\u0011\u0015\tG\u00041\u00015\u00031\u0019HO]5oOB\u0013XMZ5y+\t\u0011\t\u0007\u0005\u0003\u0003d\t5TB\u0001B3\u0015\u0011\u00119G!\u001b\u0002\t1\fgn\u001a\u0006\u0003\u0005W\nAA[1wC&!\u0011q\fB3\u0003\u0019\u0011UO\u001a4feB\u0011\u0011gH\n\u0004?\tU\u0004#\u0002B<\u0005{\u0012ebA&\u0003z%\u0019!1P\u0013\u0002\u0015M+\u0017OR1di>\u0014\u00180\u0003\u0003\u0003\u0000\t\u0005%\u0001\u0003#fY\u0016<\u0017\r^3\u000b\u0007\tmT%\u0001\u0004=S:LGO\u0010\u000b\u0003\u0005c\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"Aa#\u0011\t\t\r$QR\u0005\u0005\u0005\u001f\u0013)G\u0001\u0004PE*,7\r\u001e\u0015\b?\tM%\u0011\u0014BN!\ri#QS\u0005\u0004\u0005/;#\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\u0019\u0001f\u0002\u0010\u0003\u0014\ne%1\u0014")
public interface Buffer<A>
extends Seq<A>,
Growable<A>,
Shrinkable<A> {
    public static <A> Builder<A, Buffer<A>> newBuilder() {
        return Buffer$.MODULE$.newBuilder();
    }

    public static SeqOps from(IterableOnce it) {
        return Buffer$.MODULE$.from(it);
    }

    public static SeqOps unapplySeq(SeqOps x) {
        return x;
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        Buffer$ tabulate_this = Buffer$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$7$adapted(tabulate_this, n2, n3, n4, n5, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        Buffer$ tabulate_this = Buffer$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$5$adapted(tabulate_this, n2, n3, n4, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f) {
        Buffer$ tabulate_this = Buffer$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$3$adapted(tabulate_this, n2, n3, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, Function2 f) {
        Buffer$ tabulate_this = Buffer$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$1$adapted(tabulate_this, n2, f, arg_0));
    }

    public static Object tabulate(int n, Function1 f) {
        return Buffer$.MODULE$.from(new View.Tabulate(n, f));
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        Buffer$ fill_this = Buffer$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$4(fill_this, n2, n3, n4, n5, elem));
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        Buffer$ fill_this = Buffer$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$3(fill_this, n2, n3, n4, elem));
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem) {
        Buffer$ fill_this = Buffer$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$2(fill_this, n2, n3, elem));
    }

    public static Object fill(int n1, int n2, Function0 elem) {
        Buffer$ fill_this = Buffer$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$1(fill_this, n2, elem));
    }

    public static Object fill(int n, Function0 elem) {
        return Buffer$.MODULE$.from(new View.Fill(n, elem));
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(Buffer$.MODULE$, start2, end, step, evidence$4);
    }

    public static Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(Buffer$.MODULE$, start2, end, evidence$3);
    }

    public static Object unfold(Object init2, Function1 f) {
        return Buffer$.MODULE$.from(new View.Unfold(init2, f));
    }

    public static Object iterate(Object start2, int len, Function1 f) {
        return Buffer$.MODULE$.from(new View.Iterate<Object>(start2, len, f));
    }

    public static /* synthetic */ SeqFactory iterableFactory$(Buffer $this) {
        return $this.iterableFactory();
    }

    @Override
    default public SeqFactory<Buffer> iterableFactory() {
        return Buffer$.MODULE$;
    }

    public static /* synthetic */ int knownSize$(Buffer $this) {
        return $this.knownSize();
    }

    @Override
    default public int knownSize() {
        return -1;
    }

    public Buffer<A> prepend(A var1);

    public static /* synthetic */ Buffer append$(Buffer $this, Object elem) {
        return $this.append(elem);
    }

    default public Buffer<A> append(A elem) {
        return (Buffer)this.addOne(elem);
    }

    public static /* synthetic */ Buffer append$(Buffer $this, scala.collection.immutable.Seq elems) {
        return $this.append(elems);
    }

    default public Buffer<A> append(scala.collection.immutable.Seq<A> elems) {
        return (Buffer)this.addAll(elems);
    }

    public static /* synthetic */ Buffer appendAll$(Buffer $this, IterableOnce xs) {
        return $this.appendAll(xs);
    }

    default public Buffer<A> appendAll(IterableOnce<A> xs) {
        return (Buffer)this.addAll(xs);
    }

    public static /* synthetic */ Buffer $plus$eq$colon$(Buffer $this, Object elem) {
        return $this.$plus$eq$colon(elem);
    }

    default public Buffer<A> $plus$eq$colon(A elem) {
        return this.prepend(elem);
    }

    public static /* synthetic */ Buffer prependAll$(Buffer $this, IterableOnce elems) {
        return $this.prependAll(elems);
    }

    default public Buffer<A> prependAll(IterableOnce<A> elems) {
        this.insertAll(0, elems);
        return this;
    }

    public static /* synthetic */ Buffer prepend$(Buffer $this, scala.collection.immutable.Seq elems) {
        return $this.prepend(elems);
    }

    default public Buffer<A> prepend(scala.collection.immutable.Seq<A> elems) {
        return this.prependAll(elems);
    }

    public static /* synthetic */ Buffer $plus$plus$eq$colon$(Buffer $this, IterableOnce elems) {
        return $this.$plus$plus$eq$colon(elems);
    }

    default public Buffer<A> $plus$plus$eq$colon(IterableOnce<A> elems) {
        return this.prependAll(elems);
    }

    public void insert(int var1, A var2) throws IndexOutOfBoundsException;

    public void insertAll(int var1, IterableOnce<A> var2) throws IndexOutOfBoundsException;

    public A remove(int var1) throws IndexOutOfBoundsException;

    public void remove(int var1, int var2) throws IndexOutOfBoundsException, IllegalArgumentException;

    public static /* synthetic */ Buffer subtractOne$(Buffer $this, Object x) {
        return $this.subtractOne(x);
    }

    @Override
    default public Buffer<A> subtractOne(A x) {
        int i = this.indexOf(x);
        if (i != -1) {
            this.remove(i);
        }
        return this;
    }

    public static /* synthetic */ void trimStart$(Buffer $this, int n) {
        $this.trimStart(n);
    }

    default public void trimStart(int n) {
        this.dropInPlace(n);
    }

    public static /* synthetic */ void trimEnd$(Buffer $this, int n) {
        $this.trimEnd(n);
    }

    default public void trimEnd(int n) {
        this.dropRightInPlace(n);
    }

    public Buffer<A> patchInPlace(int var1, IterableOnce<A> var2, int var3);

    public static /* synthetic */ Buffer dropInPlace$(Buffer $this, int n) {
        return $this.dropInPlace(n);
    }

    default public Buffer<A> dropInPlace(int n) {
        this.remove(0, this.normalized(n));
        return this;
    }

    public static /* synthetic */ Buffer dropRightInPlace$(Buffer $this, int n) {
        return $this.dropRightInPlace(n);
    }

    default public Buffer<A> dropRightInPlace(int n) {
        int norm = this.normalized(n);
        this.remove(this.length() - norm, norm);
        return this;
    }

    public static /* synthetic */ Buffer takeInPlace$(Buffer $this, int n) {
        return $this.takeInPlace(n);
    }

    default public Buffer<A> takeInPlace(int n) {
        int norm = this.normalized(n);
        this.remove(norm, this.length() - norm);
        return this;
    }

    public static /* synthetic */ Buffer takeRightInPlace$(Buffer $this, int n) {
        return $this.takeRightInPlace(n);
    }

    default public Buffer<A> takeRightInPlace(int n) {
        this.remove(0, this.length() - this.normalized(n));
        return this;
    }

    public static /* synthetic */ Buffer sliceInPlace$(Buffer $this, int start2, int end) {
        return $this.sliceInPlace(start2, end);
    }

    default public Buffer<A> sliceInPlace(int start2, int end) {
        return this.takeInPlace(end).dropInPlace(start2);
    }

    private int normalized(int n) {
        int max_y = 0;
        return Math.min(Math.max(n, max_y), this.length());
    }

    public static /* synthetic */ Buffer dropWhileInPlace$(Buffer $this, Function1 p) {
        return $this.dropWhileInPlace(p);
    }

    default public Buffer<A> dropWhileInPlace(Function1<A, Object> p) {
        int idx = this.indexWhere((Function1<Object, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(Buffer.$anonfun$dropWhileInPlace$1(p, x$1)));
        if (idx < 0) {
            this.clear();
            return this;
        }
        return this.dropInPlace(idx);
    }

    public static /* synthetic */ Buffer takeWhileInPlace$(Buffer $this, Function1 p) {
        return $this.takeWhileInPlace(p);
    }

    default public Buffer<A> takeWhileInPlace(Function1<A, Object> p) {
        int idx = this.indexWhere((Function1<Object, Object> & Serializable)x$2 -> BoxesRunTime.boxToBoolean(Buffer.$anonfun$takeWhileInPlace$1(p, x$2)));
        if (idx < 0) {
            return this;
        }
        return this.takeInPlace(idx);
    }

    public static /* synthetic */ Buffer padToInPlace$(Buffer $this, int len, Object elem) {
        return $this.padToInPlace(len, elem);
    }

    default public Buffer<A> padToInPlace(int len, A elem) {
        while (this.length() < len) {
            this.addOne(elem);
        }
        return this;
    }

    public static /* synthetic */ String stringPrefix$(Buffer $this) {
        return $this.stringPrefix();
    }

    @Override
    default public String stringPrefix() {
        return "Buffer";
    }

    public static /* synthetic */ boolean $anonfun$dropWhileInPlace$1(Function1 p$1, Object x$1) {
        return !BoxesRunTime.unboxToBoolean(p$1.apply(x$1));
    }

    public static /* synthetic */ boolean $anonfun$takeWhileInPlace$1(Function1 p$2, Object x$2) {
        return !BoxesRunTime.unboxToBoolean(p$2.apply(x$2));
    }

    public static void $init$(Buffer $this) {
    }
}

