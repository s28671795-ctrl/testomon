/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.NoSuchElementException;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterator;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.MapFactory;
import scala.collection.MapOps;
import scala.collection.Set;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StrictOptimizedMapOps;
import scala.collection.generic.DefaultSerializable;
import scala.collection.immutable.Seq;
import scala.collection.mutable.AbstractMap;
import scala.collection.mutable.Builder;
import scala.collection.mutable.GrowableBuilder;
import scala.collection.mutable.HashEntry;
import scala.collection.mutable.HashTable;
import scala.collection.mutable.Iterable;
import scala.collection.mutable.LinkedHashMap$;
import scala.collection.mutable.LinkedHashSet$;
import scala.collection.mutable.SeqMap;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.util.Either;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@ScalaSignature(bytes="\u0006\u0005\r-q!B A\u0011\u00039e!B%A\u0011\u0003Q\u0005bBA\u0004\u0003\u0011\u0005!q\u0016\u0005\b\u0005c\u000bA\u0011\u0001BZ\u0011\u001d\u0011\t-\u0001C\u0001\u0005\u0007DqA!8\u0002\t\u0003\u0011yNB\u0004\u0002\u0016\u0005\u0011\u0001)a\u0006\t\u0015\u00055bA!b\u0001\n\u0003\ty\u0003\u0003\u0006\u00022\u0019\u0011\t\u0011)A\u0005\u0003CA!\"a\r\u0007\u0005\u0003\u0007I\u0011AA\u001b\u0011)\t9D\u0002BA\u0002\u0013\u0005\u0011\u0011\b\u0005\u000b\u0003\u000b2!\u0011!Q!\n\u0005%\u0002bBA\u0004\r\u0011\u0005\u0011q\t\u0005\n\u0003\u001b2\u0001\u0019!C\u0001\u0003\u001fB\u0011\"!\u0015\u0007\u0001\u0004%\t!a\u0015\t\u0011\u0005]c\u0001)Q\u0005\u0003KA\u0011\"!\u0017\u0007\u0001\u0004%\t!a\u0014\t\u0013\u0005mc\u00011A\u0005\u0002\u0005u\u0003\u0002CA1\r\u0001\u0006K!!\n\t\u0013\tU\u0018!!A\u0005\n\t]h\u0001B%A\u0001QCq!a\u0002\u0015\t\u0003\tI\u0001C\u0004\u0002\fQ!\t%!\u0004\u0006\u000f\u0005=A\u0003\u0001\"\u0002\u0012!A\u00111\r\u000b\u0005\u0002\t\u000b)\u0007C\u0005\u0002lQ\u0001\r\u0011\"\u0005\u0002f!I\u0011Q\u000e\u000bA\u0002\u0013E\u0011q\u000e\u0005\t\u0003g\"\u0002\u0015)\u0003\u0002h!I\u0011Q\u0010\u000bA\u0002\u0013E\u0011Q\r\u0005\n\u0003\u007f\"\u0002\u0019!C\t\u0003\u0003C\u0001\"!\"\u0015A\u0003&\u0011q\r\u0005\t\u0003\u0013#\u0002\u0015)\u0003\u0002\f\"A\u00111\u0013\u000b\u0005\u0002\u0001\u000b)\nC\u0004\u0002\u0018R!I!!'\t\u000f\u0005\u0005F\u0003\"\u0011\u0002$\"9\u0011Q\u0015\u000b\u0005B\u0005\u001d\u0006bBAX)\u0011\u0005\u00131\u0015\u0005\b\u0003c#B\u0011IAT\u0011\u001d\t\u0019\f\u0006C!\u0003kCq!!0\u0015\t\u0003\n)\fC\u0004\u0002@R!\t%!1\t\u000f\u0005%G\u0003\"\u0001\u0002L\"9\u0011\u0011\u001b\u000b\u0005B\u0005M\u0007bBAl)\u0011\u0005\u0013\u0011\u001c\u0005\b\u0003?$B\u0011IAq\u0011\u001d\t9\u000f\u0006C!\u0003SD\u0001\"!<\u0015A\u0013%\u0011q\u001e\u0005\b\u0003k$B\u0011AA|\u0011\u001d\ti\u0010\u0006C\u0001\u0003\u007fDqAa\u0001\u0015\t\u0003\u0011)A\u0002\u0004\u0003\u000eQA!q\u0002\u0005\b\u0003\u000f\u0011D\u0011\u0001B\r\u0011\u001d\u0011iB\rC!\u0005?AqA!\f\u0015\t\u0003\u0012y\u0003C\u0004\u00034Q!\tE!\u000e\t\u000f\teB\u0003\"\u0011\u0003<!9!1\n\u000b\u0005B\t5\u0003b\u0002B))\u0011\u0005#1\u000b\u0005\b\u0005G\"B\u0011\tB3\u0011\u001d\u0011)\b\u0006C!\u0005oBqA!\u001f\u0015\t\u0013\u0011Y\bC\u0004\u0003\u0012R!IAa%\t\u0011\t}E\u0003)C)\u0005C\u000bQ\u0002T5oW\u0016$\u0007*Y:i\u001b\u0006\u0004(BA!C\u0003\u001diW\u000f^1cY\u0016T!a\u0011#\u0002\u0015\r|G\u000e\\3di&|gNC\u0001F\u0003\u0015\u00198-\u00197b\u0007\u0001\u0001\"\u0001S\u0001\u000e\u0003\u0001\u0013Q\u0002T5oW\u0016$\u0007*Y:i\u001b\u0006\u00048cA\u0001L\u001fB\u0011A*T\u0007\u0002\t&\u0011a\n\u0012\u0002\u0007\u0003:L(+\u001a4\u0011\u0007A\u000b6+D\u0001C\u0013\t\u0011&I\u0001\u0006NCB4\u0015m\u0019;pef\u0004\"\u0001\u0013\u000b\u0016\u0007U[Vm\u0005\u0005\u0015-\u001eTgn\u001e>~!\u0011Au+\u00173\n\u0005a\u0003%aC!cgR\u0014\u0018m\u0019;NCB\u0004\"AW.\r\u0001\u0011)A\f\u0006b\u0001;\n\t1*\u0005\u0002_CB\u0011AjX\u0005\u0003A\u0012\u0013qAT8uQ&tw\r\u0005\u0002ME&\u00111\r\u0012\u0002\u0004\u0003:L\bC\u0001.f\t\u00151GC1\u0001^\u0005\u00051\u0006\u0003\u0002%i3\u0012L!!\u001b!\u0003\rM+\u0017/T1q!\u0019A5.\u00173T[&\u0011A\u000e\u0011\u0002\u0007\u001b\u0006\u0004x\n]:\u0011\t!#\u0012\f\u001a\t\u0006!>\fH/\\\u0005\u0003a\n\u0013!d\u0015;sS\u000e$x\n\u001d;j[&TX\rZ%uKJ\f'\r\\3PaN\u0004B\u0001\u0014:ZI&\u00111\u000f\u0012\u0002\u0007)V\u0004H.\u001a\u001a\u0011\u0005!+\u0018B\u0001<A\u0005!IE/\u001a:bE2,\u0007C\u0002)y3\u0012\u001cV.\u0003\u0002z\u0005\n)2\u000b\u001e:jGR|\u0005\u000f^5nSj,G-T1q\u001fB\u001c\bC\u0002)|3\u0012\u001cF/\u0003\u0002}\u0005\n\u0011R*\u00199GC\u000e$xN]=EK\u001a\fW\u000f\u001c;t!\rq\u00181A\u0007\u0002\u007f*\u0019\u0011\u0011\u0001\"\u0002\u000f\u001d,g.\u001a:jG&\u0019\u0011QA@\u0003'\u0011+g-Y;miN+'/[1mSj\f'\r\\3\u0002\rqJg.\u001b;?)\u0005i\u0017AC7ba\u001a\u000b7\r^8ssV\tqJA\u0003F]R\u0014\u0018\u0010E\u0003\u0002\u0014\u0019IFM\u0004\u0002I\u0001\tYA*\u001b8lK\u0012,e\u000e\u001e:z+\u0019\tI\"a\t\u0002,M!aaSA\u000e!\u001dA\u0015QDA\u0011\u0003KI1!a\bA\u0005%A\u0015m\u001d5F]R\u0014\u0018\u0010E\u0002[\u0003G!Q\u0001\u0018\u0004C\u0002u\u0003r!a\n\u0007\u0003C\tI#D\u0001\u0002!\rQ\u00161\u0006\u0003\u0006M\u001a\u0011\r!X\u0001\u0004W\u0016LXCAA\u0011\u0003\u0011YW-\u001f\u0011\u0002\u000bY\fG.^3\u0016\u0005\u0005%\u0012!\u0003<bYV,w\fJ3r)\u0011\tY$!\u0011\u0011\u00071\u000bi$C\u0002\u0002@\u0011\u0013A!\u00168ji\"I\u00111\t\u0006\u0002\u0002\u0003\u0007\u0011\u0011F\u0001\u0004q\u0012\n\u0014A\u0002<bYV,\u0007\u0005\u0006\u0004\u0002&\u0005%\u00131\n\u0005\b\u0003[a\u0001\u0019AA\u0011\u0011\u001d\t\u0019\u0004\u0004a\u0001\u0003S\tq!Z1sY&,'/\u0006\u0002\u0002&\u0005YQ-\u0019:mS\u0016\u0014x\fJ3r)\u0011\tY$!\u0016\t\u0013\u0005\rc\"!AA\u0002\u0005\u0015\u0012\u0001C3be2LWM\u001d\u0011\u0002\u000b1\fG/\u001a:\u0002\u00131\fG/\u001a:`I\u0015\fH\u0003BA\u001e\u0003?B\u0011\"a\u0011\u0012\u0003\u0003\u0005\r!!\n\u0002\r1\fG/\u001a:!\u0003-yf-\u001b:ti\u0016sGO]=\u0016\u0005\u0005\u001d\u0004cAA5/5\tA#\u0001\u0006gSJ\u001cH/\u00128uef\faBZ5sgR,e\u000e\u001e:z?\u0012*\u0017\u000f\u0006\u0003\u0002<\u0005E\u0004\"CA\"5\u0005\u0005\t\u0019AA4\u0003-1\u0017N]:u\u000b:$(/\u001f\u0011)\u0007m\t9\bE\u0002M\u0003sJ1!a\u001fE\u0005%!(/\u00198tS\u0016tG/A\u0005mCN$XI\u001c;ss\u0006iA.Y:u\u000b:$(/_0%KF$B!a\u000f\u0002\u0004\"I\u00111I\u000f\u0002\u0002\u0003\u0007\u0011qM\u0001\u000bY\u0006\u001cH/\u00128uef\u0004\u0003f\u0001\u0010\u0002x\u0005)A/\u00192mKB9\u0001*!$ZI\u0006\u001d\u0014bAAH\u0001\nI\u0001*Y:i)\u0006\u0014G.\u001a\u0015\u0004?\u0005]\u0014\u0001C4fiR\u000b'\r\\3\u0016\u0005\u0005-\u0015\u0001\u00048fo\"\u000b7\u000f\u001b+bE2,WCAAN%\u0015\tijSAF\r\u0019\ty*\t\u0001\u0002\u001c\naAH]3gS:,W.\u001a8u}\u0005!A.Y:u+\u0005\t\u0018A\u00037bgR|\u0005\u000f^5p]V\u0011\u0011\u0011\u0016\t\u0005\u0019\u0006-\u0016/C\u0002\u0002.\u0012\u0013aa\u00149uS>t\u0017\u0001\u00025fC\u0012\f!\u0002[3bI>\u0003H/[8o\u0003\u0011\u0019\u0018N_3\u0016\u0005\u0005]\u0006c\u0001'\u0002:&\u0019\u00111\u0018#\u0003\u0007%sG/A\u0005l]><hnU5{K\u00069\u0011n]#naRLXCAAb!\ra\u0015QY\u0005\u0004\u0003\u000f$%a\u0002\"p_2,\u0017M\\\u0001\u0004O\u0016$H\u0003BAg\u0003\u001f\u0004B\u0001TAVI\"1\u0011QF\u0015A\u0002e\u000b\u0001bY8oi\u0006Lgn\u001d\u000b\u0005\u0003\u0007\f)\u000e\u0003\u0004\u0002.)\u0002\r!W\u0001\u0004aV$HCBAg\u00037\fi\u000e\u0003\u0004\u0002.-\u0002\r!\u0017\u0005\u0007\u0003gY\u0003\u0019\u00013\u0002\rU\u0004H-\u0019;f)\u0019\tY$a9\u0002f\"1\u0011Q\u0006\u0017A\u0002eCa!a\r-\u0001\u0004!\u0017A\u0002:f[>4X\r\u0006\u0003\u0002N\u0006-\bBBA\u0017[\u0001\u0007\u0011,A\u0004sK6|g/\u001a\u0019\u0015\u0007\u0011\f\t\u0010C\u0004\u0002t:\u0002\r!a\u001a\u0002\u0003\u0015\fa!\u00193e\u001f:,G\u0003BA5\u0003sDa!a?0\u0001\u0004\t\u0018AA6w\u0003-\u0019XO\u0019;sC\u000e$xJ\\3\u0015\t\u0005%$\u0011\u0001\u0005\u0007\u0003[\u0001\u0004\u0019A-\u0002\u0011%$XM]1u_J,\"Aa\u0002\u0011\tA\u0013I!]\u0005\u0004\u0005\u0017\u0011%\u0001C%uKJ\fGo\u001c:\u0003\u00191Kgn[3e\u0017\u0016L8+\u001a;\u0014\u0007I\u0012\t\u0002\u0005\u0003\u0002j\tM\u0011\u0002\u0002B\u000b\u0005/\u0011aaS3z'\u0016$\u0018B\u00017C)\t\u0011Y\u0002E\u0002\u0002jI\nq\"\u001b;fe\u0006\u0014G.\u001a$bGR|'/_\u000b\u0003\u0005C\u0001R\u0001\u0015B\u0012\u0005OI1A!\nC\u0005=IE/\u001a:bE2,g)Y2u_JL\bc\u0001)\u0003*%\u0019!1\u0006\"\u0003\u0007M+G/\u0001\u0004lKf\u001cV\r^\u000b\u0003\u0005c\u0001B\u0001\u0015B\u00153\u0006a1.Z=t\u0013R,'/\u0019;peV\u0011!q\u0007\t\u0005!\n%\u0011,\u0001\u0006va\u0012\fG/Z,ji\"$BA!\u0010\u0003JQ!\u0011Q\u001aB \u0011\u001d\u0011\te\u000ea\u0001\u0005\u0007\n\u0011C]3nCB\u0004\u0018N\\4Gk:\u001cG/[8o!\u001da%QIAg\u0003\u001bL1Aa\u0012E\u0005%1UO\\2uS>t\u0017\u0007\u0003\u0004\u0002.]\u0002\r!W\u0001\u000fm\u0006dW/Z:Ji\u0016\u0014\u0018\r^8s+\t\u0011y\u0005\u0005\u0003Q\u0005\u0013!\u0017a\u00024pe\u0016\f7\r[\u000b\u0005\u0005+\u0012y\u0006\u0006\u0003\u0002<\t]\u0003b\u0002B-s\u0001\u0007!1L\u0001\u0002MB1AJ!\u0012r\u0005;\u00022A\u0017B0\t\u0019\u0011\t'\u000fb\u0001;\n\tQ+\u0001\u0007g_J,\u0017m\u00195F]R\u0014\u00180\u0006\u0003\u0003h\tMD\u0003BA\u001e\u0005SBqA!\u0017;\u0001\u0004\u0011Y\u0007E\u0004M\u0005[JFM!\u001d\n\u0007\t=DIA\u0005Gk:\u001cG/[8oeA\u0019!La\u001d\u0005\r\t\u0005$H1\u0001^\u0003\u0015\u0019G.Z1s)\t\tY$A\u0006xe&$Xm\u00142kK\u000e$H\u0003BA\u001e\u0005{BqAa =\u0001\u0004\u0011\t)A\u0002pkR\u0004BAa!\u0003\u000e6\u0011!Q\u0011\u0006\u0005\u0005\u000f\u0013I)\u0001\u0002j_*\u0011!1R\u0001\u0005U\u00064\u0018-\u0003\u0003\u0003\u0010\n\u0015%AE(cU\u0016\u001cGoT;uaV$8\u000b\u001e:fC6\f!B]3bI>\u0013'.Z2u)\u0011\tYD!&\t\u000f\t]U\b1\u0001\u0003\u001a\u0006\u0011\u0011N\u001c\t\u0005\u0005\u0007\u0013Y*\u0003\u0003\u0003\u001e\n\u0015%!E(cU\u0016\u001cG/\u00138qkR\u001cFO]3b[\u0006a1\u000f\u001e:j]\u001e\u0004&/\u001a4jqV\u0011!1\u0015\t\u0005\u0005K\u0013Y+\u0004\u0002\u0003(*!!\u0011\u0016BE\u0003\u0011a\u0017M\\4\n\t\t5&q\u0015\u0002\u0007'R\u0014\u0018N\\4\u0015\u0003\u001d\u000bQ!Z7qif,bA!.\u0003<\n}VC\u0001B\\!\u0019AEC!/\u0003>B\u0019!La/\u0005\u000bq\u001b!\u0019A/\u0011\u0007i\u0013y\fB\u0003g\u0007\t\u0007Q,\u0001\u0003ge>lWC\u0002Bc\u0005\u0017\u0014y\r\u0006\u0003\u0003H\nE\u0007C\u0002%\u0015\u0005\u0013\u0014i\rE\u0002[\u0005\u0017$Q\u0001\u0018\u0003C\u0002u\u00032A\u0017Bh\t\u00151GA1\u0001^\u0011\u001d\u0011\u0019\u000e\u0002a\u0001\u0005+\f!!\u001b;\u0011\u000bA\u00139Na7\n\u0007\te'I\u0001\u0007Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cW\r\u0005\u0004Me\n%'QZ\u0001\u000b]\u0016<()^5mI\u0016\u0014XC\u0002Bq\u0005[\u0014\t0\u0006\u0002\u0003dB9\u0001J!:\u0003j\nM\u0018b\u0001Bt\u0001\nyqI]8xC\ndWMQ;jY\u0012,'\u000f\u0005\u0004Me\n-(q\u001e\t\u00045\n5H!\u0002/\u0006\u0005\u0004i\u0006c\u0001.\u0003r\u0012)a-\u0002b\u0001;B1\u0001\n\u0006Bv\u0005_\fAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"A!?\u0011\t\t\u0015&1`\u0005\u0005\u0005{\u00149K\u0001\u0004PE*,7\r\u001e\u0015\b\u0003\r\u0005\u00111GB\u0004!\ra51A\u0005\u0004\u0007\u000b!%\u0001E*fe&\fGNV3sg&|g.V%E=\u0005\u0019\u0001f\u0002\u0001\u0004\u0002\u0005M2q\u0001")
public class LinkedHashMap<K, V>
extends AbstractMap<K, V>
implements SeqMap<K, V>,
StrictOptimizedMapOps<K, V, LinkedHashMap, LinkedHashMap<K, V>>,
DefaultSerializable {
    private transient LinkedEntry<K, V> firstEntry = null;
    private transient LinkedEntry<K, V> lastEntry = null;
    private transient HashTable<K, V, LinkedEntry<K, V>> table = new HashTable<K, V, LinkedEntry<K, V>>(this){
        private int _loadFactor;
        private HashEntry<K, LinkedEntry<K, V>>[] table;
        private int tableSize;
        private int threshold;
        private int[] sizemap;
        private int seedvalue;
        private final /* synthetic */ LinkedHashMap $outer;

        public final int size() {
            return HashTable.size$(this);
        }

        public int tableSizeSeed() {
            return HashTable.tableSizeSeed$(this);
        }

        public int initialSize() {
            return HashTable.initialSize$(this);
        }

        public void init(ObjectInputStream in, Function0<LinkedEntry<K, V>> readEntry2) {
            HashTable.init$(this, in, readEntry2);
        }

        public void serializeTo(ObjectOutputStream out, Function1<LinkedEntry<K, V>, BoxedUnit> writeEntry) {
            HashTable.serializeTo$(this, out, writeEntry);
        }

        public final HashEntry findEntry(Object key) {
            return HashTable.findEntry$(this, key);
        }

        public final HashEntry findEntry0(Object key, int h2) {
            return HashTable.findEntry0$(this, key, h2);
        }

        public final void addEntry(HashEntry e) {
            HashTable.addEntry$(this, e);
        }

        public final void addEntry0(HashEntry e, int h2) {
            HashTable.addEntry0$(this, e, h2);
        }

        public HashEntry findOrAddEntry(Object key, Object value) {
            return HashTable.findOrAddEntry$(this, key, value);
        }

        public final HashEntry removeEntry(Object key) {
            return HashTable.removeEntry$(this, key);
        }

        public final HashEntry removeEntry0(Object key, int h2) {
            return HashTable.removeEntry0$(this, key, h2);
        }

        public Iterator<LinkedEntry<K, V>> entriesIterator() {
            return HashTable.entriesIterator$(this);
        }

        public void clearTable() {
            HashTable.clearTable$(this);
        }

        public final void nnSizeMapAdd(int h2) {
            HashTable.nnSizeMapAdd$(this, h2);
        }

        public final void nnSizeMapRemove(int h2) {
            HashTable.nnSizeMapRemove$(this, h2);
        }

        public final void nnSizeMapReset(int tableLength) {
            HashTable.nnSizeMapReset$(this, tableLength);
        }

        public final int totalSizeMapBuckets() {
            return HashTable.totalSizeMapBuckets$(this);
        }

        public final int calcSizeMapSize(int tableLength) {
            return HashTable.calcSizeMapSize$(this, tableLength);
        }

        public void sizeMapInit(int tableLength) {
            HashTable.sizeMapInit$(this, tableLength);
        }

        public final void sizeMapInitAndRebuild() {
            HashTable.sizeMapInitAndRebuild$(this);
        }

        public void printSizeMap() {
            HashTable.printSizeMap$(this);
        }

        public final void sizeMapDisable() {
            HashTable.sizeMapDisable$(this);
        }

        public final boolean isSizeMapDefined() {
            return HashTable.isSizeMapDefined$(this);
        }

        public boolean alwaysInitSizeMap() {
            return HashTable.alwaysInitSizeMap$(this);
        }

        public boolean elemEquals(K key1, K key2) {
            return HashTable.elemEquals$(this, key1, key2);
        }

        public final int index(int hcode) {
            return HashTable.index$(this, hcode);
        }

        public final int sizeMapBucketBitSize() {
            return HashTable.HashUtils.sizeMapBucketBitSize$(this);
        }

        public final int sizeMapBucketSize() {
            return HashTable.HashUtils.sizeMapBucketSize$(this);
        }

        public int elemHashCode(K key) {
            return HashTable.HashUtils.elemHashCode$(this, key);
        }

        public final int improve(int hcode, int seed) {
            return HashTable.HashUtils.improve$(this, hcode, seed);
        }

        public int _loadFactor() {
            return this._loadFactor;
        }

        public void _loadFactor_$eq(int x$1) {
            this._loadFactor = x$1;
        }

        public HashEntry<K, LinkedEntry<K, V>>[] table() {
            return this.table;
        }

        public void table_$eq(HashEntry<K, LinkedEntry<K, V>>[] x$1) {
            this.table = x$1;
        }

        public int tableSize() {
            return this.tableSize;
        }

        public void tableSize_$eq(int x$1) {
            this.tableSize = x$1;
        }

        public int threshold() {
            return this.threshold;
        }

        public void threshold_$eq(int x$1) {
            this.threshold = x$1;
        }

        public int[] sizemap() {
            return this.sizemap;
        }

        public void sizemap_$eq(int[] x$1) {
            this.sizemap = x$1;
        }

        public int seedvalue() {
            return this.seedvalue;
        }

        public void seedvalue_$eq(int x$1) {
            this.seedvalue = x$1;
        }

        public LinkedEntry<K, V> createNewEntry(K key, V value) {
            LinkedEntry<K, V> e = new LinkedEntry<K, V>(key, value);
            if (this.$outer.firstEntry() == null) {
                this.$outer.firstEntry_$eq(e);
            } else {
                this.$outer.lastEntry().later_$eq(e);
                e.earlier_$eq(this.$outer.lastEntry());
            }
            this.$outer.lastEntry_$eq(e);
            return e;
        }

        public <U> void foreachEntry(Function1<LinkedEntry<K, V>, U> f) {
            for (LinkedEntry<K, V> cur = this.$outer.firstEntry(); cur != null; cur = cur.later()) {
                f.apply(cur);
            }
        }
        {
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            HashTable.$init$(this);
        }
    };

    public static <K, V> GrowableBuilder<Tuple2<K, V>, LinkedHashMap<K, V>> newBuilder() {
        return LinkedHashMap$.MODULE$.newBuilder();
    }

    public static <K, V> LinkedHashMap<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        return LinkedHashMap$.MODULE$.from((IterableOnce)it);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
    }

    @Override
    public IterableOps map(Function1 f) {
        return StrictOptimizedMapOps.map$(this, f);
    }

    @Override
    public IterableOps flatMap(Function1 f) {
        return StrictOptimizedMapOps.flatMap$(this, f);
    }

    @Override
    public IterableOps concat(IterableOnce suffix) {
        return StrictOptimizedMapOps.concat$(this, suffix);
    }

    @Override
    public IterableOps collect(PartialFunction pf) {
        return StrictOptimizedMapOps.collect$(this, pf);
    }

    @Override
    public IterableOps $plus(Tuple2 elem1, Tuple2 elem2, Seq elems) {
        return StrictOptimizedMapOps.$plus$(this, elem1, elem2, elems);
    }

    @Override
    public Tuple2<LinkedHashMap<K, V>, LinkedHashMap<K, V>> partition(Function1<Tuple2<K, V>, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<LinkedHashMap<K, V>, LinkedHashMap<K, V>> span(Function1<Tuple2<K, V>, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<Iterable<A1>, Iterable<A2>> unzip(Function1<Tuple2<K, V>, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<Iterable<A1>, Iterable<A2>, Iterable<A3>> unzip3(Function1<Tuple2<K, V>, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<Tuple2<K, V>, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<Tuple2<K, V>, IterableOnce<B>> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatMap$(this, b, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedConcat(IterableOnce<B> that, Builder<B, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedConcat$(this, that, b);
    }

    @Override
    public Object collect(PartialFunction pf) {
        return StrictOptimizedIterableOps.collect$(this, pf);
    }

    @Override
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<Tuple2<K, V>, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<Tuple2<K, V>, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<Tuple2<K, V>, B>, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedZip$(this, that, b);
    }

    @Override
    public Object zipWithIndex() {
        return StrictOptimizedIterableOps.zipWithIndex$(this);
    }

    @Override
    public Object scanLeft(Object z, Function2 op) {
        return StrictOptimizedIterableOps.scanLeft$(this, z, op);
    }

    @Override
    public Object filter(Function1 pred) {
        return StrictOptimizedIterableOps.filter$(this, pred);
    }

    @Override
    public Object filterNot(Function1 pred) {
        return StrictOptimizedIterableOps.filterNot$(this, pred);
    }

    @Override
    public Object filterImpl(Function1 pred, boolean isFlipped) {
        return StrictOptimizedIterableOps.filterImpl$(this, pred, isFlipped);
    }

    @Override
    public <A1, A2> Tuple2<Iterable<A1>, Iterable<A2>> partitionMap(Function1<Tuple2<K, V>, Either<A1, A2>> f) {
        return StrictOptimizedIterableOps.partitionMap$(this, f);
    }

    @Override
    public Object tapEach(Function1 f) {
        return StrictOptimizedIterableOps.tapEach$(this, f);
    }

    @Override
    public Object takeRight(int n) {
        return StrictOptimizedIterableOps.takeRight$(this, n);
    }

    @Override
    public Object dropRight(int n) {
        return StrictOptimizedIterableOps.dropRight$(this, n);
    }

    @Override
    public MapFactory<LinkedHashMap> mapFactory() {
        return LinkedHashMap$.MODULE$;
    }

    public LinkedEntry<K, V> _firstEntry() {
        return this.firstEntry();
    }

    public LinkedEntry<K, V> firstEntry() {
        return this.firstEntry;
    }

    public void firstEntry_$eq(LinkedEntry<K, V> x$1) {
        this.firstEntry = x$1;
    }

    public LinkedEntry<K, V> lastEntry() {
        return this.lastEntry;
    }

    public void lastEntry_$eq(LinkedEntry<K, V> x$1) {
        this.lastEntry = x$1;
    }

    public HashTable<K, V, LinkedEntry<K, V>> getTable() {
        return this.table;
    }

    private HashTable<K, V, LinkedEntry<K, V>> newHashTable() {
        return new /* invalid duplicate definition of identical inner class */;
    }

    @Override
    public Tuple2<K, V> last() {
        if (this.size() > 0) {
            return new Tuple2<K, V>(this.lastEntry().key(), this.lastEntry().value());
        }
        throw new NoSuchElementException("Cannot call .last on empty LinkedHashMap");
    }

    @Override
    public Option<Tuple2<K, V>> lastOption() {
        if (this.size() > 0) {
            return new Some<Tuple2<K, V>>(new Tuple2<K, V>(this.lastEntry().key(), this.lastEntry().value()));
        }
        return None$.MODULE$;
    }

    @Override
    public Tuple2<K, V> head() {
        if (this.size() > 0) {
            return new Tuple2<K, V>(this.firstEntry().key(), this.firstEntry().value());
        }
        throw new NoSuchElementException("Cannot call .head on empty LinkedHashMap");
    }

    @Override
    public Option<Tuple2<K, V>> headOption() {
        if (this.size() > 0) {
            return new Some<Tuple2<K, V>>(new Tuple2<K, V>(this.firstEntry().key(), this.firstEntry().value()));
        }
        return None$.MODULE$;
    }

    @Override
    public int size() {
        return this.table.tableSize();
    }

    @Override
    public int knownSize() {
        return this.size();
    }

    @Override
    public boolean isEmpty() {
        return this.table.tableSize() == 0;
    }

    @Override
    public Option<V> get(K key) {
        LinkedEntry<K, V> e = this.table.findEntry(key);
        if (e == null) {
            return None$.MODULE$;
        }
        return new Some<V>(e.value());
    }

    @Override
    public boolean contains(K key) {
        if (this.getClass() == LinkedHashMap.class) {
            return this.table.findEntry(key) != null;
        }
        return MapOps.contains$(this, key);
    }

    @Override
    public Option<V> put(K key, V value) {
        LinkedEntry<K, V> e = this.table.findOrAddEntry(key, value);
        if (e == null) {
            return None$.MODULE$;
        }
        V v = e.value();
        e.value_$eq(value);
        return new Some<V>(v);
    }

    @Override
    public void update(K key, V value) {
        LinkedEntry<K, V> e = this.table.findOrAddEntry(key, value);
        if (e != null) {
            e.value_$eq(value);
            return;
        }
    }

    @Override
    public Option<V> remove(K key) {
        LinkedEntry<K, V> e = this.table.removeEntry(key);
        if (e == null) {
            return None$.MODULE$;
        }
        return new Some<V>(this.remove0(e));
    }

    private V remove0(LinkedEntry<K, V> e) {
        if (e.earlier() == null) {
            this.firstEntry_$eq(e.later());
        } else {
            e.earlier().later_$eq(e.later());
        }
        if (e.later() == null) {
            this.lastEntry_$eq(e.earlier());
        } else {
            e.later().earlier_$eq(e.earlier());
        }
        e.earlier_$eq(null);
        e.later_$eq(null);
        return e.value();
    }

    public LinkedHashMap<K, V> addOne(Tuple2<K, V> kv) {
        this.put(kv._1(), kv._2());
        return this;
    }

    public LinkedHashMap<K, V> subtractOne(K key) {
        this.remove(key);
        return this;
    }

    @Override
    public Iterator<Tuple2<K, V>> iterator() {
        return new AbstractIterator<Tuple2<K, V>>(this){
            private LinkedEntry<K, V> cur;

            public boolean hasNext() {
                return this.cur != null;
            }

            public Tuple2<K, V> next() {
                if (this.hasNext()) {
                    Tuple2<K, V> res = new Tuple2<K, V>(this.cur.key(), this.cur.value());
                    this.cur = this.cur.later();
                    return res;
                }
                return (Tuple2)((Object)Iterator$.scala$collection$Iterator$$_empty.next());
            }
            {
                this.cur = $outer.firstEntry();
            }
        };
    }

    @Override
    public Set<K> keySet() {
        return new LinkedKeySet(this);
    }

    @Override
    public Iterator<K> keysIterator() {
        return new AbstractIterator<K>(this){
            private LinkedEntry<K, V> cur;

            public boolean hasNext() {
                return this.cur != null;
            }

            public K next() {
                if (this.hasNext()) {
                    K res = this.cur.key();
                    this.cur = this.cur.later();
                    return res;
                }
                return (K)Iterator$.scala$collection$Iterator$$_empty.next();
            }
            {
                this.cur = $outer.firstEntry();
            }
        };
    }

    @Override
    public Option<V> updateWith(K key, Function1<Option<V>, Option<V>> remappingFunction) {
        int keyIndex = this.table.index(this.table.elemHashCode(key));
        LinkedEntry entry = this.table.findEntry0(key, keyIndex);
        Option previousValue = entry == null ? None$.MODULE$ : new Some<V>(entry.value());
        Option<V> nextValue = remappingFunction.apply(previousValue);
        Tuple2<None$, Option<V>> tuple2 = new Tuple2<None$, Option<V>>((None$)previousValue, nextValue);
        Option option = previousValue;
        if (!None$.MODULE$.equals(option) || !None$.MODULE$.equals(nextValue)) {
            if ((Option)previousValue instanceof Some && None$.MODULE$.equals(nextValue)) {
                this.remove0(entry);
                this.table.removeEntry0(key, keyIndex);
            } else {
                Option option2 = previousValue;
                if (None$.MODULE$.equals(option2) && nextValue instanceof Some) {
                    Object value = ((Some)nextValue).value();
                    this.table.addEntry0(this.table.createNewEntry(key, value), keyIndex);
                } else if ((Option)previousValue instanceof Some && nextValue instanceof Some) {
                    Object value = ((Some)nextValue).value();
                    entry.value_$eq(value);
                } else {
                    throw new MatchError(tuple2);
                }
            }
        }
        return nextValue;
    }

    @Override
    public Iterator<V> valuesIterator() {
        return new AbstractIterator<V>(this){
            private LinkedEntry<K, V> cur;

            public boolean hasNext() {
                return this.cur != null;
            }

            public V next() {
                if (this.hasNext()) {
                    V res = this.cur.value();
                    this.cur = this.cur.later();
                    return res;
                }
                return (V)Iterator$.scala$collection$Iterator$$_empty.next();
            }
            {
                this.cur = $outer.firstEntry();
            }
        };
    }

    @Override
    public <U> void foreach(Function1<Tuple2<K, V>, U> f) {
        for (LinkedEntry<K, V> cur = this.firstEntry(); cur != null; cur = cur.later()) {
            f.apply(new Tuple2<K, V>(cur.key(), cur.value()));
        }
    }

    @Override
    public <U> void foreachEntry(Function2<K, V, U> f) {
        for (LinkedEntry<K, V> cur = this.firstEntry(); cur != null; cur = cur.later()) {
            f.apply(cur.key(), cur.value());
        }
    }

    @Override
    public void clear() {
        this.table.clearTable();
        this.firstEntry_$eq(null);
        this.lastEntry_$eq(null);
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        this.table.serializeTo(out, (Function1<LinkedEntry, Object> & Serializable)entry -> {
            LinkedHashMap.$anonfun$writeObject$1(out, entry);
            return BoxedUnit.UNIT;
        });
    }

    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        this.table = new /* invalid duplicate definition of identical inner class */;
        this.table.init(in, (Function0<LinkedEntry> & Serializable)() -> $this.table.createNewEntry(in.readObject(), in.readObject()));
    }

    @Override
    public String stringPrefix() {
        return "LinkedHashMap";
    }

    public static final /* synthetic */ void $anonfun$writeObject$1(ObjectOutputStream out$1, LinkedEntry entry) {
        out$1.writeObject(entry.key());
        out$1.writeObject(entry.value());
    }

    public static final class LinkedEntry<K, V>
    implements HashEntry<K, LinkedEntry<K, V>> {
        private final K key;
        private V value;
        private LinkedEntry<K, V> earlier;
        private LinkedEntry<K, V> later;
        private LinkedEntry<K, V> next;

        @Override
        public LinkedEntry<K, V> next() {
            return this.next;
        }

        @Override
        public void next_$eq(LinkedEntry<K, V> x$1) {
            this.next = x$1;
        }

        @Override
        public K key() {
            return this.key;
        }

        public V value() {
            return this.value;
        }

        public void value_$eq(V x$1) {
            this.value = x$1;
        }

        public LinkedEntry<K, V> earlier() {
            return this.earlier;
        }

        public void earlier_$eq(LinkedEntry<K, V> x$1) {
            this.earlier = x$1;
        }

        public LinkedEntry<K, V> later() {
            return this.later;
        }

        public void later_$eq(LinkedEntry<K, V> x$1) {
            this.later = x$1;
        }

        public LinkedEntry(K key, V value) {
            this.key = key;
            this.value = value;
            this.earlier = null;
            this.later = null;
        }
    }

    public class LinkedKeySet
    extends MapOps.KeySet {
        @Override
        public IterableFactory<Set> iterableFactory() {
            return LinkedHashSet$.MODULE$;
        }

        public /* synthetic */ LinkedHashMap scala$collection$mutable$LinkedHashMap$LinkedKeySet$$$outer() {
            return (LinkedHashMap)this.$outer;
        }

        public LinkedKeySet(LinkedHashMap $outer) {
        }
    }
}

