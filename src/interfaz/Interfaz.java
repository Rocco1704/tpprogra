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
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;

import datos.conexion;
import datos.Localidad;
import datos.ParametroCosto;
import datos.PlanificacionResultado;

import logica.PlanificadorRed;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.Coordinate;

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
        EventQueue.invokeLater(() -> {
            try {
                Interfaz frame = new Interfaz();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Interfaz() {

        planificador = new PlanificadorRed();

        setTitle("Conectando Localidades");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 650);
        setLayout(new BorderLayout(5, 5));

        // PANEL FORMULARIO
        JPanel panelFormulario = new JPanel(new FlowLayout());
        panelFormulario.setBorder(new TitledBorder("Agregar localidad"));

        campoNombre = new JTextField(10);
        campoProvincia = new JTextField(10);
        campoLatitud = new JTextField(8);
        campoLongitud = new JTextField(8);

        JButton btnAgregar = new JButton("Agregar");
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

        // TABLA
        String[] columnas = {
            "Nombre",
            "Provincia",
            "Latitud",
            "Longitud"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaLocalidades = new JTable(modeloTabla);

        add(new JScrollPane(tablaLocalidades), BorderLayout.CENTER);

        // PANEL SUR
        JPanel panelSur = new JPanel(new BorderLayout());

        JPanel panelParametros = new JPanel(new FlowLayout());
        panelParametros.setBorder(new TitledBorder("Parámetros de costo"));

        campoCostoPorKm = new JTextField("200", 8);
        campoPorcentaje = new JTextField("10", 5);
        campoCostoFijo = new JTextField("300", 8);

        JButton btnPlanificar = new JButton("PLANIFICAR");
        btnPlanificar.setFont(new Font("Arial", Font.BOLD, 13));

        JButton btnVerMapa = new JButton("Ver Mapa");
        btnVerMapa.setFont(new Font("Arial", Font.BOLD, 13));

        panelParametros.add(new JLabel("Costo/km ($):"));
        panelParametros.add(campoCostoPorKm);

        panelParametros.add(new JLabel("% aumento >300km:"));
        panelParametros.add(campoPorcentaje);

        panelParametros.add(new JLabel("Costo fijo interprovincial ($):"));
        panelParametros.add(campoCostoFijo);

        panelParametros.add(btnVerMapa);
        panelParametros.add(btnPlanificar);

        panelSur.add(panelParametros, BorderLayout.NORTH);

        // RESULTADO
        areaResultado = new JTextArea(6, 50);
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaResultado.setBorder(new TitledBorder("Resultado"));

        panelSur.add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        add(panelSur, BorderLayout.SOUTH);

        // EVENTO MAPA
        btnVerMapa.addActionListener(e -> {

            JFrame ventanaMapa = new JFrame("Mapa de Localidades");
            ventanaMapa.setBounds(150, 150, 800, 600);

            JMapViewer visorMapa = new JMapViewer();

            visorMapa.setDisplayPosition(
                new Coordinate(-34.6, -58.4),
                6
            );

            // AGREGAR MARCADORES
            for (Localidad loc : planificador.getLocalidades()) {

                MapMarkerDot marcador = new MapMarkerDot(
                    loc.getLatitud(),
                    loc.getLongitud()
                );

                marcador.setName(loc.getNombre());

                visorMapa.addMapMarker(marcador);
            }

            ventanaMapa.add(visorMapa);
            ventanaMapa.setVisible(true);
        });

        // EVENTOS
        btnAgregar.addActionListener(e -> agregarLocalidad());

        btnEliminar.addActionListener(e -> eliminarLocalidad());

        btnPlanificar.addActionListener(e -> planificar());
    }

    private void agregarLocalidad() {

        try {

            String nombre = campoNombre.getText().trim();
            String provincia = campoProvincia.getText().trim();

            if (nombre.isEmpty() || provincia.isEmpty()) {

                JOptionPane.showMessageDialog(
                    this,
                    "Nombre y provincia no pueden estar vacíos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            String entradaNormalizada =
                (nombre + provincia)
                .toLowerCase()
                .replace(" ", "");

            for (int i = 0; i < modeloTabla.getRowCount(); i++) {

                String nombreTabla =
                    modeloTabla.getValueAt(i, 0).toString();

                String provinciaTabla =
                    modeloTabla.getValueAt(i, 1).toString();

                String existenteNormalizado =
                    (nombreTabla + provinciaTabla)
                    .toLowerCase()
                    .replace(" ", "");

                if (entradaNormalizada.equals(existenteNormalizado)) {

                    JOptionPane.showMessageDialog(
                        this,
                        "Esta localidad ya existe.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }
            }

            double latitud =
                Double.parseDouble(campoLatitud.getText().trim());

            double longitud =
                Double.parseDouble(campoLongitud.getText().trim());

            Localidad localidad =
                new Localidad(nombre, provincia, latitud, longitud);

            planificador.agregarLocalidad(localidad);

            modeloTabla.addRow(new Object[] {
                nombre,
                provincia,
                latitud,
                longitud
            });

            campoNombre.setText("");
            campoProvincia.setText("");
            campoLatitud.setText("");
            campoLongitud.setText("");

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                this,
                "Latitud y longitud deben ser números.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarLocalidad() {

        int fila = tablaLocalidades.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                this,
                "Seleccioná una localidad.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Localidad localidad =
            planificador.getLocalidades().get(fila);

        planificador.eliminarLocalidad(localidad);

        modeloTabla.removeRow(fila);
    }

    private void planificar() {

        try {

            double costoPorKm =
                Double.parseDouble(campoCostoPorKm.getText().trim());

            double porcentaje =
                Double.parseDouble(campoPorcentaje.getText().trim());

            double costoFijo =
                Double.parseDouble(campoCostoFijo.getText().trim());

            ParametroCosto parametros =
                new ParametroCosto(
                    costoPorKm,
                    porcentaje,
                    costoFijo
                );

            PlanificacionResultado resultado =
                planificador.planificar(parametros);

            StringBuilder sb = new StringBuilder();

            for (conexion c : resultado.getConexiones()) {
                sb.append(c.toString() + "\n");
            }

            sb.append("\nCOSTO TOTAL: $");
            sb.append(resultado.getCostoTotal());

            areaResultado.setText(sb.toString());

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                this,
                "Los parámetros deben ser números.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}