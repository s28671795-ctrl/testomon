/*
 * Decompiled with CFR 0.152.
 */
package scala.ref;

import scala.ref.ReferenceQueue;
import scala.ref.ReferenceWithWrapper;
import scala.ref.WeakReference;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005]2AAB\u0004\u0005\u0019!Aq\u0005\u0001B\u0001B\u0003%q\u0003\u0003\u0005)\u0001\t\u0005\t\u0015!\u0003*\u0011!a\u0003A!b\u0001\n\u0003i\u0003\u0002\u0003\u0019\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0018\t\u000bE\u0002A\u0011\u0001\u001a\u00031]+\u0017m\u001b*fM\u0016\u0014XM\\2f/&$\bn\u0016:baB,'O\u0003\u0002\t\u0013\u0005\u0019!/\u001a4\u000b\u0003)\tQa]2bY\u0006\u001c\u0001!\u0006\u0002\u000e3M\u0019\u0001AD\u0012\u0011\u0007=)r#D\u0001\u0011\u0015\tA\u0011C\u0003\u0002\u0013'\u0005!A.\u00198h\u0015\u0005!\u0012\u0001\u00026bm\u0006L!A\u0006\t\u0003\u001b]+\u0017m\u001b*fM\u0016\u0014XM\\2f!\tA\u0012\u0004\u0004\u0001\u0005\u000bi\u0001!\u0019A\u000e\u0003\u0003Q\u000b\"\u0001\b\u0011\u0011\u0005uqR\"A\u0005\n\u0005}I!a\u0002(pi\"Lgn\u001a\t\u0003;\u0005J!AI\u0005\u0003\r\u0005s\u0017PU3g!\r!SeF\u0007\u0002\u000f%\u0011ae\u0002\u0002\u0015%\u00164WM]3oG\u0016<\u0016\u000e\u001e5Xe\u0006\u0004\b/\u001a:\u0002\u000bY\fG.^3\u0002\u000bE,X-^3\u0011\u0007\u0011Rs#\u0003\u0002,\u000f\tq!+\u001a4fe\u0016t7-Z)vKV,\u0017aB<sCB\u0004XM]\u000b\u0002]A\u0019AeL\f\n\u0005Y9\u0011\u0001C<sCB\u0004XM\u001d\u0011\u0002\rqJg.\u001b;?)\u0011\u0019D'\u000e\u001c\u0011\u0007\u0011\u0002q\u0003C\u0003(\u000b\u0001\u0007q\u0003C\u0003)\u000b\u0001\u0007\u0011\u0006C\u0003-\u000b\u0001\u0007a\u0006")
public class WeakReferenceWithWrapper<T>
extends java.lang.ref.WeakReference<T>
implements ReferenceWithWrapper<T> {
    private final WeakReference<T> wrapper;

    @Override
    public WeakReference<T> wrapper() {
        return this.wrapper;
    }

    public WeakReferenceWithWrapper(T value, ReferenceQueue<T> queue, WeakReference<T> wrapper) {
        this.wrapper = wrapper;
        super(value, queue == null ? null : queue.underlying());
    }
}

