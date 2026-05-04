
package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.model.MultimediaEstudio;
import com.ConsultorioOdontologico.consultorioOdontologico.model.RegistroClinico;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IMultimediaEstudioRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IRegistroClinicoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/media")
@Tag(name = "Multimedia", description = "Gestión de archivos y estudios médicos")
public class MediaController {

    private final Path root = Paths.get("uploads");

    @Autowired
    private IMultimediaEstudioRepository multimediaRepo;

    @Autowired
    private IRegistroClinicoRepository registroRepo;

    public MediaController() {
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar la carpeta de subidas");
        }
    }

    @PostMapping("/upload/{registroId}")
    @Operation(summary = "Subir un archivo para un registro clínico")
    public ResponseEntity<MultimediaEstudio> uploadFile(@PathVariable Long registroId, @RequestParam("file") MultipartFile file) {
        try {
            RegistroClinico registro = registroRepo.findById(registroId).orElseThrow(() -> new RuntimeException("Registro no encontrado"));
            
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String filename = UUID.randomUUID().toString() + extension;
            
            Files.copy(file.getInputStream(), this.root.resolve(filename));

            MultimediaEstudio estudio = new MultimediaEstudio();
            estudio.setNombreArchivo(file.getOriginalFilename());
            estudio.setTipoContenido(file.getContentType());
            estudio.setUrlArchivo(filename);
            estudio.setRegistroClinico(registro);

            return ResponseEntity.ok(multimediaRepo.save(estudio));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{filename:.+}")
    @Operation(summary = "Obtener un archivo por su nombre")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
