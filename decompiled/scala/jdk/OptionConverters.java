/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import scala.Option;
import scala.jdk.OptionConverters$;
import scala.jdk.OptionConverters$RichOption$;
import scala.jdk.OptionConverters$RichOptional$;
import scala.jdk.OptionConverters$RichOptionalDouble$;
import scala.jdk.OptionConverters$RichOptionalInt$;
import scala.jdk.OptionConverters$RichOptionalLong$;
import scala.jdk.OptionShape;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\r-t!B/_\u0011\u0003\u0019g!B3_\u0011\u00031\u0007\"B6\u0002\t\u0003ag\u0001B7\u0002\u00079DAb]\u0002\u0005\u0002\u0003\u0015)Q1A\u0005\nQD!\"!\u0005\u0004\u0005\u000b\u0005\t\u0015!\u0003v\u0011\u0019Y7\u0001\"\u0001\u0002\u0014!9\u0011QD\u0002\u0005\u0002\u0005}\u0001bBA\u0014\u0007\u0011\u0005\u0011q\u0004\u0005\b\u0003{\u0019A\u0011AA \u0011%\t\u0019fAA\u0001\n\u0003\n)\u0006C\u0005\u0002^\r\t\t\u0011\"\u0011\u0002`\u001dI\u00111N\u0001\u0002\u0002#\u0005\u0011Q\u000e\u0004\t[\u0006\t\t\u0011#\u0001\u0002p!11.\u0004C\u0001\u0003cBq!a\u001d\u000e\t\u000b\t)\bC\u0004\u0002\u00066!)!a\"\t\u000f\u0005]U\u0002\"\u0002\u0002\u001a\"I\u0011qV\u0007\u0002\u0002\u0013\u0015\u0011\u0011\u0017\u0005\n\u0003{k\u0011\u0011!C\u0003\u0003\u007fC\u0011\"a\u001b\u0002\u0003\u0003%\u0019!a4\u0007\r\u0005u\u0017aAAp\u00119\t\u0019/\u0006C\u0001\u0002\u000b\u0015)\u0019!C\u0005\u0003KD1\"!<\u0016\u0005\u000b\u0005\t\u0015!\u0003\u0002h\"11.\u0006C\u0001\u0003_Dq!!>\u0016\t\u0003\t9\u0010C\u0004\u0002|V!\t!a>\t\u000f\u0005uR\u0003\"\u0001\u0003\u0004!I\u00111K\u000b\u0002\u0002\u0013\u0005\u0013Q\u000b\u0005\n\u0003;*\u0012\u0011!C!\u0005\u001f9\u0011Ba\u0005\u0002\u0003\u0003E\tA!\u0006\u0007\u0013\u0005u\u0017!!A\t\u0002\t]\u0001BB6 \t\u0003\u0011I\u0002C\u0004\u0003\u001c}!)A!\b\t\u000f\t-r\u0004\"\u0002\u0003.!9\u0011qS\u0010\u0005\u0006\tu\u0002\"CAX?\u0005\u0005IQ\u0001B*\u0011%\tilHA\u0001\n\u000b\u0011y\u0006C\u0005\u0003\u0014\u0005\t\t\u0011b\u0001\u0003p\u00191!QP\u0001\u0004\u0005\u007fBaB!!(\t\u0003\u0005)Q!b\u0001\n\u0013\u0011\u0019\tC\u0006\u0003\f\u001e\u0012)\u0011!Q\u0001\n\t\u0015\u0005BB6(\t\u0003\u0011i\tC\u0004\u0002\u001e\u001d\"\tAa%\t\u000f\u0005\u001dr\u0005\"\u0001\u0003\u0014\"9!qT\u0014\u0005\u0002\t\u0005\u0006\"CA*O\u0005\u0005I\u0011IA+\u0011%\tifJA\u0001\n\u0003\u0012)kB\u0005\u0003*\u0006\t\t\u0011#\u0001\u0003,\u001aI!QP\u0001\u0002\u0002#\u0005!Q\u0016\u0005\u0007WF\"\tAa,\t\u000f\u0005M\u0014\u0007\"\u0002\u00032\"9\u0011QQ\u0019\u0005\u0006\tU\u0006b\u0002B^c\u0011\u0015!Q\u0018\u0005\n\u0003_\u000b\u0014\u0011!C\u0003\u0005\u0003D\u0011\"!02\u0003\u0003%)A!2\t\u0013\t%\u0016!!A\u0005\u0004\t5gA\u0002Bi\u0003\r\u0011\u0019\u000e\u0003\b\u0003Vf\"\t\u0011!B\u0003\u0006\u0004%IAa6\t\u0017\t}\u0017H!B\u0001B\u0003%!\u0011\u001c\u0005\u0007Wf\"\tA!9\t\u000f\u0005u\u0011\b\"\u0001\u0003h\"9\u0011qE\u001d\u0005\u0002\t\u001d\bb\u0002BPs\u0011\u0005!Q\u001e\u0005\n\u0003'J\u0014\u0011!C!\u0003+B\u0011\"!\u0018:\u0003\u0003%\tE!=\b\u0013\tU\u0018!!A\t\u0002\t]h!\u0003Bi\u0003\u0005\u0005\t\u0012\u0001B}\u0011\u0019Y7\t\"\u0001\u0003|\"9\u00111O\"\u0005\u0006\tu\bbBAC\u0007\u0012\u00151\u0011\u0001\u0005\b\u0005w\u001bEQAB\u0004\u0011%\tykQA\u0001\n\u000b\u0019Y\u0001C\u0005\u0002>\u000e\u000b\t\u0011\"\u0002\u0004\u0010!I!Q_\u0001\u0002\u0002\u0013\r1q\u0003\u0004\u0007\u00077\t1a!\b\t\u001d\r}1\n\"A\u0001\u0006\u000b\u0015\r\u0011\"\u0003\u0004\"!Y1\u0011F&\u0003\u0006\u0003\u0005\u000b\u0011BB\u0012\u0011\u0019Y7\n\"\u0001\u0004,!9\u0011QD&\u0005\u0002\rE\u0002bBA\u0014\u0017\u0012\u00051\u0011\u0007\u0005\b\u0005?[E\u0011AB\u001f\u0011%\t\u0019fSA\u0001\n\u0003\n)\u0006C\u0005\u0002^-\u000b\t\u0011\"\u0011\u0004B\u001dI1QI\u0001\u0002\u0002#\u00051q\t\u0004\n\u00077\t\u0011\u0011!E\u0001\u0007\u0013Baa[+\u0005\u0002\r-\u0003bBA:+\u0012\u00151Q\n\u0005\b\u0003\u000b+FQAB)\u0011\u001d\u0011Y,\u0016C\u0003\u0007/B\u0011\"a,V\u0003\u0003%)aa\u0017\t\u0013\u0005uV+!A\u0005\u0006\r}\u0003\"CB#\u0003\u0005\u0005I1AB4\u0003Ay\u0005\u000f^5p]\u000e{gN^3si\u0016\u00148O\u0003\u0002`A\u0006\u0019!\u000eZ6\u000b\u0003\u0005\fQa]2bY\u0006\u001c\u0001\u0001\u0005\u0002e\u00035\taL\u0001\tPaRLwN\\\"p]Z,'\u000f^3sgN\u0011\u0011a\u001a\t\u0003Q&l\u0011\u0001Y\u0005\u0003U\u0002\u0014a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001d\u00051\u0011\u0016n\u00195PaRLwN\\1m+\tywp\u0005\u0002\u0004aB\u0011\u0001.]\u0005\u0003e\u0002\u0014a!\u00118z-\u0006d\u0017AK:dC2\fGE\u001b3lI=\u0003H/[8o\u0007>tg/\u001a:uKJ\u001cHEU5dQ>\u0003H/[8oC2$Ce\\\u000b\u0002kB\u0019ao_?\u000e\u0003]T!\u0001_=\u0002\tU$\u0018\u000e\u001c\u0006\u0002u\u0006!!.\u0019<b\u0013\taxO\u0001\u0005PaRLwN\\1m!\tqx\u0010\u0004\u0001\u0005\u000f\u0005\u00051A1\u0001\u0002\u0004\t\t\u0011)\u0005\u0003\u0002\u0006\u0005-\u0001c\u00015\u0002\b%\u0019\u0011\u0011\u00021\u0003\u000f9{G\u000f[5oOB\u0019\u0001.!\u0004\n\u0007\u0005=\u0001MA\u0002B]f\f1f]2bY\u0006$#\u000eZ6%\u001fB$\u0018n\u001c8D_:4XM\u001d;feN$#+[2i\u001fB$\u0018n\u001c8bY\u0012\"s\u000e\t\u000b\u0005\u0003+\tI\u0002\u0005\u0003\u0002\u0018\riX\"A\u0001\t\r\u0005ma\u00011\u0001v\u0003\u0005y\u0017a\u0002;p'\u000e\fG.Y\u000b\u0003\u0003C\u0001B\u0001[A\u0012{&\u0019\u0011Q\u00051\u0003\r=\u0003H/[8o\u0003\u001d\t7oU2bY\u0006D3\u0002CA\u0016\u0003c\t\u0019$a\u000e\u0002:A\u0019\u0001.!\f\n\u0007\u0005=\u0002M\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-\t\u0002\u00026\u0005)Rk]3!AR|7kY1mC\u0002\u0004\u0013N\\:uK\u0006$\u0017!B:j]\u000e,\u0017EAA\u001e\u0003\u0019\u0011d&M\u001a/a\u0005yAo\u001c&bm\u0006\u0004&/[7ji&4X-\u0006\u0003\u0002B\u0005\u0015C\u0003BA\"\u0003\u0013\u00022A`A#\t\u001d\t9%\u0003b\u0001\u0003\u0007\u0011\u0011a\u0014\u0005\b\u0003\u0017J\u00019AA'\u0003\u0015\u0019\b.\u00199f!\u0019!\u0017qJ?\u0002D%\u0019\u0011\u0011\u000b0\u0003\u0017=\u0003H/[8o'\"\f\u0007/Z\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011q\u000b\t\u0004Q\u0006e\u0013bAA.A\n\u0019\u0011J\u001c;\u0002\r\u0015\fX/\u00197t)\u0011\t\t'a\u001a\u0011\u0007!\f\u0019'C\u0002\u0002f\u0001\u0014qAQ8pY\u0016\fg\u000eC\u0005\u0002j-\t\t\u00111\u0001\u0002\f\u0005\u0019\u0001\u0010J\u0019\u0002\u0019IK7\r[(qi&|g.\u00197\u0011\u0007\u0005]Qb\u0005\u0002\u000eOR\u0011\u0011QN\u0001\u0012i>\u001c6-\u00197bI\u0015DH/\u001a8tS>tW\u0003BA<\u0003{\"B!!\u001f\u0002\u0000A)\u0001.a\t\u0002|A\u0019a0! \u0005\u000f\u0005\u0005qB1\u0001\u0002\u0004!9\u0011\u0011Q\bA\u0002\u0005\r\u0015!\u0002\u0013uQ&\u001c\b#BA\f\u0007\u0005m\u0014!E1t'\u000e\fG.\u0019\u0013fqR,gn]5p]V!\u0011\u0011RAH)\u0011\tY)!%\u0011\u000b!\f\u0019#!$\u0011\u0007y\fy\tB\u0004\u0002\u0002A\u0011\r!a\u0001\t\u000f\u0005\u0005\u0005\u00031\u0001\u0002\u0014B)\u0011qC\u0002\u0002\u000e\"Z\u0001#a\u000b\u00022\u0005M\u0012qGA\u001d\u0003e!xNS1wCB\u0013\u0018.\\5uSZ,G%\u001a=uK:\u001c\u0018n\u001c8\u0016\r\u0005m\u0015\u0011UAU)\u0011\ti*a+\u0015\t\u0005}\u00151\u0015\t\u0004}\u0006\u0005FaBA$#\t\u0007\u00111\u0001\u0005\b\u0003\u0017\n\u00029AAS!\u001d!\u0017qJAT\u0003?\u00032A`AU\t\u001d\t\t!\u0005b\u0001\u0003\u0007Aq!!!\u0012\u0001\u0004\ti\u000bE\u0003\u0002\u0018\r\t9+\u0001\niCND7i\u001c3fI\u0015DH/\u001a8tS>tW\u0003BAZ\u0003w#B!!\u0016\u00026\"9\u0011\u0011\u0011\nA\u0002\u0005]\u0006#BA\f\u0007\u0005e\u0006c\u0001@\u0002<\u00129\u0011\u0011\u0001\nC\u0002\u0005\r\u0011\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o+\u0011\t\t-!4\u0015\t\u0005\r\u0017q\u0019\u000b\u0005\u0003C\n)\rC\u0005\u0002jM\t\t\u00111\u0001\u0002\f!9\u0011\u0011Q\nA\u0002\u0005%\u0007#BA\f\u0007\u0005-\u0007c\u0001@\u0002N\u00129\u0011\u0011A\nC\u0002\u0005\rQ\u0003BAi\u0003/$B!a5\u0002ZB)\u0011qC\u0002\u0002VB\u0019a0a6\u0005\u000f\u0005\u0005AC1\u0001\u0002\u0004!9\u00111\u0004\u000bA\u0002\u0005m\u0007\u0003\u0002<|\u0003+\u0014!BU5dQ>\u0003H/[8o+\u0011\t\t/a;\u0014\u0005U\u0001\u0018\u0001K:dC2\fGE\u001b3lI=\u0003H/[8o\u0007>tg/\u001a:uKJ\u001cHEU5dQ>\u0003H/[8oI\u0011zWCAAt!\u0015A\u00171EAu!\rq\u00181\u001e\u0003\b\u0003\u0003)\"\u0019AA\u0002\u0003%\u001a8-\u00197bI)$7\u000eJ(qi&|gnQ8om\u0016\u0014H/\u001a:tIIK7\r[(qi&|g\u000e\n\u0013pAQ!\u0011\u0011_Az!\u0015\t9\"FAu\u0011\u001d\tY\u0002\u0007a\u0001\u0003O\fa\u0001^8KCZ\fWCAA}!\u0011180!;\u0002\r\u0005\u001c(*\u0019<bQ-Q\u00121FA\u0019\u0003\u007f\f9$!\u000f\"\u0005\t\u0005\u0011\u0001F+tK\u0002\u0002Go\u001c&bm\u0006\u0004\u0007%\u001b8ti\u0016\fG-\u0006\u0003\u0003\u0006\t%A\u0003\u0002B\u0004\u0005\u0017\u00012A B\u0005\t\u001d\t9e\u0007b\u0001\u0003\u0007Aq!a\u0013\u001c\u0001\b\u0011i\u0001E\u0004e\u0003\u001f\nIOa\u0002\u0015\t\u0005\u0005$\u0011\u0003\u0005\n\u0003Sj\u0012\u0011!a\u0001\u0003\u0017\t!BU5dQ>\u0003H/[8o!\r\t9bH\n\u0003?\u001d$\"A!\u0006\u0002!Q|'*\u0019<bI\u0015DH/\u001a8tS>tW\u0003\u0002B\u0010\u0005K!BA!\t\u0003(A!ao\u001fB\u0012!\rq(Q\u0005\u0003\b\u0003\u0003\t#\u0019AA\u0002\u0011\u001d\t\t)\ta\u0001\u0005S\u0001R!a\u0006\u0016\u0005G\t\u0001#Y:KCZ\fG%\u001a=uK:\u001c\u0018n\u001c8\u0016\t\t=\"Q\u0007\u000b\u0005\u0005c\u00119\u0004\u0005\u0003ww\nM\u0002c\u0001@\u00036\u00119\u0011\u0011\u0001\u0012C\u0002\u0005\r\u0001bBAAE\u0001\u0007!\u0011\b\t\u0006\u0003/)\"1\u0007\u0015\fE\u0005-\u0012\u0011GA\u0000\u0003o\tI$\u0006\u0004\u0003@\t\u0015#Q\n\u000b\u0005\u0005\u0003\u0012y\u0005\u0006\u0003\u0003D\t\u001d\u0003c\u0001@\u0003F\u00119\u0011qI\u0012C\u0002\u0005\r\u0001bBA&G\u0001\u000f!\u0011\n\t\bI\u0006=#1\nB\"!\rq(Q\n\u0003\b\u0003\u0003\u0019#\u0019AA\u0002\u0011\u001d\t\ti\ta\u0001\u0005#\u0002R!a\u0006\u0016\u0005\u0017*BA!\u0016\u0003^Q!\u0011Q\u000bB,\u0011\u001d\t\t\t\na\u0001\u00053\u0002R!a\u0006\u0016\u00057\u00022A B/\t\u001d\t\t\u0001\nb\u0001\u0003\u0007)BA!\u0019\u0003nQ!!1\rB4)\u0011\t\tG!\u001a\t\u0013\u0005%T%!AA\u0002\u0005-\u0001bBAAK\u0001\u0007!\u0011\u000e\t\u0006\u0003/)\"1\u000e\t\u0004}\n5DaBA\u0001K\t\u0007\u00111A\u000b\u0005\u0005c\u00129\b\u0006\u0003\u0003t\te\u0004#BA\f+\tU\u0004c\u0001@\u0003x\u00119\u0011\u0011\u0001\u0014C\u0002\u0005\r\u0001bBA\u000eM\u0001\u0007!1\u0010\t\u0006Q\u0006\r\"Q\u000f\u0002\u0013%&\u001c\u0007n\u00149uS>t\u0017\r\u001c#pk\ndWm\u0005\u0002(a\u0006\u00014oY1mC\u0012RGm\u001b\u0013PaRLwN\\\"p]Z,'\u000f^3sg\u0012\u0012\u0016n\u00195PaRLwN\\1m\t>,(\r\\3%I=,\"A!\"\u0011\u0007Y\u00149)C\u0002\u0003\n^\u0014ab\u00149uS>t\u0017\r\u001c#pk\ndW-A\u0019tG\u0006d\u0017\r\n6eW\u0012z\u0005\u000f^5p]\u000e{gN^3si\u0016\u00148\u000f\n*jG\"|\u0005\u000f^5p]\u0006dGi\\;cY\u0016$Ce\u001c\u0011\u0015\t\t=%\u0011\u0013\t\u0004\u0003/9\u0003bBA\u000eU\u0001\u0007!QQ\u000b\u0003\u0005+\u0003R\u0001[A\u0012\u0005/\u00032\u0001\u001bBM\u0013\r\u0011Y\n\u0019\u0002\u0007\t>,(\r\\3)\u00171\nY#!\r\u00024\u0005]\u0012\u0011H\u0001\u000ei>T\u0015M^1HK:,'/[2\u0016\u0005\t\r\u0006\u0003\u0002<|\u0005/#B!!\u0019\u0003(\"I\u0011\u0011N\u0018\u0002\u0002\u0003\u0007\u00111B\u0001\u0013%&\u001c\u0007n\u00149uS>t\u0017\r\u001c#pk\ndW\rE\u0002\u0002\u0018E\u001a\"!M4\u0015\u0005\t-F\u0003\u0002BK\u0005gCq!!!4\u0001\u0004\u0011y\t\u0006\u0003\u0003\u0016\n]\u0006bBAAi\u0001\u0007!q\u0012\u0015\fi\u0005-\u0012\u0011GA\u001a\u0003o\tI$A\fu_*\u000bg/Y$f]\u0016\u0014\u0018n\u0019\u0013fqR,gn]5p]R!!1\u0015B`\u0011\u001d\t\t)\u000ea\u0001\u0005\u001f#B!!\u0016\u0003D\"9\u0011\u0011\u0011\u001cA\u0002\t=E\u0003\u0002Bd\u0005\u0017$B!!\u0019\u0003J\"I\u0011\u0011N\u001c\u0002\u0002\u0003\u0007\u00111\u0002\u0005\b\u0003\u0003;\u0004\u0019\u0001BH)\u0011\u0011yIa4\t\u000f\u0005m\u0001\b1\u0001\u0003\u0006\ny!+[2i\u001fB$\u0018n\u001c8bY&sGo\u0005\u0002:a\u0006i3oY1mC\u0012RGm\u001b\u0013PaRLwN\\\"p]Z,'\u000f^3sg\u0012\u0012\u0016n\u00195PaRLwN\\1m\u0013:$H\u0005J8\u0016\u0005\te\u0007c\u0001<\u0003\\&\u0019!Q\\<\u0003\u0017=\u0003H/[8oC2Le\u000e^\u0001/g\u000e\fG.\u0019\u0013kI.$s\n\u001d;j_:\u001cuN\u001c<feR,'o\u001d\u0013SS\u000eDw\n\u001d;j_:\fG.\u00138uI\u0011z\u0007\u0005\u0006\u0003\u0003d\n\u0015\bcAA\fs!9\u00111\u0004\u001fA\u0002\teWC\u0001Bu!\u0015A\u00171EA,Q-q\u00141FA\u0019\u0003g\t9$!\u000f\u0016\u0005\t=\b\u0003\u0002<|\u0003/\"B!!\u0019\u0003t\"I\u0011\u0011N!\u0002\u0002\u0003\u0007\u00111B\u0001\u0010%&\u001c\u0007n\u00149uS>t\u0017\r\\%oiB\u0019\u0011qC\"\u0014\u0005\r;GC\u0001B|)\u0011\u0011IOa@\t\u000f\u0005\u0005U\t1\u0001\u0003dR!!\u0011^B\u0002\u0011\u001d\t\tI\u0012a\u0001\u0005GD3BRA\u0016\u0003c\t\u0019$a\u000e\u0002:Q!!q^B\u0005\u0011\u001d\t\ti\u0012a\u0001\u0005G$B!!\u0016\u0004\u000e!9\u0011\u0011\u0011%A\u0002\t\rH\u0003BB\t\u0007+!B!!\u0019\u0004\u0014!I\u0011\u0011N%\u0002\u0002\u0003\u0007\u00111\u0002\u0005\b\u0003\u0003K\u0005\u0019\u0001Br)\u0011\u0011\u0019o!\u0007\t\u000f\u0005m!\n1\u0001\u0003Z\n\u0001\"+[2i\u001fB$\u0018n\u001c8bY2{gnZ\n\u0003\u0017B\faf]2bY\u0006$#\u000eZ6%\u001fB$\u0018n\u001c8D_:4XM\u001d;feN$#+[2i\u001fB$\u0018n\u001c8bY2{gn\u001a\u0013%_V\u001111\u0005\t\u0004m\u000e\u0015\u0012bAB\u0014o\naq\n\u001d;j_:\fG\u000eT8oO\u0006y3oY1mC\u0012RGm\u001b\u0013PaRLwN\\\"p]Z,'\u000f^3sg\u0012\u0012\u0016n\u00195PaRLwN\\1m\u0019>tw\r\n\u0013pAQ!1QFB\u0018!\r\t9b\u0013\u0005\b\u00037q\u0005\u0019AB\u0012+\t\u0019\u0019\u0004E\u0003i\u0003G\u0019)\u0004E\u0002i\u0007oI1a!\u000fa\u0005\u0011auN\\4)\u0017A\u000bY#!\r\u00024\u0005]\u0012\u0011H\u000b\u0003\u0007\u007f\u0001BA^>\u00046Q!\u0011\u0011MB\"\u0011%\tIgUA\u0001\u0002\u0004\tY!\u0001\tSS\u000eDw\n\u001d;j_:\fG\u000eT8oOB\u0019\u0011qC+\u0014\u0005U;GCAB$)\u0011\u0019\u0019da\u0014\t\u000f\u0005\u0005u\u000b1\u0001\u0004.Q!11GB*\u0011\u001d\t\t\t\u0017a\u0001\u0007[A3\u0002WA\u0016\u0003c\t\u0019$a\u000e\u0002:Q!1qHB-\u0011\u001d\t\t)\u0017a\u0001\u0007[!B!!\u0016\u0004^!9\u0011\u0011\u0011.A\u0002\r5B\u0003BB1\u0007K\"B!!\u0019\u0004d!I\u0011\u0011N.\u0002\u0002\u0003\u0007\u00111\u0002\u0005\b\u0003\u0003[\u0006\u0019AB\u0017)\u0011\u0019ic!\u001b\t\u000f\u0005mA\f1\u0001\u0004$\u0001")
public final class OptionConverters {
    public static OptionalLong RichOptionalLong(OptionalLong o) {
        return OptionConverters$.MODULE$.RichOptionalLong(o);
    }

    public static OptionalInt RichOptionalInt(OptionalInt o) {
        return OptionConverters$.MODULE$.RichOptionalInt(o);
    }

    public static OptionalDouble RichOptionalDouble(OptionalDouble o) {
        return OptionConverters$.MODULE$.RichOptionalDouble(o);
    }

    public static Option RichOption(Option o) {
        return OptionConverters$.MODULE$.RichOption(o);
    }

    public static Optional RichOptional(Optional o) {
        return OptionConverters$.MODULE$.RichOptional(o);
    }

    public static final class RichOption<A> {
        private final Option<A> scala$jdk$OptionConverters$RichOption$$o;

        public Option<A> scala$jdk$OptionConverters$RichOption$$o() {
            return this.scala$jdk$OptionConverters$RichOption$$o;
        }

        public Optional<A> toJava() {
            return OptionConverters$RichOption$.MODULE$.toJava$extension(this.scala$jdk$OptionConverters$RichOption$$o());
        }

        public Optional<A> asJava() {
            return OptionConverters$RichOption$.MODULE$.asJava$extension(this.scala$jdk$OptionConverters$RichOption$$o());
        }

        public <O> O toJavaPrimitive(OptionShape<A, O> shape) {
            Option<A> toJavaPrimitive$extension_$this = this.scala$jdk$OptionConverters$RichOption$$o();
            return shape.fromScala(toJavaPrimitive$extension_$this);
        }

        public int hashCode() {
            return this.scala$jdk$OptionConverters$RichOption$$o().hashCode();
        }

        public boolean equals(Object x$1) {
            return OptionConverters$RichOption$.MODULE$.equals$extension(this.scala$jdk$OptionConverters$RichOption$$o(), x$1);
        }

        public RichOption(Option<A> o) {
            this.scala$jdk$OptionConverters$RichOption$$o = o;
        }
    }

    public static final class RichOptional<A> {
        private final Optional<A> scala$jdk$OptionConverters$RichOptional$$o;

        public Optional<A> scala$jdk$OptionConverters$RichOptional$$o() {
            return this.scala$jdk$OptionConverters$RichOptional$$o;
        }

        public Option<A> toScala() {
            return OptionConverters$RichOptional$.MODULE$.toScala$extension(this.scala$jdk$OptionConverters$RichOptional$$o());
        }

        public Option<A> asScala() {
            return OptionConverters$RichOptional$.MODULE$.asScala$extension(this.scala$jdk$OptionConverters$RichOptional$$o());
        }

        public <O> O toJavaPrimitive(OptionShape<A, O> shape) {
            Optional<A> toJavaPrimitive$extension_$this = this.scala$jdk$OptionConverters$RichOptional$$o();
            return shape.fromJava(toJavaPrimitive$extension_$this);
        }

        public int hashCode() {
            return this.scala$jdk$OptionConverters$RichOptional$$o().hashCode();
        }

        public boolean equals(Object x$1) {
            return OptionConverters$RichOptional$.MODULE$.equals$extension(this.scala$jdk$OptionConverters$RichOptional$$o(), x$1);
        }

        public RichOptional(Optional<A> o) {
            this.scala$jdk$OptionConverters$RichOptional$$o = o;
        }
    }

    public static final class RichOptionalDouble {
        private final OptionalDouble scala$jdk$OptionConverters$RichOptionalDouble$$o;

        public OptionalDouble scala$jdk$OptionConverters$RichOptionalDouble$$o() {
            return this.scala$jdk$OptionConverters$RichOptionalDouble$$o;
        }

        public Option<Object> toScala() {
            return OptionConverters$RichOptionalDouble$.MODULE$.toScala$extension(this.scala$jdk$OptionConverters$RichOptionalDouble$$o());
        }

        public Option<Object> asScala() {
            return OptionConverters$RichOptionalDouble$.MODULE$.asScala$extension(this.scala$jdk$OptionConverters$RichOptionalDouble$$o());
        }

        public Optional<Object> toJavaGeneric() {
            return OptionConverters$RichOptionalDouble$.MODULE$.toJavaGeneric$extension(this.scala$jdk$OptionConverters$RichOptionalDouble$$o());
        }

        public int hashCode() {
            return this.scala$jdk$OptionConverters$RichOptionalDouble$$o().hashCode();
        }

        public boolean equals(Object x$1) {
            return OptionConverters$RichOptionalDouble$.MODULE$.equals$extension(this.scala$jdk$OptionConverters$RichOptionalDouble$$o(), x$1);
        }

        public RichOptionalDouble(OptionalDouble o) {
            this.scala$jdk$OptionConverters$RichOptionalDouble$$o = o;
        }
    }

    public static final class RichOptionalInt {
        private final OptionalInt scala$jdk$OptionConverters$RichOptionalInt$$o;

        public OptionalInt scala$jdk$OptionConverters$RichOptionalInt$$o() {
            return this.scala$jdk$OptionConverters$RichOptionalInt$$o;
        }

        public Option<Object> toScala() {
            return OptionConverters$RichOptionalInt$.MODULE$.toScala$extension(this.scala$jdk$OptionConverters$RichOptionalInt$$o());
        }

        public Option<Object> asScala() {
            return OptionConverters$RichOptionalInt$.MODULE$.asScala$extension(this.scala$jdk$OptionConverters$RichOptionalInt$$o());
        }

        public Optional<Object> toJavaGeneric() {
            return OptionConverters$RichOptionalInt$.MODULE$.toJavaGeneric$extension(this.scala$jdk$OptionConverters$RichOptionalInt$$o());
        }

        public int hashCode() {
            return this.scala$jdk$OptionConverters$RichOptionalInt$$o().hashCode();
        }

        public boolean equals(Object x$1) {
            return OptionConverters$RichOptionalInt$.MODULE$.equals$extension(this.scala$jdk$OptionConverters$RichOptionalInt$$o(), x$1);
        }

        public RichOptionalInt(OptionalInt o) {
            this.scala$jdk$OptionConverters$RichOptionalInt$$o = o;
        }
    }

    public static final class RichOptionalLong {
        private final OptionalLong scala$jdk$OptionConverters$RichOptionalLong$$o;

        public OptionalLong scala$jdk$OptionConverters$RichOptionalLong$$o() {
            return this.scala$jdk$OptionConverters$RichOptionalLong$$o;
        }

        public Option<Object> toScala() {
            return OptionConverters$RichOptionalLong$.MODULE$.toScala$extension(this.scala$jdk$OptionConverters$RichOptionalLong$$o());
        }

        public Option<Object> asScala() {
            return OptionConverters$RichOptionalLong$.MODULE$.asScala$extension(this.scala$jdk$OptionConverters$RichOptionalLong$$o());
        }

        public Optional<Object> toJavaGeneric() {
            return OptionConverters$RichOptionalLong$.MODULE$.toJavaGeneric$extension(this.scala$jdk$OptionConverters$RichOptionalLong$$o());
        }

        public int hashCode() {
            return this.scala$jdk$OptionConverters$RichOptionalLong$$o().hashCode();
        }

        public boolean equals(Object x$1) {
            return OptionConverters$RichOptionalLong$.MODULE$.equals$extension(this.scala$jdk$OptionConverters$RichOptionalLong$$o(), x$1);
        }

        public RichOptionalLong(OptionalLong o) {
            this.scala$jdk$OptionConverters$RichOptionalLong$$o = o;
        }
    }
}

