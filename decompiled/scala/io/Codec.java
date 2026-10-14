/*
 * Decompiled with CFR 0.152.
 */
package scala.io;

import java.io.Serializable;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import scala.Function0;
import scala.Function1;
import scala.io.Codec$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@ScalaSignature(bytes="\u0006\u0005\u0005Eh\u0001B\u0015+\u0001=B\u0001\u0002\u000e\u0001\u0003\u0006\u0004%\t!\u000e\u0005\t\u0001\u0002\u0011\t\u0011)A\u0005m!)\u0011\t\u0001C\u0001\u0005\u0016!a\t\u0001\u0001H\u000b\u0011a\u0006\u0001A/\t\r\u0011\u0004\u0001\u0015)\u0003f\u0011\u0019A\u0007\u0001)Q\u0005K\"1\u0011\u000e\u0001Q!\n)Da\u0001\u001d\u0001!B\u0013\t\bB\u0002?\u0001A\u0003&Q\u0010\u0003\u0004\u0000\u0001\u0011\u0005\u0013\u0011\u0001\u0005\b\u0003\u001b\u0001A\u0011AA\b\u0011\u001d\t)\u0002\u0001C\u0001\u0003/Aq!a\u0007\u0001\t\u0003\ti\u0002C\u0004\u0002$\u0001!\t!!\n\t\u000f\u0005%\u0002\u0001\"\u0001\u0002,!9\u0011\u0011\u0007\u0001\u0005\u0002\u0005M\u0002bBA\u001b\u0001\u0011\u0005\u0011q\u0007\u0005\b\u0003\u007f\u0001A\u0011AA!\u0011\u001d\tI\u0005\u0001C\u0001\u0003\u0017:q!a\u0016+\u0011\u0003\tIF\u0002\u0004*U!\u0005\u00111\f\u0005\u0007\u0003Z!\t!a\u0019\t\u0013\u0005\u0015dC1A\u0005\u0006\u0005\u001d\u0004bBA5-\u0001\u0006ia\u0011\u0005\n\u0003W2\"\u0019!C\u0003\u0003OBq!!\u001c\u0017A\u000351\tC\u0004\u0002pY!\t!a\u001a\t\u000f\u0005Ed\u0003\"\u0001\u0002h!9\u00111\u000f\f\u0005\u0002\u0005\u001d\u0004bBA;-\u0011\u0005\u0011q\u000f\u0005\b\u0003k2B\u0011AA?\u0011\u001d\t)H\u0006C\u0001\u0003\u0003Cq!!\"\u0017\t\u0003\t9\tC\u0004\u0002\u0006Z!\t!a,\t\u000f\u0005mf\u0003\"\u0001\u0002>\"9\u00111\u0018\f\u0005\u0002\u0005=\u0007bBAm-\u0011\r\u00111\u001c\u0005\b\u0003C4B1AAr\u0011\u001d\tIO\u0006C\u0002\u0003W\u0014QaQ8eK\u000eT!a\u000b\u0017\u0002\u0005%|'\"A\u0017\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001M\u0011\u0001\u0001\r\t\u0003cIj\u0011\u0001L\u0005\u0003g1\u0012a!\u00118z%\u00164\u0017aB2iCJ\u001cV\r^\u000b\u0002mA\u0011qGP\u0007\u0002q)\u0011\u0011HO\u0001\bG\"\f'o]3u\u0015\tYD(A\u0002oS>T\u0011!P\u0001\u0005U\u00064\u0018-\u0003\u0002@q\t91\t[1sg\u0016$\u0018\u0001C2iCJ\u001cV\r\u001e\u0011\u0002\rqJg.\u001b;?)\t\u0019U\t\u0005\u0002E\u00015\t!\u0006C\u00035\u0007\u0001\u0007aGA\u0005D_:4\u0017nZ;sKV\u0011\u0001\n\u0015\t\u0005c%[\u0015,\u0003\u0002KY\t1A+\u001e9mKJ\u0002B!\r'O\u001d&\u0011Q\n\f\u0002\n\rVt7\r^5p]F\u0002\"a\u0014)\r\u0001\u0011)\u0011\u000b\u0002b\u0001%\n\tA+\u0005\u0002T-B\u0011\u0011\u0007V\u0005\u0003+2\u0012qAT8uQ&tw\r\u0005\u00022/&\u0011\u0001\f\f\u0002\u0004\u0003:L\bCA\u0019[\u0013\tYFFA\u0004C_>dW-\u00198\u0003\u000f!\u000bg\u000e\u001a7feB!\u0011\u0007\u00140b!\t9t,\u0003\u0002aq\tA2\t[1sC\u000e$XM]\"pI&tw-\u0012=dKB$\u0018n\u001c8\u0011\u0005E\u0012\u0017BA2-\u0005\rIe\u000e^\u0001\u0012?>tW*\u00197g_JlW\rZ%oaV$\bCA\u001cg\u0013\t9\u0007HA\tD_\u0012LgnZ#se>\u0014\u0018i\u0019;j_:\facX8o+:l\u0017\r\u001d9bE2,7\t[1sC\u000e$XM]\u0001\u0015?\u0016t7m\u001c3j]\u001e\u0014V\r\u001d7bG\u0016lWM\u001c;\u0011\u0007EZW.\u0003\u0002mY\t)\u0011I\u001d:bsB\u0011\u0011G\\\u0005\u0003_2\u0012AAQ=uK\u0006!r\fZ3d_\u0012Lgn\u001a*fa2\f7-Z7f]R\u0004\"A]=\u000f\u0005M<\bC\u0001;-\u001b\u0005)(B\u0001</\u0003\u0019a$o\\8u}%\u0011\u0001\u0010L\u0001\u0007!J,G-\u001a4\n\u0005i\\(AB*ue&twM\u0003\u0002yY\u0005\u0011rl\u001c8D_\u0012LgnZ#yG\u0016\u0004H/[8o!\tqX!D\u0001\u0001\u0003!!xn\u0015;sS:<GCAA\u0002!\u0011\t)!a\u0003\u000e\u0005\u0005\u001d!bAA\u0005y\u0005!A.\u00198h\u0013\rQ\u0018qA\u0001\u0011_:l\u0015\r\u001c4pe6,G-\u00138qkR$2A`A\t\u0011\u0019\t\u0019\u0002\u0004a\u0001K\u0006Ia.Z<BGRLwN\\\u0001\u0016_:,f.\\1qa\u0006\u0014G.Z\"iCJ\f7\r^3s)\rq\u0018\u0011\u0004\u0005\u0007\u0003'i\u0001\u0019A3\u0002'\u0011,7m\u001c3j]\u001e\u0014V\r\u001d7bG\u0016<\u0016\u000e\u001e5\u0015\u0007y\fy\u0002\u0003\u0004\u0002\"9\u0001\r!]\u0001\u000f]\u0016<(+\u001a9mC\u000e,W.\u001a8u\u0003M)gnY8eS:<'+\u001a9mC\u000e,w+\u001b;i)\rq\u0018q\u0005\u0005\u0007\u0003Cy\u0001\u0019\u00016\u0002#=t7i\u001c3j]\u001e,\u0005pY3qi&|g\u000eF\u0002\u007f\u0003[Aa!a\f\u0011\u0001\u0004i\u0018a\u00025b]\u0012dWM]\u0001\u0005]\u0006lW-\u0006\u0002\u0002\u0004\u00059QM\\2pI\u0016\u0014XCAA\u001d!\r9\u00141H\u0005\u0004\u0003{A$AD\"iCJ\u001cX\r^#oG>$WM]\u0001\bI\u0016\u001cw\u000eZ3s+\t\t\u0019\u0005E\u00028\u0003\u000bJ1!a\u00129\u00059\u0019\u0005.\u0019:tKR$UmY8eKJ\fAa\u001e:baR\u0019\u0011-!\u0014\t\u0011\u0005=C\u0003\"a\u0001\u0003#\nAAY8esB!\u0011'a\u0015b\u0013\r\t)\u0006\f\u0002\ty\tLh.Y7f}\u0005)1i\u001c3fGB\u0011AIF\n\u0005-A\ni\u0006E\u0002E\u0003?J1!!\u0019+\u0005eaun\u001e)sS>\u0014\u0018\u000e^=D_\u0012,7-S7qY&\u001c\u0017\u000e^:\u0015\u0005\u0005e\u0013aB%T\u001fbBT'O\u000b\u0002\u0007\u0006A\u0011jU(9qUJ\u0004%\u0001\u0003V)\u001aC\u0014!B+U\rb\u0002\u0013a\u00053fM\u0006,H\u000e^\"iCJ\u001cX\r^\"pI\u0016\u001c\u0017!\u00054jY\u0016,enY8eS:<7i\u001c3fG\u00069A-\u001a4bk2$\u0018!B1qa2LHcA\"\u0002z!1\u00111P\u0010A\u0002E\f\u0001\"\u001a8d_\u0012Lgn\u001a\u000b\u0004\u0007\u0006}\u0004\"\u0002\u001b!\u0001\u00041DcA\"\u0002\u0004\"9\u0011qH\u0011A\u0002\u0005\r\u0013\u0001\u00034s_6,FK\u0012\u001d\u0015\t\u0005%\u0015\u0011\u0013\t\u0005c-\fY\tE\u00022\u0003\u001bK1!a$-\u0005\u0011\u0019\u0005.\u0019:\t\r\u0005M%\u00051\u0001k\u0003\u0015\u0011\u0017\u0010^3tQ-\u0011\u0013qSAR\u0003K\u000bI+a+\u0011\t\u0005e\u0015qT\u0007\u0003\u00037S1!!(-\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003C\u000bYJA\u0005nS\u001e\u0014\u0018\r^5p]\u00069Q.Z:tC\u001e,\u0017EAAT\u0003]#\u0006.[:![\u0016$\bn\u001c3!o\u0006\u001c\b\u0005\u001d:fm&|Wo\u001d7zA5L7O\\1nK\u0012\u0004\u0003\r^8V)\u001aC\u0004M\f\u0011D_:4XM\u001d;tA\u0019\u0014x.\u001c\u0011BeJ\f\u0017p\u0017\"zi\u0016l\u0006\u0005^8!\u0003J\u0014\u0018-_.DQ\u0006\u0014XLL\u0001\nG\"\fgnZ3e\u0013:\f#!!,\u0002\u000bIr\u0013H\f\u0019\u0015\u0011\u0005%\u0015\u0011WAZ\u0003oCa!a%$\u0001\u0004Q\u0007BBA[G\u0001\u0007\u0011-\u0001\u0004pM\u001a\u001cX\r\u001e\u0005\u0007\u0003s\u001b\u0003\u0019A1\u0002\u00071,g.\u0001\u0004u_V#f\t\u000f\u000b\u0004U\u0006}\u0006bBAaI\u0001\u0007\u00111Y\u0001\u0003GN\u0004B!!\u0002\u0002F&!\u0011qYA\u0004\u00051\u0019\u0005.\u0019:TKF,XM\\2fQ-!\u0013qSAR\u0003\u0017\fI+a+\"\u0005\u00055\u0017\u0001\u0019+iSN\u0004S.\u001a;i_\u0012\u0004s/Y:!aJ,g/[8vg2L\b%\\5t]\u0006lW\r\u001a\u0011aMJ|W.\u0016+Gq\u0001t\u0003eQ8om\u0016\u0014Ho\u001d\u0011ge>l\u0007e\u00195be\u0006\u001cG/\u001a:!g\u0016\fX/\u001a8dK\u0002\"x\u000eI!se\u0006L8LQ=uKvsCc\u00026\u0002R\u0006U\u0017q\u001b\u0005\b\u0003',\u0003\u0019AAE\u0003\u0015\u0019\u0007.\u0019:t\u0011\u0019\t),\na\u0001C\"1\u0011\u0011X\u0013A\u0002\u0005\fAb\u001d;sS:<'gY8eK\u000e$2aQAo\u0011\u0019\tyN\na\u0001c\u0006\t1/A\u0007dQ\u0006\u00148/\u001a;3G>$Wm\u0019\u000b\u0004\u0007\u0006\u0015\bBBAtO\u0001\u0007a'A\u0001d\u00035!WmY8eKJ\u00144m\u001c3fGR\u00191)!<\t\u000f\u0005=\b\u00061\u0001\u0002D\u0005\u00111\r\u001a")
public class Codec {
    private final Charset charSet;
    private CodingErrorAction _onMalformedInput;
    private CodingErrorAction _onUnmappableCharacter;
    private byte[] _encodingReplacement;
    private String _decodingReplacement;
    private Function1<CharacterCodingException, Object> _onCodingException;

    public static Codec decoder2codec(CharsetDecoder cd2) {
        return Codec$.MODULE$.apply(cd2);
    }

    public static Codec charset2codec(Charset c) {
        return new Codec(c);
    }

    public static Codec string2codec(String s2) {
        return Codec$.MODULE$.apply(s2);
    }

    public static byte[] toUTF8(char[] chars, int offset, int len) {
        return Codec$.MODULE$.toUTF8(chars, offset, len);
    }

    public static byte[] toUTF8(CharSequence cs) {
        return Codec$.MODULE$.toUTF8(cs);
    }

    public static char[] fromUTF8(byte[] bytes, int offset, int len) {
        return Codec$.MODULE$.fromUTF8(bytes, offset, len);
    }

    public static char[] fromUTF8(byte[] bytes) {
        return Codec$.MODULE$.fromUTF8(bytes, 0, bytes.length);
    }

    public static Codec apply(CharsetDecoder decoder) {
        return Codec$.MODULE$.apply(decoder);
    }

    public static Codec apply(Charset charSet) {
        return new Codec(charSet);
    }

    public static Codec apply(String encoding) {
        return Codec$.MODULE$.apply(encoding);
    }

    public static Codec default() {
        return Codec$.MODULE$.defaultCharsetCodec();
    }

    public static Codec fileEncodingCodec() {
        return Codec$.MODULE$.fileEncodingCodec();
    }

    public static Codec defaultCharsetCodec() {
        return Codec$.MODULE$.defaultCharsetCodec();
    }

    public static Codec UTF8() {
        return Codec$.MODULE$.UTF8();
    }

    public static Codec ISO8859() {
        return Codec$.MODULE$.ISO8859();
    }

    public static Codec fallbackSystemCodec() {
        return Codec$.MODULE$.fallbackSystemCodec();
    }

    public Charset charSet() {
        return this.charSet;
    }

    public String toString() {
        return this.name();
    }

    public Codec onMalformedInput(CodingErrorAction newAction) {
        this._onMalformedInput = newAction;
        return this;
    }

    public Codec onUnmappableCharacter(CodingErrorAction newAction) {
        this._onUnmappableCharacter = newAction;
        return this;
    }

    public Codec decodingReplaceWith(String newReplacement) {
        this._decodingReplacement = newReplacement;
        return this;
    }

    public Codec encodingReplaceWith(byte[] newReplacement) {
        this._encodingReplacement = newReplacement;
        return this;
    }

    public Codec onCodingException(Function1<CharacterCodingException, Object> handler) {
        this._onCodingException = handler;
        return this;
    }

    public String name() {
        return this.charSet().name();
    }

    public CharsetEncoder encoder() {
        CharsetEncoder enc = this.charSet().newEncoder();
        if (this._onMalformedInput != null) {
            enc.onMalformedInput(this._onMalformedInput);
        }
        if (this._onUnmappableCharacter != null) {
            enc.onUnmappableCharacter(this._onUnmappableCharacter);
        }
        if (this._encodingReplacement != null) {
            enc.replaceWith(this._encodingReplacement);
        }
        return enc;
    }

    public CharsetDecoder decoder() {
        CharsetDecoder dec = this.charSet().newDecoder();
        if (this._onMalformedInput != null) {
            dec.onMalformedInput(this._onMalformedInput);
        }
        if (this._onUnmappableCharacter != null) {
            dec.onUnmappableCharacter(this._onUnmappableCharacter);
        }
        if (this._decodingReplacement != null) {
            dec.replaceWith(this._decodingReplacement);
        }
        return dec;
    }

    public int wrap(Function0<Object> body2) {
        try {
            return body2.apply$mcI$sp();
        }
        catch (CharacterCodingException e) {
            return BoxesRunTime.unboxToInt(this._onCodingException.apply(e));
        }
    }

    public Codec(Charset charSet) {
        this.charSet = charSet;
        this._onMalformedInput = null;
        this._onUnmappableCharacter = null;
        this._encodingReplacement = null;
        this._decodingReplacement = null;
        this._onCodingException = (Function1<CharacterCodingException, Nothing$> & Serializable)e -> {
            throw e;
        };
    }
}

