/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver20.ptz.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.onvif.ver20.ptz.wsdl.AbsoluteMove;
import org.onvif.ver20.ptz.wsdl.AbsoluteMoveResponse;
import org.onvif.ver20.ptz.wsdl.Capabilities;
import org.onvif.ver20.ptz.wsdl.ContinuousMove;
import org.onvif.ver20.ptz.wsdl.ContinuousMoveResponse;
import org.onvif.ver20.ptz.wsdl.CreatePresetTour;
import org.onvif.ver20.ptz.wsdl.CreatePresetTourResponse;
import org.onvif.ver20.ptz.wsdl.GetCompatibleConfigurations;
import org.onvif.ver20.ptz.wsdl.GetCompatibleConfigurationsResponse;
import org.onvif.ver20.ptz.wsdl.GetConfiguration;
import org.onvif.ver20.ptz.wsdl.GetConfigurationOptions;
import org.onvif.ver20.ptz.wsdl.GetConfigurationOptionsResponse;
import org.onvif.ver20.ptz.wsdl.GetConfigurationResponse;
import org.onvif.ver20.ptz.wsdl.GetConfigurations;
import org.onvif.ver20.ptz.wsdl.GetConfigurationsResponse;
import org.onvif.ver20.ptz.wsdl.GetNode;
import org.onvif.ver20.ptz.wsdl.GetNodeResponse;
import org.onvif.ver20.ptz.wsdl.GetNodes;
import org.onvif.ver20.ptz.wsdl.GetNodesResponse;
import org.onvif.ver20.ptz.wsdl.GetPresetTour;
import org.onvif.ver20.ptz.wsdl.GetPresetTourOptions;
import org.onvif.ver20.ptz.wsdl.GetPresetTourOptionsResponse;
import org.onvif.ver20.ptz.wsdl.GetPresetTourResponse;
import org.onvif.ver20.ptz.wsdl.GetPresetTours;
import org.onvif.ver20.ptz.wsdl.GetPresetToursResponse;
import org.onvif.ver20.ptz.wsdl.GetPresets;
import org.onvif.ver20.ptz.wsdl.GetPresetsResponse;
import org.onvif.ver20.ptz.wsdl.GetServiceCapabilities;
import org.onvif.ver20.ptz.wsdl.GetServiceCapabilitiesResponse;
import org.onvif.ver20.ptz.wsdl.GetStatus;
import org.onvif.ver20.ptz.wsdl.GetStatusResponse;
import org.onvif.ver20.ptz.wsdl.GotoHomePosition;
import org.onvif.ver20.ptz.wsdl.GotoHomePositionResponse;
import org.onvif.ver20.ptz.wsdl.GotoPreset;
import org.onvif.ver20.ptz.wsdl.GotoPresetResponse;
import org.onvif.ver20.ptz.wsdl.ModifyPresetTour;
import org.onvif.ver20.ptz.wsdl.ModifyPresetTourResponse;
import org.onvif.ver20.ptz.wsdl.OperatePresetTour;
import org.onvif.ver20.ptz.wsdl.OperatePresetTourResponse;
import org.onvif.ver20.ptz.wsdl.RelativeMove;
import org.onvif.ver20.ptz.wsdl.RelativeMoveResponse;
import org.onvif.ver20.ptz.wsdl.RemovePreset;
import org.onvif.ver20.ptz.wsdl.RemovePresetResponse;
import org.onvif.ver20.ptz.wsdl.RemovePresetTour;
import org.onvif.ver20.ptz.wsdl.RemovePresetTourResponse;
import org.onvif.ver20.ptz.wsdl.SendAuxiliaryCommand;
import org.onvif.ver20.ptz.wsdl.SendAuxiliaryCommandResponse;
import org.onvif.ver20.ptz.wsdl.SetConfiguration;
import org.onvif.ver20.ptz.wsdl.SetConfigurationResponse;
import org.onvif.ver20.ptz.wsdl.SetHomePosition;
import org.onvif.ver20.ptz.wsdl.SetHomePositionResponse;
import org.onvif.ver20.ptz.wsdl.SetPreset;
import org.onvif.ver20.ptz.wsdl.SetPresetResponse;
import org.onvif.ver20.ptz.wsdl.Stop;
import org.onvif.ver20.ptz.wsdl.StopResponse;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Capabilities_QNAME = new QName("http://www.onvif.org/ver20/ptz/wsdl", "Capabilities");

    public GetServiceCapabilities createGetServiceCapabilities() {
        return new GetServiceCapabilities();
    }

    public GetServiceCapabilitiesResponse createGetServiceCapabilitiesResponse() {
        return new GetServiceCapabilitiesResponse();
    }

    public Capabilities createCapabilities() {
        return new Capabilities();
    }

    public GetNodes createGetNodes() {
        return new GetNodes();
    }

    public GetNodesResponse createGetNodesResponse() {
        return new GetNodesResponse();
    }

    public GetNode createGetNode() {
        return new GetNode();
    }

    public GetNodeResponse createGetNodeResponse() {
        return new GetNodeResponse();
    }

    public GetConfigurations createGetConfigurations() {
        return new GetConfigurations();
    }

    public GetConfigurationsResponse createGetConfigurationsResponse() {
        return new GetConfigurationsResponse();
    }

    public GetConfiguration createGetConfiguration() {
        return new GetConfiguration();
    }

    public GetConfigurationResponse createGetConfigurationResponse() {
        return new GetConfigurationResponse();
    }

    public SetConfiguration createSetConfiguration() {
        return new SetConfiguration();
    }

    public SetConfigurationResponse createSetConfigurationResponse() {
        return new SetConfigurationResponse();
    }

    public GetConfigurationOptions createGetConfigurationOptions() {
        return new GetConfigurationOptions();
    }

    public GetConfigurationOptionsResponse createGetConfigurationOptionsResponse() {
        return new GetConfigurationOptionsResponse();
    }

    public SendAuxiliaryCommand createSendAuxiliaryCommand() {
        return new SendAuxiliaryCommand();
    }

    public SendAuxiliaryCommandResponse createSendAuxiliaryCommandResponse() {
        return new SendAuxiliaryCommandResponse();
    }

    public GetPresets createGetPresets() {
        return new GetPresets();
    }

    public GetPresetsResponse createGetPresetsResponse() {
        return new GetPresetsResponse();
    }

    public SetPreset createSetPreset() {
        return new SetPreset();
    }

    public SetPresetResponse createSetPresetResponse() {
        return new SetPresetResponse();
    }

    public RemovePreset createRemovePreset() {
        return new RemovePreset();
    }

    public RemovePresetResponse createRemovePresetResponse() {
        return new RemovePresetResponse();
    }

    public GotoPreset createGotoPreset() {
        return new GotoPreset();
    }

    public GotoPresetResponse createGotoPresetResponse() {
        return new GotoPresetResponse();
    }

    public GetStatus createGetStatus() {
        return new GetStatus();
    }

    public GetStatusResponse createGetStatusResponse() {
        return new GetStatusResponse();
    }

    public GotoHomePosition createGotoHomePosition() {
        return new GotoHomePosition();
    }

    public GotoHomePositionResponse createGotoHomePositionResponse() {
        return new GotoHomePositionResponse();
    }

    public SetHomePosition createSetHomePosition() {
        return new SetHomePosition();
    }

    public SetHomePositionResponse createSetHomePositionResponse() {
        return new SetHomePositionResponse();
    }

    public ContinuousMove createContinuousMove() {
        return new ContinuousMove();
    }

    public ContinuousMoveResponse createContinuousMoveResponse() {
        return new ContinuousMoveResponse();
    }

    public RelativeMove createRelativeMove() {
        return new RelativeMove();
    }

    public RelativeMoveResponse createRelativeMoveResponse() {
        return new RelativeMoveResponse();
    }

    public AbsoluteMove createAbsoluteMove() {
        return new AbsoluteMove();
    }

    public AbsoluteMoveResponse createAbsoluteMoveResponse() {
        return new AbsoluteMoveResponse();
    }

    public Stop createStop() {
        return new Stop();
    }

    public StopResponse createStopResponse() {
        return new StopResponse();
    }

    public GetPresetTours createGetPresetTours() {
        return new GetPresetTours();
    }

    public GetPresetToursResponse createGetPresetToursResponse() {
        return new GetPresetToursResponse();
    }

    public GetPresetTour createGetPresetTour() {
        return new GetPresetTour();
    }

    public GetPresetTourResponse createGetPresetTourResponse() {
        return new GetPresetTourResponse();
    }

    public GetPresetTourOptions createGetPresetTourOptions() {
        return new GetPresetTourOptions();
    }

    public GetPresetTourOptionsResponse createGetPresetTourOptionsResponse() {
        return new GetPresetTourOptionsResponse();
    }

    public CreatePresetTour createCreatePresetTour() {
        return new CreatePresetTour();
    }

    public CreatePresetTourResponse createCreatePresetTourResponse() {
        return new CreatePresetTourResponse();
    }

    public ModifyPresetTour createModifyPresetTour() {
        return new ModifyPresetTour();
    }

    public ModifyPresetTourResponse createModifyPresetTourResponse() {
        return new ModifyPresetTourResponse();
    }

    public OperatePresetTour createOperatePresetTour() {
        return new OperatePresetTour();
    }

    public OperatePresetTourResponse createOperatePresetTourResponse() {
        return new OperatePresetTourResponse();
    }

    public RemovePresetTour createRemovePresetTour() {
        return new RemovePresetTour();
    }

    public RemovePresetTourResponse createRemovePresetTourResponse() {
        return new RemovePresetTourResponse();
    }

    public GetCompatibleConfigurations createGetCompatibleConfigurations() {
        return new GetCompatibleConfigurations();
    }

    public GetCompatibleConfigurationsResponse createGetCompatibleConfigurationsResponse() {
        return new GetCompatibleConfigurationsResponse();
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver20/ptz/wsdl", name="Capabilities")
    public JAXBElement<Capabilities> createCapabilities(Capabilities value) {
        return new JAXBElement<Capabilities>(_Capabilities_QNAME, Capabilities.class, null, value);
    }
}

