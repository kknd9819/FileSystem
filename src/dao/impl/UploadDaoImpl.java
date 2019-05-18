package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.UploadDao;
import model.Upload;
import util.JdbcUtil;

public class UploadDaoImpl implements UploadDao{

	@Override
	public void insert(Upload upload) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JdbcUtil.getCon();
			String sql = "insert into t_upload (upload_time,username,original_name,uuid_name,absolute_path) values (?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setTimestamp(1, new java.sql.Timestamp(upload.getUploadTime().getTime()));
			pstmt.setString(2, upload.getUsername());
			pstmt.setString(3, upload.getOriginalName());
			pstmt.setString(4, upload.getUuidName());
			pstmt.setString(5, upload.getAbsolutePath());
			pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, null, con);
		}
	}

	@Override
	public List<Upload> findAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JdbcUtil.getCon();
			String sql = "select * from t_upload order by upload_time desc";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<Upload> list = new ArrayList<>();
			while(rs.next()) {
				Upload upload = new Upload();
				upload.setId(rs.getInt("id"));
				upload.setUsername(rs.getString("username"));
				upload.setOriginalName(rs.getString("original_name"));
				upload.setUuidName(rs.getString("uuid_name"));
				upload.setAbsolutePath(rs.getString("absolute_path"));
				upload.setUploadTime(rs.getTimestamp("upload_time"));
				list.add(upload);
			}
			return list;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, null, con);
		}
		return null;
	}

	@Override
	public void update(Upload upload) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JdbcUtil.getCon();
			String sql = "update t_upload set original_name=?,uuid_name=?,absolute_path=?,upload_time=? where username=?"; 
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, upload.getOriginalName());
			pstmt.setString(2,upload.getUuidName());
			pstmt.setString(3, upload.getAbsolutePath());
			pstmt.setTimestamp(4, upload.getUploadTime());
			pstmt.setString(5, upload.getUsername());
			pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, null, con);
		}
	}

	@Override
	public List<Upload> findByFileNameAndUsername(String fileName, String username) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JdbcUtil.getCon();
			String sql = "select * from t_upload where original_name=? and username=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fileName);
			pstmt.setString(2, username);
			rs = pstmt.executeQuery();
			List<Upload> list = new ArrayList<>();
			while(rs.next()) {
				Upload upload = new Upload();
				upload.setId(rs.getInt("id"));
				upload.setUsername(rs.getString("username"));
				upload.setOriginalName(rs.getString("original_name"));
				upload.setUuidName(rs.getString("uuid_name"));
				upload.setAbsolutePath(rs.getString("absolute_path"));
				upload.setUploadTime(rs.getTimestamp("upload_time"));
				list.add(upload);
			}
			return list;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, null, con);
		}
		return null;
	}

}
