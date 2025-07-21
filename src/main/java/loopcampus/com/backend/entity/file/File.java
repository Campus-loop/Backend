package loopcampus.com.backend.entity.file;

import jakarta.persistence.*;
import loopcampus.com.backend.domain.FileType;
import loopcampus.com.backend.entity.audit.Period;

public abstract class File extends Period {

    private String fileOriginalName;
    private String fileUUID;
    private String filePath;
    private Long fileSize;

    @Enumerated(EnumType.STRING)
    private FileType fileType;


}
