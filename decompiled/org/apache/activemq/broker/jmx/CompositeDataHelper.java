/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;

public class CompositeDataHelper {
    public static Map getTabularMap(CompositeData cdata, String fieldName) {
        HashMap map = new HashMap();
        CompositeDataHelper.appendTabularMap(map, cdata, fieldName);
        return map;
    }

    public static void appendTabularMap(Map map, CompositeData cdata, String fieldName) {
        Object tabularObject = cdata.get(fieldName);
        if (tabularObject instanceof TabularData) {
            TabularData tabularData = (TabularData)tabularObject;
            Collection<?> values2 = tabularData.values();
            for (CompositeData compositeData : values2) {
                Object key = compositeData.get("key");
                Object value = compositeData.get("value");
                map.put(key, value);
            }
        }
    }

    public static Map getMessageUserProperties(CompositeData cdata) {
        HashMap map = new HashMap();
        CompositeDataHelper.appendTabularMap(map, cdata, "StringProperties");
        CompositeDataHelper.appendTabularMap(map, cdata, "BooleanProperties");
        CompositeDataHelper.appendTabularMap(map, cdata, "ByteProperties");
        CompositeDataHelper.appendTabularMap(map, cdata, "ShortProperties");
        CompositeDataHelper.appendTabularMap(map, cdata, "IntProperties");
        CompositeDataHelper.appendTabularMap(map, cdata, "LongProperties");
        CompositeDataHelper.appendTabularMap(map, cdata, "FloatProperties");
        CompositeDataHelper.appendTabularMap(map, cdata, "DoubleProperties");
        return map;
    }
}

