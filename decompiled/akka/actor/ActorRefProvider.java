/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ActorSystemImpl;
import akka.actor.Address;
import akka.actor.Deploy;
import akka.actor.Deployer;
import akka.actor.InternalActorRef;
import akka.actor.LocalActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.annotation.DoNotInherit;
import akka.annotation.InternalApi;
import akka.serialization.Serialization;
import scala.Option;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dea\u0002\r\u001a!\u0003\r\nA\b\u0005\u0006K\u00011\tA\n\u0005\u0006W\u00011\t\u0001\f\u0005\u0006k\u00011\tA\u000e\u0005\u0006u\u00011\tA\u000e\u0005\u0006w\u00011\t\u0001\u0010\u0005\u0007{\u00011\ta\u0007\u001f\t\u000b\u0015\u0003a\u0011\u0001$\t\u000b)\u0003a\u0011A&\t\rM\u0003a\u0011A\u000eU\u0011\u0015i\u0006A\"\u0001_\u0011\u0015\u0011\u0007A\"\u0001d\u0011\u0015\u0011\u0007A\"\u0001e\u0011\u0015\u0011\bA\"\u0001'\u0011\u0019\u0019\bA\"\u0001\u001ci\")\u0011\u0010\u0001D\u0001u\"1A\u0010\u0001D\u00017uDq!!\r\u0001\r\u0003\t\u0019\u0004C\u0004\u00022\u00011\t!a\u000e\t\u000f\u0005m\u0002A\"\u0001\u0002>!9\u0011\u0011\u000b\u0001\u0007\u0002\u0005M\u0003bBA.\u0001\u0019\u0005\u0011Q\f\u0005\t\u0003?\u0002a\u0011A\u000e\u0002b!A\u0011\u0011\u0010\u0001\u0007\u0002m\tYH\u0001\tBGR|'OU3g!J|g/\u001b3fe*\u0011!dG\u0001\u0006C\u000e$xN\u001d\u0006\u00029\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001A\u0010\u0011\u0005\u0001\u001aS\"A\u0011\u000b\u0003\t\nQa]2bY\u0006L!\u0001J\u0011\u0003\r\u0005s\u0017PU3g\u00031\u0011xn\u001c;Hk\u0006\u0014H-[1o+\u00059\u0003C\u0001\u0015*\u001b\u0005I\u0012B\u0001\u0016\u001a\u0005AIe\u000e^3s]\u0006d\u0017i\u0019;peJ+g-\u0001\bs_>$x)^1sI&\fg.\u0011;\u0015\u00055\u0002\u0004C\u0001\u0015/\u0013\ty\u0013D\u0001\u0005BGR|'OU3g\u0011\u0015\t$\u00011\u00013\u0003\u001d\tG\r\u001a:fgN\u0004\"\u0001K\u001a\n\u0005QJ\"aB!eIJ,7o]\u0001\tOV\f'\u000fZ5b]V\tq\u0007\u0005\u0002)q%\u0011\u0011(\u0007\u0002\u000e\u0019>\u001c\u0017\r\\!di>\u0014(+\u001a4\u0002\u001dML8\u000f^3n\u000fV\f'\u000fZ5b]\u0006YA-Z1e\u0019\u0016$H/\u001a:t+\u0005i\u0013!C5h]>\u0014XMU3gQ\t1q\b\u0005\u0002A\u00076\t\u0011I\u0003\u0002C7\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005\u0011\u000b%aC%oi\u0016\u0014h.\u00197Ba&\f\u0001B]8piB\u000bG\u000f[\u000b\u0002\u000fB\u0011\u0001\u0006S\u0005\u0003\u0013f\u0011\u0011\"Q2u_J\u0004\u0016\r\u001e5\u0002\u0011M,G\u000f^5oON,\u0012\u0001\u0014\t\u0003\u001bBs!\u0001\u000b(\n\u0005=K\u0012aC!di>\u00148+_:uK6L!!\u0015*\u0003\u0011M+G\u000f^5oONT!aT\r\u0002\t%t\u0017\u000e\u001e\u000b\u0003+b\u0003\"\u0001\t,\n\u0005]\u000b#\u0001B+oSRDQ!W\u0005A\u0002i\u000baa]=ti\u0016l\u0007C\u0001\u0015\\\u0013\ta\u0016DA\bBGR|'oU=ti\u0016l\u0017*\u001c9m\u0003!!W\r\u001d7ps\u0016\u0014X#A0\u0011\u0005!\u0002\u0017BA1\u001a\u0005!!U\r\u001d7ps\u0016\u0014\u0018\u0001\u0003;f[B\u0004\u0016\r\u001e5\u0015\u0003\u001d#\"aR3\t\u000b\u0019d\u0001\u0019A4\u0002\rA\u0014XMZ5y!\tAwN\u0004\u0002j[B\u0011!.I\u0007\u0002W*\u0011A.H\u0001\u0007yI|w\u000e\u001e \n\u00059\f\u0013A\u0002)sK\u0012,g-\u0003\u0002qc\n11\u000b\u001e:j]\u001eT!A\\\u0011\u0002\u001bQ,W\u000e]\"p]R\f\u0017N\\3s\u0003E\u0011XmZ5ti\u0016\u0014H+Z7q\u0003\u000e$xN\u001d\u000b\u0004+V<\b\"\u0002<\u000f\u0001\u00049\u0013\u0001C1di>\u0014(+\u001a4\t\u000bat\u0001\u0019A$\u0002\tA\fG\u000f[\u0001\u0014k:\u0014XmZ5ti\u0016\u0014H+Z7q\u0003\u000e$xN\u001d\u000b\u0003+nDQ\u0001_\bA\u0002\u001d\u000bq!Y2u_J|e\rF\b(}~\fI!!\u0004\u0002\u0010\u0005e\u0011\u0011FA\u0017\u0011\u0015I\u0006\u00031\u0001[\u0011\u001d\t\t\u0001\u0005a\u0001\u0003\u0007\tQ\u0001\u001d:paN\u00042\u0001KA\u0003\u0013\r\t9!\u0007\u0002\u0006!J|\u0007o\u001d\u0005\u0007\u0003\u0017\u0001\u0002\u0019A\u0014\u0002\u0015M,\b/\u001a:wSN|'\u000fC\u0003y!\u0001\u0007q\tC\u0004\u0002\u0012A\u0001\r!a\u0005\u0002\u001bML8\u000f^3n'\u0016\u0014h/[2f!\r\u0001\u0013QC\u0005\u0004\u0003/\t#a\u0002\"p_2,\u0017M\u001c\u0005\b\u00037\u0001\u0002\u0019AA\u000f\u0003\u0019!W\r\u001d7psB)\u0001%a\b\u0002$%\u0019\u0011\u0011E\u0011\u0003\r=\u0003H/[8o!\rA\u0013QE\u0005\u0004\u0003OI\"A\u0002#fa2|\u0017\u0010C\u0004\u0002,A\u0001\r!a\u0005\u0002\u00191|wn[;q\t\u0016\u0004Hn\\=\t\u000f\u0005=\u0002\u00031\u0001\u0002\u0014\u0005)\u0011m]=oG\u0006y!/Z:pYZ,\u0017i\u0019;peJ+g\rF\u0002.\u0003kAQ\u0001_\tA\u0002\u001d$2!LA\u001d\u0011\u0015A(\u00031\u0001H\u0003E!XM]7j]\u0006$\u0018n\u001c8GkR,(/Z\u000b\u0003\u0003\u007f\u0001b!!\u0011\u0002H\u0005-SBAA\"\u0015\r\t)%I\u0001\u000bG>t7-\u001e:sK:$\u0018\u0002BA%\u0003\u0007\u0012aAR;ukJ,\u0007c\u0001\u0015\u0002N%\u0019\u0011qJ\r\u0003\u0015Q+'/\\5oCR,G-A\u000bhKR,\u0005\u0010^3s]\u0006d\u0017\t\u001a3sKN\u001chi\u001c:\u0015\t\u0005U\u0013q\u000b\t\u0005A\u0005}!\u0007\u0003\u0004\u0002ZQ\u0001\rAM\u0001\u0005C\u0012$'/A\thKR$UMZ1vYR\fE\r\u001a:fgN,\u0012AM\u0001\u0019g\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8J]\u001a|'/\\1uS>tWCAA2!\u0011\t)'!\u001d\u000f\t\u0005\u001d\u0014QN\u0007\u0003\u0003SR1!a\u001b\u001c\u00035\u0019XM]5bY&T\u0018\r^5p]&!\u0011qNA5\u00035\u0019VM]5bY&T\u0018\r^5p]&!\u00111OA;\u0005-IeNZ8s[\u0006$\u0018n\u001c8\u000b\t\u0005=\u0014\u0011\u000e\u0015\u0003-}\nQ\"\u00193ee\u0016\u001c8o\u0015;sS:<W#A4)\u0005]y\u0004f\u0001\u0001\u0002\u0002B\u0019\u0001)a!\n\u0007\u0005\u0015\u0015I\u0001\u0007E_:{G/\u00138iKJLG\u000f")
@DoNotInherit
public interface ActorRefProvider {
    public InternalActorRef rootGuardian();

    public ActorRef rootGuardianAt(Address var1);

    public LocalActorRef guardian();

    public LocalActorRef systemGuardian();

    public ActorRef deadLetters();

    @InternalApi
    public ActorRef ignoreRef();

    public ActorPath rootPath();

    public ActorSystem.Settings settings();

    public void init(ActorSystemImpl var1);

    public Deployer deployer();

    public ActorPath tempPath();

    public ActorPath tempPath(String var1);

    public InternalActorRef tempContainer();

    public void registerTempActor(InternalActorRef var1, ActorPath var2);

    public void unregisterTempActor(ActorPath var1);

    public InternalActorRef actorOf(ActorSystemImpl var1, Props var2, InternalActorRef var3, ActorPath var4, boolean var5, Option<Deploy> var6, boolean var7, boolean var8);

    public ActorRef resolveActorRef(String var1);

    public ActorRef resolveActorRef(ActorPath var1);

    public Future<Terminated> terminationFuture();

    public Option<Address> getExternalAddressFor(Address var1);

    public Address getDefaultAddress();

    @InternalApi
    public Serialization.Information serializationInformation();

    @InternalApi
    public String addressString();
}

