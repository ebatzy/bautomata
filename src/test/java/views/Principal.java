package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import config.Mensajes;
import config.MensajesObserver;
import config.Preferencias;
import controllers.Login;
import controllers.transferencia.Transferencia_propia;
import helpers.Bi_helper;

public class Principal extends JFrame implements MensajesObserver {
	static Preferencias preferencias = Preferencias.getInstance();
	private String AMBIENTE_JSON = preferencias.obtenerAtributo("rutaJsonAmbiente");
	private static Map<Integer, String> ambientes = new HashMap<>();

	private static JPanel panelNivel = new JPanel();
	private static JPanel panelNavegador = new JPanel();
	private static JPanel panelAmbiente = new JPanel();
	private static JPanel panelPruebas = new JPanel();
	private static JPanel panelReinicio = new JPanel(new BorderLayout());
	private static JPanel panelMensaje = new JPanel(new BorderLayout());
	private static JLabel labelMensaje = new JLabel();

	private Color colorFondo = new Color(158, 218, 255);

	public Principal() {
		setTitle("Automatizaciones Transversales");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		setSize(900, 700);
		getAmbientes();

		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(5, 2, 2, 2);
		c.gridx = 0;
		c.gridy = 0;
		add(columnaNivel(panelNivel, panelNavegador), c);
		c.gridx = 1;
		add(columnaNavegador(panelNavegador, panelAmbiente), c);
		c.gridx = 2;
		add(columnaAmbiente(panelAmbiente, panelPruebas), c);
		c.gridx = 3;
		add(columnaTest(panelPruebas, panelReinicio), c);
		c.gridy = 1;
		c.gridwidth = 4;
		c.gridx = 0;
		add(btnReinicio(panelReinicio), c);

		c.gridy = 2;
		c.gridwidth = 5;
		c.gridx = 0;
		add(vistaMensaje(panelMensaje), c);

		System.out.println("HashCode de la instancia en views: " + preferencias.hashCode());

		iniciarBotones();
		getContentPane().setBackground(new Color(199, 238, 255));
		setVisible(true);
	}

	private JPanel columnaNivel(JPanel actual, JPanel siguiente) {
		JLabel titulo = new JLabel("Nivel", SwingConstants.CENTER);
		JPanel secundario = new JPanel(new BorderLayout());

		secundario.setBackground(colorFondo);
		secundario.setPreferredSize(new Dimension(150, 270));

		titulo.setPreferredSize(new Dimension(300, 25));
		titulo.setFont(new Font("Arial", Font.BOLD, 15));

		actual.setLayout(new BoxLayout(actual, BoxLayout.Y_AXIS));
		actual.setBackground(new Color(199, 238, 255));

		secundario.add(titulo, BorderLayout.NORTH);
		for (int i = 1; i <= 3; i++) {
			int valor = i;
			JButton temp = new JButton("Nivel " + valor);
			temp.setAlignmentX(Component.CENTER_ALIGNMENT);
			temp.addActionListener(new AccionBtn(actual, siguiente));
			temp.addActionListener(e -> {
				preferencias.valorAtributo("nivelTest", String.valueOf(valor));
				temp.setBackground(new Color(158, 218, 255));
				temp.setSelected(true);
			});

			actual.add(temp);
		}

		secundario.add(actual, BorderLayout.CENTER);

		return secundario;
	}

	private JPanel columnaNavegador(JPanel actual, JPanel siguiente) {
		JLabel titulo = new JLabel("Navegador", SwingConstants.CENTER);
		JPanel secundario = new JPanel(new BorderLayout());

		JButton btnChrome = new JButton("Chrome",
				iconoRedimensionado(new ImageIcon("src/test/resources/img/Chrome.png"), 25, 25));
		JButton btnFirefox = new JButton("Firefox",
				iconoRedimensionado(new ImageIcon("src/test/resources/img/Firefox.png"), 25, 25));
		JButton btnEdge = new JButton("Edge",
				iconoRedimensionado(new ImageIcon("src/test/resources/img/Edge.png"), 25, 25));
		JButton btnSafari = new JButton("Safari",
				iconoRedimensionado(new ImageIcon("src/test/resources/img/Safari.png"), 25, 25));

		secundario.setBackground(colorFondo);
		secundario.setPreferredSize(new Dimension(150, 270));

		titulo.setPreferredSize(new Dimension(300, 25));
		titulo.setFont(new Font("Arial", Font.BOLD, 15));

		actual.setLayout(new BoxLayout(actual, BoxLayout.Y_AXIS));
		actual.setBackground(new Color(199, 238, 255));

		btnChrome.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnChrome.addActionListener(new AccionBtn(actual, siguiente));
		btnChrome.addActionListener(e -> {
			preferencias.valorAtributo("navegadorTipo", "1");
			preferencias.valorAtributo("navegadorNombre", "Chrome");
			btnChrome.setBackground(colorFondo);
		});

		btnFirefox.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnFirefox.addActionListener(new AccionBtn(actual, siguiente));
		btnFirefox.addActionListener(e -> {
			preferencias.valorAtributo("navegadorTipo", "2");
			preferencias.valorAtributo("navegadorNombre", "Firefox");
			btnFirefox.setBackground(colorFondo);
		});

		btnEdge.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnEdge.addActionListener(new AccionBtn(actual, siguiente));
		btnEdge.addActionListener(e -> {
			preferencias.valorAtributo("navegadorTipo", "3");
			preferencias.valorAtributo("navegadorNombre", "Edge");
			btnEdge.setBackground(colorFondo);
		});

		btnSafari.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSafari.addActionListener(new AccionBtn(actual, siguiente));
		btnSafari.addActionListener(e -> {
			preferencias.valorAtributo("navegadorTipo", "4");
			preferencias.valorAtributo("navegadorNombre", "Safari");
			btnSafari.setBackground(colorFondo);
		});

		secundario.add(titulo, BorderLayout.NORTH);
		actual.add(btnChrome);
		actual.add(btnFirefox);
		actual.add(btnEdge);
		actual.add(btnSafari);
		secundario.add(actual, BorderLayout.CENTER);

		return secundario;
	}

	public JPanel columnaAmbiente(JPanel actual, JPanel siguiente) {
		JLabel titulo = new JLabel("QA's", SwingConstants.CENTER);
		JPanel secundario = new JPanel(new BorderLayout());

		secundario.setBackground(colorFondo);
		secundario.setPreferredSize(new Dimension(150, 270));

		titulo.setPreferredSize(new Dimension(300, 25));
		titulo.setFont(new Font("Arial", Font.BOLD, 15));

		actual.setLayout(new BoxLayout(actual, BoxLayout.Y_AXIS));
		actual.setBackground(new Color(199, 238, 255));

		secundario.add(titulo, BorderLayout.NORTH);

		for (Map.Entry<Integer, String> entry : ambientes.entrySet()) {

			JButton boton = new JButton(entry.getValue());
			boton.setAlignmentX(Component.CENTER_ALIGNMENT);
			boton.addActionListener(new AccionBtn(actual, siguiente));
			boton.addActionListener(e -> {
				try {
					setAmbientes(entry.getKey());
					boton.setBackground(colorFondo);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			});
			actual.add(boton);
		}

		secundario.add(actual, BorderLayout.CENTER);

		return secundario;
	}

	public JPanel columnaTest(JPanel actual, JPanel siguiente) {
		JLabel titulo = new JLabel("Test", SwingConstants.CENTER);
		JPanel secundario = new JPanel(new BorderLayout());

		secundario.setBackground(colorFondo);
		secundario.setPreferredSize(new Dimension(200, 270));

		titulo.setPreferredSize(new Dimension(300, 25));
		titulo.setFont(new Font("Arial", Font.BOLD, 15));

		actual.setLayout(new BoxLayout(actual, BoxLayout.Y_AXIS));
		actual.setBackground(new Color(199, 238, 255));

		JButton test1 = new JButton("Login");
		test1.setAlignmentX(Component.CENTER_ALIGNMENT);
		test1.addActionListener(new AccionBtn(actual, siguiente));
		test1.addActionListener(e -> {
			test1.setBackground(colorFondo);
			Login.main(null);
		});

		JButton test2 = new JButton("Transferencias Propias");
		test2.setAlignmentX(Component.CENTER_ALIGNMENT);
		test2.addActionListener(new AccionBtn(actual, siguiente));
		test2.addActionListener(e -> {
			test2.setBackground(colorFondo);
			Transferencia_propia.main(null);
		});

		secundario.add(titulo, BorderLayout.NORTH);
		actual.add(test1);
		actual.add(test2);
		secundario.add(actual, BorderLayout.CENTER);

		return secundario;
	}

	private JPanel btnReinicio(JPanel actual) {
		JButton temp = new JButton("Reiniciar");
		temp.setEnabled(false);
		temp.addActionListener(e -> iniciarBotones());

		actual.add(temp);

		return actual;
	}

	private JPanel vistaMensaje(JPanel actual) {
		actual.setLayout(new BoxLayout(panelMensaje, BoxLayout.Y_AXIS));
		actual.setBorder(new LineBorder(Color.black));

		labelMensaje.setHorizontalAlignment(JLabel.CENTER);

		actual.add(labelMensaje);
		return actual;
	}

	private class AccionBtn implements ActionListener {
		private JPanel siguiente;
		private JPanel previo;

		public AccionBtn(JPanel previo, JPanel siguiente) {
			this.previo = previo;
			this.siguiente = siguiente;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (Component previo : previo.getComponents()) {
				if (previo instanceof JButton) {
					previo.setEnabled(false);
				}
			}
			for (Component siguiente : siguiente.getComponents()) {
				if (siguiente instanceof JButton) {
					siguiente.setEnabled(true);
				}
			}
		}
	}

	@Override
	public void actualizar(ArrayList<String> nuevoMensaje) {

		if (nuevoMensaje.size() == 0) {
			labelMensaje.setIcon(iconoRedimensionado(new ImageIcon("src/test/resources/img/Correcto.png"), 36, 36));
			labelMensaje.setText("Test ejecutado con éxito");
			panelMensaje.setBackground(Color.green);
		} else {
			StringBuilder mensaje = new StringBuilder("<html>");
			for (String msg : nuevoMensaje) {
				mensaje.append(msg).append("\n");
			}

			mensaje.append("</html>");

			labelMensaje.setIcon(iconoRedimensionado(new ImageIcon("src/test/resources/img/Fallo.png"), 36, 36));
			labelMensaje.setText(mensaje.toString());
			panelMensaje.setBackground(Color.red);
		}

		Border border = BorderFactory.createEmptyBorder(15, 5, 15, 25);
		labelMensaje.setBorder(border);
		new LineBorder(Color.RED, 4);

		labelMensaje.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		labelMensaje.setAlignmentY(JLabel.CENTER_ALIGNMENT);

		Dimension size = labelMensaje.getPreferredSize();
		labelMensaje.setPreferredSize(size);
		labelMensaje.setFont(new Font("Serif", Font.BOLD, 12));
		labelMensaje.setForeground(Color.white);

		panelMensaje.setVisible(true);
	}

	private static Icon iconoRedimensionado(ImageIcon icono, int w, int h) {
		Image imagenRedimensionada = icono.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
		ImageIcon iconoRedimensionado = new ImageIcon(imagenRedimensionada);
		return iconoRedimensionado;
	}

	private static void getAmbientes() {
		ambientes.put(1, "QA01");
		ambientes.put(2, "QA02");
		ambientes.put(3, "QA03");
		ambientes.put(4, "QA04");
		ambientes.put(5, "QA05");
		ambientes.put(6, "QA06");
		ambientes.put(7, "QA07");
		ambientes.put(8, "QA08");
		ambientes.put(9, "Produccion");
	}

	private void setAmbientes(Integer valor) throws InterruptedException {

		String temp = "";

		if (ambientes.containsKey(valor)) {
			temp = String.valueOf(ambientes.get(valor));
		} else {
			temp = "Produccion";
		}

		preferencias.valorAtributo("paginaWeb", Bi_helper.obtenerDato(temp, "url", AMBIENTE_JSON));
	}

	private void iniciarBotones() {
		List<JButton> botones = obtenerTodosLosJButtons(this);
		Color tmp = new JButton().getBackground();
		for (JButton boton : botones) {
			boton.setEnabled(false);
			boton.setBackground(tmp);
		}

		for (Component panelNivel : panelNivel.getComponents()) {
			if (panelNivel instanceof JButton) {
				panelNivel.setEnabled(true);
			}
		}

		Mensajes.limpiarMensaje();
		panelMensaje.setVisible(false);
	}

	private List<JButton> obtenerTodosLosJButtons(Container container) {
		List<JButton> botones = new ArrayList<>();
		buscarJButtons(container, botones);
		return botones;
	}

	private void buscarJButtons(Container container, List<JButton> botones) {
		Component[] componentes = container.getComponents();

		for (Component componente : componentes) {
			if (componente instanceof JButton) {
				botones.add((JButton) componente);
			} else if (componente instanceof Container) {
				buscarJButtons((Container) componente, botones);
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Principal ventana = new Principal();
			Mensajes.addObserver(ventana);
		});
	}
}