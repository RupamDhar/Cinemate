package com.example.apicall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cinemate 
{
    public static void main(String[] args)
    {
        System.out.print("\033[H\033[2J");
        System.out.println("Program running...");

        //instance of software interface
        new Cinemate();
    }

    Cinemate()
    {
        //initializing JFrame
        JFrame frame = new JFrame("Cinemate");
        frame.setVisible(true);
        frame.setSize(500, 500);  
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBackground(Color.WHITE);
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
        JLabel movieDetails = new JLabel("OUTPUT HERE");
        movieDetails.setHorizontalAlignment(SwingConstants.CENTER);
        movieDetails.setSize(200, 200);
        frame.add(movieDetails, BorderLayout.CENTER);

        //fetching results on clicking "SEARCH"
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movieDetails.setText("");
                //sending http request through constructor
                CinemateRequest sAPIobj = new CinemateRequest(nameTextField.getText());
                movieDetails.setText("<html>"+sAPIobj.getMovieDetails().toString()+"</html>");
            }
        });
    }
}
