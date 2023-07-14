package coding.mentor.entity;
import java.util.Date;


public class Order {
	 private int id;
	 private int accountId;
	 private Date date;
	 private boolean approved;
	 
	 
	 public Order () {
		 
	 }
	 
	public Order(int accountId, boolean approved) {
		super();
		this.accountId = accountId;
		this.approved = approved;
	}

	public Order(int id, int accountId, Date date, boolean approved) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.date = date;
		this.approved = approved;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	 
	 
	 
	 
	 
	 
	 
}