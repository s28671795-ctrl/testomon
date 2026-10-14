/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.helpers;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;
import javax.xml.namespace.QName;
import org.apache.cxf.annotations.SchemaValidation;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.JavaUtils;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.service.model.AbstractPropertiesHolder;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.OperationInfo;

public final class ServiceUtils {
    private ServiceUtils() {
    }

    public static boolean isSchemaValidationEnabled(SchemaValidation.SchemaValidationType type, Message message) {
        SchemaValidation.SchemaValidationType validationType = ServiceUtils.getSchemaValidationType(message);
        boolean isRequestor = MessageUtils.isRequestor(message);
        if (SchemaValidation.SchemaValidationType.REQUEST.equals((Object)validationType)) {
            validationType = isRequestor ? SchemaValidation.SchemaValidationType.OUT : SchemaValidation.SchemaValidationType.IN;
        } else if (SchemaValidation.SchemaValidationType.RESPONSE.equals((Object)validationType)) {
            validationType = isRequestor ? SchemaValidation.SchemaValidationType.IN : SchemaValidation.SchemaValidationType.OUT;
        }
        return validationType.equals((Object)type) || (SchemaValidation.SchemaValidationType.IN.equals((Object)type) || SchemaValidation.SchemaValidationType.OUT.equals((Object)type)) && SchemaValidation.SchemaValidationType.BOTH.equals((Object)validationType);
    }

    public static SchemaValidation.SchemaValidationType getSchemaValidationType(Message message) {
        Object obj;
        SchemaValidation.SchemaValidationType validationType = ServiceUtils.getOverrideSchemaValidationType(message);
        if (validationType == null) {
            validationType = ServiceUtils.getSchemaValidationTypeFromModel(message);
        }
        if (validationType == null && (obj = message.getContextualProperty("schema-validation-enabled")) != null) {
            validationType = ServiceUtils.getSchemaValidationType(obj);
        }
        if (validationType == null) {
            validationType = SchemaValidation.SchemaValidationType.NONE;
        }
        return validationType;
    }

    private static SchemaValidation.SchemaValidationType getOverrideSchemaValidationType(Message message) {
        Object obj = message.get("schema-validation-enabled");
        if (obj == null && message.getExchange() != null) {
            obj = message.getExchange().get("schema-validation-enabled");
        }
        if (obj != null) {
            return ServiceUtils.getSchemaValidationType(obj);
        }
        return null;
    }

    private static SchemaValidation.SchemaValidationType getSchemaValidationTypeFromModel(Message message) {
        Exchange exchange = message.getExchange();
        SchemaValidation.SchemaValidationType validationType = null;
        if (exchange != null) {
            EndpointInfo ep;
            Endpoint endpoint;
            OperationInfo opInfo;
            BindingOperationInfo boi = exchange.getBindingOperationInfo();
            if (boi != null && (opInfo = boi.getOperationInfo()) != null) {
                validationType = ServiceUtils.getSchemaValidationTypeFromModel(opInfo);
            }
            if (validationType == null && (endpoint = exchange.getEndpoint()) != null && (ep = endpoint.getEndpointInfo()) != null) {
                validationType = ServiceUtils.getSchemaValidationTypeFromModel(ep);
            }
        }
        return validationType;
    }

    private static SchemaValidation.SchemaValidationType getSchemaValidationTypeFromModel(AbstractPropertiesHolder properties) {
        Object obj = properties.getProperty("schema-validation-type");
        if (obj != null) {
            return ServiceUtils.getSchemaValidationType(obj);
        }
        return null;
    }

    public static SchemaValidation.SchemaValidationType getSchemaValidationType(Object obj) {
        if (obj instanceof SchemaValidation.SchemaValidationType) {
            return (SchemaValidation.SchemaValidationType)((Object)obj);
        }
        if (obj != null) {
            String value = obj.toString().toUpperCase();
            if ("TRUE".equals(value)) {
                return SchemaValidation.SchemaValidationType.BOTH;
            }
            if ("FALSE".equals(value)) {
                return SchemaValidation.SchemaValidationType.NONE;
            }
            if (value.length() > 0) {
                return SchemaValidation.SchemaValidationType.valueOf(value);
            }
        }
        return SchemaValidation.SchemaValidationType.NONE;
    }

    public static String makeServiceNameFromClassName(Class<?> clazz) {
        int inner;
        String name = clazz.getName();
        int last = name.lastIndexOf(46);
        if (last != -1) {
            name = name.substring(last + 1);
        }
        if ((inner = name.lastIndexOf(36)) != -1) {
            name = name.substring(inner + 1);
        }
        return name;
    }

    public static QName makeQualifiedNameFromClass(Class<?> clazz) {
        String namespace = ServiceUtils.makeNamespaceFromClassName(clazz.getName(), "http");
        String localPart = ServiceUtils.makeServiceNameFromClassName(clazz);
        return new QName(namespace, localPart);
    }

    public static String getMethodName(Method m4) {
        StringBuilder sb = new StringBuilder();
        sb.append(m4.getDeclaringClass().getName());
        sb.append('.');
        sb.append(m4.getName());
        sb.append('(');
        Class<?>[] params = m4.getParameterTypes();
        for (int i = 0; i < params.length; ++i) {
            Class<?> param2 = params[i];
            sb.append(param2.getName());
            if (i >= params.length - 1) continue;
            sb.append(", ");
        }
        sb.append(')');
        return sb.toString();
    }

    public static String makeNamespaceFromClassName(String className, String protocol) {
        int index = className.lastIndexOf(46);
        if (index == -1) {
            return protocol + "://DefaultNamespace";
        }
        String packageName = className.substring(0, index);
        StringTokenizer st = new StringTokenizer(packageName, ".");
        CharSequence[] words = new String[st.countTokens()];
        for (int i = words.length - 1; i >= 0; --i) {
            words[i] = st.nextToken();
        }
        return protocol + "://" + String.join((CharSequence)".", words) + "/";
    }

    public static String makePackageName(String namespace) {
        String hostname = null;
        String path = "";
        try {
            URL u = new URL(namespace);
            hostname = u.getHost();
            path = u.getPath();
        }
        catch (MalformedURLException e) {
            if (namespace.indexOf(58) > -1) {
                hostname = namespace.substring(namespace.indexOf(58) + 1);
                if (hostname.indexOf(47) > -1) {
                    hostname = hostname.substring(0, hostname.indexOf(47));
                }
            }
            hostname = namespace;
        }
        if (hostname == null) {
            return null;
        }
        hostname = hostname.replace('-', '_');
        if ((path = path.replace('-', '_')).length() > 0 && path.charAt(path.length() - 1) == '/') {
            path = path.substring(0, path.length() - 1);
        }
        StringTokenizer st = new StringTokenizer(hostname, ".:");
        String[] words = new String[st.countTokens()];
        for (int i = 0; i < words.length; ++i) {
            words[i] = st.nextToken();
        }
        StringBuilder sb = new StringBuilder(namespace.length());
        for (int i = words.length - 1; i >= 0; --i) {
            ServiceUtils.addWordToPackageBuffer(sb, words[i], i == words.length - 1);
        }
        StringTokenizer st2 = new StringTokenizer(path, "/");
        while (st2.hasMoreTokens()) {
            ServiceUtils.addWordToPackageBuffer(sb, st2.nextToken(), false);
        }
        return sb.toString();
    }

    private static void addWordToPackageBuffer(StringBuilder sb, String word, boolean firstWord) {
        if (JavaUtils.isJavaKeyword(word)) {
            word = JavaUtils.makeNonJavaKeyword(word);
        }
        if (!firstWord) {
            sb.append('.');
        }
        if (Character.isDigit(word.charAt(0))) {
            sb.append('_');
        }
        sb.append(word.replace('.', '_'));
    }
}

