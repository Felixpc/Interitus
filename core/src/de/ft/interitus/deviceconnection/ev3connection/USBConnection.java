/*
 * Copyright (c) 2020.
 * Copyright by Tim and Felix
 */

package de.ft.interitus.deviceconnection.ev3connection;

import de.ft.interitus.deviceconnection.ev3connection.usb.USBConnectionHandle;
import de.ft.interitus.deviceconnection.ev3connection.usb.USBDevice;
import de.ft.interitus.utils.ArrayList;

/**
 * Only for testing purpose
 */
public class USBConnection {


    public static void main(String[] args) {





USBConnectionHandle usbConnectionHandle = new USBConnectionHandle();



        ArrayList<Byte> command = new ArrayList<>();

/*
        for(int i=0;i<128;i++) {
            command.clear();


            command.addAll(Operations.ev3statusline(false));
            command.addAll(Operations.fillwindow(false, 1, 1));
            command.addAll(Operations.fillwindow(true, 128, 2));

            System.out.println(i);


            try {
                TimeUnit.MILLISECONDS.sleep(75);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

 */


       // command.clear();
        // command.addAll(Operations.playTone(100, 600, 100));

//command.addAll(Operations.blockbackbutton(true));
        //command.addAll(Operations.updateev3screen());

        //command.addAll(Operations.stopProgramm(0));
        // command.addAll(Operations.updateev3screen());
        //   while(true) {

        //command.addAll(Operations.setbrickname("Tim"));
        //command.addAll(Operations.setbrickname("Tim"));
//command.addAll(Operations.setbrickname("Hallo"));

       // command.addAll(Operations.setbrickname("Hallo"));
        USBDevice device = new USBDevice(USBConnectionHandle.hidServices.getHidDevice(ev3.ID_VENDOR_LEGO, ev3.ID_PRODUCT_EV3, null),usbConnectionHandle,"");

        System.out.println(device.getDevice().getPath());


      // command.addAll(Operations.fillwindow(true,0,40));
      // command.addAll(Operations.updateev3screen());
        command.addAll(Operations.ev3statusline(false));
        command.addAll(Operations.updateev3screen());
      //  command.addAll(Operations.playSound("./ui/DownloadSucces",100,false));

        Byte[] returnbytes = usbConnectionHandle.sendData(ev3.makeDirectCmd(command,4,0),device);

ev3.printHex("recv",returnbytes);


        //  command.addAll(Operations.loadProgrammFiles(4,"../prjs/newUI/test.rbf",0,4));
        // command.addAll(Operations.startProgramm(4,0,4,false));

        //  ev3.sendcommand(command, 10, 10);
        // command.clear();
        // try {
        //      TimeUnit.MILLISECONDS.sleep(7000);
        //  } catch (InterruptedException e) {
        ///       e.printStackTrace();
        ///   }
        ///   command.addAll(Operations.stopProgramm(0));
        //   ev3.sendcommand(command, 10, 10);
        // command.clear();
        // }

        // command.addAll(Operations.setbrickname("MyEv3"));
        device.getDevice().close();
        USBConnectionHandle.hidServices.shutdown();




    }


}
