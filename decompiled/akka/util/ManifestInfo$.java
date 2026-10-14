/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import akka.actor.ExtensionIdProvider;
import akka.util.ManifestInfo;
import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Some;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.MapView;
import scala.collection.SeqOps;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.math.Ordering$;
import scala.math.Ordering$String$;
import scala.runtime.ScalaRunTime$;

public final class ManifestInfo$
implements ExtensionId<ManifestInfo>,
ExtensionIdProvider {
    public static final ManifestInfo$ MODULE$ = new ManifestInfo$();
    private static final String akka$util$ManifestInfo$$ImplTitle;
    private static final String akka$util$ManifestInfo$$ImplVersion;
    private static final String akka$util$ManifestInfo$$ImplVendor;
    private static final String akka$util$ManifestInfo$$BundleName;
    private static final String akka$util$ManifestInfo$$BundleVersion;
    private static final String akka$util$ManifestInfo$$BundleVendor;
    private static final Set<String> akka$util$ManifestInfo$$knownVendors;

    static {
        ExtensionId.$init$(MODULE$);
        akka$util$ManifestInfo$$ImplTitle = "Implementation-Title";
        akka$util$ManifestInfo$$ImplVersion = "Implementation-Version";
        akka$util$ManifestInfo$$ImplVendor = "Implementation-Vendor-Id";
        akka$util$ManifestInfo$$BundleName = "Bundle-Name";
        akka$util$ManifestInfo$$BundleVersion = "Bundle-Version";
        akka$util$ManifestInfo$$BundleVendor = "Bundle-Vendor";
        akka$util$ManifestInfo$$knownVendors = (Set)Predef$.MODULE$.Set().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new String[]{"com.typesafe.akka", "com.lightbend.akka", "Lightbend Inc.", "Lightbend", "com.lightbend.lagom", "com.typesafe.play"}));
    }

    @Override
    public Extension apply(ActorSystem system) {
        return ExtensionId.apply$((ExtensionId)this, system);
    }

    @Override
    public Extension apply(ClassicActorSystemProvider system) {
        return ExtensionId.apply$((ExtensionId)this, system);
    }

    @Override
    public final int hashCode() {
        return ExtensionId.hashCode$(this);
    }

    @Override
    public final boolean equals(Object other) {
        return ExtensionId.equals$(this, other);
    }

    public String akka$util$ManifestInfo$$ImplTitle() {
        return akka$util$ManifestInfo$$ImplTitle;
    }

    public String akka$util$ManifestInfo$$ImplVersion() {
        return akka$util$ManifestInfo$$ImplVersion;
    }

    public String akka$util$ManifestInfo$$ImplVendor() {
        return akka$util$ManifestInfo$$ImplVendor;
    }

    public String akka$util$ManifestInfo$$BundleName() {
        return akka$util$ManifestInfo$$BundleName;
    }

    public String akka$util$ManifestInfo$$BundleVersion() {
        return akka$util$ManifestInfo$$BundleVersion;
    }

    public String akka$util$ManifestInfo$$BundleVendor() {
        return akka$util$ManifestInfo$$BundleVendor;
    }

    public Set<String> akka$util$ManifestInfo$$knownVendors() {
        return akka$util$ManifestInfo$$knownVendors;
    }

    @Override
    public ManifestInfo get(ActorSystem system) {
        return (ManifestInfo)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public ManifestInfo get(ClassicActorSystemProvider system) {
        return (ManifestInfo)ExtensionId.get$((ExtensionId)this, system);
    }

    public ManifestInfo$ lookup() {
        return this;
    }

    @Override
    public ManifestInfo createExtension(ExtendedActorSystem system) {
        return new ManifestInfo(system);
    }

    public Option<String> checkSameVersion(String productName, Seq<String> dependencies, Map<String, ManifestInfo.Version> versions) {
        MapView<String, ManifestInfo.Version> filteredVersions = versions.filterKeys(dependencies.toSet());
        Set values2 = filteredVersions.values().toSet();
        if (values2.size() > 1) {
            ManifestInfo.Version highestVersion = (ManifestInfo.Version)values2.max(Ordering$.MODULE$.ordered(Predef$.MODULE$.$conforms()));
            String toBeUpdated = ((IterableOnceOps)filteredVersions.collect(new Serializable(highestVersion){
                private static final long serialVersionUID = 0L;
                private final ManifestInfo.Version highestVersion$1;

                public final <A1 extends Tuple2<String, ManifestInfo.Version>, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                    A1 A1 = x1;
                    if (A1 != null) {
                        ManifestInfo.Version v;
                        String k = A1._1();
                        ManifestInfo.Version version = v = A1._2();
                        ManifestInfo.Version version2 = this.highestVersion$1;
                        if (version == null ? version2 != null : !((Object)version).equals(version2)) {
                            return (B1)String.valueOf(k);
                        }
                    }
                    return function1.apply(x1);
                }

                public final boolean isDefinedAt(Tuple2<String, ManifestInfo.Version> x1) {
                    Tuple2<String, ManifestInfo.Version> tuple2 = x1;
                    if (tuple2 != null) {
                        ManifestInfo.Version v;
                        ManifestInfo.Version version = v = tuple2._2();
                        ManifestInfo.Version version2 = this.highestVersion$1;
                        if (version == null ? version2 != null : !((Object)version).equals(version2)) {
                            return true;
                        }
                    }
                    return false;
                }
                {
                    this.highestVersion$1 = highestVersion$1;
                }
            })).mkString(", ");
            String groupedByVersion = ((IterableOnceOps)((IterableOps)((IterableOps)filteredVersions.toSeq().groupBy((Function1<Tuple2, ManifestInfo.Version> & Serializable)x0$1 -> {
                Tuple2 tuple2 = x0$1;
                if (tuple2 != null) {
                    ManifestInfo.Version v = (ManifestInfo.Version)tuple2._2();
                    return v;
                }
                throw new MatchError(tuple2);
            }).toSeq().sortBy((Function1<Tuple2, ManifestInfo.Version> & Serializable)x$1 -> (ManifestInfo.Version)x$1._1(), Ordering$.MODULE$.ordered(Predef$.MODULE$.$conforms()))).map((Function1<Tuple2, Tuple2> & Serializable)x0$2 -> {
                Tuple2 tuple2 = x0$2;
                if (tuple2 != null) {
                    ManifestInfo.Version k = (ManifestInfo.Version)tuple2._1();
                    Seq v = (Seq)tuple2._2();
                    return Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(k), ((IterableOnceOps)((SeqOps)v.map((Function1<Tuple2, String> & Serializable)x$2 -> (String)x$2._1())).sorted(Ordering$String$.MODULE$)).mkString("[", ", ", "]"));
                }
                throw new MatchError(tuple2);
            })).map((Function1<Tuple2, String> & Serializable)x0$3 -> {
                Tuple2 tuple2 = x0$3;
                if (tuple2 != null) {
                    ManifestInfo.Version k = (ManifestInfo.Version)tuple2._1();
                    String v = (String)tuple2._2();
                    return new StringBuilder(4).append("(").append(k).append(", ").append(v).append(")").toString();
                }
                throw new MatchError(tuple2);
            })).mkString(", ");
            return new Some<String>(new StringBuilder(393).append("You are using version ").append(highestVersion).append(" of ").append(productName).append(", but it appears ").append("you (perhaps indirectly) also depend on older versions of related artifacts. ").append("You can solve this by adding an explicit dependency on version ").append(highestVersion).append(" ").append("of the [").append(toBeUpdated).append("] artifacts to your project. ").append("Here's a complete collection of detected artifacts: ").append(groupedByVersion).append(". ").append("See also: https://doc.akka.io/docs/akka/current/common/binary-compatibility-rules.html#mixed-versioning-is-not-allowed").toString());
        }
        return None$.MODULE$;
    }

    private ManifestInfo$() {
    }
}

