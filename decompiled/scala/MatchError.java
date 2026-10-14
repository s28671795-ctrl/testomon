/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.ObjectStreamException;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m3AAB\u0004\u0003\u0015!A1\u0003\u0001B\u0001B\u0003%A\u0003C\u0003\u001c\u0001\u0011\u0005A\u0004\u0003\u0005 \u0001!\u0015\r\u0015\"\u0003!\u0011\u0015I\u0003\u0001\"\u0003+\u0011\u0015I\u0006\u0001\"\u0011[\u0005)i\u0015\r^2i\u000bJ\u0014xN\u001d\u0006\u0002\u0011\u0005)1oY1mC\u000e\u00011C\u0001\u0001\f!\ta\u0001C\u0004\u0002\u000e\u001d5\tq!\u0003\u0002\u0010\u000f\u00059\u0001/Y2lC\u001e,\u0017BA\t\u0013\u0005A\u0011VO\u001c;j[\u0016,\u0005pY3qi&|gN\u0003\u0002\u0010\u000f\u0005\u0019qN\u00196\u0011\u00055)\u0012B\u0001\f\b\u0005\r\te.\u001f\u0015\u0003\u0003a\u0001\"!D\r\n\u0005i9!!\u0003;sC:\u001c\u0018.\u001a8u\u0003\u0019a\u0014N\\5u}Q\u0011QD\b\t\u0003\u001b\u0001AQa\u0005\u0002A\u0002Q\t\u0011b\u001c2k'R\u0014\u0018N\\4\u0016\u0003\u0005\u0002\"AI\u0014\u000e\u0003\rR!\u0001J\u0013\u0002\t1\fgn\u001a\u0006\u0002M\u0005!!.\u0019<b\u0013\tA3E\u0001\u0004TiJLgnZ\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0002WA\u0011!\u0005L\u0005\u0003[\r\u0012aa\u00142kK\u000e$\bf\u0001\u00030qA\u0019Q\u0002\r\u001a\n\u0005E:!A\u0002;ie><8\u000f\u0005\u00024m5\tAG\u0003\u00026K\u0005\u0011\u0011n\\\u0005\u0003oQ\u0012Qc\u00142kK\u000e$8\u000b\u001e:fC6,\u0005pY3qi&|g.\r\u0003\u001fs\rC\u0006C\u0001\u001eB\u001d\tYt\b\u0005\u0002=\u000f5\tQH\u0003\u0002?\u0013\u00051AH]8pizJ!\u0001Q\u0004\u0002\rA\u0013X\rZ3g\u0013\tA#I\u0003\u0002A\u000fE*1\u0005\u0012%T\u0013V\u0011QIR\u000b\u0002s\u0011)q)\u0003b\u0001\u0019\n\tA+\u0003\u0002J\u0015\u0006YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIER!aS\u0004\u0002\rQD'o\\<t#\ti\u0005\u000b\u0005\u0002\u000e\u001d&\u0011qj\u0002\u0002\b\u001d>$\b.\u001b8h!\ta\u0011+\u0003\u0002S%\tIA\u000b\u001b:po\u0006\u0014G.Z\u0019\u0006GQ+fk\u0013\b\u0003\u001bUK!aS\u00042\t\tjqa\u0016\u0002\u0006g\u000e\fG.Y\u0019\u0003MI\n!bZ3u\u001b\u0016\u001c8/Y4f)\u0005\t\u0003")
public final class MatchError
extends RuntimeException {
    private String objString;
    private final transient Object obj;
    private volatile boolean bitmap$0;

    private String objString$lzycompute() {
        synchronized (this) {
            if (!this.bitmap$0) {
                this.objString = this.obj == null ? "null" : this.liftedTree1$1();
                this.bitmap$0 = true;
            }
        }
        return this.objString;
    }

    private String objString() {
        if (!this.bitmap$0) {
            return this.objString$lzycompute();
        }
        return this.objString;
    }

    private Object writeReplace() throws ObjectStreamException {
        this.objString();
        return this;
    }

    @Override
    public String getMessage() {
        return this.objString();
    }

    private final String ofClass$1() {
        return new StringBuilder(9).append("of class ").append(this.obj.getClass().getName()).toString();
    }

    private final /* synthetic */ String liftedTree1$1() {
        try {
            return new StringBuilder(3).append(this.obj.toString()).append(" (").append(this.ofClass$1()).append(")").toString();
        }
        catch (Throwable throwable) {
            return new StringBuilder(12).append("an instance ").append(this.ofClass$1()).toString();
        }
    }

    public MatchError(Object obj) {
        this.obj = obj;
    }
}

