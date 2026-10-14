/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Option;
import scala.collection.StringParsers$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005MrA\u0002\f\u0018\u0011\u0003I2D\u0002\u0004\u001e/!\u0005\u0011D\b\u0005\u0006G\u0005!\t!\n\u0005\bM\u0005\u0011\r\u0011\"\u0004(\u0011\u0019Q\u0013\u0001)A\u0007Q!91&\u0001b\u0001\n\u001ba\u0003BB\u0018\u0002A\u00035Q\u0006C\u00041\u0003\t\u0007IQB\u0019\t\rQ\n\u0001\u0015!\u00043\u0011\u001d)\u0014A1A\u0005\u000e1BaAN\u0001!\u0002\u001bi\u0003BB\u001c\u0002A\u00135\u0001\b\u0003\u0004F\u0003\u0001&iA\u0012\u0005\u0007G\u0006\u0001KQ\u00023\t\u000b!\fAQA5\t\u000b5\fAQ\u00018\t\u000bQ\fAQA;\t\u000bm\fAQ\u0001?\t\u000by\fAQA@\t\u000f\u0005-\u0011\u0001\"\u0002\u0002\u000e!9\u00111C\u0001\u0005\u0002\u0005U\u0001bBA\u0012\u0003\u0011\u0005\u0011QE\u0001\u000e'R\u0014\u0018N\\4QCJ\u001cXM]:\u000b\u0005aI\u0012AC2pY2,7\r^5p]*\t!$A\u0003tG\u0006d\u0017\r\u0005\u0002\u001d\u00035\tqCA\u0007TiJLgn\u001a)beN,'o]\n\u0003\u0003}\u0001\"\u0001I\u0011\u000e\u0003eI!AI\r\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}\r\u0001A#A\u000e\u0002'%tGo\u0014<fe\u001adwn\u001e\"pk:$\u0017M]=\u0016\u0003!z\u0011!K\u000f\u0005gP\u001aD'\u0001\u000bj]R|e/\u001a:gY><(i\\;oI\u0006\u0014\u0018\u0010I\u0001\u0011S:$xJ^3sM2|w\u000fR5hSR,\u0012!L\b\u0002]u\t\u0011\"A\tj]R|e/\u001a:gY><H)[4ji\u0002\nA\u0003\\8oO>3XM\u001d4m_^\u0014u.\u001e8eCJLX#\u0001\u001a\u0010\u0003Mr\u0002b}\u001a4gM\u001a4\u0007N\u0001\u0016Y>twm\u0014<fe\u001adwn\u001e\"pk:$\u0017M]=!\u0003EawN\\4Pm\u0016\u0014h\r\\8x\t&<\u0017\u000e^\u0001\u0013Y>twm\u0014<fe\u001adwn\u001e#jO&$\b%\u0001\u0005eK\u000e4\u0016\r\\;f)\tID\b\u0005\u0002!u%\u00111(\u0007\u0002\u0004\u0013:$\b\"B\u001f\f\u0001\u0004q\u0014AA2i!\t\u0001s(\u0003\u0002A3\t!1\t[1sQ\tY!\t\u0005\u0002!\u0007&\u0011A)\u0007\u0002\u0007S:d\u0017N\\3\u0002\u001dM$X\r\u001d+p\u001fZ,'O\u001a7poR1qIS,Z7\u0002\u00042\u0001\t%:\u0013\tI\u0015D\u0001\u0004PaRLwN\u001c\u0005\u0006\u00172\u0001\r\u0001T\u0001\u0005MJ|W\u000e\u0005\u0002N):\u0011aJ\u0015\t\u0003\u001ffi\u0011\u0001\u0015\u0006\u0003#\u0012\na\u0001\u0010:p_Rt\u0014BA*\u001a\u0003\u0019\u0001&/\u001a3fM&\u0011QK\u0016\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005MK\u0002\"\u0002-\r\u0001\u0004I\u0014a\u00017f]\")!\f\u0004a\u0001s\u0005\u0019\u0011mZ4\t\u000bqc\u0001\u0019A/\u0002\u0015%\u001c\bk\\:ji&4X\r\u0005\u0002!=&\u0011q,\u0007\u0002\b\u0005>|G.Z1o\u0011\u0015\tG\u00021\u0001:\u0003\ri\u0017N\u001c\u0015\u0003\u0019\t\u000bq![:ES\u001eLG\u000f\u0006\u0002^K\")a-\u0004a\u0001}\u0005\t1\r\u000b\u0002\u000e\u0005\u0006I\u0001/\u0019:tK\n{w\u000e\u001c\u000b\u0003U.\u00042\u0001\t%^\u0011\u0015Ye\u00021\u0001MQ\tq!)A\u0005qCJ\u001cXMQ=uKR\u0011qn\u001d\t\u0004A!\u0003\bC\u0001\u0011r\u0013\t\u0011\u0018D\u0001\u0003CsR,\u0007\"B&\u0010\u0001\u0004a\u0015A\u00039beN,7\u000b[8siR\u0011aO\u001f\t\u0004A!;\bC\u0001\u0011y\u0013\tI\u0018DA\u0003TQ>\u0014H\u000fC\u0003L!\u0001\u0007A*\u0001\u0005qCJ\u001cX-\u00138u)\t9U\u0010C\u0003L#\u0001\u0007A*A\u0005qCJ\u001cX\rT8oOR!\u0011\u0011AA\u0005!\u0011\u0001\u0003*a\u0001\u0011\u0007\u0001\n)!C\u0002\u0002\be\u0011A\u0001T8oO\")1J\u0005a\u0001\u0019\u0006\u00012\r[3dW\u001acw.\u0019;G_Jl\u0017\r\u001e\u000b\u0004;\u0006=\u0001BBA\t'\u0001\u0007A*\u0001\u0004g_Jl\u0017\r^\u0001\u000ba\u0006\u00148/\u001a$m_\u0006$H\u0003BA\f\u0003?\u0001B\u0001\t%\u0002\u001aA\u0019\u0001%a\u0007\n\u0007\u0005u\u0011DA\u0003GY>\fG\u000fC\u0003L)\u0001\u0007A\n\u000b\u0002\u0015\u0005\u0006Y\u0001/\u0019:tK\u0012{WO\u00197f)\u0011\t9#a\f\u0011\t\u0001B\u0015\u0011\u0006\t\u0004A\u0005-\u0012bAA\u00173\t1Ai\\;cY\u0016DQaS\u000bA\u00021C#!\u0006\"")
public final class StringParsers {
    public static Option<Object> parseDouble(String from) {
        return StringParsers$.MODULE$.parseDouble(from);
    }

    public static Option<Object> parseFloat(String from) {
        return StringParsers$.MODULE$.parseFloat(from);
    }

    public static boolean checkFloatFormat(String format) {
        return StringParsers$.MODULE$.checkFloatFormat(format);
    }

    public static Option<Object> parseLong(String from) {
        return StringParsers$.MODULE$.parseLong(from);
    }

    public static Option<Object> parseInt(String from) {
        return StringParsers$.MODULE$.parseInt(from);
    }

    public static Option<Object> parseShort(String from) {
        return StringParsers$.MODULE$.parseShort(from);
    }

    public static Option<Object> parseByte(String from) {
        return StringParsers$.MODULE$.parseByte(from);
    }

    public static Option<Object> parseBool(String from) {
        return StringParsers$.MODULE$.parseBool(from);
    }
}

