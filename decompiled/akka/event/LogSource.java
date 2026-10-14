/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.LogSource$;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dfaB\f\u0019!\u0003\r\t!\b\u0005\u0006K\u0001!\tA\n\u0005\u0006U\u00011\ta\u000b\u0005\u0006U\u0001!\t\u0001\u0012\u0005\u0006+\u0002!\tAV\u0004\u0006SbA\tA\u001b\u0004\u0006/aA\t\u0001\u001c\u0005\u0006[\u001a!\tA\u001c\u0005\b_\u001a\u0011\r\u0011b\u0001q\u0011\u0019\u0011h\u0001)A\u0005c\"91O\u0002b\u0001\n\u0007!\bBB=\u0007A\u0003%Q\u000fC\u0004{\r\t\u0007I1A>\t\u000f\u0005\u0005a\u0001)A\u0005y\"Q\u00111\u0001\u0004C\u0002\u0013\r!$!\u0002\t\u0011\u0005=a\u0001)A\u0005\u0003\u000fA\u0011\"!\b\u0007\u0005\u0004%\t!a\b\t\u0011\u00055b\u0001)A\u0005\u0003CAq!a\f\u0007\t\u0007\t\t\u0004C\u0004\u0002>\u0019!\t!a\u0010\t\u000f\u0005ub\u0001\"\u0001\u0002d!9\u00111\u0011\u0004\u0005\u0002\u0005\u0015\u0005bBAB\r\u0011\u0005\u0011Q\u0013\u0002\n\u0019><7k\\;sG\u0016T!!\u0007\u000e\u0002\u000b\u00154XM\u001c;\u000b\u0003m\tA!Y6lC\u000e\u0001QC\u0001\u0010<'\t\u0001q\u0004\u0005\u0002!G5\t\u0011EC\u0001#\u0003\u0015\u00198-\u00197b\u0013\t!\u0013E\u0001\u0004B]f\u0014VMZ\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003\u001d\u0002\"\u0001\t\u0015\n\u0005%\n#\u0001B+oSR\f\u0011bZ3o'R\u0014\u0018N\\4\u0015\u00051:\u0004CA\u00175\u001d\tq#\u0007\u0005\u00020C5\t\u0001G\u0003\u000229\u00051AH]8pizJ!aM\u0011\u0002\rA\u0013X\rZ3g\u0013\t)dG\u0001\u0004TiJLgn\u001a\u0006\u0003g\u0005BQ\u0001\u000f\u0002A\u0002e\n\u0011\u0001\u001e\t\u0003umb\u0001\u0001\u0002\u0004=\u0001!\u0015\r!\u0010\u0002\u0002)F\u0011a(\u0011\t\u0003A}J!\u0001Q\u0011\u0003\u000f9{G\u000f[5oOB\u0011\u0001EQ\u0005\u0003\u0007\u0006\u00121!\u00118z)\raSI\u0012\u0005\u0006q\r\u0001\r!\u000f\u0005\u0006\u000f\u000e\u0001\r\u0001S\u0001\u0007gf\u001cH/Z7\u0011\u0005%cU\"\u0001&\u000b\u0005-S\u0012!B1di>\u0014\u0018BA'K\u0005-\t5\r^8s'f\u001cH/Z7)\u0005\u0019{\u0005C\u0001)T\u001b\u0005\t&B\u0001*\u001b\u0003\u0011)H/\u001b7\n\u0005Q\u000b&AB;okN,G-\u0001\u0005hKR\u001cE.\u0019>{)\t9f\f\r\u0002Y9B\u0019Q&W.\n\u0005i3$!B\"mCN\u001c\bC\u0001\u001e]\t%iF!!A\u0001\u0002\u000b\u0005QHA\u0002`IMBQ\u0001\u000f\u0003A\u0002eBC\u0001\u00011gOB\u0011\u0011\rZ\u0007\u0002E*\u00111-I\u0001\u000bC:tw\u000e^1uS>t\u0017BA3c\u0005AIW\u000e\u001d7jG&$hj\u001c;G_VtG-A\u0002ng\u001e\f\u0013\u0001[\u0001e\u0007\u0006tgn\u001c;!M&tG\r\t'pON{WO]2fA\u0019|'\u000f\t\u0013|)v\u0004\u0003\u000f\\3bg\u0016\u00043/Z3!'\u000e\fG.\u0019#pG\u00022wN\u001d\u0011M_\u001e\u001cv.\u001e:dK\u00022wN\u001d\u0011i_^\u0004Co\u001c\u0011pER\f\u0017N\u001c\u0011pe\u0002\u001awN\\:ueV\u001cG\u000fI8oK:\n\u0011\u0002T8h'>,(oY3\u0011\u0005-4Q\"\u0001\r\u0014\u0005\u0019y\u0012A\u0002\u001fj]&$h\bF\u0001k\u0003)1'o\\7TiJLgnZ\u000b\u0002cB\u00191\u000e\u0001\u0017\u0002\u0017\u0019\u0014x.\\*ue&tw\rI\u0001\nMJ|W.Q2u_J,\u0012!\u001e\t\u0004W\u00021\bCA%x\u0013\tA(JA\u0003BGR|'/\u0001\u0006ge>l\u0017i\u0019;pe\u0002\nAB\u001a:p[\u0006\u001bGo\u001c:SK\u001a,\u0012\u0001 \t\u0004W\u0002i\bCA%\u007f\u0013\ty(J\u0001\u0005BGR|'OU3g\u000351'o\\7BGR|'OU3gA\u0005AbM]8n\u0003\u000e$xN],ji\"dunZ4fe\u000ec\u0017m]:\u0016\u0005\u0005\u001d\u0001\u0003B6\u0001\u0003\u0013\u00012a[A\u0006\u0013\r\ti\u0001\u0007\u0002\u0012\u0003\u000e$xN],ji\"dunZ\"mCN\u001c\u0018!\u00074s_6\f5\r^8s/&$\b\u000eT8hO\u0016\u00148\t\\1tg\u0002B3aDA\n!\u0011\t)\"!\u0007\u000e\u0005\u0005]!BA2\u001b\u0013\u0011\tY\"a\u0006\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\nMJ|Wn\u00117bgN,\"!!\t\u0011\t-\u0004\u00111\u0005\u0019\u0005\u0003K\tI\u0003\u0005\u0003.3\u0006\u001d\u0002c\u0001\u001e\u0002*\u0011Q\u00111F\t\u0002\u0002\u0003\u0005)\u0011A\u001f\u0003\u0007}#c'\u0001\u0006ge>l7\t\\1tg\u0002\nAB\u001a:p[\u0006s\u0017p\u00117bgN,B!a\r\u0002<U\u0011\u0011Q\u0007\t\u0005W\u0002\t9\u0004\u0005\u0003.3\u0006e\u0002c\u0001\u001e\u0002<\u0011)AH\u0005b\u0001{\u0005)\u0011\r\u001d9msV!\u0011\u0011IA/)\u0011\t\u0019%a\u0018\u0015\t\u0005\u0015\u0013Q\u000b\t\u0007A\u0005\u001dC&a\u0013\n\u0007\u0005%\u0013E\u0001\u0004UkBdWM\r\u0019\u0005\u0003\u001b\n\t\u0006\u0005\u0003.3\u0006=\u0003c\u0001\u001e\u0002R\u0011Q\u00111K\n\u0002\u0002\u0003\u0005)\u0011A\u001f\u0003\t}#\u0013G\r\u0005\n\u0003/\u001a\u0012\u0011!a\u0002\u00033\n!\"\u001a<jI\u0016t7-\u001a\u00132!\u0011Y\u0007!a\u0017\u0011\u0007i\ni\u0006B\u0003='\t\u0007Q\bC\u0004\u0002bM\u0001\r!a\u0017\u0002\u0003=,B!!\u001a\u0002~Q1\u0011qMA@\u0003\u0003#B!!\u001b\u0002vA1\u0001%a\u0012-\u0003W\u0002D!!\u001c\u0002rA!Q&WA8!\rQ\u0014\u0011\u000f\u0003\u000b\u0003g\"\u0012\u0011!A\u0001\u0006\u0003i$\u0001B0%cMB\u0011\"a\u001e\u0015\u0003\u0003\u0005\u001d!!\u001f\u0002\u0015\u00154\u0018\u000eZ3oG\u0016$#\u0007\u0005\u0003l\u0001\u0005m\u0004c\u0001\u001e\u0002~\u0011)A\b\u0006b\u0001{!9\u0011\u0011\r\u000bA\u0002\u0005m\u0004\"B$\u0015\u0001\u0004A\u0015A\u00034s_6\fe.\u001f*fMR!\u0011qQAJ!\u0019\u0001\u0013q\t\u0017\u0002\nB\"\u00111RAH!\u0011i\u0013,!$\u0011\u0007i\ny\t\u0002\u0006\u0002\u0012V\t\t\u0011!A\u0003\u0002u\u0012Aa\u0018\u00132i!1\u0011\u0011M\u000bA\u0002}!b!a&\u0002$\u0006\u0015\u0006C\u0002\u0011\u0002H1\nI\n\r\u0003\u0002\u001c\u0006}\u0005\u0003B\u0017Z\u0003;\u00032AOAP\t)\t\tKFA\u0001\u0002\u0003\u0015\t!\u0010\u0002\u0005?\u0012\nT\u0007\u0003\u0004\u0002bY\u0001\ra\b\u0005\u0006\u000fZ\u0001\r\u0001\u0013")
public interface LogSource<T> {
    public static Tuple2<String, Class<?>> fromAnyRef(Object o, ActorSystem system) {
        return LogSource$.MODULE$.fromAnyRef(o, system);
    }

    public static Tuple2<String, Class<?>> fromAnyRef(Object o) {
        return LogSource$.MODULE$.fromAnyRef(o);
    }

    public static <T> Tuple2<String, Class<?>> apply(T o, ActorSystem system, LogSource<T> evidence$2) {
        return LogSource$.MODULE$.apply(o, system, evidence$2);
    }

    public static <T> Tuple2<String, Class<?>> apply(T o, LogSource<T> evidence$1) {
        return LogSource$.MODULE$.apply(o, evidence$1);
    }

    public static <T> LogSource<Class<T>> fromAnyClass() {
        return LogSource$.MODULE$.fromAnyClass();
    }

    public static LogSource<Class<?>> fromClass() {
        return LogSource$.MODULE$.fromClass();
    }

    public static LogSource<ActorRef> fromActorRef() {
        return LogSource$.MODULE$.fromActorRef();
    }

    public static LogSource<Actor> fromActor() {
        return LogSource$.MODULE$.fromActor();
    }

    public static LogSource<String> fromString() {
        return LogSource$.MODULE$.fromString();
    }

    public String genString(T var1);

    public static /* synthetic */ String genString$(LogSource $this, Object t2, ActorSystem system) {
        return $this.genString(t2, system);
    }

    default public String genString(T t2, ActorSystem system) {
        return this.genString(t2);
    }

    public static /* synthetic */ Class getClazz$(LogSource $this, Object t2) {
        return $this.getClazz(t2);
    }

    default public Class<?> getClazz(T t2) {
        return t2.getClass();
    }

    public static void $init$(LogSource $this) {
    }
}

