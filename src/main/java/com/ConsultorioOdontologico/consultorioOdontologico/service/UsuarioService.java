package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.config.JwtService;
import com.ConsultorioOdontologico.consultorioOdontologico.dto.LoginDto;
import com.ConsultorioOdontologico.consultorioOdontologico.dto.UsuarioDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Usuario;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IUsuarioRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements IUsuarioService {
    
    @Autowired
    private IUsuarioRepository usuRepo;

    @Autowired
    private com.ConsultorioOdontologico.consultorioOdontologico.repository.IOdontologoRepository odontoRepo;
    
    @Autowired
    private com.ConsultorioOdontologico.consultorioOdontologico.repository.ISecretariaRepository secreRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public List<UsuarioDTO> getUsuario() {
        return usuRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO saveUsuario(UsuarioDTO uDTO) {
        Usuario u = convertToEntity(uDTO);
        if (u.getContrasenia() != null && !u.getContrasenia().startsWith("$2")) {
            u.setContrasenia(passwordEncoder.encode(u.getContrasenia()));
        }
        Usuario guardado = usuRepo.save(u);
        return convertToDTO(guardado);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void deleteUsuario(Long id) {
        odontoRepo.findAll().stream()
            .filter(o -> o.getUnUsuario() != null && o.getUnUsuario().getId_usuario().equals(id))
            .forEach(o -> {
                o.setUnUsuario(null);
                odontoRepo.save(o);
            });

        secreRepo.findAll().stream()
            .filter(s -> s.getUnUsuario() != null && s.getUnUsuario().getId_usuario().equals(id))
            .forEach(s -> {
                s.setUnUsuario(null);
                secreRepo.save(s);
            });

        usuRepo.deleteById(id);
    }

    @Override
    public UsuarioDTO findUsuario(Long id) {
        Usuario u = usuRepo.findById(id).orElse(null);
        return (u != null) ? convertToDTO(u) : null;
    }

    @Override
    public void editUsuario(UsuarioDTO uDTO) {
        Usuario original = usuRepo.findById(uDTO.getId_usuario()).orElse(null);
        if (original != null) {
            Usuario u = convertToEntity(uDTO);
            if (u.getContrasenia() != null && !u.getContrasenia().isEmpty() && !u.getContrasenia().startsWith("$2")) {
                u.setContrasenia(passwordEncoder.encode(u.getContrasenia()));
            } else {
                u.setContrasenia(original.getContrasenia());
            }
            usuRepo.save(u);
        }
    }

    public Map<String, Object> login(LoginDto l) {
        Optional<Usuario> usuarioOpt = usuRepo.findByUsuario(l.getUsername());
        
        if (usuarioOpt.isPresent()) {
            Usuario usu = usuarioOpt.get();
            if (passwordEncoder.matches(l.getContrasenia(), usu.getContrasenia())) {
                Map<String, Object> claims = new HashMap<>();
                claims.put("rol", usu.getRol());
                claims.put("id_usuario", usu.getId_usuario());
                
                String token = jwtService.generateToken(usu.getUsername(), claims);
                
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("usuario", usu.getUsername());
                response.put("rol", usu.getRol());
                response.put("id_usuario", usu.getId_usuario());
                return response;
            }
        }
        return null;
    }

    @Override
    public int validarUsuario(LoginDto l) {
        return login(l) != null ? 1 : 0;
    }

    private UsuarioDTO convertToDTO(Usuario u) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId_usuario(u.getId_usuario());
        dto.setUsuario(u.getUsuario());
        dto.setRol(u.getRol());
        // NO devolvemos la contraseña
        return dto;
    }

    private Usuario convertToEntity(UsuarioDTO dto) {
        Usuario u = new Usuario();
        u.setId_usuario(dto.getId_usuario());
        u.setUsuario(dto.getUsuario());
        u.setRol(dto.getRol());
        u.setContrasenia(dto.getContrasenia());
        return u;
    }
}
