/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urirfid.views;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import urirfid.models.Location;
import javazoom.jl.player.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.Executors;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.rtf.RTFEditorKit;
import javazoom.jl.decoder.JavaLayerException;

/**
 *
 * @author Enayet
 */
public class LocationDetailView extends javax.swing.JPanel {

    /**
     * Creates new form LocationDetailView
     */
    public LocationDetailView() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPaneDetailText = new javax.swing.JTextPane();
        jLabelImage = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(800, 1000));

        jTextPaneDetailText.setEditable(false);
        jScrollPane1.setViewportView(jTextPaneDetailText);

        jLabelImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelImage.setText("Image");

        jLabel1.setText("About");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                aboutMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelImage, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelImage, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(24, 24, 24))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void aboutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aboutMouseClicked
        // TODO add your handling code here:
//        JOptionPane optionPane = new JOptionPane("https://www.planetware.com/tourist-attractions/rhode-island-usri.htm", JOptionPane.INFORMATION_MESSAGE);
//            dialog = optionPane.createDialog("Information");
//            dialog.setAlwaysOnTop(true);
//            dialog.setVisible(true);
          String link = "";
          if (this.currentLocation.id.equals("012DFD0009EB0DC5")) {
              link = "https://en.wikipedia.org/wiki/The_Towers_(Narragansett,_Rhode_Island)";
          }
          else if(this.currentLocation.id.equals("012EFD0009EB0DCD")) {
              link = "https://www.flickr.com/photos/matt_hintsa/9752923796";
          }
          else if(this.currentLocation.id.equals("013CFD0009EB0DBC")) {
              link = "https://www.planetware.com/tourist-attractions/rhode-island-usri.htm";
          }
          else if(this.currentLocation.id.equals("0135FD0009EB0DB4")) {
              link = "https://en.wikipedia.org/wiki/The_Towers_(Narragansett,_Rhode_Island)";
          }
          else if(this.currentLocation.id.equals("0139FD0009EB0DC2")){
              link = "bigbluebug.com";
          }
          else if(this.currentLocation.id.equals("no_tag")){
              link = "None";
          }
          JOptionPane.showMessageDialog(null, link, 
                  "Picture taken from:", JOptionPane.INFORMATION_MESSAGE);
            
    }//GEN-LAST:event_aboutMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelImage;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPaneDetailText;
    // End of variables declaration//GEN-END:variables

    public Location currentLocation;
    public Player playMP3;
    private JDialog dialog;

    public void updateCurrentLocation(Location l){
        this.currentLocation = l;
        //jTextPaneDetailText.setText(this.currentLocation.description);
        jTextPaneDetailText.setText("");
        readDesctiptionRFT(this.currentLocation.descriptionLocation);
        Toolkit toolkit=Toolkit.getDefaultToolkit();  
        Image img = toolkit.getImage(l.picLocation);
        img = img.getScaledInstance(jLabelImage.getWidth(), jLabelImage.getHeight(),  java.awt.Image.SCALE_SMOOTH);
        jLabelImage.setIcon(new ImageIcon(img));
        jLabelImage.setText("");
        this.invalidate();
        this.repaint();
        
        if (playMP3 != null) {
            playMP3.close();
        }
//        playAudio(this.currentLocation.audioLocation);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        playAudio(currentLocation.audioLocation);
                    }
        });
    }
    
    public void playAudio(String audioLocation){
        try{
             FileInputStream fis = new FileInputStream(audioLocation);
             playMP3 = new Player(fis);
             playMP3.play();
        }  catch(FileNotFoundException | JavaLayerException e){
             System.out.println(e);
           }
    }
    
    public void readDesctiptionRFT(String rftLocation){
        // Create an RTF editor window
        RTFEditorKit rtf = new RTFEditorKit();
        // Load an RTF file into the editor
        try {
            FileInputStream fi = new FileInputStream(rftLocation);
            rtf.read(fi, jTextPaneDetailText.getDocument(), 0);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("I/O error");
        } catch (BadLocationException e) {
            Logger.getLogger(LocationDetailView.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}