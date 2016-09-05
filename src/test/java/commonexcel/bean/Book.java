package commonexcel.bean;

import java.util.Date;

import com.commonexcel.core.annotion.PropertyCell;

public class Book {
	
	@PropertyCell(title="id")
	private Integer id;
	
	@PropertyCell(title="名称")
	private String name;
	
	@PropertyCell(title="数量")
	private Integer count;
	
	@PropertyCell(title="创建日期", formatParam="yyyy-MM-dd")
	private Date createDate;
	
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		if(createDate == null){
			return "Book [id=" + id + ", name=" + name + ", count=" + count + ", createDate=" + createDate + "]";
		}else{
			return "Book [id=" + id + ", name=" + name + ", count=" + count + ", createDate=" + createDate.toLocaleString() + "]";

		}
	}

	
}
