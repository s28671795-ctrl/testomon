/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.util.Clock;
import akka.util.DoubleLinkedList;
import akka.util.FrequencyList$;
import akka.util.OptionVal;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.collection.Iterator;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Map;
import scala.collection.mutable.Map$;
import scala.concurrent.duration.FiniteDuration;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\r-sAB1c\u0011\u0003!gM\u0002\u0004iE\"\u0005A-\u001b\u0005\u0006a\u0006!\tA\u001d\u0005\u0006g\u0006!\t\u0001\u001e\u0005\n\u0007?\t\u0011\u0013!C\u0001\u0007C9qa!\u000b\u0002\u0011\u0003\u0019YCB\u0004\u0004.\u0005A\taa\f\t\rA4A\u0011AB\u0019\u0011\u0019\u0019h\u0001\"\u0001\u00044!I1\u0011\t\u0004\u0012\u0002\u0013\u000511\t\u0004\u0007\u0003k\ta!a\u000e\t\u0015\u0005m\"B!b\u0001\n\u0003\ti\u0004\u0003\u0006\u0002F)\u0011\t\u0011)A\u0005\u0003\u007fAa\u0001\u001d\u0006\u0005\u0002\u0005\u001d\u0003\"CA*\u0015\u0001\u0007I\u0011AA+\u0011%\tIF\u0003a\u0001\n\u0003\tY\u0006\u0003\u0005\u0002h)\u0001\u000b\u0015BA,\u0011%\tIG\u0003a\u0001\n\u0003\t)\u0006C\u0005\u0002l)\u0001\r\u0011\"\u0001\u0002n!A\u0011\u0011\u000f\u0006!B\u0013\t9\u0006C\u0005\u0002t)\u0011\r\u0011\"\u0001\u0002v!A\u0011\u0011\u001d\u0006!\u0002\u0013\t9H\u0002\u0004\u0002|\u00051\u0011Q\u0010\u0005\u000b\u0003\u00033\"Q1A\u0005\u0002\u0005\r\u0005BCAE-\t\u0005\t\u0015!\u0003\u0002\u0006\"Q\u00111\u0012\f\u0003\u0002\u0003\u0006I!!$\t\rA4B\u0011AAH\u0011%\t9J\u0006a\u0001\n\u0003\ti\u0004C\u0005\u0002\u001aZ\u0001\r\u0011\"\u0001\u0002\u001c\"A\u0011q\u0014\f!B\u0013\ty\u0004C\u0005\u0002(Y\u0001\r\u0011\"\u0001\u0002\"\"I\u00111\u0015\fA\u0002\u0013\u0005\u0011Q\u0015\u0005\t\u0003S3\u0002\u0015)\u0003\u0002\u000e\"I\u00111\u0016\fA\u0002\u0013\u0005\u0011Q\u0016\u0005\n\u0003c3\u0002\u0019!C\u0001\u0003gC\u0001\"a.\u0017A\u0003&\u0011q\u0016\u0005\n\u0003s3\u0002\u0019!C\u0001\u0003[C\u0011\"a/\u0017\u0001\u0004%\t!!0\t\u0011\u0005\u0005g\u0003)Q\u0005\u0003_C\u0011\"a1\u0017\u0001\u0004%\t!!,\t\u0013\u0005\u0015g\u00031A\u0005\u0002\u0005\u001d\u0007\u0002CAf-\u0001\u0006K!a,\t\u0013\u00055g\u00031A\u0005\u0002\u00055\u0006\"CAh-\u0001\u0007I\u0011AAi\u0011!\t)N\u0006Q!\n\u0005=\u0006\"CAl-\u0001\u0007I\u0011AA\u001f\u0011%\tIN\u0006a\u0001\n\u0003\tY\u000e\u0003\u0005\u0002`Z\u0001\u000b\u0015BA \r\u0015A'M\u00013x\u0011!I\bG!A!\u0002\u0013Q\b\u0002C?1\u0005\u0003\u0005\u000b\u0011\u0002@\t\rA\u0004D\u0011AA\u0005\u0011%\t9\u0003\rb\u0001\n\u0013\tI\u0003\u0003\u0005\u0002*B\u0002\u000b\u0011BA\u0016\u0011%\t\u0019\u000f\rb\u0001\n\u0013\t)\u000f\u0003\u0005\u0002lB\u0002\u000b\u0011BAt\u0011%\ti\u000f\rb\u0001\n\u0013\ty\u000f\u0003\u0005\u0003\u0002A\u0002\u000b\u0011BAy\u0011%\u0011\u0019\u0001\ra\u0001\n\u0013\ti\u0004C\u0005\u0003\u0006A\u0002\r\u0011\"\u0003\u0003\b!A!1\u0002\u0019!B\u0013\ty\u0004C\u0004\u0003\u000eA\"\tAa\u0004\t\u000f\t]\u0001\u0007\"\u0001\u0003\u001a!9!Q\u0004\u0019\u0005\u0002\t}\u0001b\u0002B\u0012a\u0011\u0005!Q\u0005\u0005\b\u0005S\u0001D\u0011\u0001B\u0016\u0011\u001d\u0011y\u0003\rC\u0001\u0005WAqA!\r1\t\u0003\u0011\u0019\u0004C\u0004\u0003NA\"\tAa\r\t\u000f\t=\u0003\u0007\"\u0001\u0003R!9!q\n\u0019\u0005\u0002\t\r\u0004\"\u0003B6aE\u0005I\u0011\u0001B7\u0011\u001d\u0011y\u0005\rC\u0001\u0005\u0007CqA!\"1\t\u0003\u00119\tC\u0004\u0003\u0006B\"\tAa#\t\u0013\tE\u0005'%A\u0005\u0002\t5\u0004b\u0002BCa\u0011\u0005!1\u0011\u0005\b\u0005'\u0003D\u0011\u0001B\u001a\u0011\u001d\u0011)\n\rC\u0001\u0005gAqAa&1\t\u0003\u0011I\nC\u0005\u0003\u001eB\n\n\u0011\"\u0001\u0003n!9!q\u0014\u0019\u0005\u0002\t\u0005\u0006\"\u0003BSaE\u0005I\u0011\u0001B7\u0011\u001d\u00119\u000b\rC\u0001\u0005SCqA!01\t\u0003\u0011y\fC\u0004\u0003DB\"IA!2\t\u000f\t%\u0007\u0007\"\u0003\u0003L\"9!q\u001a\u0019\u0005\n\tE\u0007b\u0002Bka\u0011%!q\u001b\u0005\b\u0005;\u0004D\u0011\u0002Bp\u0011\u001d\u0011\u0019\u000f\rC\u0005\u0005KDqA!;1\t\u0013\u0011Y\u000fC\u0004\u0003tB\"IA!>\t\u000f\te\b\u0007\"\u0003\u0003|\"9!q \u0019\u0005\n\r\u0005\u0001bBB\u0003a\u0011%1q\u0001\u0005\b\u0007\u0017\u0001D\u0011BB\u0004\u000351%/Z9vK:\u001c\u0017\u0010T5ti*\u00111\rZ\u0001\u0005kRLGNC\u0001f\u0003\u0011\t7n[1\u0011\u0005\u001d\fQ\"\u00012\u0003\u001b\u0019\u0013X-];f]\u000eLH*[:u'\t\t!\u000e\u0005\u0002l]6\tANC\u0001n\u0003\u0015\u00198-\u00197b\u0013\tyGN\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\ta-A\u0003f[B$\u00180F\u0002v\u00077!2A^B\u000f!\u00119\u0007g!\u0007\u0016\u0007a\f\tb\u0005\u00021U\u0006aA-\u001f8b[&\u001c\u0017iZ5oOB\u00111n_\u0005\u0003y2\u0014qAQ8pY\u0016\fg.A\u0003dY>\u001c7\u000e\u0005\u0003h\u007f\u0006\r\u0011bAA\u0001E\nIq\n\u001d;j_:4\u0016\r\u001c\t\u0004O\u0006\u0015\u0011bAA\u0004E\n)1\t\\8dWR1\u00111BA\u0012\u0003K\u0001Ba\u001a\u0019\u0002\u000eA!\u0011qBA\t\u0019\u0001!q!a\u00051\u0005\u0004\t)BA\u0001B#\u0011\t9\"!\b\u0011\u0007-\fI\"C\u0002\u0002\u001c1\u0014qAT8uQ&tw\rE\u0002l\u0003?I1!!\tm\u0005\r\te.\u001f\u0005\u0006sN\u0002\rA\u001f\u0005\u0006{N\u0002\rA`\u0001\nMJ,\u0017/^3oGf,\"!a\u000b\u0011\u000b\u001d\fi#!\r\n\u0007\u0005=\"M\u0001\tE_V\u0014G.\u001a'j].,G\rT5tiB)\u00111\u0007\u0006\u0002\u000e9\u0011q\r\u0001\u0002\u000e\rJ,\u0017/^3oGftu\u000eZ3\u0016\t\u0005e\u0012qJ\n\u0003\u0015)\f\u0001\u0002\u001d:j_JLG/_\u000b\u0003\u0003\u007f\u00012a[A!\u0013\r\t\u0019\u0005\u001c\u0002\u0005\u0019>tw-A\u0005qe&|'/\u001b;zAQ!\u0011\u0011JA)!\u0015\tYECA'\u001b\u0005\t\u0001\u0003BA\b\u0003\u001f\"q!a\u0005\u000b\u0005\u0004\t)\u0002C\u0004\u0002<5\u0001\r!a\u0010\u0002\u00191,7o\u001d$sKF,XM\u001c;\u0016\u0005\u0005]\u0003\u0003B4\u0000\u0003\u0013\n\u0001\u0003\\3tg\u001a\u0013X-];f]R|F%Z9\u0015\t\u0005u\u00131\r\t\u0004W\u0006}\u0013bAA1Y\n!QK\\5u\u0011%\t)gDA\u0001\u0002\u0004\t9&A\u0002yIE\nQ\u0002\\3tg\u001a\u0013X-];f]R\u0004\u0013\u0001D7pe\u00164%/Z9vK:$\u0018\u0001E7pe\u00164%/Z9vK:$x\fJ3r)\u0011\ti&a\u001c\t\u0013\u0005\u0015$#!AA\u0002\u0005]\u0013!D7pe\u00164%/Z9vK:$\b%A\u0003o_\u0012,7/\u0006\u0002\u0002xA)q-!\f\u0002zA)\u00111\n\f\u0002N\t!aj\u001c3f+\u0011\ty(a\"\u0014\u0005YQ\u0017!\u0002<bYV,WCAAC!\u0011\ty!a\"\u0005\u000f\u0005MaC1\u0001\u0002\u0016\u00051a/\u00197vK\u0002\n\u0001#\u001b8ji&\fGN\u0012:fcV,gnY=\u0011\u000b\u0005-#\"!\"\u0015\r\u0005E\u00151SAK!\u0015\tYEFAC\u0011\u001d\t\tI\u0007a\u0001\u0003\u000bCq!a#\u001b\u0001\u0004\ti)\u0001\u0005bG\u000e,7o]3t\u00031\t7mY3tg\u0016\u001cx\fJ3r)\u0011\ti&!(\t\u0013\u0005\u0015D$!AA\u0002\u0005}\u0012!C1dG\u0016\u001c8/Z:!+\t\ti)A\u0007ge\u0016\fX/\u001a8ds~#S-\u001d\u000b\u0005\u0003;\n9\u000bC\u0005\u0002f}\t\t\u00111\u0001\u0002\u000e\u0006QaM]3rk\u0016t7-\u001f\u0011\u0002\u00151,7o\u001d*fG\u0016tG/\u0006\u0002\u00020B!qm`AI\u00039aWm]:SK\u000e,g\u000e^0%KF$B!!\u0018\u00026\"I\u0011Q\r\u0012\u0002\u0002\u0003\u0007\u0011qV\u0001\fY\u0016\u001c8OU3dK:$\b%\u0001\u0006n_J,'+Z2f]R\fa\"\\8sKJ+7-\u001a8u?\u0012*\u0017\u000f\u0006\u0003\u0002^\u0005}\u0006\"CA3K\u0005\u0005\t\u0019AAX\u0003-iwN]3SK\u000e,g\u000e\u001e\u0011\u0002#=4XM]1mY2+7o\u001d*fG\u0016tG/A\u000bpm\u0016\u0014\u0018\r\u001c7MKN\u001c(+Z2f]R|F%Z9\u0015\t\u0005u\u0013\u0011\u001a\u0005\n\u0003KB\u0013\u0011!a\u0001\u0003_\u000b!c\u001c<fe\u0006dG\u000eT3tgJ+7-\u001a8uA\u0005\trN^3sC2dWj\u001c:f%\u0016\u001cWM\u001c;\u0002+=4XM]1mY6{'/\u001a*fG\u0016tGo\u0018\u0013fcR!\u0011QLAj\u0011%\t)gKA\u0001\u0002\u0004\ty+\u0001\npm\u0016\u0014\u0018\r\u001c7N_J,'+Z2f]R\u0004\u0013!\u0003;j[\u0016\u001cH/Y7q\u00035!\u0018.\\3ti\u0006l\u0007o\u0018\u0013fcR!\u0011QLAo\u0011%\t)GLA\u0001\u0002\u0004\ty$\u0001\u0006uS6,7\u000f^1na\u0002\naA\\8eKN\u0004\u0013AD8wKJ\fG\u000e\u001c*fG\u0016t7-_\u000b\u0003\u0003O\u0004RaZA\u0017\u0003S\u0004R!a\r\u0017\u0003\u001b\tqb\u001c<fe\u0006dGNU3dK:\u001c\u0017\u0010I\u0001\u000bY>|7.\u001e9O_\u0012,WCAAy!!\t\u00190!@\u0002\u000e\u0005%XBAA{\u0015\u0011\t90!?\u0002\u000f5,H/\u00192mK*\u0019\u00111 7\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002\u0000\u0006U(aA'ba\u0006YAn\\8lkBtu\u000eZ3!\u0003\r\tw-Z\u0001\bC\u001e,w\fJ3r)\u0011\tiF!\u0003\t\u0013\u0005\u00154(!AA\u0002\u0005}\u0012\u0001B1hK\u0002\nAa]5{KV\u0011!\u0011\u0003\t\u0004W\nM\u0011b\u0001B\u000bY\n\u0019\u0011J\u001c;\u0002\rU\u0004H-\u0019;f)\u0011\tYAa\u0007\t\u000f\u0005\u0005e\b1\u0001\u0002\u000e\u00051!/Z7pm\u0016$B!a\u0003\u0003\"!9\u0011\u0011Q A\u0002\u00055\u0011\u0001C2p]R\f\u0017N\\:\u0015\u0007i\u00149\u0003C\u0004\u0002\u0002\u0002\u0003\r!!\u0004\u0002\u001b1,\u0017m\u001d;Ge\u0016\fX/\u001a8u+\t\u0011i\u0003\u0005\u0003h\u007f\u00065\u0011\u0001D7pgR4%/Z9vK:$\u0018a\u00057fCN$Hk\\'pgR4%/Z9vK:$XC\u0001B\u001b!\u0019\u00119Da\u0012\u0002\u000e9!!\u0011\bB\"\u001d\u0011\u0011YD!\u0011\u000e\u0005\tu\"b\u0001B c\u00061AH]8pizJ\u0011!\\\u0005\u0004\u0005\u000bb\u0017a\u00029bG.\fw-Z\u0005\u0005\u0005\u0013\u0012YE\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0015\r\u0011)\u0005\\\u0001\u0014[>\u001cH\u000fV8MK\u0006\u001cHO\u0012:fcV,g\u000e^\u0001\u0014e\u0016lwN^3MK\u0006\u001cHO\u0012:fcV,g\u000e\u001e\u000b\u0005\u0005'\u0012y\u0006\u0005\u0004\u0003V\tm\u0013QB\u0007\u0003\u0005/RAA!\u0017\u0002z\u0006I\u0011.\\7vi\u0006\u0014G.Z\u0005\u0005\u0005;\u00129FA\u0002TKFDqA!\u0019F\u0001\u0004\u0011\t\"A\u0001o)\u0019\u0011\u0019F!\u001a\u0003h!I!\u0011\r$\u0011\u0002\u0003\u0007!\u0011\u0003\u0005\b\u0005S2\u0005\u0019\u0001B\u0017\u0003\u0011\u00198.\u001b9\u0002;I,Wn\u001c<f\u0019\u0016\f7\u000f\u001e$sKF,XM\u001c;%I\u00164\u0017-\u001e7uIE*\"Aa\u001c+\t\tE!\u0011O\u0016\u0003\u0005g\u0002BA!\u001e\u0003\u00005\u0011!q\u000f\u0006\u0005\u0005s\u0012Y(A\u0005v]\u000eDWmY6fI*\u0019!Q\u00107\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0003\u0002\n]$!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dKR\u0011!1K\u0001\u0013e\u0016lwN^3N_N$hI]3rk\u0016tG\u000f\u0006\u0003\u0003T\t%\u0005b\u0002B1\u0013\u0002\u0007!\u0011\u0003\u000b\u0007\u0005'\u0012iIa$\t\u0013\t\u0005$\n%AA\u0002\tE\u0001b\u0002B5\u0015\u0002\u0007!QF\u0001\u001de\u0016lwN^3N_N$hI]3rk\u0016tG\u000f\n3fM\u0006,H\u000e\u001e\u00132\u0003ayg/\u001a:bY2dU-Y:u)>lun\u001d;SK\u000e,g\u000e^\u0001\u0019_Z,'/\u00197m\u001b>\u001cH\u000fV8MK\u0006\u001cHOU3dK:$\u0018\u0001\u0007:f[>4Xm\u0014<fe\u0006dG\u000eT3bgR\u0014VmY3oiR!!1\u000bBN\u0011%\u0011\tg\u0014I\u0001\u0002\u0004\u0011\t\"\u0001\u0012sK6|g/Z(wKJ\fG\u000e\u001c'fCN$(+Z2f]R$C-\u001a4bk2$H%M\u0001\u0018e\u0016lwN^3Pm\u0016\u0014\u0018\r\u001c7N_N$(+Z2f]R$BAa\u0015\u0003$\"I!\u0011M)\u0011\u0002\u0003\u0007!\u0011C\u0001\"e\u0016lwN^3Pm\u0016\u0014\u0018\r\u001c7N_N$(+Z2f]R$C-\u001a4bk2$H%M\u0001 e\u0016lwN^3Pm\u0016\u0014\u0018\r\u001c7MK\u0006\u001cHOU3dK:$x*\u001e;tS\u0012,G\u0003\u0002B*\u0005WCqA!,T\u0001\u0004\u0011y+\u0001\u0005ekJ\fG/[8o!\u0011\u0011\tL!/\u000e\u0005\tM&\u0002\u0002BW\u0005kS1Aa.m\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0005\u0005w\u0013\u0019L\u0001\bGS:LG/\u001a#ve\u0006$\u0018n\u001c8\u0002;I,Wn\u001c<f\u001fZ,'/\u00197m\u001b>\u001cHOU3dK:$x+\u001b;iS:$BAa\u0015\u0003B\"9!Q\u0016+A\u0002\t=\u0016aE1eI&s\u0017\u000e^5bY\u001a\u0013X-];f]\u000eLH\u0003BAu\u0005\u000fDq!!!V\u0001\u0004\ti!\u0001\nbI\u0012\f5\u000fT3bgR4%/Z9vK:$H\u0003BAu\u0005\u001bDq!!!W\u0001\u0004\ti!A\u000ebI\u0012Le.\u001b;jC2\fEM[;ti\u0016$gI]3rk\u0016t7-\u001f\u000b\u0005\u0003S\u0014\u0019\u000eC\u0004\u0002\u0002^\u0003\r!!\u0004\u0002#%t7M]3bg\u00164%/Z9vK:\u001c\u0017\u0010\u0006\u0003\u0002^\te\u0007b\u0002Bn1\u0002\u0007\u0011\u0011^\u0001\u0005]>$W-A\rj]\u000e\u0014X-Y:f\u0003\u0012TWo\u001d;fI\u001a\u0013X-];f]\u000eLH\u0003BA/\u0005CDqAa7Z\u0001\u0004\tI/A\fj]\u000e\u0014X-Y:f)>tU\r\u001f;Ge\u0016\fX/\u001a8dsR!\u0011Q\fBt\u0011\u001d\u0011YN\u0017a\u0001\u0003S\fa\"\u00193e)>4%/Z9vK:\u001c\u0017\u0010\u0006\u0004\u0002^\t5(q\u001e\u0005\b\u00057\\\u0006\u0019AAu\u0011\u001d\u0011\tp\u0017a\u0001\u0003c\tQB\u001a:fcV,gnY=O_\u0012,\u0017a\u0005:f[>4XM\u0012:p[\u001a\u0013X-];f]\u000eLH\u0003BA/\u0005oDqAa7]\u0001\u0004\tI/\u0001\u0006sK6|g/\u001a(pI\u0016$B!!\u0004\u0003~\"9!1\\/A\u0002\u0005%\u0018a\u0006:f[>4X\rT3bgR4%/Z9vK:$hj\u001c3f)\u0011\tiaa\u0001\t\u000f\tmg\f1\u0001\u0002j\u0006yam\u001c:xCJ$\u0017\n^3sCR|'/\u0006\u0002\u0004\nA1!q\u0007B$\u0003S\f\u0001CY1dW^\f'\u000fZ%uKJ\fGo\u001c:)\u0007A\u001ay\u0001\u0005\u0003\u0004\u0012\rUQBAB\n\u0015\r\u0011i\bZ\u0005\u0005\u0007/\u0019\u0019BA\u0006J]R,'O\\1m\u0003BL\u0007\u0003BA\b\u00077!q!a\u0005\u0004\u0005\u0004\t)\u0002C\u0004z\u0007A\u0005\t\u0019\u0001>\u0002\u001f\u0015l\u0007\u000f^=%I\u00164\u0017-\u001e7uIE*Baa\t\u0004(U\u00111Q\u0005\u0016\u0004u\nEDaBA\n\t\t\u0007\u0011QC\u0001\u0013o&$\bn\u0014<fe\u0006dGNU3dK:\u001c\u0017\u0010E\u0002\u0002L\u0019\u0011!c^5uQ>3XM]1mYJ+7-\u001a8dsN\u0011aA\u001b\u000b\u0003\u0007W)Ba!\u000e\u0004<Q11qGB\u001f\u0007\u007f\u0001Ba\u001a\u0019\u0004:A!\u0011qBB\u001e\t\u001d\t\u0019\u0002\u0003b\u0001\u0003+Aa! \u0005A\u0002\u0005\r\u0001bB=\t!\u0003\u0005\rA_\u0001\u0010K6\u0004H/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU!11EB#\t\u001d\t\u0019\"\u0003b\u0001\u0003+A3!AB\bQ\r\u00011q\u0002")
@InternalApi
public final class FrequencyList<A> {
    private final boolean dynamicAging;
    private final Clock clock;
    private final DoubleLinkedList<FrequencyNode<A>> frequency;
    private final DoubleLinkedList<Node<A>> overallRecency;
    private final Map<A, Node<A>> lookupNode;
    private long age;

    public static <A> boolean empty$default$1() {
        return FrequencyList$.MODULE$.empty$default$1();
    }

    public static <A> FrequencyList<A> empty(boolean dynamicAging) {
        return FrequencyList$.MODULE$.empty(dynamicAging);
    }

    private DoubleLinkedList<FrequencyNode<A>> frequency() {
        return this.frequency;
    }

    private DoubleLinkedList<Node<A>> overallRecency() {
        return this.overallRecency;
    }

    private Map<A, Node<A>> lookupNode() {
        return this.lookupNode;
    }

    private long age() {
        return this.age;
    }

    private void age_$eq(long x$1) {
        this.age = x$1;
    }

    public int size() {
        return this.lookupNode().size();
    }

    public FrequencyList<A> update(A value) {
        Node<A> node;
        if (this.lookupNode().contains(value)) {
            Node node2 = (Node)this.lookupNode().apply(value);
            this.increaseFrequency(node2);
            if (OptionVal$.MODULE$.isDefined$extension(this.clock)) {
                node2.timestamp_$eq(OptionVal$.MODULE$.get$extension(this.clock).currentTime());
                node = this.overallRecency().moveToBack(node2);
            } else {
                node = BoxedUnit.UNIT;
            }
        } else {
            Node<A> node3 = this.addInitialFrequency(value);
            this.lookupNode().$plus$eq(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(value), node3));
            if (OptionVal$.MODULE$.isDefined$extension(this.clock)) {
                node3.timestamp_$eq(OptionVal$.MODULE$.get$extension(this.clock).currentTime());
                node = this.overallRecency().append(node3);
            } else {
                node = BoxedUnit.UNIT;
            }
        }
        return this;
    }

    public FrequencyList<A> remove(A value) {
        BoxedUnit boxedUnit = this.lookupNode().contains(value) ? this.removeNode((Node)this.lookupNode().apply(value)) : BoxedUnit.UNIT;
        return this;
    }

    public boolean contains(A value) {
        return this.lookupNode().contains(value);
    }

    public A leastFrequent() {
        FrequencyNode<A> frequencyNode = this.frequency().getFirst();
        FrequencyNode<A> frequencyNode2 = OptionVal$Some$.MODULE$.unapply(frequencyNode);
        if (!OptionVal$.MODULE$.isEmpty$extension(frequencyNode2)) {
            FrequencyNode<A> least = OptionVal$.MODULE$.get$extension(frequencyNode2);
            Node<A> node = least.nodes().getFirst();
            Node<A> node2 = OptionVal$Some$.MODULE$.unapply(node);
            if (!OptionVal$.MODULE$.isEmpty$extension(node2)) {
                Node<A> first = OptionVal$.MODULE$.get$extension(node2);
                return OptionVal$Some$.MODULE$.apply(first.value());
            }
            return OptionVal$.MODULE$.none();
        }
        return OptionVal$.MODULE$.none();
    }

    public A mostFrequent() {
        FrequencyNode<A> frequencyNode = this.frequency().getLast();
        FrequencyNode<A> frequencyNode2 = OptionVal$Some$.MODULE$.unapply(frequencyNode);
        if (!OptionVal$.MODULE$.isEmpty$extension(frequencyNode2)) {
            FrequencyNode<A> most = OptionVal$.MODULE$.get$extension(frequencyNode2);
            Node<A> node = most.nodes().getLast();
            Node<A> node2 = OptionVal$Some$.MODULE$.unapply(node);
            if (!OptionVal$.MODULE$.isEmpty$extension(node2)) {
                Node<A> last = OptionVal$.MODULE$.get$extension(node2);
                return OptionVal$Some$.MODULE$.apply(last.value());
            }
            return OptionVal$.MODULE$.none();
        }
        return OptionVal$.MODULE$.none();
    }

    public Iterator<A> leastToMostFrequent() {
        return this.forwardIterator().map((Function1<Node, Object> & Serializable)x$7 -> x$7.value());
    }

    public Iterator<A> mostToLeastFrequent() {
        return this.backwardIterator().map((Function1<Node, Object> & Serializable)x$8 -> x$8.value());
    }

    public Seq<A> removeLeastFrequent(int n) {
        if (n == 1) {
            return this.removeLeastFrequent();
        }
        return this.forwardIterator().take(n).map((Function1<Node, Object> & Serializable)node -> this.removeLeastFrequentNode((Node)node)).toList();
    }

    public Seq<A> removeLeastFrequent(int n, A skip) {
        return this.forwardIterator().filterNot((Function1<Node, Object> & Serializable)node -> BoxesRunTime.boxToBoolean(OptionVal$.MODULE$.contains$extension(skip, node.value()))).take(n).map((Function1<Node, Object> & Serializable)node -> this.removeLeastFrequentNode((Node)node)).toList();
    }

    public Seq<A> removeLeastFrequent() {
        FrequencyNode<A> frequencyNode = this.frequency().getFirst();
        FrequencyNode<A> frequencyNode2 = OptionVal$Some$.MODULE$.unapply(frequencyNode);
        if (!OptionVal$.MODULE$.isEmpty$extension(frequencyNode2)) {
            FrequencyNode<A> least = OptionVal$.MODULE$.get$extension(frequencyNode2);
            Node<A> node = least.nodes().getFirst();
            Node<A> node2 = OptionVal$Some$.MODULE$.unapply(node);
            if (!OptionVal$.MODULE$.isEmpty$extension(node2)) {
                Node<A> first = OptionVal$.MODULE$.get$extension(node2);
                return (Seq)package$.MODULE$.List().apply((Seq)ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.removeLeastFrequentNode(first)}));
            }
            return Nil$.MODULE$;
        }
        return Nil$.MODULE$;
    }

    public int removeLeastFrequent$default$1() {
        return 1;
    }

    public Seq<A> removeMostFrequent(int n) {
        if (n == 1) {
            return this.removeMostFrequent();
        }
        return this.backwardIterator().take(n).map((Function1<Node, Object> & Serializable)node -> this.removeNode((Node)node)).toList();
    }

    public Seq<A> removeMostFrequent(int n, A skip) {
        return this.backwardIterator().filterNot((Function1<Node, Object> & Serializable)node -> BoxesRunTime.boxToBoolean(OptionVal$.MODULE$.contains$extension(skip, node.value()))).take(n).map((Function1<Node, Object> & Serializable)node -> this.removeNode((Node)node)).toList();
    }

    public Seq<A> removeMostFrequent() {
        FrequencyNode<A> frequencyNode = this.frequency().getLast();
        FrequencyNode<A> frequencyNode2 = OptionVal$Some$.MODULE$.unapply(frequencyNode);
        if (!OptionVal$.MODULE$.isEmpty$extension(frequencyNode2)) {
            FrequencyNode<A> most = OptionVal$.MODULE$.get$extension(frequencyNode2);
            Node<A> node = most.nodes().getLast();
            Node<A> node2 = OptionVal$Some$.MODULE$.unapply(node);
            if (!OptionVal$.MODULE$.isEmpty$extension(node2)) {
                Node<A> last = OptionVal$.MODULE$.get$extension(node2);
                return (Seq)package$.MODULE$.List().apply((Seq)ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.removeNode(last)}));
            }
            return Nil$.MODULE$;
        }
        return Nil$.MODULE$;
    }

    public int removeMostFrequent$default$1() {
        return 1;
    }

    public Iterator<A> overallLeastToMostRecent() {
        return this.overallRecency().forwardIterator().map((Function1<Node, Object> & Serializable)x$9 -> x$9.value());
    }

    public Iterator<A> overallMostToLeastRecent() {
        return this.overallRecency().backwardIterator().map((Function1<Node, Object> & Serializable)x$10 -> x$10.value());
    }

    public Seq<A> removeOverallLeastRecent(int n) {
        if (OptionVal$.MODULE$.isEmpty$extension(this.clock)) {
            throw new UnsupportedOperationException("Overall recency is not enabled for this FrequencyList");
        }
        return this.overallRecency().forwardIterator().take(n).map((Function1<Node, Object> & Serializable)node -> this.removeNode((Node)node)).toList();
    }

    public int removeOverallLeastRecent$default$1() {
        return 1;
    }

    public Seq<A> removeOverallMostRecent(int n) {
        if (OptionVal$.MODULE$.isEmpty$extension(this.clock)) {
            throw new UnsupportedOperationException("Overall recency is not enabled for this FrequencyList");
        }
        return this.overallRecency().backwardIterator().take(n).map((Function1<Node, Object> & Serializable)node -> this.removeNode((Node)node)).toList();
    }

    public int removeOverallMostRecent$default$1() {
        return 1;
    }

    public Seq<A> removeOverallLeastRecentOutside(FiniteDuration duration) {
        if (OptionVal$.MODULE$.isEmpty$extension(this.clock)) {
            throw new UnsupportedOperationException("Overall recency is not enabled for this FrequencyList");
        }
        long min2 = OptionVal$.MODULE$.get$extension(this.clock).earlierTime(duration);
        return this.overallRecency().forwardIterator().takeWhile((Function1<Node, Object> & Serializable)x$11 -> BoxesRunTime.boxToBoolean(FrequencyList.$anonfun$removeOverallLeastRecentOutside$1(min2, x$11))).map((Function1<Node, Object> & Serializable)node -> this.removeNode((Node)node)).toList();
    }

    public Seq<A> removeOverallMostRecentWithin(FiniteDuration duration) {
        if (OptionVal$.MODULE$.isEmpty$extension(this.clock)) {
            throw new UnsupportedOperationException("Overall recency is not enabled for this FrequencyList");
        }
        long max = OptionVal$.MODULE$.get$extension(this.clock).earlierTime(duration);
        return this.overallRecency().backwardIterator().takeWhile((Function1<Node, Object> & Serializable)x$12 -> BoxesRunTime.boxToBoolean(FrequencyList.$anonfun$removeOverallMostRecentWithin$1(max, x$12))).map((Function1<Node, Object> & Serializable)node -> this.removeNode((Node)node)).toList();
    }

    private Node<A> addInitialFrequency(A value) {
        if (this.dynamicAging) {
            return this.addInitialAdjustedFrequency(value);
        }
        return this.addAsLeastFrequent(value);
    }

    private Node<A> addAsLeastFrequent(A value) {
        FrequencyNode<A> one = this.frequency().getFirstOrElsePrepend((Function1<FrequencyNode, Object> & Serializable)x$13 -> BoxesRunTime.boxToBoolean(FrequencyList.$anonfun$addAsLeastFrequent$1(x$13)), (Function0<FrequencyNode> & Serializable)() -> new FrequencyNode(1L));
        Node<A> node = new Node<A>(value, one);
        this.addToFrequency(node, one);
        return node;
    }

    private Node<A> addInitialAdjustedFrequency(A value) {
        FrequencyNode<A> first;
        long priority = 1L + this.age();
        FrequencyNode<A> frequencyNode = this.frequency().getFirst();
        FrequencyNode<A> frequencyNode2 = OptionVal$Some$.MODULE$.unapply(frequencyNode);
        FrequencyNode<A> frequencyNode3 = !OptionVal$.MODULE$.isEmpty$extension(frequencyNode2) && (first = OptionVal$.MODULE$.get$extension(frequencyNode2)).priority() < priority ? this.frequency().findNextOrElseInsert(first, (Function1<FrequencyNode, Object> & Serializable)x$14 -> BoxesRunTime.boxToBoolean(FrequencyList.$anonfun$addInitialAdjustedFrequency$1(priority, x$14)), (Function1<FrequencyNode, Object> & Serializable)x$15 -> BoxesRunTime.boxToBoolean(FrequencyList.$anonfun$addInitialAdjustedFrequency$2(priority, x$15)), (Function0<FrequencyNode> & Serializable)() -> new FrequencyNode(priority)) : this.frequency().getFirstOrElsePrepend((Function1<FrequencyNode, Object> & Serializable)x$16 -> BoxesRunTime.boxToBoolean(FrequencyList.$anonfun$addInitialAdjustedFrequency$4(priority, x$16)), (Function0<FrequencyNode> & Serializable)() -> new FrequencyNode(priority));
        Node<A> node = new Node<A>(value, frequencyNode3);
        this.addToFrequency(node, frequencyNode3);
        return node;
    }

    private void increaseFrequency(Node<A> node) {
        if (this.dynamicAging) {
            this.increaseAdjustedFrequency(node);
            return;
        }
        this.increaseToNextFrequency(node);
    }

    private void increaseAdjustedFrequency(Node<A> node) {
        node.accesses_$eq(node.accesses() + 1L);
        long priority = node.accesses() + this.age();
        FrequencyNode<A> frequencyNode = this.frequency().findNextOrElseInsert(node.frequency(), (Function1<FrequencyNode, Object> & Serializable)x$17 -> BoxesRunTime.boxToBoolean(FrequencyList.$anonfun$increaseAdjustedFrequency$1(priority, x$17)), (Function1<FrequencyNode, Object> & Serializable)x$18 -> BoxesRunTime.boxToBoolean(FrequencyList.$anonfun$increaseAdjustedFrequency$2(priority, x$18)), (Function0<FrequencyNode> & Serializable)() -> new FrequencyNode(priority));
        this.removeFromFrequency(node);
        this.addToFrequency(node, frequencyNode);
    }

    private void increaseToNextFrequency(Node<A> node) {
        node.accesses_$eq(node.accesses() + 1L);
        long priority = node.accesses();
        FrequencyNode<A> frequencyNode = this.frequency().getNextOrElseInsert(node.frequency(), (Function1<FrequencyNode, Object> & Serializable)x$19 -> BoxesRunTime.boxToBoolean(FrequencyList.$anonfun$increaseToNextFrequency$1(priority, x$19)), (Function0<FrequencyNode> & Serializable)() -> new FrequencyNode(priority));
        this.removeFromFrequency(node);
        this.addToFrequency(node, frequencyNode);
    }

    private void addToFrequency(Node<A> node, FrequencyNode<A> frequencyNode) {
        node.frequency_$eq(frequencyNode);
        frequencyNode.nodes().append(node);
    }

    private void removeFromFrequency(Node<A> node) {
        FrequencyNode<A> frequencyNode = node.frequency();
        frequencyNode.nodes().remove(node);
        if (frequencyNode.nodes().isEmpty()) {
            this.frequency().remove(frequencyNode);
            return;
        }
    }

    private A removeNode(Node<A> node) {
        A value = node.value();
        this.removeFromFrequency(node);
        if (OptionVal$.MODULE$.isDefined$extension(this.clock)) {
            this.overallRecency().remove(node);
        }
        this.lookupNode().$minus$eq(value);
        return value;
    }

    private A removeLeastFrequentNode(Node<A> node) {
        block0: {
            if (!this.dynamicAging) break block0;
            this.age_$eq(node.frequency().priority());
        }
        return this.removeNode(node);
    }

    private Iterator<Node<A>> forwardIterator() {
        return this.frequency().forwardIterator().flatMap((Function1<FrequencyNode, Iterator> & Serializable)x$20 -> x$20.nodes().forwardIterator());
    }

    private Iterator<Node<A>> backwardIterator() {
        return this.frequency().backwardIterator().flatMap((Function1<FrequencyNode, Iterator> & Serializable)x$21 -> x$21.nodes().backwardIterator());
    }

    public static final /* synthetic */ boolean $anonfun$removeOverallLeastRecentOutside$1(long min$1, Node x$11) {
        return x$11.timestamp() < min$1;
    }

    public static final /* synthetic */ boolean $anonfun$removeOverallMostRecentWithin$1(long max$1, Node x$12) {
        return x$12.timestamp() > max$1;
    }

    public static final /* synthetic */ boolean $anonfun$addAsLeastFrequent$1(FrequencyNode x$13) {
        return x$13.priority() == 1L;
    }

    public static final /* synthetic */ boolean $anonfun$addInitialAdjustedFrequency$1(long priority$1, FrequencyNode x$14) {
        return x$14.priority() < priority$1;
    }

    public static final /* synthetic */ boolean $anonfun$addInitialAdjustedFrequency$2(long priority$1, FrequencyNode x$15) {
        return x$15.priority() == priority$1;
    }

    public static final /* synthetic */ boolean $anonfun$addInitialAdjustedFrequency$4(long priority$1, FrequencyNode x$16) {
        return x$16.priority() == priority$1;
    }

    public static final /* synthetic */ boolean $anonfun$increaseAdjustedFrequency$1(long priority$2, FrequencyNode x$17) {
        return x$17.priority() < priority$2;
    }

    public static final /* synthetic */ boolean $anonfun$increaseAdjustedFrequency$2(long priority$2, FrequencyNode x$18) {
        return x$18.priority() == priority$2;
    }

    public static final /* synthetic */ boolean $anonfun$increaseToNextFrequency$1(long priority$3, FrequencyNode x$19) {
        return x$19.priority() == priority$3;
    }

    public FrequencyList(boolean dynamicAging, Clock clock) {
        this.dynamicAging = dynamicAging;
        this.clock = clock;
        this.frequency = new DoubleLinkedList((Function1<FrequencyNode, Object> & Serializable)x$3 -> new OptionVal<FrequencyNode>(x$3.lessFrequent()), (Function1<FrequencyNode, Object> & Serializable)x$4 -> new OptionVal<FrequencyNode>(x$4.moreFrequent()), (Function2<FrequencyNode, Object, Object> & Serializable)(node, previous) -> {
            node.lessFrequent_$eq((FrequencyNode)((OptionVal)previous).x());
            return BoxedUnit.UNIT;
        }, (Function2<FrequencyNode, Object, Object> & Serializable)(node, next) -> {
            node.moreFrequent_$eq((FrequencyNode)((OptionVal)next).x());
            return BoxedUnit.UNIT;
        });
        this.overallRecency = new DoubleLinkedList((Function1<Node, Object> & Serializable)x$5 -> new OptionVal<Node>(x$5.overallLessRecent()), (Function1<Node, Object> & Serializable)x$6 -> new OptionVal<Node>(x$6.overallMoreRecent()), (Function2<Node, Object, Object> & Serializable)(node, previous) -> {
            node.overallLessRecent_$eq((Node)((OptionVal)previous).x());
            return BoxedUnit.UNIT;
        }, (Function2<Node, Object, Object> & Serializable)(node, next) -> {
            node.overallMoreRecent_$eq((Node)((OptionVal)next).x());
            return BoxedUnit.UNIT;
        });
        this.lookupNode = (Map)Map$.MODULE$.empty();
        this.age = 0L;
    }

    private static final class FrequencyNode<A> {
        private final long priority;
        private FrequencyNode<A> lessFrequent;
        private FrequencyNode<A> moreFrequent;
        private final DoubleLinkedList<Node<A>> nodes;

        public long priority() {
            return this.priority;
        }

        public FrequencyNode<A> lessFrequent() {
            return this.lessFrequent;
        }

        public void lessFrequent_$eq(FrequencyNode<A> x$1) {
            this.lessFrequent = x$1;
        }

        public FrequencyNode<A> moreFrequent() {
            return this.moreFrequent;
        }

        public void moreFrequent_$eq(FrequencyNode<A> x$1) {
            this.moreFrequent = x$1;
        }

        public DoubleLinkedList<Node<A>> nodes() {
            return this.nodes;
        }

        public FrequencyNode(long priority) {
            this.priority = priority;
            OptionVal$.MODULE$.None();
            this.lessFrequent = null;
            OptionVal$.MODULE$.None();
            this.moreFrequent = null;
            this.nodes = new DoubleLinkedList((Function1<Node, Object> & Serializable)x$1 -> new OptionVal<Node>(x$1.lessRecent()), (Function1<Node, Object> & Serializable)x$2 -> new OptionVal<Node>(x$2.moreRecent()), (Function2<Node, Object, Object> & Serializable)(node, previous) -> {
                node.lessRecent_$eq((Node)((OptionVal)previous).x());
                return BoxedUnit.UNIT;
            }, (Function2<Node, Object, Object> & Serializable)(node, next) -> {
                node.moreRecent_$eq((Node)((OptionVal)next).x());
                return BoxedUnit.UNIT;
            });
        }
    }

    private static final class Node<A> {
        private final A value;
        private long accesses;
        private FrequencyNode<A> frequency;
        private Node<A> lessRecent;
        private Node<A> moreRecent;
        private Node<A> overallLessRecent;
        private Node<A> overallMoreRecent;
        private long timestamp;

        public A value() {
            return this.value;
        }

        public long accesses() {
            return this.accesses;
        }

        public void accesses_$eq(long x$1) {
            this.accesses = x$1;
        }

        public FrequencyNode<A> frequency() {
            return this.frequency;
        }

        public void frequency_$eq(FrequencyNode<A> x$1) {
            this.frequency = x$1;
        }

        public Node<A> lessRecent() {
            return this.lessRecent;
        }

        public void lessRecent_$eq(Node<A> x$1) {
            this.lessRecent = x$1;
        }

        public Node<A> moreRecent() {
            return this.moreRecent;
        }

        public void moreRecent_$eq(Node<A> x$1) {
            this.moreRecent = x$1;
        }

        public Node<A> overallLessRecent() {
            return this.overallLessRecent;
        }

        public void overallLessRecent_$eq(Node<A> x$1) {
            this.overallLessRecent = x$1;
        }

        public Node<A> overallMoreRecent() {
            return this.overallMoreRecent;
        }

        public void overallMoreRecent_$eq(Node<A> x$1) {
            this.overallMoreRecent = x$1;
        }

        public long timestamp() {
            return this.timestamp;
        }

        public void timestamp_$eq(long x$1) {
            this.timestamp = x$1;
        }

        public Node(A value, FrequencyNode<A> initialFrequency) {
            this.value = value;
            this.accesses = 1L;
            this.frequency = initialFrequency;
            OptionVal$.MODULE$.None();
            this.lessRecent = null;
            OptionVal$.MODULE$.None();
            this.moreRecent = null;
            OptionVal$.MODULE$.None();
            this.overallLessRecent = null;
            OptionVal$.MODULE$.None();
            this.overallMoreRecent = null;
            this.timestamp = 0L;
        }
    }
}

