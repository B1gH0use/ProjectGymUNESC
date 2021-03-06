package PackageGraphicWindows;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import PackageClassLibrary.Observer;
import PackageClassLibrary.createClientLib;
import PackageDatabase.dao.DAOclient;
import PackageDatabase.dao.DAOdegree;
import PackageDatabase.dao.DAOmodality;
import PackageDatabase.dao.DAOplans;
import PackageDatabase.model.modelClient;
import PackageDatabase.model.modelDegree;
import PackageDatabase.model.modelModality;
import PackageDatabase.model.modelPlans;

@SuppressWarnings("serial")
public class windowClient extends JInternalFrame implements Observer{
	
	private JTable tblGym = new JTable();
	private JScrollPane spGym = new JScrollPane();
	private DefaultTableModel tblmodelGym= new DefaultTableModel(){public boolean isCellEditable(int row, int col) {return false;}};
	
	List<Object> objModa;
	List<Object> objDegree;
	List<Object> objPlans;
	
	private DAOmodality daoModality;
	private DAOdegree daoDegree;
	private DAOplans daoPlans;
	private DAOclient daoClient;
	
	Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
	
	private createClientLib clientLib;
	
	String[] combBoxTextsSex = new String[] {"Selecione", "Feminino", "Masculino"}; 
	String[] combBoxTextsStates = new String[] 
			{"Selecione", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES",
					"GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE",
					"PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
	
	JLabel lblCodClient = new JLabel();
	JLabel lblFullName = new JLabel();
	JLabel lblCPF = new JLabel();
	JLabel lblRG = new JLabel();
	JLabel lblDateOfBirth = new JLabel();
	JLabel lblNumberPhone = new JLabel();
	JLabel lblNumberHouse = new JLabel();
	JLabel lblSex = new JLabel();
	JLabel lblStreet = new JLabel();
	JLabel lblCodHouse = new JLabel();
	JLabel lblZipCode = new JLabel();
	JLabel lblHouseNearWhat = new JLabel();
	JLabel lblneighborhood = new JLabel();
	JLabel lblCity = new JLabel();
	JLabel lblState = new JLabel();
	JLabel lblObservation = new JLabel();
	JLabel lblImage = new JLabel();
	JLabel lblModality = new JLabel();
	JLabel lblDegree = new JLabel();
	JLabel lblPlan = new JLabel();
	
	JTextField txfCodClient = new JTextField();
	JTextField txfFullName = new JTextField();
	JTextField txfRG = new JTextField();
	JTextField txfneighborhood = new JTextField();
	JTextField txfCity = new JTextField();
	JTextField txfState = new JTextField();
	JTextField txfStreet = new JTextField();
	JTextField txfCodHouse = new JTextField();
	JTextField txfZipCode = new JTextField();
	JTextField txfHouseNearWhat = new JTextField();
	
	JTextArea txfObservation = new JTextArea();	
	
	JFormattedTextField txfCPF = new JFormattedTextField();
	JFormattedTextField txfDateOfBirth = new JFormattedTextField();
	JFormattedTextField txfNumberPhone = new JFormattedTextField();
	JFormattedTextField txfNumberHouse = new JFormattedTextField();
	
	JButton btnAddImage = new JButton();
	JButton btnSave = new JButton();
	JButton btnConsultClient = new JButton();
	JButton btnCancel = new JButton();
	JButton btnOK = new JButton();
	JButton btnLessTbl = new JButton();
	
	JComboBox<String> cbSex = new JComboBox<>();
	JComboBox<String> cbState = new JComboBox<>();
	JComboBox<String> cbModality = new JComboBox<>();
	JComboBox<String> cbDegree = new JComboBox<>();
	JComboBox<String> cbPlan = new JComboBox<>();
	
	private Connection conn;
	JPanel panel = new JPanel();
	
	public windowClient (Connection conn) throws ParseException{ 

		this.conn = conn;
		
		try {
			daoModality = new DAOmodality(conn);	
			daoDegree = new DAOdegree(conn);
			daoPlans = new DAOplans(conn);
			daoClient = new DAOclient(conn);
		}catch (Exception e) {
			
		}
		
		setSize(900,600);
		setTitle("Clientes");
		setIconifiable(true);
		setMaximizable(false);
		setClosable(true);
		setResizable(false);
		setLocation(175, 20);
		
		buildWindow();
		actionsButtons();
		
		clientLib = new createClientLib(this);

	}
	
	public void buildWindow() throws ParseException{
		
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(panel);
		panel.setLayout(null);		
		
		//Table
		
		if (tblmodelGym.getColumnCount() == 0) {
			tblmodelGym.addColumn("Modalidade");
			tblmodelGym.addColumn("Gradua��o");
			tblmodelGym.addColumn("Planos");
		}
		
		tblGym = new JTable(tblmodelGym);
		spGym = new JScrollPane(tblGym);
		spGym.setBounds(450, 300, 383, 140);
		panel.add(spGym);
		tblGym.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//Label
		
		lblCodClient = new JLabel("C�digo");
		lblCodClient.setBounds(25, 25, 100, 25);
		lblCodClient.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(lblCodClient);
		
		lblFullName = new JLabel("Nome Completo");
		lblFullName.setBounds(150, 25, 100, 25);
		lblFullName.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(lblFullName);
		
		lblCPF = new JLabel("CPF");
		lblCPF.setBounds(480, 25, 50, 25);
		lblCPF.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(lblCPF);
		
		lblRG = new JLabel("RG");
		lblRG.setBounds(320, 220, 50, 25);
		lblRG.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(lblRG);
	
		lblDateOfBirth = new JLabel("Data de Nascimento");
		lblDateOfBirth.setBounds(25, 75, 150, 25);
		lblDateOfBirth.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(lblDateOfBirth);
		
		lblNumberPhone = new JLabel("N� do Celular");
		lblNumberPhone.setBounds(170, 75, 150, 25);
		lblNumberPhone.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(lblNumberPhone);
		
		lblNumberHouse = new JLabel("N� da Casa");
		lblNumberHouse.setBounds(320, 75, 200, 25);
		lblNumberHouse.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(lblNumberHouse);
		
		lblStreet = new JLabel("Rua");
		lblStreet.setBounds(25, 120, 150, 25);
		lblStreet.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(lblStreet);
		
		lblCodHouse = new JLabel("N� Resid�ncia");
		lblCodHouse.setBounds(400, 120, 150, 25);
		lblCodHouse.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(lblCodHouse);
		
		lblZipCode = new JLabel("CEP");
		lblZipCode.setBounds(25, 165, 100, 25);
		lblZipCode.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(lblZipCode);
		
		lblHouseNearWhat = new JLabel("Pr�ximo");
		lblHouseNearWhat.setBounds(150, 165, 100, 25);
		lblHouseNearWhat.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(lblHouseNearWhat);
		
		lblneighborhood = new JLabel("Bairro");
		lblneighborhood.setBounds(280, 165, 100, 25);
		lblneighborhood.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(lblneighborhood);
		
		lblCity = new JLabel("Cidade");
		lblCity.setBounds(25, 220, 100, 25);
		lblCity.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(lblCity);
		
		lblState = new JLabel("UF");
		lblState.setBounds(200, 220, 100, 25);
		lblState.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(lblState);
		
		lblSex = new JLabel("G�nero");
		lblSex.setBounds(450, 75, 100, 25);
		lblSex.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(lblSex);
		
		lblObservation = new JLabel("Observa��o");
		lblObservation.setBounds(25, 275, 100, 25);
		lblObservation.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(lblObservation);
		
		lblImage = new JLabel();
		lblImage.setBounds(710, 25, 150, 200);
		lblImage.setIcon(new ImageIcon("E:\\WorksSpaces\\ProjectGymUNESC\\ClassGUILibrary\\teste.jpg"));
		panel.add(lblImage);
		
		lblModality = new JLabel("Modalidade");
		lblModality.setBounds(530, 120, 100, 25);
		lblModality.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(lblModality);
		
		lblDegree = new JLabel("Gradua��o");
		lblDegree.setBounds(530, 165, 100, 25);
		lblDegree.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(lblDegree);
		
		lblPlan = new JLabel("Planos");
		lblPlan.setBounds(530, 210, 100, 25);
		lblPlan.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(lblPlan);
		
		//JTextFields
		
		txfCodClient = new JTextField();
		txfCodClient.setBounds(25, 50, 100, 20);
		txfCodClient.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(txfCodClient);
		
		txfFullName = new JTextField();
		txfFullName.setBounds(150, 50, 300, 20);
		txfFullName.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(txfFullName);
		
		txfCPF = new JFormattedTextField();
		txfCPF.setBounds(480, 50, 110, 20);
		txfCPF.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		MaskFormatter dateMaskCPF = new MaskFormatter("###.###.###-##");
		dateMaskCPF.install(txfCPF);
		panel.add(txfCPF);
		
		txfRG = new JTextField();
		txfRG.setBounds(320, 245, 100, 20);
		txfRG.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(txfRG);
	
		txfDateOfBirth = new JFormattedTextField();
		txfDateOfBirth.setBounds(25, 100, 80, 20);
		txfDateOfBirth.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		MaskFormatter dateMaskDateOfBirth = new MaskFormatter("##/##/####");
		dateMaskDateOfBirth.install(txfDateOfBirth);
		panel.add(txfDateOfBirth);
		
		txfNumberPhone = new JFormattedTextField();
		txfNumberPhone.setBounds(170, 100, 120, 20);
		txfNumberPhone.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		MaskFormatter dateMaskNumberPhone = new MaskFormatter("(##) # ####-####");
		dateMaskNumberPhone.install(txfNumberPhone);
		panel.add(txfNumberPhone);
		
		txfNumberHouse = new JFormattedTextField();
		txfNumberHouse.setBounds(320, 100, 110, 20);
		txfNumberHouse.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		MaskFormatter dateMaskNumberHouse = new MaskFormatter("(##) ####-####");
		dateMaskNumberHouse.install(txfNumberHouse);
		panel.add(txfNumberHouse);

		txfStreet = new JTextField();
		txfStreet.setBounds(25, 145, 350, 20);
		txfStreet.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(txfStreet);
		
		txfCodHouse = new JTextField();
		txfCodHouse.setBounds(400, 145, 90, 20);
		txfCodHouse.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(txfCodHouse);
		
		txfZipCode = new JTextField();
		txfZipCode.setBounds(25, 190, 100, 20);
		txfZipCode.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(txfZipCode);
		
		txfHouseNearWhat = new JTextField();
		txfHouseNearWhat.setBounds(150, 190, 100, 20);
		txfHouseNearWhat.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(txfHouseNearWhat);
		
		txfneighborhood = new JTextField();
		txfneighborhood.setBounds(280, 190, 210, 20);
		txfneighborhood.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(txfneighborhood);
		
		txfCity = new JTextField();
		txfCity.setBounds(25, 245, 150, 20);
		txfCity.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(txfCity);
		
		txfObservation = new JTextArea();
		txfObservation.setBounds(25, 300, 275, 100);
		txfObservation.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		txfObservation.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		panel.add(txfObservation);
		
		//ComboBox
		
		cbSex = new JComboBox<String>(combBoxTextsSex);
		cbSex.setBounds(450, 100, 110, 20);
		cbSex.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(cbSex);
		
		cbState = new JComboBox<String>(combBoxTextsStates);
		cbState.setBounds(200, 245, 100, 20);
		cbState.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(cbState);

		cbModality = new JComboBox<String>(loadDataAllModalitys());
		cbModality.setBounds(530, 145, 130, 20);
		cbModality.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(cbModality);
		
		cbDegree = new JComboBox<String>(loadDataAllDegree());
		cbDegree.setBounds(530, 190, 130, 20);
		cbDegree.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(cbDegree);
		
		cbPlan = new JComboBox<String>(loadDataAllPans());
		cbPlan.setBounds(530, 235, 130, 20);
		cbPlan.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(cbPlan);
		
		//Buttons
		
		btnAddImage = new JButton("+");
		btnAddImage.setBounds(810, 235, 50, 25);
		btnAddImage.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		panel.add(btnAddImage);
		
		btnConsultClient = new JButton("Consultar");
		btnConsultClient.setBounds(275, 480, 90, 65);
		btnConsultClient.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
		btnConsultClient.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnConsultClient.setHorizontalTextPosition(SwingConstants.CENTER);
		panel.add(btnConsultClient);
		
		btnSave = new JButton("Salvar");
		btnSave.setBounds(150, 480, 90, 65);
		btnSave.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
		btnSave.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnSave.setHorizontalTextPosition(SwingConstants.CENTER);
		panel.add(btnSave);

		btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(25, 480, 90, 65);
		btnCancel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
		panel.add(btnCancel);
		
	//	btnLessTbl

		btnLessTbl = new JButton("-");
		btnLessTbl.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		btnLessTbl.setBounds(530, 260, 50	, 25);
		panel.add(btnLessTbl);
		
		btnOK = new JButton("OK");
		btnOK.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		btnOK.setBounds(530, 260, 130, 25);
		panel.add(btnOK);

	}
	
	public void actionsButtons() {

		btnAddImage.addActionListener(e->{
			clientLib.chooseImageForPerson(lblImage);
		});
		
		btnConsultClient.addActionListener(e->{
			new windowConsultGeneric(daoClient, new String[] {"ID Cliente", "Nome"}, windowClient.this).setVisible(true);
		});
		
		btnSave.addActionListener(e->{
			clientLib.validations(cbSex);
			String combBoxTextModality = (String)cbModality.getSelectedItem();
			try {
				
				modelClient client = new modelClient();
				
				client.setName(txfFullName.getText());
				client.setCpf(txfCPF.getText());
				client.setDateBorn(txfDateOfBirth.getText());
				client.setSex(combBoxTextModality);
				client.setRg(txfRG.getText());
				client.setNote(txfObservation.getText());
				client.setStreet(txfStreet.getText());
				client.setNumberHouse(txfNumberHouse.getText());
				client.setCep(txfZipCode.getText());
				client.setNear(txfHouseNearWhat.getText());
				client.setNeighborhood(txfneighborhood.getText());
				client.setCity(txfCity.getText());
				client.setState(txfState.getText());
		    	client.setModalityClient((String) tblmodelGym.getValueAt(0, 0));
		    	client.setDegreClient((String) tblmodelGym.getValueAt(0, 1));
		    	client.setPlanClient((String) tblmodelGym.getValueAt(0, 2));	 
				client.setNumberPhone(txfNumberPhone.getText());
				client.setNumberPhoneHouse(txfNumberHouse.getText());
		    	
		    	
		    	DAOclient dao = new DAOclient(conn);
				dao.Insert(client);
				
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "Erro: " + e1.getMessage(), "Erro abrir Insert Degree", JOptionPane.ERROR_MESSAGE);
			}
			JOptionPane.showMessageDialog(null, "Sucesso ao Gravar o Cliente", "Sucesso ao Gravar", JOptionPane.INFORMATION_MESSAGE);
		});

		btnCancel.addActionListener(e->{
			dispose();
		});
		
		btnOK.addActionListener(e->{
			String combBoxTextModality = (String)cbModality.getSelectedItem();
			String combBoxTextDegree = (String)cbDegree.getSelectedItem();
			String combBoxTextplan = (String)cbPlan.getSelectedItem();
			
			tblmodelGym.addRow(new String[] {combBoxTextModality, combBoxTextDegree, combBoxTextplan});
			
		});
		
	}
	
	private Vector<String> loadDataAllDegree() {
		
		Vector<String> vcDegree = new Vector<String>();
		
		try {
			objDegree = daoDegree.SelectWithOutParameter();	
			
			for(Object ob : objDegree) {
				modelDegree degree = (modelDegree) ob;
				vcDegree.add(degree.getDegree());
			}
		}catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Erro Carregar dados na table: " + e1.getMessage());
		}
		return vcDegree;
	}
	
	private Vector<String> loadDataAllPans() {
		
		Vector<String> vcPlans = new Vector<String>();
		
		try {
			objPlans = daoPlans.SelectWithOutParameter();	
			
			for(Object ob : objPlans) {
				modelPlans Plans = (modelPlans) ob;
				vcPlans.add(Plans.getPlans());
			}
		}catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Erro Carregar dados na table: " + e1.getMessage());
		}
		return vcPlans;
	}
	
	private Vector<String> loadDataAllModalitys() {
		
		Vector<String> vcModality = new Vector<String>();
		
		try {
			objModa = daoModality.SelectWithOutParameter();	
			
			for(Object ob : objModa) {
				modelModality modality = (modelModality) ob;
				vcModality.add(modality.getModality());
			}
		}catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Erro Carregar dados na table: " + e1.getMessage());
		}
		return vcModality;
	}	
	
	@Override
	public void Update(Object obj) {
		
		Vector<String> vec = new Vector<String>();
		
		modelClient client = (modelClient) obj;
		
		String strCodClient = String.valueOf(client.getIdClient());
		
		txfCodClient.setText(strCodClient);
		txfFullName.setText(client.getName());
		txfRG.setText(client.getRg());
		txfneighborhood.setText(client.getNeighborhood());
		txfCity.setText(client.getCity());
		txfState.setText(client.getState());
		txfStreet.setText(client.getStreet());
		txfCodHouse.setText(client.getNumberHouse());
		txfZipCode.setText(client.getCep());
		txfHouseNearWhat.setText(client.getNear());
		txfCPF.setText(client.getCpf());
		txfDateOfBirth.setText(client.getDateBorn());
		txfNumberPhone.setText(client.getNumberPhone());
		txfNumberHouse.setText(client.getNumberPhoneHouse());
		txfObservation.setText(client.getNote());
		cbSex.setSelectedItem(client.getSex());
		cbState.setSelectedItem(client.getState());
		
		vec.add(client.getModalityClient());
		vec.add(client.getDegreClient());
		vec.add(client.getPlanClient());
		tblmodelGym.addRow(vec);
		
		
	}
}