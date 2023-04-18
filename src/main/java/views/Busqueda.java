package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.util.List;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import dao.HuespedDao;
import dao.ReservaDao;
import modelos.Huesped;
import modelos.Reserva;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modeloReserva;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;

	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("hotel");
	static EntityManager em = emf.createEntityManager();
	private static HuespedDao huespedDao = new HuespedDao(em);
	private static ReservaDao reservaDao = new ReservaDao(em);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
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
	public Busqueda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);

		txtBuscar = new JTextField("Ingrese un apellido o id");
		txtBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				txtBuscar.setText("");
				txtBuscar.setForeground(new Color(0, 0,0));
			}
		});

		txtBuscar.setForeground(new Color(108, 108, 108));
		txtBuscar.setBounds(545, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);

		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloReserva = (DefaultTableModel) tbReservas.getModel();
		modeloReserva.addColumn("Numero de Reserva");
		modeloReserva.addColumn("Fecha Check In");
		modeloReserva.addColumn("Fecha Check Out");
		modeloReserva.addColumn("Forma de Pago");
		modeloReserva.addColumn("Valor");

		cargarTablaReservas();

		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table,
				null);
		scroll_table.setVisible(true);

		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");

		cargarTablaHuespedes();

		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")),
				scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);

		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);

			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);

		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) { // Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) { // Al usuario quitar el mouse por el botón este volverá al estado
													// original
				btnexit.setBackground(Color.white);
				labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);

		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (panel.getSelectedIndex() == 1) {
					limpiarTabla(modeloHuesped, tbHuespedes);
					var listaHuespedes = huespedDao.listarPorApellido(txtBuscar.getText());
					listaHuespedes.forEach(huesped -> modeloHuesped.addRow(new Object[] { huesped.getId(),
							huesped.getNombre(), huesped.getApellido(), huesped.getFechaNacimiento(),
							huesped.getNacionalidad(), huesped.getTelefono(), huesped.getId() }));
				} else if (panel.getSelectedIndex() == 0) {
					limpiarTabla(modeloReserva, tbHuespedes);
					String buscar = txtBuscar.getText().trim();
					if (!buscar.isEmpty() && buscar.matches("\\d+")) {
						var listaReservas = reservaDao.listarPorId(Integer.parseInt(buscar));
						listaReservas.forEach(reserva -> modeloReserva
								.addRow(new Object[] { reserva.getId(), reserva.getFechaEntrada(),
										reserva.getFechaSalida(), reserva.getFormaPago(), reserva.getValor() }));
					} else {
						JOptionPane.showMessageDialog(null, "Debe ingresar un número válido.");
					}
				}
			}
		});

		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);

		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (panel.getSelectedIndex() == 1) {
					editarHuesped();
					limpiarTabla(modeloHuesped, tbHuespedes);
					cargarTablaHuespedes();

				} else if (panel.getSelectedIndex() == 0) {
					editarReserva();
					limpiarTabla(modeloReserva, tbReservas);
					cargarTablaReservas();
				}
			}
		});

		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);

		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (panel.getSelectedIndex() == 1) {
					eliminarHuesped();
					limpiarTabla(modeloHuesped, tbHuespedes);
					cargarTablaHuespedes();

				} else if (panel.getSelectedIndex() == 0) {
					eliminarReserva();
					limpiarTabla(modeloReserva, tbReservas);
					cargarTablaReservas();
				}
			}
		});

		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}

//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}

	public void cargarTablaHuespedes() {

		List<Huesped> listaHuespedes = huespedDao.obtenerTodos();
		for (Huesped huesped : listaHuespedes) {
			modeloHuesped.addRow(new Object[] { huesped.getId(), huesped.getNombre(), huesped.getApellido(),
					huesped.getFechaNacimiento(), huesped.getNacionalidad(), huesped.getTelefono(),
					huesped.getReserva().getId() });
			System.out.println(huesped.getApellido());
		}
	}

	public void cargarTablaReservas() {

		List<Reserva> listaReservas = reservaDao.obtenerTodos();
		for (Reserva reserva : listaReservas) {
			modeloReserva.addRow(new Object[] { reserva.getId(), reserva.getFechaEntrada(), reserva.getFechaSalida(),
					reserva.getFormaPago(), reserva.getValor() });

		}
	}

	public void editarHuesped() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Long idHuesped = (Long) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0);
		String nombre = modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1).toString().trim();
		String apellido = modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2).toString().trim();

		String nacimiento = modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3).toString().trim();
		LocalDate fechaNacimiento = LocalDate.parse(nacimiento, formatter);

		String nacionalidad = modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4).toString().trim();
		String telefono = modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5).toString().trim();

		Huesped huesped = new Huesped(idHuesped, nombre, apellido, fechaNacimiento, nacionalidad, telefono);
		huespedDao.actualizarHuesped(huesped);
		modeloHuesped.fireTableDataChanged();
	}

	public void editarReserva() {
		Long idReserva = (Long) modeloReserva.getValueAt(tbReservas.getSelectedRow(), 0);
		String fechaEntradaStr = modeloReserva.getValueAt(tbReservas.getSelectedRow(), 1).toString().trim();
		String fechaSalidaStr = modeloReserva.getValueAt(tbReservas.getSelectedRow(), 2).toString().trim();
		String formaPago = modeloReserva.getValueAt(tbReservas.getSelectedRow(), 3).toString().trim();
		Double valor = Double.parseDouble(modeloReserva.getValueAt(tbReservas.getSelectedRow(), 4).toString().trim());

		LocalDate fechaEntrada = LocalDate.parse(fechaEntradaStr);
		LocalDate fechaSalida = LocalDate.parse(fechaSalidaStr);

		Reserva reserva = new Reserva(idReserva, fechaEntrada, fechaSalida, valor, formaPago, null);
		reservaDao.actualizarReserva(reserva);
		modeloReserva.fireTableDataChanged();
	}

	public void eliminarHuesped() {
		int filaSeleccionada = tbHuespedes.getSelectedRow();
		if (filaSeleccionada == -1) {
			return;
		}
		Long idHuesped = (Long) modeloHuesped.getValueAt(filaSeleccionada, 0);
		huespedDao.eliminarHuesped(idHuesped);
		modeloHuesped.removeRow(filaSeleccionada);
	}

	public void eliminarReserva() {
		int filaSeleccionada = tbReservas.getSelectedRow();
		if (filaSeleccionada == -1) {
			return;
		}
		Long idReserva = (Long) modeloReserva.getValueAt(filaSeleccionada, 0);
		reservaDao.eliminarReserva(idReserva);
		modeloReserva.removeRow(filaSeleccionada);
	}

	public void limpiarTabla(DefaultTableModel tableModel, JTable table) {
		if (tableModel.getRowCount() > 0) {
			for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
				tableModel.removeRow(i);
			}
		}
		table.setModel(tableModel);
	}

}
