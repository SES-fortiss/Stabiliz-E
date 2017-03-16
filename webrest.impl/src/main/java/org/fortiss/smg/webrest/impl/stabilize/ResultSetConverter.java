package org.fortiss.smg.webrest.impl.stabilize;

import java.sql.ResultSet;

import org.codehaus.jettison.json.*;

public class ResultSetConverter {

	public static JSONObject convertToJSON(ResultSet resultSet)
            throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < total_rows; i++) {
                obj.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(), resultSet.getObject(i + 1));
            }
          jsonArray.put(obj);
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("arrayName",jsonArray);
        return jsonObject;
    }
}
