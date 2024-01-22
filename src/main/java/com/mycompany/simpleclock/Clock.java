package com.mycompany.simpleclock;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Clock extends JFrame implements ActionListener{
    
    private SimpleDateFormat timeFormat;
    private SimpleDateFormat dayFormat;
    private SimpleDateFormat dateFormat;
    
    private JLabel timeLabel;
    private JLabel dayLabel;
    private JLabel dateLabel;
    private static JLabel alarmLabel;
    
    private String time;
    private String day;
    private String date;
    
    private JPanel panel;
    private static JButton alarmButton;

    public Clock() {
        
        this.setTitle("Simple Clock - Manila Time");
        this.setSize(350, 230);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        
        timeFormat = new SimpleDateFormat("hh:mm:ss a");
        
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Courier New", Font.PLAIN, 50));
        timeLabel.setForeground(Color.GREEN);
        timeLabel.setBackground(Color.BLACK);
        timeLabel.setOpaque(true);

        dayFormat = new SimpleDateFormat("EEEE");
        
        dayLabel = new JLabel();
        dayLabel.setFont(new Font("Verndana", Font.PLAIN, 35));
        
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        
        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Verdana", Font.PLAIN, 25));
        
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(350, 35));
        panel.setLayout(new FlowLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        
        alarmButton = new JButton("Set an Alarm");
        alarmButton.setPreferredSize(new Dimension(120, 25));
        alarmButton.setFocusable(false);
        alarmButton.addActionListener(this);
        panel.add(alarmButton);
        
        alarmLabel = new JLabel();
        alarmLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        panel.add(alarmLabel);
        
        this.add(dayLabel);
        this.add(timeLabel);
        this.add(dateLabel);
        this.add(panel);
        this.setVisible(true);
        
        setTime();
    }
    
    private void setTime() {
        while (true) {
            time = timeFormat.format(Calendar.getInstance().getTime());
            timeLabel.setText(time);
            
            day = dayFormat.format(Calendar.getInstance().getTime());
            dayLabel.setText(day);
            
            date = dateFormat.format(Calendar.getInstance().getTime());
            dateLabel.setText(date);
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == alarmButton) {
            AlarmClock alarmClock = new AlarmClock();
        }
    }
    
    public static void setAlarmLabel(String text) {
        alarmLabel.setText(text);
    }
    
    public static void enableAlarmButton() {
        alarmButton.setEnabled(true);
    }
    
    public static void disableAlarmButton() {
        alarmButton.setEnabled(false);
    }
}
