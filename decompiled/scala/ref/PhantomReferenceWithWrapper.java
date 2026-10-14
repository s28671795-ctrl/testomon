/*
 * Decompiled with CFR 0.152.
 */
package scala.ref;

import scala.ref.PhantomReference;
import scala.ref.ReferenceQueue;
import scala.ref.ReferenceWithWrapper;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005]2AAB\u0004\u0005\u0019!Aq\u0005\u0001B\u0001B\u0003%q\u0003\u0003\u0005)\u0001\t\u0005\t\u0015!\u0003*\u0011!a\u0003A!b\u0001\n\u0003i\u0003\u0002\u0003\u0019\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0018\t\u000bE\u0002A\u0011\u0001\u001a\u00037AC\u0017M\u001c;p[J+g-\u001a:f]\u000e,w+\u001b;i/J\f\u0007\u000f]3s\u0015\tA\u0011\"A\u0002sK\u001aT\u0011AC\u0001\u0006g\u000e\fG.Y\u0002\u0001+\ti\u0011dE\u0002\u0001\u001d\r\u00022aD\u000b\u0018\u001b\u0005\u0001\"B\u0001\u0005\u0012\u0015\t\u00112#\u0001\u0003mC:<'\"\u0001\u000b\u0002\t)\fg/Y\u0005\u0003-A\u0011\u0001\u0003\u00155b]R|WNU3gKJ,gnY3\u0011\u0005aIB\u0002\u0001\u0003\u00065\u0001\u0011\ra\u0007\u0002\u0002)F\u0011A\u0004\t\t\u0003;yi\u0011!C\u0005\u0003?%\u0011qAT8uQ&tw\r\u0005\u0002\u001eC%\u0011!%\u0003\u0002\u0007\u0003:L(+\u001a4\u0011\u0007\u0011*s#D\u0001\b\u0013\t1sA\u0001\u000bSK\u001a,'/\u001a8dK^KG\u000f[,sCB\u0004XM]\u0001\u0006m\u0006dW/Z\u0001\u0006cV,W/\u001a\t\u0004I):\u0012BA\u0016\b\u00059\u0011VMZ3sK:\u001cW-U;fk\u0016\fqa\u001e:baB,'/F\u0001/!\r!sfF\u0005\u0003-\u001d\t\u0001b\u001e:baB,'\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\tM\"TG\u000e\t\u0004I\u00019\u0002\"B\u0014\u0006\u0001\u00049\u0002\"\u0002\u0015\u0006\u0001\u0004I\u0003\"\u0002\u0017\u0006\u0001\u0004q\u0003")
public class PhantomReferenceWithWrapper<T>
extends java.lang.ref.PhantomReference<T>
implements ReferenceWithWrapper<T> {
    private final PhantomReference<T> wrapper;

    @Override
    public PhantomReference<T> wrapper() {
        return this.wrapper;
    }

    public PhantomReferenceWithWrapper(T value, ReferenceQueue<T> queue, PhantomReference<T> wrapper) {
        this.wrapper = wrapper;
        super(value, queue.underlying());
    }
}

