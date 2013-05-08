package epos.main.java.service;

import java.util.List;

import epos.main.java.dao.DepartmentDao;
import epos.main.java.vo.Department;

public class DepartmentService {

	private DepartmentDao departmentDao;
	
	public void addDepartment(Department department){
		getDepartmentDao().addDepartment(department);
	}
	
	public void deleteDepartment(int departmentId){
		getDepartmentDao().deleteDepartment(departmentId);
	}
	
	public void updateDepartment(Department department){
		getDepartmentDao().updateDepartment(department);
	}
	
	public List<Department> listDepartment(){
		return getDepartmentDao().listDepartment();
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
}
