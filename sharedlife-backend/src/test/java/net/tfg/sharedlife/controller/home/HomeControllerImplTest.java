package net.tfg.sharedlife.controller.home;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import net.tfg.sharedlife.common.ErrorMessages;
import net.tfg.sharedlife.dto.HomeDTO;
import net.tfg.sharedlife.dto.InvitationDTO;
import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.model.Home;
import net.tfg.sharedlife.service.home.HomeServiceImpl;

@ExtendWith(MockitoExtension.class)
class HomeControllerImplTest {

    @InjectMocks
    private HomeControllerImpl homeController;

    @Mock
    private HomeServiceImpl homeService;

    @Test
    public void createHomeExceptionTest(){
        // Datos de Entrada
        HomeDTO homeDTO = null;
        Mockito.when(homeService.createHome(homeDTO))
               .thenThrow(new DataIncorrectException(ErrorMessages.HOME_INFORMATION_ERR));
        assertEquals(HttpStatus.BAD_REQUEST, homeController.createHome(homeDTO).getStatusCode());
    }

    @Test
    public void createHomeSuccessTest(){
        HomeDTO homeDTO = new HomeDTO();
        homeDTO.setId(1L);
        Home home = new Home();
        home.setId(1L);
        Mockito.when(homeService.createHome(homeDTO)).thenReturn(home);
        Home actualHome = homeController.createHome(homeDTO).getBody();
        assertEquals(home.getId(), actualHome.getId());
    }

    @Test
    public void getHomesByUserTest(){
        HomeDTO home = new HomeDTO();
        home.setId(1L);
        List<HomeDTO> homeListExpected = new ArrayList<>();
        homeListExpected.add(home);
        Mockito.when(homeService.getHomesByUser("username"))
               .thenReturn(homeListExpected);
        assertEquals(homeListExpected, homeController.getHomesByUser("username").getBody());
    }

    @Test
    public void getHomesByUserExceptionTest(){
        Mockito.when(homeService.getHomesByUser("username"))
               .thenThrow(new DataIncorrectException(ErrorMessages.HOME_INFORMATION_ERR));
        assertEquals(HttpStatus.BAD_REQUEST, homeController.getHomesByUser("username").getStatusCode());
    }

    @Test
    public void getHomeByIdTest(){
        HomeDTO home = new HomeDTO();
        home.setId(1L);
        Mockito.when(homeService.getHomeById(1L)).thenReturn(home);
        assertEquals(home.getId(), homeController.getHomeById(1L).getBody().getId());
    }

    @Test
    public void getHomeByIdExceptionTest(){
        Mockito.when(homeService.getHomeById(1L)).thenThrow(new DataIncorrectException(ErrorMessages.HOME_INFORMATION_ERR));
        assertEquals(HttpStatus.BAD_REQUEST, homeController.getHomeById(1L).getStatusCode());
    }

    //@Test
    public void createInvitationToHomeTest(){
        InvitationDTO invitationDTO = new InvitationDTO();
        invitationDTO.setAddress("address");
    }
}