package vista;

import exceptions.EdadNoValidaException;
import entidades.Campaña;
import entidades.Donante;
import entidades.Extraccion;
import entidades.InfoDonacion;
import procesamiento.Validadores;
import procesamiento.GestionHistorial;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private GestionHistorial sistema;

    public VentanaPrincipal(GestionHistorial sistema) {
        this.sistema = sistema;
        setTitle("Gestión de Donaciones de Sangre");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(0, 1, 5, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton btnCampanas = new JButton("Administrar Campañas");
        JButton btnDonantes = new JButton("Administrar Donantes");
        JButton btnExtracciones = new JButton("Administrar Extracciones");
        JButton btnInventario = new JButton("Administrar Inventario");
        JButton btnEmergencia = new JButton(" Generar Llamado de Emergencia");
        JButton btnSalir = new JButton("Salir y Guardar");
        
        JButton btnRegDonante = new JButton("Registrar Nuevo Donante");
        JButton btnRegCampana = new JButton("Crear Nueva Campaña");
        JButton btnRegExtraccion = new JButton("Registrar Donación (Extracción)");
        JButton btnAgregarStockManual = new JButton("Agregar Stock Manual");
        

        JButton btnBusDonante = new JButton("Buscar Donante (RUT)");
        JButton btnBusCampana = new JButton("Buscar Campaña (ID)");
        JButton btnHistExtracciones = new JButton("Mostrar Historial Extracciones");


        JButton btnEdiDonante = new JButton("Editar Datos de Donante");
        JButton btnEdiCampana = new JButton("Editar Datos de Campaña");
        JButton btnEdiExtraccion = new JButton("Editar Extraccion");


        JButton btnEliDonante = new JButton("Eliminar Donante (Y su historial)");
        JButton btnEliCampana = new JButton("Eliminar Campaña (Y su stock)");
        JButton btnEliExtraccion = new JButton("Eliminar Extraccion");
        JButton btnRestarStockManual = new JButton("Restar/Corregir Stock");

        JButton btnListDonantes = new JButton("Listar Todos los Donantes");
        JButton btnListCampanas = new JButton("Listar Todas las Campañas");
        JButton btnListExtracciones = new JButton("Listar Todos las Extracciones");
        
        // --- SUB MENUS ---
        btnCampanas.addActionListener(e -> {
            String[] opciones = {"Crear Nueva Campaña", "Buscar Campaña (ID)", "Editar Datos de Campaña", "Eliminar Campaña", "Listar Todas las Campañas"};

            int op = JOptionPane.showOptionDialog(
                this,
                "Gestión de Campañas",
                "Campañas",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]
            );

            if (op == -1) return;

            switch (op) {
                case 0: btnRegCampana.doClick(); break;
                case 1: btnBusCampana.doClick(); break;
                case 2: btnEdiCampana.doClick(); break;
                case 3: btnEliCampana.doClick(); break;
                case 4: btnListCampanas.doClick(); break;
            }
        });
        
        btnDonantes.addActionListener(e -> {
            String[] opciones = {"Registrar Nuevo Donante", "Buscar Donante (RUT)", "Editar Datos de Donante", "Eliminar Donante", "Listar Todos los Donantes"};

            int op = JOptionPane.showOptionDialog(
                this,
                "Gestión de Donantes",
                "Donantes",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]
            );

            if (op == -1) return;

            switch (op) {
                case 0: btnRegDonante.doClick(); break;
                case 1: btnBusDonante.doClick(); break;
                case 2: btnEdiDonante.doClick(); break;
                case 3: btnEliDonante.doClick(); break;
                case 4: btnListDonantes.doClick(); break;
            }
        });
        
        btnExtracciones.addActionListener(e -> {
            String[] opciones = {"Registrar Nueva Extraccion", "Listar Extraccion de una Campaña", "Mostrar Historial de Donante (RUT + ID)", "Editar Datos de Extraccion", "Eliminar Extraccion"};

            int op = JOptionPane.showOptionDialog(
                this,
                "Gestión de Extracciones",
                "Extracciones",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]
            );

            if (op == -1) return;

            switch (op) {
                case 0: btnRegExtraccion.doClick(); break;
                case 1: btnListExtracciones.doClick(); break;
                case 2: btnHistExtracciones.doClick(); break;
                case 3: btnEdiExtraccion.doClick(); break;
                case 4: btnEliExtraccion.doClick(); break;
            }
        });
        
        btnInventario.addActionListener(e -> {
            String[] opciones = {"Ver Estado General", "Agregar Stock (+)", "Corregir Stock (-)"};
            
            int seleccion = JOptionPane.showOptionDialog(
                    this,
                    "Seleccione la acción que desea realizar en el Banco de Sangre:",
                    "Gestión de Inventario",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, 
                    opciones, 
                    opciones[0]
            );

            if (seleccion == 0) {
                // OPCIÓN 1: Mostrar Inventario
                StringBuilder sb = new StringBuilder("--- INVENTARIO DE SANGRE ---\n\n");
                java.util.List<String> inventarioResumen = sistema.obtenerResumenInventario();
                
                if (inventarioResumen.isEmpty()) {
                    sb.append("No hay stock registrado en el sistema.\n");
                } else {
                    for (String resumen : inventarioResumen) {
                        sb.append(resumen).append("\n");
                    }
                }
                mostrarScroll(sb.toString(), "Estado del Inventario");
                
            } else if (seleccion == 1) {
                // OPCIÓN 2: Agregar Stock Manual
                String tipo = JOptionPane.showInputDialog(this, "Ingrese Tipo de Sangre (ej: O+, A-):", "Ingreso Manual", JOptionPane.QUESTION_MESSAGE);
                if (tipo != null && !tipo.trim().isEmpty()) {
                    String cantStr = JOptionPane.showInputDialog(this, "Volumen en ml a ingresar:");
                    if (cantStr != null) {
                        try {
                            int cantidad = Integer.parseInt(cantStr);
                            sistema.agregarStockManual(tipo.trim().toUpperCase(), cantidad);
                            JOptionPane.showMessageDialog(this, "Stock de " + tipo.toUpperCase() + " actualizado exitosamente.");
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Error: Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                
            } else if (seleccion == 2) {
                // OPCIÓN 3: Restar / Corregir Stock
                String tipo = JOptionPane.showInputDialog(this, "Ingrese Tipo de Sangre a corregir:", "Restar Stock", JOptionPane.WARNING_MESSAGE);
                if (tipo != null && !tipo.trim().isEmpty()) {
                    String cantStr = JOptionPane.showInputDialog(this, "Cantidad de ml a descontar:");
                    if (cantStr != null) {
                        try {
                            int cantidad = Integer.parseInt(cantStr);
                            boolean exito = sistema.restarStockManual(tipo.trim().toUpperCase(), cantidad);
                            if (exito) {
                                JOptionPane.showMessageDialog(this, "Stock corregido correctamente.");
                            } else {
                                JOptionPane.showMessageDialog(this, "Error: El tipo '" + tipo.toUpperCase() + "' no existe en el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Error: Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        } 
                    }
                }
            }
        });

        btnSalir.addActionListener(e -> {
            archivos.ArchivoUtil.guardarTodo(sistema);
            System.exit(0);
        });

        // REGISTROS
        btnRegDonante.addActionListener(e -> {
            try {
                String nom = JOptionPane.showInputDialog("Nombre:");
                if (nom == null || nom.trim().isEmpty()) return;
                
                String rut = JOptionPane.showInputDialog("RUT:");
                if (rut == null || rut.trim().isEmpty()) return;
                
                String edad = JOptionPane.showInputDialog("Edad:");
                if (edad == null || edad.trim().isEmpty()) return;
                int edadParseada = Integer.parseInt(edad);
                
                String sexo = JOptionPane.showInputDialog("Sexo (M, F u Otro):");
                if (sexo == null || sexo.trim().isEmpty()) return;
                
                String tipo = JOptionPane.showInputDialog(this, "Tipo de Sangre (Ej: O+):");
                if (tipo == null || tipo.trim().isEmpty()) return;

                String fecha = JOptionPane.showInputDialog(this, "Fecha última donación (DD/MM/AAAA):");
                if (fecha == null || fecha.trim().isEmpty()) return;
                
                String tel = JOptionPane.showInputDialog("Teléfono:");
                if (tel == null || tel.trim().isEmpty()) return;

                Donante d = new Donante(nom, rut, edadParseada, sexo, tipo, fecha, tel);
                d.validar();
                
                if (sistema.agregarDonante(d)) {
                    JOptionPane.showMessageDialog(this, "Donante registrado.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error: Ya existe un donante con este RUT.", "Error", JOptionPane.WARNING_MESSAGE);
                }

            } catch (exceptions.EdadNoValidaException ex) {
                JOptionPane.showMessageDialog(this, "Donación rechazada: " + ex.getMessage(), "Edad no permitida", JOptionPane.WARNING_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Debe ingresar un número válido para la edad.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (java.time.format.DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Error: La fecha debe cumplir el formato DD/MM/AAAA.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error de validación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRegCampana.addActionListener(e -> {
            try {
                String nom = JOptionPane.showInputDialog("Nombre Campaña:");
                if (nom == null || nom.trim().isEmpty()) return;
                String ubi = JOptionPane.showInputDialog("Ubicación:");
                if (ubi == null || ubi.trim().isEmpty()) return;
                String fec = JOptionPane.showInputDialog("Fecha (dd/mm/yyyy):");
                if (fec == null || fec.trim().isEmpty()) return;
                String meta = JOptionPane.showInputDialog("Meta (ml):");
                if (meta == null || meta.trim().isEmpty()) return;

                int metaParseada = Integer.parseInt(meta);

                Campaña nuevaCampaña = new Campaña(nom, ubi, fec, metaParseada);
                nuevaCampaña.validar();

                if (sistema.agregarCampaña(nuevaCampaña)) {
                    JOptionPane.showMessageDialog(this, "Campaña creada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error: Ya existe una campaña con este identificador.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error de formato: La meta debe ser un número entero (ej: 10000).", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (java.time.format.DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Error de formato: La fecha debe cumplir el formato exacto DD/MM/AAAA.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error de validación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        });

        btnRegExtraccion.addActionListener(e -> {
            // 1 y 2: Agregamos 'this' y los retornos tempranos si el usuario cancela
            String idC = JOptionPane.showInputDialog(this, "ID Campaña:");
            if (idC == null || idC.trim().isEmpty()) return;

            String rutD = JOptionPane.showInputDialog(this, "RUT Donante:");
            if (rutD == null || rutD.trim().isEmpty()) return;
            String rutFormateado = procesamiento.Validadores.formatearRut(rutD);

            entidades.Campaña c = sistema.buscarCampaña(idC);
            entidades.Donante d = sistema.buscarDonante(rutFormateado);

            if (c != null && d != null) {
                try {
                    d.esAptoParaDonar(c.getFechaCampaña());

                    String volStr = JOptionPane.showInputDialog(this, "Volumen (ml):");
                    if (volStr == null || volStr.trim().isEmpty()) return;

                    int vol = Integer.parseInt(volStr);

                    entidades.Extraccion nueva = new entidades.Extraccion(d, c.getFechaCampaña(), vol, false);
                    nueva.validar();

                    if (sistema.registrarExtraccion(idC, nueva)) {
                        JOptionPane.showMessageDialog(this, "Extracción registrada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Error: Este donante ya tiene una extracción en esta campaña.", "Error", JOptionPane.WARNING_MESSAGE);
                    }

                } catch (exceptions.FrecuenciaDonacionException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Donación Rechazada", JOptionPane.WARNING_MESSAGE);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Error: El volumen debe ser un número entero (ej: 450).", "Error de Formato", JOptionPane.ERROR_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error de validación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Campaña o Donante no existen en el sistema.", "No encontrado", JOptionPane.ERROR_MESSAGE);
            }
        }); 
        
        btnAgregarStockManual.addActionListener(e -> {
            String tipo = JOptionPane.showInputDialog(this, 
                "Ingrese Tipo de Sangre (ej: O+, A-):", 
                "Ingreso Manual",
                JOptionPane.QUESTION_MESSAGE);
            if (tipo == null || tipo.trim().isEmpty()) return;
            
            String cantStr = JOptionPane.showInputDialog(this, "Volumen en ml a ingresar:");
            if (cantStr == null || cantStr.trim().isEmpty()) return;
            try {
                int cantidad = Integer.parseInt(cantStr);
                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(this, "Error: El volumen debe ser mayor a 0 ml.", "Error Lógico", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                sistema.agregarStockManual(tipo.trim().toUpperCase(), cantidad);
                JOptionPane.showMessageDialog(this, "Stock actualizado exitosamente.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // BÚSQUEDAS
        btnBusDonante.addActionListener(e -> {
            String rut = JOptionPane.showInputDialog(this, "Ingrese RUT a buscar:", "Buscar Donante", JOptionPane.QUESTION_MESSAGE);

            if (rut == null || rut.trim().isEmpty()) return;
            String rutFormateado = procesamiento.Validadores.formatearRut(rut);

            entidades.Donante d = sistema.buscarDonante(rutFormateado);

            if (d != null) {
                String mensaje = "Datos del Donante:\n"
                               + "----------------------\n"
                               + "Nombre: " + d.getNombre() + "\n"
                               + "Tipo de Sangre: " + d.getTipoSangre() + "\n"
                               + "Teléfono: " + d.getTelefono();

                JOptionPane.showMessageDialog(this, mensaje, "Donante Encontrado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error: No existe un donante registrado con ese RUT.", "No encontrado", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnBusCampana.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "Ingrese ID de Campaña a buscar:", "Buscar Campaña", JOptionPane.QUESTION_MESSAGE);

            if (id == null || id.trim().isEmpty()) return;

            entidades.Campaña c = sistema.buscarCampaña(id.trim());

            if (c != null) {
                String fechaStr = c.getFechaCampaña().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                String mensaje = "Datos de la Campaña:\n"
                               + "----------------------\n"
                               + "Nombre: " + c.getNombreCampaña() + "\n"
                               + "Ubicación: " + c.getUbicacion() + "\n"
                               + "Fecha: " + fechaStr + "\n"
                               + "Meta: " + c.getMetaDonaciones() + " ml";

                JOptionPane.showMessageDialog(this, mensaje, "Campaña Encontrada", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error: No existe una campaña registrada con ese ID.", "No encontrada", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnHistExtracciones.addActionListener(e -> {
            String rut = JOptionPane.showInputDialog(this, "Ingrese RUT del donante:", "Historial de Donaciones", JOptionPane.QUESTION_MESSAGE);

            if (rut == null || rut.trim().isEmpty()) return;
            String rutF = procesamiento.Validadores.formatearRut(rut);
            
            if (sistema.buscarDonante(rutF.trim()) == null) {
                JOptionPane.showMessageDialog(this, "Error: El RUT ingresado no está registrado en el sistema.", "No encontrado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String[] opciones = {"Historial General", "Donación Específica"};
            int op = JOptionPane.showOptionDialog(
                this,
                "¿Qué deseas consultar?",
                "Tipo de Historial",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
            );

            if (op == -1) return; 

            if (op == 0) {
                java.util.List<entidades.InfoDonacion> lista = sistema.buscarExtraccion(rutF.trim());

                if (lista.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El donante registrado aún no tiene donaciones en su historial.", "Sin historial", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    StringBuilder sb = new StringBuilder("=== HISTORIAL DE DONACIONES ===\n\n");

                    for (entidades.InfoDonacion info : lista) {
                        sb.append("• Campaña: ").append(info.getIdCampaña())
                          .append(" | Vol: ").append(info.getVolumenExtraido()).append(" ml")
                          .append(" | Malestar: ").append(info.getSeSintioMal() ? "SÍ" : "NO")
                          .append("\n");
                    }

                    mostrarScroll(sb.toString(), "Historial General");
                }
            } else {
                String id = JOptionPane.showInputDialog(this, "Ingrese ID de la Campaña:", "Donación Específica", JOptionPane.QUESTION_MESSAGE);

                if (id == null || id.trim().isEmpty()) return;

                entidades.Extraccion ex = sistema.buscarExtraccion(rutF.trim(), id.trim());

                if (ex != null) {
                    String fechaStr = ex.getFechaExtraccion().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    String msg = "Detalle de la Donación:\n" +
                                 "-----------------------\n" +
                                 "Fecha: " + fechaStr + "\n" +
                                 "Volumen: " + ex.getVolumenExtraido() + " ml\n" +
                                 "Malestar: " + (ex.getSeSintioMal() ? "SÍ" : "NO");

                    JOptionPane.showMessageDialog(this, msg, "Detalle Encontrado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error: No se encontró una extracción para este donante en la campaña indicada.", "No encontrada", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // EDICIONES
        btnEdiDonante.addActionListener(e -> {
            String rut = JOptionPane.showInputDialog(this, "RUT del donante a editar:", "Editar Donante", JOptionPane.QUESTION_MESSAGE);
             
            if (rut == null || rut.trim().isEmpty()) return;
            String rutF = procesamiento.Validadores.formatearRut(rut);
            
            entidades.Donante d = sistema.buscarDonante(rutF.trim());

            if (d != null) {
                try {
                    String nNom = JOptionPane.showInputDialog(this, "Nuevo Nombre:", d.getNombre());
                    if (nNom == null) return; 

                    String nTel = JOptionPane.showInputDialog(this, "Nuevo Teléfono:", d.getTelefono());
                    if (nTel == null) return;

                    sistema.actualizarDonante(rutF.trim(), nNom.trim(), d.getEdad(), d.getSexo(), d.getTipoSangre(), nTel.trim());
                    JOptionPane.showMessageDialog(this, "Datos del donante actualizados exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error de validación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error: No se encontró un donante con ese RUT.", "No encontrado", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnEdiCampana.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "ID de campaña a editar:", "Editar Campaña", JOptionPane.QUESTION_MESSAGE);

            if (id == null || id.trim().isEmpty()) return;

            entidades.Campaña c = sistema.buscarCampaña(id.trim());

            if (c != null) {
                try {
                    String nNom = JOptionPane.showInputDialog(this, "Nuevo Nombre:", c.getNombreCampaña());
                    if (nNom == null) return;

                    String fechaActual = c.getFechaCampaña().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    String nFec = JOptionPane.showInputDialog(this, "Nueva Fecha (DD/MM/AAAA):", fechaActual);
                    if (nFec == null) return;

                    sistema.actualizarCampaña(id.trim(), nNom.trim(), c.getUbicacion(), nFec.trim(), c.getMetaDonaciones());
                    JOptionPane.showMessageDialog(this, "Campaña actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                } catch (java.time.format.DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(this, "Error de formato: La fecha debe cumplir el formato DD/MM/AAAA.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error de validación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error: No se encontró una campaña con ese ID.", "No encontrada", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnEdiExtraccion.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "ID Campaña:", "Editar Extracción", JOptionPane.QUESTION_MESSAGE);
            if (id == null || id.trim().isEmpty()) return;
            
            String rut = JOptionPane.showInputDialog(this, "RUT Donante:", "Editar Extracción", JOptionPane.QUESTION_MESSAGE);
            if (rut == null || rut.trim().isEmpty()) return;
            String rutFormateado = procesamiento.Validadores.formatearRut(rut);
            
            entidades.Extraccion ex = sistema.buscarExtraccion(rutFormateado.trim(), id.trim());

            if (ex != null) {
                try {
                    String rutN = JOptionPane.showInputDialog(this, "Nuevo RUT:", ex.getVoluntario().getRut());
                    if (rutN == null) return;
                    if (rutN.trim().isEmpty()) rutN = ex.getVoluntario().getRut();
                    String rutNFormateado = procesamiento.Validadores.formatearRut(rutN);

                    String fechaActual = ex.getFechaExtraccion().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    String fecha = JOptionPane.showInputDialog(this, "Nueva Fecha (DD/MM/AAAA):", fechaActual);
                    if (fecha == null) return; 
                    if (fecha.trim().isEmpty()) fecha = fechaActual;

                    String volStr = JOptionPane.showInputDialog(this, "Nuevo Volumen (ml):", ex.getVolumenExtraido());
                    if (volStr == null) return; 
                    int vol = volStr.trim().isEmpty() ? ex.getVolumenExtraido() : Integer.parseInt(volStr.trim());

                    int malOp = JOptionPane.showConfirmDialog(this, "¿Se sintió mal el donante?", "Estado del Donante", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (malOp == JOptionPane.CANCEL_OPTION || malOp == JOptionPane.CLOSED_OPTION) return; // Si cancela o cierra en la X
                    boolean mal = (malOp == JOptionPane.YES_OPTION);

                    if (sistema.actualizarExtraccion(rutFormateado.trim(), id.trim(), rutNFormateado.trim(), fecha.trim(), vol, mal)) {
                        JOptionPane.showMessageDialog(this, "Extracción actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al actualizar. Revise los datos o verifique que el nuevo RUT exista.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException excep) {
                    JOptionPane.showMessageDialog(this, "Error de formato: El volumen debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception excep) {
                    // Captura errores de formato de fecha o validaciones internas
                    JOptionPane.showMessageDialog(this, "Error de validación: " + excep.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Error: Extracción no encontrada para este donante en esta campaña.", "No encontrada", JOptionPane.WARNING_MESSAGE);
            }
        });

        // ELIMINACIONES
        btnEliDonante.addActionListener(e -> {
            String rut = JOptionPane.showInputDialog(this, "RUT del donante a eliminar:", "Eliminar Donante", JOptionPane.QUESTION_MESSAGE);
            if (rut == null || rut.trim().isEmpty()) return;
            String rutFormateado = procesamiento.Validadores.formatearRut(rut);
            
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro que desea eliminar a este donante y todo su historial de extracciones?\nEsta acción no se puede deshacer.", 
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);

            if (confirmacion != JOptionPane.YES_OPTION) return;

            if (sistema.eliminarDonante(rutFormateado.trim())) {
                JOptionPane.showMessageDialog(this, "Donante e historial eliminados correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error: RUT no encontrado en el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliCampana.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "ID de campaña a eliminar:", "Eliminar Campaña", JOptionPane.QUESTION_MESSAGE);
            if (id == null || id.trim().isEmpty()) return;

            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro que desea eliminar esta campaña?\nEsto revertirá todo el stock de sangre asociado a la misma.", 
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);

            if (confirmacion != JOptionPane.YES_OPTION) return;

            if (sistema.eliminarCampaña(id.trim())) {
                JOptionPane.showMessageDialog(this, "Campaña y stock revertidos correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error: ID de campaña no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliExtraccion.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "ID de la Campaña:", "Eliminar Extracción", JOptionPane.QUESTION_MESSAGE);
            if (id == null || id.trim().isEmpty()) return; 

            String rut = JOptionPane.showInputDialog(this, "RUT del Donante:", "Eliminar Extracción", JOptionPane.QUESTION_MESSAGE);
            if (rut == null || rut.trim().isEmpty()) return; 
            String rutFormateado = procesamiento.Validadores.formatearRut(rut);
            
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro que desea eliminar esta extracción específica?", 
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);

            if (confirmacion != JOptionPane.YES_OPTION) return;

            if (sistema.borrarExtraccion(id.trim(), rutFormateado.trim())) {
                JOptionPane.showMessageDialog(this, "Extracción eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error: No se encontró la extracción especificada.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnRestarStockManual.addActionListener(e -> {
            String tipo = JOptionPane.showInputDialog(this, 
                "Ingrese Tipo de Sangre a corregir (ej: O+, A-):", 
                "Restar Stock Manual", 
                JOptionPane.WARNING_MESSAGE);

            if (tipo == null || tipo.trim().isEmpty()) return;
            String tipoSangre = tipo.trim().toUpperCase();


            String cantStr = JOptionPane.showInputDialog(this, "Cantidad de ml a descontar:");
            if (cantStr == null || cantStr.trim().isEmpty()) return;

            try {
                int cantidad = Integer.parseInt(cantStr.trim());

                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(this, "Error: La cantidad a descontar debe ser mayor a 0 ml.", "Error Lógico", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean exito = sistema.restarStockManual(tipoSangre, cantidad);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Stock corregido correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error: No hay suficiente stock de '" + tipoSangre + "' para descontar esa cantidad, o el tipo no tiene registros.", "Operación Denegada", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Ingrese un número entero válido (ej: 450).", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // LISTADOS
        btnListDonantes.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("--- DONANTES REGISTRADOS ---\n");
            
            java.util.List<String> resumenesDonantes = sistema.obtenerListaVoluntarios();
            if (resumenesDonantes.isEmpty()) {
                sb.append("No hay donantes registrados en el sistema.\n");
            } else {
                for (String resumen : resumenesDonantes) {
                    sb.append(resumen).append("\n");
                }
            } 
            
            mostrarScroll(sb.toString(), "Listado de Donantes");
        });

        btnListCampanas.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("--- CAMPAÑAS REGISTRADAS ---\n");
            
            java.util.List<String> resumenesCampañas = sistema.obtenerListaCampañas();
            if (resumenesCampañas.isEmpty()) {
                sb.append("No hay campañas registradas en el sistema.\n");
            } else {
                for (String resumen : resumenesCampañas) {
                    sb.append(resumen).append("\n");
                }
            }
            
            mostrarScroll(sb.toString(), "Listado de Campañas");
        });
        
        btnListExtracciones.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "Ingrese ID de la Campaña:", "Listar Extracciones", JOptionPane.QUESTION_MESSAGE);

            if (id == null || id.trim().isEmpty()) return;

            java.util.List<entidades.Extraccion> lista = sistema.getListaExtracciones(id.trim());

            if (lista != null && !lista.isEmpty()) {
                StringBuilder sb = new StringBuilder("--- DONACIONES EN: " + id.trim() + " ---\n\n");

                for (entidades.Extraccion ex : lista) {
                    sb.append("• RUT: ").append(ex.getVoluntario().getRut())
                      .append(" | Donante: ").append(ex.getVoluntario().getNombre())
                      .append(" | Volumen: ").append(ex.getVolumenExtraido()).append(" ml\n");
                }

                mostrarScroll(sb.toString(), "Extracciones de la Campaña");
            } else {
                JOptionPane.showMessageDialog(this, "Error: No hay extracciones registradas o el ID de la campaña es inválido.", "Sin resultados", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnSalir.addActionListener(e -> {
            System.out.println("Guardando cambios en archivos...");
            archivos.ArchivoUtil.guardarTodo(sistema);
            System.exit(0);
        });
        
        btnEmergencia.addActionListener(e -> ejecutarLlamadoEmergencia());

        // AGREGAR BOTONES AL PANEL
        panelPrincipal.add(btnCampanas);
        panelPrincipal.add(btnDonantes);
        panelPrincipal.add(btnExtracciones);
        panelPrincipal.add(btnInventario);
        panelPrincipal.add(btnEmergencia);
        panelPrincipal.add(btnSalir);

        add(new JScrollPane(panelPrincipal));
    
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("Cerrando ventana: Guardando cambios en archivos...");
                archivos.ArchivoUtil.guardarTodo(sistema); 
                System.exit(0);
            }
        });
    }

    private void mostrarScroll(String texto, String titulo) {
        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(this, scroll, titulo, JOptionPane.PLAIN_MESSAGE);
    }

    private void ejecutarLlamadoEmergencia() {
        String tipo = JOptionPane.showInputDialog(this, "Ingrese el tipo de sangre requerido:");
        if (tipo == null || tipo.trim().isEmpty()) return;

        tipo = tipo.toUpperCase().trim();
        java.util.List<entidades.Donante> aptos = sistema.filtroTipoAntiguedad(tipo);

        if (aptos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay donantes aptos para " + tipo);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
                "Se encontraron " + aptos.size() + " donantes. ¿Exportar lista?",
                "Emergencia", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            if (sistema.exportarListaAptos(aptos, "emergencia_" + tipo + ".txt")) {
                JOptionPane.showMessageDialog(this, "Archivo generado.");
                ofrecerCrearCampanaEnfocada(tipo);
            }
        }
    }

    private void ofrecerCrearCampanaEnfocada(String tipo) {
        int r = JOptionPane.showConfirmDialog(this, "¿Desea crear una Campaña Enfocada?", "Sugerencia", JOptionPane.YES_NO_OPTION);
        if (r != JOptionPane.YES_OPTION) return;

        String nombreCampaña = "(" + tipo + ")Emergencia";
        String sede = "SinSede";
        JTextField meta = new JTextField("10000");
        JTextField fecha = new JTextField("DD/MM/YYYY");
        JTextField porcentaje = new JTextField("50");
        
        Object[] mensaje = {
            "Configuración de Campaña de Emergencia",
            "Nombre por defecto: " + nombreCampaña,
            "-----------------------------",
            "Meta (en ml):", meta, 
            "Ingrese la fecha de la campaña (dd/mm/yyyy):", fecha,
            "Ingrese el porcentaje objetivo del grupo (0 a 100):", porcentaje
        };
                
        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Nueva Campaña", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                // Convertimos el porcentaje igual que en consola
                float porcentajeMeta = Float.parseFloat(porcentaje.getText().trim()) / 100f;
                
                // Convetir la meta a un int
                String textoExtraido = meta.getText().trim();
                int metaValor = Integer.parseInt(textoExtraido);
                
                // Llamamos al sistema
                entidades.CampañaEnfocada nueva = sistema.crearCampañaEnfocada(
                        nombreCampaña, 
                        sede, 
                        fecha.getText().trim(), 
                        metaValor,
                        tipo, 
                        porcentajeMeta
                );
                
                // Mismos mensajes de éxito/error que en consola
                if (nueva != null) {
                    JOptionPane.showMessageDialog(this, "Exito: Se ha creado la campaña con ID:\n" + nueva.getIdCampaña());
                } else {
                    JOptionPane.showMessageDialog(this, "Error: Ya existe una campaña con ese ID automatico.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: El porcentaje debe ser numérico.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

    
