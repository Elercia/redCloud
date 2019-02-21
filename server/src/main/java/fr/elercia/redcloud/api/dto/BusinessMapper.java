package fr.elercia.redcloud.api.dto;

import fr.elercia.redcloud.api.dto.entity.MonitorIntegrityCheckRequestDto;
import fr.elercia.redcloud.business.entity.MonitorIntegrityCheckRequest;

public class BusinessMapper {

    public static MonitorIntegrityCheckRequest dtoToEntity(MonitorIntegrityCheckRequestDto monitorIntegrityCheckRequestDto) {
        return new MonitorIntegrityCheckRequest(monitorIntegrityCheckRequestDto.getActionType());
    }
}
