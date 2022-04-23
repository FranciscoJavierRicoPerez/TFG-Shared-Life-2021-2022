package net.tfg.sharedlife.controller.spent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.tfg.sharedlife.dto.SpentDTO;
import net.tfg.sharedlife.service.spent.SpentService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/spents")
public class SpentControllerImpl implements SpentController {

	private final static Logger logger = LoggerFactory.getLogger(SpentControllerImpl.class);

	@Autowired
	private SpentService spentService;
	
	@Override
	public ResponseEntity<?> createSpent(SpentDTO spent, boolean admin) {
		logger.info("Creating a new spent...");
		spentService.createTask(spent, admin);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	
	
}
