/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.streamref;

import akka.actor.ActorRef;
import akka.actor.ExtendedActorSystem;
import akka.annotation.InternalApi;
import akka.stream.SinkRef;
import akka.stream.SourceRef;
import akka.stream.StreamRefResolver;
import akka.stream.impl.streamref.SinkRefImpl;
import akka.stream.impl.streamref.SourceRefImpl;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00154Qa\u0002\u0005\u0003\u001dAA\u0001b\u0007\u0001\u0003\u0002\u0003\u0006I!\b\u0005\u0006G\u0001!\t\u0001\n\u0005\u0006Q\u0001!\t!\u000b\u0005\u0006Q\u0001!\tA\u0012\u0005\u0006\u001d\u0002!\ta\u0014\u0005\u0006-\u0002!\ta\u0016\u0002\u0016'R\u0014X-Y7SK\u001a\u0014Vm]8mm\u0016\u0014\u0018*\u001c9m\u0015\tI!\"A\u0005tiJ,\u0017-\u001c:fM*\u00111\u0002D\u0001\u0005S6\u0004HN\u0003\u0002\u000e\u001d\u000511\u000f\u001e:fC6T\u0011aD\u0001\u0005C.\\\u0017mE\u0002\u0001#]\u0001\"AE\u000b\u000e\u0003MQ\u0011\u0001F\u0001\u0006g\u000e\fG.Y\u0005\u0003-M\u0011a!\u00118z%\u00164\u0007C\u0001\r\u001a\u001b\u0005a\u0011B\u0001\u000e\r\u0005E\u0019FO]3b[J+gMU3t_24XM]\u0001\u0007gf\u001cH/Z7\u0004\u0001A\u0011a$I\u0007\u0002?)\u0011\u0001ED\u0001\u0006C\u000e$xN]\u0005\u0003E}\u00111#\u0012=uK:$W\rZ!di>\u00148+_:uK6\fa\u0001P5oSRtDCA\u0013(!\t1\u0003!D\u0001\t\u0011\u0015Y\"\u00011\u0001\u001e\u0003U!xnU3sS\u0006d\u0017N_1uS>tgi\u001c:nCR,\"AK\u001f\u0015\u0005-2\u0004C\u0001\u00174\u001d\ti\u0013\u0007\u0005\u0002/'5\tqF\u0003\u000219\u00051AH]8pizJ!AM\n\u0002\rA\u0013X\rZ3g\u0013\t!TG\u0001\u0004TiJLgn\u001a\u0006\u0003eMAQaN\u0002A\u0002a\n1A]3g!\rA\u0012hO\u0005\u0003u1\u0011\u0011bU8ve\u000e,'+\u001a4\u0011\u0005qjD\u0002\u0001\u0003\u0006}\r\u0011\ra\u0010\u0002\u0002)F\u0011\u0001i\u0011\t\u0003%\u0005K!AQ\n\u0003\u000f9{G\u000f[5oOB\u0011!\u0003R\u0005\u0003\u000bN\u00111!\u00118z+\t9U\n\u0006\u0002,\u0011\")q\u0007\u0002a\u0001\u0013B\u0019\u0001D\u0013'\n\u0005-c!aB*j].\u0014VM\u001a\t\u0003y5#QA\u0010\u0003C\u0002}\n\u0001C]3t_24XmU8ve\u000e,'+\u001a4\u0016\u0005A\u001bFCA)U!\rA\u0012H\u0015\t\u0003yM#QAP\u0003C\u0002}BQ!V\u0003A\u0002-\n1c]3sS\u0006d\u0017N_3e'>,(oY3SK\u001a\faB]3t_24XmU5oWJ+g-\u0006\u0002Y7R\u0011\u0011\f\u0018\t\u00041)S\u0006C\u0001\u001f\\\t\u0015qdA1\u0001@\u0011\u0015if\u00011\u0001,\u0003E\u0019XM]5bY&TX\rZ*j].\u0014VM\u001a\u0015\u0003\u0001}\u0003\"\u0001Y2\u000e\u0003\u0005T!A\u0019\b\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002eC\nY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
public final class StreamRefResolverImpl
implements StreamRefResolver {
    private final ExtendedActorSystem system;

    @Override
    public <T> String toSerializationFormat(SourceRef<T> ref) {
        SourceRef<T> sourceRef = ref;
        if (sourceRef instanceof SourceRefImpl) {
            SourceRefImpl sourceRefImpl = (SourceRefImpl)sourceRef;
            ActorRef actorRef2 = sourceRefImpl.initialPartnerRef();
            return actorRef2.path().toSerializationFormatWithAddress(this.system.provider().getDefaultAddress());
        }
        throw new IllegalArgumentException(new StringBuilder(27).append("Unexpected SourceRef impl: ").append(sourceRef.getClass()).toString());
    }

    @Override
    public <T> String toSerializationFormat(SinkRef<T> ref) {
        SinkRef<T> sinkRef = ref;
        if (sinkRef instanceof SinkRefImpl) {
            SinkRefImpl sinkRefImpl = (SinkRefImpl)sinkRef;
            ActorRef actorRef2 = sinkRefImpl.initialPartnerRef();
            return actorRef2.path().toSerializationFormatWithAddress(this.system.provider().getDefaultAddress());
        }
        throw new IllegalArgumentException(new StringBuilder(25).append("Unexpected SinkRef impl: ").append(sinkRef.getClass()).toString());
    }

    @Override
    public <T> SourceRef<T> resolveSourceRef(String serializedSourceRef) {
        return new SourceRefImpl(this.system.provider().resolveActorRef(serializedSourceRef));
    }

    @Override
    public <T> SinkRef<T> resolveSinkRef(String serializedSinkRef) {
        return new SinkRefImpl(this.system.provider().resolveActorRef(serializedSinkRef));
    }

    public StreamRefResolverImpl(ExtendedActorSystem system) {
        this.system = system;
    }
}

