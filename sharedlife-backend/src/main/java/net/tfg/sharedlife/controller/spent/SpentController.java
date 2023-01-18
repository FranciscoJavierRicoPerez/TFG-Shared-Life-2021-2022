package net.tfg.sharedlife.controller.spent;

import java.util.List;

import net.tfg.sharedlife.dto.SpentCheckPaidDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@GetMapping("/home/{id}/username")
	ResponseEntity<List<SpentDTO>> getSpentsByUsernameAndHomeId(@RequestParam("username") String username, @PathVariable("id") Long id);
	
	@GetMapping("/debts/id/{id}")
	ResponseEntity<List<DebtDTO>> getDebtsBySpentId(@PathVariable("id") Long id);
	
	@GetMapping("/byHomeId/{id}")
	ResponseEntity<List<SpentDTO>> getAllSpentsByHomeId(@PathVariable("id") Long id);
	
	@PutMapping("/debt/{id}/paid")
	ResponseEntity<?> updatePaidStatus(@PathVariable("id") Long id, @RequestBody boolean paid);
	
	@DeleteMapping("/{id}/delete")
	ResponseEntity<?> deleteSpentAndDebts(@PathVariable("id") Long id);
	
	@DeleteMapping("debt/{idDebt}")
	ResponseEntity<?> deleteDebt(@PathVariable("idDebt") Long debtId);
	
	@DeleteMapping("/{idSpent}")
	ResponseEntity<?> deleteSpent(@PathVariable("idSpent") Long idSpent);

	@GetMapping("debt/{idDebt}/username")
	ResponseEntity<Boolean> verifyUserPaidDebt(@PathVariable("idDebt") Long idDebt, @RequestParam String username);

	@GetMapping("/{id}/debtsInfo")
	SpentCheckPaidDTO getDebtsInfo(@PathVariable("id") Long id);
}
