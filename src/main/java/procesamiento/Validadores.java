package procesamiento;

import exceptions.EdadNoValidaException;

public interface Validadores {
    
    void validar() throws Exception;

    // ============ CONSTANTES VALIDAS ============
    String[] SEXOS_DISPONIBLES = {"M", "F", "Otro"};
    
    int LONGITUD_RUT_MINIMA = 8;
    int LONGITUD_TELEFONO_MINIMA = 8;
    int LONGITUD_TELEFONO_MAXIMA = 15;
    
    // ============ MÉTODOS ESTÁTICOS DE UTILIDAD ============
    static boolean esRutValido(String rut) {
        if (rut == null || rut.trim().isEmpty()) return false;

        // CORRECCIÓN: Ahora borra puntos, guiones O espacios en cualquier lugar
        String rutLimpio = rut.replaceAll("[\\-\\.\\s]", "").trim().toUpperCase();

        if (rutLimpio.length() < LONGITUD_RUT_MINIMA) return false;

        // Verifica que todo sean números y termine en un número o K
        return rutLimpio.matches("^[0-9]+[0-9K]$"); 
    }

    static String obtenerMensajeErrorRUT(String rut) {
        if (rut == null || rut.trim().isEmpty()) return "El RUT no puede estar vacío";
        
        // 1 y 2. CORRECCIÓN: Mismo regex del validador y agregamos toUpperCase()
        String rutLimpio = rut.replaceAll("[\\-\\.\\s]", "").trim().toUpperCase();
        
        if (rutLimpio.length() < LONGITUD_RUT_MINIMA) {
            return "El RUT debe tener al menos " + LONGITUD_RUT_MINIMA + " caracteres.";
        }
        
        // 3. CORRECCIÓN: Misma validación estricta que arriba
        if (!rutLimpio.matches("^[0-9]+[0-9K]$")) {
            return "El RUT debe contener solo números (y opcionalmente K al final).";
        }
        
        return "Formato de RUT inválido.";
    }
    
    static boolean esSexoValido(String sexo) {
        if (sexo == null || sexo.trim().isEmpty()) return false;
        String sexoNormalizado = sexo.trim();
        for (String s : SEXOS_DISPONIBLES) {
            if (s.equals(sexoNormalizado)) return true;
        }
        return false;
    }

    static String[] obtenerSexosDisponibles() {
        return SEXOS_DISPONIBLES.clone();
    }

    static String obtenerMensajeErrorSexo() {
        return "Sexo inválido. Sexos válidos: " + String.join(", ", SEXOS_DISPONIBLES);
    }
    
    static boolean esTelefonoValido(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) return false;
        String digitosSolo = telefono.replaceAll("[^0-9]", "");
        return digitosSolo.length() >= LONGITUD_TELEFONO_MINIMA && digitosSolo.length() <= LONGITUD_TELEFONO_MAXIMA;
    }
    
    static String obtenerMensajeErrorTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) return "El teléfono no puede estar vacío";
        String digitosSolo = telefono.replaceAll("[^0-9]", "");
        if (digitosSolo.length() < LONGITUD_TELEFONO_MINIMA) return "El teléfono debe tener al menos " + LONGITUD_TELEFONO_MINIMA + " dígitos.";
        if (digitosSolo.length() > LONGITUD_TELEFONO_MAXIMA) return "El teléfono no debe exceder " + LONGITUD_TELEFONO_MAXIMA + " dígitos.";
        return "Formato de teléfono inválido";
    }
    
    static boolean esNombreValido(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return false;
        return nombre.trim().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s\\-']+");
    }
    
    static String obtenerMensajeErrorNombre() {
        return "El nombre contiene caracteres inválidos. Solo se permiten letras, espacios, guiones y apóstrofes.";
    }
    
    static void validarEdad(int edad) throws EdadNoValidaException {
        if (edad < 18 || edad > 65) {
            throw new EdadNoValidaException(edad);
        }
    } 
}