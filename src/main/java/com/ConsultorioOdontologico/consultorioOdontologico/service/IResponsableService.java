
package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.ResponsableDTO;
import java.util.List;


public interface IResponsableService {
    
    public List<ResponsableDTO> getResponsables();
    
    public void saveResponsable(ResponsableDTO r);
    
    public void deleteResponsable(Long id);
    
    public ResponsableDTO findResponsable(Long id);
    
    public void editResponsable(ResponsableDTO r);
    
}
