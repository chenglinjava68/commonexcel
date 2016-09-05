package commonexcel;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

public class PoiTest {

	@Test
	public void testRead() {

		File workbookFile = new File(this.getClass().getResource("/test.xlsx").getPath());

		try {
			
			FileInputStream fis = new FileInputStream(workbookFile);
			Workbook workbook = WorkbookFactory.create(fis);
			
			Sheet sheet = workbook.getSheetAt(0);
			
			int rowCount = sheet.getLastRowNum();
			
			if(rowCount < 1){
				System.out.println("不存在数据");
				return ;
			}
			
			Row titleRow = sheet.getRow(0);
			
			int cellCount  = titleRow.getLastCellNum();
			
			System.out.println("-------------标题-------------------");
			for(int i = 0; i < cellCount; i++){
				System.out.print(titleRow.getCell(i).getStringCellValue() + "\t");
			}
			System.out.println();
			System.out.println("--------------内容--------------------" + rowCount);
			for(int i = 1; i < (rowCount + 1); i++){
				Row contentRow = sheet.getRow(i);
				for(int j = 0; j < cellCount; j++){
					System.out.print(contentRow.getCell(j) + "\t");
				}
				System.out.println();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
