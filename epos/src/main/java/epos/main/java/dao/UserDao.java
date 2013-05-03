package epos.main.java.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import epos.main.java.vo.User;

public class UserDao extends SqlMapClientDaoSupport {

	public User getUserByNameAndPsw(String userName, String password){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userName", userName);
		param.put("password", password);
		return (User)getSqlMapClientTemplate().queryForObject("User.getUserByNameAndPsw",param);
	} 
}
