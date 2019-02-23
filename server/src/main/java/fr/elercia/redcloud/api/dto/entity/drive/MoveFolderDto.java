package fr.elercia.redcloud.api.dto.entity.drive;

import java.util.UUID;

public class MoveFolderDto {

    private UUID moveToFolderId;

    public MoveFolderDto() {
    }

    public UUID getMoveToFolderId() {
        return moveToFolderId;
    }
}
