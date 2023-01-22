package net.tfg.sharedlife.mapper;

import org.mapstruct.Mapper;

import net.tfg.sharedlife.dto.HomeDTO;
import net.tfg.sharedlife.model.Home;

@Mapper
public interface HomeMapper {

    Home homeDTOtoHome(HomeDTO homeDTO);

    HomeDTO homeToHomeDTO(Home home);

}