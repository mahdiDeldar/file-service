package com.chubock.fileserver.repository;


import com.chubock.fileserver.enitity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, String> {
}
