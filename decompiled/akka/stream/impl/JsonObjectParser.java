/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.JsonObjectParser$;
import akka.stream.scaladsl.Framing;
import akka.util.ByteString;
import akka.util.ByteString$;
import java.io.Serializable;
import scala.Array$;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.collection.ArrayOps$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u00055tA\u0002\u00180\u0011\u0003\u0019TG\u0002\u00048_!\u00051\u0007\u000f\u0005\u0006\u007f\u0005!\t!\u0011\u0005\b\u0005\u0006\u0011\r\u0011\"\u0002D\u0011\u00199\u0015\u0001)A\u0007\t\"9\u0001*\u0001b\u0001\n\u000b\u0019\u0005BB%\u0002A\u00035A\tC\u0004K\u0003\t\u0007IQA\"\t\r-\u000b\u0001\u0015!\u0004E\u0011\u001da\u0015A1A\u0005\u0006\rCa!T\u0001!\u0002\u001b!\u0005b\u0002(\u0002\u0005\u0004%)a\u0011\u0005\u0007\u001f\u0006\u0001\u000bQ\u0002#\t\u000fA\u000b!\u0019!C\u0003\u0007\"1\u0011+\u0001Q\u0001\u000e\u0011CqAU\u0001C\u0002\u0013\u00151\t\u0003\u0004T\u0003\u0001\u0006i\u0001\u0012\u0005\b)\u0006\u0011\r\u0011\"\u0002V\u0011\u0019A\u0016\u0001)A\u0007-\"9\u0011,\u0001b\u0001\n\u000bQ\u0006BB/\u0002A\u000351\fC\u0004_\u0003\t\u0007IQA0\t\r\t\f\u0001\u0015!\u0004a\u0011\u001d\u0019\u0017A1A\u0005\u0006\u0011DaaZ\u0001!\u0002\u001b)\u0007\"\u00025\u0002\t\u0003I\u0007bB8\u0002#\u0003%\t\u0001\u001d\u0004\u0007o=\u00021'a\u0003\t\u0013\u000551D!A!\u0002\u0013\u0011\bBB \u001c\t\u0003\ty\u0001\u0003\u0005\u0002\u0016m\u0001\u000b\u0015BA\f\u0011\u001d\tib\u0007Q!\nIDq!a\b\u001cA\u0003&!\u000fC\u0004\u0002\"m\u0001\u000b\u0015\u0002:\t\u000f\u0005\r2\u0004)Q\u0005U\"9\u0011QE\u000e!B\u0013Q\u0007bBA\u00147\u0001\u0006KA\u001b\u0005\b\u0003SYB\u0011AA\u0016\u0011\u001d\t\u0019e\u0007C\u0001\u0003\u000bBq!a\u0012\u001c\t\u0003\t)\u0005C\u0004\u0002Jm!\t!a\u0013\t\u000f\u0005M3\u0004\"\u0003\u0002V!9\u0011qK\u000e\u0005\n\u0005e\u0003bBA07\u0011%\u0011\u0011\r\u0005\n\u0003KZ\"\u0019!C\u0005\u0003OB\u0001\"!\u001b\u001cA\u0003%\u0011qC\u0001\u0011\u0015N|gn\u00142kK\u000e$\b+\u0019:tKJT!\u0001M\u0019\u0002\t%l\u0007\u000f\u001c\u0006\u0003eM\naa\u001d;sK\u0006l'\"\u0001\u001b\u0002\t\u0005\\7.\u0019\t\u0003m\u0005i\u0011a\f\u0002\u0011\u0015N|gn\u00142kK\u000e$\b+\u0019:tKJ\u001c\"!A\u001d\u0011\u0005ijT\"A\u001e\u000b\u0003q\nQa]2bY\u0006L!AP\u001e\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}\r\u0001A#A\u001b\u0002!M\u000bX/\u0019:f\u0005J\f7-Z*uCJ$X#\u0001#\u0011\u0005i*\u0015B\u0001$<\u0005\u0011\u0011\u0015\u0010^3\u0002#M\u000bX/\u0019:f\u0005J\f7-Z*uCJ$\b%\u0001\bTcV\f'/\u001a\"sC\u000e,WI\u001c3\u0002\u001fM\u000bX/\u0019:f\u0005J\f7-Z#oI\u0002\nqbQ;sYf\u0014%/Y2f'R\f'\u000f^\u0001\u0011\u0007V\u0014H.\u001f\"sC\u000e,7\u000b^1si\u0002\nQbQ;sYf\u0014%/Y2f\u000b:$\u0017AD\"ve2L(I]1dK\u0016sG\rI\u0001\f\t>,(\r\\3Rk>$X-\u0001\u0007E_V\u0014G.Z)v_R,\u0007%A\u0005CC\u000e\\7\u000f\\1tQ\u0006Q!)Y2lg2\f7\u000f\u001b\u0011\u0002\u000b\r{W.\\1\u0002\r\r{W.\\1!\u0003%a\u0015N\\3Ce\u0016\f7.F\u0001W\u001f\u00059V$\u0001\u0006\u0002\u00151Kg.\u001a\"sK\u0006\\\u0007%\u0001\u0006MS:,'I]3bWJ*\u0012aW\b\u00029v\tQ\"A\u0006MS:,'I]3bWJ\u0002\u0013a\u0001+bEV\t\u0001mD\u0001b;\u0005I\u0011\u0001\u0002+bE\u0002\nQa\u00159bG\u0016,\u0012!Z\b\u0002Mv\t\u0001%\u0001\u0004Ta\u0006\u001cW\rI\u0001\rSN<\u0006.\u001b;fgB\f7-\u001a\u000b\u0003U6\u0004\"AO6\n\u00051\\$a\u0002\"p_2,\u0017M\u001c\u0005\u0006]f\u0001\r\u0001R\u0001\u0002E\u0006YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIE*\u0012!\u001d\u0016\u0003eV\u0004\"AO:\n\u0005Q\\$aA%oi.\na\u000f\u0005\u0002xy6\t\u0001P\u0003\u0002zu\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003wn\n!\"\u00198o_R\fG/[8o\u0013\ti\bPA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016D#!A@\u0011\t\u0005\u0005\u0011QA\u0007\u0003\u0003\u0007Q!a_\u001a\n\t\u0005\u001d\u00111\u0001\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e\u000b\u0002\u0001\u007fN\u00111$O\u0001\u0014[\u0006D\u0018.\\;n\u001f\nTWm\u0019;MK:<G\u000f\u001b\u000b\u0005\u0003#\t\u0019\u0002\u0005\u000277!A\u0011QB\u000f\u0011\u0002\u0003\u0007!/\u0001\u0004ck\u001a4WM\u001d\t\u0005u\u0005eA)C\u0002\u0002\u001cm\u0012Q!\u0011:sCf\f1\u0001]8t\u0003\u0015\u0019H/\u0019:u\u0003\u0015!W\r\u001d;i\u0003=\u0019w.\u001c9mKR,Gm\u00142kK\u000e$\u0018AE5o'R\u0014\u0018N\\4FqB\u0014Xm]:j_:\f\u0011#\u001b8CC\u000e\\7\u000f\\1tQ\u0016\u001b8-\u00199f\u0003\u0015ygMZ3s)\u0011\ti#a\r\u0011\u0007i\ny#C\u0002\u00022m\u0012A!\u00168ji\"9\u0011QG\u0013A\u0002\u0005]\u0012!B5oaV$\b\u0003BA\u001d\u0003\u007fi!!a\u000f\u000b\u0007\u0005u2'\u0001\u0003vi&d\u0017\u0002BA!\u0003w\u0011!BQ=uKN#(/\u001b8h\u0003\u001dI7/R7qif,\u0012A[\u0001\fG\u0006t7i\\7qY\u0016$X-\u0001\u0003q_2dGCAA'!\u0015Q\u0014qJA\u001c\u0013\r\t\tf\u000f\u0002\u0007\u001fB$\u0018n\u001c8\u0002\u0015M,Wm[(cU\u0016\u001cG\u000fF\u0001k\u0003A\u00198.\u001b9U_:+\u0007\u0010^(cU\u0016\u001cG\u000f\u0006\u0003\u0002.\u0005m\u0003BBA/U\u0001\u0007!/A\u0004ck\u001a\u001c\u0016N_3\u0002\u000fA\u0014xnY3fIR!\u0011QFA2\u0011\u0019\t)d\u000ba\u0001\t\u0006Qq.\u001e;fe\u000eC\u0017M]:\u0016\u0005\u0005]\u0011aC8vi\u0016\u00148\t[1sg\u0002B#aG@")
@InternalApi
public class JsonObjectParser {
    private final int maximumObjectLength;
    private byte[] buffer;
    private int pos;
    private int start;
    private int depth;
    private boolean completedObject;
    private boolean inStringExpression;
    private boolean inBackslashEscape;
    private final byte[] outerChars;

    public static int $lessinit$greater$default$1() {
        return JsonObjectParser$.MODULE$.$lessinit$greater$default$1();
    }

    public static boolean isWhitespace(byte b) {
        return JsonObjectParser$.MODULE$.isWhitespace(b);
    }

    public static int Space() {
        return JsonObjectParser$.MODULE$.Space();
    }

    public static int Tab() {
        return JsonObjectParser$.MODULE$.Tab();
    }

    public static int LineBreak2() {
        return JsonObjectParser$.MODULE$.LineBreak2();
    }

    public static int LineBreak() {
        return JsonObjectParser$.MODULE$.LineBreak();
    }

    public static byte Comma() {
        return JsonObjectParser$.MODULE$.Comma();
    }

    public static byte Backslash() {
        return JsonObjectParser$.MODULE$.Backslash();
    }

    public static byte DoubleQuote() {
        return JsonObjectParser$.MODULE$.DoubleQuote();
    }

    public static byte CurlyBraceEnd() {
        return JsonObjectParser$.MODULE$.CurlyBraceEnd();
    }

    public static byte CurlyBraceStart() {
        return JsonObjectParser$.MODULE$.CurlyBraceStart();
    }

    public static byte SquareBraceEnd() {
        return JsonObjectParser$.MODULE$.SquareBraceEnd();
    }

    public static byte SquareBraceStart() {
        return JsonObjectParser$.MODULE$.SquareBraceStart();
    }

    public void offer(ByteString input) {
        byte[] oldBuffer = this.buffer;
        this.buffer = new byte[oldBuffer.length - this.start + input.size()];
        System.arraycopy(oldBuffer, this.start, this.buffer, 0, oldBuffer.length - this.start);
        input.copyToArray(this.buffer, oldBuffer.length - this.start);
        this.pos -= this.start;
        this.start = 0;
    }

    public boolean isEmpty() {
        return ArrayOps$.MODULE$.isEmpty$extension(Predef$.MODULE$.byteArrayOps(this.buffer));
    }

    public boolean canComplete() {
        return this.depth == 0;
    }

    public Option<ByteString> poll() {
        Option option;
        try {
            boolean foundObject = this.seekObject();
            if (!foundObject) {
                option = None$.MODULE$;
            } else {
                int n = this.pos;
                switch (n) {
                    case -1: 
                    case 0: {
                        option = None$.MODULE$;
                        break;
                    }
                    default: {
                        if (this.start == this.pos) {
                            option = None$.MODULE$;
                            break;
                        }
                        ByteString res = ByteString$.MODULE$.fromArrayUnsafe(this.buffer, this.start, this.pos - this.start);
                        this.start = this.pos;
                        option = new Some<ByteString>(res);
                        break;
                    }
                }
            }
        }
        catch (ArithmeticException arithmeticException) {
            throw new Framing.FramingException(new StringBuilder(45).append("Invalid JSON encountered at position [").append(this.pos).append("] of [").append(ByteString$.MODULE$.apply(this.buffer).utf8String()).append("]").toString());
        }
        return option;
    }

    private boolean seekObject() {
        this.completedObject = false;
        int bufSize = this.buffer.length;
        this.skipToNextObject(bufSize);
        while (this.pos < bufSize && this.pos - this.start < this.maximumObjectLength && !this.completedObject) {
            byte input = this.buffer[this.pos];
            this.proceed(input);
            ++this.pos;
        }
        if (this.pos - this.start >= this.maximumObjectLength) {
            throw new Framing.FramingException(new StringBuilder(51).append("JSON element exceeded maximumObjectLength (").append(this.maximumObjectLength).append(" bytes)!").toString());
        }
        return this.completedObject;
    }

    private void skipToNextObject(int bufSize) {
        while (this.pos != -1 && this.pos < bufSize && this.pos - this.start < this.maximumObjectLength && this.depth == 0) {
            byte outer = this.outerChars()[this.buffer[this.pos] & 0xFF];
            this.start += outer & 1;
            this.depth = (outer & 2) >> 1;
            int cfr_ignored_0 = 1 / outer;
            ++this.pos;
        }
    }

    private void proceed(byte input) {
        if (this.inStringExpression) {
            byte by = input;
            if (JsonObjectParser$.MODULE$.DoubleQuote() == by) {
                this.inStringExpression = this.inBackslashEscape;
                this.inBackslashEscape = false;
                return;
            }
            if (JsonObjectParser$.MODULE$.Backslash() == by) {
                this.inBackslashEscape = !this.inBackslashEscape;
                return;
            }
            this.inBackslashEscape = false;
            return;
        }
        byte by = input;
        if (JsonObjectParser$.MODULE$.DoubleQuote() == by) {
            this.inStringExpression = true;
            return;
        }
        if (JsonObjectParser$.MODULE$.CurlyBraceStart() == by) {
            ++this.depth;
            return;
        }
        if (JsonObjectParser$.MODULE$.CurlyBraceEnd() == by) {
            --this.depth;
            this.completedObject = this.depth == 0;
            return;
        }
    }

    private byte[] outerChars() {
        return this.outerChars;
    }

    public static final /* synthetic */ byte $anonfun$outerChars$1(int i) {
        byte by = (byte)i;
        if (JsonObjectParser$.MODULE$.CurlyBraceStart() == by) {
            return 2;
        }
        if (JsonObjectParser$.MODULE$.SquareBraceStart() == by ? true : (JsonObjectParser$.MODULE$.SquareBraceEnd() == by ? true : JsonObjectParser$.MODULE$.Comma() == by)) {
            return 1;
        }
        if (JsonObjectParser$.MODULE$.isWhitespace(by)) {
            return 1;
        }
        return 0;
    }

    public JsonObjectParser(int maximumObjectLength) {
        this.maximumObjectLength = maximumObjectLength;
        this.buffer = (byte[])Array$.MODULE$.empty(ClassTag$.MODULE$.Byte());
        this.pos = 0;
        this.start = 0;
        this.depth = 0;
        this.completedObject = false;
        this.inStringExpression = false;
        this.inBackslashEscape = false;
        this.outerChars = (byte[])Array$.MODULE$.tabulate(256, (Function1<Object, Object> & Serializable)i -> BoxesRunTime.boxToByte(JsonObjectParser.$anonfun$outerChars$1(BoxesRunTime.unboxToInt(i))), ClassTag$.MODULE$.Byte());
    }
}

