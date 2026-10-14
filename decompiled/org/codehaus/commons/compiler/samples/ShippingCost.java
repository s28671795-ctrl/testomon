/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.samples;

import org.codehaus.commons.compiler.CompilerFactoryFactory;
import org.codehaus.commons.compiler.IExpressionEvaluator;

public final class ShippingCost {
    private ShippingCost() {
    }

    public static void main(String[] args2) throws Exception {
        if (args2.length != 1) {
            System.err.println("Usage: <total>");
            System.err.println("Computes the shipping costs from the double value \"total\".");
            System.err.println("If \"total\" is less than 100.0, then the result is 7.95, else the result is 0.");
            System.exit(1);
        }
        Object[] arguments = new Object[]{new Double(args2[0])};
        IExpressionEvaluator ee = CompilerFactoryFactory.getDefaultCompilerFactory(ShippingCost.class.getClassLoader()).newExpressionEvaluator();
        ee.setExpressionType(Double.TYPE);
        ee.setParameters(new String[]{"total"}, new Class[]{Double.TYPE});
        ee.cook("total >= 100.0 ? 0.0 : 7.95");
        Object res = ee.evaluate(arguments);
        System.out.println("Result = " + String.valueOf(res));
    }
}

