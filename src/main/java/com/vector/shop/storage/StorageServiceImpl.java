package com.vector.shop.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceImpl implements StorageService{
    private final Path rootLocation;


    public StorageServiceImpl(StorageProperties storageProperties) {
        this.rootLocation = Paths.get(storageProperties.getLocation());
    }


    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
        
    }


    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage",e);
        }    
    }


    @Override
    public Path load(String fileName) {
        return rootLocation.resolve(fileName);
    }


    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                .filter(path->!path.equals(this.rootLocation))
                .map(path->this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored Files",e);
        }
    }


    @Override
    public Resource loadAsResource(String fileName) {
        try {
            Path file = load(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.isReadable() || resource.exists()) return resource;
            else throw new StorageFileNotFoundException("Could Not Read File :"+ fileName);
        }catch(IOException e) {
            throw new StorageFileNotFoundException("Couldn't read file " +fileName,e);
        }
    }


    @Override
    public void store(MultipartFile file) {
        try {
            if(file.isEmpty()) {
                throw new StorageException("Failed to store empty file "+ file.getName());
            }
            Files.copy(file.getInputStream(),this.rootLocation.resolve(file.getOriginalFilename()));
        } catch(IOException e) {
            throw new StorageException("Failed to store file "+file.getName(),e);
        }
    }
}
