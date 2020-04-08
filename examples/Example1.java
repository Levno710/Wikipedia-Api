package examples;

import eu.oelschner.wikipedia.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Example1 extends JFrame {
	private static final long serialVersionUID = 5528693278352881460L;

	private JPanel contentPane;
	private JTextField textField;
	private JTextPane textPane;

	private Wikipedia w = new Wikipedia(Wikipedia.LANG_EN);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Example1 frame = new Example1();
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
	public Example1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 667, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					WikipediaArticle[] articles = w.query(textField.getText());
					if (articles.length <= 0) {
						textPane.setText("<h1>Article not Found!</h1>");
					} else {
						textPane.setText(articles[0].getText(Wikipedia.TYPE_HTML));
					}
				} catch (IOException | InterruptedException e1) {
					textPane.setText("<h1>Error While Loading Article!</h1>");
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnSearch, BorderLayout.EAST);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		textPane = new JTextPane();

		javax.swing.text.html.HTMLEditorKit eKit = new javax.swing.text.html.HTMLEditorKit();
		textPane.setEditorKit(eKit);

		textPane.setText("<h1>Article not Found!</h1>");

		scrollPane.setViewportView(textPane);
	}

}
