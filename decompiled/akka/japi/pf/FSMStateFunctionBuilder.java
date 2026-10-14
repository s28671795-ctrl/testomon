/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.pf;

import akka.actor.FSM;
import akka.japi.pf.FI;
import akka.japi.pf.PFBuilder;
import java.util.List;
import scala.PartialFunction;

public class FSMStateFunctionBuilder<S, D> {
    private PFBuilder<FSM.Event<D>, FSM.State<S, D>> builder = new PFBuilder();

    private FSMStateFunctionBuilder<S, D> erasedEvent(final Object object, final Object object2, final FI.TypedPredicate2 typedPredicate2, final FI.Apply2 apply2) {
        this.builder.match(FSM.Event.class, new FI.TypedPredicate<FSM.Event>(){

            @Override
            public boolean defined(FSM.Event event) {
                Class clazz;
                boolean bl = true;
                if (object != null) {
                    if (object instanceof Class) {
                        clazz = (Class)object;
                        bl = clazz.isInstance(event.event());
                    } else {
                        bl = object.equals(event.event());
                    }
                }
                if (bl && object2 != null) {
                    if (object2 instanceof Class) {
                        clazz = (Class)object2;
                        bl = clazz.isInstance(event.stateData());
                    } else {
                        bl = object2.equals(event.stateData());
                    }
                }
                if (bl && typedPredicate2 != null) {
                    boolean bl2;
                    bl = bl2 = typedPredicate2.defined(event.event(), event.stateData());
                }
                return bl;
            }
        }, new FI.Apply<FSM.Event, FSM.State<S, D>>(){

            @Override
            public FSM.State<S, D> apply(FSM.Event event) throws Exception {
                FSM.State state = (FSM.State)apply2.apply(event.event(), event.stateData());
                return state;
            }
        });
        return this;
    }

    public final <P, Q> FSMStateFunctionBuilder<S, D> event(Class<P> clazz, Class<Q> clazz2, FI.TypedPredicate2<P, Q> typedPredicate2, FI.Apply2<P, Q, FSM.State<S, D>> apply2) {
        this.erasedEvent(clazz, clazz2, typedPredicate2, apply2);
        return this;
    }

    public <P, Q> FSMStateFunctionBuilder<S, D> event(Class<P> clazz, Class<Q> clazz2, FI.Apply2<P, Q, FSM.State<S, D>> apply2) {
        return this.erasedEvent(clazz, clazz2, null, apply2);
    }

    public <P> FSMStateFunctionBuilder<S, D> event(Class<P> clazz, FI.TypedPredicate2<P, D> typedPredicate2, FI.Apply2<P, D, FSM.State<S, D>> apply2) {
        return this.erasedEvent(clazz, null, typedPredicate2, apply2);
    }

    public <P> FSMStateFunctionBuilder<S, D> event(Class<P> clazz, FI.Apply2<P, D, FSM.State<S, D>> apply2) {
        return this.erasedEvent(clazz, null, null, apply2);
    }

    public FSMStateFunctionBuilder<S, D> event(FI.TypedPredicate2<Object, D> typedPredicate2, FI.Apply2<Object, D, FSM.State<S, D>> apply2) {
        return this.erasedEvent(null, null, typedPredicate2, apply2);
    }

    public <Q> FSMStateFunctionBuilder<S, D> event(final List<Object> list, final Class<Q> clazz, final FI.Apply2<Object, Q, FSM.State<S, D>> apply2) {
        this.builder.match(FSM.Event.class, new FI.TypedPredicate<FSM.Event>(){

            @Override
            public boolean defined(FSM.Event event) {
                if (clazz != null && !clazz.isInstance(event.stateData())) {
                    return false;
                }
                boolean bl = false;
                Object object = event.event();
                for (Object e : list) {
                    if (e instanceof Class) {
                        Class clazz2 = (Class)e;
                        bl = clazz2.isInstance(object);
                    } else {
                        bl = object.equals(e);
                    }
                    if (!bl) continue;
                    break;
                }
                return bl;
            }
        }, new FI.Apply<FSM.Event, FSM.State<S, D>>(){

            @Override
            public FSM.State<S, D> apply(FSM.Event event) throws Exception {
                Object d = event.stateData();
                return (FSM.State)apply2.apply(event.event(), d);
            }
        });
        return this;
    }

    public FSMStateFunctionBuilder<S, D> event(List<Object> list, FI.Apply2<Object, D, FSM.State<S, D>> apply2) {
        return this.event(list, null, apply2);
    }

    public <P, Q> FSMStateFunctionBuilder<S, D> eventEquals(P p, Class<Q> clazz, FI.Apply2<P, Q, FSM.State<S, D>> apply2) {
        return this.erasedEvent(p, clazz, null, apply2);
    }

    public <P> FSMStateFunctionBuilder<S, D> eventEquals(P p, FI.Apply2<P, D, FSM.State<S, D>> apply2) {
        return this.erasedEvent(p, null, null, apply2);
    }

    public FSMStateFunctionBuilder<S, D> anyEvent(FI.Apply2<Object, D, FSM.State<S, D>> apply2) {
        return this.erasedEvent(null, null, null, apply2);
    }

    public PartialFunction<FSM.Event<D>, FSM.State<S, D>> build() {
        return this.builder.build();
    }
}

