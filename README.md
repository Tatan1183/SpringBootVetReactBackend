 VetSys Pro - Backend (Spring Boot API)

Este es el componente backend del sistema de gestión de clínica veterinaria VetSys Pro. Proporciona una API RESTful para gestionar citas, clientes, mascotas, servicios y veterinarios.

-Características Principales

*API RESTful: Endpoints para operaciones CRUD en todas las entidades principales.
*Persistencia de Datos: Uso de Spring Data JPA con Hibernate para interactuar con una base de datos MySQL.
*Manejo de Archivos: Endpoints para la subida de imágenes para mascotas y veterinarios, almacenadas localmente en el servidor.
*Configuración CORS: Permite peticiones desde el frontend desarrollado en React.

# Tecnologías Utilizadas

-   Java 17 (o la versión que estés usando)
-   Spring Boot (última versión estable usada, ej: 3.5.0)
-   Spring Data JPA
-   Spring Web
-   Hibernate
-   MySQL Connector/J
-   Lombok
-   Jackson (para JSON)
-   Maven


-Estructura del Proyecto Backend

![image](https://github.com/user-attachments/assets/c2868a97-79cf-45f3-bde0-940628519689)

 -Configuración y Puesta en Marcha


-Prerrequisitos

-   JDK 17 (o la versión correspondiente) instalado.
-   Maven 3.6+ (o Gradle) instalado.
-  Servidor de base de datos MySQL en ejecución.

"Base de Datos"

1.  Asegúrate de tener un servidor MySQL accesible.
2.  Crea una base de datos llamada `appveterinaria` (o el nombre que hayas usado).
    ```sql
    CREATE DATABASE IF NOT EXISTS appveterinaria;
    ```
3.  El esquema de las tablas se creará/actualizará automáticamente por Hibernate basado en las entidades (`spring.jpa.hibernate.ddl-auto=update`).


Mysql

-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS appveterinaria;
USE appveterinaria;

-- Tabla de veterinarios
CREATE TABLE veterinarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    imagen VARCHAR(255)
);

-- Tabla de clientes
CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    email VARCHAR(100) UNIQUE,
    direccion VARCHAR(255)
);

-- Tabla de mascotas
CREATE TABLE mascotas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    especie VARCHAR(50) NOT NULL,
    raza VARCHAR(100),
    fecha_nacimiento DATE,
    imagen VARCHAR(255),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE
);

-- Tabla de servicios
CREATE TABLE servicios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL
);

-- Tabla de citas
CREATE TABLE citas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mascota_id INT NOT NULL,
    veterinario_id INT NOT NULL,
    servicio_id INT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    estado VARCHAR(20) NOT NULL,
    notas TEXT,
    FOREIGN KEY (mascota_id) REFERENCES mascotas(id) ON DELETE CASCADE,
    FOREIGN KEY (veterinario_id) REFERENCES veterinarios(id) ON DELETE CASCADE,
    FOREIGN KEY (servicio_id) REFERENCES servicios(id) ON DELETE CASCADE
);

-- Índices para mejorar el rendimiento
CREATE INDEX idx_mascotas_cliente ON mascotas(cliente_id);
CREATE INDEX idx_citas_mascota ON citas(mascota_id);
CREATE INDEX idx_citas_veterinario ON citas(veterinario_id);
CREATE INDEX idx_citas_servicio ON citas(servicio_id);
CREATE INDEX idx_citas_fecha ON citas(fecha_hora);



-- API Endpoints Principales

   Autenticación:
      `POST /api/auth/login`: Iniciar sesión y obtener token JWT.
   Citas:
      `GET /api/citas`: Listar todas.
      `GET /api/citas/{id}`: Obtener una cita.
      `POST /api/citas`: Crear nueva.
      `PUT /api/citas/{id}`: Actualizar existente.
      `DELETE /api/citas/{id}`: Eliminar.
   Clientes:
      `GET /api/clientes`: Listar todos.
      `GET /api/clientes/{id}`: Obtener un cliente.
      `POST /api/clientes`: Crear nuevo.
      `PUT /api/clientes/{id}`: Actualizar existente.
       `DELETE /api/clientes/{id}`: Eliminar.
  Mascotas:
       `GET /api/mascotas`: Listar todas.
       `GET /api/mascotas/{id}`: Obtener una mascota.
       `POST /api/mascotas`: Crear nueva.
       `POST /api/mascotas/upload`: Subir imagen para mascota (devuelve nombre del archivo).
       `PUT /api/mascotas/{id}`: Actualizar existente.
       `DELETE /api/mascotas/{id}`: Eliminar.
  Servicios:
       `GET /api/servicios`: Listar todos.
      `GET /api/servicios/{id}`: Obtener un servicio.
       `POST /api/servicios`: Crear nuevo.
       `PUT /api/servicios/{id}`: Actualizar existente.
       `DELETE /api/servicios/{id}`: Eliminar.
   Veterinarios:
       `GET /api/veterinarios`: Listar todos.
       `GET /api/veterinarios/{id}`: Obtener un veterinario.
       `POST /api/veterinarios`: Crear nuevo.
       `POST /api/veterinarios/upload`: Subir imagen para veterinario (devuelve nombre del archivo).
       `PUT /api/veterinarios/{id}`: Actualizar existente.
       `DELETE /api/veterinarios/{id}`: Eliminar.


-Configuración de la Aplicación

Modifica el archivo `src/main/resources/application.properties` con la configuración de tu base de datos:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/appveterinaria # Ajusta el puerto si es necesario
spring.datasource.username=tu_usuario_mysql
spring.datasource.password=tu_contraseña_mysql

server.port=8095 # Puerto en el que correrá el backend

La API estará disponible en http://localhost:8095.












