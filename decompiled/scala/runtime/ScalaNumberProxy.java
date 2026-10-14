/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.math.Numeric;
import scala.math.ScalaNumericAnyConversions;
import scala.reflect.ScalaSignature;
import scala.runtime.OrderedProxy;

@ScalaSignature(bytes="\u0006\u0005Q4qAD\b\u0011\u0002\u0007\u0005A\u0003C\u00034\u0001\u0011\u0005A\u0007C\u00039\u0001\u0019M\u0011\bC\u0003B\u0001\u0011\u0005!\tC\u0003G\u0001\u0011\u0005q\tC\u0003L\u0001\u0011\u0005A\nC\u0003Q\u0001\u0011\u0005\u0011\u000bC\u0003V\u0001\u0011\u0005a\u000bC\u0003[\u0001\u0011\u00051\fC\u0003`\u0001\u0011\u0005\u0001\rC\u0003d\u0001\u0011\u0005A\rC\u0003g\u0001\u0011\u0005q\rC\u0003i\u0001\u0011\u0005q\rC\u0003j\u0001\u0011\u0005\u0011K\u0001\tTG\u0006d\u0017MT;nE\u0016\u0014\bK]8ys*\u0011\u0001#E\u0001\beVtG/[7f\u0015\u0005\u0011\u0012!B:dC2\f7\u0001A\u000b\u0003+%\u001aR\u0001\u0001\f\u001bA=\u0002\"a\u0006\r\u000e\u0003EI!!G\t\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u001c=5\tAD\u0003\u0002\u001e#\u0005!Q.\u0019;i\u0013\tyBD\u0001\u000eTG\u0006d\u0017MT;nKJL7-\u00118z\u0007>tg/\u001a:tS>t7\u000fE\u0002\"I\u001dr!a\u0006\u0012\n\u0005\r\n\u0012!\u0002)s_bL\u0018BA\u0013'\u0005\u0015!\u0016\u0010]3e\u0015\t\u0019\u0013\u0003\u0005\u0002)S1\u0001A!\u0002\u0016\u0001\u0005\u0004Y#!\u0001+\u0012\u000512\u0002CA\f.\u0013\tq\u0013CA\u0004O_RD\u0017N\\4\u0011\u0007A\nt%D\u0001\u0010\u0013\t\u0011tB\u0001\u0007Pe\u0012,'/\u001a3Qe>D\u00180\u0001\u0004%S:LG\u000f\n\u000b\u0002kA\u0011qCN\u0005\u0003oE\u0011A!\u00168ji\u0006\u0019a.^7\u0016\u0003i\u00022a\u000f (\u001d\t9B(\u0003\u0002>#\u00059\u0001/Y2lC\u001e,\u0017BA A\u0005\u001dqU/\\3sS\u000eT!!P\t\u0002\u0017\u0011|WO\u00197f-\u0006dW/Z\u000b\u0002\u0007B\u0011q\u0003R\u0005\u0003\u000bF\u0011a\u0001R8vE2,\u0017A\u00034m_\u0006$h+\u00197vKV\t\u0001\n\u0005\u0002\u0018\u0013&\u0011!*\u0005\u0002\u0006\r2|\u0017\r^\u0001\nY>twMV1mk\u0016,\u0012!\u0014\t\u0003/9K!aT\t\u0003\t1{gnZ\u0001\tS:$h+\u00197vKV\t!\u000b\u0005\u0002\u0018'&\u0011A+\u0005\u0002\u0004\u0013:$\u0018!\u00032zi\u00164\u0016\r\\;f+\u00059\u0006CA\fY\u0013\tI\u0016C\u0001\u0003CsR,\u0017AC:i_J$h+\u00197vKV\tA\f\u0005\u0002\u0018;&\u0011a,\u0005\u0002\u0006'\"|'\u000f^\u0001\u0004[&tGCA\u0014b\u0011\u0015\u0011\u0017\u00021\u0001(\u0003\u0011!\b.\u0019;\u0002\u00075\f\u0007\u0010\u0006\u0002(K\")!M\u0003a\u0001O\u0005\u0019\u0011MY:\u0016\u0003\u001d\nAa]5h]\u000611/[4ok6Dc!D6o_F\u0014\bCA\fm\u0013\ti\u0017C\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-I\u0001q\u0003e)8/\u001a\u0011ag&<g\u000e\u0019\u0011nKRDw\u000e\u001a\u0011j]N$X-\u00193\u0002\u000bMLgnY3\"\u0003M\faA\r\u00182g9\u0002\u0004")
public interface ScalaNumberProxy<T>
extends ScalaNumericAnyConversions,
OrderedProxy<T> {
    public Numeric<T> num();

    public static /* synthetic */ double doubleValue$(ScalaNumberProxy $this) {
        return $this.doubleValue();
    }

    @Override
    default public double doubleValue() {
        return this.num().toDouble(this.self());
    }

    public static /* synthetic */ float floatValue$(ScalaNumberProxy $this) {
        return $this.floatValue();
    }

    @Override
    default public float floatValue() {
        return this.num().toFloat(this.self());
    }

    public static /* synthetic */ long longValue$(ScalaNumberProxy $this) {
        return $this.longValue();
    }

    @Override
    default public long longValue() {
        return this.num().toLong(this.self());
    }

    public static /* synthetic */ int intValue$(ScalaNumberProxy $this) {
        return $this.intValue();
    }

    @Override
    default public int intValue() {
        return this.num().toInt(this.self());
    }

    public static /* synthetic */ byte byteValue$(ScalaNumberProxy $this) {
        return $this.byteValue();
    }

    @Override
    default public byte byteValue() {
        return (byte)this.intValue();
    }

    public static /* synthetic */ short shortValue$(ScalaNumberProxy $this) {
        return $this.shortValue();
    }

    @Override
    default public short shortValue() {
        return (short)this.intValue();
    }

    public static /* synthetic */ Object min$(ScalaNumberProxy $this, Object that) {
        return $this.min(that);
    }

    default public T min(T that) {
        return this.num().min(this.self(), that);
    }

    public static /* synthetic */ Object max$(ScalaNumberProxy $this, Object that) {
        return $this.max(that);
    }

    default public T max(T that) {
        return this.num().max(this.self(), that);
    }

    public static /* synthetic */ Object abs$(ScalaNumberProxy $this) {
        return $this.abs();
    }

    default public T abs() {
        return this.num().abs(this.self());
    }

    public static /* synthetic */ Object sign$(ScalaNumberProxy $this) {
        return $this.sign();
    }

    default public T sign() {
        return this.num().sign(this.self());
    }

    public static /* synthetic */ int signum$(ScalaNumberProxy $this) {
        return $this.signum();
    }

    default public int signum() {
        return this.num().signum(this.self());
    }

    public static void $init$(ScalaNumberProxy $this) {
    }
}

