package vista;

import exceptions.EdadNoValidaException;
import entidades.Campaña;
import entidades.Donante;
import entidades.Extraccion;
import entidades.InfoDonacion;
import procesamiento.GestionHistorial;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private GestionHistorial sistema;

    public VentanaPrincipal(GestionHistorial sistema) {
        this.sistema = sistema;
        setTitle("SIA 2026 - Gestión de Donaciones de Sangre");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(5, 1, 5, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton btnCampanas = new JButton("Administrar Campañas");
        JButton btnDonantes = new JButton("Administrar Donantes");
        JButton btnExtracciones = new JButton("Administrar Extracciones");
        JButton btnInventario = new JButton("Ver Inventario General");
        JButton btnSalir = new JButton("Salir y Guardar");
        
        JButton btnRegDonante = new JButton("Registrar Nuevo Donante");
        JButton btnRegCampana = new JButton("Crear Nueva Campaña");
        JButton btnRegExtraccion = new JButton("Registrar Donación (Extracción)");


        JButton btnBusDonante = new JButton("Buscar Donante (RUT)");
        JButton btnBusCampana = new JButton("Buscar Campaña (ID)");
        JButton btnHistExtracciones = new JButton("Mostrar Historial Extracciones");


        JButton btnEdiDonante = new JButton("Editar Datos de Donante");
        JButton btnEdiCampana = new JButton("Editar Datos de Campaña");
        JButton btnEdiExtraccion = new JButton("Editar Extraccion");


        JButton btnEliDonante = new JButton("Eliminar Donante (Y su historial)");
        JButton btnEliCampana = new JButton("Eliminar Campaña (Y su stock)");
        JButton btnEliExtraccion = new JButton("Eliminar Extraccion");


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
            StringBuilder sb = new StringBuilder("--- INVENTARIO ---\n");
            sistema.getInv().getStockSangre().forEach((tipo, cant) ->
                    sb.append(tipo).append(": ").append(cant).append(" ml\n"));
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        // REGISTROS
        btnRegDonante.addActionListener(e -> {
            try {
                String nom = JOptionPane.showInputDialog("Nombre:");
                String rut = JOptionPane.showInputDialog("RUT:");

                int edad = Integer.parseInt(JOptionPane.showInputDialog("Edad:"));
                
                if (edad < 18 || edad > 65) throw new EdadNoValidaException(edad);

                String tipo = JOptionPane.showInputDialog("Tipo de Sangre (Ej: O+):");
                String tel = JOptionPane.showInputDialog("Teléfono:");

                Donante d = new Donante(nom, rut, edad, "M", tipo, "01/01/2000", tel);

                if (sistema.agregarDonante(d)) {
                    JOptionPane.showMessageDialog(this, "Donante registrado.");
                }

            } catch (EdadNoValidaException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Debe ingresar una edad válida.");
            }
        });

        btnRegCampana.addActionListener(e -> {
            String nom = JOptionPane.showInputDialog("Nombre Campaña:");
            String ubi = JOptionPane.showInputDialog("Ubicación:");
            String fec = JOptionPane.showInputDialog("Fecha (dd/mm/yyyy):");
            int meta = Integer.parseInt(JOptionPane.showInputDialog("Meta (ml):"));
            if(sistema.agregarCampaña(new Campaña(nom, ubi, fec, meta)))
                JOptionPane.showMessageDialog(this, "Campaña creada.");
        });

        btnRegExtraccion.addActionListener(e -> {
            String idC = JOptionPane.showInputDialog("ID Campaña:");
            String rutD = JOptionPane.showInputDialog("RUT Donante:");
            Campaña c = sistema.buscarCampaña(idC);
            Donante d = sistema.buscarDonante(rutD);
            if (c != null && d != null) {
                int vol = Integer.parseInt(JOptionPane.showInputDialog("Volumen (ml):"));
                sistema.registrarExtraccion(idC, new Extraccion(d, c.getFechaCampaña(), vol, false));
                JOptionPane.showMessageDialog(this, "Extracción registrada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Campaña o Donante no existen.");
            }
        });

        // BÚSQUEDAS
        btnBusDonante.addActionListener(e -> {
            String rut = JOptionPane.showInputDialog("Ingrese RUT a buscar:");
            Donante d = sistema.buscarDonante(rut);
            if (d != null) JOptionPane.showMessageDialog(this, "Nombre: " + d.getNombre() + "\nTipo: " + d.getTipoSangre() + "\nTel: " + d.getTelefono());
            else JOptionPane.showMessageDialog(this, "No encontrado.");
        });

        btnBusCampana.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Ingrese ID de Campaña:");
            Campaña c = sistema.buscarCampaña(id);
            if (c != null) JOptionPane.showMessageDialog(this, "Nombre: " + c.getNombreCampaña() + "\nMeta: " + c.getMetaDonaciones() + " ml");
            else JOptionPane.showMessageDialog(this, "No encontrada.");
        });
        
        btnHistExtracciones.addActionListener(e -> {
            String rut = JOptionPane.showInputDialog(this, "Ingrese RUT del donante:");

            if (sistema.buscarDonante(rut) == null) {
                JOptionPane.showMessageDialog(this, "RUT no registrado.");
                return;
            }

            String[] opciones = {"Historial General", "Donación Específica"};

            int op = JOptionPane.showOptionDialog(
                this,
                "¿Qué deseas consultar?",
                "Historial",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]
            );

            if (op == -1) return;

            if (op == 0) {
                java.util.List<InfoDonacion> lista = sistema.buscarExtraccion(rut);

                if (lista.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No tiene donaciones.");
                } else {
                    StringBuilder sb = new StringBuilder("=== HISTORIAL ===\n");

                    for (InfoDonacion info : lista) {
                        sb.append("Campaña: ").append(info.getIdCampaña())
                          .append(" | Vol: ").append(info.getVolumenExtraido())
                          .append(" | Malestar: ").append(info.getSeSintioMal() ? "SI" : "NO")
                          .append("\n");
                    }

                    mostrarScroll(sb.toString(), "Historial");
                }
            } else {
                String id = JOptionPane.showInputDialog(this, "ID Campaña:");
                Extraccion ex = sistema.buscarExtraccion(rut, id);

                if (ex != null) {
                    String msg = "Fecha: " + ex.getFechaExtraccion() +
                                 "\nVolumen: " + ex.getVolumenExtraido() +
                                 "\nMalestar: " + (ex.getSeSintioMal() ? "SI" : "NO");

                    JOptionPane.showMessageDialog(this, msg);
                } else {
                    JOptionPane.showMessageDialog(this, "No encontrada.");
                }
            }
        });

        // EDICIONES
        btnEdiDonante.addActionListener(e -> {
            String rut = JOptionPane.showInputDialog("RUT del donante a editar:");
            Donante d = sistema.buscarDonante(rut);
            if (d != null) {
                String nNom = JOptionPane.showInputDialog("Nuevo Nombre:", d.getNombre());
                String nTel = JOptionPane.showInputDialog("Nuevo Teléfono:", d.getTelefono());
                sistema.actualizarDonante(rut, nNom, d.getEdad(), d.getSexo(), d.getTipoSangre(), nTel);
                JOptionPane.showMessageDialog(this, "Datos actualizados.");
            }
        });

        btnEdiCampana.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("ID de campaña a editar:");
            Campaña c = sistema.buscarCampaña(id);
            if (c != null) {
                String nNom = JOptionPane.showInputDialog("Nuevo Nombre:", c.getNombreCampaña());
                String nFec = JOptionPane.showInputDialog("Nueva Fecha (dd/mm/yyyy):", "10/10/2026");
                sistema.actualizarCampaña(id, nNom, c.getUbicacion(), nFec, c.getMetaDonaciones());
                JOptionPane.showMessageDialog(this, "Campaña actualizada.");
            }
        });
        
        btnEdiExtraccion.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "ID Campaña:");
            String rut = JOptionPane.showInputDialog(this, "RUT Donante:");

            Extraccion ex = sistema.buscarExtraccion(rut, id);

            if (ex != null) {

                String rutN = JOptionPane.showInputDialog(this, "Nuevo RUT:", ex.getVoluntario().getRut());
                if (rutN.isEmpty()) rutN = ex.getVoluntario().getRut();

                String fecha = JOptionPane.showInputDialog(this, "Nueva Fecha:", ex.getFechaExtraccion());
                if (fecha.isEmpty()) fecha = ex.getFechaExtraccion().toString();

                String volStr = JOptionPane.showInputDialog(this, "Nuevo Volumen:", ex.getVolumenExtraido());
                int vol = volStr.isEmpty() ? ex.getVolumenExtraido() : Integer.parseInt(volStr);

                int malOp = JOptionPane.showConfirmDialog(this, "¿Se sintió mal?");
                boolean mal = (malOp == JOptionPane.YES_OPTION);

                if (sistema.actualizarExtraccion(rut, id, rutN, fecha, vol, mal)) {
                    JOptionPane.showMessageDialog(this, "Actualizada correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar.");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Extracción no encontrada.");
            }
        });

        // ELIMINACIONES
        btnEliDonante.addActionListener(e -> {
            String rut = JOptionPane.showInputDialog("RUT del donante a eliminar:");
            if (sistema.eliminarDonante(rut)) JOptionPane.showMessageDialog(this, "Donante e historial eliminados.");
            else JOptionPane.showMessageDialog(this, "Error: RUT no encontrado.");
        });

        btnEliCampana.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("ID de campaña a eliminar:");
            if (sistema.eliminarCampaña(id)) JOptionPane.showMessageDialog(this, "Campaña y stock revertidos.");
            else JOptionPane.showMessageDialog(this, "Error: ID no encontrado.");
        });
        
        btnEliExtraccion.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "ID Campaña:");
            String rut = JOptionPane.showInputDialog(this, "RUT Donante:");

            if (sistema.borrarExtraccion(id, rut)) {
                JOptionPane.showMessageDialog(this, "Eliminada correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la extracción.");
            }
        });

        // LISTADOS
        btnListDonantes.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("--- DONANTES REGISTRADOS ---\n");
            sistema.getVoluntarios().values().forEach(d ->
                    sb.append(d.getRut()).append(" | ").append(d.getNombre()).append("\n"));
            mostrarScroll(sb.toString(), "Listado de Donantes");
        });

        btnListCampanas.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("--- CAMPAÑAS REGISTRADAS ---\n");
            sistema.getCampañas().forEach(c ->
                    sb.append(c.getIdCampaña()).append(" | ").append(c.getNombreCampaña()).append("\n"));
            mostrarScroll(sb.toString(), "Listado de Campañas");
        });
        
        btnListExtracciones.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "Ingrese ID de la Campaña:");
            java.util.List<Extraccion> lista = sistema.getListaExtracciones(id);

            if (lista != null && !lista.isEmpty()) {
                StringBuilder sb = new StringBuilder("--- DONACIONES EN: " + id + " ---\n");

                for (Extraccion ex : lista) {
                    sb.append("RUT: ").append(ex.getVoluntario().getRut())
                      .append(" | Donante: ").append(ex.getVoluntario().getNombre())
                      .append(" | Volumen: ").append(ex.getVolumenExtraido()).append(" ml\n");
                }

                mostrarScroll(sb.toString(), "Extracciones");
            } else {
                JOptionPane.showMessageDialog(this, "No hay extracciones o ID inválido.");
            }
        });

        btnSalir.addActionListener(e -> dispose());

        // AGREGAR BOTONES AL PANEL
        panelPrincipal.add(btnCampanas);
        panelPrincipal.add(btnDonantes);
        panelPrincipal.add(btnExtracciones);
        panelPrincipal.add(btnInventario);
        panelPrincipal.add(btnSalir);

        add(new JScrollPane(panelPrincipal));
    }

    private void mostrarScroll(String texto, String titulo) {
        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(this, scroll, titulo, JOptionPane.PLAIN_MESSAGE);
    }
}