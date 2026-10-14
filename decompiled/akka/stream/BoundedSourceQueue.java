/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.annotation.DoNotInherit;
import akka.stream.QueueOfferResult;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u001d3q!\u0002\u0004\u0011\u0002G\u00051\u0002C\u0003\u0014\u0001\u0019\u0005A\u0003C\u0003'\u0001\u0019\u0005q\u0005C\u0003,\u0001\u0019\u0005A\u0006C\u0003<\u0001\u0019\u0005AH\u0001\nC_VtG-\u001a3T_V\u00148-Z)vKV,'BA\u0004\t\u0003\u0019\u0019HO]3b[*\t\u0011\"\u0001\u0003bW.\f7\u0001A\u000b\u0003\u0019u\u0019\"\u0001A\u0007\u0011\u00059\tR\"A\b\u000b\u0003A\tQa]2bY\u0006L!AE\b\u0003\r\u0005s\u0017PU3g\u0003\u0015ygMZ3s)\t)\u0012\u0004\u0005\u0002\u0017/5\ta!\u0003\u0002\u0019\r\t\u0001\u0012+^3vK>3g-\u001a:SKN,H\u000e\u001e\u0005\u00065\u0005\u0001\raG\u0001\u0005K2,W\u000e\u0005\u0002\u001d;1\u0001A!\u0002\u0010\u0001\u0005\u0004y\"!\u0001+\u0012\u0005\u0001\u001a\u0003C\u0001\b\"\u0013\t\u0011sBA\u0004O_RD\u0017N\\4\u0011\u00059!\u0013BA\u0013\u0010\u0005\r\te._\u0001\tG>l\u0007\u000f\\3uKR\t\u0001\u0006\u0005\u0002\u000fS%\u0011!f\u0004\u0002\u0005+:LG/\u0001\u0003gC&dGC\u0001\u0015.\u0011\u0015q3\u00011\u00010\u0003\t)\u0007\u0010\u0005\u00021q9\u0011\u0011G\u000e\b\u0003eUj\u0011a\r\u0006\u0003i)\ta\u0001\u0010:p_Rt\u0014\"\u0001\t\n\u0005]z\u0011a\u00029bG.\fw-Z\u0005\u0003si\u0012\u0011\u0002\u00165s_^\f'\r\\3\u000b\u0005]z\u0011\u0001B:ju\u0016$\u0012!\u0010\t\u0003\u001dyJ!aP\b\u0003\u0007%sG\u000f\u000b\u0002\u0001\u0003B\u0011!)R\u0007\u0002\u0007*\u0011A\tC\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001$D\u00051!uNT8u\u0013:DWM]5u\u0001")
@DoNotInherit
public interface BoundedSourceQueue<T> {
    public QueueOfferResult offer(T var1);

    public void complete();

    public void fail(Throwable var1);

    public int size();
}

