package net.tfg.sharedlife.service.spent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.tfg.sharedlife.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.tfg.sharedlife.dto.DebtDTO;
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
	public void createSpent(SpentDTO spentDto, boolean admin) {
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
		spent.setDebts(createDebt(admin, spentDto, spent));
	
	}
	
	private Set<Debt> createDebt(boolean admin, SpentDTO spentDto, Spent spent) {
		// CAUSISTICA
		// 1 - SI EL GASTO LO CREA EL ADMIN
			// => LAS DEUDAS ES PARA CADA UNO DE LOS MIEMBROS DE LA VIVIENDA (HECHA)
		// 2 - SI EL GASTO LO CREA EL USUARIO 
			// => LAS DEUDAS DEL PISO SE CREAN PARA TODOS MENOS PARA EL QUE LA HA CREADO Y PARA EL ADMIN
			// => ES DECIR EL USUARIO QUE TENGA EL NOMBRE Y EL QUE CONTENGA EL ROL DE USUARIO
		logger.info("Creating de debts for the member users");
		Set<Debt> debts = new HashSet<>();
		List<UserDTO> members = homeService.getMembers(Long.parseLong(spentDto.getIdHome()));
		for(UserDTO u : members) {
			if(!spentDto.getUserToPay().equals(u.getUsername()) && !admin && !u.getRoles().contains("ROLE_ADMIN")) { // En este caso no crea si es ADMIN
				logger.info("Debts in the case the spent is creating by the user");
				User member = userService.getByUsername(u.getUsername()).get();
				Debt debt = new Debt();
				debt.setIdHome(spent.getHome().getId());
				debt.setIdUser(member.getId());
				//debt.setIdSpent(spent.getId());
				System.out.println("CANTIDAD DE MIEMBROS => " + members.size());
				System.out.println("CANTIDAD A PAGAR => " + spent.getTotalPrice());
				debt.setPricePerPerson(spent.getTotalPrice() / (members.size() - 2)); // Resto al admin y al usuario que crea el gasto
				debt.setPaid(false);
				debt.setUserToPay(spentDto.getUserToPay());
				debt.setSpent(spent);
				debtRepository.save(debt);
				debts.add(debt);
			}
			else {
				if(admin && !spentDto.getUserToPay().equals(u.getUsername())) {
					logger.info("Debts in the case the spent is creating by the admin");
					User member = userService.getByUsername(u.getUsername()).get();
					Debt debt = new Debt();
					debt.setIdHome(spent.getHome().getId());
					debt.setIdUser(member.getId());
					//debt.setIdSpent(spent.getId());
					debt.setPricePerPerson(spent.getTotalPrice() / (members.size() - 1)); // Resto al admin
					debt.setPaid(false);
					debt.setUserToPay(spentDto.getUserToPay());
					debt.setSpent(spent);
					debtRepository.save(debt);
					debts.add(debt);
				}
			}
		}
		return debts;
	}

	@Override
	public List<DebtDTO> getDebtsByUsername(String username) {
		List<DebtDTO> debtsdto = new ArrayList<>();
		User user = userService.getByUsername(username).get();
		List<Debt> debts = debtRepository.findAll();
		for(Debt debt: debts) {
			if(debt.getIdUser().equals(user.getId())) {
				DebtDTO debtdto = new DebtDTO();
				debtdto.setId(debt.getId());
				debtdto.setIdHome(debt.getIdHome());
				debtdto.setIdSpent(debt.getSpent().getId());
				debtdto.setIdUser(debt.getIdUser());
				debtdto.setPricePerPerson(debt.getPricePerPerson());
				debtdto.setUserToPay(debt.getUserToPay());
				debtdto.setPaid(debt.isPaid());
				debtsdto.add(debtdto);
			}
		}
		return debtsdto;
	}

	@Override
	public SpentDTO getSpentById(Long id) {
		SpentDTO spentdto = new SpentDTO();
		Spent spent = spentRepository.findById(id).get();
		spentdto.setId(spent.getId());
		spentdto.setTitle(spent.getTitle());
		spentdto.setDescription(spent.getDescription());
		spentdto.setIdHome(spent.getHome().getId().toString());
		spentdto.setTotalPrice(spent.getTotalPrice());
		if(this.checkSpentsPaid(id)) {
			spentdto.setPaid(true);
		}
		return spentdto;
	}

	@Override
	public List<SpentDTO> getSpentsByUsernameAndHomeId(String username, Long id) {
		List<SpentDTO> spentsdto = new ArrayList<>();
		User user = userRepository.getByUsername(username).get();
		List<Spent> spents = spentRepository.findAll();
		for(Spent s : spents) {
			for(User u: s.getUsers()) {
				if(u.getId().equals(user.getId()) && s.getHome().getId().equals(id)) {
					SpentDTO spentdto = new SpentDTO();
					spentdto.setTitle(s.getTitle());
					spentdto.setDescription(s.getDescription());
					spentdto.setIdHome(s.getHome().getId().toString());
					spentdto.setId(s.getId());
					spentdto.setTotalPrice(s.getTotalPrice());
					spentdto.setUserToPay(u.getUsername()); // esto es correctp????
					if(this.checkSpentsPaid(s.getId())) {
						spentdto.setPaid(true);
					}
					spentsdto.add(spentdto);
				}
			}
		}
		return spentsdto;
	}

	@Override
	public List<DebtDTO> getDebtsBySpentId(Long id) {
		List<DebtDTO> debtsdto = new ArrayList<>();
		List<Debt> debts = debtRepository.findAll();
		for(Debt d : debts) {
			if(d.getSpent().getId().equals(id)) {
				DebtDTO debtdto = new DebtDTO();
				debtdto.setId(d.getId());
				debtdto.setIdHome(d.getIdHome());
				//debtdto.setIdSpent(d.getIdSpent());
				debtdto.setIdUser(d.getIdUser());
				debtdto.setPricePerPerson(d.getPricePerPerson());
				debtdto.setPaid(d.isPaid());
				debtsdto.add(debtdto);
			}
		}
		return debtsdto;
	}

	@Override
	public List<SpentDTO> getAllSpentsByHomeId(Long id) {
		List<SpentDTO> spentsDTO = new ArrayList<>();
		List<Spent> spents = spentRepository.findAll();
		for(Spent s: spents) {
			if(s.getHome().getId().equals(id)) {
				SpentDTO spentdto = new SpentDTO();
				spentdto.setId(s.getId());
				spentdto.setTitle(s.getTitle());
				spentdto.setDescription(s.getDescription());
				spentdto.setTotalPrice(s.getTotalPrice());
				List<Debt> debts = debtRepository.findAll();
				for(Debt d : debts) {
					if(d.getIdHome().equals(id)) {
						spentdto.setUserToPay(d.getUserToPay());
						break;
					}
				}
				if(this.checkSpentsPaid(s.getId())) {
					spentdto.setPaid(true);
				}
				spentsDTO.add(spentdto);
			}
		}
		return spentsDTO;
	}

	@Override
	public void updatePaidStatus(Long id, boolean paid) {
		Debt d = debtRepository.getById(id);
		d.setPaid(paid);
		debtRepository.save(d);
	}
	
	private boolean checkSpentsPaid(Long id) { // id del spent
		boolean paid = false;
		int contador = 0;
		List<Long> ids = new ArrayList<>();
		List<DebtDTO> debtsDTO = getDebtsBySpentId(id);
		for(DebtDTO d : debtsDTO) {
			if(d.isPaid()) {
				contador++;
				ids.add(d.getId());
			}
		}
		if(contador == debtsDTO.size()) {
			updateSpentPaid(id);
			paid = true;
		}
		return paid;
	}
	
	private void updateSpentPaid(Long id) {
		Spent s = spentRepository.getById(id);
		s.setPaid(true);
		spentRepository.save(s);
	}

	@Override
	public void deleteSpentAndDebts(Long id) {
		Spent spent = spentRepository.getById(id);
		List<DebtDTO> debtsdto = getDebtsBySpentId(id);
		for(DebtDTO d : debtsdto) {
			Debt debt = debtRepository.findById(d.getId()).get();
			debtRepository.delete(debt);
		}
	}

	@Override
	public void deleteDebt(Long id) {
		logger.info("Deleting the debt with id: {}", id);
		if(id != null) {
			debtRepository.deleteById(id);
		}
	}

	@Override
	public void deleteSpent(Long id) {
		logger.info("Deleting the spent with id: {}",id);
		if(id != null) {
			Spent s = spentRepository.findById(id).get();
			List<Debt> debts = debtRepository.findDebtsBySpentId(s.getId());
			System.out.print("TAMAÑO DE LA LISTA " + debts.size());
			for(Debt d : debts){
				deleteDebt(d.getId());
			}
			spentRepository.deleteById(id);
		}
	}
	
}
