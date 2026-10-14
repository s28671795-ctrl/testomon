/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.ByteString;
import akka.util.ByteString$;
import akka.util.ByteString$ByteString1$;
import akka.util.ByteString$ByteStrings$;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import scala.Array$;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.AbstractSeq;
import scala.collection.IndexedSeq;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Vector;
import scala.collection.immutable.VectorBuilder;
import scala.collection.mutable.ArraySeq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.RichInt$;

@ScalaSignature(bytes="\u0006\u0005\t\rg\u0001\u0002\u001c8\u0005qBQA\u0015\u0001\u0005\u0002MCq!\u0016\u0001A\u0002\u0013%a\u000bC\u0004[\u0001\u0001\u0007I\u0011B.\t\r\u0005\u0004\u0001\u0015)\u0003X\u0011\u001d\u0011\u0007A1A\u0005\n\rDa!\u001d\u0001!\u0002\u0013!\u0007\"\u0003:\u0001\u0001\u0004\u0005\r\u0011\"\u0003t\u0011%9\b\u00011AA\u0002\u0013%\u0001\u0010C\u0005{\u0001\u0001\u0007\t\u0011)Q\u0005i\"91\u0010\u0001a\u0001\n\u00131\u0006b\u0002?\u0001\u0001\u0004%I! \u0005\u0007\u007f\u0002\u0001\u000b\u0015B,\t\u001b\u0005\u0005\u0001\u0001\"A\u0001\u0006\u0003\u0005\r\u0011\"\u0003W\u0011%\t\u0019\u0001\u0001a\u0001\n\u0013\t)\u0001\u0003\u0006\u0002\n\u0001\u0011\t\u0011!Q!\n]Cq!a\u0003\u0001\t#\ti\u0001C\u0004\u0002\"\u0001!)\"a\t\t\r\u0005M\u0003\u0001\"\u0001W\u0011\u001d\t)\u0006\u0001C!\u0003/Bq!a\u0017\u0001\t\u0013\ti\u0006C\u0004\u0002`\u0001!I!!\u0019\t\u000f\u0005\u001d\u0004\u0001\"\u0003\u0002j!9\u0011Q\u000f\u0001\u0005\n\u0005]\u0004bBA>\u0001\u0011\u0005\u0013Q\u0010\u0005\b\u0003\u0007\u0003A\u0011AAC\u0011\u001d\tY\t\u0001C\u0001\u0003\u001bCq!a#\u0001\t\u0003\n\t\n\u0003\u0005\u00020\u0002!\t!OAY\u0011\u001d\t)\f\u0001C\u0001\u0003oCq!!0\u0001\t\u0003\ty\fC\u0004\u0002F\u0002!\t!a2\t\u000f\u0005=\u0007\u0001\"\u0001\u0002R\"9\u0011\u0011\u001c\u0001\u0005\u0002\u0005m\u0007bBAu\u0001\u0011\u0005\u00111\u001e\u0005\b\u0003o\u0004A\u0011AA}\u0011\u001d\u00119\u0001\u0001C\u0001\u0005\u0013AqAa\u0006\u0001\t\u0003\u0011I\u0002C\u0004\u0003\u0018\u0001!\tAa\b\t\u000f\t%\u0002\u0001\"\u0001\u0003,!9!\u0011\u0006\u0001\u0005\u0002\tm\u0002b\u0002B$\u0001\u0011\u0005!\u0011\n\u0005\b\u0005\u000f\u0002A\u0011\u0001B*\u0011\u001d\u0011y\u0006\u0001C\u0001\u0005CBqAa\u0018\u0001\t\u0003\u0011Y\u0007C\u0004\u0003x\u0001!\tA!\u001f\t\u000f\t]\u0004\u0001\"\u0001\u0003\u0004\"9!q\u0012\u0001\u0005\u0002\tE\u0005b\u0002BH\u0001\u0011\u0005!1\u0014\u0005\b\u0005O\u0003A\u0011AA/\u0011\u001d\u0011I\u000b\u0001C\u0001\u0005WCqA!,\u0001\t\u0003\u0011y\u000bC\u0004\u0003>\u0002!\tAa0\t\u000f\t\u0005\u0007\u0001\"\u0001\u0003@\n\t\")\u001f;f'R\u0014\u0018N\\4Ck&dG-\u001a:\u000b\u0005aJ\u0014\u0001B;uS2T\u0011AO\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0007\u0001i4\t\u0005\u0002?\u00036\tqHC\u0001A\u0003\u0015\u00198-\u00197b\u0013\t\u0011uH\u0001\u0004B]f\u0014VM\u001a\t\u0005\t&[e*D\u0001F\u0015\t1u)A\u0004nkR\f'\r\\3\u000b\u0005!{\u0014AC2pY2,7\r^5p]&\u0011!*\u0012\u0002\b\u0005VLG\u000eZ3s!\tqD*\u0003\u0002N\u007f\t!!)\u001f;f!\ty\u0005+D\u00018\u0013\t\tvG\u0001\u0006CsR,7\u000b\u001e:j]\u001e\fa\u0001P5oSRtD#\u0001+\u0011\u0005=\u0003\u0011aB0mK:<G\u000f[\u000b\u0002/B\u0011a\bW\u0005\u00033~\u00121!\u00138u\u0003-yF.\u001a8hi\"|F%Z9\u0015\u0005q{\u0006C\u0001 ^\u0013\tqvH\u0001\u0003V]&$\bb\u00021\u0004\u0003\u0003\u0005\raV\u0001\u0004q\u0012\n\u0014\u0001C0mK:<G\u000f\u001b\u0011\u0002\u0011}\u0013W/\u001b7eKJ,\u0012\u0001\u001a\t\u0004K\"TW\"\u00014\u000b\u0005\u001d<\u0015!C5n[V$\u0018M\u00197f\u0013\tIgMA\u0007WK\u000e$xN\u001d\"vS2$WM\u001d\t\u0003W:t!a\u00147\n\u00055<\u0014A\u0003\"zi\u0016\u001cFO]5oO&\u0011q\u000e\u001d\u0002\f\u0005f$Xm\u0015;sS:<\u0017G\u0003\u0002no\u0005IqLY;jY\u0012,'\u000fI\u0001\u0006?R,W\u000e]\u000b\u0002iB\u0019a(^&\n\u0005Y|$!B!se\u0006L\u0018!C0uK6\u0004x\fJ3r)\ta\u0016\u0010C\u0004a\u0011\u0005\u0005\t\u0019\u0001;\u0002\r}#X-\u001c9!\u0003-yF/Z7q\u0019\u0016tw\r\u001e5\u0002\u001f}#X-\u001c9MK:<G\u000f[0%KF$\"\u0001\u0018@\t\u000f\u0001\\\u0011\u0011!a\u0001/\u0006aq\f^3na2+gn\u001a;iA\u0005Q\u0013m[6bIU$\u0018\u000e\u001c\u0013CsR,7\u000b\u001e:j]\u001e\u0014U/\u001b7eKJ$Ce\u0018;f[B\u001c\u0015\r]1dSRL\u0018!E0uK6\u00048)\u00199bG&$\u0018p\u0018\u0013fcR\u0019A,a\u0002\t\u000f\u0001t\u0011\u0011!a\u0001/\u0006Y\u0013m[6bIU$\u0018\u000e\u001c\u0013CsR,7\u000b\u001e:j]\u001e\u0014U/\u001b7eKJ$Ce\u0018;f[B\u001c\u0015\r]1dSRL\b%A\u0005gS2d\u0017I\u001d:bsR!\u0011qBA\u000f)\u0011\t\t\"a\u0005\u000e\u0003\u0001Aq!!\u0006\u0011\u0001\u0004\t9\"\u0001\u0003gS2d\u0007C\u0002 \u0002\u001aQ<F,C\u0002\u0002\u001c}\u0012\u0011BR;oGRLwN\u001c\u001a\t\r\u0005}\u0001\u00031\u0001X\u0003\raWM\\\u0001\u000fM&dGNQ=uK\n+hMZ3s)\u0019\t)#a\u0010\u0002BQ!\u0011\u0011CA\u0014\u0011\u001d\t)\"\u0005a\u0001\u0003S\u0001bAPA\u0016\u0003_a\u0016bAA\u0017\u007f\tIa)\u001e8di&|g.\r\t\u0005\u0003c\tY$\u0004\u0002\u00024)!\u0011QGA\u001c\u0003\rq\u0017n\u001c\u0006\u0003\u0003s\tAA[1wC&!\u0011QHA\u001a\u0005)\u0011\u0015\u0010^3Ck\u001a4WM\u001d\u0005\u0007\u0003?\t\u0002\u0019A,\t\u000f\u0005\r\u0013\u00031\u0001\u0002F\u0005I!-\u001f;f\u001fJ$WM\u001d\t\u0005\u0003c\t9%\u0003\u0003\u0002J\u0005M\"!\u0003\"zi\u0016|%\u000fZ3sQ\r\t\u0012Q\n\t\u0004}\u0005=\u0013bAA)\u007f\t1\u0011N\u001c7j]\u0016\fa\u0001\\3oORD\u0017\u0001C:ju\u0016D\u0015N\u001c;\u0015\u0007q\u000bI\u0006\u0003\u0004\u0002 M\u0001\raV\u0001\nG2,\u0017M\u001d+f[B$\u0012\u0001X\u0001\u000be\u0016\u001c\u0018N_3UK6\u0004Hc\u0001/\u0002d!1\u0011QM\u000bA\u0002]\u000bAa]5{K\u0006\u00192\u000f[8vY\u0012\u0014Vm]5{KR+W\u000e\u001d$peR!\u00111NA9!\rq\u0014QN\u0005\u0004\u0003_z$a\u0002\"p_2,\u0017M\u001c\u0005\u0007\u0003K2\u0002\u0019A,)\u0007Y\ti%\u0001\bf]N,(/\u001a+f[B\u001c\u0016N_3\u0015\u0007q\u000bI\b\u0003\u0004\u0002f]\u0001\raV\u0001\u0007C\u0012$wJ\\3\u0015\t\u0005E\u0011q\u0010\u0005\u0007\u0003\u0003C\u0002\u0019A&\u0002\t\u0015dW-\\\u0001\u000eIAdWo\u001d\u0013qYV\u001cH%Z9\u0015\t\u0005E\u0011q\u0011\u0005\u0007\u0003\u0013K\u0002\u0019\u0001(\u0002\u000b\tLH/Z:\u0002\r\u0005$G-\u00117m)\u0011\t\t\"a$\t\r\u0005%%\u00041\u0001O)\u0011\t\t\"a%\t\u000f\u0005U5\u00041\u0001\u0002\u0018\u0006\u0011\u0001p\u001d\t\u0006\u00033\u000bIk\u0013\b\u0005\u00037\u000b)K\u0004\u0003\u0002\u001e\u0006\rVBAAP\u0015\r\t\tkO\u0001\u0007yI|w\u000e\u001e \n\u0003\u0001K1!a*@\u0003\u001d\u0001\u0018mY6bO\u0016LA!a+\u0002.\na\u0011\n^3sC\ndWm\u00148dK*\u0019\u0011qU \u0002%A,HOQ=uK\u0006\u0013(/Y=V]N\fg-\u001a\u000b\u0005\u0003#\t\u0019\f\u0003\u0004\u0002\u0016r\u0001\r\u0001^\u0001\u0007CB\u0004XM\u001c3\u0015\t\u0005E\u0011\u0011\u0018\u0005\u0007\u0003wk\u0002\u0019\u0001(\u0002\u0005\t\u001c\u0018a\u00029vi\nKH/\u001a\u000b\u0005\u0003#\t\t\r\u0003\u0004\u0002Dz\u0001\raS\u0001\u0002q\u0006A\u0001/\u001e;TQ>\u0014H\u000f\u0006\u0003\u0002J\u00065G\u0003BA\t\u0003\u0017Dq!a\u0011 \u0001\b\t)\u0005\u0003\u0004\u0002D~\u0001\raV\u0001\u0007aV$\u0018J\u001c;\u0015\t\u0005M\u0017q\u001b\u000b\u0005\u0003#\t)\u000eC\u0004\u0002D\u0001\u0002\u001d!!\u0012\t\r\u0005\r\u0007\u00051\u0001X\u0003\u001d\u0001X\u000f\u001e'p]\u001e$B!!8\u0002bR!\u0011\u0011CAp\u0011\u001d\t\u0019%\ta\u0002\u0003\u000bBq!a1\"\u0001\u0004\t\u0019\u000fE\u0002?\u0003KL1!a:@\u0005\u0011auN\\4\u0002\u0017A,H\u000fT8oOB\u000b'\u000f\u001e\u000b\u0007\u0003[\f\t0a=\u0015\t\u0005E\u0011q\u001e\u0005\b\u0003\u0007\u0012\u00039AA#\u0011\u001d\t\u0019M\ta\u0001\u0003GDa!!>#\u0001\u00049\u0016!\u00018\u0002\u0011A,HO\u00127pCR$B!a?\u0002\u0000R!\u0011\u0011CA\u007f\u0011\u001d\t\u0019e\ta\u0002\u0003\u000bBq!a1$\u0001\u0004\u0011\t\u0001E\u0002?\u0005\u0007I1A!\u0002@\u0005\u00151En\\1u\u0003%\u0001X\u000f\u001e#pk\ndW\r\u0006\u0003\u0003\f\t=A\u0003BA\t\u0005\u001bAq!a\u0011%\u0001\b\t)\u0005C\u0004\u0002D\u0012\u0002\rA!\u0005\u0011\u0007y\u0012\u0019\"C\u0002\u0003\u0016}\u0012a\u0001R8vE2,\u0017\u0001\u00039vi\nKH/Z:\u0015\t\u0005E!1\u0004\u0005\u0007\u0005;)\u0003\u0019\u0001;\u0002\u000b\u0005\u0014(/Y=\u0015\u0011\u0005E!\u0011\u0005B\u0012\u0005OAaA!\b'\u0001\u0004!\bB\u0002B\u0013M\u0001\u0007q+A\u0003ti\u0006\u0014H\u000f\u0003\u0004\u0002 \u0019\u0002\raV\u0001\naV$8\u000b[8siN$BA!\f\u00032Q!\u0011\u0011\u0003B\u0018\u0011\u001d\t\u0019e\na\u0002\u0003\u000bBqA!\b(\u0001\u0004\u0011\u0019\u0004\u0005\u0003?k\nU\u0002c\u0001 \u00038%\u0019!\u0011H \u0003\u000bMCwN\u001d;\u0015\u0011\tu\"\u0011\tB\"\u0005\u000b\"B!!\u0005\u0003@!9\u00111\t\u0015A\u0004\u0005\u0015\u0003b\u0002B\u000fQ\u0001\u0007!1\u0007\u0005\u0007\u0005KA\u0003\u0019A,\t\r\u0005}\u0001\u00061\u0001X\u0003\u001d\u0001X\u000f^%oiN$BAa\u0013\u0003PQ!\u0011\u0011\u0003B'\u0011\u001d\t\u0019%\u000ba\u0002\u0003\u000bBqA!\b*\u0001\u0004\u0011\t\u0006E\u0002?k^#\u0002B!\u0016\u0003Z\tm#Q\f\u000b\u0005\u0003#\u00119\u0006C\u0004\u0002D)\u0002\u001d!!\u0012\t\u000f\tu!\u00061\u0001\u0003R!1!Q\u0005\u0016A\u0002]Ca!a\b+\u0001\u00049\u0016\u0001\u00039vi2{gnZ:\u0015\t\t\r$q\r\u000b\u0005\u0003#\u0011)\u0007C\u0004\u0002D-\u0002\u001d!!\u0012\t\u000f\tu1\u00061\u0001\u0003jA!a(^Ar)!\u0011iG!\u001d\u0003t\tUD\u0003BA\t\u0005_Bq!a\u0011-\u0001\b\t)\u0005C\u0004\u0003\u001e1\u0002\rA!\u001b\t\r\t\u0015B\u00061\u0001X\u0011\u0019\ty\u0002\fa\u0001/\u0006I\u0001/\u001e;GY>\fGo\u001d\u000b\u0005\u0005w\u0012y\b\u0006\u0003\u0002\u0012\tu\u0004bBA\"[\u0001\u000f\u0011Q\t\u0005\b\u0005;i\u0003\u0019\u0001BA!\u0011qTO!\u0001\u0015\u0011\t\u0015%\u0011\u0012BF\u0005\u001b#B!!\u0005\u0003\b\"9\u00111\t\u0018A\u0004\u0005\u0015\u0003b\u0002B\u000f]\u0001\u0007!\u0011\u0011\u0005\u0007\u0005Kq\u0003\u0019A,\t\r\u0005}a\u00061\u0001X\u0003)\u0001X\u000f\u001e#pk\ndWm\u001d\u000b\u0005\u0005'\u00139\n\u0006\u0003\u0002\u0012\tU\u0005bBA\"_\u0001\u000f\u0011Q\t\u0005\b\u0005;y\u0003\u0019\u0001BM!\u0011qTO!\u0005\u0015\u0011\tu%\u0011\u0015BR\u0005K#B!!\u0005\u0003 \"9\u00111\t\u0019A\u0004\u0005\u0015\u0003b\u0002B\u000fa\u0001\u0007!\u0011\u0014\u0005\u0007\u0005K\u0001\u0004\u0019A,\t\r\u0005}\u0001\u00071\u0001X\u0003\u0015\u0019G.Z1s\u0003\u0019\u0011Xm];miR\ta*\u0001\bbg>+H\u000f];u'R\u0014X-Y7\u0016\u0005\tE\u0006\u0003\u0002BZ\u0005sk!A!.\u000b\t\t]\u0016qG\u0001\u0003S>LAAa/\u00036\naq*\u001e;qkR\u001cFO]3b[\u00069\u0011n]#naRLXCAA6\u0003!qwN\\#naRL\b")
public final class ByteStringBuilder
implements Builder<Object, ByteString> {
    private int _length;
    private final VectorBuilder<ByteString.ByteString1> _builder;
    private byte[] _temp;
    private int _tempLength;
    private int akka$util$ByteStringBuilder$$_tempCapacity;

    @Override
    public final void sizeHint(IterableOnce<?> coll, int delta) {
        Builder.sizeHint$(this, coll, delta);
    }

    @Override
    public final int sizeHint$default$2() {
        return Builder.sizeHint$default$2$(this);
    }

    @Override
    public final void sizeHintBounded(int size, Iterable<?> boundingColl) {
        Builder.sizeHintBounded$(this, size, boundingColl);
    }

    @Override
    public <NewTo> Builder<Object, NewTo> mapResult(Function1<ByteString, NewTo> f) {
        return Builder.mapResult$(this, f);
    }

    @Override
    public final Growable $plus$eq(Object elem) {
        return Growable.$plus$eq$(this, elem);
    }

    @Override
    public final Growable $plus$eq(Object elem1, Object elem2, Seq elems) {
        return Growable.$plus$eq$(this, elem1, elem2, elems);
    }

    @Override
    public final Growable<Object> $plus$plus$eq(IterableOnce<Object> xs) {
        return Growable.$plus$plus$eq$(this, xs);
    }

    @Override
    public int knownSize() {
        return Growable.knownSize$(this);
    }

    private int _length() {
        return this._length;
    }

    private void _length_$eq(int x$1) {
        this._length = x$1;
    }

    private VectorBuilder<ByteString.ByteString1> _builder() {
        return this._builder;
    }

    private byte[] _temp() {
        return this._temp;
    }

    private void _temp_$eq(byte[] x$1) {
        this._temp = x$1;
    }

    private int _tempLength() {
        return this._tempLength;
    }

    private void _tempLength_$eq(int x$1) {
        this._tempLength = x$1;
    }

    public int akka$util$ByteStringBuilder$$_tempCapacity() {
        return this.akka$util$ByteStringBuilder$$_tempCapacity;
    }

    private void _tempCapacity_$eq(int x$1) {
        this.akka$util$ByteStringBuilder$$_tempCapacity = x$1;
    }

    public ByteStringBuilder fillArray(int len, Function2<byte[], Object, BoxedUnit> fill) {
        this.ensureTempSize(this._tempLength() + len);
        fill.apply(this._temp(), BoxesRunTime.boxToInteger(this._tempLength()));
        this._tempLength_$eq(this._tempLength() + len);
        this._length_$eq(this._length() + len);
        return this;
    }

    public final ByteStringBuilder fillByteBuffer(int len, ByteOrder byteOrder, Function1<ByteBuffer, BoxedUnit> fill) {
        return this.fillArray(len, (Function2<byte[], Object, BoxedUnit>)(Function2<byte[], Object, Object> & Serializable)(x0$1, x1$1) -> {
            ByteStringBuilder.$anonfun$fillByteBuffer$1(len, byteOrder, fill, x0$1, BoxesRunTime.unboxToInt(x1$1));
            return BoxedUnit.UNIT;
        });
    }

    public int length() {
        return this._length();
    }

    @Override
    public void sizeHint(int len) {
        this.resizeTemp(len - (this._length() - this._tempLength()));
    }

    private void clearTemp() {
        if (this._tempLength() > 0) {
            byte[] arr = new byte[this._tempLength()];
            Array$.MODULE$.copy(this._temp(), 0, arr, 0, this._tempLength());
            this._builder().$plus$eq(ByteString$ByteString1$.MODULE$.apply(arr));
            this._tempLength_$eq(0);
            return;
        }
    }

    private void resizeTemp(int size) {
        byte[] newtemp = new byte[size];
        if (this._tempLength() > 0) {
            Array$.MODULE$.copy(this._temp(), 0, newtemp, 0, this._tempLength());
        }
        this._temp_$eq(newtemp);
        this._tempCapacity_$eq(this._temp().length);
    }

    private boolean shouldResizeTempFor(int size) {
        return this.akka$util$ByteStringBuilder$$_tempCapacity() < size || this.akka$util$ByteStringBuilder$$_tempCapacity() == 0;
    }

    private void ensureTempSize(int size) {
        if (this.shouldResizeTempFor(size)) {
            int newSize;
            int n = newSize = this.akka$util$ByteStringBuilder$$_tempCapacity() == 0 ? 16 : this.akka$util$ByteStringBuilder$$_tempCapacity() * 2;
            while (newSize < size) {
                newSize *= 2;
            }
            this.resizeTemp(newSize);
            return;
        }
    }

    public ByteStringBuilder addOne(byte elem) {
        this.ensureTempSize(this._tempLength() + 1);
        this._temp()[this._tempLength()] = elem;
        this._tempLength_$eq(this._tempLength() + 1);
        this._length_$eq(this._length() + 1);
        return this;
    }

    public ByteStringBuilder $plus$plus$eq(ByteString bytes) {
        return this.addAll(bytes);
    }

    public ByteStringBuilder addAll(ByteString bytes) {
        if (bytes.nonEmpty()) {
            this.clearTemp();
            ByteString byteString = bytes;
            if (byteString instanceof ByteString.ByteString1C) {
                ByteString.ByteString1C byteString1C = (ByteString.ByteString1C)byteString;
                this._builder().$plus$eq(byteString1C.toByteString1());
                this._length_$eq(this._length() + byteString1C.length());
            } else if (byteString instanceof ByteString.ByteString1) {
                ByteString.ByteString1 byteString1 = (ByteString.ByteString1)byteString;
                this._builder().$plus$eq(byteString1);
                this._length_$eq(this._length() + byteString1.length());
            } else if (byteString instanceof ByteString.ByteStrings) {
                ByteString.ByteStrings byteStrings = (ByteString.ByteStrings)byteString;
                this._builder().$plus$plus$eq(byteStrings.bytestrings());
                this._length_$eq(this._length() + byteStrings.length());
            } else {
                throw new MatchError(byteString);
            }
        }
        return this;
    }

    public ByteStringBuilder addAll(IterableOnce<Object> xs) {
        block1: {
            block4: {
                IndexedSeq indexedSeq;
                boolean bl;
                block3: {
                    IterableOnce<Object> iterableOnce;
                    block2: {
                        block0: {
                            bl = false;
                            indexedSeq = null;
                            iterableOnce = xs;
                            if (!(iterableOnce instanceof ByteString)) break block0;
                            ByteString byteString = (ByteString)iterableOnce;
                            this.addAll(byteString);
                            break block1;
                        }
                        if (!(iterableOnce instanceof ArraySeq.ofByte)) break block2;
                        ArraySeq.ofByte ofByte2 = (ArraySeq.ofByte)iterableOnce;
                        if (!ofByte2.nonEmpty()) break block1;
                        this.putByteArrayUnsafe((byte[])ofByte2.array().clone());
                        break block1;
                    }
                    if (!(iterableOnce instanceof IndexedSeq)) break block3;
                    bl = true;
                    indexedSeq = (IndexedSeq)iterableOnce;
                    if (!this.shouldResizeTempFor(indexedSeq.length())) break block3;
                    if (!indexedSeq.nonEmpty()) break block1;
                    byte[] copied = (byte[])Array$.MODULE$.from(xs, ClassTag$.MODULE$.Byte());
                    this.clearTemp();
                    this._builder().$plus$eq(ByteString$ByteString1$.MODULE$.apply(copied));
                    this._length_$eq(this._length() + indexedSeq.length());
                    break block1;
                }
                if (!bl) break block4;
                if (!indexedSeq.nonEmpty()) break block1;
                this.ensureTempSize(this._tempLength() + indexedSeq.size());
                indexedSeq.copyToArray(this._temp(), this._tempLength());
                this._tempLength_$eq(this._tempLength() + indexedSeq.length());
                this._length_$eq(this._length() + indexedSeq.length());
                break block1;
            }
            Growable.$plus$plus$eq$(this, xs);
        }
        return this;
    }

    public ByteStringBuilder putByteArrayUnsafe(byte[] xs) {
        this.clearTemp();
        this._builder().$plus$eq(ByteString$ByteString1$.MODULE$.apply(xs));
        this._length_$eq(this._length() + xs.length);
        return this;
    }

    public ByteStringBuilder append(ByteString bs) {
        if (bs.isEmpty()) {
            return this;
        }
        return this.$plus$plus$eq(bs);
    }

    public ByteStringBuilder putByte(byte x) {
        return (ByteStringBuilder)this.$plus$eq(BoxesRunTime.boxToByte(x));
    }

    public ByteStringBuilder putShort(int x, ByteOrder byteOrder) {
        ByteOrder byteOrder2 = byteOrder;
        ByteOrder byteOrder3 = ByteOrder.BIG_ENDIAN;
        if (!(byteOrder2 != null ? !byteOrder2.equals(byteOrder3) : byteOrder3 != null)) {
            this.$plus$eq(BoxesRunTime.boxToByte((byte)(x >>> 8)));
            return (ByteStringBuilder)this.$plus$eq(BoxesRunTime.boxToByte((byte)(x >>> 0)));
        }
        ByteOrder byteOrder4 = byteOrder;
        ByteOrder byteOrder5 = ByteOrder.LITTLE_ENDIAN;
        if (!(byteOrder4 != null ? !byteOrder4.equals(byteOrder5) : byteOrder5 != null)) {
            this.$plus$eq(BoxesRunTime.boxToByte((byte)(x >>> 0)));
            return (ByteStringBuilder)this.$plus$eq(BoxesRunTime.boxToByte((byte)(x >>> 8)));
        }
        throw new IllegalArgumentException(new StringBuilder(19).append("Unknown byte order ").append(byteOrder).toString());
    }

    public ByteStringBuilder putInt(int x, ByteOrder byteOrder) {
        this.fillArray(4, (Function2<byte[], Object, BoxedUnit>)(Function2<byte[], Object, Object> & Serializable)(target, offset) -> {
            ByteStringBuilder.$anonfun$putInt$1(byteOrder, x, target, BoxesRunTime.unboxToInt(offset));
            return BoxedUnit.UNIT;
        });
        return this;
    }

    public ByteStringBuilder putLong(long x, ByteOrder byteOrder) {
        this.fillArray(8, (Function2<byte[], Object, BoxedUnit>)(Function2<byte[], Object, Object> & Serializable)(target, offset) -> {
            ByteStringBuilder.$anonfun$putLong$1(byteOrder, x, target, BoxesRunTime.unboxToInt(offset));
            return BoxedUnit.UNIT;
        });
        return this;
    }

    public ByteStringBuilder putLongPart(long x, int n, ByteOrder byteOrder) {
        return this.fillArray(n, (Function2<byte[], Object, BoxedUnit>)(Function2<byte[], Object, Object> & Serializable)(target, offset) -> {
            ByteStringBuilder.$anonfun$putLongPart$1(byteOrder, n, x, target, BoxesRunTime.unboxToInt(offset));
            return BoxedUnit.UNIT;
        });
    }

    public ByteStringBuilder putFloat(float x, ByteOrder byteOrder) {
        return this.putInt(Float.floatToRawIntBits(x), byteOrder);
    }

    public ByteStringBuilder putDouble(double x, ByteOrder byteOrder) {
        return this.putLong(Double.doubleToRawLongBits(x), byteOrder);
    }

    public ByteStringBuilder putBytes(byte[] array) {
        return this.putBytes(array, 0, array.length);
    }

    public ByteStringBuilder putBytes(byte[] array, int start2, int len) {
        return this.fillArray(len, (Function2<byte[], Object, BoxedUnit>)(Function2<byte[], Object, Object> & Serializable)(x0$1, x1$1) -> {
            ByteStringBuilder.$anonfun$putBytes$1(array, start2, len, x0$1, BoxesRunTime.unboxToInt(x1$1));
            return BoxedUnit.UNIT;
        });
    }

    public ByteStringBuilder putShorts(short[] array, ByteOrder byteOrder) {
        return this.putShorts(array, 0, array.length, byteOrder);
    }

    public ByteStringBuilder putShorts(short[] array, int start2, int len, ByteOrder byteOrder) {
        return this.fillByteBuffer(len * 2, byteOrder, (Function1<ByteBuffer, BoxedUnit>)(Function1<ByteBuffer, Object> & Serializable)x$15 -> {
            ByteStringBuilder.$anonfun$putShorts$1(array, start2, len, x$15);
            return BoxedUnit.UNIT;
        });
    }

    public ByteStringBuilder putInts(int[] array, ByteOrder byteOrder) {
        return this.putInts(array, 0, array.length, byteOrder);
    }

    public ByteStringBuilder putInts(int[] array, int start2, int len, ByteOrder byteOrder) {
        return this.fillByteBuffer(len * 4, byteOrder, (Function1<ByteBuffer, BoxedUnit>)(Function1<ByteBuffer, Object> & Serializable)x$16 -> {
            ByteStringBuilder.$anonfun$putInts$1(array, start2, len, x$16);
            return BoxedUnit.UNIT;
        });
    }

    public ByteStringBuilder putLongs(long[] array, ByteOrder byteOrder) {
        return this.putLongs(array, 0, array.length, byteOrder);
    }

    public ByteStringBuilder putLongs(long[] array, int start2, int len, ByteOrder byteOrder) {
        return this.fillByteBuffer(len * 8, byteOrder, (Function1<ByteBuffer, BoxedUnit>)(Function1<ByteBuffer, Object> & Serializable)x$17 -> {
            ByteStringBuilder.$anonfun$putLongs$1(array, start2, len, x$17);
            return BoxedUnit.UNIT;
        });
    }

    public ByteStringBuilder putFloats(float[] array, ByteOrder byteOrder) {
        return this.putFloats(array, 0, array.length, byteOrder);
    }

    public ByteStringBuilder putFloats(float[] array, int start2, int len, ByteOrder byteOrder) {
        return this.fillByteBuffer(len * 4, byteOrder, (Function1<ByteBuffer, BoxedUnit>)(Function1<ByteBuffer, Object> & Serializable)x$18 -> {
            ByteStringBuilder.$anonfun$putFloats$1(array, start2, len, x$18);
            return BoxedUnit.UNIT;
        });
    }

    public ByteStringBuilder putDoubles(double[] array, ByteOrder byteOrder) {
        return this.putDoubles(array, 0, array.length, byteOrder);
    }

    public ByteStringBuilder putDoubles(double[] array, int start2, int len, ByteOrder byteOrder) {
        return this.fillByteBuffer(len * 8, byteOrder, (Function1<ByteBuffer, BoxedUnit>)(Function1<ByteBuffer, Object> & Serializable)x$19 -> {
            ByteStringBuilder.$anonfun$putDoubles$1(array, start2, len, x$19);
            return BoxedUnit.UNIT;
        });
    }

    @Override
    public void clear() {
        this._builder().clear();
        this._length_$eq(0);
        this._tempLength_$eq(0);
    }

    @Override
    public ByteString result() {
        if (this._length() == 0) {
            return ByteString$.MODULE$.empty();
        }
        this.clearTemp();
        Object bytestrings = this._builder().result();
        if (((AbstractSeq)bytestrings).size() == 1) {
            return (ByteString)((Vector)bytestrings).head();
        }
        return ByteString$ByteStrings$.MODULE$.apply((Vector<ByteString.ByteString1>)bytestrings, this._length());
    }

    public OutputStream asOutputStream() {
        return new OutputStream(this){
            private final /* synthetic */ ByteStringBuilder $outer;

            public void write(int b) {
                this.$outer.$plus$eq(BoxesRunTime.boxToByte((byte)b));
            }

            public void write(byte[] b, int off, int len) {
                this.$outer.putBytes(b, off, len);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public boolean isEmpty() {
        return this._length() == 0;
    }

    public boolean nonEmpty() {
        return this._length() > 0;
    }

    public static final /* synthetic */ void $anonfun$fillByteBuffer$1(int len$1, ByteOrder byteOrder$1, Function1 fill$1, byte[] x0$1, int x1$1) {
        Tuple2<byte[], Integer> tuple2 = new Tuple2<byte[], Integer>(x0$1, BoxesRunTime.boxToInteger(x1$1));
        if (tuple2 != null) {
            byte[] array = tuple2._1();
            int start2 = tuple2._2$mcI$sp();
            ByteBuffer buffer = ByteBuffer.wrap(array, start2, len$1);
            buffer.order(byteOrder$1);
            BoxedUnit cfr_ignored_0 = (BoxedUnit)fill$1.apply(buffer);
            return;
        }
        throw new MatchError(tuple2);
    }

    public static final /* synthetic */ void $anonfun$putInt$1(ByteOrder byteOrder$2, int x$20, byte[] target, int offset) {
        ByteOrder byteOrder = byteOrder$2;
        ByteOrder byteOrder2 = ByteOrder.BIG_ENDIAN;
        if (!(byteOrder != null ? !byteOrder.equals(byteOrder2) : byteOrder2 != null)) {
            target[offset + 0] = (byte)(x$20 >>> 24);
            target[offset + 1] = (byte)(x$20 >>> 16);
            target[offset + 2] = (byte)(x$20 >>> 8);
            target[offset + 3] = (byte)(x$20 >>> 0);
            return;
        }
        ByteOrder byteOrder3 = byteOrder$2;
        ByteOrder byteOrder4 = ByteOrder.LITTLE_ENDIAN;
        if (!(byteOrder3 != null ? !byteOrder3.equals(byteOrder4) : byteOrder4 != null)) {
            target[offset + 0] = (byte)(x$20 >>> 0);
            target[offset + 1] = (byte)(x$20 >>> 8);
            target[offset + 2] = (byte)(x$20 >>> 16);
            target[offset + 3] = (byte)(x$20 >>> 24);
            return;
        }
        throw new IllegalArgumentException(new StringBuilder(19).append("Unknown byte order ").append(byteOrder$2).toString());
    }

    public static final /* synthetic */ void $anonfun$putLong$1(ByteOrder byteOrder$3, long x$21, byte[] target, int offset) {
        ByteOrder byteOrder = byteOrder$3;
        ByteOrder byteOrder2 = ByteOrder.BIG_ENDIAN;
        if (!(byteOrder != null ? !byteOrder.equals(byteOrder2) : byteOrder2 != null)) {
            target[offset + 0] = (byte)(x$21 >>> 56);
            target[offset + 1] = (byte)(x$21 >>> 48);
            target[offset + 2] = (byte)(x$21 >>> 40);
            target[offset + 3] = (byte)(x$21 >>> 32);
            target[offset + 4] = (byte)(x$21 >>> 24);
            target[offset + 5] = (byte)(x$21 >>> 16);
            target[offset + 6] = (byte)(x$21 >>> 8);
            target[offset + 7] = (byte)(x$21 >>> 0);
            return;
        }
        ByteOrder byteOrder3 = byteOrder$3;
        ByteOrder byteOrder4 = ByteOrder.LITTLE_ENDIAN;
        if (!(byteOrder3 != null ? !byteOrder3.equals(byteOrder4) : byteOrder4 != null)) {
            target[offset + 0] = (byte)(x$21 >>> 0);
            target[offset + 1] = (byte)(x$21 >>> 8);
            target[offset + 2] = (byte)(x$21 >>> 16);
            target[offset + 3] = (byte)(x$21 >>> 24);
            target[offset + 4] = (byte)(x$21 >>> 32);
            target[offset + 5] = (byte)(x$21 >>> 40);
            target[offset + 6] = (byte)(x$21 >>> 48);
            target[offset + 7] = (byte)(x$21 >>> 56);
            return;
        }
        throw new IllegalArgumentException(new StringBuilder(19).append("Unknown byte order ").append(byteOrder$3).toString());
    }

    public static final /* synthetic */ void $anonfun$putLongPart$1(ByteOrder byteOrder$4, int n$2, long x$22, byte[] target, int offset) {
        ByteOrder byteOrder = byteOrder$4;
        ByteOrder byteOrder2 = ByteOrder.BIG_ENDIAN;
        if (!(byteOrder != null ? !byteOrder.equals(byteOrder2) : byteOrder2 != null)) {
            int start2 = n$2 * 8 - 8;
            RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), n$2).foreach$mVc$sp(i -> {
                target$1[offset$1 + i] = (byte)(x$22 >>> start2 - 8 * i);
            });
            return;
        }
        ByteOrder byteOrder3 = byteOrder$4;
        ByteOrder byteOrder4 = ByteOrder.LITTLE_ENDIAN;
        if (!(byteOrder3 != null ? !byteOrder3.equals(byteOrder4) : byteOrder4 != null)) {
            RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), n$2).foreach$mVc$sp(i -> {
                target$1[offset$1 + i] = (byte)(x$22 >>> 8 * i);
            });
            return;
        }
        throw new IllegalArgumentException(new StringBuilder(19).append("Unknown byte order ").append(byteOrder$4).toString());
    }

    public static final /* synthetic */ void $anonfun$putBytes$1(byte[] array$1, int start$2, int len$2, byte[] x0$1, int x1$1) {
        Tuple2<byte[], Integer> tuple2 = new Tuple2<byte[], Integer>(x0$1, BoxesRunTime.boxToInteger(x1$1));
        if (tuple2 != null) {
            byte[] target = tuple2._1();
            int targetOffset = tuple2._2$mcI$sp();
            Array$.MODULE$.copy(array$1, start$2, target, targetOffset, len$2);
            return;
        }
        throw new MatchError(tuple2);
    }

    public static final /* synthetic */ void $anonfun$putShorts$1(short[] array$2, int start$3, int len$3, ByteBuffer x$15) {
        x$15.asShortBuffer().put(array$2, start$3, len$3);
    }

    public static final /* synthetic */ void $anonfun$putInts$1(int[] array$3, int start$4, int len$4, ByteBuffer x$16) {
        x$16.asIntBuffer().put(array$3, start$4, len$4);
    }

    public static final /* synthetic */ void $anonfun$putLongs$1(long[] array$4, int start$5, int len$5, ByteBuffer x$17) {
        x$17.asLongBuffer().put(array$4, start$5, len$5);
    }

    public static final /* synthetic */ void $anonfun$putFloats$1(float[] array$5, int start$6, int len$6, ByteBuffer x$18) {
        x$18.asFloatBuffer().put(array$5, start$6, len$6);
    }

    public static final /* synthetic */ void $anonfun$putDoubles$1(double[] array$6, int start$7, int len$7, ByteBuffer x$19) {
        x$19.asDoubleBuffer().put(array$6, start$7, len$7);
    }

    public ByteStringBuilder() {
        Growable.$init$(this);
        Builder.$init$(this);
        this._length = 0;
        this._builder = new VectorBuilder();
        this._tempLength = 0;
        this.akka$util$ByteStringBuilder$$_tempCapacity = 0;
    }
}

