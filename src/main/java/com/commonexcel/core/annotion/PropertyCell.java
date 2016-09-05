package com.commonexcel.core.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.commonexcel.core.format.DefaultCellValueFormat;

@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.ANNOTATION_TYPE, ElementType.FIELD })
public @interface PropertyCell {

	/**
	 * @Title: title 
	 * @Description: 标题
	 *
	 * @return
	 */
	String title();
	
	/**
	 * @Title: order 
	 * @Description: 排序
	 *
	 * @return
	 */
	int order() default 0;
	
	/**
	 * @Title: format 
	 * @Description: 格式化
	 *
	 * @return
	 */
	Class<? extends CellValueFormat> format() default DefaultCellValueFormat.class;
	
	/**
	 * @Title: formatParam 
	 * @Description: 配合format的参数  如果formatParam有值 但是format 为默认值得花 那么采取DateCellValueFormat 来处理
	 * @return
	 */
	String formatParam() default "";
}
