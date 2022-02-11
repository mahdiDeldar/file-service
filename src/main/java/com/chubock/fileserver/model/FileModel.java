package com.chubock.fileserver.model;


import com.chubock.fileserver.enitity.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FileModel {

    private String id;
    private String name;
    private String contentType;
    private Long size;
    private byte[] content;
    private String uploader;

    public static FileModel of(File file) {

        return builder()
                .id(file.getId())
                .name(file.getName())
                .contentType(file.getContentType())
                .size(file.getSize())
                .uploader(file.getUploader())
                .build();

    }

}
