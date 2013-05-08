package epos.main.java.dao;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import epos.main.java.utils.DBUtils;
import epos.main.java.vo.Department;

public class DepartmentDao extends SqlMapClientDaoSupport {

	public void addDepartment(Department department){
		getSqlMapClientTemplate().insert("Department.addDepartment",department);
	}
	
	public void addDepartments(final List<Department> departments){
		DBUtils.excuteBatchInsert(getSqlMapClientTemplate(), "Department.addDepartment", departments);
	}
	
	public void deleteDepartment(int departmentId){
		getSqlMapClientTemplate().delete("Department.deleteDepartment",departmentId);
	}
	
	public void deleteDepartments(List<Integer> departmentIds){
		DBUtils.excuteBatchDelete(getSqlMapClientTemplate(), "Department.deleteDepartment", departmentIds);
	}
	
	public void updateDepartment(Department department){
		getSqlMapClientTemplate().update("Department.updateDepartment",department);
	}
	
	public void updateDepartments(List<Department> departments){
		DBUtils.excuteBatchUpdate(getSqlMapClientTemplate(), "Department.updateDepartment", departments);
	}
	
	@SuppressWarnings("unchecked")
	public List<Department> listDepartment(){
		return (List<Department>)getSqlMapClientTemplate().queryForList("Department.listDepartment");
	}
}
