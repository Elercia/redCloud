package fr.elercia.redcloud.api.dto.entity;

import java.util.UUID;

public class MoveFileDto {

    private UUID directoryId;

    public MoveFileDto() {
    }

    public UUID getDirectoryId() {
        return directoryId;
    }
}
