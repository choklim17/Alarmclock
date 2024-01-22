package com.mycompany.simpleclock;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class AlarmTriggered extends JDialog implements ActionListener, LineListener {

    private JLabel dialogLabel;
    private JButton stopButton;
    
    private File file;
    private Clip clip;
    
    public AlarmTriggered() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        
        triggerSound();
        
        this.setTitle("Alarm Clock");
        this.setSize(300, 110);
        this.setLayout(new FlowLayout());
        this.setModal(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        dialogLabel = new JLabel("Alarm Triggered!");
        dialogLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        
        stopButton = new JButton("Stop");
        stopButton.setPreferredSize(new Dimension(100, 25));
        stopButton.setFocusable(false);
        stopButton.addActionListener(this);
        
        this.add(dialogLabel);
        this.add(stopButton);
        this.setVisible(true);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == stopButton) {
            clip.close();
            this.dispose();
            Clock.enableAlarmButton();
            Clock.setAlarmLabel("");
            
        }
    }
    
    private void triggerSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        
            file = new File("Alarm_sound.wav");
        
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.addLineListener(this);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
    }

    @Override
    public void update(LineEvent event) {
        
        if (event.getType() == LineEvent.Type.STOP) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
}
