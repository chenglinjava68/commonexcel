package com.commonexcel.core.utils;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.ss.usermodel.Cell;

import com.commonexcel.core.annotion.CellValueFormat;
import com.commonexcel.core.annotion.PropertyCell;
import com.commonexcel.core.format.DateCellValueFormat;
import com.commonexcel.core.format.DefaultCellValueFormat;

public class BeanAnnotionUtils {
	private static final Map<Class<?>, Map<String, PropertyDesc>> cache = new ConcurrentHashMap<Class<?>, Map<String, PropertyDesc>>();
	private static final DefaultCellValueFormat DEFAULTCELLVALUEFORMAT = new DefaultCellValueFormat();
	public static Map<String, PropertyDesc> getBeanAnnotion(Class<?> clazz) {
		Map<String, PropertyDesc> map = cache.get(clazz);
		if (map != null) {
			return map;
		}
		try {
			map = new LinkedHashMap<String, BeanAnnotionUtils.PropertyDesc>();
			PropertyDescriptor[] ps = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
			Map<String, PropertyDescriptor> tempPsMap = new HashMap<String, PropertyDescriptor>();
			for (PropertyDescriptor pd : ps) {
				tempPsMap.put(pd.getName(), pd);
			}
			Field[] fields = clazz.getDeclaredFields();
			List<PropertyDesc> listPD = new ArrayList<PropertyDesc>(fields.length);
			for (Field field : fields) {
				PropertyCell propertyCell = field.getAnnotation(PropertyCell.class);
				if (propertyCell != null) {
					listPD.add(new PropertyDesc(tempPsMap.get(field.getName()), propertyCell));
				}
			}
			// 排序
			Collections.sort(listPD, new Comparator<PropertyDesc>() {
				@Override
				public int compare(PropertyDesc o1, PropertyDesc o2) {
					return o1.getOrder() - o2.getOrder();
				}
			});
			for (PropertyDesc p : listPD) {
				map.put(p.getTitle(), p);
			}
			cache.put(clazz, map);
			return map;
		} catch (Exception e) {
			throw new RuntimeException("未知异常", e);
		}
	}

	public static class PropertyDesc {

		private Class<?> propertyClass;
		private String propertyName;
		private Method readMethod;
		private Method writeMethod;
		private PropertyCell propertyCell;
		private CellValueFormat cellValueFormat;

		public PropertyDesc(PropertyDescriptor pd, PropertyCell propertyCell) {
			this.propertyClass = pd.getPropertyType();
			this.readMethod = pd.getReadMethod();
			this.writeMethod = pd.getWriteMethod();
			this.propertyCell = propertyCell;
			this.propertyName = pd.getName();
			Class<? extends CellValueFormat> clazz = propertyCell.format();
			if (clazz == DefaultCellValueFormat.class) {
				if("".equals(propertyCell.formatParam())){
					this.cellValueFormat = DEFAULTCELLVALUEFORMAT;
				}else{
					this.cellValueFormat = new DateCellValueFormat();
				}
				
			} else if(clazz == DateCellValueFormat.class){
				this.cellValueFormat = new DateCellValueFormat();
			}else{
				try {
					this.cellValueFormat = clazz.newInstance();
				} catch (Exception e) {
					throw new RuntimeException("构造格式化类初始化失败 class " + clazz.getName(), e);
				}
			}
			this.cellValueFormat.setFormatParam(propertyCell.formatParam());
		}

		public String getTitle() {
			return this.propertyCell.title();
		}

		public int getOrder() {
			return this.propertyCell.order();
		}

		/**
		 * @Title: setValue 
		 * @param t
		 * @param cell
		 * @return  成功设置值得话返回true  如果值为空的话 返回false;
		 */
		public boolean setValue(Object t, Cell cell) {

			Object value = this.cellValueFormat.readFormat(cell, propertyClass, this.propertyName);
			if (value == null) {
				return false;
			}

			try {
				this.writeMethod.invoke(t, new Object[] { value });
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("写入属性失败", e);
			}
		}

		/**
		 * @Title: getValue
		 * @Description: 去掉小数点
		 * @param t
		 * @return
		 */
		public String getValue(Object t) {
			try {
				String value = this.cellValueFormat.writeFormat(this.readMethod.invoke(t, new Object[] {}), this.propertyClass, this.propertyName);
				if (value == null) {
					value = "";
				}
				return value.toString();
			} catch (Exception e) {
				throw new RuntimeException("读取属性失败", e);
			}
		}

	}

}
