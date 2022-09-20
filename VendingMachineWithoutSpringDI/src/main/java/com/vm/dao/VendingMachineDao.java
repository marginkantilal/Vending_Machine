package com.vm.dao;

import java.math.BigDecimal;
import java.util.List;

import com.vm.dto.Change;
import com.vm.dto.Item;
import com.vm.exception.VendingMachinePersistenceException;

public interface VendingMachineDao {
	Change buyItem(String name, BigDecimal price) throws VendingMachinePersistenceException;
	List<Item> getAllItems() throws VendingMachinePersistenceException;
	Item addItem(Item item) throws VendingMachinePersistenceException;
	Item getItem(String name) throws VendingMachinePersistenceException;
	Change getChange(BigDecimal itemPrice, BigDecimal cash) throws VendingMachinePersistenceException;
}
