package dao;

import java.util.List;

import model.Upload;

public interface UploadDao {
	void insert(Upload upload);
	void update(Upload upload);
	List<Upload> findAll();
	List<Upload> findByFileNameAndUsername(String fileName,String username);
}
