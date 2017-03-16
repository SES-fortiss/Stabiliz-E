/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fortiss.smg.actuatorclient.dummy.impl;

import org.fortiss.smg.containermanager.api.devices.SIDeviceType;

/**
 *
 * @author hugo.pereira
 */
public enum DataIdentifier {
    /*! Definition of protocol data items identifiers */

    /*! Measured Parameters */
    /*! RMS Voltage */

    DAPO_ENRG_l1_Volt(1, SIDeviceType.ConsumptionVoltmeter),//L1 RMS Voltage
    DAPO_ENRG_l2_Volt(2, SIDeviceType.ConsumptionVoltmeter),//L2 RMS Voltage
    DAPO_ENRG_l3_Volt(3, SIDeviceType.ConsumptionVoltmeter),//L3 RMS Voltage

    /*! RMS Current */
    DAPO_ENRG_l1_Curr(4, SIDeviceType.ConsumptionAmperemeter),//L1 RMS Current
    DAPO_ENRG_l2_Curr(5, SIDeviceType.ConsumptionAmperemeter),//L2 RMS Current
    DAPO_ENRG_l3_Curr(6, SIDeviceType.ConsumptionAmperemeter),//L3 RMS Current

    /*! Power */
    DAPO_ENRG_l1_PowerWA(7, SIDeviceType.ConsumptionPowermeter),//L1 Active Power
    DAPO_ENRG_l2_PowerWA(8, SIDeviceType.ConsumptionPowermeter),//L2 Active Power
    DAPO_ENRG_l3_PowerWA(9, SIDeviceType.ConsumptionPowermeter),//L3 Active Power
    DAPO_ENRG_l1_PowerVA(10, SIDeviceType.ConsumptionVoltAmperemeter),//L1 Apparent Power
    DAPO_ENRG_l2_PowerVA(11, SIDeviceType.ConsumptionVoltAmperemeter),//L2 Apparent Power
    DAPO_ENRG_l3_PowerVA(12, SIDeviceType.ConsumptionVoltAmperemeter),//L3 Apparent Power
    DAPO_ENRG_l1_PowerVAR(13, SIDeviceType.ConsumptionVoltmeter),//L1 Reactive Power
    DAPO_ENRG_l2_PowerVAR(14, SIDeviceType.ConsumptionVoltmeter),//L2 Reactive Power
    DAPO_ENRG_l3_PowerVAR(15, SIDeviceType.ConsumptionVoltmeter),//L3 Reactive Power

    /*! Power Factor */
    DAPO_ENRG_l1_Pf(16, SIDeviceType.PowerFactor),//L1 Power Factor
    DAPO_ENRG_l2_Pf(17, SIDeviceType.PowerFactor),//L2 Power Factor
    DAPO_ENRG_l3_Pf(18, SIDeviceType.PowerFactor),//L3 Power Factor


    /*! Frequency */
    DAPO_ENRG_l1_Hz(19, SIDeviceType.Frequency),//L1 Frequency
    DAPO_ENRG_l2_Hz(20, SIDeviceType.Frequency),//L2 Frequency
    DAPO_ENRG_l3_Hz(21, SIDeviceType.Frequency),//L3 Frequency

    /*! Interval between phases voltage (Degrees) */
    DAPO_ENRG_VoltIntervL1L3(22, SIDeviceType.ConsumptionVoltmeter),//L1-L3 Phase Interval
    DAPO_ENRG_VoltIntervL2L3(23, SIDeviceType.ConsumptionVoltmeter),//L2-L3 Phase Interval
    DAPO_ENRG_VoltIntervL1L2(24, SIDeviceType.ConsumptionVoltmeter),//L1-L2 Phase Interval

    /*! Consumed Energy */
    DAPO_ENRG_TOT_EnergyWA(25, SIDeviceType.ConsumptionVoltmeter),//Total Consumed Active Energy
    DAPO_ENRG_TOT_EnergyVA(26, SIDeviceType.ConsumptionVoltAmperemeter),//Total Consumed Apparent Energy
    DAPO_ENRG_TOT_EnergyVAR(27, SIDeviceType.ConsumptionVoltmeter),//Total Consumed Reactive Energy
    DAPO_ENRG_l1_EnergyWA(28, SIDeviceType.ConsumptionVoltmeter),//L1 Consumed Active Energy
    DAPO_ENRG_l1_EnergyVA(29, SIDeviceType.ConsumptionVoltmeter),//L1 Consumed Apparent Energy
    DAPO_ENRG_l1_EnergyVAR(30, SIDeviceType.ConsumptionVoltmeter),//L1 Consumed Reactive Energy
    DAPO_ENRG_l2_EnergyWA(31, SIDeviceType.ConsumptionPowermeter),//L2 Consumed Active Energy
    DAPO_ENRG_l2_EnergyVA(32, SIDeviceType.ConsumptionVoltmeter),//L2 Consumed Apparent Energy
    DAPO_ENRG_l2_EnergyVAR(33, SIDeviceType.ConsumptionVoltmeter),//L2 Consumed Reactive Energy
    DAPO_ENRG_l3_EnergyWA(34, SIDeviceType.ConsumptionVoltmeter),//L3 Consumed Active Energy
    DAPO_ENRG_l3_EnergyVA(35, SIDeviceType.ConsumptionVoltmeter),//L3 Consumed Apparent Energy
    DAPO_ENRG_l3_EnergyVAR(36, SIDeviceType.ConsumptionVoltmeter),//L3 Consumed Reactive Energy

    /*! System Parameters */
    DAPO_ENRG_PowerSource(37, SIDeviceType.ConsumptionVoltmeter),//System Power Source Type

    /*! Outputs Parameters */
    /*! Digital Outputs */
    DAPO_ENRG_OUT_D1(38, SIDeviceType.Powerplug),//Digital Output 1
    DAPO_ENRG_OUT_D2(39, SIDeviceType.Powerplug),//Digital Output 2
    DAPO_ENRG_OUT_D3(40, SIDeviceType.Powerplug),//Digital Output 3
    DAPO_ENRG_OUT_D4(41, SIDeviceType.Powerplug),//Digital Output 4
    DAPO_ENRG_OUT_D5(42, SIDeviceType.Powerplug),//Digital Output 5
    DAPO_ENRG_OUT_D6(43, SIDeviceType.Powerplug),//Digital Output 6
    DAPO_ENRG_OUT_D7(44, SIDeviceType.Powerplug),//Digital Output 7
    DAPO_ENRG_OUT_D8(45, SIDeviceType.Powerplug),//Digital Output 8

    /*! Corresponds to the number of on-board data items defined */
    DAPO_ENRG_ID_COUNT(46, SIDeviceType.ConsumptionVoltmeter);
    
    
    
    private final int key;
	private final SIDeviceType type;

    DataIdentifier(int key, SIDeviceType type) {
        this.key = key;
        this.type = type;
    }

    public SIDeviceType getType() {
		return type;
	}

	public int getKey() {
        return this.key;
    }

    public static DataIdentifier fromKey(int key) {
        for (DataIdentifier type : DataIdentifier.values()) {
            if (type.getKey() == key) {
                return type;
            }
        }
        return null;
    }
    

}
