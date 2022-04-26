package net.tfg.sharedlife.service.spent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	@Override
	public List<DebtDTO> getDebtsByUsername(String username) {
		List<DebtDTO> debtsdto = new ArrayList<>();
		User user = userService.getByUsername(username).get();
		List<Debt> debts = debtRepository.findAll();
		for(Debt debt: debts) {
			if(debt.getIdUser().equals(user.getId())) {
				if(!debt.isPaid()) {
					DebtDTO debtdto = new DebtDTO();
					debtdto.setId(debt.getId());
					debtdto.setIdHome(debt.getIdHome());
					debtdto.setIdSpent(debt.getIdSpent());;
					debtdto.setIdUser(debt.getIdUser());
					debtdto.setPricePerPerson(debt.getPricePerPerson());
					debtdto.setUserToPay(debt.getUserToPay());
					debtsdto.add(debtdto);
				}
			}
		}
		return debtsdto;
	}

	@Override
	public SpentDTO getSpentById(Long id) {
		SpentDTO spentdto = new SpentDTO();
		if(!this.checkSpentsPaid(id)) {/// */*/*/*/
			Spent spent = spentRepository.findById(id).get();
			spentdto.setId(spent.getId());
			spentdto.setTitle(spent.getTitle());
			spentdto.setDescription(spent.getDescription());
			spentdto.setIdHome(spent.getHome().getId().toString());
			spentdto.setTotalPrice(spent.getTotalPrice());
		}
		return spentdto;
	}

	@Override
	public List<SpentDTO> getSpentsByUsername(String username) {
		List<SpentDTO> spentsdto = new ArrayList<>();
		User user = userRepository.getByUsername(username).get();
		List<Spent> spents = spentRepository.findAll();
		for(Spent s : spents) {
			if(!this.checkSpentsPaid(s.getId())) { ///*/*/*/*/*
				for(User u: s.getUsers()) {
					if(u.getId().equals(user.getId())) {
						SpentDTO spentdto = new SpentDTO();
						spentdto.setTitle(s.getTitle());
						spentdto.setDescription(s.getDescription());
						spentdto.setIdHome(s.getHome().getId().toString());
						spentdto.setId(s.getId());
						spentdto.setTotalPrice(s.getTotalPrice());
						spentdto.setUserToPay(u.getUsername()); // esto es correctp????
						spentsdto.add(spentdto);
					}
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
			if(d.getIdSpent().equals(id)) {
				DebtDTO debtdto = new DebtDTO();
				debtdto.setId(d.getId());
				debtdto.setIdHome(d.getIdHome());
				debtdto.setIdSpent(d.getIdSpent());
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
			if(!this.checkSpentsPaid(s.getId())) {
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
					spentsDTO.add(spentdto);
				}
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
		boolean borrado = false;
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
			for(int i = 0; i < ids.size(); i++) {
				debtRepository.deleteById(ids.get(i));
			}
			spentRepository.deleteById(id);
			borrado = true;
		}
		return borrado;
		
	}
	
}
