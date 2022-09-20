package com.vm.view;

import java.math.BigDecimal;

public interface UserIo {
	

	
	
	void print(String message);
	int readInteger(String msg);
	String readString(String msg);
	BigDecimal readBigDecimal(String msg);

}