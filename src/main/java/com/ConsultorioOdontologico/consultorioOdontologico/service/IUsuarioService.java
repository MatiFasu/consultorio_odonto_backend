package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.LoginDto;
import com.ConsultorioOdontologico.consultorioOdontologico.dto.UsuarioDTO;
import java.util.List;


public interface IUsuarioService {
    
    public List<UsuarioDTO> getUsuario();
    
    public UsuarioDTO saveUsuario(UsuarioDTO u);
    
    public void deleteUsuario(Long id);
    
    public UsuarioDTO findUsuario(Long id);
    
    public void editUsuario(UsuarioDTO u);

    public int validarUsuario(LoginDto l);
    
}
