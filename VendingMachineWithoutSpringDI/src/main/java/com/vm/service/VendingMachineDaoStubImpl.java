package com.vm.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vm.dao.VendingMachineDao;
import com.vm.dto.Change;
import com.vm.dto.Item;
import com.vm.exception.VendingMachinePersistenceException;


public class VendingMachineDaoStubImpl implements VendingMachineDao {
	
	
	public Item item;
	public Change change;
	
	public VendingMachineDaoStubImpl() {
		item = new Item();
		item.setItemName("Fanta");
		item.setPrice(new BigDecimal("2.00"));
		item.setInventoryLevel(10);
		
	}
	
	public VendingMachineDaoStubImpl(Item tempItem, Change tempChange) {
		this.item = tempItem;
		this.change = tempChange;
	}
	
	@Override
	public Item addItem(Item item) throws VendingMachinePersistenceException {
		
		 if (item.equals(item.getItemName())) 
			 {  
			 return item;
			 }
		 
			 else {
				 return null;
			 }		 
		
	}
	
	@Override
	public Change buyItem(String name, BigDecimal cash) throws VendingMachinePersistenceException{
		if(name.equals(item.getItemName())) {
			item.setInventoryLevel(item.getInventoryLevel() - 1);
			return change;
		}
		else {
			return null;
		}
	}
	
	@Override
	public List<Item> getAllItems(){
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(item);
		return itemList;
	}
	
	@Override
	public Item getItem(String name) {
		if(name.equals(item.getItemName())) {
			return item;
		}
		else {
			return null;
		}
	}
	
	@Override
	public Change getChange(BigDecimal itemPrice, BigDecimal cash) {
		if(itemPrice.equals(item.getPrice())) {
			return change;
		}
		else {
			return null;
		}
	}


	
}
