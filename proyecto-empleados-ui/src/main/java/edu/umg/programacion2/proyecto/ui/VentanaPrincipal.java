package edu.umg.programacion2.proyecto.ui;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.table.JTableHeader;
import java.math.RoundingMode;
import java.util.List;

import edu.umg.programacion2.proyecto.dao.EmpleadoDAO;
import edu.umg.programacion2.proyecto.modelo.Empleado;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtNombre;
    private JTextField txtDepartamento;
    private JTextField txtSalario;
    private JTextField txtFecha;
    private JCheckBox chkActivo;

    private JButton btnRegistrar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnVerTotales;

    private JTable tablaEmpleados;
    private DefaultTableModel modeloTabla;
    private EmpleadoDAO empleadoDAO;
    private JLabel lblTotalEmpleados;
    private JComboBox<String> cmbTipoContrato;
    
    

    public VentanaPrincipal() {

        empleadoDAO = new EmpleadoDAO();

        setTitle("Gestión de Empleados");
        setSize(1150, 650);
        setMinimumSize(new Dimension(1000, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        crearEncabezado();
        crearContenidoPrincipal();
        aplicarEstilos();

        configurarEventos();
        cargarEmpleados();
    }
    private void crearEncabezado() {

        JPanel panelEncabezado = new JPanel(new BorderLayout());

        panelEncabezado.setBorder(
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        JLabel titulo = new JLabel("Gestión de Empleados");

        titulo.setFont(
                new Font("SansSerif", Font.BOLD, 28)
        );

        JLabel subtitulo = new JLabel(
                "Registra, consulta y administra la información del personal"
        );

        subtitulo.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        JPanel textos = new JPanel();

        textos.setLayout(
                new BoxLayout(textos, BoxLayout.Y_AXIS)
        );

        textos.add(titulo);
        textos.add(Box.createVerticalStrut(5));
        textos.add(subtitulo);

        panelEncabezado.add(textos, BorderLayout.WEST);

        add(panelEncabezado, BorderLayout.NORTH);
    }
    private void crearContenidoPrincipal() {

        JPanel panelPrincipal = new JPanel(
                new BorderLayout(15, 15)
        );

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(0, 25, 25, 25)
        );

        JPanel panelIzquierdo = crearPanelFormulario();

        JPanel panelDerecho = crearPanelTabla();

        panelPrincipal.add(
                panelIzquierdo,
                BorderLayout.WEST
        );

        panelPrincipal.add(
                panelDerecho,
                BorderLayout.CENTER
        );

        add(panelPrincipal, BorderLayout.CENTER);
    }

    private JPanel crearPanelFormulario() {

        JPanel panel = new JPanel(
                new GridBagLayout()
        );
        panel.setBackground(Color.WHITE);

        panel.setPreferredSize(
                new Dimension(360, 0)
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Datos del Empleado"),
                        BorderFactory.createEmptyBorder(12, 8, 8, 8)
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 8, 3, 8);

        int fila = 0;

        JLabel lblNombre =
                new JLabel("Nombre completo:");

        gbc.gridy = fila++;
        panel.add(lblNombre, gbc);

        txtNombre = new JTextField();

        gbc.gridy = fila++;
        panel.add(txtNombre, gbc);

        JLabel lblDepartamento =
                new JLabel("Departamento:");

        gbc.gridy = fila++;
        panel.add(lblDepartamento, gbc);

        txtDepartamento = new JTextField();

        gbc.gridy = fila++;
        panel.add(txtDepartamento, gbc);

        JLabel lblSalario =
                new JLabel("Salario mensual:");

        gbc.gridy = fila++;
        panel.add(lblSalario, gbc);

        txtSalario = new JTextField();

        gbc.gridy = fila++;
        panel.add(txtSalario, gbc);

        JLabel lblFecha =
                new JLabel(
                        "Fecha de contratación:"
                );

        gbc.gridy = fila++;
        panel.add(lblFecha, gbc);

        txtFecha = new JTextField();

        gbc.gridy = fila++;
        panel.add(txtFecha, gbc);

        JLabel ayudaFecha =
                new JLabel("Formato: AAAA-MM-DD");

        ayudaFecha.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        11
                )
        );
        JLabel lblTipoContrato =
                new JLabel("Tipo de contrato:");

        gbc.gridy = fila++;
        panel.add(lblTipoContrato, gbc);

        cmbTipoContrato =
                new JComboBox<>(TIPOS_CONTRATO);

        gbc.gridy = fila++;
        panel.add(cmbTipoContrato, gbc);

        gbc.gridy = fila++;
        panel.add(ayudaFecha, gbc);

        chkActivo =
                new JCheckBox("Empleado activo");

        chkActivo.setSelected(true);

        gbc.gridy = fila++;
        panel.add(chkActivo, gbc);

        gbc.insets =
                new Insets(10, 8, 4, 8);

        btnRegistrar =
                new JButton("Registrar");

        gbc.gridy = fila++;
        panel.add(btnRegistrar, gbc);

        gbc.insets =
                new Insets(4, 8, 4, 8);

        btnActualizar =
                new JButton("Actualizar");

        gbc.gridy = fila++;
        panel.add(btnActualizar, gbc);

        btnEliminar =
                new JButton("Eliminar");

        gbc.gridy = fila++;
        panel.add(btnEliminar, gbc);

        btnLimpiar =
                new JButton("Limpiar");

        gbc.gridy = fila++;
        panel.add(btnLimpiar, gbc);

        gbc.gridy = fila;
        gbc.weighty = 1;
        panel.add(
                Box.createVerticalGlue(),
                gbc
        );
        btnVerTotales = new JButton("Ver totales");

        gbc.gridy = fila++;
        panel.add(btnVerTotales, gbc);

        return panel;
    }
    private JPanel crearPanelTabla() {

        JPanel panel =
                new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createTitledBorder(
                        "Lista de Empleados"
                )
        );

        String[] columnas = {
                "ID",
                "Nombre",
                "Departamento",
                "Salario",
                "Fecha contratación",
                "Tipo contrato",
                "Activo"
        };

        modeloTabla =
                new DefaultTableModel(
                        columnas,
                        0
                ) {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {

                return false;
            }
        };

        tablaEmpleados =
                new JTable(modeloTabla);

        tablaEmpleados.setRowHeight(28);

        tablaEmpleados.getTableHeader()
                .setReorderingAllowed(false);

        tablaEmpleados.setSelectionMode(
                javax.swing.ListSelectionModel
                        .SINGLE_SELECTION
        );

        JScrollPane scroll =
                new JScrollPane(tablaEmpleados);

        panel.add(
                scroll,
                BorderLayout.CENTER
        );
        lblTotalEmpleados = new JLabel("Mostrando 0 empleados");

        lblTotalEmpleados.setFont(
                new Font("SansSerif", Font.PLAIN, 12)
        );

        lblTotalEmpleados.setHorizontalAlignment(
                SwingConstants.RIGHT
        );

        lblTotalEmpleados.setBorder(
                BorderFactory.createEmptyBorder(8, 5, 5, 8)
        );

        panel.add(
                lblTotalEmpleados,
                BorderLayout.SOUTH
        );

        return panel;
    }

    private void cargarEmpleados() {

        modeloTabla.setRowCount(0);

        try {

        	for (Empleado empleado : empleadoDAO.listarTodos()) {

        		Object[] fila = {
        		        empleado.getId(),
        		        empleado.getNombreCompleto(),
        		        empleado.getDepartamento(),
        		        "Q " + empleado.getSalario().toPlainString(),
        		        empleado.getFechaContratacion(),
        		        empleado.getTipoContrato(),
        		        empleado.isActivo() ? "Activo" : "Inactivo"
        		};

        	    modeloTabla.addRow(fila);
        	}

        	lblTotalEmpleados.setText(
        	        "Mostrando " + modeloTabla.getRowCount() + " empleados"
        	);

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudieron cargar los empleados.\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void aplicarEstilos() {

        Color azul = new Color(45, 120, 220);
        Color rojo = new Color(220, 70, 70);
        Color gris = new Color(225, 230, 235);
        Color fondoTabla = new Color(250, 250, 250);

        // Campos
        txtNombre.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtDepartamento.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtSalario.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtFecha.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Botón Registrar
        btnRegistrar.setBackground(azul);
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnRegistrar.setFocusPainted(false);

        // Actualizar
        btnActualizar.setBackground(gris);
        btnActualizar.setForeground(new Color(40, 60, 90));
        btnActualizar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnActualizar.setFocusPainted(false);

        // Eliminar
        btnEliminar.setBackground(rojo);
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnEliminar.setFocusPainted(false);

        // Limpiar
        btnLimpiar.setBackground(gris);
        btnLimpiar.setForeground(new Color(40, 60, 90));
        btnLimpiar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnLimpiar.setFocusPainted(false);

        // Tabla
        tablaEmpleados.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );

        tablaEmpleados.setRowHeight(30);

        tablaEmpleados.setGridColor(
                new Color(220, 225, 230)
        );

        tablaEmpleados.setBackground(fondoTabla);

        tablaEmpleados.setSelectionBackground(
                new Color(210, 225, 245)
        );

        tablaEmpleados.setSelectionForeground(
                Color.BLACK
        );

        JTableHeader encabezado =
                tablaEmpleados.getTableHeader();
        encabezado.setBackground(
                new Color(225, 232, 240)
        );

        encabezado.setForeground(
                new Color(35, 45, 60)
        );

        encabezado.setOpaque(true);

        encabezado.setFont(
                new Font("SansSerif", Font.BOLD, 13)
        );

        encabezado.setPreferredSize(
                new Dimension(0, 35)
        );

        // Anchos de columnas
        tablaEmpleados.getColumnModel()
                .getColumn(0).setPreferredWidth(40);

        tablaEmpleados.getColumnModel()
                .getColumn(1).setPreferredWidth(170);

        tablaEmpleados.getColumnModel()
                .getColumn(2).setPreferredWidth(140);

        tablaEmpleados.getColumnModel()
                .getColumn(3).setPreferredWidth(90);

        tablaEmpleados.getColumnModel()
                .getColumn(4).setPreferredWidth(130);

        tablaEmpleados.getColumnModel()
                .getColumn(5).setPreferredWidth(70);
    }
    private void configurarEventos() {

        btnRegistrar.addActionListener(e -> registrarEmpleado());

        btnActualizar.addActionListener(e -> actualizarEmpleado());

        btnEliminar.addActionListener(e -> eliminarEmpleado());

        btnLimpiar.addActionListener(e -> limpiarFormulario());
        
        btnVerTotales.addActionListener(e -> mostrarTotales());

        tablaEmpleados.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                cargarEmpleadoSeleccionado();
            }
        });
    }
    private void registrarEmpleado() {

        String nombre = txtNombre.getText().trim();
        String departamento = txtDepartamento.getText().trim();
        String salarioTexto = txtSalario.getText().trim();
        String fechaTexto = txtFecha.getText().trim();
        boolean activo = chkActivo.isSelected();
        String tipoContrato =
                cmbTipoContrato.getSelectedItem().toString();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "El nombre no puede quedar vacío.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (departamento.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "El departamento no puede quedar vacío.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (salarioTexto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar el salario.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (fechaTexto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar la fecha de contratación.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        if (!tipoContratoValido(tipoContrato)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un tipo de contrato válido.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            BigDecimal salario = new BigDecimal(salarioTexto);

            if (salario.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "El salario debe ser mayor que cero.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            LocalDate fechaContratacion = LocalDate.parse(fechaTexto);

            if (fechaContratacion.isAfter(LocalDate.now())) {
                JOptionPane.showMessageDialog(
                        this,
                        "La fecha de contratación no puede ser futura.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Empleado empleado = new Empleado(
                    nombre,
                    departamento,
                    salario,
                    fechaContratacion,
                    activo,
                    tipoContrato
            );

            empleadoDAO.crear(empleado);

            JOptionPane.showMessageDialog(
                    this,
                    "Empleado registrado correctamente."
            );

            limpiarFormulario();
            cargarEmpleados();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El salario debe ser un número válido.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "La fecha debe tener el formato AAAA-MM-DD.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el empleado.\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void limpiarFormulario() {

        txtNombre.setText("");
        txtDepartamento.setText("");
        txtSalario.setText("");
        txtFecha.setText("");

        chkActivo.setSelected(true);

        tablaEmpleados.clearSelection();

        txtNombre.requestFocus();
        
        cmbTipoContrato.setSelectedIndex(0);
    }
    private void cargarEmpleadoSeleccionado() {

        int fila = tablaEmpleados.getSelectedRow();

        if (fila == -1) {
            return;
        }

        txtNombre.setText(
                modeloTabla.getValueAt(fila, 1).toString()
        );

        txtDepartamento.setText(
                modeloTabla.getValueAt(fila, 2).toString()
        );

        String salario = modeloTabla
                .getValueAt(fila, 3)
                .toString()
                .replace("Q ", "");

        txtSalario.setText(salario);

        txtFecha.setText(
                modeloTabla.getValueAt(fila, 4).toString()
        );

        String estado =
                modeloTabla.getValueAt(fila, 6).toString();

        chkActivo.setSelected(
                estado.equals("Activo")
        );
        String tipoContrato =
                modeloTabla.getValueAt(fila, 5).toString();

        cmbTipoContrato.setSelectedItem(tipoContrato);
    }
    private void actualizarEmpleado() {

        int fila = tablaEmpleados.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un empleado de la tabla.",
                    "Actualizar empleado",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int filaModelo = tablaEmpleados.convertRowIndexToModel(fila);

        int id = (int) modeloTabla.getValueAt(filaModelo, 0);

        String nombre = txtNombre.getText().trim();
        String departamento = txtDepartamento.getText().trim();
        String salarioTexto = txtSalario.getText().trim();
        String fechaTexto = txtFecha.getText().trim();
        boolean activo = chkActivo.isSelected();
        String tipoContrato =
                cmbTipoContrato.getSelectedItem().toString();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "El nombre no puede quedar vacío.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (departamento.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "El departamento no puede quedar vacío.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (salarioTexto.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar el salario.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (fechaTexto.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar la fecha de contratación.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }
        if (!tipoContratoValido(tipoContrato)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un tipo de contrato válido.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            BigDecimal salario = new BigDecimal(salarioTexto);

            if (salario.compareTo(BigDecimal.ZERO) <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "El salario debe ser mayor que cero.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            LocalDate fechaContratacion = LocalDate.parse(fechaTexto);

            if (fechaContratacion.isAfter(LocalDate.now())) {

                JOptionPane.showMessageDialog(
                        this,
                        "La fecha de contratación no puede ser futura.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            Empleado empleado = new Empleado(
                    id,
                    nombre,
                    departamento,
                    salario,
                    fechaContratacion,
                    activo,
                    tipoContrato
            );

            boolean actualizado = empleadoDAO.actualizar(empleado);

            if (actualizado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Empleado actualizado correctamente."
                );

                cargarEmpleados();
                limpiarFormulario();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo actualizar el empleado.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El salario debe ser un número válido.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "La fecha debe tener el formato AAAA-MM-DD.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar el empleado.\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void eliminarEmpleado() {

        int fila = tablaEmpleados.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un empleado de la tabla.",
                    "Eliminar empleado",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int filaModelo = tablaEmpleados.convertRowIndexToModel(fila);

        int id = (int) modeloTabla.getValueAt(filaModelo, 0);
        String nombre = modeloTabla.getValueAt(filaModelo, 1).toString();

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar al empleado " + nombre + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            boolean eliminado = empleadoDAO.eliminar(id);

            if (eliminado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Empleado eliminado correctamente."
                );

                cargarEmpleados();
                limpiarFormulario();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo eliminar el empleado.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el empleado.\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private static final String[] TIPOS_CONTRATO = {
            "Temporal",
            "Permanente",
            "Por hora"
    };
    private boolean tipoContratoValido(String tipoContrato) {

        for (String tipo : TIPOS_CONTRATO) {

            if (tipo.equals(tipoContrato)) {
                return true;
            }
        }

        return false;
    }
}
