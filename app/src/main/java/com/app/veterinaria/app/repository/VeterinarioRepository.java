package com.app.veterinaria.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.veterinaria.app.model.Veterinario;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {

    // Método eliminado: Optional<Veterinario> findByEmail(String email);
    // Si en el futuro necesitas buscar veterinarios por email para otra funcionalidad,
    // se puede volver a añadir.
}

