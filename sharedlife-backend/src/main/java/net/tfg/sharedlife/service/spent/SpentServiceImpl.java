package net.tfg.sharedlife.service.spent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.tfg.sharedlife.dto.NewUserDto;
import net.tfg.sharedlife.dto.SpentDTO;
import net.tfg.sharedlife.model.Debt;
import net.tfg.sharedlife.model.Spent;
import net.tfg.sharedlife.model.User;
import net.tfg.sharedlife.repository.DebtRepository;
import net.tfg.sharedlife.repository.HomeRepository;
import net.tfg.sharedlife.repository.SpentRepository;
import net.tfg.sharedlife.repository.UserRepository;
import net.tfg.sharedlife.service.home.HomeService;
import net.tfg.sharedlife.service.user.UserService;

@Service
public class SpentServiceImpl implements SpentService {
	
	private final static Logger logger = LoggerFactory.getLogger(SpentServiceImpl.class);


	@Autowired
	private SpentRepository spentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HomeRepository homeRepository;
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private DebtRepository debtRepository;
	
	@Autowired
	private UserService userService;

	@Override
	public void createTask(SpentDTO spentDto, boolean admin) {
		logger.info("Creating a new spent with values: {}", spentDto);
		User user = userRepository.getByUsername(spentDto.getUserToPay()).get();
		Spent spent = new Spent();
		spent.setTitle(spentDto.getTitle());
		spent.setDescription(spentDto.getDescription());
		spent.setTotalPrice(spentDto.getTotalPrice());
		Set<User> users = new HashSet<>();
		users.add(user);
		spent.setUsers(users);
		spent.setHome(homeRepository.findById(Long.parseLong(spentDto.getIdHome())).get());
		spentRepository.save(spent);
		createDebt(admin, spentDto, spent);
	}
	
	private void createDebt(boolean admin, SpentDTO spentDto, Spent spent) {
		// CAUSISTICA
		// 1 - SI EL GASTO LO CREA EL ADMIN
			// => LAS DEUDAS ES PARA CADA UNO DE LOS MIEMBROS DE LA VIVIENDA (HECHA)
		// 2 - SI EL GASTO LO CREA EL USUARIO 
			// => LAS DEUDAS DEL PISO SE CREAN PARA TODOS MENOS PARA EL QUE LA HA CREADO Y PARA EL ADMIN
			// => ES DECIR EL USUARIO QUE TENGA EL NOMBRE Y EL QUE CONTENGA EL ROL DE USUARIO
		logger.info("Creating de debts for the member users");
		List<NewUserDto> members = homeService.getMembers(Long.parseLong(spentDto.getIdHome()));
		for(NewUserDto u : members) {
			if(!spentDto.getUserToPay().equals(u.getUsername()) && !admin && !u.getRoles().contains("ROLE_ADMIN")) { // En este caso no crea si es ADMIN
				User member = userService.getByUsername(u.getUsername()).get();
				Debt debt = new Debt();
				debt.setIdHome(spent.getHome().getId());
				debt.setIdUser(member.getId());
				debt.setIdSpent(spent.getId());
				System.out.println("CANTIDAD DE MIEMBROS => " + members.size());
				System.out.println("CANTIDAD A PAGAR => " + spent.getTotalPrice());
				debt.setPricePerPerson(spent.getTotalPrice() / (members.size() - 2)); // Resto al admin y al usuario que crea el gasto
				debt.setPaid(false);
				debt.setUserToPay(spentDto.getUserToPay());
				debtRepository.save(debt);
			}
			else {
				if(admin && !spentDto.getUserToPay().equals(u.getUsername())) {
					User member = userService.getByUsername(u.getUsername()).get();
					Debt debt = new Debt();
					debt.setIdHome(spent.getHome().getId());
					debt.setIdUser(member.getId());
					debt.setIdSpent(spent.getId());
					debt.setPricePerPerson(spent.getTotalPrice() / (members.size() - 1)); // Resto al admin
					debt.setPaid(false);
					debt.setUserToPay(spentDto.getUserToPay());
					debtRepository.save(debt);
				}
			}
		}
	}
	
}
