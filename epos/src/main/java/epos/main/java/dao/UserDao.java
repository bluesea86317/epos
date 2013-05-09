package epos.main.java.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import epos.main.java.utils.DBUtils;
import epos.main.java.vo.User;

public class UserDao extends SqlMapClientDaoSupport {

	public User getUserByNameAndPsw(String userName, String password){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userName", userName);
		param.put("password", password);
		return (User)getSqlMapClientTemplate().queryForObject("User.getUserByNameAndPsw",param);
	}
	
	@SuppressWarnings("unchecked")
	public List<User> listUsers(){
		return (List<User>)getSqlMapClientTemplate().queryForList("User.listUsers");
	}
	
	public void updateUser(User user){
		getSqlMapClientTemplate().update("User.updateUser", user);
	}
	
	public void updateUsers(List<User> users){
		DBUtils.excuteBatchUpdate(getSqlMapClientTemplate(), "User.updateUser", users);
	}
	
	public void deleteUser(int userId){
		getSqlMapClientTemplate().delete("User.deleteUser", userId);
	}
	
	public void deleteUsers(List<Integer> userIds){
		DBUtils.excuteBatchDelete(getSqlMapClientTemplate(), "User.deleteUsers", userIds);
	}
	
	public void addUser(User user){
		getSqlMapClientTemplate().insert("User.addUser", user);
	}
	
	public void addUsers(List<User> users){
		DBUtils.excuteBatchInsert(getSqlMapClientTemplate(), "User.addUser", users);
	}
	
}
