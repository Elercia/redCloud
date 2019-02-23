package fr.elercia.redcloud.api.dto.entity.drive;

import java.util.UUID;

public class MoveFileDto {

    private UUID directoryId;

    public MoveFileDto() {
    }

    public UUID getFolderId() {
        return directoryId;
    }
}
