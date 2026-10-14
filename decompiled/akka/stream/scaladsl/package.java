/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.Sink;
import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.package$;
import akka.stream.scaladsl.package$SinkToCompletionStage$;
import akka.stream.scaladsl.package$SourceToCompletionStage$;
import java.util.concurrent.CompletionStage;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005w!B\u0010!\u0011\u00039c!B\u0015!\u0011\u0003Q\u0003\"B\u0019\u0002\t\u0003\u0011d\u0001B\u001a\u0002\u0007QB\u0001\"O\u0002\u0003\u0006\u0004%\tA\u000f\u0005\t%\u000e\u0011\t\u0011)A\u0005w!)\u0011g\u0001C\u0001'\")qk\u0001C\u00011\"91mAA\u0001\n\u0003\"\u0007b\u00025\u0004\u0003\u0003%\t%[\u0004\b_\u0006\t\t\u0011#\u0001q\r\u001d\u0019\u0014!!A\t\u0002EDQ!M\u0006\u0005\u0002IDQa]\u0006\u0005\u0006QD\u0011\"!\u0001\f\u0003\u0003%)!a\u0001\t\u0013\u0005M1\"!A\u0005\u0006\u0005U\u0001\u0002C8\u0002\u0003\u0003%\u0019!!\u000b\u0007\r\u0005u\u0012aAA \u0011)\t\u0019%\u0005BC\u0002\u0013\u0005\u0011Q\t\u0005\u000b\u00033\n\"\u0011!Q\u0001\n\u0005\u001d\u0003BB\u0019\u0012\t\u0003\tY\u0006\u0003\u0004X#\u0011\u0005\u0011\u0011\r\u0005\bGF\t\t\u0011\"\u0011e\u0011!A\u0017#!A\u0005B\u0005\u001dt!CA6\u0003\u0005\u0005\t\u0012AA7\r%\ti$AA\u0001\u0012\u0003\ty\u0007\u0003\u000423\u0011\u0005\u0011\u0011\u000f\u0005\u0007gf!)!a\u001d\t\u0013\u0005\u0005\u0011$!A\u0005\u0006\u0005%\u0005\"CA\n3\u0005\u0005IQAAM\u0011%\tY'AA\u0001\n\u0007\ti+A\u0004qC\u000e\\\u0017mZ3\u000b\u0005\u0005\u0012\u0013\u0001C:dC2\fGm\u001d7\u000b\u0005\r\"\u0013AB:ue\u0016\fWNC\u0001&\u0003\u0011\t7n[1\u0004\u0001A\u0011\u0001&A\u0007\u0002A\t9\u0001/Y2lC\u001e,7CA\u0001,!\tas&D\u0001.\u0015\u0005q\u0013!B:dC2\f\u0017B\u0001\u0019.\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012a\n\u0002\u0018'>,(oY3U_\u000e{W\u000e\u001d7fi&|gn\u0015;bO\u0016,2!\u000e!Q'\t\u0019a\u0007\u0005\u0002-o%\u0011\u0001(\f\u0002\u0007\u0003:Lh+\u00197\u0002\u0007M\u00148-F\u0001<!\u0011ACHP%\n\u0005u\u0002#AB*pkJ\u001cW\r\u0005\u0002@\u00012\u0001A!B!\u0004\u0005\u0004\u0011%aA(viF\u00111I\u0012\t\u0003Y\u0011K!!R\u0017\u0003\u000f9{G\u000f[5oOB\u0011AfR\u0005\u0003\u00116\u00121!\u00118z!\rQUjT\u0007\u0002\u0017*\u0011A*L\u0001\u000bG>t7-\u001e:sK:$\u0018B\u0001(L\u0005\u00191U\u000f^;sKB\u0011q\b\u0015\u0003\u0006#\u000e\u0011\rA\u0011\u0002\u0002)\u0006!1O]2!)\t!f\u000b\u0005\u0003V\u0007yzU\"A\u0001\t\u000be2\u0001\u0019A\u001e\u0002#Q|7i\\7qY\u0016$\u0018n\u001c8Ti\u0006<W\rF\u0001Z!\u0011ACH\u0010.\u0011\u0007m\u000bw*D\u0001]\u0015\taUL\u0003\u0002_?\u0006!Q\u000f^5m\u0015\u0005\u0001\u0017\u0001\u00026bm\u0006L!A\u0019/\u0003\u001f\r{W\u000e\u001d7fi&|gn\u0015;bO\u0016\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002KB\u0011AFZ\u0005\u0003O6\u00121!\u00138u\u0003\u0019)\u0017/^1mgR\u0011!.\u001c\t\u0003Y-L!\u0001\\\u0017\u0003\u000f\t{w\u000e\\3b]\"9a.CA\u0001\u0002\u00041\u0015a\u0001=%c\u000592k\\;sG\u0016$vnQ8na2,G/[8o'R\fw-\u001a\t\u0003+.\u0019\"aC\u0016\u0015\u0003A\f1\u0004^8D_6\u0004H.\u001a;j_:\u001cF/Y4fI\u0015DH/\u001a8tS>tWcA;zyR\u0011a/ \u000b\u0002oB!\u0001\u0006\u0010={!\ty\u0014\u0010B\u0003B\u001b\t\u0007!\tE\u0002\\Cn\u0004\"a\u0010?\u0005\u000bEk!\u0019\u0001\"\t\u000byl\u0001\u0019A@\u0002\u000b\u0011\"\b.[:\u0011\tU\u001b\u0001p_\u0001\u0013Q\u0006\u001c\bnQ8eK\u0012*\u0007\u0010^3og&|g.\u0006\u0004\u0002\u0006\u00055\u0011\u0011\u0003\u000b\u0004I\u0006\u001d\u0001B\u0002@\u000f\u0001\u0004\tI\u0001\u0005\u0004V\u0007\u0005-\u0011q\u0002\t\u0004\u007f\u00055A!B!\u000f\u0005\u0004\u0011\u0005cA \u0002\u0012\u0011)\u0011K\u0004b\u0001\u0005\u0006\u0001R-];bYN$S\r\u001f;f]NLwN\\\u000b\u0007\u0003/\t\u0019#a\n\u0015\t\u0005e\u0011Q\u0004\u000b\u0004U\u0006m\u0001b\u00028\u0010\u0003\u0003\u0005\rA\u0012\u0005\u0007}>\u0001\r!a\b\u0011\rU\u001b\u0011\u0011EA\u0013!\ry\u00141\u0005\u0003\u0006\u0003>\u0011\rA\u0011\t\u0004\u007f\u0005\u001dB!B)\u0010\u0005\u0004\u0011UCBA\u0016\u0003c\t)\u0004\u0006\u0003\u0002.\u0005]\u0002CB+\u0004\u0003_\t\u0019\u0004E\u0002@\u0003c!Q!\u0011\tC\u0002\t\u00032aPA\u001b\t\u0015\t\u0006C1\u0001C\u0011\u0019I\u0004\u00031\u0001\u0002:A1\u0001\u0006PA\u0018\u0003w\u0001BAS'\u00024\t)2+\u001b8l)>\u001cu.\u001c9mKRLwN\\*uC\u001e,WCBA!\u0003\u001f\n9f\u0005\u0002\u0012m\u0005!1/\u001b8l+\t\t9\u0005E\u0004)\u0003\u0013\ni%a\u0015\n\u0007\u0005-\u0003E\u0001\u0003TS:\\\u0007cA \u0002P\u00111\u0011\u0011K\tC\u0002\t\u0013!!\u00138\u0011\t)k\u0015Q\u000b\t\u0004\u007f\u0005]C!B)\u0012\u0005\u0004\u0011\u0015!B:j].\u0004C\u0003BA/\u0003?\u0002b!V\t\u0002N\u0005U\u0003bBA\")\u0001\u0007\u0011q\t\u000b\u0003\u0003G\u0002r\u0001KA%\u0003\u001b\n)\u0007\u0005\u0003\\C\u0006UCc\u00016\u0002j!9anFA\u0001\u0002\u00041\u0015!F*j].$vnQ8na2,G/[8o'R\fw-\u001a\t\u0003+f\u0019\"!G\u0016\u0015\u0005\u00055TCBA;\u0003{\n\u0019\t\u0006\u0003\u0002x\u0005\u0015ECAA=!\u001dA\u0013\u0011JA>\u0003\u007f\u00022aPA?\t\u0019\t\tf\u0007b\u0001\u0005B!1,YAA!\ry\u00141\u0011\u0003\u0006#n\u0011\rA\u0011\u0005\u0007}n\u0001\r!a\"\u0011\rU\u000b\u00121PAA+\u0019\tY)a%\u0002\u0018R\u0019A-!$\t\ryd\u0002\u0019AAH!\u0019)\u0016#!%\u0002\u0016B\u0019q(a%\u0005\r\u0005ECD1\u0001C!\ry\u0014q\u0013\u0003\u0006#r\u0011\rAQ\u000b\u0007\u00037\u000b9+a+\u0015\t\u0005u\u0015\u0011\u0015\u000b\u0004U\u0006}\u0005b\u00028\u001e\u0003\u0003\u0005\rA\u0012\u0005\u0007}v\u0001\r!a)\u0011\rU\u000b\u0012QUAU!\ry\u0014q\u0015\u0003\u0007\u0003#j\"\u0019\u0001\"\u0011\u0007}\nY\u000bB\u0003R;\t\u0007!)\u0006\u0004\u00020\u0006U\u0016\u0011\u0018\u000b\u0005\u0003c\u000bY\f\u0005\u0004V#\u0005M\u0016q\u0017\t\u0004\u007f\u0005UFABA)=\t\u0007!\tE\u0002@\u0003s#Q!\u0015\u0010C\u0002\tCq!a\u0011\u001f\u0001\u0004\ti\fE\u0004)\u0003\u0013\n\u0019,a0\u0011\t)k\u0015q\u0017")
public final class package {
    public static Sink SinkToCompletionStage(Sink sink2) {
        return package$.MODULE$.SinkToCompletionStage(sink2);
    }

    public static Source SourceToCompletionStage(Source src) {
        return package$.MODULE$.SourceToCompletionStage(src);
    }

    public static final class SinkToCompletionStage<In, T> {
        private final Sink<In, Future<T>> sink;

        public Sink<In, Future<T>> sink() {
            return this.sink;
        }

        public Sink<In, CompletionStage<T>> toCompletionStage() {
            return package$SinkToCompletionStage$.MODULE$.toCompletionStage$extension(this.sink());
        }

        public int hashCode() {
            return package$SinkToCompletionStage$.MODULE$.hashCode$extension(this.sink());
        }

        public boolean equals(Object x$1) {
            return package$SinkToCompletionStage$.MODULE$.equals$extension(this.sink(), x$1);
        }

        public SinkToCompletionStage(Sink<In, Future<T>> sink2) {
            this.sink = sink2;
        }
    }

    public static final class SourceToCompletionStage<Out, T> {
        private final Source<Out, Future<T>> src;

        public Source<Out, Future<T>> src() {
            return this.src;
        }

        public Source<Out, CompletionStage<T>> toCompletionStage() {
            return package$SourceToCompletionStage$.MODULE$.toCompletionStage$extension(this.src());
        }

        public int hashCode() {
            return package$SourceToCompletionStage$.MODULE$.hashCode$extension(this.src());
        }

        public boolean equals(Object x$1) {
            return package$SourceToCompletionStage$.MODULE$.equals$extension(this.src(), x$1);
        }

        public SourceToCompletionStage(Source<Out, Future<T>> src) {
            this.src = src;
        }
    }
}

