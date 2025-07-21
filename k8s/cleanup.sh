# VirtualGR - Documentación de APIs GraphQL

## Descripción General

VirtualGR es un sistema de gestión de tareas y proyectos universitarios desarrollado con Spring Boot y GraphQL. Permite a estudiantes y profesores gestionar equipos, proyectos y tareas de manera colaborativa.



## Endpoints Principales

### GraphQL Endpoint
- **URL:** `http://localhost:8080/graphql`
- **Método:** POST
- **Content-Type:** `application/json`

### GraphiQL Interface
- **URL:** `http://localhost:8080/graphiql`
- **Descripción:** Interfaz interactiva para probar queries

### Health Check
- **URL:** `http://localhost:8080/actuator/health`
- **Método:** GET

### Metrics
- **URL:** `http://localhost:8080/actuator/prometheus`
- **Método:** GET

## Entidades del Sistema

### Usuario
- **ID:** Identificador único
- **Nombre:** Nombre completo
- **Email:** Email institucional (@udea.edu.co)
- **Rol:** ESTUDIANTE | PROFESOR
- **Relaciones:** Equipos, Tareas creadas, Tareas asignadas

### Equipo
- **ID:** Identificador único
- **Nombre:** Nombre único del equipo
- **Creador:** Usuario que creó el equipo
- **Miembros:** Lista de usuarios con sus roles
- **Relaciones:** Proyectos

### Proyecto
- **ID:** Identificador único
- **Nombre:** Nombre del proyecto
- **Descripción:** Descripción detallada
- **Estado:** PENDIENTE | EN_PROGRESO | COMPLETADO
- **Fechas:** Inicio, fin prevista, completado
- **Relaciones:** Equipo, Tareas

### Tarea
- **ID:** Identificador único
- **Título:** Título descriptivo
- **Estado:** PENDIENTE | EN_PROGRESO | REVISION | COMPLETADA
- **Prioridad:** BAJA | MEDIA | ALTA
- **Fechas:** Creación, entrega, completado
- **Relaciones:** Proyecto, Usuario creador, Usuario asignado

## Ejemplos de Uso

### Crear Usuario
```graphql
mutation {
  createUsuario(
    nombre: "Juan Pérez"
    email: "juan.perez@udea.edu.co"
    password: "123456"
    rol: "ESTUDIANTE"
  ) {
    id
    nombre
    email
    rol
    fechaCreacion
  }
}

### Consultar Todos los Proyectos
```graphql
query {
  allProyectos {
    id
    nombre
    descripcion
    estado
    equipo {
      nombre
      creador {
        nombre
      }
    }
    tareas {
      titulo
      estado
      asignadaA {
        nombre
      }
    }
  }
}

Despliegue
Docker:
docker-compose up -d

Kubernetes:

cd k8s
./deploy.sh

