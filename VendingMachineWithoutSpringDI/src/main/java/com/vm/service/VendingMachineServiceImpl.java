package com.vm.service;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import com.vm.dao.VendingMachineAuditDao;
import com.vm.dao.VendingMachineDao;
import com.vm.dto.Change;
import com.vm.dto.Item;
import com.vm.exception.DataValidationException;
import com.vm.exception.InsufficientFundsException;
import com.vm.exception.InvalidItemException;
import com.vm.exception.NoItemInventoryException;
import com.vm.exception.VendingMachinePersistenceException;



public class VendingMachineServiceImpl implements VendingMachineService{
	
	
	private VendingMachineDao dao;
	private VendingMachineAuditDao auditDao;
	
	
	//Initializes a constructor to inject dao and auditDao objects
	public VendingMachineServiceImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
		this.dao = dao;
		this.auditDao = auditDao;
	}
	
	
	//Add item to the vending machine
	@Override
	public void addItem(Item item) throws DataValidationException, VendingMachinePersistenceException {
		
		if(item.getItemName() == null || item.getPrice() == null || item.getInventoryLevel() < 0) {
			throw new DataValidationException("ERROR: item must have a name, price and valid quantity");
		}
		else {
			dao.addItem(item);
		}
	}
	//Returns all items from the vending machine and writes the time that this occured.
	@Override
	public List<Item> getAllItems() throws VendingMachinePersistenceException{
		auditDao.writeAuditEntry("All items have been retrieved");
		
        List<Item> items = dao.getAllItems();

		 Iterator<Item> itr = items.iterator();
	        while (itr.hasNext()) {
	            Item item = itr.next();
	            if (item.getInventoryLevel() <= 0) {
	                itr.remove();
	            }
	        }

	        return items;
	    
		
	}
	
	//Buy an item from the vending machine and return the change
		@Override
		public Change buyItem(String name, BigDecimal cash) throws
			VendingMachinePersistenceException, InsufficientFundsException, 
			NoItemInventoryException, DataValidationException, InvalidItemException{
			
			userInput(name, cash);
			Change change = null;
			
			if(dao.getItem(name) == null) {
				throw new InvalidItemException("Sorry, but this item does not exist in the vending machine");
			}
			
			BigDecimal itemPrice = dao.getItem(name).getPrice();
			int inventoryLevel = dao.getItem(name).getInventoryLevel();
			
			if(itemPrice.compareTo(cash) > 0) {
				throw new InsufficientFundsException("Sorry, but you don't have enough funds to buy this item\n");
			}
			else if(inventoryLevel == 0) {
				throw new NoItemInventoryException("Sorry, but this item is currently not in the inventory");
			}
			else {
				auditDao.writeAuditEntry("Items were purchased");
				change = dao.buyItem(name, cash);
			}
			return change;
			
		}
	
	//Checks whether the data that the user entered is valid
	private void userInput(String name, BigDecimal cash) throws DataValidationException {
		
		if(name == null || name.trim().length() == 0|| cash == null || cash.compareTo(BigDecimal.valueOf(0.00)) < 0) {
			throw new DataValidationException("ERROR: You must insert some cash and insert an existing item name\n");
		}
		return;
	}


	@Override
	public Item getItem(String name) throws VendingMachinePersistenceException {
	          
	               return dao.getItem(name);
	             
	}
}
