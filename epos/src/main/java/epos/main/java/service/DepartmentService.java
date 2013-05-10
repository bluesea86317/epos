package epos.main.java.service;

import java.util.List;

import epos.main.java.dao.DepartmentDao;
import epos.main.java.vo.Department;
import epos.main.java.vo.ItemType;

public class DepartmentService {

	private DepartmentDao departmentDao;
	
	private ItemTypeService itemTypeService;
	
	public void addDepartment(Department department){
		getDepartmentDao().addDepartment(department);
	}
	
	public void addDepartments(List<Department> departments){
		getDepartmentDao().addDepartments(departments);
	}
	
	/**
	 * 删除部门时, 如果部门指定了某些菜品分类, 则不允许删除
	 * @param departmentId
	 * @throws Exception
	 */
	public void deleteDepartment(int departmentId) throws Exception{
		List<ItemType> itemTypes = itemTypeService.listItemTypesByDepartmentId(departmentId);		
		if(itemTypes != null && itemTypes.size() > 0){
			throw new Exception("部门'"+ getDepartmentDao().getDepartment(departmentId).getDepartmentName() +"'已经指定了菜品分类, 不允许删除该部门");
		}
		getDepartmentDao().deleteDepartment(departmentId);
	}
	
	/**
	 * 删除部门时, 如果部门指定了某些菜品分类, 则不允许删除
	 * @param departmentIds
	 * @throws Exception
	 */
	public void deleteDepartments(List<Integer> departmentIds) throws Exception{
		for(int departmentId : departmentIds){
			List<ItemType> itemTypes = itemTypeService.listItemTypesByDepartmentId(departmentId);		
			if(itemTypes != null && itemTypes.size() > 0){
				throw new Exception("部门'"+ getDepartmentDao().getDepartment(departmentId).getDepartmentName() +"'已经指定了菜品分类, 不允许删除该部门");
			}
		}
		getDepartmentDao().deleteDepartments(departmentIds);
	}
	
	public void updateDepartment(Department department){
		getDepartmentDao().updateDepartment(department);
	}
	
	public void updateDepartments(List<Department> departments){
		getDepartmentDao().updateDepartments(departments);
	}
	
	public List<Department> listDepartment(){
		return getDepartmentDao().listDepartment();
	}
	
	public Department getDepartment(int departmentId){
		return getDepartmentDao().getDepartment(departmentId);
	}
	
	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public ItemTypeService getItemTypeService() {
		return itemTypeService;
	}

	public void setItemTypeService(ItemTypeService itemTypeService) {
		this.itemTypeService = itemTypeService;
	}
}
