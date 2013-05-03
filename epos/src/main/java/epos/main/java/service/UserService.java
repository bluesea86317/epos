package epos.main.java.service;

import epos.main.java.dao.UserDao;
import epos.main.java.vo.User;

public class UserService {

	private UserDao userDao;
	
	public boolean validateUserNameAndPassword(String userName, String password){
		User user = userDao.getUserByNameAndPsw(userName, password);
		return user != null ? true : false;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
