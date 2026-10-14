/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U3qa\u0003\u0007\u0011\u0002\u0007\u0005q\u0002C\u0003\u0015\u0001\u0011\u0005Q\u0003C\u0003\u001a\u0001\u0019\u0005!\u0004C\u0003\u001c\u0001\u0011\u0005C\u0004C\u0003!\u0001\u0011\u0005\u0013\u0005C\u0003(\u0001\u0011\u0005\u0003fB\u0003<\u0019!\u0005AHB\u0003\f\u0019!\u0005Q\bC\u0003B\u000f\u0011\u0005!IB\u0004D\u000fA\u0005\u0019\u0013\u0001#\t\u000beIa\u0011A$\u0003\u000bA\u0013x\u000e_=\u000b\u00035\tQa]2bY\u0006\u001c\u0001a\u0005\u0002\u0001!A\u0011\u0011CE\u0007\u0002\u0019%\u00111\u0003\u0004\u0002\u0004\u0003:L\u0018A\u0002\u0013j]&$H\u0005F\u0001\u0017!\t\tr#\u0003\u0002\u0019\u0019\t!QK\\5u\u0003\u0011\u0019X\r\u001c4\u0016\u0003A\t\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002;A\u0011\u0011CH\u0005\u0003?1\u00111!\u00138u\u0003\u0019)\u0017/^1mgR\u0011!%\n\t\u0003#\rJ!\u0001\n\u0007\u0003\u000f\t{w\u000e\\3b]\")a\u0005\u0002a\u0001!\u0005!A\u000f[1u\u0003!!xn\u0015;sS:<G#A\u0015\u0011\u0005)zS\"A\u0016\u000b\u00051j\u0013\u0001\u00027b]\u001eT\u0011AL\u0001\u0005U\u00064\u0018-\u0003\u00021W\t11\u000b\u001e:j]\u001eDc\u0001\u0001\u001a6maJ\u0004CA\t4\u0013\t!DB\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-I\u00018\u0003i*\u0005\u0010\u001d7jG&$H.\u001f\u0011pm\u0016\u0014(/\u001b3fA!\f7\u000f[\"pI\u0016d\u0003%Z9vC2\u001c\b%\u00198eAQ|7\u000b\u001e:j]\u001e\u0004\u0013N\\:uK\u0006$g&A\u0003tS:\u001cW-I\u0001;\u0003\u0019\u0011d&M\u001a/a\u0005)\u0001K]8ysB\u0011\u0011cB\n\u0003\u000fy\u0002\"!E \n\u0005\u0001c!AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002y\t)A+\u001f9fIV\u0011QIS\n\u0004\u0013A1\u0005CA\t\u0001+\u0005A\u0005CA%K\u0019\u0001!QaS\u0005C\u00021\u0013\u0011\u0001V\t\u0003\u001bB\u0001\"!\u0005(\n\u0005=c!a\u0002(pi\"Lgn\u001a\u0015\u0007\u0013I*d\u0007O\u001d)\r\u001d\u0011TG\u0015\u001d:C\u0005\u0019\u0016AK!mY\u0002jW-\u001c2feN\u0004sN\u001a\u0011uQ&\u001c\be\u001c2kK\u000e$\b%\u0019:fA\u0011,\u0007O]3dCR,GM\f\u0015\u0007\rI*$\u000bO\u001d")
public interface Proxy {
    public Object self();

    public static /* synthetic */ int hashCode$(Proxy $this) {
        return $this.hashCode();
    }

    default public int hashCode() {
        return this.self().hashCode();
    }

    public static /* synthetic */ boolean equals$(Proxy $this, Object that) {
        return $this.equals(that);
    }

    default public boolean equals(Object that) {
        if (that == null) {
            return false;
        }
        return that == this || that == this.self() || that.equals(this.self());
    }

    public static /* synthetic */ String toString$(Proxy $this) {
        return $this.toString();
    }

    default public String toString() {
        return String.valueOf(this.self());
    }

    public static void $init$(Proxy $this) {
    }

    public static interface Typed<T>
    extends Proxy {
        public T self();
    }
}

