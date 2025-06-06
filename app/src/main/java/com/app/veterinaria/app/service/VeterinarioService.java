package com.app.veterinaria.app.service;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder; // Eliminada
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.app.veterinaria.app.model.Veterinario;
import com.app.veterinaria.app.repository.VeterinarioRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class VeterinarioService {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    // @Autowired // Eliminada
    // private PasswordEncoder passwordEncoder; // Eliminada

    public List<Veterinario> findAll() {
        return veterinarioRepository.findAll();
    }

    public Optional<Veterinario> findById(Long id) {
        return veterinarioRepository.findById(id);
    }

    @Transactional
    public Veterinario save(Veterinario veterinario) {
        Veterinario vetToSave;

        if (veterinario.getId() != null) {
            // --- ACTUALIZACIÓN ---
            Veterinario existingVet = veterinarioRepository.findById(veterinario.getId())
                    .orElseThrow(() -> new RuntimeException("Veterinario no encontrado con id: " + veterinario.getId()));

            existingVet.setNombre(veterinario.getNombre());
            existingVet.setApellido(veterinario.getApellido());
            existingVet.setEspecialidad(veterinario.getEspecialidad());
            existingVet.setEmail(veterinario.getEmail()); // El email se puede seguir actualizando

            // Imagen: si no se proporciona una nueva, mantiene la imagen existente
            existingVet.setImagen(
                    (veterinario.getImagen() == null || veterinario.getImagen().trim().isEmpty())
                    ? existingVet.getImagen()
                    : veterinario.getImagen()
            );

            // Ya no hay manejo de contraseña
            vetToSave = existingVet;

        } else {
            // --- CREACIÓN ---
            // Ya no hay encriptación de contraseña
            vetToSave = veterinario;
        }

        return veterinarioRepository.save(vetToSave);
    }

    public void deleteById(Long id) {
        veterinarioRepository.deleteById(id);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        // Asegúrate que la carpeta 'src/main/resources/static/images/' exista
        Path uploadPath = Paths.get("src/main/resources/static/images/");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path path = uploadPath.resolve(fileName);
        Files.write(path, file.getBytes());
        return fileName;
    }
}