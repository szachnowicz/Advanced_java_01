package com.szachnowicz.impl;

import com.szachnowicz.interfaceRmi.IBillboard;
import com.szachnowicz.interfaceRmi.IRegistry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class BilboardImpl extends UnicastRemoteObject implements IBillboard {


    private final IRegistry registry;
    private JTextField textField;
    private JFrame frame;
    private boolean isBillboardON = false;

    public BilboardImpl(IRegistry registry) throws RemoteException {
        super();
        this.registry = registry;
    }

    public void createAndShowGUI() {
        isBillboardON = true;

        frame = new JFrame();
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = frame.getContentPane();
        container.setLayout(new FlowLayout());

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 200));
        textField.setEditable(false);

//        JLabel label = new JLabel("Input will appear here");

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                System.out.println("Input: " + input);
            }
        });
        container.add(textField);
        frame.setVisible(true);
    }

    @Override
    public boolean addAdvertisement(String advertText, Duration displayPeriod, int orderId) throws RemoteException {
        createAndShowGUI();
        textField.setText(advertText);
        closeGuiAfter(displayPeriod);
        return false;
    }

    private void closeGuiAfter(Duration displayPeriod) {

        final int nano = displayPeriod.getNano();
        System.out.println(nano);
        Timer timer = new Timer(nano, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                frame.dispose();
            }
        });
        timer.setRepeats(false); // Only execute once
        timer.start(); // Go go go!
    }

    @Override
    public boolean removeAdvertisement(int orderId) throws RemoteException {
        return false;
    }

    @Override
    public int[] getCapacity() throws RemoteException {
        return new int[0];
    }

    @Override
    public void setDisplayInterval(Duration displayInterval) throws RemoteException {

    }

    @Override
    public boolean start() throws RemoteException {
        return isBillboardON;
    }

    @Override
    public boolean stop() throws RemoteException {
        return isBillboardON;
    }


}
