/*
 * Decompiled with CFR 0.152.
 */
package scala.util;

import java.io.Serializable;
import scala.Function0;
import scala.collection.BuildFrom;
import scala.collection.IterableOnce;
import scala.collection.immutable.LazyList;
import scala.collection.immutable.LazyList$;
import scala.collection.immutable.Range;
import scala.collection.mutable.ArrayBuffer;
import scala.collection.mutable.Builder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005uf\u0001B\u0010!\u0001\u0015B\u0001\"\r\u0001\u0003\u0006\u0004%\tA\r\u0005\ts\u0001\u0011\t\u0011)A\u0005g!)!\b\u0001C\u0001w!)!\b\u0001C\u0001\u007f!)!\b\u0001C\u0001\u000b\")!\b\u0001C\u0001\u0015\")1\n\u0001C\u0001\u0019\")\u0001\u000b\u0001C\u0001#\")\u0001\u000b\u0001C\u0001;\")\u0001\r\u0001C\u0001C\")Q\r\u0001C\u0001M\")1\u000e\u0001C\u0001Y\")Q\r\u0001C\u0001a\")1\u000f\u0001C\u0001C\")A\u000f\u0001C\u0001k\")A\u000f\u0001C\u0001m\")Q\r\u0001C\u0001q\")1\u0010\u0001C\u0001y\")1\u0010\u0001C\u0001{\")Q\r\u0001C\u0001\u007f\"9\u0011Q\u0001\u0001\u0005\u0002\u0005\u001d\u0001bBA\u0012\u0001\u0011\u0005\u0011Q\u0005\u0005\b\u0003[\u0001A\u0011AA\u0018\u0011\u001d\t\u0019\u0004\u0001C\u0001\u0003kAq!a\u001d\u0001\t\u0003\t)hB\u0004\u0002\u001e\u0002B\t!a(\u0007\r}\u0001\u0003\u0012AAQ\u0011\u0019Q4\u0004\"\u0001\u0002$\"9\u0011QU\u000e\u0005\u0004\u0005\u001d\u0006\"CAW7\u0005\u0005I\u0011BAX\u0005\u0019\u0011\u0016M\u001c3p[*\u0011\u0011EI\u0001\u0005kRLGNC\u0001$\u0003\u0015\u00198-\u00197b\u0007\u0001\u00192\u0001\u0001\u0014+!\t9\u0003&D\u0001#\u0013\tI#E\u0001\u0004B]f\u0014VM\u001a\t\u0003W9r!a\n\u0017\n\u00055\u0012\u0013a\u00029bG.\fw-Z\u0005\u0003_A\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!!\f\u0012\u0002\tM,GNZ\u000b\u0002gA\u0011A\u0007O\u0007\u0002k)\u0011\u0011E\u000e\u0006\u0002o\u0005!!.\u0019<b\u0013\tyR'A\u0003tK24\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0003yy\u0002\"!\u0010\u0001\u000e\u0003\u0001BQ!M\u0002A\u0002M\"\"\u0001\u0010!\t\u000b\u0005#\u0001\u0019\u0001\"\u0002\tM,W\r\u001a\t\u0003O\rK!\u0001\u0012\u0012\u0003\t1{gn\u001a\u000b\u0003y\u0019CQ!Q\u0003A\u0002\u001d\u0003\"a\n%\n\u0005%\u0013#aA%oiR\tA(A\u0006oKb$(i\\8mK\u0006tG#A'\u0011\u0005\u001dr\u0015BA(#\u0005\u001d\u0011un\u001c7fC:\f\u0011B\\3yi\nKH/Z:\u0015\u0005I+\u0006CA\u0014T\u0013\t!&E\u0001\u0003V]&$\b\"\u0002,\t\u0001\u00049\u0016!\u00022zi\u0016\u001c\bcA\u0014Y5&\u0011\u0011L\t\u0002\u0006\u0003J\u0014\u0018-\u001f\t\u0003OmK!\u0001\u0018\u0012\u0003\t\tKH/\u001a\u000b\u0003/zCQaX\u0005A\u0002\u001d\u000b\u0011A\\\u0001\u000b]\u0016DH\u000fR8vE2,G#\u00012\u0011\u0005\u001d\u001a\u0017B\u00013#\u0005\u0019!u.\u001e2mK\u00069!-\u001a;xK\u0016tGc\u00012hS\")\u0001n\u0003a\u0001E\u0006aQ.\u001b8J]\u000edWo]5wK\")!n\u0003a\u0001E\u0006aQ.\u0019=Fq\u000edWo]5wK\u0006Ia.\u001a=u\r2|\u0017\r\u001e\u000b\u0002[B\u0011qE\\\u0005\u0003_\n\u0012QA\u00127pCR$2!\\9s\u0011\u0015AW\u00021\u0001n\u0011\u0015QW\u00021\u0001n\u00031qW\r\u001f;HCV\u001c8/[1o\u0003\u001dqW\r\u001f;J]R$\u0012a\u0012\u000b\u0003\u000f^DQa\u0018\tA\u0002\u001d#2aR={\u0011\u0015A\u0017\u00031\u0001H\u0011\u0015Q\u0017\u00031\u0001H\u0003!qW\r\u001f;M_:<G#\u0001\"\u0015\u0005\ts\b\"B0\u0014\u0001\u0004\u0011E#\u0002\"\u0002\u0002\u0005\r\u0001\"\u00025\u0015\u0001\u0004\u0011\u0005\"\u00026\u0015\u0001\u0004\u0011\u0015A\u00038fqR\u001cFO]5oOR!\u0011\u0011BA\u0010!\u0011\tY!!\u0007\u000f\t\u00055\u0011Q\u0003\t\u0004\u0003\u001f\u0011SBAA\t\u0015\r\t\u0019\u0002J\u0001\u0007yI|w\u000e\u001e \n\u0007\u0005]!%\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u00037\tiB\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003/\u0011\u0003BBA\u0011+\u0001\u0007q)\u0001\u0004mK:<G\u000f[\u0001\u0012]\u0016DH\u000f\u0015:j]R\f'\r\\3DQ\u0006\u0014HCAA\u0014!\r9\u0013\u0011F\u0005\u0004\u0003W\u0011#\u0001B\"iCJ\fqa]3u'\u0016,G\rF\u0002S\u0003cAQ!Q\fA\u0002\t\u000bqa\u001d5vM\u001adW-\u0006\u0004\u00028\u0005=\u0014q\b\u000b\u0005\u0003s\t\u0019\u0007\u0006\u0003\u0002<\u0005E\u0003\u0003BA\u001f\u0003\u007fa\u0001\u0001B\u0004\u0002Ba\u0011\r!a\u0011\u0003\u0003\r\u000bB!!\u0012\u0002LA\u0019q%a\u0012\n\u0007\u0005%#EA\u0004O_RD\u0017N\\4\u0011\u0007\u001d\ni%C\u0002\u0002P\t\u00121!\u00118z\u0011\u001d\t\u0019\u0006\u0007a\u0002\u0003+\n!A\u00194\u0011\u0015\u0005]\u0013QLA1\u0003[\nY$\u0004\u0002\u0002Z)\u0019\u00111\f\u0012\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002`\u0005e#!\u0003\"vS2$gI]8n\u001d\u0011\ti$a\u0019\t\u000f\u0005\u0015\u0004\u00041\u0001\u0002h\u0005\u0011\u0001p\u001d\t\u0006W\u0005%\u0014QN\u0005\u0004\u0003W\u0002$\u0001D%uKJ\f'\r\\3P]\u000e,\u0007\u0003BA\u001f\u0003_\"q!!\u001d\u0019\u0005\u0004\t\u0019EA\u0001U\u00031\tG\u000e\u001d5b]VlWM]5d+\t\t9\b\u0005\u0004\u0002z\u0005}\u0014qE\u0007\u0003\u0003wRA!! \u0002Z\u0005I\u0011.\\7vi\u0006\u0014G.Z\u0005\u0005\u0003\u0003\u000bYH\u0001\u0005MCjLH*[:uQ-I\u0012QQAI\u0003'\u000b9*!'\u0011\t\u0005\u001d\u0015QR\u0007\u0003\u0003\u0013S1!a##\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003\u001f\u000bIIA\u0005nS\u001e\u0014\u0018\r^5p]\u00069Q.Z:tC\u001e,\u0017EAAK\u0003U\u0002\u0017\r\u001c9iC:,X.\u001a:jG\u0002\u0004#/\u001a;ve:\u001c\b%\u0019\u0011MCjLH*[:uA%t7\u000f^3bI\u0002zg\rI1!'R\u0014X-Y7\u0002\u0013\rD\u0017M\\4fI&s\u0017EAAN\u0003\u0019\u0011d&M\u001a/a\u00051!+\u00198e_6\u0004\"!P\u000e\u0014\u0005maDCAAP\u0003IQ\u0017M^1SC:$w.\u001c+p%\u0006tGm\\7\u0015\u0007q\nI\u000b\u0003\u0004\u0002,v\u0001\raM\u0001\u0002e\u0006aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011\u0011\u0017\t\u0005\u0003g\u000bI,\u0004\u0002\u00026*\u0019\u0011q\u0017\u001c\u0002\t1\fgnZ\u0005\u0005\u0003w\u000b)L\u0001\u0004PE*,7\r\u001e")
public class Random
implements Serializable {
    private final java.util.Random self;

    public static Random javaRandomToRandom(java.util.Random r) {
        return new Random(r);
    }

    public java.util.Random self() {
        return this.self;
    }

    public boolean nextBoolean() {
        return this.self().nextBoolean();
    }

    public void nextBytes(byte[] bytes) {
        this.self().nextBytes(bytes);
    }

    public byte[] nextBytes(int n) {
        int n2 = 0;
        byte[] bytes = new byte[Math.max(n2, n)];
        this.self().nextBytes(bytes);
        return bytes;
    }

    public double nextDouble() {
        return this.self().nextDouble();
    }

    public double between(double minInclusive, double maxExclusive) {
        if (!(minInclusive < maxExclusive)) {
            throw new IllegalArgumentException(new StringBuilder(20).append("requirement failed: ").append((Object)"Invalid bounds").toString());
        }
        double next = this.nextDouble() * (maxExclusive - minInclusive) + minInclusive;
        if (next < maxExclusive) {
            return next;
        }
        return Math.nextAfter(maxExclusive, Double.NEGATIVE_INFINITY);
    }

    public float nextFloat() {
        return this.self().nextFloat();
    }

    public float between(float minInclusive, float maxExclusive) {
        if (!(minInclusive < maxExclusive)) {
            throw new IllegalArgumentException(new StringBuilder(20).append("requirement failed: ").append((Object)"Invalid bounds").toString());
        }
        float next = this.nextFloat() * (maxExclusive - minInclusive) + minInclusive;
        if (next < maxExclusive) {
            return next;
        }
        return Math.nextAfter(maxExclusive, Double.NEGATIVE_INFINITY);
    }

    public double nextGaussian() {
        return this.self().nextGaussian();
    }

    public int nextInt() {
        return this.self().nextInt();
    }

    public int nextInt(int n) {
        return this.self().nextInt(n);
    }

    public int between(int minInclusive, int maxExclusive) {
        if (!(minInclusive < maxExclusive)) {
            throw new IllegalArgumentException(new StringBuilder(20).append("requirement failed: ").append((Object)"Invalid bounds").toString());
        }
        int difference = maxExclusive - minInclusive;
        if (difference >= 0) {
            return this.nextInt(difference) + minInclusive;
        }
        return this.loop$1(minInclusive, maxExclusive);
    }

    public long nextLong() {
        return this.self().nextLong();
    }

    public long nextLong(long n) {
        if (!(n > 0L)) {
            throw new IllegalArgumentException(new StringBuilder(20).append("requirement failed: ").append((Object)"n must be positive").toString());
        }
        long offset = 0L;
        long _n = n;
        while (_n >= Integer.MAX_VALUE) {
            long nextn;
            int bits = this.nextInt(2);
            long halfn = _n >>> 1;
            long l = nextn = (bits & 2) == 0 ? halfn : _n - halfn;
            if ((bits & 1) == 0) {
                offset += _n - nextn;
            }
            _n = nextn;
        }
        return offset + (long)this.nextInt((int)_n);
    }

    public long between(long minInclusive, long maxExclusive) {
        if (!(minInclusive < maxExclusive)) {
            throw new IllegalArgumentException(new StringBuilder(20).append("requirement failed: ").append((Object)"Invalid bounds").toString());
        }
        long difference = maxExclusive - minInclusive;
        if (difference >= 0L) {
            return this.nextLong(difference) + minInclusive;
        }
        return this.loop$2(minInclusive, maxExclusive);
    }

    public String nextString(int length) {
        if (length <= 0) {
            return "";
        }
        char[] arr = new char[length];
        for (int i = 0; i < length; ++i) {
            arr[i] = this.safeChar$1();
        }
        return new String(arr);
    }

    public char nextPrintableChar() {
        int low = 33;
        int high = 127;
        return (char)(this.self().nextInt(high - low) + low);
    }

    public void setSeed(long seed) {
        this.self().setSeed(seed);
    }

    public <T, C> C shuffle(IterableOnce<T> xs, BuildFrom<? extends IterableOnce<T>, T, C> bf) {
        ArrayBuffer buf = (ArrayBuffer)new ArrayBuffer<T>().addAll(xs);
        int n = buf.length();
        int to$extension_end = 2;
        Range range = new Range.Inclusive(n, to$extension_end, 1).by(-1);
        if (range == null) {
            throw null;
        }
        Range foreach$mVc$sp_this = range;
        if (!foreach$mVc$sp_this.isEmpty()) {
            int foreach$mVc$sp_i = foreach$mVc$sp_this.start();
            while (true) {
                Random.$anonfun$shuffle$1(this, buf, foreach$mVc$sp_i);
                if (foreach$mVc$sp_i == foreach$mVc$sp_this.scala$collection$immutable$Range$$lastElement) break;
                foreach$mVc$sp_i += foreach$mVc$sp_this.step();
            }
        }
        Object var5_6 = null;
        Builder<T, C> builder = bf.newBuilder(xs);
        if (builder == null) {
            throw null;
        }
        return (C)((Builder)builder.addAll(buf)).result();
    }

    public LazyList<Object> alphanumeric() {
        Function0<LazyList.State> & Serializable continually_scala$collection$immutable$LazyList$$newLL_state = () -> LazyList$.$anonfun$continually$1(() -> this.nextAlphaNum$1());
        return new LazyList<Object>(continually_scala$collection$immutable$LazyList$$newLL_state);
    }

    public static final /* synthetic */ String $anonfun$between$1() {
        return "Invalid bounds";
    }

    public static final /* synthetic */ String $anonfun$between$2() {
        return "Invalid bounds";
    }

    public static final /* synthetic */ String $anonfun$between$3() {
        return "Invalid bounds";
    }

    private final int loop$1(int minInclusive$1, int maxExclusive$1) {
        int n;
        while ((n = this.nextInt()) < minInclusive$1 || n >= maxExclusive$1) {
        }
        return n;
    }

    public static final /* synthetic */ String $anonfun$nextLong$1() {
        return "n must be positive";
    }

    public static final /* synthetic */ String $anonfun$between$4() {
        return "Invalid bounds";
    }

    private final long loop$2(long minInclusive$2, long maxExclusive$2) {
        long n;
        while ((n = this.nextLong()) < minInclusive$2 || n >= maxExclusive$2) {
        }
        return n;
    }

    private final char safeChar$1() {
        int surrogateStart = 55296;
        return (char)(this.nextInt(surrogateStart - 1) + 1);
    }

    private static final void swap$1(int i1, int i2, ArrayBuffer buf$1) {
        Object tmp = buf$1.apply(i1);
        buf$1.update(i1, buf$1.apply(i2));
        buf$1.update(i2, tmp);
    }

    public static final /* synthetic */ void $anonfun$shuffle$1(Random $this, ArrayBuffer buf$1, int n) {
        int k = $this.nextInt(n);
        Random.swap$1(n - 1, k, buf$1);
    }

    private final char nextAlphaNum$1() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return chars.charAt(this.self().nextInt(chars.length()));
    }

    public Random(java.util.Random self) {
        this.self = self;
    }

    public Random(long seed) {
        this(new java.util.Random(seed));
    }

    public Random(int seed) {
        this((long)seed);
    }

    public Random() {
        this(new java.util.Random());
    }
}

