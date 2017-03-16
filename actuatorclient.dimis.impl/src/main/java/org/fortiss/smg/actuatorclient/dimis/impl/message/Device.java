/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fortiss.smg.actuatorclient.dimis.impl.message;

import org.fortiss.smg.actuatorclient.dimis.impl.Configs;
import org.fortiss.smg.actuatorclient.dimis.impl.object.DataIdentifier;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 *
 * @author hugo.pereira
 */
public class Device {
//{
//    "PN": "DA-0001",
//    "SN": "161001121401-0001",
//    "dataID": [1,2,3,4,5,6,7,8],
//    "data": [
//        228.100000,
//        228.100000,
//        228.100000,
//        228.100000,
//        228.100000,
//        228.100000,
//        228.100000,
//        228.100000
//    ]
//}
    @SerializedName("PN")
    public String partNumber;
    @SerializedName("SN")
    public String serialNumber;
    @SerializedName("dataID")
    public ArrayList<DataIdentifier> dataIDList;
    @SerializedName("data")
    public ArrayList<Double> valuesList;

    /**
     * Constructor
     */
    public Device() {
        this.partNumber = "";
        this.serialNumber = "";
        this.dataIDList = new ArrayList<DataIdentifier>();
        this.valuesList = new ArrayList<Double>();
    }
    /**
     * Constructor
     * @param partNumber
     * @param serialNumber
     * @param dataIDList
     * @param valuesList 
     */
    public Device(String partNumber, String serialNumber, ArrayList<DataIdentifier> dataIDList, ArrayList<Double> valuesList) {
        this.partNumber = partNumber;
        this.serialNumber = serialNumber;
        this.dataIDList = dataIDList;
        this.valuesList = valuesList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Device{ PN=").append(partNumber).append(", SN=").append(serialNumber).append(", dataID=[");
        for (DataIdentifier value : dataIDList) {
            sb.append(value).append(", ");
        }
        sb.append("], data=[");
        for (Double value : valuesList) {
            sb.append(value).append(", ");
        }
        sb.append("] }");
        return sb.toString();
    }
    
    /**
     * 
     * @return 
     */
    public String toJson() {
        return Configs.gson.toJson(this);
    }
    
    public String printData() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dataIDList.size(); i++) {
            sb.append(dataIDList.get(i)).append("(").append(dataIDList.get(i).getKey()).append("): ").append(valuesList.get(i)).append(",\t");
        }
        return sb.toString();
    }
}
