package com.mycom.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.mycom.domain.Subject;

public class JdbcUtil {
  @Resource
  private BasicDataSource dataSource;

  /**
   * 保存对象
   */
  public void save(Subject sub){
	  Connection conn = null;
	  try {
		conn = dataSource.getConnection();
		String sql = "insert into subject(id,msg) values(?,?)";
		PreparedStatement prep = conn.prepareStatement(sql);
		prep.setInt(1, sub.getId());
		prep.setString(2, sub.getMsg());
		prep.executeUpdate();
	} catch (Exception e) {
	} finally{
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}
	  
  }

}
