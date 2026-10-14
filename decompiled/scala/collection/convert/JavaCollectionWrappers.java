/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterable;
import scala.collection.AbstractIterator;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Map;
import scala.collection.Seq;
import scala.collection.Set;
import scala.collection.SetOps;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StrictOptimizedMapOps;
import scala.collection.StrictOptimizedSeqOps;
import scala.collection.StrictOptimizedSetOps;
import scala.collection.convert.AsJavaExtensions;
import scala.collection.convert.AsScalaExtensions;
import scala.collection.convert.JavaCollectionWrappers$;
import scala.collection.convert.JavaCollectionWrappers$MapWrapper$;
import scala.collection.convert.JavaCollectionWrappers$MapWrapper$$anon$2$;
import scala.collection.mutable.AbstractBuffer;
import scala.collection.mutable.AbstractSet;
import scala.collection.mutable.ArrayBuffer$;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.HashMap$;
import scala.collection.mutable.HashSet$;
import scala.collection.mutable.MapOps;
import scala.jdk.CollectionConverters$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ObjectRef;
import scala.util.Either;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.ControlThrowable;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005!\u0015tACAh\u0003#D\t!!6\u0002^\u001aQ\u0011\u0011]Ai\u0011\u0003\t).a9\t\u000f\u0005m\u0018\u0001\"\u0001\u0002\u0000\u001a1!\u0011A\u0001\u0001\u0005\u0007A!Ba\u0010\u0004\u0005\u000b\u0007I\u0011\u0001B!\u0011)\u0011Ie\u0001B\u0001B\u0003%!1\t\u0005\b\u0003w\u001cA\u0011\u0001B&\u0011\u001d\u0011\u0019f\u0001C\u0001\u0005+BqA!\u0018\u0004\t\u0003\u0011y\u0006C\u0004\u0003b\r!\tA!\u0016\t\u000f\t\r4\u0001\"\u0001\u0003`!9!QM\u0002\u0005B\t\u001ddA\u0002B;\u0003\u0001\u00119\b\u0003\u0006\u0003@1\u0011)\u0019!C\u0001\u0005\u000fC!B!\u0013\r\u0005\u0003\u0005\u000b\u0011\u0002BE\u0011\u001d\tY\u0010\u0004C\u0001\u0005\u0017CqAa\u0015\r\t\u0003\u0011\t\nC\u0004\u0003^1!\tAa%\u0007\r\t]\u0015\u0001\u0001BM\u0011)\u0011yD\u0005BC\u0002\u0013\u0005!Q\u0015\u0005\u000b\u0005\u0013\u0012\"\u0011!Q\u0001\n\t\u001d\u0006bBA~%\u0011\u0005!\u0011\u0016\u0005\b\u0005'\u0012B\u0011\u0001BI\u0011\u001d\u0011iF\u0005C\u0001\u0005_3\u0011Ba-\u0002!\u0003\r\tA!.\t\u000f\t\r\u0007\u0004\"\u0001\u0003F\"I!q\b\rC\u0002\u001b\u0005!Q\u001a\u0005\b\u0005+DB\u0011\u0001Bl\u0011\u001d\u0011y\u000e\u0007C!\u0005CDqA!:\u0019\t\u0003\u0012)F\u0002\u0004\u0003h\u0006\u0001!\u0011\u001e\u0005\u000b\u0005\u007fq\"Q1A\u0005\u0002\tU\bB\u0003B%=\t\u0005\t\u0015!\u0003\u0003x\"9\u00111 \u0010\u0005\u0002\tehABB\u0001\u0003\u0001\u0019\u0019\u0001\u0003\u0006\u0003@\t\u0012)\u0019!C\u0001\u00077A!B!\u0013#\u0005\u0003\u0005\u000b\u0011BB\u000f\u0011\u001d\tYP\tC\u0001\u0007CAqAa8#\t\u0003\u00199\u0003C\u0004\u0004,\t\"\te!\f\t\u000f\t\u0015(\u0005\"\u0011\u0003\u0012\u001a11QH\u0001\u0001\u0007\u007fA!Ba\u0010*\u0005\u000b\u0007I\u0011AB'\u0011)\u0011I%\u000bB\u0001B\u0003%1q\n\u0005\b\u0003wLC\u0011AB+\u0011\u001d\u0011y.\u000bC\u0001\u00077BqA!6*\t\u0003\u001ay\u0006C\u0004\u0004b%\"\tea\u0018\t\u000f\t\u0015\u0018\u0006\"\u0011\u0003\u0012\"911F\u0015\u0005B\r5bABB3\u0003\u0001\u00199\u0007\u0003\u0006\u0003@I\u0012)\u0019!C\u0001\u0007oB!B!\u00133\u0005\u0003\u0005\u000b\u0011BB=\u0011\u001d\tYP\rC\u0001\u0007\u007fBqa!\"3\t\u0003\u00199I\u0002\u0004\u0004\u0010\u0006\u00011\u0011\u0013\u0005\u000b\u0005\u007f9$Q1A\u0005\u0002\ru\u0005B\u0003B%o\t\u0005\t\u0015!\u0003\u0004 \"9\u00111`\u001c\u0005\u0002\r\r\u0006bBBCo\u0011\u00051\u0011\u0016\u0005\b\u0007[;D\u0011IBX\r\u0019\u0019I,\u0001\u0001\u0004<\"Q!qH\u001f\u0003\u0006\u0004%\taa2\t\u0015\t%SH!A!\u0002\u0013\u0019I\rC\u0004\u0002|v\"\taa4\t\u000f\r\u0015U\b\"\u0001\u0004V\"91QV\u001f\u0005B\re\u0007bBBp{\u0011\u00053\u0011\u001d\u0005\b\u0005KjD\u0011IBs\r\u0019\u0019Y/\u0001\u0001\u0004n\"Q!qH#\u0003\u0006\u0004%\t\u0001\"\u0005\t\u0015\t%SI!A!\u0002\u0013!\u0019\u0002C\u0004\u0002|\u0016#\t\u0001\"\u0007\t\u000f\u0011}Q\t\"\u0001\u0004`!91\u0011M#\u0005B\r}\u0003b\u0002Bs\u000b\u0012\u0005#\u0011\u0013\u0005\b\u0005?,E\u0011\tC\u0011\u0011\u001d!)#\u0012C\u0001\tOAq\u0001b\u000bF\t\u0003!i\u0003C\u0004\u00054\u0015#\t\u0001\"\u000e\t\u000f\u0011mR\t\"\u0001\u0005>!9A\u0011I#\u0005\u0002\u0011\r\u0003b\u0002C&\u000b\u0012\u0005AQ\n\u0005\b\u0005K*E\u0011\u0001C.\u0011\u001d!y&\u0012C\u0001\u0005\u000bDq\u0001\"\u0019F\t\u0003\"\u0019\u0007C\u0004\u0005f\u0015#\t\u0001b\u001a\t\u000f\t\u0015T\t\"\u0001\u0005v!911F#\u0005B\r5\u0002b\u0002C?\u000b\u0012\u0005Cq\u0010\u0004\u0007\t\u000b\u000b\u0001\u0001b\"\t\u0015\t}\"L!A!\u0002\u0013!)\nC\u0004\u0002|j#\t\u0001b'\t\u000f\u0011\u0005&\f\"\u0011\u0005$\"9!Q\u001d.\u0005B\tU\u0003b\u0002Bk5\u0012\u0005!q\u001b\u0005\b\u0005?TF\u0011\u0001CU\r\u0019!),\u0001\u0001\u00058\"Q!qH1\u0003\u0006\u0004%\t\u0001\"1\t\u0015\t%\u0013M!A!\u0002\u0013!\u0019\rC\u0004\u0002|\u0006$\t\u0001b2\t\u000f\r}\u0017\r\"\u0011\u0005N\"9!QM1\u0005B\u0011E\u0007b\u0002C0C\u0012\u0005#Q\u0019\u0004\u0007\t/\f\u0001\u0001\"7\t\u0015\t}\u0002N!b\u0001\n\u0003!)\u0010\u0003\u0006\u0003J!\u0014\t\u0011)A\u0005\toDq!a?i\t\u0003!Y\u0010C\u0004\u0003V\"$\tea\u0018\t\u000f\t\u0015\b\u000e\"\u0011\u0003\u0012\"91\u0011\r5\u0005B\r}\u0003b\u0002BpQ\u0012\u0005Q\u0011\u0001\u0005\b\tCCG\u0011AC\u0003\u0011\u001d!Y\u0004\u001bC\u0001\u000b\u0013Aq\u0001\" i\t\u0003)y\u0001C\u0004\u0003f!$\t%b\u0005\t\u000f\u0011}\u0003\u000e\"\u0011\u0003F\"9Qq\u00035\u0005B\u0015e\u0001b\u0002C1Q\u0012\u0005S1\u0004\u0005\b\u0007WAG\u0011IC\u000f\u0011\u001d))\u0003\u001bC!\u000bO1a!\"\u000e\u0002\u0001\u0015]\u0002B\u0003B s\n\u0005\t\u0015!\u0003\u0006N!9\u00111`=\u0005\u0002\u0015M\u0003b\u0002Bks\u0012\u0005#q\u001b\u0005\b\u0007\u000bKH\u0011IC-\u0011\u001d)y&\u001fC!\u000bCBq!\"\u001fz\t\u0003*YH\u0002\u0004\u0006\u0002\u0006\u0001Q1\u0011\u0005\f\u0005\u007f\t\tA!b\u0001\n\u0003)\t\nC\u0006\u0003J\u0005\u0005!\u0011!Q\u0001\n\u0015M\u0005\u0002CA~\u0003\u0003!\t!b&\t\u0011\u0015u\u0015\u0011\u0001C!\u000b?C\u0001B!\u001a\u0002\u0002\u0011\u0005S\u0011\u0016\u0005\t\t?\n\t\u0001\"\u0011\u0003F\u001a9QqV\u0001\u0002\u0002\u0015E\u0006\u0002CA~\u0003\u001f!\tA\"\u001f\u0007\u0013\u0015\r\u0017\u0001%A\u0002\u0002\u0015\u0015\u0007\u0002\u0003Bb\u0003'!\tA!2\t\u0011\t}\u00121\u0003D\u0001\r#A\u0001B!6\u0002\u0014\u0011\u00053q\f\u0005\t\u0007\u000b\u000b\u0019\u0002\"\u0001\u0007\u0018!Aa\u0011EA\n\t\u00032\u0019\u0003\u0003\u0005\u0005<\u0005MA\u0011\u0001D\u0019\u0011!!i(a\u0005\u0005\u0002\u0019e\u0002\u0002CCO\u0003'!\tE\"\u0010\t\u0011\u0011-\u00121\u0003C!\r\u0007B\u0001B\"\u0013\u0002\u0014\u0011\u0005c1\n\u0005\t\u0005K\n\u0019\u0002\"\u0011\u0007X!A!q\\A\n\t\u00031Y\u0006\u0003\u0005\u0007`\u0005MA\u0011\tD1\u0011!!y&a\u0005\u0005B\t\u0015gA\u0002D@\u0003\u00011\t\tC\u0006\u0003@\u0005E\"Q1A\u0005\u0002\u0019=\u0005b\u0003B%\u0003c\u0011\t\u0011)A\u0005\r#C\u0001\"a?\u00022\u0011\u0005a1\u0013\u0005\t\u0005K\f\t\u0004\"\u0011\u0003\u0012\"A1\u0011MA\u0019\t\u0003\u001ay\u0006\u0003\u0005\u0006\u0018\u0005EB\u0011\tDM\r\u00191i*\u0001\u0001\u0007 \"i!qHA \u0005\u0003\u0005\u000b\u0011\u0002D]\u0003\u0007A\u0001\"a?\u0002@\u0011\u0005a\u0011\u0019\u0005\t\r\u000f\fy\u0004\"\u0001\u0007J\"Aa1ZA \t\u00032i\r\u0003\u0005\u0003f\u0005}B\u0011\tDj\u0011!1I.a\u0010\u0005B\u0019m\u0007\u0002\u0003Dm\u0003\u007f!\tE\"9\u0007\r\u0019=\u0018\u0001\u0001Dy\u0011-\u0011y$a\u0014\u0003\u0006\u0004%\ta\"\u0001\t\u0017\t%\u0013q\nB\u0001B\u0003%q1\u0001\u0005\t\u0003w\fy\u0005\"\u0001\b\u0006!A1QQA(\t\u0003:Y\u0001\u0003\u0005\u0007\"\u0005=C\u0011ID\t\u0011!\u0011)/a\u0014\u0005B\tE\u0005\u0002CB1\u0003\u001f\"\tea\u0018\t\u0011\u0015]\u0011q\nC!\u000f3A\u0001Bb3\u0002P\u0011\u0005q1\u0004\u0005\t\u0005K\ny\u0005\"\u0001\b\"!Aa\u0011\\A(\t\u000399\u0003\u0003\u0005\u0007Z\u0006=C\u0011AD\u0017\u0011!9I$a\u0014\u0005B\u001dm\u0002\u0002\u0003D%\u0003\u001f\"\te\"\u0011\u0007\r\u001d5\u0013\u0001AD(\u0011-\u0011y$!\u001c\u0003\u0006\u0004%\ta\"\u0019\t\u0017\t%\u0013Q\u000eB\u0001B\u0003%q1\r\u0005\t\u0003w\fi\u0007\"\u0001\bf!A!Q[A7\t\u0003\u00119\u000e\u0003\u0005\u0003f\u00065D\u0011\u0001B+\u0011!9Y'!\u001c\u0005\u0002\u001d5\u0004\u0002CD9\u0003[\"\tab\u001d\t\u0011\r\u0015\u0015Q\u000eC\u0001\u000foB\u0001\"\"(\u0002n\u0011\u0005q1\u0010\u0005\t\u0005K\ni\u0007\"\u0011\b\u0002\u001a1qqQ\u0001\u0001\u000f\u0013C1Ba\u0010\u0002\u0004\n\u0015\r\u0011\"\u0001\b\u0018\"Y!\u0011JAB\u0005\u0003\u0005\u000b\u0011BDM\u0011!\tY0a!\u0005\u0002\u001dm\u0005\u0002\u0003Bk\u0003\u0007#\tea\u0018\t\u0011\t\u0015\u00181\u0011C!\u0005#C\u0001b!\u0019\u0002\u0004\u0012\u00053q\f\u0005\t\u0007\u000b\u000b\u0019\t\"\u0001\b\"\"AA1HAB\t\u000399\u000b\u0003\u0005\u0005~\u0005\rE\u0011ADX\u0011!)i*a!\u0005B\u001dM\u0006\u0002\u0003C\u0016\u0003\u0007#\te\"/\t\u0011\t\u0015\u00141\u0011C!\u000f\u007fC\u0001Ba8\u0002\u0004\u0012\u0005q1\u0019\u0005\t\t?\n\u0019\t\"\u0011\u0003F\"AqqYAB\t\u0003:IM\u0002\u0004\bT\u0006\u0001qQ\u001b\u0005\f\u0005\u007f\t\u0019K!A!\u0002\u00139I\u0010\u0003\u0005\u0002|\u0006\rF\u0011AD\u0000\u0011!\u0011).a)\u0005B\r}\u0003\u0002\u0003Bs\u0003G#\tE!%\t\u0011\r\u0005\u00141\u0015C!\u0007?B\u0001b!\"\u0002$\u0012\u0005\u0001R\u0001\u0005\t\tw\t\u0019\u000b\"\u0001\t\f!AAQPAR\t\u0003A\t\u0002\u0003\u0005\u0006\u001e\u0006\rF\u0011\tE\u000b\u0011!!Y#a)\u0005B!m\u0001\u0002\u0003B3\u0003G#\t\u0005#\t\t\u0011\t}\u00171\u0015C\u0001\u0011KA\u0001\u0002b\u0018\u0002$\u0012\u0005#Q\u0019\u0005\t\u000b/\t\u0019\u000b\"\u0011\t*!A\u00012FAR\t\u0003Ai\u0003\u0003\u0005\t,\u0005\rF\u0011\u0001E\u001b\u0011!Ai$a)\u0005\u0002!}\u0002\u0002CDd\u0003G#\te\"3\t\u0013!%\u0013A1A\u0005\n!-\u0003\u0002\u0003E.\u0003\u0001\u0006I\u0001#\u0014\t\u0013!u\u0013!!A\u0005\n!}\u0013A\u0006&bm\u0006\u001cu\u000e\u001c7fGRLwN\\,sCB\u0004XM]:\u000b\t\u0005M\u0017Q[\u0001\bG>tg/\u001a:u\u0015\u0011\t9.!7\u0002\u0015\r|G\u000e\\3di&|gN\u0003\u0002\u0002\\\u0006)1oY1mCB\u0019\u0011q\\\u0001\u000e\u0005\u0005E'A\u0006&bm\u0006\u001cu\u000e\u001c7fGRLwN\\,sCB\u0004XM]:\u0014\u000b\u0005\t)/!<\u0011\t\u0005\u001d\u0018\u0011^\u0007\u0003\u00033LA!a;\u0002Z\n1\u0011I\\=SK\u001a\u0004B!a<\u0002v:!\u0011q]Ay\u0013\u0011\t\u00190!7\u0002\u000fA\f7m[1hK&!\u0011q_A}\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\u0011\t\u00190!7\u0002\rqJg.\u001b;?\u0007\u0001!\"!!8\u0003\u001f%#XM]1u_J<&/\u00199qKJ,BA!\u0002\u0003(MI1Aa\u0002\u0003\u0018\te\u0012Q\u001e\t\u0005\u0005\u0013\u0011\u0019\"\u0004\u0002\u0003\f)!!Q\u0002B\b\u0003\u0011a\u0017M\\4\u000b\u0005\tE\u0011\u0001\u00026bm\u0006LAA!\u0006\u0003\f\t1qJ\u00196fGR\u0004bA!\u0007\u0003 \t\rRB\u0001B\u000e\u0015\u0011\u0011iBa\u0004\u0002\tU$\u0018\u000e\\\u0005\u0005\u0005C\u0011YB\u0001\u0005Ji\u0016\u0014\u0018\r^8s!\u0011\u0011)Ca\n\r\u0001\u00119!\u0011F\u0002C\u0002\t-\"!A!\u0012\t\t5\"1\u0007\t\u0005\u0003O\u0014y#\u0003\u0003\u00032\u0005e'a\u0002(pi\"Lgn\u001a\t\u0005\u0003O\u0014)$\u0003\u0003\u00038\u0005e'aA!osB1!\u0011\u0004B\u001e\u0005GIAA!\u0010\u0003\u001c\tYQI\\;nKJ\fG/[8o\u0003))h\u000eZ3sYfLgnZ\u000b\u0003\u0005\u0007\u0002bA!\u0012\u0003H\t\rRBAAk\u0013\u0011\u0011\t#!6\u0002\u0017UtG-\u001a:ms&tw\r\t\u000b\u0005\u0005\u001b\u0012\t\u0006E\u0003\u0003P\r\u0011\u0019#D\u0001\u0002\u0011\u001d\u0011yD\u0002a\u0001\u0005\u0007\nq\u0001[1t\u001d\u0016DH\u000f\u0006\u0002\u0003XA!\u0011q\u001dB-\u0013\u0011\u0011Y&!7\u0003\u000f\t{w\u000e\\3b]\u0006!a.\u001a=u)\t\u0011\u0019#A\biCNluN]3FY\u0016lWM\u001c;t\u0003-qW\r\u001f;FY\u0016lWM\u001c;\u0002\rI,Wn\u001c<f)\t\u0011i\u0003K\u0004\u0004\u0005W\u0012\tHa\u001d\u0011\t\u0005\u001d(QN\u0005\u0005\u0005_\nIN\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t1A\u0001\tK\u0013R,'/\u0019;pe^\u0013\u0018\r\u001d9feV!!\u0011\u0010BB'\u001da!1\u0010BC\u0003[\u0004bA!\u0012\u0003~\t\u0005\u0015\u0002\u0002B@\u0003+\u0014\u0001#\u00112tiJ\f7\r^%uKJ\fGo\u001c:\u0011\t\t\u0015\"1\u0011\u0003\b\u0005Sa!\u0019\u0001B\u0016!\u0019\u0011)Ea\u0012\u0003\u0002V\u0011!\u0011\u0012\t\u0007\u00053\u0011yB!!\u0015\t\t5%q\u0012\t\u0006\u0005\u001fb!\u0011\u0011\u0005\b\u0005\u007fy\u0001\u0019\u0001BE+\t\u00119\u0006\u0006\u0002\u0003\u0002\":ABa\u001b\u0003r\tM$a\u0005&F]VlWM]1uS>twK]1qa\u0016\u0014X\u0003\u0002BN\u0005C\u001brA\u0005BO\u0005G\u000bi\u000f\u0005\u0004\u0003F\tu$q\u0014\t\u0005\u0005K\u0011\t\u000bB\u0004\u0003*I\u0011\rAa\u000b\u0011\r\t\u0015#q\tBP+\t\u00119\u000b\u0005\u0004\u0003\u001a\tm\"q\u0014\u000b\u0005\u0005W\u0013i\u000bE\u0003\u0003PI\u0011y\nC\u0004\u0003@U\u0001\rAa*\u0015\u0005\t}\u0005f\u0002\n\u0003l\tE$1\u000f\u0002\u0015\u0013R,'/\u00192mK^\u0013\u0018\r\u001d9feR\u0013\u0018-\u001b;\u0016\t\t]&\u0011Y\n\u00041\te\u0006C\u0002B\r\u0005w\u0013y,\u0003\u0003\u0003>\nm!AE!cgR\u0014\u0018m\u0019;D_2dWm\u0019;j_:\u0004BA!\n\u0003B\u00129!\u0011\u0006\rC\u0002\t-\u0012A\u0002\u0013j]&$H\u0005\u0006\u0002\u0003HB!\u0011q\u001dBe\u0013\u0011\u0011Y-!7\u0003\tUs\u0017\u000e^\u000b\u0003\u0005\u001f\u0004bA!\u0012\u0003R\n}\u0016\u0002\u0002Bj\u0003+\u0014\u0001\"\u0013;fe\u0006\u0014G.Z\u0001\u0005g&TX\r\u0006\u0002\u0003ZB!\u0011q\u001dBn\u0013\u0011\u0011i.!7\u0003\u0007%sG/\u0001\u0005ji\u0016\u0014\u0018\r^8s)\t\u0011\u0019\u000fE\u0003\u0003P\r\u0011y,A\u0004jg\u0016k\u0007\u000f^=\u0003\u001f%#XM]1cY\u0016<&/\u00199qKJ,BAa;\u0003rN9aD!<\u0003t\u00065\bC\u0002B\r\u0005w\u0013y\u000f\u0005\u0003\u0003&\tEHa\u0002B\u0015=\t\u0007!1\u0006\t\u0006\u0005\u001fB\"q^\u000b\u0003\u0005o\u0004bA!\u0012\u0003R\n=H\u0003\u0002B~\u0005{\u0004RAa\u0014\u001f\u0005_DqAa\u0010\"\u0001\u0004\u00119\u0010K\u0004\u001f\u0005W\u0012\tHa\u001d\u0003!)KE/\u001a:bE2,wK]1qa\u0016\u0014X\u0003BB\u0003\u0007\u001f\u0019rAIB\u0004\u0007#\ti\u000f\u0005\u0004\u0003F\r%1QB\u0005\u0005\u0007\u0017\t)N\u0001\tBEN$(/Y2u\u0013R,'/\u00192mKB!!QEB\b\t\u001d\u0011IC\tb\u0001\u0005W\u0001\"B!\u0012\u0004\u0014\r51qCB\r\u0013\u0011\u0019)\"!6\u00035M#(/[2u\u001fB$\u0018.\\5{K\u0012LE/\u001a:bE2,w\n]:\u0011\t\t\u0015#\u0011\u001b\t\u0007\u0005\u000b\u0012\tn!\u0004\u0016\u0005\ru\u0001C\u0002B\u0005\u0007?\u0019i!\u0003\u0003\u0003T\n-A\u0003BB\u0012\u0007K\u0001RAa\u0014#\u0007\u001bAqAa\u0010&\u0001\u0004\u0019i\"\u0006\u0002\u0004*A1!Q\tB$\u0007\u001b\tq\"\u001b;fe\u0006\u0014G.\u001a$bGR|'/_\u000b\u0003\u0007_\u0001Ba!\r\u000485\u001111\u0007\u0006\u0005\u0007k\t).A\u0004nkR\f'\r\\3\u000b\t\re21G\u0001\f\u0003J\u0014\u0018-\u001f\"vM\u001a,'\u000fK\u0004#\u0005W\u0012\tHa\u001d\u0003%)\u001bu\u000e\u001c7fGRLwN\\,sCB\u0004XM]\u000b\u0005\u0007\u0003\u001a9eE\u0004*\u0007\u0007\u001aI%!<\u0011\r\t\u00153\u0011BB#!\u0011\u0011)ca\u0012\u0005\u000f\t%\u0012F1\u0001\u0003,AQ!QIB\n\u0007\u000b\u001a9ba\u0013\u0011\r\t\u0015#\u0011[B#+\t\u0019y\u0005\u0005\u0004\u0003\u001a\rE3QI\u0005\u0005\u0007'\u0012YB\u0001\u0006D_2dWm\u0019;j_:$Baa\u0016\u0004ZA)!qJ\u0015\u0004F!9!q\b\u0017A\u0002\r=SCAB/!\u0019\u0011)Ea\u0012\u0004FU\u0011!\u0011\\\u0001\nW:|wO\\*ju\u0016Ds!\u000bB6\u0005c\u0012\u0019H\u0001\u0006TKF<&/\u00199qKJ,Ba!\u001b\u0004tM9!ga\u001b\u0004v\u00055\bC\u0002B\r\u0007[\u001a\t(\u0003\u0003\u0004p\tm!\u0001D!cgR\u0014\u0018m\u0019;MSN$\b\u0003\u0002B\u0013\u0007g\"qA!\u000b3\u0005\u0004\u0011Y\u0003E\u0003\u0003Pa\u0019\t(\u0006\u0002\u0004zA1!QIB>\u0007cJAa! \u0002V\n\u00191+Z9\u0015\t\r\u000551\u0011\t\u0006\u0005\u001f\u00124\u0011\u000f\u0005\b\u0005\u007f)\u0004\u0019AB=\u0003\r9W\r\u001e\u000b\u0005\u0007c\u001aI\tC\u0004\u0004\fZ\u0002\rA!7\u0002\u0003%DsA\rB6\u0005c\u0012\u0019HA\tNkR\f'\r\\3TKF<&/\u00199qKJ,Baa%\u0004\u001aN9qg!&\u0004\u001c\u00065\bC\u0002B\r\u0007[\u001a9\n\u0005\u0003\u0003&\reEa\u0002B\u0015o\t\u0007!1\u0006\t\u0006\u0005\u001fB2qS\u000b\u0003\u0007?\u0003ba!\r\u0004\"\u000e]\u0015\u0002BB?\u0007g!Ba!*\u0004(B)!qJ\u001c\u0004\u0018\"9!q\b\u001eA\u0002\r}E\u0003BBL\u0007WCqaa#<\u0001\u0004\u0011I.A\u0002tKR$baa&\u00042\u000eM\u0006bBBFy\u0001\u0007!\u0011\u001c\u0005\b\u0007kc\u0004\u0019ABL\u0003\u0011)G.Z7)\u000f]\u0012YG!\u001d\u0003t\t!R*\u001e;bE2,')\u001e4gKJ<&/\u00199qKJ,Ba!0\u0004DN9Qha0\u0004F\u00065\bC\u0002B\r\u0007[\u001a\t\r\u0005\u0003\u0003&\r\rGa\u0002B\u0015{\t\u0007!1\u0006\t\u0006\u0005\u001fB2\u0011Y\u000b\u0003\u0007\u0013\u0004ba!\r\u0004L\u000e\u0005\u0017\u0002BBg\u0007g\u0011aAQ;gM\u0016\u0014H\u0003BBi\u0007'\u0004RAa\u0014>\u0007\u0003DqAa\u0010A\u0001\u0004\u0019I\r\u0006\u0003\u0004B\u000e]\u0007bBBF\u0003\u0002\u0007!\u0011\u001c\u000b\u0007\u0007\u0003\u001cYn!8\t\u000f\r-%\t1\u0001\u0003Z\"91Q\u0017\"A\u0002\r\u0005\u0017aA1eIR!!qKBr\u0011\u001d\u0019)l\u0011a\u0001\u0007\u0003$Ba!1\u0004h\"911\u0012#A\u0002\te\u0007fB\u001f\u0003l\tE$1\u000f\u0002\r\u00152K7\u000f^,sCB\u0004XM]\u000b\u0005\u0007_\u001cIpE\u0006F\u0007c\u001cY\u0010\"\u0002\u0005\f\u00055\bCBB\u0019\u0007g\u001c90\u0003\u0003\u0004v\u000eM\"AD!cgR\u0014\u0018m\u0019;Ck\u001a4WM\u001d\t\u0005\u0005K\u0019I\u0010B\u0004\u0003*\u0015\u0013\rAa\u000b\u0011\u0015\t\u00153Q`B|\t\u0003!\u0019!\u0003\u0003\u0004\u0000\u0006U'AB*fc>\u00038\u000f\u0005\u0003\u00042\r-\u0007CBB\u0019\u0007\u0017\u001c9\u0010\u0005\u0006\u0003F\u0011\u001d1q\u001fC\u0001\t\u0007IA\u0001\"\u0003\u0002V\n)2\u000b\u001e:jGR|\u0005\u000f^5nSj,GmU3r\u001fB\u001c\b\u0003\u0003B#\t\u001b\u00199\u0010\"\u0001\n\t\u0011=\u0011Q\u001b\u0002\u0018\u0013R,'/\u00192mK\u001a\u000b7\r^8ss\u0012+g-Y;miN,\"\u0001b\u0005\u0011\r\teAQCB|\u0013\u0011!9Ba\u0007\u0003\t1K7\u000f\u001e\u000b\u0005\t7!i\u0002E\u0003\u0003P\u0015\u001b9\u0010C\u0004\u0003@!\u0003\r\u0001b\u0005\u0002\r1,gn\u001a;i+\t!\u0019\u0003\u0005\u0004\u0003F\t\u001d3q_\u0001\u0006CB\u0004H.\u001f\u000b\u0005\u0007o$I\u0003C\u0004\u0004\f6\u0003\rA!7\u0002\rU\u0004H-\u0019;f)\u0019\u00119\rb\f\u00052!911\u0012(A\u0002\te\u0007bBB[\u001d\u0002\u00071q_\u0001\baJ,\u0007/\u001a8e)\u0011!9\u0004\"\u000f\u000e\u0003\u0015Cqa!.P\u0001\u0004\u001990\u0001\u0004bI\u0012|e.\u001a\u000b\u0005\to!y\u0004C\u0004\u00046B\u0003\raa>\u0002\r%t7/\u001a:u)\u0019\u00119\r\"\u0012\u0005J!9AqI)A\u0002\te\u0017aA5eq\"91QW)A\u0002\r]\u0018!C5og\u0016\u0014H/\u00117m)\u0019\u00119\rb\u0014\u0005R!911\u0012*A\u0002\te\u0007b\u0002C*%\u0002\u0007AQK\u0001\u0006K2,Wn\u001d\t\u0007\u0005\u000b\"9fa>\n\t\u0011e\u0013Q\u001b\u0002\r\u0013R,'/\u00192mK>s7-\u001a\u000b\u0005\u0007o$i\u0006C\u0004\u0004\fN\u0003\rA!7\u0002\u000b\rdW-\u0019:\u0002\u000b\rdwN\\3\u0015\u0005\u0011m\u0011\u0001\u00049bi\u000eD\u0017J\u001c)mC\u000e,G\u0003\u0003C\u001c\tS\"i\u0007\"\u001d\t\u000f\u0011-d\u000b1\u0001\u0003Z\u0006!aM]8n\u0011\u001d!yG\u0016a\u0001\t+\nQ\u0001]1uG\"Dq\u0001b\u001dW\u0001\u0004\u0011I.\u0001\u0005sKBd\u0017mY3e)\u0019\u00119\rb\u001e\u0005z!9A1N,A\u0002\te\u0007b\u0002C>/\u0002\u0007!\u0011\\\u0001\u0002]\u0006Y1/\u001e2ue\u0006\u001cGo\u00148f)\u0011!9\u0004\"!\t\u000f\rU\u0016\f1\u0001\u0004x\":QIa\u001b\u0003r\tM$AC*fi^\u0013\u0018\r\u001d9feV!A\u0011\u0012CJ'\u0015QF1RAw!\u0019\u0011I\u0002\"$\u0005\u0012&!Aq\u0012B\u000e\u0005-\t%m\u001d;sC\u000e$8+\u001a;\u0011\t\t\u0015B1\u0013\u0003\b\u0005SQ&\u0019\u0001B\u0016!\u0019\u0011)\u0005b&\u0005\u0012&!A\u0011TAk\u0005\r\u0019V\r\u001e\u000b\u0005\t;#y\nE\u0003\u0003Pi#\t\nC\u0004\u0003@q\u0003\r\u0001\"&\u0002\u0011\r|g\u000e^1j]N$BAa\u0016\u0005&\"9AqU/A\u0002\t\u001d\u0011!A8\u0015\u0005\u0011-&C\u0002CW\u0005\u000f!\tL\u0002\u0004\u00050\u0002\u0004A1\u0016\u0002\ryI,g-\u001b8f[\u0016tGO\u0010\t\u0007\u00053\u0011y\u0002\"%)\u000fi\u0013YG!\u001d\u0003t\t\tR*\u001e;bE2,7+\u001a;Xe\u0006\u0004\b/\u001a:\u0016\t\u0011eFqX\n\u0006C\u0012m\u0016Q\u001e\t\u0006\u0005\u001fRFQ\u0018\t\u0005\u0005K!y\fB\u0004\u0003*\u0005\u0014\rAa\u000b\u0016\u0005\u0011\r\u0007CBB\u0019\t\u000b$i,\u0003\u0003\u0005\u001a\u000eMB\u0003\u0002Ce\t\u0017\u0004RAa\u0014b\t{CqAa\u0010e\u0001\u0004!\u0019\r\u0006\u0003\u0003X\u0011=\u0007bBB[K\u0002\u0007AQ\u0018\u000b\u0005\u0005/\"\u0019\u000eC\u0004\u00046\u001a\u0004\r!!:)\u000f\u0005\u0014YG!\u001d\u0003t\tY!jU3u/J\f\u0007\u000f]3s+\u0011!Y\u000eb9\u0014\u0013!$i\u000e\":\u0005p\u00065\bCBB\u0019\t?$\t/\u0003\u0003\u0005\u0010\u000eM\u0002\u0003\u0002B\u0013\tG$qA!\u000bi\u0005\u0004\u0011Y\u0003\u0005\u0006\u00042\u0011\u001dH\u0011\u001dCv\t[LA\u0001\";\u00044\t11+\u001a;PaN\u0004Ba!\r\u0005FB11\u0011\u0007Cc\tC\u0004\"B!\u0012\u0005r\u0012\u0005H1\u001eCw\u0013\u0011!\u00190!6\u0003+M#(/[2u\u001fB$\u0018.\\5{K\u0012\u001cV\r^(qgV\u0011Aq\u001f\t\u0007\u00053!I\u0010\"9\n\t\u0011e%1\u0004\u000b\u0005\t{$y\u0010E\u0003\u0003P!$\t\u000fC\u0004\u0003@-\u0004\r\u0001b>\u0016\u0005\u0015\r\u0001C\u0002B#\u0005\u000f\"\t\u000f\u0006\u0003\u0003X\u0015\u001d\u0001bBB[a\u0002\u0007A\u0011\u001d\u000b\u0005\u000b\u0017)i!D\u0001i\u0011\u001d\u0019),\u001da\u0001\tC$B!b\u0003\u0006\u0012!91Q\u0017:A\u0002\u0011\u0005H\u0003\u0002B,\u000b+Aqa!.t\u0001\u0004!\t/A\u0003f[B$\u00180\u0006\u0002\u0005nR\u0011AQ^\u000b\u0003\u000b?\u0001bA!\u0012\u0006\"\u0011-\u0018\u0002BC\u0012\u0003+\u0014q\"\u0013;fe\u0006\u0014G.\u001a$bGR|'/_\u0001\u000eM&dG/\u001a:J]Bc\u0017mY3\u0015\t\u0015-Q\u0011\u0006\u0005\b\u000bWA\b\u0019AC\u0017\u0003\u0005\u0001\b\u0003CAt\u000b_!\tOa\u0016\n\t\u0015E\u0012\u0011\u001c\u0002\n\rVt7\r^5p]FBs\u0001\u001bB6\u0005c\u0012\u0019H\u0001\u0006NCB<&/\u00199qKJ,b!\"\u000f\u0006D\u0015%3#B=\u0006<\u00055\b\u0003\u0003B\r\u000b{)\t%b\u0012\n\t\u0015}\"1\u0004\u0002\f\u0003\n\u001cHO]1di6\u000b\u0007\u000f\u0005\u0003\u0003&\u0015\rCaBC#s\n\u0007!1\u0006\u0002\u0002\u0017B!!QEC%\t\u001d)Y%\u001fb\u0001\u0005W\u0011\u0011A\u0016\t\t\u0005\u000b*y%\"\u0011\u0006H%!Q\u0011KAk\u0005\ri\u0015\r\u001d\u000b\u0005\u000b+*9\u0006E\u0004\u0003Pe,\t%b\u0012\t\u000f\t}2\u00101\u0001\u0006NQ!QqIC.\u0011\u001d)i& a\u0001\u0003K\f1a[3z\u0003!)g\u000e\u001e:z'\u0016$HCAC2!\u0019\u0011I\u0002\"?\u0006fAAQqMC:\u000b\u0003*9E\u0004\u0003\u0006j\u0015=d\u0002BC6\u000b[j!Aa\u0004\n\t\tu!qB\u0005\u0005\u000bc\u0012Y\"A\u0002NCBLA!\"\u001e\u0006x\t)QI\u001c;ss*!Q\u0011\u000fB\u000e\u0003-\u0019wN\u001c;bS:\u001c8*Z=\u0015\t\t]SQ\u0010\u0005\b\u000b;z\b\u0019AAsQ\u001dI(1\u000eB9\u0005g\u0012\u0011#T;uC\ndW-T1q/J\f\u0007\u000f]3s+\u0019)))b#\u0006\u0010N!\u0011\u0011ACD!\u001d\u0011y%_CE\u000b\u001b\u0003BA!\n\u0006\f\u0012AQQIA\u0001\u0005\u0004\u0011Y\u0003\u0005\u0003\u0003&\u0015=E\u0001CC&\u0003\u0003\u0011\rAa\u000b\u0016\u0005\u0015M\u0005\u0003CB\u0019\u000b++I)\"$\n\t\u0015E31\u0007\u000b\u0005\u000b3+Y\n\u0005\u0005\u0003P\u0005\u0005Q\u0011RCG\u0011!\u0011y$a\u0002A\u0002\u0015M\u0015a\u00019viR1QQRCQ\u000bKC\u0001\"b)\u0002\n\u0001\u0007Q\u0011R\u0001\u0002W\"AQqUA\u0005\u0001\u0004)i)A\u0001w)\u0011)i)b+\t\u0011\u0015\r\u00161\u0002a\u0001\u0003KD\u0003\"!\u0001\u0003l\tE$1\u000f\u0002\u0014\u0003\n\u001cHO]1di*k\u0015\r],sCB\u0004XM]\u000b\u0007\u000bg+Y,b0\u0014\u0011\u0005=QQWCa\u0003[\u0004\u0002b!\r\u00068\u0016eVQX\u0005\u0005\u000b\u007f\u0019\u0019\u0004\u0005\u0003\u0003&\u0015mF\u0001CC#\u0003\u001f\u0011\rAa\u000b\u0011\t\t\u0015Rq\u0018\u0003\t\u000b\u0017\nyA1\u0001\u0003,Aa!qJA\n\u000bs+iL\"\u001e\u0007x\ty!*T1q/J\f\u0007\u000f]3s\u0019&\\W-\u0006\u0006\u0006H\u0016EWQ[Cm\u000bs\u001c\"\"a\u0005\u0002f\u0016%Wq D\u0003!1\u0019\t$b3\u0006P\u0016MWq[C|\u0013\u0011)ima\r\u0003\r5\u000b\u0007o\u00149t!\u0011\u0011)#\"5\u0005\u0011\u0015\u0015\u00131\u0003b\u0001\u0005W\u0001BA!\n\u0006V\u0012AQ1JA\n\u0005\u0004\u0011Y\u0003\u0005\u0003\u0003&\u0015eG!CCn\u0003'!)\u0019ACo\u0005\t\u00195)\u0006\u0004\u0006`\u0016\u001dXQ^\t\u0005\u0005[)\t\u000f\r\u0003\u0006d\u0016M\b\u0003DB\u0019\u000b\u0017,)/b;\u0006X\u0016E\b\u0003\u0002B\u0013\u000bO$\u0001\"\";\u0006Z\n\u0007!1\u0006\u0002\u00021B!!QECw\t!)y/\"7C\u0002\t-\"!A-\u0011\t\t\u0015R1\u001f\u0003\r\u000bk,I.!A\u0001\u0002\u000b\u0005!1\u0006\u0002\u0004?\u0012\n\u0004\u0003\u0002B\u0013\u000bs$\u0011\"b?\u0002\u0014\u0011\u0015\r!\"@\u0003\u0003\r\u000bBA!\f\u0006JBa!Q\tD\u0001\u000b\u001f,\u0019.b6\u0006x&!a1AAk\u0005U\u0019FO]5di>\u0003H/[7ju\u0016$W*\u00199PaN\u0004\"B!\u0012\u0004\u0014\u0019\u001daQBC|!!\t9O\"\u0003\u0006P\u0016M\u0017\u0002\u0002D\u0006\u00033\u0014a\u0001V;qY\u0016\u0014\u0004\u0003BB\u0019\r\u001fIAAa5\u00044U\u0011a1\u0003\t\t\u000531)\"b4\u0006T&!Q\u0011\u000bB\u000e)\u00111IBb\b\u0011\r\u0005\u001dh1DCj\u0013\u00111i\"!7\u0003\r=\u0003H/[8o\u0011!)\u0019+a\u0007A\u0002\u0015=\u0017aD4fi>\u0013X\t\\:f+B$\u0017\r^3\u0015\r\u0015MgQ\u0005D\u0014\u0011!)i&!\bA\u0002\u0015=\u0007\"\u0003D\u0015\u0003;!\t\u0019\u0001D\u0016\u0003\ty\u0007\u000f\u0005\u0004\u0002h\u001a5R1[\u0005\u0005\r_\tIN\u0001\u0005=Eft\u0017-\\3?)\u00111\u0019D\"\u000e\u000e\u0005\u0005M\u0001\u0002\u0003D\u001c\u0003?\u0001\rAb\u0002\u0002\u0005-4H\u0003\u0002D\u001a\rwA\u0001\"\"\u0018\u0002\"\u0001\u0007Qq\u001a\u000b\u0007\r31yD\"\u0011\t\u0011\u0015\r\u00161\u0005a\u0001\u000b\u001fD\u0001\"b*\u0002$\u0001\u0007Q1\u001b\u000b\u0007\u0005\u000f4)Eb\u0012\t\u0011\u0015\r\u0016Q\u0005a\u0001\u000b\u001fD\u0001\"b*\u0002&\u0001\u0007Q1[\u0001\u000bkB$\u0017\r^3XSRDG\u0003\u0002D'\r+\"BA\"\u0007\u0007P!Aa\u0011KA\u0014\u0001\u00041\u0019&A\tsK6\f\u0007\u000f]5oO\u001a+hn\u0019;j_:\u0004\u0002\"a:\u00060\u0019ea\u0011\u0004\u0005\t\u000b;\n9\u00031\u0001\u0006PR!a\u0011\u0004D-\u0011!)\u0019+!\u000bA\u0002\u0015=WC\u0001D/!\u0019\u0011)Ea\u0012\u0007\b\u0005aam\u001c:fC\u000eDWI\u001c;ssV!a1\rD9)\u0011\u00119M\"\u001a\t\u0011\u0019\u001d\u0014Q\u0006a\u0001\rS\n\u0011A\u001a\t\u000b\u0003O4Y'b4\u0006T\u001a=\u0014\u0002\u0002D7\u00033\u0014\u0011BR;oGRLwN\u001c\u001a\u0011\t\t\u0015b\u0011\u000f\u0003\t\rg\niC1\u0001\u0003,\t\tQ\u000b\u0005\u0003\u00042\u0015U\u0005\u0003CB\u0019\u000b++I,\"0\u0015\u0005\u0019m\u0004\u0003\u0003B(\u0003\u001f)I,\"0)\u0011\u0005=!1\u000eB9\u0005g\u00121BS'ba^\u0013\u0018\r\u001d9feV1a1\u0011DE\r\u001b\u001bb!!\r\u0007\u0006\u00065\b\u0003\u0003B(\u0003\u001f19Ib#\u0011\t\t\u0015b\u0011\u0012\u0003\t\u000b\u000b\n\tD1\u0001\u0003,A!!Q\u0005DG\t!)Y%!\rC\u0002\t-RC\u0001DI!!\u0011IB\"\u0006\u0007\b\u001a-E\u0003\u0002DK\r/\u0003\u0002Ba\u0014\u00022\u0019\u001de1\u0012\u0005\t\u0005\u007f\t9\u00041\u0001\u0007\u0012V\u0011aQ\u0013\u0015\t\u0003c\u0011YG!\u001d\u0003t\t!2i\u001c8dkJ\u0014XM\u001c;NCB<&/\u00199qKJ,bA\")\u0007(\u001a-6CBA \rG3i\u000b\u0005\u0005\u0003P\u0005\u0005aQ\u0015DU!\u0011\u0011)Cb*\u0005\u0011\u0015\u0015\u0013q\bb\u0001\u0005W\u0001BA!\n\u0007,\u0012AQ1JA \u0005\u0004\u0011Y\u0003\u0005\u0005\u00070\u001aUfQ\u0015DU\u001b\t1\tL\u0003\u0003\u00074\nm\u0011AC2p]\u000e,(O]3oi&!aq\u0017DY\u00055\u0019uN\\2veJ,g\u000e^'baBAa1\u0018D`\rK3I+\u0004\u0002\u0007>*!a1WAk\u0013\u0011)\tF\"0\u0015\t\u0019\rgQ\u0019\t\t\u0005\u001f\nyD\"*\u0007*\"A!qHA\"\u0001\u00041I,A\fv]\u0012,'\u000f\\=j]\u001e\u001cuN\\2veJ,g\u000e^'baV\u0011a\u0011X\u0001\faV$\u0018JZ!cg\u0016tG\u000f\u0006\u0004\u0007*\u001a=g\u0011\u001b\u0005\t\u000bG\u000b9\u00051\u0001\u0007&\"AQqUA$\u0001\u00041I\u000b\u0006\u0004\u0003X\u0019Ugq\u001b\u0005\t\u000bG\u000bI\u00051\u0001\u0002f\"AQqUA%\u0001\u0004\t)/A\u0004sKBd\u0017mY3\u0015\r\u0019%fQ\u001cDp\u0011!)\u0019+a\u0013A\u0002\u0019\u0015\u0006\u0002CCT\u0003\u0017\u0002\rA\"+\u0015\u0011\t]c1\u001dDs\rSD\u0001\"b)\u0002N\u0001\u0007aQ\u0015\u0005\t\rO\fi\u00051\u0001\u0007*\u00061q\u000e\u001c3wC2D\u0001Bb;\u0002N\u0001\u0007a\u0011V\u0001\u0007]\u0016<h/\u00197)\u0011\u0005}\"1\u000eB9\u0005g\u0012QCS\"p]\u000e,(O]3oi6\u000b\u0007o\u0016:baB,'/\u0006\u0004\u0007t\u001aehQ`\n\u0007\u0003\u001f2)Pb@\u0011\u0011\t=\u0013q\u0002D|\rw\u0004BA!\n\u0007z\u0012AQQIA(\u0005\u0004\u0011Y\u0003\u0005\u0003\u0003&\u0019uH\u0001CC&\u0003\u001f\u0012\rAa\u000b\u0011\u0011\u0019mfq\u0018D|\rw,\"ab\u0001\u0011\u0011\u0019=fQ\u0017D|\rw$Bab\u0002\b\nAA!qJA(\ro4Y\u0010\u0003\u0005\u0003@\u0005U\u0003\u0019AD\u0002)\u00119iab\u0004\u0011\r\u0005\u001dh1\u0004D~\u0011!)\u0019+a\u0016A\u0002\u0019]HC\u0002D~\u000f'9)\u0002\u0003\u0005\u0006^\u0005e\u0003\u0019\u0001D|\u0011%1I#!\u0017\u0005\u0002\u000499\u0002\u0005\u0004\u0002h\u001a5b1`\u000b\u0003\u000f\u000f!ba\"\u0004\b\u001e\u001d}\u0001\u0002CCR\u0003C\u0002\rAb>\t\u0011\u0015\u001d\u0016\u0011\ra\u0001\rw$bAa\u0016\b$\u001d\u0015\u0002\u0002CCR\u0003G\u0002\rAb>\t\u0011\u0015\u001d\u00161\ra\u0001\rw$ba\"\u0004\b*\u001d-\u0002\u0002CCR\u0003K\u0002\rAb>\t\u0011\u0015\u001d\u0016Q\ra\u0001\rw$\u0002Ba\u0016\b0\u001dErQ\u0007\u0005\t\u000bG\u000b9\u00071\u0001\u0007x\"Aq1GA4\u0001\u00041Y0\u0001\u0005pY\u00124\u0018\r\\;f\u0011!99$a\u001aA\u0002\u0019m\u0018\u0001\u00038foZ\fG.^3\u0002\u00151\f7\u000f^(qi&|g.\u0006\u0002\b>A1\u0011q\u001dD\u000e\u000f\u007f\u0001\u0002\"a:\u0007\n\u0019]h1 \u000b\u0005\u000f\u0007:I\u0005\u0006\u0003\b\u000e\u001d\u0015\u0003\u0002\u0003D)\u0003W\u0002\rab\u0012\u0011\u0011\u0005\u001dXqFD\u0007\u000f\u001bA\u0001\"\"\u0018\u0002l\u0001\u0007aq\u001f\u0015\t\u0003\u001f\u0012YG!\u001d\u0003t\t\tB)[2uS>t\u0017M]=Xe\u0006\u0004\b/\u001a:\u0016\r\u001dEs1LD0'\u0019\tigb\u0015\u0002nBA!\u0011DD+\u000f3:i&\u0003\u0003\bX\tm!A\u0003#jGRLwN\\1ssB!!QED.\t!))%!\u001cC\u0002\t-\u0002\u0003\u0002B\u0013\u000f?\"\u0001\"b\u0013\u0002n\t\u0007!1F\u000b\u0003\u000fG\u0002\u0002b!\r\u0006\u0016\u001eesQ\f\u000b\u0005\u000fO:I\u0007\u0005\u0005\u0003P\u00055t\u0011LD/\u0011!\u0011y$a\u001dA\u0002\u001d\r\u0014\u0001B6fsN$\"ab\u001c\u0011\r\te!1HD-\u0003!)G.Z7f]R\u001cHCAD;!\u0019\u0011IBa\u000f\b^Q!qQLD=\u0011!)i&! A\u0002\u0005\u0015HCBD/\u000f{:y\b\u0003\u0005\u0006^\u0005}\u0004\u0019AD-\u0011!\u0011\t(a A\u0002\u001duC\u0003BD/\u000f\u0007C\u0001\"\"\u0018\u0002\u0002\u0002\u0007\u0011Q\u001d\u0015\t\u0003[\u0012YG!\u001d\u0003t\t\u0011\"\nR5di&|g.\u0019:z/J\f\u0007\u000f]3s+\u00199Yi\"%\b\u0016N1\u00111QDG\u0003[\u0004\u0002b!\r\u00068\u001e=u1\u0013\t\u0005\u0005K9\t\n\u0002\u0005\u0006F\u0005\r%\u0019\u0001B\u0016!\u0011\u0011)c\"&\u0005\u0011\u0015-\u00131\u0011b\u0001\u0005W)\"a\"'\u0011\u0011\teqQKDH\u000f'#Ba\"(\b BA!qJAB\u000f\u001f;\u0019\n\u0003\u0005\u0003@\u0005%\u0005\u0019ADM)\u00119\u0019k\"*\u0011\r\u0005\u001dh1DDJ\u0011!)\u0019+!%A\u0002\u001d=E\u0003BDU\u000fWk!!a!\t\u0011\u0019]\u00121\u0013a\u0001\u000f[\u0003\u0002\"a:\u0007\n\u001d=u1\u0013\u000b\u0005\u000fS;\t\f\u0003\u0005\u0006^\u0005U\u0005\u0019ADH)\u00199\u0019k\".\b8\"AQ1UAL\u0001\u00049y\t\u0003\u0005\u0006(\u0006]\u0005\u0019ADJ)\u0019\u00119mb/\b>\"AQ1UAM\u0001\u00049y\t\u0003\u0005\u0006(\u0006e\u0005\u0019ADJ)\u00119\u0019k\"1\t\u0011\u0015\r\u00161\u0014a\u0001\u000f\u001f+\"a\"2\u0011\r\t\u0015#qIDW\u0003)i\u0017\r\u001d$bGR|'/_\u000b\u0003\u000f\u0017\u0004Ba!\r\bN*!qqZB\u001a\u0003\u001dA\u0015m\u001d5NCBD\u0003\"a!\u0003l\tE$1\u000f\u0002\u0013\u0015B\u0013x\u000e]3si&,7o\u0016:baB,'o\u0005\u0007\u0002$\u001e]wq^Dz\u000fk\fi\u000f\u0005\u0005\u00042\u0015]v\u0011\\Dm!\u00119Yn\";\u000f\t\u001duwQ\u001d\t\u0005\u000f?\fI.\u0004\u0002\bb*!q1]A\u007f\u0003\u0019a$o\\8u}%!qq]Am\u0003\u0019\u0001&/\u001a3fM&!q1^Dw\u0005\u0019\u0019FO]5oO*!qq]Am!1\u0019\t$b3\bZ\u001eegQODy!!\u0019\t$\"&\bZ\u001ee\u0007\u0003\u0004B#\r\u00039In\"7\u0007v\u001dE\bC\u0003B#\u0007'99P\"\u0004\brBA\u0011q\u001dD\u0005\u000f3<I\u000e\u0005\u0003\u0003\u001a\u001dm\u0018\u0002BD\u007f\u00057\u0011!\u0002\u0015:pa\u0016\u0014H/[3t)\u0011A\t\u0001c\u0001\u0011\t\t=\u00131\u0015\u0005\t\u0005\u007f\t9\u000b1\u0001\bzR!\u0001r\u0001E\u0005!\u0019\t9Ob\u0007\bZ\"AQ1UAX\u0001\u00049I\u000e\u0006\u0003\t\u000e!=QBAAR\u0011!19$!-A\u0002\u001d]H\u0003\u0002E\u0007\u0011'A\u0001\"\"\u0018\u00024\u0002\u0007q\u0011\u001c\u000b\u0007\u0011\u000fA9\u0002#\u0007\t\u0011\u0015\r\u0016Q\u0017a\u0001\u000f3D\u0001\"b*\u00026\u0002\u0007q\u0011\u001c\u000b\u0007\u0005\u000fDi\u0002c\b\t\u0011\u0015\r\u0016q\u0017a\u0001\u000f3D\u0001\"b*\u00028\u0002\u0007q\u0011\u001c\u000b\u0005\u0011\u000fA\u0019\u0003\u0003\u0005\u0006$\u0006e\u0006\u0019ADm+\tA9\u0003\u0005\u0004\u0003F\t\u001dsq_\u000b\u0003\u0011\u0003\t1bZ3u!J|\u0007/\u001a:usR!\u0001r\u0006E\u001a!\u0011\u0011I\u0001#\r\n\t\u001d-(1\u0002\u0005\t\u000b;\n\t\r1\u0001\bZR1\u0001r\u0006E\u001c\u0011sA\u0001\"\"\u0018\u0002D\u0002\u0007q\u0011\u001c\u0005\t\u0011w\t\u0019\r1\u0001\bZ\u0006aA-\u001a4bk2$h+\u00197vK\u0006Y1/\u001a;Qe>\u0004XM\u001d;z)\u0019A\t\u0005c\u0011\tFA!!\u0011\u0002B\n\u0011!)i&!2A\u0002\u001de\u0007\u0002\u0003B9\u0003\u000b\u0004\ra\"7)\u0011\u0005\r&1\u000eB9\u0005g\nq\u0001U;u\u001dVdG.\u0006\u0002\tNA!\u0001r\nE,\u001b\tA\tF\u0003\u0003\tT!U\u0013aB2p]R\u0014x\u000e\u001c\u0006\u0005\u0005;\tI.\u0003\u0003\tZ!E#\u0001E\"p]R\u0014x\u000e\u001c+ie><\u0018M\u00197f\u0003!\u0001V\u000f\u001e(vY2\u0004\u0013\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001B\u0004Q\u001d\t!1\u000eB9\u0005gBs\u0001\u0001B6\u0005c\u0012\u0019\b")
public final class JavaCollectionWrappers {

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static abstract class AbstractJMapWrapper<K, V>
    extends scala.collection.mutable.AbstractMap<K, V>
    implements JMapWrapperLike<K, V, scala.collection.mutable.Map, scala.collection.mutable.Map<K, V>>,
    Serializable {
        private static final long serialVersionUID = 3L;

        @Override
        public int size() {
            return ((JMapWrapperLike)this).size();
        }

        @Override
        public Option<V> get(K k) {
            return ((JMapWrapperLike)this).get(k);
        }

        @Override
        public V getOrElseUpdate(K key, Function0<V> op) {
            return (V)((JMapWrapperLike)this).getOrElseUpdate(key, op);
        }

        @Override
        public JMapWrapperLike<K, V, scala.collection.mutable.Map, scala.collection.mutable.Map<K, V>> addOne(Tuple2<K, V> kv) {
            return ((JMapWrapperLike)this).addOne(kv);
        }

        @Override
        public JMapWrapperLike<K, V, scala.collection.mutable.Map, scala.collection.mutable.Map<K, V>> subtractOne(K key) {
            return ((JMapWrapperLike)this).subtractOne((K)key);
        }

        @Override
        public Option<V> put(K k, V v) {
            return ((JMapWrapperLike)this).put(k, v);
        }

        @Override
        public void update(K k, V v) {
            ((JMapWrapperLike)this).update(k, v);
        }

        @Override
        public Option<V> updateWith(K key, Function1<Option<V>, Option<V>> remappingFunction) {
            return ((JMapWrapperLike)this).updateWith(key, remappingFunction);
        }

        @Override
        public Option<V> remove(K k) {
            return ((JMapWrapperLike)this).remove(k);
        }

        @Override
        public Iterator<Tuple2<K, V>> iterator() {
            return ((JMapWrapperLike)this).iterator();
        }

        @Override
        public <U> void foreachEntry(Function2<K, V, U> f) {
            ((JMapWrapperLike)this).foreachEntry(f);
        }

        @Override
        public void clear() {
            ((JMapWrapperLike)this).clear();
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
        public IterableOps $plus(Tuple2 elem1, Tuple2 elem2, scala.collection.immutable.Seq elems) {
            return StrictOptimizedMapOps.$plus$(this, elem1, elem2, elems);
        }

        @Override
        public Tuple2<scala.collection.mutable.Map<K, V>, scala.collection.mutable.Map<K, V>> partition(Function1<Tuple2<K, V>, Object> p) {
            return StrictOptimizedIterableOps.partition$(this, p);
        }

        @Override
        public Tuple2<scala.collection.mutable.Map<K, V>, scala.collection.mutable.Map<K, V>> span(Function1<Tuple2<K, V>, Object> p) {
            return StrictOptimizedIterableOps.span$(this, p);
        }

        @Override
        public <A1, A2> Tuple2<scala.collection.mutable.Iterable<A1>, scala.collection.mutable.Iterable<A2>> unzip(Function1<Tuple2<K, V>, Tuple2<A1, A2>> asPair) {
            return StrictOptimizedIterableOps.unzip$(this, asPair);
        }

        @Override
        public <A1, A2, A3> Tuple3<scala.collection.mutable.Iterable<A1>, scala.collection.mutable.Iterable<A2>, scala.collection.mutable.Iterable<A3>> unzip3(Function1<Tuple2<K, V>, Tuple3<A1, A2, A3>> asTriple) {
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
        public <A1, A2> Tuple2<scala.collection.mutable.Iterable<A1>, scala.collection.mutable.Iterable<A2>> partitionMap(Function1<Tuple2<K, V>, Either<A1, A2>> f) {
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
    }

    public static class ConcurrentMapWrapper<K, V>
    extends MutableMapWrapper<K, V>
    implements ConcurrentMap<K, V> {
        private static final long serialVersionUID = 3L;

        @Override
        public V getOrDefault(Object x$1, V x$2) {
            return ConcurrentMap.super.getOrDefault(x$1, x$2);
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super V> x$1) {
            ConcurrentMap.super.forEach(x$1);
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super V, ? extends V> x$1) {
            ConcurrentMap.super.replaceAll(x$1);
        }

        @Override
        public V computeIfAbsent(K x$1, Function<? super K, ? extends V> x$2) {
            return ConcurrentMap.super.computeIfAbsent(x$1, x$2);
        }

        @Override
        public V computeIfPresent(K x$1, BiFunction<? super K, ? super V, ? extends V> x$2) {
            return ConcurrentMap.super.computeIfPresent(x$1, x$2);
        }

        @Override
        public V compute(K x$1, BiFunction<? super K, ? super V, ? extends V> x$2) {
            return ConcurrentMap.super.compute(x$1, x$2);
        }

        @Override
        public V merge(K x$1, V x$2, BiFunction<? super V, ? super V, ? extends V> x$3) {
            return ConcurrentMap.super.merge(x$1, x$2, x$3);
        }

        public scala.collection.concurrent.Map<K, V> underlyingConcurrentMap() {
            return (scala.collection.concurrent.Map)super.underlying();
        }

        @Override
        public V putIfAbsent(K k, V v) {
            Option<V> option = ((scala.collection.concurrent.Map)super.underlying()).putIfAbsent(k, v);
            if (option instanceof Some) {
                return (V)((Some)option).value();
            }
            if (None$.MODULE$.equals(option)) {
                return null;
            }
            throw new MatchError(option);
        }

        @Override
        public boolean remove(Object k, Object v) {
            try {
                return ((scala.collection.concurrent.Map)super.underlying()).remove(k, v);
            }
            catch (ClassCastException classCastException) {
                return false;
            }
        }

        @Override
        public V replace(K k, V v) {
            Option<V> option = ((scala.collection.concurrent.Map)super.underlying()).replace(k, v);
            if (option instanceof Some) {
                return (V)((Some)option).value();
            }
            if (None$.MODULE$.equals(option)) {
                return null;
            }
            throw new MatchError(option);
        }

        @Override
        public boolean replace(K k, V oldval, V newval) {
            return ((scala.collection.concurrent.Map)super.underlying()).replace(k, oldval, newval);
        }

        public ConcurrentMapWrapper(scala.collection.concurrent.Map<K, V> underlying) {
            super(underlying);
        }
    }

    public static class DictionaryWrapper<K, V>
    extends Dictionary<K, V>
    implements Serializable {
        private static final long serialVersionUID = 3L;
        private final scala.collection.mutable.Map<K, V> underlying;

        public scala.collection.mutable.Map<K, V> underlying() {
            return this.underlying;
        }

        @Override
        public int size() {
            return this.underlying().size();
        }

        @Override
        public boolean isEmpty() {
            return this.underlying().isEmpty();
        }

        @Override
        public Enumeration<K> keys() {
            Iterator IteratorHasAsJava_i = this.underlying().keysIterator();
            CollectionConverters$ IteratorHasAsJava_this = CollectionConverters$.MODULE$;
            Object var1_2 = null;
            Object var2_1 = null;
            return new AsJavaExtensions.IteratorHasAsJava(IteratorHasAsJava_this, IteratorHasAsJava_i).asJavaEnumeration();
        }

        @Override
        public Enumeration<V> elements() {
            Iterator IteratorHasAsJava_i = this.underlying().valuesIterator();
            CollectionConverters$ IteratorHasAsJava_this = CollectionConverters$.MODULE$;
            Object var1_2 = null;
            Object var2_1 = null;
            return new AsJavaExtensions.IteratorHasAsJava(IteratorHasAsJava_this, IteratorHasAsJava_i).asJavaEnumeration();
        }

        @Override
        public V get(Object key) {
            Object v0;
            block4: {
                try {
                    Option option = this.underlying().get(key);
                    if (None$.MODULE$.equals(option)) {
                        v0 = null;
                        break block4;
                    }
                    if (option instanceof Some) {
                        v0 = ((Some)option).value();
                        break block4;
                    }
                    throw new MatchError(option);
                }
                catch (ClassCastException classCastException) {
                    v0 = null;
                }
            }
            return v0;
        }

        @Override
        public V put(K key, V value) {
            Option<V> option = this.underlying().put(key, value);
            if (option instanceof Some) {
                return (V)((Some)option).value();
            }
            if (None$.MODULE$.equals(option)) {
                return null;
            }
            throw new MatchError(option);
        }

        @Override
        public V remove(Object key) {
            Object v0;
            block4: {
                try {
                    Option option = this.underlying().remove(key);
                    if (None$.MODULE$.equals(option)) {
                        v0 = null;
                        break block4;
                    }
                    if (option instanceof Some) {
                        v0 = ((Some)option).value();
                        break block4;
                    }
                    throw new MatchError(option);
                }
                catch (ClassCastException classCastException) {
                    v0 = null;
                }
            }
            return v0;
        }

        public DictionaryWrapper(scala.collection.mutable.Map<K, V> underlying) {
            this.underlying = underlying;
        }
    }

    public static class IterableWrapper<A>
    extends AbstractCollection<A>
    implements IterableWrapperTrait<A>,
    Serializable {
        private static final long serialVersionUID = 3L;
        private final Iterable<A> underlying;

        @Override
        public int size() {
            return ((IterableWrapperTrait)this).size();
        }

        @Override
        public IteratorWrapper<A> iterator() {
            return ((IterableWrapperTrait)this).iterator();
        }

        @Override
        public boolean isEmpty() {
            return ((IterableWrapperTrait)this).isEmpty();
        }

        @Override
        public Iterable<A> underlying() {
            return this.underlying;
        }

        public IterableWrapper(Iterable<A> underlying) {
            this.underlying = underlying;
        }
    }

    public static interface IterableWrapperTrait<A> {
        public Iterable<A> underlying();

        default public int size() {
            return this.underlying().size();
        }

        default public IteratorWrapper<A> iterator() {
            return new IteratorWrapper(this.underlying().iterator());
        }

        default public boolean isEmpty() {
            return this.underlying().isEmpty();
        }

        public static void $init$(IterableWrapperTrait $this) {
        }
    }

    public static class IteratorWrapper<A>
    implements java.util.Iterator<A>,
    Enumeration<A>,
    Serializable {
        private static final long serialVersionUID = 3L;
        private final Iterator<A> underlying;

        @Override
        public void forEachRemaining(Consumer<? super A> x$1) {
            java.util.Iterator.super.forEachRemaining(x$1);
        }

        public Iterator<A> underlying() {
            return this.underlying;
        }

        @Override
        public boolean hasNext() {
            return this.underlying().hasNext();
        }

        @Override
        public A next() {
            return this.underlying().next();
        }

        @Override
        public boolean hasMoreElements() {
            return this.underlying().hasNext();
        }

        @Override
        public A nextElement() {
            return this.underlying().next();
        }

        public Nothing$ remove() {
            throw new UnsupportedOperationException();
        }

        public IteratorWrapper(Iterator<A> underlying) {
            this.underlying = underlying;
        }
    }

    public static class JCollectionWrapper<A>
    extends AbstractIterable<A>
    implements StrictOptimizedIterableOps<A, Iterable, Iterable<A>>,
    Serializable {
        private static final long serialVersionUID = 3L;
        private final Collection<A> underlying;

        @Override
        public Tuple2<Iterable<A>, Iterable<A>> partition(Function1<A, Object> p) {
            return StrictOptimizedIterableOps.partition$(this, p);
        }

        @Override
        public Tuple2<Iterable<A>, Iterable<A>> span(Function1<A, Object> p) {
            return StrictOptimizedIterableOps.span$(this, p);
        }

        @Override
        public <A1, A2> Tuple2<Iterable<A1>, Iterable<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
            return StrictOptimizedIterableOps.unzip$(this, asPair);
        }

        @Override
        public <A1, A2, A3> Tuple3<Iterable<A1>, Iterable<A2>, Iterable<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
            return StrictOptimizedIterableOps.unzip3$(this, asTriple);
        }

        @Override
        public Object map(Function1 f) {
            return StrictOptimizedIterableOps.map$(this, f);
        }

        @Override
        public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<A, B> f) {
            return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
        }

        @Override
        public Object flatMap(Function1 f) {
            return StrictOptimizedIterableOps.flatMap$(this, f);
        }

        @Override
        public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<A, IterableOnce<B>> f) {
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
        public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<A, B> pf) {
            return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
        }

        @Override
        public Object flatten(Function1 toIterableOnce) {
            return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
        }

        @Override
        public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<A, IterableOnce<B>> toIterableOnce) {
            return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
        }

        @Override
        public Object zip(IterableOnce that) {
            return StrictOptimizedIterableOps.zip$(this, that);
        }

        @Override
        public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<A, B>, C2> b) {
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
        public <A1, A2> Tuple2<Iterable<A1>, Iterable<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
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

        public Collection<A> underlying() {
            return this.underlying;
        }

        @Override
        public Iterator<A> iterator() {
            java.util.Iterator<A> IteratorHasAsScala_i = this.underlying().iterator();
            CollectionConverters$ IteratorHasAsScala_this = CollectionConverters$.MODULE$;
            Object var1_2 = null;
            Object var2_1 = null;
            return new AsScalaExtensions.IteratorHasAsScala<A>(IteratorHasAsScala_this, IteratorHasAsScala_i).asScala();
        }

        @Override
        public int size() {
            return this.underlying().size();
        }

        @Override
        public int knownSize() {
            if (this.underlying().isEmpty()) {
                return 0;
            }
            return -1;
        }

        @Override
        public boolean isEmpty() {
            return this.underlying().isEmpty();
        }

        public ArrayBuffer$ iterableFactory() {
            return ArrayBuffer$.MODULE$;
        }

        public JCollectionWrapper(Collection<A> underlying) {
            this.underlying = underlying;
        }
    }

    public static class JConcurrentMapWrapper<K, V>
    extends AbstractJMapWrapper<K, V>
    implements scala.collection.concurrent.Map<K, V> {
        private static final long serialVersionUID = 3L;
        private final ConcurrentMap<K, V> underlying;

        @Override
        public boolean removeRefEq(K k, V v) {
            return scala.collection.concurrent.Map.removeRefEq$(this, k, v);
        }

        @Override
        public boolean replaceRefEq(K k, V oldValue, V newValue) {
            return scala.collection.concurrent.Map.replaceRefEq$(this, k, oldValue, newValue);
        }

        @Override
        public scala.collection.concurrent.Map<K, V> filterInPlaceImpl(Function2<K, V, Object> p) {
            return scala.collection.concurrent.Map.filterInPlaceImpl$(this, p);
        }

        @Override
        public scala.collection.concurrent.Map<K, V> mapValuesInPlaceImpl(Function2<K, V, V> f) {
            return scala.collection.concurrent.Map.mapValuesInPlaceImpl$(this, f);
        }

        @Override
        public ConcurrentMap<K, V> underlying() {
            return this.underlying;
        }

        @Override
        public Option<V> get(K k) {
            return Option$.MODULE$.apply(this.underlying().get(k));
        }

        @Override
        public V getOrElseUpdate(K key, Function0<V> op) {
            Object object = this.underlying().computeIfAbsent(key, x$6 -> op.apply());
            if (object == null) {
                Option option = this.get(key);
                if (option instanceof Some) {
                    return (V)((Some)option).value();
                }
                if (None$.MODULE$.equals(option)) {
                    V getOrElseUpdate_v = op.apply();
                    Option<V> option2 = this.putIfAbsent(key, getOrElseUpdate_v);
                    if (option2 instanceof Some) {
                        return (V)((Some)option2).value();
                    }
                    if (None$.MODULE$.equals(option2)) {
                        return getOrElseUpdate_v;
                    }
                    throw new MatchError(option2);
                }
                throw new MatchError(option);
            }
            return (V)object;
        }

        @Override
        public boolean isEmpty() {
            return this.underlying().isEmpty();
        }

        @Override
        public int knownSize() {
            if (this.underlying().isEmpty()) {
                return 0;
            }
            return -1;
        }

        @Override
        public JConcurrentMapWrapper<K, V> empty() {
            return new JConcurrentMapWrapper(new ConcurrentHashMap());
        }

        @Override
        public Option<V> putIfAbsent(K k, V v) {
            return Option$.MODULE$.apply(this.underlying().putIfAbsent(k, v));
        }

        @Override
        public boolean remove(K k, V v) {
            return this.underlying().remove(k, v);
        }

        @Override
        public Option<V> replace(K k, V v) {
            return Option$.MODULE$.apply(this.underlying().replace(k, v));
        }

        @Override
        public boolean replace(K k, V oldvalue, V newvalue) {
            return this.underlying().replace(k, oldvalue, newvalue);
        }

        @Override
        public Option<Tuple2<K, V>> lastOption() {
            Option<Throwable> option;
            Try try_;
            java.util.Map map = this.underlying();
            if (map instanceof NavigableMap) {
                Option option2 = Option$.MODULE$.apply(((NavigableMap)map).lastEntry());
                if (option2 == null) {
                    throw null;
                }
                Option map_this = option2;
                if (map_this.isEmpty()) {
                    return None$.MODULE$;
                }
                return new Some<Tuple2<K, V>>(JConcurrentMapWrapper.$anonfun$lastOption$1(map_this.get()));
            }
            if (this.isEmpty()) {
                return None$.MODULE$;
            }
            try {
                try_ = new Success<Tuple2>((Tuple2)this.last());
            }
            catch (Throwable throwable) {
                if (throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty()) {
                    Throwable apply_e = option.get();
                    try_ = new Failure(apply_e);
                }
                throw throwable;
            }
            Object var3_4 = null;
            option = null;
            Object var5_6 = null;
            return ((Try)try_).toOption();
        }

        @Override
        public Option<V> updateWith(K key, Function1<Option<V>, Option<V>> remappingFunction) {
            try {
                return Option$.MODULE$.apply(this.underlying().compute(key, (k, v) -> JConcurrentMapWrapper.remap$2(k, v, remappingFunction)));
            }
            catch (Throwable throwable) {
                ControlThrowable controlThrowable = JavaCollectionWrappers$.MODULE$.scala$collection$convert$JavaCollectionWrappers$$PutNull();
                if (!(controlThrowable != null ? !controlThrowable.equals(throwable) : throwable != null)) {
                    block6: {
                        Option<V> updateWith_updateWithAux_nextValue;
                        while (true) {
                            Option updateWith_updateWithAux_previousValue = this.get(key);
                            updateWith_updateWithAux_nextValue = remappingFunction.apply(updateWith_updateWithAux_previousValue);
                            if (updateWith_updateWithAux_previousValue instanceof Some) {
                                Object updateWith_updateWithAux_prev = ((Some)updateWith_updateWithAux_previousValue).value();
                                if (updateWith_updateWithAux_nextValue instanceof Some) {
                                    Object updateWith_updateWithAux_next = ((Some)updateWith_updateWithAux_nextValue).value();
                                    if (!this.replaceRefEq(key, updateWith_updateWithAux_prev, updateWith_updateWithAux_next)) continue;
                                    return updateWith_updateWithAux_nextValue;
                                }
                                if (!this.removeRefEq(key, updateWith_updateWithAux_prev)) continue;
                                return None$.MODULE$;
                            }
                            if (!(updateWith_updateWithAux_nextValue instanceof Some)) break block6;
                            Object updateWith_updateWithAux_next = ((Some)updateWith_updateWithAux_nextValue).value();
                            if (this.putIfAbsent(key, updateWith_updateWithAux_next).isEmpty()) break;
                        }
                        return updateWith_updateWithAux_nextValue;
                    }
                    return None$.MODULE$;
                }
                throw throwable;
            }
        }

        public static final /* synthetic */ Tuple2 $anonfun$lastOption$1(Map.Entry e) {
            return new Tuple2(e.getKey(), e.getValue());
        }

        public static final /* synthetic */ Tuple2 $anonfun$lastOption$2(JConcurrentMapWrapper $this) {
            return (Tuple2)$this.last();
        }

        private static final Object remap$2(Object k, Object v, Function1 remappingFunction$2) {
            boolean bl = false;
            Some some = null;
            Option option = (Option)remappingFunction$2.apply(Option$.MODULE$.apply(v));
            if (option instanceof Some) {
                bl = true;
                some = (Some)option;
                if (some.value() == null) {
                    throw JavaCollectionWrappers$.MODULE$.scala$collection$convert$JavaCollectionWrappers$$PutNull();
                }
            }
            if (bl) {
                return some.value();
            }
            if (None$.MODULE$.equals(option)) {
                return null;
            }
            throw new MatchError(option);
        }

        public JConcurrentMapWrapper(ConcurrentMap<K, V> underlying) {
            this.underlying = underlying;
        }
    }

    public static class JDictionaryWrapper<K, V>
    extends scala.collection.mutable.AbstractMap<K, V>
    implements Serializable {
        private static final long serialVersionUID = 3L;
        private final Dictionary<K, V> underlying;

        public Dictionary<K, V> underlying() {
            return this.underlying;
        }

        @Override
        public int size() {
            return this.underlying().size();
        }

        @Override
        public boolean isEmpty() {
            return this.underlying().isEmpty();
        }

        @Override
        public int knownSize() {
            if (this.underlying().isEmpty()) {
                return 0;
            }
            return -1;
        }

        @Override
        public Option<V> get(K k) {
            return Option$.MODULE$.apply(this.underlying().get(k));
        }

        public JDictionaryWrapper<K, V> addOne(Tuple2<K, V> kv) {
            this.underlying().put(kv._1(), kv._2());
            return this;
        }

        public JDictionaryWrapper<K, V> subtractOne(K key) {
            this.underlying().remove(key);
            return this;
        }

        @Override
        public Option<V> put(K k, V v) {
            return Option$.MODULE$.apply(this.underlying().put(k, v));
        }

        @Override
        public void update(K k, V v) {
            this.underlying().put(k, v);
        }

        @Override
        public Option<V> remove(K k) {
            return Option$.MODULE$.apply(this.underlying().remove(k));
        }

        @Override
        public Iterator<Tuple2<K, V>> iterator() {
            Enumeration<K> EnumerationHasAsScala_e = this.underlying().keys();
            CollectionConverters$ EnumerationHasAsScala_this = CollectionConverters$.MODULE$;
            Object var1_2 = null;
            Object var2_1 = null;
            return new AsScalaExtensions.EnumerationHasAsScala<K>(EnumerationHasAsScala_this, EnumerationHasAsScala_e).asScala().map((Function1<Object, Tuple2> & Serializable)(A k) -> new Tuple2(k, this.underlying().get(k)));
        }

        @Override
        public void clear() {
            this.iterator().foreach((Function1<Tuple2, Object> & Serializable)entry -> this.underlying().remove(entry._1()));
        }

        public HashMap$ mapFactory() {
            return HashMap$.MODULE$;
        }

        public JDictionaryWrapper(Dictionary<K, V> underlying) {
            this.underlying = underlying;
        }
    }

    public static class JEnumerationWrapper<A>
    extends AbstractIterator<A>
    implements Serializable {
        private static final long serialVersionUID = 3L;
        private final Enumeration<A> underlying;

        public Enumeration<A> underlying() {
            return this.underlying;
        }

        @Override
        public boolean hasNext() {
            return this.underlying().hasMoreElements();
        }

        @Override
        public A next() {
            return this.underlying().nextElement();
        }

        public JEnumerationWrapper(Enumeration<A> underlying) {
            this.underlying = underlying;
        }
    }

    public static class JIterableWrapper<A>
    extends AbstractIterable<A>
    implements StrictOptimizedIterableOps<A, Iterable, Iterable<A>>,
    Serializable {
        private static final long serialVersionUID = 3L;
        private final java.lang.Iterable<A> underlying;

        @Override
        public Tuple2<Iterable<A>, Iterable<A>> partition(Function1<A, Object> p) {
            return StrictOptimizedIterableOps.partition$(this, p);
        }

        @Override
        public Tuple2<Iterable<A>, Iterable<A>> span(Function1<A, Object> p) {
            return StrictOptimizedIterableOps.span$(this, p);
        }

        @Override
        public <A1, A2> Tuple2<Iterable<A1>, Iterable<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
            return StrictOptimizedIterableOps.unzip$(this, asPair);
        }

        @Override
        public <A1, A2, A3> Tuple3<Iterable<A1>, Iterable<A2>, Iterable<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
            return StrictOptimizedIterableOps.unzip3$(this, asTriple);
        }

        @Override
        public Object map(Function1 f) {
            return StrictOptimizedIterableOps.map$(this, f);
        }

        @Override
        public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<A, B> f) {
            return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
        }

        @Override
        public Object flatMap(Function1 f) {
            return StrictOptimizedIterableOps.flatMap$(this, f);
        }

        @Override
        public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<A, IterableOnce<B>> f) {
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
        public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<A, B> pf) {
            return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
        }

        @Override
        public Object flatten(Function1 toIterableOnce) {
            return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
        }

        @Override
        public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<A, IterableOnce<B>> toIterableOnce) {
            return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
        }

        @Override
        public Object zip(IterableOnce that) {
            return StrictOptimizedIterableOps.zip$(this, that);
        }

        @Override
        public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<A, B>, C2> b) {
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
        public <A1, A2> Tuple2<Iterable<A1>, Iterable<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
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

        public java.lang.Iterable<A> underlying() {
            return this.underlying;
        }

        @Override
        public Iterator<A> iterator() {
            java.util.Iterator<A> IteratorHasAsScala_i = this.underlying().iterator();
            CollectionConverters$ IteratorHasAsScala_this = CollectionConverters$.MODULE$;
            Object var1_2 = null;
            Object var2_1 = null;
            return new AsScalaExtensions.IteratorHasAsScala<A>(IteratorHasAsScala_this, IteratorHasAsScala_i).asScala();
        }

        public ArrayBuffer$ iterableFactory() {
            return ArrayBuffer$.MODULE$;
        }

        @Override
        public boolean isEmpty() {
            return !this.underlying().iterator().hasNext();
        }

        public JIterableWrapper(java.lang.Iterable<A> underlying) {
            this.underlying = underlying;
        }
    }

    public static class JIteratorWrapper<A>
    extends AbstractIterator<A>
    implements Serializable {
        private static final long serialVersionUID = 3L;
        private final java.util.Iterator<A> underlying;

        public java.util.Iterator<A> underlying() {
            return this.underlying;
        }

        @Override
        public boolean hasNext() {
            return this.underlying().hasNext();
        }

        @Override
        public A next() {
            return this.underlying().next();
        }

        public JIteratorWrapper(java.util.Iterator<A> underlying) {
            this.underlying = underlying;
        }
    }

    public static class JListWrapper<A>
    extends AbstractBuffer<A>
    implements StrictOptimizedSeqOps<A, Buffer, Buffer<A>>,
    Serializable {
        private static final long serialVersionUID = 3L;
        private final List<A> underlying;

        @Override
        public Object distinctBy(Function1 f) {
            return StrictOptimizedSeqOps.distinctBy$(this, f);
        }

        @Override
        public Object prepended(Object elem) {
            return StrictOptimizedSeqOps.prepended$(this, elem);
        }

        @Override
        public Object appended(Object elem) {
            return StrictOptimizedSeqOps.appended$(this, elem);
        }

        @Override
        public Object appendedAll(IterableOnce suffix) {
            return StrictOptimizedSeqOps.appendedAll$(this, suffix);
        }

        @Override
        public Object prependedAll(IterableOnce prefix) {
            return StrictOptimizedSeqOps.prependedAll$(this, prefix);
        }

        @Override
        public Object padTo(int len, Object elem) {
            return StrictOptimizedSeqOps.padTo$(this, len, elem);
        }

        @Override
        public Object diff(Seq that) {
            return StrictOptimizedSeqOps.diff$(this, that);
        }

        @Override
        public Object intersect(Seq that) {
            return StrictOptimizedSeqOps.intersect$(this, that);
        }

        @Override
        public Tuple2<Buffer<A>, Buffer<A>> partition(Function1<A, Object> p) {
            return StrictOptimizedIterableOps.partition$(this, p);
        }

        @Override
        public Tuple2<Buffer<A>, Buffer<A>> span(Function1<A, Object> p) {
            return StrictOptimizedIterableOps.span$(this, p);
        }

        @Override
        public <A1, A2> Tuple2<Buffer<A1>, Buffer<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
            return StrictOptimizedIterableOps.unzip$(this, asPair);
        }

        @Override
        public <A1, A2, A3> Tuple3<Buffer<A1>, Buffer<A2>, Buffer<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
            return StrictOptimizedIterableOps.unzip3$(this, asTriple);
        }

        @Override
        public Object map(Function1 f) {
            return StrictOptimizedIterableOps.map$(this, f);
        }

        @Override
        public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<A, B> f) {
            return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
        }

        @Override
        public Object flatMap(Function1 f) {
            return StrictOptimizedIterableOps.flatMap$(this, f);
        }

        @Override
        public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<A, IterableOnce<B>> f) {
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
        public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<A, B> pf) {
            return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
        }

        @Override
        public Object flatten(Function1 toIterableOnce) {
            return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
        }

        @Override
        public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<A, IterableOnce<B>> toIterableOnce) {
            return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
        }

        @Override
        public Object zip(IterableOnce that) {
            return StrictOptimizedIterableOps.zip$(this, that);
        }

        @Override
        public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<A, B>, C2> b) {
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
        public <A1, A2> Tuple2<Buffer<A1>, Buffer<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
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

        public List<A> underlying() {
            return this.underlying;
        }

        @Override
        public int length() {
            return this.underlying().size();
        }

        @Override
        public int knownSize() {
            if (this.underlying().isEmpty()) {
                return 0;
            }
            return -1;
        }

        @Override
        public boolean isEmpty() {
            return this.underlying().isEmpty();
        }

        @Override
        public Iterator<A> iterator() {
            java.util.Iterator<A> IteratorHasAsScala_i = this.underlying().iterator();
            CollectionConverters$ IteratorHasAsScala_this = CollectionConverters$.MODULE$;
            Object var1_2 = null;
            Object var2_1 = null;
            return new AsScalaExtensions.IteratorHasAsScala<A>(IteratorHasAsScala_this, IteratorHasAsScala_i).asScala();
        }

        @Override
        public A apply(int i) {
            return this.underlying().get(i);
        }

        @Override
        public void update(int i, A elem) {
            this.underlying().set(i, elem);
        }

        @Override
        public JListWrapper<A> prepend(A elem) {
            this.underlying().subList(0, 0).add(elem);
            return this;
        }

        @Override
        public JListWrapper<A> addOne(A elem) {
            this.underlying().add(elem);
            return this;
        }

        @Override
        public void insert(int idx, A elem) {
            this.underlying().subList(0, idx).add(elem);
        }

        @Override
        public void insertAll(int i, IterableOnce<A> elems) {
            List ins = this.underlying().subList(0, i);
            elems.iterator().foreach((Function1<Object, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(ins.add(x$1)));
        }

        @Override
        public A remove(int i) {
            return this.underlying().remove(i);
        }

        @Override
        public void clear() {
            this.underlying().clear();
        }

        @Override
        public JListWrapper<A> clone() {
            return new JListWrapper<A>(new ArrayList<A>(this.underlying()));
        }

        @Override
        public JListWrapper<A> patchInPlace(int from, IterableOnce<A> patch, int replaced) {
            this.remove(from, replaced);
            this.insertAll(from, patch);
            return this;
        }

        @Override
        public void remove(int from, int n) {
            this.underlying().subList(from, from + n).clear();
        }

        @Override
        public ArrayBuffer$ iterableFactory() {
            return ArrayBuffer$.MODULE$;
        }

        @Override
        public JListWrapper<A> subtractOne(A elem) {
            this.underlying().remove(elem);
            return this;
        }

        public JListWrapper(List<A> underlying) {
            this.underlying = underlying;
        }
    }

    public static class JMapWrapper<K, V>
    extends AbstractJMapWrapper<K, V> {
        private static final long serialVersionUID = 3L;
        private final java.util.Map<K, V> underlying;

        @Override
        public java.util.Map<K, V> underlying() {
            return this.underlying;
        }

        @Override
        public boolean isEmpty() {
            return this.underlying().isEmpty();
        }

        @Override
        public int knownSize() {
            if (this.underlying().isEmpty()) {
                return 0;
            }
            return -1;
        }

        @Override
        public JMapWrapper<K, V> empty() {
            return new JMapWrapper(new HashMap());
        }

        public JMapWrapper(java.util.Map<K, V> underlying) {
            this.underlying = underlying;
        }
    }

    public static interface JMapWrapperLike<K, V, CC extends MapOps<Object, Object, CC, ?>, C extends MapOps<K, V, CC, C>>
    extends MapOps<K, V, CC, C>,
    StrictOptimizedMapOps<K, V, CC, C> {
        public java.util.Map<K, V> underlying();

        @Override
        default public int size() {
            return this.underlying().size();
        }

        @Override
        default public Option<V> get(K k) {
            V v = this.underlying().get(k);
            if (v != null) {
                return new Some<V>(v);
            }
            if (this.underlying().containsKey(k)) {
                return new Some<Object>(null);
            }
            return None$.MODULE$;
        }

        @Override
        default public V getOrElseUpdate(K key, Function0<V> op) {
            Object object = this.underlying().computeIfAbsent(key, x$4 -> op.apply());
            if (object == null) {
                this.update(key, null);
                return null;
            }
            return (V)object;
        }

        default public JMapWrapperLike<K, V, CC, C> addOne(Tuple2<K, V> kv) {
            this.underlying().put(kv._1(), kv._2());
            return this;
        }

        default public JMapWrapperLike<K, V, CC, C> subtractOne(K key) {
            this.underlying().remove(key);
            return this;
        }

        @Override
        default public Option<V> put(K k, V v) {
            if (v == null) {
                boolean present = this.underlying().containsKey(k);
                Object result = this.underlying().put(k, null);
                if (present) {
                    return new Some<Object>(result);
                }
                return None$.MODULE$;
            }
            None$ create_e = None$.MODULE$;
            Object var6_5 = null;
            ObjectRef<None$> result = new ObjectRef<None$>(create_e);
            this.underlying().compute(k, (k0, v0) -> this.recompute$1(k0, v0, v, result));
            return (Option)result.elem;
        }

        @Override
        default public void update(K k, V v) {
            this.underlying().put(k, v);
        }

        @Override
        default public Option<V> updateWith(K key, Function1<Option<V>, Option<V>> remappingFunction) {
            try {
                return Option$.MODULE$.apply(this.underlying().compute(key, (k, v) -> JMapWrapperLike.remap$1(k, v, remappingFunction)));
            }
            catch (Throwable throwable) {
                ControlThrowable controlThrowable = JavaCollectionWrappers$.MODULE$.scala$collection$convert$JavaCollectionWrappers$$PutNull();
                if (!(controlThrowable != null ? !controlThrowable.equals(throwable) : throwable != null)) {
                    this.update(key, null);
                    return new Some<Object>(null);
                }
                throw throwable;
            }
        }

        @Override
        default public Option<V> remove(K k) {
            None$ create_e = None$.MODULE$;
            Object var3_2 = null;
            ObjectRef<None$> result = new ObjectRef<None$>(create_e);
            this.underlying().compute(k, (k0, v0) -> this.recompute$2(k0, v0, result));
            return (Option)result.elem;
        }

        @Override
        default public Iterator<Tuple2<K, V>> iterator() {
            return new AbstractIterator<Tuple2<K, V>>(this){
                private final java.util.Iterator<Map.Entry<K, V>> ui;

                private java.util.Iterator<Map.Entry<K, V>> ui() {
                    return this.ui;
                }

                public boolean hasNext() {
                    return this.ui().hasNext();
                }

                public Tuple2<K, V> next() {
                    Map.Entry<K, V> e = this.ui().next();
                    return new Tuple2<K, V>(e.getKey(), e.getValue());
                }
                {
                    this.ui = $outer.underlying().entrySet().iterator();
                }
            };
        }

        @Override
        default public <U> void foreachEntry(Function2<K, V, U> f) {
            for (Map.Entry<K, V> entry : this.underlying().entrySet()) {
                f.apply(entry.getKey(), entry.getValue());
            }
        }

        @Override
        default public void clear() {
            this.underlying().clear();
        }

        public static /* synthetic */ void $anonfun$put$1(JMapWrapperLike $this, Object v0$1, ObjectRef result$1, Object k0$1, Object x$5) {
            if (v0$1 != null) {
                result$1.elem = new Some<Object>(v0$1);
                return;
            }
            if ($this.underlying().containsKey(k0$1)) {
                result$1.elem = new Some<Object>(null);
                return;
            }
        }

        private Object recompute$1(Object k0, Object v0, Object v$2, ObjectRef result$1) {
            if (v0 != null) {
                result$1.elem = new Some<Object>(v0);
            } else if (this.underlying().containsKey(k0)) {
                result$1.elem = new Some<Object>(null);
            }
            return v$2;
        }

        private static Object remap$1(Object k, Object v, Function1 remappingFunction$1) {
            boolean bl = false;
            Some some = null;
            Option option = (Option)remappingFunction$1.apply(Option$.MODULE$.apply(v));
            if (option instanceof Some) {
                bl = true;
                some = (Some)option;
                if (some.value() == null) {
                    throw JavaCollectionWrappers$.MODULE$.scala$collection$convert$JavaCollectionWrappers$$PutNull();
                }
            }
            if (bl) {
                return some.value();
            }
            if (None$.MODULE$.equals(option)) {
                return null;
            }
            throw new MatchError(option);
        }

        private Object recompute$2(Object k0, Object v0, ObjectRef result$2) {
            if (v0 != null) {
                result$2.elem = new Some<Object>(v0);
            } else if (this.underlying().containsKey(k0)) {
                result$2.elem = new Some<Object>(null);
            }
            return null;
        }

        public static void $init$(JMapWrapperLike $this) {
        }

        public static /* synthetic */ Object $anonfun$put$1$adapted(JMapWrapperLike $this, Object v0$1, ObjectRef result$1, Object k0$1, Object x$5) {
            JMapWrapperLike.$anonfun$put$1($this, v0$1, result$1, k0$1, x$5);
            return BoxedUnit.UNIT;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class JPropertiesWrapper
    extends scala.collection.mutable.AbstractMap<String, String>
    implements StrictOptimizedMapOps<String, String, scala.collection.mutable.Map, scala.collection.mutable.Map<String, String>>,
    Serializable {
        private static final long serialVersionUID = 3L;
        public final Properties scala$collection$convert$JavaCollectionWrappers$JPropertiesWrapper$$underlying;

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
        public IterableOps $plus(Tuple2 elem1, Tuple2 elem2, scala.collection.immutable.Seq elems) {
            return StrictOptimizedMapOps.$plus$(this, elem1, elem2, elems);
        }

        @Override
        public Tuple2<scala.collection.mutable.Map<String, String>, scala.collection.mutable.Map<String, String>> partition(Function1<Tuple2<String, String>, Object> p) {
            return StrictOptimizedIterableOps.partition$(this, p);
        }

        @Override
        public Tuple2<scala.collection.mutable.Map<String, String>, scala.collection.mutable.Map<String, String>> span(Function1<Tuple2<String, String>, Object> p) {
            return StrictOptimizedIterableOps.span$(this, p);
        }

        @Override
        public <A1, A2> Tuple2<scala.collection.mutable.Iterable<A1>, scala.collection.mutable.Iterable<A2>> unzip(Function1<Tuple2<String, String>, Tuple2<A1, A2>> asPair) {
            return StrictOptimizedIterableOps.unzip$(this, asPair);
        }

        @Override
        public <A1, A2, A3> Tuple3<scala.collection.mutable.Iterable<A1>, scala.collection.mutable.Iterable<A2>, scala.collection.mutable.Iterable<A3>> unzip3(Function1<Tuple2<String, String>, Tuple3<A1, A2, A3>> asTriple) {
            return StrictOptimizedIterableOps.unzip3$(this, asTriple);
        }

        @Override
        public Object map(Function1 f) {
            return StrictOptimizedIterableOps.map$(this, f);
        }

        @Override
        public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<Tuple2<String, String>, B> f) {
            return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
        }

        @Override
        public Object flatMap(Function1 f) {
            return StrictOptimizedIterableOps.flatMap$(this, f);
        }

        @Override
        public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<Tuple2<String, String>, IterableOnce<B>> f) {
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
        public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<Tuple2<String, String>, B> pf) {
            return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
        }

        @Override
        public Object flatten(Function1 toIterableOnce) {
            return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
        }

        @Override
        public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<Tuple2<String, String>, IterableOnce<B>> toIterableOnce) {
            return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
        }

        @Override
        public Object zip(IterableOnce that) {
            return StrictOptimizedIterableOps.zip$(this, that);
        }

        @Override
        public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<Tuple2<String, String>, B>, C2> b) {
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
        public <A1, A2> Tuple2<scala.collection.mutable.Iterable<A1>, scala.collection.mutable.Iterable<A2>> partitionMap(Function1<Tuple2<String, String>, Either<A1, A2>> f) {
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
        public int size() {
            return this.scala$collection$convert$JavaCollectionWrappers$JPropertiesWrapper$$underlying.size();
        }

        @Override
        public boolean isEmpty() {
            return this.scala$collection$convert$JavaCollectionWrappers$JPropertiesWrapper$$underlying.isEmpty();
        }

        @Override
        public int knownSize() {
            return this.size();
        }

        @Override
        public Option<String> get(String k) {
            Object v = this.scala$collection$convert$JavaCollectionWrappers$JPropertiesWrapper$$underlying.get(k);
            if (v != null) {
                return new Some<String>((String)v);
            }
            return None$.MODULE$;
        }

        public JPropertiesWrapper addOne(Tuple2<String, String> kv) {
            this.scala$collection$convert$JavaCollectionWrappers$JPropertiesWrapper$$underlying.put(kv._1(), kv._2());
            return this;
        }

        public JPropertiesWrapper subtractOne(String key) {
            this.scala$collection$convert$JavaCollectionWrappers$JPropertiesWrapper$$underlying.remove(key);
            return this;
        }

        @Override
        public Option<String> put(String k, String v) {
            Object r = this.scala$collection$convert$JavaCollectionWrappers$JPropertiesWrapper$$underlying.put(k, v);
            if (r != null) {
                return new Some<String>((String)r);
            }
            return None$.MODULE$;
        }

        @Override
        public void update(String k, String v) {
            this.scala$collection$convert$JavaCollectionWrappers$JPropertiesWrapper$$underlying.put(k, v);
        }

        @Override
        public Option<String> remove(String k) {
            Object r = this.scala$collection$convert$JavaCollectionWrappers$JPropertiesWrapper$$underlying.remove(k);
            if (r != null) {
                return new Some<String>((String)r);
            }
            return None$.MODULE$;
        }

        @Override
        public Iterator<Tuple2<String, String>> iterator() {
            return new AbstractIterator<Tuple2<String, String>>(this){
                private final java.util.Iterator<Map.Entry<Object, Object>> ui;

                private java.util.Iterator<Map.Entry<Object, Object>> ui() {
                    return this.ui;
                }

                public boolean hasNext() {
                    return this.ui().hasNext();
                }

                public Tuple2<String, String> next() {
                    Map.Entry<Object, Object> e = this.ui().next();
                    return new Tuple2<String, String>((String)e.getKey(), (String)e.getValue());
                }
                {
                    this.ui = $outer.scala$collection$convert$JavaCollectionWrappers$JPropertiesWrapper$$underlying.entrySet().iterator();
                }
            };
        }

        @Override
        public void clear() {
            this.scala$collection$convert$JavaCollectionWrappers$JPropertiesWrapper$$underlying.clear();
        }

        @Override
        public JPropertiesWrapper empty() {
            return new JPropertiesWrapper(new Properties());
        }

        public String getProperty(String key) {
            return this.scala$collection$convert$JavaCollectionWrappers$JPropertiesWrapper$$underlying.getProperty(key);
        }

        public String getProperty(String key, String defaultValue) {
            return this.scala$collection$convert$JavaCollectionWrappers$JPropertiesWrapper$$underlying.getProperty(key, defaultValue);
        }

        public Object setProperty(String key, String value) {
            return this.scala$collection$convert$JavaCollectionWrappers$JPropertiesWrapper$$underlying.setProperty(key, value);
        }

        public HashMap$ mapFactory() {
            return HashMap$.MODULE$;
        }

        public JPropertiesWrapper(Properties underlying) {
            this.scala$collection$convert$JavaCollectionWrappers$JPropertiesWrapper$$underlying = underlying;
        }
    }

    public static class JSetWrapper<A>
    extends AbstractSet<A>
    implements StrictOptimizedSetOps<A, scala.collection.mutable.Set, scala.collection.mutable.Set<A>>,
    Serializable {
        private static final long serialVersionUID = 3L;
        private final java.util.Set<A> underlying;

        @Override
        public SetOps concat(IterableOnce that) {
            return StrictOptimizedSetOps.concat$(this, that);
        }

        @Override
        public Tuple2<scala.collection.mutable.Set<A>, scala.collection.mutable.Set<A>> partition(Function1<A, Object> p) {
            return StrictOptimizedIterableOps.partition$(this, p);
        }

        @Override
        public Tuple2<scala.collection.mutable.Set<A>, scala.collection.mutable.Set<A>> span(Function1<A, Object> p) {
            return StrictOptimizedIterableOps.span$(this, p);
        }

        @Override
        public <A1, A2> Tuple2<scala.collection.mutable.Set<A1>, scala.collection.mutable.Set<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
            return StrictOptimizedIterableOps.unzip$(this, asPair);
        }

        @Override
        public <A1, A2, A3> Tuple3<scala.collection.mutable.Set<A1>, scala.collection.mutable.Set<A2>, scala.collection.mutable.Set<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
            return StrictOptimizedIterableOps.unzip3$(this, asTriple);
        }

        @Override
        public Object map(Function1 f) {
            return StrictOptimizedIterableOps.map$(this, f);
        }

        @Override
        public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<A, B> f) {
            return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
        }

        @Override
        public Object flatMap(Function1 f) {
            return StrictOptimizedIterableOps.flatMap$(this, f);
        }

        @Override
        public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<A, IterableOnce<B>> f) {
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
        public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<A, B> pf) {
            return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
        }

        @Override
        public Object flatten(Function1 toIterableOnce) {
            return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
        }

        @Override
        public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<A, IterableOnce<B>> toIterableOnce) {
            return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
        }

        @Override
        public Object zip(IterableOnce that) {
            return StrictOptimizedIterableOps.zip$(this, that);
        }

        @Override
        public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<A, B>, C2> b) {
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
        public <A1, A2> Tuple2<scala.collection.mutable.Set<A1>, scala.collection.mutable.Set<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
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

        public java.util.Set<A> underlying() {
            return this.underlying;
        }

        @Override
        public int size() {
            return this.underlying().size();
        }

        @Override
        public boolean isEmpty() {
            return this.underlying().isEmpty();
        }

        @Override
        public int knownSize() {
            if (this.underlying().isEmpty()) {
                return 0;
            }
            return -1;
        }

        @Override
        public Iterator<A> iterator() {
            java.util.Iterator<A> IteratorHasAsScala_i = this.underlying().iterator();
            CollectionConverters$ IteratorHasAsScala_this = CollectionConverters$.MODULE$;
            Object var1_2 = null;
            Object var2_1 = null;
            return new AsScalaExtensions.IteratorHasAsScala<A>(IteratorHasAsScala_this, IteratorHasAsScala_i).asScala();
        }

        @Override
        public boolean contains(A elem) {
            return this.underlying().contains(elem);
        }

        @Override
        public JSetWrapper<A> addOne(A elem) {
            this.underlying().add(elem);
            return this;
        }

        @Override
        public JSetWrapper<A> subtractOne(A elem) {
            this.underlying().remove(elem);
            return this;
        }

        @Override
        public boolean remove(A elem) {
            return this.underlying().remove(elem);
        }

        @Override
        public void clear() {
            this.underlying().clear();
        }

        @Override
        public scala.collection.mutable.Set<A> empty() {
            return new JSetWrapper(new HashSet());
        }

        @Override
        public scala.collection.mutable.Set<A> clone() {
            return new JSetWrapper<A>(new LinkedHashSet<A>(this.underlying()));
        }

        @Override
        public IterableFactory<scala.collection.mutable.Set> iterableFactory() {
            return HashSet$.MODULE$;
        }

        public JSetWrapper<A> filterInPlace(Function1<A, Object> p) {
            if (this.underlying().size() > 0) {
                this.underlying().removeIf(x$2 -> !BoxesRunTime.unboxToBoolean(p.apply(x$2)));
            }
            return this;
        }

        public JSetWrapper(java.util.Set<A> underlying) {
            this.underlying = underlying;
        }
    }

    public static class MapWrapper<K, V>
    extends AbstractMap<K, V>
    implements Serializable {
        private static final long serialVersionUID = 3L;
        public final Map<K, V> scala$collection$convert$JavaCollectionWrappers$MapWrapper$$underlying;

        @Override
        public int size() {
            return this.scala$collection$convert$JavaCollectionWrappers$MapWrapper$$underlying.size();
        }

        @Override
        public V get(Object key) {
            Object v0;
            block4: {
                try {
                    Option option = this.scala$collection$convert$JavaCollectionWrappers$MapWrapper$$underlying.get(key);
                    if (None$.MODULE$.equals(option)) {
                        v0 = null;
                        break block4;
                    }
                    if (option instanceof Some) {
                        v0 = ((Some)option).value();
                        break block4;
                    }
                    throw new MatchError(option);
                }
                catch (ClassCastException classCastException) {
                    v0 = null;
                }
            }
            return v0;
        }

        @Override
        public java.util.Set<Map.Entry<K, V>> entrySet() {
            return new java.util.AbstractSet<Map.Entry<K, V>>(this){
                private final /* synthetic */ MapWrapper $outer;

                public int size() {
                    return this.$outer.size();
                }

                public java.util.Iterator<Map.Entry<K, V>> iterator() {
                    return new java.util.Iterator<Map.Entry<K, V>>(this){
                        private final Iterator<Tuple2<K, V>> ui;
                        private Option<K> prev;
                        private final /* synthetic */ MapWrapper$$anon$2 $outer;

                        public void forEachRemaining(Consumer<? super Map.Entry<K, V>> x$1) {
                            java.util.Iterator.super.forEachRemaining(x$1);
                        }

                        private Iterator<Tuple2<K, V>> ui() {
                            return this.ui;
                        }

                        private Option<K> prev() {
                            return this.prev;
                        }

                        private void prev_$eq(Option<K> x$1) {
                            this.prev = x$1;
                        }

                        public boolean hasNext() {
                            return this.ui().hasNext();
                        }

                        /*
                         * WARNING - void declaration
                         */
                        public Map.Entry<K, V> next() {
                            void var3_3;
                            void var2_2;
                            Tuple2<K, V> tuple2 = this.ui().next();
                            if (tuple2 == null) {
                                throw new MatchError((Object)null);
                            }
                            K k = tuple2._1();
                            V v = tuple2._2();
                            this.prev_$eq(new Some<void>(var2_2));
                            return new Map.Entry<K, V>(this, (Object)var2_2, (Object)var3_3){
                                private final /* synthetic */ MapWrapper$$anon$2$$anon$3 $outer;
                                private final Object k$1;
                                private final Object v$1;

                                public K getKey() {
                                    return (K)this.k$1;
                                }

                                public V getValue() {
                                    return (V)this.v$1;
                                }

                                public V setValue(V v1) {
                                    return this.$outer.scala$collection$convert$JavaCollectionWrappers$MapWrapper$$anon$$anon$$$outer().scala$collection$convert$JavaCollectionWrappers$MapWrapper$$anon$$$outer().put(this.k$1, v1);
                                }

                                public int hashCode() {
                                    return (this.k$1 == null ? 0 : this.k$1.hashCode()) ^ (this.v$1 == null ? 0 : this.v$1.hashCode());
                                }

                                public boolean equals(Object other) {
                                    if (other instanceof Map.Entry) {
                                        Map.Entry entry = (Map.Entry)other;
                                        return BoxesRunTime.equals(this.k$1, entry.getKey()) && BoxesRunTime.equals(this.v$1, entry.getValue());
                                    }
                                    return false;
                                }
                                {
                                    if ($outer == null) {
                                        throw null;
                                    }
                                    this.$outer = $outer;
                                    this.k$1 = k$1;
                                    this.v$1 = v$1;
                                }
                            };
                        }

                        public void remove() {
                            Option<K> option = this.prev();
                            if (option instanceof Some) {
                                A k = ((Some)option).value();
                                Map<K, V> map = this.$outer.scala$collection$convert$JavaCollectionWrappers$MapWrapper$$anon$$$outer().scala$collection$convert$JavaCollectionWrappers$MapWrapper$$underlying;
                                if (map instanceof scala.collection.mutable.Map) {
                                    scala.collection.mutable.Map map2 = (scala.collection.mutable.Map)map;
                                    if (map2 == null) {
                                        throw null;
                                    }
                                    map2.subtractOne(k);
                                    this.prev_$eq(None$.MODULE$);
                                    return;
                                }
                                throw new UnsupportedOperationException("remove");
                            }
                            throw new IllegalStateException("next must be called at least once before remove");
                        }

                        public /* synthetic */ MapWrapper$$anon$2 scala$collection$convert$JavaCollectionWrappers$MapWrapper$$anon$$anon$$$outer() {
                            return this.$outer;
                        }
                        {
                            if ($outer == null) {
                                throw null;
                            }
                            this.$outer = $outer;
                            this.ui = $outer.scala$collection$convert$JavaCollectionWrappers$MapWrapper$$anon$$$outer().scala$collection$convert$JavaCollectionWrappers$MapWrapper$$underlying.iterator();
                            this.prev = None$.MODULE$;
                        }
                    };
                }

                public /* synthetic */ MapWrapper scala$collection$convert$JavaCollectionWrappers$MapWrapper$$anon$$$outer() {
                    return this.$outer;
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                }
            };
        }

        @Override
        public boolean containsKey(Object key) {
            try {
                return this.scala$collection$convert$JavaCollectionWrappers$MapWrapper$$underlying.contains(key);
            }
            catch (ClassCastException classCastException) {
                return false;
            }
        }

        public MapWrapper(Map<K, V> underlying) {
            this.scala$collection$convert$JavaCollectionWrappers$MapWrapper$$underlying = underlying;
        }
    }

    public static class MutableBufferWrapper<A>
    extends AbstractList<A>
    implements IterableWrapperTrait<A>,
    Serializable {
        private static final long serialVersionUID = 3L;
        private final Buffer<A> underlying;

        @Override
        public int size() {
            return ((IterableWrapperTrait)this).size();
        }

        @Override
        public IteratorWrapper<A> iterator() {
            return ((IterableWrapperTrait)this).iterator();
        }

        @Override
        public boolean isEmpty() {
            return ((IterableWrapperTrait)this).isEmpty();
        }

        @Override
        public Buffer<A> underlying() {
            return this.underlying;
        }

        @Override
        public A get(int i) {
            return (A)this.underlying().apply(i);
        }

        @Override
        public A set(int i, A elem) {
            Object p = this.underlying().apply(i);
            this.underlying().update(i, elem);
            return (A)p;
        }

        @Override
        public boolean add(A elem) {
            Iterable iterable = this.underlying();
            if (iterable == null) {
                throw null;
            }
            iterable.addOne(elem);
            return true;
        }

        @Override
        public A remove(int i) {
            return this.underlying().remove(i);
        }

        public MutableBufferWrapper(Buffer<A> underlying) {
            this.underlying = underlying;
        }
    }

    public static class MutableMapWrapper<K, V>
    extends MapWrapper<K, V> {
        private static final long serialVersionUID = 3L;
        private final scala.collection.mutable.Map<K, V> underlying;

        public scala.collection.mutable.Map<K, V> underlying() {
            return this.underlying;
        }

        @Override
        public V put(K k, V v) {
            Option<V> option = this.underlying().put(k, v);
            if (option instanceof Some) {
                return (V)((Some)option).value();
            }
            if (None$.MODULE$.equals(option)) {
                return null;
            }
            throw new MatchError(option);
        }

        @Override
        public V remove(Object k) {
            Object v0;
            block4: {
                try {
                    Option option = this.underlying().remove(k);
                    if (None$.MODULE$.equals(option)) {
                        v0 = null;
                        break block4;
                    }
                    if (option instanceof Some) {
                        v0 = ((Some)option).value();
                        break block4;
                    }
                    throw new MatchError(option);
                }
                catch (ClassCastException classCastException) {
                    v0 = null;
                }
            }
            return v0;
        }

        @Override
        public void clear() {
            this.underlying().clear();
        }

        public MutableMapWrapper(scala.collection.mutable.Map<K, V> underlying) {
            this.underlying = underlying;
            super(underlying);
        }
    }

    public static class MutableSeqWrapper<A>
    extends AbstractList<A>
    implements IterableWrapperTrait<A>,
    Serializable {
        private static final long serialVersionUID = 3L;
        private final scala.collection.mutable.Seq<A> underlying;

        @Override
        public int size() {
            return ((IterableWrapperTrait)this).size();
        }

        @Override
        public IteratorWrapper<A> iterator() {
            return ((IterableWrapperTrait)this).iterator();
        }

        @Override
        public boolean isEmpty() {
            return ((IterableWrapperTrait)this).isEmpty();
        }

        @Override
        public scala.collection.mutable.Seq<A> underlying() {
            return this.underlying;
        }

        @Override
        public A get(int i) {
            return (A)this.underlying().apply(i);
        }

        @Override
        public A set(int i, A elem) {
            Object p = this.underlying().apply(i);
            this.underlying().update(i, elem);
            return (A)p;
        }

        public MutableSeqWrapper(scala.collection.mutable.Seq<A> underlying) {
            this.underlying = underlying;
        }
    }

    public static class MutableSetWrapper<A>
    extends SetWrapper<A> {
        private static final long serialVersionUID = 3L;
        private final scala.collection.mutable.Set<A> underlying;

        public scala.collection.mutable.Set<A> underlying() {
            return this.underlying;
        }

        @Override
        public boolean add(A elem) {
            int sz = this.underlying().size();
            scala.collection.mutable.Set<A> set = this.underlying();
            if (set == null) {
                throw null;
            }
            set.addOne(elem);
            return sz < this.underlying().size();
        }

        @Override
        public boolean remove(Object elem) {
            try {
                return this.underlying().remove(elem);
            }
            catch (ClassCastException classCastException) {
                return false;
            }
        }

        @Override
        public void clear() {
            this.underlying().clear();
        }

        public MutableSetWrapper(scala.collection.mutable.Set<A> underlying) {
            this.underlying = underlying;
            super(underlying);
        }
    }

    public static class SeqWrapper<A>
    extends AbstractList<A>
    implements IterableWrapperTrait<A>,
    Serializable {
        private static final long serialVersionUID = 3L;
        private final Seq<A> underlying;

        @Override
        public int size() {
            return ((IterableWrapperTrait)this).size();
        }

        @Override
        public IteratorWrapper<A> iterator() {
            return ((IterableWrapperTrait)this).iterator();
        }

        @Override
        public boolean isEmpty() {
            return ((IterableWrapperTrait)this).isEmpty();
        }

        @Override
        public Seq<A> underlying() {
            return this.underlying;
        }

        @Override
        public A get(int i) {
            return (A)this.underlying().apply(i);
        }

        public SeqWrapper(Seq<A> underlying) {
            this.underlying = underlying;
        }
    }

    public static class SetWrapper<A>
    extends java.util.AbstractSet<A>
    implements Serializable {
        private static final long serialVersionUID = 3L;
        public final Set<A> scala$collection$convert$JavaCollectionWrappers$SetWrapper$$underlying;

        @Override
        public boolean contains(Object o) {
            try {
                return this.scala$collection$convert$JavaCollectionWrappers$SetWrapper$$underlying.contains(o);
            }
            catch (ClassCastException classCastException) {
                return false;
            }
        }

        @Override
        public boolean isEmpty() {
            return this.scala$collection$convert$JavaCollectionWrappers$SetWrapper$$underlying.isEmpty();
        }

        @Override
        public int size() {
            return this.scala$collection$convert$JavaCollectionWrappers$SetWrapper$$underlying.size();
        }

        @Override
        public java.util.Iterator<A> iterator() {
            return new java.util.Iterator<A>(this){
                private final Iterator<A> ui;
                private Option<A> prev;
                private final /* synthetic */ SetWrapper $outer;

                public void forEachRemaining(Consumer<? super A> x$1) {
                    java.util.Iterator.super.forEachRemaining(x$1);
                }

                private Iterator<A> ui() {
                    return this.ui;
                }

                private Option<A> prev() {
                    return this.prev;
                }

                private void prev_$eq(Option<A> x$1) {
                    this.prev = x$1;
                }

                public boolean hasNext() {
                    return this.ui().hasNext();
                }

                public A next() {
                    A e = this.ui().next();
                    this.prev_$eq(new Some<A>(e));
                    return e;
                }

                public void remove() {
                    Option<A> option = this.prev();
                    if (option instanceof Some) {
                        A e = ((Some)option).value();
                        Set<A> set = this.$outer.scala$collection$convert$JavaCollectionWrappers$SetWrapper$$underlying;
                        if (set instanceof scala.collection.mutable.Set) {
                            ((scala.collection.mutable.Set)set).remove(e);
                            this.prev_$eq(None$.MODULE$);
                            return;
                        }
                        throw new UnsupportedOperationException("remove");
                    }
                    throw new IllegalStateException("next must be called at least once before remove");
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    this.ui = $outer.scala$collection$convert$JavaCollectionWrappers$SetWrapper$$underlying.iterator();
                    this.prev = None$.MODULE$;
                }
            };
        }

        public SetWrapper(Set<A> underlying) {
            this.scala$collection$convert$JavaCollectionWrappers$SetWrapper$$underlying = underlying;
        }
    }
}

