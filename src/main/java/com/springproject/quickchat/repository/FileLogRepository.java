package com.springproject.quickchat.repository;

import com.springproject.quickchat.model.FileLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileLogRepository extends JpaRepository<FileLog, Long> {
}
