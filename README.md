#commonexcel

##特性

> - 注解支持
> - 格式化支持

##code example

```java

#Book

	//PropertyCell 可以指定自定义cell处理器  具体实现看 format包      format=AAFormat.class  formatParam="xxx" 这个会作为AAFormat 的参数， order 参数为写入excel的顺序

	@PropertyCell(title="id")
	private Integer id;
	
	@PropertyCell(title="名称")
	private String name;
	
	@PropertyCell(title="数量")
	private Integer count;
	
	@PropertyCell(title="创建日期", formatParam="yyyy-MM-dd")
	private Date createDate;

	@Test
	public void readTest() {
		try {
			List<Book> bookList = ExcelHelper.readList(this.getClass().getResource("/test.xlsx").getPath(), Book.class);
			printList(bookList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void writeTest() {
		try {
			List<Book> bookList = new ArrayList<Book>();
			for (int i = 0; i < 5; i++) {
				Book book = new Book();
				book.setId(i);
				book.setName("书名_" + i);
				book.setCount(RandomUtils.nextInt(100));
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
	
	
```

@