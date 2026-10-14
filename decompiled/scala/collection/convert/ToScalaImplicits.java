/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.collection.JavaConverters$;
import scala.collection.concurrent.Map;
import scala.collection.convert.AsScalaConverters;
import scala.collection.mutable.Buffer;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dea\u0002\u0007\u000e!\u0003\r\t\u0001\u0006\u0005\u00063\u0001!\tA\u0007\u0005\u0006=\u0001!\u0019a\b\u0005\u0006s\u0001!\u0019A\u000f\u0005\u0006\t\u0002!\u0019!\u0012\u0005\u0006%\u0002!\u0019a\u0015\u0005\u00069\u0002!\u0019!\u0018\u0005\u0006Y\u0002!\u0019!\u001c\u0005\u0006q\u0002!\u0019!\u001f\u0005\b\u0003#\u0001A1AA\n\u0011\u001d\t)\u0004\u0001C\u0002\u0003oAq!a\u0014\u0001\t\u0007\t\tF\u0001\tU_N\u001b\u0017\r\\1J[Bd\u0017nY5ug*\u0011abD\u0001\bG>tg/\u001a:u\u0015\t\u0001\u0012#\u0001\u0006d_2dWm\u0019;j_:T\u0011AE\u0001\u0006g\u000e\fG.Y\u0002\u0001'\t\u0001Q\u0003\u0005\u0002\u0017/5\t\u0011#\u0003\u0002\u0019#\t1\u0011I\\=SK\u001a\fa\u0001J5oSR$C#A\u000e\u0011\u0005Ya\u0012BA\u000f\u0012\u0005\u0011)f.\u001b;\u0002+%$XM]1u_J$S\u000f\r\u00193a\u0005\u001c8kY1mCV\u0011\u0001e\n\u000b\u0003CA\u00022AI\u0012&\u001b\u0005y\u0011B\u0001\u0013\u0010\u0005!IE/\u001a:bi>\u0014\bC\u0001\u0014(\u0019\u0001!Q\u0001\u000b\u0002C\u0002%\u0012\u0011!Q\t\u0003U5\u0002\"AF\u0016\n\u00051\n\"a\u0002(pi\"Lgn\u001a\t\u0003-9J!aL\t\u0003\u0007\u0005s\u0017\u0010C\u00032\u0005\u0001\u0007!'\u0001\u0002jiB\u00191\u0007O\u0013\u000e\u0003QR!!\u000e\u001c\u0002\tU$\u0018\u000e\u001c\u0006\u0002o\u0005!!.\u0019<b\u0013\t!C'\u0001\u0011f]VlWM]1uS>tG%\u001e\u00191eA\n5oU2bY\u0006LE/\u001a:bi>\u0014XCA\u001e?)\tat\bE\u0002#Gu\u0002\"A\n \u0005\u000b!\u001a!\u0019A\u0015\t\u000b\u0001\u001b\u0001\u0019A!\u0002\u0003%\u00042a\r\">\u0013\t\u0019EGA\u0006F]VlWM]1uS>t\u0017!H5uKJ\f'\r\\3%kB\u0002$\u0007M!t'\u000e\fG.Y%uKJ\f'\r\\3\u0016\u0005\u0019[ECA$M!\r\u0011\u0003JS\u0005\u0003\u0013>\u0011\u0001\"\u0013;fe\u0006\u0014G.\u001a\t\u0003M-#Q\u0001\u000b\u0003C\u0002%BQ\u0001\u0011\u0003A\u00025\u00032AT)K\u001b\u0005y%B\u0001)7\u0003\u0011a\u0017M\\4\n\u0005%{\u0015aH2pY2,7\r^5p]\u0012*\b\u0007\r\u001a1\u0003N\u001c6-\u00197b\u0013R,'/\u00192mKV\u0011Ak\u0016\u000b\u0003+b\u00032A\t%W!\t1s\u000bB\u0003)\u000b\t\u0007\u0011\u0006C\u0003A\u000b\u0001\u0007\u0011\fE\u000245ZK!a\u0017\u001b\u0003\u0015\r{G\u000e\\3di&|g.A\fmSN$H%\u001e\u00191eA\n7oU2bY\u0006\u0014UO\u001a4feV\u0011aL\u001a\u000b\u0003?\u001e\u00042\u0001Y2f\u001b\u0005\t'B\u00012\u0010\u0003\u001diW\u000f^1cY\u0016L!\u0001Z1\u0003\r\t+hMZ3s!\t1c\rB\u0003)\r\t\u0007\u0011\u0006C\u0003i\r\u0001\u0007\u0011.A\u0001m!\r\u0019$.Z\u0005\u0003WR\u0012A\u0001T5ti\u0006\u00012/\u001a;%kB\u0002$\u0007M1t'\u000e\fG.Y\u000b\u0003]N$\"a\u001c;\u0011\u0007\u0001\u0004(/\u0003\u0002rC\n\u00191+\u001a;\u0011\u0005\u0019\u001aH!\u0002\u0015\b\u0005\u0004I\u0003\"B;\b\u0001\u00041\u0018!A:\u0011\u0007M:(/\u0003\u0002ri\u0005\u0001R.\u00199%kB\u0002$\u0007M!t'\u000e\fG.Y\u000b\u0005u~\f)\u0001F\u0002|\u0003\u0013\u0001R\u0001\u0019?\u007f\u0003\u0007I!!`1\u0003\u00075\u000b\u0007\u000f\u0005\u0002'\u007f\u00121\u0011\u0011\u0001\u0005C\u0002%\u0012\u0011a\u0013\t\u0004M\u0005\u0015AABA\u0004\u0011\t\u0007\u0011FA\u0001W\u0011\u001d\tY\u0001\u0003a\u0001\u0003\u001b\t\u0011!\u001c\t\u0007g\u0005=a0a\u0001\n\u0005u$\u0014!H7ba\u0012*\b\u0007\r\u001a1\u0003N\u001c6-\u00197b\u0007>t7-\u001e:sK:$X*\u00199\u0016\r\u0005U\u00111EA\u0014)\u0011\t9\"!\u000b\u0011\u0011\u0005e\u0011qDA\u0011\u0003Ki!!a\u0007\u000b\u0007\u0005uq\"\u0001\u0006d_:\u001cWO\u001d:f]RL1!`A\u000e!\r1\u00131\u0005\u0003\u0007\u0003\u0003I!\u0019A\u0015\u0011\u0007\u0019\n9\u0003\u0002\u0004\u0002\b%\u0011\r!\u000b\u0005\b\u0003\u0017I\u0001\u0019AA\u0016!!\ti#!\r\u0002\"\u0005\u0015RBAA\u0018\u0015\r\ti\u0002N\u0005\u0005\u0003g\tyCA\u0007D_:\u001cWO\u001d:f]Rl\u0015\r]\u0001\u001bI&\u001cG/[8oCJLH%\u001e\u00191eA\n5oU2bY\u0006l\u0015\r]\u000b\u0007\u0003s\ty$a\u0011\u0015\t\u0005m\u0012Q\t\t\u0007Ar\fi$!\u0011\u0011\u0007\u0019\ny\u0004\u0002\u0004\u0002\u0002)\u0011\r!\u000b\t\u0004M\u0005\rCABA\u0004\u0015\t\u0007\u0011\u0006C\u0004\u0002H)\u0001\r!!\u0013\u0002\u0003A\u0004raMA&\u0003{\t\t%C\u0002\u0002NQ\u0012!\u0002R5di&|g.\u0019:z\u0003i\u0001(o\u001c9feRLWm\u001d\u0013vaA\u0012\u0004'Q:TG\u0006d\u0017-T1q)\u0011\t\u0019&a\u001b\u0011\r\u0001d\u0018QKA+!\u0011\t9&!\u001a\u000f\t\u0005e\u0013\u0011\r\t\u0004\u00037\nRBAA/\u0015\r\tyfE\u0001\u0007yI|w\u000e\u001e \n\u0007\u0005\r\u0014#\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003O\nIG\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003G\n\u0002bBA$\u0017\u0001\u0007\u0011Q\u000e\t\u0004g\u0005=\u0014bAA9i\tQ\u0001K]8qKJ$\u0018.Z:)\u0017\u0001\t)(a\u001f\u0002~\u0005\u0005\u00151\u0011\t\u0004-\u0005]\u0014bAA=#\tQA-\u001a9sK\u000e\fG/\u001a3\u0002\u000f5,7o]1hK\u0006\u0012\u0011qP\u0001-+N,\u0007\u0005Y:dC2\fgF\u001b3l]\r{G\u000e\\3di&|gnQ8om\u0016\u0014H/\u001a:tA\u0002Jgn\u001d;fC\u0012\fQa]5oG\u0016\f#!!\"\u0002\rIr\u0013g\r\u00181\u0001")
public interface ToScalaImplicits {
    public static /* synthetic */ Iterator iterator$u0020asScala$(ToScalaImplicits $this, java.util.Iterator it) {
        return $this.iterator$u0020asScala(it);
    }

    default public <A> Iterator<A> iterator$u0020asScala(java.util.Iterator<A> it) {
        return AsScalaConverters.asScala$((AsScalaConverters)JavaConverters$.MODULE$, it);
    }

    public static /* synthetic */ Iterator enumeration$u0020AsScalaIterator$(ToScalaImplicits $this, Enumeration i) {
        return $this.enumeration$u0020AsScalaIterator(i);
    }

    default public <A> Iterator<A> enumeration$u0020AsScalaIterator(Enumeration<A> i) {
        return AsScalaConverters.asScala$((AsScalaConverters)JavaConverters$.MODULE$, i);
    }

    public static /* synthetic */ Iterable iterable$u0020AsScalaIterable$(ToScalaImplicits $this, java.lang.Iterable i) {
        return $this.iterable$u0020AsScalaIterable(i);
    }

    default public <A> Iterable<A> iterable$u0020AsScalaIterable(java.lang.Iterable<A> i) {
        return AsScalaConverters.asScala$((AsScalaConverters)JavaConverters$.MODULE$, i);
    }

    public static /* synthetic */ Iterable collection$u0020AsScalaIterable$(ToScalaImplicits $this, Collection i) {
        return $this.collection$u0020AsScalaIterable(i);
    }

    default public <A> Iterable<A> collection$u0020AsScalaIterable(Collection<A> i) {
        return AsScalaConverters.asScala$((AsScalaConverters)JavaConverters$.MODULE$, i);
    }

    public static /* synthetic */ Buffer list$u0020asScalaBuffer$(ToScalaImplicits $this, List l) {
        return $this.list$u0020asScalaBuffer(l);
    }

    default public <A> Buffer<A> list$u0020asScalaBuffer(List<A> l) {
        return AsScalaConverters.asScala$((AsScalaConverters)JavaConverters$.MODULE$, l);
    }

    public static /* synthetic */ scala.collection.mutable.Set set$u0020asScala$(ToScalaImplicits $this, Set s2) {
        return $this.set$u0020asScala(s2);
    }

    default public <A> scala.collection.mutable.Set<A> set$u0020asScala(Set<A> s2) {
        return AsScalaConverters.asScala$((AsScalaConverters)JavaConverters$.MODULE$, s2);
    }

    public static /* synthetic */ scala.collection.mutable.Map map$u0020AsScala$(ToScalaImplicits $this, java.util.Map m4) {
        return $this.map$u0020AsScala(m4);
    }

    default public <K, V> scala.collection.mutable.Map<K, V> map$u0020AsScala(java.util.Map<K, V> m4) {
        return AsScalaConverters.asScala$((AsScalaConverters)JavaConverters$.MODULE$, m4);
    }

    public static /* synthetic */ Map map$u0020AsScalaConcurrentMap$(ToScalaImplicits $this, ConcurrentMap m4) {
        return $this.map$u0020AsScalaConcurrentMap(m4);
    }

    default public <K, V> Map<K, V> map$u0020AsScalaConcurrentMap(ConcurrentMap<K, V> m4) {
        return AsScalaConverters.asScala$((AsScalaConverters)JavaConverters$.MODULE$, m4);
    }

    public static /* synthetic */ scala.collection.mutable.Map dictionary$u0020AsScalaMap$(ToScalaImplicits $this, Dictionary p) {
        return $this.dictionary$u0020AsScalaMap(p);
    }

    default public <K, V> scala.collection.mutable.Map<K, V> dictionary$u0020AsScalaMap(Dictionary<K, V> p) {
        return AsScalaConverters.asScala$((AsScalaConverters)JavaConverters$.MODULE$, p);
    }

    public static /* synthetic */ scala.collection.mutable.Map properties$u0020AsScalaMap$(ToScalaImplicits $this, Properties p) {
        return $this.properties$u0020AsScalaMap(p);
    }

    default public scala.collection.mutable.Map<String, String> properties$u0020AsScalaMap(Properties p) {
        return AsScalaConverters.asScala$((AsScalaConverters)JavaConverters$.MODULE$, p);
    }

    public static void $init$(ToScalaImplicits $this) {
    }
}

