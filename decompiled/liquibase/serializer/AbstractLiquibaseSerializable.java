/*
 * Decompiled with CFR 0.152.
 */
package liquibase.serializer;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.serializer.ReflectionSerializer;
import liquibase.util.ISODateFormat;
import liquibase.util.ObjectUtil;

public abstract class AbstractLiquibaseSerializable
implements LiquibaseSerializable {
    private Set<String> serializableFields;

    @Override
    public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        for (ParsedNode childNode : parsedNode.getChildren()) {
            if (!this.shouldAutoLoad(childNode)) continue;
            try {
                if (this.getSerializableFields().contains(childNode.getName())) {
                    Type[] dataTypeClassParameters;
                    Class dataTypeClass = this.getSerializableFieldDataTypeClass(childNode.getName());
                    if (Collection.class.isAssignableFrom(dataTypeClass) && (dataTypeClassParameters = this.getSerializableFieldDataTypeClassParameters(childNode.getName())).length == 1) {
                        Class collectionType = null;
                        if (dataTypeClassParameters[0] instanceof Class) {
                            collectionType = (Class)dataTypeClassParameters[0];
                        } else if (dataTypeClassParameters[0] instanceof ParameterizedType) {
                            collectionType = (Class)((ParameterizedType)dataTypeClassParameters[0]).getRawType();
                        }
                        if (collectionType != null && LiquibaseSerializable.class.isAssignableFrom(collectionType) && !collectionType.isInterface() && !Modifier.isAbstract(collectionType.getModifiers())) {
                            String elementName = ((LiquibaseSerializable)collectionType.getConstructor(new Class[0]).newInstance(new Object[0])).getSerializedObjectName();
                            List<Object> elementNodes = Collections.emptyList();
                            elementNodes = childNode.getName().equals(elementName) ? Collections.singletonList(childNode) : childNode.getChildren(null, elementName);
                            if (!elementNodes.isEmpty()) {
                                Collection collection = (Collection)this.getSerializableFieldValue(childNode.getName());
                                for (ParsedNode parsedNode2 : elementNodes) {
                                    LiquibaseSerializable childObject = (LiquibaseSerializable)collectionType.getConstructor(new Class[0]).newInstance(new Object[0]);
                                    childObject.load(parsedNode2, resourceAccessor);
                                    collection.add(childObject);
                                }
                            }
                        }
                    }
                    if (LiquibaseSerializable.class.isAssignableFrom(dataTypeClass)) {
                        if (dataTypeClass.isInterface() || Modifier.isAbstract(dataTypeClass.getModifiers())) continue;
                        LiquibaseSerializable childObject = (LiquibaseSerializable)dataTypeClass.getConstructor(new Class[0]).newInstance(new Object[0]);
                        childObject.load(childNode, resourceAccessor);
                        this.setSerializableFieldValue(childNode.getName(), childObject);
                        continue;
                    }
                    if (childNode.getValue() == null) continue;
                    ObjectUtil.setProperty((Object)this, childNode.getName(), this.convertEscaped(childNode.getValue().toString()));
                    continue;
                }
                for (String field2 : this.getSerializableFields()) {
                    Type[] dataTypeClassParameters;
                    Class dataTypeClass = this.getSerializableFieldDataTypeClass(field2);
                    if (!Collection.class.isAssignableFrom(dataTypeClass) || (dataTypeClassParameters = this.getSerializableFieldDataTypeClassParameters(field2)).length != 1) continue;
                    Class collectionType = null;
                    if (dataTypeClassParameters[0] instanceof Class) {
                        collectionType = (Class)dataTypeClassParameters[0];
                    } else if (dataTypeClassParameters[0] instanceof ParameterizedType) {
                        collectionType = (Class)((ParameterizedType)dataTypeClassParameters[0]).getRawType();
                    }
                    if (collectionType == null || !LiquibaseSerializable.class.isAssignableFrom(collectionType) || collectionType.isInterface() || Modifier.isAbstract(collectionType.getModifiers())) continue;
                    String elementName = ((LiquibaseSerializable)collectionType.getConstructor(new Class[0]).newInstance(new Object[0])).getSerializedObjectName();
                    List<Object> elementNodes = Collections.emptyList();
                    if (childNode.getName().equals(elementName)) {
                        elementNodes = Collections.singletonList(childNode);
                    } else if (childNode.getName().equals(field2)) {
                        elementNodes = childNode.getChildren(null, elementName);
                    }
                    if (elementNodes.isEmpty()) continue;
                    Collection collection = (Collection)this.getSerializableFieldValue(field2);
                    for (ParsedNode parsedNode3 : elementNodes) {
                        LiquibaseSerializable childObject = (LiquibaseSerializable)collectionType.getConstructor(new Class[0]).newInstance(new Object[0]);
                        childObject.load(parsedNode3, resourceAccessor);
                        collection.add(childObject);
                    }
                }
            }
            catch (Exception e) {
                throw new ParsedNodeException("Error setting property", e);
            }
        }
        if (parsedNode.getValue() != null) {
            for (String field3 : this.getSerializableFields()) {
                Object value;
                LiquibaseSerializable.SerializationType type = this.getSerializableFieldType(field3);
                if (type == LiquibaseSerializable.SerializationType.DIRECT_VALUE) {
                    value = parsedNode.getValue(String.class);
                    value = this.convertEscaped(value);
                    ObjectUtil.setProperty((Object)this, field3, value);
                    continue;
                }
                if (type != LiquibaseSerializable.SerializationType.NAMED_FIELD) continue;
                value = parsedNode.getChildValue((String)null, field3, Object.class);
                value = this.convertEscaped(value);
                ObjectUtil.setProperty((Object)this, field3, value);
            }
        }
    }

    protected Object convertEscaped(Object value) {
        if (value == null) {
            return null;
        }
        Matcher matcher = Pattern.compile("(.*)!\\{(.*)\\}").matcher((String)value);
        if (matcher.matches()) {
            String stringValue = matcher.group(1);
            try {
                Class<?> aClass = Class.forName(matcher.group(2));
                if (Date.class.isAssignableFrom(aClass)) {
                    Date date = new ISODateFormat().parse(stringValue);
                    value = aClass.getConstructor(Long.TYPE).newInstance(date.getTime());
                } else {
                    value = Enum.class.isAssignableFrom(aClass) ? Enum.valueOf(aClass, stringValue) : aClass.getConstructor(String.class).newInstance(stringValue);
                }
            }
            catch (Exception e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
        return value;
    }

    protected boolean shouldAutoLoad(ParsedNode node) {
        return true;
    }

    @Override
    public ParsedNode serialize() throws ParsedNodeException {
        ParsedNode node = new ParsedNode(null, this.getSerializedObjectName());
        for (String field2 : this.getSerializableFields()) {
            Object fieldValue = this.getSerializableFieldValue(field2);
            if ((fieldValue = this.serializeValue(fieldValue)) == null) continue;
            LiquibaseSerializable.SerializationType type = this.getSerializableFieldType(field2);
            if (type == LiquibaseSerializable.SerializationType.DIRECT_VALUE) {
                node.setValue(fieldValue);
                continue;
            }
            if (type == LiquibaseSerializable.SerializationType.NAMED_FIELD || type == LiquibaseSerializable.SerializationType.NESTED_OBJECT) {
                if (fieldValue instanceof ParsedNode) {
                    node.addChild((ParsedNode)fieldValue);
                    continue;
                }
                node.addChild(new ParsedNode(null, field2).setValue(fieldValue));
                continue;
            }
            throw new UnexpectedLiquibaseException("Unknown type: " + (Object)((Object)type));
        }
        return node;
    }

    @Override
    public Set<String> getSerializableFields() {
        return ReflectionSerializer.getInstance().getFields(this);
    }

    @Override
    public Object getSerializableFieldValue(String field2) {
        return ReflectionSerializer.getInstance().getValue(this, field2);
    }

    @Override
    public LiquibaseSerializable.SerializationType getSerializableFieldType(String field2) {
        return LiquibaseSerializable.SerializationType.NAMED_FIELD;
    }

    protected Class getSerializableFieldDataTypeClass(String field2) {
        return ReflectionSerializer.getInstance().getDataTypeClass(this, field2);
    }

    protected Type[] getSerializableFieldDataTypeClassParameters(String field2) {
        return ReflectionSerializer.getInstance().getDataTypeClassParameters(this, field2);
    }

    protected void setSerializableFieldValue(String field2, Object value) {
        ReflectionSerializer.getInstance().setValue(this, field2, value);
    }

    protected Object serializeValue(Object value) throws ParsedNodeException {
        if (value instanceof Collection) {
            ArrayList<Object> returnList = new ArrayList<Object>();
            for (Object obj : (Collection)value) {
                Object objValue = this.serializeValue(obj);
                if (objValue == null) continue;
                returnList.add(objValue);
            }
            if (((Collection)value).isEmpty()) {
                return null;
            }
            return returnList;
        }
        if (value instanceof LiquibaseSerializable) {
            return ((LiquibaseSerializable)value).serialize();
        }
        return value;
    }

    @Override
    public String getSerializableFieldNamespace(String field2) {
        return this.getSerializedObjectNamespace();
    }
}

