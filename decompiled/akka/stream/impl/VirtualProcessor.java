/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.EmptyPublisher$;
import akka.stream.impl.ErrorPublisher;
import akka.stream.impl.ReactiveStreamsCompliance$;
import akka.stream.impl.VirtualProcessor$;
import akka.stream.impl.VirtualProcessor$Establishing$;
import akka.stream.impl.VirtualProcessor$Inert$;
import akka.stream.impl.VirtualProcessor$WrappedSubscription$;
import akka.util.OptionVal;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Processor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0011\rqACA\u0003\u0003\u000fA\t!a\u0003\u0002\u0014\u0019Q\u0011qCA\u0004\u0011\u0003\tY!!\u0007\t\u000f\u0005]\u0012\u0001\"\u0001\u0002<!I\u0011QH\u0001C\u0002\u0013\u0015\u0011q\b\u0005\t\u0003\u000b\n\u0001\u0015!\u0004\u0002B\u0019I\u0011qI\u0001\u0011\u0002G\u0005\u0012\u0011\n\u0005\b\u0003\u0017*a\u0011AA'\u000f\u001d\u0011\u0019&\u0001EA\u0005+2qAa\u0016\u0002\u0011\u0003\u0013I\u0006C\u0004\u00028!!\tAa\u0017\t\u0013\u0005-\u0003B1A\u0005\u0002\tu\u0003\u0002CAF\u0011\u0001\u0006IAa\u0018\t\u0013\u0005E\u0006\"!A\u0005B\u0005M\u0006\"CAa\u0011\u0005\u0005I\u0011AAb\u0011%\tY\rCA\u0001\n\u0003\u0011)\u0007C\u0005\u0002T\"\t\t\u0011\"\u0011\u0002V\"I\u00111\u001d\u0005\u0002\u0002\u0013\u0005!\u0011\u000e\u0005\n\u0003kD\u0011\u0011!C!\u0003oD\u0011\"!?\t\u0003\u0003%\t%a?\t\u0013\t5\u0004\"!A\u0005\n\t=dABA4\u0003\t\u000bI\u0007\u0003\u0006\u0002LQ\u0011)\u001a!C\u0001\u0003\u001bB!\"a#\u0015\u0005#\u0005\u000b\u0011BA(\u0011\u001d\t9\u0004\u0006C\u0001\u0003\u001bC\u0011\"a%\u0015\u0003\u0003%\t!!&\t\u0013\u0005eE#%A\u0005\u0002\u0005m\u0005\"CAY)\u0005\u0005I\u0011IAZ\u0011%\t\t\rFA\u0001\n\u0003\t\u0019\rC\u0005\u0002LR\t\t\u0011\"\u0001\u0002N\"I\u00111\u001b\u000b\u0002\u0002\u0013\u0005\u0013Q\u001b\u0005\n\u0003G$\u0012\u0011!C\u0001\u0003KD\u0011\"a<\u0015\u0003\u0003%\t%!=\t\u0013\u0005UH#!A\u0005B\u0005]\b\"CA})\u0005\u0005I\u0011IA~\u0011%\ti\u0010FA\u0001\n\u0003\nypB\u0005\u0003x\u0005\t\t\u0011#\u0001\u0003z\u0019I\u0011qM\u0001\u0002\u0002#\u0005!1\u0010\u0005\b\u0003o!C\u0011\u0001BE\u0011%\tI\u0010JA\u0001\n\u000b\nY\u0010C\u0005\u0003\f\u0012\n\t\u0011\"!\u0003\u000e\"I!\u0011\u0013\u0013\u0002\u0002\u0013\u0005%1\u0013\u0005\n\u0005[\"\u0013\u0011!C\u0005\u0005_2aAa\u0001\u0002\u0005\n\u0015\u0001BCA&U\tU\r\u0011\"\u0001\u0002N!Q\u00111\u0012\u0016\u0003\u0012\u0003\u0006I!a\u0014\t\u0015\t\u001d!F!f\u0001\n\u0003\u0011I\u0001\u0003\u0006\u0003\f)\u0012\t\u0012)A\u0005\u0003OD!B!\u0004+\u0005+\u0007I\u0011\u0001B\b\u0011)\u0011\u0019C\u000bB\tB\u0003%!\u0011\u0003\u0005\b\u0003oQC\u0011\u0001B\u0013\u0011%\t\u0019JKA\u0001\n\u0003\u0011y\u0003C\u0005\u0002\u001a*\n\n\u0011\"\u0001\u0002\u001c\"I!q\u0007\u0016\u0012\u0002\u0013\u0005!\u0011\b\u0005\n\u0005{Q\u0013\u0013!C\u0001\u0005\u007fA\u0011\"!-+\u0003\u0003%\t%a-\t\u0013\u0005\u0005'&!A\u0005\u0002\u0005\r\u0007\"CAfU\u0005\u0005I\u0011\u0001B\"\u0011%\t\u0019NKA\u0001\n\u0003\n)\u000eC\u0005\u0002d*\n\t\u0011\"\u0001\u0003H!I\u0011q\u001e\u0016\u0002\u0002\u0013\u0005#1\n\u0005\n\u0003kT\u0013\u0011!C!\u0003oD\u0011\"!?+\u0003\u0003%\t%a?\t\u0013\u0005u(&!A\u0005B\t=sa\u0002BP\u0003!\u0005!\u0011\u0015\u0004\b\u0005\u0007\t\u0001\u0012\u0001BR\u0011\u001d\t9\u0004\u0011C\u0001\u0005KCqAa*A\t\u0003\u0011I\u000bC\u0005\u0003\f\u0002\u000b\t\u0011\"!\u0003D\"I!1\u001a!\u0012\u0002\u0013\u0005!\u0011\b\u0005\n\u0005\u001b\u0004\u0015\u0013!C\u0001\u0005\u007fA\u0011B!%A\u0003\u0003%\tIa4\t\u0013\tm\u0007)%A\u0005\u0002\te\u0002\"\u0003Bo\u0001F\u0005I\u0011\u0001B \u0011%\u0011i\u0007QA\u0001\n\u0013\u0011y\u0007C\u0005\u0003n\u0005\t\t\u0011\"\u0003\u0003p\u0019I\u0011qCA\u0004\u0005\u0005-!Q\u001e\u0005\b\u0003oYE\u0011AB\b\u0011\u001d\tIp\u0013C!\u0007'Aqaa\tL\t\u0003\u001a)\u0003C\u0004\u0004<-#\te!\u0010\t\u000f\r\u001d3\n\"\u0003\u0004J!91qK&\u0005B\re\u0003bBB0\u0017\u0012\u00053\u0011\r\u0005\b\u0007[ZE\u0011IB8\u0011\u001d\u0019\u0019h\u0013C\u0005\u0003g;qa!\u001eL\u0011\u0003\u00199HB\u0004\u0004|-C\ta! \t\u000f\u0005]b\u000b\"\u0001\u0004\u0000\u0019I1\u0011\u0011,\u0011\u0002G\u000521\u0011\u0005\b\u0007\u000bCf\u0011ABD\u000f\u001d\u0019IM\u0016EA\u0007\u007f3qa!/W\u0011\u0003\u001bY\fC\u0004\u00028m#\ta!0\t\u000f\r\u00155\f\"\u0011\u0004\b\"I\u0011\u0011W.\u0002\u0002\u0013\u0005\u00131\u0017\u0005\n\u0003\u0003\\\u0016\u0011!C\u0001\u0003\u0007D\u0011\"a3\\\u0003\u0003%\ta!1\t\u0013\u0005M7,!A\u0005B\u0005U\u0007\"CAr7\u0006\u0005I\u0011ABc\u0011%\t)pWA\u0001\n\u0003\n9\u0010C\u0005\u0002zn\u000b\t\u0011\"\u0011\u0002|\u001a11\u0011\u0013,A\u0007'C!b!\"f\u0005+\u0007I\u0011ABD\u0011)\u0019I*\u001aB\tB\u0003%1\u0011\u0012\u0005\b\u0003o)G\u0011ABN\u0011%\t\u0019*ZA\u0001\n\u0003\u0019\t\u000bC\u0005\u0002\u001a\u0016\f\n\u0011\"\u0001\u0004&\"I\u0011\u0011W3\u0002\u0002\u0013\u0005\u00131\u0017\u0005\n\u0003\u0003,\u0017\u0011!C\u0001\u0003\u0007D\u0011\"a3f\u0003\u0003%\ta!+\t\u0013\u0005MW-!A\u0005B\u0005U\u0007\"CArK\u0006\u0005I\u0011ABW\u0011%\ty/ZA\u0001\n\u0003\u001a\t\fC\u0005\u0002v\u0016\f\t\u0011\"\u0011\u0002x\"I\u0011\u0011`3\u0002\u0002\u0013\u0005\u00131 \u0005\n\u0003{,\u0017\u0011!C!\u0007k;\u0011ba3W\u0003\u0003E\ta!4\u0007\u0013\rEe+!A\t\u0002\r=\u0007bBA\u001ck\u0012\u000511\u001b\u0005\n\u0003s,\u0018\u0011!C#\u0003wD\u0011Ba#v\u0003\u0003%\ti!6\t\u0013\tEU/!A\u0005\u0002\u000ee\u0007\"CBp-\n\u0007I\u0011ABq\u0011!\u0019\u0019O\u0016Q\u0001\n\rueABB>\u0017\u0012\u0019)\u000f\u0003\u0006\u0004nr\u0014\t\u0011)A\u0005\u0007\u0003Bq!a\u000e}\t\u0003\u0019y\u000fC\u0004\u0004vr$\ta!\u0019\t\u000f\r]H\u0010\"\u0011\u0004z\"91q ?\u0005B\r\u0005\u0014\u0001\u0005,jeR,\u0018\r\u001c)s_\u000e,7o]8s\u0015\u0011\tI!a\u0003\u0002\t%l\u0007\u000f\u001c\u0006\u0005\u0003\u001b\ty!\u0001\u0004tiJ,\u0017-\u001c\u0006\u0003\u0003#\tA!Y6lCB\u0019\u0011QC\u0001\u000e\u0005\u0005\u001d!\u0001\u0005,jeR,\u0018\r\u001c)s_\u000e,7o]8s'\u0015\t\u00111DA\u0014!\u0011\ti\"a\t\u000e\u0005\u0005}!BAA\u0011\u0003\u0015\u00198-\u00197b\u0013\u0011\t)#a\b\u0003\r\u0005s\u0017PU3g!\u0011\tI#a\r\u000e\u0005\u0005-\"\u0002BA\u0017\u0003_\t!![8\u000b\u0005\u0005E\u0012\u0001\u00026bm\u0006LA!!\u000e\u0002,\ta1+\u001a:jC2L'0\u00192mK\u00061A(\u001b8jiz\u001a\u0001\u0001\u0006\u0002\u0002\u0014\u0005)A)\u001a2vOV\u0011\u0011\u0011I\b\u0003\u0003\u0007J\u0012\u0001A\u0001\u0007\t\u0016\u0014Wo\u001a\u0011\u0003'!\u000b7/Q2uk\u0006d7+\u001e2tGJL'-\u001a:\u0014\u0007\u0015\tY\"\u0001\u0006tk\n\u001c8M]5cKJ,\"!a\u0014\u0011\r\u0005E\u00131LA0\u001b\t\t\u0019F\u0003\u0003\u0002V\u0005]\u0013a\u0004:fC\u000e$\u0018N^3tiJ,\u0017-\\:\u000b\u0005\u0005e\u0013aA8sO&!\u0011QLA*\u0005)\u0019VOY:de&\u0014WM\u001d\t\u0005\u0003;\t\t'\u0003\u0003\u0002d\u0005}!aA!os&\u001aQ\u0001\u0006\u0016\u0003\t\t{G\u000f[\n\n)\u0005m\u00111NA8\u0003k\u00022!!\u001c\u0006\u001b\u0005\t\u0001\u0003BA\u000f\u0003cJA!a\u001d\u0002 \t9\u0001K]8ek\u000e$\b\u0003BA<\u0003\u000fsA!!\u001f\u0002\u0004:!\u00111PAA\u001b\t\tiH\u0003\u0003\u0002\u0000\u0005e\u0012A\u0002\u001fs_>$h(\u0003\u0002\u0002\"%!\u0011QQA\u0010\u0003\u001d\u0001\u0018mY6bO\u0016LA!!\u000e\u0002\n*!\u0011QQA\u0010\u0003-\u0019XOY:de&\u0014WM\u001d\u0011\u0015\t\u0005=\u0015\u0011\u0013\t\u0004\u0003[\"\u0002bBA&/\u0001\u0007\u0011qJ\u0001\u0005G>\u0004\u0018\u0010\u0006\u0003\u0002\u0010\u0006]\u0005\"CA&1A\u0005\t\u0019AA(\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\"!!(+\t\u0005=\u0013qT\u0016\u0003\u0003C\u0003B!a)\u0002.6\u0011\u0011Q\u0015\u0006\u0005\u0003O\u000bI+A\u0005v]\u000eDWmY6fI*!\u00111VA\u0010\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003_\u000b)KA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAA[!\u0011\t9,!0\u000e\u0005\u0005e&\u0002BA^\u0003_\tA\u0001\\1oO&!\u0011qXA]\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u0011Q\u0019\t\u0005\u0003;\t9-\u0003\u0003\u0002J\u0006}!aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$H\u0003BA0\u0003\u001fD\u0011\"!5\u001d\u0003\u0003\u0005\r!!2\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\t9\u000e\u0005\u0004\u0002Z\u0006}\u0017qL\u0007\u0003\u00037TA!!8\u0002 \u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0005\u0005\u00181\u001c\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0002h\u00065\b\u0003BA\u000f\u0003SLA!a;\u0002 \t9!i\\8mK\u0006t\u0007\"CAi=\u0005\u0005\t\u0019AA0\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\u0005U\u00161\u001f\u0005\n\u0003#|\u0012\u0011!a\u0001\u0003\u000b\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0003\u000b\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003k\u000ba!Z9vC2\u001cH\u0003BAt\u0005\u0003A\u0011\"!5#\u0003\u0003\u0005\r!a\u0018\u0003\u0019\u0015\u001bH/\u00192mSND\u0017N\\4\u0014\u0013)\nY\"a\u001b\u0002p\u0005U\u0014AE8o\u0007>l\u0007\u000f\\3uK\n+hMZ3sK\u0012,\"!a:\u0002'=t7i\\7qY\u0016$XMQ;gM\u0016\u0014X\r\u001a\u0011\u0002\u001f=tWI\u001d:pe\n+hMZ3sK\u0012,\"A!\u0005\u0011\r\tM!\u0011\u0004B\u000f\u001b\t\u0011)B\u0003\u0003\u0003\u0018\u0005=\u0011\u0001B;uS2LAAa\u0007\u0003\u0016\tIq\n\u001d;j_:4\u0016\r\u001c\t\u0005\u0003o\u0012y\"\u0003\u0003\u0003\"\u0005%%!\u0003+ie><\u0018M\u00197f\u0003Ayg.\u0012:s_J\u0014UO\u001a4fe\u0016$\u0007\u0005\u0006\u0005\u0003(\t%\"1\u0006B\u0017!\r\tiG\u000b\u0005\b\u0003\u0017\n\u0004\u0019AA(\u0011%\u00119!\rI\u0001\u0002\u0004\t9\u000fC\u0005\u0003\u000eE\u0002\n\u00111\u0001\u0003\u0012QA!q\u0005B\u0019\u0005g\u0011)\u0004C\u0005\u0002LI\u0002\n\u00111\u0001\u0002P!I!q\u0001\u001a\u0011\u0002\u0003\u0007\u0011q\u001d\u0005\n\u0005\u001b\u0011\u0004\u0013!a\u0001\u0005#\tabY8qs\u0012\"WMZ1vYR$#'\u0006\u0002\u0003<)\"\u0011q]AP\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*\"A!\u0011+\t\tE\u0011q\u0014\u000b\u0005\u0003?\u0012)\u0005C\u0005\u0002Rb\n\t\u00111\u0001\u0002FR!\u0011q\u001dB%\u0011%\t\tNOA\u0001\u0002\u0004\ty\u0006\u0006\u0003\u00026\n5\u0003\"CAiw\u0005\u0005\t\u0019AAc)\u0011\t9O!\u0015\t\u0013\u0005Eg(!AA\u0002\u0005}\u0013!B%oKJ$\bcAA7\u0011\t)\u0011J\\3siN9\u0001\"a\u0007\u0002p\u0005UDC\u0001B++\t\u0011y\u0006\u0005\u0004\u0002\u0016\t\u0005\u0014qL\u0005\u0005\u0005G\n9A\u0001\u000bDC:\u001cW\r\u001c7j]\u001e\u001cVOY:de&\u0014WM\u001d\u000b\u0005\u0003?\u00129\u0007C\u0005\u0002R:\t\t\u00111\u0001\u0002FR!\u0011q\u001dB6\u0011%\t\t\u000eEA\u0001\u0002\u0004\ty&\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0003rA!\u0011q\u0017B:\u0013\u0011\u0011)(!/\u0003\r=\u0013'.Z2u\u0003\u0011\u0011u\u000e\u001e5\u0011\u0007\u00055DeE\u0003%\u0005{\n9\u0003\u0005\u0005\u0003\u0000\t\u0015\u0015qJAH\u001b\t\u0011\tI\u0003\u0003\u0003\u0004\u0006}\u0011a\u0002:v]RLW.Z\u0005\u0005\u0005\u000f\u0013\tIA\tBEN$(/Y2u\rVt7\r^5p]F\"\"A!\u001f\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\t\u0005=%q\u0012\u0005\b\u0003\u0017:\u0003\u0019AA(\u0003\u001d)h.\u00199qYf$BA!&\u0003\u001cB1\u0011Q\u0004BL\u0003\u001fJAA!'\u0002 \t1q\n\u001d;j_:D\u0011B!()\u0003\u0003\u0005\r!a$\u0002\u0007a$\u0003'\u0001\u0007FgR\f'\r\\5tQ&tw\rE\u0002\u0002n\u0001\u001bR\u0001QA\u000e\u0003O!\"A!)\u0002\r\r\u0014X-\u0019;f)\u0011\u00119Ca+\t\u000f\t5&\t1\u0001\u00030\u0006\t1\u000f\r\u0003\u00032\n]\u0006CBA)\u00037\u0012\u0019\f\u0005\u0003\u00036\n]F\u0002\u0001\u0003\r\u0005s\u0013Y+!A\u0001\u0002\u000b\u0005!1\u0018\u0002\u0004?\u0012\n\u0014\u0003\u0002B_\u0003?\u0002B!!\b\u0003@&!!\u0011YA\u0010\u0005\u001dqu\u000e\u001e5j]\u001e$\u0002Ba\n\u0003F\n\u001d'\u0011\u001a\u0005\b\u0003\u0017\u001a\u0005\u0019AA(\u0011%\u00119a\u0011I\u0001\u0002\u0004\t9\u000fC\u0005\u0003\u000e\r\u0003\n\u00111\u0001\u0003\u0012\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$#'A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00134)\u0011\u0011\tN!7\u0011\r\u0005u!q\u0013Bj!)\tiB!6\u0002P\u0005\u001d(\u0011C\u0005\u0005\u0005/\fyB\u0001\u0004UkBdWm\r\u0005\n\u0005;3\u0015\u0011!a\u0001\u0005O\t1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u0012\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$3\u0007K\u0002\u0002\u0005C\u0004BAa9\u0003h6\u0011!Q\u001d\u0006\u0005\u0003W\u000by!\u0003\u0003\u0003j\n\u0015(aC%oi\u0016\u0014h.\u00197Ba&D3\u0001\u0001Bq+\u0011\u0011yoa\u0003\u0014\u000b-\u0013\tpa\u0001\u0011\r\tM(q`A\u000e\u001b\t\u0011)P\u0003\u0003\u0003x\ne\u0018AB1u_6L7M\u0003\u0003\u0003|\nu\u0018AC2p]\u000e,(O]3oi*!!qCA\u0018\u0013\u0011\u0019\tA!>\u0003\u001f\u0005#x.\\5d%\u00164WM]3oG\u0016\u0004\u0002\"!\u0015\u0004\u0006\r%1\u0011B\u0005\u0005\u0007\u000f\t\u0019FA\u0005Qe>\u001cWm]:peB!!QWB\u0006\t\u001d\u0019ia\u0013b\u0001\u0005w\u0013\u0011\u0001\u0016\u000b\u0003\u0007#\u0001R!!\u0006L\u0007\u0013!\"a!\u0006\u0011\t\r]1q\u0004\b\u0005\u00073\u0019Y\u0002\u0005\u0003\u0002|\u0005}\u0011\u0002BB\u000f\u0003?\ta\u0001\u0015:fI\u00164\u0017\u0002BA`\u0007CQAa!\b\u0002 \u0005I1/\u001e2tGJL'-\u001a\u000b\u0005\u0007O\u0019i\u0003\u0005\u0003\u0002\u001e\r%\u0012\u0002BB\u0016\u0003?\u0011A!\u00168ji\"9!Q\u0016(A\u0002\r=\u0002\u0007BB\u0019\u0007k\u0001b!!\u0015\u0002\\\rM\u0002\u0003\u0002B[\u0007k!Aba\u000e\u0004.\u0005\u0005\t\u0011!B\u0001\u0007s\u00111a\u0018\u00133#\u0011\u0019I!a\u0018\u0002\u0017=t7+\u001e2tGJL'-\u001a\u000b\u0005\u0007O\u0019y\u0004C\u0004\u0003.>\u0003\ra!\u0011\u0011\t\u0005E31I\u0005\u0005\u0007\u000b\n\u0019F\u0001\u0007Tk\n\u001c8M]5qi&|g.A\u000bfgR\f'\r\\5tQN+(m]2sSB$\u0018n\u001c8\u0015\r\r\u001d21JB*\u0011\u001d\u0019i\u0005\u0015a\u0001\u0007\u001f\nA\"Z:uC\nd\u0017n\u001d5j]\u001e\u00042a!\u0015+\u001d\r\t)\u0002\u0001\u0005\b\u0007+\u0002\u0006\u0019AB!\u00031\u0019XOY:de&\u0004H/[8o\u0003\u001dyg.\u0012:s_J$Baa\n\u0004\\!91QL)A\u0002\tu\u0011!\u0001;\u0002\u0015=t7i\\7qY\u0016$X\r\u0006\u0002\u0004(!\u001a!k!\u001a\u0011\t\r\u001d4\u0011N\u0007\u0003\u0003SKAaa\u001b\u0002*\n9A/Y5me\u0016\u001c\u0017AB8o\u001d\u0016DH\u000f\u0006\u0003\u0004(\rE\u0004bBB/'\u0002\u00071\u0011B\u0001\t]>$U-\\1oI\u0006\u0019rK]1qa\u0016$7+\u001e2tGJL\u0007\u000f^5p]B\u00191\u0011\u0010,\u000e\u0003-\u00131c\u0016:baB,GmU;cg\u000e\u0014\u0018\u000e\u001d;j_:\u001cRAVA\u000e\u0003O!\"aa\u001e\u0003#M+(m]2sSB$\u0018n\u001c8Ti\u0006$XmE\u0002Y\u00037\ta\u0001Z3nC:$WCABE!\u0011\tiba#\n\t\r5\u0015q\u0004\u0002\u0005\u0019>tw-K\u0002YKn\u0013\u0011BQ;gM\u0016\u0014\u0018N\\4\u0014\u0013\u0015\fYb!&\u0002p\u0005U\u0004cABL16\ta+A\u0004eK6\fg\u000e\u001a\u0011\u0015\t\ru5q\u0014\t\u0004\u0007/+\u0007bBBCQ\u0002\u00071\u0011\u0012\u000b\u0005\u0007;\u001b\u0019\u000bC\u0005\u0004\u0006&\u0004\n\u00111\u0001\u0004\nV\u00111q\u0015\u0016\u0005\u0007\u0013\u000by\n\u0006\u0003\u0002`\r-\u0006\"CAi[\u0006\u0005\t\u0019AAc)\u0011\t9oa,\t\u0013\u0005Ew.!AA\u0002\u0005}C\u0003BA[\u0007gC\u0011\"!5q\u0003\u0003\u0005\r!!2\u0015\t\u0005\u001d8q\u0017\u0005\n\u0003#\u001c\u0018\u0011!a\u0001\u0003?\u00121\u0002U1tgRC'o\\;hQNI1,a\u0007\u0004\u0016\u0006=\u0014Q\u000f\u000b\u0003\u0007\u007f\u00032aa&\\)\u0011\tyfa1\t\u0013\u0005E\u0007-!AA\u0002\u0005\u0015G\u0003BAt\u0007\u000fD\u0011\"!5c\u0003\u0003\u0005\r!a\u0018\u0002\u0017A\u000b7o\u001d+ie>,x\r[\u0001\n\u0005V4g-\u001a:j]\u001e\u00042aa&v'\u0015)8\u0011[A\u0014!!\u0011yH!\"\u0004\n\u000euECABg)\u0011\u0019ija6\t\u000f\r\u0015\u0005\u00101\u0001\u0004\nR!11\\Bo!\u0019\tiBa&\u0004\n\"I!QT=\u0002\u0002\u0003\u00071QT\u0001\u0011\u001d>\u0014UO\u001a4fe\u0016$G)Z7b]\u0012,\"a!(\u0002#9{')\u001e4gKJ,G\rR3nC:$\u0007eE\u0003}\u0007O\u001c\t\u0005\u0005\u0004\u0003t\n}8\u0011\u001e\t\u0004\u0007WDfbAB=+\u0006!!/Z1m)\u0011\u0019\tpa=\u0011\u0007\reD\u0010C\u0004\u0004nz\u0004\ra!\u0011\u0002=Utw-\u0019;f\t\u0016l\u0017M\u001c3B]\u0012\u0014V-];fgR\u0014UO\u001a4fe\u0016$\u0017a\u0002:fcV,7\u000f\u001e\u000b\u0005\u0007O\u0019Y\u0010\u0003\u0005\u0004~\u0006\u0005\u0001\u0019ABE\u0003\u0005q\u0017AB2b]\u000e,G\u000eK\u0002L\u0005C\u0004")
@InternalApi
public final class VirtualProcessor<T>
extends AtomicReference<Object>
implements Processor<T, T> {
    private volatile VirtualProcessor$WrappedSubscription$ WrappedSubscription$module;

    public static boolean Debug() {
        return VirtualProcessor$.MODULE$.Debug();
    }

    public VirtualProcessor$WrappedSubscription$ WrappedSubscription() {
        if (this.WrappedSubscription$module == null) {
            this.WrappedSubscription$lzycompute$1();
        }
        return this.WrappedSubscription$module;
    }

    @Override
    public String toString() {
        return new StringBuilder(18).append("VirtualProcessor(").append(this.hashCode()).append(")").toString();
    }

    @Override
    public void subscribe(Subscriber<? super T> s2) {
        if (s2 == null) {
            Throwable ex = ReactiveStreamsCompliance$.MODULE$.subscriberMustNotBeNullException();
            try {
                this.rec$1(VirtualProcessor$Inert$.MODULE$.subscriber(), s2);
            }
            finally {
                throw ex;
            }
        }
        this.rec$1(s2, s2);
    }

    @Override
    public void onSubscribe(Subscription s2) {
        if (s2 == null) {
            Throwable ex = ReactiveStreamsCompliance$.MODULE$.subscriptionMustNotBeNullException();
            try {
                this.rec$2(new ErrorPublisher(ex, "failed-VirtualProcessor"), s2);
            }
            finally {
                throw ex;
            }
        }
        this.rec$2(s2, s2);
    }

    private void establishSubscription(Establishing establishing, Subscription subscription) {
        block9: {
            WrappedSubscription wrapped = new WrappedSubscription(this, subscription);
            try {
                establishing.subscriber().onSubscribe(wrapped);
                if (this.compareAndSet(establishing, new Both(establishing.subscriber()))) {
                    wrapped.ungateDemandAndRequestBuffered();
                    break block9;
                }
                boolean bl = false;
                Establishing establishing2 = null;
                Object v = this.get();
                if (v instanceof Establishing) {
                    bl = true;
                    establishing2 = (Establishing)v;
                    Subscriber<Object> sub = establishing2.subscriber();
                    Throwable throwable = establishing2.onErrorBuffered();
                    Throwable throwable2 = OptionVal$Some$.MODULE$.unapply(throwable);
                    if (!OptionVal$.MODULE$.isEmpty$extension(throwable2)) {
                        Throwable error = OptionVal$.MODULE$.get$extension(throwable2);
                        ReactiveStreamsCompliance$.MODULE$.tryOnError(sub, error);
                        this.set(VirtualProcessor$Inert$.MODULE$);
                        break block9;
                    }
                }
                if (bl) {
                    Subscriber<Object> sub = establishing2.subscriber();
                    boolean bl2 = establishing2.onCompleteBuffered();
                    if (bl2) {
                        ReactiveStreamsCompliance$.MODULE$.tryOnComplete(sub);
                        this.set(VirtualProcessor$Inert$.MODULE$);
                        break block9;
                    }
                }
                if (VirtualProcessor$Inert$.MODULE$.equals(v)) {
                    ReactiveStreamsCompliance$.MODULE$.tryCancel(subscription, new IllegalStateException("VirtualProcessor was already subscribed to."));
                    break block9;
                }
                throw new IllegalStateException(new StringBuilder(74).append("Unexpected state while establishing: [").append(v).append("], if this ever happens it is a bug.").toString());
            }
            catch (Throwable throwable) {
                Option<Throwable> option;
                Throwable throwable3 = throwable;
                if (throwable3 != null && !(option = NonFatal$.MODULE$.unapply(throwable3)).isEmpty()) {
                    Throwable ex = option.get();
                    this.set(VirtualProcessor$Inert$.MODULE$);
                    ReactiveStreamsCompliance$.MODULE$.tryCancel(subscription, ex);
                    ReactiveStreamsCompliance$.MODULE$.tryOnError(establishing.subscriber(), ex);
                }
                throw throwable;
            }
        }
    }

    @Override
    public void onError(Throwable t2) {
        Throwable ex = t2 == null ? ReactiveStreamsCompliance$.MODULE$.exceptionMustNotBeNullException() : t2;
        this.rec$3(ex);
        if (t2 == null) {
            throw ex;
        }
    }

    @Override
    public void onComplete() {
        while (true) {
            Throwable x$3;
            Object v;
            if ((v = this.get()) == null) {
                if (!this.compareAndSet(null, EmptyPublisher$.MODULE$)) continue;
                break;
            }
            if (v instanceof Subscription) {
                Subscription subscription = (Subscription)v;
                if (!this.compareAndSet(subscription, EmptyPublisher$.MODULE$)) continue;
                break;
            }
            if (v instanceof Both) {
                Both both = (Both)v;
                Subscriber<Object> s2 = both.subscriber();
                this.set(VirtualProcessor$Inert$.MODULE$);
                ReactiveStreamsCompliance$.MODULE$.tryOnComplete(s2);
                break;
            }
            if (v instanceof Subscriber) {
                Subscriber subscriber = (Subscriber)v;
                this.set(VirtualProcessor$Inert$.MODULE$);
                EmptyPublisher$.MODULE$.subscribe((Subscriber<?>)subscriber);
                break;
            }
            if (!(v instanceof Establishing)) break;
            Establishing establishing = (Establishing)v;
            boolean bl = establishing.onCompleteBuffered();
            Throwable throwable = establishing.onErrorBuffered();
            if (bl) break;
            OptionVal$.MODULE$.None();
            Throwable throwable2 = throwable;
            if (null != null ? !((Object)null).equals(throwable2) : throwable2 != null) break;
            if (establishing.onCompleteBuffered()) break;
            boolean x$1 = true;
            Subscriber<Object> x$2 = establishing.copy$default$1();
            if (this.compareAndSet(establishing, establishing.copy(x$2, true, x$3 = establishing.copy$default$3()))) break;
        }
    }

    @Override
    public void onNext(T t2) {
        if (t2 == null) {
            Throwable ex = ReactiveStreamsCompliance$.MODULE$.elementMustNotBeNullException();
            this.rec$4(ex);
            throw ex;
        }
        this.rec$5(t2);
    }

    private String noDemand() {
        return "spec violation: onNext was signaled from upstream without demand";
    }

    private final void WrappedSubscription$lzycompute$1() {
        VirtualProcessor virtualProcessor = this;
        synchronized (virtualProcessor) {
            if (this.WrappedSubscription$module == null) {
                this.WrappedSubscription$module = new VirtualProcessor$WrappedSubscription$(this);
            }
        }
    }

    private final void rec$1(Subscriber sub, Subscriber s$1) {
        block3: {
            while (true) {
                Object v;
                if ((v = this.get()) == null) {
                    if (!this.compareAndSet(null, s$1)) continue;
                    break block3;
                }
                if (v instanceof Subscription) {
                    Subscription subscription = (Subscription)v;
                    Establishing establishing = new Establishing(sub, false, VirtualProcessor$Establishing$.MODULE$.apply$default$3());
                    if (!this.compareAndSet(subscription, establishing)) continue;
                    this.establishSubscription(establishing, subscription);
                    break block3;
                }
                if (!(v instanceof Publisher)) break;
                Publisher publisher = (Publisher)v;
                if (!this.compareAndSet(publisher, VirtualProcessor$Inert$.MODULE$)) continue;
                publisher.subscribe(sub);
                break block3;
            }
            ReactiveStreamsCompliance$.MODULE$.rejectAdditionalSubscriber(sub, "VirtualProcessor");
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final void rec$2(Object obj, Subscription s$2) {
        Object v;
        block4: {
            Object object;
            Subscriber subscriber;
            while (true) {
                if ((v = this.get()) == null) {
                    if (!this.compareAndSet(null, obj)) continue;
                    return;
                }
                if (!(v instanceof Subscriber)) break block4;
                subscriber = (Subscriber)v;
                object = obj;
                if (!(object instanceof Subscription)) break;
                Subscription subscription = (Subscription)object;
                Establishing establishing = VirtualProcessor$Establishing$.MODULE$.create(subscriber);
                if (!this.compareAndSet(subscriber, establishing)) continue;
                this.establishSubscription(establishing, subscription);
                return;
            }
            if (!(object instanceof Publisher)) throw new IllegalStateException(new StringBuilder(38).append("Unexpected state in VirtualProcessor: ").append(object).toString());
            Publisher publisher = (Publisher)object;
            VirtualProcessor$Inert$ virtualProcessor$Inert$ = this.getAndSet(VirtualProcessor$Inert$.MODULE$);
            if (VirtualProcessor$Inert$.MODULE$.equals(virtualProcessor$Inert$)) {
                return;
            } else {
                publisher.subscribe(subscriber);
            }
            return;
        }
        ReactiveStreamsCompliance$.MODULE$.tryCancel(s$2, new IllegalStateException(new StringBuilder(50).append("VirtualProcessor in wrong state [").append(v).append("]. Spec violation").toString()));
    }

    private final void rec$3(Throwable ex) {
        while (true) {
            boolean x$3;
            Object v;
            if ((v = this.get()) == null) {
                if (!this.compareAndSet(null, new ErrorPublisher(ex, "failed-VirtualProcessor"))) continue;
                break;
            }
            if (v instanceof Subscription) {
                Subscription subscription = (Subscription)v;
                if (!this.compareAndSet(subscription, new ErrorPublisher(ex, "failed-VirtualProcessor"))) continue;
                break;
            }
            if (v instanceof Both) {
                Both both = (Both)v;
                Subscriber<Object> s2 = both.subscriber();
                this.set(VirtualProcessor$Inert$.MODULE$);
                ReactiveStreamsCompliance$.MODULE$.tryOnError(s2, ex);
                break;
            }
            if (v instanceof Subscriber) {
                Subscriber subscriber = (Subscriber)v;
                VirtualProcessor$Inert$ virtualProcessor$Inert$ = this.getAndSet(VirtualProcessor$Inert$.MODULE$);
                if (VirtualProcessor$Inert$.MODULE$.equals(virtualProcessor$Inert$)) {
                } else {
                    new ErrorPublisher(ex, "failed-VirtualProcessor").subscribe((Subscriber<?>)subscriber);
                }
                break;
            }
            if (!(v instanceof Establishing)) break;
            Establishing establishing = (Establishing)v;
            boolean bl = establishing.onCompleteBuffered();
            Throwable throwable = establishing.onErrorBuffered();
            if (bl) break;
            OptionVal$.MODULE$.None();
            Throwable throwable2 = throwable;
            if (null != null ? !((Object)null).equals(throwable2) : throwable2 != null) break;
            Throwable x$1 = OptionVal$Some$.MODULE$.apply(ex);
            Subscriber<Object> x$2 = establishing.copy$default$1();
            if (this.compareAndSet(establishing, establishing.copy(x$2, x$3 = establishing.copy$default$2(), x$1))) break;
        }
    }

    private final void rec$4(Throwable ex$1) {
        block13: {
            Object v;
            block14: {
                while ((v = this.get()) == null ? true : v instanceof Subscription) {
                    if (!this.compareAndSet(v, new ErrorPublisher(ex$1, "failed-VirtualProcessor"))) continue;
                    break block13;
                }
                if (!(v instanceof Subscriber)) break block14;
                Subscriber subscriber = (Subscriber)v;
                try {
                    BoxedUnit boxedUnit;
                    try {
                        subscriber.onError(ex$1);
                        boxedUnit = BoxedUnit.UNIT;
                    }
                    catch (Throwable throwable) {
                        Option<Throwable> option;
                        Throwable throwable2 = throwable;
                        if (throwable2 == null || (option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                            throw throwable;
                        }
                        boxedUnit = BoxedUnit.UNIT;
                    }
                }
                finally {
                    this.set(VirtualProcessor$Inert$.MODULE$);
                }
            }
            if (!(v instanceof Both)) break block13;
            Both both = (Both)v;
            Subscriber<Object> s2 = both.subscriber();
            try {
                BoxedUnit boxedUnit;
                try {
                    s2.onError(ex$1);
                    boxedUnit = BoxedUnit.UNIT;
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable3 = throwable;
                    if (throwable3 == null || (option = NonFatal$.MODULE$.unapply(throwable3)).isEmpty()) {
                        throw throwable;
                    }
                    boxedUnit = BoxedUnit.UNIT;
                }
            }
            finally {
                this.set(VirtualProcessor$Inert$.MODULE$);
            }
        }
    }

    private final void rec$5(Object t$1) {
        block8: {
            ErrorPublisher pub;
            Object v;
            do {
                if ((v = this.get()) instanceof HasActualSubscriber) {
                    HasActualSubscriber hasActualSubscriber = (HasActualSubscriber)v;
                    Subscriber<Object> s2 = hasActualSubscriber.subscriber();
                    try {
                        s2.onNext(t$1);
                        break block8;
                    }
                    catch (Throwable throwable) {
                        Option<Throwable> option;
                        Throwable throwable2 = throwable;
                        if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                            Throwable e = option.get();
                            this.set(VirtualProcessor$Inert$.MODULE$);
                            throw new IllegalStateException("Subscriber threw exception, this is in violation of rule 2:13", e);
                        }
                        throw throwable;
                    }
                }
                if (v instanceof Subscriber) {
                    Subscriber subscriber = (Subscriber)v;
                    IllegalStateException ex = new IllegalStateException(this.noDemand());
                    VirtualProcessor$Inert$ virtualProcessor$Inert$ = this.getAndSet(VirtualProcessor$Inert$.MODULE$);
                    if (VirtualProcessor$Inert$.MODULE$.equals(virtualProcessor$Inert$)) {
                    } else {
                        new ErrorPublisher(ex, "failed-VirtualProcessor").subscribe((Subscriber<?>)subscriber);
                    }
                    throw ex;
                }
                if (!(VirtualProcessor$Inert$.MODULE$.equals(v) ? true : v instanceof Publisher)) continue;
                break block8;
            } while (!this.compareAndSet(v, pub = new ErrorPublisher(new IllegalStateException(this.noDemand()), "failed-VirtualPublisher")));
            throw pub.t();
        }
    }

    public static final class Both
    implements HasActualSubscriber,
    Product,
    Serializable {
        private final Subscriber<Object> subscriber;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public Subscriber<Object> subscriber() {
            return this.subscriber;
        }

        public Both copy(Subscriber<Object> subscriber) {
            return new Both(subscriber);
        }

        public Subscriber<Object> copy$default$1() {
            return this.subscriber();
        }

        @Override
        public String productPrefix() {
            return "Both";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.subscriber();
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
            return x$1 instanceof Both;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "subscriber";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof Both)) return false;
            boolean bl = true;
            if (!bl) return false;
            Both both = (Both)x$1;
            Subscriber<Object> subscriber = this.subscriber();
            Subscriber<Object> subscriber2 = both.subscriber();
            if (subscriber != null) {
                if (!subscriber.equals(subscriber2)) return false;
                return true;
            }
            if (subscriber2 == null) return true;
            return false;
        }

        public Both(Subscriber<Object> subscriber) {
            this.subscriber = subscriber;
            Product.$init$(this);
        }
    }

    public static final class Establishing
    implements HasActualSubscriber,
    Product,
    Serializable {
        private final Subscriber<Object> subscriber;
        private final boolean onCompleteBuffered;
        private final Throwable onErrorBuffered;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public Subscriber<Object> subscriber() {
            return this.subscriber;
        }

        public boolean onCompleteBuffered() {
            return this.onCompleteBuffered;
        }

        public Throwable onErrorBuffered() {
            return this.onErrorBuffered;
        }

        public Establishing copy(Subscriber<Object> subscriber, boolean onCompleteBuffered, Throwable onErrorBuffered) {
            return new Establishing(subscriber, onCompleteBuffered, onErrorBuffered);
        }

        public Subscriber<Object> copy$default$1() {
            return this.subscriber();
        }

        public boolean copy$default$2() {
            return this.onCompleteBuffered();
        }

        public Throwable copy$default$3() {
            return this.onErrorBuffered();
        }

        @Override
        public String productPrefix() {
            return "Establishing";
        }

        @Override
        public int productArity() {
            return 3;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.subscriber();
                }
                case 1: {
                    return BoxesRunTime.boxToBoolean(this.onCompleteBuffered());
                }
                case 2: {
                    return new OptionVal<Throwable>(this.onErrorBuffered());
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
            return x$1 instanceof Establishing;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "subscriber";
                }
                case 1: {
                    return "onCompleteBuffered";
                }
                case 2: {
                    return "onErrorBuffered";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.anyHash(this.subscriber()));
            n = Statics.mix(n, this.onCompleteBuffered() ? 1231 : 1237);
            n = Statics.mix(n, Statics.anyHash(new OptionVal<Throwable>(this.onErrorBuffered())));
            return Statics.finalizeHash(n, 3);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof Establishing)) return false;
            boolean bl = true;
            if (!bl) return false;
            Establishing establishing = (Establishing)x$1;
            if (this.onCompleteBuffered() != establishing.onCompleteBuffered()) return false;
            Subscriber<Object> subscriber = this.subscriber();
            Subscriber<Object> subscriber2 = establishing.subscriber();
            if (subscriber == null) {
                if (subscriber2 != null) {
                    return false;
                }
            } else if (!subscriber.equals(subscriber2)) return false;
            Throwable throwable = this.onErrorBuffered();
            Throwable throwable2 = establishing.onErrorBuffered();
            if (throwable == null) {
                if (throwable2 == null) return true;
                return false;
            } else {
                if (!throwable.equals(throwable2)) return false;
                return true;
            }
        }

        public Establishing(Subscriber<Object> subscriber, boolean onCompleteBuffered, Throwable onErrorBuffered) {
            this.subscriber = subscriber;
            this.onCompleteBuffered = onCompleteBuffered;
            this.onErrorBuffered = onErrorBuffered;
            Product.$init$(this);
        }
    }

    public static interface HasActualSubscriber {
        public Subscriber<Object> subscriber();
    }

    private class WrappedSubscription
    extends AtomicReference<SubscriptionState>
    implements Subscription {
        private final Subscription real;
        public final /* synthetic */ VirtualProcessor $outer;

        public void ungateDemandAndRequestBuffered() {
            long requests = ((SubscriptionState)this.getAndSet(this.akka$stream$impl$VirtualProcessor$WrappedSubscription$$$outer().WrappedSubscription().PassThrough())).demand();
            if (requests > 0L) {
                this.real.request(requests);
                return;
            }
        }

        @Override
        public void request(long n) {
            if (n < 1L) {
                ReactiveStreamsCompliance$.MODULE$.tryCancel(this.real, new IllegalArgumentException(new StringBuilder(31).append("Demand must not be < 1 but was ").append(n).toString()));
                VirtualProcessor$Inert$ virtualProcessor$Inert$ = this.akka$stream$impl$VirtualProcessor$WrappedSubscription$$$outer().getAndSet(VirtualProcessor$Inert$.MODULE$);
                if (virtualProcessor$Inert$ instanceof Both) {
                    Both both = (Both)((Object)virtualProcessor$Inert$);
                    Subscriber<Object> subscriber = both.subscriber();
                    ReactiveStreamsCompliance$.MODULE$.rejectDueToNonPositiveDemand(subscriber);
                    return;
                }
                if (virtualProcessor$Inert$ instanceof Establishing) {
                    Establishing establishing = (Establishing)((Object)virtualProcessor$Inert$);
                    ReactiveStreamsCompliance$.MODULE$.rejectDueToNonPositiveDemand(establishing.subscriber());
                    return;
                }
                if (VirtualProcessor$Inert$.MODULE$.equals(virtualProcessor$Inert$)) {
                    return;
                }
                return;
            }
            this.bufferDemand$1(n);
        }

        @Override
        public void cancel() {
            this.akka$stream$impl$VirtualProcessor$WrappedSubscription$$$outer().set(VirtualProcessor$Inert$.MODULE$);
            this.real.cancel();
        }

        public /* synthetic */ VirtualProcessor akka$stream$impl$VirtualProcessor$WrappedSubscription$$$outer() {
            return this.$outer;
        }

        private final void bufferDemand$1(long n) {
            block1: {
                BoxedUnit boxedUnit;
                SubscriptionState current;
                do {
                    if ((current = (SubscriptionState)this.get()) != this.akka$stream$impl$VirtualProcessor$WrappedSubscription$$$outer().WrappedSubscription().PassThrough()) continue;
                    this.real.request(n);
                    boxedUnit = BoxedUnit.UNIT;
                    break block1;
                } while (!this.compareAndSet(current, new Buffering(this.akka$stream$impl$VirtualProcessor$WrappedSubscription$$$outer().WrappedSubscription(), current.demand() + n)));
                boxedUnit = BoxedUnit.UNIT;
            }
        }

        public WrappedSubscription(VirtualProcessor $outer, Subscription real) {
            this.real = real;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            super($outer.WrappedSubscription().NoBufferedDemand());
        }

        public class Buffering
        implements SubscriptionState,
        Product,
        Serializable {
            private final long demand;
            public final /* synthetic */ VirtualProcessor$WrappedSubscription$ $outer;

            @Override
            public Iterator<String> productElementNames() {
                return Product.productElementNames$(this);
            }

            @Override
            public long demand() {
                return this.demand;
            }

            public Buffering copy(long demand) {
                return new Buffering(this.akka$stream$impl$VirtualProcessor$WrappedSubscription$Buffering$$$outer(), demand);
            }

            public long copy$default$1() {
                return this.demand();
            }

            @Override
            public String productPrefix() {
                return "Buffering";
            }

            @Override
            public int productArity() {
                return 1;
            }

            @Override
            public Object productElement(int x$1) {
                int n = x$1;
                switch (n) {
                    case 0: {
                        return BoxesRunTime.boxToLong(this.demand());
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
                return x$1 instanceof Buffering;
            }

            @Override
            public String productElementName(int x$1) {
                int n = x$1;
                switch (n) {
                    case 0: {
                        return "demand";
                    }
                }
                return (String)Statics.ioobe(x$1);
            }

            public int hashCode() {
                int n = -889275714;
                n = Statics.mix(n, this.productPrefix().hashCode());
                n = Statics.mix(n, Statics.longHash(this.demand()));
                return Statics.finalizeHash(n, 1);
            }

            public String toString() {
                return ScalaRunTime$.MODULE$._toString(this);
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public boolean equals(Object x$1) {
                if (this == x$1) return true;
                Object object = x$1;
                if (!(object instanceof Buffering)) return false;
                if (((Buffering)object).akka$stream$impl$VirtualProcessor$WrappedSubscription$Buffering$$$outer() != this.akka$stream$impl$VirtualProcessor$WrappedSubscription$Buffering$$$outer()) return false;
                boolean bl = true;
                if (!bl) return false;
                Buffering buffering = (Buffering)x$1;
                if (this.demand() != buffering.demand()) return false;
                if (!buffering.canEqual(this)) return false;
                return true;
            }

            public /* synthetic */ VirtualProcessor$WrappedSubscription$ akka$stream$impl$VirtualProcessor$WrappedSubscription$Buffering$$$outer() {
                return this.$outer;
            }

            public Buffering(VirtualProcessor$WrappedSubscription$ $outer, long demand) {
                this.demand = demand;
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                Product.$init$(this);
            }
        }

        public interface SubscriptionState {
            public long demand();
        }
    }
}

