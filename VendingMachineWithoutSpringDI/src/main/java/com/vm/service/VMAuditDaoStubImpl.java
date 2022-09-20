package com.vm.service;

import com.vm.dao.VendingMachineAuditDao;
import com.vm.exception.VendingMachinePersistenceException;

public class VMAuditDaoStubImpl implements VendingMachineAuditDao {

	@Override
	public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
		
	}

}
