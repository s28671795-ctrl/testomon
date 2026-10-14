/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function0;
import scala.collection.Factory;
import scala.collection.View;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)3qa\u0002\u0005\u0011\u0002\u0007\u0005Q\u0002C\u0003&\u0001\u0011\u0005a\u0005C\u0003+\u0001\u0019\u00051\u0006C\u0003-\u0001\u0011\u0005Q\u0006C\u00034\u0001\u0011\u0005A\u0007C\u0003A\u0001\u0019\u0005\u0011\tC\u0003I\u0001\u0011\r\u0011JA\fTa\u0016\u001c\u0017NZ5d\u0013R,'/\u00192mK\u001a\u000b7\r^8ss*\u0011\u0011BC\u0001\u000bG>dG.Z2uS>t'\"A\u0006\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0019a\"G\u0012\u0014\u0007\u0001y1\u0003\u0005\u0002\u0011#5\t!\"\u0003\u0002\u0013\u0015\t1\u0011I\\=SK\u001a\u0004B\u0001F\u000b\u0018E5\t\u0001\"\u0003\u0002\u0017\u0011\t9a)Y2u_JL\bC\u0001\r\u001a\u0019\u0001!aA\u0007\u0001\t\u0006\u0004Y\"!A!\u0012\u0005qy\u0002C\u0001\t\u001e\u0013\tq\"BA\u0004O_RD\u0017N\\4\u0011\u0005A\u0001\u0013BA\u0011\u000b\u0005\r\te.\u001f\t\u00031\r\"a\u0001\n\u0001\u0005\u0006\u0004Y\"!A\"\u0002\r\u0011Jg.\u001b;%)\u00059\u0003C\u0001\t)\u0013\tI#B\u0001\u0003V]&$\u0018!B3naRLX#\u0001\u0012\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0005\tr\u0003\"B\u0018\u0004\u0001\u0004\u0001\u0014A\u0001=t!\r\u0001\u0012gF\u0005\u0003e)\u0011!\u0002\u0010:fa\u0016\fG/\u001a3?\u0003\u00111\u0017\u000e\u001c7\u0015\u0005UZDC\u0001\u00127\u0011\u00199D\u0001\"a\u0001q\u0005!Q\r\\3n!\r\u0001\u0012hF\u0005\u0003u)\u0011\u0001\u0002\u00102z]\u0006lWM\u0010\u0005\u0006y\u0011\u0001\r!P\u0001\u0002]B\u0011\u0001CP\u0005\u0003\u007f)\u00111!\u00138u\u0003)qWm\u001e\"vS2$WM]\u000b\u0002\u0005B!1IR\f#\u001b\u0005!%BA#\t\u0003\u001diW\u000f^1cY\u0016L!a\u0012#\u0003\u000f\t+\u0018\u000e\u001c3fe\u000692\u000f]3dS\u001aL7-\u0013;fe\u0006\u0014G.\u001a$bGR|'/_\u000b\u0002'\u0001")
public interface SpecificIterableFactory<A, C>
extends Factory<A, C> {
    public C empty();

    public static /* synthetic */ Object apply$(SpecificIterableFactory $this, Seq xs) {
        return $this.apply(xs);
    }

    default public C apply(Seq<A> xs) {
        return this.fromSpecific(xs);
    }

    public static /* synthetic */ Object fill$(SpecificIterableFactory $this, int n, Function0 elem) {
        return $this.fill(n, elem);
    }

    default public C fill(int n, Function0<A> elem) {
        return this.fromSpecific(new View.Fill<A>(n, elem));
    }

    @Override
    public Builder<A, C> newBuilder();

    public static /* synthetic */ Factory specificIterableFactory$(SpecificIterableFactory $this) {
        return $this.specificIterableFactory();
    }

    default public Factory<A, C> specificIterableFactory() {
        return this;
    }

    public static void $init$(SpecificIterableFactory $this) {
    }
}

