package vista;

import entidades.Campaña;
import entidades.Donante;
import entidades.Extraccion;
import procesamiento.GestionHistorial;
import procesamiento.Validadores;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class VentanaPrincipal extends JFrame {
    private GestionHistorial sistema;

    public VentanaPrincipal(GestionHistorial sistema) {
        this.sistema = sistema;
        setTitle("SIA 2026 - Gestión de Donaciones de Sangre");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(15, 1, 5, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));


        JButton btnRegDonante = new JButton("Registrar Nuevo Donante");
        JButton btnRegCampana = new JButton("Crear Nueva Campaña");
        JButton btnRegExtraccion = new JButton("Registrar Donación (Extracción)");


        JButton btnBusDonante = new JButton("Buscar Donante (RUT)");
        JButton btnBusCampana = new JButton("Buscar Campaña (ID)");


        JButton btnEdiDonante = new JButton("Editar Datos de Donante");
        JButton btnEdiCampana = new JButton("Editar Datos de Campaña");



        JButton btnEliDonante = new JButton("Eliminar Donante (Y su historial)");
        JButton btnEliCampana = new JButton("Eliminar Campaña (Y su stock)");


        JButton btnListDonantes = new JButton("Listar Todos los Donantes");
        JButton btnListCampanas = new JButton("Listar Todas las Campañas");
        JButton btnVerInventario = new JButton("Ver Stock de Sangre");
        JButton btnSalir = new JButton("Salir y Guardar Todo");

        // --- LÓGICA DE LOS BOTONES ---

        // REGISTROS
        btnRegDonante.addActionListener(e -> {
            String nom = JOptionPane.showInputDialog("Nombre:");
            String rut = JOptionPane.showInputDialog("RUT:");
            int edad = Integer.parseInt(JOptionPane.showInputDialog("Edad:"));
            String tipo = JOptionPane.showInputDialog("Tipo de Sangre (Ej: O+):");
            String tel = JOptionPane.showInputDialog("Teléfono:");
            if(sistema.agregarDonante(new Donante(nom, rut, edad, "M", tipo, "01/01/2000", tel)))
                JOptionPane.showMessageDialog(this, "Donante registrado.");
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

        btnVerInventario.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("--- STOCK ACTUAL ---\n");
            sistema.getInv().getStockSangre().forEach((tipo, cant) ->
                    sb.append(tipo).append(": ").append(cant).append(" ml\n"));
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        btnSalir.addActionListener(e -> dispose());

        // AGREGAR BOTONES AL PANEL
        panelPrincipal.add(btnRegDonante); panelPrincipal.add(btnRegCampana); panelPrincipal.add(btnRegExtraccion);
        panelPrincipal.add(btnBusDonante); panelPrincipal.add(btnBusCampana);
        panelPrincipal.add(btnEdiDonante); panelPrincipal.add(btnEdiCampana);
        panelPrincipal.add(btnEliDonante); panelPrincipal.add(btnEliCampana);
        panelPrincipal.add(btnListDonantes); panelPrincipal.add(btnListCampanas);
        panelPrincipal.add(btnVerInventario); panelPrincipal.add(btnSalir);

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