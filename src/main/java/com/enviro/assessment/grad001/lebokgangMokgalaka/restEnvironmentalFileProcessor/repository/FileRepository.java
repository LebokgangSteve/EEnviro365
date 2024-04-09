package com.enviro.assessment.grad001.lebokgangMokgalaka.restEnvironmentalFileProcessor.repository;

import com.enviro.assessment.grad001.lebokgangMokgalaka.restEnvironmentalFileProcessor.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByFileName(String fileName);
}
