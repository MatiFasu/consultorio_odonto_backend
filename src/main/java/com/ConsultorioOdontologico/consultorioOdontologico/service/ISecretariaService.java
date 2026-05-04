package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.SecretariaDTO;
import java.util.List;


public interface ISecretariaService {
    
    public List<SecretariaDTO> getSecretarias();
    
    public void saveSecretaria(SecretariaDTO s);
    
    public void deleteSecretaria(Long id);
    
    public SecretariaDTO findSecretaria(Long id);
    
    public void editSecretaria(SecretariaDTO s);
    
}
