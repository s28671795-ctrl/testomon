/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.Shape$;
import akka.util.ccompat.package$JavaConverters$;
import java.io.Serializable;
import java.util.List;
import scala.Function0;
import scala.Predef$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005mqA\u0002\t\u0012\u0011\u0003\u0019RC\u0002\u0004\u0018#!\u00051\u0003\u0007\u0005\u0006?\u0005!\t!\t\u0005\u0006E\u0005!\ta\t\u0004\u0006/E\t\t!\u0012\u0005\u0006?\u0011!\tA\u0012\u0005\u0006\u0011\u00121\t!\u0013\u0005\u0006%\u00121\ta\u0015\u0005\u00069\u00121\tA\u0012\u0005\u0006;\u0012!\tA\u0018\u0005\u0006Y\u0012!\t!\u001c\u0005\u0006i\u0012!\t!\u001e\u0005\u0006q\u0012!\t!\u001f\u0005\u0006w\u0012!\t\u0001 \u0005\b\u0003\u0007!A\u0011AA\u0003\u0011\u001d\tI\u0001\u0002C\u0005\u0003\u0017\tQa\u00155ba\u0016T!AE\n\u0002\rM$(/Z1n\u0015\u0005!\u0012\u0001B1lW\u0006\u0004\"AF\u0001\u000e\u0003E\u0011Qa\u00155ba\u0016\u001c\"!A\r\u0011\u0005iiR\"A\u000e\u000b\u0003q\tQa]2bY\u0006L!AH\u000e\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}\r\u0001A#A\u000b\u0002\u0015!\f7o\u00148f!>\u0014H\u000f\u0006\u0002%OA\u0011!$J\u0005\u0003Mm\u0011qAQ8pY\u0016\fg\u000eC\u0003)\u0007\u0001\u0007\u0011&A\u0003q_J$8\u000f\r\u0002+iA\u00191\u0006\r\u001a\u000e\u00031R!!\f\u0018\u0002\u0013%lW.\u001e;bE2,'BA\u0018\u001c\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003c1\u00121aU3r!\t\u0019D\u0007\u0004\u0001\u0005\u0013U:\u0013\u0011!A\u0001\u0006\u00031$aA0%kE\u0011qG\u000f\t\u00035aJ!!O\u000e\u0003\u000f9{G\u000f[5oOB\u0011!dO\u0005\u0003ym\u00111!\u00118zQ\t\ta\b\u0005\u0002@\u00056\t\u0001I\u0003\u0002B'\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005\r\u0003%aC%oi\u0016\u0014h.\u00197Ba&D#\u0001\u0001 \u0014\u0005\u0011IB#A$\u0011\u0005Y!\u0011AB5oY\u0016$8/F\u0001K!\rY\u0003g\u0013\u0019\u0003\u0019B\u00032AF'P\u0013\tq\u0015CA\u0003J]2,G\u000f\u0005\u00024!\u0012I\u0011KBA\u0001\u0002\u0003\u0015\tA\u000e\u0002\u0004?\u00122\u0014aB8vi2,Go]\u000b\u0002)B\u00191\u0006M+1\u0005YS\u0006c\u0001\fX3&\u0011\u0001,\u0005\u0002\u0007\u001fV$H.\u001a;\u0011\u0005MRF!C.\b\u0003\u0003\u0005\tQ!\u00017\u0005\ryFeN\u0001\tI\u0016,\u0007oQ8qs\u0006Iq-\u001a;J]2,Go]\u000b\u0002?B\u0019\u0001-Z4\u000e\u0003\u0005T!AY2\u0002\tU$\u0018\u000e\u001c\u0006\u0002I\u0006!!.\u0019<b\u0013\t1\u0017M\u0001\u0003MSN$\bG\u00015k!\r1R*\u001b\t\u0003g)$\u0011b[\u0005\u0002\u0002\u0003\u0005)\u0011\u0001\u001c\u0003\u0007}#\u0003(\u0001\u0006hKR|U\u000f\u001e7fiN,\u0012A\u001c\t\u0004A\u0016|\u0007G\u00019s!\r1r+\u001d\t\u0003gI$\u0011b\u001d\u0006\u0002\u0002\u0003\u0005)\u0011\u0001\u001c\u0003\u0007}#\u0013(\u0001\biCN\u001c\u0016-\\3Q_J$8/Q:\u0015\u0005\u00112\b\"B<\f\u0001\u00049\u0015!A:\u0002-!\f7oU1nKB{'\u000f^:B]\u0012\u001c\u0006.\u00199f\u0003N$\"\u0001\n>\t\u000b]d\u0001\u0019A$\u0002%I,\u0017/^5sKN\u000bW.\u001a)peR\u001c\u0018i\u001d\u000b\u0004{\u0006\u0005\u0001C\u0001\u000e\u007f\u0013\ty8D\u0001\u0003V]&$\b\"B<\u000e\u0001\u00049\u0015A\u0007:fcVL'/Z*b[\u0016\u0004vN\u001d;t\u0003:$7\u000b[1qK\u0006\u001bHcA?\u0002\b!)qO\u0004a\u0001\u000f\u00069bn\u001c8D_J\u0014Xm\u001d9p]\u0012LgnZ'fgN\fw-\u001a\u000b\u0005\u0003\u001b\tI\u0002\u0005\u0003\u0002\u0010\u0005UQBAA\t\u0015\r\t\u0019bY\u0001\u0005Y\u0006tw-\u0003\u0003\u0002\u0018\u0005E!AB*ue&tw\rC\u0003x\u001f\u0001\u0007q\t")
public abstract class Shape {
    public static boolean hasOnePort(Seq<?> ports) {
        return Shape$.MODULE$.hasOnePort(ports);
    }

    public abstract Seq<Inlet<?>> inlets();

    public abstract Seq<Outlet<?>> outlets();

    public abstract Shape deepCopy();

    public List<Inlet<?>> getInlets() {
        return package$JavaConverters$.MODULE$.SeqHasAsJava(this.inlets()).asJava();
    }

    public List<Outlet<?>> getOutlets() {
        return package$JavaConverters$.MODULE$.SeqHasAsJava(this.outlets()).asJava();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean hasSamePortsAs(Shape s2) {
        Set set = this.inlets().toSet();
        Set set2 = s2.inlets().toSet();
        if (set == null) {
            if (set2 != null) {
                return false;
            }
        } else if (!set.equals(set2)) return false;
        Set set3 = this.outlets().toSet();
        Set set4 = s2.outlets().toSet();
        if (set3 == null) {
            if (set4 == null) return true;
            return false;
        } else {
            if (!set3.equals(set4)) return false;
            return true;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean hasSamePortsAndShapeAs(Shape s2) {
        Seq<Inlet<?>> seq = this.inlets();
        Seq<Inlet<?>> seq2 = s2.inlets();
        if (seq == null) {
            if (seq2 != null) {
                return false;
            }
        } else if (!seq.equals(seq2)) return false;
        Seq<Outlet<?>> seq3 = this.outlets();
        Seq<Outlet<?>> seq4 = s2.outlets();
        if (seq3 == null) {
            if (seq4 == null) return true;
            return false;
        } else {
            if (!seq3.equals(seq4)) return false;
            return true;
        }
    }

    public void requireSamePortsAs(Shape s2) {
        Predef$.MODULE$.require(this.hasSamePortsAs(s2), (Function0<Object>)(Function0<String> & Serializable)() -> this.nonCorrespondingMessage(s2));
    }

    public void requireSamePortsAndShapeAs(Shape s2) {
        Predef$.MODULE$.require(this.hasSamePortsAndShapeAs(s2), (Function0<Object>)(Function0<String> & Serializable)() -> this.nonCorrespondingMessage(s2));
    }

    private String nonCorrespondingMessage(Shape s2) {
        return new StringBuilder(76).append("The inlets [").append(s2.inlets().mkString(", ")).append("] and outlets [").append(s2.outlets().mkString(", ")).append("] must correspond to the inlets [").append(this.inlets().mkString(", ")).append("] and outlets [").append(this.outlets().mkString(", ")).append("]").toString();
    }
}

