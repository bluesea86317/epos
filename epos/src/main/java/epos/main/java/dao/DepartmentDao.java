package epos.main.java.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import epos.main.java.vo.Department;

public class DepartmentDao extends SqlMapClientDaoSupport {

	public void addDepartment(Department department){
		getSqlMapClientTemplate().insert("Department.addDepartment",department);
	}
	
	public void deleteDepartment(int departmentId){
		getSqlMapClientTemplate().delete("Department.deleteDepartment",departmentId);
	}
	
	public void updateDepartment(Department department){
		getSqlMapClientTemplate().update("Department.updateDepartment",department);
	}
	
	@SuppressWarnings("unchecked")
	public List<Department> listDepartment(){
		return (List<Department>)getSqlMapClientTemplate().queryForList("Department.listDepartment");
	}
}
