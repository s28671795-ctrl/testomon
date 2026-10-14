/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io.compression;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.impl.io.compression.DeflateDecompressor$;
import akka.stream.impl.io.compression.DeflateDecompressor$$anon$1$inflating$;
import akka.stream.impl.io.compression.DeflateDecompressorBase;
import java.util.zip.Inflater;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005%d!\u0002\u0004\b\u0001=\t\u0002\u0002\u0003\f\u0001\u0005\u0003\u0005\u000b\u0011\u0002\r\t\u0011y\u0001!\u0011!Q\u0001\n}AQA\t\u0001\u0005\u0002\rBQA\t\u0001\u0005\u0002\u001dBQ!\u000b\u0001\u0005B)\u00121\u0003R3gY\u0006$X\rR3d_6\u0004(/Z:t_JT!\u0001C\u0005\u0002\u0017\r|W\u000e\u001d:fgNLwN\u001c\u0006\u0003\u0015-\t!![8\u000b\u00051i\u0011\u0001B5na2T!AD\b\u0002\rM$(/Z1n\u0015\u0005\u0001\u0012\u0001B1lW\u0006\u001c\"\u0001\u0001\n\u0011\u0005M!R\"A\u0004\n\u0005U9!a\u0006#fM2\fG/\u001a#fG>l\u0007O]3tg>\u0014()Y:f\u0003Ai\u0017\r\u001f\"zi\u0016\u001c\b+\u001a:DQVt7n\u0001\u0001\u0011\u0005eaR\"\u0001\u000e\u000b\u0003m\tQa]2bY\u0006L!!\b\u000e\u0003\u0007%sG/\u0001\u0004o_^\u0014\u0018\r\u001d\t\u00033\u0001J!!\t\u000e\u0003\u000f\t{w\u000e\\3b]\u00061A(\u001b8jiz\"2\u0001J\u0013'!\t\u0019\u0002\u0001C\u0003\u0017\u0007\u0001\u0007\u0001\u0004C\u0003\u001f\u0007\u0001\u0007q\u0004\u0006\u0002%Q!)a\u0003\u0002a\u00011\u0005Y1M]3bi\u0016dunZ5d)\rY\u0013q\n\n\u0003Y92A!L\u0003\u0001W\taAH]3gS:,W.\u001a8u}A\u0011q\u0006M\u0007\u0002\u0001%\u0011\u0011\u0007\u0006\u0002\u0019\t\u0016\u001cw.\u001c9sKN\u001cxN\u001d)beNLgn\u001a'pO&\u001cw!B\u001a-\u0011\u0003$\u0014!C5oM2\fG/\u001b8h!\t)d'D\u0001-\r\u00159\u0004\b#!g\u0005%IgN\u001a7bi&twM\u0002\u0003:\u000b\tQ$!\u0002\u0013b]>t7C\u0001\u001d/\u0011\u0015\u0011\u0003\b\"\u0001=)\u0005i\u0004C\u0001 9\u0019\u0001Aq\u0001\u0011\u001dC\u0002\u0013\u0005\u0013)\u0001\u0005j]\u001ad\u0017\r^3s+\u0005\u0011\u0005CA\"K\u001b\u0005!%BA#G\u0003\rQ\u0018\u000e\u001d\u0006\u0003\u000f\"\u000bA!\u001e;jY*\t\u0011*\u0001\u0003kCZ\f\u0017BA&E\u0005!IeN\u001a7bi\u0016\u0014\bBB'9A\u0003%!)A\u0005j]\u001ad\u0017\r^3sA\u001d)1\u0007\u000fEa\u001fB\u0011\u0001KN\u0007\u0002q!)!\u000b\u000fC!'\u0006a\u0011M\u001a;fe&sg\r\\1uKV\tq\nC\u0003Vq\u0011\u0005c+\u0001\bbMR,'OQ=uKN\u0014V-\u00193\u0015\t]S&\r\u001a\t\u00033aK!!\u0017\u000e\u0003\tUs\u0017\u000e\u001e\u0005\u00067R\u0003\r\u0001X\u0001\u0007EV4g-\u001a:\u0011\u0007eiv,\u0003\u0002_5\t)\u0011I\u001d:bsB\u0011\u0011\u0004Y\u0005\u0003Cj\u0011AAQ=uK\")1\r\u0016a\u00011\u00051qN\u001a4tKRDQ!\u001a+A\u0002a\ta\u0001\\3oORD7\u0003\u0002\u001chU6\u0004\"\u0001\u00155\n\u0005%\u0004$aB%oM2\fG/\u001a\t\u00033-L!\u0001\u001c\u000e\u0003\u000fA\u0013x\u000eZ;diB\u0011aN\u001e\b\u0003_Rt!\u0001]:\u000e\u0003ET!A]\f\u0002\rq\u0012xn\u001c;?\u0013\u0005Y\u0012BA;\u001b\u0003\u001d\u0001\u0018mY6bO\u0016L!a\u001e=\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005UT\u0002\"\u0002\u00127\t\u0003QH#A(\t\u000bq4D\u0011I?\u0002\u0019=tGK];oG\u0006$\u0018n\u001c8\u0015\u0003]C\u0001b \u001c\u0002\u0002\u0013\u0005\u0013\u0011A\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005\r\u0001\u0003BA\u0003\u0003\u0017i!!a\u0002\u000b\u0007\u0005%\u0001*\u0001\u0003mC:<\u0017\u0002BA\u0007\u0003\u000f\u0011aa\u0015;sS:<\u0007\"CA\tm\u0005\u0005I\u0011AA\n\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005A\u0002\"CA\fm\u0005\u0005I\u0011AA\r\u00039\u0001(o\u001c3vGR,E.Z7f]R$B!a\u0007\u0002\"A\u0019\u0011$!\b\n\u0007\u0005}!DA\u0002B]fD\u0011\"a\t\u0002\u0016\u0005\u0005\t\u0019\u0001\r\u0002\u0007a$\u0013\u0007C\u0005\u0002(Y\n\t\u0011\"\u0011\u0002*\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002,A1\u0011QFA\u001a\u00037i!!a\f\u000b\u0007\u0005E\"$\u0001\u0006d_2dWm\u0019;j_:LA!!\u000e\u00020\tA\u0011\n^3sCR|'\u000fC\u0005\u0002:Y\n\t\u0011\"\u0001\u0002<\u0005A1-\u00198FcV\fG\u000eF\u0002 \u0003{A!\"a\t\u00028\u0005\u0005\t\u0019AA\u000e\u0011%\t\tENA\u0001\n\u0003\n\u0019%\u0001\u0005iCND7i\u001c3f)\u0005A\u0002\"CA$m\u0005\u0005I\u0011IA%\u0003!!xn\u0015;sS:<GCAA\u0002\u0011\u0019\u0011F\u0006\"\u0011\u0002NU\tA\u0007C\u0004\u0002R\u0015\u0001\r!a\u0015\u0002\t\u0005$HO\u001d\t\u0005\u0003+\n9&D\u0001\u000e\u0013\r\tI&\u0004\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\bf\u0001\u0001\u0002^A!\u0011qLA3\u001b\t\t\tGC\u0002\u0002d=\t!\"\u00198o_R\fG/[8o\u0013\u0011\t9'!\u0019\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b")
@InternalApi
public class DeflateDecompressor
extends DeflateDecompressorBase {
    public final boolean akka$stream$impl$io$compression$DeflateDecompressor$$nowrap;

    @Override
    public DeflateDecompressorBase.DecompressorParsingLogic createLogic(Attributes attr) {
        return new DeflateDecompressorBase.DecompressorParsingLogic(this){
            private volatile DeflateDecompressor$$anon$1$inflating$ inflating$module;
            private final Inflater inflater;

            public DeflateDecompressor$$anon$1$inflating$ inflating() {
                if (this.inflating$module == null) {
                    this.inflating$lzycompute$1();
                }
                return this.inflating$module;
            }

            public Inflater inflater() {
                return this.inflater;
            }

            public DeflateDecompressor$$anon$1$inflating$ afterInflate() {
                return this.inflating();
            }

            public void afterBytesRead(byte[] buffer, int offset, int length) {
            }

            private final void inflating$lzycompute$1() {
                $anon$1 var1_1 = this;
                synchronized (var1_1) {
                    if (this.inflating$module == null) {
                        this.inflating$module = new DeflateDecompressor$$anon$1$inflating$(this);
                    }
                }
            }
            {
                this.inflater = new Inflater($outer.akka$stream$impl$io$compression$DeflateDecompressor$$nowrap);
                this.startWith(this.inflating());
            }
        };
    }

    public DeflateDecompressor(int maxBytesPerChunk, boolean nowrap) {
        this.akka$stream$impl$io$compression$DeflateDecompressor$$nowrap = nowrap;
        super(maxBytesPerChunk);
    }

    public DeflateDecompressor(int maxBytesPerChunk) {
        this(maxBytesPerChunk, false);
    }
}

