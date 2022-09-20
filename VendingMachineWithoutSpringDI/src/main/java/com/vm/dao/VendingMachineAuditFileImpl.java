package com.vm.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.vm.exception.VendingMachinePersistenceException;


public class VendingMachineAuditFileImpl implements VendingMachineAuditDao {

		public static final String AUDIT_FILE = "Audit.txt";
		
		public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
	        PrintWriter out;
	       
	        try {
	            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
	        } catch (IOException e) {
	            throw new VendingMachinePersistenceException("Could not persist audit information.", e);
	        }
	        
	        LocalDateTime timestamp = LocalDateTime.now();
	        out.println(timestamp.toString() + " : " + entry);
	        out.flush();
		
	}

	
}
