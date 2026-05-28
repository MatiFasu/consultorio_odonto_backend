package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.config.JwtService;
import com.ConsultorioOdontologico.consultorioOdontologico.dto.LoginDto;
import com.ConsultorioOdontologico.consultorioOdontologico.dto.UsuarioDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.mapper.UsuarioMapper;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Usuario;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.access.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {
    
    private final IUsuarioRepository usuRepo;
    private final com.ConsultorioOdontologico.consultorioOdontologico.repository.IOdontologoRepository odontoRepo;
    private final com.ConsultorioOdontologico.consultorioOdontologico.repository.ISecretariaRepository secreRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UsuarioMapper usuarioMapper;

    @Override
    public List<UsuarioDTO> getUsuario() {
        return usuRepo.findAll().stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public UsuarioDTO saveUsuario(UsuarioDTO uDTO) {
        // Validación de seguridad interna: Solo un ADMIN puede crear un usuario ADMIN
        if ("ADMIN".equalsIgnoreCase(uDTO.getRol())) {
            validarSiEsAdmin();
        }

        Usuario u = usuarioMapper.toEntity(uDTO);
        if (u.getContrasenia() != null && !u.getContrasenia().startsWith("$2")) {
            u.setContrasenia(passwordEncoder.encode(u.getContrasenia()));
        }
        Usuario guardado = usuRepo.save(u);
        return usuarioMapper.toDTO(guardado);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void deleteUsuario(Long id) {
        usuRepo.findById(id).ifPresent(u -> {
            if ("ADMIN".equalsIgnoreCase(u.getRol())) {
                validarSiEsAdmin();
            }
        });

        odontoRepo.findAll().stream()
            .filter(o -> o.getUnUsuario() != null && o.getUnUsuario().getId().equals(id))
            .forEach(o -> {
                o.setUnUsuario(null);
                odontoRepo.save(o);
            });

        secreRepo.findAll().stream()
            .filter(s -> s.getUnUsuario() != null && s.getUnUsuario().getId().equals(id))
            .forEach(s -> {
                s.setUnUsuario(null);
                secreRepo.save(s);
            });

        usuRepo.deleteById(id);
    }

    @Override
    public UsuarioDTO findUsuario(Long id) {
        return usuRepo.findById(id)
                .map(usuarioMapper::toDTO)
                .orElse(null);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void editUsuario(UsuarioDTO uDTO) {
        if (uDTO == null || uDTO.getId() == null) return;

        usuRepo.findById(uDTO.getId()).ifPresent(original -> {
            // Si se intenta cambiar a ADMIN o si el usuario original era ADMIN
            if ("ADMIN".equalsIgnoreCase(uDTO.getRol()) || "ADMIN".equalsIgnoreCase(original.getRol())) {
                validarSiEsAdmin();
            }

            String passOriginal = original.getContrasenia();
            usuarioMapper.updateEntityFromDTO(uDTO, original);
            
            if (uDTO.getContrasenia() != null && !uDTO.getContrasenia().isEmpty() && !uDTO.getContrasenia().startsWith("$2")) {
                original.setContrasenia(passwordEncoder.encode(uDTO.getContrasenia()));
            } else {
                original.setContrasenia(passOriginal);
            }
            usuRepo.save(original);
        });
    }

    private void validarSiEsAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            throw new AccessDeniedException("No tiene permisos para gestionar usuarios con rol ADMIN");
        }
    }

    public Map<String, Object> login(LoginDto l) {
        Optional<Usuario> usuarioOpt = usuRepo.findByUsuario(l.getUsername());
        
        if (usuarioOpt.isPresent()) {
            Usuario usu = usuarioOpt.get();
            if (passwordEncoder.matches(l.getContrasenia(), usu.getContrasenia())) {
                Map<String, Object> claims = new HashMap<>();
                claims.put("rol", usu.getRol());
                claims.put("id", usu.getId());

                String token = jwtService.generateToken(usu.getUsername(), claims);

                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("usuario", usu.getUsername());
                response.put("rol", usu.getRol());
                response.put("id", usu.getId());
                return response;
            }
        }
        return null;
    }

    @Override
    public int validarUsuario(LoginDto l) {
        return login(l) != null ? 1 : 0;
    }
}
