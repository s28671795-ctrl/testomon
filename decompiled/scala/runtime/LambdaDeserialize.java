/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SerializedLambda;
import java.util.HashMap;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.LambdaDeserialize$;
import scala.runtime.LambdaDeserializer$;

@ScalaSignature(bytes="\u0006\u0005\u0005=b\u0001\u0002\b\u0010\u0005QA\u0001\"\u0007\u0001\u0003\u0002\u0003\u0006IA\u0007\u0005\tQ\u0001\u0011\t\u0011)A\u0005S!)q\u0006\u0001C\u0005a!9Q\u0007\u0001b\u0001\n\u00131\u0004B\u0002%\u0001A\u0003%q\u0007C\u0004J\u0001\t\u0007I\u0011\u0002\u001c\t\r)\u0003\u0001\u0015!\u00038\u0011\u0015Y\u0005\u0001\"\u0001M\u000f\u0015\u0011v\u0002#\u0001T\r\u0015qq\u0002#\u0001U\u0011\u0015y#\u0002\"\u0001V\u0011\u00151&\u0002\"\u0001X\u0011\u001d\t\u0019C\u0003C\u0001\u0003K\u0011\u0011\u0003T1nE\u0012\fG)Z:fe&\fG.\u001b>f\u0015\t\u0001\u0012#A\u0004sk:$\u0018.\\3\u000b\u0003I\tQa]2bY\u0006\u001c\u0001a\u0005\u0002\u0001+A\u0011acF\u0007\u0002#%\u0011\u0001$\u0005\u0002\u0007\u0003:L(+\u001a4\u0002\r1|wn[;q!\tYRE\u0004\u0002\u001dG5\tQD\u0003\u0002\u001f?\u00051\u0011N\u001c<pW\u0016T!\u0001I\u0011\u0002\t1\fgn\u001a\u0006\u0002E\u0005!!.\u0019<b\u0013\t!S$A\u0007NKRDw\u000e\u001a%b]\u0012dWm]\u0005\u0003M\u001d\u0012a\u0001T8pWV\u0004(B\u0001\u0013\u001e\u00035!\u0018M]4fi6+G\u000f[8egB\u0019aC\u000b\u0017\n\u0005-\n\"!B!se\u0006L\bC\u0001\u000f.\u0013\tqSD\u0001\u0007NKRDw\u000e\u001a%b]\u0012dW-\u0001\u0004=S:LGO\u0010\u000b\u0004cM\"\u0004C\u0001\u001a\u0001\u001b\u0005y\u0001\"B\r\u0004\u0001\u0004Q\u0002\"\u0002\u0015\u0004\u0001\u0004I\u0013a\u0004;be\u001e,G/T3uQ>$W*\u00199\u0016\u0003]\u0002B\u0001O\u001e>Y5\t\u0011H\u0003\u0002;C\u0005!Q\u000f^5m\u0013\ta\u0014HA\u0004ICNDW*\u00199\u0011\u0005y*eBA D!\t\u0001\u0015#D\u0001B\u0015\t\u00115#\u0001\u0004=e>|GOP\u0005\u0003\tF\ta\u0001\u0015:fI\u00164\u0017B\u0001$H\u0005\u0019\u0019FO]5oO*\u0011A)E\u0001\u0011i\u0006\u0014x-\u001a;NKRDw\u000eZ'ba\u0002\nQaY1dQ\u0016\faaY1dQ\u0016\u0004\u0013!\u00053fg\u0016\u0014\u0018.\u00197ju\u0016d\u0015-\u001c2eCR\u0011Q#\u0014\u0005\u0006\u001d\"\u0001\raT\u0001\u000bg\u0016\u0014\u0018.\u00197ju\u0016$\u0007C\u0001\u000fQ\u0013\t\tVD\u0001\tTKJL\u0017\r\\5{K\u0012d\u0015-\u001c2eC\u0006\tB*Y7cI\u0006$Um]3sS\u0006d\u0017N_3\u0011\u0005IR1C\u0001\u0006\u0016)\u0005\u0019\u0016!\u00032p_R\u001cHO]1q)\u0015A6\f\u00180d!\ta\u0012,\u0003\u0002[;\tA1)\u00197m'&$X\rC\u0003\u001a\u0019\u0001\u0007!\u0004C\u0003^\u0019\u0001\u0007Q(A\u0006j]Z|7.\u001a3OC6,\u0007\"B0\r\u0001\u0004\u0001\u0017aC5om>\\W\r\u001a+za\u0016\u0004\"\u0001H1\n\u0005\tl\"AC'fi\"|G\rV=qK\")\u0001\u0006\u0004a\u0001IB\u0019a#\u001a\u0017\n\u0005\u0019\f\"A\u0003\u001fsKB,\u0017\r^3e}!\u001aA\u0002\u001b;\u0011\u0007YI7.\u0003\u0002k#\t1A\u000f\u001b:poN\u0004\"\u0001\\9\u000f\u00055|gB\u0001!o\u0013\u0005\u0011\u0012B\u00019\u0012\u0003\u001d\u0001\u0018mY6bO\u0016L!A]:\u0003\u0013QC'o\\<bE2,'B\u00019\u0012c\u0015qR(^A\nc\u0019\u0019cO_A\u0005wV\u0011q\u000f_\u000b\u0002{\u0011)\u0011p\u0005b\u0001}\n\tA+\u0003\u0002|y\u0006YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIER!!`\t\u0002\rQD'o\\<t#\ry\u0018Q\u0001\t\u0004-\u0005\u0005\u0011bAA\u0002#\t9aj\u001c;iS:<\u0007cAA\u0004c:\u0011ac\\\u0019\tG\u0005-\u0011QBA\b{:\u0019a#!\u0004\n\u0005u\f\u0012'\u0002\u0012\u0017#\u0005E!!B:dC2\f\u0017G\u0001\u0014lQ\ra\u0011q\u0003\t\u0005\u00033\ty\"\u0004\u0002\u0002\u001c)\u0019\u0011QD\t\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002\"\u0005m!a\u0002<be\u0006\u0014xm]\u0001\u0015]\u0006lW-\u00118e\t\u0016\u001c8M]5qi>\u00148*Z=\u0015\u000bu\n9#a\u000b\t\r\u0005%R\u00021\u0001>\u0003\u0011q\u0017-\\3\t\r\u00055R\u00021\u0001>\u0003)!Wm]2sSB$xN\u001d")
public final class LambdaDeserialize {
    private final MethodHandles.Lookup lookup;
    private final HashMap<String, MethodHandle> targetMethodMap;
    private final HashMap<String, MethodHandle> cache;

    public static CallSite bootstrap(MethodHandles.Lookup lookup, String invokedName, MethodType invokedType, MethodHandle ... targetMethods) {
        return LambdaDeserialize$.MODULE$.bootstrap(lookup, invokedName, invokedType, targetMethods);
    }

    public static String nameAndDescriptorKey(String name, String descriptor) {
        return LambdaDeserialize$.MODULE$.nameAndDescriptorKey(name, descriptor);
    }

    public static CallSite bootstrap(MethodHandles.Lookup lookup, String invokedName, MethodType invokedType, Seq<MethodHandle> targetMethods) throws Throwable {
        return LambdaDeserialize$.MODULE$.bootstrap(lookup, invokedName, invokedType, targetMethods);
    }

    private HashMap<String, MethodHandle> targetMethodMap() {
        return this.targetMethodMap;
    }

    private HashMap<String, MethodHandle> cache() {
        return this.cache;
    }

    public Object deserializeLambda(SerializedLambda serialized) {
        return LambdaDeserializer$.MODULE$.deserializeLambda(this.lookup, this.cache(), this.targetMethodMap(), serialized);
    }

    public static final /* synthetic */ MethodHandle $anonfun$new$1(LambdaDeserialize $this, MethodHandle targetMethod) {
        MethodHandleInfo info = $this.lookup.revealDirect(targetMethod);
        String key = LambdaDeserialize$.MODULE$.nameAndDescriptorKey(info.getName(), info.getMethodType().toMethodDescriptorString());
        return $this.targetMethodMap().put(key, targetMethod);
    }

    public LambdaDeserialize(MethodHandles.Lookup lookup, MethodHandle[] targetMethods) {
        this.lookup = lookup;
        this.targetMethodMap = new HashMap(targetMethods.length);
        for (MethodHandle methodHandle : targetMethods) {
            LambdaDeserialize.$anonfun$new$1(this, methodHandle);
        }
        this.cache = new HashMap();
    }
}

