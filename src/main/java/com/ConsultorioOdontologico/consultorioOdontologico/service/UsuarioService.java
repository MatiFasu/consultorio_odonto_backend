package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.LoginDto;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Usuario;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IUsuarioRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.utils.BCrypt;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements IUsuarioService {
    
    @Autowired
    private IUsuarioRepository usuRepo;

    @Override
    public List<Usuario> getUsuario() {
        return usuRepo.findAll();
    }

    @Override
    public Usuario saveUsuario(Usuario u) {
        // Encriptamos solo si no parece estar ya encriptada (BCrypt empieza con $)
        if (u.getContrasenia() != null && !u.getContrasenia().startsWith("$2")) {
            u.setContrasenia(BCrypt.hashpw(u.getContrasenia(), BCrypt.gensalt()));
        }
        return usuRepo.save(u);
    }

    @Override
    public void deleteUsuario(Long id) {
        usuRepo.deleteById(id);
    }

    @Override
    public Usuario findUsuario(Long id) {
        return usuRepo.findById(id).orElse(null);
    }

    @Override
    public void editUsuario(Usuario u) {
        // Al editar, buscamos el original para no perder datos si no se envían todos
        Usuario original = usuRepo.findById(u.getId_usuario()).orElse(null);
        if (original != null) {
            if (u.getContrasenia() != null && !u.getContrasenia().isEmpty()) {
                u.setContrasenia(BCrypt.hashpw(u.getContrasenia(), BCrypt.gensalt()));
            } else {
                u.setContrasenia(original.getContrasenia());
            }
        }
        usuRepo.save(u);
    }

    @Override
    public int validarUsuario(LoginDto l) {
        Optional<Usuario> usuarioOpt = usuRepo.findByUsuario(l.getUsername());
        
        if (usuarioOpt.isPresent()) {
            Usuario usu = usuarioOpt.get();
            try {
                // Validación segura contra hashes inválidos
                if (BCrypt.checkpw(l.getContrasenia(), usu.getContrasenia())) {
                    return 1;
                }
            } catch (Exception e) {
                System.err.println("Error validando contraseña para usuario: " + l.getUsername());
            }
        }
        return 0;
    }
}
