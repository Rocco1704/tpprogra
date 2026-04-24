package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import datos.conexion;
import datos.Localidad;
import datos.ParametroCosto;
import datos.PlanificacionResultado;
import logica.PlanificadorRed;

public class Interfaz extends JFrame {

    private static final long serialVersionUID = 1L;

    private PlanificadorRed planificador;

    // Campos formulario
    private JTextField campoNombre;
    private JTextField campoProvincia;
    private JTextField campoLatitud;
    private JTextField campoLongitud;

    // Tabla
    private JTable tablaLocalidades;
    private DefaultTableModel modeloTabla;

    // Parámetros
    private JTextField campoCostoPorKm;
    private JTextField campoPorcentaje;
    private JTextField campoCostoFijo;

    // Resultado
    private JTextArea areaResultado;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Interfaz frame = new Interfaz();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Interfaz() {
        planificador = new PlanificadorRed();

        setTitle("Conectando Localidades");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 650);
        setLayout(new BorderLayout(5, 5));

        // ── Panel North: formulario ──────────────────────────────────
        JPanel panelFormulario = new JPanel(new FlowLayout());
        panelFormulario.setBorder(new TitledBorder("Agregar localidad"));

        campoNombre    = new JTextField(10);
        campoProvincia = new JTextField(10);
        campoLatitud   = new JTextField(8);
        campoLongitud  = new JTextField(8);

        JButton btnAgregar  = new JButton("Agregar");
        JButton btnEliminar = new JButton("Eliminar");

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(campoNombre);
        panelFormulario.add(new JLabel("Provincia:"));
        panelFormulario.add(campoProvincia);
        panelFormulario.add(new JLabel("Latitud:"));
        panelFormulario.add(campoLatitud);
        panelFormulario.add(new JLabel("Longitud:"));
        panelFormulario.add(campoLongitud);
        panelFormulario.add(btnAgregar);
        panelFormulario.add(btnEliminar);

        add(panelFormulario, BorderLayout.NORTH);

        // ── Panel Center: tabla ──────────────────────────────────────
        String[] columnas = {"Nombre", "Provincia", "Latitud", "Longitud"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaLocalidades = new JTable(modeloTabla);
        add(new JScrollPane(tablaLocalidades), BorderLayout.CENTER);

        // ── Panel South: parámetros + resultado ──────────────────────
        JPanel panelSur = new JPanel(new BorderLayout());

        // Parámetros
        JPanel panelParametros = new JPanel(new FlowLayout());
        panelParametros.setBorder(new TitledBorder("Parámetros de costo"));

        campoCostoPorKm = new JTextField("200", 8);
        campoPorcentaje = new JTextField("10", 5);
        campoCostoFijo  = new JTextField("300", 8);

        JButton btnPlanificar = new JButton("PLANIFICAR");
        btnPlanificar.setFont(new Font("Arial", Font.BOLD, 13));

        panelParametros.add(new JLabel("Costo/km ($):"));
        panelParametros.add(campoCostoPorKm);
        panelParametros.add(new JLabel("% aumento >300km:"));
        panelParametros.add(campoPorcentaje);
        panelParametros.add(new JLabel("Costo fijo interprovincial ($):"));
        panelParametros.add(campoCostoFijo);
        panelParametros.add(btnPlanificar);

        panelSur.add(panelParametros, BorderLayout.NORTH);

        // Resultado
        areaResultado = new JTextArea(6, 50);
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaResultado.setBorder(new TitledBorder("Resultado"));
        panelSur.add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        add(panelSur, BorderLayout.SOUTH);

        // ── Eventos de botones ───────────────────────────────────────
        btnAgregar.addActionListener(e -> agregarLocalidad());
        btnEliminar.addActionListener(e -> eliminarLocalidad());
        btnPlanificar.addActionListener(e -> planificar());
    }

    private void agregarLocalidad() {
        try {
            String nombre    = campoNombre.getText().trim();
            String provincia = campoProvincia.getText().trim();
            double latitud   = Double.parseDouble(campoLatitud.getText().trim());
            double longitud  = Double.parseDouble(campoLongitud.getText().trim());

            if (nombre.isEmpty() || provincia.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Nombre y provincia no pueden estar vacíos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Localidad localidad = new Localidad(nombre, provincia, latitud, longitud);
            planificador.agregarLocalidad(localidad);
            modeloTabla.addRow(new Object[]{nombre, provincia, latitud, longitud});

            campoNombre.setText("");
            campoProvincia.setText("");
            campoLatitud.setText("");
            campoLongitud.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Latitud y longitud deben ser números.",
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarLocalidad() {
        int fila = tablaLocalidades.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                "Seleccioná una localidad de la tabla.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Localidad localidad = planificador.getLocalidades().get(fila);
        planificador.eliminarLocalidad(localidad);
        modeloTabla.removeRow(fila);
    }

    private void planificar() {
        try {
            double costoPorKm = Double.parseDouble(campoCostoPorKm.getText().trim());
            double porcentaje = Double.parseDouble(campoPorcentaje.getText().trim());
            double costoFijo  = Double.parseDouble(campoCostoFijo.getText().trim());

            ParametroCosto parametros = new ParametroCosto(costoPorKm, porcentaje, costoFijo);
            PlanificacionResultado resultado = planificador.planificar(parametros);

            StringBuilder sb = new StringBuilder();
            for (conexion c : resultado.getConexiones()) {
                sb.append(c.toString() + "\n");
            }
            sb.append("\nCOSTO TOTAL: $" + resultado.getCostoTotal());
            areaResultado.setText(sb.toString());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Los parámetros deben ser números.",
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}