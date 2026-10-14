/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class AbbaDetector {
    private static final boolean TRACE = false;
    private static final ThreadLocal<Deque<Object>> STACK = ThreadLocal.withInitial(ArrayDeque::new);
    private static final Map<Object, Map<Object, Exception>> LOCK_ORDERING = new WeakHashMap<Object, Map<Object, Exception>>();
    private static final Set<String> KNOWN_DEADLOCKS = new HashSet<String>();

    public static Object begin(Object clazz) {
        Deque<Object> deque;
        if (clazz == null) {
            clazz = new SecurityManager(){
                Class<?> clazz = this.getClassContext()[2];
            }.clazz;
        }
        if (!(deque = STACK.get()).isEmpty()) {
            Object object;
            if (deque.contains(clazz)) {
                return clazz;
            }
            while (!deque.isEmpty() && !Thread.holdsLock(object = deque.peek())) {
                deque.pop();
            }
        }
        if (!deque.isEmpty()) {
            AbbaDetector.markHigher(clazz, deque);
        }
        deque.push(clazz);
        return clazz;
    }

    private static Object getTest(Object object) {
        return object;
    }

    private static String getObjectName(Object object) {
        return object.getClass().getSimpleName() + "@" + System.identityHashCode(object);
    }

    private static synchronized void markHigher(Object object, Deque<Object> deque) {
        Object object2 = AbbaDetector.getTest(object);
        Map<Object, Exception> map = LOCK_ORDERING.get(object2);
        if (map == null) {
            map = new WeakHashMap<Object, Exception>();
            LOCK_ORDERING.put(object2, map);
        }
        Exception exception = null;
        for (Object object3 : deque) {
            String string;
            Exception exception2;
            Object object4 = AbbaDetector.getTest(object3);
            if (object4 == object2) continue;
            Map<Object, Exception> map2 = LOCK_ORDERING.get(object4);
            if (map2 != null && (exception2 = map2.get(object2)) != null && !KNOWN_DEADLOCKS.contains(string = object2.getClass() + " " + object4.getClass())) {
                String string2 = AbbaDetector.getObjectName(object2) + " synchronized after \n " + AbbaDetector.getObjectName(object4) + ", but in the past before";
                RuntimeException runtimeException = new RuntimeException(string2);
                runtimeException.initCause(exception2);
                runtimeException.printStackTrace(System.out);
                KNOWN_DEADLOCKS.add(string);
            }
            if (map.containsKey(object4)) continue;
            if (exception == null) {
                exception = new Exception("Before");
            }
            map.put(object4, exception);
        }
    }
}

