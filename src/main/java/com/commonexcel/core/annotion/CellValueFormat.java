package com.commonexcel.core.annotion;

import org.apache.poi.ss.usermodel.Cell;

public abstract class CellValueFormat {
	
	protected String formatParam;
	
	public abstract Object readFormat(Cell cell, Class<?> propertyClass, String propertyName);
	
	public abstract String writeFormat(Object value, Class<?> propertyClass, String propertyName);

	public String getFormatParam() {
		return formatParam;
	}

	public void setFormatParam(String formatParam) {
		this.formatParam = formatParam;
	}
	
}
