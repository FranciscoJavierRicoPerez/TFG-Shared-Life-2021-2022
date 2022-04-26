package net.tfg.sharedlife.controller.spent;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.tfg.sharedlife.dto.DebtDTO;
import net.tfg.sharedlife.dto.SpentDTO;

public interface SpentController {

	@PostMapping("/create")
	ResponseEntity<?> createSpent(@RequestBody SpentDTO spent, @RequestParam("admin") boolean admin);
	
	@GetMapping("/debts")
	ResponseEntity<List<DebtDTO>> getDebtsByUsername(@RequestParam("username") String username);
	
	@GetMapping("/id/{id}")
	ResponseEntity<SpentDTO> getSpentById(@PathVariable("id") Long id);
	
	@GetMapping("/byUsername")
	ResponseEntity<List<SpentDTO>> getSpentsByUsername(@RequestParam("username") String username);
	
	@GetMapping("/debts/id/{id}")
	ResponseEntity<List<DebtDTO>> getDebtsBySpentId(@PathVariable("id") Long id);
	
}
