package epos.main.java.service;

import java.util.List;

import epos.main.java.dao.FlavorDao;
import epos.main.java.vo.Flavor;

public class FlavorService {

	private FlavorDao flavorDao;
	
	public void addItemType(Flavor flavor){
		getFlavorDao().addFlavor(flavor);
	}
	
	public void addItemTypes(List<Flavor> flavors){
		getFlavorDao().addFlavors(flavors);
	}
	
	public void flavorId(int flavorId){
		getFlavorDao().deleteFlavor(flavorId);
	}
	
	public void deleteItemTypes(List<Integer> flavorIds){
		getFlavorDao().deleteFlavors(flavorIds);
	}
	
	public void updateFlavor(Flavor flavor){
		getFlavorDao().updateFlavor(flavor);
	}
	
	public void updateFlavors(List<Flavor> flavors){
		getFlavorDao().updateFlavors(flavors);
	}
	
	public List<Flavor> listFlavors(){
		return getFlavorDao().listFlavors();
	}

	public FlavorDao getFlavorDao() {
		return flavorDao;
	}

	public void setFlavorDao(FlavorDao flavorDao) {
		this.flavorDao = flavorDao;
	}
}
