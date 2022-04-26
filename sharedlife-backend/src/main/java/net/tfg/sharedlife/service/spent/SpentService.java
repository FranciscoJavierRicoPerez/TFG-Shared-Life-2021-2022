package net.tfg.sharedlife.service.spent;

import java.util.List;

import net.tfg.sharedlife.dto.DebtDTO;
import net.tfg.sharedlife.dto.SpentDTO;

public interface SpentService {
	
	void createTask(SpentDTO spentDto, boolean admin);
	
	List<DebtDTO> getDebtsByUsername(String username);
	
	SpentDTO getSpentById(Long id);
	
	List<SpentDTO> getSpentsByUsername(String username);
	
	List<DebtDTO> getDebtsBySpentId(Long id);
	
	List<SpentDTO> getAllSpentsByHomeId(Long id);
	
	void updatePaidStatus(Long id, boolean paid);

}
