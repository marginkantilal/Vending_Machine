package com.vm.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class UserIoConsoleImpl implements UserIo{
	Scanner sc = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public int readInteger(String prompt) {
        print(prompt);
		int integer = sc.nextInt();
		return integer;
    }

    @Override
    public String readString(String prompt) {
    	   print(prompt);
   		String myString = sc.nextLine();
   		return myString;

    }

public BigDecimal readBigDecimal(String prompt) {
		
		print(prompt);
		String myString = sc.nextLine();
		BigDecimal value = new BigDecimal(myString);
		return value.setScale(2, RoundingMode.HALF_UP);
	}

    

   

   

}
