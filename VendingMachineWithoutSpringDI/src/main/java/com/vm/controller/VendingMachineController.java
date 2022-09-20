package com.vm.controller;

import java.math.BigDecimal;
import java.util.List;

import com.vm.dto.Change;
import com.vm.dto.Item;
import com.vm.exception.DataValidationException;
import com.vm.exception.InsufficientFundsException;
import com.vm.exception.InvalidItemException;
import com.vm.exception.NoItemInventoryException;
import com.vm.exception.VendingMachinePersistenceException;
import com.vm.service.VendingMachineService;
import com.vm.view.VendingMachineView;



public class VendingMachineController {
	
    private VendingMachineService service;
    private VendingMachineView view;
    
    
  //Use constructor based dependency injection to connect service layer and view to the controller
  	public VendingMachineController(VendingMachineService service, VendingMachineView view) {
  		this.service = service;
  		this.view = view;
  	}
  	
  	//Run the main program
  	public void run() throws Exception {
  		
  		listItems();
  		BigDecimal fundsToBeAdd = view.addFunds();
  		String userChoice = view.chooseItem();
  		Change change = service.buyItem(userChoice, fundsToBeAdd);
  		view.displayChange(change.getCoins());
  	}
  	
  	
  	//Return all items from the vending machine
  	public void listItems() throws VendingMachinePersistenceException {
  		
  		List<Item> itemList = service.getAllItems();
  		view.displayAllItems(itemList);
  	}

  	

}
