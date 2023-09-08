package com.example.apicall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cinemate 
{
    //the GUI frame
    public static JFrame frame;

    public static void main(String[] args)
    {
        System.out.print("\033[H\033[2J");
        System.out.println("Program running...");

        //instance of software interface
        new Cinemate();
        frame.setVisible(true);
    }

    Cinemate()
    {
        //initializing JFrame
        frame = new JFrame("Cinemate");
        
        frame.setSize(380, 600);  
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);
        frame.setLayout(new BorderLayout());

        //creating input parameters and button
        JLabel nameLabel = new JLabel("NAME:");
        JTextField nameTextField = new JTextField();
        nameTextField.setColumns(20);
        JButton searchButton = new JButton("SEARCH");

        //adding input related components to JPanel
        JPanel jInputPanel = new JPanel(new FlowLayout());
        jInputPanel.add(nameLabel);
        jInputPanel.add(nameTextField);
        jInputPanel.add(searchButton);
        frame.add(jInputPanel, BorderLayout.NORTH);

        
        //setting output
        JTextPane outputPane = new JTextPane();
        outputPane.setEditable(false);
        outputPane.setBackground(Color.BLACK);
        outputPane.setForeground(Color.WHITE);
        outputPane.setFont(new Font("Aptos", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(outputPane);
        scrollPane.setBorder(null);
        frame.add(scrollPane, BorderLayout.CENTER);

        //fetching and displaying results on clicking "SEARCH"
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.print("\033[H\033[2J");
                outputPane.setText("Fetching data...");
                //sending http request through constructor
                CinemateRequest reqObj = new CinemateRequest(nameTextField.getText().replace(" ", ""));
                outputPane.setText(reqObj.getMovieDetails().toString().replace("\"", ""));
                
            }
        });
    }
}
