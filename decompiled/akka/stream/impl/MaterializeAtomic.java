/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Shape;
import akka.stream.impl.MaterializeAtomic$;
import akka.stream.impl.StreamLayout;
import akka.stream.impl.Traversal;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005f!\u0002\u000e\u001c\u0005~\t\u0003\u0002\u0003\u001f\u0001\u0005+\u0007I\u0011A\u001f\t\u0011I\u0003!\u0011#Q\u0001\nyB\u0001b\u0015\u0001\u0003\u0016\u0004%\t\u0001\u0016\u0005\t7\u0002\u0011\t\u0012)A\u0005+\")A\f\u0001C\u0001;\")\u0011\r\u0001C!E\")1\u000e\u0001C!Y\"9q\u000eAA\u0001\n\u0003\u0001\bbB:\u0001#\u0003%\t\u0001\u001e\u0005\t\u007f\u0002\t\n\u0011\"\u0001\u0002\u0002!I\u0011Q\u0001\u0001\u0002\u0002\u0013\u0005\u0013q\u0001\u0005\n\u0003/\u0001\u0011\u0011!C\u0001\u00033A\u0011\"a\u0007\u0001\u0003\u0003%\t!!\b\t\u0013\u0005\r\u0002!!A\u0005B\u0005\u0015\u0002\"CA\u001a\u0001\u0005\u0005I\u0011AA\u001b\u0011%\ty\u0004AA\u0001\n\u0003\n\t\u0005C\u0005\u0002F\u0001\t\t\u0011\"\u0011\u0002H!I\u0011\u0011\n\u0001\u0002\u0002\u0013\u0005\u00131J\u0004\u000b\u00037Z\u0012\u0011!E\u0001?\u0005uc!\u0003\u000e\u001c\u0003\u0003E\taHA0\u0011\u0019aF\u0003\"\u0001\u0002x!A\u0011\rFA\u0001\n\u000b\nI\bC\u0005\u0002|Q\t\t\u0011\"!\u0002~!I\u00111\u0011\u000b\u0002\u0002\u0013\u0005\u0015Q\u0011\u0005\n\u0003/#\u0012\u0011!C\u0005\u00033\u0013\u0011#T1uKJL\u0017\r\\5{K\u0006#x.\\5d\u0015\taR$\u0001\u0003j[Bd'B\u0001\u0010 \u0003\u0019\u0019HO]3b[*\t\u0001%\u0001\u0003bW.\f7#\u0002\u0001#Q1z\u0003CA\u0012'\u001b\u0005!#\"A\u0013\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u001d\"#AB!osJ+g\r\u0005\u0002*U5\t1$\u0003\u0002,7\tIAK]1wKJ\u001c\u0018\r\u001c\t\u0003G5J!A\f\u0013\u0003\u000fA\u0013x\u000eZ;diB\u0011\u0001'\u000f\b\u0003c]r!A\r\u001c\u000e\u0003MR!\u0001N\u001b\u0002\rq\u0012xn\u001c;?\u0007\u0001I\u0011!J\u0005\u0003q\u0011\nq\u0001]1dW\u0006<W-\u0003\u0002;w\ta1+\u001a:jC2L'0\u00192mK*\u0011\u0001\bJ\u0001\u0007[>$W\u000f\\3\u0016\u0003y\u0002Ba\u0010%L\u001f:\u0011\u0001I\u0012\b\u0003\u0003\u0016s!A\u0011#\u000f\u0005I\u001a\u0015\"\u0001\u0011\n\u0005yy\u0012B\u0001\u000f\u001e\u0013\t95$\u0001\u0007TiJ,\u0017-\u001c'bs>,H/\u0003\u0002J\u0015\na\u0011\t^8nS\u000elu\u000eZ;mK*\u0011qi\u0007\t\u0003\u00196k\u0011!H\u0005\u0003\u001dv\u0011Qa\u00155ba\u0016\u0004\"a\t)\n\u0005E##aA!os\u00069Qn\u001c3vY\u0016\u0004\u0013AC8viR{7\u000b\\8ugV\tQ\u000bE\u0002$-bK!a\u0016\u0013\u0003\u000b\u0005\u0013(/Y=\u0011\u0005\rJ\u0016B\u0001.%\u0005\rIe\u000e^\u0001\f_V$Hk\\*m_R\u001c\b%\u0001\u0004=S:LGO\u0010\u000b\u0004=~\u0003\u0007CA\u0015\u0001\u0011\u0015aT\u00011\u0001?\u0011\u0015\u0019V\u00011\u0001V\u0003!!xn\u0015;sS:<G#A2\u0011\u0005\u0011DgBA3g!\t\u0011D%\u0003\u0002hI\u00051\u0001K]3eK\u001aL!!\u001b6\u0003\rM#(/\u001b8h\u0015\t9G%A\u0007sK^L'/\u001a$jeN$Hk\u001c\u000b\u0003Q5DQA\\\u0004A\u0002a\u000baB]3mCRLg/Z(gMN,G/\u0001\u0003d_BLHc\u00010re\"9A\b\u0003I\u0001\u0002\u0004q\u0004bB*\t!\u0003\u0005\r!V\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005)(F\u0001 wW\u00059\bC\u0001=~\u001b\u0005I(B\u0001>|\u0003%)hn\u00195fG.,GM\u0003\u0002}I\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005yL(!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012TCAA\u0002U\t)f/A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003\u0013\u0001B!a\u0003\u0002\u00165\u0011\u0011Q\u0002\u0006\u0005\u0003\u001f\t\t\"\u0001\u0003mC:<'BAA\n\u0003\u0011Q\u0017M^1\n\u0007%\fi!\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001Y\u00039\u0001(o\u001c3vGR,E.Z7f]R$2aTA\u0010\u0011!\t\t#DA\u0001\u0002\u0004A\u0016a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002(A)\u0011\u0011FA\u0018\u001f6\u0011\u00111\u0006\u0006\u0004\u0003[!\u0013AC2pY2,7\r^5p]&!\u0011\u0011GA\u0016\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005]\u0012Q\b\t\u0004G\u0005e\u0012bAA\u001eI\t9!i\\8mK\u0006t\u0007\u0002CA\u0011\u001f\u0005\u0005\t\u0019A(\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0003\u0013\t\u0019\u0005\u0003\u0005\u0002\"A\t\t\u00111\u0001Y\u0003!A\u0017m\u001d5D_\u0012,G#\u0001-\u0002\r\u0015\fX/\u00197t)\u0011\t9$!\u0014\t\u0011\u0005\u0005\"#!AA\u0002=C3\u0001AA)!\u0011\t\u0019&a\u0016\u000e\u0005\u0005U#B\u0001? \u0013\u0011\tI&!\u0016\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\u0012\u001b\u0006$XM]5bY&TX-\u0011;p[&\u001c\u0007CA\u0015\u0015'\u0015!\u0012\u0011MA7!\u001d\t\u0019'!\u001b?+zk!!!\u001a\u000b\u0007\u0005\u001dD%A\u0004sk:$\u0018.\\3\n\t\u0005-\u0014Q\r\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\u0014\u0004\u0003BA8\u0003kj!!!\u001d\u000b\t\u0005M\u0014\u0011C\u0001\u0003S>L1AOA9)\t\ti\u0006\u0006\u0002\u0002\n\u0005)\u0011\r\u001d9msR)a,a \u0002\u0002\")Ah\u0006a\u0001}!)1k\u0006a\u0001+\u00069QO\\1qa2LH\u0003BAD\u0003'\u0003RaIAE\u0003\u001bK1!a#%\u0005\u0019y\u0005\u000f^5p]B)1%a$?+&\u0019\u0011\u0011\u0013\u0013\u0003\rQ+\b\u000f\\33\u0011!\t)\nGA\u0001\u0002\u0004q\u0016a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u00111\u0014\t\u0005\u0003\u0017\ti*\u0003\u0003\u0002 \u00065!AB(cU\u0016\u001cG\u000f")
@InternalApi
public final class MaterializeAtomic
implements Traversal,
Product,
Serializable {
    private final StreamLayout.AtomicModule<Shape, Object> module;
    private final int[] outToSlots;

    public static Option<Tuple2<StreamLayout.AtomicModule<Shape, Object>, int[]>> unapply(MaterializeAtomic x$0) {
        return MaterializeAtomic$.MODULE$.unapply(x$0);
    }

    public static MaterializeAtomic apply(StreamLayout.AtomicModule<Shape, Object> module, int[] outToSlots) {
        return MaterializeAtomic$.MODULE$.apply(module, outToSlots);
    }

    public static Function1<Tuple2<StreamLayout.AtomicModule<Shape, Object>, int[]>, MaterializeAtomic> tupled() {
        return MaterializeAtomic$.MODULE$.tupled();
    }

    public static Function1<StreamLayout.AtomicModule<Shape, Object>, Function1<int[], MaterializeAtomic>> curried() {
        return MaterializeAtomic$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public Traversal concat(Traversal that) {
        return Traversal.concat$(this, that);
    }

    public StreamLayout.AtomicModule<Shape, Object> module() {
        return this.module;
    }

    public int[] outToSlots() {
        return this.outToSlots;
    }

    public String toString() {
        return new StringBuilder(21).append("MaterializeAtomic(").append(this.module()).append(", ").append(Predef$.MODULE$.wrapIntArray(this.outToSlots()).mkString("[", ", ", "]")).append(")").toString();
    }

    @Override
    public Traversal rewireFirstTo(int relativeOffset) {
        int[] x$1 = new int[]{relativeOffset};
        StreamLayout.AtomicModule<Shape, Object> x$2 = this.copy$default$1();
        return this.copy(x$2, x$1);
    }

    public MaterializeAtomic copy(StreamLayout.AtomicModule<Shape, Object> module, int[] outToSlots) {
        return new MaterializeAtomic(module, outToSlots);
    }

    public StreamLayout.AtomicModule<Shape, Object> copy$default$1() {
        return this.module();
    }

    public int[] copy$default$2() {
        return this.outToSlots();
    }

    @Override
    public String productPrefix() {
        return "MaterializeAtomic";
    }

    @Override
    public int productArity() {
        return 2;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.module();
            }
            case 1: {
                return this.outToSlots();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof MaterializeAtomic;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "module";
            }
            case 1: {
                return "outToSlots";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof MaterializeAtomic)) return false;
        boolean bl = true;
        if (!bl) return false;
        MaterializeAtomic materializeAtomic = (MaterializeAtomic)x$1;
        StreamLayout.AtomicModule<Shape, Object> atomicModule = this.module();
        StreamLayout.AtomicModule<Shape, Object> atomicModule2 = materializeAtomic.module();
        if (atomicModule == null) {
            if (atomicModule2 != null) {
                return false;
            }
        } else if (!atomicModule.equals(atomicModule2)) return false;
        if (this.outToSlots() != materializeAtomic.outToSlots()) return false;
        return true;
    }

    public MaterializeAtomic(StreamLayout.AtomicModule<Shape, Object> module, int[] outToSlots) {
        this.module = module;
        this.outToSlots = outToSlots;
        Traversal.$init$(this);
        Product.$init$(this);
    }
}

