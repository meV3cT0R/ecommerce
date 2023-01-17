package com.vector.shop.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties("storage")
@Data
@Component
public class StorageProperties {
    private String location ="images";
}
