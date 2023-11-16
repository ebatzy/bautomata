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
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

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

public class Principal implements MensajesObserver {
	static Preferencias preferencias = Preferencias.PREFERENCIAS();
	private static final String AMBIENTE_JSON = preferencias.obtenerAtributo("rutaJsonAmbiente");

	static ImageIcon iconoChrome = new ImageIcon("src/test/resources/img/Chrome.png");
	static ImageIcon iconoFirefox = new ImageIcon("src/test/resources/img/Firefox.png");
	static ImageIcon iconoEdge = new ImageIcon("src/test/resources/img/Edge.png");
	static ImageIcon iconoSafari = new ImageIcon("src/test/resources/img/Safari.png");
	static ImageIcon iconoFallo = new ImageIcon("src/test/resources/img/Fallo.png");
	static ImageIcon iconoCorrecto = new ImageIcon("src/test/resources/img/Correcto.png");

	static ArrayList<String> arregloResultado = new ArrayList<String>();
	static ImageIcon iconoResultado;
	static Color colorResultado;
	static Boolean mostrarResultado = false;

	private static PropertyChangeSupport escucha;

	public Principal() {
		Mensajes.addObserver(this);
	}

	public static void main(String[] args) {
		escucha = new PropertyChangeSupport(new Principal());

		Color colorFondo = new Color(158, 218, 255);

		SwingUtilities.invokeLater(() -> {

			Principal obj = new Principal();

			JFrame frame = new JFrame("Automatizaciones Transversales");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();

			JPanel panelNivel = new JPanel(new BorderLayout());
			panelNivel.setBackground(colorFondo);
			panelNivel.setPreferredSize(new Dimension(150, 270));
			JLabel tituloNivel = new JLabel("Nivel", SwingConstants.CENTER);
			tituloNivel.setPreferredSize(new Dimension(300, 25));
			tituloNivel.setFont(new Font("Arial", Font.BOLD, 15));
			panelNivel.add(tituloNivel, BorderLayout.NORTH);

			JPanel botonesNiveles = new JPanel();
			botonesNiveles.setLayout(new BoxLayout(botonesNiveles, BoxLayout.Y_AXIS));
			botonesNiveles.setBackground(new Color(199, 238, 255));
			panelNivel.add(botonesNiveles, BorderLayout.CENTER);

			JPanel panelBrowser = new JPanel(new BorderLayout());
			panelBrowser.setBackground(colorFondo);
			panelBrowser.setPreferredSize(new Dimension(150, 270));
			JLabel tituloBrowser = new JLabel("Navegador", SwingConstants.CENTER);
			tituloBrowser.setPreferredSize(new Dimension(150, 25));
			tituloBrowser.setFont(new Font("Arial", Font.BOLD, 15));
			panelBrowser.add(tituloBrowser, BorderLayout.NORTH);

			JPanel botonesBrowsers = new JPanel();
			botonesBrowsers.setLayout(new BoxLayout(botonesBrowsers, BoxLayout.Y_AXIS));
			botonesBrowsers.setBackground(new Color(199, 238, 255));
			panelBrowser.add(botonesBrowsers, BorderLayout.CENTER);

			JPanel panelAmbientes = new JPanel(new BorderLayout());
			panelAmbientes.setBackground(new Color(158, 218, 255));
			panelAmbientes.setPreferredSize(new Dimension(150, 270));
			JLabel tituloIzquierdo = new JLabel("QA's", SwingConstants.CENTER);
			tituloIzquierdo.setPreferredSize(new Dimension(300, 25));
			tituloIzquierdo.setFont(new Font("Arial", Font.BOLD, 15));
			panelAmbientes.add(tituloIzquierdo, BorderLayout.NORTH);

			JPanel botonesAmbientes = new JPanel();
			botonesAmbientes.setLayout(new BoxLayout(botonesAmbientes, BoxLayout.Y_AXIS));
			botonesAmbientes.setBackground(new Color(199, 238, 255));
			panelAmbientes.add(botonesAmbientes, BorderLayout.CENTER);

			JPanel panelTests = new JPanel(new BorderLayout());
			panelTests.setBackground(new Color(158, 218, 255));
			panelTests.setPreferredSize(new Dimension(200, 270));
			JLabel tituloDerecho = new JLabel("Tests", SwingConstants.CENTER);
			tituloDerecho.setPreferredSize(new Dimension(300, 25));
			tituloDerecho.setFont(new Font("Arial", Font.BOLD, 15));
			panelTests.add(tituloDerecho, BorderLayout.NORTH);

			JPanel botonesTests = new JPanel();
			botonesTests.setLayout(new BoxLayout(botonesTests, BoxLayout.Y_AXIS));
			botonesTests.setBackground(new Color(199, 238, 255));

			JButton botonNivel1 = new JButton("Nivel 1");
			botonNivel1.setEnabled(true);
			botonNivel1.setAlignmentX(Component.CENTER_ALIGNMENT);
			c.insets.set(10, 10, 10, 10);
			botonesNiveles.add(botonNivel1);
			botonNivel1.addActionListener(e -> {
				preferencias.valorAtributo("nivelTest", 1);
				botonNivel1.setBackground(colorFondo);
			});
			botonNivel1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (Component btnsLevels : botonesNiveles.getComponents()) {
						if (btnsLevels instanceof JButton) {
							btnsLevels.setEnabled(false);
						}
					}
					for (Component btnsBrowsers : botonesBrowsers.getComponents()) {
						if (btnsBrowsers instanceof JButton) {
							btnsBrowsers.setEnabled(true);
						}
					}
				}
			});

			JButton botonNivel2 = new JButton("Nivel 2");
			botonNivel2.setEnabled(true);
			botonNivel2.setAlignmentX(Component.CENTER_ALIGNMENT);
			botonesNiveles.add(botonNivel2);
			botonNivel2.addActionListener(e -> {
				preferencias.valorAtributo("nivelTest", 2);
				botonNivel2.setBackground(colorFondo);
			});
			botonNivel2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (Component btnsLevels : botonesNiveles.getComponents()) {
						if (btnsLevels instanceof JButton) {
							btnsLevels.setEnabled(false);
						}
					}
					for (Component btnsBrowsers : botonesBrowsers.getComponents()) {
						if (btnsBrowsers instanceof JButton) {
							btnsBrowsers.setEnabled(true);
						}
					}
				}
			});

			JButton botonNivel3 = new JButton("Nivel 3");
			botonNivel3.setEnabled(true);
			botonNivel3.setAlignmentX(Component.CENTER_ALIGNMENT);
			botonesNiveles.add(botonNivel3);
			botonNivel3.addActionListener(e -> {
				preferencias.valorAtributo("nivelTest", 3);
				botonNivel3.setBackground(colorFondo);
			});
			botonNivel3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (Component btnsLevels : botonesNiveles.getComponents()) {
						if (btnsLevels instanceof JButton) {
							btnsLevels.setEnabled(false);
						}
					}
					for (Component btnsBrowsers : botonesBrowsers.getComponents()) {
						if (btnsBrowsers instanceof JButton) {
							btnsBrowsers.setEnabled(true);
						}
					}
				}
			});
			panelTests.add(botonesTests, BorderLayout.CENTER);

			/*
			 * JButton boton3 = new JButton("Transferencias Terceros");
			 * boton2.setEnabled(false); boton2.setAlignmentX(Component.CENTER_ALIGNMENT);
			 * botonesDerechos.add(boton3); boton2.addActionListener(e -> {
			 * TransferenciasTercerosExec.main(args); });
			 */

			JButton[] botonesAmbientesArray = new JButton[9];
			for (int i = 0; i < 9; i++) {
				final int j = i;

				if (i < 8) {
					JButton boton = new JButton("QA0" + (i + 1));
					boton.setEnabled(false);
					boton.setAlignmentX(Component.CENTER_ALIGNMENT);
					boton.addActionListener(e -> {
						try {
							boton.setEnabled(false);
							boton.setBackground(colorFondo);
							for (JButton botonIzquierdo : botonesAmbientesArray) {
								if (botonIzquierdo != boton) {
									botonIzquierdo.setEnabled(false);
								}
							}
							for (Component comp : botonesTests.getComponents()) {
								if (comp instanceof JButton) {
									comp.setEnabled(true);
								}
							}
							selectQA(j + 1);
						} catch (IOException | NoSuchFieldException | SecurityException | IllegalArgumentException
								| IllegalAccessException | InterruptedException e1) {
							e1.printStackTrace();
						}
					});
					boton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							for (JButton botonIzquierdo : botonesAmbientesArray) {
								if (botonIzquierdo != boton) {
									botonIzquierdo.setEnabled(false);
								}
							}
							for (Component comp : botonesTests.getComponents()) {
								if (comp instanceof JButton) {
									comp.setEnabled(true);
								}
							}
						}
					});

					botonesAmbientesArray[i] = boton;
					botonesAmbientes.add(boton);

				} else {
					JButton boton = new JButton("Produccion");
					boton.setEnabled(false);
					boton.setAlignmentX(Component.CENTER_ALIGNMENT);
					boton.addActionListener(e -> {
						try {
							boton.setEnabled(false);
							boton.setBackground(colorFondo);
							for (JButton botonIzquierdo : botonesAmbientesArray) {
								if (botonIzquierdo != boton) {
									botonIzquierdo.setEnabled(false);
								}
							}
							for (Component comp : botonesTests.getComponents()) {
								if (comp instanceof JButton) {
									comp.setEnabled(true);
								}
							}
							selectQA(j + 1);
						} catch (IOException | NoSuchFieldException | SecurityException | IllegalArgumentException
								| IllegalAccessException | InterruptedException e1) {
							e1.printStackTrace();
						}

					});

					botonesAmbientesArray[i] = boton;
					botonesAmbientes.add(boton);
				}
			}

			JButton botonChrome = new JButton("Chrome", iconoRedimensionado(iconoChrome));
			botonChrome.setEnabled(false);
			botonChrome.setAlignmentX(Component.CENTER_ALIGNMENT);
			botonesBrowsers.add(botonChrome);
			botonChrome.addActionListener(e -> {
				preferencias.valorAtributo("navegadorTipo", "1");
				preferencias.valorAtributo("navegadorNombre", "Chrome");
				botonChrome.setBackground(colorFondo);
			});
			botonChrome.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (Component btnsBrowsers : botonesBrowsers.getComponents()) {
						if (btnsBrowsers instanceof JButton) {
							btnsBrowsers.setEnabled(false);
						}
					}
					for (JButton btnsEnvironment : botonesAmbientesArray) {
						if (btnsEnvironment instanceof JButton) {
							btnsEnvironment.setEnabled(true);
						}
					}
				}
			});

			JButton botonFIrefox = new JButton("Firefox", iconoRedimensionado(iconoFirefox));
			botonFIrefox.setEnabled(false);
			botonFIrefox.setAlignmentX(Component.CENTER_ALIGNMENT);
			botonesBrowsers.add(botonFIrefox);
			botonFIrefox.addActionListener(e -> {
				preferencias.valorAtributo("navegadorTipo", "2");
				preferencias.valorAtributo("navegadorNombre", "Firefox");
				botonFIrefox.setBackground(colorFondo);
			});
			botonFIrefox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (Component btnsBrowsers : botonesBrowsers.getComponents()) {
						if (btnsBrowsers instanceof JButton) {
							btnsBrowsers.setEnabled(false);
						}
					}
					for (JButton btnsEnvironment : botonesAmbientesArray) {
						if (btnsEnvironment instanceof JButton) {
							btnsEnvironment.setEnabled(true);
						}
					}
				}
			});

			JButton botonEdge = new JButton("Edge", iconoRedimensionado(iconoEdge));
			botonEdge.setEnabled(false);
			botonEdge.setAlignmentX(Component.CENTER_ALIGNMENT);
			botonesBrowsers.add(botonEdge);
			botonEdge.addActionListener(e -> {
				preferencias.valorAtributo("navegadorTipo", "3");
				preferencias.valorAtributo("navegadorNombre", "Edge");
				botonEdge.setBackground(colorFondo);
			});
			botonEdge.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (Component btnsBrowsers : botonesBrowsers.getComponents()) {
						if (btnsBrowsers instanceof JButton) {
							btnsBrowsers.setEnabled(false);
						}
					}
					for (JButton btnsEnvironment : botonesAmbientesArray) {
						if (btnsEnvironment instanceof JButton) {
							btnsEnvironment.setEnabled(true);
						}
					}
				}
			});

			JButton botonSafari = new JButton("Safari", iconoRedimensionado(iconoSafari));
			botonSafari.setEnabled(false);
			botonSafari.setAlignmentX(Component.CENTER_ALIGNMENT);
			botonesBrowsers.add(botonSafari);
			botonSafari.addActionListener(e -> {
				preferencias.valorAtributo("navegadorTipo", "4");
				preferencias.valorAtributo("navegadorNombre", "Safari");
				botonSafari.setBackground(colorFondo);
			});
			botonSafari.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					for (Component btnsBrowsers : botonesBrowsers.getComponents()) {
						if (btnsBrowsers instanceof JButton) {
							btnsBrowsers.setEnabled(false);
						}
					}
					for (JButton btnsEnvironment : botonesAmbientesArray) {
						if (btnsEnvironment instanceof JButton) {
							btnsEnvironment.setEnabled(true);
						}
					}
				}
			});

			JButton boton1 = new JButton("Login");
			boton1.setEnabled(false);
			boton1.setAlignmentX(Component.CENTER_ALIGNMENT);
			botonesTests.add(boton1);
			boton1.addActionListener(e -> {
				Login.main(args);

			});

			JButton boton2 = new JButton("Transferencias Propias");
			boton2.setEnabled(false);
			boton2.setAlignmentX(Component.CENTER_ALIGNMENT);
			botonesTests.add(boton2);
			boton2.addActionListener(e -> {
				Transferencia_propia.main(args);

			});

			JPanel panelMensaje = new JPanel();
			panelMensaje.setLayout(new BoxLayout(panelMensaje, BoxLayout.Y_AXIS));

			obj.addPropertyChangeListener(evt -> {
				panelMensaje.setBackground(colorResultado);
				JLabel label = new JLabel(iconoRedimensionado2(iconoResultado));
				panelMensaje.setBorder(new LineBorder(Color.black));
				label.setHorizontalAlignment(JLabel.CENTER);
				panelMensaje.add(label);
				panelMensaje.setVisible(mostrarResultado);

				String texto = "";

				for (String elemento : arregloResultado) {
					texto += elemento + "<br>";
				}

				label.setText("<html>" + texto + "</html>");

				Border border = BorderFactory.createEmptyBorder(15, 5, 15, 25);
				label.setBorder(border);
				new LineBorder(Color.RED, 4);

				label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
				label.setAlignmentY(JLabel.CENTER_ALIGNMENT);

				Dimension size = label.getPreferredSize();
				label.setPreferredSize(size);
				label.setFont(new Font("Serif", Font.BOLD, 12));
				label.setForeground(Color.white);
				frame.pack();

			});

			c.insets = new Insets(5, 2, 2, 2);

			c.gridx = 0;
			c.gridy = 0;
			frame.add(panelNivel, c);
			c.gridx = 1;
			frame.add(panelBrowser, c);
			c.gridx = 2;
			frame.add(panelAmbientes, c);
			c.gridx = 3;
			frame.add(panelTests, c);
			c.gridy = 1;
			c.gridwidth = 4;
			c.gridx = 0;
			frame.add(panelMensaje, c);
			frame.getContentPane().setBackground(new Color(199, 238, 255));
			frame.pack();
			frame.setVisible(true);
		});
	}

	private static void selectQA(int valor) throws IOException, InterruptedException, NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {

		String temp = "";

		switch (valor) {
			case 1:
				temp = "QA01";
				break;
			case 2:
				temp = "QA02";
				break;
			case 3:
				temp = "QA03";
				break;
			case 4:
				temp = "QA04";
				break;
			case 5:
				temp = "QA05";
				break;
			case 6:
				temp = "QA06";
				break;
			case 7:
				temp = "QA07";
				break;
			case 8:
				temp = "QA08";
				break;
			case 9:
				temp = "Produccion";
				break;
			default:
				temp = "Produccion";
				break;
		}

		preferencias.valorAtributo("paginaWeb", Bi_helper.obtenerDato(temp, "url", AMBIENTE_JSON));
	}

	private static Icon iconoRedimensionado(ImageIcon icono) {
		Image imagenRedimensionada = icono.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		ImageIcon iconoRedimensionado = new ImageIcon(imagenRedimensionada);
		return iconoRedimensionado;
	}

	private static Icon iconoRedimensionado2(ImageIcon icono) {
		Image imagenRedimensionada = icono.getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH);
		ImageIcon iconoRedimensionado = new ImageIcon(imagenRedimensionada);
		return iconoRedimensionado;
	}

	public static void resultado(ArrayList<String> errores, Boolean ejecutadoExitosamente) {
		ArrayList<String> old = arregloResultado;
		mostrarResultado = true;
		if (ejecutadoExitosamente) {
			ArrayList<String> arreglo = new ArrayList<>();
			arreglo.add("Test ejecutado exitosamente");
			arregloResultado = arreglo;
			colorResultado = Color.green;
			iconoResultado = (ImageIcon) iconoRedimensionado2(iconoCorrecto);
		} else {
			arregloResultado = errores;
			colorResultado = Color.red;
			iconoResultado = (ImageIcon) iconoRedimensionado2(iconoFallo);
		}
		escucha.firePropertyChange("arregloResultado", old, errores);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		escucha.addPropertyChangeListener(listener);
	}

	@Override
	public void onListaMensajeChanged(ArrayList<String> nuevaListaMensaje) {
		for (String mensaje : nuevaListaMensaje) {
			if (mensaje.equals("Test ejecutado exitosamente") && nuevaListaMensaje.size() == 1) {
				resultado(nuevaListaMensaje, true);
			} else
				resultado(nuevaListaMensaje, false);
		}
	}
}
