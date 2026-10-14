/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.util.ConcurrentModificationException;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterator;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.MapFactory;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.generic.DefaultSerializable;
import scala.collection.mutable.AbstractMap;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Iterable;
import scala.collection.mutable.OpenHashMap$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.Statics;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\r\u001dq!B A\u0011\u00039e!B%A\u0011\u0003Q\u0005bBA\u0002\u0003\u0011\u0005!1\u0015\u0005\b\u0005K\u000bA\u0011\u0001BT\u0011\u001d\u0011I,\u0001C\u0001\u0005wCqA!6\u0002\t\u0003\u00119N\u0002\u0004\u0002\u0010\u00051\u0011\u0011\u0003\u0005\u000b\u0003+1!\u00111A\u0005\u0002\u0005]\u0001BCA\u000f\r\t\u0005\r\u0011\"\u0001\u0002 !Q\u00111\u0006\u0004\u0003\u0002\u0003\u0006K!!\u0007\t\u0015\u00055bA!a\u0001\n\u0003\ty\u0003\u0003\u0006\u00022\u0019\u0011\t\u0019!C\u0001\u0003gA\u0011\"a\u000e\u0007\u0005\u0003\u0005\u000b\u0015\u0002@\t\u0015\u0005ebA!a\u0001\n\u0003\tY\u0004\u0003\u0006\u0002H\u0019\u0011\t\u0019!C\u0001\u0003\u0013B!\"!\u0014\u0007\u0005\u0003\u0005\u000b\u0015BA\u001f\u0011\u001d\t\u0019A\u0002C\u0001\u0003\u001fB\u0011B!<\u0002\u0003\u0003%IAa<\u0007\t%\u0003\u0005\u0001\u0016\u0005\t{J\u0011\t\u0011)A\u0005}\"9\u00111\u0001\n\u0005\u0002\u0005\u0015QABA\u0005%\u0011\tY\u0001C\u0004\u0002\u0004I!\t!a\u0017\t\u000f\u0005u#\u0003\"\u0011\u0002`!9\u0011\u0011\r\n!\u0002\u0013q\bbBA2%\u0001\u0006KA \u0005\t\u0003K\u0012\u0002\u0015)\u0003\u0002h!9\u0011\u0011\u000f\n!B\u0013q\b\"DA:%\u0011\u0005\tQ!A\u0001B\u0003&a\u0010C\u0004\u0002vI\u0001\u000b\u0015\u0002@\t\u000f\u0005]$\u0003\"\u0011\u00020!9\u0011\u0011\u0010\n\u0005B\u0005=\u0002BDA>%\u0011\u0005\tQ!A\u0001B\u0013%\u0011Q\u0010\u0005\b\u0003\u0007\u0013B\u0011IAC\u0011\u001d\tiI\u0005C\t\u0003\u001fC\u0001\"a%\u0013A\u0013%\u0011Q\u0013\u0005\t\u0003/\u0013\u0002\u0015\"\u0003\u0002\u001a\"9\u0011q\u0014\n\u0005B\u0005\u0005\u0006bBAT%\u0011\u0005\u0011\u0011\u0016\u0005\b\u0003\u0007\u0014B\u0011AAc\u0011\u001d\tyM\u0005C!\u0003#Dq!a4\u0013\t\u0013\tI\u000e\u0003\u0005\u0002bJ\u0001K\u0011BAr\u0011\u001d\t\tP\u0005C!\u0003gDq!a>\u0013\t\u0003\tI\u0010C\u0004\u0002~J!\t!a@\t\u000f\t\u001d!\u0003\"\u0011\u0003\n!9!Q\u0002\n\u0005B\t=aa\u0002B\n%\u0005%!Q\u0003\u0005\b\u0003\u0007\u0001D\u0011\u0001B\u0013\u0011\u001d\u0011I\u0003\rQ!\nyDqAa\u000b1A\u0003%a\u0010\u0003\u0005\u0003.A\u0002K\u0011BAK\u0011\u001d\u0011y\u0003\rC\u0001\u0003\u000bCqA!\r1\t\u0003\u0011\u0019\u0004C\u0004\u00036A2\tBa\u000e\t\u000f\tu\"\u0003\"\u0011\u0002\\!9!q\b\n\u0005B\t\u0005\u0003b\u0002B+%\u0011\u0005#q\u000b\u0005\t\u0005O\u0012\u0002\u0015\"\u0003\u0003j!9!q\u000e\n\u0005B\tE\u0004b\u0002B<%\u0011\u0005#\u0011\u0010\u0005\t\u0005\u007f\u0012\u0002\u0015\"\u0015\u0003\u0002\u0006Yq\n]3o\u0011\u0006\u001c\b.T1q\u0015\t\t%)A\u0004nkR\f'\r\\3\u000b\u0005\r#\u0015AC2pY2,7\r^5p]*\tQ)A\u0003tG\u0006d\u0017m\u0001\u0001\u0011\u0005!\u000bQ\"\u0001!\u0003\u0017=\u0003XM\u001c%bg\"l\u0015\r]\n\u0004\u0003-{\u0005C\u0001'N\u001b\u0005!\u0015B\u0001(E\u0005\u0019\te.\u001f*fMB\u0019\u0001+U*\u000e\u0003\tK!A\u0015\"\u0003\u00155\u000b\u0007OR1di>\u0014\u0018\u0010\u0005\u0002I%U\u0019QkW3\u0014\rI1vm\u001b;x!\u0011Au+\u00173\n\u0005a\u0003%aC!cgR\u0014\u0018m\u0019;NCB\u0004\"AW.\r\u0001\u0011)AL\u0005b\u0001;\n\u00191*Z=\u0012\u0005y\u000b\u0007C\u0001'`\u0013\t\u0001GIA\u0004O_RD\u0017N\\4\u0011\u00051\u0013\u0017BA2E\u0005\r\te.\u001f\t\u00035\u0016$QA\u001a\nC\u0002u\u0013QAV1mk\u0016\u0004b\u0001\u00135ZINS\u0017BA5A\u0005\u0019i\u0015\r](qgB!\u0001JE-e!\u0015\u0001FN\\9k\u0013\ti'I\u0001\u000eTiJL7\r^(qi&l\u0017N_3e\u0013R,'/\u00192mK>\u00038\u000f\u0005\u0003M_f#\u0017B\u00019E\u0005\u0019!V\u000f\u001d7feA\u0011\u0001J]\u0005\u0003g\u0002\u0013\u0001\"\u0013;fe\u0006\u0014G.\u001a\t\u0007!VLFmU9\n\u0005Y\u0014%AE'ba\u001a\u000b7\r^8ss\u0012+g-Y;miN\u0004\"\u0001_>\u000e\u0003eT!A\u001f\"\u0002\u000f\u001d,g.\u001a:jG&\u0011A0\u001f\u0002\u0014\t\u00164\u0017-\u001e7u'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\fS:LG/[1m'&TX\r\u0005\u0002M\u007f&\u0019\u0011\u0011\u0001#\u0003\u0007%sG/\u0001\u0004=S:LGO\u0010\u000b\u0004U\u0006\u001d\u0001\"B?\u0015\u0001\u0004q(!B#oiJL\b#BA\u0007\re#gB\u0001%\u0001\u0005%y\u0005/\u001a8F]R\u0014\u00180\u0006\u0004\u0002\u0014\u0005m\u0011QI\n\u0003\r-\u000b1a[3z+\t\tI\u0002E\u0002[\u00037!Q\u0001\u0018\u0004C\u0002u\u000bqa[3z?\u0012*\u0017\u000f\u0006\u0003\u0002\"\u0005\u001d\u0002c\u0001'\u0002$%\u0019\u0011Q\u0005#\u0003\tUs\u0017\u000e\u001e\u0005\n\u0003SA\u0011\u0011!a\u0001\u00033\t1\u0001\u001f\u00132\u0003\u0011YW-\u001f\u0011\u0002\t!\f7\u000f[\u000b\u0002}\u0006A\u0001.Y:i?\u0012*\u0017\u000f\u0006\u0003\u0002\"\u0005U\u0002\u0002CA\u0015\u0017\u0005\u0005\t\u0019\u0001@\u0002\u000b!\f7\u000f\u001b\u0011\u0002\u000bY\fG.^3\u0016\u0005\u0005u\u0002#\u0002'\u0002@\u0005\r\u0013bAA!\t\n1q\n\u001d;j_:\u00042AWA#\t\u00151gA1\u0001^\u0003%1\u0018\r\\;f?\u0012*\u0017\u000f\u0006\u0003\u0002\"\u0005-\u0003\"CA\u0015\u001d\u0005\u0005\t\u0019AA\u001f\u0003\u00191\u0018\r\\;fAQA\u0011\u0011KA+\u0003/\nI\u0006E\u0004\u0002T\u0019\tI\"a\u0011\u000e\u0003\u0005Aq!!\u0006\u0011\u0001\u0004\tI\u0002\u0003\u0004\u0002.A\u0001\rA \u0005\b\u0003s\u0001\u0002\u0019AA\u001f)\u0005Q\u0017AC7ba\u001a\u000b7\r^8ssV\tq*A\tbGR,\u0018\r\\%oSRL\u0017\r\\*ju\u0016\fA!\\1tW\u0006)A/\u00192mKB)A*!\u001b\u0002n%\u0019\u00111\u000e#\u0003\u000b\u0005\u0013(/Y=\u0011\u0007\u0005=T#D\u0001\u0013\u0003\u0015y6/\u001b>f\u00035\u001a8-\u00197bI\r|G\u000e\\3di&|g\u000eJ7vi\u0006\u0014G.\u001a\u0013Pa\u0016t\u0007*Y:i\u001b\u0006\u0004H\u0005\n3fY\u0016$X\rZ\u0001\t[>$7i\\;oi\u0006!1/\u001b>f\u0003%Ygn\\<o'&TX-\u0001\u0018tG\u0006d\u0017\rJ2pY2,7\r^5p]\u0012jW\u000f^1cY\u0016$s\n]3o\u0011\u0006\u001c\b.T1qI\u0011\u001a\u0018N_3`I\u0015\fH\u0003BA\u0011\u0003\u007fBa!!!!\u0001\u0004q\u0018!A:\u0002\u000f%\u001cX)\u001c9usV\u0011\u0011q\u0011\t\u0004\u0019\u0006%\u0015bAAF\t\n9!i\\8mK\u0006t\u0017A\u00025bg\"|e\rF\u0002\u007f\u0003#Ca!!\u0006#\u0001\u0004I\u0016!C4s_^$\u0016M\u00197f)\t\t\t#A\u0005gS:$\u0017J\u001c3fqR)a0a'\u0002\u001e\"1\u0011Q\u0003\u0013A\u0002eCa!!\f%\u0001\u0004q\u0018AB;qI\u0006$X\r\u0006\u0004\u0002\"\u0005\r\u0016Q\u0015\u0005\u0007\u0003+)\u0003\u0019A-\t\r\u0005eR\u00051\u0001e\u0003\u0019\tG\rZ(oKR!\u0011qNAV\u0011\u0019\tiK\na\u0001]\u0006\u00111N\u001e\u0015\fM\u0005E\u0016qWA]\u0003{\u000by\fE\u0002M\u0003gK1!!.E\u0005Q!W\r\u001d:fG\u0006$X\rZ(wKJ\u0014\u0018\u000eZ5oO\u00069Q.Z:tC\u001e,\u0017EAA^\u0003)\u000bG\rZ(oK\u0002\u001a\bn\\;mI\u0002rw\u000e\u001e\u0011cK\u0002zg/\u001a:sS\u0012$WM\u001c\u0011j]\u0002z'\u000fZ3sAQ|\u0007%\\1j]R\f\u0017N\u001c\u0011d_:\u001c\u0018n\u001d;f]\u000eL\be^5uQ\u0002\u0002X\u000f\u001e\u0018\u0002\u000bMLgnY3\"\u0005\u0005\u0005\u0017A\u0002\u001a/cEr\u0003'A\u0006tk\n$(/Y2u\u001f:,G\u0003BA8\u0003\u000fDa!!\u0006(\u0001\u0004I\u0006fC\u0014\u00022\u0006]\u00161ZA_\u0003\u007f\u000b#!!4\u0002%N,(\r\u001e:bGR|e.\u001a\u0011tQ>,H\u000e\u001a\u0011o_R\u0004#-\u001a\u0011pm\u0016\u0014(/\u001b3eK:\u0004\u0013N\u001c\u0011pe\u0012,'\u000f\t;pA5\f\u0017N\u001c;bS:\u00043m\u001c8tSN$XM\\2zA]LG\u000f\u001b\u0011sK6|g/\u001a\u0018\u0002\u0007A,H\u000f\u0006\u0004\u0002T\u0006U\u0017q\u001b\t\u0005\u0019\u0006}B\r\u0003\u0004\u0002\u0016!\u0002\r!\u0017\u0005\u0007\u0003sA\u0003\u0019\u00013\u0015\u0011\u0005M\u00171\\Ao\u0003?Da!!\u0006*\u0001\u0004I\u0006BBA\u0017S\u0001\u0007a\u0010\u0003\u0004\u0002:%\u0002\r\u0001Z\u0001\u000bI\u0016dW\r^3TY>$H\u0003BA\u0011\u0003KDq!a:+\u0001\u0004\ti'A\u0003f]R\u0014\u0018\u0010K\u0002+\u0003W\u00042\u0001TAw\u0013\r\ty\u000f\u0012\u0002\u0007S:d\u0017N\\3\u0002\rI,Wn\u001c<f)\u0011\t\u0019.!>\t\r\u0005U1\u00061\u0001Z\u0003\r9W\r\u001e\u000b\u0005\u0003'\fY\u0010\u0003\u0004\u0002\u00161\u0002\r!W\u0001\tSR,'/\u0019;peV\u0011!\u0011\u0001\t\u0005!\n\ra.C\u0002\u0003\u0006\t\u0013\u0001\"\u0013;fe\u0006$xN]\u0001\rW\u0016L8/\u0013;fe\u0006$xN]\u000b\u0003\u0005\u0017\u0001B\u0001\u0015B\u00023\u0006qa/\u00197vKNLE/\u001a:bi>\u0014XC\u0001B\t!\u0011\u0001&1\u00013\u0003'=\u0003XM\u001c%bg\"l\u0015\r]%uKJ\fGo\u001c:\u0016\t\t]!\u0011E\n\u0004a\te\u0001#\u0002)\u0003\u001c\t}\u0011b\u0001B\u000f\u0005\n\u0001\u0012IY:ue\u0006\u001cG/\u0013;fe\u0006$xN\u001d\t\u00045\n\u0005BA\u0002B\u0012a\t\u0007QLA\u0001B)\t\u00119\u0003E\u0003\u0002pA\u0012y\"A\u0003j]\u0012,\u00070A\bj]&$\u0018.\u00197N_\u0012\u001cu.\u001e8u\u0003\u001d\tGM^1oG\u0016\fq\u0001[1t\u001d\u0016DH/\u0001\u0003oKb$HC\u0001B\u0010\u0003)qW\r\u001f;SKN,H\u000e\u001e\u000b\u0005\u0005?\u0011I\u0004C\u0004\u0003<]\u0002\r!!\u001c\u0002\t9|G-Z\u0001\u0006G2|g.Z\u0001\bM>\u0014X-Y2i+\u0011\u0011\u0019E!\u0015\u0015\t\u0005\u0005\"Q\t\u0005\b\u0005\u000fJ\u0004\u0019\u0001B%\u0003\u00051\u0007C\u0002'\u0003L9\u0014y%C\u0002\u0003N\u0011\u0013\u0011BR;oGRLwN\\\u0019\u0011\u0007i\u0013\t\u0006\u0002\u0004\u0003Te\u0012\r!\u0018\u0002\u0002+\u0006aam\u001c:fC\u000eDWI\u001c;ssV!!\u0011\fB3)\u0011\t\tCa\u0017\t\u000f\t\u001d#\b1\u0001\u0003^A9AJa\u0018ZI\n\r\u0014b\u0001B1\t\nIa)\u001e8di&|gN\r\t\u00045\n\u0015DA\u0002B*u\t\u0007Q,A\u000bg_J,\u0017m\u00195V]\u0012,G.\u001a;fI\u0016sGO]=\u0015\t\u0005\u0005\"1\u000e\u0005\b\u0005\u000fZ\u0004\u0019\u0001B7!\u001da%1JA7\u0003C\t\u0001#\\1q-\u0006dW/Z:J]Bc\u0017mY3\u0015\t\u0005=$1\u000f\u0005\b\u0005\u000fb\u0004\u0019\u0001B;!\u0019a%qL-eI\u0006ia-\u001b7uKJLe\u000e\u00157bG\u0016$B!a\u001c\u0003|!9!qI\u001fA\u0002\tu\u0004c\u0002'\u0003`e#\u0017qQ\u0001\rgR\u0014\u0018N\\4Qe\u00164\u0017\u000e_\u000b\u0003\u0005\u0007\u0003BA!\"\u0003\u00106\u0011!q\u0011\u0006\u0005\u0005\u0013\u0013Y)\u0001\u0003mC:<'B\u0001BG\u0003\u0011Q\u0017M^1\n\t\tE%q\u0011\u0002\u0007'R\u0014\u0018N\\4)\u0017I\u0011)*a.\u0003\u001c\u0006u&q\u0014\t\u0004\u0019\n]\u0015b\u0001BM\t\nQA-\u001a9sK\u000e\fG/\u001a3\"\u0005\tu\u0015AW+tK\u0002B\u0015m\u001d5NCB\u0004sN\u001d\u0011p]\u0016\u0004sN\u001a\u0011uQ\u0016\u00043\u000f]3dS\u0006d\u0017N_3eAY,'o]5p]N\u0004\u0003\u0006T8oO6\u000b\u0007\u000f\f\u0011B]f\u0014VMZ'ba&\u0002\u0013N\\:uK\u0006$\u0007e\u001c4!\u001fB,g\u000eS1tQ6\u000b\u0007/\t\u0002\u0003\"\u00061!GL\u00194]A\"\u0012aR\u0001\u0006K6\u0004H/_\u000b\u0007\u0005S\u0013yK!.\u0016\u0005\t-\u0006C\u0002%\u0013\u0005[\u0013\u0019\fE\u0002[\u0005_#aA!-\u0004\u0005\u0004i&!A&\u0011\u0007i\u0013)\f\u0002\u0004\u00038\u000e\u0011\r!\u0018\u0002\u0002-\u0006!aM]8n+\u0019\u0011iLa1\u0003HR!!q\u0018Be!\u0019A%C!1\u0003FB\u0019!La1\u0005\r\tEFA1\u0001^!\rQ&q\u0019\u0003\u0007\u0005o#!\u0019A/\t\u000f\t-G\u00011\u0001\u0003N\u0006\u0011\u0011\u000e\u001e\t\u0006!\n='1[\u0005\u0004\u0005#\u0014%\u0001D%uKJ\f'\r\\3P]\u000e,\u0007C\u0002'p\u0005\u0003\u0014)-\u0001\u0006oK^\u0014U/\u001b7eKJ,bA!7\u0003f\n%XC\u0001Bn!\u001dA%Q\u001cBq\u0005WL1Aa8A\u0005\u001d\u0011U/\u001b7eKJ\u0004b\u0001T8\u0003d\n\u001d\bc\u0001.\u0003f\u00121!\u0011W\u0003C\u0002u\u00032A\u0017Bu\t\u0019\u00119,\u0002b\u0001;B1\u0001J\u0005Br\u0005O\fAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"A!=\u0011\t\t\u0015%1_\u0005\u0005\u0005k\u00149I\u0001\u0004PE*,7\r\u001e\u0015\b\u0003\te\u0018\u0011\bB\u0000!\ra%1`\u0005\u0004\u0005{$%\u0001E*fe&\fGNV3sg&|g.V%E=\u0005\u0019\u0001fC\u0001\u0003\u0016\u0006]&1TA_\u0005?Cs\u0001\u0001B}\u0003s\u0011y\u0010K\u0006\u0001\u0005+\u000b9La'\u0002>\n}\u0005")
public class OpenHashMap<Key, Value>
extends AbstractMap<Key, Value>
implements StrictOptimizedIterableOps<Tuple2<Key, Value>, Iterable, OpenHashMap<Key, Value>>,
DefaultSerializable {
    private final int actualInitialSize;
    public int scala$collection$mutable$OpenHashMap$$mask;
    public OpenEntry<Key, Value>[] scala$collection$mutable$OpenHashMap$$table;
    private int _size;
    public int scala$collection$mutable$OpenHashMap$$deleted;
    public int scala$collection$mutable$OpenHashMap$$modCount;

    public static <K, V> Builder<Tuple2<K, V>, OpenHashMap<K, V>> newBuilder() {
        return OpenHashMap$.MODULE$.newBuilder();
    }

    public static <K, V> OpenHashMap<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        return OpenHashMap$.MODULE$.from((IterableOnce)it);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
    }

    @Override
    public Tuple2<OpenHashMap<Key, Value>, OpenHashMap<Key, Value>> partition(Function1<Tuple2<Key, Value>, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<OpenHashMap<Key, Value>, OpenHashMap<Key, Value>> span(Function1<Tuple2<Key, Value>, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<Iterable<A1>, Iterable<A2>> unzip(Function1<Tuple2<Key, Value>, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<Iterable<A1>, Iterable<A2>, Iterable<A3>> unzip3(Function1<Tuple2<Key, Value>, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<Tuple2<Key, Value>, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<Tuple2<Key, Value>, IterableOnce<B>> f) {
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
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<Tuple2<Key, Value>, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<Tuple2<Key, Value>, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<Tuple2<Key, Value>, B>, C2> b) {
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
    public <A1, A2> Tuple2<Iterable<A1>, Iterable<A2>> partitionMap(Function1<Tuple2<Key, Value>, Either<A1, A2>> f) {
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
    public MapFactory<OpenHashMap> mapFactory() {
        return OpenHashMap$.MODULE$;
    }

    @Override
    public int size() {
        return this._size;
    }

    @Override
    public int knownSize() {
        return this.size();
    }

    public void scala$collection$mutable$OpenHashMap$$size_$eq(int s2) {
        this._size = s2;
    }

    @Override
    public boolean isEmpty() {
        return this._size == 0;
    }

    public int hashOf(Key key) {
        int h2 = Statics.anyHash(key);
        h2 ^= h2 >>> 20 ^ h2 >>> 12;
        return h2 ^ h2 >>> 7 ^ h2 >>> 4;
    }

    private void growTable() {
        int oldSize = this.scala$collection$mutable$OpenHashMap$$mask + 1;
        int newSize = 4 * oldSize;
        OpenEntry<Key, Value>[] oldTable = this.scala$collection$mutable$OpenHashMap$$table;
        this.scala$collection$mutable$OpenHashMap$$table = new OpenEntry[newSize];
        this.scala$collection$mutable$OpenHashMap$$mask = newSize - 1;
        for (OpenEntry<Key, Value> openEntry : oldTable) {
            OpenHashMap.$anonfun$growTable$1(this, openEntry);
        }
        this.scala$collection$mutable$OpenHashMap$$deleted = 0;
    }

    private int findIndex(Key key, int hash) {
        int index = hash & this.scala$collection$mutable$OpenHashMap$$mask;
        int j = 0;
        int firstDeletedIndex = -1;
        OpenEntry<Key, Value> entry = this.scala$collection$mutable$OpenHashMap$$table[index];
        while (entry != null) {
            if (entry.hash() == hash && BoxesRunTime.equals(entry.key(), key)) {
                Option<Value> option = entry.value();
                None$ none$ = None$.MODULE$;
                if (option == null || !option.equals(none$)) {
                    return index;
                }
            }
            if (firstDeletedIndex == -1) {
                Option<Value> option = entry.value();
                None$ none$ = None$.MODULE$;
                if (option != null && option.equals(none$)) {
                    firstDeletedIndex = index;
                }
            }
            index = index + ++j & this.scala$collection$mutable$OpenHashMap$$mask;
            entry = this.scala$collection$mutable$OpenHashMap$$table[index];
        }
        if (firstDeletedIndex == -1) {
            return index;
        }
        return firstDeletedIndex;
    }

    @Override
    public void update(Key key, Value value) {
        this.put(key, value);
    }

    public OpenHashMap<Key, Value> addOne(Tuple2<Key, Value> kv) {
        this.put(kv._1(), kv._2());
        return this;
    }

    public OpenHashMap<Key, Value> subtractOne(Key key) {
        this.remove(key);
        return this;
    }

    @Override
    public Option<Value> put(Key key, Value value) {
        return this.put(key, this.hashOf(key), value);
    }

    private Option<Value> put(Key key, int hash, Value value) {
        int index;
        OpenEntry<Key, Value> entry;
        if (2 * (this.size() + this.scala$collection$mutable$OpenHashMap$$deleted) > this.scala$collection$mutable$OpenHashMap$$mask) {
            this.growTable();
        }
        if ((entry = this.scala$collection$mutable$OpenHashMap$$table[index = this.findIndex(key, hash)]) == null) {
            this.scala$collection$mutable$OpenHashMap$$table[index] = new OpenEntry<Key, Value>(key, hash, new Some<Value>(value));
            ++this.scala$collection$mutable$OpenHashMap$$modCount;
            this.scala$collection$mutable$OpenHashMap$$size_$eq(this.size() + 1);
            return None$.MODULE$;
        }
        Option<Value> res = entry.value();
        Option<Value> option = entry.value();
        None$ none$ = None$.MODULE$;
        if (option != null && option.equals(none$)) {
            entry.key_$eq(key);
            entry.hash_$eq(hash);
            this.scala$collection$mutable$OpenHashMap$$size_$eq(this.size() + 1);
            --this.scala$collection$mutable$OpenHashMap$$deleted;
            ++this.scala$collection$mutable$OpenHashMap$$modCount;
        }
        entry.value_$eq(new Some<Value>(value));
        return res;
    }

    private void deleteSlot(OpenEntry<Key, Value> entry) {
        entry.key_$eq(null);
        entry.hash_$eq(0);
        entry.value_$eq(None$.MODULE$);
        this.scala$collection$mutable$OpenHashMap$$size_$eq(this.size() - 1);
        ++this.scala$collection$mutable$OpenHashMap$$deleted;
    }

    @Override
    public Option<Value> remove(Key key) {
        OpenEntry<Key, Nothing$> entry = this.scala$collection$mutable$OpenHashMap$$table[this.findIndex(key, this.hashOf(key))];
        if (entry != null) {
            Option<Value> option = entry.value();
            None$ none$ = None$.MODULE$;
            if (option == null || !option.equals(none$)) {
                Option<Value> res = entry.value();
                entry.key_$eq(null);
                entry.hash_$eq(0);
                entry.value_$eq(None$.MODULE$);
                this.scala$collection$mutable$OpenHashMap$$size_$eq(this.size() - 1);
                ++this.scala$collection$mutable$OpenHashMap$$deleted;
                return res;
            }
        }
        return None$.MODULE$;
    }

    @Override
    public Option<Value> get(Key key) {
        int hash = this.hashOf(key);
        int index = hash & this.scala$collection$mutable$OpenHashMap$$mask;
        OpenEntry<Key, Value> entry = this.scala$collection$mutable$OpenHashMap$$table[index];
        int j = 0;
        while (entry != null) {
            if (entry.hash() == hash && BoxesRunTime.equals(entry.key(), key)) {
                return entry.value();
            }
            index = index + ++j & this.scala$collection$mutable$OpenHashMap$$mask;
            entry = this.scala$collection$mutable$OpenHashMap$$table[index];
        }
        return None$.MODULE$;
    }

    @Override
    public Iterator<Tuple2<Key, Value>> iterator() {
        return new OpenHashMapIterator<Tuple2<Key, Value>>(this){

            public Tuple2<Key, Value> nextResult(OpenEntry<Key, Value> node) {
                return new Tuple2<Key, Value>(node.key(), node.value().get());
            }
        };
    }

    @Override
    public Iterator<Key> keysIterator() {
        return new OpenHashMapIterator<Key>(this){

            public Key nextResult(OpenEntry<Key, Value> node) {
                return node.key();
            }
        };
    }

    @Override
    public Iterator<Value> valuesIterator() {
        return new OpenHashMapIterator<Value>(this){

            public Value nextResult(OpenEntry<Key, Value> node) {
                return node.value().get();
            }
        };
    }

    @Override
    public OpenHashMap<Key, Value> clone() {
        OpenHashMap<Key, Value> it = new OpenHashMap<Key, Value>();
        for (OpenEntry<Key, Value> openEntry : this.scala$collection$mutable$OpenHashMap$$table) {
            None$ none$;
            if (openEntry != null) {
                Option<Value> option = openEntry.value();
                none$ = None$.MODULE$;
                if (option == null || !option.equals(none$)) {
                    ((OpenHashMap)it).put(openEntry.key(), openEntry.hash(), openEntry.value().get());
                }
            }
            none$ = null;
        }
        return it;
    }

    @Override
    public <U> void foreach(Function1<Tuple2<Key, Value>, U> f) {
        int startModCount = this.scala$collection$mutable$OpenHashMap$$modCount;
        for (OpenEntry<Key, Value> openEntry : this.scala$collection$mutable$OpenHashMap$$table) {
            None$ none$;
            if (openEntry != null) {
                Option<Value> option = openEntry.value();
                none$ = None$.MODULE$;
                if (option == null || !option.equals(none$)) {
                    if (this.scala$collection$mutable$OpenHashMap$$modCount != startModCount) {
                        throw new ConcurrentModificationException();
                    }
                    f.apply(new Tuple2<Key, Value>(openEntry.key(), openEntry.value().get()));
                }
            }
            none$ = null;
        }
    }

    @Override
    public <U> void foreachEntry(Function2<Key, Value, U> f) {
        int startModCount = this.scala$collection$mutable$OpenHashMap$$modCount;
        for (OpenEntry<Key, Value> openEntry : this.scala$collection$mutable$OpenHashMap$$table) {
            None$ none$;
            if (openEntry != null) {
                Option<Value> option = openEntry.value();
                none$ = None$.MODULE$;
                if (option == null || !option.equals(none$)) {
                    if (this.scala$collection$mutable$OpenHashMap$$modCount != startModCount) {
                        throw new ConcurrentModificationException();
                    }
                    f.apply(openEntry.key(), openEntry.value().get());
                }
            }
            none$ = null;
        }
    }

    private void foreachUndeletedEntry(Function1<OpenEntry<Key, Value>, BoxedUnit> f) {
        for (OpenEntry<Key, Value> openEntry : this.scala$collection$mutable$OpenHashMap$$table) {
            None$ none$;
            if (openEntry != null) {
                Option<Value> option = openEntry.value();
                none$ = None$.MODULE$;
                if (option == null || !option.equals(none$)) {
                    f.apply(openEntry);
                }
            }
            none$ = null;
        }
    }

    public OpenHashMap<Key, Value> mapValuesInPlace(Function2<Key, Value, Value> f) {
        for (OpenEntry<Key, Value> openEntry : this.scala$collection$mutable$OpenHashMap$$table) {
            None$ none$;
            if (openEntry != null) {
                Option<Value> option = openEntry.value();
                none$ = None$.MODULE$;
                if (option == null || !option.equals(none$)) {
                    openEntry.value_$eq(new Some<Value>(f.apply(openEntry.key(), openEntry.value().get())));
                }
            }
            none$ = null;
        }
        return this;
    }

    public OpenHashMap<Key, Value> filterInPlace(Function2<Key, Value, Object> f) {
        for (OpenEntry<Key, Nothing$> openEntry : this.scala$collection$mutable$OpenHashMap$$table) {
            None$ none$;
            if (openEntry != null) {
                Option<Value> option = openEntry.value();
                none$ = None$.MODULE$;
                if (!(option != null && option.equals(none$) || BoxesRunTime.unboxToBoolean(f.apply(openEntry.key(), openEntry.value().get())))) {
                    openEntry.key_$eq(null);
                    openEntry.hash_$eq(0);
                    openEntry.value_$eq(None$.MODULE$);
                    this.scala$collection$mutable$OpenHashMap$$size_$eq(this.size() - 1);
                    ++this.scala$collection$mutable$OpenHashMap$$deleted;
                }
            }
            none$ = null;
        }
        return this;
    }

    @Override
    public String stringPrefix() {
        return "OpenHashMap";
    }

    public static final /* synthetic */ void $anonfun$growTable$1(OpenHashMap $this, OpenEntry entry) {
        if (entry != null) {
            Option option = entry.value();
            None$ none$ = None$.MODULE$;
            if (option == null || !option.equals(none$)) {
                $this.scala$collection$mutable$OpenHashMap$$table[$this.findIndex(entry.key(), (int)entry.hash())] = entry;
                return;
            }
        }
    }

    public static final /* synthetic */ void $anonfun$foreach$1(OpenHashMap $this, int startModCount$1, Function1 f$1, OpenEntry entry) {
        if ($this.scala$collection$mutable$OpenHashMap$$modCount != startModCount$1) {
            throw new ConcurrentModificationException();
        }
        f$1.apply(new Tuple2(entry.key(), entry.value().get()));
    }

    public static final /* synthetic */ void $anonfun$foreachEntry$1(OpenHashMap $this, int startModCount$2, Function2 f$2, OpenEntry entry) {
        if ($this.scala$collection$mutable$OpenHashMap$$modCount != startModCount$2) {
            throw new ConcurrentModificationException();
        }
        f$2.apply(entry.key(), entry.value().get());
    }

    public static final /* synthetic */ void $anonfun$foreachUndeletedEntry$1(Function1 f$3, OpenEntry entry) {
        if (entry != null) {
            Option option = entry.value();
            None$ none$ = None$.MODULE$;
            if (option == null || !option.equals(none$)) {
                f$3.apply(entry);
                return;
            }
        }
    }

    public static final /* synthetic */ void $anonfun$filterInPlace$1(OpenHashMap $this, Function2 f$5, OpenEntry entry) {
        if (!BoxesRunTime.unboxToBoolean(f$5.apply(entry.key(), entry.value().get()))) {
            entry.key_$eq(null);
            entry.hash_$eq(0);
            entry.value_$eq(None$.MODULE$);
            $this.scala$collection$mutable$OpenHashMap$$size_$eq($this.size() - 1);
            ++$this.scala$collection$mutable$OpenHashMap$$deleted;
            return;
        }
    }

    public OpenHashMap(int initialSize) {
        this.actualInitialSize = 1 << -Integer.numberOfLeadingZeros(initialSize - 1);
        this.scala$collection$mutable$OpenHashMap$$mask = this.actualInitialSize - 1;
        this.scala$collection$mutable$OpenHashMap$$table = new OpenEntry[this.actualInitialSize];
        this._size = 0;
        this.scala$collection$mutable$OpenHashMap$$deleted = 0;
        this.scala$collection$mutable$OpenHashMap$$modCount = 0;
    }

    public OpenHashMap() {
        this(8);
    }

    public static final /* synthetic */ Object $anonfun$growTable$1$adapted(OpenHashMap $this, OpenEntry entry) {
        OpenHashMap.$anonfun$growTable$1($this, entry);
        return BoxedUnit.UNIT;
    }

    public static final /* synthetic */ Object $anonfun$clone$1$adapted(OpenHashMap it$1, OpenEntry entry) {
        it$1.put(entry.key(), entry.hash(), entry.value().get());
        return BoxedUnit.UNIT;
    }

    public static final /* synthetic */ Object $anonfun$foreach$1$adapted(OpenHashMap $this, int startModCount$1, Function1 f$1, OpenEntry entry) {
        OpenHashMap.$anonfun$foreach$1($this, startModCount$1, f$1, entry);
        return BoxedUnit.UNIT;
    }

    public static final /* synthetic */ Object $anonfun$foreachEntry$1$adapted(OpenHashMap $this, int startModCount$2, Function2 f$2, OpenEntry entry) {
        OpenHashMap.$anonfun$foreachEntry$1($this, startModCount$2, f$2, entry);
        return BoxedUnit.UNIT;
    }

    public static final /* synthetic */ Object $anonfun$foreachUndeletedEntry$1$adapted(Function1 f$3, OpenEntry entry) {
        OpenHashMap.$anonfun$foreachUndeletedEntry$1(f$3, entry);
        return BoxedUnit.UNIT;
    }

    public static final /* synthetic */ Object $anonfun$mapValuesInPlace$1$adapted(Function2 f$4, OpenEntry entry) {
        entry.value_$eq(new Some(f$4.apply(entry.key(), entry.value().get())));
        return BoxedUnit.UNIT;
    }

    public static final /* synthetic */ Object $anonfun$filterInPlace$1$adapted(OpenHashMap $this, Function2 f$5, OpenEntry entry) {
        OpenHashMap.$anonfun$filterInPlace$1($this, f$5, entry);
        return BoxedUnit.UNIT;
    }

    private static final class OpenEntry<Key, Value> {
        private Key key;
        private int hash;
        private Option<Value> value;

        public Key key() {
            return this.key;
        }

        public void key_$eq(Key x$1) {
            this.key = x$1;
        }

        public int hash() {
            return this.hash;
        }

        public void hash_$eq(int x$1) {
            this.hash = x$1;
        }

        public Option<Value> value() {
            return this.value;
        }

        public void value_$eq(Option<Value> x$1) {
            this.value = x$1;
        }

        public OpenEntry(Key key, int hash, Option<Value> value) {
            this.key = key;
            this.hash = hash;
            this.value = value;
        }
    }

    private abstract class OpenHashMapIterator<A>
    extends AbstractIterator<A> {
        private int index;
        private final int initialModCount;

        private void advance() {
            if (this.initialModCount != this.scala$collection$mutable$OpenHashMap$OpenHashMapIterator$$$outer().scala$collection$mutable$OpenHashMap$$modCount) {
                throw new ConcurrentModificationException();
            }
            while (this.index <= this.scala$collection$mutable$OpenHashMap$OpenHashMapIterator$$$outer().scala$collection$mutable$OpenHashMap$$mask) {
                if (this.scala$collection$mutable$OpenHashMap$OpenHashMapIterator$$$outer().scala$collection$mutable$OpenHashMap$$table[this.index] != null) {
                    Option option = this.scala$collection$mutable$OpenHashMap$OpenHashMapIterator$$$outer().scala$collection$mutable$OpenHashMap$$table[this.index].value();
                    None$ none$ = None$.MODULE$;
                    if (option == null) {
                        return;
                    }
                    if (!option.equals(none$)) break;
                }
                ++this.index;
            }
        }

        @Override
        public boolean hasNext() {
            this.advance();
            return this.index <= this.scala$collection$mutable$OpenHashMap$OpenHashMapIterator$$$outer().scala$collection$mutable$OpenHashMap$$mask;
        }

        @Override
        public A next() {
            this.advance();
            OpenEntry result = this.scala$collection$mutable$OpenHashMap$OpenHashMapIterator$$$outer().scala$collection$mutable$OpenHashMap$$table[this.index];
            ++this.index;
            return this.nextResult(result);
        }

        public abstract A nextResult(OpenEntry<Key, Value> var1);

        public /* synthetic */ OpenHashMap scala$collection$mutable$OpenHashMap$OpenHashMapIterator$$$outer() {
            return OpenHashMap.this;
        }

        public OpenHashMapIterator() {
            if (OpenHashMap.this == null) {
                throw null;
            }
            this.index = 0;
            this.initialModCount = OpenHashMap.this.scala$collection$mutable$OpenHashMap$$modCount;
        }
    }
}

