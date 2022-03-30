# CRUDRelacionesSpring

Crear CRUD de la siguiente tabla: table persona { id_persona int [pk, increment] usuario string [not null max-length: 10 min-length: 6] password string [not null] name string [not null] surname string company_email string [not null ] personal_email string [not null] city string [not null] active boolean [not null] created_date date [not null] imagen_url string termination_date date }

Realizar validaciones necesarias con lógica en java (no usar etiqueta @Valid) if (usuario==null) {throw new Exception(“Usuario no puede ser nulo”); } if (usuario.length()>10) { throw new Exception(“Longitud de usuario no puede ser superior a 10 caracteres) ...

Poner 3 endpoints en la búsqueda. ⦁ Buscar por ID ⦁ Buscar por nombre de usuario (campo usuario) ⦁ Mostrar todos los registros.

Usar DTOS, interfaces y clases de servicio.

Crear dos tipos de excepción al CRUD anteriormente realizado: NotFoundException que generara un código HTTP 404. 
Se lanzará cuando no se encuentren registros en un findById o si al borrar o modificar un registro este no existe. 
UnprocesableException que devolverá un 422 (UNPROCESSABLE ENTITY) cuando la validación de los campos no cumpla los requisitos establecidos, al modificar o insertar un registro. 
Ambas excepciones deberán devolver un objeto llamado CustomError con los campos: Date timestamp; Int HttpCode; String mensaje; // Devolverá el mensaje de error.

añadir las siguientes tablas: 
table student
{
  id_student string [pk, increment]
  id_persona string [ref:-  persona.id_persona] -- Relación OneToOne con la tabla persona.
  num_hours_week int   [not null] -- Número de horas semanales del estudiante en formación
  coments string 
  id_profesor string [ref: > profesor.id_profesor] -- Un estudiante solo puede tener un profesor.
  branch string [not null] -- Rama principal delestudiante (Front, Back, FullStack)
}
table profesor
{
  id_profesor string [pk, increment]
  id_persona string [ref:- persona.id_persona] -- Relación OneToOne con la tabla persona.
  coments string 
  branch string [not null] -- Materia principal que imparte. Por ejemplo: Front
}
table estudiante_asignatura
{
  id_asignatura String [pk, increment]
  id_student string [ref: > student.id_student] -- Un estudiante puede tener N asignaturas
  asignatura string  -- Ejemplo: HTML, Angular, SpringBoot... 
 coments string
 initial_date date [not null], -- Fecha en que estudiante empezó a estudiar la asignatura
 finish_date date  -- Fecha en que estudiante termina de estudiar la asignatura
}
--- Ejemplo de entidades --

@Entity
@Table(name = "estudiantes")
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id_student;
    @OneToOne
    @JoinColumn(name = "id_persona")
    Persona persona;
    @Column(name = "horas_por_semana")
    Integer num_hours_week;
    @Column(name = "comentarios")
    String coments;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profesor")
    Profesor profesor;
    @Column(name = "rama")
    String branch;
    @OneToMany
    List<Alumnos_Estudios> estudios;
}

 @Entity
@Table(name = "estudios")
@Getter
@Setter
public class Alumnos_Estudios {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id_study;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id")
    Profesor profesor;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_student")
    Student student;
    @Column(name = "asignatura")
    String asignatura;
    @Column(name = "comentarios")
    String comment;
    @Column(name = "initial_date")
    Date initial_date;
    @Column(name = "finish_date")
    Date finish_date;
---

La relación gráfica entre las tablas la tenéis en este enlace: https://dbdiagram.io/d/60938b29b29a09603d139d64 
La relación de la tabla student y profesor con Persona es one-to-one
La relación entre estudiante y profesor es tipo one-to-many. Un estudiante puede tener un solo profesor y un profesor puede tener N estudiantes. 
Un estudiante puede estar en N estudios por lo cual la relación es many-to-many.
Realizar CRUD sobre TODAS las tablas. Cuando se busque por ID la persona, si es estudiante, debe devolver los datos de ‘estudiante’, ‘profesores’ y los estudios realizados por el estudiante. Si la persona es profesor, sacara el profesor los estudiantes a su cargo, y para cada estudiante los estudios realizados
Como se puede apreciar los índices son tipo String y autoincreméntales. Recordar que en el punto DB1-1 tenéis como crear campos autoincreméntales del tipo String.
⦁	Primera parte ejercicio:  Realizar CRUD de estudiante con endpoints similares a los de persona. De momento, no poner relación con profesor ( id_profesor )
Modificar endpoint “/estudiante/{id}” para que acepte el QueryParam ‘ouputType’. Por defecto deberá tener el valor ‘simple’ (es decir si no se manda cogerá como valor el literal ‘simple’). 
En el caso de que el valor de ‘outputType’ sea ‘simple‘,  la consulta devolverá solo los datos del estudiante. En el caso de que sea ‘full’ devolverá los datos del estudiante y de la persona asociada.
Ejemplo: 
GET “http://localhost:8080/{id}?outputType=full”
{
  id_student : 1,
  num_hours_week : 40,
  coments: “No puede trabajar por las tardes”,
 id_persona: 1,
 user: ‘francisco’,
 password: “secreto”,
 Name: “Francisco”,
 Surname: “perez”,
 company_email: “francisco@bosonit.com”,
 personal_email: “francisco@gmail.com”,
 city : “zaragoza”,
 Active: true
 created_date: ‘2021-10-03'
 imagen_url: “http://pictures.com/imagen1.png”,
 termination_date: null
}

Como se puede observar habrá que crear diferentes DTOs de salida.
⦁	Segunda parte ejercicio:  Realizar CRUD de las tablas restantes.
Tener en cuenta que UNA persona solo puede ser o profesor o estudiante. 
En TODOS los endpoints de búsqueda de la entidad persona (por ID, por nombre o todas las personas), aceptar un parámetro que indique si debe devolver solo los datos de la persona o también los de estudiante o profesor si fuera alguno de ellos.
⦁	Tercera parte: Crear endpoint en ‘estudiante_asignatura’ que permita buscar por id de estudiante. Este endpoint sacara todas las asignaturas que tiene un estudiante.
 Realizar chequeos lógicos: ¿Borrar persona si tiene un estudiante/profesor asignado? ¿Borrar asignatura si tiene estudiantes asignados?
⦁	Cuarta parte: Realizar endpoints en estudiante para asignarle una o más asignaturas. Crear otro endpoint para desasignar una o más asignaturas (deberá poder recibir una lista de IDs de asignaturas).
