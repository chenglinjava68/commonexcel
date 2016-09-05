package com.commonexcel.core.cellconver;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.commonexcel.core.utils.BeanAnnotionUtils;
import com.commonexcel.core.utils.BeanAnnotionUtils.PropertyDesc;

public class AnnotionCellConvertImpl implements CellConvert {
	
	public <T> T readConvert(Map<String, Cell> map, Class<T> clazz) {

		try {
			T t = clazz.newInstance();
			Iterator<java.util.Map.Entry<String, Cell>> it= map.entrySet().iterator();
			Map<String, PropertyDesc> proMap = BeanAnnotionUtils.getBeanAnnotion(clazz);
			boolean isAllNull = true;
			while(it.hasNext()){
				java.util.Map.Entry<String, Cell> entry = it.next();
				PropertyDesc pd = proMap.get(entry.getKey());
				if(pd != null){
					if(pd.setValue(t, entry.getValue())){
						isAllNull = false;
					};
				}
			}
			if(isAllNull){
				return null;
			}
			return t;
		} catch (Exception e) {
			throw new RuntimeException("cell转换bean失败", e);
		}

	}

	@Override
	public <T> void writeConvertHead(Class<T> clazz, Sheet sheet) {
		Row head = sheet.createRow(0);
		Set<String> set = BeanAnnotionUtils.getBeanAnnotion(clazz).keySet();
		int i = 0;
		for(String title : set){
			head.createCell(i++).setCellValue(title);
		}
	}

	@Override
	public <T> void writeConvertContent(List<T> dataList, Class<T> clazz, Sheet sheet) {
		Collection<PropertyDesc> coll = BeanAnnotionUtils.getBeanAnnotion(clazz).values();
		for(int i = 0; i < dataList.size(); i++){
			Row dataRow = sheet.createRow(i + 1);
			int j = 0;
			for(PropertyDesc pd : coll){
				dataRow.createCell(j++).setCellValue(pd.getValue(dataList.get(i)));
			}
		}
	}
}
