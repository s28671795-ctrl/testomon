/*
 * Decompiled with CFR 0.152.
 */
package scala.io;

import java.io.EOFException;
import java.text.MessageFormat;
import scala.Console$;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.IterableOps;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005ea\u0001\u0003\u000b\u0016!\u0003\r\taF\r\t\u000by\u0001A\u0011\u0001\u0011\t\u000b\u0011\u0002A\u0011A\u0013\t\u000b\u0011\u0002A\u0011A\u0019\t\u000bq\u0002A\u0011A\u001f\t\u000b\u0005\u0003A\u0011\u0001\"\t\u000b\u0019\u0003A\u0011A$\t\u000b-\u0003A\u0011\u0001'\t\u000bA\u0003A\u0011A)\t\u000bU\u0003A\u0011\u0001,\t\u000bi\u0003A\u0011A.\t\u000b}\u0003A\u0011\u00011\t\u000b\u0011\u0004A\u0011A3\t\u000b=\u0004A\u0011\u00019\t\u000bI\u0004A\u0011A:\t\u000ba\u0004A\u0011A=\t\u000by\u0004A\u0011B@\b\u000f\u0005-Q\u0003#\u0001\u0002\u000e\u00191A#\u0006E\u0001\u0003#Aq!!\u0006\u0013\t\u0003\t9BA\u0003Ti\u0012LeN\u0003\u0002\u0017/\u0005\u0011\u0011n\u001c\u0006\u00021\u0005)1oY1mCN\u0011\u0001A\u0007\t\u00037qi\u0011aF\u0005\u0003;]\u0011a!\u00118z%\u00164\u0017A\u0002\u0013j]&$He\u0001\u0001\u0015\u0003\u0005\u0002\"a\u0007\u0012\n\u0005\r:\"\u0001B+oSR\f\u0001B]3bI2Kg.\u001a\u000b\u0002MA\u0011qE\f\b\u0003Q1\u0002\"!K\f\u000e\u0003)R!aK\u0010\u0002\rq\u0012xn\u001c;?\u0013\tis#\u0001\u0004Qe\u0016$WMZ\u0005\u0003_A\u0012aa\u0015;sS:<'BA\u0017\u0018)\r1#\u0007\u000e\u0005\u0006g\r\u0001\rAJ\u0001\u0005i\u0016DH\u000fC\u00036\u0007\u0001\u0007a'\u0001\u0003be\u001e\u001c\bcA\u000e8s%\u0011\u0001h\u0006\u0002\u000byI,\u0007/Z1uK\u0012t\u0004CA\u000e;\u0013\tYtCA\u0002B]f\f1B]3bI\n{w\u000e\\3b]R\ta\b\u0005\u0002\u001c\u007f%\u0011\u0001i\u0006\u0002\b\u0005>|G.Z1o\u0003!\u0011X-\u00193CsR,G#A\"\u0011\u0005m!\u0015BA#\u0018\u0005\u0011\u0011\u0015\u0010^3\u0002\u0013I,\u0017\rZ*i_J$H#\u0001%\u0011\u0005mI\u0015B\u0001&\u0018\u0005\u0015\u0019\u0006n\u001c:u\u0003!\u0011X-\u00193DQ\u0006\u0014H#A'\u0011\u0005mq\u0015BA(\u0018\u0005\u0011\u0019\u0005.\u0019:\u0002\u000fI,\u0017\rZ%oiR\t!\u000b\u0005\u0002\u001c'&\u0011Ak\u0006\u0002\u0004\u0013:$\u0018\u0001\u0003:fC\u0012duN\\4\u0015\u0003]\u0003\"a\u0007-\n\u0005e;\"\u0001\u0002'p]\u001e\f\u0011B]3bI\u001acw.\u0019;\u0015\u0003q\u0003\"aG/\n\u0005y;\"!\u0002$m_\u0006$\u0018A\u0003:fC\u0012$u.\u001e2mKR\t\u0011\r\u0005\u0002\u001cE&\u00111m\u0006\u0002\u0007\t>,(\r\\3\u0002\u000bI,\u0017\r\u001a4\u0015\u0005\u0019l\u0007cA4ks9\u00111\u0004[\u0005\u0003S^\tq\u0001]1dW\u0006<W-\u0003\u0002lY\n!A*[:u\u0015\tIw\u0003C\u0003o\u0019\u0001\u0007a%\u0001\u0004g_Jl\u0017\r^\u0001\u0007e\u0016\fGMZ\u0019\u0015\u0005e\n\b\"\u00028\u000e\u0001\u00041\u0013A\u0002:fC\u00124'\u0007\u0006\u0002uoB!1$^\u001d:\u0013\t1xC\u0001\u0004UkBdWM\r\u0005\u0006]:\u0001\rAJ\u0001\u0007e\u0016\fGMZ\u001a\u0015\u0005il\b#B\u000e|seJ\u0014B\u0001?\u0018\u0005\u0019!V\u000f\u001d7fg!)an\u0004a\u0001M\u0005qA/\u001a=u\u0007>l\u0007o\u001c8f]R\u001cHc\u00014\u0002\u0002!9\u00111\u0001\tA\u0002\u0005\u0015\u0011!A1\u0011\tm\t9AG\u0005\u0004\u0003\u00139\"!B!se\u0006L\u0018!B*uI&s\u0007cAA\b%5\tQc\u0005\u0003\u00135\u0005M\u0001cAA\b\u0001\u00051A(\u001b8jiz\"\"!!\u0004")
public interface StdIn {
    public static /* synthetic */ String readLine$(StdIn $this) {
        return $this.readLine();
    }

    default public String readLine() {
        return Console$.MODULE$.in().readLine();
    }

    public static /* synthetic */ String readLine$(StdIn $this, String text, Seq args2) {
        return $this.readLine(text, args2);
    }

    default public String readLine(String text, Seq<Object> args2) {
        Console$.MODULE$.printf(text, args2);
        Console$.MODULE$.out().flush();
        return this.readLine();
    }

    public static /* synthetic */ boolean readBoolean$(StdIn $this) {
        return $this.readBoolean();
    }

    default public boolean readBoolean() {
        String s2 = this.readLine();
        if (s2 == null) {
            throw new EOFException("Console has reached end of input");
        }
        String string = s2.toLowerCase();
        switch (string == null ? 0 : string.hashCode()) {
            case 116: {
                if (!"t".equals(string)) break;
                return true;
            }
            case 121: {
                if (!"y".equals(string)) break;
                return true;
            }
            case 119527: {
                if (!"yes".equals(string)) break;
                return true;
            }
            case 3569038: {
                if (!"true".equals(string)) break;
                return true;
            }
        }
        return false;
    }

    public static /* synthetic */ byte readByte$(StdIn $this) {
        return $this.readByte();
    }

    default public byte readByte() {
        String s2 = this.readLine();
        if (s2 == null) {
            throw new EOFException("Console has reached end of input");
        }
        return Byte.parseByte(s2);
    }

    public static /* synthetic */ short readShort$(StdIn $this) {
        return $this.readShort();
    }

    default public short readShort() {
        String s2 = this.readLine();
        if (s2 == null) {
            throw new EOFException("Console has reached end of input");
        }
        return Short.parseShort(s2);
    }

    public static /* synthetic */ char readChar$(StdIn $this) {
        return $this.readChar();
    }

    default public char readChar() {
        String s2 = this.readLine();
        if (s2 == null) {
            throw new EOFException("Console has reached end of input");
        }
        return s2.charAt(0);
    }

    public static /* synthetic */ int readInt$(StdIn $this) {
        return $this.readInt();
    }

    default public int readInt() {
        String s2 = this.readLine();
        if (s2 == null) {
            throw new EOFException("Console has reached end of input");
        }
        return Integer.parseInt(s2);
    }

    public static /* synthetic */ long readLong$(StdIn $this) {
        return $this.readLong();
    }

    default public long readLong() {
        String s2 = this.readLine();
        if (s2 == null) {
            throw new EOFException("Console has reached end of input");
        }
        return Long.parseLong(s2);
    }

    public static /* synthetic */ float readFloat$(StdIn $this) {
        return $this.readFloat();
    }

    default public float readFloat() {
        String s2 = this.readLine();
        if (s2 == null) {
            throw new EOFException("Console has reached end of input");
        }
        return Float.parseFloat(s2);
    }

    public static /* synthetic */ double readDouble$(StdIn $this) {
        return $this.readDouble();
    }

    default public double readDouble() {
        String s2 = this.readLine();
        if (s2 == null) {
            throw new EOFException("Console has reached end of input");
        }
        return Double.parseDouble(s2);
    }

    public static /* synthetic */ List readf$(StdIn $this, String format) {
        return $this.readf(format);
    }

    default public List<Object> readf(String format) {
        String s2 = this.readLine();
        if (s2 == null) {
            throw new EOFException("Console has reached end of input");
        }
        return this.textComponents(new MessageFormat(format).parse(s2));
    }

    public static /* synthetic */ Object readf1$(StdIn $this, String format) {
        return $this.readf1(format);
    }

    default public Object readf1(String format) {
        return this.readf(format).head();
    }

    public static /* synthetic */ Tuple2 readf2$(StdIn $this, String format) {
        return $this.readf2(format);
    }

    default public Tuple2<Object, Object> readf2(String format) {
        List<Object> res = this.readf(format);
        return new Tuple2<Object, Object>(res.head(), ((IterableOps)res.tail()).head());
    }

    public static /* synthetic */ Tuple3 readf3$(StdIn $this, String format) {
        return $this.readf3(format);
    }

    default public Tuple3<Object, Object, Object> readf3(String format) {
        List<Object> res = this.readf(format);
        return new Tuple3<Object, Object, Object>(res.head(), ((IterableOps)res.tail()).head(), ((IterableOps)((IterableOps)res.tail()).tail()).head());
    }

    private List<Object> textComponents(Object[] a) {
        List res = Nil$.MODULE$;
        for (int i = a.length - 1; i >= 0; --i) {
            Object object = a[i];
            Boolean bl = object instanceof Boolean ? Boolean.valueOf((Boolean)object) : (object instanceof Byte ? Byte.valueOf((Byte)object) : (object instanceof Short ? Short.valueOf((Short)object) : (object instanceof Character ? Character.valueOf(((Character)object).charValue()) : (object instanceof Integer ? Integer.valueOf((Integer)object) : (object instanceof Long ? Long.valueOf((Long)object) : (object instanceof Float ? Float.valueOf(((Float)object).floatValue()) : (object instanceof Double ? Double.valueOf((Double)object) : object)))))));
            res = res.$colon$colon(bl);
        }
        return res;
    }

    public static void $init$(StdIn $this) {
    }
}

