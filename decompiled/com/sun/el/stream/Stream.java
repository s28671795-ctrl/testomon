/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.stream;

import com.sun.el.lang.ELArithmetic;
import com.sun.el.lang.ELSupport;
import com.sun.el.stream.Operator;
import com.sun.el.stream.Optional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;
import javax.el.ELException;
import javax.el.LambdaExpression;

public class Stream {
    private Iterator<Object> source;
    private Stream upstream;
    private Operator op;

    Stream(Iterator<Object> source2) {
        this.source = source2;
    }

    Stream(Stream upstream, Operator op) {
        this.upstream = upstream;
        this.op = op;
    }

    public Iterator<Object> iterator() {
        if (this.source != null) {
            return this.source;
        }
        return this.op.iterator(this.upstream.iterator());
    }

    public Stream filter(final LambdaExpression predicate) {
        return new Stream(this, new Operator(){

            @Override
            public Iterator<Object> iterator(Iterator<Object> upstream) {
                return new Iterator2(upstream){

                    @Override
                    public void doItem(Object item) {
                        if (((Boolean)predicate.invoke(item)).booleanValue()) {
                            this.yield(item);
                        }
                    }
                };
            }
        });
    }

    public Stream map(final LambdaExpression mapper2) {
        return new Stream(this, new Operator(){

            @Override
            public Iterator<Object> iterator(Iterator<Object> up) {
                return new Iterator1(up){

                    @Override
                    public Object next() {
                        return mapper2.invoke(this.iter.next());
                    }
                };
            }
        });
    }

    public Stream peek(final LambdaExpression comsumer) {
        return new Stream(this, new Operator(){

            @Override
            public Iterator<Object> iterator(Iterator<Object> up) {
                return new Iterator2(up){

                    @Override
                    void doItem(Object item) {
                        comsumer.invoke(item);
                        this.yield(item);
                    }
                };
            }
        });
    }

    public Stream limit(final long n) {
        if (n < 0L) {
            throw new IllegalArgumentException("limit must be non-negative");
        }
        return new Stream(this, new Operator(){

            @Override
            public Iterator<Object> iterator(final Iterator<Object> up) {
                return new Iterator0(){
                    long limit;
                    {
                        this.limit = n;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.limit > 0L ? up.hasNext() : false;
                    }

                    @Override
                    public Object next() {
                        --this.limit;
                        return up.next();
                    }
                };
            }
        });
    }

    public Stream substream(final long startIndex) {
        if (startIndex < 0L) {
            throw new IllegalArgumentException("substream index must be non-negative");
        }
        return new Stream(this, new Operator(){
            long skip;
            {
                this.skip = startIndex;
            }

            @Override
            public Iterator<Object> iterator(Iterator<Object> up) {
                while (this.skip > 0L && up.hasNext()) {
                    up.next();
                    --this.skip;
                }
                return up;
            }
        });
    }

    public Stream substream(long startIndex, long endIndex) {
        return this.substream(startIndex).limit(endIndex - startIndex);
    }

    public Stream distinct() {
        return new Stream(this, new Operator(){

            @Override
            public Iterator<Object> iterator(Iterator<Object> up) {
                return new Iterator2(up){
                    private Set<Object> set;
                    {
                        this.set = new HashSet<Object>();
                    }

                    @Override
                    public void doItem(Object item) {
                        if (this.set.add(item)) {
                            this.yield(item);
                        }
                    }
                };
            }
        });
    }

    public Stream sorted() {
        return new Stream(this, new Operator(){
            private PriorityQueue<Object> queue = null;

            @Override
            public Iterator<Object> iterator(Iterator<Object> up) {
                if (this.queue == null) {
                    this.queue = new PriorityQueue<Object>(16, new Comparator<Object>(){

                        @Override
                        public int compare(Object o1, Object o2) {
                            return ((Comparable)o1).compareTo(o2);
                        }
                    });
                    while (up.hasNext()) {
                        this.queue.add(up.next());
                    }
                }
                return new Iterator0(){

                    @Override
                    public boolean hasNext() {
                        return !queue.isEmpty();
                    }

                    @Override
                    public Object next() {
                        return queue.remove();
                    }
                };
            }
        });
    }

    public Stream sorted(final LambdaExpression comparator) {
        return new Stream(this, new Operator(){
            private PriorityQueue<Object> queue = null;

            @Override
            public Iterator<Object> iterator(Iterator<Object> up) {
                if (this.queue == null) {
                    this.queue = new PriorityQueue<Object>(16, new Comparator<Object>(){

                        @Override
                        public int compare(Object o1, Object o2) {
                            return (Integer)ELSupport.coerceToType(comparator.invoke(o1, o2), Integer.class);
                        }
                    });
                    while (up.hasNext()) {
                        this.queue.add(up.next());
                    }
                }
                return new Iterator0(){

                    @Override
                    public boolean hasNext() {
                        return !queue.isEmpty();
                    }

                    @Override
                    public Object next() {
                        return queue.remove();
                    }
                };
            }
        });
    }

    public Stream flatMap(final LambdaExpression mapper2) {
        return new Stream(this, new Operator(){

            @Override
            public Iterator<Object> iterator(final Iterator<Object> upstream) {
                return new Iterator0(){
                    Iterator<Object> iter = null;

                    @Override
                    public boolean hasNext() {
                        while (true) {
                            if (this.iter == null) {
                                if (!upstream.hasNext()) {
                                    return false;
                                }
                                Object mapped = mapper2.invoke(upstream.next());
                                if (!(mapped instanceof Stream)) {
                                    throw new ELException("Expecting a Stream from flatMap's mapper function.");
                                }
                                this.iter = ((Stream)mapped).iterator();
                                continue;
                            }
                            if (this.iter.hasNext()) {
                                return true;
                            }
                            this.iter = null;
                        }
                    }

                    @Override
                    public Object next() {
                        if (this.iter == null) {
                            return null;
                        }
                        return this.iter.next();
                    }
                };
            }
        });
    }

    public Object reduce(Object base, LambdaExpression op) {
        Iterator<Object> iter = this.iterator();
        while (iter.hasNext()) {
            base = op.invoke(base, iter.next());
        }
        return base;
    }

    public Optional reduce(LambdaExpression op) {
        Iterator<Object> iter = this.iterator();
        if (iter.hasNext()) {
            Object base = iter.next();
            while (iter.hasNext()) {
                base = op.invoke(base, iter.next());
            }
            return new Optional(base);
        }
        return new Optional();
    }

    public void forEach(LambdaExpression comsumer) {
        Iterator<Object> iter = this.iterator();
        while (iter.hasNext()) {
            comsumer.invoke(iter.next());
        }
    }

    public boolean anyMatch(LambdaExpression predicate) {
        Iterator<Object> iter = this.iterator();
        while (iter.hasNext()) {
            if (!((Boolean)predicate.invoke(iter.next())).booleanValue()) continue;
            return true;
        }
        return false;
    }

    public boolean allMatch(LambdaExpression predicate) {
        Iterator<Object> iter = this.iterator();
        while (iter.hasNext()) {
            if (((Boolean)predicate.invoke(iter.next())).booleanValue()) continue;
            return false;
        }
        return true;
    }

    public boolean noneMatch(LambdaExpression predicate) {
        Iterator<Object> iter = this.iterator();
        while (iter.hasNext()) {
            if (!((Boolean)predicate.invoke(iter.next())).booleanValue()) continue;
            return false;
        }
        return true;
    }

    public Object[] toArray() {
        Iterator<Object> iter = this.iterator();
        ArrayList<Object> al = new ArrayList<Object>();
        while (iter.hasNext()) {
            al.add(iter.next());
        }
        return al.toArray();
    }

    public Object toList() {
        Iterator<Object> iter = this.iterator();
        ArrayList<Object> al = new ArrayList<Object>();
        while (iter.hasNext()) {
            al.add(iter.next());
        }
        return al;
    }

    public Optional findFirst() {
        Iterator<Object> iter = this.iterator();
        if (iter.hasNext()) {
            return new Optional(iter.next());
        }
        return new Optional();
    }

    public Object sum() {
        Number sum = 0L;
        Iterator<Object> iter = this.iterator();
        while (iter.hasNext()) {
            sum = ELArithmetic.add(sum, iter.next());
        }
        return sum;
    }

    public Object count() {
        long count = 0L;
        Iterator<Object> iter = this.iterator();
        while (iter.hasNext()) {
            ++count;
            iter.next();
        }
        return count;
    }

    public Optional min() {
        Object min2 = null;
        Iterator<Object> iter = this.iterator();
        while (iter.hasNext()) {
            Object item = iter.next();
            if (min2 != null && ELSupport.compare(min2, item) <= 0) continue;
            min2 = item;
        }
        if (min2 == null) {
            return new Optional();
        }
        return new Optional(min2);
    }

    public Optional max() {
        Object max = null;
        Iterator<Object> iter = this.iterator();
        while (iter.hasNext()) {
            Object item = iter.next();
            if (max != null && ELSupport.compare(max, item) >= 0) continue;
            max = item;
        }
        if (max == null) {
            return new Optional();
        }
        return new Optional(max);
    }

    public Optional min(LambdaExpression comparator) {
        Object min2 = null;
        Iterator<Object> iter = this.iterator();
        while (iter.hasNext()) {
            Object item = iter.next();
            if (min2 != null && ELSupport.compare(comparator.invoke(item, min2), 0L) >= 0) continue;
            min2 = item;
        }
        if (min2 == null) {
            return new Optional();
        }
        return new Optional(min2);
    }

    public Optional max(LambdaExpression comparator) {
        Object max = null;
        Iterator<Object> iter = this.iterator();
        while (iter.hasNext()) {
            Object item = iter.next();
            if (max != null && ELSupport.compare(comparator.invoke(max, item), 0L) >= 0) continue;
            max = item;
        }
        if (max == null) {
            return new Optional();
        }
        return new Optional(max);
    }

    public Optional average() {
        Number sum = 0L;
        long count = 0L;
        Iterator<Object> iter = this.iterator();
        while (iter.hasNext()) {
            ++count;
            sum = ELArithmetic.add(sum, iter.next());
        }
        if (count == 0L) {
            return new Optional();
        }
        return new Optional(ELArithmetic.divide(sum, count));
    }

    abstract class Iterator2
    extends Iterator1 {
        private Object current;
        private boolean yielded;

        Iterator2(Iterator upstream) {
            super(upstream);
        }

        @Override
        public Object next() {
            this.yielded = false;
            return this.current;
        }

        @Override
        public boolean hasNext() {
            while (!this.yielded && this.iter.hasNext()) {
                this.doItem(this.iter.next());
            }
            return this.yielded;
        }

        void yield(Object current) {
            this.current = current;
            this.yielded = true;
        }

        abstract void doItem(Object var1);
    }

    abstract class Iterator1
    extends Iterator0 {
        Iterator iter;

        Iterator1(Iterator iter) {
            this.iter = iter;
        }

        @Override
        public boolean hasNext() {
            return this.iter.hasNext();
        }
    }

    abstract class Iterator0
    implements Iterator<Object> {
        Iterator0() {
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

