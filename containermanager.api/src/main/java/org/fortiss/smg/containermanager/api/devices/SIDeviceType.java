package org.fortiss.smg.containermanager.api.devices;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SIDeviceType {
	ConsumptionPowermeter(SIUnitType.W), 
	
	ProductionPowermeter(SIUnitType.W), 
	FeedPowerMeter(SIUnitType.W), ConsumptionVoltmeter(SIUnitType.V), 
	ProductionVoltmeter(SIUnitType.V), FeedVoltmeter(SIUnitType.V), 
	FeedAmperemeter(SIUnitType.A), ConsumptionAmperemeter(SIUnitType.A), 
	ProductionAmperemeter(SIUnitType.A), 
	FeedVoltAmperemeter(SIUnitType.VA), ConsumptionVoltAmperemeter(SIUnitType.VA), 
	ProductionVoltAmperemeter(SIUnitType.VA),
	ConsumptionPowermeterAggregated(SIUnitType.WH), 
	ProductionPowermeterAggregated(SIUnitType.WH), FeedPowerMeterAggregated(SIUnitType.WH), 
	//Light(SIUnitType.LUX), same as brightness (Google uses: light) 
	Brightness(SIUnitType.LUX),  
	LightSimple(SIUnitType.BOOL),
	LightDimmable(SIUnitType.PERCENT),
	Temperature(SIUnitType.CELSIUS), 
	Balance(SIUnitType.KG),	
	Powerplug(SIUnitType.BOOL), 
	Heating(SIUnitType.PERCENT), 
	Cooling(SIUnitType.PERCENT), 
	Blinds(SIUnitType.PERCENT), 
	Window(SIUnitType.BOOL), 
	Door(SIUnitType.BOOL), 
	Occupancy(SIUnitType.BOOL), 
	Humidity(SIUnitType.PERCENT), 
	Noise(SIUnitType.DB), 
	Pressure(SIUnitType.HPA), 
	Battery(SIUnitType.PERCENT), 
	Calculator(SIUnitType.NONE),
	Frequency(SIUnitType.HZ),
	Accelerometer(SIUnitType.NONE),
	Acceleration(SIUnitType.NONE),
	Proximity(SIUnitType.NONE),
	Gravity(SIUnitType.NONE),
	MagneticField(SIUnitType.NONE),
	MagneticFieldUncalibrated(SIUnitType.NONE),
	SignificantMotion(SIUnitType.BOOL),
	Switch(SIUnitType.SWITCHPOSITION),
	GameRotationVector(SIUnitType.NONE),
	GeomagneticRotationVector(SIUnitType.NONE),
	RotationVector(SIUnitType.NONE),
	StepCounter(SIUnitType.Count),
	StepDetector(SIUnitType.BOOL),
	
	/* added for stabilize */
	
	/* First Grid meter of a Node */
	GM_1_RMSVoltage_L1(SIUnitType.V),
	GM_1_RMSVoltage_L2(SIUnitType.V),
	GM_1_RMSVoltage_L3(SIUnitType.V),
	
	GM_1_RMSCurrent_L1(SIUnitType.V),
	GM_1_RMSCurrent_L2(SIUnitType.V),
	GM_1_RMSCurrent_L3(SIUnitType.V),
	
	GM_1_ActivePower_L1(SIUnitType.W),
	GM_1_ActivePower_L2(SIUnitType.W),
	GM_1_ActivePower_L3(SIUnitType.W),
	
	GM_1_ApparentPower_L1(SIUnitType.VA),
	GM_1_ApparentPower_L2(SIUnitType.VA),
	GM_1_ApparentPower_L3(SIUnitType.VA),
	
	GM_1_ReactivePower_L1(SIUnitType.VAR),
	GM_1_ReactivePower_L2(SIUnitType.VAR),
	GM_1_ReactivePower_L3(SIUnitType.VAR),
	
	GM_1_PowerFactor_L1(SIUnitType.Numeric),
	GM_1_PowerFactor_L2(SIUnitType.Numeric),
	GM_1_PowerFactor_L3(SIUnitType.Numeric),
	
	GM_1_Frequency_L1(SIUnitType.HZ),
	GM_1_Frequency_L2(SIUnitType.HZ),
	GM_1_Frequency_L3(SIUnitType.HZ),
	
	GM_1_PhaseInterval_L1L3(SIUnitType.DEG),
	GM_1_PhaseInterval_L2L3(SIUnitType.DEG),
	GM_1_PhaseInterval_L1L2(SIUnitType.DEG),
	
	GM_1_TotalActiveEnergy(SIUnitType.WA),
	GM_1_TotalApparentEnergy(SIUnitType.VA),
	GM_1_TotalReactiveEnergy(SIUnitType.VAR),
	
	GM_1_ActiveEnergy_L1(SIUnitType.WA),
	GM_1_ActiveEnergy_L2(SIUnitType.WA),
	GM_1_ActiveEnergy_L3(SIUnitType.WA),
	
	GM_1_ApparentEnergy_L1(SIUnitType.VA),
	GM_1_ApparentEnergy_L2(SIUnitType.VA),
	GM_1_ApparentEnergy_L3(SIUnitType.VA),
	
	GM_1_ReactiveEnergy_L1(SIUnitType.VAR),
	GM_1_ReactiveEnergy_L2(SIUnitType.VAR),
	GM_1_ReactiveEnergy_L3(SIUnitType.VAR),
	
	GM_1_PowerSource(SIUnitType.NONE),
	GM_1_IdCount(SIUnitType.Numeric),
	
	/* Second Grid meter of a Node */
	GM_2_RMSVoltage_L1(SIUnitType.V),
	GM_2_RMSVoltage_L2(SIUnitType.V),
	GM_2_RMSVoltage_L3(SIUnitType.V),
	
	GM_2_RMSCurrent_L1(SIUnitType.V),
	GM_2_RMSCurrent_L2(SIUnitType.V),
	GM_2_RMSCurrent_L3(SIUnitType.V),
	
	GM_2_ActivePower_L1(SIUnitType.W),
	GM_2_ActivePower_L2(SIUnitType.W),
	GM_2_ActivePower_L3(SIUnitType.W),
	
	GM_2_ApparentPower_L1(SIUnitType.VA),
	GM_2_ApparentPower_L2(SIUnitType.VA),
	GM_2_ApparentPower_L3(SIUnitType.VA),
	
	GM_2_ReactivePower_L1(SIUnitType.VAR),
	GM_2_ReactivePower_L2(SIUnitType.VAR),
	GM_2_ReactivePower_L3(SIUnitType.VAR),
	
	GM_2_PowerFactor_L1(SIUnitType.Numeric),
	GM_2_PowerFactor_L2(SIUnitType.Numeric),
	GM_2_PowerFactor_L3(SIUnitType.Numeric),
	
	GM_2_Frequency_L1(SIUnitType.HZ),
	GM_2_Frequency_L2(SIUnitType.HZ),
	GM_2_Frequency_L3(SIUnitType.HZ),
	
	GM_2_PhaseInterval_L1L3(SIUnitType.DEG),
	GM_2_PhaseInterval_L2L3(SIUnitType.DEG),
	GM_2_PhaseInterval_L1L2(SIUnitType.DEG),
	
	GM_2_TotalActiveEnergy(SIUnitType.WA),
	GM_2_TotalApparentEnergy(SIUnitType.VA),
	GM_2_TotalReactiveEnergy(SIUnitType.VAR),
	
	GM_2_ActiveEnergy_L1(SIUnitType.WA),
	GM_2_ActiveEnergy_L2(SIUnitType.WA),
	GM_2_ActiveEnergy_L3(SIUnitType.WA),
	
	GM_2_ApparentEnergy_L1(SIUnitType.VA),
	GM_2_ApparentEnergy_L2(SIUnitType.VA),
	GM_2_ApparentEnergy_L3(SIUnitType.VA),
	
	GM_2_ReactiveEnergy_L1(SIUnitType.VAR),
	GM_2_ReactiveEnergy_L2(SIUnitType.VAR),
	GM_2_ReactiveEnergy_L3(SIUnitType.VAR),

	GM_2_PowerSource(SIUnitType.NONE),
	GM_2_IdCount(SIUnitType.Numeric),
	
	
	/* Load Grid meter of a Node */
	LM_RMSVoltage_L1(SIUnitType.V),
	LM_RMSVoltage_L2(SIUnitType.V),
	LM_RMSVoltage_L3(SIUnitType.V),
	
	LM_RMSCurrent_L1(SIUnitType.V),
	LM_RMSCurrent_L2(SIUnitType.V),
	LM_RMSCurrent_L3(SIUnitType.V),
	
	LM_ActivePower_L1(SIUnitType.W),
	LM_ActivePower_L2(SIUnitType.W),
	LM_ActivePower_L3(SIUnitType.W),
	
	LM_ApparentPower_L1(SIUnitType.VA),
	LM_ApparentPower_L2(SIUnitType.VA),
	LM_ApparentPower_L3(SIUnitType.VA),
	
	LM_ReactivePower_L1(SIUnitType.VAR),
	LM_ReactivePower_L2(SIUnitType.VAR),
	LM_ReactivePower_L3(SIUnitType.VAR),
	
	LM_PowerFactor_L1(SIUnitType.Numeric),
	LM_PowerFactor_L2(SIUnitType.Numeric),
	LM_PowerFactor_L3(SIUnitType.Numeric),
	
	LM_Frequency_L1(SIUnitType.HZ),
	LM_Frequency_L2(SIUnitType.HZ),
	LM_Frequency_L3(SIUnitType.HZ),
	
	LM_PhaseInterval_L1L3(SIUnitType.DEG),
	LM_PhaseInterval_L2L3(SIUnitType.DEG),
	LM_PhaseInterval_L1L2(SIUnitType.DEG),
	
	LM_TotalActiveEnergy(SIUnitType.WA),
	LM_TotalApparentEnergy(SIUnitType.VA),
	LM_TotalReactiveEnergy(SIUnitType.VAR),
	
	LM_ActiveEnergy_L1(SIUnitType.WA),
	LM_ActiveEnergy_L2(SIUnitType.WA),
	LM_ActiveEnergy_L3(SIUnitType.WA),
	
	LM_ApparentEnergy_L1(SIUnitType.VA),
	LM_ApparentEnergy_L2(SIUnitType.VA),
	LM_ApparentEnergy_L3(SIUnitType.VA),
	
	LM_ReactiveEnergy_L1(SIUnitType.VAR),
	LM_ReactiveEnergy_L2(SIUnitType.VAR),
	LM_ReactiveEnergy_L3(SIUnitType.VAR),

	LM_PowerSource(SIUnitType.NONE),
	
	LM_IdCount(SIUnitType.Numeric), 
	
	PowerFactor(SIUnitType.Numeric), 
	
	/* Apros */
	GM_Voltage(SIUnitType.V),
	GM_Current(SIUnitType.A),
	GM_ActivePower(SIUnitType.VA),
	GM_ReactivePower(SIUnitType.VA),
	
	LM_Voltage(SIUnitType.V),
	LM_Current(SIUnitType.A),
	LM_ActivePower(SIUnitType.VA),
	LM_ReactivePower(SIUnitType.VA),
	
	OC(SIUnitType.SWITCHPOSITION),
	IC(SIUnitType.SWITCHPOSITION);
	;
	
	
	

	final SIUnitType type;

	private SIDeviceType(final SIUnitType type) {
		this.type = type;
	}

	public SIUnitType getType() {
		return type;
	}
	
	@JsonCreator
	public static SIDeviceType fromString(String text) {
		    if (text != null) {
		      for (SIDeviceType b : SIDeviceType.values()) {
		        if (text.toLowerCase().equals(b.name().toLowerCase())) {
		          return b;
		        }
		      }
		    }
		    return null;
		  }
	
	
}
