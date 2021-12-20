package ui;

import capture.JNetPcapHandler;
import capture.NetworkInterface;

import sysutil.SystemController;

import java.util.ArrayList;
import java.util.Scanner;

public class TempUI {
    public void startUI() {
        Scanner input = new Scanner(System.in);

        boolean b = true;
        while (b) {
            System.out.println("Available actions: ");
            System.out.println("0) EXIT");
            System.out.println("1) Start WinPcap");
            System.out.println("2) Stop WinPcap");
            System.out.println("3) Get available interfaces");
            System.out.print("Your choice: ");

            int choice = input.nextInt();

            switch (choice) {
            case 0:
                b = false;
                break;

            case 1:
                SystemController.startWin();
                break;

            case 2:
                SystemController.stopWin();

            case 3:
                capture.PacketCapturer pc = new JNetPcapHandler();
                ArrayList<NetworkInterface> nis = pc.getNetworkInterfaces();

                for (capture.NetworkInterface ni : nis) {
                    System.out.println("Name: " + ni.getName());
                    System.out.println("Description: " + ni.getDescription());
                    System.out.println("----");
                }
                break;

            default:
                System.out.println("Invalid option!\n");
            }
        }
    }
}
