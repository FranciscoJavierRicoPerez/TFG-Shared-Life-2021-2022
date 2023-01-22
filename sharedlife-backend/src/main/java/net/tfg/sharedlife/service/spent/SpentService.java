package net.tfg.sharedlife.service.spent;

import java.util.List;

import net.tfg.sharedlife.dto.DebtDTO;
import net.tfg.sharedlife.dto.SpentCheckPaidDTO;
import net.tfg.sharedlife.dto.SpentDTO;

public interface SpentService {
	
	void createSpent(SpentDTO spentDto, boolean admin);
	
	List<DebtDTO> getDebtsByUsername(String username);
	
	SpentDTO getSpentById(Long id);
	
	List<SpentDTO> getSpentsByUsernameAndHomeId(String username, Long id);
	
	List<DebtDTO> getDebtsBySpentId(Long id);
	
	List<SpentDTO> getAllSpentsByHomeId(Long id);
	
	void updatePaidStatus(Long id, boolean paid);
	
	void deleteSpentAndDebts(Long id);
	
	void deleteDebt(Long id);

	void deleteSpent(Long id);

	boolean verifyUserPaidDebt(Long idDebt, String username);

	SpentCheckPaidDTO getDebtsInfo(Long idSpent);
	
}
