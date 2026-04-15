package procesamiento;

public class Validadores {
    
    // ============ CONSTANTES VÁLIDAS ============
    private static final String[] SEXOS_DISPONIBLES = {
        "M", "F", "Otro"
    };
    private static final String[] TIPOS_SANGRE_VALIDOS = {
        "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"
    };
    
    private static final int LONGITUD_RUT_MINIMA = 8;
    private static final int LONGITUD_TELEFONO_MINIMA = 8;
    private static final int LONGITUD_TELEFONO_MAXIMA = 15;
    
    // ============ VALIDACIÓN DE RUT ============

    /**
     * Valida un RUT chileno (formato básico, sin verificar dígito verificador).
     * 
     * @param rut el RUT a validar (puede tener o no puntos/guión)
     * @return true si el RUT tiene formato válido (8+ dígitos)
     */
    public static boolean esRutValido(String rut) {
        if (rut == null || rut.trim().isEmpty()) {
            return false;
        }
    
        // Remover puntos, guiones y espacios
        String rutLimpio = rut.replaceAll("[\\-\\.]\\s", "").trim().toUpperCase();
    
        // Debe tener al menos 8 caracteres
        if (rutLimpio.length() < LONGITUD_RUT_MINIMA) {
            return false;
        }
    
        // Debe contener solo dígitos (y opcionalmente K al final)
        if (!rutLimpio.matches("\\d+[0-9K]?")) {
            return false;
        }
    
        return true;
    }

    public static String obtenerMensajeErrorRUT(String rut) {
        if (rut == null || rut.trim().isEmpty()) {
            return "El RUT no puede estar vacío";
        }
    
        String rutLimpio = rut.replaceAll("[\\-\\.]\\s", "").trim();
    
        if (rutLimpio.length() < LONGITUD_RUT_MINIMA) {
            return "El RUT debe tener al menos " + LONGITUD_RUT_MINIMA + 
               " caracteres. Ingresaste: " + rutLimpio.length();
        }
    
        if (!rutLimpio.matches("\\d+[0-9K]?")) {
            return "El RUT debe contener solo números (y opcionalmente K al final). " +
            "Ejemplos válidos: 12345678, 12.345.678, 12345678-K";
        }
    
        return "Formato de RUT inválido.";
    }
    
    // ============ VALIDACIÓN DE TIPO DE SANGRE ============
    
    public static boolean esTipoSangreValido(String tipoSangre) {
        if (tipoSangre == null || tipoSangre.trim().isEmpty()) {
            return false;
        }
        
        String tipoNormalizado = tipoSangre.trim().toUpperCase();
        
        for (String tipo : TIPOS_SANGRE_VALIDOS) {
            if (tipo.equals(tipoNormalizado)) {
                return true;
            }
        }
        
        return false;
    }
    
    public static String[] obtenerTiposSangreValidos() {
        return TIPOS_SANGRE_VALIDOS.clone();
    }
    
    public static String obtenerMensajeErrorTipoSangre() {
        return "Tipo de sangre inválido. Tipos válidos: " + 
               String.join(", ", TIPOS_SANGRE_VALIDOS);
    }
    
    // ============ VALIDACIÓN DE SEXO ============
    /**
     * Valida que el sexo sea uno de los valores permitidos.
     * 
     * @param sexo el sexo a validar
     * @return true si es un sexo válido
     */
    public static boolean esSexoValido(String sexo) {
        if (sexo == null || sexo.trim().isEmpty()) {
            return false;
        }

        String sexoNormalizado = sexo.trim();

        for (String s : SEXOS_DISPONIBLES) {
            if (s.equals(sexoNormalizado)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Retorna los sexos válidos disponibles.
     * 
     * @return Array de sexos válidos
     */
    public static String[] obtenerSexosDisponibles() {
        return SEXOS_DISPONIBLES.clone();
    }

    /**
     * Retorna mensaje de error descriptivo para sexo inválido.
     * 
     * @return String con sexos válidos
     */
    public static String obtenerMensajeErrorSexo() {
        return "Sexo inválido. Sexos válidos: " + 
               String.join(", ", SEXOS_DISPONIBLES);
    }
    // ============ VALIDACIÓN DE TELÉFONO ============
    
    public static boolean esTelefonoValido(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return false;
        }
        
        String digitosSolo = telefono.replaceAll("[^0-9]", "");
        
        return digitosSolo.length() >= LONGITUD_TELEFONO_MINIMA && 
               digitosSolo.length() <= LONGITUD_TELEFONO_MAXIMA;
    }
    
    public static String obtenerMensajeErrorTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return "El teléfono no puede estar vacío";
        }
        
        String digitosSolo = telefono.replaceAll("[^0-9]", "");
        
        if (digitosSolo.length() < LONGITUD_TELEFONO_MINIMA) {
            return "El teléfono debe tener al menos " + LONGITUD_TELEFONO_MINIMA + 
                   " dígitos. Ingresaste: " + digitosSolo.length();
        }
        
        if (digitosSolo.length() > LONGITUD_TELEFONO_MAXIMA) {
            return "El teléfono no debe exceder " + LONGITUD_TELEFONO_MAXIMA + 
                   " dígitos. Ingresaste: " + digitosSolo.length();
        }
        
        return "Formato de teléfono inválido";
    }
    
    // ============ VALIDACIÓN DE NOMBRE ============
    
    public static boolean esNombreValido(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        
        return nombre.trim().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s\\-']+");
    }
    
    public static String obtenerMensajeErrorNombre() {
        return "El nombre contiene caracteres inválidos. " +
               "Solo se permiten letras, espacios, guiones y apóstrofes.";
    }
    
    // ============ VALIDACIÓN DE EDAD ============
    
    public static boolean esEdadValida(int edad) {
        return edad >= 18 && edad <= 65;
    }
    
    public static String obtenerMensajeErrorEdad(int edad) {
        return "La edad debe estar entre 18 y 65 años. " +
               "Ingresaste: " + edad + " años";
    }
}