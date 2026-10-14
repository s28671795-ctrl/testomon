/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.actor.NoSerializationVerificationNeeded;
import akka.annotation.DoNotInherit;
import akka.annotation.InternalApi;
import akka.io.dns.CachePolicy;
import akka.io.dns.ResourceRecord$;
import akka.util.ByteIterator;
import akka.util.ByteString;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y4QAD\b\u0002\"YA\u0001b\t\u0001\u0003\u0006\u0004%\t\u0001\n\u0005\ta\u0001\u0011\t\u0011)A\u0005K!A\u0011\u0007\u0001BC\u0002\u0013\u0005!\u0007\u0003\u0005<\u0001\t\u0005\t\u0015!\u00034\u0011!a\u0004A!b\u0001\n\u0003i\u0004\u0002C!\u0001\u0005\u0003\u0005\u000b\u0011\u0002 \t\u0011\t\u0003!Q1A\u0005\u0002uB\u0001b\u0011\u0001\u0003\u0002\u0003\u0006IA\u0010\u0005\u0006\t\u0002!\t!R\u0004\u0007;>A\ta\u00040\u0007\r9y\u0001\u0012A\b`\u0011\u0015!5\u0002\"\u0001a\u0011\u0015\t7\u0002\"\u0001c\u00059\u0011Vm]8ve\u000e,'+Z2pe\u0012T!\u0001E\t\u0002\u0007\u0011t7O\u0003\u0002\u0013'\u0005\u0011\u0011n\u001c\u0006\u0002)\u0005!\u0011m[6b\u0007\u0001\u00192\u0001A\f\u001e!\tA2$D\u0001\u001a\u0015\u0005Q\u0012!B:dC2\f\u0017B\u0001\u000f\u001a\u0005\u0019\te.\u001f*fMB\u0011a$I\u0007\u0002?)\u0011\u0001eE\u0001\u0006C\u000e$xN]\u0005\u0003E}\u0011\u0011ET8TKJL\u0017\r\\5{CRLwN\u001c,fe&4\u0017nY1uS>tg*Z3eK\u0012\fAA\\1nKV\tQ\u0005\u0005\u0002'[9\u0011qe\u000b\t\u0003Qei\u0011!\u000b\u0006\u0003UU\ta\u0001\u0010:p_Rt\u0014B\u0001\u0017\u001a\u0003\u0019\u0001&/\u001a3fM&\u0011af\f\u0002\u0007'R\u0014\u0018N\\4\u000b\u00051J\u0012!\u00028b[\u0016\u0004\u0013a\u0001;uYV\t1\u0007\u0005\u00025q9\u0011QGN\u0007\u0002\u001f%\u0011qgD\u0001\f\u0007\u0006\u001c\u0007.\u001a)pY&\u001c\u00170\u0003\u0002:u\t\u0019A\u000b\u001e7\u000b\u0005]z\u0011\u0001\u0002;uY\u0002\nqA]3d)f\u0004X-F\u0001?!\tAr(\u0003\u0002A3\t)1\u000b[8si\u0006A!/Z2UsB,\u0007%\u0001\u0005sK\u000e\u001cE.Y:t\u0003%\u0011XmY\"mCN\u001c\b%\u0001\u0004=S:LGO\u0010\u000b\u0006\r\u001eC\u0015J\u0013\t\u0003k\u0001AQaI\u0005A\u0002\u0015BQ!M\u0005A\u0002MBQ\u0001P\u0005A\u0002yBQAQ\u0005A\u0002yJc\u0001\u0001'O!J#\u0016BA'\u0010\u0005)\t\u0015)Q!SK\u000e|'\u000fZ\u0005\u0003\u001f>\u0011q!\u0011*fG>\u0014H-\u0003\u0002R\u001f\tY1IT1nKJ+7m\u001c:e\u0013\t\u0019vBA\u0005T%Z\u0013VmY8sI&\u0011Qk\u0004\u0002\u000e+:\\gn\\<o%\u0016\u001cwN\u001d3)\u0005\u00019\u0006C\u0001-\\\u001b\u0005I&B\u0001.\u0014\u0003)\tgN\\8uCRLwN\\\u0005\u00039f\u0013A\u0002R8O_RLe\u000e[3sSR\faBU3t_V\u00148-\u001a*fG>\u0014H\r\u0005\u00026\u0017M\u00111b\u0006\u000b\u0002=\u0006)\u0001/\u0019:tKR\u0019aiY6\t\u000b\u0011l\u0001\u0019A3\u0002\u0005%$\bC\u00014j\u001b\u00059'B\u00015\u0014\u0003\u0011)H/\u001b7\n\u0005)<'\u0001\u0004\"zi\u0016LE/\u001a:bi>\u0014\b\"\u00027\u000e\u0001\u0004i\u0017aA7tOB\u0011aM\\\u0005\u0003_\u001e\u0014!BQ=uKN#(/\u001b8hQ\ti\u0011\u000f\u0005\u0002Ye&\u00111/\u0017\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e\u000b\u0002\fc\"\u0012!\"\u001d")
@DoNotInherit
public abstract class ResourceRecord
implements NoSerializationVerificationNeeded {
    private final String name;
    private final CachePolicy.Ttl ttl;
    private final short recType;
    private final short recClass;

    @InternalApi
    public static ResourceRecord parse(ByteIterator it, ByteString msg) {
        return ResourceRecord$.MODULE$.parse(it, msg);
    }

    public String name() {
        return this.name;
    }

    public CachePolicy.Ttl ttl() {
        return this.ttl;
    }

    public short recType() {
        return this.recType;
    }

    public short recClass() {
        return this.recClass;
    }

    public ResourceRecord(String name, CachePolicy.Ttl ttl, short recType, short recClass) {
        this.name = name;
        this.ttl = ttl;
        this.recType = recType;
        this.recClass = recClass;
    }
}

