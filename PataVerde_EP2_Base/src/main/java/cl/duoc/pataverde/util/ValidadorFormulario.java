package cl.duoc.pataverde.util;

public class ValidadorFormulario {

    public static String validar(String tipo, String nombre, String edadTexto,
                                 String pesoTexto, String raza) {
        // Tipo: debe estar seleccionado
        if (tipo == null) {
            return "Tipo: debe seleccionar Perro o Gato.";
        }

        // Nombre: no vacío ni solo espacios
        if (nombre == null || nombre.trim().isEmpty()) {
            return "Nombre: no puede estar vacío ni contener solo espacios.";
        }

        // Edad: número entero entre 0 y 30
        int edad;
        try {
            edad = Integer.parseInt(edadTexto.trim());
        } catch (NumberFormatException e) {
            return "Edad: debe ser un número entero.";
        }
        if (edad < 0 || edad > 30) {
            return "Edad: debe estar entre 0 y 30 años.";
        }

        // Peso: número mayor que cero, acepta decimales
        double peso;
        try {
            peso = Double.parseDouble(pesoTexto.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            return "Peso: debe ser un número (puede tener decimales).";
        }
        if (peso <= 0) {
            return "Peso: debe ser mayor que cero.";
        }

        // Raza: solo para perro, no vacía
        if (tipo.equals("Perro") && (raza == null || raza.trim().isEmpty())) {
            return "Raza: no puede estar vacía ni contener solo espacios.";
        }

        // Si llegó hasta aquí, no hay errores
        return null;
    }
}