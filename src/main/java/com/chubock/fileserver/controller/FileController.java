package com.chubock.fileserver.controller;

import com.chubock.fileserver.model.FileModel;
import com.chubock.fileserver.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    @Operation(summary = "Upload files")
    public List<FileModel> upload(Authentication authentication, MultipartHttpServletRequest request) {

        List<FileModel> models = request.getFileMap()
                .values()
                .stream()
                .map(this::getModel)
                .peek(model -> model.setUploader(authentication.getName()))
                .collect(Collectors.toList());

        return fileService.save(models);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Download file")
    public void download(@PathVariable String id, HttpServletResponse response) throws IOException {

        FileModel file = fileService.find(id);
        response.setContentType(file.getContentType());
        response.setContentLengthLong(file.getSize());
        response.setHeader("Content-Disposition", "inline" + "; filename=" + file.getName());
        response.getOutputStream().write(file.getContent());

    }

    private FileModel getModel(MultipartFile file) {
        try {
            return FileModel.builder()
                    .name(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .content(file.getBytes())
                    .build();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
