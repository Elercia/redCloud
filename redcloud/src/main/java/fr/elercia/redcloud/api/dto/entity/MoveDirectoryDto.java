package fr.elercia.redcloud.api.dto.entity;

import java.util.UUID;

public class MoveDirectoryDto {

    private UUID moveToDirectoryId;

    public MoveDirectoryDto() {
    }

    public UUID getMoveToDirectoryId() {
        return moveToDirectoryId;
    }
}
