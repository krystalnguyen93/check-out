package coding.mentor.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import coding.mentor.db.util.DBUtil;
import coding.mentor.entity.Order;

public class OrderService {

	public int newOrder(Order order) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.makeConnection();
			String sql = "INSERT INTO `order` (account_id, date, approved) VALUES (?,?,?)";
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, order.getAccountId());
			ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
			ps.setBoolean(3, order.isApproved());
			ps.executeUpdate();

			rs = ps.getGeneratedKeys();

			if (rs.next()) {
			    int id = rs.getInt(1);
			    return id;
			}

			return 0;

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