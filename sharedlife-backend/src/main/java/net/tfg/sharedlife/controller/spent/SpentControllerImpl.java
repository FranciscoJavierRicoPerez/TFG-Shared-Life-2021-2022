package net.tfg.sharedlife.controller.spent;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@GetMapping("/byUsername")
	@Override
	public ResponseEntity<List<SpentDTO>> getSpentsByUsername(@RequestParam("username") String username) {
		logger.info("Getting all the spents created by ther user with username {}", username);
		List<SpentDTO> spents = new ArrayList<>();
		spents = spentService.getSpentsByUsername(username);
		return new ResponseEntity<>(spents, HttpStatus.OK);
	}

	@GetMapping("/debts/id/{id}")
	@Override
	public ResponseEntity<List<DebtDTO>> getDebtsBySpentId(@PathVariable("id") Long id) {
		logger.info("Getting debts by the spent with the id: {}", id);
		List<DebtDTO> debts = new ArrayList<>();
		debts = spentService.getDebtsBySpentId(id);
		return new ResponseEntity<>(debts, HttpStatus.OK);
	}

	@GetMapping("/byHomeId/{id}")
	@Override
	public ResponseEntity<List<SpentDTO>> getAllSpentsByHomeId(@PathVariable("id") Long id){
		logger.info("Getting spents of the home with id: {}", id);
		List<SpentDTO> spents = new ArrayList<>();
		spents = spentService.getAllSpentsByHomeId(id);
		return new ResponseEntity<>(spents, HttpStatus.OK);
	}
	
	@PutMapping("/debt/{id}/paid")
	@Override
	public ResponseEntity<?> updatePaidStatus(@PathVariable("id") Long id, @RequestBody boolean paid){
		logger.info("Changing the value of the paid");
		spentService.updatePaidStatus(id, paid);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@DeleteMapping("/{id}/delete")
	@Override
	public ResponseEntity<?> deleteSpentAndDebts(Long id) {
		logger.info("Deleting the spent with id {} and his associated debts");
		spentService.deleteSpentAndDebts(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
