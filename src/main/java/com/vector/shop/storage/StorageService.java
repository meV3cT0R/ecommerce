package com.vector.shop.storage;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void init();
    void store(MultipartFile file);
    Stream<Path> loadAll();
    Path load(String fileName);
    Resource loadAsResource(String fileName);
    void deleteAll();
}
