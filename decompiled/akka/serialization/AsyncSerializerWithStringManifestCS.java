/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.actor.ExtendedActorSystem;
import akka.serialization.AsyncSerializerWithStringManifest;
import java.util.concurrent.CompletionStage;
import scala.compat.java8.FutureConverters$;
import scala.compat.java8.FutureConverters$CompletionStageOps$;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y3Qa\u0002\u0005\u0002\u00025A\u0001B\u0005\u0001\u0003\u0002\u0003\u0006Ia\u0005\u0005\u00063\u0001!\tA\u0007\u0005\u0006;\u00011\tA\b\u0005\u0006o\u00011\t\u0001\u000f\u0005\u0006\u0013\u0002!)A\u0013\u0005\u0006#\u0002!\tA\u0015\u0002$\u0003NLhnY*fe&\fG.\u001b>fe^KG\u000f[*ue&tw-T1oS\u001a,7\u000f^\"T\u0015\tI!\"A\u0007tKJL\u0017\r\\5{CRLwN\u001c\u0006\u0002\u0017\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001\u0001\b\u0011\u0005=\u0001R\"\u0001\u0005\n\u0005EA!!I!ts:\u001c7+\u001a:jC2L'0\u001a:XSRD7\u000b\u001e:j]\u001el\u0015M\\5gKN$\u0018AB:zgR,W\u000e\u0005\u0002\u0015/5\tQC\u0003\u0002\u0017\u0015\u0005)\u0011m\u0019;pe&\u0011\u0001$\u0006\u0002\u0014\u000bb$XM\u001c3fI\u0006\u001bGo\u001c:TsN$X-\\\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005ma\u0002CA\b\u0001\u0011\u0015\u0011\"\u00011\u0001\u0014\u0003=!xNQ5oCJL\u0018i]=oG\u000e\u001bFCA\u00103!\r\u0001s%K\u0007\u0002C)\u0011!eI\u0001\u000bG>t7-\u001e:sK:$(B\u0001\u0013&\u0003\u0011)H/\u001b7\u000b\u0003\u0019\nAA[1wC&\u0011\u0001&\t\u0002\u0010\u0007>l\u0007\u000f\\3uS>t7\u000b^1hKB\u0019!&L\u0018\u000e\u0003-R\u0011\u0001L\u0001\u0006g\u000e\fG.Y\u0005\u0003]-\u0012Q!\u0011:sCf\u0004\"A\u000b\u0019\n\u0005EZ#\u0001\u0002\"zi\u0016DQaM\u0002A\u0002Q\n\u0011a\u001c\t\u0003UUJ!AN\u0016\u0003\r\u0005s\u0017PU3g\u0003E1'o\\7CS:\f'/_!ts:\u001c7i\u0015\u000b\u0004sib\u0004c\u0001\u0011(i!)1\b\u0002a\u0001S\u0005)!-\u001f;fg\")Q\b\u0002a\u0001}\u0005AQ.\u00198jM\u0016\u001cH\u000f\u0005\u0002@\r:\u0011\u0001\t\u0012\t\u0003\u0003.j\u0011A\u0011\u0006\u0003\u00072\ta\u0001\u0010:p_Rt\u0014BA#,\u0003\u0019\u0001&/\u001a3fM&\u0011q\t\u0013\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005\u0015[\u0013!\u0004;p\u0005&t\u0017M]=Bgft7\r\u0006\u0002L!B\u0019AJT\u0015\u000e\u00035S!AI\u0016\n\u0005=k%A\u0002$viV\u0014X\rC\u00034\u000b\u0001\u0007A'A\bge>l')\u001b8bef\f5/\u001f8d)\r\u0019F+\u0016\t\u0004\u0019:#\u0004\"B\u001e\u0007\u0001\u0004I\u0003\"B\u001f\u0007\u0001\u0004q\u0004")
public abstract class AsyncSerializerWithStringManifestCS
extends AsyncSerializerWithStringManifest {
    public abstract CompletionStage<byte[]> toBinaryAsyncCS(Object var1);

    public abstract CompletionStage<Object> fromBinaryAsyncCS(byte[] var1, String var2);

    @Override
    public final Future<byte[]> toBinaryAsync(Object o) {
        return FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps(this.toBinaryAsyncCS(o)));
    }

    @Override
    public Future<Object> fromBinaryAsync(byte[] bytes, String manifest) {
        return FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps(this.fromBinaryAsyncCS(bytes, manifest)));
    }

    public AsyncSerializerWithStringManifestCS(ExtendedActorSystem system) {
        super(system);
    }
}

