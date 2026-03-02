package com.ConsultorioOdontologico.consultorioOdontologico;

import com.ConsultorioOdontologico.consultorioOdontologico.model.Usuario;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IUsuarioRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.utils.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final IUsuarioRepository usuarioRepository;

    public DataLoader(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setUsuario("admin");
            admin.setContrasenia(BCrypt.hashpw("admin", BCrypt.gensalt()));
            admin.setRol("ADMIN");
            usuarioRepository.save(admin);
            System.out.println(">>> SISTEMA: Usuario admin/admin creado automáticamente.");
        }
    }
}
