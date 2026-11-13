package com.tamzuri.backend.config;

import com.tamzuri.backend.model.ContactoConfianza;
import com.tamzuri.backend.model.Usuario;
import com.tamzuri.backend.repository.ContactoRepository;
import com.tamzuri.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ContactoRepository contactoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (usuarioRepository.existsByEmail("demo@tamzuri.com")) return;

        Usuario demo = new Usuario();
        demo.setNombre("Usuaria Demo");
        demo.setEmail("demo@tamzuri.com");
        demo.setPassword(passwordEncoder.encode("123456"));
        demo.setPinSeguridad(passwordEncoder.encode("1234"));
        demo.setTelefono("600111222");
        usuarioRepository.save(demo);

        Usuario admin = new Usuario();
        admin.setNombre("Administrador");
        admin.setEmail("admin@tamzuri.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setPinSeguridad(passwordEncoder.encode("0000"));
        admin.setRol("ADMIN");
        usuarioRepository.save(admin);

        crearContacto("Mamá", "655178345", demo);
        crearContacto("Hermana", "611223344", demo);
    }

    private void crearContacto(String nombre, String telefono, Usuario u) {
        ContactoConfianza c = new ContactoConfianza();
        c.setNombre(nombre);
        c.setTelefono(telefono);
        c.setUsuario(u);
        contactoRepository.save(c);
    }
}