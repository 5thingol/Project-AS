package as.project.presentation;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;



public class View extends JFrame {


	private JPanel contentPane;
	private JTextField textField = new JTextField();
	
	Date date = new Date();
	SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
	private JSpinner horaIni = new JSpinner(sm);
	JSpinner.DateEditor de = new JSpinner.DateEditor(horaIni, "HH");
	
	SpinnerDateModel sm2 = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
	private JSpinner horaFi = new JSpinner(sm2);
	JSpinner.DateEditor de2 = new JSpinner.DateEditor(horaFi, "HH");
	private JTextField textField_1;
	private JTextField coment;
	private JTextField username;
	private JTextField textField_2;
	JComboBox recursos;
	JList textArea;
	
	
	String[] values = null;

	CtrlCrearReservaAmbNotificacio ctrl;

	/**
	 * Create the frame.
	 */
	public View(CtrlCrearReservaAmbNotificacio ctrl) {
		this.ctrl = ctrl;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		setVisible(true);
		
		initPanel0();
		
		//initPanel1();
		
		//initPanel2();
		
		//initPanel3();
		
	}
	
	private void initPanel0(){
		UtilDateModel model = new UtilDateModel();
		//model.setDate(20,04,2014);
		// Need this...
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		// Don't know about the formatter, but there it is...
		final JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		JPanel panel = new JPanel();
		contentPane.add(panel, "name_7191221287232");
		panel.setLayout(null);
		
		
		
		JButton btnNext = new JButton("Accept");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date today = new Date();
				Date selectedDate = (Date) datePicker.getModel().getValue();
				int hI = Integer.parseInt(de.getTextField().getText());
				int hF = Integer.parseInt(de2.getTextField().getText());
				ctrl.PrAcceptObteRecursosDisponibles(selectedDate, hI, hF);
			}
		});
		btnNext.setBounds(485, 285, 89, 23);
		panel.add(btnNext);
		
		JLabel Data = new JLabel("Data: ");
		Data.setBounds(73, 110, 62, 14);
		panel.add(Data);
		
		JLabel lblNewLabel_1 = new JLabel("Hora inici: ");
		lblNewLabel_1.setBounds(73, 145, 62, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblHoraFi = new JLabel("Hora fi: ");
		lblHoraFi.setBounds(73, 170, 62, 14);
		panel.add(lblHoraFi);
		

		datePicker.setBounds(145, 101, 119, 30);
		panel.add(datePicker);
		

		
		horaIni.setEditor(de);
		horaIni.setBounds(145, 142, 45, 20);
		panel.add(horaIni);
		
		
		horaFi.setEditor(de2);
		horaFi.setBounds(145, 167, 45, 20);
		panel.add(horaFi);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(10, 285, 89, 23);
		panel.add(btnCancel);
		textField.setEditable(false);
		
		textField.setBounds(10, 318, 564, 33);
		panel.add(textField);
		textField.setColumns(10);
	}

	private void initPanel1(){
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, "name_7192469542491");
		panel_1.setLayout(null);
		
		JButton btnCancel_1 = new JButton("Cancel");
		btnCancel_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel_1.setBounds(10, 282, 89, 23);
		panel_1.add(btnCancel_1);
		
		JButton btnAccept = new JButton("Accept");
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nomR = (String)recursos.getSelectedItem();
				String user = username.getText();
				String comentari = coment.getText();
				ctrl.PrAcceptCreaReservaAmbNotificacio(nomR, user, comentari);
			}
		});
		btnAccept.setBounds(485, 282, 89, 23);
		panel_1.add(btnAccept);
		
		JLabel lblRecursosDisponibles = new JLabel("Recursos disponibles: ");
		lblRecursosDisponibles.setBounds(105, 95, 106, 14);
		panel_1.add(lblRecursosDisponibles);
		
		JLabel lblNomDusuari = new JLabel("Nom d'usuari: ");
		lblNomDusuari.setBounds(105, 120, 106, 14);
		panel_1.add(lblNomDusuari);
		
		JLabel lblComentari = new JLabel("Comentari: ");
		lblComentari.setBounds(105, 145, 74, 14);
		panel_1.add(lblComentari);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(10, 316, 564, 35);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		
		recursos = new JComboBox(values);
		recursos.setToolTipText("R. Disp.");
		recursos.setBounds(221, 92, 135, 20);
		panel_1.add(recursos);
		
		coment = new JTextField();
		coment.setBounds(221, 142, 135, 61);
		panel_1.add(coment);
		coment.setColumns(10);
		
		username = new JTextField();
		username.setBounds(221, 117, 135, 20);
		panel_1.add(username);
		username.setColumns(10);
	}

	private void initPanel2(){
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, "name_7193798618213");
		panel_2.setLayout(null);
		
		JButton btnCancel_2 = new JButton("Cancel");
		btnCancel_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel_2.setBounds(10, 283, 89, 23);
		panel_2.add(btnCancel_2);
		
		JButton btnAccept_1 = new JButton("Accept");
		btnAccept_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList <String> users = new ArrayList<String>();
			    int[] selectedIx = textArea.getSelectedIndices();
			    for (int i = 0; i < selectedIx.length; i++) {
			        users.add(textArea.getModel().getElementAt(selectedIx[i]).toString());
			    }
				ctrl.PrAcceptAssignarUsuarisAReserva(users);
			}
		});
		btnAccept_1.setBounds(485, 283, 89, 23);
		panel_2.add(btnAccept_1);
		
		JLabel lblUsuarisANotificar = new JLabel("Usuaris a notificar: ");
		lblUsuarisANotificar.setBounds(146, 71, 137, 14);
		panel_2.add(lblUsuarisANotificar);
		

		textArea.setBounds(146, 96, 272, 147);
		
	    JScrollPane scroll = new JScrollPane ( textArea );
	    scroll.setBounds(146, 96, 272, 147); 
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		panel_2.add(scroll);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBounds(10, 311, 564, 40);
		panel_2.add(textField_2);
		textField_2.setColumns(10);
	}
	
	private void initPanel3(){
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, "name_7195039116315");
		panel_3.setLayout(null);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnOk.setBounds(227, 229, 89, 23);
		panel_3.add(btnOk);
		
		JLabel lblLaReservaSha = new JLabel("La reserva s'ha realitzat correctament");
		lblLaReservaSha.setBounds(171, 126, 189, 37);
		panel_3.add(lblLaReservaSha);
	}
	
	public void continua(){
		CardLayout cardLayout = (CardLayout) contentPane.getLayout();
		cardLayout.next(contentPane);
	}
	
	public void mostraRecursos(String[] ll_recursos){
		values = ll_recursos;
		initPanel1();
	}
	
	public void mostraSeleccionaUsuarisPerNotificar(String[] usernames){
		textArea = new JList(usernames);
		initPanel2();
	}
	
	public void MostraMissatge (String msg, int panell){
		switch (panell){
		case 0: textField.setText(msg);
			break;
		case 1: textField_1.setText(msg);
			break;
		case 2: textField_2.setText(msg);
			break;
		}
		
	}
}
