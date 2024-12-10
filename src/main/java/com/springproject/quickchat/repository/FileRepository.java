package com.springproject.quickchat.repository;

import com.springproject.quickchat.model.File;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository {
    void save(File file);
    List<File> findAllByDiscussionId(String discussionId);
    File findById(String fileId);
    void delete(File file);
}
