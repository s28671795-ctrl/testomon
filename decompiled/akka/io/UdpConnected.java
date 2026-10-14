/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.io.Inet;
import akka.io.SelectionHandler;
import akka.io.UdpConnected$;
import akka.io.UdpConnectedExt;
import akka.util.ByteString;
import java.io.Serializable;
import java.net.InetSocketAddress;
import scala.Function0;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.collection.Iterator;
import scala.collection.immutable.Iterable;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0011Ux\u0001CAD\u0003\u0013C\t!a%\u0007\u0011\u0005]\u0015\u0011\u0012E\u0001\u00033Cq!a0\u0002\t\u0003\t\t\rC\u0004\u0002D\u0006!\t%!2\t\u000f\u0005\u001d\u0017\u0001\"\u0011\u0002J\"9\u0011Q[\u0001\u0005B\u0005]\u0007bBAk\u0003\u0011\u0005\u0013\u0011\u001d\u0004\n\u0003W\f\u0001\u0013aI\u0011\u0003[4\u0011\"!=\u0002!\u0003\r\t!a=\t\u000f\t\u001d\u0001\u0002\"\u0001\u0003\n!9!\u0011\u0003\u0005\u0005\u0002\tMaA\u0002Bc\u0003\u0001\u00139\r\u0003\u0006\u0003J.\u0011)\u001a!C\u0001\u0005\u0017D!B!4\f\u0005#\u0005\u000b\u0011\u0002BF\u0011\u001d\tyl\u0003C\u0001\u0005\u001fD\u0011Ba\u0013\f\u0003\u0003%\tA!6\t\u0013\tE3\"%A\u0005\u0002\te\u0007\"\u0003B5\u0017\u0005\u0005I\u0011\tB6\u0011%\u0011ihCA\u0001\n\u0003\u0011y\bC\u0005\u0003\b.\t\t\u0011\"\u0001\u0003^\"I!QS\u0006\u0002\u0002\u0013\u0005#q\u0013\u0005\n\u0005K[\u0011\u0011!C\u0001\u0005CD\u0011B!-\f\u0003\u0003%\tE!:\t\u0013\t]6\"!A\u0005B\te\u0006\"\u0003B^\u0017\u0005\u0005I\u0011\tB_\u0011%\u0011ylCA\u0001\n\u0003\u0012IoB\u0004\u0003n\u0006A\tAa<\u0007\u000f\t\u0015\u0017\u0001#\u0001\u0003r\"9\u0011qX\u000e\u0005\u0002\tM\b\"\u0003B{7\u0005\u0005I\u0011\u0011B|\u0011%\u0011YpGA\u0001\n\u0003\u0013i\u0010C\u0005\u0004\nm\t\t\u0011\"\u0003\u0004\f\u0019111C\u0001C\u0007+A!ba\u0006!\u0005+\u0007I\u0011AB\r\u0011)\u00199\u0003\tB\tB\u0003%11\u0004\u0005\u000b\u0007S\u0001#Q3A\u0005\u0002\t-\u0007BCB\u0016A\tE\t\u0015!\u0003\u0003\f\"9\u0011q\u0018\u0011\u0005\u0002\r5\u0002bBB\u001bA\u0011\u00051q\u0007\u0005\n\u0005\u0017\u0002\u0013\u0011!C\u0001\u0007sA\u0011B!\u0015!#\u0003%\taa\u0010\t\u0013\r\r\u0003%%A\u0005\u0002\te\u0007\"\u0003B5A\u0005\u0005I\u0011\tB6\u0011%\u0011i\bIA\u0001\n\u0003\u0011y\bC\u0005\u0003\b\u0002\n\t\u0011\"\u0001\u0004F!I!Q\u0013\u0011\u0002\u0002\u0013\u0005#q\u0013\u0005\n\u0005K\u0003\u0013\u0011!C\u0001\u0007\u0013B\u0011B!-!\u0003\u0003%\te!\u0014\t\u0013\t]\u0006%!A\u0005B\te\u0006\"\u0003B^A\u0005\u0005I\u0011\tB_\u0011%\u0011y\fIA\u0001\n\u0003\u001a\tfB\u0004\u0004V\u0005A\taa\u0016\u0007\u000f\rM\u0011\u0001#\u0001\u0004Z!9\u0011q\u0018\u001b\u0005\u0002\r\r\u0004b\u0002B{i\u0011\u00051Q\r\u0005\n\u0005k$\u0014\u0011!CA\u0007WB\u0011Ba?5\u0003\u0003%\ti!\u001d\t\u0013\r%A'!A\u0005\n\r-aABB?\u0003\t\u001by\b\u0003\u0006\u0004\u0002j\u0012)\u001a!C\u0001\u0007\u0007C!ba#;\u0005#\u0005\u000b\u0011BBC\u0011)\u0019iI\u000fBK\u0002\u0013\u00051q\u0012\u0005\u000b\u0007;S$\u0011#Q\u0001\n\rE\u0005BCBPu\tU\r\u0011\"\u0001\u0004\"\"Q1Q\u0015\u001e\u0003\u0012\u0003\u0006Iaa)\t\u0015\r\u001d&H!f\u0001\n\u0003\u0019I\u000b\u0003\u0006\u0004Vj\u0012\t\u0012)A\u0005\u0007WCq!a0;\t\u0003\u00199\u000eC\u0005\u0003Li\n\t\u0011\"\u0001\u0004d\"I!\u0011\u000b\u001e\u0012\u0002\u0013\u00051Q\u001e\u0005\n\u0007\u0007R\u0014\u0013!C\u0001\u0007cD\u0011b!>;#\u0003%\taa>\t\u0013\rm((%A\u0005\u0002\ru\b\"\u0003B5u\u0005\u0005I\u0011\tB6\u0011%\u0011iHOA\u0001\n\u0003\u0011y\bC\u0005\u0003\bj\n\t\u0011\"\u0001\u0005\u0002!I!Q\u0013\u001e\u0002\u0002\u0013\u0005#q\u0013\u0005\n\u0005KS\u0014\u0011!C\u0001\t\u000bA\u0011B!-;\u0003\u0003%\t\u0005\"\u0003\t\u0013\t]&(!A\u0005B\te\u0006\"\u0003B^u\u0005\u0005I\u0011\tB_\u0011%\u0011yLOA\u0001\n\u0003\"iaB\u0005\u0005\u0012\u0005\t\t\u0011#\u0001\u0005\u0014\u0019I1QP\u0001\u0002\u0002#\u0005AQ\u0003\u0005\b\u0003\u007f\u001bF\u0011\u0001C\u0012\u0011%\u0011YlUA\u0001\n\u000b\u0012i\fC\u0005\u0003vN\u000b\t\u0011\"!\u0005&!IAqF*\u0012\u0002\u0013\u00051q\u001f\u0005\n\tc\u0019\u0016\u0013!C\u0001\u0007{D\u0011Ba?T\u0003\u0003%\t\tb\r\t\u0013\u0011}2+%A\u0005\u0002\r]\b\"\u0003C!'F\u0005I\u0011AB\u007f\u0011%\u0019IaUA\u0001\n\u0013\u0019YaB\u0004\u0005D\u0005A\t\t\"\u0012\u0007\u000f\u0011\u001d\u0013\u0001#!\u0005J!9\u0011q\u00180\u0005\u0002\u0011-\u0003\"\u0003B5=\u0006\u0005I\u0011\tB6\u0011%\u0011iHXA\u0001\n\u0003\u0011y\bC\u0005\u0003\bz\u000b\t\u0011\"\u0001\u0005N!I!Q\u00130\u0002\u0002\u0013\u0005#q\u0013\u0005\n\u0005Ks\u0016\u0011!C\u0001\t#B\u0011Ba._\u0003\u0003%\tE!/\t\u0013\tmf,!A\u0005B\tu\u0006\"CB\u0005=\u0006\u0005I\u0011BB\u0006\u000f\u001d!)&\u0001EA\t/2q\u0001\"\u0017\u0002\u0011\u0003#Y\u0006C\u0004\u0002@&$\t\u0001\"\u0018\t\u0013\t%\u0014.!A\u0005B\t-\u0004\"\u0003B?S\u0006\u0005I\u0011\u0001B@\u0011%\u00119)[A\u0001\n\u0003!y\u0006C\u0005\u0003\u0016&\f\t\u0011\"\u0011\u0003\u0018\"I!QU5\u0002\u0002\u0013\u0005A1\r\u0005\n\u0005oK\u0017\u0011!C!\u0005sC\u0011Ba/j\u0003\u0003%\tE!0\t\u0013\r%\u0011.!A\u0005\n\r-qa\u0002C4\u0003!\u0005E\u0011\u000e\u0004\b\tW\n\u0001\u0012\u0011C7\u0011\u001d\ty\f\u001eC\u0001\t_B\u0011B!\u001bu\u0003\u0003%\tEa\u001b\t\u0013\tuD/!A\u0005\u0002\t}\u0004\"\u0003BDi\u0006\u0005I\u0011\u0001C9\u0011%\u0011)\n^A\u0001\n\u0003\u00129\nC\u0005\u0003&R\f\t\u0011\"\u0001\u0005v!I!q\u0017;\u0002\u0002\u0013\u0005#\u0011\u0018\u0005\n\u0005w#\u0018\u0011!C!\u0005{C\u0011b!\u0003u\u0003\u0003%Iaa\u0003\u0007\u0013\tu\u0011\u0001%A\u0012\u0002\t}aA\u0002C=\u0003\t#Y\b\u0003\u0006\u0004j}\u0014)\u001a!C\u0001\u00073A!\u0002\" \u0000\u0005#\u0005\u000b\u0011BB\u000e\u0011\u001d\tyl C\u0001\t\u007fB\u0011Ba\u0013\u0000\u0003\u0003%\t\u0001\"\"\t\u0013\tEs0%A\u0005\u0002\r}\u0002\"\u0003B5\u007f\u0006\u0005I\u0011\tB6\u0011%\u0011ih`A\u0001\n\u0003\u0011y\bC\u0005\u0003\b~\f\t\u0011\"\u0001\u0005\n\"I!QS@\u0002\u0002\u0013\u0005#q\u0013\u0005\n\u0005K{\u0018\u0011!C\u0001\t\u001bC\u0011B!-\u0000\u0003\u0003%\t\u0005\"%\t\u0013\t]v0!A\u0005B\te\u0006\"\u0003B^\u007f\u0006\u0005I\u0011\tB_\u0011%\u0011yl`A\u0001\n\u0003\")jB\u0005\u0005\u001a\u0006\t\t\u0011#\u0001\u0005\u001c\u001aIA\u0011P\u0001\u0002\u0002#\u0005AQ\u0014\u0005\t\u0003\u007f\u000by\u0002\"\u0001\u0005&\"Q!1XA\u0010\u0003\u0003%)E!0\t\u0015\tU\u0018qDA\u0001\n\u0003#9\u000b\u0003\u0006\u0003|\u0006}\u0011\u0011!CA\tWC!b!\u0003\u0002 \u0005\u0005I\u0011BB\u0006\r\u0019\u00119\"\u0001\"\u0003\u001a!Y!qHA\u0016\u0005+\u0007I\u0011\u0001B!\u0011-\u0011)%a\u000b\u0003\u0012\u0003\u0006IAa\u0011\t\u0011\u0005}\u00161\u0006C\u0001\u0005\u000fB!Ba\u0013\u0002,\u0005\u0005I\u0011\u0001B'\u0011)\u0011\t&a\u000b\u0012\u0002\u0013\u0005!1\u000b\u0005\u000b\u0005S\nY#!A\u0005B\t-\u0004B\u0003B?\u0003W\t\t\u0011\"\u0001\u0003\u0000!Q!qQA\u0016\u0003\u0003%\tA!#\t\u0015\tU\u00151FA\u0001\n\u0003\u00129\n\u0003\u0006\u0003&\u0006-\u0012\u0011!C\u0001\u0005OC!B!-\u0002,\u0005\u0005I\u0011\tBZ\u0011)\u00119,a\u000b\u0002\u0002\u0013\u0005#\u0011\u0018\u0005\u000b\u0005w\u000bY#!A\u0005B\tu\u0006B\u0003B`\u0003W\t\t\u0011\"\u0011\u0003B\u001eIA\u0011W\u0001\u0002\u0002#\u0005A1\u0017\u0004\n\u0005/\t\u0011\u0011!E\u0001\tkC\u0001\"a0\u0002L\u0011\u0005A\u0011\u0018\u0005\u000b\u0005w\u000bY%!A\u0005F\tu\u0006B\u0003B{\u0003\u0017\n\t\u0011\"!\u0005<\"Q!1`A&\u0003\u0003%\t\tb0\t\u0015\r%\u00111JA\u0001\n\u0013\u0019YAB\u0005\u0005F\u0006\u0001\n1%\t\u0005H\u001e9A1\\\u0001\t\u0002\u0012Ega\u0002Cc\u0003!\u0005E1\u001a\u0005\t\u0003\u007f\u000bY\u0006\"\u0001\u0005P\"Q!\u0011NA.\u0003\u0003%\tEa\u001b\t\u0015\tu\u00141LA\u0001\n\u0003\u0011y\b\u0003\u0006\u0003\b\u0006m\u0013\u0011!C\u0001\t'D!B!&\u0002\\\u0005\u0005I\u0011\tBL\u0011)\u0011)+a\u0017\u0002\u0002\u0013\u0005Aq\u001b\u0005\u000b\u0005o\u000bY&!A\u0005B\te\u0006B\u0003B^\u00037\n\t\u0011\"\u0011\u0003>\"Q1\u0011BA.\u0003\u0003%Iaa\u0003\u0007\u0013\u0011u\u0017\u0001%A\u0012\"\u0011}wa\u0002Cz\u0003!\u0005E\u0011\u001e\u0004\b\t;\f\u0001\u0012\u0011Cr\u0011!\ty,a\u001d\u0005\u0002\u0011\u001d\bB\u0003B5\u0003g\n\t\u0011\"\u0011\u0003l!Q!QPA:\u0003\u0003%\tAa \t\u0015\t\u001d\u00151OA\u0001\n\u0003!Y\u000f\u0003\u0006\u0003\u0016\u0006M\u0014\u0011!C!\u0005/C!B!*\u0002t\u0005\u0005I\u0011\u0001Cx\u0011)\u00119,a\u001d\u0002\u0002\u0013\u0005#\u0011\u0018\u0005\u000b\u0005w\u000b\u0019(!A\u0005B\tu\u0006BCB\u0005\u0003g\n\t\u0011\"\u0003\u0004\f\u0005aQ\u000b\u001a9D_:tWm\u0019;fI*!\u00111RAG\u0003\tIwN\u0003\u0002\u0002\u0010\u0006!\u0011m[6b\u0007\u0001\u00012!!&\u0002\u001b\t\tII\u0001\u0007VIB\u001cuN\u001c8fGR,GmE\u0004\u0002\u00037\u000b9+!/\u0011\t\u0005u\u00151U\u0007\u0003\u0003?S!!!)\u0002\u000bM\u001c\u0017\r\\1\n\t\u0005\u0015\u0016q\u0014\u0002\u0007\u0003:L(+\u001a4\u0011\r\u0005%\u0016qVAZ\u001b\t\tYK\u0003\u0003\u0002.\u00065\u0015!B1di>\u0014\u0018\u0002BAY\u0003W\u00131\"\u0012=uK:\u001c\u0018n\u001c8JIB!\u0011QSA[\u0013\u0011\t9,!#\u0003\u001fU#\u0007oQ8o]\u0016\u001cG/\u001a3FqR\u0004B!!+\u0002<&!\u0011QXAV\u0005M)\u0005\u0010^3og&|g.\u00133Qe>4\u0018\u000eZ3s\u0003\u0019a\u0014N\\5u}Q\u0011\u00111S\u0001\u0007Y>|7.\u001e9\u0016\u0005\u0005M\u0015aD2sK\u0006$X-\u0012=uK:\u001c\u0018n\u001c8\u0015\t\u0005M\u00161\u001a\u0005\b\u0003\u001b$\u0001\u0019AAh\u0003\u0019\u0019\u0018p\u001d;f[B!\u0011\u0011VAi\u0013\u0011\t\u0019.a+\u0003'\u0015CH/\u001a8eK\u0012\f5\r^8s'f\u001cH/Z7\u0002\u0007\u001d,G\u000f\u0006\u0003\u00024\u0006e\u0007bBAg\u000b\u0001\u0007\u00111\u001c\t\u0005\u0003S\u000bi.\u0003\u0003\u0002`\u0006-&aC!di>\u00148+_:uK6$B!a-\u0002d\"9\u0011Q\u001a\u0004A\u0002\u0005\u0015\b\u0003BAU\u0003OLA!!;\u0002,\nQ2\t\\1tg&\u001c\u0017i\u0019;peNK8\u000f^3n!J|g/\u001b3fe\n9Q*Z:tC\u001e,7cA\u0004\u0002\u001c&\u001aq\u0001\u0003@\u0003\u000f\r{W.\\1oIN9\u0001\"a'\u0002v\n\r\u0001\u0003BA|\u0003{tA!!&\u0002z&!\u00111`AE\u0003A\u0019V\r\\3di&|g\u000eS1oI2,'/\u0003\u0003\u0002\u0000\n\u0005!!\u0005%bg\u001a\u000b\u0017\u000e\\;sK6+7o]1hK*!\u00111`AE!\r\u0011)aB\u0007\u0002\u0003\u00051A%\u001b8ji\u0012\"\"Aa\u0003\u0011\t\u0005u%QB\u0005\u0005\u0005\u001f\tyJ\u0001\u0003V]&$\u0018A\u00044bS2,(/Z'fgN\fw-Z\u000b\u0003\u0005+\u0001BA!\u0002\u0002,\ti1i\\7nC:$g)Y5mK\u0012\u001c\"\"a\u000b\u0002\u001c\nm!\u0011\u0005B\u0014!\r\u0011)A \u0002\u0006\u000bZ,g\u000e^\n\u0006}\u0006m%1\u0001\t\u0005\u0003;\u0013\u0019#\u0003\u0003\u0003&\u0005}%a\u0002)s_\u0012,8\r\u001e\t\u0005\u0005S\u0011ID\u0004\u0003\u0003,\tUb\u0002\u0002B\u0017\u0005gi!Aa\f\u000b\t\tE\u0012\u0011S\u0001\u0007yI|w\u000e\u001e \n\u0005\u0005\u0005\u0016\u0002\u0002B\u001c\u0003?\u000bq\u0001]1dW\u0006<W-\u0003\u0003\u0003<\tu\"\u0001D*fe&\fG.\u001b>bE2,'\u0002\u0002B\u001c\u0003?\u000b1aY7e+\t\u0011\u0019\u0005E\u0002\u0003\u0006!\tAaY7eAQ!!Q\u0003B%\u0011!\u0011y$!\rA\u0002\t\r\u0013\u0001B2paf$BA!\u0006\u0003P!Q!qHA\u001a!\u0003\u0005\rAa\u0011\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011!Q\u000b\u0016\u0005\u0005\u0007\u00129f\u000b\u0002\u0003ZA!!1\fB3\u001b\t\u0011iF\u0003\u0003\u0003`\t\u0005\u0014!C;oG\",7m[3e\u0015\u0011\u0011\u0019'a(\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0003h\tu#!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006i\u0001O]8ek\u000e$\bK]3gSb,\"A!\u001c\u0011\t\t=$\u0011P\u0007\u0003\u0005cRAAa\u001d\u0003v\u0005!A.\u00198h\u0015\t\u00119(\u0001\u0003kCZ\f\u0017\u0002\u0002B>\u0005c\u0012aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRLXC\u0001BA!\u0011\tiJa!\n\t\t\u0015\u0015q\u0014\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0005\u0017\u0013\t\n\u0005\u0003\u0002\u001e\n5\u0015\u0002\u0002BH\u0003?\u00131!\u00118z\u0011)\u0011\u0019*a\u000f\u0002\u0002\u0003\u0007!\u0011Q\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\te\u0005C\u0002BN\u0005C\u0013Y)\u0004\u0002\u0003\u001e*!!qTAP\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0005G\u0013iJ\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003\u0002BU\u0005_\u0003B!!(\u0003,&!!QVAP\u0005\u001d\u0011un\u001c7fC:D!Ba%\u0002@\u0005\u0005\t\u0019\u0001BF\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\t5$Q\u0017\u0005\u000b\u0005'\u000b\t%!AA\u0002\t\u0005\u0015\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\t\u0005\u0015\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\t5\u0014AB3rk\u0006d7\u000f\u0006\u0003\u0003*\n\r\u0007B\u0003BJ\u0003\u000f\n\t\u00111\u0001\u0003\f\n)aj\\!dWNI1\"a'\u0003\u001c\t\u0005\"qE\u0001\u0006i>\\WM\\\u000b\u0003\u0005\u0017\u000ba\u0001^8lK:\u0004C\u0003\u0002Bi\u0005'\u00042A!\u0002\f\u0011\u001d\u0011IM\u0004a\u0001\u0005\u0017#BA!5\u0003X\"I!\u0011Z\b\u0011\u0002\u0003\u0007!1R\u000b\u0003\u00057TCAa#\u0003XQ!!1\u0012Bp\u0011%\u0011\u0019jEA\u0001\u0002\u0004\u0011\t\t\u0006\u0003\u0003*\n\r\b\"\u0003BJ+\u0005\u0005\t\u0019\u0001BF)\u0011\u0011iGa:\t\u0013\tMe#!AA\u0002\t\u0005E\u0003\u0002BU\u0005WD\u0011Ba%\u001a\u0003\u0003\u0005\rAa#\u0002\u000b9{\u0017iY6\u0011\u0007\t\u00151dE\u0002\u001c\u0005#$\"Aa<\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\t\tE'\u0011 \u0005\b\u0005\u0013l\u0002\u0019\u0001BF\u0003\u001d)h.\u00199qYf$BAa@\u0004\u0006A1\u0011QTB\u0001\u0005\u0017KAaa\u0001\u0002 \n1q\n\u001d;j_:D\u0011ba\u0002\u001f\u0003\u0003\u0005\rA!5\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0004\u000eA!!qNB\b\u0013\u0011\u0019\tB!\u001d\u0003\r=\u0013'.Z2u\u0005\u0011\u0019VM\u001c3\u0014\u0013\u0001\nYJa\u0011\u0003\"\t\u001d\u0012a\u00029bs2|\u0017\rZ\u000b\u0003\u00077\u0001Ba!\b\u0004$5\u00111q\u0004\u0006\u0005\u0007C\ti)\u0001\u0003vi&d\u0017\u0002BB\u0013\u0007?\u0011!BQ=uKN#(/\u001b8h\u0003!\u0001\u0018-\u001f7pC\u0012\u0004\u0013aA1dW\u0006!\u0011mY6!)\u0019\u0019yc!\r\u00044A\u0019!Q\u0001\u0011\t\u000f\r]Q\u00051\u0001\u0004\u001c!91\u0011F\u0013A\u0002\t-\u0015\u0001C<b]R\u001c\u0018iY6\u0016\u0005\t%FCBB\u0018\u0007w\u0019i\u0004C\u0005\u0004\u0018\u001d\u0002\n\u00111\u0001\u0004\u001c!I1\u0011F\u0014\u0011\u0002\u0003\u0007!1R\u000b\u0003\u0007\u0003RCaa\u0007\u0003X\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012D\u0003\u0002BF\u0007\u000fB\u0011Ba%-\u0003\u0003\u0005\rA!!\u0015\t\t%61\n\u0005\n\u0005's\u0013\u0011!a\u0001\u0005\u0017#BA!\u001c\u0004P!I!1S\u0018\u0002\u0002\u0003\u0007!\u0011\u0011\u000b\u0005\u0005S\u001b\u0019\u0006C\u0005\u0003\u0014J\n\t\u00111\u0001\u0003\f\u0006!1+\u001a8e!\r\u0011)\u0001N\n\u0006i\u0005m51\f\t\u0005\u0007;\u001a\t'\u0004\u0002\u0004`)!\u00111\u0012B;\u0013\u0011\u0011Yda\u0018\u0015\u0005\r]C\u0003BB\u0018\u0007OBqa!\u001b7\u0001\u0004\u0019Y\"\u0001\u0003eCR\fGCBB\u0018\u0007[\u001ay\u0007C\u0004\u0004\u0018]\u0002\raa\u0007\t\u000f\r%r\u00071\u0001\u0003\fR!11OB>!\u0019\tij!\u0001\u0004vAA\u0011QTB<\u00077\u0011Y)\u0003\u0003\u0004z\u0005}%A\u0002+va2,'\u0007C\u0005\u0004\ba\n\t\u00111\u0001\u00040\t91i\u001c8oK\u000e$8#\u0003\u001e\u0002\u001c\n\r#\u0011\u0005B\u0014\u0003\u001dA\u0017M\u001c3mKJ,\"a!\"\u0011\t\u0005%6qQ\u0005\u0005\u0007\u0013\u000bYK\u0001\u0005BGR|'OU3g\u0003!A\u0017M\u001c3mKJ\u0004\u0013!\u0004:f[>$X-\u00113ee\u0016\u001c8/\u0006\u0002\u0004\u0012B!11SBM\u001b\t\u0019)J\u0003\u0003\u0004\u0018\nU\u0014a\u00018fi&!11TBK\u0005EIe.\u001a;T_\u000e\\W\r^!eIJ,7o]\u0001\u000fe\u0016lw\u000e^3BI\u0012\u0014Xm]:!\u00031awnY1m\u0003\u0012$'/Z:t+\t\u0019\u0019\u000b\u0005\u0004\u0002\u001e\u000e\u00051\u0011S\u0001\u000eY>\u001c\u0017\r\\!eIJ,7o\u001d\u0011\u0002\u000f=\u0004H/[8ogV\u001111\u0016\t\u0007\u0007[\u001bIla0\u000f\t\r=6Q\u0017\b\u0005\u00057\u001b\t,\u0003\u0003\u00044\nu\u0015!C5n[V$\u0018M\u00197f\u0013\u0011\u00119da.\u000b\t\rM&QT\u0005\u0005\u0007w\u001biLA\u0006Ue\u00064XM]:bE2,'\u0002\u0002B\u001c\u0007o\u0003Ba!1\u0004P:!11YBf\u001d\u0011\u0019)m!3\u000f\t\t52qY\u0005\u0003\u0003\u001fKA!a#\u0002\u000e&!1QZAE\u0003\u0011Ie.\u001a;\n\t\rE71\u001b\u0002\r'>\u001c7.\u001a;PaRLwN\u001c\u0006\u0005\u0007\u001b\fI)\u0001\u0005paRLwN\\:!))\u0019Ina7\u0004^\u000e}7\u0011\u001d\t\u0004\u0005\u000bQ\u0004bBBA\u0007\u0002\u00071Q\u0011\u0005\b\u0007\u001b\u001b\u0005\u0019ABI\u0011%\u0019yj\u0011I\u0001\u0002\u0004\u0019\u0019\u000bC\u0005\u0004(\u000e\u0003\n\u00111\u0001\u0004,RQ1\u0011\\Bs\u0007O\u001cIoa;\t\u0013\r\u0005E\t%AA\u0002\r\u0015\u0005\"CBG\tB\u0005\t\u0019ABI\u0011%\u0019y\n\u0012I\u0001\u0002\u0004\u0019\u0019\u000bC\u0005\u0004(\u0012\u0003\n\u00111\u0001\u0004,V\u00111q\u001e\u0016\u0005\u0007\u000b\u00139&\u0006\u0002\u0004t*\"1\u0011\u0013B,\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*\"a!?+\t\r\r&qK\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00135+\t\u0019yP\u000b\u0003\u0004,\n]C\u0003\u0002BF\t\u0007A\u0011Ba%L\u0003\u0003\u0005\rA!!\u0015\t\t%Fq\u0001\u0005\n\u0005'k\u0015\u0011!a\u0001\u0005\u0017#BA!\u001c\u0005\f!I!1\u0013(\u0002\u0002\u0003\u0007!\u0011\u0011\u000b\u0005\u0005S#y\u0001C\u0005\u0003\u0014F\u000b\t\u00111\u0001\u0003\f\u000691i\u001c8oK\u000e$\bc\u0001B\u0003'N)1\u000bb\u0006\u0004\\AqA\u0011\u0004C\u0010\u0007\u000b\u001b\tja)\u0004,\u000eeWB\u0001C\u000e\u0015\u0011!i\"a(\u0002\u000fI,h\u000e^5nK&!A\u0011\u0005C\u000e\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g\u000e\u000e\u000b\u0003\t'!\"b!7\u0005(\u0011%B1\u0006C\u0017\u0011\u001d\u0019\tI\u0016a\u0001\u0007\u000bCqa!$W\u0001\u0004\u0019\t\nC\u0005\u0004 Z\u0003\n\u00111\u0001\u0004$\"I1q\u0015,\u0011\u0002\u0003\u000711V\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%g\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$C\u0007\u0006\u0003\u00056\u0011u\u0002CBAO\u0007\u0003!9\u0004\u0005\u0007\u0002\u001e\u0012e2QQBI\u0007G\u001bY+\u0003\u0003\u0005<\u0005}%A\u0002+va2,G\u0007C\u0005\u0004\be\u000b\t\u00111\u0001\u0004Z\u0006YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIM\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\"\u0014A\u0003#jg\u000e|gN\\3diB\u0019!Q\u00010\u0003\u0015\u0011K7oY8o]\u0016\u001cGoE\u0005_\u00037\u0013\u0019E!\t\u0003(Q\u0011AQ\t\u000b\u0005\u0005\u0017#y\u0005C\u0005\u0003\u0014\n\f\t\u00111\u0001\u0003\u0002R!!\u0011\u0016C*\u0011%\u0011\u0019\nZA\u0001\u0002\u0004\u0011Y)\u0001\bTkN\u0004XM\u001c3SK\u0006$\u0017N\\4\u0011\u0007\t\u0015\u0011N\u0001\bTkN\u0004XM\u001c3SK\u0006$\u0017N\\4\u0014\u0013%\fYJa\u0011\u0003\"\t\u001dBC\u0001C,)\u0011\u0011Y\t\"\u0019\t\u0013\tMU.!AA\u0002\t\u0005E\u0003\u0002BU\tKB\u0011Ba%p\u0003\u0003\u0005\rAa#\u0002\u001bI+7/^7f%\u0016\fG-\u001b8h!\r\u0011)\u0001\u001e\u0002\u000e%\u0016\u001cX/\\3SK\u0006$\u0017N\\4\u0014\u0013Q\fYJa\u0011\u0003\"\t\u001dBC\u0001C5)\u0011\u0011Y\tb\u001d\t\u0013\tM\u00050!AA\u0002\t\u0005E\u0003\u0002BU\toB\u0011Ba%{\u0003\u0003\u0005\rAa#\u0003\u0011I+7-Z5wK\u0012\u001c\u0012b`AN\u00057\u0011\tCa\n\u0002\u000b\u0011\fG/\u0019\u0011\u0015\t\u0011\u0005E1\u0011\t\u0004\u0005\u000by\b\u0002CB5\u0003\u000b\u0001\raa\u0007\u0015\t\u0011\u0005Eq\u0011\u0005\u000b\u0007S\n9\u0001%AA\u0002\rmA\u0003\u0002BF\t\u0017C!Ba%\u0002\u0010\u0005\u0005\t\u0019\u0001BA)\u0011\u0011I\u000bb$\t\u0015\tM\u00151CA\u0001\u0002\u0004\u0011Y\t\u0006\u0003\u0003n\u0011M\u0005B\u0003BJ\u0003+\t\t\u00111\u0001\u0003\u0002R!!\u0011\u0016CL\u0011)\u0011\u0019*a\u0007\u0002\u0002\u0003\u0007!1R\u0001\t%\u0016\u001cW-\u001b<fIB!!QAA\u0010'\u0019\ty\u0002b(\u0004\\AAA\u0011\u0004CQ\u00077!\t)\u0003\u0003\u0005$\u0012m!!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8ocQ\u0011A1\u0014\u000b\u0005\t\u0003#I\u000b\u0003\u0005\u0004j\u0005\u0015\u0002\u0019AB\u000e)\u0011!i\u000bb,\u0011\r\u0005u5\u0011AB\u000e\u0011)\u00199!a\n\u0002\u0002\u0003\u0007A\u0011Q\u0001\u000e\u0007>lW.\u00198e\r\u0006LG.\u001a3\u0011\t\t\u0015\u00111J\n\u0007\u0003\u0017\"9la\u0017\u0011\u0011\u0011eA\u0011\u0015B\"\u0005+!\"\u0001b-\u0015\t\tUAQ\u0018\u0005\t\u0005\u007f\t\t\u00061\u0001\u0003DQ!A\u0011\u0019Cb!\u0019\tij!\u0001\u0003D!Q1qAA*\u0003\u0003\u0005\rA!\u0006\u0003\u0013\r{gN\\3di\u0016$7CBA,\u00037\u0013Y\"\u000b\u0003\u0002X\u0005m3CCA.\u00037#iM!\t\u0003(A!!QAA,)\t!\t\u000e\u0005\u0003\u0003\u0006\u0005mC\u0003\u0002BF\t+D!Ba%\u0002d\u0005\u0005\t\u0019\u0001BA)\u0011\u0011I\u000b\"7\t\u0015\tM\u0015qMA\u0001\u0002\u0004\u0011Y)A\u0005D_:tWm\u0019;fI\naA)[:d_:tWm\u0019;fIN1\u0011qNAN\u00057IC!a\u001c\u0002tMQ\u00111OAN\tK\u0014\tCa\n\u0011\t\t\u0015\u0011q\u000e\u000b\u0003\tS\u0004BA!\u0002\u0002tQ!!1\u0012Cw\u0011)\u0011\u0019*a\u001f\u0002\u0002\u0003\u0007!\u0011\u0011\u000b\u0005\u0005S#\t\u0010\u0003\u0006\u0003\u0014\u0006}\u0014\u0011!a\u0001\u0005\u0017\u000bA\u0002R5tG>tg.Z2uK\u0012\u0004")
public final class UdpConnected {
    public static UdpConnectedExt get(ClassicActorSystemProvider system) {
        return UdpConnected$.MODULE$.get(system);
    }

    public static UdpConnectedExt get(ActorSystem system) {
        return UdpConnected$.MODULE$.get(system);
    }

    public static UdpConnectedExt createExtension(ExtendedActorSystem system) {
        return UdpConnected$.MODULE$.createExtension(system);
    }

    public static UdpConnected$ lookup() {
        return UdpConnected$.MODULE$.lookup();
    }

    public static boolean equals(Object other) {
        return UdpConnected$.MODULE$.equals(other);
    }

    public static int hashCode() {
        return UdpConnected$.MODULE$.hashCode();
    }

    public static Extension apply(ClassicActorSystemProvider system) {
        return UdpConnected$.MODULE$.apply(system);
    }

    public static Extension apply(ActorSystem system) {
        return UdpConnected$.MODULE$.apply(system);
    }

    public static interface Command
    extends SelectionHandler.HasFailureMessage,
    Message {
        @Override
        default public CommandFailed failureMessage() {
            return new CommandFailed(this);
        }

        public static void $init$(Command $this) {
        }
    }

    public static final class CommandFailed
    implements Event,
    Product,
    Serializable {
        private final Command cmd;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Command cmd() {
            return this.cmd;
        }

        public CommandFailed copy(Command cmd) {
            return new CommandFailed(cmd);
        }

        public Command copy$default$1() {
            return this.cmd();
        }

        @Override
        public String productPrefix() {
            return "CommandFailed";
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
                    return this.cmd();
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
            return x$1 instanceof CommandFailed;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "cmd";
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
            if (!(object instanceof CommandFailed)) return false;
            boolean bl = true;
            if (!bl) return false;
            CommandFailed commandFailed = (CommandFailed)x$1;
            Command command = this.cmd();
            Command command2 = commandFailed.cmd();
            if (command != null) {
                if (!command.equals(command2)) return false;
                return true;
            }
            if (command2 == null) return true;
            return false;
        }

        public CommandFailed(Command cmd) {
            this.cmd = cmd;
            Product.$init$(this);
        }
    }

    public static final class Connect
    implements Command,
    Product,
    Serializable {
        private final ActorRef handler;
        private final InetSocketAddress remoteAddress;
        private final Option<InetSocketAddress> localAddress;
        private final Iterable<Inet.SocketOption> options;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public CommandFailed failureMessage() {
            return ((Command)this).failureMessage();
        }

        public ActorRef handler() {
            return this.handler;
        }

        public InetSocketAddress remoteAddress() {
            return this.remoteAddress;
        }

        public Option<InetSocketAddress> localAddress() {
            return this.localAddress;
        }

        public Iterable<Inet.SocketOption> options() {
            return this.options;
        }

        public Connect copy(ActorRef handler, InetSocketAddress remoteAddress, Option<InetSocketAddress> localAddress, Iterable<Inet.SocketOption> options) {
            return new Connect(handler, remoteAddress, localAddress, options);
        }

        public ActorRef copy$default$1() {
            return this.handler();
        }

        public InetSocketAddress copy$default$2() {
            return this.remoteAddress();
        }

        public Option<InetSocketAddress> copy$default$3() {
            return this.localAddress();
        }

        public Iterable<Inet.SocketOption> copy$default$4() {
            return this.options();
        }

        @Override
        public String productPrefix() {
            return "Connect";
        }

        @Override
        public int productArity() {
            return 4;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.handler();
                }
                case 1: {
                    return this.remoteAddress();
                }
                case 2: {
                    return this.localAddress();
                }
                case 3: {
                    return this.options();
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
            return x$1 instanceof Connect;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "handler";
                }
                case 1: {
                    return "remoteAddress";
                }
                case 2: {
                    return "localAddress";
                }
                case 3: {
                    return "options";
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
            if (!(object instanceof Connect)) return false;
            boolean bl = true;
            if (!bl) return false;
            Connect connect2 = (Connect)x$1;
            ActorRef actorRef2 = this.handler();
            ActorRef actorRef3 = connect2.handler();
            if (actorRef2 == null) {
                if (actorRef3 != null) {
                    return false;
                }
            } else if (!((Object)actorRef2).equals(actorRef3)) return false;
            InetSocketAddress inetSocketAddress = this.remoteAddress();
            InetSocketAddress inetSocketAddress2 = connect2.remoteAddress();
            if (inetSocketAddress == null) {
                if (inetSocketAddress2 != null) {
                    return false;
                }
            } else if (!((Object)inetSocketAddress).equals(inetSocketAddress2)) return false;
            Option<InetSocketAddress> option = this.localAddress();
            Option<InetSocketAddress> option2 = connect2.localAddress();
            if (option == null) {
                if (option2 != null) {
                    return false;
                }
            } else if (!option.equals(option2)) return false;
            Iterable<Inet.SocketOption> iterable = this.options();
            Iterable<Inet.SocketOption> iterable2 = connect2.options();
            if (iterable == null) {
                if (iterable2 == null) return true;
                return false;
            } else {
                if (!iterable.equals(iterable2)) return false;
                return true;
            }
        }

        public Connect(ActorRef handler, InetSocketAddress remoteAddress, Option<InetSocketAddress> localAddress, Iterable<Inet.SocketOption> options) {
            this.handler = handler;
            this.remoteAddress = remoteAddress;
            this.localAddress = localAddress;
            this.options = options;
            Command.$init$(this);
            Product.$init$(this);
        }
    }

    public static interface Connected
    extends Event {
    }

    public static interface Disconnected
    extends Event {
    }

    public static interface Event
    extends Message {
    }

    public static interface Message {
    }

    public static class NoAck
    implements Event,
    Product,
    Serializable {
        private final Object token;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Object token() {
            return this.token;
        }

        public NoAck copy(Object token) {
            return new NoAck(token);
        }

        public Object copy$default$1() {
            return this.token();
        }

        @Override
        public String productPrefix() {
            return "NoAck";
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
                    return this.token();
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
            return x$1 instanceof NoAck;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "token";
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
            if (!(object instanceof NoAck)) return false;
            boolean bl = true;
            if (!bl) return false;
            NoAck noAck = (NoAck)x$1;
            if (!BoxesRunTime.equals(this.token(), noAck.token())) return false;
            if (!noAck.canEqual(this)) return false;
            return true;
        }

        public NoAck(Object token) {
            this.token = token;
            Product.$init$(this);
        }
    }

    public static final class Received
    implements Event,
    Product,
    Serializable {
        private final ByteString data;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ByteString data() {
            return this.data;
        }

        public Received copy(ByteString data) {
            return new Received(data);
        }

        public ByteString copy$default$1() {
            return this.data();
        }

        @Override
        public String productPrefix() {
            return "Received";
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
                    return this.data();
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
            return x$1 instanceof Received;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "data";
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
            if (!(object instanceof Received)) return false;
            boolean bl = true;
            if (!bl) return false;
            Received received = (Received)x$1;
            ByteString byteString = this.data();
            ByteString byteString2 = received.data();
            if (byteString != null) {
                if (!((Object)byteString).equals(byteString2)) return false;
                return true;
            }
            if (byteString2 == null) return true;
            return false;
        }

        public Received(ByteString data) {
            this.data = data;
            Product.$init$(this);
        }
    }

    public static final class Send
    implements Command,
    Product,
    Serializable {
        private final ByteString payload;
        private final Object ack;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public CommandFailed failureMessage() {
            return ((Command)this).failureMessage();
        }

        public ByteString payload() {
            return this.payload;
        }

        public Object ack() {
            return this.ack;
        }

        public boolean wantsAck() {
            return !(this.ack() instanceof NoAck);
        }

        public Send copy(ByteString payload, Object ack) {
            return new Send(payload, ack);
        }

        public ByteString copy$default$1() {
            return this.payload();
        }

        public Object copy$default$2() {
            return this.ack();
        }

        @Override
        public String productPrefix() {
            return "Send";
        }

        @Override
        public int productArity() {
            return 2;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.payload();
                }
                case 1: {
                    return this.ack();
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
            return x$1 instanceof Send;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "payload";
                }
                case 1: {
                    return "ack";
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
            if (!(object instanceof Send)) return false;
            boolean bl = true;
            if (!bl) return false;
            Send send = (Send)x$1;
            ByteString byteString = this.payload();
            ByteString byteString2 = send.payload();
            if (byteString == null) {
                if (byteString2 != null) {
                    return false;
                }
            } else if (!((Object)byteString).equals(byteString2)) return false;
            if (!BoxesRunTime.equals(this.ack(), send.ack())) return false;
            return true;
        }

        public Send(ByteString payload, Object ack) {
            this.payload = payload;
            this.ack = ack;
            Command.$init$(this);
            Product.$init$(this);
            Predef$.MODULE$.require(ack != null, (Function0<Object>)(Function0<String> & Serializable)() -> "ack must be non-null. Use NoAck if you don't want acks.");
        }
    }
}

