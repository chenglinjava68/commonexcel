package commonexcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import com.commonexcel.core.ExcelHelper;

import commonexcel.bean.Book;

public class ExcelHelperTest {

	@Test
	public void readTest() {
		try {
			List<Book> bookList = ExcelHelper.readList(this.getClass().getResource("/test.xlsx").getPath(), Book.class);
			printList(bookList);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	@Test
	public void writeTest() {
		
		try {
			List<Book> bookList = new ArrayList<Book>();

			for (int i = 0; i < 5; i++) {

				Book book = new Book();

				book.setId(i);
				book.setName("书名_" + i);
				book.setCount(RandomUtils.nextInt(0,100));
				book.setCreateDate(new Date());
				bookList.add(book);
			}

			printList(bookList);
			System.out.println();
			String path = this.getClass().getResource("/").getPath();
			System.out.println("输出路径 : " + path);
			ExcelHelper.writeList(path + "test_" + System.currentTimeMillis() + ".xlsx", bookList);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void printList(List<Book> bookList) {
		for (Book book : bookList) {
			System.out.println(book);
		}
	}

}
