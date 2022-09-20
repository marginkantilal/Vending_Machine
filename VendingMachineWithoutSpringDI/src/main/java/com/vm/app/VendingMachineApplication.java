package com.vm.app;

import com.vm.controller.VendingMachineController;
import com.vm.dao.VendingMachineAuditDao;
import com.vm.dao.VendingMachineAuditFileImpl;
import com.vm.dao.VendingMachineDao;
import com.vm.dao.VendingMachineDaoFileImpl;
import com.vm.service.VendingMachineServiceImpl;
import com.vm.view.UserIo;
import com.vm.view.UserIoConsoleImpl;
import com.vm.view.VendingMachineView;

public class VendingMachineApplication {

	public static void main(String[] args) throws Exception {
		 // Instantiate the UserIO implementation
		UserIo myIo = new UserIoConsoleImpl();
	    // Instantiate the View and wire the UserIO implementation into it
		VendingMachineView myView = new VendingMachineView(myIo);
	    // Instantiate the DAO
		VendingMachineDao myDao = new VendingMachineDaoFileImpl();
		// Instantiate the Audit DAO
	    VendingMachineAuditDao myAuditDao = new VendingMachineAuditFileImpl();
	    // Instantiate the Service Layer and wire the DAO and Audit DAO into it
		VendingMachineServiceImpl myService = new VendingMachineServiceImpl(myDao, myAuditDao);
	    // Instantiate the Controller and wire the Service Layer into it
		VendingMachineController myController = new VendingMachineController(myService, myView);
	    // Kick off the Controller
		myController.run();
	}
}
