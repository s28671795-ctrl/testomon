/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io.compression;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.impl.io.ByteStringParser;
import akka.stream.impl.io.compression.DeflateDecompressorBase;
import akka.stream.impl.io.compression.GzipDecompressor$;
import akka.stream.impl.io.compression.GzipDecompressor$$anon$1$ReadHeaders$;
import akka.stream.impl.io.compression.GzipDecompressor$$anon$1$ReadTrailer$;
import akka.stream.impl.io.compression.GzipDecompressor$$anon$1$inflating$;
import akka.util.ByteString;
import java.util.zip.CRC32;
import java.util.zip.Inflater;
import java.util.zip.ZipException;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005\tmc!\u0002\u0006\f\u0001M)\u0002\u0002\u0003\u000e\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u000f\t\u000b\t\u0002A\u0011A\u0012\t\u000b\u0019\u0002A\u0011I\u0014\t\u000f\tM\u0002\u0001\"\u0003\u00036\u001dA!\u0011J\u0006\t\u0002M\u0011YEB\u0004\u000b\u0017!\u00051C!\u0014\t\r\t2A\u0011\u0001B(\u0011)\u0011\tF\u0002b\u0001\n\u0003y!1\u000b\u0005\b\u0005+2\u0001\u0015!\u0003k\u0005A9%0\u001b9EK\u000e|W\u000e\u001d:fgN|'O\u0003\u0002\r\u001b\u0005Y1m\\7qe\u0016\u001c8/[8o\u0015\tqq\"\u0001\u0002j_*\u0011\u0001#E\u0001\u0005S6\u0004HN\u0003\u0002\u0013'\u000511\u000f\u001e:fC6T\u0011\u0001F\u0001\u0005C.\\\u0017m\u0005\u0002\u0001-A\u0011q\u0003G\u0007\u0002\u0017%\u0011\u0011d\u0003\u0002\u0018\t\u00164G.\u0019;f\t\u0016\u001cw.\u001c9sKN\u001cxN\u001d\"bg\u0016\f\u0001#\\1y\u0005f$Xm\u001d)fe\u000eCWO\\6\u0004\u0001A\u0011Q\u0004I\u0007\u0002=)\tq$A\u0003tG\u0006d\u0017-\u0003\u0002\"=\t\u0019\u0011J\u001c;\u0002\rqJg.\u001b;?)\t!S\u0005\u0005\u0002\u0018\u0001!)!D\u0001a\u00019\u0005Y1M]3bi\u0016dunZ5d)\rA#q\u0005\n\u0003S-2AAK\u0002\u0001Q\taAH]3gS:,W.\u001a8u}A\u0011A&L\u0007\u0002\u0001%\u0011a\u0006\u0007\u0002\u0019\t\u0016\u001cw.\u001c9sKN\u001cxN\u001d)beNLgn\u001a'pO&\u001cG!\u0002\u0019*\u0005\u0003\t$\u0001B*uKB\f\"AM\u001b\u0011\u0005u\u0019\u0014B\u0001\u001b\u001f\u0005\u001dqu\u000e\u001e5j]\u001e\u0014BANA\u00041\u001a!!f\u000e\u00016\r\u0011A4AA\u001d\u0003\u000b\u0011\ngn\u001c8\u0014\u0005]Z\u0003\"\u0002\u00128\t\u0003YD#\u0001\u001f\u0011\u0005u:D\u0002\u0001\u0005\b\u007f]\u0012\r\u0011\"\u0011A\u0003!IgN\u001a7bi\u0016\u0014X#A!\u0011\u0005\tKU\"A\"\u000b\u0005\u0011+\u0015a\u0001>ja*\u0011aiR\u0001\u0005kRLGNC\u0001I\u0003\u0011Q\u0017M^1\n\u0005)\u001b%\u0001C%oM2\fG/\u001a:\t\r1;\u0004\u0015!\u0003B\u0003%IgN\u001a7bi\u0016\u0014\b\u0005C\u0004Oo\t\u0007I\u0011B(\u0002\u000b\r\u00148m\r\u001a\u0016\u0003A\u0003\"AQ)\n\u0005I\u001b%!B\"S\u0007N\u0012\u0004B\u0002+8A\u0003%\u0001+\u0001\u0004de\u000e\u001c$\u0007\t\u0005\u0006-^\"\teV\u0001\rC\u001a$XM]%oM2\fG/Z\u000b\u00021B\u0019\u0011l\u001a6\u000f\u0005i+gBA.e\u001d\ta6M\u0004\u0002^E:\u0011a,Y\u0007\u0002?*\u0011\u0001mG\u0001\u0007yI|w\u000e\u001e \n\u0003QI!AE\n\n\u0005A\t\u0012B\u0001\b\u0010\u0013\t1W\"\u0001\tCsR,7\u000b\u001e:j]\u001e\u0004\u0016M]:fe&\u0011\u0001.\u001b\u0002\n!\u0006\u00148/Z*uKBT!AZ\u0007\u0011\u0005-lW\"\u00017\u000b\u0005\u0019\u001b\u0012B\u00018m\u0005)\u0011\u0015\u0010^3TiJLgn\u001a\u0005\u0006a^\"\t%]\u0001\u000fC\u001a$XM\u001d\"zi\u0016\u001c(+Z1e)\u0011\u0011X/`@\u0011\u0005u\u0019\u0018B\u0001;\u001f\u0005\u0011)f.\u001b;\t\u000bY|\u0007\u0019A<\u0002\r\t,hMZ3s!\ri\u0002P_\u0005\u0003sz\u0011Q!\u0011:sCf\u0004\"!H>\n\u0005qt\"\u0001\u0002\"zi\u0016DQA`8A\u0002q\taa\u001c4gg\u0016$\bBBA\u0001_\u0002\u0007A$\u0001\u0004mK:<G\u000f\u001b\u0004\ta]\u0002\n1!\u0001\u0002\u0006M)\u00111AA\u00041B\u0019Q$!\u0003\n\u0007\u0005-aD\u0001\u0004B]f\u0014VM\u001a\u0005\t\u0003\u001f\t\u0019\u0001\"\u0001\u0002\u0012\u00051A%\u001b8ji\u0012\"\u0012A\u001d\u0005\t\u0003+\t\u0019\u0001\"\u0011\u0002\u0012\u0005aqN\u001c+sk:\u001c\u0017\r^5p]\u001e9\u0011\u0011D\u001c\tB\u0006m\u0011!C5oM2\fG/\u001b8h!\u0011\ti\"a\b\u000e\u0003]2q!!\t8\u0011\u0003\u000b\u0019CA\u0005j]\u001ad\u0017\r^5oONQ\u0011qDA\u0013\u0003W\ti#a\r\u0011\t\u0005u\u0011qE\u0005\u0004\u0003Si#aB%oM2\fG/\u001a\t\u0005\u0003;\t\u0019\u0001E\u0002\u001e\u0003_I1!!\r\u001f\u0005\u001d\u0001&o\u001c3vGR\u0004B!!\u000e\u0002@9!\u0011qGA\u001e\u001d\rq\u0016\u0011H\u0005\u0002?%\u0019\u0011Q\b\u0010\u0002\u000fA\f7m[1hK&!\u0011\u0011IA\"\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\r\tiD\b\u0005\bE\u0005}A\u0011AA$)\t\tY\u0002\u0003\u0006\u0002L\u0005}\u0011\u0011!C!\u0003\u001b\nQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAA(!\u0011\t\t&a\u0016\u000e\u0005\u0005M#bAA+\u000f\u0006!A.\u00198h\u0013\u0011\tI&a\u0015\u0003\rM#(/\u001b8h\u0011)\ti&a\b\u0002\u0002\u0013\u0005\u0011qL\u0001\raJ|G-^2u\u0003JLG/_\u000b\u00029!Q\u00111MA\u0010\u0003\u0003%\t!!\u001a\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!\u0011qMA7!\ri\u0012\u0011N\u0005\u0004\u0003Wr\"aA!os\"I\u0011qNA1\u0003\u0003\u0005\r\u0001H\u0001\u0004q\u0012\n\u0004BCA:\u0003?\t\t\u0011\"\u0011\u0002v\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002xA1\u0011\u0011PA@\u0003Oj!!a\u001f\u000b\u0007\u0005ud$\u0001\u0006d_2dWm\u0019;j_:LA!!!\u0002|\tA\u0011\n^3sCR|'\u000f\u0003\u0006\u0002\u0006\u0006}\u0011\u0011!C\u0001\u0003\u000f\u000b\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003\u0013\u000by\tE\u0002\u001e\u0003\u0017K1!!$\u001f\u0005\u001d\u0011un\u001c7fC:D!\"a\u001c\u0002\u0004\u0006\u0005\t\u0019AA4\u0011)\t\u0019*a\b\u0002\u0002\u0013\u0005\u0013QS\u0001\tQ\u0006\u001c\bnQ8eKR\tA\u0004\u0003\u0006\u0002\u001a\u0006}\u0011\u0011!C!\u00037\u000b\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003\u001f:q!a(8\u0011\u0013\u000b\t+A\u0006SK\u0006$\u0007*Z1eKJ\u001c\b\u0003BA\u000f\u0003G3q!!*8\u0011\u0003\u000b9KA\u0006SK\u0006$\u0007*Z1eKJ\u001c8CCAR\u0003\u000f\tY#!\f\u00024!9!%a)\u0005\u0002\u0005-FCAAQ\u0011!\ty+a)\u0005B\u0005E\u0016!\u00029beN,G\u0003BAZ\u0003s\u0003B!WA[U&\u0019\u0011qW5\u0003\u0017A\u000b'o]3SKN,H\u000e\u001e\u0005\t\u0003w\u000bi\u000b1\u0001\u0002>\u00061!/Z1eKJ\u0004B!a0\u0002D:\u0019\u0011\u0011Y3\u000e\u00035I1!!2j\u0005)\u0011\u0015\u0010^3SK\u0006$WM\u001d\u0005\u000b\u0003\u0017\n\u0019+!A\u0005B\u00055\u0003BCA/\u0003G\u000b\t\u0011\"\u0001\u0002`!Q\u00111MAR\u0003\u0003%\t!!4\u0015\t\u0005\u001d\u0014q\u001a\u0005\n\u0003_\nY-!AA\u0002qA!\"a\u001d\u0002$\u0006\u0005I\u0011IA;\u0011)\t))a)\u0002\u0002\u0013\u0005\u0011Q\u001b\u000b\u0005\u0003\u0013\u000b9\u000e\u0003\u0006\u0002p\u0005M\u0017\u0011!a\u0001\u0003OB!\"a%\u0002$\u0006\u0005I\u0011IAK\u0011)\tI*a)\u0002\u0002\u0013\u0005\u00131\u0014\u0005\b\u0003?<D\u0011BAq\u0003\u00111\u0017-\u001b7\u0015\u0007I\n\u0019\u000f\u0003\u0005\u0002f\u0006u\u0007\u0019AAt\u0003\ri7o\u001a\t\u0005\u0003S\f\tP\u0004\u0003\u0002l\u00065\bC\u00010\u001f\u0013\r\tyOH\u0001\u0007!J,G-\u001a4\n\t\u0005e\u00131\u001f\u0006\u0004\u0003_traBA|o!%\u0015\u0011`\u0001\f%\u0016\fG\r\u0016:bS2,'\u000f\u0005\u0003\u0002\u001e\u0005mhaBA\u007fo!\u0005\u0015q \u0002\f%\u0016\fG\r\u0016:bS2,'o\u0005\u0006\u0002|\u0006\u001d\u00111FA\u0017\u0003gAqAIA~\t\u0003\u0011\u0019\u0001\u0006\u0002\u0002z\"A\u0011qVA~\t\u0003\u00129\u0001\u0006\u0003\u00024\n%\u0001\u0002CA^\u0005\u000b\u0001\r!!0\t\u0015\u0005-\u00131`A\u0001\n\u0003\ni\u0005\u0003\u0006\u0002^\u0005m\u0018\u0011!C\u0001\u0003?B!\"a\u0019\u0002|\u0006\u0005I\u0011\u0001B\t)\u0011\t9Ga\u0005\t\u0013\u0005=$qBA\u0001\u0002\u0004a\u0002BCA:\u0003w\f\t\u0011\"\u0011\u0002v!Q\u0011QQA~\u0003\u0003%\tA!\u0007\u0015\t\u0005%%1\u0004\u0005\u000b\u0003_\u00129\"!AA\u0002\u0005\u001d\u0004BCAJ\u0003w\f\t\u0011\"\u0011\u0002\u0016\"Q\u0011\u0011TA~\u0003\u0003%\t%a'\b\u000f\u0005e\u0011\u0006#1\u0003$A!!QEA\u0010\u001b\u0005I\u0003b\u0002B\u0015\u0007\u0001\u0007!1F\u0001\u0005CR$(\u000f\u0005\u0003\u0003.\t=R\"A\t\n\u0007\tE\u0012C\u0001\u0006BiR\u0014\u0018NY;uKN\fQa\u0019:dcY\"2\u0001\bB\u001c\u0011\u0019\u0011I\u0004\u0002a\u0001U\u0006!A-\u0019;bQ\r\u0001!Q\b\t\u0005\u0005\u007f\u0011)%\u0004\u0002\u0003B)\u0019!1I\n\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0003H\t\u0005#aC%oi\u0016\u0014h.\u00197Ba&\f\u0001c\u0012>ja\u0012+7m\\7qe\u0016\u001c8o\u001c:\u0011\u0005]11c\u0001\u0004\u0002\bQ\u0011!1J\u0001\u0007\u0011\u0016\fG-\u001a:\u0016\u0003)\fq\u0001S3bI\u0016\u0014\b\u0005K\u0002\u0007\u0005{A3!\u0002B\u001f\u0001")
@InternalApi
public class GzipDecompressor
extends DeflateDecompressorBase {
    @Override
    public DeflateDecompressorBase.DecompressorParsingLogic createLogic(Attributes attr) {
        return new DeflateDecompressorBase.DecompressorParsingLogic(this){
            private volatile GzipDecompressor$$anon$1$inflating$ inflating$module;
            private volatile GzipDecompressor$$anon$1$ReadHeaders$ ReadHeaders$module;
            private volatile GzipDecompressor$$anon$1$ReadTrailer$ ReadTrailer$module;
            private final Inflater inflater;
            private final CRC32 akka$stream$impl$io$compression$GzipDecompressor$$anon$$crc32;
            private final /* synthetic */ GzipDecompressor $outer;

            public GzipDecompressor$$anon$1$inflating$ inflating() {
                if (this.inflating$module == null) {
                    this.inflating$lzycompute$1();
                }
                return this.inflating$module;
            }

            public GzipDecompressor$$anon$1$ReadHeaders$ akka$stream$impl$io$compression$GzipDecompressor$$anon$$ReadHeaders() {
                if (this.ReadHeaders$module == null) {
                    this.ReadHeaders$lzycompute$1();
                }
                return this.ReadHeaders$module;
            }

            private GzipDecompressor$$anon$1$ReadTrailer$ ReadTrailer() {
                if (this.ReadTrailer$module == null) {
                    this.ReadTrailer$lzycompute$1();
                }
                return this.ReadTrailer$module;
            }

            public Inflater inflater() {
                return this.inflater;
            }

            public CRC32 akka$stream$impl$io$compression$GzipDecompressor$$anon$$crc32() {
                return this.akka$stream$impl$io$compression$GzipDecompressor$$anon$$crc32;
            }

            public ByteStringParser.ParseStep<ByteString> afterInflate() {
                return this.ReadTrailer();
            }

            public void afterBytesRead(byte[] buffer, int offset, int length) {
                this.akka$stream$impl$io$compression$GzipDecompressor$$anon$$crc32().update(buffer, offset, length);
            }

            public Nothing$ akka$stream$impl$io$compression$GzipDecompressor$$anon$$fail(String msg) {
                throw new ZipException(msg);
            }

            public /* synthetic */ GzipDecompressor akka$stream$impl$io$compression$GzipDecompressor$$anon$$$outer() {
                return this.$outer;
            }

            private final void inflating$lzycompute$1() {
                $anon$1 var1_1 = this;
                synchronized (var1_1) {
                    if (this.inflating$module == null) {
                        this.inflating$module = new GzipDecompressor$$anon$1$inflating$(this);
                    }
                }
            }

            private final void ReadHeaders$lzycompute$1() {
                $anon$1 var1_1 = this;
                synchronized (var1_1) {
                    if (this.ReadHeaders$module == null) {
                        this.ReadHeaders$module = new GzipDecompressor$$anon$1$ReadHeaders$(this);
                    }
                }
            }

            private final void ReadTrailer$lzycompute$1() {
                $anon$1 var1_1 = this;
                synchronized (var1_1) {
                    if (this.ReadTrailer$module == null) {
                        this.ReadTrailer$module = new GzipDecompressor$$anon$1$ReadTrailer$(this);
                    }
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer);
                this.inflater = new Inflater(true);
                this.akka$stream$impl$io$compression$GzipDecompressor$$anon$$crc32 = new CRC32();
                this.startWith(this.akka$stream$impl$io$compression$GzipDecompressor$$anon$$ReadHeaders());
            }
        };
    }

    public int akka$stream$impl$io$compression$GzipDecompressor$$crc16(ByteString data) {
        CRC32 crc = new CRC32();
        crc.update(data.toArrayUnsafe());
        return (int)crc.getValue() & 0xFFFF;
    }

    public GzipDecompressor(int maxBytesPerChunk) {
        super(maxBytesPerChunk);
    }
}

