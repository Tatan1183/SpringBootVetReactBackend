package com.app.veterinaria.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.veterinaria.app.model.Cliente;

@Repository // Marca esta interfaz como un repositorio Spring, lo que permite inyectarla en otros componentes
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}