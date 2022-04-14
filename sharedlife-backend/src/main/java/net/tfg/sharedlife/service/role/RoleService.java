package net.tfg.sharedlife.service.role;

import java.util.Optional;

import net.tfg.sharedlife.enums.RoleEnum;
import net.tfg.sharedlife.model.Role;

public interface RoleService {
	Optional<Role> getByRoleName(RoleEnum role);
}
