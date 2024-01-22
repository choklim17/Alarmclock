package com.mycompany.simpleclock;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class AlarmClock extends JFrame implements ActionListener {
    
    private SpinnerModel hourModel;
    private SpinnerModel minuteModel;
    
    private JSpinner hourSpinner;
    private JSpinner minuteSpinner;
    private JComboBox timePeriodBox;
    private JButton setAlarmButton;
    
    private int hour;
    private int minute;
    private String timePeriod;
    
    public AlarmClock() {
        
        this.setTitle("Set an Alarm Clock");
        this.setSize(350, 200);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        

        JLabel hourLabel = new JLabel("Hour:");
        hourLabel.setBounds(40, 40, 60, 30);
        hourLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        
        JLabel minuteLabel = new JLabel("Minutes:");
        minuteLabel.setBounds(120, 40, 100, 30);
        minuteLabel.setFont(new Font("Verdana", Font.PLAIN, 20));

        JLabel dayLabel = new JLabel("AM/PM");
        dayLabel.setBounds(220, 40, 100, 30);
        dayLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        
        hourModel = new SpinnerNumberModel(6, 1, 12, 1);
        
        hourSpinner = new JSpinner(hourModel);
        hourSpinner.setEditor(new JSpinner.DefaultEditor(hourSpinner));
        hourSpinner.setBounds(40, 75, 50, 30);
        hourSpinner.setFont(new Font("Verdana", Font.PLAIN, 20));
        
        minuteModel = new SpinnerNumberModel(30, 0, 59, 1);
        
        JLabel label = new JLabel(":");
        label.setBounds(110, 75, 10, 30);
        label.setFont(new Font("Verdana", Font.PLAIN, 20));
        
        minuteSpinner = new JSpinner(minuteModel);
        minuteSpinner.setEditor(new JSpinner.DefaultEditor(minuteSpinner));
        minuteSpinner.setBounds(140, 75, 50, 30);
        minuteSpinner.setFont(new Font("Verdana", Font.PLAIN, 20));
        
        String[] choices = {"AM", "PM"};
        timePeriodBox = new JComboBox(choices);
        timePeriodBox.setBounds(220, 75, 60, 30);
        timePeriodBox.setFont(new Font("Verdana", Font.PLAIN, 20));
        timePeriodBox.setFocusable(false);
        
        setAlarmButton = new JButton("Set Alarm");
        setAlarmButton.setBounds(115, 120, 120, 25);
        setAlarmButton.setFocusable(false);
        setAlarmButton.addActionListener(this);
        
        this.add(hourLabel);
        this.add(minuteLabel);
        this.add(dayLabel);
        this.add(hourSpinner);
        this.add(label);
        this.add(minuteSpinner);
        this.add(timePeriodBox);
        this.add(setAlarmButton);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == setAlarmButton) {
            hour = (int) hourSpinner.getValue();
            minute = (int) minuteSpinner.getValue();
            timePeriod = (String) timePeriodBox.getSelectedItem();
            
            int convertedHour = hour;
            if (timePeriod.equals("PM") && hour < 12) {
                convertedHour = hour + 12;
            }
            else if(timePeriod.equals("AM") && hour == 12) {
                convertedHour = 0;
            }
            
            scheduleAlarm(convertedHour, minute);
            alarmInformation();
            Clock.disableAlarmButton();
            this.dispose();
        }
    }
    
    private void scheduleAlarm(int hour, int minute) {
        Timer timer = new Timer();
        
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    AlarmTriggered alarmTriggered = new AlarmTriggered();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    Logger.getLogger(AlarmClock.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        
        Calendar currentTime = Calendar.getInstance();
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.set(Calendar.HOUR_OF_DAY, hour);
        alarmTime.set(Calendar.MINUTE, minute);
        alarmTime.set(Calendar.SECOND, 0);
        alarmTime.set(Calendar.MILLISECOND, 0);
        
        if (alarmTime.before(currentTime)) {
            alarmTime.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        timer.schedule(timerTask, alarmTime.getTime());
    }
    
    
    private void alarmInformation() {
        String message = String.format("Alarm is set at %02d:%02d%s", hour, minute, timePeriod);
        
        if (hour == 0) {
            hour = 12;
        }
        
        JOptionPane.showMessageDialog(this, message, "Alarm Clock", JOptionPane.INFORMATION_MESSAGE);
        Clock.setAlarmLabel(message);
    }
}
