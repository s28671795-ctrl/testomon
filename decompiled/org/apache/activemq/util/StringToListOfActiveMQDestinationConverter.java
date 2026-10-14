/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.util.URISupport;

public class StringToListOfActiveMQDestinationConverter {
    public static List<ActiveMQDestination> convertToActiveMQDestination(Object value) {
        if (value == null) {
            return null;
        }
        String text = value.toString();
        if (text.startsWith("[") && text.endsWith("]")) {
            if ((text = text.substring(1, text.length() - 1).trim()).isEmpty()) {
                return null;
            }
            String[] array = text.split(",");
            ArrayList<ActiveMQDestination> list = new ArrayList<ActiveMQDestination>();
            for (String item : array) {
                list.add(ActiveMQDestination.createDestination(item.trim(), (byte)1));
            }
            return list;
        }
        return null;
    }

    public static String convertFromActiveMQDestination(Object value) {
        return StringToListOfActiveMQDestinationConverter.convertFromActiveMQDestination(value, false);
    }

    public static String convertFromActiveMQDestination(Object value, boolean includeOptions) {
        if (value == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder("[");
        if (value instanceof List) {
            List list = (List)value;
            for (int i = 0; i < list.size(); ++i) {
                Object e = list.get(i);
                if (!(e instanceof ActiveMQDestination)) continue;
                ActiveMQDestination destination = (ActiveMQDestination)e;
                if (includeOptions && destination.getOptions() != null) {
                    try {
                        sb.append(destination.toString() + URISupport.applyParameters(new URI(""), destination.getOptions()));
                    }
                    catch (URISyntaxException e1) {
                        sb.append(destination);
                    }
                } else {
                    sb.append(destination);
                }
                if (i >= list.size() - 1) continue;
                sb.append(", ");
            }
        }
        sb.append("]");
        if (sb.length() > 2) {
            return sb.toString();
        }
        return null;
    }
}

