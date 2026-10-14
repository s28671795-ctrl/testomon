/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.DoNotInherit;
import akka.annotation.InternalStableApi;
import akka.stream.Attributes;
import akka.stream.InPort;
import akka.stream.OutPort;
import akka.stream.Shape;
import akka.stream.impl.StreamLayout;
import org.reactivestreams.Publisher;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Ma\u0001\u0003\u0005\n!\u0003\r\n!D\b\t\u000b]\u0001a\u0011A\r\t\u000b\u0015\u0002a\u0011\u0001\u0014\t\u000bQ\u0003a\u0011A+\t\u000bQ\u0003a\u0011\u00014\t\u000b=\u0004a\u0011\u00019\t\u000bq\u0004a\u0011A?\t\u000f\u0005\u001d\u0001A\"\u0001\u0002\n\tY\u0001\u000b[1tK&\u001bH.\u00198e\u0015\tQ1\"\u0001\u0003j[Bd'B\u0001\u0007\u000e\u0003\u0019\u0019HO]3b[*\ta\"\u0001\u0003bW.\fWC\u0001\t-'\t\u0001\u0011\u0003\u0005\u0002\u0013+5\t1CC\u0001\u0015\u0003\u0015\u00198-\u00197b\u0013\t12C\u0001\u0004B]f\u0014VMZ\u0001\u0005]\u0006lWm\u0001\u0001\u0016\u0003i\u0001\"a\u0007\u0012\u000f\u0005q\u0001\u0003CA\u000f\u0014\u001b\u0005q\"BA\u0010\u0019\u0003\u0019a$o\\8u}%\u0011\u0011eE\u0001\u0007!J,G-\u001a4\n\u0005\r\"#AB*ue&twM\u0003\u0002\"'\u0005\tR.\u0019;fe&\fG.\u001b>f\u0003R|W.[2\u0015\u0007\u001d*\u0004\n\u0005\u0003\u0013Q)\u0012\u0014BA\u0015\u0014\u0005\u0019!V\u000f\u001d7feA\u00111\u0006\f\u0007\u0001\t\u0015i\u0003A1\u0001/\u0005\u0005i\u0015CA\u00183!\t\u0011\u0002'\u0003\u00022'\t9aj\u001c;iS:<\u0007C\u0001\n4\u0013\t!4CA\u0002B]fDQA\u000e\u0002A\u0002]\n1!\\8e!\u0011A\u0014\t\u0012\u001a\u000f\u0005ezdB\u0001\u001e?\u001d\tYTH\u0004\u0002\u001ey%\ta\"\u0003\u0002\r\u001b%\u0011!bC\u0005\u0003\u0001&\tAb\u0015;sK\u0006lG*Y=pkRL!AQ\"\u0003\u0019\u0005#x.\\5d\u001b>$W\u000f\\3\u000b\u0005\u0001K\u0001CA#G\u001b\u0005Y\u0011BA$\f\u0005\u0015\u0019\u0006.\u00199f\u0011\u0015I%\u00011\u0001K\u0003)\tG\u000f\u001e:jEV$Xm\u001d\t\u0003\u000b.K!\u0001T\u0006\u0003\u0015\u0005#HO]5ckR,7\u000f\u000b\u0002\u0003\u001dB\u0011qJU\u0007\u0002!*\u0011\u0011+D\u0001\u000bC:tw\u000e^1uS>t\u0017BA*Q\u0005EIe\u000e^3s]\u0006d7\u000b^1cY\u0016\f\u0005/[\u0001\u000bCN\u001c\u0018n\u001a8Q_J$H\u0003\u0002,Z=\u000e\u0004\"AE,\n\u0005a\u001b\"\u0001B+oSRDQAW\u0002A\u0002m\u000b!!\u001b8\u0011\u0005\u0015c\u0016BA/\f\u0005\u0019Ie\u000eU8si\")ql\u0001a\u0001A\u0006!1\u000f\\8u!\t\u0011\u0012-\u0003\u0002c'\t\u0019\u0011J\u001c;\t\u000b\u0011\u001c\u0001\u0019\u0001\u0016\u0002\u000b1|w-[2)\u0005\rqE\u0003\u0002,hY6DQ\u0001\u001b\u0003A\u0002%\f1a\\;u!\t)%.\u0003\u0002l\u0017\t9q*\u001e;Q_J$\b\"B0\u0005\u0001\u0004\u0001\u0007\"\u00023\u0005\u0001\u0004Q\u0003F\u0001\u0003O\u0003=\u0019'/Z1uKB+(\r\\5tQ\u0016\u0014HcA9zuB\u0019!o\u001e\u001a\u000e\u0003MT!\u0001^;\u0002\u001fI,\u0017m\u0019;jm\u0016\u001cHO]3b[NT\u0011A^\u0001\u0004_J<\u0017B\u0001=t\u0005%\u0001VO\u00197jg\",'\u000fC\u0003i\u000b\u0001\u0007\u0011\u000eC\u0003e\u000b\u0001\u0007!\u0006\u000b\u0002\u0006\u001d\u0006iA/Y6f!V\u0014G.[:iKJ$RA\u0016@\u0000\u0003\u0007AQa\u0018\u0004A\u0002\u0001Da!!\u0001\u0007\u0001\u0004\t\u0018!\u00039vE2L7\u000f[3s\u0011\u0015Ie\u00011\u0001KQ\t1a*A\u0007p]&\u001bH.\u00198e%\u0016\fG-\u001f\u000b\u0002-\"\u001a\u0001!!\u0004\u0011\u0007=\u000by!C\u0002\u0002\u0012A\u0013A\u0002R8O_RLe\u000e[3sSR\u0004")
@DoNotInherit
public interface PhaseIsland<M> {
    public String name();

    @InternalStableApi
    public Tuple2<M, Object> materializeAtomic(StreamLayout.AtomicModule<Shape, Object> var1, Attributes var2);

    @InternalStableApi
    public void assignPort(InPort var1, int var2, M var3);

    @InternalStableApi
    public void assignPort(OutPort var1, int var2, M var3);

    @InternalStableApi
    public Publisher<Object> createPublisher(OutPort var1, M var2);

    @InternalStableApi
    public void takePublisher(int var1, Publisher<Object> var2, Attributes var3);

    public void onIslandReady();
}

