package com.vm.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.vm.dto.Change;
import com.vm.dto.Item;
import com.vm.dto.Change.Coin;
import com.vm.exception.VendingMachinePersistenceException;


public class VendingMachineDaoFileImpl implements VendingMachineDao{
	
	public static String VENDING_MACHINE_FILE;
	public static final String DELIMITER = "::";
	
	public VendingMachineDaoFileImpl() {
		VENDING_MACHINE_FILE = "VendingMachineItems.txt";

	}
	public VendingMachineDaoFileImpl(String vendingMachineTextFile) {
		VENDING_MACHINE_FILE = vendingMachineTextFile;
	}
	
	Map<String, Item> Inventory = new HashMap<>();

	//Buy item
	@Override
	public Change buyItem(String itemName, BigDecimal cash) throws VendingMachinePersistenceException {
		loadItem();
		Item userItem = Inventory.get(itemName);
		userItem.setInventoryLevel(userItem.getInventoryLevel() - 1);
		Inventory.replace(itemName, userItem);
		
		Change change = getChange(userItem.getPrice(), cash);
		writeItem();
		return change;
	}
	
	//Adds an item to the vending machine
	@Override
	public Item addItem(Item item) throws VendingMachinePersistenceException {
		loadItem();
		Inventory.put(item.getItemName(), item);
		writeItem();
		return item;
	}

	//Gets all the items from the vending machine
	@Override
	public List<Item> getAllItems() throws VendingMachinePersistenceException{
		loadItem();
		return new ArrayList<Item>(Inventory.values());
	}
	

	// Get single item
	@Override
	public Item getItem(String name) throws VendingMachinePersistenceException {
		return Inventory.get(name);
	}
	
	//Gets the required change
	@Override
	public Change getChange(BigDecimal itemPrice, BigDecimal cash) throws VendingMachinePersistenceException{
		
		Change change = new Change();
		List<Coin> coins = new ArrayList<Coin>();
		BigDecimal changeAmount = cash.subtract(itemPrice);
		change.setTotalChange(changeAmount);
		
		while(changeAmount.compareTo(BigDecimal.ZERO) != 0) {
			
			if(changeAmount.compareTo(BigDecimal.valueOf(0.25)) >= 0) {
				changeAmount = changeAmount.subtract(BigDecimal.valueOf(0.25));
				coins.add(change.getQuarter());
			}
			else if(changeAmount.compareTo(BigDecimal.valueOf(0.10)) >= 0) {
				changeAmount = changeAmount.subtract(BigDecimal.valueOf(0.10));
				coins.add(change.getDime());
			}
			else if(changeAmount.compareTo(BigDecimal.valueOf(0.05)) >= 0) {
				changeAmount = changeAmount.subtract(BigDecimal.valueOf(0.05));
				coins.add(change.getNickel());
			}
			else if(changeAmount.compareTo(BigDecimal.valueOf(0.01)) >= 0) {
				changeAmount = changeAmount.subtract(BigDecimal.valueOf(0.01));
				coins.add(change.getPenny());
			}
		}
		change.setCoins(coins);
		return change;
	}
	
	
	//Translate data from file to an object in memory
		private Item unmarshallItem(String ItemAsText) {
			
			String[] ItemAsElements = ItemAsText.split(DELIMITER);
			String itemName = ItemAsElements[0];
			Item itemFromFile = new Item();
			itemFromFile.setItemName(itemName);
			
			BigDecimal itemPrice = new BigDecimal(ItemAsElements[1]);
			itemFromFile.setPrice(itemPrice.setScale(2, RoundingMode.HALF_UP));
			itemFromFile.setInventoryLevel(Integer.parseInt(ItemAsElements[2]));
			
			return itemFromFile;
		}
		
		//Put all unmarshalled data into the vending machine inventory
		private void loadItem() throws VendingMachinePersistenceException{
			
			Scanner sc;
			
			try {
				sc = new Scanner(new BufferedReader(new FileReader(VENDING_MACHINE_FILE)));
			}
			catch(FileNotFoundException e) {
				throw new VendingMachinePersistenceException("Could not locate the file", e);
			}
			
			String currentLine;
			Item currentItem;
			
			while(sc.hasNextLine()) {
				currentLine = sc.nextLine();
				currentItem = unmarshallItem(currentLine);
				Inventory.put(currentItem.getItemName(), currentItem);
			}
			sc.close();
		}
		
		//Translate data from object in memory into a text file.
		private String marshallItem(Item item) {
			
			String ItemAsText = item.getItemName() + DELIMITER;
			ItemAsText += item.getPrice() + DELIMITER;
			ItemAsText += item.getInventoryLevel();
			
			return ItemAsText;
		}
		
		//Write marshalled data into the text file.
		private void writeItem() throws VendingMachinePersistenceException{
			
			PrintWriter out;
			
			try {
				out = new PrintWriter(new FileWriter(VENDING_MACHINE_FILE));
			}
			catch(Exception e) {
				throw new VendingMachinePersistenceException("Could not save DVD data", e);
			}
			
			String ItemAsText;
			List<Item> ItemList = this.getAllItems();
			for(Item currentItem : ItemList) {
				ItemAsText = marshallItem(currentItem);
				out.println(ItemAsText);
				out.flush();
			}
			out.close();
		}
}
