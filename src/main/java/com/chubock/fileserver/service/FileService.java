package com.chubock.fileserver.service;

import com.chubock.fileserver.enitity.File;
import com.chubock.fileserver.exception.FileNotFoundException;
import com.chubock.fileserver.model.FileModel;
import com.chubock.fileserver.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Transactional(readOnly = true)
    public FileModel find(String id) {
        return fileRepository.findById(id)
                .map(file -> {
                    FileModel model = FileModel.of(file);
                    model.setContent(file.getContent());
                    return model;
                })
                .orElseThrow(FileNotFoundException::new);
    }

    @Transactional
    public List<FileModel> save(List<FileModel> models) {

        return models.stream()
                .map(this::save)
                .collect(Collectors.toList());

    }

    public FileModel save(FileModel model) {

        File file = File.builder()
                .name(model.getName())
                .contentType(model.getContentType())
                .size(model.getSize())
                .content(model.getContent())
                .uploader(model.getUploader())
                .build();

        fileRepository.save(file);

        return FileModel.of(file);
    }

}
