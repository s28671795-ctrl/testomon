/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import java.util.NoSuchElementException;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterator;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.MapFactory;
import scala.collection.SeqMap;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.generic.BitOperations;
import scala.collection.generic.BitOperations$Int$;
import scala.collection.immutable.AbstractMap;
import scala.collection.immutable.Map;
import scala.collection.immutable.MapBuilderImpl;
import scala.collection.immutable.Seq;
import scala.collection.immutable.StrictOptimizedMapOps;
import scala.collection.immutable.TreeSeqMap$;
import scala.collection.immutable.TreeSeqMap$OrderBy$Insertion$;
import scala.collection.immutable.TreeSeqMap$OrderBy$Modification$;
import scala.collection.immutable.TreeSeqMap$Ordering$;
import scala.collection.immutable.TreeSeqMap$Ordering$Iterator$;
import scala.collection.immutable.TreeSeqMap$Ordering$Zero$;
import scala.collection.mutable.Growable;
import scala.collection.mutable.StringBuilder;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.Nothing$;
import scala.runtime.ObjectRef;
import scala.runtime.Statics;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005!=baBAT\u0003S\u0013\u0011q\u0017\u0005\u000b\u0005#\u0001!Q1A\u0005\n\tM\u0001BCD\u0017\u0001\t\u0005\t\u0015!\u0003\u0003\u0016!qqq\u0006\u0001\u0005\u0002\u0003\u0015)Q1A\u0005\n\u001dE\u0002bCD\u001b\u0001\t\u0015\t\u0011)A\u0005\u000fgA!b!;\u0001\u0005\u000b\u0007I\u0011\u0002BC\u0011)99\u0004\u0001B\u0001B\u0003%!q\u0011\u0005\u000b\u00077\u0002!Q1A\u0005\u0002\u001de\u0002BCD\u001f\u0001\t\u0005\t\u0015!\u0003\b<!9!1\u0006\u0001\u0005\n\u001d}\u0002\u0002CD&\u0001\u0001&\tf!*\t\u000f\u001d5\u0003\u0001\"\u0011\bP!Iq\u0011\u000b\u0001C\u0002\u0013\u0005#Q\u0011\u0005\t\u000f'\u0002\u0001\u0015!\u0003\u0003\b\"9qQ\u000b\u0001\u0005B\t\u0015\u0005bBD,\u0001\u0011\u0005C1\u0013\u0005\b\u000f3\u0002A\u0011AD.\u0011\u001d9y\u0006\u0001C\u0001\u000fCBqab\u001d\u0001\t\u00039)\bC\u0004\bz\u0001!\tab\u001f\t\u000f\u001d}\u0004\u0001\"\u0001\b\u0002\"9A\u0011\u0005\u0001\u0005\u0002\u001d\u001d\u0005bBDF\u0001\u0011\u0005sQ\u0012\u0005\b\u000f#\u0003A\u0011IDJ\u0011\u001d99\n\u0001C!\u000f3Cqaa1\u0001\t\u0003:i\nC\u0004\u0004V\u0002!\teb(\t\u000f\r\u0005\b\u0001\"\u0011\b\u001e\"91Q\u001d\u0001\u0005B\u001d}\u0005b\u0002C\b\u0001\u0011\u0005s1\u0015\u0005\b\t3\u0001A\u0011IDR\u0011\u001d9)\u000b\u0001C!\u000fOCqab,\u0001\t\u0003:\t\fC\u0004\bL\u0002!\te\"4\t\u000f\u001d\r\b\u0001\"\u0011\bf\"9qq \u0001\u0005B!\u0005\u0001\u0002\u0003Cr\u0001\u0001&I\u0001c\u0005\t\u0011!\u0015\u0002\u0001)C\u0005\u0011O9\u0001B!\u0007\u0002*\"\u0005!1\u0004\u0004\t\u0003O\u000bI\u000b#\u0001\u0003\u001e!9!1F\u0014\u0005\u0002\t5b!\u0003B\u0018OA\u0005\u0019\u0013\u0005B\u0019\u000f\u001d\u0011Im\nE\u0001\u0005w1qAa\f(\u0011\u0003\u00119\u0004C\u0004\u0003,-\"\tA!\u000f\b\u000f\t}2\u0006#!\u0003B\u00199!QG\u0016\t\u0002\nu\u0006b\u0002B\u0016]\u0011\u0005!q\u0018\u0005\n\u0005_r\u0013\u0011!C!\u0005cB\u0011Ba!/\u0003\u0003%\tA!\"\t\u0013\t5e&!A\u0005\u0002\t\u0005\u0007\"\u0003BK]\u0005\u0005I\u0011\tBL\u0011%\u0011yJLA\u0001\n\u0003\u0011)\rC\u0005\u0003,:\n\t\u0011\"\u0011\u0003.\"I!q\u0016\u0018\u0002\u0002\u0013\u0005#\u0011\u0017\u0005\n\u0005gs\u0013\u0011!C\u0005\u0005k;qA!\u0012,\u0011\u0003\u00139EB\u0004\u0003J-B\tIa\u0013\t\u000f\t-\u0012\b\"\u0001\u0003n!I!qN\u001d\u0002\u0002\u0013\u0005#\u0011\u000f\u0005\n\u0005\u0007K\u0014\u0011!C\u0001\u0005\u000bC\u0011B!$:\u0003\u0003%\tAa$\t\u0013\tU\u0015(!A\u0005B\t]\u0005\"\u0003BPs\u0005\u0005I\u0011\u0001BQ\u0011%\u0011Y+OA\u0001\n\u0003\u0012i\u000bC\u0005\u00030f\n\t\u0011\"\u0011\u00032\"I!1W\u001d\u0002\u0002\u0013%!Q\u0017\u0005\n\u0005\u0017<#\u0019!C\u0005\u0005\u001bD\u0001B!5(A\u0003%!q\u001a\u0005\n\u0005'<#\u0019!C\u0005\u0005\u001bD\u0001B!6(A\u0003%!q\u001a\u0005\n\u0005/<#\u0019!C\u0001\u0005\u001bD\u0001B!7(A\u0003%!q\u001a\u0005\b\u00057<C\u0011\u0001Bo\u0011\u001d\u0011Yn\nC\u0001\u0005WDqA!@(\t\u0003\u0011y\u0010C\u0004\u0004\u001a\u001d\"Iaa\u0007\t\u000f\r%r\u0005\"\u0001\u0004,!91\u0011F\u0014\u0005\u0002\r\u001dcABB\u001dO\t\u0019i\u0006\u0003\u0006\u0004\\=\u0013\t\u0011)A\u0005\u0005\u001bBqAa\u000bP\t\u0003\u0019y\u0007\u0003\u0005\u0004v=\u0003\u000b\u0011BB<\u0011!\u0019yh\u0014Q!\n\r\u0005\u0005\u0002CB\u0010\u001f\u0002\u0006KAa\"\t\u0017\u0019%x\n1A\u0001B\u0003&1Q\u000e\u0005\b\rW|E\u0011\tDw\u0011\u001d1Yo\u0014C\u0001\rkDqA\"@P\t\u00032y\u0010C\u0004\b\u0002=#\teb\u0001\u0006\r\u001d\u0015q\u0005BD\u0004\u0011%9Ib\nb\u0001\n\u00139Y\u0002\u0003\u0005\b,\u001d\u0002\u000b\u0011BD\u000f\u000f%!Ic\nE\u0001\u0003S#YCB\u0005\u0004\u0004\u001eB\t!!+\u0005.!9!1\u00060\u0005\u0002\u0011=\u0002\"\u0003C\u0019=\u0012\u0005\u0011\u0011\u0016C\u001a\u0011\u001d\u0011YN\u0018C\u0001\twAq\u0001\"\u0012_\t\u0003!9E\u0002\u0004\u0003\u001ez\u0013AQ\f\u0005\u000b\u0007\u001f\u0019'\u0011!Q\u0001\n\u0011\u0005\u0004b\u0002B\u0016G\u0012\u0005Aq\r\u0005\t\t_\u001a\u0007\u0015)\u0003\u0003\b\"AA\u0011O2!\u0002\u0013!\u0019\b\u0003\u0005\u0005z\r\u0004K\u0011\u0002C>\u0011!!ih\u0019Q\u0005\n\u0011}\u0004b\u0002CIG\u0012\u0005A1\u0013\u0005\b\t+\u001bG\u0011\u0001CL\u000f\u001d!YJ\u0018E\u0001\t;3qA!(_\u0011\u0003!y\nC\u0004\u0003,5$\t\u0001\")\t\u0013\t]WN1A\u0005\u0002\u0011\r\u0006\u0002\u0003Bm[\u0002\u0006I\u0001\"*\t\u000f\tmW\u000e\"\u0001\u0005(\u001e9A\u0011\u00170\t\u0002\u0012Mfa\u0002C[=\"\u0005Eq\u0017\u0005\b\u0005W\u0019H\u0011\u0001C^\u0011\u001d!il\u001dC!\t\u007fCqaa)t\t#!)\rC\u0005\u0003pM\f\t\u0011\"\u0011\u0003r!I!1Q:\u0002\u0002\u0013\u0005!Q\u0011\u0005\n\u0005\u001b\u001b\u0018\u0011!C\u0001\t\u001bD\u0011B!&t\u0003\u0003%\tEa&\t\u0013\t}5/!A\u0005\u0002\u0011E\u0007\"\u0003BVg\u0006\u0005I\u0011\tBW\u0011%\u0011\u0019l]A\u0001\n\u0013\u0011)L\u0002\u0004\u0005Vz\u0013Eq\u001b\u0005\u000b\u0007?q(Q3A\u0005\u0002\r-\bB\u0003Cq}\nE\t\u0015!\u0003\u0004n\"QA1\u001d@\u0003\u0016\u0004%\t\u0001\":\t\u0015\u0011\u001dhP!E!\u0002\u0013!i\u000eC\u0004\u0003,y$\t\u0001\";\t\u000f\u0011Eh\u0010\"\u0001\u0005t\"911\u0015@\u0005\u0012\u0015\r\u0001\"CC\u0006}\u0006\u0005I\u0011AC\u0007\u0011%)YB`I\u0001\n\u0003)i\u0002C\u0005\u00064y\f\n\u0011\"\u0001\u00066!I!q\u000e@\u0002\u0002\u0013\u0005#\u0011\u000f\u0005\n\u0005\u0007s\u0018\u0011!C\u0001\u0005\u000bC\u0011B!$\u007f\u0003\u0003%\t!\"\u0010\t\u0013\tUe0!A\u0005B\t]\u0005\"\u0003BP}\u0006\u0005I\u0011AC!\u0011%))E`A\u0001\n\u0003*9\u0005C\u0005\u0003,z\f\t\u0011\"\u0011\u0003.\"IAQ\u0018@\u0002\u0002\u0013\u0005S1J\u0004\n\u000b\u001fr\u0016\u0011!E\u0001\u000b#2\u0011\u0002\"6_\u0003\u0003E\t!b\u0015\t\u0011\t-\u0012Q\u0005C\u0001\u000b?B!Ba,\u0002&\u0005\u0005IQ\tBY\u0011)!)%!\n\u0002\u0002\u0013\u0005U\u0011\r\u0005\u000b\u000b_\n)#!A\u0005\u0002\u0016E\u0004B\u0003BZ\u0003K\t\t\u0011\"\u0003\u00036\u001a1Q1\u00110C\u000b\u000bC1b!0\u00022\tU\r\u0011\"\u0001\u0004l\"YQqRA\u0019\u0005#\u0005\u000b\u0011BBw\u0011-)\t*!\r\u0003\u0016\u0004%\taa;\t\u0017\u0015M\u0015\u0011\u0007B\tB\u0003%1Q\u001e\u0005\f\u000b+\u000b\tD!f\u0001\n\u0003)9\nC\u0006\u0006\u001a\u0006E\"\u0011#Q\u0001\n\u0015%\u0005bCCN\u0003c\u0011\t\u001a!C\u0001\u000b;C1\"\")\u00022\t\u0005\r\u0011\"\u0001\u0006$\"YQqUA\u0019\u0005#\u0005\u000b\u0015BCP\u0011!\u0011Y#!\r\u0005\u0002\u0015%\u0006\u0002CC[\u0003c!\t!b.\t\u0011\r\r\u0016\u0011\u0007C\t\u000b\u000bD!\"b\u0003\u00022\u0005\u0005I\u0011ACg\u0011))Y\"!\r\u0012\u0002\u0013\u0005Q1\u001d\u0005\u000b\u000bg\t\t$%A\u0005\u0002\u0015\u001d\bBCCv\u0003c\t\n\u0011\"\u0001\u0006n\"QQ\u0011_A\u0019#\u0003%\t!b=\t\u0015\t=\u0014\u0011GA\u0001\n\u0003\u0012\t\b\u0003\u0006\u0003\u0004\u0006E\u0012\u0011!C\u0001\u0005\u000bC!B!$\u00022\u0005\u0005I\u0011AC|\u0011)\u0011)*!\r\u0002\u0002\u0013\u0005#q\u0013\u0005\u000b\u0005?\u000b\t$!A\u0005\u0002\u0015m\bBCC#\u0003c\t\t\u0011\"\u0011\u0006\u0000\"Q!1VA\u0019\u0003\u0003%\tE!,\t\u0015\u0011u\u0016\u0011GA\u0001\n\u00032\u0019aB\u0005\u0007\by\u000b\t\u0011#\u0001\u0007\n\u0019IQ1\u00110\u0002\u0002#\u0005a1\u0002\u0005\t\u0005W\t9\u0007\"\u0001\u0007\u000e!Q!qVA4\u0003\u0003%)E!-\t\u0015\u0011\u0015\u0013qMA\u0001\n\u00033y\u0001\u0003\u0006\u0006p\u0005\u001d\u0014\u0011!CA\rKA!Ba-\u0002h\u0005\u0005I\u0011\u0002B[\u0011\u001d1iD\u0018C\u0005\r\u007fAqAb\u0012_\t\u00131I\u0005C\u0004\u00066z#IAb\u0019\u0007\u000f\r\ru%!\t\u0004\u0006\"A!1FA=\t\u0003\u0019I\t\u0003\u0005\u00030\u0006eDQIBJ\u0011!\u0019\u0019+!\u001f\u0005\u0006\r\u0015\u0006\u0002CBR\u0003s2\tba*\t\u0011\r\r\u0017\u0011\u0010C\u0003\u0007\u000bD\u0001b!6\u0002z\u0011\u00151q\u001b\u0005\t\u0007C\fI\b\"\u0002\u0004F\"A1Q]A=\t\u000b\u00199\u000e\u0003\u0005\u0004j\u0006eDQABv\u0011!!y!!\u001f\u0005\u0006\u0011E\u0001\u0002\u0003C\n\u0003s\")\u0001\"\u0006\t\u0011\u0011e\u0011\u0011\u0010C\u0003\t#A\u0001\u0002b\u0007\u0002z\u0011\u0015AQ\u0004\u0005\t\tC\tI\b\"\u0002\u0005$!AaQOA=\t\u000b19\b\u0003\u0005\u0007\b\u0006eDQ\u0001DE\u0011)19*!\u001f\u0005\u0006\u00055f\u0011\u0014\u0005\u000b\rS\u000bI\b\"\u0002\u0002.\u001a-\u0006\u0002\u0003D`\u0003s\")A\"1\t\u0011\u0019\u0015\u0017\u0011\u0010C\u0003\r\u000fD\u0001Bb4\u0002z\u0011\u0015a\u0011\u001b\u0005\n\u0005g;\u0013\u0011!C\u0005\u0005k\u0013!\u0002\u0016:fKN+\u0017/T1q\u0015\u0011\tY+!,\u0002\u0013%lW.\u001e;bE2,'\u0002BAX\u0003c\u000b!bY8mY\u0016\u001cG/[8o\u0015\t\t\u0019,A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\r\u0005e\u0016qYAo'5\u0001\u00111XAq\u0003O\f\tP!\u0002\u0003\fAA\u0011QXA`\u0003\u0007\fY.\u0004\u0002\u0002*&!\u0011\u0011YAU\u0005-\t%m\u001d;sC\u000e$X*\u00199\u0011\t\u0005\u0015\u0017q\u0019\u0007\u0001\t\u001d\tI\r\u0001b\u0001\u0003\u0017\u0014\u0011aS\t\u0005\u0003\u001b\f)\u000e\u0005\u0003\u0002P\u0006EWBAAY\u0013\u0011\t\u0019.!-\u0003\u000f9{G\u000f[5oOB!\u0011qZAl\u0013\u0011\tI.!-\u0003\u0007\u0005s\u0017\u0010\u0005\u0003\u0002F\u0006uG\u0001CAp\u0001\u0011\u0015\r!a3\u0003\u0003Y\u0003\u0002\"!0\u0002d\u0006\r\u00171\\\u0005\u0005\u0003K\fIK\u0001\u0004TKFl\u0015\r\u001d\t\r\u0003{\u000bI/a1\u0002\\\u00065\u0018q^\u0005\u0005\u0003W\fIK\u0001\u0004NCB|\u0005o\u001d\t\u0004\u0003{\u0003\u0001cBA_\u0001\u0005\r\u00171\u001c\t\u000b\u0003g\f)0!?\u0002\u0000\u0006=XBAAW\u0013\u0011\t90!,\u00035M#(/[2u\u001fB$\u0018.\\5{K\u0012LE/\u001a:bE2,w\n]:\u0011\u0011\u0005=\u00171`Ab\u00037LA!!@\u00022\n1A+\u001e9mKJ\u0002B!!0\u0003\u0002%!!1AAU\u0005!IE/\u001a:bE2,\u0007\u0003DA_\u0005\u000f\t\u0019-a7\u0002n\u0006=\u0018\u0002\u0002B\u0005\u0003S\u0013Qc\u0015;sS\u000e$x\n\u001d;j[&TX\rZ'ba>\u00038\u000f\u0005\u0007\u0002t\n5\u00111YAn\u0003[\fy0\u0003\u0003\u0003\u0010\u00055&AE'ba\u001a\u000b7\r^8ss\u0012+g-Y;miN\f\u0001b\u001c:eKJLgnZ\u000b\u0003\u0005+\u0001bAa\u0006\u0002z\u0005\rgbAA_M\u0005QAK]3f'\u0016\fX*\u00199\u0011\u0007\u0005uveE\u0003(\u0005?\u0011)\u0003\u0005\u0003\u0002P\n\u0005\u0012\u0002\u0002B\u0012\u0003c\u0013a!\u00118z%\u00164\u0007CBAz\u0005O\ti/\u0003\u0003\u0003*\u00055&AC'ba\u001a\u000b7\r^8ss\u00061A(\u001b8jiz\"\"Aa\u0007\u0003\u000f=\u0013H-\u001a:CsN\u0019\u0011Fa\b*\u0007%r\u0013HA\u0005J]N,'\u000f^5p]N\u00191Fa\b\u0015\u0005\tm\u0002c\u0001B\u001fW5\tq%A\u0005J]N,'\u000f^5p]B\u0019!1\t\u0018\u000e\u0003-\nA\"T8eS\u001aL7-\u0019;j_:\u00042Aa\u0011:\u00051iu\u000eZ5gS\u000e\fG/[8o'%I$q\u0004B'\u0005\u001f\u0012)\u0006E\u0002\u0003>%\u0002B!a4\u0003R%!!1KAY\u0005\u001d\u0001&o\u001c3vGR\u0004BAa\u0016\u0003h9!!\u0011\fB2\u001d\u0011\u0011YF!\u0019\u000e\u0005\tu#\u0002\u0002B0\u0003k\u000ba\u0001\u0010:p_Rt\u0014BAAZ\u0013\u0011\u0011)'!-\u0002\u000fA\f7m[1hK&!!\u0011\u000eB6\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\u0011\u0011)'!-\u0015\u0005\t\u001d\u0013!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0003tA!!Q\u000fB@\u001b\t\u00119H\u0003\u0003\u0003z\tm\u0014\u0001\u00027b]\u001eT!A! \u0002\t)\fg/Y\u0005\u0005\u0005\u0003\u00139H\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0005\u000f\u0003B!a4\u0003\n&!!1RAY\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\u0011\t)N!%\t\u0013\tMU(!AA\u0002\t\u001d\u0015a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0003\u001aB1\u00111\u001fBN\u0003+LAA!(\u0002.\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\u0011\u0019K!+\u0011\t\u0005='QU\u0005\u0005\u0005O\u000b\tLA\u0004C_>dW-\u00198\t\u0013\tMu(!AA\u0002\u0005U\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\t\u001d\u0015\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\tM\u0014\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001B\\!\u0011\u0011)H!/\n\t\tm&q\u000f\u0002\u0007\u001f\nTWm\u0019;\u0014\u00139\u0012yB!\u0014\u0003P\tUCC\u0001B!)\u0011\t)Na1\t\u0013\tM%'!AA\u0002\t\u001dE\u0003\u0002BR\u0005\u000fD\u0011Ba%5\u0003\u0003\u0005\r!!6\u0002\u000f=\u0013H-\u001a:Cs\u0006\u0001R)\u001c9us\nK\u0018J\\:feRLwN\\\u000b\u0003\u0005\u001f\u0004r!!0\u0001\u0003\u001b\fi-A\tF[B$\u0018PQ=J]N,'\u000f^5p]\u0002\n1#R7qif\u0014\u00150T8eS\u001aL7-\u0019;j_:\fA#R7qif\u0014\u00150T8eS\u001aL7-\u0019;j_:\u0004\u0013!B#naRL\u0018AB#naRL\b%A\u0003f[B$\u00180\u0006\u0004\u0003`\n\u0015(\u0011^\u000b\u0003\u0005C\u0004r!!0\u0001\u0005G\u00149\u000f\u0005\u0003\u0002F\n\u0015HaBAe\u0013\n\u0007\u00111\u001a\t\u0005\u0003\u000b\u0014I\u000fB\u0004\u0002`&\u0013\r!a3\u0016\r\t5(1\u001fB|)\u0011\u0011yO!?\u0011\u000f\u0005u\u0006A!=\u0003vB!\u0011Q\u0019Bz\t\u001d\tIM\u0013b\u0001\u0003\u0017\u0004B!!2\u0003x\u00129\u0011q\u001c&C\u0002\u0005-\u0007b\u0002B~\u0015\u0002\u0007!QJ\u0001\b_J$WM\u001d\"z\u0003\u00111'o\\7\u0016\r\r\u00051qAB\u0006)\u0011\u0019\u0019a!\u0004\u0011\u000f\u0005u\u0006a!\u0002\u0004\nA!\u0011QYB\u0004\t\u001d\tIm\u0013b\u0001\u0003\u0017\u0004B!!2\u0004\f\u00119\u0011q\\&C\u0002\u0005-\u0007bBB\b\u0017\u0002\u00071\u0011C\u0001\u0003SR\u0004b!a=\u0004\u0014\r]\u0011\u0002BB\u000b\u0003[\u0013A\"\u0013;fe\u0006\u0014G.Z(oG\u0016\u0004\u0002\"a4\u0002|\u000e\u00151\u0011B\u0001\nS:\u001c'/Z7f]R$BAa\"\u0004\u001e!91q\u0004'A\u0002\t\u001d\u0015aA8sI\"\u001aAja\t\u0011\t\u0005=7QE\u0005\u0005\u0007O\t\tL\u0001\u0004j]2Lg.Z\u0001\u000b]\u0016<()^5mI\u0016\u0014XCBB\u0017\u0007\u007f\u0019\u0019%\u0006\u0002\u00040AA1\u0011GB\u001c\u0007w\u0019)%\u0004\u0002\u00044)!1QGAW\u0003\u001diW\u000f^1cY\u0016LAa!\u000f\u00044\t9!)^5mI\u0016\u0014\b\u0003CAh\u0003w\u001cid!\u0011\u0011\t\u0005\u00157q\b\u0003\b\u0003\u0013l%\u0019AAf!\u0011\t)ma\u0011\u0005\u000f\u0005}WJ1\u0001\u0002LB9\u0011Q\u0018\u0001\u0004>\r\u0005SCBB%\u0007#\u001a)\u0006\u0006\u0003\u0004L\re\u0003\u0003CB\u0019\u0007o\u0019iea\u0016\u0011\u0011\u0005=\u00171`B(\u0007'\u0002B!!2\u0004R\u00119\u0011\u0011\u001a(C\u0002\u0005-\u0007\u0003BAc\u0007+\"q!a8O\u0005\u0004\tY\rE\u0004\u0002>\u0002\u0019yea\u0015\t\u000f\rmc\n1\u0001\u0003N\u0005IqN\u001d3fe\u0016$')_\u000b\u0007\u0007?\u001a9ga\u001b\u0014\u000b=\u0013yb!\u0019\u0011\u0011\rE2qGB2\u0007[\u0002\u0002\"a4\u0002|\u000e\u00154\u0011\u000e\t\u0005\u0003\u000b\u001c9\u0007B\u0004\u0002J>\u0013\r!a3\u0011\t\u0005\u001571\u000e\u0003\b\u0003?|%\u0019AAf!\u001d\ti\fAB3\u0007S\"Ba!\u001d\u0004tA9!QH(\u0004f\r%\u0004bBB.#\u0002\u0007!QJ\u0001\u0004E\u0012\u0014\b\u0003CA_\u0007s\u001a)g! \n\t\rm\u0014\u0011\u0016\u0002\u000f\u001b\u0006\u0004()^5mI\u0016\u0014\u0018*\u001c9m!!\ty-a?\u0003\b\u000e%\u0014aA8oOB1!QHA=\u0007K\u0012\u0001b\u0014:eKJLgnZ\u000b\u0005\u0007\u000f\u001byi\u0005\u0003\u0002z\t}ACABF!\u0019\u0011i$!\u001f\u0004\u000eB!\u0011QYBH\t%\u0019\t*!\u001f\u0005\u0006\u0004\tYMA\u0001U)\t\u0019)\n\u0005\u0003\u0004\u0018\u000e}e\u0002BBM\u00077\u0003BAa\u0017\u00022&!1QTAY\u0003\u0019\u0001&/\u001a3fM&!!\u0011QBQ\u0015\u0011\u0019i*!-\u0002\r\u0019|'/\\1u+\t\u0019)\n\u0006\u0005\u0004*\u000e=61XB`!\u0011\tyma+\n\t\r5\u0016\u0011\u0017\u0002\u0005+:LG\u000f\u0003\u0005\u00042\u0006\u0005\u0005\u0019ABZ\u0003\t\u0019(\r\u0005\u0003\u00046\u000e]f\u0002BAh\u0005GJAa!/\u0003l\ti1\u000b\u001e:j]\u001e\u0014U/\u001b7eKJD\u0001b!0\u0002\u0002\u0002\u00071QS\u0001\u0007aJ,g-\u001b=\t\u0011\r\u0005\u0017\u0011\u0011a\u0001\u0007+\u000b\u0011b];c!J,g-\u001b=\u0002\t!,\u0017\rZ\u000b\u0003\u0007\u001bCC!a!\u0004JB!11ZBi\u001b\t\u0019iM\u0003\u0003\u0004P\u0006E\u0016AC1o]>$\u0018\r^5p]&!11[Bg\u0005\u001d!\u0018-\u001b7sK\u000e\f!\u0002[3bI>\u0003H/[8o+\t\u0019I\u000e\u0005\u0004\u0002P\u000em7QR\u0005\u0005\u0007;\f\tL\u0001\u0004PaRLwN\u001c\u0015\u0005\u0003\u000b\u001bI-\u0001\u0003mCN$\b\u0006BAD\u0007\u0013\f!\u0002\\1ti>\u0003H/[8oQ\u0011\tIi!3\u0002\u000f=\u0014H-\u001b8bYV\u00111Q\u001e\t\u0005\u0007_$IA\u0004\u0003\u0004r\u0012\ra\u0002BBz\u0007{tAa!>\u0004z:!!\u0011LB|\u0013\u0011\ty+!-\n\t\rm\u0018QV\u0001\bO\u0016tWM]5d\u0013\u0011\u0019y\u0010\"\u0001\u0002\u001b\tKGo\u00149fe\u0006$\u0018n\u001c8t\u0015\u0011\u0019Y0!,\n\t\u0011\u0015AqA\u0001\u0004\u0013:$(\u0002BB\u0000\t\u0003IAAa#\u0005\f%!!1\u0012C\u0004Q\u0011\tYi!3\u0002\tQ\f\u0017\u000e\\\u000b\u0003\u0007\u0017\u000b\u0001\u0002[3bIR\u000b\u0017\u000e\\\u000b\u0003\t/\u0001\u0002\"a4\u0002|\u000e551R\u0001\u0005S:LG/\u0001\u0005j]&$H*Y:u+\t!y\u0002\u0005\u0005\u0002P\u0006m81RBG\u0003!IG/\u001a:bi>\u0014XC\u0001C\u0013!\u0015!9cYBG\u001d\r\u0011i$X\u0001\t\u001fJ$WM]5oOB\u0019!Q\b0\u0014\u0007y\u0013y\u0002\u0006\u0002\u0005,\u0005qAo\u001c\"j]\u0006\u0014\u0018p\u0015;sS:<G\u0003BBK\tkAq\u0001b\u000ea\u0001\u0004\u0019i/A\u0001jQ\r\u000171E\u000b\u0005\t{!\u0019%\u0006\u0002\u0005@A1!QHA=\t\u0003\u0002B!!2\u0005D\u001191\u0011S1C\u0002\u0005-\u0017!B1qa2LX\u0003\u0002C%\t\u001f\"B\u0001b\u0013\u0005RA1!QHA=\t\u001b\u0002B!!2\u0005P\u001191\u0011\u00132C\u0002\u0005-\u0007b\u0002C*E\u0002\u0007AQK\u0001\u0006K2,Wn\u001d\t\u0007\u0003\u001f$9\u0006b\u0017\n\t\u0011e\u0013\u0011\u0017\u0002\u000byI,\u0007/Z1uK\u0012t\u0004\u0003CAh\u0003w\u001ci\u000f\"\u0014\u0016\t\u0011}CQM\n\u0004G\n}\u0001C\u0002B\u001f\u0003s\"\u0019\u0007\u0005\u0003\u0002F\u0012\u0015D\u0001CApG\u0012\u0015\r!a3\u0015\t\u0011%DQ\u000e\t\u0006\tW\u001aG1M\u0007\u0002=\"91qB3A\u0002\u0011\u0005\u0014!B5oI\u0016D\u0018A\u00022vM\u001a,'\u000f\u0005\u0004\u0002P\u0012U$qD\u0005\u0005\to\n\tLA\u0003BeJ\f\u00170A\u0002q_B,\"\u0001\"\u0019\u0002\tA,8\u000f[\u000b\u0005\t\u0003#Y\t\u0006\u0003\u0004*\u0012\r\u0005b\u0002CCS\u0002\u0007AqQ\u0001\u0002qB1!QHA=\t\u0013\u0003B!!2\u0005\f\u00129AQR5C\u0002\u0011=%A\u0001,3#\u0011!\u0019'!6\u0002\u000f!\f7OT3yiV\u0011!1U\u0001\u0005]\u0016DH\u000f\u0006\u0002\u0005d!\u001a1n!3\u0002\u0011%#XM]1u_J\u00042\u0001b\u001bn'\ri'q\u0004\u000b\u0003\t;+\"\u0001\"*\u0011\u000b\u0011-4-!4\u0016\t\u0011%FqV\u000b\u0003\tW\u0003R\u0001b\u001bd\t[\u0003B!!2\u00050\u00129\u0011q\\9C\u0002\u0005-\u0017\u0001\u0002.fe>\u00042\u0001b\u001bt\u0005\u0011QVM]8\u0014\u000fM$ILa\u0014\u0003VA1!QHA=\u0003\u001b$\"\u0001b-\u0002\r\u0015\fX/\u00197t)\u0011\u0011\u0019\u000b\"1\t\u000f\u0011\rW\u000f1\u0001\u0002V\u0006!A\u000f[1u)!\u0019I\u000bb2\u0005J\u0012-\u0007bBBYm\u0002\u000711\u0017\u0005\b\u0007{3\b\u0019ABK\u0011\u001d\u0019\tM\u001ea\u0001\u0007+#B!!6\u0005P\"I!1S=\u0002\u0002\u0003\u0007!q\u0011\u000b\u0005\u0005G#\u0019\u000eC\u0005\u0003\u0014n\f\t\u00111\u0001\u0002V\n\u0019A+\u001b9\u0016\t\u0011eGq\\\n\b}\u0012m'q\nB+!\u0019\u0011i$!\u001f\u0005^B!\u0011Q\u0019Cp\t!\u0019\tJ CC\u0002\u0005-\u0017\u0001B8sI\u0002\nQA^1mk\u0016,\"\u0001\"8\u0002\rY\fG.^3!)\u0019!Y\u000f\"<\u0005pB)A1\u000e@\u0005^\"A1qDA\u0004\u0001\u0004\u0019i\u000f\u0003\u0005\u0005d\u0006\u001d\u0001\u0019\u0001Co\u0003%9\u0018\u000e\u001e5WC2,X-\u0006\u0003\u0005v\u0012mH\u0003\u0002C|\t\u007f\u0004R\u0001b\u001b\u007f\ts\u0004B!!2\u0005|\u0012AAQ`A\u0005\u0005\u0004\tYMA\u0001T\u0011!)\t!!\u0003A\u0002\u0011e\u0018!A:\u0015\u0011\r%VQAC\u0004\u000b\u0013A\u0001b!-\u0002\f\u0001\u000711\u0017\u0005\t\u0007{\u000bY\u00011\u0001\u0004\u0016\"A1\u0011YA\u0006\u0001\u0004\u0019)*\u0001\u0003d_BLX\u0003BC\b\u000b+!b!\"\u0005\u0006\u0018\u0015e\u0001#\u0002C6}\u0016M\u0001\u0003BAc\u000b+!\u0001b!%\u0002\u000e\t\u0007\u00111\u001a\u0005\u000b\u0007?\ti\u0001%AA\u0002\r5\bB\u0003Cr\u0003\u001b\u0001\n\u00111\u0001\u0006\u0014\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT\u0003BC\u0010\u000bc)\"!\"\t+\t\r5X1E\u0016\u0003\u000bK\u0001B!b\n\u0006.5\u0011Q\u0011\u0006\u0006\u0005\u000bW\u0019i-A\u0005v]\u000eDWmY6fI&!QqFC\u0015\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\t\u0007#\u000byA1\u0001\u0002L\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012T\u0003BC\u001c\u000bw)\"!\"\u000f+\t\u0011uW1\u0005\u0003\t\u0007#\u000b\tB1\u0001\u0002LR!\u0011Q[C \u0011)\u0011\u0019*a\u0006\u0002\u0002\u0003\u0007!q\u0011\u000b\u0005\u0005G+\u0019\u0005\u0003\u0006\u0003\u0014\u0006m\u0011\u0011!a\u0001\u0003+\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!!1OC%\u0011)\u0011\u0019*!\b\u0002\u0002\u0003\u0007!q\u0011\u000b\u0005\u0005G+i\u0005\u0003\u0006\u0003\u0014\u0006\u0005\u0012\u0011!a\u0001\u0003+\f1\u0001V5q!\u0011!Y'!\n\u0014\r\u0005\u0015\"qDC+!\u0011)9&\"\u0018\u000e\u0005\u0015e#\u0002BC.\u0005w\n!![8\n\t\t%T\u0011\f\u000b\u0003\u000b#*B!b\u0019\u0006jQ1QQMC6\u000b[\u0002R\u0001b\u001b\u007f\u000bO\u0002B!!2\u0006j\u0011A1\u0011SA\u0016\u0005\u0004\tY\r\u0003\u0005\u0004 \u0005-\u0002\u0019ABw\u0011!!\u0019/a\u000bA\u0002\u0015\u001d\u0014aB;oCB\u0004H._\u000b\u0005\u000bg*Y\b\u0006\u0003\u0006v\u0015u\u0004CBAh\u00077,9\b\u0005\u0005\u0002P\u0006m8Q^C=!\u0011\t)-b\u001f\u0005\u0011\rE\u0015Q\u0006b\u0001\u0003\u0017D!\"b \u0002.\u0005\u0005\t\u0019ACA\u0003\rAH\u0005\r\t\u0006\tWrX\u0011\u0010\u0002\u0004\u0005&tW\u0003BCD\u000b\u001b\u001b\u0002\"!\r\u0006\n\n=#Q\u000b\t\u0007\u0005{\tI(b#\u0011\t\u0005\u0015WQ\u0012\u0003\n\u0007#\u000b\t\u0004\"b\u0001\u0003\u0017\fq\u0001\u001d:fM&D\b%\u0001\u0003nCN\\\u0017!B7bg.\u0004\u0013\u0001\u00027fMR,\"!\"#\u0002\u000b1,g\r\u001e\u0011\u0002\u000bILw\r\u001b;\u0016\u0005\u0015}%\u0006BCE\u000bG\t\u0011B]5hQR|F%Z9\u0015\t\r%VQ\u0015\u0005\u000b\u0005'\u000b\t%!AA\u0002\u0015}\u0015A\u0002:jO\"$\b\u0005\u0006\u0006\u0006,\u00165VqVCY\u000bg\u0003b\u0001b\u001b\u00022\u0015-\u0005\u0002CB_\u0003\u000b\u0002\ra!<\t\u0011\u0015E\u0015Q\ta\u0001\u0007[D\u0001\"\"&\u0002F\u0001\u0007Q\u0011\u0012\u0005\t\u000b7\u000b)\u00051\u0001\u0006 \u0006\u0019!-\u001b8\u0016\t\u0015eVq\u0018\u000b\u0007\u000bw+\t-b1\u0011\r\tu\u0012\u0011PC_!\u0011\t)-b0\u0005\u0011\u0011u\u0018q\tb\u0001\u0003\u0017D\u0001\"\"&\u0002H\u0001\u0007Q1\u0018\u0005\t\u000b7\u000b9\u00051\u0001\u0006<RA1\u0011VCd\u000b\u0013,Y\r\u0003\u0005\u00042\u0006%\u0003\u0019ABZ\u0011!\u0019i,!\u0013A\u0002\rU\u0005\u0002CBa\u0003\u0013\u0002\ra!&\u0016\t\u0015=WQ\u001b\u000b\u000b\u000b#,9.\"7\u0006\\\u0016}\u0007C\u0002C6\u0003c)\u0019\u000e\u0005\u0003\u0002F\u0016UG\u0001CBI\u0003\u0017\u0012\r!a3\t\u0015\ru\u00161\nI\u0001\u0002\u0004\u0019i\u000f\u0003\u0006\u0006\u0012\u0006-\u0003\u0013!a\u0001\u0007[D!\"\"&\u0002LA\u0005\t\u0019ACo!\u0019\u0011i$!\u001f\u0006T\"QQ1TA&!\u0003\u0005\r!\"9+\t\u0015uW1E\u000b\u0005\u000b?))\u000f\u0002\u0005\u0004\u0012\u00065#\u0019AAf+\u0011)y\"\";\u0005\u0011\rE\u0015q\nb\u0001\u0003\u0017\fabY8qs\u0012\"WMZ1vYR$3'\u0006\u0003\u0006\u001e\u0016=H\u0001CBI\u0003#\u0012\r!a3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iU!QQTC{\t!\u0019\t*a\u0015C\u0002\u0005-G\u0003BAk\u000bsD!Ba%\u0002Z\u0005\u0005\t\u0019\u0001BD)\u0011\u0011\u0019+\"@\t\u0015\tM\u0015QLA\u0001\u0002\u0004\t)\u000e\u0006\u0003\u0003t\u0019\u0005\u0001B\u0003BJ\u0003?\n\t\u00111\u0001\u0003\bR!!1\u0015D\u0003\u0011)\u0011\u0019*a\u0019\u0002\u0002\u0003\u0007\u0011Q[\u0001\u0004\u0005&t\u0007\u0003\u0002C6\u0003O\u001ab!a\u001a\u0003 \u0015UCC\u0001D\u0005+\u00111\tBb\u0006\u0015\u0015\u0019Ma\u0011\u0004D\u000e\r;1\t\u0003\u0005\u0004\u0005l\u0005EbQ\u0003\t\u0005\u0003\u000b49\u0002\u0002\u0005\u0004\u0012\u00065$\u0019AAf\u0011!\u0019i,!\u001cA\u0002\r5\b\u0002CCI\u0003[\u0002\ra!<\t\u0011\u0015U\u0015Q\u000ea\u0001\r?\u0001bA!\u0010\u0002z\u0019U\u0001\u0002CCN\u0003[\u0002\rAb\t+\t\u0019}Q1E\u000b\u0005\rO1)\u0004\u0006\u0003\u0007*\u0019e\u0002CBAh\u000774Y\u0003\u0005\u0007\u0002P\u001a52Q^Bw\rc19$\u0003\u0003\u00070\u0005E&A\u0002+va2,G\u0007\u0005\u0004\u0003>\u0005ed1\u0007\t\u0005\u0003\u000b4)\u0004\u0002\u0005\u0004\u0012\u0006=$\u0019AAfU\u00111\t$b\t\t\u0015\u0015}\u0014qNA\u0001\u0002\u00041Y\u0004\u0005\u0004\u0005l\u0005Eb1G\u0001\u000bEJ\fgn\u00195NCN\\GC\u0002BD\r\u00032\u0019\u0005\u0003\u0005\u00058\u0005M\u0004\u0019ABw\u0011!1)%a\u001dA\u0002\r5\u0018!\u00016\u0002\t)|\u0017N\\\u000b\u0005\r\u00172\t\u0006\u0006\u0006\u0007N\u0019Mcq\u000bD.\r?\u0002bA!\u0010\u0002z\u0019=\u0003\u0003BAc\r#\"\u0001b!%\u0002v\t\u0007\u00111\u001a\u0005\t\r+\n)\b1\u0001\u0004n\u0006\u0011\u0001/\r\u0005\t\r3\n)\b1\u0001\u0007N\u0005\u0011A/\r\u0005\t\r;\n)\b1\u0001\u0004n\u0006\u0011\u0001O\r\u0005\t\rC\n)\b1\u0001\u0007N\u0005\u0011AOM\u000b\u0005\rK2Y\u0007\u0006\u0006\u0007h\u00195dq\u000eD9\rg\u0002bA!\u0010\u0002z\u0019%\u0004\u0003BAc\rW\"\u0001b!%\u0002x\t\u0007\u00111\u001a\u0005\t\u0007{\u000b9\b1\u0001\u0004n\"AQ\u0011SA<\u0001\u0004\u0019i\u000f\u0003\u0005\u0006\u0016\u0006]\u0004\u0019\u0001D4\u0011!)Y*a\u001eA\u0002\u0019\u001d\u0014aB5oG2,H-Z\u000b\u0005\rs2y\b\u0006\u0004\u0007|\u0019\reQ\u0011\t\u0007\u0005{\tIH\" \u0011\t\u0005\u0015gq\u0010\u0003\t\t{\f9J1\u0001\u0007\u0002F!1QRAk\u0011!\u0019I/a&A\u0002\r5\b\u0002\u0003Cr\u0003/\u0003\rA\" \u0002\r\u0005\u0004\b/\u001a8e+\u00111YI\"%\u0015\r\u00195e1\u0013DK!\u0019\u0011i$!\u001f\u0007\u0010B!\u0011Q\u0019DI\t!!i0!'C\u0002\u0019\u0005\u0005\u0002CBu\u00033\u0003\ra!<\t\u0011\u0011\r\u0018\u0011\u0014a\u0001\r\u001f\u000bQ\"\u00199qK:$\u0017J\u001c)mC\u000e,W\u0003\u0002DN\rC#bA\"(\u0007$\u001a\u0015\u0006C\u0002B\u001f\u0003s2y\n\u0005\u0003\u0002F\u001a\u0005F\u0001\u0003C\u007f\u00037\u0013\rA\"!\t\u0011\r%\u00181\u0014a\u0001\u0007[D\u0001\u0002b9\u0002\u001c\u0002\u0007aq\u0014\u0015\u0005\u00037\u001b\u0019#\u0001\bbaB,g\u000eZ%o!2\f7-Z\u0019\u0016\t\u00195f1\u0017\u000b\t\r_3)Lb/\u0007>B1!QHA=\rc\u0003B!!2\u00074\u0012AAQ`AO\u0005\u00041\t\t\u0003\u0005\u00078\u0006u\u0005\u0019\u0001D]\u0003\u0019\u0001\u0018M]3oiB1AqEA\u0019\rcC\u0001b!;\u0002\u001e\u0002\u00071Q\u001e\u0005\t\tG\fi\n1\u0001\u00072\u00069Q\r_2mk\u0012,G\u0003BBF\r\u0007D\u0001b!;\u0002 \u0002\u00071Q^\u0001\bgBd\u0017\u000e^!u)\u00111IMb3\u0011\u0011\u0005=\u00171`BF\u0007\u0017C\u0001B\"4\u0002\"\u0002\u00071Q^\u0001\u0002]\u0006qQn\u001c3jMf|%OU3n_Z,W\u0003\u0002Dj\r3$BA\"6\u0007\\B1!QHA=\r/\u0004B!!2\u0007Z\u0012AAQ`AR\u0005\u0004\tY\r\u0003\u0005\u0007^\u0006\r\u0006\u0019\u0001Dp\u0003\u00051\u0007CCAh\rC\u001cio!$\u0007f&!a1]AY\u0005%1UO\\2uS>t'\u0007\u0005\u0004\u0002P\u000emgq[\u0015\u0007\u0003s\n\tD`:\u0002\u000f\u0005d\u0017.Y:fI\u00061\u0011\r\u001a3P]\u0016$BAb<\u0007r6\tq\nC\u0004\u0007tZ\u0003\raa\u0019\u0002\t\u0015dW-\u001c\u000b\u0007\r_49Pb?\t\u000f\u0019ex\u000b1\u0001\u0004f\u0005\u00191.Z=\t\u000f\u0011\rx\u000b1\u0001\u0004j\u0005)1\r\\3beR\u00111\u0011V\u0001\u0007e\u0016\u001cX\u000f\u001c;\u0015\u0005\r5$aB'baBLgnZ\u000b\u0007\u000f\u00139\tbb\u0006\u0011\u0011\u0005uv1BD\b\u000f'IAa\"\u0004\u0002*\n\u0019Q*\u00199\u0011\t\u0005\u0015w\u0011\u0003\u0003\b\u0003\u0013T&\u0019AAf!!\ty-a?\u0003\b\u001eU\u0001\u0003BAc\u000f/!\u0001\"a8[\t\u000b\u0007\u00111Z\u0001\b\u001b\u0006\u0004\b/\u001b8h+\t9iB\u0004\u0003\u0002>\u001e}\u0011\u0002BD\u0011\u0003S\u000b1!T1qQ\rYvQ\u0005\t\u0005\u0007\u0017<9#\u0003\u0003\b*\r5'AB;okN,G-\u0001\u0005NCB\u0004\u0018N\\4!\u0003%y'\u000fZ3sS:<\u0007%\u0001\u0018tG\u0006d\u0017\rJ2pY2,7\r^5p]\u0012JW.\\;uC\ndW\r\n+sK\u0016\u001cV-]'ba\u0012\"S.\u00199qS:<WCAD\u001a!\u001d\u00119BWAb\u00037\fqf]2bY\u0006$3m\u001c7mK\u000e$\u0018n\u001c8%S6lW\u000f^1cY\u0016$CK]3f'\u0016\fX*\u00199%I5\f\u0007\u000f]5oO\u0002\n\u0001b\u001c:eS:\fG\u000eI\u000b\u0003\u000fw\u00012Aa\u0006*\u0003)y'\u000fZ3sK\u0012\u0014\u0015\u0010\t\u000b\u000b\u0003_<\teb\u0011\bH\u001d%\u0003b\u0002B\t\u0013\u0001\u0007!Q\u0003\u0005\b\u000f\u000bJ\u0001\u0019AD\u001a\u0003\u001di\u0017\r\u001d9j]\u001eDqa!;\n\u0001\u0004\u00119\tC\u0004\u0004\\%\u0001\rab\u000f\u0002\u0013\rd\u0017m]:OC6,\u0017AC7ba\u001a\u000b7\r^8ssV\u0011!QE\u0001\u0005g&TX-A\u0003tSj,\u0007%A\u0005l]><hnU5{K\u00069\u0011n]#naRL\u0018AC8sI\u0016\u0014\u0018N\\4CsR!\u0011q^D/\u0011\u001d\u0011Y\u0010\u0005a\u0001\u000fw\tq!\u001e9eCR,G-\u0006\u0003\bd\u001d%DCBD3\u000f_:\t\bE\u0004\u0002>\u0002\t\u0019mb\u001a\u0011\t\u0005\u0015w\u0011\u000e\u0003\b\u000fW\n\"\u0019AD7\u0005\t1\u0016'\u0005\u0003\u0002\\\u0006U\u0007b\u0002D}#\u0001\u0007\u00111\u0019\u0005\b\tG\f\u0002\u0019AD4\u0003\u001d\u0011X-\\8wK\u0012$B!a<\bx!9a\u0011 \nA\u0002\u0005\r\u0017a\u0002:fMJ,7\u000f\u001b\u000b\u0005\u0003_<i\bC\u0004\u0007zN\u0001\r!a1\u0002\u0007\u001d,G\u000f\u0006\u0003\b\u0004\u001e\u0015\u0005CBAh\u00077\fY\u000eC\u0004\u0007zR\u0001\r!a1\u0016\u0005\u001d%\u0005CBAz\u00057\u000bI0\u0001\u0007lKf\u001c\u0018\n^3sCR|'/\u0006\u0002\b\u0010B1\u00111\u001fBN\u0003\u0007\faB^1mk\u0016\u001c\u0018\n^3sCR|'/\u0006\u0002\b\u0016B1\u00111\u001fBN\u00037\f\u0001bY8oi\u0006Lgn\u001d\u000b\u0005\u0005G;Y\nC\u0004\u0007zb\u0001\r!a1\u0016\u0005\u0005eXCADQ!\u0019\tyma7\u0002zV\u0011\u0011q^\u0001\u0006g2L7-\u001a\u000b\u0007\u0003_<Ikb+\t\u000f\tux\u00041\u0001\u0003\b\"9qQV\u0010A\u0002\t\u001d\u0015!B;oi&d\u0017aA7baV1q1WD]\u000f\u007f#Ba\".\bBB9\u0011Q\u0018\u0001\b8\u001eu\u0006\u0003BAc\u000fs#qab/!\u0005\u0004\tYM\u0001\u0002LeA!\u0011QYD`\t\u001d!i\t\tb\u0001\u0003\u0017DqA\"8!\u0001\u00049\u0019\r\u0005\u0005\u0002P\u001e\u0015\u0017\u0011`De\u0013\u001199-!-\u0003\u0013\u0019+hn\u0019;j_:\f\u0004\u0003CAh\u0003w<9l\"0\u0002\u000f\u0019d\u0017\r^'baV1qqZDk\u000f3$Ba\"5\b\\B9\u0011Q\u0018\u0001\bT\u001e]\u0007\u0003BAc\u000f+$qab/\"\u0005\u0004\tY\r\u0005\u0003\u0002F\u001eeGa\u0002CGC\t\u0007\u00111\u001a\u0005\b\r;\f\u0003\u0019ADo!!\tym\"2\u0002z\u001e}\u0007CBAz\u0007'9\t\u000f\u0005\u0005\u0002P\u0006mx1[Dl\u0003\u001d\u0019w\u000e\u001c7fGR,bab:\bn\u001eEH\u0003BDu\u000fg\u0004r!!0\u0001\u000fW<y\u000f\u0005\u0003\u0002F\u001e5HaBD^E\t\u0007\u00111\u001a\t\u0005\u0003\u000b<\t\u0010B\u0004\u0005\u000e\n\u0012\r!a3\t\u000f\u001dU(\u00051\u0001\bx\u0006\u0011\u0001O\u001a\t\t\u0003\u001f<I0!?\b~&!q1`AY\u0005=\u0001\u0016M\u001d;jC24UO\\2uS>t\u0007\u0003CAh\u0003w<Yob<\u0002\r\r|gnY1u+\u0011A\u0019\u0001#\u0003\u0015\t!\u0015\u00012\u0002\t\b\u0003{\u0003\u00111\u0019E\u0004!\u0011\t)\r#\u0003\u0005\u000f\u001155E1\u0001\bn!9\u0001RB\u0012A\u0002!=\u0011AB:vM\u001aL\u0007\u0010\u0005\u0004\u0002t\u000eM\u0001\u0012\u0003\t\t\u0003\u001f\fY0a1\t\bQ!\u00111\u001cE\u000b\u0011\u001dA9\u0002\na\u0001\u00113\t\u0011\u0001\u001d\u0019\u0005\u00117Ay\u0002\u0005\u0005\u0002P\u0006m\bRDAn!\u0011\t)\rc\b\u0005\u0019!\u0005\u0002RCA\u0001\u0002\u0003\u0015\t!a3\u0003\u0007}#\u0013\u0007K\u0002%\u0007G\tqAY5oI&tw\r\u0006\u0003\u0002z\"%\u0002b\u0002E\u0016K\u0001\u0007\u00111Y\u0001\u0002W\"\u001aQea\t")
public final class TreeSeqMap<K, V>
extends AbstractMap<K, V>
implements scala.collection.immutable.SeqMap<K, V>,
StrictOptimizedMapOps<K, V, TreeSeqMap, TreeSeqMap<K, V>> {
    private final Ordering<K> scala$collection$immutable$TreeSeqMap$$ordering;
    private final Map<K, Tuple2<Object, V>> scala$collection$immutable$TreeSeqMap$$mapping;
    private final int ordinal;
    private final OrderBy orderedBy;
    private final int size;

    public static <K, V> scala.collection.mutable.Builder<Tuple2<K, V>, TreeSeqMap<K, V>> newBuilder(OrderBy orderedBy) {
        return new Builder(orderedBy);
    }

    public static <K, V> scala.collection.mutable.Builder<Tuple2<K, V>, TreeSeqMap<K, V>> newBuilder() {
        TreeSeqMap$OrderBy$Insertion$ newBuilder_newBuilder_orderedBy = TreeSeqMap$OrderBy$Insertion$.MODULE$;
        return new Builder(newBuilder_newBuilder_orderedBy);
    }

    public static <K, V> TreeSeqMap<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        return TreeSeqMap$.MODULE$.from((IterableOnce)it);
    }

    public static TreeSeqMap<Nothing$, Nothing$> Empty() {
        return TreeSeqMap$.MODULE$.Empty();
    }

    @Override
    public IterableOps $plus(Tuple2 elem1, Tuple2 elem2, Seq elems) {
        return scala.collection.StrictOptimizedMapOps.$plus$(this, elem1, elem2, elems);
    }

    @Override
    public Tuple2<TreeSeqMap<K, V>, TreeSeqMap<K, V>> partition(Function1<Tuple2<K, V>, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<TreeSeqMap<K, V>, TreeSeqMap<K, V>> span(Function1<Tuple2<K, V>, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> unzip(Function1<Tuple2<K, V>, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>, scala.collection.immutable.Iterable<A3>> unzip3(Function1<Tuple2<K, V>, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(scala.collection.mutable.Builder<B, C2> b, Function1<Tuple2<K, V>, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(scala.collection.mutable.Builder<B, C2> b, Function1<Tuple2<K, V>, IterableOnce<B>> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatMap$(this, b, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedConcat(IterableOnce<B> that, scala.collection.mutable.Builder<B, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedConcat$(this, that, b);
    }

    @Override
    public Object collect(PartialFunction pf) {
        return StrictOptimizedIterableOps.collect$(this, pf);
    }

    @Override
    public final <B, C2> C2 strictOptimizedCollect(scala.collection.mutable.Builder<B, C2> b, PartialFunction<Tuple2<K, V>, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(scala.collection.mutable.Builder<B, C2> b, Function1<Tuple2<K, V>, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, scala.collection.mutable.Builder<Tuple2<Tuple2<K, V>, B>, C2> b) {
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
    public <A1, A2> Tuple2<scala.collection.immutable.Iterable<A1>, scala.collection.immutable.Iterable<A2>> partitionMap(Function1<Tuple2<K, V>, Either<A1, A2>> f) {
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
    public String stringPrefix() {
        return SeqMap.stringPrefix$(this);
    }

    public Ordering<K> scala$collection$immutable$TreeSeqMap$$ordering() {
        return this.scala$collection$immutable$TreeSeqMap$$ordering;
    }

    public Map<K, Tuple2<Object, V>> scala$collection$immutable$TreeSeqMap$$mapping() {
        return this.scala$collection$immutable$TreeSeqMap$$mapping;
    }

    private int ordinal() {
        return this.ordinal;
    }

    public OrderBy orderedBy() {
        return this.orderedBy;
    }

    @Override
    public String className() {
        return "TreeSeqMap";
    }

    @Override
    public MapFactory<TreeSeqMap> mapFactory() {
        return TreeSeqMap$.MODULE$;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int knownSize() {
        return this.size();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    public TreeSeqMap<K, V> orderingBy(OrderBy orderBy) {
        OrderBy orderBy2 = orderBy;
        OrderBy orderBy3 = this.orderedBy();
        if (!(orderBy2 != null ? !orderBy2.equals(orderBy3) : orderBy3 != null)) {
            return this;
        }
        if (this.isEmpty()) {
            return TreeSeqMap$.MODULE$.empty(orderBy);
        }
        return new TreeSeqMap<K, V>(this.scala$collection$immutable$TreeSeqMap$$ordering(), this.scala$collection$immutable$TreeSeqMap$$mapping(), this.ordinal(), orderBy);
    }

    @Override
    public <V1> TreeSeqMap<K, V1> updated(K key, V1 value) {
        Tuple2 tuple2;
        boolean bl = false;
        Some some = null;
        Option option = this.scala$collection$immutable$TreeSeqMap$$mapping().get(key);
        if (this.ordinal() == -1) {
            OrderBy orderBy = this.orderedBy();
            TreeSeqMap$OrderBy$Modification$ treeSeqMap$OrderBy$Modification$ = TreeSeqMap$OrderBy$Modification$.MODULE$;
            if (orderBy != null && orderBy.equals(treeSeqMap$OrderBy$Modification$) || option.isEmpty()) {
                TreeSeqMap treeSeqMap = TreeSeqMap$.MODULE$.empty(this.orderedBy());
                if (treeSeqMap == null) {
                    throw null;
                }
                return (TreeSeqMap)treeSeqMap.concat((IterableOnce)this).$plus(new Tuple2<K, V1>(key, value));
            }
        }
        if (option instanceof Some) {
            bl = true;
            some = (Some)option;
            Tuple2 tuple22 = (Tuple2)some.value();
            if (tuple22 != null) {
                int o = tuple22._1$mcI$sp();
                OrderBy orderBy = this.orderedBy();
                TreeSeqMap$OrderBy$Insertion$ treeSeqMap$OrderBy$Insertion$ = TreeSeqMap$OrderBy$Insertion$.MODULE$;
                if (orderBy != null && orderBy.equals(treeSeqMap$OrderBy$Insertion$)) {
                    return new TreeSeqMap<K, V>(this.scala$collection$immutable$TreeSeqMap$$ordering().include(o, key), (Map)this.scala$collection$immutable$TreeSeqMap$$mapping().updated(key, new Tuple2<Integer, V1>(o, value)), this.ordinal(), this.orderedBy());
                }
            }
        }
        if (bl && (tuple2 = (Tuple2)some.value()) != null) {
            int o = tuple2._1$mcI$sp();
            int scala$collection$immutable$TreeSeqMap$$increment_ord = this.ordinal();
            int o1 = scala$collection$immutable$TreeSeqMap$$increment_ord == Integer.MAX_VALUE ? Integer.MIN_VALUE : scala$collection$immutable$TreeSeqMap$$increment_ord + 1;
            return new TreeSeqMap<K, V>(this.scala$collection$immutable$TreeSeqMap$$ordering().exclude(o).append(o1, key), (Map)this.scala$collection$immutable$TreeSeqMap$$mapping().updated(key, new Tuple2<Integer, V1>(o1, value)), o1, this.orderedBy());
        }
        if (None$.MODULE$.equals(option)) {
            int scala$collection$immutable$TreeSeqMap$$increment_ord = this.ordinal();
            int o1 = scala$collection$immutable$TreeSeqMap$$increment_ord == Integer.MAX_VALUE ? Integer.MIN_VALUE : scala$collection$immutable$TreeSeqMap$$increment_ord + 1;
            return new TreeSeqMap<K, V>(this.scala$collection$immutable$TreeSeqMap$$ordering().append(o1, key), (Map)this.scala$collection$immutable$TreeSeqMap$$mapping().updated(key, new Tuple2<Integer, V1>(o1, value)), o1, this.orderedBy());
        }
        throw new MatchError(option);
    }

    @Override
    public TreeSeqMap<K, V> removed(K key) {
        Tuple2 tuple2;
        Option option = this.scala$collection$immutable$TreeSeqMap$$mapping().get(key);
        if (option instanceof Some && (tuple2 = (Tuple2)((Some)option).value()) != null) {
            int o = tuple2._1$mcI$sp();
            return new TreeSeqMap<K, V>(this.scala$collection$immutable$TreeSeqMap$$ordering().exclude(o), (Map)this.scala$collection$immutable$TreeSeqMap$$mapping().removed(key), this.ordinal(), this.orderedBy());
        }
        if (None$.MODULE$.equals(option)) {
            return this;
        }
        throw new MatchError(option);
    }

    public TreeSeqMap<K, V> refresh(K key) {
        Tuple2 tuple2;
        Option option = this.scala$collection$immutable$TreeSeqMap$$mapping().get(key);
        if (option instanceof Some && (tuple2 = (Tuple2)((Some)option).value()) != null) {
            int o = tuple2._1$mcI$sp();
            int scala$collection$immutable$TreeSeqMap$$increment_ord = this.ordinal();
            int o1 = scala$collection$immutable$TreeSeqMap$$increment_ord == Integer.MAX_VALUE ? Integer.MIN_VALUE : scala$collection$immutable$TreeSeqMap$$increment_ord + 1;
            return new TreeSeqMap<K, V>(this.scala$collection$immutable$TreeSeqMap$$ordering().exclude(o).append(o1, key), this.scala$collection$immutable$TreeSeqMap$$mapping(), o1, this.orderedBy());
        }
        if (None$.MODULE$.equals(option)) {
            return this;
        }
        throw new MatchError(option);
    }

    @Override
    public Option<V> get(K key) {
        Option option = this.scala$collection$immutable$TreeSeqMap$$mapping().get(key);
        if (option == null) {
            throw null;
        }
        Option map_this = option;
        if (map_this.isEmpty()) {
            return None$.MODULE$;
        }
        return new Some(((Tuple2)map_this.get())._2());
    }

    @Override
    public Iterator<Tuple2<K, V>> iterator() {
        return new AbstractIterator<Tuple2<K, V>>(this){
            private final Ordering.Iterator<K> iter;
            private final /* synthetic */ TreeSeqMap $outer;

            public boolean hasNext() {
                return this.iter.hasNext();
            }

            /*
             * WARNING - void declaration
             */
            public Tuple2<K, V> next() {
                void scala$collection$immutable$TreeSeqMap$$binding_k;
                TreeSeqMap treeSeqMap = this.$outer;
                K k = this.iter.next();
                if (treeSeqMap == null) {
                    throw null;
                }
                Tuple2 scala$collection$immutable$TreeSeqMap$$binding_qual$1 = (Tuple2)treeSeqMap.scala$collection$immutable$TreeSeqMap$$mapping().apply(scala$collection$immutable$TreeSeqMap$$binding_k);
                T2 scala$collection$immutable$TreeSeqMap$$binding_x$2 = scala$collection$immutable$TreeSeqMap$$binding_qual$1.copy$default$2();
                return scala$collection$immutable$TreeSeqMap$$binding_qual$1.copy(scala$collection$immutable$TreeSeqMap$$binding_k, scala$collection$immutable$TreeSeqMap$$binding_x$2);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.iter = $outer.scala$collection$immutable$TreeSeqMap$$ordering().iterator();
            }
        };
    }

    @Override
    public Iterator<K> keysIterator() {
        return new AbstractIterator<K>(this){
            private final Ordering.Iterator<K> iter;

            public boolean hasNext() {
                return this.iter.hasNext();
            }

            public K next() {
                return this.iter.next();
            }
            {
                this.iter = $outer.scala$collection$immutable$TreeSeqMap$$ordering().iterator();
            }
        };
    }

    @Override
    public Iterator<V> valuesIterator() {
        return new AbstractIterator<V>(this){
            private final Ordering.Iterator<K> iter;
            private final /* synthetic */ TreeSeqMap $outer;

            public boolean hasNext() {
                return this.iter.hasNext();
            }

            /*
             * WARNING - void declaration
             */
            public V next() {
                void scala$collection$immutable$TreeSeqMap$$value_p;
                void scala$collection$immutable$TreeSeqMap$$binding_k;
                TreeSeqMap treeSeqMap = this.$outer;
                K k = this.iter.next();
                if (treeSeqMap == null) {
                    throw null;
                }
                TreeSeqMap scala$collection$immutable$TreeSeqMap$$binding_this = treeSeqMap;
                Tuple2 scala$collection$immutable$TreeSeqMap$$binding_qual$1 = (Tuple2)scala$collection$immutable$TreeSeqMap$$binding_this.scala$collection$immutable$TreeSeqMap$$mapping().apply(scala$collection$immutable$TreeSeqMap$$binding_k);
                T2 scala$collection$immutable$TreeSeqMap$$binding_x$2 = scala$collection$immutable$TreeSeqMap$$binding_qual$1.copy$default$2();
                Object var1_2 = null;
                k = null;
                Object var3_3 = null;
                Object var4_4 = null;
                Tuple2<void, T2> tuple2 = scala$collection$immutable$TreeSeqMap$$binding_qual$1.copy(scala$collection$immutable$TreeSeqMap$$binding_k, scala$collection$immutable$TreeSeqMap$$binding_x$2);
                if (this.$outer == null) {
                    throw null;
                }
                return (V)scala$collection$immutable$TreeSeqMap$$value_p._2();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.iter = $outer.scala$collection$immutable$TreeSeqMap$$ordering().iterator();
            }
        };
    }

    @Override
    public boolean contains(K key) {
        return this.scala$collection$immutable$TreeSeqMap$$mapping().contains(key);
    }

    @Override
    public Tuple2<K, V> head() {
        K scala$collection$immutable$TreeSeqMap$$binding_k = this.scala$collection$immutable$TreeSeqMap$$ordering().head();
        Tuple2 scala$collection$immutable$TreeSeqMap$$binding_qual$1 = (Tuple2)this.scala$collection$immutable$TreeSeqMap$$mapping().apply(scala$collection$immutable$TreeSeqMap$$binding_k);
        Object scala$collection$immutable$TreeSeqMap$$binding_x$2 = scala$collection$immutable$TreeSeqMap$$binding_qual$1.copy$default$2();
        return scala$collection$immutable$TreeSeqMap$$binding_qual$1.copy(scala$collection$immutable$TreeSeqMap$$binding_k, scala$collection$immutable$TreeSeqMap$$binding_x$2);
    }

    @Override
    public Option<Tuple2<K, V>> headOption() {
        Option<K> option = this.scala$collection$immutable$TreeSeqMap$$ordering().headOption();
        if (option == null) {
            throw null;
        }
        Option<K> map_this = option;
        if (map_this.isEmpty()) {
            return None$.MODULE$;
        }
        K k = map_this.get();
        Tuple2 scala$collection$immutable$TreeSeqMap$$binding_qual$1 = (Tuple2)this.scala$collection$immutable$TreeSeqMap$$mapping().apply(k);
        Object scala$collection$immutable$TreeSeqMap$$binding_x$2 = scala$collection$immutable$TreeSeqMap$$binding_qual$1.copy$default$2();
        Object var3_3 = null;
        Object var4_4 = null;
        return new Some<Tuple2<K, V>>(scala$collection$immutable$TreeSeqMap$$binding_qual$1.copy(k, scala$collection$immutable$TreeSeqMap$$binding_x$2));
    }

    @Override
    public Tuple2<K, V> last() {
        K scala$collection$immutable$TreeSeqMap$$binding_k = this.scala$collection$immutable$TreeSeqMap$$ordering().last();
        Tuple2 scala$collection$immutable$TreeSeqMap$$binding_qual$1 = (Tuple2)this.scala$collection$immutable$TreeSeqMap$$mapping().apply(scala$collection$immutable$TreeSeqMap$$binding_k);
        Object scala$collection$immutable$TreeSeqMap$$binding_x$2 = scala$collection$immutable$TreeSeqMap$$binding_qual$1.copy$default$2();
        return scala$collection$immutable$TreeSeqMap$$binding_qual$1.copy(scala$collection$immutable$TreeSeqMap$$binding_k, scala$collection$immutable$TreeSeqMap$$binding_x$2);
    }

    @Override
    public Option<Tuple2<K, V>> lastOption() {
        Option<K> option = this.scala$collection$immutable$TreeSeqMap$$ordering().lastOption();
        if (option == null) {
            throw null;
        }
        Option<K> map_this = option;
        if (map_this.isEmpty()) {
            return None$.MODULE$;
        }
        K k = map_this.get();
        Tuple2 scala$collection$immutable$TreeSeqMap$$binding_qual$1 = (Tuple2)this.scala$collection$immutable$TreeSeqMap$$mapping().apply(k);
        Object scala$collection$immutable$TreeSeqMap$$binding_x$2 = scala$collection$immutable$TreeSeqMap$$binding_qual$1.copy$default$2();
        Object var3_3 = null;
        Object var4_4 = null;
        return new Some<Tuple2<K, V>>(scala$collection$immutable$TreeSeqMap$$binding_qual$1.copy(k, scala$collection$immutable$TreeSeqMap$$binding_x$2));
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public TreeSeqMap<K, V> tail() {
        void var2_2;
        void var3_3;
        Tuple2<K, Ordering<K>> tuple2 = this.scala$collection$immutable$TreeSeqMap$$ordering().headTail();
        if (tuple2 == null) {
            throw new MatchError((Object)null);
        }
        K head = tuple2._1();
        Ordering<K> tail = tuple2._2();
        return new TreeSeqMap<K, V>(var3_3, (Map)this.scala$collection$immutable$TreeSeqMap$$mapping().removed(var2_2), this.ordinal(), this.orderedBy());
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public TreeSeqMap<K, V> init() {
        void var3_3;
        void var2_2;
        Tuple2<Ordering<K>, K> tuple2 = this.scala$collection$immutable$TreeSeqMap$$ordering().initLast();
        if (tuple2 == null) {
            throw new MatchError((Object)null);
        }
        Ordering<K> init2 = tuple2._1();
        K last = tuple2._2();
        return new TreeSeqMap<K, V>(var2_2, (Map)this.scala$collection$immutable$TreeSeqMap$$mapping().removed(var3_3), this.ordinal(), this.orderedBy());
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public TreeSeqMap<K, V> slice(int from, int until) {
        int i;
        int f;
        if (this.size() == 0 || from >= until) {
            return TreeSeqMap$.MODULE$.empty(this.orderedBy());
        }
        int sz = this.size();
        int u = until <= sz ? until : sz;
        int l = u - (f = from >= 0 ? from : 0);
        if (l <= 0) {
            return TreeSeqMap$.MODULE$.empty(this.orderedBy());
        }
        if (l > sz / 2) {
            void var11_11;
            void var12_12;
            void var8_8;
            void var9_9;
            Tuple2<Ordering<K>, Ordering<K>> tuple2 = this.scala$collection$immutable$TreeSeqMap$$ordering().splitAt(f);
            if (tuple2 == null) {
                throw new MatchError((Object)null);
            }
            Ordering<K> front = tuple2._1();
            Ordering<K> rest = tuple2._2();
            Tuple2 tuple22 = var9_9.splitAt(l);
            if (tuple22 == null) {
                throw new MatchError((Object)null);
            }
            Ordering ong = tuple22._1();
            Ordering rear = tuple22._2();
            Map mng = this.scala$collection$immutable$TreeSeqMap$$mapping();
            for (Object t2 : var8_8) {
                void $minus_key;
                if (mng == null) {
                    throw null;
                }
                t2 = null;
                mng = (Map)mng.removed($minus_key);
            }
            for (Object t3 : var12_12) {
                void $minus_key;
                if (mng == null) {
                    throw null;
                }
                t3 = null;
                mng = (Map)mng.removed($minus_key);
            }
            return new TreeSeqMap<K, V>(var11_11, mng, this.ordinal(), this.orderedBy());
        }
        OrderBy newBuilder_orderedBy = this.orderedBy();
        Object var22_18 = null;
        Builder bdr = new Builder(newBuilder_orderedBy);
        Ordering.Iterator<K> iter = this.scala$collection$immutable$TreeSeqMap$$ordering().iterator();
        for (i = 0; i < f; ++i) {
            iter.next();
        }
        while (i < u) {
            K k = iter.next();
            Tuple2 addOne_elem = new Tuple2(k, ((Tuple2)this.scala$collection$immutable$TreeSeqMap$$mapping().apply(k))._2());
            bdr.addOne(addOne_elem);
            Object var23_23 = null;
            ++i;
        }
        return bdr.result();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <K2, V2> TreeSeqMap<K2, V2> map(Function1<Tuple2<K, V>, Tuple2<K2, V2>> f) {
        OrderBy newBuilder_orderedBy = this.orderedBy();
        Object var10_2 = null;
        Builder<void, void> bdr = new Builder<void, void>(newBuilder_orderedBy);
        Ordering.Iterator<K> iter = this.scala$collection$immutable$TreeSeqMap$$ordering().iterator();
        while (iter.hasNext()) {
            void var9_10;
            void var8_9;
            void var6_7;
            K k = iter.next();
            Tuple2 tuple2 = (Tuple2)this.scala$collection$immutable$TreeSeqMap$$mapping().apply(k);
            if (tuple2 == null) {
                throw new MatchError((Object)null);
            }
            Object v = tuple2._2();
            Tuple2<K2, V2> tuple22 = f.apply(new Tuple2<K, void>(k, var6_7));
            if (tuple22 == null) {
                throw new MatchError((Object)null);
            }
            K2 k2 = tuple22._1();
            V2 v2 = tuple22._2();
            Tuple2<void, void> addOne_elem = new Tuple2<void, void>(var8_9, var9_10);
            bdr.addOne(addOne_elem);
            Object var11_11 = null;
        }
        return bdr.result();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <K2, V2> TreeSeqMap<K2, V2> flatMap(Function1<Tuple2<K, V>, IterableOnce<Tuple2<K2, V2>>> f) {
        OrderBy newBuilder_orderedBy = this.orderedBy();
        Object var11_2 = null;
        Builder<void, void> bdr = new Builder<void, void>(newBuilder_orderedBy);
        Ordering.Iterator<K> iter = this.scala$collection$immutable$TreeSeqMap$$ordering().iterator();
        while (iter.hasNext()) {
            void var6_7;
            K k = iter.next();
            Tuple2 tuple2 = (Tuple2)this.scala$collection$immutable$TreeSeqMap$$mapping().apply(k);
            if (tuple2 == null) {
                throw new MatchError((Object)null);
            }
            Object v = tuple2._2();
            Iterator<Tuple2<K2, V2>> jter = f.apply(new Tuple2<K, void>(k, var6_7)).iterator();
            while (jter.hasNext()) {
                void var10_11;
                void var9_10;
                Tuple2<K2, V2> tuple22 = jter.next();
                if (tuple22 == null) {
                    throw new MatchError((Object)null);
                }
                K2 k2 = tuple22._1();
                V2 v2 = tuple22._2();
                Tuple2<void, void> addOne_elem = new Tuple2<void, void>(var9_10, var10_11);
                bdr.addOne(addOne_elem);
                Object var12_12 = null;
            }
        }
        return bdr.result();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <K2, V2> TreeSeqMap<K2, V2> collect(PartialFunction<Tuple2<K, V>, Tuple2<K2, V2>> pf) {
        OrderBy newBuilder_orderedBy = this.orderedBy();
        Object var7_2 = null;
        Builder bdr = new Builder(newBuilder_orderedBy);
        Ordering.Iterator<K> iter = this.scala$collection$immutable$TreeSeqMap$$ordering().iterator();
        while (iter.hasNext()) {
            void var6_7;
            K k = iter.next();
            Tuple2 tuple2 = (Tuple2)this.scala$collection$immutable$TreeSeqMap$$mapping().apply(k);
            if (tuple2 == null) {
                throw new MatchError((Object)null);
            }
            Object v = tuple2._2();
            pf.runWith((Function1<Tuple2, scala.collection.mutable.Builder> & Serializable)(B x0$1) -> {
                if (x0$1 != null) {
                    Object k2 = x0$1._1();
                    Object v2 = x0$1._2();
                    return (scala.collection.mutable.Builder)bdr.addOne(new Tuple2(k2, v2));
                }
                throw new MatchError((Object)null);
            }).apply(new Tuple2<K, void>(k, var6_7));
        }
        return bdr.result();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <V2> TreeSeqMap<K, V2> concat(IterableOnce<Tuple2<K, V2>> suffix) {
        Ordering<Object> ong = this.scala$collection$immutable$TreeSeqMap$$ordering();
        Map mng = this.scala$collection$immutable$TreeSeqMap$$mapping();
        int scala$collection$immutable$TreeSeqMap$$increment_ord = this.ordinal();
        int ord = scala$collection$immutable$TreeSeqMap$$increment_ord == Integer.MAX_VALUE ? Integer.MIN_VALUE : scala$collection$immutable$TreeSeqMap$$increment_ord + 1;
        Iterator<Tuple2<K, V2>> iter = suffix.iterator();
        while (iter.hasNext()) {
            void var8_9;
            Tuple2 tuple2;
            void var7_8;
            Tuple2<K, V2> tuple22 = iter.next();
            if (tuple22 == null) {
                throw new MatchError((Object)null);
            }
            K k = tuple22._1();
            V2 v2 = tuple22._2();
            Option option = mng.get(var7_8);
            if (option instanceof Some && (tuple2 = (Tuple2)((Some)option).value()) != null) {
                int o = tuple2._1$mcI$sp();
                Object v = tuple2._2();
                OrderBy orderBy = this.orderedBy();
                TreeSeqMap$OrderBy$Insertion$ treeSeqMap$OrderBy$Insertion$ = TreeSeqMap$OrderBy$Insertion$.MODULE$;
                if (orderBy != null && orderBy.equals(treeSeqMap$OrderBy$Insertion$) && !BoxesRunTime.equals(v, var8_9)) {
                    mng = (Map)mng.updated(var7_8, new Tuple2<Integer, void>(o, var8_9));
                    continue;
                }
                OrderBy orderBy2 = this.orderedBy();
                TreeSeqMap$OrderBy$Modification$ treeSeqMap$OrderBy$Modification$ = TreeSeqMap$OrderBy$Modification$.MODULE$;
                if (orderBy2 == null || !orderBy2.equals(treeSeqMap$OrderBy$Modification$)) continue;
                mng = (Map)mng.updated(var7_8, new Tuple2<Integer, void>(ord, var8_9));
                ong = ong.exclude(o).append(ord, var7_8);
                ord = ord == Integer.MAX_VALUE ? Integer.MIN_VALUE : ord + 1;
                continue;
            }
            if (None$.MODULE$.equals(option)) {
                mng = (Map)mng.updated(var7_8, new Tuple2<Integer, void>(ord, var8_9));
                ong = ong.append(ord, var7_8);
                ord = ord == Integer.MAX_VALUE ? Integer.MIN_VALUE : ord + 1;
                continue;
            }
            throw new MatchError(option);
        }
        return new TreeSeqMap<K, V>(ong, mng, ord, this.orderedBy());
    }

    public V scala$collection$immutable$TreeSeqMap$$value(Tuple2<?, V> p) {
        return p._2();
    }

    public Tuple2<K, V> scala$collection$immutable$TreeSeqMap$$binding(K k) {
        Tuple2 qual$1 = (Tuple2)this.scala$collection$immutable$TreeSeqMap$$mapping().apply(k);
        Object x$2 = qual$1.copy$default$2();
        return qual$1.copy(k, x$2);
    }

    public static final /* synthetic */ Object $anonfun$get$1(TreeSeqMap $this, Tuple2 p) {
        return $this.scala$collection$immutable$TreeSeqMap$$value(p);
    }

    public static final /* synthetic */ Tuple2 $anonfun$headOption$1(TreeSeqMap $this, Object k) {
        return $this.scala$collection$immutable$TreeSeqMap$$binding(k);
    }

    public static final /* synthetic */ Tuple2 $anonfun$lastOption$1(TreeSeqMap $this, Object k) {
        return $this.scala$collection$immutable$TreeSeqMap$$binding(k);
    }

    public TreeSeqMap(Ordering<K> ordering, Map<K, Tuple2<Object, V>> mapping, int ordinal, OrderBy orderedBy) {
        this.scala$collection$immutable$TreeSeqMap$$ordering = ordering;
        this.scala$collection$immutable$TreeSeqMap$$mapping = mapping;
        this.ordinal = ordinal;
        this.orderedBy = orderedBy;
        this.size = mapping.size();
    }

    public static final class Builder<K, V>
    implements scala.collection.mutable.Builder<Tuple2<K, V>, TreeSeqMap<K, V>> {
        private final OrderBy orderedBy;
        private final MapBuilderImpl<K, Tuple2<Object, V>> bdr;
        private Ordering<K> ong;
        private int ord;
        private TreeSeqMap<K, V> aliased;

        @Override
        public void sizeHint(int size) {
            scala.collection.mutable.Builder.sizeHint$(this, size);
        }

        @Override
        public final void sizeHint(IterableOnce<?> coll, int delta) {
            scala.collection.mutable.Builder.sizeHint$(this, coll, delta);
        }

        @Override
        public final int sizeHint$default$2() {
            return scala.collection.mutable.Builder.sizeHint$default$2$(this);
        }

        @Override
        public final void sizeHintBounded(int size, Iterable<?> boundingColl) {
            scala.collection.mutable.Builder.sizeHintBounded$(this, size, boundingColl);
        }

        @Override
        public <NewTo> scala.collection.mutable.Builder<Tuple2<K, V>, NewTo> mapResult(Function1<TreeSeqMap<K, V>, NewTo> f) {
            return scala.collection.mutable.Builder.mapResult$(this, f);
        }

        @Override
        public final Growable $plus$eq(Object elem) {
            return Growable.$plus$eq$(this, elem);
        }

        @Override
        public final Growable $plus$eq(Object elem1, Object elem2, Seq elems) {
            return Growable.$plus$eq$(this, elem1, elem2, elems);
        }

        @Override
        public Growable<Tuple2<K, V>> addAll(IterableOnce<Tuple2<K, V>> xs) {
            return Growable.addAll$(this, xs);
        }

        @Override
        public final Growable<Tuple2<K, V>> $plus$plus$eq(IterableOnce<Tuple2<K, V>> xs) {
            return Growable.$plus$plus$eq$(this, xs);
        }

        @Override
        public int knownSize() {
            return Growable.knownSize$(this);
        }

        public Builder<K, V> addOne(Tuple2<K, V> elem) {
            return this.addOne(elem._1(), elem._2());
        }

        /*
         * WARNING - void declaration
         */
        public Builder<K, V> addOne(K key, V value) {
            if (this.aliased != null) {
                this.aliased = this.aliased.updated((Object)key, value);
            } else {
                Tuple2 tuple2 = this.bdr.getOrElse(key, null);
                if (tuple2 != null) {
                    int o = tuple2._1$mcI$sp();
                    Object v = tuple2._2();
                    OrderBy orderBy = this.orderedBy;
                    TreeSeqMap$OrderBy$Insertion$ treeSeqMap$OrderBy$Insertion$ = TreeSeqMap$OrderBy$Insertion$.MODULE$;
                    if (orderBy != null && orderBy.equals(treeSeqMap$OrderBy$Insertion$) && !BoxesRunTime.equals(v, value)) {
                        this.bdr.addOne(key, new Tuple2<Integer, V>(o, value));
                    } else {
                        OrderBy orderBy2 = this.orderedBy;
                        TreeSeqMap$OrderBy$Modification$ treeSeqMap$OrderBy$Modification$ = TreeSeqMap$OrderBy$Modification$.MODULE$;
                        if (orderBy2 != null && orderBy2.equals(treeSeqMap$OrderBy$Modification$)) {
                            void appendInPlace_ordinal;
                            this.bdr.addOne(key, new Tuple2<Integer, V>(this.ord, value));
                            Ordering<K> ordering = this.ong.exclude(o);
                            int n = this.ord;
                            if (ordering == null) {
                                throw null;
                            }
                            this.ong = ordering.appendInPlace1(null, (int)appendInPlace_ordinal, key);
                            int scala$collection$immutable$TreeSeqMap$$increment_ord = this.ord;
                            this.ord = scala$collection$immutable$TreeSeqMap$$increment_ord == Integer.MAX_VALUE ? Integer.MIN_VALUE : scala$collection$immutable$TreeSeqMap$$increment_ord + 1;
                        }
                    }
                } else {
                    void appendInPlace_ordinal;
                    this.bdr.addOne(key, new Tuple2<Integer, V>(this.ord, value));
                    Ordering<K> ordering = this.ong;
                    int n = this.ord;
                    if (ordering == null) {
                        throw null;
                    }
                    this.ong = ordering.appendInPlace1(null, (int)appendInPlace_ordinal, key);
                    int scala$collection$immutable$TreeSeqMap$$increment_ord = this.ord;
                    this.ord = scala$collection$immutable$TreeSeqMap$$increment_ord == Integer.MAX_VALUE ? Integer.MIN_VALUE : scala$collection$immutable$TreeSeqMap$$increment_ord + 1;
                }
            }
            return this;
        }

        @Override
        public void clear() {
            this.ong = TreeSeqMap$Ordering$Zero$.MODULE$;
            this.ord = 0;
            this.bdr.clear();
            this.aliased = null;
        }

        @Override
        public TreeSeqMap<K, V> result() {
            if (this.aliased == null) {
                this.aliased = new TreeSeqMap(this.ong, this.bdr.result(), this.ord, this.orderedBy);
            }
            return this.aliased;
        }

        public Builder(OrderBy orderedBy) {
            this.orderedBy = orderedBy;
            this.bdr = new MapBuilderImpl();
            this.ong = TreeSeqMap$Ordering$Zero$.MODULE$;
            this.ord = 0;
        }
    }

    public static interface OrderBy {
    }

    public static abstract class Ordering<T> {
        public final String toString() {
            return this.format();
        }

        public final String format() {
            StringBuilder sb = new StringBuilder();
            this.format(sb, "", "");
            return sb.result();
        }

        public abstract void format(StringBuilder var1, String var2, String var3);

        public final T head() {
            while (true) {
                if (((Object)TreeSeqMap$Ordering$Zero$.MODULE$).equals(this_)) {
                    throw new NoSuchElementException("head of empty map");
                }
                if (this_ instanceof Tip) {
                    return ((Tip)this_).value();
                }
                if (!(this_ instanceof Bin)) break;
                Ordering this_ = ((Bin)this_).left();
            }
            throw new MatchError(this_);
        }

        public final Option<T> headOption() {
            while (true) {
                if (((Object)TreeSeqMap$Ordering$Zero$.MODULE$).equals(this_)) {
                    return None$.MODULE$;
                }
                if (this_ instanceof Tip) {
                    Object v = ((Tip)this_).value();
                    return new Some(v);
                }
                if (!(this_ instanceof Bin)) break;
                Ordering this_ = ((Bin)this_).left();
            }
            throw new MatchError(this_);
        }

        public final T last() {
            while (true) {
                if (((Object)TreeSeqMap$Ordering$Zero$.MODULE$).equals(this_)) {
                    throw new NoSuchElementException("last of empty map");
                }
                if (this_ instanceof Tip) {
                    return ((Tip)this_).value();
                }
                if (!(this_ instanceof Bin)) break;
                Ordering this_ = ((Bin)this_).right();
            }
            throw new MatchError(this_);
        }

        public final Option<T> lastOption() {
            while (true) {
                if (((Object)TreeSeqMap$Ordering$Zero$.MODULE$).equals(this_)) {
                    return None$.MODULE$;
                }
                if (this_ instanceof Tip) {
                    Object v = ((Tip)this_).value();
                    return new Some(v);
                }
                if (!(this_ instanceof Bin)) break;
                Ordering this_ = ((Bin)this_).right();
            }
            throw new MatchError(this_);
        }

        public final int ordinal() {
            while (true) {
                if (((Object)TreeSeqMap$Ordering$Zero$.MODULE$).equals(this_)) {
                    return 0;
                }
                if (this_ instanceof Tip) {
                    return ((Tip)this_).ord();
                }
                if (!(this_ instanceof Bin)) break;
                Ordering this_ = ((Bin)this_).right();
            }
            throw new MatchError(this_);
        }

        public final Ordering<T> tail() {
            if (((Object)TreeSeqMap$Ordering$Zero$.MODULE$).equals(this)) {
                throw new NoSuchElementException("tail of empty map");
            }
            if (this instanceof Tip) {
                return TreeSeqMap$Ordering$Zero$.MODULE$;
            }
            if (this instanceof Bin) {
                Bin bin = (Bin)this;
                int p = bin.prefix();
                int m4 = bin.mask();
                Ordering l = bin.left();
                Ordering r = bin.right();
                return TreeSeqMap$Ordering$.MODULE$.scala$collection$immutable$TreeSeqMap$Ordering$$bin(p, m4, l.tail(), r);
            }
            throw new MatchError(this);
        }

        /*
         * WARNING - void declaration
         */
        public final Tuple2<T, Ordering<T>> headTail() {
            if (((Object)TreeSeqMap$Ordering$Zero$.MODULE$).equals(this)) {
                throw new NoSuchElementException("init of empty map");
            }
            if (this instanceof Tip) {
                Object v = ((Tip)this).value();
                return new Tuple2(v, TreeSeqMap$Ordering$Zero$.MODULE$);
            }
            if (this instanceof Bin) {
                void var9_9;
                void var8_8;
                Bin bin = (Bin)this;
                int p = bin.prefix();
                int m4 = bin.mask();
                Ordering l = bin.left();
                Ordering r = bin.right();
                Tuple2 tuple2 = l.headTail();
                if (tuple2 == null) {
                    throw new MatchError((Object)null);
                }
                Object head = tuple2._1();
                Ordering tail = tuple2._2();
                return new Tuple2(var8_8, TreeSeqMap$Ordering$.MODULE$.scala$collection$immutable$TreeSeqMap$Ordering$$bin(p, m4, var9_9, r));
            }
            throw new MatchError(this);
        }

        public final Ordering<T> init() {
            if (((Object)TreeSeqMap$Ordering$Zero$.MODULE$).equals(this)) {
                throw new NoSuchElementException("init of empty map");
            }
            if (this instanceof Tip) {
                return TreeSeqMap$Ordering$Zero$.MODULE$;
            }
            if (this instanceof Bin) {
                Bin bin = (Bin)this;
                int p = bin.prefix();
                int m4 = bin.mask();
                Ordering l = bin.left();
                Ordering r = bin.right();
                return TreeSeqMap$Ordering$.MODULE$.scala$collection$immutable$TreeSeqMap$Ordering$$bin(p, m4, l, r.init());
            }
            throw new MatchError(this);
        }

        /*
         * WARNING - void declaration
         */
        public final Tuple2<Ordering<T>, T> initLast() {
            if (((Object)TreeSeqMap$Ordering$Zero$.MODULE$).equals(this)) {
                throw new NoSuchElementException("init of empty map");
            }
            if (this instanceof Tip) {
                Object v = ((Tip)this).value();
                return new Tuple2(TreeSeqMap$Ordering$Zero$.MODULE$, v);
            }
            if (this instanceof Bin) {
                void var8_8;
                void var7_7;
                Bin bin = (Bin)this;
                int p = bin.prefix();
                int m4 = bin.mask();
                Ordering l = bin.left();
                Tuple2 tuple2 = bin.right().initLast();
                if (tuple2 == null) {
                    throw new MatchError((Object)null);
                }
                Ordering init2 = tuple2._1();
                Object last = tuple2._2();
                return new Tuple2(TreeSeqMap$Ordering$.MODULE$.scala$collection$immutable$TreeSeqMap$Ordering$$bin(p, m4, l, var7_7), var8_8);
            }
            throw new MatchError(this);
        }

        public final Iterator<T> iterator() {
            if (((Object)TreeSeqMap$Ordering$Zero$.MODULE$).equals(this)) {
                return TreeSeqMap$Ordering$Iterator$.MODULE$.Empty();
            }
            return new Iterator(this);
        }

        public final <S> Ordering<S> include(int ordinal, S value) {
            if (((Object)TreeSeqMap$Ordering$Zero$.MODULE$).equals(this)) {
                return new Tip<S>(ordinal, value);
            }
            if (this instanceof Tip) {
                int o = ((Tip)this).ord();
                if (ordinal == o) {
                    return new Tip<S>(ordinal, value);
                }
                return TreeSeqMap$Ordering$.MODULE$.scala$collection$immutable$TreeSeqMap$Ordering$$join(ordinal, new Tip<S>(ordinal, value), o, this);
            }
            if (this instanceof Bin) {
                Bin bin = (Bin)this;
                int p = bin.prefix();
                int m4 = bin.mask();
                Ordering l = bin.left();
                Ordering r = bin.right();
                if (!BitOperations.Int.hasMatch$(BitOperations$Int$.MODULE$, ordinal, p, m4)) {
                    return TreeSeqMap$Ordering$.MODULE$.scala$collection$immutable$TreeSeqMap$Ordering$$join(ordinal, new Tip<S>(ordinal, value), p, this);
                }
                if (BitOperations.Int.zero$(BitOperations$Int$.MODULE$, ordinal, m4)) {
                    return new Bin<S>(p, m4, l.include(ordinal, value), r);
                }
                return new Bin(p, m4, l, r.include(ordinal, value));
            }
            throw new MatchError(this);
        }

        public final <S> Ordering<S> append(int ordinal, S value) {
            if (((Object)TreeSeqMap$Ordering$Zero$.MODULE$).equals(this)) {
                return new Tip<S>(ordinal, value);
            }
            if (this instanceof Tip) {
                int o = ((Tip)this).ord();
                if (ordinal == o) {
                    return new Tip<S>(ordinal, value);
                }
                return TreeSeqMap$Ordering$.MODULE$.scala$collection$immutable$TreeSeqMap$Ordering$$join(ordinal, new Tip<S>(ordinal, value), o, this);
            }
            if (this instanceof Bin) {
                Bin bin = (Bin)this;
                int p = bin.prefix();
                int m4 = bin.mask();
                Ordering l = bin.left();
                Ordering r = bin.right();
                if (!BitOperations.Int.hasMatch$(BitOperations$Int$.MODULE$, ordinal, p, m4)) {
                    return TreeSeqMap$Ordering$.MODULE$.scala$collection$immutable$TreeSeqMap$Ordering$$join(ordinal, new Tip<S>(ordinal, value), p, this);
                }
                if (BitOperations.Int.zero$(BitOperations$Int$.MODULE$, ordinal, m4)) {
                    throw new IllegalArgumentException(new java.lang.StringBuilder(82).append("Append called with ordinal out of range: ").append(ordinal).append(" is not greater than current max ordinal ").append(this.ordinal()).toString());
                }
                return new Bin(p, m4, l, r.append(ordinal, value));
            }
            throw new MatchError(this);
        }

        public final <S> Ordering<S> appendInPlace(int ordinal, S value) {
            return this.appendInPlace1(null, ordinal, value);
        }

        public final <S> Ordering<S> appendInPlace1(Bin<S> parent, int ordinal, S value) {
            boolean bl = false;
            Tip tip = null;
            if (((Object)TreeSeqMap$Ordering$Zero$.MODULE$).equals(this)) {
                return new Tip<S>(ordinal, value);
            }
            if (this instanceof Tip) {
                bl = true;
                tip = (Tip)this;
                int o = tip.ord();
                if (o >= ordinal) {
                    throw new IllegalArgumentException(new java.lang.StringBuilder(82).append("Append called with ordinal out of range: ").append(o).append(" is not greater than current max ordinal ").append(this.ordinal()).toString());
                }
            }
            if (bl) {
                int o = tip.ord();
                if (parent == null) {
                    return TreeSeqMap$Ordering$.MODULE$.scala$collection$immutable$TreeSeqMap$Ordering$$join(ordinal, new Tip<S>(ordinal, value), o, this);
                }
            }
            if (bl) {
                int o = tip.ord();
                parent.right_$eq(TreeSeqMap$Ordering$.MODULE$.scala$collection$immutable$TreeSeqMap$Ordering$$join(ordinal, new Tip<S>(ordinal, value), o, this));
                return parent;
            }
            if (this instanceof Bin) {
                Bin bin = (Bin)this;
                int p = bin.prefix();
                int m4 = bin.mask();
                Ordering r = bin.right();
                if (!BitOperations.Int.hasMatch$(BitOperations$Int$.MODULE$, ordinal, p, m4)) {
                    Ordering<S> b2 = TreeSeqMap$Ordering$.MODULE$.scala$collection$immutable$TreeSeqMap$Ordering$$join(ordinal, new Tip<S>(ordinal, value), p, this);
                    if (parent != null) {
                        parent.right_$eq(b2);
                        return parent;
                    }
                    return b2;
                }
                if (BitOperations.Int.zero$(BitOperations$Int$.MODULE$, ordinal, m4)) {
                    throw new IllegalArgumentException(new java.lang.StringBuilder(82).append("Append called with ordinal out of range: ").append(ordinal).append(" is not greater than current max ordinal ").append(this.ordinal()).toString());
                }
                r.appendInPlace1(bin, ordinal, value);
                return this;
            }
            throw new MatchError(this);
        }

        public final Ordering<T> exclude(int ordinal) {
            if (((Object)TreeSeqMap$Ordering$Zero$.MODULE$).equals(this)) {
                return TreeSeqMap$Ordering$Zero$.MODULE$;
            }
            if (this instanceof Tip) {
                int o = ((Tip)this).ord();
                if (ordinal == o) {
                    return TreeSeqMap$Ordering$Zero$.MODULE$;
                }
                return this;
            }
            if (this instanceof Bin) {
                Bin bin = (Bin)this;
                int p = bin.prefix();
                int m4 = bin.mask();
                Ordering l = bin.left();
                Ordering r = bin.right();
                if (!BitOperations.Int.hasMatch$(BitOperations$Int$.MODULE$, ordinal, p, m4)) {
                    return this;
                }
                if (BitOperations.Int.zero$(BitOperations$Int$.MODULE$, ordinal, m4)) {
                    return TreeSeqMap$Ordering$.MODULE$.scala$collection$immutable$TreeSeqMap$Ordering$$bin(p, m4, l.exclude(ordinal), r);
                }
                return TreeSeqMap$Ordering$.MODULE$.scala$collection$immutable$TreeSeqMap$Ordering$$bin(p, m4, l, r.exclude(ordinal));
            }
            throw new MatchError(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public final Tuple2<Ordering<T>, Ordering<T>> splitAt(int n) {
            Ordering ordering;
            TreeSeqMap$Ordering$Zero$ create_e = TreeSeqMap$Ordering$Zero$.MODULE$;
            Object var17_2 = null;
            ObjectRef<TreeSeqMap$Ordering$Zero$> rear = new ObjectRef<TreeSeqMap$Ordering$Zero$>(create_e);
            IntRef i = new IntRef(n);
            Function2<Object, Object, Option> & Serializable modifyOrRemove_f = (Function2<Object, Object, Option> & Serializable)(o, v) -> Ordering.$anonfun$splitAt$1(i, rear, BoxesRunTime.unboxToInt(o), v);
            if (((Object)TreeSeqMap$Ordering$Zero$.MODULE$).equals(this)) {
                ordering = TreeSeqMap$Ordering$Zero$.MODULE$;
            } else if (this instanceof Tip) {
                Option option;
                Tip tip = (Tip)this;
                int modifyOrRemove_key = tip.ord();
                Object modifyOrRemove_value = tip.value();
                --i.elem;
                if (i.elem >= 0) {
                    option = new Some(modifyOrRemove_value);
                } else {
                    Ordering ordering2 = (Ordering)rear.elem;
                    if (ordering2 == null) {
                        throw null;
                    }
                    rear.elem = ordering2.appendInPlace1(null, modifyOrRemove_key, modifyOrRemove_value);
                    option = None$.MODULE$;
                }
                Option option2 = option;
                if (None$.MODULE$.equals(option2)) {
                    ordering = TreeSeqMap$Ordering$Zero$.MODULE$;
                } else {
                    if (!(option2 instanceof Some)) throw new MatchError(option2);
                    Object modifyOrRemove_value2 = ((Some)option2).value();
                    ordering = modifyOrRemove_value == modifyOrRemove_value2 ? this : new Tip(modifyOrRemove_key, modifyOrRemove_value2);
                }
            } else {
                if (!(this instanceof Bin)) throw new MatchError(this);
                Bin bin = (Bin)this;
                int modifyOrRemove_prefix = bin.prefix();
                int modifyOrRemove_mask = bin.mask();
                Ordering modifyOrRemove_left = bin.left();
                Ordering modifyOrRemove_right = bin.right();
                Ordering modifyOrRemove_l = modifyOrRemove_left.modifyOrRemove(modifyOrRemove_f);
                Ordering modifyOrRemove_r = modifyOrRemove_right.modifyOrRemove(modifyOrRemove_f);
                ordering = modifyOrRemove_left == modifyOrRemove_l && modifyOrRemove_right == modifyOrRemove_r ? this : TreeSeqMap$Ordering$.MODULE$.scala$collection$immutable$TreeSeqMap$Ordering$$bin(modifyOrRemove_prefix, modifyOrRemove_mask, modifyOrRemove_l, modifyOrRemove_r);
            }
            Object var4_5 = null;
            return new Tuple2<Ordering<T>, Ordering<T>>(ordering, (Ordering)rear.elem);
        }

        public final <S> Ordering<S> modifyOrRemove(Function2<Object, T, Option<S>> f) {
            if (((Object)TreeSeqMap$Ordering$Zero$.MODULE$).equals(this)) {
                return TreeSeqMap$Ordering$Zero$.MODULE$;
            }
            if (this instanceof Tip) {
                Tip tip = (Tip)this;
                int key = tip.ord();
                Object value = tip.value();
                Option<S> option = f.apply(key, value);
                if (None$.MODULE$.equals(option)) {
                    return TreeSeqMap$Ordering$Zero$.MODULE$;
                }
                if (option instanceof Some) {
                    Object value2 = ((Some)option).value();
                    if (value == value2) {
                        return this;
                    }
                    return new Tip(key, value2);
                }
                throw new MatchError(option);
            }
            if (this instanceof Bin) {
                Bin bin = (Bin)this;
                int prefix = bin.prefix();
                int mask = bin.mask();
                Ordering<T> left = bin.left();
                Ordering<T> right = bin.right();
                Ordering<S> l = left.modifyOrRemove(f);
                Ordering<S> r = right.modifyOrRemove(f);
                if (left == l && right == r) {
                    return this;
                }
                return TreeSeqMap$Ordering$.MODULE$.scala$collection$immutable$TreeSeqMap$Ordering$$bin(prefix, mask, l, r);
            }
            throw new MatchError(this);
        }

        public static final /* synthetic */ Option $anonfun$splitAt$1(IntRef i$1, ObjectRef rear$1, int o, Object v) {
            --i$1.elem;
            if (i$1.elem >= 0) {
                return new Some<Object>(v);
            }
            Ordering ordering = (Ordering)rear$1.elem;
            if (ordering == null) {
                throw null;
            }
            rear$1.elem = ordering.appendInPlace1(null, o, v);
            return None$.MODULE$;
        }

        public static final class Bin<T>
        extends Ordering<T>
        implements Product,
        Serializable {
            private final int prefix;
            private final int mask;
            private final Ordering<T> left;
            private Ordering<T> right;

            @Override
            public scala.collection.Iterator<String> productElementNames() {
                return Product.productElementNames$(this);
            }

            public int prefix() {
                return this.prefix;
            }

            public int mask() {
                return this.mask;
            }

            public Ordering<T> left() {
                return this.left;
            }

            public Ordering<T> right() {
                return this.right;
            }

            public void right_$eq(Ordering<T> x$1) {
                this.right = x$1;
            }

            public <S> Ordering<S> bin(Ordering<S> left, Ordering<S> right) {
                if (this.left() == left && this.right() == right) {
                    return this;
                }
                return new Bin<S>(this.prefix(), this.mask(), left, right);
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void format(StringBuilder sb, String prefix, String subPrefix) {
                void $plus$plus$eq_s;
                int toBinaryString_i = this.prefix();
                int toBinaryString_i2 = this.mask();
                String string = new java.lang.StringBuilder(7).append(prefix).append("Bin(").append(new java.lang.StringBuilder(1).append(toBinaryString_i).append("/").append(Integer.toBinaryString(toBinaryString_i)).toString()).append(":").append(new java.lang.StringBuilder(1).append(toBinaryString_i2).append("/").append(Integer.toBinaryString(toBinaryString_i2)).toString()).append(")\n").toString();
                if (sb == null) {
                    throw null;
                }
                sb.addAll((String)$plus$plus$eq_s);
                string = null;
                this.left().format(sb, new java.lang.StringBuilder(4).append(subPrefix).append("\u251c\u2500\u2500 ").toString(), new java.lang.StringBuilder(4).append(subPrefix).append("\u2502   ").toString());
                this.right().format(sb, new java.lang.StringBuilder(4).append(subPrefix).append("\u2514\u2500\u2500 ").toString(), new java.lang.StringBuilder(4).append(subPrefix).append("    ").toString());
            }

            public <T> Bin<T> copy(int prefix, int mask, Ordering<T> left, Ordering<T> right) {
                return new Bin<T>(prefix, mask, left, right);
            }

            public <T> int copy$default$1() {
                return this.prefix();
            }

            public <T> int copy$default$2() {
                return this.mask();
            }

            public <T> Ordering<T> copy$default$3() {
                return this.left();
            }

            public <T> Ordering<T> copy$default$4() {
                return this.right();
            }

            @Override
            public String productPrefix() {
                return "Bin";
            }

            @Override
            public int productArity() {
                return 4;
            }

            @Override
            public Object productElement(int x$1) {
                switch (x$1) {
                    case 0: {
                        return this.prefix();
                    }
                    case 1: {
                        return this.mask();
                    }
                    case 2: {
                        return this.left();
                    }
                    case 3: {
                        return this.right();
                    }
                }
                return Statics.ioobe(x$1);
            }

            @Override
            public scala.collection.Iterator<Object> productIterator() {
                return new AbstractIterator<T>(this){
                    private int c;
                    private final int cmax;
                    private final Product x$2;

                    public boolean hasNext() {
                        return this.c < this.cmax;
                    }

                    public T next() {
                        Object result = this.x$2.productElement(this.c);
                        ++this.c;
                        return (T)result;
                    }
                    {
                        this.x$2 = x$2;
                        this.c = 0;
                        this.cmax = x$2.productArity();
                    }
                };
            }

            @Override
            public boolean canEqual(Object x$1) {
                return x$1 instanceof Bin;
            }

            @Override
            public String productElementName(int x$1) {
                switch (x$1) {
                    case 0: {
                        return "prefix";
                    }
                    case 1: {
                        return "mask";
                    }
                    case 2: {
                        return "left";
                    }
                    case 3: {
                        return "right";
                    }
                }
                return (String)Statics.ioobe(x$1);
            }

            public int hashCode() {
                int n = -889275714;
                n = Statics.mix(n, "Bin".hashCode());
                n = Statics.mix(n, this.prefix());
                n = Statics.mix(n, this.mask());
                n = Statics.mix(n, Statics.anyHash(this.left()));
                n = Statics.mix(n, Statics.anyHash(this.right()));
                int finalizeHash_length = 4;
                return Statics.avalanche(n ^ finalizeHash_length);
            }

            @Override
            public boolean equals(Object x$1) {
                block3: {
                    block2: {
                        if (this == x$1) break block2;
                        if (!(x$1 instanceof Bin)) break block3;
                        Bin bin = (Bin)x$1;
                        if (this.prefix() != bin.prefix() || this.mask() != bin.mask()) break block3;
                        Ordering<T> ordering = this.left();
                        Ordering<T> ordering2 = bin.left();
                        if (ordering != null ? !ordering.equals(ordering2) : ordering2 != null) break block3;
                        Ordering<T> ordering3 = this.right();
                        Ordering<T> ordering4 = bin.right();
                        if (ordering3 != null ? !ordering3.equals(ordering4) : ordering4 != null) break block3;
                    }
                    return true;
                }
                return false;
            }

            public Bin(int prefix, int mask, Ordering<T> left, Ordering<T> right) {
                this.prefix = prefix;
                this.mask = mask;
                this.left = left;
                this.right = right;
            }
        }

        public static final class Iterator<V> {
            private int index = 0;
            private final Object[] buffer = new Object[33];

            private Ordering<V> pop() {
                --this.index;
                return (Ordering)this.buffer[this.index];
            }

            private <V2> void push(Ordering<V2> x) {
                this.buffer[this.index] = x;
                ++this.index;
            }

            public boolean hasNext() {
                return this.index != 0;
            }

            public V next() {
                Ordering<V> ordering;
                while (true) {
                    boolean bl = false;
                    Bin bin = null;
                    ordering = this.pop();
                    if (ordering instanceof Bin) {
                        bl = true;
                        bin = (Bin)ordering;
                        Ordering ordering2 = bin.left();
                        Ordering right = bin.right();
                        if (ordering2 instanceof Tip) {
                            Object v = ((Tip)ordering2).value();
                            this.push(right);
                            return (V)v;
                        }
                    }
                    if (!bl) break;
                    Ordering left = bin.left();
                    Ordering right = bin.right();
                    this.push(right);
                    this.push(left);
                }
                if (ordering instanceof Tip) {
                    return (V)((Tip)ordering).value();
                }
                if (((Object)TreeSeqMap$Ordering$Zero$.MODULE$).equals(ordering)) {
                    throw new IllegalStateException("empty subtree not allowed");
                }
                throw new MatchError(ordering);
            }

            public Iterator(Ordering<V> it) {
                Ordering<V> ordering = it;
                TreeSeqMap$Ordering$Zero$ treeSeqMap$Ordering$Zero$ = TreeSeqMap$Ordering$Zero$.MODULE$;
                if (ordering == null || !ordering.equals(treeSeqMap$Ordering$Zero$)) {
                    this.push(it);
                }
            }
        }

        public static final class Tip<T>
        extends Ordering<T>
        implements Product,
        Serializable {
            private final int ord;
            private final T value;

            @Override
            public scala.collection.Iterator<String> productElementNames() {
                return Product.productElementNames$(this);
            }

            public int ord() {
                return this.ord;
            }

            public T value() {
                return this.value;
            }

            public <S> Tip<S> withValue(S s2) {
                if (s2 == this.value()) {
                    return this;
                }
                return new Tip<S>(this.ord(), s2);
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void format(StringBuilder sb, String prefix, String subPrefix) {
                void $plus$plus$eq_s;
                int toBinaryString_i = this.ord();
                String string = new java.lang.StringBuilder(10).append(prefix).append("Tip(").append(new java.lang.StringBuilder(1).append(toBinaryString_i).append("/").append(Integer.toBinaryString(toBinaryString_i)).toString()).append(" -> ").append(this.value()).append(")\n").toString();
                if (sb == null) {
                    throw null;
                }
                sb.addAll((String)$plus$plus$eq_s);
            }

            public <T> Tip<T> copy(int ord, T value) {
                return new Tip<T>(ord, value);
            }

            public <T> int copy$default$1() {
                return this.ord();
            }

            public <T> T copy$default$2() {
                return this.value();
            }

            @Override
            public String productPrefix() {
                return "Tip";
            }

            @Override
            public int productArity() {
                return 2;
            }

            @Override
            public Object productElement(int x$1) {
                switch (x$1) {
                    case 0: {
                        return this.ord();
                    }
                    case 1: {
                        return this.value();
                    }
                }
                return Statics.ioobe(x$1);
            }

            @Override
            public scala.collection.Iterator<Object> productIterator() {
                return new /* invalid duplicate definition of identical inner class */;
            }

            @Override
            public boolean canEqual(Object x$1) {
                return x$1 instanceof Tip;
            }

            @Override
            public String productElementName(int x$1) {
                switch (x$1) {
                    case 0: {
                        return "ord";
                    }
                    case 1: {
                        return "value";
                    }
                }
                return (String)Statics.ioobe(x$1);
            }

            public int hashCode() {
                int n = -889275714;
                n = Statics.mix(n, "Tip".hashCode());
                n = Statics.mix(n, this.ord());
                n = Statics.mix(n, Statics.anyHash(this.value()));
                int finalizeHash_length = 2;
                return Statics.avalanche(n ^ finalizeHash_length);
            }

            @Override
            public boolean equals(Object x$1) {
                block3: {
                    block2: {
                        if (this == x$1) break block2;
                        if (!(x$1 instanceof Tip)) break block3;
                        Tip tip = (Tip)x$1;
                        if (this.ord() != tip.ord() || !BoxesRunTime.equals(this.value(), tip.value())) break block3;
                    }
                    return true;
                }
                return false;
            }

            public Tip(int ord, T value) {
                this.ord = ord;
                this.value = value;
            }
        }
    }
}

