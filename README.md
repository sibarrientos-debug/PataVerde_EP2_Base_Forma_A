Clínica Veterinaria PataVerde 

Aplicación de escritorio en JavaFX para registrar y eliminar pacientes (perros y gatos) de la Clínica Veterinaria PataVerde. La información se guarda en archivos JSON, así que sigue disponible al volver a abrir la aplicación.

Evaluación Parcial 2 — DSY1102 Programación Orientada a Objetos (Duoc UC).

Tecnologías
Java 21
JavaFX 21 (FXML + Scene Builder)
Maven
Gson 2.11.0
Funcionalidades
Registrar perros y gatos desde un formulario.
Al elegir el tipo, solo se habilitan los datos de ese animal.
Validación de datos antes de registrar (tipo, nombre, edad 0–30, peso > 0, raza para perros).
Tabla con Tipo, Nombre, Edad, Peso (kg) y Costo consulta.
Eliminar la mascota seleccionada en la tabla.
Mensajes en la ventana: verde para acciones exitosas y rojo para errores.
Guardado automático en perros.json y gatos.json al registrar o eliminar.
Estructura
src/main/java/cl/duoc/pataverde
├── controller   → MainController (eventos de la vista)
├── dao          → MascotaDAO (interfaz) y MascotaJsonDAO (lectura/escritura con Gson)
├── model        → Mascota, Perro, Gato, Adoptable
├── repository   → MascotaRepository (lista observable y guardado)
├── util         → ValidadorFormulario
├── App.java
└── Launcher.java
src/main/resources/cl/duoc/pataverde/view
└── main-view.fxml

El proyecto sigue el patrón MVC con una capa de acceso a datos DAO/Repository.

Cómo ejecutar
Abrir la carpeta del proyecto en IntelliJ IDEA como proyecto Maven.
Verificar que el JDK sea 21.
Ejecutar la clase Launcher.

Los archivos perros.json y gatos.json se crean en la carpeta raíz del proyecto la primera vez que se registra una mascota.

Costos de consulta
Tipo	Base	Recargo
Perro	$15.000	+30% si no está vacunado
Gato	$12.000	+15% si vive en exterior
A
