package com.vm.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.vm.dto.Change;
import com.vm.dto.Item;
import com.vm.exception.VendingMachinePersistenceException;

class VendingMachineDaoFileImplTest {
	
	VendingMachineDao testDao;
	
	public VendingMachineDaoFileImplTest() {
		
	}


	 @BeforeEach
	    public void setUp() throws IOException {
	    	String testFile = "TestVMItems.txt";
	    	new FileWriter(testFile);
	    	testDao = new VendingMachineDaoFileImpl(testFile);
	    }

	// Testing  add single item	
	@Test
	public void testAddBuyItem() throws VendingMachinePersistenceException  {
		
		//Arrange
	    // Create our method test inputs
		Item item = new Item();
		item.setItemName("Walkers");
		item.setPrice(new BigDecimal("1.00"));
		item.setInventoryLevel(10);
	    //  Add the item to the DAO
		testDao.addItem(item);
		
		//Act
	    // Get the item from the DAO
		Item receivedAddedItem = testDao.getItem(item.getItemName());
		
		//Assert
	    // Check the data is equal
		assertEquals(receivedAddedItem.getItemName(), item.getItemName(), "Checking item names");
		assertEquals(receivedAddedItem.getPrice(), item.getPrice(), "Checking item prices");
		assertEquals(receivedAddedItem.getInventoryLevel(), item.getInventoryLevel(), "Check inventory levels and should be 10");
		
		//Add Cash
		BigDecimal cash = new BigDecimal("3.00");
		
		//Test change
		Change giveChange = testDao.buyItem(item.getItemName(), cash);
		//Get item price from Dao
		BigDecimal price = item.getPrice();
		//Sell item
		Item getItem = testDao.getItem(item.getItemName());
		
	    // Check the data is equal
		assertEquals(getItem.getInventoryLevel() + 1, item.getInventoryLevel(), "Inventory level must be reduced by 1");
		assertEquals(cash.subtract(price), giveChange.getTotalChange(), "Change returned must be correct");
	}
	
	
	@Test
	public void testAddBuyItems() throws VendingMachinePersistenceException  {
	    // Create our first item
		Item item = new Item();
		item.setItemName("Walker");
		item.setPrice(new BigDecimal("1.00"));
		item.setInventoryLevel(7);
	    // Create our second student
		Item Moreitem = new Item();
		Moreitem.setItemName("Sprite");
		Moreitem.setPrice(new BigDecimal("0.80"));
		Moreitem.setInventoryLevel(8);
		
	    // Add both our items to the DAO
		testDao.addItem(item);
		testDao.addItem(Moreitem);
		
	    // Retrieve the list of all items within the DAO
		List<Item>items = testDao.getAllItems();
		
	    // First check the general contents of the list
		assertNotNull(items, "The list should not be empty or null");
		assertEquals(2, items.size(), "It should only have 2 items in the list");
	    // Then the specifics
		assertTrue(items.contains(item), "Item Walker should be in the list");
		assertTrue(items.contains(Moreitem), "Item Sprite should be in the list");
		
		//Add money
		BigDecimal cash = new BigDecimal("5.00");
		
		// Get change for item one
		Change getChangeForItemOne = testDao.buyItem(item.getItemName(), cash);
		// Get change for item two
		Change getChangeForItemTwo = testDao.buyItem(Moreitem.getItemName(), cash);
		
		//// Get price for both items
		BigDecimal priceForItemOne = item.getPrice();
		BigDecimal priceForItemTwo = Moreitem.getPrice();
		
		//Checks if the item is available by its name
		Item getItemOne = testDao.getItem(item.getItemName());
		Item getItemTwo = testDao.getItem(Moreitem.getItemName());


		//Checks if the quality level by purchasing for each items
		assertEquals(getItemOne.getInventoryLevel() + 1, item.getInventoryLevel(), "Quantity should be reduced by 1");
		assertEquals(getItemTwo.getInventoryLevel() + 1, Moreitem.getInventoryLevel(), "Quantity should be reduced by 1");
		// Checks change amount for each item.
		assertEquals(cash.subtract(priceForItemOne), getChangeForItemOne.getTotalChange(), "Correct Change  must be given after buying item one");
		assertEquals(cash.subtract(priceForItemTwo), getChangeForItemTwo.getTotalChange(), "Correct Change  must be given");

	}
	

}
