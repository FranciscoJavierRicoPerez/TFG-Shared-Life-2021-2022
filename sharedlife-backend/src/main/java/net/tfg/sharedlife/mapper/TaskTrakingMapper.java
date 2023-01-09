package net.tfg.sharedlife.mapper;

import org.mapstruct.Mapper;

import net.tfg.sharedlife.dto.TaskTrakingDTO;
import net.tfg.sharedlife.model.TaskTraking;

@Mapper
public interface TaskTrakingMapper {
    TaskTraking taskTrakingDTOtoTaskTraking(TaskTrakingDTO dto);
    TaskTrakingDTO taskTrakingToTaskTrakingDTO(TaskTraking taskTraking);
}
