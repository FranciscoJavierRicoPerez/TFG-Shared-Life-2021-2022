package net.tfg.sharedlife.service.spent;

import net.tfg.sharedlife.dto.SpentDTO;

public interface SpentService {
	
	void createTask(SpentDTO spentDto, boolean admin);

}
