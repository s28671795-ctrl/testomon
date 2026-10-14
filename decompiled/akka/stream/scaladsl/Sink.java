/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorRef;
import akka.stream.ActorMaterializer;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.Graph;
import akka.stream.Materializer;
import akka.stream.SinkShape;
import akka.stream.SourceShape;
import akka.stream.UniformFanOutShape;
import akka.stream.impl.LinearTraversalBuilder;
import akka.stream.scaladsl.Flow$;
import akka.stream.scaladsl.Keep$;
import akka.stream.scaladsl.RunnableGraph;
import akka.stream.scaladsl.Sink$;
import akka.stream.scaladsl.SinkQueueWithCancel;
import akka.stream.scaladsl.Source$;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.Option;
import scala.Tuple2;
import scala.collection.Factory;
import scala.collection.immutable.Seq;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\u0019}d\u0001\u0002 @\u0005\u0019C\u0001b\u0019\u0001\u0003\u0006\u0004%\t\u0005\u001a\u0005\tW\u0002\u0011\t\u0011)A\u0005K\"AA\u000e\u0001BC\u0002\u0013\u0005S\u000e\u0003\u0005o\u0001\t\u0005\t\u0015!\u0003S\u0011\u0015y\u0007\u0001\"\u0001q\u0011\u0015)\b\u0001\"\u0011w\u0011\u001d\t)\u0001\u0001C\u0001\u0003\u000fAq!!\b\u0001\t\u0003\ty\u0002C\u0004\u0002B\u0001!\t!a\u0011\t\u000f\u0005E\u0003\u0001\"\u0001\u0002T!9\u0011\u0011\u000e\u0001\u0005B\u0005-\u0004bBA<\u0001\u0011\u0005\u0013\u0011\u0010\u0005\b\u0003{\u0002A\u0011IA@\u0011\u001d\t)\t\u0001C!\u0003\u000fCq!!\"\u0001\t\u0003\nI\tC\u0004\u0002\u0006\u0002!\t%a$\t\u000f\u0005u\u0005\u0001\"\u0001\u0002 \"9\u0011\u0011\u001a\u0001\u0005B\u0005-waBAg\u007f!\u0005\u0011q\u001a\u0004\u0007}}B\t!!5\t\r=$B\u0011AAj\u0011\u0019aG\u0003\"\u0001\u0002V\"9\u00111\u001d\u000b\u0005\u0002\u0005\u0015\bbBA\u007f)\u0011\u0005\u0011q \u0005\b\u0005K!B\u0011\u0001B\u0014\u0011\u001d\u00119\u0006\u0006C\u0001\u00053BqAa\u001e\u0015\t\u0003\u0011I\bC\u0004\u0003\u0004R!\tA!\"\t\u000f\tEE\u0003\"\u0001\u0003\u0014\"9!Q\u0015\u000b\u0005\u0002\t\u001d\u0006b\u0002BZ)\u0011\u0005!Q\u0017\u0005\b\u0005\u0007$B\u0011\u0001Bc\u0011\u001d\u0011)\u000f\u0006C\u0001\u0005ODqAa7\u0015\t\u0003\u0011)\u0010C\u0004\u0004NQ!\taa\u0014\t\u000f\r%D\u0003\"\u0001\u0004l!91q\u000f\u000b\u0005\u0002\r-\u0004\u0002CB=)\u0001\u0006Ia!\u001c\t\u000f\rmD\u0003\"\u0001\u0004~!91\u0011\u0013\u000b\u0005\u0002\rM\u0005bBBU)\u0011\u000511\u0016\u0005\b\to!B\u0011\u0001C\u001d\u0011\u001d\u0019I\u000b\u0006C\u0001\tcBq\u0001\"'\u0015\t\u0003!Y\nC\u0004\u00056R!\t\u0001b.\t\u000f\u0011=G\u0003\"\u0001\u0005R\"9A\u0011\u001d\u000b\u0005\u0002\u0011\r\bb\u0002C\u007f)\u0011\u0005Aq \u0005\b\t{$B\u0011AC\u0018\u0011!)\u0019\u0005\u0006C\u0001\u0007\u0016\u0015\u0003bBC;)\u0011\u0005Qq\u000f\u0005\b\u000bk\"B\u0011ACF\u0011\u001d)\u0019\u0005\u0006C\u0001\u000b;C\u0011\"b.\u0015#\u0003%\t!\"/\t\u000f\u0015\u0005G\u0003\"\u0001\u0006D\"9Q\u0011\u0019\u000b\u0005\u0002\u0015]\u0007bBCr)\u0011\u0005QQ\u001d\u0005\b\r\u001f!B\u0011\u0001D\t\u0011\u001d1\t\u0004\u0006C\u0001\rgAqAb\u0013\u0015\t\u00031i\u0005C\u0004\u0007fQ!\tAb\u001a\u0003\tMKgn\u001b\u0006\u0003\u0001\u0006\u000b\u0001b]2bY\u0006$7\u000f\u001c\u0006\u0003\u0005\u000e\u000baa\u001d;sK\u0006l'\"\u0001#\u0002\t\u0005\\7.Y\u0002\u0001+\r9u+Y\n\u0004\u0001!s\u0005CA%M\u001b\u0005Q%\"A&\u0002\u000bM\u001c\u0017\r\\1\n\u00055S%AB!osJ+g\r\u0005\u0003P!J\u0003W\"A!\n\u0005E\u000b%!B$sCBD\u0007cA(T+&\u0011A+\u0011\u0002\n'&t7n\u00155ba\u0016\u0004\"AV,\r\u0001\u00111\u0001\f\u0001EC\u0002e\u0013!!\u00138\u0012\u0005ik\u0006CA%\\\u0013\ta&JA\u0004O_RD\u0017N\\4\u0011\u0005%s\u0016BA0K\u0005\r\te.\u001f\t\u0003-\u0006$aA\u0019\u0001\u0005\u0006\u0004I&aA'bi\u0006\u0001BO]1wKJ\u001c\u0018\r\u001c\"vS2$WM]\u000b\u0002KB\u0011a-[\u0007\u0002O*\u0011\u0001.Q\u0001\u0005S6\u0004H.\u0003\u0002kO\n1B*\u001b8fCJ$&/\u0019<feN\fGNQ;jY\u0012,'/A\tue\u00064XM]:bY\n+\u0018\u000e\u001c3fe\u0002\nQa\u001d5ba\u0016,\u0012AU\u0001\u0007g\"\f\u0007/\u001a\u0011\u0002\rqJg.\u001b;?)\r\t8\u000f\u001e\t\u0005e\u0002)\u0006-D\u0001@\u0011\u0015\u0019W\u00011\u0001f\u0011\u0015aW\u00011\u0001S\u0003!!xn\u0015;sS:<G#A<\u0011\u0005a|hBA=~!\tQ(*D\u0001|\u0015\taX)\u0001\u0004=e>|GOP\u0005\u0003}*\u000ba\u0001\u0015:fI\u00164\u0017\u0002BA\u0001\u0003\u0007\u0011aa\u0015;sS:<'B\u0001@K\u0003%\u0019wN\u001c;sC6\f\u0007/\u0006\u0003\u0002\n\u0005=A\u0003BA\u0006\u0003'\u0001RA\u001d\u0001\u0002\u000e\u0001\u00042AVA\b\t\u0019\t\tb\u0002b\u00013\n\u0019\u0011J\u001c\u001a\t\u000f\u0005Uq\u00011\u0001\u0002\u0018\u0005\ta\r\u0005\u0004J\u00033\ti!V\u0005\u0004\u00037Q%!\u0003$v]\u000e$\u0018n\u001c82\u0003\u001d\u0011XO\\,ji\",B!!\t\u0002(Q!\u00111EA\u001b)\u0011\t)#a\u000b\u0011\u0007Y\u000b9\u0003\u0002\u0004\u0002*!\u0011\r!\u0017\u0002\u0005\u001b\u0006$(\u0007C\u0004\u0002.!\u0001\u001d!a\f\u0002\u00195\fG/\u001a:jC2L'0\u001a:\u0011\u0007=\u000b\t$C\u0002\u00024\u0005\u0013A\"T1uKJL\u0017\r\\5{KJDq!a\u000e\t\u0001\u0004\tI$\u0001\u0004t_V\u00148-\u001a\t\u0007\u001fB\u000bY$!\n\u0011\t=\u000bi$V\u0005\u0004\u0003\u007f\t%aC*pkJ\u001cWm\u00155ba\u0016\fA#\\1q\u001b\u0006$XM]5bY&TX\r\u001a,bYV,W\u0003BA#\u0003\u0017\"B!a\u0012\u0002NA)!\u000fA+\u0002JA\u0019a+a\u0013\u0005\r\u0005%\u0012B1\u0001Z\u0011\u001d\t)\"\u0003a\u0001\u0003\u001f\u0002b!SA\rA\u0006%\u0013A\u00049sK6\u000bG/\u001a:jC2L'0\u001a\u000b\u0003\u0003+\"B!a\u0016\u0002hA1\u0011*!\u0017a\u0003;J1!a\u0017K\u0005\u0019!V\u000f\u001d7feA)!\u000fA+\u0002`A!\u0011\u0011MA2\u001b\u0005\u0019\u0015bAA3\u0007\n9aj\u001c;Vg\u0016$\u0007bBA\u0017\u0015\u0001\u000f\u0011qF\u0001\u000fo&$\b.\u0011;ue&\u0014W\u000f^3t)\r\t\u0018Q\u000e\u0005\b\u0003_Z\u0001\u0019AA9\u0003\u0011\tG\u000f\u001e:\u0011\u0007=\u000b\u0019(C\u0002\u0002v\u0005\u0013!\"\u0011;ue&\u0014W\u000f^3t\u00035\tG\rZ!uiJL'-\u001e;fgR\u0019\u0011/a\u001f\t\u000f\u0005=D\u00021\u0001\u0002r\u0005)a.Y7fIR\u0019\u0011/!!\t\r\u0005\rU\u00021\u0001x\u0003\u0011q\u0017-\\3\u0002\u000b\u0005\u001c\u0018P\\2\u0016\u0003E$2!]AF\u0011\u0019\tii\u0004a\u0001o\u0006QA-[:qCR\u001c\u0007.\u001a:\u0015\u000bE\f\t*a%\t\r\u00055\u0005\u00031\u0001x\u0011\u001d\t)\n\u0005a\u0001\u0003/\u000bq\"\u001b8qkR\u0014UO\u001a4feNK'0\u001a\t\u0004\u0013\u0006e\u0015bAAN\u0015\n\u0019\u0011J\u001c;\u0002\r\u0005\u001c(*\u0019<b+\u0011\t\t+a,\u0016\u0005\u0005\r\u0006\u0003CAS\u0003W\u000bi+!.\u000e\u0005\u0005\u001d&bAAU\u0003\u00069!.\u0019<bINd\u0017b\u0001 \u0002(B\u0019a+a,\u0005\u000f\u0005E\u0016C1\u0001\u00024\n\u0019!*\u00138\u0012\u0005i+&f\u00011\u00028.\u0012\u0011\u0011\u0018\t\u0005\u0003w\u000b)-\u0004\u0002\u0002>*!\u0011qXAa\u0003%)hn\u00195fG.,GMC\u0002\u0002D*\u000b!\"\u00198o_R\fG/[8o\u0013\u0011\t9-!0\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007hKR\fE\u000f\u001e:jEV$Xm]\u000b\u0003\u0003c\nAaU5oWB\u0011!\u000fF\n\u0003)!#\"!a4\u0016\t\u0005]\u0017Q\u001c\u000b\u0005\u00033\f\t\u000f\u0005\u0003P'\u0006m\u0007c\u0001,\u0002^\u00121\u0011q\u001c\fC\u0002e\u0013\u0011\u0001\u0016\u0005\u0007\u0003\u00073\u0002\u0019A<\u0002\u0013\u0019\u0014x.\\$sCBDWCBAt\u0003[\f\t\u0010\u0006\u0003\u0002j\u0006U\bC\u0002:\u0001\u0003W\fy\u000fE\u0002W\u0003[$a!a8\u0018\u0005\u0004I\u0006c\u0001,\u0002r\u00121\u00111_\fC\u0002e\u0013\u0011!\u0014\u0005\b\u0003o<\u0002\u0019AA}\u0003\u00059\u0007CB(Q\u0003w\fy\u000f\u0005\u0003P'\u0006-\u0018\u0001\u00054s_6l\u0015\r^3sS\u0006d\u0017N_3s+\u0019\u0011\tAa\u0002\u0003\u0018Q!!1\u0001B\r!\u0019\u0011\bA!\u0002\u0003\nA\u0019aKa\u0002\u0005\r\u0005}\u0007D1\u0001Z!\u0019\u0011YA!\u0005\u0003\u00165\u0011!Q\u0002\u0006\u0004\u0005\u001fQ\u0015AC2p]\u000e,(O]3oi&!!1\u0003B\u0007\u0005\u00191U\u000f^;sKB\u0019aKa\u0006\u0005\r\u0005M\bD1\u0001Z\u0011\u001d\u0011Y\u0002\u0007a\u0001\u0005;\tqAZ1di>\u0014\u0018\u0010E\u0005J\u0005?\ty#!\u001d\u0003$%\u0019!\u0011\u0005&\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004C\u0002:\u0001\u0005\u000b\u0011)\"A\u0003tKR,\b/\u0006\u0004\u0003*\t=\"Q\u0007\u000b\u0005\u0005W\u00119\u0004\u0005\u0004s\u0001\t5\"\u0011\u0007\t\u0004-\n=BABAp3\t\u0007\u0011\f\u0005\u0004\u0003\f\tE!1\u0007\t\u0004-\nUBABAz3\t\u0007\u0011\fC\u0004\u0003\u001ce\u0001\rA!\u000f\u0011\u0013%\u0013yBa\u000f\u0002r\t\u0005\u0003cA(\u0003>%\u0019!qH!\u0003#\u0005\u001bGo\u001c:NCR,'/[1mSj,'\u000f\u0005\u0004s\u0001\t5\"1\u0007\u0015\f3\t\u0015#1\nB'\u0005#\u0012\u0019\u0006E\u0002J\u0005\u000fJ1A!\u0013K\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\t\u0011y%\u0001\u0010Vg\u0016\u0004sE\u001a:p[6\u000bG/\u001a:jC2L'0\u001a:(A%t7\u000f^3bI\u0006)1/\u001b8dK\u0006\u0012!QK\u0001\u0006e92d\u0006M\u0001\u000fMJ|WnU;cg\u000e\u0014\u0018NY3s+\u0011\u0011YF!\u0019\u0015\t\tu#1\r\t\u0007e\u0002\u0011y&a\u0018\u0011\u0007Y\u0013\t\u0007\u0002\u0004\u0002`j\u0011\r!\u0017\u0005\b\u0005KR\u0002\u0019\u0001B4\u0003)\u0019XOY:de&\u0014WM\u001d\t\u0007\u0005S\u0012\u0019Ha\u0018\u000e\u0005\t-$\u0002\u0002B7\u0005_\nqB]3bGRLg/Z:ue\u0016\fWn\u001d\u0006\u0003\u0005c\n1a\u001c:h\u0013\u0011\u0011)Ha\u001b\u0003\u0015M+(m]2sS\n,'/A\u0005dC:\u001cW\r\u001c7fIV!!1\u0010BA+\t\u0011i\b\u0005\u0004s\u0001\t}\u0014q\f\t\u0004-\n\u0005EABAp7\t\u0007\u0011,\u0001\u0003iK\u0006$W\u0003\u0002BD\u0005\u001b+\"A!#\u0011\rI\u0004!1\u0012BH!\r1&Q\u0012\u0003\u0007\u0003?d\"\u0019A-\u0011\r\t-!\u0011\u0003BF\u0003)AW-\u00193PaRLwN\\\u000b\u0005\u0005+\u0013Y*\u0006\u0002\u0003\u0018B1!\u000f\u0001BM\u0005;\u00032A\u0016BN\t\u0019\ty.\bb\u00013B1!1\u0002B\t\u0005?\u0003R!\u0013BQ\u00053K1Aa)K\u0005\u0019y\u0005\u000f^5p]\u0006!A.Y:u+\u0011\u0011IKa,\u0016\u0005\t-\u0006C\u0002:\u0001\u0005[\u0013\t\fE\u0002W\u0005_#a!a8\u001f\u0005\u0004I\u0006C\u0002B\u0006\u0005#\u0011i+\u0001\u0006mCN$x\n\u001d;j_:,BAa.\u0003>V\u0011!\u0011\u0018\t\u0007e\u0002\u0011YLa0\u0011\u0007Y\u0013i\f\u0002\u0004\u0002`~\u0011\r!\u0017\t\u0007\u0005\u0017\u0011\tB!1\u0011\u000b%\u0013\tKa/\u0002\u0011Q\f7.\u001a'bgR,BAa2\u0003NR!!\u0011\u001aBq!\u0019\u0011\bAa3\u0003PB\u0019aK!4\u0005\r\u0005}\u0007E1\u0001Z!\u0019\u0011YA!\u0005\u0003RB1!1\u001bBo\u0005\u0017l!A!6\u000b\t\t]'\u0011\\\u0001\nS6lW\u000f^1cY\u0016T1Aa7K\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0005?\u0014)NA\u0002TKFDqAa9!\u0001\u0004\t9*A\u0001o\u0003\r\u0019X-]\u000b\u0005\u0005S\u0014y/\u0006\u0002\u0003lB1!\u000f\u0001Bw\u0005c\u00042A\u0016Bx\t\u0019\ty.\tb\u00013B1!1\u0002B\t\u0005g\u0004bAa5\u0003^\n5XC\u0002B|\u0005{\u001c\u0019\u0001\u0006\u0003\u0003z\u000e\u001d\u0001C\u0002:\u0001\u0005w\u0014y\u0010E\u0002W\u0005{$a!a8#\u0005\u0004I\u0006C\u0002B\u0006\u0005#\u0019\t\u0001E\u0002W\u0007\u0007!aa!\u0002#\u0005\u0004I&\u0001\u0002+iCRDqa!\u0003#\u0001\b\u0019Y!A\u0002dE\u001a\u0004\u0002b!\u0004\u0004(\tm8Q\u0006\b\u0005\u0007\u001f\u0019\tC\u0004\u0003\u0004\u0012\rma\u0002BB\n\u0007/q1A_B\u000b\u0013\u0005!\u0015bAB\r\u0007\u0006!Q\u000f^5m\u0013\u0011\u0019iba\b\u0002\u000f\r\u001cw.\u001c9bi*\u00191\u0011D\"\n\t\r\r2QE\u0001\ba\u0006\u001c7.Y4f\u0015\u0011\u0019iba\b\n\t\r%21\u0006\u0002\b\r\u0006\u001cGo\u001c:z\u0015\u0011\u0019\u0019c!\n\u0013\r\r=2\u0011AB\u001a\r\u0019\u0019\t\u0004\u0006\u0001\u0004.\taAH]3gS:,W.\u001a8u}A\"1QGB\u001f!\u0019\u0011\u0019na\u000e\u0004<%!1\u0011\bBk\u0005!IE/\u001a:bE2,\u0007c\u0001,\u0004>\u0011Y1qHB!\u0003\u0003\u0005\tQ!\u0001Z\u0005\ryF%\r\u0005\b\u0007\u0013\u0011\u00039AB\"!!\u0019iaa\n\u0004F\r\u001d\u0003c\u0001,\u0003~J11\u0011JB&\u0007g1aa!\r\u0015\u0001\r\u001d\u0003c\u0001,\u0004\u0004\u0005Y\u0011m\u001d)vE2L7\u000f[3s+\u0011\u0019\tfa\u0016\u0015\t\rM3q\f\t\u0007e\u0002\u0019)f!\u0017\u0011\u0007Y\u001b9\u0006\u0002\u0004\u0002`\u000e\u0012\r!\u0017\t\u0007\u0005S\u001aYf!\u0016\n\t\ru#1\u000e\u0002\n!V\u0014G.[:iKJDqa!\u0019$\u0001\u0004\u0019\u0019'\u0001\u0004gC:|W\u000f\u001e\t\u0004\u0013\u000e\u0015\u0014bAB4\u0015\n9!i\\8mK\u0006t\u0017AB5h]>\u0014X-\u0006\u0002\u0004nA)!\u000fA/\u0004pA1!1\u0002B\t\u0007c\u0002B!!\u0019\u0004t%\u00191QO\"\u0003\t\u0011{g.Z\u0001\u0006]\u00164XM]\u0001\u0007?:,g/\u001a:\u0002\u000f\u0019|'/Z1dQV!1qPBC)\u0011\u0019\tia\"\u0011\rI\u000411QB8!\r16Q\u0011\u0003\u0007\u0003?<#\u0019A-\t\u000f\u0005Uq\u00051\u0001\u0004\nB9\u0011*!\u0007\u0004\u0004\u000e-\u0005cA%\u0004\u000e&\u00191q\u0012&\u0003\tUs\u0017\u000e^\u0001\rM>\u0014X-Y2i\u0003NLhnY\u000b\u0005\u0007+\u001bi\n\u0006\u0003\u0004\u0018\u000e\u0015F\u0003BBM\u0007?\u0003bA\u001d\u0001\u0004\u001c\u000e=\u0004c\u0001,\u0004\u001e\u00121\u0011q\u001c\u0015C\u0002eCq!!\u0006)\u0001\u0004\u0019\t\u000bE\u0004J\u00033\u0019Yja)\u0011\r\t-!\u0011CBF\u0011\u001d\u00199\u000b\u000ba\u0001\u0003/\u000b1\u0002]1sC2dW\r\\5t[\u000691m\\7cS:,WCBBW\u0007k\u001b9\r\u0006\u0005\u00040\u000euH1\u0002C\r)\u0011\u0019\tla.\u0011\rI\u000411WA0!\r16Q\u0017\u0003\u0007\u0003?L#\u0019A-\t\u000f\re\u0016\u00061\u0001\u0004<\u0006qa-\u00198PkR\u001cFO]1uK\u001eL\bcB%\u0002\u001a\u0005]5Q\u0018\t\u0007\u001fB\u001by,a\u0018\u0011\u000f=\u001b\tma-\u0004F&\u001911Y!\u0003%Us\u0017NZ8s[\u001a\u000bgnT;u'\"\f\u0007/\u001a\t\u0004-\u000e\u001dGABBeS\t\u0007\u0011LA\u0001VQ\u0019\u00199l!4\u0004TB\u0019\u0011ja4\n\u0007\rE'J\u0001\beKB\u0014XmY1uK\u0012t\u0015-\\32\u000f}\u0019)na7\u0004vB\u0019\u0011ja6\n\u0007\re'J\u0001\u0004Ts6\u0014w\u000e\\\u0019\nG\ru7\u0011]Bu\u0007G$Ba!6\u0004`\"1\u00111Q#A\u0002]LAaa9\u0004f\u0006)\u0011\r\u001d9ms*\u00191q\u001d&\u0002\rMKXNY8mc%\u001931^By\u0007g\u001c9O\u0004\u0003\u0004n\u000eEhb\u0001>\u0004p&\t1*C\u0002\u0004h*\u000bd\u0001JBw\u0007_\\\u0015'B\u0013\u0004x\u000eexBAB}C\t\u0019Y0\u0001\u0005tiJ\fG/Z4z\u0011\u001d\u0019y0\u000ba\u0001\t\u0003\tQAZ5sgR\u0004D\u0001b\u0001\u0005\bA1!\u000fABc\t\u000b\u00012A\u0016C\u0004\t-!Ia!@\u0002\u0002\u0003\u0005)\u0011A-\u0003\u0007}##\u0007C\u0004\u0005\u000e%\u0002\r\u0001b\u0004\u0002\rM,7m\u001c8ea\u0011!\t\u0002\"\u0006\u0011\rI\u00041Q\u0019C\n!\r1FQ\u0003\u0003\f\t/!Y!!A\u0001\u0002\u000b\u0005\u0011LA\u0002`IMBq\u0001b\u0007*\u0001\u0004!i\"\u0001\u0003sKN$\b#B%\u0005 \u0011\r\u0012b\u0001C\u0011\u0015\nQAH]3qK\u0006$X\r\u001a 1\t\u0011\u0015B\u0011\u0006\t\u0007e\u0002\u0019)\rb\n\u0011\u0007Y#I\u0003B\u0006\u0005,\u00115\u0012\u0011!A\u0001\u0006\u0003I&aA0%i!9A1D\u0015A\u0002\u0011=\u0002#B%\u0005 \u0011E\u0002\u0007\u0002C\u001a\tS\u0001bA\u001d\u0001\u00056\u0011\u001d\u0002c\u0001,\u0004H\u0006Q1m\\7cS:,W*\u0019;\u0016\u0019\u0011mBQ\tC4\t'\"I\u0006\"\u0013\u0015\r\u0011uB\u0011\u000eC7)\u0011!y\u0004\"\u0018\u0015\t\u0011\u0005C1\n\t\u0007e\u0002!\u0019\u0005b\u0012\u0011\u0007Y#)\u0005\u0002\u0004\u0002`*\u0012\r!\u0017\t\u0004-\u0012%CABAzU\t\u0007\u0011\fC\u0004\u0005N)\u0002\r\u0001b\u0014\u0002\t5\fGO\u0012\t\n\u0013\n}A\u0011\u000bC,\t\u000f\u00022A\u0016C*\t\u0019!)F\u000bb\u00013\n\u0011Q*\r\t\u0004-\u0012eCA\u0002C.U\t\u0007\u0011L\u0001\u0002Ne!91\u0011\u0018\u0016A\u0002\u0011}\u0003cB%\u0002\u001a\u0005]E\u0011\r\t\u0007\u001fB#\u0019'a\u0018\u0011\u000f=\u001b\t\rb\u0011\u0005fA\u0019a\u000bb\u001a\u0005\r\r%'F1\u0001Z\u0011\u001d\u0019yP\u000ba\u0001\tW\u0002bA\u001d\u0001\u0005f\u0011E\u0003b\u0002C\u0007U\u0001\u0007Aq\u000e\t\u0007e\u0002!)\u0007b\u0016\u0016\u0011\u0011MD1\u0010CG\t\u0003#B\u0001\"\u001e\u0005\u0010R!Aq\u000fCB!\u0019\u0011\b\u0001\"\u001f\u0005~A\u0019a\u000bb\u001f\u0005\r\u0005}7F1\u0001Z!\u0019\u0011\u0019N!8\u0005\u0000A\u0019a\u000b\"!\u0005\r\u0005M8F1\u0001Z\u0011\u001d\u0019Il\u000ba\u0001\t\u000b\u0003r!SA\r\u0003/#9\t\u0005\u0004P!\u0012%\u0015q\f\t\b\u001f\u000e\u0005G\u0011\u0010CF!\r1FQ\u0012\u0003\u0007\u0007\u0013\\#\u0019A-\t\u000f\u0011E5\u00061\u0001\u0005\u0014\u0006)1/\u001b8lgB1!1\u001bBo\t+\u0003ba\u0014)\u0005\u0018\u0012}\u0004\u0003B(T\t\u0017\u000bAAZ8mIV1AQ\u0014CV\tK#B\u0001b(\u00052R!A\u0011\u0015CW!\u0019\u0011\b\u0001b)\u0005(B\u0019a\u000b\"*\u0005\r\u0005}GF1\u0001Z!\u0019\u0011YA!\u0005\u0005*B\u0019a\u000bb+\u0005\r\r%GF1\u0001Z\u0011\u001d\t)\u0002\fa\u0001\t_\u0003\u0012\"\u0013B\u0010\tS#\u0019\u000b\"+\t\u000f\u0011MF\u00061\u0001\u0005*\u0006!!0\u001a:p\u0003%1w\u000e\u001c3Bgft7-\u0006\u0004\u0005:\u0012\u001dG\u0011\u0019\u000b\u0005\tw#i\r\u0006\u0003\u0005>\u0012%\u0007C\u0002:\u0001\t\u007f#\u0019\rE\u0002W\t\u0003$a!a8.\u0005\u0004I\u0006C\u0002B\u0006\u0005#!)\rE\u0002W\t\u000f$aa!3.\u0005\u0004I\u0006bBA\u000b[\u0001\u0007A1\u001a\t\n\u0013\n}AQ\u0019C`\t\u0007Dq\u0001b-.\u0001\u0004!)-\u0001\u0004sK\u0012,8-Z\u000b\u0005\t'$I\u000e\u0006\u0003\u0005V\u0012u\u0007C\u0002:\u0001\t/$Y\u000eE\u0002W\t3$a!a8/\u0005\u0004I\u0006C\u0002B\u0006\u0005#!9\u000eC\u0004\u0002\u00169\u0002\r\u0001b8\u0011\u0013%\u0013y\u0002b6\u0005X\u0012]\u0017AC8o\u0007>l\u0007\u000f\\3uKV!AQ\u001dCv)\u0011!9\u000f\"<\u0011\rI\u0004A\u0011^A0!\r1F1\u001e\u0003\u0007\u0003?|#\u0019A-\t\u000f\u0011=x\u00061\u0001\u0005r\u0006A1-\u00197mE\u0006\u001c7\u000eE\u0004J\u00033!\u0019pa#\u0011\r\u0011UH\u0011`B9\u001b\t!9PC\u0002\u0004\u001a)KA\u0001b?\u0005x\n\u0019AK]=\u0002\u0011\u0005\u001cGo\u001c:SK\u001a,B!\"\u0001\u0006\bQAQ1AC\u0005\u000b3)i\u0002\u0005\u0004s\u0001\u0015\u0015\u0011q\f\t\u0004-\u0016\u001dAABApa\t\u0007\u0011\fC\u0004\u0006\fA\u0002\r!\"\u0004\u0002\u0007I,g\r\u0005\u0003\u0006\u0010\u0015UQBAC\t\u0015\r)\u0019bQ\u0001\u0006C\u000e$xN]\u0005\u0005\u000b/)\tB\u0001\u0005BGR|'OU3g\u0011\u0019)Y\u0002\ra\u0001;\u0006\trN\\\"p[BdW\r^3NKN\u001c\u0018mZ3\t\u000f\u0015}\u0001\u00071\u0001\u0006\"\u0005\u0001rN\u001c$bS2,(/Z'fgN\fw-\u001a\t\u0007\u0013\u0006eQ1E/\u0011\t\u0015\u0015R\u0011\u0006\b\u0005\u0007[,9#C\u0002\u0004$)KA!b\u000b\u0006.\tIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0004\u0007GQU\u0003BC\u0019\u000bo!b!b\r\u0006:\u0015m\u0002C\u0002:\u0001\u000bk\ty\u0006E\u0002W\u000bo!a!a82\u0005\u0004I\u0006bBC\u0006c\u0001\u0007QQ\u0002\u0005\u0007\u000b7\t\u0004\u0019A/)\u0017E\u0012)Ea\u0013\u0006@\tE#1K\u0011\u0003\u000b\u0003\nQ(V:fAY\f'/[1oi\u0002\n7mY3qi&tw\r\t2pi\"\u0004sN\u001c\u0011d_6\u0004H.\u001a;fA\u0005tG\rI8oA\u0019\f\u0017\u000e\\;sK\u0002jWm]:bO\u0016\fq\"Y2u_J\u0014VMZ,ji\"\f5m[\u000b\u0005\u000b\u000f*i\u0005\u0006\b\u0006J\u0015=S\u0011KC-\u000b?*)'b\u001a\u0011\rI\u0004Q1JA0!\r1VQ\n\u0003\u0007\u0003?\u0014$\u0019A-\t\u000f\u0015-!\u00071\u0001\u0006\u000e!9Q1\u000b\u001aA\u0002\u0015U\u0013AD7fgN\fw-Z!eCB$XM\u001d\t\b\u0013\u0006eQQBC,!\u0019I\u0015\u0011DC&;\"9Q1\f\u001aA\u0002\u0015u\u0013!D8o\u0013:LG/T3tg\u0006<W\r\u0005\u0004J\u00033)i!\u0018\u0005\b\u000bC\u0012\u0004\u0019AC2\u0003)\t7m['fgN\fw-\u001a\t\u0005\u0013\n\u0005V\f\u0003\u0004\u0006\u001cI\u0002\r!\u0018\u0005\b\u000b?\u0011\u0004\u0019AC\u0011Q\r\u0011T1\u000e\t\u0005\u000b[*\t(\u0004\u0002\u0006p)\u0019\u00111Y\"\n\t\u0015MTq\u000e\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018.\u0001\rbGR|'OU3g/&$\bNQ1dWB\u0014Xm]:ve\u0016,B!\"\u001f\u0006\u0000QaQ1PCA\u000b\u0007+))b\"\u0006\nB1!\u000fAC?\u0003?\u00022AVC@\t\u0019\tyn\rb\u00013\"9Q1B\u001aA\u0002\u00155\u0001BBC.g\u0001\u0007Q\f\u0003\u0004\u0006bM\u0002\r!\u0018\u0005\u0007\u000b7\u0019\u0004\u0019A/\t\u000f\u0015}1\u00071\u0001\u0006\"U!QQRCJ)))y)\"&\u0006\u0018\u0016eU1\u0014\t\u0007e\u0002)\t*a\u0018\u0011\u0007Y+\u0019\n\u0002\u0004\u0002`R\u0012\r!\u0017\u0005\b\u000b\u0017!\u0004\u0019AC\u0007\u0011\u0019)Y\u0006\u000ea\u0001;\"1Q1\u0004\u001bA\u0002uCq!b\b5\u0001\u0004)\t#\u0006\u0003\u0006 \u0016\u0015F\u0003DCQ\u000bO+I+b+\u0006.\u0016=\u0006C\u0002:\u0001\u000bG\u000by\u0006E\u0002W\u000bK#a!a86\u0005\u0004I\u0006bBC\u0006k\u0001\u0007QQ\u0002\u0005\u0007\u000b7*\u0004\u0019A/\t\r\u0015\u0005T\u00071\u0001^\u0011\u0019)Y\"\u000ea\u0001;\"IQqD\u001b\u0011\u0002\u0003\u0007Q\u0011\u0005\u0015\fk\t\u0015#1JCZ\u0005#\u0012\u0019&\t\u0002\u00066\u0006qUk]3!C\u000e$xN\u001d*fM^KG\u000f\u001b\"bG.\u0004(/Z:tkJ,\u0007%Y2dKB$\u0018N\\4!G>l\u0007\u000f\\3uS>t\u0007%\u00198eA\u0019\f\u0017\u000e\\;sK\u0002j\u0017\r^2iKJ\u001c\b%\u001b8ti\u0016\fG-A\rbGR|'OU3g/&$\b.Q2lI\u0011,g-Y;mi\u0012*T\u0003BC^\u000b\u007f+\"!\"0+\t\u0015\u0005\u0012q\u0017\u0003\u0007\u0003?4$\u0019A-\u0002\u000bE,X-^3\u0016\t\u0015\u0015W1\u001a\u000b\u0005\u000b\u000f,\u0019\u000e\u0005\u0004s\u0001\u0015%WQ\u001a\t\u0004-\u0016-GABApo\t\u0007\u0011\fE\u0003s\u000b\u001f,I-C\u0002\u0006R~\u00121cU5oWF+X-^3XSRD7)\u00198dK2Dq!\"68\u0001\u0004\t9*\u0001\nnCb\u001cuN\\2veJ,g\u000e\u001e)vY2\u001cX\u0003BCm\u000b?$\"!b7\u0011\rI\u0004QQ\\Cq!\r1Vq\u001c\u0003\u0007\u0003?D$\u0019A-\u0011\u000bI,y-\"8\u0002\u00111\f'0_%oSR,b!b:\u0006n\u0016MHCBCu\u000bk,y\u0010\u0005\u0004s\u0001\u0015-Xq\u001e\t\u0004-\u00165HABAps\t\u0007\u0011\f\u0005\u0004\u0003\f\tEQ\u0011\u001f\t\u0004-\u0016MHABAzs\t\u0007\u0011\fC\u0004\u0006xf\u0002\r!\"?\u0002\u0017MLgn\u001b$bGR|'/\u001f\t\b\u0013\u0006eQ1^C~!\u0019\u0011YA!\u0005\u0006~B1!\u000fACv\u000bcDqA\"\u0001:\u0001\u00041\u0019!\u0001\u0005gC2d'-Y2l!\u0015IeQACy\u0013\r19A\u0013\u0002\n\rVt7\r^5p]BB3\"\u000fB#\u0005\u00172YA!\u0015\u0003T\u0005\u0012aQB\u0001N+N,\u0007eJ*j].tC.\u0019>z\rV$XO]3TS:\\w\u0005I5oA\r|WNY5oCRLwN\u001c\u0011xSRD\u0007e\n$m_^t\u0003O]3gSb\fe\u000e\u001a+bS2D\u0013'K\u0014!S:\u001cH/Z1e\u00035a\u0017M_=J]&$\u0018i]=oGV1a1\u0003D\r\rC!BA\"\u0006\u0007$A1!\u000f\u0001D\f\r7\u00012A\u0016D\r\t\u0019\tyN\u000fb\u00013B1!1\u0002B\t\r;\u0001R!\u0013BQ\r?\u00012A\u0016D\u0011\t\u0019\t\u0019P\u000fb\u00013\"9Qq\u001f\u001eA\u0002\u0019\u0015\u0002#B%\u0007\u0006\u0019\u001d\u0002C\u0002B\u0006\u0005#1I\u0003\u0005\u0004s\u0001\u0019]aq\u0004\u0015\fu\t\u0015#1\nD\u0017\u0005#\u0012\u0019&\t\u0002\u00070\u0005\tSk]3!OMKgn\u001b\u0018mCjLh)\u001e;ve\u0016\u001c\u0016N\\6(A%t7\u000f^3bI\u0006Qa-\u001e;ve\u0016\u001c\u0016N\\6\u0016\r\u0019Ub1\bD!)\u001119Db\u0011\u0011\rI\u0004a\u0011\bD\u001f!\r1f1\b\u0003\u0007\u0003?\\$\u0019A-\u0011\r\t-!\u0011\u0003D !\r1f\u0011\t\u0003\u0007\u0003g\\$\u0019A-\t\u000f\u0019\u00153\b1\u0001\u0007H\u00051a-\u001e;ve\u0016\u0004bAa\u0003\u0003\u0012\u0019%\u0003C\u0002:\u0001\rs1y$\u0001\u0005mCjL8+\u001b8l+\u00191yE\"\u0016\u0007\\Q!a\u0011\u000bD/!\u0019\u0011\bAb\u0015\u0007XA\u0019aK\"\u0016\u0005\r\u0005}GH1\u0001Z!\u0019\u0011YA!\u0005\u0007ZA\u0019aKb\u0017\u0005\r\u0005MHH1\u0001Z\u0011\u001d1y\u0006\u0010a\u0001\rC\naa\u0019:fCR,\u0007#B%\u0007\u0006\u0019\r\u0004C\u0002:\u0001\r'2I&\u0001\bmCjLh)\u001e;ve\u0016\u001c\u0016N\\6\u0016\r\u0019%dq\u000eD;)\u00111YGb\u001e\u0011\rI\u0004aQ\u000eD9!\r1fq\u000e\u0003\u0007\u0003?l$\u0019A-\u0011\r\t-!\u0011\u0003D:!\r1fQ\u000f\u0003\u0007\u0003gl$\u0019A-\t\u000f\u0019}S\b1\u0001\u0007zA)\u0011J\"\u0002\u0007|A1!1\u0002B\t\r{\u0002bA\u001d\u0001\u0007n\u0019M\u0004")
public final class Sink<In, Mat>
implements Graph<SinkShape<In>, Mat> {
    private final LinearTraversalBuilder traversalBuilder;
    private final SinkShape<In> shape;

    public static <T, M> Sink<T, Future<M>> lazyFutureSink(Function0<Future<Sink<T, M>>> create) {
        return Sink$.MODULE$.lazyFutureSink(create);
    }

    public static <T, M> Sink<T, Future<M>> lazySink(Function0<Sink<T, M>> create) {
        return Sink$.MODULE$.lazySink(create);
    }

    public static <T, M> Sink<T, Future<M>> futureSink(Future<Sink<T, M>> future) {
        return Sink$.MODULE$.futureSink(future);
    }

    public static <T, M> Sink<T, Future<Option<M>>> lazyInitAsync(Function0<Future<Sink<T, M>>> sinkFactory) {
        return Sink$.MODULE$.lazyInitAsync(sinkFactory);
    }

    public static <T, M> Sink<T, Future<M>> lazyInit(Function1<T, Future<Sink<T, M>>> sinkFactory, Function0<M> fallback) {
        return Sink$.MODULE$.lazyInit(sinkFactory, fallback);
    }

    public static <T> Sink<T, SinkQueueWithCancel<T>> queue() {
        return Sink$.MODULE$.queue();
    }

    public static <T> Sink<T, SinkQueueWithCancel<T>> queue(int maxConcurrentPulls) {
        return Sink$.MODULE$.queue(maxConcurrentPulls);
    }

    public static <T> Function1<Throwable, Object> actorRefWithAck$default$5() {
        return Sink$.MODULE$.actorRefWithAck$default$5();
    }

    public static <T> Sink<T, NotUsed> actorRefWithAck(ActorRef ref, Object onInitMessage, Object ackMessage, Object onCompleteMessage, Function1<Throwable, Object> onFailureMessage) {
        return Sink$.MODULE$.actorRefWithAck(ref, onInitMessage, ackMessage, onCompleteMessage, onFailureMessage);
    }

    public static <T> Sink<T, NotUsed> actorRefWithBackpressure(ActorRef ref, Object onInitMessage, Object onCompleteMessage, Function1<Throwable, Object> onFailureMessage) {
        return Sink$.MODULE$.actorRefWithBackpressure(ref, onInitMessage, onCompleteMessage, onFailureMessage);
    }

    public static <T> Sink<T, NotUsed> actorRefWithBackpressure(ActorRef ref, Object onInitMessage, Object ackMessage, Object onCompleteMessage, Function1<Throwable, Object> onFailureMessage) {
        return Sink$.MODULE$.actorRefWithBackpressure(ref, onInitMessage, ackMessage, onCompleteMessage, onFailureMessage);
    }

    public static <T> Sink<T, NotUsed> actorRef(ActorRef ref, Object onCompleteMessage) {
        return Sink$.MODULE$.actorRef(ref, onCompleteMessage);
    }

    public static <T> Sink<T, NotUsed> actorRef(ActorRef ref, Object onCompleteMessage, Function1<Throwable, Object> onFailureMessage) {
        return Sink$.MODULE$.actorRef(ref, onCompleteMessage, onFailureMessage);
    }

    public static <T> Sink<T, NotUsed> onComplete(Function1<Try<Done>, BoxedUnit> callback) {
        return Sink$.MODULE$.onComplete(callback);
    }

    public static <T> Sink<T, Future<T>> reduce(Function2<T, T, T> f) {
        return Sink$.MODULE$.reduce(f);
    }

    public static <U, T> Sink<T, Future<U>> foldAsync(U zero, Function2<U, T, Future<U>> f) {
        return Sink$.MODULE$.foldAsync(zero, f);
    }

    public static <U, T> Sink<T, Future<U>> fold(U zero, Function2<U, T, U> f) {
        return Sink$.MODULE$.fold(zero, f);
    }

    public static <T, U, M> Sink<T, Seq<M>> combine(Seq<Graph<SinkShape<U>, M>> sinks, Function1<Object, Graph<UniformFanOutShape<T, U>, NotUsed>> fanOutStrategy) {
        return Sink$.MODULE$.combine(sinks, fanOutStrategy);
    }

    public static <T, U, M1, M2, M> Sink<T, M> combineMat(Sink<U, M1> first, Sink<U, M2> second, Function1<Object, Graph<UniformFanOutShape<T, U>, NotUsed>> fanOutStrategy, Function2<M1, M2, M> matF) {
        return Sink$.MODULE$.combineMat(first, second, fanOutStrategy, matF);
    }

    public static <T, U> Sink<T, NotUsed> combine(Sink<U, ?> first, Sink<U, ?> second, Seq<Sink<U, ?>> rest, Function1<Object, Graph<UniformFanOutShape<T, U>, NotUsed>> fanOutStrategy) {
        return Sink$.MODULE$.combine(first, second, rest, fanOutStrategy);
    }

    public static <T> Sink<T, Future<Done>> foreachAsync(int parallelism, Function1<T, Future<BoxedUnit>> f) {
        return Sink$.MODULE$.foreachAsync(parallelism, f);
    }

    public static <T> Sink<T, Future<Done>> foreach(Function1<T, BoxedUnit> f) {
        return Sink$.MODULE$.foreach(f);
    }

    public static Sink<Object, Future<Done>> never() {
        return Sink$.MODULE$.never();
    }

    public static Sink<Object, Future<Done>> ignore() {
        return Sink$.MODULE$.ignore();
    }

    public static <T> Sink<T, Publisher<T>> asPublisher(boolean fanout) {
        return Sink$.MODULE$.asPublisher(fanout);
    }

    public static <T, That> Sink<T, Future<That>> collection(Factory<T, That> cbf) {
        return Sink$.MODULE$.collection(cbf);
    }

    public static <T> Sink<T, Future<Seq<T>>> seq() {
        return Sink$.MODULE$.seq();
    }

    public static <T> Sink<T, Future<Seq<T>>> takeLast(int n) {
        return Sink$.MODULE$.takeLast(n);
    }

    public static <T> Sink<T, Future<Option<T>>> lastOption() {
        return Sink$.MODULE$.lastOption();
    }

    public static <T> Sink<T, Future<T>> last() {
        return Sink$.MODULE$.last();
    }

    public static <T> Sink<T, Future<Option<T>>> headOption() {
        return Sink$.MODULE$.headOption();
    }

    public static <T> Sink<T, Future<T>> head() {
        return Sink$.MODULE$.head();
    }

    public static <T> Sink<T, NotUsed> cancelled() {
        return Sink$.MODULE$.cancelled();
    }

    public static <T> Sink<T, NotUsed> fromSubscriber(Subscriber<T> subscriber) {
        return Sink$.MODULE$.fromSubscriber(subscriber);
    }

    public static <T, M> Sink<T, Future<M>> setup(Function2<ActorMaterializer, Attributes, Sink<T, M>> factory2) {
        return Sink$.MODULE$.setup(factory2);
    }

    public static <T, M> Sink<T, Future<M>> fromMaterializer(Function2<Materializer, Attributes, Sink<T, M>> factory2) {
        return Sink$.MODULE$.fromMaterializer(factory2);
    }

    public static <T, M> Sink<T, M> fromGraph(Graph<SinkShape<T>, M> g2) {
        return Sink$.MODULE$.fromGraph(g2);
    }

    @Override
    public LinearTraversalBuilder traversalBuilder() {
        return this.traversalBuilder;
    }

    @Override
    public SinkShape<In> shape() {
        return this.shape;
    }

    public String toString() {
        return new StringBuilder(6).append("Sink(").append(this.shape()).append(")").toString();
    }

    public <In2> Sink<In2, Mat> contramap(Function1<In2, In> f) {
        return Flow$.MODULE$.fromFunction(f).toMat((Graph)this, Keep$.MODULE$.right());
    }

    public <Mat2> Mat2 runWith(Graph<SourceShape<In>, Mat2> source2, Materializer materializer) {
        return (Mat2)((RunnableGraph)Source$.MODULE$.fromGraph(source2).to((Graph)this)).run(materializer);
    }

    public <Mat2> Sink<In, Mat2> mapMaterializedValue(Function1<Mat, Mat2> f) {
        return new Sink<In, Mat>((LinearTraversalBuilder)this.traversalBuilder().transformMat(f), this.shape());
    }

    public Tuple2<Mat, Sink<In, NotUsed>> preMaterialize(Materializer materializer) {
        Tuple2 tuple2 = (Tuple2)((RunnableGraph)Source$.MODULE$.asSubscriber().toMat((Graph)this, Keep$.MODULE$.both())).run(materializer);
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        Subscriber sub = (Subscriber)tuple2._1();
        Object mat = tuple2._2();
        Tuple2 tuple22 = new Tuple2(sub, mat);
        Subscriber sub2 = tuple22._1();
        Object mat2 = tuple22._2();
        return new Tuple2(mat2, Sink$.MODULE$.fromSubscriber(sub2));
    }

    @Override
    public Sink<In, Mat> withAttributes(Attributes attr) {
        return new Sink<In, Mat>(this.traversalBuilder().setAttributes(attr), this.shape());
    }

    @Override
    public Sink<In, Mat> addAttributes(Attributes attr) {
        return this.withAttributes(this.traversalBuilder().attributes().and(attr));
    }

    @Override
    public Sink<In, Mat> named(String name) {
        return this.addAttributes(Attributes$.MODULE$.name(name));
    }

    @Override
    public Sink<In, Mat> async() {
        return (Sink)Graph.async$(this);
    }

    @Override
    public Sink<In, Mat> async(String dispatcher) {
        return (Sink)Graph.async$(this, dispatcher);
    }

    @Override
    public Sink<In, Mat> async(String dispatcher, int inputBufferSize) {
        return (Sink)Graph.async$(this, dispatcher, inputBufferSize);
    }

    public <JIn extends In> akka.stream.javadsl.Sink<JIn, Mat> asJava() {
        return new akka.stream.javadsl.Sink(this);
    }

    @Override
    public Attributes getAttributes() {
        return this.traversalBuilder().attributes();
    }

    public Sink(LinearTraversalBuilder traversalBuilder, SinkShape<In> shape) {
        this.traversalBuilder = traversalBuilder;
        this.shape = shape;
        Graph.$init$(this);
    }
}

