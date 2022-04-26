package net.tfg.sharedlife.service.spent;

import java.util.List;

import net.tfg.sharedlife.dto.DebtDTO;
import net.tfg.sharedlife.dto.SpentDTO;

public interface SpentService {
	
	void createTask(SpentDTO spentDto, boolean admin);
	
	List<DebtDTO> getDebtsByUsername(String username);
	
	SpentDTO getSpentById(Long id);

}
