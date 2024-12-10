/*package com.springproject.quickchat.repository;

import com.springproject.quickchat.model.File;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InMemoryFileRepository implements FileRepository {
    private final List<File> files = new ArrayList<>();

    @Override
    public void save(File file) {
        files.add(file);
    }

    @Override
    public List<File> findAllByDiscussionId(String discussionId) {
        return files.stream()
                .filter(file -> file.getDiscussionId().equals(discussionId) && !file.isDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public File findById(String fileId) {
        return files.stream()
                .filter(file -> file.getId().equals(fileId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(File file) {
        file.markAsDeleted();
    }
}
*/