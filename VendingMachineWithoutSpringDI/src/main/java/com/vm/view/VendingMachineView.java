package com.vm.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


import com.vm.dto.Change;
import com.vm.dto.Item;
import com.vm.dto.Change.Coin;




public class VendingMachineView {
    private UserIo io;
    
    public VendingMachineView(UserIo io) {
        this.io = io;
    }
    
  //Display all items in the VM
  	public void displayAllItems(List<Item> allItems) {
  		io.print("Vending machine");
  		io.print("This vending machine contains the following items:");
  		
  		for(Item item : allItems) {
  			io.print("Item name: " + item.getItemName());
  			io.print("Price: " + item.getPrice());
  			io.print("Items left: " + item.getInventoryLevel());
  			io.print("");
  		}
  	}
  	
  //Add fund into vending machine
  	public BigDecimal addFunds() {
  		
  		String userInput = io.readString("Please insert some money into the vending machine");
  		io.print("");
  		BigDecimal userFunds = new BigDecimal(userInput);
  		userFunds.setScale(2, RoundingMode.HALF_UP);
  		return userFunds;
  	}
  	
  //Select an item from a vending machine
  	public String chooseItem() {
  		
  		String userInput = io.readString("Please select the item that you want to buy");
  		io.print("");
  		return userInput;
  	}
   //change returned to the user. Change must be displayed as the number of quarters, dimes, nickels, and pennies returned to the user.
  //Returns the change as number of coins of each category
  	public void displayChange(List<Change.Coin> typeOfcoins) {
  		
  		int penny_counter = 0;
  		int nickel_counter = 0;
  		int dime_counter = 0;
  		int quarter_counter = 0;
  		
  		for(Change.Coin coin : typeOfcoins) {
  			if (coin == Coin.PENNY) {
  				penny_counter += 1;
  			}
  			else if(coin == Coin.NICKEL) {
  				nickel_counter += 1;
  			}
  			else if(coin == Coin.DIME) {
  				dime_counter += 1;
  			}
  			else if(coin == Coin.QUARTER) {
  				quarter_counter += 1;
  			}
  		}
  		io.print("Your change contains the following:");
  		io.print(penny_counter + " Pennies");
  		io.print(nickel_counter + " Nickels");
  		io.print(dime_counter + " Dimes");
  		io.print(quarter_counter + " Quarters");
  		
  	}
  	
}
