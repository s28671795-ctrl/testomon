/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.ActorRefWithCell;
import akka.actor.ActorSelection;
import akka.actor.ActorSelectionMessage;
import akka.actor.EmptyLocalActorRef;
import akka.actor.InternalActorRef;
import akka.actor.Nobody$;
import akka.actor.ScalaActorSelection;
import akka.actor.SelectChildName;
import akka.actor.SelectChildPattern;
import akka.actor.SelectParent$;
import akka.actor.SelectionPathElement;
import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.Predef$;
import scala.collection.Iterable;
import scala.collection.IterableFactory$;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.IndexedSeq$;
import scala.collection.immutable.Vector;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class ActorSelection$
implements Serializable {
    public static final ActorSelection$ MODULE$ = new ActorSelection$();

    public ScalaActorSelection toScala(ActorSelection sel) {
        return (ScalaActorSelection)((Object)sel);
    }

    public ActorSelection apply(ActorRef anchorRef, String path) {
        return this.apply(anchorRef, Predef$.MODULE$.wrapRefArray((Object[])path.split("/+")));
    }

    public ActorSelection apply(ActorRef anchorRef, Iterable<String> elements) {
        IndexedSeq compiled = elements.iterator().collect(new Serializable(){
            private static final long serialVersionUID = 0L;

            public final <A1 extends String, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (StringOps$.MODULE$.nonEmpty$extension(Predef$.MODULE$.augmentString(A1))) {
                    if (A1.indexOf(63) != -1 || A1.indexOf(42) != -1) {
                        return (B1)new SelectChildPattern(A1);
                    }
                    A1 A12 = A1;
                    String string = "..";
                    if (!(A12 != null ? !A12.equals(string) : string != null)) {
                        return (B1)SelectParent$.MODULE$;
                    }
                    return (B1)new SelectChildName(A1);
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(String x1) {
                String string = x1;
                return StringOps$.MODULE$.nonEmpty$extension(Predef$.MODULE$.augmentString(string));
            }
        }).to(IterableFactory$.MODULE$.toFactory(IndexedSeq$.MODULE$));
        return new ScalaActorSelection(anchorRef, compiled){
            private final ActorRef anchor;
            private final IndexedSeq<SelectionPathElement> path;

            public void $bang(Object msg, ActorRef sender) {
                ScalaActorSelection.$bang$(this, msg, sender);
            }

            public ActorRef $bang$default$2(Object msg) {
                return ScalaActorSelection.$bang$default$2$(this, msg);
            }

            public ActorRef anchor() {
                return this.anchor;
            }

            public IndexedSeq<SelectionPathElement> path() {
                return this.path;
            }
            {
                ScalaActorSelection.$init$(this);
                this.anchor = anchorRef$1;
                this.path = compiled$1;
            }
        };
    }

    public void deliverSelection(InternalActorRef anchor, ActorRef sender, ActorSelectionMessage sel) {
        if (sel.elements().isEmpty()) {
            anchor.tell(sel.msg(), sender);
            return;
        }
        Iterator iter = sel.elements().iterator();
        this.rec$1(anchor, anchor, sel, iter, sender);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ActorSelection$.class);
    }

    private static final EmptyLocalActorRef emptyRef$1(ActorRefWithCell x2$1, InternalActorRef anchor$1, ActorSelectionMessage sel$1) {
        return new EmptyLocalActorRef(x2$1.provider(), anchor$1.path().$div((Iterable)sel$1.elements().map((Function1<SelectionPathElement, String> & Serializable)x$1 -> x$1.toString())), x2$1.underlying().system().eventStream());
    }

    public static final /* synthetic */ boolean $anonfun$deliverSelection$2(SelectChildPattern x4$1, ActorRef c) {
        return x4$1.pattern().matcher(c.path().name()).matches();
    }

    public static final /* synthetic */ boolean $anonfun$deliverSelection$4(SelectChildPattern x4$1, ActorRef c) {
        return x4$1.pattern().matcher(c.path().name()).matches();
    }

    private final void rec$1(InternalActorRef ref, InternalActorRef anchor$1, ActorSelectionMessage sel$1, Iterator iter$1, ActorRef sender$1) {
        block14: {
            InternalActorRef internalActorRef;
            while ((internalActorRef = ref) instanceof ActorRefWithCell) {
                ActorRefWithCell actorRefWithCell = (ActorRefWithCell)internalActorRef;
                SelectionPathElement selectionPathElement = (SelectionPathElement)iter$1.next();
                if (SelectParent$.MODULE$.equals(selectionPathElement)) {
                    InternalActorRef parent = ref.getParent();
                    if (iter$1.isEmpty()) {
                        parent.tell(sel$1.msg(), sender$1);
                        break block14;
                    }
                    ref = parent;
                    continue;
                }
                if (selectionPathElement instanceof SelectChildName) {
                    InternalActorRef child;
                    SelectChildName selectChildName = (SelectChildName)selectionPathElement;
                    String name = selectChildName.name();
                    InternalActorRef internalActorRef2 = child = actorRefWithCell.getSingleChild(name);
                    Nobody$ nobody$ = Nobody$.MODULE$;
                    if (!(internalActorRef2 != null ? !((Object)internalActorRef2).equals(nobody$) : nobody$ != null)) {
                        if (!sel$1.wildcardFanOut()) {
                            ActorSelection$.emptyRef$1(actorRefWithCell, anchor$1, sel$1).tell(sel$1, sender$1);
                        }
                        break block14;
                    }
                    if (iter$1.isEmpty()) {
                        child.tell(sel$1.msg(), sender$1);
                        break block14;
                    }
                    ref = child;
                    continue;
                }
                if (selectionPathElement instanceof SelectChildPattern) {
                    SelectChildPattern selectChildPattern = (SelectChildPattern)selectionPathElement;
                    scala.collection.immutable.Iterable<ActorRef> chldr = actorRefWithCell.children();
                    if (iter$1.isEmpty()) {
                        scala.collection.immutable.Iterable matchingChildren = (scala.collection.immutable.Iterable)chldr.filter((Function1<ActorRef, Object> & Serializable)c -> BoxesRunTime.boxToBoolean(ActorSelection$.$anonfun$deliverSelection$2(selectChildPattern, c)));
                        if (matchingChildren.isEmpty() && !sel$1.wildcardFanOut()) {
                            ActorSelection$.emptyRef$1(actorRefWithCell, anchor$1, sel$1).tell(sel$1, sender$1);
                        } else {
                            matchingChildren.foreach((Function1<ActorRef, Object> & Serializable)x$2 -> {
                                x$2.tell(sel$1.msg(), sender$1);
                                return BoxedUnit.UNIT;
                            });
                        }
                    } else {
                        scala.collection.immutable.Iterable matchingChildren = (scala.collection.immutable.Iterable)chldr.filter((Function1<ActorRef, Object> & Serializable)c -> BoxesRunTime.boxToBoolean(ActorSelection$.$anonfun$deliverSelection$4(selectChildPattern, c)));
                        if (matchingChildren.isEmpty() && !sel$1.wildcardFanOut()) {
                            ActorSelection$.emptyRef$1(actorRefWithCell, anchor$1, sel$1).tell(sel$1, sender$1);
                        } else {
                            Vector<SelectionPathElement> x$1 = iter$1.toVector();
                            boolean x$22 = sel$1.wildcardFanOut() || matchingChildren.size() > 1;
                            Object x$3 = sel$1.copy$default$1();
                            ActorSelectionMessage m4 = sel$1.copy(x$3, x$1, x$22);
                            matchingChildren.foreach((Function1<ActorRef, Object> & Serializable)c -> {
                                ActorSelection$.MODULE$.deliverSelection((InternalActorRef)c, sender$1, m4);
                                return BoxedUnit.UNIT;
                            });
                        }
                    }
                    break block14;
                }
                throw new MatchError(selectionPathElement);
            }
            Vector<SelectionPathElement> x$4 = iter$1.toVector();
            Object x$5 = sel$1.copy$default$1();
            boolean x$6 = sel$1.copy$default$3();
            ref.tell(sel$1.copy(x$5, x$4, x$6), sender$1);
        }
    }

    private ActorSelection$() {
    }
}

