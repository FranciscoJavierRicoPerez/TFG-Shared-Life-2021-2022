package net.tfg.sharedlife.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SpentCheckPaidDTO {
    List<VerifyPaidDTO> verifyPaidDTOList;
}
