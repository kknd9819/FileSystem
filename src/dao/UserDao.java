package dao;

import model.User;

/**
 * DAO 数据访问对象  Data Access Object
 * @author Troublemaker
 */
public interface UserDao {
	User findByUsernameAndPassword(String username,String password);
	User findByUsername(String username);
	
	int insert(User user);
}
