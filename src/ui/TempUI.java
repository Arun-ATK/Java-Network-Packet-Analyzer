package ui;

import sysutil.SystemController;
import capture.CaptureController;
import capture.NetworkInterface;

import java.util.ArrayList;
import java.util.Scanner;

public class TempUI {
    public void startUI() {
        Scanner input = new Scanner(System.in);

        ArrayList<NetworkInterface> nis;

        boolean b = true;
        while (b) {
            System.out.println("Available actions: ");
            System.out.println("0) EXIT");
            System.out.println("1) Start WinPcap");
            System.out.println("2) Stop WinPcap");
            System.out.println("3) Get available interfaces");
            System.out.println("4) Choose interface");
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
                nis = CaptureController.getInterfaces();

                for (capture.NetworkInterface ni : nis) {
                    System.out.println("Name: " + ni.getName());
                    System.out.println("Description: " + ni.getDescription());
                    System.out.println("----");
                }
                break;

            case 4:
                System.out.print("Enter interface number: ");
                CaptureController.startCapture(input.nextInt());

            default:
                System.out.println("Invalid option!\n");
            }
        }
    }
}
