
package com.ConsultorioOdontologico.consultorioOdontologico.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter @Setter
@Entity
@Audited
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @jakarta.persistence.Column(name = "id")
    private Long id;
    
    @jakarta.persistence.Column(unique = true, nullable = false)
    private String usuario;
    
    private String contrasenia;
    private String rol;

    public Usuario() {
    }

    public Usuario(Long id, String usuario, String contrasenia, String rol) {
        this.id = id;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol));
    }

    @Override
    public String getPassword() {
        return contrasenia;
    }

    @Override
    public String getUsername() {
        return usuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

