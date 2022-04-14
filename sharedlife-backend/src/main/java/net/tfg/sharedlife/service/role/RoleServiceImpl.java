package net.tfg.sharedlife.service.role;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.tfg.sharedlife.enums.RoleEnum;
import net.tfg.sharedlife.model.Role;
import net.tfg.sharedlife.repository.RoleRepository;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

	@Autowired
	RoleRepository roleRepository;
	
	public Optional<Role> getByRoleName(RoleEnum role){
		return roleRepository.findByRoleName(role);
	}
	
}
