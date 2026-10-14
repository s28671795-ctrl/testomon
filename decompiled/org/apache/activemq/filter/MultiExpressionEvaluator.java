/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jms.JMSException;
import org.apache.activemq.filter.BinaryExpression;
import org.apache.activemq.filter.Expression;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.filter.UnaryExpression;

public class MultiExpressionEvaluator {
    Map<String, ExpressionListenerSet> rootExpressions = new HashMap<String, ExpressionListenerSet>();
    Map<Expression, CacheExpression> cachedExpressions = new HashMap<Expression, CacheExpression>();
    int view;

    public void addExpressionListner(Expression selector, ExpressionListener c) {
        ExpressionListenerSet data = this.rootExpressions.get(selector.toString());
        if (data == null) {
            data = new ExpressionListenerSet();
            data.expression = this.addToCache(selector);
            this.rootExpressions.put(selector.toString(), data);
        }
        data.listeners.add(c);
    }

    public boolean removeEventListner(String selector, ExpressionListener c) {
        String expKey = selector;
        ExpressionListenerSet d = this.rootExpressions.get(expKey);
        if (d == null) {
            return false;
        }
        if (!d.listeners.remove(c)) {
            return false;
        }
        if (d.listeners.size() == 0) {
            this.removeFromCache((CacheExpression)d.expression);
            this.rootExpressions.remove(expKey);
        }
        return true;
    }

    private CacheExpression addToCache(Expression expr) {
        CacheExpression n = this.cachedExpressions.get(expr);
        if (n == null) {
            n = new CacheExpression(expr);
            this.cachedExpressions.put(expr, n);
            if (expr instanceof UnaryExpression) {
                UnaryExpression un = (UnaryExpression)expr;
                un.setRight(this.addToCache(un.getRight()));
            } else if (expr instanceof BinaryExpression) {
                BinaryExpression bn = (BinaryExpression)expr;
                bn.setRight(this.addToCache(bn.getRight()));
                bn.setLeft(this.addToCache(bn.getLeft()));
            }
        }
        n.refCount = (short)(n.refCount + 1);
        return n;
    }

    private void removeFromCache(CacheExpression cn) {
        cn.refCount = (short)(cn.refCount - 1);
        Expression realExpr = cn.getRight();
        if (cn.refCount == 0) {
            this.cachedExpressions.remove(realExpr);
        }
        if (realExpr instanceof UnaryExpression) {
            UnaryExpression un = (UnaryExpression)realExpr;
            this.removeFromCache((CacheExpression)un.getRight());
        }
        if (realExpr instanceof BinaryExpression) {
            BinaryExpression bn = (BinaryExpression)realExpr;
            this.removeFromCache((CacheExpression)bn.getRight());
        }
    }

    public void evaluate(MessageEvaluationContext message) {
        Collection<ExpressionListenerSet> expressionListeners = this.rootExpressions.values();
        for (ExpressionListenerSet els : expressionListeners) {
            try {
                Object result = els.expression.evaluate(message);
                for (ExpressionListener l : els.listeners) {
                    l.evaluateResultEvent(els.expression, message, result);
                }
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    static interface ExpressionListener {
        public void evaluateResultEvent(Expression var1, MessageEvaluationContext var2, Object var3);
    }

    static class ExpressionListenerSet {
        Expression expression;
        List<ExpressionListener> listeners = new ArrayList<ExpressionListener>();

        ExpressionListenerSet() {
        }
    }

    public class CacheExpression
    extends UnaryExpression {
        short refCount;
        int cview;
        Object cachedValue;
        int cachedHashCode;

        public CacheExpression(Expression realExpression) {
            super(realExpression);
            this.cview = MultiExpressionEvaluator.this.view - 1;
            this.cachedHashCode = realExpression.hashCode();
        }

        @Override
        public Object evaluate(MessageEvaluationContext message) throws JMSException {
            if (MultiExpressionEvaluator.this.view == this.cview) {
                return this.cachedValue;
            }
            this.cachedValue = this.right.evaluate(message);
            this.cview = MultiExpressionEvaluator.this.view;
            return this.cachedValue;
        }

        @Override
        public int hashCode() {
            return this.cachedHashCode;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            return ((CacheExpression)o).right.equals(this.right);
        }

        @Override
        public String getExpressionSymbol() {
            return null;
        }

        @Override
        public String toString() {
            return this.right.toString();
        }
    }
}

