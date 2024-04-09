package com.enviro.assessment.grad001.lebokgangMokgalaka.restEnvironmentalFileProcessor.controller;

import com.enviro.assessment.grad001.lebokgangMokgalaka.restEnvironmentalFileProcessor.model.FileEntity;
import com.enviro.assessment.grad001.lebokgangMokgalaka.restEnvironmentalFileProcessor.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    public FileController(final FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please upload a file!", HttpStatus.BAD_REQUEST);
        }

        try {
            fileService.saveFile(file);
            return new ResponseEntity<>("File uploaded successfully!", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload file!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<FileEntity>> getAllFiles() {
        List<FileEntity> files = fileService.getAllFiles();
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<FileEntity> getFileById(@PathVariable Long id) {
        return fileService.getFileById(id);
    }

    @GetMapping("/byName/{fileName:.+}")
    public ResponseEntity<List<FileEntity>> getFileByName(@PathVariable String fileName) {
        return fileService.getFileByName(fileName);
    }
}
