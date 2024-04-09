package com.enviro.assessment.grad001.lebokgangMokgalaka.restEnvironmentalFileProcessor.service;

import com.enviro.assessment.grad001.lebokgangMokgalaka.restEnvironmentalFileProcessor.model.FileEntity;
import com.enviro.assessment.grad001.lebokgangMokgalaka.restEnvironmentalFileProcessor.repository.FileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {


    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void saveFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String fileContent = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent += line + "\n";
            }
        }

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(fileName);
        fileEntity.setFileContent(fileContent);
        fileRepository.save(fileEntity);
    }

    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    public ResponseEntity<FileEntity> getFileById(Long id) {
        Optional<FileEntity> fileOptional = fileRepository.findById(id);
            if (fileOptional.isPresent()) {
            return new ResponseEntity<>(fileOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<FileEntity>> getFileByName(String fileName) {
        List<FileEntity> files = fileRepository.findByFileName(fileName);
        if (!files.isEmpty()) {
            return new ResponseEntity<>(files, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
