package ru.systemoteh.photos.ejb.service.impl;

import ru.systemoteh.photos.ejb.service.FileNameGeneratorService;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class FileNameGeneratorServiceImpl implements FileNameGeneratorService {
    @Override
    public String generateUniqueFileName() {
        return UUID.randomUUID().toString() + ".jpg";
    }
}
