package com.commonexcel.core.format;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;

import com.commonexcel.core.annotion.CellValueFormat;

public class DateCellValueFormat extends CellValueFormat {
	@Override
	public Object readFormat(Cell cell, Class<?> propertyClass, String propertyName) {

		String cellSrcValue = null;
		if (cell == null || StringUtils.isBlank(cellSrcValue = cell.toString().trim())) {
			return null;
		}
		try {
			if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
				return cell.getDateCellValue();
			}else{
				return DateUtils.parseDate(cellSrcValue, new String[] { this.getFormatParam() });
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("转换日式失败 class %s  propertyName %s srcValue %s", propertyClass.getName(), propertyName, cellSrcValue), e);
		}

	}

	@Override
	public String writeFormat(Object value, Class<?> propertyClass, String propertyName) {
		if (value != null && value instanceof Date) {
			return DateFormatUtils.format((Date) value, this.getFormatParam());
		}
		return value.toString();
	}

	@Override
	public void setFormatParam(String formatParam) {
		if (StringUtils.isBlank(formatParam)) {
			this.formatParam = "yyyy-MM-dd HH:mm:ss";
		}else{
			this.formatParam = formatParam;
		}
	}
	
}
