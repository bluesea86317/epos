package epos.main.java.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
	
	public User getUserByName(String userName){
		return userDao.getUserByName(userName);
	}
	
	public List<User> listUser(){
		return userDao.listUsers();
	}
	
	public void addUser(User user){
		userDao.addUser(user);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackForClassName={"java.lang.Exception"})
	public void addUsers(List<User> users) throws Exception{
		for(User user : users){
			if(this.getUserByName(user.getUserName()) != null){
				throw new Exception("已经存在账号为'" + user.getUserName() + "'的用户, 不能重复添加");
			}
			userDao.addUser(user);
		}
	}
	
	public void updateUser(User user){
		userDao.updateUser(user);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackForClassName={"java.lang.Exception"})
	public void updateUsers(List<User> users) throws Exception{
		for(User user : users){
			User existUser = this.getUserByName(user.getUserName());
			if(existUser != null && user.getUserId() != existUser.getUserId()){
				throw new Exception("已经存在账号为'" + user.getUserName() + "'的用户, 不能修改成与其同名的账号");
			}
			userDao.updateUser(user);
		}
	}
	
	public void deleteUser(int userId){
		userDao.deleteUser(userId);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
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
