/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import scala.reflect.ScalaSignature;
import scala.runtime.EmptyMethodCache;
import scala.runtime.MethodCache;
import scala.runtime.StructuralCallSite$;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005b\u0001B\b\u0011\u0005UA\u0001B\u0007\u0001\u0003\u0002\u0003\u0006Ia\u0007\u0005\u0006K\u0001!IA\n\u0005\bU\u0001\u0001\r\u0011\"\u0003,\u0011\u001d)\u0004\u00011A\u0005\nYBa\u0001\u0010\u0001!B\u0013a\u0003bB\u001f\u0001\u0005\u0004%\tA\u0010\u0005\u0007%\u0002\u0001\u000b\u0011B \t\u000bi\u0003A\u0011A.\t\u000bq\u0003A\u0011A/\t\u000b-\u0004A\u0011\u00017\b\u000bU\u0004\u0002\u0012\u0001<\u0007\u000b=\u0001\u0002\u0012A<\t\u000b\u0015bA\u0011\u0001=\t\u000bedA\u0011\u0001>\u0003%M#(/^2ukJ\fGnQ1mYNKG/\u001a\u0006\u0003#I\tqA];oi&lWMC\u0001\u0014\u0003\u0015\u00198-\u00197b\u0007\u0001\u0019\"\u0001\u0001\f\u0011\u0005]AR\"\u0001\n\n\u0005e\u0011\"AB!osJ+g-\u0001\u0005dC2dG+\u001f9f!\ta2%D\u0001\u001e\u0015\tqr$\u0001\u0004j]Z|7.\u001a\u0006\u0003A\u0005\nA\u0001\\1oO*\t!%\u0001\u0003kCZ\f\u0017B\u0001\u0013\u001e\u0005)iU\r\u001e5pIRK\b/Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\u001dJ\u0003C\u0001\u0015\u0001\u001b\u0005\u0001\u0002\"\u0002\u000e\u0003\u0001\u0004Y\u0012!B2bG\",W#\u0001\u0017\u0011\u00075\u0002$'D\u0001/\u0015\tys$A\u0002sK\u001aL!!\r\u0018\u0003\u001bM{g\r\u001e*fM\u0016\u0014XM\\2f!\tA3'\u0003\u00025!\tYQ*\u001a;i_\u0012\u001c\u0015m\u00195f\u0003%\u0019\u0017m\u00195f?\u0012*\u0017\u000f\u0006\u00028uA\u0011q\u0003O\u0005\u0003sI\u0011A!\u00168ji\"91\bBA\u0001\u0002\u0004a\u0013a\u0001=%c\u000511-Y2iK\u0002\na\u0002]1sC6,G/\u001a:UsB,7/F\u0001@!\r9\u0002IQ\u0005\u0003\u0003J\u0011Q!\u0011:sCf\u0004$a\u0011)\u0011\u0007\u0011[eJ\u0004\u0002F\u0013B\u0011aIE\u0007\u0002\u000f*\u0011\u0001\nF\u0001\u0007yI|w\u000e\u001e \n\u0005)\u0013\u0012A\u0002)sK\u0012,g-\u0003\u0002M\u001b\n)1\t\\1tg*\u0011!J\u0005\t\u0003\u001fBc\u0001\u0001B\u0005R\u000f\u0005\u0005\t\u0011!B\u0001'\n\u0019q\fJ\u0019\u0002\u001fA\f'/Y7fi\u0016\u0014H+\u001f9fg\u0002\n\"\u0001V,\u0011\u0005])\u0016B\u0001,\u0013\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"a\u0006-\n\u0005e\u0013\"aA!os\u0006\u0019q-\u001a;\u0016\u0003I\nAAZ5oIR\u0011a\f\u001a\t\u0003?\nl\u0011\u0001\u0019\u0006\u0003C~\tqA]3gY\u0016\u001cG/\u0003\u0002dA\n1Q*\u001a;i_\u0012DQ!Z\u0005A\u0002\u0019\f\u0001B]3dK&4XM\u001d\u0019\u0003O&\u00042\u0001R&i!\ty\u0015\u000eB\u0005kI\u0006\u0005\t\u0011!B\u0001'\n\u0019q\f\n\u001a\u0002\u0007\u0005$G\rF\u0002_[NDQ!\u001a\u0006A\u00029\u0004$a\\9\u0011\u0007\u0011[\u0005\u000f\u0005\u0002Pc\u0012I!/\\A\u0001\u0002\u0003\u0015\ta\u0015\u0002\u0004?\u0012\u001a\u0004\"\u0002;\u000b\u0001\u0004q\u0016!A7\u0002%M#(/^2ukJ\fGnQ1mYNKG/\u001a\t\u0003Q1\u0019\"\u0001\u0004\f\u0015\u0003Y\f\u0011BY8piN$(/\u00199\u0015\u0011mt\u0018qBA\r\u0003;\u0001\"\u0001\b?\n\u0005ul\"\u0001C\"bY2\u001c\u0016\u000e^3\t\r}t\u0001\u0019AA\u0001\u0003\u0019awn\\6vaB!\u00111AA\u0005\u001d\ra\u0012QA\u0005\u0004\u0003\u000fi\u0012!D'fi\"|G\rS1oI2,7/\u0003\u0003\u0002\f\u00055!A\u0002'p_.,\bOC\u0002\u0002\buAq!!\u0005\u000f\u0001\u0004\t\u0019\"A\u0006j]Z|7.\u001a3OC6,\u0007c\u0001#\u0002\u0016%\u0019\u0011qC'\u0003\rM#(/\u001b8h\u0011\u0019\tYB\u0004a\u00017\u0005Y\u0011N\u001c<pW\u0016$G+\u001f9f\u0011\u0019\tyB\u0004a\u00017\u0005\u0011\"/\u001a4mK\u000e$\u0018N^3DC2dG+\u001f9f\u0001")
public final class StructuralCallSite {
    private SoftReference<MethodCache> cache = new SoftReference<EmptyMethodCache>(new EmptyMethodCache());
    private final Class<?>[] parameterTypes;

    public static CallSite bootstrap(MethodHandles.Lookup lookup, String invokedName, MethodType invokedType, MethodType reflectiveCallType) {
        return StructuralCallSite$.MODULE$.bootstrap(lookup, invokedName, invokedType, reflectiveCallType);
    }

    private SoftReference<MethodCache> cache() {
        return this.cache;
    }

    private void cache_$eq(SoftReference<MethodCache> x$1) {
        this.cache = x$1;
    }

    public Class<?>[] parameterTypes() {
        return this.parameterTypes;
    }

    public MethodCache get() {
        MethodCache cache = this.cache().get();
        if (cache == null) {
            cache = new EmptyMethodCache();
            this.cache_$eq(new SoftReference<MethodCache>(cache));
        }
        return cache;
    }

    public Method find(Class<?> receiver) {
        return this.get().find(receiver);
    }

    public Method add(Class<?> receiver, Method m4) {
        this.cache_$eq(new SoftReference<MethodCache>(this.get().add(receiver, m4)));
        return m4;
    }

    public StructuralCallSite(MethodType callType) {
        this.parameterTypes = callType.parameterArray();
    }
}

