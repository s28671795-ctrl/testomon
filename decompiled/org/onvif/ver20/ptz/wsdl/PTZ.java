/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver20.ptz.wsdl;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.Duration;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.onvif.ver10.schema.PTZConfiguration;
import org.onvif.ver10.schema.PTZConfigurationOptions;
import org.onvif.ver10.schema.PTZNode;
import org.onvif.ver10.schema.PTZPreset;
import org.onvif.ver10.schema.PTZPresetTourOperation;
import org.onvif.ver10.schema.PTZPresetTourOptions;
import org.onvif.ver10.schema.PTZSpeed;
import org.onvif.ver10.schema.PTZStatus;
import org.onvif.ver10.schema.PTZVector;
import org.onvif.ver10.schema.PresetTour;
import org.onvif.ver20.ptz.wsdl.Capabilities;
import org.onvif.ver20.ptz.wsdl.ObjectFactory;

@WebService(targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", name="PTZ")
@XmlSeeAlso(value={ObjectFactory.class, org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, org.w3._2004._08.xop.include.ObjectFactory.class, org.onvif.ver10.schema.ObjectFactory.class, org.oasis_open.docs.wsn.b_2.ObjectFactory.class, org.oasis_open.docs.wsn.t_1.ObjectFactory.class, org.w3._2003._05.soap_envelope.ObjectFactory.class, org.w3._2005._05.xmlmime.ObjectFactory.class})
public interface PTZ {
    @WebMethod(operationName="SetPreset", action="http://www.onvif.org/ver20/ptz/wsdl/SetPreset")
    @RequestWrapper(localName="SetPreset", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.SetPreset")
    @ResponseWrapper(localName="SetPresetResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.SetPresetResponse")
    public void setPreset(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1, @WebParam(name="PresetName", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var2, @WebParam(mode=WebParam.Mode.INOUT, name="PresetToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") Holder<String> var3);

    @WebMethod(operationName="ContinuousMove", action="http://www.onvif.org/ver20/ptz/wsdl/ContinuousMove")
    @RequestWrapper(localName="ContinuousMove", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.ContinuousMove")
    @ResponseWrapper(localName="ContinuousMoveResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.ContinuousMoveResponse")
    public void continuousMove(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1, @WebParam(name="Velocity", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") PTZSpeed var2, @WebParam(name="Timeout", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") Duration var3);

    @WebMethod(operationName="GetStatus", action="http://www.onvif.org/ver20/ptz/wsdl/GetStatus")
    @RequestWrapper(localName="GetStatus", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetStatus")
    @ResponseWrapper(localName="GetStatusResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetStatusResponse")
    @WebResult(name="PTZStatus", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl")
    public PTZStatus getStatus(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1);

    @WebMethod(operationName="OperatePresetTour", action="http://www.onvif.org/ver20/ptz/wsdl/OperatePresetTour")
    @RequestWrapper(localName="OperatePresetTour", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.OperatePresetTour")
    @ResponseWrapper(localName="OperatePresetTourResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.OperatePresetTourResponse")
    public void operatePresetTour(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1, @WebParam(name="PresetTourToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var2, @WebParam(name="Operation", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") PTZPresetTourOperation var3);

    @WebMethod(operationName="SetHomePosition", action="http://www.onvif.org/ver20/ptz/wsdl/SetHomePosition")
    @RequestWrapper(localName="SetHomePosition", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.SetHomePosition")
    @ResponseWrapper(localName="SetHomePositionResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.SetHomePositionResponse")
    public void setHomePosition(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1);

    @WebMethod(operationName="CreatePresetTour", action="http://www.onvif.org/ver20/ptz/wsdl/CreatePresetTour")
    @RequestWrapper(localName="CreatePresetTour", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.CreatePresetTour")
    @ResponseWrapper(localName="CreatePresetTourResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.CreatePresetTourResponse")
    @WebResult(name="PresetTourToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl")
    public String createPresetTour(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1);

    @WebMethod(operationName="ModifyPresetTour", action="http://www.onvif.org/ver20/ptz/wsdl/ModifyPresetTour")
    @RequestWrapper(localName="ModifyPresetTour", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.ModifyPresetTour")
    @ResponseWrapper(localName="ModifyPresetTourResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.ModifyPresetTourResponse")
    public void modifyPresetTour(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1, @WebParam(name="PresetTour", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") PresetTour var2);

    @WebMethod(operationName="Stop", action="http://www.onvif.org/ver20/ptz/wsdl/Stop")
    @RequestWrapper(localName="Stop", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.Stop")
    @ResponseWrapper(localName="StopResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.StopResponse")
    public void stop(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1, @WebParam(name="PanTilt", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") Boolean var2, @WebParam(name="Zoom", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") Boolean var3);

    @WebMethod(operationName="GetConfigurations", action="http://www.onvif.org/ver20/ptz/wsdl/GetConfigurations")
    @RequestWrapper(localName="GetConfigurations", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetConfigurations")
    @ResponseWrapper(localName="GetConfigurationsResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetConfigurationsResponse")
    @WebResult(name="PTZConfiguration", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl")
    public List<PTZConfiguration> getConfigurations();

    @WebMethod(operationName="GetPresetTours", action="http://www.onvif.org/ver20/ptz/wsdl/GetPresetTours")
    @RequestWrapper(localName="GetPresetTours", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetPresetTours")
    @ResponseWrapper(localName="GetPresetToursResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetPresetToursResponse")
    @WebResult(name="PresetTour", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl")
    public List<PresetTour> getPresetTours(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1);

    @WebMethod(operationName="RemovePreset", action="http://www.onvif.org/ver20/ptz/wsdl/RemovePreset")
    @RequestWrapper(localName="RemovePreset", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.RemovePreset")
    @ResponseWrapper(localName="RemovePresetResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.RemovePresetResponse")
    public void removePreset(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1, @WebParam(name="PresetToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var2);

    @WebMethod(operationName="GetPresetTour", action="http://www.onvif.org/ver20/ptz/wsdl/GetPresetTour")
    @RequestWrapper(localName="GetPresetTour", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetPresetTour")
    @ResponseWrapper(localName="GetPresetTourResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetPresetTourResponse")
    @WebResult(name="PresetTour", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl")
    public PresetTour getPresetTour(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1, @WebParam(name="PresetTourToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var2);

    @WebMethod(operationName="RelativeMove", action="http://www.onvif.org/ver20/ptz/wsdl/RelativeMove")
    @RequestWrapper(localName="RelativeMove", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.RelativeMove")
    @ResponseWrapper(localName="RelativeMoveResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.RelativeMoveResponse")
    public void relativeMove(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1, @WebParam(name="Translation", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") PTZVector var2, @WebParam(name="Speed", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") PTZSpeed var3);

    @WebMethod(operationName="GotoHomePosition", action="http://www.onvif.org/ver20/ptz/wsdl/GotoHomePosition")
    @RequestWrapper(localName="GotoHomePosition", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GotoHomePosition")
    @ResponseWrapper(localName="GotoHomePositionResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GotoHomePositionResponse")
    public void gotoHomePosition(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1, @WebParam(name="Speed", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") PTZSpeed var2);

    @WebMethod(operationName="GetServiceCapabilities", action="http://www.onvif.org/ver20/ptz/wsdl/GetServiceCapabilities")
    @RequestWrapper(localName="GetServiceCapabilities", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetServiceCapabilities")
    @ResponseWrapper(localName="GetServiceCapabilitiesResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetServiceCapabilitiesResponse")
    @WebResult(name="Capabilities", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl")
    public Capabilities getServiceCapabilities();

    @WebMethod(operationName="GetConfiguration", action="http://www.onvif.org/ver20/ptz/wsdl/GetConfiguration")
    @RequestWrapper(localName="GetConfiguration", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetConfiguration")
    @ResponseWrapper(localName="GetConfigurationResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetConfigurationResponse")
    @WebResult(name="PTZConfiguration", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl")
    public PTZConfiguration getConfiguration(@WebParam(name="PTZConfigurationToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1);

    @WebMethod(operationName="SendAuxiliaryCommand", action="http://www.onvif.org/ver20/ptz/wsdl/SendAuxiliaryCommand")
    @RequestWrapper(localName="SendAuxiliaryCommand", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.SendAuxiliaryCommand")
    @ResponseWrapper(localName="SendAuxiliaryCommandResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.SendAuxiliaryCommandResponse")
    @WebResult(name="AuxiliaryResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl")
    public String sendAuxiliaryCommand(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1, @WebParam(name="AuxiliaryData", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var2);

    @WebMethod(operationName="SetConfiguration", action="http://www.onvif.org/ver20/ptz/wsdl/SetConfiguration")
    @RequestWrapper(localName="SetConfiguration", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.SetConfiguration")
    @ResponseWrapper(localName="SetConfigurationResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.SetConfigurationResponse")
    public void setConfiguration(@WebParam(name="PTZConfiguration", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") PTZConfiguration var1, @WebParam(name="ForcePersistence", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") boolean var2);

    @WebMethod(operationName="GetPresets", action="http://www.onvif.org/ver20/ptz/wsdl/GetPresets")
    @RequestWrapper(localName="GetPresets", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetPresets")
    @ResponseWrapper(localName="GetPresetsResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetPresetsResponse")
    @WebResult(name="Preset", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl")
    public List<PTZPreset> getPresets(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1);

    @WebMethod(operationName="GetNode", action="http://www.onvif.org/ver20/ptz/wsdl/GetNode")
    @RequestWrapper(localName="GetNode", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetNode")
    @ResponseWrapper(localName="GetNodeResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetNodeResponse")
    @WebResult(name="PTZNode", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl")
    public PTZNode getNode(@WebParam(name="NodeToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1);

    @WebMethod(operationName="GotoPreset", action="http://www.onvif.org/ver20/ptz/wsdl/GotoPreset")
    @RequestWrapper(localName="GotoPreset", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GotoPreset")
    @ResponseWrapper(localName="GotoPresetResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GotoPresetResponse")
    public void gotoPreset(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1, @WebParam(name="PresetToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var2, @WebParam(name="Speed", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") PTZSpeed var3);

    @WebMethod(operationName="RemovePresetTour", action="http://www.onvif.org/ver20/ptz/wsdl/RemovePresetTour")
    @RequestWrapper(localName="RemovePresetTour", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.RemovePresetTour")
    @ResponseWrapper(localName="RemovePresetTourResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.RemovePresetTourResponse")
    public void removePresetTour(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1, @WebParam(name="PresetTourToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var2);

    @WebMethod(operationName="GetNodes", action="http://www.onvif.org/ver20/ptz/wsdl/GetNodes")
    @RequestWrapper(localName="GetNodes", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetNodes")
    @ResponseWrapper(localName="GetNodesResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetNodesResponse")
    @WebResult(name="PTZNode", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl")
    public List<PTZNode> getNodes();

    @WebMethod(operationName="GetPresetTourOptions", action="http://www.onvif.org/ver20/ptz/wsdl/GetPresetTourOptions")
    @RequestWrapper(localName="GetPresetTourOptions", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetPresetTourOptions")
    @ResponseWrapper(localName="GetPresetTourOptionsResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetPresetTourOptionsResponse")
    @WebResult(name="Options", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl")
    public PTZPresetTourOptions getPresetTourOptions(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1, @WebParam(name="PresetTourToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var2);

    @WebMethod(operationName="AbsoluteMove", action="http://www.onvif.org/ver20/ptz/wsdl/AbsoluteMove")
    @RequestWrapper(localName="AbsoluteMove", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.AbsoluteMove")
    @ResponseWrapper(localName="AbsoluteMoveResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.AbsoluteMoveResponse")
    public void absoluteMove(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1, @WebParam(name="Position", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") PTZVector var2, @WebParam(name="Speed", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") PTZSpeed var3);

    @WebMethod(operationName="GetCompatibleConfigurations", action="http://www.onvif.org/ver20/ptz/wsdl/GetCompatibleConfigurations")
    @RequestWrapper(localName="GetCompatibleConfigurations", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetCompatibleConfigurations")
    @ResponseWrapper(localName="GetCompatibleConfigurationsResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetCompatibleConfigurationsResponse")
    @WebResult(name="PTZConfiguration", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl")
    public List<PTZConfiguration> getCompatibleConfigurations(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1);

    @WebMethod(operationName="GetConfigurationOptions", action="http://www.onvif.org/ver20/ptz/wsdl/GetConfigurationOptions")
    @RequestWrapper(localName="GetConfigurationOptions", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetConfigurationOptions")
    @ResponseWrapper(localName="GetConfigurationOptionsResponse", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl", className="org.onvif.ver20.ptz.wsdl.GetConfigurationOptionsResponse")
    @WebResult(name="PTZConfigurationOptions", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl")
    public PTZConfigurationOptions getConfigurationOptions(@WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver20/ptz/wsdl") String var1);
}

