package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.OdontologoDTO;
import java.util.List;


public interface IOdontologoService {
    
    public List<OdontologoDTO> getOdontologos();
    
    public void saveOdontologo(OdontologoDTO o);
    
    public void deleteOdontologo(Long id);
    
    public OdontologoDTO findOdontologo(Long id);
    
    public void editOdontologo(OdontologoDTO o);
    
    public OdontologoDTO findByUserId(Long userId);
    
}
