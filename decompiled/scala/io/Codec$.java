/*
 * Decompiled with CFR 0.152.
 */
package scala.io;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import scala.io.Codec;
import scala.io.LowPriorityCodecImplicits;
import scala.util.Properties$;
import scala.util.PropertiesTrait;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class Codec$
implements LowPriorityCodecImplicits {
    public static final Codec$ MODULE$ = new Codec$();
    private static final Codec ISO8859;
    private static final Codec UTF8;
    private static Codec fallbackSystemCodec;
    private static volatile boolean bitmap$0;

    static {
        Charset apply_charSet = StandardCharsets.ISO_8859_1;
        Object var0 = null;
        ISO8859 = new Codec(apply_charSet);
        Charset apply_charSet2 = StandardCharsets.UTF_8;
        UTF8 = new Codec(apply_charSet2);
    }

    private Codec fallbackSystemCodec$lzycompute() {
        synchronized (this) {
            if (!bitmap$0) {
                fallbackSystemCodec = this.defaultCharsetCodec();
                bitmap$0 = true;
            }
        }
        return fallbackSystemCodec;
    }

    @Override
    public Codec fallbackSystemCodec() {
        if (!bitmap$0) {
            return this.fallbackSystemCodec$lzycompute();
        }
        return fallbackSystemCodec;
    }

    public final Codec ISO8859() {
        return ISO8859;
    }

    public final Codec UTF8() {
        return UTF8;
    }

    public Codec defaultCharsetCodec() {
        Charset apply_charSet = Charset.defaultCharset();
        return new Codec(apply_charSet);
    }

    public Codec fileEncodingCodec() {
        return this.apply(PropertiesTrait.encodingString$(Properties$.MODULE$));
    }

    public Codec default() {
        return this.defaultCharsetCodec();
    }

    public Codec apply(String encoding) {
        return new Codec(Charset.forName(encoding));
    }

    public Codec apply(Charset charSet) {
        return new Codec(charSet);
    }

    public Codec apply(CharsetDecoder decoder) {
        return new Codec(decoder, decoder){
            private final CharsetDecoder _decoder$1;

            public CharsetDecoder decoder() {
                return this._decoder$1;
            }
            {
                this._decoder$1 = _decoder$1;
                super(decoder$1.charset());
            }
        };
    }

    public char[] fromUTF8(byte[] bytes) {
        return this.fromUTF8(bytes, 0, bytes.length);
    }

    public char[] fromUTF8(byte[] bytes, int offset, int len) {
        ByteBuffer bbuffer = ByteBuffer.wrap(bytes, offset, len);
        CharBuffer cbuffer = this.UTF8().charSet().decode(bbuffer);
        char[] chars = new char[cbuffer.remaining()];
        cbuffer.get(chars);
        return chars;
    }

    public byte[] toUTF8(CharSequence cs) {
        CharBuffer cbuffer = CharBuffer.wrap(cs, 0, cs.length());
        ByteBuffer bbuffer = this.UTF8().charSet().encode(cbuffer);
        byte[] bytes = new byte[bbuffer.remaining()];
        bbuffer.get(bytes);
        return bytes;
    }

    public byte[] toUTF8(char[] chars, int offset, int len) {
        CharBuffer cbuffer = CharBuffer.wrap(chars, offset, len);
        ByteBuffer bbuffer = this.UTF8().charSet().encode(cbuffer);
        byte[] bytes = new byte[bbuffer.remaining()];
        bbuffer.get(bytes);
        return bytes;
    }

    public Codec string2codec(String s2) {
        return this.apply(s2);
    }

    public Codec charset2codec(Charset c) {
        return new Codec(c);
    }

    public Codec decoder2codec(CharsetDecoder cd2) {
        return this.apply(cd2);
    }

    private Codec$() {
    }
}

