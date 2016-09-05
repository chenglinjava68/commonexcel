package com.commonexcel.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.commonexcel.core.cellconver.AnnotionCellConvertImpl;
import com.commonexcel.core.cellconver.CellConvert;

public class ExcelHelper {
	
	public static final String EXCEL_SUFFIX_XLSX = ".xlsx";
	public static final String EXCEL_SUFFIX_XLS = ".xls";

	private static final CellConvert ANNOTION_CELLCONVERT = new AnnotionCellConvertImpl();

	public static <T> List<T> readList(String path, Class<T> clazz) {
		return readList(new File(path), 0, clazz);
	}

	public static <T> List<T> readList(String path, int sheetIndex, Class<T> clazz) {
		return readList(new File(path), sheetIndex, clazz);
	}

	public static <T> List<T> readList(File exceleFile, int sheetIndex, Class<T> clazz) {
		return readList(exceleFile, sheetIndex, ANNOTION_CELLCONVERT, clazz);
	}

	public static <T> void writeList(String path, List<T> dataList) {
		writeList(path, "sheet_" + UUID.randomUUID(), dataList);
	}

	public static <T> void writeList(String path, String sheetName, List<T> dataList) {
		writeList(new File(path), sheetName, dataList);
	}

	public static <T> void writeList(File exceleFile, String sheetName, List<T> dataList) {
		writeList(exceleFile, sheetName, ANNOTION_CELLCONVERT, dataList);
	}

	@SuppressWarnings("unchecked")
	public static <T> void writeList(File exceleFile, String sheetName, CellConvert convert, List<T> dataList) {
		if (dataList == null || dataList.isEmpty()) {
			return;
		}
		FileOutputStream fileOut = null;
		try {
			Workbook workbook = null;
			
			if(exceleFile.getName().endsWith(".xlsx")){
				workbook = new XSSFWorkbook();
			}else if(exceleFile.getName().endsWith(".xls")){
				workbook = new HSSFWorkbook();
			}else{
				throw new RuntimeException("不识别的后缀名 , 目前只识别  【.xlsx， .xls】");
			}
			
			fileOut = new FileOutputStream(exceleFile);
			Sheet sheet = workbook.createSheet(sheetName);
			Class<T> clazz = (Class<T>) dataList.get(0).getClass();
			convert.writeConvertHead(clazz, sheet);
			convert.writeConvertContent(dataList, clazz, sheet);
			workbook.write(fileOut);
			
		} catch (Exception e) {
			throw new RuntimeException("写入excel数据失败", e);
		} finally {
			try {
				if (fileOut != null) {
					fileOut.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Title: getDataList
	 * @Description: 获取excel的数据
	 * @param exceleFile
	 *            必填
	 * @param sheetIndex
	 *            必填
	 * @param convert
	 *            必填
	 * @param clazz
	 *            必填
	 * @return
	 */
	public static <T> List<T> readList(File exceleFile, int sheetIndex, CellConvert convert, Class<T> clazz) {
		List<T> dataList = new ArrayList<T>();
		try {
			Workbook workbook = WorkbookFactory.create(exceleFile);
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			int rowCount = sheet.getLastRowNum();
			if (rowCount == 0) {return null;}
			Row titleRow = sheet.getRow(0);
			int cellCount = titleRow.getLastCellNum();
			String[] titles = new String[cellCount];
			for (int i = 0; i < cellCount; i++) {
				titles[i] = titleRow.getCell(i).toString();
			}
			for (int i = 1; i < (rowCount + 1); i++) {
				Row contentRow = sheet.getRow(i);
				Map<String, Cell> mapCell = new HashMap<String, Cell>();
				for (int j = 0; j < cellCount; j++) {
					mapCell.put(titles[j], contentRow.getCell(j, Row.RETURN_BLANK_AS_NULL));
				}
				T t = convert.readConvert(mapCell, clazz);
				if (t != null) {
					dataList.add(t);
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("获取Excel数据失败", e);
		}
		return dataList;
	}
}
