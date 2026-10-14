/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;
import scala.reflect.ScalaSignature;
import scala.runtime.ModuleSerializationProxy$;

@ScalaSignature(bytes="\u0006\u0005M<aa\u0003\u0007\t\u00021\u0001bA\u0002\n\r\u0011\u0003a1\u0003C\u0003!\u0003\u0011\u0005!\u0005C\u0004$\u0003\t\u0007I\u0011\u0002\u0013\t\r!\n\u0001\u0015!\u0003&\u0011\u0015q\u0015\u0001\"\u0003P\u0011\u001dY\u0016!!A\u0005\nq3AA\u0005\u0007\u0003;\"Aal\u0002B\u0001B\u0003%q\fC\u0003!\u000f\u0011\u0005A\rC\u0003l\u000f\u0011%A.\u0001\rN_\u0012,H.Z*fe&\fG.\u001b>bi&|g\u000e\u0015:pqfT!!\u0004\b\u0002\u000fI,h\u000e^5nK*\tq\"A\u0003tG\u0006d\u0017\r\u0005\u0002\u0012\u00035\tAB\u0001\rN_\u0012,H.Z*fe&\fG.\u001b>bi&|g\u000e\u0015:pqf\u001c2!\u0001\u000b\u0019!\t)b#D\u0001\u000f\u0013\t9bB\u0001\u0004B]f\u0014VM\u001a\t\u00033yi\u0011A\u0007\u0006\u00037q\t!![8\u000b\u0003u\tAA[1wC&\u0011qD\u0007\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\t\u0001#A\u0005j]N$\u0018M\\2fgV\tQE\u0005\u0002'S\u0019!q\u0005\u0002\u0001&\u00051a$/\u001a4j]\u0016lWM\u001c;?\u0003)Ign\u001d;b]\u000e,7\u000f\t\t\u0004#)b\u0013BA\u0016\r\u0005A\u0019E.Y:t-\u0006dW/Z\"p[B\fG\u000f\u0005\u0002.a5\taF\u0003\u000209\u0005!A.\u00198h\u0013\t\tdF\u0001\u0004PE*,7\r\u001e\u0005\u0006g\u0019\"\t\u0001N\u0001\nO\u0016$Xj\u001c3vY\u0016$\"\u0001L\u001b\t\u000bY\u0012\u0004\u0019A\u001c\u0002\u0007\rd7\u000f\r\u00029\u000bB\u0019\u0011\bQ\"\u000f\u0005ir\u0004CA\u001e\u000f\u001b\u0005a$BA\u001f\"\u0003\u0019a$o\\8u}%\u0011qHD\u0001\u0007!J,G-\u001a4\n\u0005\u0005\u0013%!B\"mCN\u001c(BA \u000f!\t!U\t\u0004\u0001\u0005\u0013\u0019+\u0014\u0011!A\u0001\u0006\u00039%aA0%cE\u0011\u0001j\u0013\t\u0003+%K!A\u0013\b\u0003\u000f9{G\u000f[5oOB\u0011Q\u0003T\u0005\u0003\u001b:\u00111!\u00118z\u00039\u0011X\r\u001e5s_^\u0014VO\u001c;j[\u0016$\"\u0001\f)\t\u000bE+\u0001\u0019\u0001*\u0002\u0003\u0015\u0004\"a\u0015-\u000f\u0005Q3fBA\u001eV\u0013\u0005y\u0011BA,\u000f\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u0017.\u0003\u0013QC'o\\<bE2,'BA,\u000f\u000319(/\u001b;f%\u0016\u0004H.Y2f)\u0005a3cA\u0004\u00151\u0005YQn\u001c3vY\u0016\u001cE.Y:ta\t\u0001'\rE\u0002:\u0001\u0006\u0004\"\u0001\u00122\u0005\u0013\rD\u0011\u0011!A\u0001\u0006\u00039%aA0%gQ\u0011QM\u001a\t\u0003#\u001dAQAX\u0005A\u0002\u001d\u0004$\u0001\u001b6\u0011\u0007e\u0002\u0015\u000e\u0005\u0002EU\u0012I1MZA\u0001\u0002\u0003\u0015\taR\u0001\fe\u0016\fGMU3t_24X-F\u0001-Q\u00119a.\u001d:\u0011\u0005Uy\u0017B\u00019\u000f\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0002\u0001")
public final class ModuleSerializationProxy
implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Class<?> moduleClass;

    private Object readResolve() {
        return ModuleSerializationProxy$.MODULE$.scala$runtime$ModuleSerializationProxy$$instances().get(this.moduleClass);
    }

    public ModuleSerializationProxy(Class<?> moduleClass) {
        this.moduleClass = moduleClass;
    }
}

