/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.collection.mutable.UnrolledBuffer;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u2Q!\u0002\u0004\u0001\u00111A\u0011b\b\u0001\u0003\u0002\u0003\u0006Y\u0001\t\u0014\t\u000b!\u0002A\u0011A\u0015\t\u000b5\u0002A\u0011\t\u0018\t\u000bQ\u0002A\u0011K\u001b\u0003-\u0011{WO\u00197j]\u001e,fN]8mY\u0016$')\u001e4gKJT!a\u0002\u0005\u0002\u000f5,H/\u00192mK*\u0011\u0011BC\u0001\u000bG>dG.Z2uS>t'\"A\u0006\u0002\u000bM\u001c\u0017\r\\1\u0016\u00055!2C\u0001\u0001\u000f!\ry\u0001CE\u0007\u0002\r%\u0011\u0011C\u0002\u0002\u000f+:\u0014x\u000e\u001c7fI\n+hMZ3s!\t\u0019B\u0003\u0004\u0001\u0005\u000bU\u0001!\u0019A\f\u0003\u0003Q\u001b\u0001!\u0005\u0002\u00199A\u0011\u0011DG\u0007\u0002\u0015%\u00111D\u0003\u0002\b\u001d>$\b.\u001b8h!\tIR$\u0003\u0002\u001f\u0015\t\u0019\u0011I\\=\u0002\u0003Q\u00042!\t\u0013\u0013\u001b\u0005\u0011#BA\u0012\u000b\u0003\u001d\u0011XM\u001a7fGRL!!\n\u0012\u0003\u0011\rc\u0017m]:UC\u001eL!a\n\t\u0002\u0007Q\fw-\u0001\u0004=S:LGO\u0010\u000b\u0002UQ\u00111\u0006\f\t\u0004\u001f\u0001\u0011\u0002\"B\u0010\u0003\u0001\b\u0001\u0013AD2bY\u000etU\r\u001f;MK:<G\u000f\u001b\u000b\u0003_I\u0002\"!\u0007\u0019\n\u0005ER!aA%oi\")1g\u0001a\u0001_\u0005\u00111O_\u0001\f]\u0016<XK\u001c:pY2,G-F\u00017!\r9$H\u0005\b\u0003\u001faJ!!\u000f\u0004\u0002\u001dUs'o\u001c7mK\u0012\u0014UO\u001a4fe&\u00111\b\u0010\u0002\t+:\u0014x\u000e\u001c7fI*\u0011\u0011H\u0002")
public class DoublingUnrolledBuffer<T>
extends UnrolledBuffer<T> {
    @Override
    public int calcNextLength(int sz) {
        if (sz < 10000) {
            return sz * 2;
        }
        return sz;
    }

    @Override
    public UnrolledBuffer.Unrolled<T> newUnrolled() {
        return new UnrolledBuffer.Unrolled(0, super.tag().newArray(4), null, this, super.tag());
    }

    public DoublingUnrolledBuffer(ClassTag<T> t2) {
        super(t2);
    }
}

