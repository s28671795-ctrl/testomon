/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.rp_2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.oasis_open.docs.wsrf.rp_2.DeleteResourceProperties;
import org.oasis_open.docs.wsrf.rp_2.DeleteResourcePropertiesRequestFailedFaultType;
import org.oasis_open.docs.wsrf.rp_2.DeleteResourcePropertiesResponse;
import org.oasis_open.docs.wsrf.rp_2.DeleteType;
import org.oasis_open.docs.wsrf.rp_2.GetMultipleResourceProperties;
import org.oasis_open.docs.wsrf.rp_2.GetMultipleResourcePropertiesResponse;
import org.oasis_open.docs.wsrf.rp_2.GetResourcePropertyDocument;
import org.oasis_open.docs.wsrf.rp_2.GetResourcePropertyDocumentResponse;
import org.oasis_open.docs.wsrf.rp_2.GetResourcePropertyResponse;
import org.oasis_open.docs.wsrf.rp_2.InsertResourceProperties;
import org.oasis_open.docs.wsrf.rp_2.InsertResourcePropertiesRequestFailedFaultType;
import org.oasis_open.docs.wsrf.rp_2.InsertResourcePropertiesResponse;
import org.oasis_open.docs.wsrf.rp_2.InsertType;
import org.oasis_open.docs.wsrf.rp_2.InvalidModificationFaultType;
import org.oasis_open.docs.wsrf.rp_2.InvalidQueryExpressionFaultType;
import org.oasis_open.docs.wsrf.rp_2.InvalidResourcePropertyQNameFaultType;
import org.oasis_open.docs.wsrf.rp_2.PutResourcePropertyDocument;
import org.oasis_open.docs.wsrf.rp_2.PutResourcePropertyDocumentResponse;
import org.oasis_open.docs.wsrf.rp_2.QueryEvaluationErrorFaultType;
import org.oasis_open.docs.wsrf.rp_2.QueryExpressionRPDocument;
import org.oasis_open.docs.wsrf.rp_2.QueryExpressionType;
import org.oasis_open.docs.wsrf.rp_2.QueryResourceProperties;
import org.oasis_open.docs.wsrf.rp_2.QueryResourcePropertiesResponse;
import org.oasis_open.docs.wsrf.rp_2.ResourcePropertyChangeFailureType;
import org.oasis_open.docs.wsrf.rp_2.ResourcePropertyValueChangeNotificationType;
import org.oasis_open.docs.wsrf.rp_2.SetResourceProperties;
import org.oasis_open.docs.wsrf.rp_2.SetResourcePropertiesResponse;
import org.oasis_open.docs.wsrf.rp_2.SetResourcePropertyRequestFailedFaultType;
import org.oasis_open.docs.wsrf.rp_2.UnableToModifyResourcePropertyFaultType;
import org.oasis_open.docs.wsrf.rp_2.UnableToPutResourcePropertyDocumentFaultType;
import org.oasis_open.docs.wsrf.rp_2.UnknownQueryExpressionDialectFaultType;
import org.oasis_open.docs.wsrf.rp_2.UpdateResourceProperties;
import org.oasis_open.docs.wsrf.rp_2.UpdateResourcePropertiesRequestFailedFaultType;
import org.oasis_open.docs.wsrf.rp_2.UpdateResourcePropertiesResponse;
import org.oasis_open.docs.wsrf.rp_2.UpdateType;

@XmlRegistry
public class ObjectFactory {
    private static final QName _QueryExpressionDialect_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "QueryExpressionDialect");
    private static final QName _ResourcePropertyValueChangeNotification_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "ResourcePropertyValueChangeNotification");
    private static final QName _QueryExpression_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "QueryExpression");
    private static final QName _GetResourceProperty_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "GetResourceProperty");
    private static final QName _InvalidResourcePropertyQNameFault_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "InvalidResourcePropertyQNameFault");
    private static final QName _UnableToPutResourcePropertyDocumentFault_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "UnableToPutResourcePropertyDocumentFault");
    private static final QName _Insert_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "Insert");
    private static final QName _Update_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "Update");
    private static final QName _Delete_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "Delete");
    private static final QName _InvalidModificationFault_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "InvalidModificationFault");
    private static final QName _UnableToModifyResourcePropertyFault_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "UnableToModifyResourcePropertyFault");
    private static final QName _SetResourcePropertyRequestFailedFault_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "SetResourcePropertyRequestFailedFault");
    private static final QName _InsertResourcePropertiesRequestFailedFault_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "InsertResourcePropertiesRequestFailedFault");
    private static final QName _UpdateResourcePropertiesRequestFailedFault_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "UpdateResourcePropertiesRequestFailedFault");
    private static final QName _DeleteResourcePropertiesRequestFailedFault_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "DeleteResourcePropertiesRequestFailedFault");
    private static final QName _UnknownQueryExpressionDialectFault_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "UnknownQueryExpressionDialectFault");
    private static final QName _InvalidQueryExpressionFault_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "InvalidQueryExpressionFault");
    private static final QName _QueryEvaluationErrorFault_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "QueryEvaluationErrorFault");
    private static final QName _ResourcePropertyValueChangeNotificationTypeOldValues_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "OldValues");

    public ResourcePropertyChangeFailureType createResourcePropertyChangeFailureType() {
        return new ResourcePropertyChangeFailureType();
    }

    public ResourcePropertyValueChangeNotificationType createResourcePropertyValueChangeNotificationType() {
        return new ResourcePropertyValueChangeNotificationType();
    }

    public QueryExpressionRPDocument createQueryExpressionRPDocument() {
        return new QueryExpressionRPDocument();
    }

    public QueryExpressionType createQueryExpressionType() {
        return new QueryExpressionType();
    }

    public GetResourcePropertyDocument createGetResourcePropertyDocument() {
        return new GetResourcePropertyDocument();
    }

    public GetResourcePropertyDocumentResponse createGetResourcePropertyDocumentResponse() {
        return new GetResourcePropertyDocumentResponse();
    }

    public GetResourcePropertyResponse createGetResourcePropertyResponse() {
        return new GetResourcePropertyResponse();
    }

    public InvalidResourcePropertyQNameFaultType createInvalidResourcePropertyQNameFaultType() {
        return new InvalidResourcePropertyQNameFaultType();
    }

    public GetMultipleResourceProperties createGetMultipleResourceProperties() {
        return new GetMultipleResourceProperties();
    }

    public GetMultipleResourcePropertiesResponse createGetMultipleResourcePropertiesResponse() {
        return new GetMultipleResourcePropertiesResponse();
    }

    public PutResourcePropertyDocument createPutResourcePropertyDocument() {
        return new PutResourcePropertyDocument();
    }

    public PutResourcePropertyDocumentResponse createPutResourcePropertyDocumentResponse() {
        return new PutResourcePropertyDocumentResponse();
    }

    public UnableToPutResourcePropertyDocumentFaultType createUnableToPutResourcePropertyDocumentFaultType() {
        return new UnableToPutResourcePropertyDocumentFaultType();
    }

    public InsertType createInsertType() {
        return new InsertType();
    }

    public UpdateType createUpdateType() {
        return new UpdateType();
    }

    public DeleteType createDeleteType() {
        return new DeleteType();
    }

    public SetResourceProperties createSetResourceProperties() {
        return new SetResourceProperties();
    }

    public SetResourcePropertiesResponse createSetResourcePropertiesResponse() {
        return new SetResourcePropertiesResponse();
    }

    public InvalidModificationFaultType createInvalidModificationFaultType() {
        return new InvalidModificationFaultType();
    }

    public UnableToModifyResourcePropertyFaultType createUnableToModifyResourcePropertyFaultType() {
        return new UnableToModifyResourcePropertyFaultType();
    }

    public SetResourcePropertyRequestFailedFaultType createSetResourcePropertyRequestFailedFaultType() {
        return new SetResourcePropertyRequestFailedFaultType();
    }

    public InsertResourcePropertiesRequestFailedFaultType createInsertResourcePropertiesRequestFailedFaultType() {
        return new InsertResourcePropertiesRequestFailedFaultType();
    }

    public UpdateResourcePropertiesRequestFailedFaultType createUpdateResourcePropertiesRequestFailedFaultType() {
        return new UpdateResourcePropertiesRequestFailedFaultType();
    }

    public DeleteResourcePropertiesRequestFailedFaultType createDeleteResourcePropertiesRequestFailedFaultType() {
        return new DeleteResourcePropertiesRequestFailedFaultType();
    }

    public InsertResourceProperties createInsertResourceProperties() {
        return new InsertResourceProperties();
    }

    public InsertResourcePropertiesResponse createInsertResourcePropertiesResponse() {
        return new InsertResourcePropertiesResponse();
    }

    public UpdateResourceProperties createUpdateResourceProperties() {
        return new UpdateResourceProperties();
    }

    public UpdateResourcePropertiesResponse createUpdateResourcePropertiesResponse() {
        return new UpdateResourcePropertiesResponse();
    }

    public DeleteResourceProperties createDeleteResourceProperties() {
        return new DeleteResourceProperties();
    }

    public DeleteResourcePropertiesResponse createDeleteResourcePropertiesResponse() {
        return new DeleteResourcePropertiesResponse();
    }

    public QueryResourceProperties createQueryResourceProperties() {
        return new QueryResourceProperties();
    }

    public QueryResourcePropertiesResponse createQueryResourcePropertiesResponse() {
        return new QueryResourcePropertiesResponse();
    }

    public UnknownQueryExpressionDialectFaultType createUnknownQueryExpressionDialectFaultType() {
        return new UnknownQueryExpressionDialectFaultType();
    }

    public InvalidQueryExpressionFaultType createInvalidQueryExpressionFaultType() {
        return new InvalidQueryExpressionFaultType();
    }

    public QueryEvaluationErrorFaultType createQueryEvaluationErrorFaultType() {
        return new QueryEvaluationErrorFaultType();
    }

    public ResourcePropertyChangeFailureType.CurrentValue createResourcePropertyChangeFailureTypeCurrentValue() {
        return new ResourcePropertyChangeFailureType.CurrentValue();
    }

    public ResourcePropertyChangeFailureType.RequestedValue createResourcePropertyChangeFailureTypeRequestedValue() {
        return new ResourcePropertyChangeFailureType.RequestedValue();
    }

    public ResourcePropertyValueChangeNotificationType.OldValues createResourcePropertyValueChangeNotificationTypeOldValues() {
        return new ResourcePropertyValueChangeNotificationType.OldValues();
    }

    public ResourcePropertyValueChangeNotificationType.NewValues createResourcePropertyValueChangeNotificationTypeNewValues() {
        return new ResourcePropertyValueChangeNotificationType.NewValues();
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/rp-2", name="QueryExpressionDialect")
    public JAXBElement<String> createQueryExpressionDialect(String value) {
        return new JAXBElement<String>(_QueryExpressionDialect_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/rp-2", name="ResourcePropertyValueChangeNotification")
    public JAXBElement<ResourcePropertyValueChangeNotificationType> createResourcePropertyValueChangeNotification(ResourcePropertyValueChangeNotificationType value) {
        return new JAXBElement<ResourcePropertyValueChangeNotificationType>(_ResourcePropertyValueChangeNotification_QNAME, ResourcePropertyValueChangeNotificationType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/rp-2", name="QueryExpression")
    public JAXBElement<QueryExpressionType> createQueryExpression(QueryExpressionType value) {
        return new JAXBElement<QueryExpressionType>(_QueryExpression_QNAME, QueryExpressionType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/rp-2", name="GetResourceProperty")
    public JAXBElement<QName> createGetResourceProperty(QName value) {
        return new JAXBElement<QName>(_GetResourceProperty_QNAME, QName.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/rp-2", name="InvalidResourcePropertyQNameFault")
    public JAXBElement<InvalidResourcePropertyQNameFaultType> createInvalidResourcePropertyQNameFault(InvalidResourcePropertyQNameFaultType value) {
        return new JAXBElement<InvalidResourcePropertyQNameFaultType>(_InvalidResourcePropertyQNameFault_QNAME, InvalidResourcePropertyQNameFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/rp-2", name="UnableToPutResourcePropertyDocumentFault")
    public JAXBElement<UnableToPutResourcePropertyDocumentFaultType> createUnableToPutResourcePropertyDocumentFault(UnableToPutResourcePropertyDocumentFaultType value) {
        return new JAXBElement<UnableToPutResourcePropertyDocumentFaultType>(_UnableToPutResourcePropertyDocumentFault_QNAME, UnableToPutResourcePropertyDocumentFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/rp-2", name="Insert")
    public JAXBElement<InsertType> createInsert(InsertType value) {
        return new JAXBElement<InsertType>(_Insert_QNAME, InsertType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/rp-2", name="Update")
    public JAXBElement<UpdateType> createUpdate(UpdateType value) {
        return new JAXBElement<UpdateType>(_Update_QNAME, UpdateType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/rp-2", name="Delete")
    public JAXBElement<DeleteType> createDelete(DeleteType value) {
        return new JAXBElement<DeleteType>(_Delete_QNAME, DeleteType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/rp-2", name="InvalidModificationFault")
    public JAXBElement<InvalidModificationFaultType> createInvalidModificationFault(InvalidModificationFaultType value) {
        return new JAXBElement<InvalidModificationFaultType>(_InvalidModificationFault_QNAME, InvalidModificationFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/rp-2", name="UnableToModifyResourcePropertyFault")
    public JAXBElement<UnableToModifyResourcePropertyFaultType> createUnableToModifyResourcePropertyFault(UnableToModifyResourcePropertyFaultType value) {
        return new JAXBElement<UnableToModifyResourcePropertyFaultType>(_UnableToModifyResourcePropertyFault_QNAME, UnableToModifyResourcePropertyFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/rp-2", name="SetResourcePropertyRequestFailedFault")
    public JAXBElement<SetResourcePropertyRequestFailedFaultType> createSetResourcePropertyRequestFailedFault(SetResourcePropertyRequestFailedFaultType value) {
        return new JAXBElement<SetResourcePropertyRequestFailedFaultType>(_SetResourcePropertyRequestFailedFault_QNAME, SetResourcePropertyRequestFailedFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/rp-2", name="InsertResourcePropertiesRequestFailedFault")
    public JAXBElement<InsertResourcePropertiesRequestFailedFaultType> createInsertResourcePropertiesRequestFailedFault(InsertResourcePropertiesRequestFailedFaultType value) {
        return new JAXBElement<InsertResourcePropertiesRequestFailedFaultType>(_InsertResourcePropertiesRequestFailedFault_QNAME, InsertResourcePropertiesRequestFailedFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/rp-2", name="UpdateResourcePropertiesRequestFailedFault")
    public JAXBElement<UpdateResourcePropertiesRequestFailedFaultType> createUpdateResourcePropertiesRequestFailedFault(UpdateResourcePropertiesRequestFailedFaultType value) {
        return new JAXBElement<UpdateResourcePropertiesRequestFailedFaultType>(_UpdateResourcePropertiesRequestFailedFault_QNAME, UpdateResourcePropertiesRequestFailedFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/rp-2", name="DeleteResourcePropertiesRequestFailedFault")
    public JAXBElement<DeleteResourcePropertiesRequestFailedFaultType> createDeleteResourcePropertiesRequestFailedFault(DeleteResourcePropertiesRequestFailedFaultType value) {
        return new JAXBElement<DeleteResourcePropertiesRequestFailedFaultType>(_DeleteResourcePropertiesRequestFailedFault_QNAME, DeleteResourcePropertiesRequestFailedFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/rp-2", name="UnknownQueryExpressionDialectFault")
    public JAXBElement<UnknownQueryExpressionDialectFaultType> createUnknownQueryExpressionDialectFault(UnknownQueryExpressionDialectFaultType value) {
        return new JAXBElement<UnknownQueryExpressionDialectFaultType>(_UnknownQueryExpressionDialectFault_QNAME, UnknownQueryExpressionDialectFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/rp-2", name="InvalidQueryExpressionFault")
    public JAXBElement<InvalidQueryExpressionFaultType> createInvalidQueryExpressionFault(InvalidQueryExpressionFaultType value) {
        return new JAXBElement<InvalidQueryExpressionFaultType>(_InvalidQueryExpressionFault_QNAME, InvalidQueryExpressionFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/rp-2", name="QueryEvaluationErrorFault")
    public JAXBElement<QueryEvaluationErrorFaultType> createQueryEvaluationErrorFault(QueryEvaluationErrorFaultType value) {
        return new JAXBElement<QueryEvaluationErrorFaultType>(_QueryEvaluationErrorFault_QNAME, QueryEvaluationErrorFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/rp-2", name="OldValues", scope=ResourcePropertyValueChangeNotificationType.class)
    public JAXBElement<ResourcePropertyValueChangeNotificationType.OldValues> createResourcePropertyValueChangeNotificationTypeOldValues(ResourcePropertyValueChangeNotificationType.OldValues value) {
        return new JAXBElement<ResourcePropertyValueChangeNotificationType.OldValues>(_ResourcePropertyValueChangeNotificationTypeOldValues_QNAME, ResourcePropertyValueChangeNotificationType.OldValues.class, ResourcePropertyValueChangeNotificationType.class, value);
    }
}

