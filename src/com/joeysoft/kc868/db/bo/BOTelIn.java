package com.joeysoft.kc868.db.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.TelIn;
import com.joeysoft.kc868.db.bean.Unused;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.db.util.UnusedUtil;

public class BOTelIn {
	
	private BOUnused boUnused = new BOUnused();
	
	public List<TelIn> getList(){
		List<TelIn> list = new ArrayList<TelIn>();
		try {
			if(SystemConfig.getInstance().isHardSoftVer2030()){
				String sql = "SELECT * FROM TEL_IN ORDER BY TEL_ID";
				List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
				for(Map mp : lt){
					
					TelIn TelIn = new TelIn(SQLUtil.returnStr(mp, "TEL_ID"), SQLUtil.returnStr(mp, "TEL_PHONE"),
							SQLUtil.returnStr(mp, "TEL_NAME"), SQLUtil.returnStr(mp, "COUNTRY_CODE"));
					
					list.add(TelIn);
				}
			}else{
				String sql = "SELECT * FROM TEL_IN ORDER BY TEL_INDEX";
				List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
				for(Map mp : lt){
					
					TelIn TelIn = new TelIn(SQLUtil.returnStr(mp, "TEL_ID"), SQLUtil.returnStr(mp, "TEL_PHONE"),
							SQLUtil.returnStr(mp, "TEL_NAME"), SQLUtil.returnStr(mp, "COUNTRY_CODE"), SQLUtil.returnInt(mp, "TEL_INDEX"));
					
					list.add(TelIn);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<TelIn> getListValid(){
		List<TelIn> list = new ArrayList<TelIn>();
		try {
			if(SystemConfig.getInstance().isHardSoftVer2030()){
				String sql = "SELECT * FROM TEL_IN WHERE TEL_PHONE IS NOT NULL ORDER BY TEL_ID";
				List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
				for(Map mp : lt){
					
					TelIn TelIn = new TelIn(SQLUtil.returnStr(mp, "TEL_ID"), SQLUtil.returnStr(mp, "TEL_PHONE"),
							SQLUtil.returnStr(mp, "TEL_NAME"), SQLUtil.returnStr(mp, "COUNTRY_CODE"));
					
					list.add(TelIn);
				}
			}else{
				String sql = "SELECT * FROM TEL_IN WHERE TEL_PHONE IS NOT NULL ORDER BY TEL_INDEX";
				List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
				for(Map mp : lt){
					
					TelIn TelIn = new TelIn(SQLUtil.returnStr(mp, "TEL_ID"), SQLUtil.returnStr(mp, "TEL_PHONE"),
							SQLUtil.returnStr(mp, "TEL_NAME"), SQLUtil.returnStr(mp, "COUNTRY_CODE"), SQLUtil.returnInt(mp, "TEL_INDEX"));
					
					list.add(TelIn);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取记录总数
	 * @param DBConnection.getConnection()
	 * @return
	 */
	public int getCount(){
		int count = 0;
		try {
			Map htData =  SQLUtil.getFirstRecord(DBConnection.getConnection(), null, true, "SELECT COUNT(*) AS COUNT FROM TEL_IN");
			if(htData != null){
				return SQLUtil.returnInt(htData, "COUNT");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 得到Key
	 * @param keyId
	 * @return
	 */
	public TelIn get(int telId){
		TelIn rm = new TelIn();
		try {
			String sql = "SELECT * FROM TEL_IN WHERE TEL_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), telId, true, sql);
			if(htData != null){
				rm.setTelId(SQLUtil.returnStr(htData, "TEL_ID"));
				rm.setTelPhone(SQLUtil.returnStr(htData, "TEL_PHONE"));
				rm.setTelName(SQLUtil.returnStr(htData, "TEL_NAME"));
				rm.setCountryCode(SQLUtil.returnStr(htData, "COUNTRY_CODE"));
				if(!SystemConfig.getInstance().isHardSoftVer2030()){
					rm.setTelIndex(SQLUtil.returnInt(htData, "TEL_INDEX"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rm;
	}
	
	/**
	 * 插入
	 * @param DBConnection.getConnection()
	 * @param floorName
	 * @return
	 */
	public boolean insert(TelIn telIn){
		try {
			
			Unused unused = boUnused.get("TEL_IN");
			
			Unused next = UnusedUtil.getNextUnused(unused);
			telIn.setTelId(DictConst.TABLE_PREFIX_TEL_IN + next.getNextUnused());
			if(!SystemConfig.getInstance().isHardSoftVer2030()){
				telIn.setTelIndex(next.getNextUnused());
			}
			SQLUtil.insertSQL(DBConnection.getConnection(), "TEL_IN", telIn.fillMap());
			boUnused.updateUnused(next);
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
			
			return true;
		} catch (Exception e) {
			try {
				DBConnection.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(TelIn telIn) throws Exception{
		try {
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("TEL_PHONE", telIn.getTelPhone());
			htUpdateParam.put("TEL_NAME", telIn.getTelName());
			htUpdateParam.put("COUNTRY_CODE", telIn.getCountryCode());
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("TEL_ID", telIn.getTelId());
			
			SQLUtil.updateSQL(DBConnection.getConnection(), "TEL_IN", htUpdateParam, htKeyParam);
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			DBConnection.getConnection().rollback();
			throw e;
		}
	}
	
	public boolean delete(int telId){
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("TEL_ID", telId);
			
			SQLUtil.deleteSQL(DBConnection.getConnection(), "TEL_IN", htParam);
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
			return true;
		} catch (Exception e) {
			try {
				DBConnection.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}
}
