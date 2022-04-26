package net.tfg.sharedlife.controller.spent;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.tfg.sharedlife.dto.DebtDTO;
import net.tfg.sharedlife.dto.SpentDTO;
import net.tfg.sharedlife.service.spent.SpentService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/spents")
public class SpentControllerImpl implements SpentController {

	private final static Logger logger = LoggerFactory.getLogger(SpentControllerImpl.class);

	@Autowired
	private SpentService spentService;
	
	@PostMapping("/create")
	@Override
	public ResponseEntity<?> createSpent(SpentDTO spent, boolean admin) {
		logger.info("Creating a new spent...");
		spentService.createTask(spent, admin);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/debts")
	@Override
	public ResponseEntity<List<DebtDTO>> getDebtsByUsername(@RequestParam("username") String username) {
		logger.info("Getting all the debts of the user with username: {}", username);
		List<DebtDTO> debts = new ArrayList<>();
		debts = spentService.getDebtsByUsername(username);
		return new ResponseEntity<>(debts, HttpStatus.OK);
	}

	@GetMapping("/id/{id}")
	@Override
	public ResponseEntity<SpentDTO> getSpentById(@PathVariable("id") Long id) {
		logger.info("Getting the spent with id: {}", id);
		SpentDTO spent = new SpentDTO();
		spent = spentService.getSpentById(id);
		return new ResponseEntity<>(spent, HttpStatus.OK);
	}

	
	
}
