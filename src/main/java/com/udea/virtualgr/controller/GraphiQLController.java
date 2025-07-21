package com.udea.virtualgr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GraphiQLController {

    @GetMapping("/graphiql")
    @ResponseBody
    public String graphiql() {
        return """
<!DOCTYPE html>
<html>
<head>
    <title>GraphiQL - VirtualGR</title>
    <style>
        body {
            height: 100vh;
            margin: 0;
            width: 100%;
            overflow: hidden;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        #graphiql {
            height: 100vh;
        }
        .loading {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            font-size: 18px;
        }
    </style>
</head>
<body>
    <div id="graphiql">
        <div class="loading">Cargando GraphiQL...</div>
    </div>
    
    <script crossorigin src="https://unpkg.com/react@17/umd/react.production.min.js"></script>
    <script crossorigin src="https://unpkg.com/react-dom@17/umd/react-dom.production.min.js"></script>
    <script crossorigin src="https://unpkg.com/graphiql@1.4.7/graphiql.min.js"></script>
    <link rel="stylesheet" href="https://unpkg.com/graphiql@1.4.7/graphiql.min.css" />
    
    <script>
        function graphQLFetcher(graphQLParams) {
            return fetch('/graphql', {
                method: 'post',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(graphQLParams),
                credentials: 'omit',
            }).then(function (response) {
                return response.text();
            }).then(function (responseBody) {
                try {
                    return JSON.parse(responseBody);
                } catch (error) {
                    return responseBody;
                }
            });
        }

        ReactDOM.render(
            React.createElement(GraphiQL, {
                fetcher: graphQLFetcher,
                defaultVariableEditorOpen: false,
                headerEditorEnabled: true,
                defaultQuery: `# Bienvenido a VirtualGR GraphQL API
# Sistema de gestion de tareas y proyectos universitarios
# Ejecuta las mutaciones en orden para crear datos de prueba

# 1. CREAR USUARIOS (ejecutar primero)
mutation CreateUsuario1 {
  createUsuario(
    nombre: "Juan Perez Martinez"
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

mutation CreateUsuario2 {
  createUsuario(
    nombre: "Maria Garcia Lopez"
    email: "maria.garcia@udea.edu.co"
    password: "123456"
    rol: "ESTUDIANTE"
  ) {
    id
    nombre
    email
    rol
  }
}

mutation CreateProfesor {
  createUsuario(
    nombre: "Dr. Carlos Rodriguez"
    email: "carlos.rodriguez@udea.edu.co"
    password: "123456"
    rol: "PROFESOR"
  ) {
    id
    nombre
    email
    rol
  }
}

# 2. CREAR EQUIPOS (usar ID del usuario creado como creadorId)
mutation CreateEquipo1 {
  createEquipo(
    nombre: "Equipo Alpha"
    creadorId: 1
  ) {
    id
    nombre
    fechaCreacion
    creador {
      nombre
      email
    }
  }
}

mutation CreateEquipo2 {
  createEquipo(
    nombre: "Equipo Beta"
    creadorId: 2
  ) {
    id
    nombre
    fechaCreacion
    creador {
      nombre
    }
  }
}

# 3. AGREGAR MIEMBROS A EQUIPOS
mutation AddMiembro {
  addMiembroEquipo(
    equipoId: 1
    usuarioId: 2
    rol: "MIEMBRO"
  ) {
    id
    rol
    fechaIncorporacion
    usuario {
      nombre
    }
    equipo {
      nombre
    }
  }
}

# 4. CREAR PROYECTOS (usar ID del equipo)
mutation CreateProyecto1 {
  createProyecto(
    nombre: "Sistema de Gestion Academica"
    descripcion: "Desarrollo de un sistema web para la gestion de estudiantes y cursos"
    equipoId: 1
    fechaInicio: "2025-01-15T08:00:00"
    fechaFinPrevista: "2025-06-30T18:00:00"
  ) {
    id
    nombre
    descripcion
    estado
    fechaInicio
    fechaFinPrevista
    equipo {
      nombre
      creador {
        nombre
      }
    }
  }
}

mutation CreateProyecto2 {
  createProyecto(
    nombre: "App Movil Universidad"
    descripcion: "Aplicacion movil para servicios universitarios"
    equipoId: 2
    fechaInicio: "2025-02-01T09:00:00"
    fechaFinPrevista: "2025-07-15T17:00:00"
  ) {
    id
    nombre
    descripcion
    estado
    equipo {
      nombre
    }
  }
}

# 5. CREAR TAREAS (usar IDs de proyecto y usuario)
mutation CreateTarea1 {
  createTarea(
    titulo: "Diseno de Base de Datos"
    descripcion: "Crear el diagrama ER y las tablas de la base de datos"
    proyectoId: 1
    creadaPorId: 1
    asignadaAId: 2
    prioridad: "ALTA"
    fechaEntrega: "2025-02-15T23:59:59"
  ) {
    id
    titulo
    descripcion
    estado
    prioridad
    fechaCreacion
    fechaEntrega
    proyecto {
      nombre
    }
    creadaPor {
      nombre
    }
    asignadaA {
      nombre
    }
  }
}

mutation CreateTarea2 {
  createTarea(
    titulo: "Desarrollo API REST"
    descripcion: "Implementar los endpoints principales de la API"
    proyectoId: 1
    creadaPorId: 1
    asignadaAId: 1
    prioridad: "MEDIA"
    fechaEntrega: "2025-03-30T23:59:59"
  ) {
    id
    titulo
    estado
    prioridad
    proyecto {
      nombre
    }
    asignadaA {
      nombre
    }
  }
}

# QUERIES PARA CONSULTAR DATOS

# Consultar todos los usuarios
query GetAllUsuarios {
  allUsuarios {
    id
    nombre
    email
    rol
    fechaCreacion
  }
}

# Consultar todos los equipos con sus miembros
query GetAllEquipos {
  allEquipos {
    id
    nombre
    fechaCreacion
    creador {
      nombre
      email
    }
    miembros {
      rol
      fechaIncorporacion
      usuario {
        nombre
        email
      }
    }
  }
}

# Consultar todos los proyectos
query GetAllProyectos {
  allProyectos {
    id
    nombre
    descripcion
    estado
    fechaInicio
    fechaFinPrevista
    equipo {
      nombre
      creador {
        nombre
      }
    }
    tareas {
      titulo
      estado
      prioridad
    }
  }
}

# Consultar todas las tareas
query GetAllTareas {
  allTareas {
    id
    titulo
    descripcion
    estado
    prioridad
    fechaCreacion
    fechaEntrega
    proyecto {
      nombre
      equipo {
        nombre
      }
    }
    creadaPor {
      nombre
    }
    asignadaA {
      nombre
    }
  }
}

# Consultar usuario especifico
query GetUsuarioById {
  usuarioById(id: 1) {
    id
    nombre
    email
    rol
    equiposCreados {
      nombre
    }
    tareasAsignadas {
      titulo
      estado
      fechaEntrega
    }
  }
}

# Consultar tareas por proyecto
query GetTareasByProyecto {
  tareasByProyecto(proyectoId: 1) {
    id
    titulo
    estado
    prioridad
    asignadaA {
      nombre
    }
  }
}

# MUTACIONES ADICIONALES

# Actualizar estado de proyecto
mutation UpdateProyectoEstado {
  updateEstadoProyecto(
    id: 1
    estado: "EN_PROGRESO"
  ) {
    id
    nombre
    estado
    fechaCompletado
  }
}

# Actualizar estado de tarea
mutation UpdateTareaEstado {
  updateEstadoTarea(
    id: 1
    estado: "EN_PROGRESO"
  ) {
    id
    titulo
    estado
    fechaCompletado
  }
}

# Asignar tarea a otro usuario
mutation AsignarTarea {
  asignarTarea(
    tareaId: 2
    usuarioId: 2
  ) {
    id
    titulo
    asignadaA {
      nombre
      email
    }
  }
}`,
            }),
            document.getElementById('graphiql'),
        );
    </script>
</body>
</html>
        """;
    }
}