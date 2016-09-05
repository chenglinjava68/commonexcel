package com.commonexcel.core.cellconver;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;

/**
 * @Description: CellConvert  将cell转换成bean
 */
public interface CellConvert {
	
	/**
	 * @Title: readConvert 
	 * @Description: 读取转换
	 * @param map
	 * @param clazz
	 * @return
	 */
	public <T> T readConvert(Map<String, Cell> map, Class<T> clazz);
	
	/**
	 * @Title: writeConvertHead 
	 * @Description: 写入excel头
	 * @param clazz
	 * @param sheet
	 */
	public <T> void writeConvertHead(Class<T> clazz, Sheet sheet);
	
	/**
	 * @Title: writeConvertContent 
	 * @Description: 写入excel行
	 * @param dataList
	 */
	public <T> void writeConvertContent(List<T> dataList, Class<T> clazz, Sheet sheet);
}
