/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import scala.collection.convert.impl.IndexedStepperBase;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A4aAF\f\u0002\u0002ey\u0002\u0002C\u001d\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u001e\t\u0011u\u0002!\u0011!Q\u0001\niB\u0001B\u0010\u0001\u0003\u0006\u0004%\tb\u0010\u0005\t\u0001\u0002\u0011\t\u0011)A\u0005u!A\u0011\t\u0001BC\u0002\u0013E!\t\u0003\u0005J\u0001\t\u0005\t\u0015!\u0003D\u0011\u0015Q\u0005\u0001\"\u0001L\u0011\u001d\t\u0006\u00011A\u0005\u0012}BqA\u0015\u0001A\u0002\u0013E1\u000b\u0003\u0004Z\u0001\u0001\u0006KA\u000f\u0005\b5\u0002\u0001\r\u0011\"\u0005C\u0011\u001dY\u0006\u00011A\u0005\u0012qCaA\u0018\u0001!B\u0013\u0019\u0005bB0\u0001\u0001\u0004%\tb\u0010\u0005\bA\u0002\u0001\r\u0011\"\u0005b\u0011\u0019\u0019\u0007\u0001)Q\u0005u!9A\r\u0001a\u0001\n#\u0011\u0005bB3\u0001\u0001\u0004%\tB\u001a\u0005\u0007Q\u0002\u0001\u000b\u0015B\"\t\u000b%\u0004AQ\u00036\t\u000b5\u0004AQ\u00038\u0003#Y+7\r^8s'R,\u0007\u000f]3s\u0005\u0006\u001cXM\u0003\u0002\u00193\u0005!\u0011.\u001c9m\u0015\tQ2$A\u0004d_:4XM\u001d;\u000b\u0005qi\u0012AC2pY2,7\r^5p]*\ta$A\u0003tG\u0006d\u0017-F\u0002!OM\u001a\"\u0001A\u0011\u0011\t\t\u001aSEM\u0007\u0002/%\u0011Ae\u0006\u0002\u0013\u0013:$W\r_3e'R,\u0007\u000f]3s\u0005\u0006\u001cX\r\u0005\u0002'O1\u0001A!\u0002\u0015\u0001\u0005\u0004Q#aA*vE\u000e\u0001\u0011CA\u00160!\taS&D\u0001\u001e\u0013\tqSD\u0001\u0003Ok2d\u0007C\u0001\u00171\u0013\t\tTDA\u0002B]f\u0004\"AJ\u001a\u0005\u000bQ\u0002!\u0019A\u001b\u0003\tM+W.[\t\u0003m\u0015\u0002\"\u0001L\u001c\n\u0005aj\"a\u0002(pi\"LgnZ\u0001\u0004?&\u0004\u0004C\u0001\u0017<\u0013\taTDA\u0002J]R\f1aX5O\u0003!!\u0017n\u001d9mCftU#\u0001\u001e\u0002\u0013\u0011L7\u000f\u001d7bs:\u0003\u0013!\u0002;sk:\\W#A\"\u0011\u00071\"e)\u0003\u0002F;\t)\u0011I\u001d:bsB\u0011AfR\u0005\u0003\u0011v\u0011a!\u00118z%\u00164\u0017A\u0002;sk:\\\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0006\u00196su\n\u0015\t\u0005E\u0001)#\u0007C\u0003:\u000f\u0001\u0007!\bC\u0003>\u000f\u0001\u0007!\bC\u0003?\u000f\u0001\u0007!\bC\u0003B\u000f\u0001\u00071)A\u0003j]\u0012,\u00070A\u0005j]\u0012,\u0007p\u0018\u0013fcR\u0011Ak\u0016\t\u0003YUK!AV\u000f\u0003\tUs\u0017\u000e\u001e\u0005\b1&\t\t\u00111\u0001;\u0003\rAH%M\u0001\u0007S:$W\r\u001f\u0011\u0002\r1,\u0017M^3t\u0003)aW-\u0019<fg~#S-\u001d\u000b\u0003)vCq\u0001\u0017\u0007\u0002\u0002\u0003\u00071)A\u0004mK\u00064Xm\u001d\u0011\u0002\r%tG-\u001a=2\u0003)Ig\u000eZ3yc}#S-\u001d\u000b\u0003)\nDq\u0001W\b\u0002\u0002\u0003\u0007!(A\u0004j]\u0012,\u00070\r\u0011\u0002\u000bQ<\u0018nZ:\u0002\u0013Q<\u0018nZ:`I\u0015\fHC\u0001+h\u0011\u001dA&#!AA\u0002\r\u000ba\u0001^<jON\u0004\u0013aC1em\u0006t7-\u001a#bi\u0006$\"\u0001V6\t\u000b1$\u0002\u0019\u0001\u001e\u0002\u0005%D\u0016AB5oSR$v\u000e\u0006\u0002U_\")A.\u0006a\u0001u\u0001")
public abstract class VectorStepperBase<Sub, Semi extends Sub>
extends IndexedStepperBase<Sub, Semi> {
    private final int displayN;
    private final Object[] trunk;
    private int index;
    private Object[] leaves;
    private int index1;
    private Object[] twigs;

    public int displayN() {
        return this.displayN;
    }

    public Object[] trunk() {
        return this.trunk;
    }

    public int index() {
        return this.index;
    }

    public void index_$eq(int x$1) {
        this.index = x$1;
    }

    public Object[] leaves() {
        return this.leaves;
    }

    public void leaves_$eq(Object[] x$1) {
        this.leaves = x$1;
    }

    public int index1() {
        return this.index1;
    }

    public void index1_$eq(int x$1) {
        this.index1 = x$1;
    }

    public Object[] twigs() {
        return this.twigs;
    }

    public void twigs_$eq(Object[] x$1) {
        this.twigs = x$1;
    }

    public final void advanceData(int iX) {
        this.index1_$eq(this.index1() + 1);
        if (this.index1() >= 32) {
            this.initTo(iX);
            return;
        }
        this.leaves_$eq((Object[])this.twigs()[this.index1()]);
        this.index_$eq(0);
    }

    public final void initTo(int iX) {
        switch (this.displayN()) {
            case 0: {
                this.leaves_$eq(this.trunk());
                this.index_$eq(iX);
                return;
            }
            case 1: {
                this.twigs_$eq(this.trunk());
                this.index1_$eq(iX >>> 5);
                this.leaves_$eq((Object[])this.twigs()[this.index1()]);
                this.index_$eq(iX & 0x1F);
                return;
            }
        }
        Object[] dataN = this.trunk();
        for (int n = this.displayN(); n > 2; --n) {
            dataN = (Object[])dataN[iX >> 5 * n & 0x1F];
        }
        this.twigs_$eq((Object[])dataN[iX >>> 10 & 0x1F]);
        this.index1_$eq(iX >> 5 & 0x1F);
        this.leaves_$eq((Object[])this.twigs()[this.index1()]);
        this.index_$eq(iX & 0x1F);
    }

    public VectorStepperBase(int _i0, int _iN, int displayN, Object[] trunk) {
        this.displayN = displayN;
        this.trunk = trunk;
        super(_i0, _iN);
        this.index = 32;
        this.leaves = null;
        this.index1 = 32;
        this.twigs = null;
    }
}

