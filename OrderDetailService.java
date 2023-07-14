package coding.mentor.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import coding.mentor.db.util.DBUtil;
import coding.mentor.entity.OrderDetail;

public class OrderDetailService {

	public void newOrderDetail (OrderDetail orderDetail) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {

			conn = DBUtil.makeConnection();
		
			String sql = "INSERT INTO `order_details`(order_id, book_id) value(?,?)";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, orderDetail.getOrderId());
			ps.setInt(2, orderDetail.getBookId());
		
			ps.execute();
			
	
	} finally {

		if (ps != null) {
			ps.close();
		}
		if (conn != null) {
			conn.close();
		}

	}
	}
}