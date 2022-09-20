package com.vm.dao;

import com.vm.exception.VendingMachinePersistenceException;

public interface VendingMachineAuditDao {
	public void writeAuditEntry(String entry) throws VendingMachinePersistenceException;

}
