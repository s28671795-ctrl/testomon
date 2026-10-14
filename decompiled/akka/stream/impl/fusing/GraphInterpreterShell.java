/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.Done;
import akka.actor.ActorRef;
import akka.annotation.InternalApi;
import akka.annotation.InternalStableApi;
import akka.event.LogSource$;
import akka.event.Logging$;
import akka.event.LoggingAdapter;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.impl.ExtendedActorMaterializer;
import akka.stream.impl.ReactiveStreamsCompliance;
import akka.stream.impl.SubFusingActorMaterializerImpl;
import akka.stream.impl.fusing.ActorGraphInterpreter;
import akka.stream.impl.fusing.GraphInterpreter;
import akka.stream.impl.fusing.GraphInterpreter$;
import akka.stream.impl.fusing.GraphInterpreterShell$Abort$;
import akka.stream.impl.fusing.GraphInterpreterShell$AsyncInput$;
import akka.stream.impl.fusing.GraphInterpreterShell$ResumeShell$;
import akka.stream.snapshot.InterpreterSnapshot;
import akka.stream.snapshot.LogicSnapshotImpl;
import akka.stream.snapshot.UninitializedInterpreterImpl;
import akka.stream.stage.GraphStageLogic;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;
import scala.Function1;
import scala.Function4;
import scala.MatchError;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Tuple2;
import scala.collection.ArrayOps$;
import scala.collection.Iterator;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.concurrent.Promise;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0011ud!CA\b\u0003#\u0011\u0011QDA\u0011\u0011)\ty\u0003\u0001BA\u0002\u0013\u0005\u00111\u0007\u0005\u000b\u0003?\u0002!\u00111A\u0005\u0002\u0005\u0005\u0004BCA7\u0001\t\u0005\t\u0015)\u0003\u00026!Q\u0011q\u000e\u0001\u0003\u0002\u0004%\t!!\u001d\t\u0015\u0005\u0005\u0005A!a\u0001\n\u0003\t\u0019\t\u0003\u0006\u0002\b\u0002\u0011\t\u0011)Q\u0005\u0003gB!\"!#\u0001\u0005\u000b\u0007I\u0011AAF\u0011)\t)\n\u0001B\u0001B\u0003%\u0011Q\u0012\u0005\u000b\u0003/\u0003!Q1A\u0005\u0002\u0005e\u0005BCAR\u0001\t\u0005\t\u0015!\u0003\u0002\u001c\"9\u0011Q\u0015\u0001\u0005\u0002\u0005\u001d\u0006bCA[\u0001\u0001\u0007\t\u0019!C\u0005\u0003oC1\"!2\u0001\u0001\u0004\u0005\r\u0011\"\u0003\u0002H\"Y\u00111\u001a\u0001A\u0002\u0003\u0005\u000b\u0015BA]\u0011)\ti\r\u0001EC\u0002\u0013\u0005\u0011q\u001a\u0004\u0007\u0003;\u0004\u0001)a8\t\u0015\t\u001d\u0001C!f\u0001\n\u0003\u0011I\u0001\u0003\u0006\u0003\fA\u0011\t\u0012)A\u0005\u0003SC!B!\u0004\u0011\u0005+\u0007I\u0011\u0001B\b\u0011)\u0011\t\u0002\u0005B\tB\u0003%\u0011Q\u000f\u0005\u000b\u0005'\u0001\"Q3A\u0005\u0002\tU\u0001B\u0003B\u000f!\tE\t\u0015!\u0003\u0003\u0018!Q!q\u0004\t\u0003\u0016\u0004%\tA!\t\t\u0015\t]\u0002C!E!\u0002\u0013\u0011\u0019\u0003\u0003\u0006\u0003:A\u0011)\u001a!C\u0001\u0005wA!Ba\u0011\u0011\u0005#\u0005\u000b\u0011\u0002B\u001f\u0011\u001d\t)\u000b\u0005C\u0001\u0005\u000bBqA!\u0016\u0011\t\u0003\u00129\u0006C\u0004\u0003rA!\tEa\u001d\t\u0013\tU\u0004#!A\u0005\u0002\t]\u0004\"\u0003BB!E\u0005I\u0011\u0001BC\u0011%\u0011I\nEI\u0001\n\u0003\u0011Y\nC\u0005\u0003 B\t\n\u0011\"\u0001\u0003\"\"I!Q\u0015\t\u0012\u0002\u0013\u0005!q\u0015\u0005\n\u0005W\u0003\u0012\u0013!C\u0001\u0005[C\u0011B!-\u0011\u0003\u0003%\tEa-\t\u0013\t\u0015\u0007#!A\u0005\u0002\t\u001d\u0007\"\u0003Be!\u0005\u0005I\u0011\u0001Bf\u0011%\u0011y\rEA\u0001\n\u0003\u0012\t\u000eC\u0005\u0003`B\t\t\u0011\"\u0001\u0003b\"I!1\u001e\t\u0002\u0002\u0013\u0005#Q\u001e\u0005\n\u0005c\u0004\u0012\u0011!C!\u0005gD\u0011B!>\u0011\u0003\u0003%\tEa>\t\u0013\te\b#!A\u0005B\tmx!\u0003B\u0000\u0001\u0005\u0005\t\u0012AB\u0001\r%\ti\u000eAA\u0001\u0012\u0003\u0019\u0019\u0001C\u0004\u0002&:\"\taa\u0007\t\u0013\tUh&!A\u0005F\t]\b\"CB\u000f]\u0005\u0005I\u0011QB\u0010\u0011%\u0019YCLA\u0001\n\u0003\u001biC\u0002\u0004\u0004@\u0001\u00015\u0011\t\u0005\u000b\u0005\u000f\u0019$Q3A\u0005\u0002\t%\u0001B\u0003B\u0006g\tE\t\u0015!\u0003\u0002*\"9\u0011QU\u001a\u0005\u0002\r\r\u0003b\u0002B+g\u0011\u00053\u0011\n\u0005\b\u0005c\u001aD\u0011\tB:\u0011%\u0011)hMA\u0001\n\u0003\u0019i\u0005C\u0005\u0003\u0004N\n\n\u0011\"\u0001\u0003\u0006\"I!\u0011W\u001a\u0002\u0002\u0013\u0005#1\u0017\u0005\n\u0005\u000b\u001c\u0014\u0011!C\u0001\u0005\u000fD\u0011B!34\u0003\u0003%\ta!\u0015\t\u0013\t=7'!A\u0005B\tE\u0007\"\u0003Bpg\u0005\u0005I\u0011AB+\u0011%\u0011YoMA\u0001\n\u0003\u001aI\u0006C\u0005\u0003rN\n\t\u0011\"\u0011\u0003t\"I!Q_\u001a\u0002\u0002\u0013\u0005#q\u001f\u0005\n\u0005s\u001c\u0014\u0011!C!\u0007;:\u0011b!\u0019\u0001\u0003\u0003E\taa\u0019\u0007\u0013\r}\u0002!!A\t\u0002\r\u0015\u0004bBAS\u000b\u0012\u00051Q\u000e\u0005\n\u0005k,\u0015\u0011!C#\u0005oD\u0011b!\bF\u0003\u0003%\tia\u001c\t\u0013\r-R)!A\u0005\u0002\u000eMdABB=\u0001\u0001\u001bY\b\u0003\u0006\u0003\b)\u0013)\u001a!C\u0001\u0005\u0013A!Ba\u0003K\u0005#\u0005\u000b\u0011BAU\u0011\u001d\t)K\u0013C\u0001\u0007{BqA!\u0016K\t\u0003\u001a\u0019\tC\u0004\u0003r)#\tEa\u001d\t\u0013\tU$*!A\u0005\u0002\r\u001d\u0005\"\u0003BB\u0015F\u0005I\u0011\u0001BC\u0011%\u0011\tLSA\u0001\n\u0003\u0012\u0019\fC\u0005\u0003F*\u000b\t\u0011\"\u0001\u0003H\"I!\u0011\u001a&\u0002\u0002\u0013\u000511\u0012\u0005\n\u0005\u001fT\u0015\u0011!C!\u0005#D\u0011Ba8K\u0003\u0003%\taa$\t\u0013\t-(*!A\u0005B\rM\u0005\"\u0003By\u0015\u0006\u0005I\u0011\tBz\u0011%\u0011)PSA\u0001\n\u0003\u00129\u0010C\u0005\u0003z*\u000b\t\u0011\"\u0011\u0004\u0018\u001eI11\u0014\u0001\u0002\u0002#\u00051Q\u0014\u0004\n\u0007s\u0002\u0011\u0011!E\u0001\u0007?Cq!!*]\t\u0003\u0019\u0019\u000bC\u0005\u0003vr\u000b\t\u0011\"\u0012\u0003x\"I1Q\u0004/\u0002\u0002\u0013\u00055Q\u0015\u0005\n\u0007Wa\u0016\u0011!CA\u0007SC1b!,\u0001\u0001\u0004\u0005\r\u0011\"\u0003\u0003<!Y1q\u0016\u0001A\u0002\u0003\u0007I\u0011BBY\u0011-\u0019)\f\u0001a\u0001\u0002\u0003\u0006KA!\u0010\t\u0015\r]\u0006\u0001#b\u0001\n\u0003\u0019I\fC\u0005\u0004B\u0002\u0001\r\u0011\"\u0003\u0003H\"I11\u0019\u0001A\u0002\u0013%1Q\u0019\u0005\t\u0007\u0013\u0004\u0001\u0015)\u0003\u0003Z!I11\u001a\u0001A\u0002\u0013%1Q\u001a\u0005\n\u00077\u0004\u0001\u0019!C\u0005\u0007;D\u0001b!9\u0001A\u0003&1q\u001a\u0005\n\u0007G\u0004\u0001\u0019!C\u0005\u0007KD\u0011ba<\u0001\u0001\u0004%Ia!=\t\u0011\rU\b\u0001)Q\u0005\u0007OD\u0011ba>\u0001\u0005\u0004%\tAa2\t\u0011\re\b\u0001)A\u0005\u00053B\u0011ba?\u0001\u0005\u0004%IAa2\t\u0011\ru\b\u0001)A\u0005\u00053B\u0011ba@\u0001\u0001\u0004%I\u0001\"\u0001\t\u0013\u0011\r\u0001\u00011A\u0005\n\u0011\u0015\u0001\u0002\u0003C\u0005\u0001\u0001\u0006KAa9\t\u000f\u0011-\u0001\u0001\"\u0001\u0005\u0002!9AQ\u0002\u0001\u0005\u0002\u0011=\u0001b\u0002C\u0011\u0001\u0011\u0005A1\u0005\u0005\n\tS\u0001\u0001\u0019!C\u0005\t\u0003A\u0011\u0002b\u000b\u0001\u0001\u0004%I\u0001\"\f\t\u0011\u0011E\u0002\u0001)Q\u0005\u0005GDq\u0001b\r\u0001\t\u0003!\t\u0001C\u0004\u00056\u0001!I\u0001\"\u0001\t\u000f\u0011]\u0002\u0001\"\u0001\u0003t!IA\u0011\b\u0001A\u0002\u0013%A\u0011\u0001\u0005\n\tw\u0001\u0001\u0019!C\u0005\t{A\u0001\u0002\"\u0011\u0001A\u0003&!1\u001d\u0005\n\t\u0007\u0002!\u0019!C\u0001\t\u000bB\u0001\u0002b\u0012\u0001A\u0003%1Q\t\u0005\b\t\u0013\u0002A\u0011\u0001C&\u0011\u001d!y\u0005\u0001C\u0001\t#Bq\u0001b\u0016\u0001\t\u0003!I\u0006C\u0004\u0005f\u0001!\t\u0001b\u001a\u0003+\u001d\u0013\u0018\r\u001d5J]R,'\u000f\u001d:fi\u0016\u00148\u000b[3mY*!\u00111CA\u000b\u0003\u00191Wo]5oO*!\u0011qCA\r\u0003\u0011IW\u000e\u001d7\u000b\t\u0005m\u0011QD\u0001\u0007gR\u0014X-Y7\u000b\u0005\u0005}\u0011\u0001B1lW\u0006\u001c2\u0001AA\u0012!\u0011\t)#a\u000b\u000e\u0005\u0005\u001d\"BAA\u0015\u0003\u0015\u00198-\u00197b\u0013\u0011\ti#a\n\u0003\r\u0005s\u0017PU3g\u0003-\u0019wN\u001c8fGRLwN\\:\u0004\u0001U\u0011\u0011Q\u0007\t\u0007\u0003K\t9$a\u000f\n\t\u0005e\u0012q\u0005\u0002\u0006\u0003J\u0014\u0018-\u001f\t\u0005\u0003{\tIF\u0004\u0003\u0002@\u0005Uc\u0002BA!\u0003'rA!a\u0011\u0002R9!\u0011QIA(\u001d\u0011\t9%!\u0014\u000e\u0005\u0005%#\u0002BA&\u0003c\ta\u0001\u0010:p_Rt\u0014BAA\u0010\u0013\u0011\tY\"!\b\n\t\u0005]\u0011\u0011D\u0005\u0005\u0003'\t)\"\u0003\u0003\u0002X\u0005E\u0011\u0001E$sCBD\u0017J\u001c;feB\u0014X\r^3s\u0013\u0011\tY&!\u0018\u0003\u0015\r{gN\\3di&|gN\u0003\u0003\u0002X\u0005E\u0011aD2p]:,7\r^5p]N|F%Z9\u0015\t\u0005\r\u0014\u0011\u000e\t\u0005\u0003K\t)'\u0003\u0003\u0002h\u0005\u001d\"\u0001B+oSRD\u0011\"a\u001b\u0003\u0003\u0003\u0005\r!!\u000e\u0002\u0007a$\u0013'\u0001\u0007d_:tWm\u0019;j_:\u001c\b%\u0001\u0004m_\u001eL7m]\u000b\u0003\u0003g\u0002b!!\n\u00028\u0005U\u0004\u0003BA<\u0003{j!!!\u001f\u000b\t\u0005m\u0014\u0011D\u0001\u0006gR\fw-Z\u0005\u0005\u0003\u007f\nIHA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0003)awnZ5dg~#S-\u001d\u000b\u0005\u0003G\n)\tC\u0005\u0002l\u0015\t\t\u00111\u0001\u0002t\u00059An\\4jGN\u0004\u0013AC1uiJL'-\u001e;fgV\u0011\u0011Q\u0012\t\u0005\u0003\u001f\u000b\t*\u0004\u0002\u0002\u001a%!\u00111SA\r\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\fCR$(/\u001b2vi\u0016\u001c\b%A\u0002nCR,\"!a'\u0011\t\u0005u\u0015qT\u0007\u0003\u0003+IA!!)\u0002\u0016\tIR\t\u001f;f]\u0012,G-Q2u_Jl\u0015\r^3sS\u0006d\u0017N_3s\u0003\u0011i\u0017\r\u001e\u0011\u0002\rqJg.\u001b;?))\tI+!,\u00020\u0006E\u00161\u0017\t\u0004\u0003W\u0003QBAA\t\u0011\u001d\tyc\u0003a\u0001\u0003kAq!a\u001c\f\u0001\u0004\t\u0019\bC\u0004\u0002\n.\u0001\r!!$\t\u000f\u0005]5\u00021\u0001\u0002\u001c\u0006!1/\u001a7g+\t\tI\f\u0005\u0003\u0002<\u0006\u0005WBAA_\u0015\u0011\ty,!\b\u0002\u000b\u0005\u001cGo\u001c:\n\t\u0005\r\u0017Q\u0018\u0002\t\u0003\u000e$xN\u001d*fM\u0006A1/\u001a7g?\u0012*\u0017\u000f\u0006\u0003\u0002d\u0005%\u0007\"CA6\u001b\u0005\u0005\t\u0019AA]\u0003\u0015\u0019X\r\u001c4!\u0003\rawnZ\u000b\u0003\u0003#\u0004B!a5\u0002Z6\u0011\u0011Q\u001b\u0006\u0005\u0003/\fi\"A\u0003fm\u0016tG/\u0003\u0003\u0002\\\u0006U'A\u0004'pO\u001eLgnZ!eCB$XM\u001d\u0002\u000b\u0003NLhnY%oaV$8#\u0003\t\u0002$\u0005\u0005\u0018q^A{!\u0011\t\u0019/!;\u000f\t\u0005-\u0016Q]\u0005\u0005\u0003O\f\t\"A\u000bBGR|'o\u0012:ba\"Le\u000e^3saJ,G/\u001a:\n\t\u0005-\u0018Q\u001e\u0002\u000e\u0005>,h\u000eZ1ss\u00163XM\u001c;\u000b\t\u0005\u001d\u0018\u0011\u0003\t\u0005\u0003K\t\t0\u0003\u0003\u0002t\u0006\u001d\"a\u0002)s_\u0012,8\r\u001e\t\u0005\u0003o\u0014\tA\u0004\u0003\u0002z\u0006uh\u0002BA$\u0003wL!!!\u000b\n\t\u0005}\u0018qE\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\u0011\u0019A!\u0002\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\t\u0005}\u0018qE\u0001\u0006g\",G\u000e\\\u000b\u0003\u0003S\u000baa\u001d5fY2\u0004\u0013!\u00027pO&\u001cWCAA;\u0003\u0019awnZ5dA\u0005\u0019QM\u001e;\u0016\u0005\t]\u0001\u0003BA\u0013\u00053IAAa\u0007\u0002(\t\u0019\u0011I\\=\u0002\t\u00154H\u000fI\u0001\baJ|W.[:f+\t\u0011\u0019\u0003\u0005\u0004\u0003&\t-\"qF\u0007\u0003\u0005OQAA!\u000b\u0002(\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\t\t5\"q\u0005\u0002\b!J|W.[:f!\u0011\u0011\tDa\r\u000e\u0005\u0005u\u0011\u0002\u0002B\u001b\u0003;\u0011A\u0001R8oK\u0006A\u0001O]8nSN,\u0007%A\u0004iC:$G.\u001a:\u0016\u0005\tu\u0002\u0003CA\u0013\u0005\u007f\u00119\"a\u0019\n\t\t\u0005\u0013q\u0005\u0002\n\rVt7\r^5p]F\n\u0001\u0002[1oI2,'\u000f\t\u000b\r\u0005\u000f\u0012YE!\u0014\u0003P\tE#1\u000b\t\u0004\u0005\u0013\u0002R\"\u0001\u0001\t\u000f\t\u001d1\u00041\u0001\u0002*\"9!QB\u000eA\u0002\u0005U\u0004b\u0002B\n7\u0001\u0007!q\u0003\u0005\b\u0005?Y\u0002\u0019\u0001B\u0012\u0011\u001d\u0011Id\u0007a\u0001\u0005{\tq!\u001a=fGV$X\r\u0006\u0003\u0003Z\t}\u0003\u0003BA\u0013\u00057JAA!\u0018\u0002(\t\u0019\u0011J\u001c;\t\u000f\t\u0005D\u00041\u0001\u0003Z\u0005QQM^3oi2KW.\u001b;)\u0007q\u0011)\u0007\u0005\u0003\u0003h\t5TB\u0001B5\u0015\u0011\u0011Y'!\b\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0003p\t%$!E%oi\u0016\u0014h.\u00197Ti\u0006\u0014G.Z!qS\u000611-\u00198dK2$\"!a\u0019\u0002\t\r|\u0007/\u001f\u000b\r\u0005\u000f\u0012IHa\u001f\u0003~\t}$\u0011\u0011\u0005\n\u0005\u000fq\u0002\u0013!a\u0001\u0003SC\u0011B!\u0004\u001f!\u0003\u0005\r!!\u001e\t\u0013\tMa\u0004%AA\u0002\t]\u0001\"\u0003B\u0010=A\u0005\t\u0019\u0001B\u0012\u0011%\u0011ID\bI\u0001\u0002\u0004\u0011i$\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\t\u001d%\u0006BAU\u0005\u0013[#Aa#\u0011\t\t5%QS\u0007\u0003\u0005\u001fSAA!%\u0003\u0014\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0005\u0005W\n9#\u0003\u0003\u0003\u0018\n=%!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012TC\u0001BOU\u0011\t)H!#\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gU\u0011!1\u0015\u0016\u0005\u0005/\u0011I)\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001b\u0016\u0005\t%&\u0006\u0002B\u0012\u0005\u0013\u000babY8qs\u0012\"WMZ1vYR$S'\u0006\u0002\u00030*\"!Q\bBE\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011!Q\u0017\t\u0005\u0005o\u0013\t-\u0004\u0002\u0003:*!!1\u0018B_\u0003\u0011a\u0017M\\4\u000b\u0005\t}\u0016\u0001\u00026bm\u0006LAAa1\u0003:\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\"A!\u0017\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!!q\u0003Bg\u0011%\tYGJA\u0001\u0002\u0004\u0011I&A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\u0011\u0019\u000e\u0005\u0004\u0003V\nm'qC\u0007\u0003\u0005/TAA!7\u0002(\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\tu'q\u001b\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0003d\n%\b\u0003BA\u0013\u0005KLAAa:\u0002(\t9!i\\8mK\u0006t\u0007\"CA6Q\u0005\u0005\t\u0019\u0001B\f\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\tU&q\u001e\u0005\n\u0003WJ\u0013\u0011!a\u0001\u00053\n\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u00053\n\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0005k\u000ba!Z9vC2\u001cH\u0003\u0002Br\u0005{D\u0011\"a\u001b-\u0003\u0003\u0005\rAa\u0006\u0002\u0015\u0005\u001b\u0018P\\2J]B,H\u000fE\u0002\u0003J9\u001aRALB\u0003\u0007#\u0001\u0002ca\u0002\u0004\u000e\u0005%\u0016Q\u000fB\f\u0005G\u0011iDa\u0012\u000e\u0005\r%!\u0002BB\u0006\u0003O\tqA];oi&lW-\u0003\u0003\u0004\u0010\r%!!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8okA!11CB\r\u001b\t\u0019)B\u0003\u0003\u0004\u0018\tu\u0016AA5p\u0013\u0011\u0011\u0019a!\u0006\u0015\u0005\r\u0005\u0011!B1qa2LH\u0003\u0004B$\u0007C\u0019\u0019c!\n\u0004(\r%\u0002b\u0002B\u0004c\u0001\u0007\u0011\u0011\u0016\u0005\b\u0005\u001b\t\u0004\u0019AA;\u0011\u001d\u0011\u0019\"\ra\u0001\u0005/AqAa\b2\u0001\u0004\u0011\u0019\u0003C\u0004\u0003:E\u0002\rA!\u0010\u0002\u000fUt\u0017\r\u001d9msR!1qFB\u001e!\u0019\t)c!\r\u00046%!11GA\u0014\u0005\u0019y\u0005\u000f^5p]Bq\u0011QEB\u001c\u0003S\u000b)Ha\u0006\u0003$\tu\u0012\u0002BB\u001d\u0003O\u0011a\u0001V;qY\u0016,\u0004\"CB\u001fe\u0005\u0005\t\u0019\u0001B$\u0003\rAH\u0005\r\u0002\f%\u0016\u001cX/\\3TQ\u0016dGnE\u00054\u0003G\t\t/a<\u0002vR!1QIB$!\r\u0011Ie\r\u0005\b\u0005\u000f1\u0004\u0019AAU)\u0011\u0011Ifa\u0013\t\u000f\t\u0005t\u00071\u0001\u0003ZQ!1QIB(\u0011%\u00119!\u000fI\u0001\u0002\u0004\tI\u000b\u0006\u0003\u0003\u0018\rM\u0003\"CA6{\u0005\u0005\t\u0019\u0001B-)\u0011\u0011\u0019oa\u0016\t\u0013\u0005-t(!AA\u0002\t]A\u0003\u0002B[\u00077B\u0011\"a\u001bA\u0003\u0003\u0005\rA!\u0017\u0015\t\t\r8q\f\u0005\n\u0003W\u001a\u0015\u0011!a\u0001\u0005/\t1BU3tk6,7\u000b[3mYB\u0019!\u0011J#\u0014\u000b\u0015\u001b9g!\u0005\u0011\u0011\r\u001d1\u0011NAU\u0007\u000bJAaa\u001b\u0004\n\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u0019\u0015\u0005\r\rD\u0003BB#\u0007cBqAa\u0002I\u0001\u0004\tI\u000b\u0006\u0003\u0004v\r]\u0004CBA\u0013\u0007c\tI\u000bC\u0005\u0004>%\u000b\t\u00111\u0001\u0004F\t)\u0011IY8siNI!*a\t\u0002b\u0006=\u0018Q\u001f\u000b\u0005\u0007\u007f\u001a\t\tE\u0002\u0003J)CqAa\u0002N\u0001\u0004\tI\u000b\u0006\u0003\u0003Z\r\u0015\u0005b\u0002B1\u001d\u0002\u0007!\u0011\f\u000b\u0005\u0007\u007f\u001aI\tC\u0005\u0003\bA\u0003\n\u00111\u0001\u0002*R!!qCBG\u0011%\tY\u0007VA\u0001\u0002\u0004\u0011I\u0006\u0006\u0003\u0003d\u000eE\u0005\"CA6-\u0006\u0005\t\u0019\u0001B\f)\u0011\u0011)l!&\t\u0013\u0005-t+!AA\u0002\teC\u0003\u0002Br\u00073C\u0011\"a\u001b[\u0003\u0003\u0005\rAa\u0006\u0002\u000b\u0005\u0013wN\u001d;\u0011\u0007\t%ClE\u0003]\u0007C\u001b\t\u0002\u0005\u0005\u0004\b\r%\u0014\u0011VB@)\t\u0019i\n\u0006\u0003\u0004\u0000\r\u001d\u0006b\u0002B\u0004?\u0002\u0007\u0011\u0011\u0016\u000b\u0005\u0007k\u001aY\u000bC\u0005\u0004>\u0001\f\t\u00111\u0001\u0004\u0000\u0005)RM\\9vKV,Gk\\*i_J$8)\u001b:dk&$\u0018!G3ocV,W/\u001a+p'\"|'\u000f^\"je\u000e,\u0018\u000e^0%KF$B!a\u0019\u00044\"I\u00111\u000e2\u0002\u0002\u0003\u0007!QH\u0001\u0017K:\fX/Z;f)>\u001c\u0006n\u001c:u\u0007&\u00148-^5uA\u0005Y\u0011N\u001c;feB\u0014X\r^3s+\t\u0019Y\f\u0005\u0003\u0002,\u000eu\u0016\u0002BB`\u0003#\u0011\u0001c\u0012:ba\"Le\u000e^3saJ,G/\u001a:\u0002#M,(m]2sS\n,7\u000fU3oI&tw-A\u000btk\n\u001c8M]5cKN\u0004VM\u001c3j]\u001e|F%Z9\u0015\t\u0005\r4q\u0019\u0005\n\u0003W2\u0017\u0011!a\u0001\u00053\n!c];cg\u000e\u0014\u0018NY3t!\u0016tG-\u001b8hA\u00051\u0011N\u001c9viN,\"aa4\u0011\r\u0005]8\u0011[Bk\u0013\u0011\u0019\u0019N!\u0002\u0003\t1K7\u000f\u001e\t\u0005\u0003G\u001c9.\u0003\u0003\u0004Z\u00065(A\u0007\"bi\u000eD\u0017N\\4BGR|'/\u00138qkR\u0014u.\u001e8eCJL\u0018AC5oaV$8o\u0018\u0013fcR!\u00111MBp\u0011%\tY'[A\u0001\u0002\u0004\u0019y-A\u0004j]B,Ho\u001d\u0011\u0002\u000f=,H\u000f];ugV\u00111q\u001d\t\u0007\u0003o\u001c\tn!;\u0011\t\u0005\r81^\u0005\u0005\u0007[\fiOA\nBGR|'oT;uaV$(i\\;oI\u0006\u0014\u00180A\u0006pkR\u0004X\u000f^:`I\u0015\fH\u0003BA2\u0007gD\u0011\"a\u001bm\u0003\u0003\u0005\raa:\u0002\u0011=,H\u000f];ug\u0002\nqb\u001d5fY2,e/\u001a8u\u0019&l\u0017\u000e^\u0001\u0011g\",G\u000e\\#wK:$H*[7ji\u0002\n!\"\u00192peRd\u0015.\\5u\u0003-\t'm\u001c:u\u0019&l\u0017\u000e\u001e\u0011\u0002\u001fI,7/^7f'\u000eDW\rZ;mK\u0012,\"Aa9\u0002'I,7/^7f'\u000eDW\rZ;mK\u0012|F%Z9\u0015\t\u0005\rDq\u0001\u0005\n\u0003W\u001a\u0018\u0011!a\u0001\u0005G\f\u0001C]3tk6,7k\u00195fIVdW\r\u001a\u0011\u0002\u001b%\u001c\u0018J\\5uS\u0006d\u0017N_3e\u0003\u0011Ig.\u001b;\u0015\u0015\teC\u0011\u0003C\n\t;!y\u0002C\u0004\u00026Z\u0004\r!!/\t\u000f\u0011Ua\u000f1\u0001\u0005\u0018\u000511/\u001e2NCR\u0004B!!(\u0005\u001a%!A1DA\u000b\u0005y\u0019VO\u0019$vg&tw-Q2u_Jl\u0015\r^3sS\u0006d\u0017N_3s\u00136\u0004H\u000eC\u0004\u0004.Z\u0004\rA!\u0010\t\u000f\t\u0005d\u000f1\u0001\u0003Z\u0005a\u0001O]8dKN\u001cXI^3oiR1!\u0011\fC\u0013\tOAq!a6x\u0001\u0004\t\t\u000fC\u0004\u0003b]\u0004\rA!\u0017\u0002)%tG/\u001a:qe\u0016$XM]\"p[BdW\r^3e\u0003aIg\u000e^3saJ,G/\u001a:D_6\u0004H.\u001a;fI~#S-\u001d\u000b\u0005\u0003G\"y\u0003C\u0005\u0002le\f\t\u00111\u0001\u0003d\u0006)\u0012N\u001c;feB\u0014X\r^3s\u0007>l\u0007\u000f\\3uK\u0012\u0004\u0013\u0001D5t)\u0016\u0014X.\u001b8bi\u0016$\u0017aC2b]NCW\u000f\u001e#po:\f\u0001c];cg\u000e\u0014\u0018NY3BeJLg/\u001a3\u0002%]\f\u0017\u000e^5oO\u001a{'o\u00155vi\u0012|wO\\\u0001\u0017o\u0006LG/\u001b8h\r>\u00148\u000b[;uI><hn\u0018\u0013fcR!\u00111\rC \u0011%\tYg`A\u0001\u0002\u0004\u0011\u0019/A\nxC&$\u0018N\\4G_J\u001c\u0006.\u001e;e_^t\u0007%\u0001\u0004sKN,X.Z\u000b\u0003\u0007\u000b\nqA]3tk6,\u0007%\u0001\u0006tK:$'+Z:v[\u0016$B!a\u0019\u0005N!AA\u0011JA\u0004\u0001\u0004\u0011\u0019/\u0001\u0005sk:\u0014\u0015\r^2i)\u0011\u0011I\u0006b\u0015\t\u0011\u0011U\u0013\u0011\u0002a\u0001\u00053\nq\"Y2u_J,e/\u001a8u\u0019&l\u0017\u000e^\u0001\tiJL\u0018IY8siR!\u00111\rC.\u0011!!i&a\u0003A\u0002\u0011}\u0013AA3y!\u0011\t9\u0010\"\u0019\n\t\u0011\r$Q\u0001\u0002\n)\"\u0014xn^1cY\u0016\f!\u0002^8T]\u0006\u00048\u000f[8u+\t!I\u0007\u0005\u0003\u0005l\u0011ETB\u0001C7\u0015\u0011!y'!\u0007\u0002\u0011Mt\u0017\r]:i_RLA\u0001b\u001d\u0005n\t\u0019\u0012J\u001c;feB\u0014X\r^3s':\f\u0007o\u001d5pi\"\u001a\u0001\u0001b\u001e\u0011\t\t\u001dD\u0011P\u0005\u0005\tw\u0012IGA\u0006J]R,'O\\1m\u0003BL\u0007")
@InternalApi
public final class GraphInterpreterShell {
    private LoggingAdapter log;
    private volatile GraphInterpreterShell$AsyncInput$ AsyncInput$module;
    private volatile GraphInterpreterShell$ResumeShell$ ResumeShell$module;
    private volatile GraphInterpreterShell$Abort$ Abort$module;
    private GraphInterpreter interpreter;
    private GraphInterpreter.Connection[] connections;
    private GraphStageLogic[] logics;
    private final Attributes attributes;
    private final ExtendedActorMaterializer mat;
    private ActorRef akka$stream$impl$fusing$GraphInterpreterShell$$self;
    private Function1<Object, BoxedUnit> enqueueToShortCircuit;
    private int akka$stream$impl$fusing$GraphInterpreterShell$$subscribesPending;
    private List<ActorGraphInterpreter.BatchingActorInputBoundary> inputs;
    private List<ActorGraphInterpreter.ActorOutputBoundary> outputs;
    private final int shellEventLimit;
    private final int abortLimit;
    private boolean resumeScheduled;
    private boolean interpreterCompleted;
    private boolean akka$stream$impl$fusing$GraphInterpreterShell$$waitingForShutdown;
    private final ResumeShell resume;
    private volatile byte bitmap$0;

    public GraphInterpreterShell$AsyncInput$ AsyncInput() {
        if (this.AsyncInput$module == null) {
            this.AsyncInput$lzycompute$1();
        }
        return this.AsyncInput$module;
    }

    public GraphInterpreterShell$ResumeShell$ ResumeShell() {
        if (this.ResumeShell$module == null) {
            this.ResumeShell$lzycompute$1();
        }
        return this.ResumeShell$module;
    }

    public GraphInterpreterShell$Abort$ Abort() {
        if (this.Abort$module == null) {
            this.Abort$lzycompute$1();
        }
        return this.Abort$module;
    }

    public GraphInterpreter.Connection[] connections() {
        return this.connections;
    }

    public void connections_$eq(GraphInterpreter.Connection[] x$1) {
        this.connections = x$1;
    }

    public GraphStageLogic[] logics() {
        return this.logics;
    }

    public void logics_$eq(GraphStageLogic[] x$1) {
        this.logics = x$1;
    }

    public Attributes attributes() {
        return this.attributes;
    }

    public ExtendedActorMaterializer mat() {
        return this.mat;
    }

    public ActorRef akka$stream$impl$fusing$GraphInterpreterShell$$self() {
        return this.akka$stream$impl$fusing$GraphInterpreterShell$$self;
    }

    private void self_$eq(ActorRef x$1) {
        this.akka$stream$impl$fusing$GraphInterpreterShell$$self = x$1;
    }

    private LoggingAdapter log$lzycompute() {
        GraphInterpreterShell graphInterpreterShell = this;
        synchronized (graphInterpreterShell) {
            if ((byte)(this.bitmap$0 & 1) == 0) {
                this.log = Logging$.MODULE$.apply(this.mat().system().eventStream(), this.akka$stream$impl$fusing$GraphInterpreterShell$$self(), LogSource$.MODULE$.fromActorRef());
                this.bitmap$0 = (byte)(this.bitmap$0 | 1);
            }
        }
        return this.log;
    }

    public LoggingAdapter log() {
        if ((byte)(this.bitmap$0 & 1) == 0) {
            return this.log$lzycompute();
        }
        return this.log;
    }

    private Function1<Object, BoxedUnit> enqueueToShortCircuit() {
        return this.enqueueToShortCircuit;
    }

    private void enqueueToShortCircuit_$eq(Function1<Object, BoxedUnit> x$1) {
        this.enqueueToShortCircuit = x$1;
    }

    private GraphInterpreter interpreter$lzycompute() {
        GraphInterpreterShell graphInterpreterShell = this;
        synchronized (graphInterpreterShell) {
            if ((byte)(this.bitmap$0 & 2) == 0) {
                this.interpreter = new GraphInterpreter(this.mat(), this.log(), this.logics(), this.connections(), (Function4<GraphStageLogic, Object, Promise<Done>, Function1<Object, BoxedUnit>, BoxedUnit>)(Function4<GraphStageLogic, Object, Promise, Function1, Object> & Serializable)(logic, event, promise, handler) -> {
                    GraphInterpreterShell.$anonfun$interpreter$1(this, logic, event, promise, handler);
                    return BoxedUnit.UNIT;
                }, ((ActorAttributes.FuzzingMode)this.attributes().mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.FuzzingMode.class))).enabled(), this.akka$stream$impl$fusing$GraphInterpreterShell$$self());
                this.bitmap$0 = (byte)(this.bitmap$0 | 2);
            }
        }
        return this.interpreter;
    }

    public GraphInterpreter interpreter() {
        if ((byte)(this.bitmap$0 & 2) == 0) {
            return this.interpreter$lzycompute();
        }
        return this.interpreter;
    }

    private int subscribesPending() {
        return this.akka$stream$impl$fusing$GraphInterpreterShell$$subscribesPending;
    }

    public void akka$stream$impl$fusing$GraphInterpreterShell$$subscribesPending_$eq(int x$1) {
        this.akka$stream$impl$fusing$GraphInterpreterShell$$subscribesPending = x$1;
    }

    private List<ActorGraphInterpreter.BatchingActorInputBoundary> inputs() {
        return this.inputs;
    }

    private void inputs_$eq(List<ActorGraphInterpreter.BatchingActorInputBoundary> x$1) {
        this.inputs = x$1;
    }

    private List<ActorGraphInterpreter.ActorOutputBoundary> outputs() {
        return this.outputs;
    }

    private void outputs_$eq(List<ActorGraphInterpreter.ActorOutputBoundary> x$1) {
        this.outputs = x$1;
    }

    public int shellEventLimit() {
        return this.shellEventLimit;
    }

    private int abortLimit() {
        return this.abortLimit;
    }

    private boolean resumeScheduled() {
        return this.resumeScheduled;
    }

    private void resumeScheduled_$eq(boolean x$1) {
        this.resumeScheduled = x$1;
    }

    public boolean isInitialized() {
        return this.akka$stream$impl$fusing$GraphInterpreterShell$$self() != null;
    }

    public int init(ActorRef self, SubFusingActorMaterializerImpl subMat, Function1<Object, BoxedUnit> enqueueToShortCircuit, int eventLimit) {
        this.self_$eq(self);
        this.enqueueToShortCircuit_$eq(enqueueToShortCircuit);
        for (int i = 0; i < this.logics().length; ++i) {
            GraphStageLogic graphStageLogic = this.logics()[i];
            if (graphStageLogic instanceof ActorGraphInterpreter.BatchingActorInputBoundary) {
                ActorGraphInterpreter.BatchingActorInputBoundary batchingActorInputBoundary = (ActorGraphInterpreter.BatchingActorInputBoundary)graphStageLogic;
                batchingActorInputBoundary.setActor(self);
                this.akka$stream$impl$fusing$GraphInterpreterShell$$subscribesPending_$eq(this.subscribesPending() + 1);
                this.inputs_$eq(this.inputs().$colon$colon(batchingActorInputBoundary));
                continue;
            }
            if (!(graphStageLogic instanceof ActorGraphInterpreter.ActorOutputBoundary)) continue;
            ActorGraphInterpreter.ActorOutputBoundary actorOutputBoundary = (ActorGraphInterpreter.ActorOutputBoundary)graphStageLogic;
            actorOutputBoundary.setActor(self);
            actorOutputBoundary.subscribePending();
            this.outputs_$eq(this.outputs().$colon$colon(actorOutputBoundary));
        }
        this.interpreter().init(subMat);
        return this.runBatch(eventLimit);
    }

    public int processEvent(ActorGraphInterpreter.BoundaryEvent event, int eventLimit) {
        this.resumeScheduled_$eq(false);
        return event.execute(eventLimit);
    }

    private boolean interpreterCompleted() {
        return this.interpreterCompleted;
    }

    private void interpreterCompleted_$eq(boolean x$1) {
        this.interpreterCompleted = x$1;
    }

    public boolean isTerminated() {
        return this.interpreterCompleted() && this.canShutDown();
    }

    private boolean canShutDown() {
        return this.subscribesPending() == 0;
    }

    public void subscribeArrived() {
        this.akka$stream$impl$fusing$GraphInterpreterShell$$subscribesPending_$eq(this.subscribesPending() - 1);
    }

    public boolean akka$stream$impl$fusing$GraphInterpreterShell$$waitingForShutdown() {
        return this.akka$stream$impl$fusing$GraphInterpreterShell$$waitingForShutdown;
    }

    private void waitingForShutdown_$eq(boolean x$1) {
        this.akka$stream$impl$fusing$GraphInterpreterShell$$waitingForShutdown = x$1;
    }

    public ResumeShell resume() {
        return this.resume;
    }

    public void sendResume(boolean sendResume) {
        this.resumeScheduled_$eq(true);
        if (sendResume) {
            ActorRef qual$1 = this.akka$stream$impl$fusing$GraphInterpreterShell$$self();
            ResumeShell x$1 = this.resume();
            ActorRef x$2 = qual$1.$bang$default$2(x$1);
            qual$1.$bang(x$1, x$2);
            return;
        }
        this.enqueueToShortCircuit().apply(this.resume());
    }

    public int runBatch(int actorEventLimit) {
        int n;
        try {
            Object object;
            boolean usingShellLimit = this.shellEventLimit() < actorEventLimit;
            int remainingQuota = this.interpreter().execute(Math.min(actorEventLimit, this.shellEventLimit()));
            if (this.interpreter().isCompleted()) {
                if (this.canShutDown()) {
                    this.interpreterCompleted_$eq(true);
                    object = BoxedUnit.UNIT;
                } else {
                    this.waitingForShutdown_$eq(true);
                    FiniteDuration subscriptionTimeout = ((ActorAttributes.StreamSubscriptionTimeout)this.attributes().mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.StreamSubscriptionTimeout.class))).timeout();
                    object = this.mat().scheduleOnce(subscriptionTimeout, new Runnable(this){
                        private final /* synthetic */ GraphInterpreterShell $outer;

                        public void run() {
                            ActorRef qual$1 = this.$outer.akka$stream$impl$fusing$GraphInterpreterShell$$self();
                            Abort x$1 = new Abort(this.$outer, this.$outer);
                            ActorRef x$2 = qual$1.$bang$default$2(x$1);
                            qual$1.$bang(x$1, x$2);
                        }
                        {
                            if ($outer == null) {
                                throw null;
                            }
                            this.$outer = $outer;
                        }
                    });
                }
            } else {
                if (this.interpreter().isSuspended() && !this.resumeScheduled()) {
                    this.sendResume(!usingShellLimit);
                }
                object = BoxedUnit.UNIT;
            }
            n = usingShellLimit ? actorEventLimit - this.shellEventLimit() + remainingQuota : remainingQuota;
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable e = option.get();
                this.tryAbort(e);
                n = actorEventLimit - 1;
            }
            throw throwable;
        }
        return n;
    }

    public void tryAbort(Throwable ex) {
        block8: {
            Throwable throwable;
            Throwable throwable2 = ex;
            if (throwable2 instanceof ReactiveStreamsCompliance.SpecViolation) {
                ReactiveStreamsCompliance.SpecViolation specViolation = (ReactiveStreamsCompliance.SpecViolation)((Object)throwable2);
                throwable = new IllegalStateException("Shutting down because of violation of the Reactive Streams specification.", (Throwable)((Object)specViolation));
            } else {
                throwable = ex;
            }
            Throwable reason = throwable;
            try {
                try {
                    this.inputs().foreach((Function1<ActorGraphInterpreter.BatchingActorInputBoundary, Object> & Serializable)x$5 -> {
                        x$5.onInternalError(reason);
                        return BoxedUnit.UNIT;
                    });
                    this.interpreter().execute(this.abortLimit());
                    this.interpreter().finish();
                }
                catch (Throwable throwable3) {
                    Option<Throwable> option;
                    Throwable throwable4 = throwable3;
                    if (throwable4 != null && !(option = NonFatal$.MODULE$.unapply(throwable4)).isEmpty()) {
                        break block8;
                    }
                    throw throwable3;
                }
            }
            finally {
                this.interpreterCompleted_$eq(true);
                this.outputs().foreach((Function1<ActorGraphInterpreter.ActorOutputBoundary, Object> & Serializable)x$6 -> {
                    x$6.fail(reason);
                    return BoxedUnit.UNIT;
                });
                this.inputs().foreach((Function1<ActorGraphInterpreter.BatchingActorInputBoundary, Object> & Serializable)x$7 -> {
                    x$7.cancel(reason);
                    return BoxedUnit.UNIT;
                });
            }
        }
    }

    public InterpreterSnapshot toSnapshot() {
        if (!this.isInitialized()) {
            return new UninitializedInterpreterImpl(Predef$.MODULE$.wrapRefArray((Object[])ArrayOps$.MODULE$.map$extension(Predef$.MODULE$.refArrayOps(ArrayOps$.MODULE$.zipWithIndex$extension(Predef$.MODULE$.refArrayOps(this.logics()))), (Function1<Tuple2, LogicSnapshotImpl> & Serializable)x0$1 -> {
                Tuple2 tuple2 = x0$1;
                if (tuple2 != null) {
                    GraphStageLogic logic = (GraphStageLogic)tuple2._1();
                    int idx = tuple2._2$mcI$sp();
                    return new LogicSnapshotImpl(idx, logic.toString(), logic.attributes());
                }
                throw new MatchError(tuple2);
            }, ClassTag$.MODULE$.apply(LogicSnapshotImpl.class))).toVector());
        }
        return this.interpreter().toSnapshot();
    }

    private final void AsyncInput$lzycompute$1() {
        GraphInterpreterShell graphInterpreterShell = this;
        synchronized (graphInterpreterShell) {
            if (this.AsyncInput$module == null) {
                this.AsyncInput$module = new GraphInterpreterShell$AsyncInput$(this);
            }
        }
    }

    private final void ResumeShell$lzycompute$1() {
        GraphInterpreterShell graphInterpreterShell = this;
        synchronized (graphInterpreterShell) {
            if (this.ResumeShell$module == null) {
                this.ResumeShell$module = new GraphInterpreterShell$ResumeShell$(this);
            }
        }
    }

    private final void Abort$lzycompute$1() {
        GraphInterpreterShell graphInterpreterShell = this;
        synchronized (graphInterpreterShell) {
            if (this.Abort$module == null) {
                this.Abort$module = new GraphInterpreterShell$Abort$(this);
            }
        }
    }

    public static final /* synthetic */ void $anonfun$interpreter$1(GraphInterpreterShell $this, GraphStageLogic logic, Object event, Promise promise, Function1 handler) {
        AsyncInput asyncInput = new AsyncInput($this, $this, logic, event, promise, handler);
        GraphInterpreter currentInterpreter = GraphInterpreter$.MODULE$.currentInterpreterOrNull();
        if (currentInterpreter == null || currentInterpreter.context() != $this.akka$stream$impl$fusing$GraphInterpreterShell$$self()) {
            ActorRef qual$1 = $this.akka$stream$impl$fusing$GraphInterpreterShell$$self();
            AsyncInput x$1 = asyncInput;
            ActorRef x$2 = qual$1.$bang$default$2(x$1);
            qual$1.$bang(x$1, x$2);
            return;
        }
        $this.enqueueToShortCircuit().apply(asyncInput);
    }

    public GraphInterpreterShell(GraphInterpreter.Connection[] connections, GraphStageLogic[] logics, Attributes attributes, ExtendedActorMaterializer mat) {
        this.connections = connections;
        this.logics = logics;
        this.attributes = attributes;
        this.mat = mat;
        this.akka$stream$impl$fusing$GraphInterpreterShell$$subscribesPending = 0;
        this.inputs = Nil$.MODULE$;
        this.outputs = Nil$.MODULE$;
        this.shellEventLimit = ((Attributes.InputBuffer)attributes.mandatoryAttribute(ClassTag$.MODULE$.apply(Attributes.InputBuffer.class))).max() * 16;
        this.abortLimit = this.shellEventLimit() * 2;
        this.resumeScheduled = false;
        this.interpreterCompleted = false;
        this.akka$stream$impl$fusing$GraphInterpreterShell$$waitingForShutdown = false;
        this.resume = new ResumeShell(this, this);
    }

    public class Abort
    implements ActorGraphInterpreter.BoundaryEvent,
    Product,
    Serializable {
        private final GraphInterpreterShell shell;
        public final /* synthetic */ GraphInterpreterShell $outer;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public GraphInterpreterShell shell() {
            return this.shell;
        }

        @Override
        public int execute(int eventLimit) {
            block0: {
                if (!this.akka$stream$impl$fusing$GraphInterpreterShell$Abort$$$outer().akka$stream$impl$fusing$GraphInterpreterShell$$waitingForShutdown()) break block0;
                this.akka$stream$impl$fusing$GraphInterpreterShell$Abort$$$outer().akka$stream$impl$fusing$GraphInterpreterShell$$subscribesPending_$eq(0);
                FiniteDuration subscriptionTimeout = ((ActorAttributes.StreamSubscriptionTimeout)this.akka$stream$impl$fusing$GraphInterpreterShell$Abort$$$outer().attributes().mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.StreamSubscriptionTimeout.class))).timeout();
                this.akka$stream$impl$fusing$GraphInterpreterShell$Abort$$$outer().tryAbort(new TimeoutException(new StringBuilder(149).append("Streaming actor has been already stopped processing (normally), but not all of its ").append("inputs or outputs have been subscribed in [").append(subscriptionTimeout).append("}]. Aborting actor now.").toString()));
            }
            return 0;
        }

        @Override
        public void cancel() {
        }

        public Abort copy(GraphInterpreterShell shell) {
            return new Abort(this.akka$stream$impl$fusing$GraphInterpreterShell$Abort$$$outer(), shell);
        }

        public GraphInterpreterShell copy$default$1() {
            return this.shell();
        }

        @Override
        public String productPrefix() {
            return "Abort";
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
                    return this.shell();
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
            return x$1 instanceof Abort;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "shell";
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
            if (!(object instanceof Abort)) return false;
            if (((Abort)object).akka$stream$impl$fusing$GraphInterpreterShell$Abort$$$outer() != this.akka$stream$impl$fusing$GraphInterpreterShell$Abort$$$outer()) return false;
            boolean bl = true;
            if (!bl) return false;
            Abort abort = (Abort)x$1;
            GraphInterpreterShell graphInterpreterShell = this.shell();
            GraphInterpreterShell graphInterpreterShell2 = abort.shell();
            if (graphInterpreterShell == null) {
                if (graphInterpreterShell2 != null) {
                    return false;
                }
            } else if (!graphInterpreterShell.equals(graphInterpreterShell2)) return false;
            if (!abort.canEqual(this)) return false;
            return true;
        }

        public /* synthetic */ GraphInterpreterShell akka$stream$impl$fusing$GraphInterpreterShell$Abort$$$outer() {
            return this.$outer;
        }

        public Abort(GraphInterpreterShell $outer, GraphInterpreterShell shell) {
            this.shell = shell;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            Product.$init$(this);
        }
    }

    public class AsyncInput
    implements ActorGraphInterpreter.BoundaryEvent,
    Product,
    Serializable {
        private final GraphInterpreterShell shell;
        private final GraphStageLogic logic;
        private final Object evt;
        private final Promise<Done> promise;
        private final Function1<Object, BoxedUnit> handler;
        public final /* synthetic */ GraphInterpreterShell $outer;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public GraphInterpreterShell shell() {
            return this.shell;
        }

        public GraphStageLogic logic() {
            return this.logic;
        }

        public Object evt() {
            return this.evt;
        }

        public Promise<Done> promise() {
            return this.promise;
        }

        public Function1<Object, BoxedUnit> handler() {
            return this.handler;
        }

        @Override
        @InternalStableApi
        public int execute(int eventLimit) {
            if (!this.akka$stream$impl$fusing$GraphInterpreterShell$AsyncInput$$$outer().akka$stream$impl$fusing$GraphInterpreterShell$$waitingForShutdown()) {
                this.akka$stream$impl$fusing$GraphInterpreterShell$AsyncInput$$$outer().interpreter().runAsyncInput(this.logic(), this.evt(), this.promise(), this.handler());
                if (eventLimit == 1 && this.akka$stream$impl$fusing$GraphInterpreterShell$AsyncInput$$$outer().interpreter().isSuspended()) {
                    this.akka$stream$impl$fusing$GraphInterpreterShell$AsyncInput$$$outer().sendResume(true);
                    return 0;
                }
                return this.akka$stream$impl$fusing$GraphInterpreterShell$AsyncInput$$$outer().runBatch(eventLimit - 1);
            }
            return eventLimit;
        }

        @Override
        public void cancel() {
        }

        public AsyncInput copy(GraphInterpreterShell shell, GraphStageLogic logic, Object evt, Promise<Done> promise, Function1<Object, BoxedUnit> handler) {
            return new AsyncInput(this.akka$stream$impl$fusing$GraphInterpreterShell$AsyncInput$$$outer(), shell, logic, evt, promise, handler);
        }

        public GraphInterpreterShell copy$default$1() {
            return this.shell();
        }

        public GraphStageLogic copy$default$2() {
            return this.logic();
        }

        public Object copy$default$3() {
            return this.evt();
        }

        public Promise<Done> copy$default$4() {
            return this.promise();
        }

        public Function1<Object, BoxedUnit> copy$default$5() {
            return this.handler();
        }

        @Override
        public String productPrefix() {
            return "AsyncInput";
        }

        @Override
        public int productArity() {
            return 5;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.shell();
                }
                case 1: {
                    return this.logic();
                }
                case 2: {
                    return this.evt();
                }
                case 3: {
                    return this.promise();
                }
                case 4: {
                    return this.handler();
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
            return x$1 instanceof AsyncInput;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "shell";
                }
                case 1: {
                    return "logic";
                }
                case 2: {
                    return "evt";
                }
                case 3: {
                    return "promise";
                }
                case 4: {
                    return "handler";
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
            if (!(object instanceof AsyncInput)) return false;
            if (((AsyncInput)object).akka$stream$impl$fusing$GraphInterpreterShell$AsyncInput$$$outer() != this.akka$stream$impl$fusing$GraphInterpreterShell$AsyncInput$$$outer()) return false;
            boolean bl = true;
            if (!bl) return false;
            AsyncInput asyncInput = (AsyncInput)x$1;
            GraphInterpreterShell graphInterpreterShell = this.shell();
            GraphInterpreterShell graphInterpreterShell2 = asyncInput.shell();
            if (graphInterpreterShell == null) {
                if (graphInterpreterShell2 != null) {
                    return false;
                }
            } else if (!graphInterpreterShell.equals(graphInterpreterShell2)) return false;
            GraphStageLogic graphStageLogic = this.logic();
            GraphStageLogic graphStageLogic2 = asyncInput.logic();
            if (graphStageLogic == null) {
                if (graphStageLogic2 != null) {
                    return false;
                }
            } else if (!graphStageLogic.equals(graphStageLogic2)) return false;
            if (!BoxesRunTime.equals(this.evt(), asyncInput.evt())) return false;
            Promise<Done> promise = this.promise();
            Promise<Done> promise2 = asyncInput.promise();
            if (promise == null) {
                if (promise2 != null) {
                    return false;
                }
            } else if (!promise.equals(promise2)) return false;
            Function1<Object, BoxedUnit> function1 = this.handler();
            Function1<Object, BoxedUnit> function12 = asyncInput.handler();
            if (function1 == null) {
                if (function12 != null) {
                    return false;
                }
            } else if (!function1.equals(function12)) return false;
            if (!asyncInput.canEqual(this)) return false;
            return true;
        }

        public /* synthetic */ GraphInterpreterShell akka$stream$impl$fusing$GraphInterpreterShell$AsyncInput$$$outer() {
            return this.$outer;
        }

        public AsyncInput(GraphInterpreterShell $outer, GraphInterpreterShell shell, GraphStageLogic logic, Object evt, Promise<Done> promise, Function1<Object, BoxedUnit> handler) {
            this.shell = shell;
            this.logic = logic;
            this.evt = evt;
            this.promise = promise;
            this.handler = handler;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            Product.$init$(this);
        }
    }

    public class ResumeShell
    implements ActorGraphInterpreter.BoundaryEvent,
    Product,
    Serializable {
        private final GraphInterpreterShell shell;
        public final /* synthetic */ GraphInterpreterShell $outer;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public GraphInterpreterShell shell() {
            return this.shell;
        }

        @Override
        public int execute(int eventLimit) {
            if (!this.akka$stream$impl$fusing$GraphInterpreterShell$ResumeShell$$$outer().akka$stream$impl$fusing$GraphInterpreterShell$$waitingForShutdown()) {
                if (this.akka$stream$impl$fusing$GraphInterpreterShell$ResumeShell$$$outer().interpreter().isSuspended()) {
                    return this.akka$stream$impl$fusing$GraphInterpreterShell$ResumeShell$$$outer().runBatch(eventLimit);
                }
                return eventLimit;
            }
            return eventLimit;
        }

        @Override
        public void cancel() {
        }

        public ResumeShell copy(GraphInterpreterShell shell) {
            return new ResumeShell(this.akka$stream$impl$fusing$GraphInterpreterShell$ResumeShell$$$outer(), shell);
        }

        public GraphInterpreterShell copy$default$1() {
            return this.shell();
        }

        @Override
        public String productPrefix() {
            return "ResumeShell";
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
                    return this.shell();
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
            return x$1 instanceof ResumeShell;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "shell";
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
            if (!(object instanceof ResumeShell)) return false;
            if (((ResumeShell)object).akka$stream$impl$fusing$GraphInterpreterShell$ResumeShell$$$outer() != this.akka$stream$impl$fusing$GraphInterpreterShell$ResumeShell$$$outer()) return false;
            boolean bl = true;
            if (!bl) return false;
            ResumeShell resumeShell = (ResumeShell)x$1;
            GraphInterpreterShell graphInterpreterShell = this.shell();
            GraphInterpreterShell graphInterpreterShell2 = resumeShell.shell();
            if (graphInterpreterShell == null) {
                if (graphInterpreterShell2 != null) {
                    return false;
                }
            } else if (!graphInterpreterShell.equals(graphInterpreterShell2)) return false;
            if (!resumeShell.canEqual(this)) return false;
            return true;
        }

        public /* synthetic */ GraphInterpreterShell akka$stream$impl$fusing$GraphInterpreterShell$ResumeShell$$$outer() {
            return this.$outer;
        }

        public ResumeShell(GraphInterpreterShell $outer, GraphInterpreterShell shell) {
            this.shell = shell;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            Product.$init$(this);
        }
    }
}

