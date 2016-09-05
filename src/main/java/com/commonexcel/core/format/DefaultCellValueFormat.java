package com.commonexcel.core.format;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;

import com.commonexcel.core.annotion.CellValueFormat;


public class DefaultCellValueFormat extends CellValueFormat{

	@Override
	public Object readFormat(Cell cell, Class<?> propertyClass, String propertyName) {
		String cellSrcValue = null;
		Object destValue = null;
		if (cell == null || StringUtils.isBlank(cellSrcValue = cell.toString().trim())) {
			return destValue;
		}
		
		if (propertyClass.equals(String.class)) {
			destValue = cellSrcValue;
		} else if (propertyClass.equals(Long.class)) {

			destValue = Double.valueOf(cell.getNumericCellValue()).longValue();
		} else if (propertyClass.equals(Integer.class)) {
			destValue = Integer.valueOf(getNumberStr(cellSrcValue));
		} else if (propertyClass.equals(Short.class)) {
			destValue = Short.valueOf(getNumberStr(cellSrcValue));
		} else if (propertyClass.equals(Double.class)) {
			destValue = Double.valueOf(cellSrcValue);
		} else if (propertyClass.equals(Float.class)) {
			destValue = Double.valueOf(cellSrcValue);
		} else {
			throw new RuntimeException("不识别的类型" + propertyClass.getName());
		}

		return destValue;
	}

	@Override
	public String writeFormat(Object value, Class<?> propertyClass, String propertyName) {
		if(value == null){
			return null;
		}
		
		return value.toString();
	}

	private static String getNumberStr(String number) {
		int index = number.indexOf(".");
		if(index != -1){
			return number.substring(0, index);
		}else{
			return number;
		}
	}
}
