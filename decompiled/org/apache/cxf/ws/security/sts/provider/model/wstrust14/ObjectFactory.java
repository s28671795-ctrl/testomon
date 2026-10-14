/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model.wstrust14;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.security.sts.provider.model.wstrust14.ActAsType;
import org.apache.cxf.ws.security.sts.provider.model.wstrust14.ChoiceChallengeResponseType;
import org.apache.cxf.ws.security.sts.provider.model.wstrust14.ChoiceChallengeType;
import org.apache.cxf.ws.security.sts.provider.model.wstrust14.ChoiceSelectedType;
import org.apache.cxf.ws.security.sts.provider.model.wstrust14.ChoiceType;
import org.apache.cxf.ws.security.sts.provider.model.wstrust14.ContextDataType;
import org.apache.cxf.ws.security.sts.provider.model.wstrust14.ImageType;
import org.apache.cxf.ws.security.sts.provider.model.wstrust14.InteractiveChallengeResponseType;
import org.apache.cxf.ws.security.sts.provider.model.wstrust14.InteractiveChallengeType;
import org.apache.cxf.ws.security.sts.provider.model.wstrust14.TextChallengeResponseType;
import org.apache.cxf.ws.security.sts.provider.model.wstrust14.TextChallengeType;
import org.apache.cxf.ws.security.sts.provider.model.wstrust14.TitleType;

@XmlRegistry
public class ObjectFactory {
    private static final QName _InteractiveChallenge_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200802", "InteractiveChallenge");
    private static final QName _Title_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200802", "Title");
    private static final QName _TextChallenge_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200802", "TextChallenge");
    private static final QName _ChoiceChallenge_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200802", "ChoiceChallenge");
    private static final QName _ContextData_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200802", "ContextData");
    private static final QName _InteractiveChallengeResponse_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200802", "InteractiveChallengeResponse");
    private static final QName _TextChallengeResponse_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200802", "TextChallengeResponse");
    private static final QName _ChoiceChallengeResponse_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200802", "ChoiceChallengeResponse");
    private static final QName _ActAs_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200802", "ActAs");

    public InteractiveChallengeType createInteractiveChallengeType() {
        return new InteractiveChallengeType();
    }

    public TitleType createTitleType() {
        return new TitleType();
    }

    public TextChallengeType createTextChallengeType() {
        return new TextChallengeType();
    }

    public ChoiceChallengeType createChoiceChallengeType() {
        return new ChoiceChallengeType();
    }

    public ContextDataType createContextDataType() {
        return new ContextDataType();
    }

    public InteractiveChallengeResponseType createInteractiveChallengeResponseType() {
        return new InteractiveChallengeResponseType();
    }

    public TextChallengeResponseType createTextChallengeResponseType() {
        return new TextChallengeResponseType();
    }

    public ChoiceChallengeResponseType createChoiceChallengeResponseType() {
        return new ChoiceChallengeResponseType();
    }

    public ActAsType createActAsType() {
        return new ActAsType();
    }

    public ImageType createImageType() {
        return new ImageType();
    }

    public ChoiceType createChoiceType() {
        return new ChoiceType();
    }

    public ChoiceSelectedType createChoiceSelectedType() {
        return new ChoiceSelectedType();
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200802", name="InteractiveChallenge")
    public JAXBElement<InteractiveChallengeType> createInteractiveChallenge(InteractiveChallengeType value) {
        return new JAXBElement<InteractiveChallengeType>(_InteractiveChallenge_QNAME, InteractiveChallengeType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200802", name="Title")
    public JAXBElement<TitleType> createTitle(TitleType value) {
        return new JAXBElement<TitleType>(_Title_QNAME, TitleType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200802", name="TextChallenge")
    public JAXBElement<TextChallengeType> createTextChallenge(TextChallengeType value) {
        return new JAXBElement<TextChallengeType>(_TextChallenge_QNAME, TextChallengeType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200802", name="ChoiceChallenge")
    public JAXBElement<ChoiceChallengeType> createChoiceChallenge(ChoiceChallengeType value) {
        return new JAXBElement<ChoiceChallengeType>(_ChoiceChallenge_QNAME, ChoiceChallengeType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200802", name="ContextData")
    public JAXBElement<ContextDataType> createContextData(ContextDataType value) {
        return new JAXBElement<ContextDataType>(_ContextData_QNAME, ContextDataType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200802", name="InteractiveChallengeResponse")
    public JAXBElement<InteractiveChallengeResponseType> createInteractiveChallengeResponse(InteractiveChallengeResponseType value) {
        return new JAXBElement<InteractiveChallengeResponseType>(_InteractiveChallengeResponse_QNAME, InteractiveChallengeResponseType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200802", name="TextChallengeResponse")
    public JAXBElement<TextChallengeResponseType> createTextChallengeResponse(TextChallengeResponseType value) {
        return new JAXBElement<TextChallengeResponseType>(_TextChallengeResponse_QNAME, TextChallengeResponseType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200802", name="ChoiceChallengeResponse")
    public JAXBElement<ChoiceChallengeResponseType> createChoiceChallengeResponse(ChoiceChallengeResponseType value) {
        return new JAXBElement<ChoiceChallengeResponseType>(_ChoiceChallengeResponse_QNAME, ChoiceChallengeResponseType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200802", name="ActAs")
    public JAXBElement<ActAsType> createActAs(ActAsType value) {
        return new JAXBElement<ActAsType>(_ActAs_QNAME, ActAsType.class, null, value);
    }
}

