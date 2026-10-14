/*
 * Decompiled with CFR 0.152.
 */
package scala.util.matching;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.collection.AbstractIterator;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.SeqOps;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Map$;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Range;
import scala.collection.immutable.Seq;
import scala.collection.immutable.WrappedString;
import scala.collection.mutable.ListBuffer;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.Statics;
import scala.util.matching.UnanchoredRegex;

@ScalaSignature(bytes="\u0006\u0005\r}e\u0001B8q\u0001]D!\"!\u0005\u0001\u0005\u000b\u0007I\u0011AA\n\u0011)\t9\u0003\u0001B\u0001B\u0003%\u0011Q\u0003\u0005\u000b\u0003S\u0001!\u0011!Q\u0001\n\u0005-\u0002\u0002CA!\u0001\u0011\u0005\u0001/a\u0011\t\u000f\u0005\u0005\u0003\u0001\"\u0001\u0002N!9\u00111\u000b\u0001\u0005\u0002\u0005U\u0003bBA*\u0001\u0011\u0005\u00111\u000f\u0005\b\u0003'\u0002A\u0011AAB\u0011\u001d\u0019I\u0002\u0001C\t\u00077Aqaa\b\u0001\t\u0003\u0019\t\u0003C\u0004\u0004(\u0001!\ta!\u000b\t\u000f\r=\u0002\u0001\"\u0001\u00042!91q\u0007\u0001\u0005\u0002\re\u0002bBB \u0001\u0011\u00051\u0011\t\u0005\b\u0007\u000b\u0002A\u0011AB$\u0011\u001d\u0019Y\u0005\u0001C\u0001\u0007\u001bBqa!\u0015\u0001\t\u0003\u0019\u0019\u0006C\u0004\u0004R\u0001!\ta!\u0018\t\u000f\r-\u0004\u0001\"\u0001\u0004n!91Q\u000f\u0001\u0005\u0002\r]\u0004bBB?\u0001\u0011\u00051q\u0010\u0005\b\u0007\u000f\u0003A\u0011ABE\u0011\u001d\u0019\t\n\u0001C\u0001\u0005GCq!a\u0007\u0001\t\u0003\t\t\u0010C\u0004\u0003\u001c\u0001!\tE!\b\b\u000f\u00055\u0005\u000f#\u0001\u0002\u0010\u001a1q\u000e\u001dE\u0001\u0003#Cq!!\u0011\u001c\t\u0003\tiJB\u0005\u0002 n\u0001\n1!\u0001\u0002\"\"9\u00111U\u000f\u0005\u0002\u0005\u0015\u0006bBAW;\u0019E\u0011q\u0016\u0005\n\u0003ok\"\u0019!D\u0001\u0003sC\u0011\"!\u000b\u001e\u0005\u00045\t!a/\t\u000f\u0005]WD\"\u0001\u0002Z\"9\u0011\u0011]\u000f\u0007\u0002\u0005e\u0007bBAq;\u0019\u0005\u00111\u001d\u0005\b\u0003Slb\u0011AAm\u0011\u001d\tI/\bD\u0001\u0003WDq!a<\u001e\t\u0003\t\t\u0010C\u0004\u0002tv!\t!!>\t\u000f\u0005eX\u0004\"\u0001\u0002|\"9\u0011Q`\u000f\u0005\u0002\u0005e\u0006bBA\u007f;\u0011\u0005\u0011q \u0005\b\u0005\u0007iB\u0011AA]\u0011\u001d\u0011\u0019!\bC\u0001\u0005\u000bAqA!\u0003\u001e\t\u0013\tY\f\u0003\u0006\u0003\fuA)\u0019)C\u0005\u0005\u001bAq!a=\u001e\t\u0003\u0011)\u0002C\u0004\u0003\u001cu!\tE!\b\u0007\r\t}1\u0004\u0001B\u0011\u0011)\t9L\rBC\u0002\u0013\u0005\u0011\u0011\u0018\u0005\u000b\u0005O\u0011$\u0011!Q\u0001\n\u0005\u001d\u0004bCAWe\t\u0015\r\u0011\"\u0005q\u0003_C!B!\u000b3\u0005\u0003\u0005\u000b\u0011BAY\u0011)\u0011YC\rB\u0001B\u0003%\u0011Q\u0018\u0005\b\u0003\u0003\u0012D\u0011\u0001B\u0017\u0011%\tIC\rb\u0001\n\u0003\tY\f\u0003\u0005\u0003:I\u0002\u000b\u0011BA_\u0011%\t\tO\rb\u0001\n\u0003\tI\u000e\u0003\u0005\u0003>I\u0002\u000b\u0011BAn\u0011%\tIO\rb\u0001\n\u0003\tI\u000e\u0003\u0005\u0003@I\u0002\u000b\u0011BAn\u0011\u001d\t9N\rC\u0001\u00033D!B!\u00113\u0011\u000b\u0007K\u0011\u0002B\"\u0011)\u0011YE\rECB\u0013%!1\t\u0005\b\u0003C\u0014D\u0011\u0001B'\u0011\u001d\tIO\rC\u0001\u0005#BqA!\u00163\t\u0003\u00119fB\u0004\u0003\\mA\tA!\u0018\u0007\u000f\t}1\u0004#\u0001\u0003`!9\u0011\u0011\t$\u0005\u0002\t\u0005\u0004b\u0002B2\r\u0012\u0005!QM\u0004\b\u0005_Z\u0002\u0012\u0001B9\r\u001d\u0011\u0019h\u0007E\u0001\u0005kBq!!\u0011K\t\u0003\u00119\bC\u0004\u0002T)#\tA!\u001f\t\u000f\t}4\u0004\"\u0003\u0003\u0002\u001a1!QR\u000e\u0001\u0005\u001fC!\"a.O\u0005\u000b\u0007I\u0011AA]\u0011)\u00119C\u0014B\u0001B\u0003%\u0011q\r\u0005\u000b\u00037q%Q1A\u0005\u0002\t\r\u0006B\u0003BS\u001d\n\u0005\t\u0015!\u0003\u0002F!Y!1\u0006(\u0003\u0006\u0004%\taGA^\u0011)\u00119K\u0014B\u0001B\u0003%\u0011Q\u0018\u0005\b\u0003\u0003rE\u0011\u0001BU\u0011%\tIC\u0014b\u0001\n\u0003\tY\f\u0003\u0005\u0003:9\u0003\u000b\u0011BA_\u0011)\tiK\u0014b\u0001\n#Y\u0012q\u0016\u0005\t\u0005Sq\u0005\u0015!\u0003\u00022\"A!q\u0017(!B\u0013\tY\u000eC\u0004\u0003::#\tAa/\t\u000f\t\rg\n\"\u0001\u0003\u001e!9!1\u0004(\u0005B\tu\u0001\u0002\u0003Bc\u001d\u0002&I!!*\t\u000f\u0005\u0005h\n\"\u0001\u0002Z\"9\u0011\u0011\u001d(\u0005\u0002\t\u001d\u0007bBAu\u001d\u0012\u0005\u0011\u0011\u001c\u0005\b\u0003StE\u0011\u0001Bf\u0011\u001d\t9N\u0014C\u0001\u00033DqAa4O\t\u0003\u0011\t\u000e\u0003\u0005\u0003V:#\t\u0001\u001dBl\r)\u0011\u0019o\u0007I\u0001\u0004\u0003\u0001(Q\u001d\u0005\b\u0003G3G\u0011AAS\u0011\u001d\tiK\u001aD\t\u0003_C\u0011Ba:g\u0005\u0004&IA!;\t\u000f\tEh\r\"\u0001\u0003t\"9!\u0011 4\u0005\u0002\tm\bbBB\u00017\u0011\u000511\u0001\u0005\b\u0007\u0013YB\u0011AB\u0006\u0011%\u0019yaGA\u0001\n\u0013\u0019\tBA\u0003SK\u001e,\u0007P\u0003\u0002re\u0006AQ.\u0019;dQ&twM\u0003\u0002ti\u0006!Q\u000f^5m\u0015\u0005)\u0018!B:dC2\f7\u0001A\n\u0004\u0001ad\bCA={\u001b\u0005!\u0018BA>u\u0005\u0019\te.\u001f*fMB\u0019Q0a\u0003\u000f\u0007y\f9AD\u0002\u0000\u0003\u000bi!!!\u0001\u000b\u0007\u0005\ra/\u0001\u0004=e>|GOP\u0005\u0002k&\u0019\u0011\u0011\u0002;\u0002\u000fA\f7m[1hK&!\u0011QBA\b\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\r\tI\u0001^\u0001\ba\u0006$H/\u001a:o+\t\t)\u0002\u0005\u0003\u0002\u0018\u0005\rRBAA\r\u0015\u0011\tY\"!\b\u0002\u000bI,w-\u001a=\u000b\u0007M\fyB\u0003\u0002\u0002\"\u0005!!.\u0019<b\u0013\u0011\t)#!\u0007\u0003\u000fA\u000bG\u000f^3s]\u0006A\u0001/\u0019;uKJt\u0007%\u0001\u0006he>,\bOT1nKN\u0004R!_A\u0017\u0003cI1!a\fu\u0005)a$/\u001a9fCR,GM\u0010\t\u0005\u0003g\tYD\u0004\u0003\u00026\u0005]\u0002CA@u\u0013\r\tI\u0004^\u0001\u0007!J,G-\u001a4\n\t\u0005u\u0012q\b\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005eB/\u0001\u0004=S:LGO\u0010\u000b\u0007\u0003\u000b\nI%a\u0013\u0011\u0007\u0005\u001d\u0003!D\u0001q\u0011\u001d\t\t\u0002\u0002a\u0001\u0003+Aq!!\u000b\u0005\u0001\u0004\tY\u0003\u0006\u0004\u0002F\u0005=\u0013\u0011\u000b\u0005\b\u00037)\u0001\u0019AA\u0019\u0011\u001d\tI#\u0002a\u0001\u0003W\t!\"\u001e8baBd\u0017pU3r)\u0011\t9&a\u0019\u0011\u000be\fI&!\u0018\n\u0007\u0005mCO\u0001\u0004PaRLwN\u001c\t\u0006{\u0006}\u0013\u0011G\u0005\u0005\u0003C\nyA\u0001\u0003MSN$\bbBA3\r\u0001\u0007\u0011qM\u0001\u0002gB!\u0011\u0011NA8\u001b\t\tYG\u0003\u0003\u0002n\u0005}\u0011\u0001\u00027b]\u001eLA!!\u001d\u0002l\ta1\t[1s'\u0016\fX/\u001a8dKR!\u0011QOA@!\u0015I\u0018\u0011LA<!\u0015i\u0018qLA=!\rI\u00181P\u0005\u0004\u0003{\"(\u0001B\"iCJDq!!!\b\u0001\u0004\tI(A\u0001d)\u0011\t9&!\"\t\u000f\u0005\u001d\u0005\u00021\u0001\u0002\n\u0006\tQ\u000eE\u0002\u0002\fJr1!a\u0012\u001b\u0003\u0015\u0011VmZ3y!\r\t9eG\n\u00057a\f\u0019\n\u0005\u0003\u0002\u0016\u0006mUBAAL\u0015\u0011\tI*a\b\u0002\u0005%|\u0017\u0002BA\u0007\u0003/#\"!a$\u0003\u00135\u000bGo\u00195ECR\f7CA\u000fy\u0003\u0019!\u0013N\\5uIQ\u0011\u0011q\u0015\t\u0004s\u0006%\u0016bAAVi\n!QK\\5u\u0003\u001di\u0017\r^2iKJ,\"!!-\u0011\t\u0005]\u00111W\u0005\u0005\u0003k\u000bIBA\u0004NCR\u001c\u0007.\u001a:\u0002\rM|WO]2f+\t\t9'\u0006\u0002\u0002>B)Q0a0\u00022%!\u0011\u0011YA\b\u0005\r\u0019V-\u001d\u0015\fC\u0005\u0015\u00171ZAg\u0003#\f\u0019\u000eE\u0002z\u0003\u000fL1!!3u\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\t\ty-\u0001(he>,\bOT1nKN\u0004Cm\\3tA9|G\u000fI5oG2,H-\u001a\u0011j]2Lg.\u001a\u0011he>,\b\u000f\t8b[\u0016\u001cH\u0006I1oI\u0002\u001a\bn\\;mI\u0002rw\u000e\u001e\u0011cK\u0002*8/\u001a3!C:LXn\u001c:f\u0003\u0015\u0019\u0018N\\2fC\t\t).\u0001\u00043]E\u001adfN\u0001\u000bOJ|W\u000f]\"pk:$XCAAn!\rI\u0018Q\\\u0005\u0004\u0003?$(aA%oi\u0006)1\u000f^1siR!\u00111\\As\u0011\u001d\t9\u000f\na\u0001\u00037\f\u0011![\u0001\u0004K:$G\u0003BAn\u0003[Dq!a:'\u0001\u0004\tY.A\u0004nCR\u001c\u0007.\u001a3\u0016\u0005\u0005E\u0012!B4s_V\u0004H\u0003BA\u0019\u0003oDq!a:)\u0001\u0004\tY.A\u0005tk\n<'o\\;qgV\u0011\u0011QL\u0001\u0007E\u00164wN]3\u0015\t\u0005\u001d$\u0011\u0001\u0005\b\u0003O\\\u0003\u0019AAn\u0003\u0015\tg\r^3s)\u0011\t9Ga\u0002\t\u000f\u0005\u001dX\u00061\u0001\u0002\\\u0006\u0001rM]8va:\u000bW.Z:O_^\f'O\\\u0001\f]\u0006lW\rV8J]\u0012,\u00070\u0006\u0002\u0003\u0010AA\u00111\u0007B\t\u0003c\tY.\u0003\u0003\u0003\u0014\u0005}\"aA'baR!\u0011\u0011\u0007B\f\u0011\u001d\u0011I\u0002\ra\u0001\u0003c\t!!\u001b3\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!!\r\u0003\u000b5\u000bGo\u00195\u0014\tIB(1\u0005\t\u0004\u0005KiR\"A\u000e\u0002\u000fM|WO]2fA\u0005AQ.\u0019;dQ\u0016\u0014\b%A\u0006`OJ|W\u000f\u001d(b[\u0016\u001cH\u0003\u0003B\u0018\u0005c\u0011\u0019D!\u000e\u0011\u0007\t\u0015\"\u0007C\u0004\u00028b\u0002\r!a\u001a\t\u000f\u00055\u0006\b1\u0001\u00022\"9!1\u0006\u001dA\u0002\u0005u\u0006fC\u001d\u0002F\u0006-\u0017QZAi\u0003'\f1b\u001a:pkBt\u0015-\\3tA!Z!(!2\u0002L\u00065\u0017\u0011[Aj\u0003\u0019\u0019H/\u0019:uA\u0005!QM\u001c3!\u0003\u0019\u0019H/\u0019:ugV\u0011!Q\t\t\u0006s\n\u001d\u00131\\\u0005\u0004\u0005\u0013\"(!B!se\u0006L\u0018\u0001B3oIN$B!a7\u0003P!9\u0011q\u001d\"A\u0002\u0005mG\u0003BAn\u0005'Bq!a:D\u0001\u0004\tY.A\u0003g_J\u001cW-\u0006\u0002\u0003Z5\t!'A\u0003NCR\u001c\u0007\u000eE\u0002\u0003&\u0019\u001b\"A\u0012=\u0015\u0005\tu\u0013aB;oCB\u0004H.\u001f\u000b\u0005\u0005O\u0012i\u0007E\u0003z\u0005S\n\t$C\u0002\u0003lQ\u0014AaU8nK\"9\u0011q\u0011%A\u0002\t=\u0012AB$s_V\u00048\u000fE\u0002\u0003&)\u0013aa\u0012:pkB\u001c8C\u0001&y)\t\u0011\t\b\u0006\u0003\u0003|\tu\u0004#B=\u0002Z\u0005u\u0006bBAD\u0019\u0002\u0007!qF\u0001\u0017Kb$(/Y2u\u000fJ|W\u000f]:Ge>lW*\u0019;dQR!\u0011q\u000bBB\u0011\u001d\t9)\u0014a\u0001\u0005_A3!\u0014BD!\rI(\u0011R\u0005\u0004\u0005\u0017#(AB5oY&tWMA\u0007NCR\u001c\u0007.\u0013;fe\u0006$xN]\n\b\u001d\nE%Q\u0014B\u0012!\u0019\u0011\u0019J!'\u000225\u0011!Q\u0013\u0006\u0004\u0005/#\u0018AC2pY2,7\r^5p]&!!1\u0014BK\u0005A\t%m\u001d;sC\u000e$\u0018\n^3sCR|'\u000fE\u0003~\u0005?\u000b\t$\u0003\u0003\u0003\"\u0006=!\u0001C%uKJ\fGo\u001c:\u0016\u0005\u0005\u0015\u0013A\u0002:fO\u0016D\b%\u0001\u0007`OJ|W\u000f\u001d(b[\u0016\u001c\b\u0005\u0006\u0005\u0003,\n5&q\u0016BY!\r\u0011)C\u0014\u0005\b\u0003o+\u0006\u0019AA4\u0011\u001d\tY\"\u0016a\u0001\u0003\u000bBqAa\u000bV\u0001\u0004\ti\fK\u0006W\u0003\u000b\fY-!4\u0002R\u0006M\u0007fC,\u0002F\u0006-\u0017QZAi\u0003'\f\u0001B\\3yiN+WM\\\u0001\bQ\u0006\u001ch*\u001a=u+\t\u0011i\fE\u0002z\u0005\u007fK1A!1u\u0005\u001d\u0011un\u001c7fC:\fAA\\3yi\u00061QM\\:ve\u0016$B!a7\u0003J\"9\u0011q\u001d1A\u0002\u0005mG\u0003BAn\u0005\u001bDq!a:c\u0001\u0004\tY.A\u0005nCR\u001c\u0007\u000eR1uCV\u0011!1\u001b\t\u0006{\n}%qF\u0001\u0010e\u0016\u0004H.Y2f[\u0016tG\u000fR1uCV\u0011!\u0011\u001c\n\u0007\u00057\u0014yN!9\u0007\r\tuW\r\u0001Bm\u00051a$/\u001a4j]\u0016lWM\u001c;?!\u0019\u0011\u0019J!'\u00030A\u0019!Q\u00054\u0003\u0017I+\u0007\u000f\\1dK6,g\u000e^\n\u0003Mb\f!a\u001d2\u0016\u0005\t-\b\u0003BA5\u0005[LAAa<\u0002l\ta1\u000b\u001e:j]\u001e\u0014UO\u001a4fe\u0006A!/\u001a9mC\u000e,G-\u0006\u0002\u0003vB!\u0011\u0011\u000eB|\u0013\u0011\ti$a\u001b\u0002\u000fI,\u0007\u000f\\1dKR!\u0011\u0011\u0017B\u007f\u0011\u001d\u0011yp\u001ba\u0001\u0003c\t!A]:\u0002\u000bE,x\u000e^3\u0015\t\u0005E2Q\u0001\u0005\b\u0007\u000fa\u0007\u0019AA\u0019\u0003\u0011!X\r\u001f;\u0002!E,x\u000e^3SKBd\u0017mY3nK:$H\u0003BA\u0019\u0007\u001bAqaa\u0002n\u0001\u0004\t\t$\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0004\u0014A!\u0011\u0011NB\u000b\u0013\u0011\u00199\"a\u001b\u0003\r=\u0013'.Z2u\u0003)\u0011XO\\'bi\u000eDWM\u001d\u000b\u0005\u0005{\u001bi\u0002C\u0004\u0002\b&\u0001\r!!-\u0002\u0013\u0019Lg\u000eZ!mY&sG\u0003BB\u0012\u0007K\u00012!a#O\u0011\u001d\t9L\u0003a\u0001\u0003O\naBZ5oI\u0006cG.T1uG\"Le\u000e\u0006\u0003\u0004,\r5\u0002#B?\u0003 \u0006%\u0005bBA\\\u0017\u0001\u0007\u0011qM\u0001\fM&tGMR5sgRLe\u000e\u0006\u0003\u00044\rU\u0002#B=\u0002Z\u0005E\u0002bBA\\\u0019\u0001\u0007\u0011qM\u0001\u0011M&tGMR5sgRl\u0015\r^2i\u0013:$Baa\u000f\u0004>A)\u00110!\u0017\u0002\n\"9\u0011qW\u0007A\u0002\u0005\u001d\u0014\u0001\u00044j]\u0012\u0004&/\u001a4jq>3G\u0003BB\u001a\u0007\u0007Bq!a.\u000f\u0001\u0004\t9'A\tgS:$\u0007K]3gSbl\u0015\r^2i\u001f\u001a$Baa\u000f\u0004J!9\u0011qW\bA\u0002\u0005\u001d\u0014aB7bi\u000eDWm\u001d\u000b\u0005\u0005{\u001by\u0005C\u0004\u00028B\u0001\r!a\u001a\u0002\u0019I,\u0007\u000f\\1dK\u0006cG.\u00138\u0015\r\u0005E2QKB-\u0011\u001d\u00199&\u0005a\u0001\u0003O\na\u0001^1sO\u0016$\bbBB.#\u0001\u0007\u0011\u0011G\u0001\fe\u0016\u0004H.Y2f[\u0016tG\u000f\u0006\u0004\u00022\r}3\u0011\r\u0005\b\u0007/\u0012\u0002\u0019AA4\u0011\u001d\u0019\u0019G\u0005a\u0001\u0007K\n\u0001B]3qY\u0006\u001cWM\u001d\t\bs\u000e\u001d\u0014\u0011RA\u0019\u0013\r\u0019I\u0007\u001e\u0002\n\rVt7\r^5p]F\nQB]3qY\u0006\u001cWmU8nK&sGCBA\u0019\u0007_\u001a\t\bC\u0004\u0004XM\u0001\r!a\u001a\t\u000f\r\r4\u00031\u0001\u0004tA9\u0011pa\u001a\u0002\n\u000eM\u0012A\u0004:fa2\f7-\u001a$jeN$\u0018J\u001c\u000b\u0007\u0003c\u0019Iha\u001f\t\u000f\r]C\u00031\u0001\u0002h!911\f\u000bA\u0002\u0005E\u0012!B:qY&$H\u0003BBA\u0007\u0007\u0003R!\u001fB$\u0003cAqa!\"\u0016\u0001\u0004\t9'A\u0004u_N\u0003H.\u001b;\u0002\u0015Ut\u0017M\\2i_J,G-\u0006\u0002\u0004\fB!\u0011qIBG\u0013\r\u0019y\t\u001d\u0002\u0010+:\fgn\u00195pe\u0016$'+Z4fq\u0006A\u0011M\\2i_J,G\rK\u0004\u0001\u0007+\u001bYj!(\u0011\u0007e\u001c9*C\u0002\u0004\u001aR\u0014\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u0002\u000bY\fG.^3\u001f\u0011\tp_[\u001bPqD}\u0006")
public class Regex
implements Serializable {
    private static final long serialVersionUID = -2094783597747625537L;
    private final Pattern pattern;
    public final Seq<String> scala$util$matching$Regex$$groupNames;

    public static String quoteReplacement(String text) {
        return Matcher.quoteReplacement(text);
    }

    public static String quote(String text) {
        return Pattern.quote(text);
    }

    public Pattern pattern() {
        return this.pattern;
    }

    public Option<List<String>> unapplySeq(CharSequence s2) {
        Matcher m4 = this.pattern().matcher(s2);
        if (this.runMatcher(m4)) {
            Object var6_6;
            int tabulate_n;
            int n = m4.groupCount();
            if (package$.MODULE$.List() == null) {
                throw null;
            }
            ListBuffer<String> tabulate_b = new ListBuffer<String>();
            tabulate_b.sizeHint(tabulate_n);
            for (int tabulate_i = 0; tabulate_i < tabulate_n; ++tabulate_i) {
                String tabulate_$plus$eq_elem = m4.group(tabulate_i + 1);
                tabulate_b.addOne(tabulate_$plus$eq_elem);
                var6_6 = null;
            }
            Object var4_4 = null;
            var6_6 = null;
            return new Some<SeqOps>((SeqOps)tabulate_b.result());
        }
        return None$.MODULE$;
    }

    public Option<List<Object>> unapplySeq(char c) {
        Matcher m4 = this.pattern().matcher(Character.toString(c));
        if (this.runMatcher(m4)) {
            if (m4.groupCount() > 0) {
                WrappedString wrappedString = Predef$.MODULE$.wrapString(m4.group(1));
                if (wrappedString == null) {
                    throw null;
                }
                return new Some<List<Object>>(IterableOnceOps.toList$(wrappedString));
            }
            return new Some<List<Object>>(Nil$.MODULE$);
        }
        return None$.MODULE$;
    }

    public Option<List<String>> unapplySeq(Match m4) {
        if (m4.matched() == null) {
            return None$.MODULE$;
        }
        Pattern pattern = m4.matcher().pattern();
        Pattern pattern2 = this.pattern();
        if (!(pattern != null ? !pattern.equals(pattern2) : pattern2 != null)) {
            Object var6_6;
            int scala$util$matching$Regex$$extractGroupsFromMatch_tabulate_n;
            int n = m4.groupCount();
            if (package$.MODULE$.List() == null) {
                throw null;
            }
            ListBuffer<String> tabulate_b = new ListBuffer<String>();
            tabulate_b.sizeHint(scala$util$matching$Regex$$extractGroupsFromMatch_tabulate_n);
            for (int tabulate_i = 0; tabulate_i < scala$util$matching$Regex$$extractGroupsFromMatch_tabulate_n; ++tabulate_i) {
                String tabulate_$plus$eq_elem = m4.group(tabulate_i + 1);
                tabulate_b.addOne(tabulate_$plus$eq_elem);
                var6_6 = null;
            }
            Object var4_4 = null;
            var6_6 = null;
            return new Some<SeqOps>((SeqOps)tabulate_b.result());
        }
        return this.unapplySeq(m4.matched());
    }

    public boolean runMatcher(Matcher m4) {
        return m4.matches();
    }

    public MatchIterator findAllIn(CharSequence source2) {
        return new MatchIterator(source2, this, this.scala$util$matching$Regex$$groupNames);
    }

    public Iterator<Match> findAllMatchIn(CharSequence source2) {
        MatchIterator matchIterator = this.findAllIn(source2);
        return new AbstractIterator<Match>(null, matchIterator){
            private final MatchIterator matchIterator$1;

            public boolean hasNext() {
                return this.matchIterator$1.hasNext();
            }

            public Match next() {
                this.matchIterator$1.next();
                return new Match(this.matchIterator$1.source(), this.matchIterator$1.matcher(), this.matchIterator$1._groupNames()).force();
            }
            {
                this.matchIterator$1 = matchIterator$1;
            }
        };
    }

    public Option<String> findFirstIn(CharSequence source2) {
        Matcher m4 = this.pattern().matcher(source2);
        if (m4.find()) {
            return new Some<String>(m4.group());
        }
        return None$.MODULE$;
    }

    public Option<Match> findFirstMatchIn(CharSequence source2) {
        Matcher m4 = this.pattern().matcher(source2);
        if (m4.find()) {
            return new Some<Match>(new Match(source2, m4, this.scala$util$matching$Regex$$groupNames));
        }
        return None$.MODULE$;
    }

    public Option<String> findPrefixOf(CharSequence source2) {
        Matcher m4 = this.pattern().matcher(source2);
        if (m4.lookingAt()) {
            return new Some<String>(m4.group());
        }
        return None$.MODULE$;
    }

    public Option<Match> findPrefixMatchOf(CharSequence source2) {
        Matcher m4 = this.pattern().matcher(source2);
        if (m4.lookingAt()) {
            return new Some<Match>(new Match(source2, m4, this.scala$util$matching$Regex$$groupNames));
        }
        return None$.MODULE$;
    }

    public boolean matches(CharSequence source2) {
        return this.runMatcher(this.pattern().matcher(source2));
    }

    public String replaceAllIn(CharSequence target, String replacement) {
        return this.pattern().matcher(target).replaceAll(replacement);
    }

    public String replaceAllIn(CharSequence target, Function1<Match, String> replacer) {
        AbstractIterator<Match> it = new MatchIterator(target, this, this.scala$util$matching$Regex$$groupNames).replacementData();
        it.foreach((Function1<Match, Matcher> & Serializable)md -> ((Replacement)((Object)it)).replace((String)replacer.apply((Match)md)));
        return ((Replacement)((Object)it)).replaced();
    }

    public String replaceSomeIn(CharSequence target, Function1<Match, Option<String>> replacer) {
        AbstractIterator<Match> it = new MatchIterator(target, this, this.scala$util$matching$Regex$$groupNames).replacementData();
        it.foreach((Function1<Match, Object> & Serializable)matchdata -> {
            Regex.$anonfun$replaceSomeIn$1(replacer, it, matchdata);
            return BoxedUnit.UNIT;
        });
        return ((Replacement)((Object)it)).replaced();
    }

    public String replaceFirstIn(CharSequence target, String replacement) {
        return this.pattern().matcher(target).replaceFirst(replacement);
    }

    public String[] split(CharSequence toSplit) {
        return this.pattern().split(toSplit);
    }

    public UnanchoredRegex unanchored() {
        return new UnanchoredRegex(this){
            private final /* synthetic */ Regex $outer;

            public boolean runMatcher(Matcher m4) {
                return UnanchoredRegex.runMatcher$(this, m4);
            }

            public UnanchoredRegex unanchored() {
                return UnanchoredRegex.unanchored$(this);
            }

            public Regex anchored() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.pattern(), $outer.scala$util$matching$Regex$$groupNames);
            }
        };
    }

    public Regex anchored() {
        return this;
    }

    public String regex() {
        return this.pattern().pattern();
    }

    public String toString() {
        return this.regex();
    }

    public static final /* synthetic */ Matcher $anonfun$replaceSomeIn$2(AbstractIterator it$2, String replacement) {
        return ((Replacement)((Object)it$2)).replace(replacement);
    }

    public static final /* synthetic */ void $anonfun$replaceSomeIn$1(Function1 replacer$2, AbstractIterator it$2, Match matchdata) {
        Option option = (Option)replacer$2.apply(matchdata);
        if (option == null) {
            throw null;
        }
        Option foreach_this = option;
        if (!foreach_this.isEmpty()) {
            String string = (String)foreach_this.get();
            ((Replacement)((Object)it$2)).replace(string);
            return;
        }
    }

    public Regex(Pattern pattern, Seq<String> groupNames) {
        this.pattern = pattern;
        this.scala$util$matching$Regex$$groupNames = groupNames;
    }

    public Regex(String regex2, Seq<String> groupNames) {
        this(Pattern.compile(regex2), groupNames);
    }

    public static final /* synthetic */ String $anonfun$unapplySeq$1$adapted(Matcher m$1, Object i) {
        return m$1.group(BoxesRunTime.unboxToInt(i) + 1);
    }

    public static class Match
    implements MatchData {
        private int[] starts;
        private int[] ends;
        private final CharSequence source;
        private final Matcher matcher;
        private final Seq<String> groupNames;
        private final int start;
        private final int end;
        private Map<String, Object> scala$util$matching$Regex$MatchData$$nameToIndex;
        private volatile byte bitmap$0;

        @Override
        public String matched() {
            return ((MatchData)this).matched();
        }

        @Override
        public String group(int i) {
            return ((MatchData)this).group(i);
        }

        @Override
        public List<String> subgroups() {
            return ((MatchData)this).subgroups();
        }

        @Override
        public CharSequence before() {
            return ((MatchData)this).before();
        }

        @Override
        public CharSequence before(int i) {
            return ((MatchData)this).before(i);
        }

        @Override
        public CharSequence after() {
            return ((MatchData)this).after();
        }

        @Override
        public CharSequence after(int i) {
            return ((MatchData)this).after(i);
        }

        @Override
        public String group(String id) {
            return ((MatchData)this).group(id);
        }

        @Override
        public String toString() {
            return ((MatchData)this).toString();
        }

        private Map<String, Object> scala$util$matching$Regex$MatchData$$nameToIndex$lzycompute() {
            synchronized (this) {
                if ((byte)(this.bitmap$0 & 4) == 0) {
                    this.scala$util$matching$Regex$MatchData$$nameToIndex = ((MatchData)this).scala$util$matching$Regex$MatchData$$nameToIndex();
                    this.bitmap$0 = (byte)(this.bitmap$0 | 4);
                }
            }
            return this.scala$util$matching$Regex$MatchData$$nameToIndex;
        }

        @Override
        public Map<String, Object> scala$util$matching$Regex$MatchData$$nameToIndex() {
            if ((byte)(this.bitmap$0 & 4) == 0) {
                return this.scala$util$matching$Regex$MatchData$$nameToIndex$lzycompute();
            }
            return this.scala$util$matching$Regex$MatchData$$nameToIndex;
        }

        @Override
        public CharSequence source() {
            return this.source;
        }

        @Override
        public Matcher matcher() {
            return this.matcher;
        }

        @Override
        public Seq<String> groupNames() {
            return this.groupNames;
        }

        @Override
        public int start() {
            return this.start;
        }

        @Override
        public int end() {
            return this.end;
        }

        @Override
        public int groupCount() {
            return this.matcher().groupCount();
        }

        private int[] starts$lzycompute() {
            synchronized (this) {
                if ((byte)(this.bitmap$0 & 1) == 0) {
                    Object var2_2;
                    int[] nArray;
                    int tabulate_n = this.groupCount() + 1;
                    if (tabulate_n <= 0) {
                        nArray = new int[]{};
                    } else {
                        int[] tabulate_array = new int[tabulate_n];
                        for (int tabulate_i = 0; tabulate_i < tabulate_n; ++tabulate_i) {
                            int n;
                            tabulate_array[tabulate_i] = n = Match.$anonfun$starts$1(this, tabulate_i);
                        }
                        nArray = var2_2;
                    }
                    var2_2 = null;
                    this.starts = nArray;
                    this.bitmap$0 = (byte)(this.bitmap$0 | 1);
                }
            }
            return this.starts;
        }

        private int[] starts() {
            if ((byte)(this.bitmap$0 & 1) == 0) {
                return this.starts$lzycompute();
            }
            return this.starts;
        }

        private int[] ends$lzycompute() {
            synchronized (this) {
                if ((byte)(this.bitmap$0 & 2) == 0) {
                    Object var2_2;
                    int[] nArray;
                    int tabulate_n = this.groupCount() + 1;
                    if (tabulate_n <= 0) {
                        nArray = new int[]{};
                    } else {
                        int[] tabulate_array = new int[tabulate_n];
                        for (int tabulate_i = 0; tabulate_i < tabulate_n; ++tabulate_i) {
                            int n;
                            tabulate_array[tabulate_i] = n = Match.$anonfun$ends$1(this, tabulate_i);
                        }
                        nArray = var2_2;
                    }
                    var2_2 = null;
                    this.ends = nArray;
                    this.bitmap$0 = (byte)(this.bitmap$0 | 2);
                }
            }
            return this.ends;
        }

        private int[] ends() {
            if ((byte)(this.bitmap$0 & 2) == 0) {
                return this.ends$lzycompute();
            }
            return this.ends;
        }

        @Override
        public int start(int i) {
            return this.starts()[i];
        }

        @Override
        public int end(int i) {
            return this.ends()[i];
        }

        public Match force() {
            this.starts();
            this.ends();
            return this;
        }

        public static final /* synthetic */ int $anonfun$starts$1(Match $this, int x$1) {
            return $this.matcher().start(x$1);
        }

        public static final /* synthetic */ int $anonfun$ends$1(Match $this, int x$1) {
            return $this.matcher().end(x$1);
        }

        public Match(CharSequence source2, Matcher matcher, Seq<String> _groupNames) {
            this.source = source2;
            this.matcher = matcher;
            this.groupNames = _groupNames;
            this.start = matcher.start();
            this.end = matcher.end();
        }
    }

    public static interface MatchData {
        public Matcher matcher();

        public CharSequence source();

        public Seq<String> groupNames();

        public int groupCount();

        public int start();

        public int start(int var1);

        public int end();

        public int end(int var1);

        default public String matched() {
            if (this.start() >= 0) {
                return ((Object)this.source().subSequence(this.start(), this.end())).toString();
            }
            return null;
        }

        default public String group(int i) {
            if (this.start(i) >= 0) {
                return ((Object)this.source().subSequence(this.start(i), this.end(i))).toString();
            }
            return null;
        }

        /*
         * WARNING - void declaration
         */
        default public List<String> subgroups() {
            void var3_5;
            $colon$colon<Nothing$> map_h;
            int n = 1;
            int to$extension_end = this.groupCount();
            List list = IterableOnceOps.toList$(new Range.Inclusive(n, to$extension_end, 1));
            if (list == null) {
                throw null;
            }
            List map_this = list;
            if (map_this == Nil$.MODULE$) {
                return Nil$.MODULE$;
            }
            int $anonfun$subgroups$1_i = BoxesRunTime.unboxToInt(map_this.head());
            $colon$colon<Nothing$> map_t = map_h = new $colon$colon<Nothing$>((Nothing$)((Object)this.group($anonfun$subgroups$1_i)), Nil$.MODULE$);
            for (List map_rest = (List)map_this.tail(); map_rest != Nil$.MODULE$; map_rest = (List)map_rest.tail()) {
                int $anonfun$subgroups$1_i2 = BoxesRunTime.unboxToInt(map_rest.head());
                $colon$colon<Nothing$> map_nx = new $colon$colon<Nothing$>((Nothing$)((Object)this.group($anonfun$subgroups$1_i2)), Nil$.MODULE$);
                map_t.next_$eq(map_nx);
                map_t = map_nx;
            }
            Statics.releaseFence();
            return var3_5;
        }

        default public CharSequence before() {
            if (this.start() >= 0) {
                return this.source().subSequence(0, this.start());
            }
            return null;
        }

        default public CharSequence before(int i) {
            if (this.start(i) >= 0) {
                return this.source().subSequence(0, this.start(i));
            }
            return null;
        }

        default public CharSequence after() {
            if (this.end() >= 0) {
                return this.source().subSequence(this.end(), this.source().length());
            }
            return null;
        }

        default public CharSequence after(int i) {
            if (this.end(i) >= 0) {
                return this.source().subSequence(this.end(i), this.source().length());
            }
            return null;
        }

        private Seq<String> groupNamesNowarn() {
            return this.groupNames();
        }

        /*
         * WARNING - void declaration
         */
        default public Map<String, Object> scala$util$matching$Regex$MatchData$$nameToIndex() {
            void $colon$colon_elem;
            void apply_elems;
            Map$ map$ = Predef$.MODULE$.Map();
            Nil$ nil$ = Nil$.MODULE$;
            if (map$ == null) {
                throw null;
            }
            nil$ = null;
            List list = this.groupNames().toList();
            String string = "";
            if (list == null) {
                throw null;
            }
            List $colon$colon_this = list;
            Object var2_3 = null;
            string = null;
            return (Map)map$.from((IterableOnce)apply_elems).$plus$plus((IterableOnce)StrictOptimizedIterableOps.zipWithIndex$(new $colon$colon<void>($colon$colon_elem, $colon$colon_this)));
        }

        default public String group(String id) {
            if (this.groupNames().isEmpty()) {
                return this.matcher().group(id);
            }
            Option option = this.scala$util$matching$Regex$MatchData$$nameToIndex().get(id);
            if (option instanceof Some) {
                int index = BoxesRunTime.unboxToInt(((Some)option).value());
                return this.group(index);
            }
            if (None$.MODULE$.equals(option)) {
                return this.matcher().group(id);
            }
            throw new MatchError(option);
        }

        default public String toString() {
            return this.matched();
        }

        public static void $init$(MatchData $this) {
        }

        public static /* synthetic */ String $anonfun$subgroups$1$adapted(MatchData $this, Object i) {
            return $this.group(BoxesRunTime.unboxToInt(i));
        }
    }

    public static class MatchIterator
    extends AbstractIterator<String>
    implements MatchData {
        private final CharSequence source;
        private final Regex regex;
        private final Seq<String> _groupNames;
        private final Seq<String> groupNames;
        private final Matcher matcher;
        private int nextSeen;
        private Map<String, Object> scala$util$matching$Regex$MatchData$$nameToIndex;
        private volatile boolean bitmap$0;

        @Override
        public String matched() {
            return ((MatchData)this).matched();
        }

        @Override
        public String group(int i) {
            return ((MatchData)this).group(i);
        }

        @Override
        public List<String> subgroups() {
            return ((MatchData)this).subgroups();
        }

        @Override
        public CharSequence before() {
            return ((MatchData)this).before();
        }

        @Override
        public CharSequence before(int i) {
            return ((MatchData)this).before(i);
        }

        @Override
        public CharSequence after() {
            return ((MatchData)this).after();
        }

        @Override
        public CharSequence after(int i) {
            return ((MatchData)this).after(i);
        }

        @Override
        public String group(String id) {
            return ((MatchData)this).group(id);
        }

        private Map<String, Object> scala$util$matching$Regex$MatchData$$nameToIndex$lzycompute() {
            synchronized (this) {
                if (!this.bitmap$0) {
                    this.scala$util$matching$Regex$MatchData$$nameToIndex = ((MatchData)this).scala$util$matching$Regex$MatchData$$nameToIndex();
                    this.bitmap$0 = true;
                }
            }
            return this.scala$util$matching$Regex$MatchData$$nameToIndex;
        }

        @Override
        public Map<String, Object> scala$util$matching$Regex$MatchData$$nameToIndex() {
            if (!this.bitmap$0) {
                return this.scala$util$matching$Regex$MatchData$$nameToIndex$lzycompute();
            }
            return this.scala$util$matching$Regex$MatchData$$nameToIndex;
        }

        @Override
        public CharSequence source() {
            return this.source;
        }

        public Regex regex() {
            return this.regex;
        }

        public Seq<String> _groupNames() {
            return this._groupNames;
        }

        @Override
        public Seq<String> groupNames() {
            return this.groupNames;
        }

        @Override
        public Matcher matcher() {
            return this.matcher;
        }

        @Override
        public boolean hasNext() {
            int n = this.nextSeen;
            switch (n) {
                case 0: {
                    this.nextSeen = this.matcher().find() ? 1 : 3;
                    break;
                }
                case 1: {
                    break;
                }
                case 2: {
                    this.nextSeen = 0;
                    this.hasNext();
                    break;
                }
                case 3: {
                    break;
                }
                default: {
                    throw new MatchError(n);
                }
            }
            return this.nextSeen == 1;
        }

        @Override
        public String next() {
            int n = this.nextSeen;
            switch (n) {
                case 0: {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.next();
                    break;
                }
                case 1: {
                    this.nextSeen = 2;
                    break;
                }
                case 2: {
                    this.nextSeen = 0;
                    this.next();
                    break;
                }
                case 3: {
                    throw new NoSuchElementException();
                }
                default: {
                    throw new MatchError(n);
                }
            }
            return this.matcher().group();
        }

        @Override
        public String toString() {
            return Iterator.toString$(this);
        }

        private void ensure() {
            int n = this.nextSeen;
            switch (n) {
                case 0: {
                    if (!this.hasNext()) {
                        throw new IllegalStateException();
                    }
                    return;
                }
                case 1: {
                    return;
                }
                case 2: {
                    return;
                }
                case 3: {
                    throw new IllegalStateException();
                }
            }
            throw new MatchError(n);
        }

        @Override
        public int start() {
            this.ensure();
            return this.matcher().start();
        }

        @Override
        public int start(int i) {
            this.ensure();
            return this.matcher().start(i);
        }

        @Override
        public int end() {
            this.ensure();
            return this.matcher().end();
        }

        @Override
        public int end(int i) {
            this.ensure();
            return this.matcher().end(i);
        }

        @Override
        public int groupCount() {
            this.ensure();
            return this.matcher().groupCount();
        }

        public Iterator<Match> matchData() {
            return new AbstractIterator<Match>(this){
                private final /* synthetic */ MatchIterator $outer;

                public boolean hasNext() {
                    return this.$outer.hasNext();
                }

                public Match next() {
                    this.$outer.next();
                    return new Match(this.$outer.source(), this.$outer.matcher(), this.$outer._groupNames()).force();
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                }
            };
        }

        public AbstractIterator<Match> replacementData() {
            return new Replacement(this){
                private StringBuffer scala$util$matching$Regex$Replacement$$sb;
                private final /* synthetic */ MatchIterator $outer;

                public String replaced() {
                    return Replacement.replaced$(this);
                }

                public Matcher replace(String rs) {
                    return Replacement.replace$(this, rs);
                }

                public StringBuffer scala$util$matching$Regex$Replacement$$sb() {
                    return this.scala$util$matching$Regex$Replacement$$sb;
                }

                public final void scala$util$matching$Regex$Replacement$_setter_$scala$util$matching$Regex$Replacement$$sb_$eq(StringBuffer x$1) {
                    this.scala$util$matching$Regex$Replacement$$sb = x$1;
                }

                public Matcher matcher() {
                    return this.$outer.matcher();
                }

                public boolean hasNext() {
                    return this.$outer.hasNext();
                }

                public Match next() {
                    this.$outer.next();
                    return new Match(this.$outer.source(), this.matcher(), this.$outer._groupNames()).force();
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    Replacement.$init$(this);
                    Statics.releaseFence();
                }
            };
        }

        public MatchIterator(CharSequence source2, Regex regex2, Seq<String> _groupNames) {
            this.source = source2;
            this.regex = regex2;
            this._groupNames = _groupNames;
            this.groupNames = _groupNames;
            this.matcher = regex2.pattern().matcher(source2);
            this.nextSeen = 0;
        }
    }

    public static interface Replacement {
        public void scala$util$matching$Regex$Replacement$_setter_$scala$util$matching$Regex$Replacement$$sb_$eq(StringBuffer var1);

        public Matcher matcher();

        public StringBuffer scala$util$matching$Regex$Replacement$$sb();

        public static /* synthetic */ String replaced$(Replacement $this) {
            return $this.replaced();
        }

        default public String replaced() {
            StringBuffer newsb = new StringBuffer(this.scala$util$matching$Regex$Replacement$$sb());
            this.matcher().appendTail(newsb);
            return newsb.toString();
        }

        public static /* synthetic */ Matcher replace$(Replacement $this, String rs) {
            return $this.replace(rs);
        }

        default public Matcher replace(String rs) {
            return this.matcher().appendReplacement(this.scala$util$matching$Regex$Replacement$$sb(), rs);
        }

        public static void $init$(Replacement $this) {
            $this.scala$util$matching$Regex$Replacement$_setter_$scala$util$matching$Regex$Replacement$$sb_$eq(new StringBuffer());
        }
    }
}

