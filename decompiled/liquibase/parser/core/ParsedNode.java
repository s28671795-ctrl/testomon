/*
 * Decompiled with CFR 0.152.
 */
package liquibase.parser.core;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.parser.core.ParsedNodeException;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.SequenceCurrentValueFunction;
import liquibase.statement.SequenceNextValueFunction;
import liquibase.util.ISODateFormat;
import liquibase.util.StringUtil;

public class ParsedNode {
    private final String namespace;
    private final String name;
    private final List<ParsedNode> children = new ArrayList<ParsedNode>();
    private Object value;

    public ParsedNode(String namespace, String name) {
        this.namespace = namespace;
        this.name = name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getName() {
        return this.name;
    }

    public List<ParsedNode> getChildren() {
        return Collections.unmodifiableList(this.children);
    }

    public List<ParsedNode> getChildren(String namespace, String nodename) {
        ArrayList<ParsedNode> returnList = new ArrayList<ParsedNode>();
        for (ParsedNode node : this.children) {
            if (!this.nodeMatches(node, namespace, nodename)) continue;
            returnList.add(node);
        }
        return Collections.unmodifiableList(returnList);
    }

    public Object getValue() {
        return this.value;
    }

    public ParsedNode setValue(Object value) throws ParsedNodeException {
        if (value instanceof ParsedNode) {
            this.addChild((ParsedNode)value);
        } else if (value instanceof Collection) {
            ArrayList newValue = new ArrayList();
            for (Object obj : (Collection)value) {
                if (obj instanceof Map) {
                    this.addChildren((Map)obj);
                    continue;
                }
                if (obj instanceof ParsedNode) {
                    this.addChild((ParsedNode)obj);
                    continue;
                }
                newValue.add(obj);
            }
            if (!newValue.isEmpty()) {
                this.value = newValue.size() == 1 ? newValue.get(0) : newValue;
            }
        } else if (value instanceof Map) {
            this.addChildren((Map)value);
        } else {
            this.value = value;
        }
        return this;
    }

    public <T> T getValue(Class<T> type) throws ParsedNodeException {
        return this.convertObject(this.value, type);
    }

    public ParsedNode addChild(String namespace, String nodeName, Object value) throws ParsedNodeException {
        this.addChild(this.createNode(namespace, nodeName).setValue(value));
        return this;
    }

    protected ParsedNode createNode(String namespace, String nodeName) {
        return new ParsedNode(namespace, nodeName);
    }

    public ParsedNode addChild(ParsedNode node) throws ParsedNodeException {
        this.children.add(node);
        return this;
    }

    public ParsedNode addChildren(Map<String, Object> child) throws ParsedNodeException {
        if (child == null || child.isEmpty()) {
            return this;
        }
        for (Map.Entry<String, Object> entry : child.entrySet()) {
            this.addChild(null, entry.getKey(), entry.getValue());
        }
        return this;
    }

    public ParsedNode getChild(String namespace, String name) throws ParsedNodeException {
        ParsedNode returnNode = null;
        for (ParsedNode node : this.children) {
            if (!this.nodeMatches(node, namespace, name)) continue;
            if (returnNode != null) {
                throw new ParsedNodeException("Multiple nodes match " + namespace + "/" + name);
            }
            returnNode = node;
        }
        return returnNode;
    }

    public ParsedNode removeChild(String namespace, String name) throws ParsedNodeException {
        this.children.removeIf(node -> this.nodeMatches((ParsedNode)node, namespace, name));
        return this;
    }

    protected boolean nodeMatches(ParsedNode node, String namespace, String nodename) {
        return this.namespaceMatches(node, namespace) && node.getName().equalsIgnoreCase(nodename);
    }

    protected boolean namespaceMatches(ParsedNode node, String namespace) {
        if (node.getNamespace() == null) {
            return namespace == null;
        }
        return node.getNamespace().equals(namespace);
    }

    public <T> T getChildValue(String namespace, String nodename, T defaultValue) throws ParsedNodeException {
        Object value = this.getChildValue(namespace, nodename, defaultValue.getClass());
        if (value == null) {
            return defaultValue;
        }
        return (T)value;
    }

    public <T> T getChildValue(String namespace, String nodename, Class<T> type) throws ParsedNodeException {
        Object rawValue = this.getChildValue(namespace, nodename);
        if (rawValue == null) {
            return null;
        }
        return this.convertObject(rawValue, type);
    }

    protected <T> T convertObject(Object rawValue, Class<T> type) throws ParsedNodeException {
        if (rawValue == null) {
            return null;
        }
        if (type.isAssignableFrom(rawValue.getClass())) {
            return (T)rawValue;
        }
        try {
            if (type.equals(String.class)) {
                return (T)rawValue.toString();
            }
            if (type.equals(Integer.class) || type.equals(Integer.TYPE)) {
                return (T)Integer.valueOf(rawValue.toString());
            }
            if (type.equals(Byte.class) || type.equals(Byte.TYPE)) {
                return (T)Byte.valueOf(rawValue.toString());
            }
            if ((type.equals(Character.class) || type.equals(Character.TYPE)) && rawValue instanceof String && ((String)rawValue).length() == 1) {
                return (T)Character.valueOf(((String)rawValue).charAt(0));
            }
            if (type.equals(Short.class) || type.equals(Short.TYPE)) {
                return (T)Short.valueOf(rawValue.toString());
            }
            if (type.equals(Float.class) || type.equals(Float.TYPE)) {
                return (T)Float.valueOf(rawValue.toString());
            }
            if (type.equals(Double.class) || type.equals(Double.TYPE)) {
                return (T)Double.valueOf(rawValue.toString());
            }
            if (type.equals(Long.class) || type.equals(Long.TYPE)) {
                return (T)Long.valueOf(rawValue.toString());
            }
            if (type.equals(BigInteger.class)) {
                return (T)new BigInteger(rawValue.toString());
            }
            if (type.equals(BigDecimal.class)) {
                return (T)new BigDecimal(rawValue.toString());
            }
            if (type.equals(Boolean.class) || type.equals(Boolean.TYPE)) {
                return (T)Boolean.valueOf(rawValue.toString());
            }
            if (type.isAssignableFrom(Date.class)) {
                return (T)new ISODateFormat().parse(rawValue.toString());
            }
            if (type.equals(SequenceNextValueFunction.class)) {
                return (T)new SequenceNextValueFunction(rawValue.toString());
            }
            if (type.equals(SequenceCurrentValueFunction.class)) {
                return (T)new SequenceCurrentValueFunction(rawValue.toString());
            }
            if (type.equals(DatabaseFunction.class)) {
                return (T)new DatabaseFunction(rawValue.toString());
            }
            if (type.isEnum()) {
                return Enum.valueOf(type, rawValue.toString());
            }
            throw new UnexpectedLiquibaseException("Cannot convert " + rawValue.getClass().getName() + " '" + rawValue + "' to " + type.getName());
        }
        catch (Exception e) {
            if (e instanceof UnexpectedLiquibaseException) {
                throw (UnexpectedLiquibaseException)e;
            }
            throw new ParsedNodeException(e);
        }
    }

    public Object getChildValue(String namespace, String nodename) throws ParsedNodeException {
        ParsedNode child = this.getChild(namespace, nodename);
        if (child == null) {
            return null;
        }
        return child.getValue();
    }

    public String toString() {
        String string = this.name;
        if (!this.children.isEmpty()) {
            string = string + "[" + StringUtil.join(this.children, ",", new StringUtil.ToStringFormatter(), true) + "]";
        }
        if (this.value != null) {
            String valueString = this.value instanceof Collection ? "(" + StringUtil.join((Collection)this.value, ",", new StringUtil.ToStringFormatter(), true) + ")" : this.value.toString();
            string = string + "=" + valueString;
        }
        return string;
    }

    public boolean equals(Object obj) {
        return obj instanceof ParsedNode && this.toString().equals(obj.toString());
    }

    public int hashCode() {
        return this.toString().hashCode();
    }
}

