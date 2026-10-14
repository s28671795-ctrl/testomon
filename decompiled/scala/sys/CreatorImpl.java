/*
 * Decompiled with CFR 0.152.
 */
package scala.sys;

import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.sys.Prop;
import scala.sys.PropImpl;

@ScalaSignature(bytes="\u0006\u0005u2a\u0001B\u0003\u0002\u0002\u0015I\u0001\u0002C\u0012\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0013\t\u000bI\u0002A\u0011A\u001a\t\u000bY\u0002A\u0011A\u001c\u0003\u0017\r\u0013X-\u0019;pe&k\u0007\u000f\u001c\u0006\u0003\r\u001d\t1a]=t\u0015\u0005A\u0011!B:dC2\fWC\u0001\u0006\u001a'\r\u00011b\u0004\t\u0003\u00195i\u0011aB\u0005\u0003\u001d\u001d\u0011a!\u00118z%\u00164\u0007c\u0001\t\u0015/9\u0011\u0011CE\u0007\u0002\u000b%\u00111#B\u0001\u0005!J|\u0007/\u0003\u0002\u0016-\t91I]3bi>\u0014(BA\n\u0006!\tA\u0012\u0004\u0004\u0001\u0005\ri\u0001AQ1\u0001\u001d\u0005\u0005!6\u0001A\t\u0003;\u0001\u0002\"\u0001\u0004\u0010\n\u0005}9!a\u0002(pi\"Lgn\u001a\t\u0003\u0019\u0005J!AI\u0004\u0003\u0007\u0005s\u00170A\u0001g!\u0011aQeJ\f\n\u0005\u0019:!!\u0003$v]\u000e$\u0018n\u001c82!\tAsF\u0004\u0002*[A\u0011!fB\u0007\u0002W)\u0011AfG\u0001\u0007yI|w\u000e\u001e \n\u00059:\u0011A\u0002)sK\u0012,g-\u0003\u00021c\t11\u000b\u001e:j]\u001eT!AL\u0004\u0002\rqJg.\u001b;?)\t!T\u0007E\u0002\u0012\u0001]AQa\t\u0002A\u0002\u0011\nQ!\u00199qYf$\"\u0001O\u001e\u0011\u0007EIt#\u0003\u0002;\u000b\t!\u0001K]8q\u0011\u0015a4\u00011\u0001(\u0003\rYW-\u001f")
public abstract class CreatorImpl<T>
implements Prop.Creator<T> {
    private final Function1<String, T> f;

    @Override
    public Prop<T> apply(String key) {
        return new PropImpl<T>(key, this.f);
    }

    public CreatorImpl(Function1<String, T> f) {
        this.f = f;
    }
}

