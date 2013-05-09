package epos.main.java.service;

import java.util.List;

import epos.main.java.dao.UserDao;
import epos.main.java.vo.User;

public class UserService {

	private UserDao userDao;
	
	public boolean validateUserNameAndPassword(String userName, String password){
		User user = userDao.getUserByNameAndPsw(userName, password);
		return user != null ? true : false;
	}

	public User getUserByNameAndPassword(String userName, String password){
		return userDao.getUserByNameAndPsw(userName, password);
	}
	
	public List<User> listUser(){
		return userDao.listUsers();
	}
	
	public void addUser(User user){
		userDao.addUser(user);
	}
	
	public void addUsers(List<User> users){
		userDao.addUsers(users);
	}
	
	public void updateUser(User user){
		userDao.updateUser(user);
	}
	
	public void updateUsers(List<User> users){
		userDao.updateUsers(users);
	}
	
	public void deleteUser(int userId){
		userDao.deleteUser(userId);
	}
	
	public void deleteUsers(List<Integer> userIds){
		userDao.deleteUsers(userIds);
	}
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
