/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io.compression;

import akka.annotation.InternalApi;
import akka.stream.impl.io.ByteStringParser;
import akka.stream.impl.io.ByteStringParser$;
import akka.util.ByteString;
import akka.util.ByteString$;
import java.util.zip.Inflater;
import scala.None$;
import scala.Some;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005MbAB\n\u0015\u0003\u0003ab\u0004\u0003\u0005*\u0001\t\u0005\t\u0015!\u0003,\u0011\u0015\t\u0004\u0001\"\u00013\r\u00151\u0004!!\u00018\u0011\u0015\t4\u0001\"\u0001=\u0011\u001dq4A1A\u0007\u0002}BQ!S\u0002\u0007\u0002)CQ!X\u0002\u0007\u0002yCQA\\\u0002\u0007\u0002=DaaY\u0002!\u0002\u0013!g!\u0002:\u0004\u0003\u0003\u0019\b\u0002C<\u000b\u0005\u0003\u0005\u000b\u0011\u0002=\t\u000bERA\u0011A>\t\u000buTA\u0011\t@\t\r}TA\u0011IA\u0001\u0011\u001d\t)b\u0001C!\u0003/9\u0001\"a\n\u0015\u0011\u0003a\u0012\u0011\u0006\u0004\b'QA\t\u0001HA\u0016\u0011\u0019\t\u0014\u0003\"\u0001\u0002.\t9B)\u001a4mCR,G)Z2p[B\u0014Xm]:pe\n\u000b7/\u001a\u0006\u0003+Y\t1bY8naJ,7o]5p]*\u0011q\u0003G\u0001\u0003S>T!!\u0007\u000e\u0002\t%l\u0007\u000f\u001c\u0006\u00037q\taa\u001d;sK\u0006l'\"A\u000f\u0002\t\u0005\\7.Y\n\u0003\u0001}\u00012\u0001I\u0011$\u001b\u00051\u0012B\u0001\u0012\u0017\u0005A\u0011\u0015\u0010^3TiJLgn\u001a)beN,'\u000f\u0005\u0002%O5\tQE\u0003\u0002'9\u0005!Q\u000f^5m\u0013\tASE\u0001\u0006CsR,7\u000b\u001e:j]\u001e\f\u0001#\\1y\u0005f$Xm\u001d)fe\u000eCWO\\6\u0004\u0001A\u0011AfL\u0007\u0002[)\ta&A\u0003tG\u0006d\u0017-\u0003\u00021[\t\u0019\u0011J\u001c;\u0002\rqJg.\u001b;?)\t\u0019T\u0007\u0005\u00025\u00015\tA\u0003C\u0003*\u0005\u0001\u00071F\u0001\rEK\u000e|W\u000e\u001d:fgN|'\u000fU1sg&tw\rT8hS\u000e\u001c\"a\u0001\u001d\u0011\u0005eRT\"\u0001\u0001\n\u0005m\n#\u0001\u0004)beNLgn\u001a'pO&\u001cG#A\u001f\u0011\u0005e\u001a\u0011\u0001C5oM2\fG/\u001a:\u0016\u0003\u0001\u0003\"!Q$\u000e\u0003\tS!a\u0011#\u0002\u0007iL\u0007O\u0003\u0002'\u000b*\ta)\u0001\u0003kCZ\f\u0017B\u0001%C\u0005!IeN\u001a7bi\u0016\u0014\u0018\u0001D1gi\u0016\u0014\u0018J\u001c4mCR,W#A&\u0011\u00071S6E\u0004\u0002N1:\u0011aj\u0016\b\u0003\u001fZs!\u0001U+\u000f\u0005E#V\"\u0001*\u000b\u0005MS\u0013A\u0002\u001fs_>$h(C\u0001\u001e\u0013\tYB$\u0003\u0002\u001a5%\u0011q\u0003G\u0005\u00033Z\t\u0001CQ=uKN#(/\u001b8h!\u0006\u00148/\u001a:\n\u0005mc&!\u0003)beN,7\u000b^3q\u0015\tIf#\u0001\bbMR,'OQ=uKN\u0014V-\u00193\u0015\t}\u0013'\u000e\u001c\t\u0003Y\u0001L!!Y\u0017\u0003\tUs\u0017\u000e\u001e\u0005\u0006G\u001e\u0001\r\u0001Z\u0001\u0007EV4g-\u001a:\u0011\u00071*w-\u0003\u0002g[\t)\u0011I\u001d:bsB\u0011A\u0006[\u0005\u0003S6\u0012AAQ=uK\")1n\u0002a\u0001W\u00051qN\u001a4tKRDQ!\\\u0004A\u0002-\na\u0001\\3oORD\u0017!C5oM2\fG/\u001b8h+\u0005\u0001\bCA9\u000b\u001b\u0005\u0019!aB%oM2\fG/Z\n\u0004\u0015Q\\\u0005C\u0001\u0017v\u0013\t1XF\u0001\u0004B]f\u0014VMZ\u0001\u0011]>\u0004vn\u001d;Qe>\u001cWm]:j]\u001e\u0004\"\u0001L=\n\u0005il#a\u0002\"p_2,\u0017M\u001c\u000b\u0003arDQa\u001e\u0007A\u0002a\facY1o/>\u00148nV5uQB\u000b'\u000f^5bY\u0012\u000bG/Y\u000b\u0002q\u0006)\u0001/\u0019:tKR!\u00111AA\u0005!\u0011a\u0015QA\u0012\n\u0007\u0005\u001dALA\u0006QCJ\u001cXMU3tk2$\bbBA\u0006\u001d\u0001\u0007\u0011QB\u0001\u0007e\u0016\fG-\u001a:\u0011\t\u0005=\u0011\u0011\u0003\b\u0003AaK1!a\u0005]\u0005)\u0011\u0015\u0010^3SK\u0006$WM]\u0001\ta>\u001cHo\u0015;paR\tq\fK\u0002\u0001\u00037\u0001B!!\b\u0002$5\u0011\u0011q\u0004\u0006\u0004\u0003Ca\u0012AC1o]>$\u0018\r^5p]&!\u0011QEA\u0010\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0002/\u0011+g\r\\1uK\u0012+7m\\7qe\u0016\u001c8o\u001c:CCN,\u0007C\u0001\u001b\u0012'\t\tB\u000f\u0006\u0002\u0002*!\u001a\u0011#a\u0007)\u0007A\tY\u0002")
@InternalApi
public abstract class DeflateDecompressorBase
extends ByteStringParser<ByteString> {
    public final int akka$stream$impl$io$compression$DeflateDecompressorBase$$maxBytesPerChunk;

    public DeflateDecompressorBase(int maxBytesPerChunk) {
        this.akka$stream$impl$io$compression$DeflateDecompressorBase$$maxBytesPerChunk = maxBytesPerChunk;
    }

    public abstract class DecompressorParsingLogic
    extends ByteStringParser.ParsingLogic {
        public final byte[] akka$stream$impl$io$compression$DeflateDecompressorBase$DecompressorParsingLogic$$buffer;

        public abstract Inflater inflater();

        public abstract ByteStringParser.ParseStep<ByteString> afterInflate();

        public abstract void afterBytesRead(byte[] var1, int var2, int var3);

        public abstract Inflate inflating();

        @Override
        public void postStop() {
            this.inflater().end();
        }

        public /* synthetic */ DeflateDecompressorBase akka$stream$impl$io$compression$DeflateDecompressorBase$DecompressorParsingLogic$$$outer() {
            return (DeflateDecompressorBase)this.$outer;
        }

        public DecompressorParsingLogic(DeflateDecompressorBase $outer) {
            this.akka$stream$impl$io$compression$DeflateDecompressorBase$DecompressorParsingLogic$$buffer = new byte[$outer.akka$stream$impl$io$compression$DeflateDecompressorBase$$maxBytesPerChunk];
        }

        public abstract class Inflate
        implements ByteStringParser.ParseStep<ByteString> {
            private final boolean noPostProcessing;
            public final /* synthetic */ DecompressorParsingLogic $outer;

            @Override
            public void onTruncation() {
                ByteStringParser.ParseStep.onTruncation$(this);
            }

            @Override
            public boolean canWorkWithPartialData() {
                return true;
            }

            @Override
            public ByteStringParser.ParseResult<ByteString> parse(ByteStringParser.ByteReader reader) {
                this.akka$stream$impl$io$compression$DeflateDecompressorBase$DecompressorParsingLogic$Inflate$$$outer().inflater().setInput(reader.remainingData().toArrayUnsafe());
                int read = this.akka$stream$impl$io$compression$DeflateDecompressorBase$DecompressorParsingLogic$Inflate$$$outer().inflater().inflate(this.akka$stream$impl$io$compression$DeflateDecompressorBase$DecompressorParsingLogic$Inflate$$$outer().akka$stream$impl$io$compression$DeflateDecompressorBase$DecompressorParsingLogic$$buffer);
                reader.skip(reader.remainingSize() - this.akka$stream$impl$io$compression$DeflateDecompressorBase$DecompressorParsingLogic$Inflate$$$outer().inflater().getRemaining());
                if (read > 0) {
                    this.akka$stream$impl$io$compression$DeflateDecompressorBase$DecompressorParsingLogic$Inflate$$$outer().afterBytesRead(this.akka$stream$impl$io$compression$DeflateDecompressorBase$DecompressorParsingLogic$Inflate$$$outer().akka$stream$impl$io$compression$DeflateDecompressorBase$DecompressorParsingLogic$$buffer, 0, read);
                    Inflate next = this.akka$stream$impl$io$compression$DeflateDecompressorBase$DecompressorParsingLogic$Inflate$$$outer().inflater().finished() ? this.akka$stream$impl$io$compression$DeflateDecompressorBase$DecompressorParsingLogic$Inflate$$$outer().afterInflate() : this;
                    return new ByteStringParser.ParseResult<ByteString>(new Some<ByteString>(ByteString$.MODULE$.fromArray(this.akka$stream$impl$io$compression$DeflateDecompressorBase$DecompressorParsingLogic$Inflate$$$outer().akka$stream$impl$io$compression$DeflateDecompressorBase$DecompressorParsingLogic$$buffer, 0, read)), next, this.noPostProcessing);
                }
                if (this.akka$stream$impl$io$compression$DeflateDecompressorBase$DecompressorParsingLogic$Inflate$$$outer().inflater().finished()) {
                    return new ByteStringParser.ParseResult<ByteString>(None$.MODULE$, this.akka$stream$impl$io$compression$DeflateDecompressorBase$DecompressorParsingLogic$Inflate$$$outer().afterInflate(), this.noPostProcessing);
                }
                throw ByteStringParser$.MODULE$.NeedMoreData();
            }

            public /* synthetic */ DecompressorParsingLogic akka$stream$impl$io$compression$DeflateDecompressorBase$DecompressorParsingLogic$Inflate$$$outer() {
                return this.$outer;
            }

            public Inflate(DecompressorParsingLogic $outer, boolean noPostProcessing) {
                this.noPostProcessing = noPostProcessing;
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                ByteStringParser.ParseStep.$init$(this);
            }
        }
    }
}

