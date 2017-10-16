package com.szachnowicz.impl;

import com.szachnowicz.enitiy.Order;
import com.szachnowicz.interfaceRmi.IClient;
import com.szachnowicz.interfaceRmi.IManager;
import com.szachnowicz.interfaceRmi.IRegistry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.Period;

public class ClientImpl extends UnicastRemoteObject implements IClient {
    private IManager registry;
    private int orderID;

    public ClientImpl(IManager registry) throws RemoteException {
        super();
        this.registry = registry;
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame();
        frame.setSize(200, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = frame.getContentPane();
        container.setLayout(new FlowLayout());

        JTextField adTimeInput = new JTextField();
        adTimeInput.setPreferredSize(new Dimension(150, 25));


        JTextField adTextInput = new JTextField();
        adTextInput.setPreferredSize(new Dimension(150, 25));
        JLabel textLabel = new JLabel(" AD name ");
        JLabel timeLabel = new JLabel(" AD time ");

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String addName = adTextInput.getText();
                Integer adTime = null;
                try {
                    adTime = Integer.valueOf(adTimeInput.getText());

                } catch (ClassCastException e1) {
                    System.out.println("Wprowadzony błedne dane");
                }
                if (addName == null || adTime == null) {
                    return;
                }
                Duration d = Duration.ofSeconds(adTime);
                try {
                    System.out.println(d.toNanos() + " " + adTime);
                    sendAdToManager(addName, d);

                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        container.add(textLabel);
        container.add(adTextInput);
        container.add(timeLabel);
        container.add(adTimeInput);
        container.add(okButton);
        frame.setVisible(true);
    }

    @Override
    public void setOrderId(int orderId) throws RemoteException {
        registry.unbindBillboard(orderId);
    }

    private void sendAdToManager(String addName, Duration adTime) throws RemoteException {
        Order order = new Order(addName, adTime, this);
        registry.placeOrder(order);
    }


}
