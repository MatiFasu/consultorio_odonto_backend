package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.model.MultimediaEstudio;
import com.ConsultorioOdontologico.consultorioOdontologico.service.MediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
@Tag(name = "Multimedia", description = "Gestión de archivos y estudios médicos")
public class MediaController {

    private final MediaService mediaService;

    @PostMapping("/upload/{registroId}")
    @Operation(summary = "Subir un archivo para un registro clínico")
    public ResponseEntity<MultimediaEstudio> uploadFile(
            @PathVariable Long registroId, 
            @RequestParam("file") MultipartFile file) {
        try {
            MultimediaEstudio estudio = mediaService.uploadFile(registroId, file);
            return ResponseEntity.ok(estudio);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{filename:.+}")
    @Operation(summary = "Obtener un archivo por su nombre")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Resource resource = mediaService.loadFile(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar un archivo multimedia")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
        try {
            mediaService.deleteFile(id);
            return ResponseEntity.ok("Archivo eliminado correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al eliminar el archivo físico o registro");
        }
    }
}
