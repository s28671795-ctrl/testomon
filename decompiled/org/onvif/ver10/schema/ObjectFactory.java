/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.onvif.ver10.schema.AACDecOptions;
import org.onvif.ver10.schema.AbsoluteFocus;
import org.onvif.ver10.schema.AbsoluteFocusOptions;
import org.onvif.ver10.schema.ActionEngineEventPayload;
import org.onvif.ver10.schema.ActionEngineEventPayloadExtension;
import org.onvif.ver10.schema.AnalyticsCapabilities;
import org.onvif.ver10.schema.AnalyticsDeviceCapabilities;
import org.onvif.ver10.schema.AnalyticsDeviceEngineConfiguration;
import org.onvif.ver10.schema.AnalyticsDeviceEngineConfigurationExtension;
import org.onvif.ver10.schema.AnalyticsDeviceExtension;
import org.onvif.ver10.schema.AnalyticsEngine;
import org.onvif.ver10.schema.AnalyticsEngineConfiguration;
import org.onvif.ver10.schema.AnalyticsEngineConfigurationExtension;
import org.onvif.ver10.schema.AnalyticsEngineControl;
import org.onvif.ver10.schema.AnalyticsEngineInput;
import org.onvif.ver10.schema.AnalyticsEngineInputInfo;
import org.onvif.ver10.schema.AnalyticsEngineInputInfoExtension;
import org.onvif.ver10.schema.AnalyticsState;
import org.onvif.ver10.schema.AnalyticsStateInformation;
import org.onvif.ver10.schema.AnyHolder;
import org.onvif.ver10.schema.Appearance;
import org.onvif.ver10.schema.AppearanceExtension;
import org.onvif.ver10.schema.ArrayOfFileProgress;
import org.onvif.ver10.schema.ArrayOfFileProgressExtension;
import org.onvif.ver10.schema.AttachmentData;
import org.onvif.ver10.schema.AudioAnalyticsStream;
import org.onvif.ver10.schema.AudioAnalyticsStreamExtension;
import org.onvif.ver10.schema.AudioAttributes;
import org.onvif.ver10.schema.AudioClassCandidate;
import org.onvif.ver10.schema.AudioClassDescriptor;
import org.onvif.ver10.schema.AudioClassDescriptorExtension;
import org.onvif.ver10.schema.AudioDecoderConfiguration;
import org.onvif.ver10.schema.AudioDecoderConfigurationOptions;
import org.onvif.ver10.schema.AudioDecoderConfigurationOptionsExtension;
import org.onvif.ver10.schema.AudioDescriptor;
import org.onvif.ver10.schema.AudioEncoder2Configuration;
import org.onvif.ver10.schema.AudioEncoder2ConfigurationOptions;
import org.onvif.ver10.schema.AudioEncoderConfiguration;
import org.onvif.ver10.schema.AudioEncoderConfigurationOption;
import org.onvif.ver10.schema.AudioEncoderConfigurationOptions;
import org.onvif.ver10.schema.AudioOutput;
import org.onvif.ver10.schema.AudioOutputConfiguration;
import org.onvif.ver10.schema.AudioOutputConfigurationOptions;
import org.onvif.ver10.schema.AudioSource;
import org.onvif.ver10.schema.AudioSourceConfiguration;
import org.onvif.ver10.schema.AudioSourceConfigurationOptions;
import org.onvif.ver10.schema.AudioSourceOptionsExtension;
import org.onvif.ver10.schema.BacklightCompensation;
import org.onvif.ver10.schema.BacklightCompensation20;
import org.onvif.ver10.schema.BacklightCompensationOptions;
import org.onvif.ver10.schema.BacklightCompensationOptions20;
import org.onvif.ver10.schema.BackupFile;
import org.onvif.ver10.schema.Behaviour;
import org.onvif.ver10.schema.BehaviourExtension;
import org.onvif.ver10.schema.BinaryData;
import org.onvif.ver10.schema.Capabilities;
import org.onvif.ver10.schema.CapabilitiesExtension;
import org.onvif.ver10.schema.CapabilitiesExtension2;
import org.onvif.ver10.schema.CellLayout;
import org.onvif.ver10.schema.Certificate;
import org.onvif.ver10.schema.CertificateGenerationParameters;
import org.onvif.ver10.schema.CertificateGenerationParametersExtension;
import org.onvif.ver10.schema.CertificateInformation;
import org.onvif.ver10.schema.CertificateInformationExtension;
import org.onvif.ver10.schema.CertificateStatus;
import org.onvif.ver10.schema.CertificateUsage;
import org.onvif.ver10.schema.CertificateWithPrivateKey;
import org.onvif.ver10.schema.ClassDescriptor;
import org.onvif.ver10.schema.ClassDescriptorExtension;
import org.onvif.ver10.schema.ClassDescriptorExtension2;
import org.onvif.ver10.schema.CodingCapabilities;
import org.onvif.ver10.schema.Color;
import org.onvif.ver10.schema.ColorCovariance;
import org.onvif.ver10.schema.ColorDescriptor;
import org.onvif.ver10.schema.ColorDescriptorExtension;
import org.onvif.ver10.schema.ColorOptions;
import org.onvif.ver10.schema.ColorspaceRange;
import org.onvif.ver10.schema.Config;
import org.onvif.ver10.schema.ConfigDescription;
import org.onvif.ver10.schema.ConfigDescriptionExtension;
import org.onvif.ver10.schema.ConfigurationEntity;
import org.onvif.ver10.schema.ContinuousFocus;
import org.onvif.ver10.schema.ContinuousFocusOptions;
import org.onvif.ver10.schema.DNSInformation;
import org.onvif.ver10.schema.DNSInformationExtension;
import org.onvif.ver10.schema.Date;
import org.onvif.ver10.schema.DateTime;
import org.onvif.ver10.schema.DateTimeRange;
import org.onvif.ver10.schema.Defogging;
import org.onvif.ver10.schema.DefoggingExtension;
import org.onvif.ver10.schema.DefoggingOptions;
import org.onvif.ver10.schema.DeviceCapabilities;
import org.onvif.ver10.schema.DeviceCapabilitiesExtension;
import org.onvif.ver10.schema.DeviceEntity;
import org.onvif.ver10.schema.DeviceIOCapabilities;
import org.onvif.ver10.schema.DigitalInput;
import org.onvif.ver10.schema.DisplayCapabilities;
import org.onvif.ver10.schema.Dot11AvailableNetworks;
import org.onvif.ver10.schema.Dot11AvailableNetworksExtension;
import org.onvif.ver10.schema.Dot11Capabilities;
import org.onvif.ver10.schema.Dot11Configuration;
import org.onvif.ver10.schema.Dot11PSKSet;
import org.onvif.ver10.schema.Dot11PSKSetExtension;
import org.onvif.ver10.schema.Dot11SecurityConfiguration;
import org.onvif.ver10.schema.Dot11SecurityConfigurationExtension;
import org.onvif.ver10.schema.Dot11Status;
import org.onvif.ver10.schema.Dot1XConfiguration;
import org.onvif.ver10.schema.Dot1XConfigurationExtension;
import org.onvif.ver10.schema.Dot3Configuration;
import org.onvif.ver10.schema.DurationRange;
import org.onvif.ver10.schema.DynamicDNSInformation;
import org.onvif.ver10.schema.DynamicDNSInformationExtension;
import org.onvif.ver10.schema.EAPMethodConfiguration;
import org.onvif.ver10.schema.EFlip;
import org.onvif.ver10.schema.EFlipOptions;
import org.onvif.ver10.schema.EFlipOptionsExtension;
import org.onvif.ver10.schema.EapMethodExtension;
import org.onvif.ver10.schema.EngineConfiguration;
import org.onvif.ver10.schema.EventCapabilities;
import org.onvif.ver10.schema.EventFilter;
import org.onvif.ver10.schema.EventStream;
import org.onvif.ver10.schema.EventStreamExtension;
import org.onvif.ver10.schema.EventSubscription;
import org.onvif.ver10.schema.Exposure;
import org.onvif.ver10.schema.Exposure20;
import org.onvif.ver10.schema.ExposureOptions;
import org.onvif.ver10.schema.ExposureOptions20;
import org.onvif.ver10.schema.FileProgress;
import org.onvif.ver10.schema.FindEventResult;
import org.onvif.ver10.schema.FindEventResultList;
import org.onvif.ver10.schema.FindMetadataResult;
import org.onvif.ver10.schema.FindMetadataResultList;
import org.onvif.ver10.schema.FindPTZPositionResult;
import org.onvif.ver10.schema.FindPTZPositionResultList;
import org.onvif.ver10.schema.FindRecordingResultList;
import org.onvif.ver10.schema.FloatList;
import org.onvif.ver10.schema.FloatRange;
import org.onvif.ver10.schema.FocusConfiguration;
import org.onvif.ver10.schema.FocusConfiguration20;
import org.onvif.ver10.schema.FocusConfiguration20Extension;
import org.onvif.ver10.schema.FocusMove;
import org.onvif.ver10.schema.FocusOptions;
import org.onvif.ver10.schema.FocusOptions20;
import org.onvif.ver10.schema.FocusOptions20Extension;
import org.onvif.ver10.schema.FocusStatus;
import org.onvif.ver10.schema.FocusStatus20;
import org.onvif.ver10.schema.FocusStatus20Extension;
import org.onvif.ver10.schema.Frame;
import org.onvif.ver10.schema.FrameExtension;
import org.onvif.ver10.schema.FrameExtension2;
import org.onvif.ver10.schema.G711DecOptions;
import org.onvif.ver10.schema.G726DecOptions;
import org.onvif.ver10.schema.GenericEapPwdConfigurationExtension;
import org.onvif.ver10.schema.GetRecordingJobsResponseItem;
import org.onvif.ver10.schema.GetRecordingsResponseItem;
import org.onvif.ver10.schema.GetTracksResponseItem;
import org.onvif.ver10.schema.GetTracksResponseList;
import org.onvif.ver10.schema.H264Configuration;
import org.onvif.ver10.schema.H264DecOptions;
import org.onvif.ver10.schema.H264Options;
import org.onvif.ver10.schema.H264Options2;
import org.onvif.ver10.schema.HostnameInformation;
import org.onvif.ver10.schema.HostnameInformationExtension;
import org.onvif.ver10.schema.IOCapabilities;
import org.onvif.ver10.schema.IOCapabilitiesExtension;
import org.onvif.ver10.schema.IOCapabilitiesExtension2;
import org.onvif.ver10.schema.IPAddress;
import org.onvif.ver10.schema.IPAddressFilter;
import org.onvif.ver10.schema.IPAddressFilterExtension;
import org.onvif.ver10.schema.IPv4Configuration;
import org.onvif.ver10.schema.IPv4NetworkInterface;
import org.onvif.ver10.schema.IPv4NetworkInterfaceSetConfiguration;
import org.onvif.ver10.schema.IPv6Configuration;
import org.onvif.ver10.schema.IPv6ConfigurationExtension;
import org.onvif.ver10.schema.IPv6NetworkInterface;
import org.onvif.ver10.schema.IPv6NetworkInterfaceSetConfiguration;
import org.onvif.ver10.schema.ImageStabilization;
import org.onvif.ver10.schema.ImageStabilizationExtension;
import org.onvif.ver10.schema.ImageStabilizationOptions;
import org.onvif.ver10.schema.ImageStabilizationOptionsExtension;
import org.onvif.ver10.schema.ImagingCapabilities;
import org.onvif.ver10.schema.ImagingOptions;
import org.onvif.ver10.schema.ImagingOptions20;
import org.onvif.ver10.schema.ImagingOptions20Extension;
import org.onvif.ver10.schema.ImagingOptions20Extension2;
import org.onvif.ver10.schema.ImagingOptions20Extension3;
import org.onvif.ver10.schema.ImagingOptions20Extension4;
import org.onvif.ver10.schema.ImagingSettings;
import org.onvif.ver10.schema.ImagingSettings20;
import org.onvif.ver10.schema.ImagingSettingsExtension;
import org.onvif.ver10.schema.ImagingSettingsExtension20;
import org.onvif.ver10.schema.ImagingSettingsExtension202;
import org.onvif.ver10.schema.ImagingSettingsExtension203;
import org.onvif.ver10.schema.ImagingSettingsExtension204;
import org.onvif.ver10.schema.ImagingStatus;
import org.onvif.ver10.schema.ImagingStatus20;
import org.onvif.ver10.schema.ImagingStatus20Extension;
import org.onvif.ver10.schema.IntList;
import org.onvif.ver10.schema.IntRange;
import org.onvif.ver10.schema.IntRectangle;
import org.onvif.ver10.schema.IntRectangleRange;
import org.onvif.ver10.schema.IrCutFilterAutoAdjustment;
import org.onvif.ver10.schema.IrCutFilterAutoAdjustmentExtension;
import org.onvif.ver10.schema.IrCutFilterAutoAdjustmentOptions;
import org.onvif.ver10.schema.IrCutFilterAutoAdjustmentOptionsExtension;
import org.onvif.ver10.schema.ItemList;
import org.onvif.ver10.schema.ItemListDescription;
import org.onvif.ver10.schema.ItemListDescriptionExtension;
import org.onvif.ver10.schema.ItemListExtension;
import org.onvif.ver10.schema.JpegDecOptions;
import org.onvif.ver10.schema.JpegOptions;
import org.onvif.ver10.schema.JpegOptions2;
import org.onvif.ver10.schema.Layout;
import org.onvif.ver10.schema.LayoutExtension;
import org.onvif.ver10.schema.LayoutOptions;
import org.onvif.ver10.schema.LayoutOptionsExtension;
import org.onvif.ver10.schema.LensDescription;
import org.onvif.ver10.schema.LensOffset;
import org.onvif.ver10.schema.LensProjection;
import org.onvif.ver10.schema.MaximumNumberOfOSDs;
import org.onvif.ver10.schema.MediaAttributes;
import org.onvif.ver10.schema.MediaCapabilities;
import org.onvif.ver10.schema.MediaCapabilitiesExtension;
import org.onvif.ver10.schema.MediaUri;
import org.onvif.ver10.schema.Merge;
import org.onvif.ver10.schema.Message;
import org.onvif.ver10.schema.MessageDescription;
import org.onvif.ver10.schema.MessageDescriptionExtension;
import org.onvif.ver10.schema.MessageExtension;
import org.onvif.ver10.schema.MetadataAttributes;
import org.onvif.ver10.schema.MetadataConfiguration;
import org.onvif.ver10.schema.MetadataConfigurationExtension;
import org.onvif.ver10.schema.MetadataConfigurationOptions;
import org.onvif.ver10.schema.MetadataConfigurationOptionsExtension;
import org.onvif.ver10.schema.MetadataConfigurationOptionsExtension2;
import org.onvif.ver10.schema.MetadataFilter;
import org.onvif.ver10.schema.MetadataInput;
import org.onvif.ver10.schema.MetadataInputExtension;
import org.onvif.ver10.schema.MetadataStream;
import org.onvif.ver10.schema.MetadataStreamExtension;
import org.onvif.ver10.schema.MetadataStreamExtension2;
import org.onvif.ver10.schema.MotionExpression;
import org.onvif.ver10.schema.MotionExpressionConfiguration;
import org.onvif.ver10.schema.MotionInCells;
import org.onvif.ver10.schema.MoveOptions;
import org.onvif.ver10.schema.MoveOptions20;
import org.onvif.ver10.schema.Mpeg4Configuration;
import org.onvif.ver10.schema.Mpeg4DecOptions;
import org.onvif.ver10.schema.Mpeg4Options;
import org.onvif.ver10.schema.Mpeg4Options2;
import org.onvif.ver10.schema.MulticastConfiguration;
import org.onvif.ver10.schema.NTPInformation;
import org.onvif.ver10.schema.NTPInformationExtension;
import org.onvif.ver10.schema.NetworkCapabilities;
import org.onvif.ver10.schema.NetworkCapabilitiesExtension;
import org.onvif.ver10.schema.NetworkCapabilitiesExtension2;
import org.onvif.ver10.schema.NetworkGateway;
import org.onvif.ver10.schema.NetworkHost;
import org.onvif.ver10.schema.NetworkHostExtension;
import org.onvif.ver10.schema.NetworkInterface;
import org.onvif.ver10.schema.NetworkInterfaceConnectionSetting;
import org.onvif.ver10.schema.NetworkInterfaceExtension;
import org.onvif.ver10.schema.NetworkInterfaceExtension2;
import org.onvif.ver10.schema.NetworkInterfaceInfo;
import org.onvif.ver10.schema.NetworkInterfaceLink;
import org.onvif.ver10.schema.NetworkInterfaceSetConfiguration;
import org.onvif.ver10.schema.NetworkInterfaceSetConfigurationExtension;
import org.onvif.ver10.schema.NetworkInterfaceSetConfigurationExtension2;
import org.onvif.ver10.schema.NetworkProtocol;
import org.onvif.ver10.schema.NetworkProtocolExtension;
import org.onvif.ver10.schema.NetworkZeroConfiguration;
import org.onvif.ver10.schema.NetworkZeroConfigurationExtension;
import org.onvif.ver10.schema.NetworkZeroConfigurationExtension2;
import org.onvif.ver10.schema.NoiseReduction;
import org.onvif.ver10.schema.NoiseReductionOptions;
import org.onvif.ver10.schema.OSDColor;
import org.onvif.ver10.schema.OSDColorOptions;
import org.onvif.ver10.schema.OSDColorOptionsExtension;
import org.onvif.ver10.schema.OSDConfiguration;
import org.onvif.ver10.schema.OSDConfigurationExtension;
import org.onvif.ver10.schema.OSDConfigurationOptions;
import org.onvif.ver10.schema.OSDConfigurationOptionsExtension;
import org.onvif.ver10.schema.OSDImgConfiguration;
import org.onvif.ver10.schema.OSDImgConfigurationExtension;
import org.onvif.ver10.schema.OSDImgOptions;
import org.onvif.ver10.schema.OSDImgOptionsExtension;
import org.onvif.ver10.schema.OSDPosConfiguration;
import org.onvif.ver10.schema.OSDPosConfigurationExtension;
import org.onvif.ver10.schema.OSDReference;
import org.onvif.ver10.schema.OSDTextConfiguration;
import org.onvif.ver10.schema.OSDTextConfigurationExtension;
import org.onvif.ver10.schema.OSDTextOptions;
import org.onvif.ver10.schema.OSDTextOptionsExtension;
import org.onvif.ver10.schema.Object;
import org.onvif.ver10.schema.ObjectExtension;
import org.onvif.ver10.schema.ObjectId;
import org.onvif.ver10.schema.ObjectTree;
import org.onvif.ver10.schema.ObjectTreeExtension;
import org.onvif.ver10.schema.OnvifVersion;
import org.onvif.ver10.schema.OtherType;
import org.onvif.ver10.schema.PTControlDirection;
import org.onvif.ver10.schema.PTControlDirectionExtension;
import org.onvif.ver10.schema.PTControlDirectionOptions;
import org.onvif.ver10.schema.PTControlDirectionOptionsExtension;
import org.onvif.ver10.schema.PTZCapabilities;
import org.onvif.ver10.schema.PTZConfiguration;
import org.onvif.ver10.schema.PTZConfigurationExtension;
import org.onvif.ver10.schema.PTZConfigurationExtension2;
import org.onvif.ver10.schema.PTZConfigurationOptions;
import org.onvif.ver10.schema.PTZConfigurationOptions2;
import org.onvif.ver10.schema.PTZFilter;
import org.onvif.ver10.schema.PTZMoveStatus;
import org.onvif.ver10.schema.PTZNode;
import org.onvif.ver10.schema.PTZNodeExtension;
import org.onvif.ver10.schema.PTZNodeExtension2;
import org.onvif.ver10.schema.PTZPositionFilter;
import org.onvif.ver10.schema.PTZPreset;
import org.onvif.ver10.schema.PTZPresetTourExtension;
import org.onvif.ver10.schema.PTZPresetTourOptions;
import org.onvif.ver10.schema.PTZPresetTourPresetDetail;
import org.onvif.ver10.schema.PTZPresetTourPresetDetailOptions;
import org.onvif.ver10.schema.PTZPresetTourPresetDetailOptionsExtension;
import org.onvif.ver10.schema.PTZPresetTourSpot;
import org.onvif.ver10.schema.PTZPresetTourSpotExtension;
import org.onvif.ver10.schema.PTZPresetTourSpotOptions;
import org.onvif.ver10.schema.PTZPresetTourStartingCondition;
import org.onvif.ver10.schema.PTZPresetTourStartingConditionExtension;
import org.onvif.ver10.schema.PTZPresetTourStartingConditionOptions;
import org.onvif.ver10.schema.PTZPresetTourStartingConditionOptionsExtension;
import org.onvif.ver10.schema.PTZPresetTourStatus;
import org.onvif.ver10.schema.PTZPresetTourStatusExtension;
import org.onvif.ver10.schema.PTZPresetTourSupported;
import org.onvif.ver10.schema.PTZPresetTourSupportedExtension;
import org.onvif.ver10.schema.PTZPresetTourTypeExtension;
import org.onvif.ver10.schema.PTZSpaces;
import org.onvif.ver10.schema.PTZSpacesExtension;
import org.onvif.ver10.schema.PTZSpeed;
import org.onvif.ver10.schema.PTZStatus;
import org.onvif.ver10.schema.PTZStatusFilterOptions;
import org.onvif.ver10.schema.PTZStatusFilterOptionsExtension;
import org.onvif.ver10.schema.PTZStream;
import org.onvif.ver10.schema.PTZStreamExtension;
import org.onvif.ver10.schema.PTZVector;
import org.onvif.ver10.schema.PanTiltLimits;
import org.onvif.ver10.schema.PaneConfiguration;
import org.onvif.ver10.schema.PaneLayout;
import org.onvif.ver10.schema.PaneLayoutOptions;
import org.onvif.ver10.schema.PaneOptionExtension;
import org.onvif.ver10.schema.Polygon;
import org.onvif.ver10.schema.PolygonConfiguration;
import org.onvif.ver10.schema.Polyline;
import org.onvif.ver10.schema.PolylineArray;
import org.onvif.ver10.schema.PolylineArrayConfiguration;
import org.onvif.ver10.schema.PolylineArrayExtension;
import org.onvif.ver10.schema.PrefixedIPv4Address;
import org.onvif.ver10.schema.PrefixedIPv6Address;
import org.onvif.ver10.schema.PresetTour;
import org.onvif.ver10.schema.Profile;
import org.onvif.ver10.schema.ProfileCapabilities;
import org.onvif.ver10.schema.ProfileExtension;
import org.onvif.ver10.schema.ProfileExtension2;
import org.onvif.ver10.schema.RealTimeStreamingCapabilities;
import org.onvif.ver10.schema.RealTimeStreamingCapabilitiesExtension;
import org.onvif.ver10.schema.Receiver;
import org.onvif.ver10.schema.ReceiverCapabilities;
import org.onvif.ver10.schema.ReceiverConfiguration;
import org.onvif.ver10.schema.ReceiverStateInformation;
import org.onvif.ver10.schema.RecordingCapabilities;
import org.onvif.ver10.schema.RecordingConfiguration;
import org.onvif.ver10.schema.RecordingInformation;
import org.onvif.ver10.schema.RecordingJobConfiguration;
import org.onvif.ver10.schema.RecordingJobConfigurationExtension;
import org.onvif.ver10.schema.RecordingJobSource;
import org.onvif.ver10.schema.RecordingJobSourceExtension;
import org.onvif.ver10.schema.RecordingJobStateInformation;
import org.onvif.ver10.schema.RecordingJobStateInformationExtension;
import org.onvif.ver10.schema.RecordingJobStateSource;
import org.onvif.ver10.schema.RecordingJobStateTrack;
import org.onvif.ver10.schema.RecordingJobStateTracks;
import org.onvif.ver10.schema.RecordingJobTrack;
import org.onvif.ver10.schema.RecordingSourceInformation;
import org.onvif.ver10.schema.RecordingSummary;
import org.onvif.ver10.schema.Rectangle;
import org.onvif.ver10.schema.RelativeFocus;
import org.onvif.ver10.schema.RelativeFocusOptions;
import org.onvif.ver10.schema.RelativeFocusOptions20;
import org.onvif.ver10.schema.RelayOutput;
import org.onvif.ver10.schema.RelayOutputSettings;
import org.onvif.ver10.schema.RemoteUser;
import org.onvif.ver10.schema.Rename;
import org.onvif.ver10.schema.ReplayCapabilities;
import org.onvif.ver10.schema.ReplayConfiguration;
import org.onvif.ver10.schema.Reverse;
import org.onvif.ver10.schema.ReverseOptions;
import org.onvif.ver10.schema.ReverseOptionsExtension;
import org.onvif.ver10.schema.Rotate;
import org.onvif.ver10.schema.RotateExtension;
import org.onvif.ver10.schema.RotateOptions;
import org.onvif.ver10.schema.RotateOptionsExtension;
import org.onvif.ver10.schema.RuleEngineConfiguration;
import org.onvif.ver10.schema.RuleEngineConfigurationExtension;
import org.onvif.ver10.schema.Scope;
import org.onvif.ver10.schema.SearchCapabilities;
import org.onvif.ver10.schema.SearchScope;
import org.onvif.ver10.schema.SearchScopeExtension;
import org.onvif.ver10.schema.SecurityCapabilities;
import org.onvif.ver10.schema.SecurityCapabilitiesExtension;
import org.onvif.ver10.schema.SecurityCapabilitiesExtension2;
import org.onvif.ver10.schema.ShapeDescriptor;
import org.onvif.ver10.schema.ShapeDescriptorExtension;
import org.onvif.ver10.schema.SourceIdentification;
import org.onvif.ver10.schema.SourceIdentificationExtension;
import org.onvif.ver10.schema.SourceReference;
import org.onvif.ver10.schema.Space1DDescription;
import org.onvif.ver10.schema.Space2DDescription;
import org.onvif.ver10.schema.Split;
import org.onvif.ver10.schema.StorageReferencePath;
import org.onvif.ver10.schema.StorageReferencePathExtension;
import org.onvif.ver10.schema.StreamSetup;
import org.onvif.ver10.schema.SupportInformation;
import org.onvif.ver10.schema.SupportedAnalyticsModules;
import org.onvif.ver10.schema.SupportedAnalyticsModulesExtension;
import org.onvif.ver10.schema.SupportedRules;
import org.onvif.ver10.schema.SupportedRulesExtension;
import org.onvif.ver10.schema.SystemCapabilities;
import org.onvif.ver10.schema.SystemCapabilitiesExtension;
import org.onvif.ver10.schema.SystemCapabilitiesExtension2;
import org.onvif.ver10.schema.SystemDateTime;
import org.onvif.ver10.schema.SystemDateTimeExtension;
import org.onvif.ver10.schema.SystemLog;
import org.onvif.ver10.schema.SystemLogUri;
import org.onvif.ver10.schema.SystemLogUriList;
import org.onvif.ver10.schema.TLSConfiguration;
import org.onvif.ver10.schema.Time;
import org.onvif.ver10.schema.TimeZone;
import org.onvif.ver10.schema.ToneCompensation;
import org.onvif.ver10.schema.ToneCompensationExtension;
import org.onvif.ver10.schema.ToneCompensationOptions;
import org.onvif.ver10.schema.TrackAttributes;
import org.onvif.ver10.schema.TrackAttributesExtension;
import org.onvif.ver10.schema.TrackConfiguration;
import org.onvif.ver10.schema.TrackInformation;
import org.onvif.ver10.schema.Transformation;
import org.onvif.ver10.schema.TransformationExtension;
import org.onvif.ver10.schema.Transport;
import org.onvif.ver10.schema.User;
import org.onvif.ver10.schema.UserExtension;
import org.onvif.ver10.schema.Vector;
import org.onvif.ver10.schema.Vector1D;
import org.onvif.ver10.schema.Vector2D;
import org.onvif.ver10.schema.VideoAnalyticsConfiguration;
import org.onvif.ver10.schema.VideoAnalyticsStream;
import org.onvif.ver10.schema.VideoAnalyticsStreamExtension;
import org.onvif.ver10.schema.VideoAttributes;
import org.onvif.ver10.schema.VideoDecoderConfigurationOptions;
import org.onvif.ver10.schema.VideoDecoderConfigurationOptionsExtension;
import org.onvif.ver10.schema.VideoEncoder2Configuration;
import org.onvif.ver10.schema.VideoEncoder2ConfigurationOptions;
import org.onvif.ver10.schema.VideoEncoderConfiguration;
import org.onvif.ver10.schema.VideoEncoderConfigurationOptions;
import org.onvif.ver10.schema.VideoEncoderOptionsExtension;
import org.onvif.ver10.schema.VideoEncoderOptionsExtension2;
import org.onvif.ver10.schema.VideoOutput;
import org.onvif.ver10.schema.VideoOutputConfiguration;
import org.onvif.ver10.schema.VideoOutputConfigurationOptions;
import org.onvif.ver10.schema.VideoOutputExtension;
import org.onvif.ver10.schema.VideoRateControl;
import org.onvif.ver10.schema.VideoRateControl2;
import org.onvif.ver10.schema.VideoResolution;
import org.onvif.ver10.schema.VideoResolution2;
import org.onvif.ver10.schema.VideoSource;
import org.onvif.ver10.schema.VideoSourceConfiguration;
import org.onvif.ver10.schema.VideoSourceConfigurationExtension;
import org.onvif.ver10.schema.VideoSourceConfigurationExtension2;
import org.onvif.ver10.schema.VideoSourceConfigurationOptions;
import org.onvif.ver10.schema.VideoSourceConfigurationOptionsExtension;
import org.onvif.ver10.schema.VideoSourceConfigurationOptionsExtension2;
import org.onvif.ver10.schema.VideoSourceExtension;
import org.onvif.ver10.schema.VideoSourceExtension2;
import org.onvif.ver10.schema.WhiteBalance;
import org.onvif.ver10.schema.WhiteBalance20;
import org.onvif.ver10.schema.WhiteBalance20Extension;
import org.onvif.ver10.schema.WhiteBalanceOptions;
import org.onvif.ver10.schema.WhiteBalanceOptions20;
import org.onvif.ver10.schema.WhiteBalanceOptions20Extension;
import org.onvif.ver10.schema.WideDynamicRange;
import org.onvif.ver10.schema.WideDynamicRange20;
import org.onvif.ver10.schema.WideDynamicRangeOptions;
import org.onvif.ver10.schema.WideDynamicRangeOptions20;
import org.onvif.ver10.schema.ZoomLimits;

@XmlRegistry
public class ObjectFactory {
    private static final QName _VideoSourceConfiguration_QNAME = new QName("http://www.onvif.org/ver10/schema", "VideoSourceConfiguration");
    private static final QName _AudioSourceConfiguration_QNAME = new QName("http://www.onvif.org/ver10/schema", "AudioSourceConfiguration");
    private static final QName _VideoEncoderConfiguration_QNAME = new QName("http://www.onvif.org/ver10/schema", "VideoEncoderConfiguration");
    private static final QName _AudioEncoderConfiguration_QNAME = new QName("http://www.onvif.org/ver10/schema", "AudioEncoderConfiguration");
    private static final QName _VideoAnalyticsConfiguration_QNAME = new QName("http://www.onvif.org/ver10/schema", "VideoAnalyticsConfiguration");
    private static final QName _PTZConfiguration_QNAME = new QName("http://www.onvif.org/ver10/schema", "PTZConfiguration");
    private static final QName _MetadataConfiguration_QNAME = new QName("http://www.onvif.org/ver10/schema", "MetadataConfiguration");
    private static final QName _AudioOutputConfiguration_QNAME = new QName("http://www.onvif.org/ver10/schema", "AudioOutputConfiguration");
    private static final QName _AudioDecoderConfiguration_QNAME = new QName("http://www.onvif.org/ver10/schema", "AudioDecoderConfiguration");
    private static final QName _Polygon_QNAME = new QName("http://www.onvif.org/ver10/schema", "Polygon");
    private static final QName _Polyline_QNAME = new QName("http://www.onvif.org/ver10/schema", "Polyline");
    private static final QName _MetadataStream_QNAME = new QName("http://www.onvif.org/ver10/schema", "MetadataStream");

    public ConfigDescription createConfigDescription() {
        return new ConfigDescription();
    }

    public Behaviour createBehaviour() {
        return new Behaviour();
    }

    public ClassDescriptor createClassDescriptor() {
        return new ClassDescriptor();
    }

    public ColorDescriptor createColorDescriptor() {
        return new ColorDescriptor();
    }

    public ItemListDescription createItemListDescription() {
        return new ItemListDescription();
    }

    public EventSubscription createEventSubscription() {
        return new EventSubscription();
    }

    public ItemList createItemList() {
        return new ItemList();
    }

    public VideoSourceConfiguration createVideoSourceConfiguration() {
        return new VideoSourceConfiguration();
    }

    public AudioSourceConfiguration createAudioSourceConfiguration() {
        return new AudioSourceConfiguration();
    }

    public VideoEncoderConfiguration createVideoEncoderConfiguration() {
        return new VideoEncoderConfiguration();
    }

    public AudioEncoderConfiguration createAudioEncoderConfiguration() {
        return new AudioEncoderConfiguration();
    }

    public VideoAnalyticsConfiguration createVideoAnalyticsConfiguration() {
        return new VideoAnalyticsConfiguration();
    }

    public PTZConfiguration createPTZConfiguration() {
        return new PTZConfiguration();
    }

    public MetadataConfiguration createMetadataConfiguration() {
        return new MetadataConfiguration();
    }

    public AudioOutputConfiguration createAudioOutputConfiguration() {
        return new AudioOutputConfiguration();
    }

    public AudioDecoderConfiguration createAudioDecoderConfiguration() {
        return new AudioDecoderConfiguration();
    }

    public Message createMessage() {
        return new Message();
    }

    public MessageExtension createMessageExtension() {
        return new MessageExtension();
    }

    public Polygon createPolygon() {
        return new Polygon();
    }

    public Polyline createPolyline() {
        return new Polyline();
    }

    public MetadataStream createMetadataStream() {
        return new MetadataStream();
    }

    public DeviceEntity createDeviceEntity() {
        return new DeviceEntity();
    }

    public IntRectangle createIntRectangle() {
        return new IntRectangle();
    }

    public IntRectangleRange createIntRectangleRange() {
        return new IntRectangleRange();
    }

    public IntRange createIntRange() {
        return new IntRange();
    }

    public FloatRange createFloatRange() {
        return new FloatRange();
    }

    public DurationRange createDurationRange() {
        return new DurationRange();
    }

    public IntList createIntList() {
        return new IntList();
    }

    public FloatList createFloatList() {
        return new FloatList();
    }

    public AnyHolder createAnyHolder() {
        return new AnyHolder();
    }

    public VideoSource createVideoSource() {
        return new VideoSource();
    }

    public VideoSourceExtension createVideoSourceExtension() {
        return new VideoSourceExtension();
    }

    public VideoSourceExtension2 createVideoSourceExtension2() {
        return new VideoSourceExtension2();
    }

    public AudioSource createAudioSource() {
        return new AudioSource();
    }

    public Profile createProfile() {
        return new Profile();
    }

    public ProfileExtension createProfileExtension() {
        return new ProfileExtension();
    }

    public ProfileExtension2 createProfileExtension2() {
        return new ProfileExtension2();
    }

    public ConfigurationEntity createConfigurationEntity() {
        return new ConfigurationEntity();
    }

    public VideoSourceConfigurationExtension createVideoSourceConfigurationExtension() {
        return new VideoSourceConfigurationExtension();
    }

    public VideoSourceConfigurationExtension2 createVideoSourceConfigurationExtension2() {
        return new VideoSourceConfigurationExtension2();
    }

    public Rotate createRotate() {
        return new Rotate();
    }

    public RotateExtension createRotateExtension() {
        return new RotateExtension();
    }

    public LensProjection createLensProjection() {
        return new LensProjection();
    }

    public LensOffset createLensOffset() {
        return new LensOffset();
    }

    public LensDescription createLensDescription() {
        return new LensDescription();
    }

    public VideoSourceConfigurationOptions createVideoSourceConfigurationOptions() {
        return new VideoSourceConfigurationOptions();
    }

    public VideoSourceConfigurationOptionsExtension createVideoSourceConfigurationOptionsExtension() {
        return new VideoSourceConfigurationOptionsExtension();
    }

    public VideoSourceConfigurationOptionsExtension2 createVideoSourceConfigurationOptionsExtension2() {
        return new VideoSourceConfigurationOptionsExtension2();
    }

    public RotateOptions createRotateOptions() {
        return new RotateOptions();
    }

    public RotateOptionsExtension createRotateOptionsExtension() {
        return new RotateOptionsExtension();
    }

    public VideoResolution createVideoResolution() {
        return new VideoResolution();
    }

    public VideoRateControl createVideoRateControl() {
        return new VideoRateControl();
    }

    public Mpeg4Configuration createMpeg4Configuration() {
        return new Mpeg4Configuration();
    }

    public H264Configuration createH264Configuration() {
        return new H264Configuration();
    }

    public VideoEncoderConfigurationOptions createVideoEncoderConfigurationOptions() {
        return new VideoEncoderConfigurationOptions();
    }

    public VideoEncoderOptionsExtension createVideoEncoderOptionsExtension() {
        return new VideoEncoderOptionsExtension();
    }

    public VideoEncoderOptionsExtension2 createVideoEncoderOptionsExtension2() {
        return new VideoEncoderOptionsExtension2();
    }

    public JpegOptions createJpegOptions() {
        return new JpegOptions();
    }

    public JpegOptions2 createJpegOptions2() {
        return new JpegOptions2();
    }

    public Mpeg4Options createMpeg4Options() {
        return new Mpeg4Options();
    }

    public Mpeg4Options2 createMpeg4Options2() {
        return new Mpeg4Options2();
    }

    public H264Options createH264Options() {
        return new H264Options();
    }

    public H264Options2 createH264Options2() {
        return new H264Options2();
    }

    public VideoEncoder2Configuration createVideoEncoder2Configuration() {
        return new VideoEncoder2Configuration();
    }

    public VideoResolution2 createVideoResolution2() {
        return new VideoResolution2();
    }

    public VideoRateControl2 createVideoRateControl2() {
        return new VideoRateControl2();
    }

    public VideoEncoder2ConfigurationOptions createVideoEncoder2ConfigurationOptions() {
        return new VideoEncoder2ConfigurationOptions();
    }

    public AudioSourceConfigurationOptions createAudioSourceConfigurationOptions() {
        return new AudioSourceConfigurationOptions();
    }

    public AudioSourceOptionsExtension createAudioSourceOptionsExtension() {
        return new AudioSourceOptionsExtension();
    }

    public AudioEncoderConfigurationOptions createAudioEncoderConfigurationOptions() {
        return new AudioEncoderConfigurationOptions();
    }

    public AudioEncoderConfigurationOption createAudioEncoderConfigurationOption() {
        return new AudioEncoderConfigurationOption();
    }

    public AudioEncoder2Configuration createAudioEncoder2Configuration() {
        return new AudioEncoder2Configuration();
    }

    public AudioEncoder2ConfigurationOptions createAudioEncoder2ConfigurationOptions() {
        return new AudioEncoder2ConfigurationOptions();
    }

    public MetadataConfigurationExtension createMetadataConfigurationExtension() {
        return new MetadataConfigurationExtension();
    }

    public PTZFilter createPTZFilter() {
        return new PTZFilter();
    }

    public MetadataConfigurationOptions createMetadataConfigurationOptions() {
        return new MetadataConfigurationOptions();
    }

    public MetadataConfigurationOptionsExtension createMetadataConfigurationOptionsExtension() {
        return new MetadataConfigurationOptionsExtension();
    }

    public MetadataConfigurationOptionsExtension2 createMetadataConfigurationOptionsExtension2() {
        return new MetadataConfigurationOptionsExtension2();
    }

    public PTZStatusFilterOptions createPTZStatusFilterOptions() {
        return new PTZStatusFilterOptions();
    }

    public PTZStatusFilterOptionsExtension createPTZStatusFilterOptionsExtension() {
        return new PTZStatusFilterOptionsExtension();
    }

    public VideoOutput createVideoOutput() {
        return new VideoOutput();
    }

    public VideoOutputExtension createVideoOutputExtension() {
        return new VideoOutputExtension();
    }

    public VideoOutputConfiguration createVideoOutputConfiguration() {
        return new VideoOutputConfiguration();
    }

    public VideoOutputConfigurationOptions createVideoOutputConfigurationOptions() {
        return new VideoOutputConfigurationOptions();
    }

    public VideoDecoderConfigurationOptions createVideoDecoderConfigurationOptions() {
        return new VideoDecoderConfigurationOptions();
    }

    public H264DecOptions createH264DecOptions() {
        return new H264DecOptions();
    }

    public JpegDecOptions createJpegDecOptions() {
        return new JpegDecOptions();
    }

    public Mpeg4DecOptions createMpeg4DecOptions() {
        return new Mpeg4DecOptions();
    }

    public VideoDecoderConfigurationOptionsExtension createVideoDecoderConfigurationOptionsExtension() {
        return new VideoDecoderConfigurationOptionsExtension();
    }

    public AudioOutput createAudioOutput() {
        return new AudioOutput();
    }

    public AudioOutputConfigurationOptions createAudioOutputConfigurationOptions() {
        return new AudioOutputConfigurationOptions();
    }

    public AudioDecoderConfigurationOptions createAudioDecoderConfigurationOptions() {
        return new AudioDecoderConfigurationOptions();
    }

    public G711DecOptions createG711DecOptions() {
        return new G711DecOptions();
    }

    public AACDecOptions createAACDecOptions() {
        return new AACDecOptions();
    }

    public G726DecOptions createG726DecOptions() {
        return new G726DecOptions();
    }

    public AudioDecoderConfigurationOptionsExtension createAudioDecoderConfigurationOptionsExtension() {
        return new AudioDecoderConfigurationOptionsExtension();
    }

    public MulticastConfiguration createMulticastConfiguration() {
        return new MulticastConfiguration();
    }

    public StreamSetup createStreamSetup() {
        return new StreamSetup();
    }

    public Transport createTransport() {
        return new Transport();
    }

    public MediaUri createMediaUri() {
        return new MediaUri();
    }

    public Scope createScope() {
        return new Scope();
    }

    public NetworkInterface createNetworkInterface() {
        return new NetworkInterface();
    }

    public NetworkInterfaceExtension createNetworkInterfaceExtension() {
        return new NetworkInterfaceExtension();
    }

    public Dot3Configuration createDot3Configuration() {
        return new Dot3Configuration();
    }

    public NetworkInterfaceExtension2 createNetworkInterfaceExtension2() {
        return new NetworkInterfaceExtension2();
    }

    public NetworkInterfaceLink createNetworkInterfaceLink() {
        return new NetworkInterfaceLink();
    }

    public NetworkInterfaceConnectionSetting createNetworkInterfaceConnectionSetting() {
        return new NetworkInterfaceConnectionSetting();
    }

    public NetworkInterfaceInfo createNetworkInterfaceInfo() {
        return new NetworkInterfaceInfo();
    }

    public IPv6NetworkInterface createIPv6NetworkInterface() {
        return new IPv6NetworkInterface();
    }

    public IPv4NetworkInterface createIPv4NetworkInterface() {
        return new IPv4NetworkInterface();
    }

    public IPv4Configuration createIPv4Configuration() {
        return new IPv4Configuration();
    }

    public IPv6Configuration createIPv6Configuration() {
        return new IPv6Configuration();
    }

    public IPv6ConfigurationExtension createIPv6ConfigurationExtension() {
        return new IPv6ConfigurationExtension();
    }

    public NetworkProtocol createNetworkProtocol() {
        return new NetworkProtocol();
    }

    public NetworkProtocolExtension createNetworkProtocolExtension() {
        return new NetworkProtocolExtension();
    }

    public NetworkHost createNetworkHost() {
        return new NetworkHost();
    }

    public NetworkHostExtension createNetworkHostExtension() {
        return new NetworkHostExtension();
    }

    public IPAddress createIPAddress() {
        return new IPAddress();
    }

    public PrefixedIPv4Address createPrefixedIPv4Address() {
        return new PrefixedIPv4Address();
    }

    public PrefixedIPv6Address createPrefixedIPv6Address() {
        return new PrefixedIPv6Address();
    }

    public HostnameInformation createHostnameInformation() {
        return new HostnameInformation();
    }

    public HostnameInformationExtension createHostnameInformationExtension() {
        return new HostnameInformationExtension();
    }

    public DNSInformation createDNSInformation() {
        return new DNSInformation();
    }

    public DNSInformationExtension createDNSInformationExtension() {
        return new DNSInformationExtension();
    }

    public NTPInformation createNTPInformation() {
        return new NTPInformation();
    }

    public NTPInformationExtension createNTPInformationExtension() {
        return new NTPInformationExtension();
    }

    public DynamicDNSInformation createDynamicDNSInformation() {
        return new DynamicDNSInformation();
    }

    public DynamicDNSInformationExtension createDynamicDNSInformationExtension() {
        return new DynamicDNSInformationExtension();
    }

    public NetworkInterfaceSetConfiguration createNetworkInterfaceSetConfiguration() {
        return new NetworkInterfaceSetConfiguration();
    }

    public NetworkInterfaceSetConfigurationExtension createNetworkInterfaceSetConfigurationExtension() {
        return new NetworkInterfaceSetConfigurationExtension();
    }

    public IPv6NetworkInterfaceSetConfiguration createIPv6NetworkInterfaceSetConfiguration() {
        return new IPv6NetworkInterfaceSetConfiguration();
    }

    public IPv4NetworkInterfaceSetConfiguration createIPv4NetworkInterfaceSetConfiguration() {
        return new IPv4NetworkInterfaceSetConfiguration();
    }

    public NetworkGateway createNetworkGateway() {
        return new NetworkGateway();
    }

    public NetworkZeroConfiguration createNetworkZeroConfiguration() {
        return new NetworkZeroConfiguration();
    }

    public NetworkZeroConfigurationExtension createNetworkZeroConfigurationExtension() {
        return new NetworkZeroConfigurationExtension();
    }

    public NetworkZeroConfigurationExtension2 createNetworkZeroConfigurationExtension2() {
        return new NetworkZeroConfigurationExtension2();
    }

    public IPAddressFilter createIPAddressFilter() {
        return new IPAddressFilter();
    }

    public IPAddressFilterExtension createIPAddressFilterExtension() {
        return new IPAddressFilterExtension();
    }

    public Dot11Configuration createDot11Configuration() {
        return new Dot11Configuration();
    }

    public Dot11SecurityConfiguration createDot11SecurityConfiguration() {
        return new Dot11SecurityConfiguration();
    }

    public Dot11SecurityConfigurationExtension createDot11SecurityConfigurationExtension() {
        return new Dot11SecurityConfigurationExtension();
    }

    public Dot11PSKSet createDot11PSKSet() {
        return new Dot11PSKSet();
    }

    public Dot11PSKSetExtension createDot11PSKSetExtension() {
        return new Dot11PSKSetExtension();
    }

    public NetworkInterfaceSetConfigurationExtension2 createNetworkInterfaceSetConfigurationExtension2() {
        return new NetworkInterfaceSetConfigurationExtension2();
    }

    public Dot11Capabilities createDot11Capabilities() {
        return new Dot11Capabilities();
    }

    public Dot11Status createDot11Status() {
        return new Dot11Status();
    }

    public Dot11AvailableNetworks createDot11AvailableNetworks() {
        return new Dot11AvailableNetworks();
    }

    public Dot11AvailableNetworksExtension createDot11AvailableNetworksExtension() {
        return new Dot11AvailableNetworksExtension();
    }

    public Capabilities createCapabilities() {
        return new Capabilities();
    }

    public CapabilitiesExtension createCapabilitiesExtension() {
        return new CapabilitiesExtension();
    }

    public CapabilitiesExtension2 createCapabilitiesExtension2() {
        return new CapabilitiesExtension2();
    }

    public AnalyticsCapabilities createAnalyticsCapabilities() {
        return new AnalyticsCapabilities();
    }

    public DeviceCapabilities createDeviceCapabilities() {
        return new DeviceCapabilities();
    }

    public DeviceCapabilitiesExtension createDeviceCapabilitiesExtension() {
        return new DeviceCapabilitiesExtension();
    }

    public EventCapabilities createEventCapabilities() {
        return new EventCapabilities();
    }

    public IOCapabilities createIOCapabilities() {
        return new IOCapabilities();
    }

    public IOCapabilitiesExtension createIOCapabilitiesExtension() {
        return new IOCapabilitiesExtension();
    }

    public IOCapabilitiesExtension2 createIOCapabilitiesExtension2() {
        return new IOCapabilitiesExtension2();
    }

    public MediaCapabilities createMediaCapabilities() {
        return new MediaCapabilities();
    }

    public MediaCapabilitiesExtension createMediaCapabilitiesExtension() {
        return new MediaCapabilitiesExtension();
    }

    public RealTimeStreamingCapabilities createRealTimeStreamingCapabilities() {
        return new RealTimeStreamingCapabilities();
    }

    public RealTimeStreamingCapabilitiesExtension createRealTimeStreamingCapabilitiesExtension() {
        return new RealTimeStreamingCapabilitiesExtension();
    }

    public ProfileCapabilities createProfileCapabilities() {
        return new ProfileCapabilities();
    }

    public NetworkCapabilities createNetworkCapabilities() {
        return new NetworkCapabilities();
    }

    public NetworkCapabilitiesExtension createNetworkCapabilitiesExtension() {
        return new NetworkCapabilitiesExtension();
    }

    public NetworkCapabilitiesExtension2 createNetworkCapabilitiesExtension2() {
        return new NetworkCapabilitiesExtension2();
    }

    public SecurityCapabilities createSecurityCapabilities() {
        return new SecurityCapabilities();
    }

    public SecurityCapabilitiesExtension createSecurityCapabilitiesExtension() {
        return new SecurityCapabilitiesExtension();
    }

    public SecurityCapabilitiesExtension2 createSecurityCapabilitiesExtension2() {
        return new SecurityCapabilitiesExtension2();
    }

    public SystemCapabilities createSystemCapabilities() {
        return new SystemCapabilities();
    }

    public SystemCapabilitiesExtension createSystemCapabilitiesExtension() {
        return new SystemCapabilitiesExtension();
    }

    public SystemCapabilitiesExtension2 createSystemCapabilitiesExtension2() {
        return new SystemCapabilitiesExtension2();
    }

    public OnvifVersion createOnvifVersion() {
        return new OnvifVersion();
    }

    public ImagingCapabilities createImagingCapabilities() {
        return new ImagingCapabilities();
    }

    public PTZCapabilities createPTZCapabilities() {
        return new PTZCapabilities();
    }

    public DeviceIOCapabilities createDeviceIOCapabilities() {
        return new DeviceIOCapabilities();
    }

    public DisplayCapabilities createDisplayCapabilities() {
        return new DisplayCapabilities();
    }

    public RecordingCapabilities createRecordingCapabilities() {
        return new RecordingCapabilities();
    }

    public SearchCapabilities createSearchCapabilities() {
        return new SearchCapabilities();
    }

    public ReplayCapabilities createReplayCapabilities() {
        return new ReplayCapabilities();
    }

    public ReceiverCapabilities createReceiverCapabilities() {
        return new ReceiverCapabilities();
    }

    public AnalyticsDeviceCapabilities createAnalyticsDeviceCapabilities() {
        return new AnalyticsDeviceCapabilities();
    }

    public AnalyticsDeviceExtension createAnalyticsDeviceExtension() {
        return new AnalyticsDeviceExtension();
    }

    public SystemLog createSystemLog() {
        return new SystemLog();
    }

    public SupportInformation createSupportInformation() {
        return new SupportInformation();
    }

    public BinaryData createBinaryData() {
        return new BinaryData();
    }

    public AttachmentData createAttachmentData() {
        return new AttachmentData();
    }

    public BackupFile createBackupFile() {
        return new BackupFile();
    }

    public SystemLogUriList createSystemLogUriList() {
        return new SystemLogUriList();
    }

    public SystemLogUri createSystemLogUri() {
        return new SystemLogUri();
    }

    public SystemDateTime createSystemDateTime() {
        return new SystemDateTime();
    }

    public SystemDateTimeExtension createSystemDateTimeExtension() {
        return new SystemDateTimeExtension();
    }

    public DateTime createDateTime() {
        return new DateTime();
    }

    public Date createDate() {
        return new Date();
    }

    public Time createTime() {
        return new Time();
    }

    public TimeZone createTimeZone() {
        return new TimeZone();
    }

    public RemoteUser createRemoteUser() {
        return new RemoteUser();
    }

    public User createUser() {
        return new User();
    }

    public UserExtension createUserExtension() {
        return new UserExtension();
    }

    public CertificateGenerationParameters createCertificateGenerationParameters() {
        return new CertificateGenerationParameters();
    }

    public CertificateGenerationParametersExtension createCertificateGenerationParametersExtension() {
        return new CertificateGenerationParametersExtension();
    }

    public Certificate createCertificate() {
        return new Certificate();
    }

    public CertificateStatus createCertificateStatus() {
        return new CertificateStatus();
    }

    public CertificateWithPrivateKey createCertificateWithPrivateKey() {
        return new CertificateWithPrivateKey();
    }

    public CertificateInformation createCertificateInformation() {
        return new CertificateInformation();
    }

    public CertificateUsage createCertificateUsage() {
        return new CertificateUsage();
    }

    public CertificateInformationExtension createCertificateInformationExtension() {
        return new CertificateInformationExtension();
    }

    public Dot1XConfiguration createDot1XConfiguration() {
        return new Dot1XConfiguration();
    }

    public Dot1XConfigurationExtension createDot1XConfigurationExtension() {
        return new Dot1XConfigurationExtension();
    }

    public EAPMethodConfiguration createEAPMethodConfiguration() {
        return new EAPMethodConfiguration();
    }

    public EapMethodExtension createEapMethodExtension() {
        return new EapMethodExtension();
    }

    public TLSConfiguration createTLSConfiguration() {
        return new TLSConfiguration();
    }

    public GenericEapPwdConfigurationExtension createGenericEapPwdConfigurationExtension() {
        return new GenericEapPwdConfigurationExtension();
    }

    public RelayOutputSettings createRelayOutputSettings() {
        return new RelayOutputSettings();
    }

    public RelayOutput createRelayOutput() {
        return new RelayOutput();
    }

    public DigitalInput createDigitalInput() {
        return new DigitalInput();
    }

    public PTZNode createPTZNode() {
        return new PTZNode();
    }

    public PTZNodeExtension createPTZNodeExtension() {
        return new PTZNodeExtension();
    }

    public PTZNodeExtension2 createPTZNodeExtension2() {
        return new PTZNodeExtension2();
    }

    public PTZPresetTourSupported createPTZPresetTourSupported() {
        return new PTZPresetTourSupported();
    }

    public PTZPresetTourSupportedExtension createPTZPresetTourSupportedExtension() {
        return new PTZPresetTourSupportedExtension();
    }

    public PTZConfigurationExtension createPTZConfigurationExtension() {
        return new PTZConfigurationExtension();
    }

    public PTZConfigurationExtension2 createPTZConfigurationExtension2() {
        return new PTZConfigurationExtension2();
    }

    public PTControlDirection createPTControlDirection() {
        return new PTControlDirection();
    }

    public PTControlDirectionExtension createPTControlDirectionExtension() {
        return new PTControlDirectionExtension();
    }

    public EFlip createEFlip() {
        return new EFlip();
    }

    public Reverse createReverse() {
        return new Reverse();
    }

    public PTZConfigurationOptions createPTZConfigurationOptions() {
        return new PTZConfigurationOptions();
    }

    public PTZConfigurationOptions2 createPTZConfigurationOptions2() {
        return new PTZConfigurationOptions2();
    }

    public PTControlDirectionOptions createPTControlDirectionOptions() {
        return new PTControlDirectionOptions();
    }

    public PTControlDirectionOptionsExtension createPTControlDirectionOptionsExtension() {
        return new PTControlDirectionOptionsExtension();
    }

    public EFlipOptions createEFlipOptions() {
        return new EFlipOptions();
    }

    public EFlipOptionsExtension createEFlipOptionsExtension() {
        return new EFlipOptionsExtension();
    }

    public ReverseOptions createReverseOptions() {
        return new ReverseOptions();
    }

    public ReverseOptionsExtension createReverseOptionsExtension() {
        return new ReverseOptionsExtension();
    }

    public PanTiltLimits createPanTiltLimits() {
        return new PanTiltLimits();
    }

    public ZoomLimits createZoomLimits() {
        return new ZoomLimits();
    }

    public PTZSpaces createPTZSpaces() {
        return new PTZSpaces();
    }

    public PTZSpacesExtension createPTZSpacesExtension() {
        return new PTZSpacesExtension();
    }

    public Space2DDescription createSpace2DDescription() {
        return new Space2DDescription();
    }

    public Space1DDescription createSpace1DDescription() {
        return new Space1DDescription();
    }

    public Vector2D createVector2D() {
        return new Vector2D();
    }

    public Vector1D createVector1D() {
        return new Vector1D();
    }

    public PTZVector createPTZVector() {
        return new PTZVector();
    }

    public PTZSpeed createPTZSpeed() {
        return new PTZSpeed();
    }

    public PTZStatus createPTZStatus() {
        return new PTZStatus();
    }

    public PTZPreset createPTZPreset() {
        return new PTZPreset();
    }

    public PTZMoveStatus createPTZMoveStatus() {
        return new PTZMoveStatus();
    }

    public PresetTour createPresetTour() {
        return new PresetTour();
    }

    public PTZPresetTourExtension createPTZPresetTourExtension() {
        return new PTZPresetTourExtension();
    }

    public PTZPresetTourSpot createPTZPresetTourSpot() {
        return new PTZPresetTourSpot();
    }

    public PTZPresetTourSpotExtension createPTZPresetTourSpotExtension() {
        return new PTZPresetTourSpotExtension();
    }

    public PTZPresetTourPresetDetail createPTZPresetTourPresetDetail() {
        return new PTZPresetTourPresetDetail();
    }

    public PTZPresetTourTypeExtension createPTZPresetTourTypeExtension() {
        return new PTZPresetTourTypeExtension();
    }

    public PTZPresetTourStatus createPTZPresetTourStatus() {
        return new PTZPresetTourStatus();
    }

    public PTZPresetTourStatusExtension createPTZPresetTourStatusExtension() {
        return new PTZPresetTourStatusExtension();
    }

    public PTZPresetTourStartingCondition createPTZPresetTourStartingCondition() {
        return new PTZPresetTourStartingCondition();
    }

    public PTZPresetTourStartingConditionExtension createPTZPresetTourStartingConditionExtension() {
        return new PTZPresetTourStartingConditionExtension();
    }

    public PTZPresetTourOptions createPTZPresetTourOptions() {
        return new PTZPresetTourOptions();
    }

    public PTZPresetTourSpotOptions createPTZPresetTourSpotOptions() {
        return new PTZPresetTourSpotOptions();
    }

    public PTZPresetTourPresetDetailOptions createPTZPresetTourPresetDetailOptions() {
        return new PTZPresetTourPresetDetailOptions();
    }

    public PTZPresetTourPresetDetailOptionsExtension createPTZPresetTourPresetDetailOptionsExtension() {
        return new PTZPresetTourPresetDetailOptionsExtension();
    }

    public PTZPresetTourStartingConditionOptions createPTZPresetTourStartingConditionOptions() {
        return new PTZPresetTourStartingConditionOptions();
    }

    public PTZPresetTourStartingConditionOptionsExtension createPTZPresetTourStartingConditionOptionsExtension() {
        return new PTZPresetTourStartingConditionOptionsExtension();
    }

    public ImagingStatus createImagingStatus() {
        return new ImagingStatus();
    }

    public FocusStatus createFocusStatus() {
        return new FocusStatus();
    }

    public FocusConfiguration createFocusConfiguration() {
        return new FocusConfiguration();
    }

    public ImagingSettings createImagingSettings() {
        return new ImagingSettings();
    }

    public ImagingSettingsExtension createImagingSettingsExtension() {
        return new ImagingSettingsExtension();
    }

    public Exposure createExposure() {
        return new Exposure();
    }

    public WideDynamicRange createWideDynamicRange() {
        return new WideDynamicRange();
    }

    public BacklightCompensation createBacklightCompensation() {
        return new BacklightCompensation();
    }

    public ImagingOptions createImagingOptions() {
        return new ImagingOptions();
    }

    public WideDynamicRangeOptions createWideDynamicRangeOptions() {
        return new WideDynamicRangeOptions();
    }

    public BacklightCompensationOptions createBacklightCompensationOptions() {
        return new BacklightCompensationOptions();
    }

    public FocusOptions createFocusOptions() {
        return new FocusOptions();
    }

    public ExposureOptions createExposureOptions() {
        return new ExposureOptions();
    }

    public WhiteBalanceOptions createWhiteBalanceOptions() {
        return new WhiteBalanceOptions();
    }

    public FocusMove createFocusMove() {
        return new FocusMove();
    }

    public AbsoluteFocus createAbsoluteFocus() {
        return new AbsoluteFocus();
    }

    public RelativeFocus createRelativeFocus() {
        return new RelativeFocus();
    }

    public ContinuousFocus createContinuousFocus() {
        return new ContinuousFocus();
    }

    public MoveOptions createMoveOptions() {
        return new MoveOptions();
    }

    public AbsoluteFocusOptions createAbsoluteFocusOptions() {
        return new AbsoluteFocusOptions();
    }

    public RelativeFocusOptions createRelativeFocusOptions() {
        return new RelativeFocusOptions();
    }

    public ContinuousFocusOptions createContinuousFocusOptions() {
        return new ContinuousFocusOptions();
    }

    public WhiteBalance createWhiteBalance() {
        return new WhiteBalance();
    }

    public ImagingStatus20 createImagingStatus20() {
        return new ImagingStatus20();
    }

    public ImagingStatus20Extension createImagingStatus20Extension() {
        return new ImagingStatus20Extension();
    }

    public FocusStatus20 createFocusStatus20() {
        return new FocusStatus20();
    }

    public FocusStatus20Extension createFocusStatus20Extension() {
        return new FocusStatus20Extension();
    }

    public ImagingSettings20 createImagingSettings20() {
        return new ImagingSettings20();
    }

    public ImagingSettingsExtension20 createImagingSettingsExtension20() {
        return new ImagingSettingsExtension20();
    }

    public ImagingSettingsExtension202 createImagingSettingsExtension202() {
        return new ImagingSettingsExtension202();
    }

    public ImagingSettingsExtension203 createImagingSettingsExtension203() {
        return new ImagingSettingsExtension203();
    }

    public ImagingSettingsExtension204 createImagingSettingsExtension204() {
        return new ImagingSettingsExtension204();
    }

    public ImageStabilization createImageStabilization() {
        return new ImageStabilization();
    }

    public ImageStabilizationExtension createImageStabilizationExtension() {
        return new ImageStabilizationExtension();
    }

    public IrCutFilterAutoAdjustment createIrCutFilterAutoAdjustment() {
        return new IrCutFilterAutoAdjustment();
    }

    public IrCutFilterAutoAdjustmentExtension createIrCutFilterAutoAdjustmentExtension() {
        return new IrCutFilterAutoAdjustmentExtension();
    }

    public WideDynamicRange20 createWideDynamicRange20() {
        return new WideDynamicRange20();
    }

    public BacklightCompensation20 createBacklightCompensation20() {
        return new BacklightCompensation20();
    }

    public Exposure20 createExposure20() {
        return new Exposure20();
    }

    public ToneCompensation createToneCompensation() {
        return new ToneCompensation();
    }

    public ToneCompensationExtension createToneCompensationExtension() {
        return new ToneCompensationExtension();
    }

    public Defogging createDefogging() {
        return new Defogging();
    }

    public DefoggingExtension createDefoggingExtension() {
        return new DefoggingExtension();
    }

    public NoiseReduction createNoiseReduction() {
        return new NoiseReduction();
    }

    public ImagingOptions20 createImagingOptions20() {
        return new ImagingOptions20();
    }

    public ImagingOptions20Extension createImagingOptions20Extension() {
        return new ImagingOptions20Extension();
    }

    public ImagingOptions20Extension2 createImagingOptions20Extension2() {
        return new ImagingOptions20Extension2();
    }

    public ImagingOptions20Extension3 createImagingOptions20Extension3() {
        return new ImagingOptions20Extension3();
    }

    public ImagingOptions20Extension4 createImagingOptions20Extension4() {
        return new ImagingOptions20Extension4();
    }

    public ImageStabilizationOptions createImageStabilizationOptions() {
        return new ImageStabilizationOptions();
    }

    public ImageStabilizationOptionsExtension createImageStabilizationOptionsExtension() {
        return new ImageStabilizationOptionsExtension();
    }

    public IrCutFilterAutoAdjustmentOptions createIrCutFilterAutoAdjustmentOptions() {
        return new IrCutFilterAutoAdjustmentOptions();
    }

    public IrCutFilterAutoAdjustmentOptionsExtension createIrCutFilterAutoAdjustmentOptionsExtension() {
        return new IrCutFilterAutoAdjustmentOptionsExtension();
    }

    public WideDynamicRangeOptions20 createWideDynamicRangeOptions20() {
        return new WideDynamicRangeOptions20();
    }

    public BacklightCompensationOptions20 createBacklightCompensationOptions20() {
        return new BacklightCompensationOptions20();
    }

    public ExposureOptions20 createExposureOptions20() {
        return new ExposureOptions20();
    }

    public MoveOptions20 createMoveOptions20() {
        return new MoveOptions20();
    }

    public RelativeFocusOptions20 createRelativeFocusOptions20() {
        return new RelativeFocusOptions20();
    }

    public WhiteBalance20 createWhiteBalance20() {
        return new WhiteBalance20();
    }

    public WhiteBalance20Extension createWhiteBalance20Extension() {
        return new WhiteBalance20Extension();
    }

    public FocusConfiguration20 createFocusConfiguration20() {
        return new FocusConfiguration20();
    }

    public FocusConfiguration20Extension createFocusConfiguration20Extension() {
        return new FocusConfiguration20Extension();
    }

    public WhiteBalanceOptions20 createWhiteBalanceOptions20() {
        return new WhiteBalanceOptions20();
    }

    public WhiteBalanceOptions20Extension createWhiteBalanceOptions20Extension() {
        return new WhiteBalanceOptions20Extension();
    }

    public FocusOptions20 createFocusOptions20() {
        return new FocusOptions20();
    }

    public FocusOptions20Extension createFocusOptions20Extension() {
        return new FocusOptions20Extension();
    }

    public ToneCompensationOptions createToneCompensationOptions() {
        return new ToneCompensationOptions();
    }

    public DefoggingOptions createDefoggingOptions() {
        return new DefoggingOptions();
    }

    public NoiseReductionOptions createNoiseReductionOptions() {
        return new NoiseReductionOptions();
    }

    public ItemListExtension createItemListExtension() {
        return new ItemListExtension();
    }

    public MessageDescription createMessageDescription() {
        return new MessageDescription();
    }

    public MessageDescriptionExtension createMessageDescriptionExtension() {
        return new MessageDescriptionExtension();
    }

    public ItemListDescriptionExtension createItemListDescriptionExtension() {
        return new ItemListDescriptionExtension();
    }

    public Vector createVector() {
        return new Vector();
    }

    public Rectangle createRectangle() {
        return new Rectangle();
    }

    public Color createColor() {
        return new Color();
    }

    public ColorCovariance createColorCovariance() {
        return new ColorCovariance();
    }

    public Appearance createAppearance() {
        return new Appearance();
    }

    public AppearanceExtension createAppearanceExtension() {
        return new AppearanceExtension();
    }

    public ShapeDescriptor createShapeDescriptor() {
        return new ShapeDescriptor();
    }

    public ShapeDescriptorExtension createShapeDescriptorExtension() {
        return new ShapeDescriptorExtension();
    }

    public ColorDescriptorExtension createColorDescriptorExtension() {
        return new ColorDescriptorExtension();
    }

    public ClassDescriptorExtension createClassDescriptorExtension() {
        return new ClassDescriptorExtension();
    }

    public ClassDescriptorExtension2 createClassDescriptorExtension2() {
        return new ClassDescriptorExtension2();
    }

    public OtherType createOtherType() {
        return new OtherType();
    }

    public Object createObject() {
        return new Object();
    }

    public ObjectExtension createObjectExtension() {
        return new ObjectExtension();
    }

    public Transformation createTransformation() {
        return new Transformation();
    }

    public TransformationExtension createTransformationExtension() {
        return new TransformationExtension();
    }

    public Frame createFrame() {
        return new Frame();
    }

    public FrameExtension createFrameExtension() {
        return new FrameExtension();
    }

    public FrameExtension2 createFrameExtension2() {
        return new FrameExtension2();
    }

    public Merge createMerge() {
        return new Merge();
    }

    public Split createSplit() {
        return new Split();
    }

    public Rename createRename() {
        return new Rename();
    }

    public ObjectId createObjectId() {
        return new ObjectId();
    }

    public BehaviourExtension createBehaviourExtension() {
        return new BehaviourExtension();
    }

    public ObjectTree createObjectTree() {
        return new ObjectTree();
    }

    public ObjectTreeExtension createObjectTreeExtension() {
        return new ObjectTreeExtension();
    }

    public MotionInCells createMotionInCells() {
        return new MotionInCells();
    }

    public AnalyticsEngineConfiguration createAnalyticsEngineConfiguration() {
        return new AnalyticsEngineConfiguration();
    }

    public AnalyticsEngineConfigurationExtension createAnalyticsEngineConfigurationExtension() {
        return new AnalyticsEngineConfigurationExtension();
    }

    public RuleEngineConfiguration createRuleEngineConfiguration() {
        return new RuleEngineConfiguration();
    }

    public RuleEngineConfigurationExtension createRuleEngineConfigurationExtension() {
        return new RuleEngineConfigurationExtension();
    }

    public Config createConfig() {
        return new Config();
    }

    public ConfigDescriptionExtension createConfigDescriptionExtension() {
        return new ConfigDescriptionExtension();
    }

    public SupportedRules createSupportedRules() {
        return new SupportedRules();
    }

    public SupportedRulesExtension createSupportedRulesExtension() {
        return new SupportedRulesExtension();
    }

    public SupportedAnalyticsModules createSupportedAnalyticsModules() {
        return new SupportedAnalyticsModules();
    }

    public SupportedAnalyticsModulesExtension createSupportedAnalyticsModulesExtension() {
        return new SupportedAnalyticsModulesExtension();
    }

    public PolygonConfiguration createPolygonConfiguration() {
        return new PolygonConfiguration();
    }

    public PolylineArray createPolylineArray() {
        return new PolylineArray();
    }

    public PolylineArrayExtension createPolylineArrayExtension() {
        return new PolylineArrayExtension();
    }

    public PolylineArrayConfiguration createPolylineArrayConfiguration() {
        return new PolylineArrayConfiguration();
    }

    public MotionExpression createMotionExpression() {
        return new MotionExpression();
    }

    public MotionExpressionConfiguration createMotionExpressionConfiguration() {
        return new MotionExpressionConfiguration();
    }

    public CellLayout createCellLayout() {
        return new CellLayout();
    }

    public MetadataStreamExtension createMetadataStreamExtension() {
        return new MetadataStreamExtension();
    }

    public MetadataStreamExtension2 createMetadataStreamExtension2() {
        return new MetadataStreamExtension2();
    }

    public AudioAnalyticsStream createAudioAnalyticsStream() {
        return new AudioAnalyticsStream();
    }

    public AudioDescriptor createAudioDescriptor() {
        return new AudioDescriptor();
    }

    public AudioAnalyticsStreamExtension createAudioAnalyticsStreamExtension() {
        return new AudioAnalyticsStreamExtension();
    }

    public VideoAnalyticsStream createVideoAnalyticsStream() {
        return new VideoAnalyticsStream();
    }

    public VideoAnalyticsStreamExtension createVideoAnalyticsStreamExtension() {
        return new VideoAnalyticsStreamExtension();
    }

    public PTZStream createPTZStream() {
        return new PTZStream();
    }

    public PTZStreamExtension createPTZStreamExtension() {
        return new PTZStreamExtension();
    }

    public EventStream createEventStream() {
        return new EventStream();
    }

    public EventStreamExtension createEventStreamExtension() {
        return new EventStreamExtension();
    }

    public PaneConfiguration createPaneConfiguration() {
        return new PaneConfiguration();
    }

    public PaneLayout createPaneLayout() {
        return new PaneLayout();
    }

    public Layout createLayout() {
        return new Layout();
    }

    public LayoutExtension createLayoutExtension() {
        return new LayoutExtension();
    }

    public CodingCapabilities createCodingCapabilities() {
        return new CodingCapabilities();
    }

    public LayoutOptions createLayoutOptions() {
        return new LayoutOptions();
    }

    public LayoutOptionsExtension createLayoutOptionsExtension() {
        return new LayoutOptionsExtension();
    }

    public PaneLayoutOptions createPaneLayoutOptions() {
        return new PaneLayoutOptions();
    }

    public PaneOptionExtension createPaneOptionExtension() {
        return new PaneOptionExtension();
    }

    public Receiver createReceiver() {
        return new Receiver();
    }

    public ReceiverConfiguration createReceiverConfiguration() {
        return new ReceiverConfiguration();
    }

    public ReceiverStateInformation createReceiverStateInformation() {
        return new ReceiverStateInformation();
    }

    public SourceReference createSourceReference() {
        return new SourceReference();
    }

    public DateTimeRange createDateTimeRange() {
        return new DateTimeRange();
    }

    public RecordingSummary createRecordingSummary() {
        return new RecordingSummary();
    }

    public SearchScope createSearchScope() {
        return new SearchScope();
    }

    public SearchScopeExtension createSearchScopeExtension() {
        return new SearchScopeExtension();
    }

    public EventFilter createEventFilter() {
        return new EventFilter();
    }

    public PTZPositionFilter createPTZPositionFilter() {
        return new PTZPositionFilter();
    }

    public MetadataFilter createMetadataFilter() {
        return new MetadataFilter();
    }

    public FindRecordingResultList createFindRecordingResultList() {
        return new FindRecordingResultList();
    }

    public FindEventResultList createFindEventResultList() {
        return new FindEventResultList();
    }

    public FindEventResult createFindEventResult() {
        return new FindEventResult();
    }

    public FindPTZPositionResultList createFindPTZPositionResultList() {
        return new FindPTZPositionResultList();
    }

    public FindPTZPositionResult createFindPTZPositionResult() {
        return new FindPTZPositionResult();
    }

    public FindMetadataResultList createFindMetadataResultList() {
        return new FindMetadataResultList();
    }

    public FindMetadataResult createFindMetadataResult() {
        return new FindMetadataResult();
    }

    public RecordingInformation createRecordingInformation() {
        return new RecordingInformation();
    }

    public RecordingSourceInformation createRecordingSourceInformation() {
        return new RecordingSourceInformation();
    }

    public TrackInformation createTrackInformation() {
        return new TrackInformation();
    }

    public MediaAttributes createMediaAttributes() {
        return new MediaAttributes();
    }

    public TrackAttributes createTrackAttributes() {
        return new TrackAttributes();
    }

    public TrackAttributesExtension createTrackAttributesExtension() {
        return new TrackAttributesExtension();
    }

    public VideoAttributes createVideoAttributes() {
        return new VideoAttributes();
    }

    public AudioAttributes createAudioAttributes() {
        return new AudioAttributes();
    }

    public MetadataAttributes createMetadataAttributes() {
        return new MetadataAttributes();
    }

    public RecordingConfiguration createRecordingConfiguration() {
        return new RecordingConfiguration();
    }

    public TrackConfiguration createTrackConfiguration() {
        return new TrackConfiguration();
    }

    public GetRecordingsResponseItem createGetRecordingsResponseItem() {
        return new GetRecordingsResponseItem();
    }

    public GetTracksResponseList createGetTracksResponseList() {
        return new GetTracksResponseList();
    }

    public GetTracksResponseItem createGetTracksResponseItem() {
        return new GetTracksResponseItem();
    }

    public RecordingJobConfiguration createRecordingJobConfiguration() {
        return new RecordingJobConfiguration();
    }

    public RecordingJobConfigurationExtension createRecordingJobConfigurationExtension() {
        return new RecordingJobConfigurationExtension();
    }

    public RecordingJobSource createRecordingJobSource() {
        return new RecordingJobSource();
    }

    public RecordingJobSourceExtension createRecordingJobSourceExtension() {
        return new RecordingJobSourceExtension();
    }

    public RecordingJobTrack createRecordingJobTrack() {
        return new RecordingJobTrack();
    }

    public RecordingJobStateInformation createRecordingJobStateInformation() {
        return new RecordingJobStateInformation();
    }

    public RecordingJobStateInformationExtension createRecordingJobStateInformationExtension() {
        return new RecordingJobStateInformationExtension();
    }

    public RecordingJobStateSource createRecordingJobStateSource() {
        return new RecordingJobStateSource();
    }

    public RecordingJobStateTracks createRecordingJobStateTracks() {
        return new RecordingJobStateTracks();
    }

    public RecordingJobStateTrack createRecordingJobStateTrack() {
        return new RecordingJobStateTrack();
    }

    public GetRecordingJobsResponseItem createGetRecordingJobsResponseItem() {
        return new GetRecordingJobsResponseItem();
    }

    public ReplayConfiguration createReplayConfiguration() {
        return new ReplayConfiguration();
    }

    public AnalyticsEngine createAnalyticsEngine() {
        return new AnalyticsEngine();
    }

    public AnalyticsDeviceEngineConfiguration createAnalyticsDeviceEngineConfiguration() {
        return new AnalyticsDeviceEngineConfiguration();
    }

    public AnalyticsDeviceEngineConfigurationExtension createAnalyticsDeviceEngineConfigurationExtension() {
        return new AnalyticsDeviceEngineConfigurationExtension();
    }

    public EngineConfiguration createEngineConfiguration() {
        return new EngineConfiguration();
    }

    public AnalyticsEngineInputInfo createAnalyticsEngineInputInfo() {
        return new AnalyticsEngineInputInfo();
    }

    public AnalyticsEngineInputInfoExtension createAnalyticsEngineInputInfoExtension() {
        return new AnalyticsEngineInputInfoExtension();
    }

    public AnalyticsEngineInput createAnalyticsEngineInput() {
        return new AnalyticsEngineInput();
    }

    public SourceIdentification createSourceIdentification() {
        return new SourceIdentification();
    }

    public SourceIdentificationExtension createSourceIdentificationExtension() {
        return new SourceIdentificationExtension();
    }

    public MetadataInput createMetadataInput() {
        return new MetadataInput();
    }

    public MetadataInputExtension createMetadataInputExtension() {
        return new MetadataInputExtension();
    }

    public AnalyticsEngineControl createAnalyticsEngineControl() {
        return new AnalyticsEngineControl();
    }

    public AnalyticsStateInformation createAnalyticsStateInformation() {
        return new AnalyticsStateInformation();
    }

    public AnalyticsState createAnalyticsState() {
        return new AnalyticsState();
    }

    public ActionEngineEventPayload createActionEngineEventPayload() {
        return new ActionEngineEventPayload();
    }

    public ActionEngineEventPayloadExtension createActionEngineEventPayloadExtension() {
        return new ActionEngineEventPayloadExtension();
    }

    public AudioClassCandidate createAudioClassCandidate() {
        return new AudioClassCandidate();
    }

    public AudioClassDescriptor createAudioClassDescriptor() {
        return new AudioClassDescriptor();
    }

    public AudioClassDescriptorExtension createAudioClassDescriptorExtension() {
        return new AudioClassDescriptorExtension();
    }

    public OSDReference createOSDReference() {
        return new OSDReference();
    }

    public OSDPosConfiguration createOSDPosConfiguration() {
        return new OSDPosConfiguration();
    }

    public OSDPosConfigurationExtension createOSDPosConfigurationExtension() {
        return new OSDPosConfigurationExtension();
    }

    public OSDColor createOSDColor() {
        return new OSDColor();
    }

    public OSDTextConfiguration createOSDTextConfiguration() {
        return new OSDTextConfiguration();
    }

    public OSDTextConfigurationExtension createOSDTextConfigurationExtension() {
        return new OSDTextConfigurationExtension();
    }

    public OSDImgConfiguration createOSDImgConfiguration() {
        return new OSDImgConfiguration();
    }

    public OSDImgConfigurationExtension createOSDImgConfigurationExtension() {
        return new OSDImgConfigurationExtension();
    }

    public ColorspaceRange createColorspaceRange() {
        return new ColorspaceRange();
    }

    public ColorOptions createColorOptions() {
        return new ColorOptions();
    }

    public OSDColorOptions createOSDColorOptions() {
        return new OSDColorOptions();
    }

    public OSDColorOptionsExtension createOSDColorOptionsExtension() {
        return new OSDColorOptionsExtension();
    }

    public OSDTextOptions createOSDTextOptions() {
        return new OSDTextOptions();
    }

    public OSDTextOptionsExtension createOSDTextOptionsExtension() {
        return new OSDTextOptionsExtension();
    }

    public OSDImgOptions createOSDImgOptions() {
        return new OSDImgOptions();
    }

    public OSDImgOptionsExtension createOSDImgOptionsExtension() {
        return new OSDImgOptionsExtension();
    }

    public OSDConfiguration createOSDConfiguration() {
        return new OSDConfiguration();
    }

    public OSDConfigurationExtension createOSDConfigurationExtension() {
        return new OSDConfigurationExtension();
    }

    public MaximumNumberOfOSDs createMaximumNumberOfOSDs() {
        return new MaximumNumberOfOSDs();
    }

    public OSDConfigurationOptions createOSDConfigurationOptions() {
        return new OSDConfigurationOptions();
    }

    public OSDConfigurationOptionsExtension createOSDConfigurationOptionsExtension() {
        return new OSDConfigurationOptionsExtension();
    }

    public FileProgress createFileProgress() {
        return new FileProgress();
    }

    public ArrayOfFileProgress createArrayOfFileProgress() {
        return new ArrayOfFileProgress();
    }

    public ArrayOfFileProgressExtension createArrayOfFileProgressExtension() {
        return new ArrayOfFileProgressExtension();
    }

    public StorageReferencePath createStorageReferencePath() {
        return new StorageReferencePath();
    }

    public StorageReferencePathExtension createStorageReferencePathExtension() {
        return new StorageReferencePathExtension();
    }

    public ConfigDescription.Messages createConfigDescriptionMessages() {
        return new ConfigDescription.Messages();
    }

    public Behaviour.Removed createBehaviourRemoved() {
        return new Behaviour.Removed();
    }

    public Behaviour.Idle createBehaviourIdle() {
        return new Behaviour.Idle();
    }

    public ClassDescriptor.ClassCandidate createClassDescriptorClassCandidate() {
        return new ClassDescriptor.ClassCandidate();
    }

    public ColorDescriptor.ColorCluster createColorDescriptorColorCluster() {
        return new ColorDescriptor.ColorCluster();
    }

    public ItemListDescription.SimpleItemDescription createItemListDescriptionSimpleItemDescription() {
        return new ItemListDescription.SimpleItemDescription();
    }

    public ItemListDescription.ElementItemDescription createItemListDescriptionElementItemDescription() {
        return new ItemListDescription.ElementItemDescription();
    }

    public EventSubscription.SubscriptionPolicy createEventSubscriptionSubscriptionPolicy() {
        return new EventSubscription.SubscriptionPolicy();
    }

    public ItemList.SimpleItem createItemListSimpleItem() {
        return new ItemList.SimpleItem();
    }

    public ItemList.ElementItem createItemListElementItem() {
        return new ItemList.ElementItem();
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/schema", name="VideoSourceConfiguration")
    public JAXBElement<VideoSourceConfiguration> createVideoSourceConfiguration(VideoSourceConfiguration value) {
        return new JAXBElement<VideoSourceConfiguration>(_VideoSourceConfiguration_QNAME, VideoSourceConfiguration.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/schema", name="AudioSourceConfiguration")
    public JAXBElement<AudioSourceConfiguration> createAudioSourceConfiguration(AudioSourceConfiguration value) {
        return new JAXBElement<AudioSourceConfiguration>(_AudioSourceConfiguration_QNAME, AudioSourceConfiguration.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/schema", name="VideoEncoderConfiguration")
    public JAXBElement<VideoEncoderConfiguration> createVideoEncoderConfiguration(VideoEncoderConfiguration value) {
        return new JAXBElement<VideoEncoderConfiguration>(_VideoEncoderConfiguration_QNAME, VideoEncoderConfiguration.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/schema", name="AudioEncoderConfiguration")
    public JAXBElement<AudioEncoderConfiguration> createAudioEncoderConfiguration(AudioEncoderConfiguration value) {
        return new JAXBElement<AudioEncoderConfiguration>(_AudioEncoderConfiguration_QNAME, AudioEncoderConfiguration.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/schema", name="VideoAnalyticsConfiguration")
    public JAXBElement<VideoAnalyticsConfiguration> createVideoAnalyticsConfiguration(VideoAnalyticsConfiguration value) {
        return new JAXBElement<VideoAnalyticsConfiguration>(_VideoAnalyticsConfiguration_QNAME, VideoAnalyticsConfiguration.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/schema", name="PTZConfiguration")
    public JAXBElement<PTZConfiguration> createPTZConfiguration(PTZConfiguration value) {
        return new JAXBElement<PTZConfiguration>(_PTZConfiguration_QNAME, PTZConfiguration.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/schema", name="MetadataConfiguration")
    public JAXBElement<MetadataConfiguration> createMetadataConfiguration(MetadataConfiguration value) {
        return new JAXBElement<MetadataConfiguration>(_MetadataConfiguration_QNAME, MetadataConfiguration.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/schema", name="AudioOutputConfiguration")
    public JAXBElement<AudioOutputConfiguration> createAudioOutputConfiguration(AudioOutputConfiguration value) {
        return new JAXBElement<AudioOutputConfiguration>(_AudioOutputConfiguration_QNAME, AudioOutputConfiguration.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/schema", name="AudioDecoderConfiguration")
    public JAXBElement<AudioDecoderConfiguration> createAudioDecoderConfiguration(AudioDecoderConfiguration value) {
        return new JAXBElement<AudioDecoderConfiguration>(_AudioDecoderConfiguration_QNAME, AudioDecoderConfiguration.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/schema", name="Polygon")
    public JAXBElement<Polygon> createPolygon(Polygon value) {
        return new JAXBElement<Polygon>(_Polygon_QNAME, Polygon.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/schema", name="Polyline")
    public JAXBElement<Polyline> createPolyline(Polyline value) {
        return new JAXBElement<Polyline>(_Polyline_QNAME, Polyline.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/schema", name="MetadataStream")
    public JAXBElement<MetadataStream> createMetadataStream(MetadataStream value) {
        return new JAXBElement<MetadataStream>(_MetadataStream_QNAME, MetadataStream.class, null, value);
    }
}

