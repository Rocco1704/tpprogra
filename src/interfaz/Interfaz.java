package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import logica.PlanificadorRed;
import datos.Localidad;
import datos.ParametroCosto;
import datos.PlanificacionResultado;
import datos.conexion;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
public class Interfaz extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PlanificadorRed planificador;
	private JTextField campoNombre;
	private JTextField campoProvincia;
	private JTextField campoLatitud;
	private JTextField campoLongitud;
	private JTable tablaLocalidades;
	private JTextField campoCostoPorKm;
	private JTextField campoPorcentaje;
	private JTextField campoCostoFijo;
	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public Interfaz() {
		planificador = new PlanificadorRed();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel_2 = new JLabel("Nombre");
		panel.add(lblNewLabel_2);
		
		campoNombre = new JTextField();
		panel.add(campoNombre);
		campoNombre.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Provincia");
		panel.add(lblNewLabel);
		
		campoProvincia = new JTextField();
		panel.add(campoProvincia);
		campoProvincia.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Latitud");
		panel.add(lblNewLabel_1);
		
		campoLatitud = new JTextField();
		panel.add(campoLatitud);
		campoLatitud.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Longitud");
		panel.add(lblNewLabel_3);
		
		campoLongitud = new JTextField();
		panel.add(campoLongitud);
		campoLongitud.setColumns(10);
		
		JButton btnNewButton = new JButton("Agregar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Eliminar");
		panel.add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		tablaLocalidades = new JTable();
		scrollPane.setViewportView(tablaLocalidades);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel_4 = new JLabel("Costo/Km:");
		panel_1.add(lblNewLabel_4);
		
		campoCostoPorKm = new JTextField();
		panel_1.add(campoCostoPorKm);
		campoCostoPorKm.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("% Aumento");
		panel_1.add(lblNewLabel_5);
		
		campoPorcentaje = new JTextField();
		panel_1.add(campoPorcentaje);
		campoPorcentaje.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Costo Fijo");
		panel_1.add(lblNewLabel_6);
		
		campoCostoFijo = new JTextField();
		panel_1.add(campoCostoFijo);
		campoCostoFijo.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("Planificar");
		panel_1.add(btnNewButton_2);
		
		JTextArea areaResultado = new JTextArea();
		areaResultado.setTabSize(10);
		panel_1.add(areaResultado);

	}

}
