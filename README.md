<h1 align="center">Arquitectura Hexagonal - To Do App</h1> 

<h2>Descripción de la aplicación</h2>

<p>Esta aplicación esta basada en un sistema de gestion de tareas, en el cual se implementa la arquitectura hexagonal. La aplicacion consta de dos entidades, usuarios y tareas, cada entidad tiene su respectivo crud; crear, leer, actualizar y eliminar, métodos adicionales como asignar y remover tareas de usuarios. Esta api te permite obtener listados de usuarios por busquedas. Cuenta con métodos como obtener lista de usuarios por su nombre, su apellido o por su rol.</p> 
  
<p>La entidad tarea tiene  métodos adicionales para poder manipular sus datos, tales como: modificar el estado a una tarea, obtener una lista de tareas por su fecha de creación, fecha de finalización, por su estado o usuarios asignados a una tarea. La mayor parte de los métodos cuentan con permisos, ya que cada usuario tiene ciertos accesos, por ejemplo, un usuario con rol <strong>COLLABORATOR</strong> solo puede modificar un estado a una tarea si esta le está asignada, de lo contrario no se le permite acceso, estos estados son <strong>IN_PROGRESS</strong> o <strong>REVISION</strong>, no puede tener acceso a completada.</p>

<p>Contiene validaciones en cada una de sus capas, tanto en dominio, aplicación e infraestructura para el funcionamiento correcto de la api. Estas validaciones están solo para que a la base de datos ingrese información que necesitamos, un ejemplo de ello, es que un nombre de usuario no permite recibir numeros o caracteres especiales, todos estos errores se realizan con manejo de excepciones.</p> 

| ROLES PERMITIDOS | ESTADOS DE LAS TAREAS PERMITIDOS |
| :----: | :----: |
| ADMIN  | IN_PROGRESS |                                           
|LEADER | COMPLETED |                                                
|COLLABORATOR | REVISION |

<h2>Descripción de la arquitectura</h2>
<p>La arquitectura hexagonal, también conocida como puertos y adaptadores, es un enfoque de diseño de software que busca desacoplar las diferentes partes de una aplicación para hacerla más flexible y fácil de mantener. Se llama hexagonal porque visualmente se representa con un núcleo central rodeado por seis lados, cada uno representando una interfaz o puerto de entrada/salida.</p>

<p>En esta arquitectura, el núcleo central contiene la lógica de negocio o funcionalidad principal de la aplicación. Este núcleo no depende de ningún detalle de implementación específico, como la interfaz de usuario o la base de datos.</p>

<p>Los puertos son las interfaces a través de las cuales el núcleo interactúa con el mundo exterior. Por ejemplo, puede haber puertos para la interfaz de usuario, la persistencia de datos, servicios externos, etc. Estos puertos definen las operaciones que el núcleo puede realizar, pero no especifican cómo se realizan esas operaciones.</p>

<p>Los adaptadores son las implementaciones concretas de estos puertos. Son responsables de conectar el núcleo con el mundo exterior. Por ejemplo, un adaptador de interfaz de usuario podría ser una aplicación web o una aplicación de escritorio que permite a los usuarios interactuar con el sistema, mientras que un adaptador de persistencia de datos podría ser una base de datos SQL o un almacenamiento en memoria.</p>

<h3>Tecnologías aplicadas</h3>

![Static Badge](https://img.shields.io/badge/Java%2017%20-%20blue?style=flat&link=https%3A%2F%2Fdocs.oracle.com%2Fen%2Fjava%2Fjavase%2F17%2Fdocs%2Fapi%2F)
![Static Badge](https://img.shields.io/badge/Spring%20Boot%203.2.5%20-%20green?style=flat&link=https%3A%2F%2Fspring.io%2Fguides%2Fgs%2Fspring-boot)
![Static Badge](https://img.shields.io/badge/Gradle%20-%20gray?style=flat)
![Static Badge](https://img.shields.io/badge/MapStruct%20-%20violet?style=flat)


## Rutas de la API -  Entidad User

- `Post: http://localhost:8080/users/save/{idCreator}`: Me permite crear un nuevo usuario, con el Rol de Admin
```
  {
    "id": "0",
    "name": "string",
    "lastname":"string",
    "email": "string@example.com",
    "password": "string*123A",
    "role": "COLLABORATOR",
    "tasks": []
}
```
- `Get: http://localhost:8080/users/id/{idUser}`: Obtienes un usuario por id, debes ingresar el id del usuario como argumento.
- `Put: http://localhost:8080/users/update/{idUpdater}`: Puedo actualizar un usuario, siempre y cuando tenga el rol de Admin, se pasa como argumento el id de actualizador (Se valida que el id del del actualizador tenga rol de Admin) y en el cuerpo, el id del usuario a actualizar.
```
{  
    "id": "10",
    "name": "string",
    "lastname":"string",
    "email": "string@example.com",
    "password": "string*123A",
    "role": "COLLABORATOR",
    
}
```
- `Get: http://localhost:8080/users/all`: Me permite obtener todos los usuarios registrados en la base de datos.
- `Get: http://localhost:8080/users/name/{name}`: Obtienes una lista de usuarios por el nombre.
- `Get: http://localhost:8080/users/lastname/{lastname}`: Obtienes una lista de usuarios por el apellido.
- `Get: http://localhost:8080/users/task-id/{idTask}`: Obtienes una lista de usuarios asignados al id de una tarea.
- `Get: http://localhost:8080/users/role/{role}`: Obtienes una lista de usuarios por su rol.
- `Delete: http://localhost:8080/users/delete/{idUserToDelete}/{idUserDeleter}}`: Puedo eliminar un usuario, siempre y cuando tenga el rol de Admin, se envía como argumento el id del usuario a eliminar y el id del eliminador.

## Rutas de la API Entidad Task

- `Post: http://localhost:8080/tasks/save/{idCreator}`: Me permite crear una nueva tarea con el Rol de Leader; no se le pasa el id de la tarea en el cuerpo, ya que esta se autogenera, <strong>la fecha final no puede ser anterior a la fecha actual.</strong>
```
{   
    "title": "Task prueba2",
    "description": "Ejemplo de descripcion",    
    "finishDate": "2024-04-10",
    "status": "IN_PROGRESS",
    "historyPoints": 100         
}
```

- `Put: http://localhost:8080/tasks/update/{idUpdater}`: Puedo actualizar una tarea, siempre y cuando tenga el rol de Leader, se pasa como argumento el id de actualizador (Se valida que el id del del actualizador tenga rol de Leader) y en el cuerpo, el id de la tarea a actualizar.
```
{
    "id": 1
    "title": "Task prueba2",
    "description": "Ejemplo de descripcion",    
    "finishDate": "2024-04-10",
    "status": "IN_PROGRESS",
    "historyPoints": 100            
}
```
- `Delete: http://localhost:8080/tasks/delete/{idTaskToDelete}/{idDeleter}`: Puedo eliminar una tarea siempre y cuando tena el Rol de Leader, se envía como argumento el id de la tarea y el id del eliminador
- `Get: http://localhost:8080/tasks/all`: Me permite obtener un listado de todas las tareas.
- `Get: http://localhost:8080/tasks/id/{idTask}`: Me permite obtener una tarea por su id.
- `Get: http://localhost:8080/tasks/task-by-user/{idUser}`: Me permite obtener una lista de tareas asignadas al id del usuario. 
- `Get: http://localhost:8080/tasks/task-by-status/{status}`: Me permite obtener una lista de tareas por su estado. 
- `Get: http://localhost:8080/tasks/task-by-start/{date}`: Me permite obtener una lista de tareas por su fecha de creación.
- `Get: http://localhost:8080/tasks/task-by-finish/{date}`: Me permite obtener una lista de tareas por su fecha de finalización.
- `Put: http://localhost:8080/tasks/remove-user/{idUserToDelete}/{idTaskToDelete}/{idUserDeleter}`: Me permite remover un usuario de una tarea, se le pasan como argumentos el id del usuario que quieres remover de la tarea, el id de la tarea y el id del usuario con el rol Leader con el permiso para remover.
- `Put: http://localhost:8080/tasks/assign-user/{idUserToAssign}/{idTaskToAssign}/{idUserAssigner}`: Me permite asignar un usuario a una tarea, se le pasan como argumentos el id del usuario al que le quiero asignar la tarea, el id de la tarea y el id del usuario con el rol Leader con el permiso para asignar.
- `Put: http://localhost:8080/tasks/task-by-month/{numberMonth}`: Obtienes una lista de tareas por su mes, se le pasa como argumento el numero del mes.

## Autores

| [<img src="https://avatars.githubusercontent.com/u/93169838?v=4" width=115><br><sub>Santiago Valencia</sub>](https://github.com/santiago971023) | [<img src="https://avatars.githubusercontent.com/u/104730392?v=4" width=115><br><sub>Yesika Simijaca</sub>](https://github.com/SimijacaB) 
| :---: | :---: |
