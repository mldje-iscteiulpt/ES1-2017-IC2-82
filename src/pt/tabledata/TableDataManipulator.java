package pt.tabledata;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import pt.antiSpamFilterGUI.AntiSpamFilterMenu;
import pt.objects.Rule;
import pt.reader.DataReader;

/**
 * Classe responsável por manipular os dados da tabela, quer reiniciando os valores das regras com o valor 0.0 ou gravando-os em ficheiro. 
 * @author ES1-2017-IC2-82
 *
 */

public class TableDataManipulator {
	
	/** Tabela onde são apresentadas as regras e respetivos pesos */
	private JTable table;
	/** DefaultTableModel onde são inseridas, editadas ou removidas as regras e respetivos pesos */
	private DefaultTableModel model;
	
	/**
	 * Construtor da classe.
	 * @param table - JTable que representará as regras e respetivos pesos após inserção do DefaultTableModel
	 * @param model - DefaultTableModel da tabela que irá conter as colunas e os respetivos dados com regras e pesos
	 */
	
	public TableDataManipulator(JTable table, DefaultTableModel model) {
		this.table = table;
		this.model = model;
	}

	/**
	 * Reinicializar os pesos das regras com o valor 0.0
	 */

	public void resetValues() {
		for (int i = 0; i != model.getRowCount(); i++) {
			model.setValueAt(0.0, i, 1);
		}
		table.setModel(model);
		model.fireTableDataChanged();
	}
	
	/**
	 * Escreve os pesos da configuração junto das respetivas regras.
	 * @param filePathRules - String que indica a path para o ficheiro que contém as regras
	 * @param rules - Map<String,Double> onde são inseridas as regras e respetivos pesos
	 */
	
	public void writeRulesWeights(String filePathRules, Map<String,Double> rules) {
		try {
			double a;
			PrintWriter writer = new PrintWriter(new FileWriter(filePathRules));
//			BufferedWriter writer = new BufferedWriter(new FileWriter(filePathRules));
			for (int i = 0; i != model.getRowCount(); i++) {
				if (table.getValueAt(i, 1) instanceof String) {
					a = Double.parseDouble((String) table.getValueAt(i, 1));
				} else {
					a = (double) table.getValueAt(i, 1);
				}

				String rule = (String) table.getValueAt(i, 0);

				//AntiSpamFilterMenu.getDatareader().getRulesList().add(new Rules(rule, a));
				//dataReader.addToRules(rule, a);
				rules.put(rule, a);
				writer.println(rule + " " + a);
				//System.out.println("In my tree: " + rule + " " + rules.get(rule));
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
