package com.health.system.tools.logistical;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.health.system.util.PageData;

public class LogisticsUtils {
	/**
	 * 物流查询
	 * @param result
	 * @return
	 */
	public static PageData getLogistics(String logisticsNo){
		KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
		KdApiOrderDistinguish codetype = new KdApiOrderDistinguish();//物流类别
		
		PageData result = new PageData();
		try {
			result.put("logisticsNo", logisticsNo);
			String typeStr = codetype.getOrderTracesByJson(logisticsNo);
			
			//获取快递公司编码
			int fromindex = typeStr.indexOf("ShipperCode") + 11;
			int toindex = typeStr.indexOf("ShipperName");
			String sub_result = typeStr.substring(fromindex, toindex);
			String typeCode = sub_result.replaceAll("[\\pP]", "").trim();
			
			//获取快递公司名
			String nameStr = typeStr.substring(toindex + 11);
			String name = nameStr.replaceAll("[\\pP]", "").trim();
			result.put("typeName", name);
			
			//获取物流轨迹
			String traceStr = api.getOrderTracesByJson(typeCode, logisticsNo);
			List<PageData> traceList = new ArrayList<PageData>();
			if(traceStr !=  null){
				JSONObject obj = JSONObject.fromObject(traceStr);
				JSONArray arr = obj.getJSONArray("Traces");
				for (int i = arr.size() - 1 ; i >= 0 ; i--) {
					JSONObject traceitem = arr.getJSONObject(i);
					PageData pd = new PageData();
					pd.put("AcceptStation", traceitem.getString("AcceptStation"));
					pd.put("AcceptTime", traceitem.getString("AcceptTime"));
					traceList.add(pd);
				}
			}
			result.put("traceList", traceList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
