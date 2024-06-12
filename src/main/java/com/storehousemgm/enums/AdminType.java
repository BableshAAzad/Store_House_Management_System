package com.storehousemgm.enums;

import java.util.List;

public enum AdminType {
     SUPER_ADMIN(List.of(
    		 Privilege.CREATE_ADMIN,
    		 Privilege.CREATE_STOREHOUSE,
    		 Privilege.CREATE_ADDRESS,
    		 Privilege.CREATE_STORAGE,
    		 
    		 Privilege.READ,
    		 
    		 Privilege.UPDATE_ADMIN,
    		 Privilege.UPDATE_STOREHOUSE,
    		 Privilege.UPDATE_ADDRESS,
    		 Privilege.UPDATE_STORAGE,
    			
    		 Privilege.DELETE_ADMIN,
    		 Privilege.DELETE_STOREHOUSE,
    		 Privilege.DELETE_ADDRESS,
    		 Privilege.DELETE_STORAGE)),
     
     
     ADMIN(List.of(
    		 Privilege.CREATE_STORAGE,
    		 
    		 Privilege.READ,
    		 
    		 Privilege.UPDATE_ADMIN,
    		 Privilege.UPDATE_STORAGE,
    			
    		 Privilege.DELETE_STORAGE));
     
     private List<Privilege> privileges;
     
     AdminType(List<Privilege> privileges){
    	 this.privileges = privileges;
     }
     
     public List<Privilege> getPrivileges(){
    	 return this.privileges;
     }
     
}
