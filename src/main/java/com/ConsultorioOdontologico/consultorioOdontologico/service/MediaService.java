package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.model.MultimediaEstudio;
import com.ConsultorioOdontologico.consultorioOdontologico.model.RegistroClinico;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IMultimediaEstudioRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IRegistroClinicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaService {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    private Path root;

    private final IMultimediaEstudioRepository multimediaRepo;
    private final IRegistroClinicoRepository registroRepo;

    @PostConstruct
    public void init() {
        try {
            root = Paths.get(uploadDir);
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar la carpeta de subidas: " + uploadDir);
        }
    }

    public MultimediaEstudio uploadFile(Long registroId, MultipartFile file) throws Exception {
        RegistroClinico registro = registroRepo.findById(registroId)
                .orElseThrow(() -> new IllegalArgumentException("Registro no encontrado"));
        
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".") 
                ? originalFilename.substring(originalFilename.lastIndexOf(".")) 
                : ".tmp";
        String filename = UUID.randomUUID().toString() + extension;
        
        Files.copy(file.getInputStream(), this.root.resolve(filename));

        MultimediaEstudio estudio = new MultimediaEstudio();
        estudio.setNombreArchivo(originalFilename);
        estudio.setTipoContenido(file.getContentType());
        estudio.setUrlArchivo(filename);
        estudio.setRegistroClinico(registro);

        return multimediaRepo.save(estudio);
    }

    public Resource loadFile(String filename) throws Exception {
        Path file = root.resolve(filename).normalize().toAbsolutePath();
        Path rootAbsolute = root.toAbsolutePath();
        
        if (!file.startsWith(rootAbsolute)) {
            throw new SecurityException("Acceso denegado: intento de Path Traversal bloqueado.");
        }

        Resource resource = new UrlResource(file.toUri());

        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new IllegalArgumentException("El archivo no existe o no es legible: " + filename);
        }
    }

    public void deleteFile(Long id) throws Exception {
        MultimediaEstudio estudio = multimediaRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Archivo multimedia no encontrado con ID: " + id));

        Files.deleteIfExists(root.resolve(estudio.getUrlArchivo()));
        multimediaRepo.delete(estudio);
    }
}
