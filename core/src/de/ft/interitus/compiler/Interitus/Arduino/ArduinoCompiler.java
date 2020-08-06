/*
 * Copyright (c) 2020.
 * Copyright by Tim and Felix
 */

package de.ft.interitus.compiler.Interitus.Arduino;

import de.ft.interitus.Block.Block;
import de.ft.interitus.Programm;
import de.ft.interitus.UI.Notification.Notification;
import de.ft.interitus.UI.Notification.NotificationManager;
import de.ft.interitus.UI.UI;
import de.ft.interitus.compiler.Compiler;
import de.ft.interitus.datamanager.programmdata.Data;
import de.ft.interitus.loading.AssetLoader;
import de.ft.interitus.projecttypes.BlockTypes.Interitus.Arduino.ArduinoBlock;
import de.ft.interitus.projecttypes.ProjectManager;
import de.ft.interitus.utils.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.management.remote.NotificationResult;
import java.io.*;
import java.lang.reflect.Array;
import java.util.concurrent.*;

public class ArduinoCompiler implements Compiler {
    //TODO APPLE SUPPORT
    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static final ArrayList<String> errorstring = new ArrayList<>();
    private static Notification notification;
    private static boolean uploaderror = false;
    private static Process pr;

    private static String compilesketch() {
        String Programm = "";
        Block a = ProjectManager.getActProjectVar().blocks.get(0);

        Programm = ((ArduinoBlock) a.getBlocktype()).getCode() + "\n";
        while (a.getRight() != null) {

            //block.getRight().setX(block.getRight().getX() + block.getW());
            a = a.getRight();

            Programm = Programm + ((ArduinoBlock) a.getBlocktype()).getCode() + "//" + a.getIndex() + " \n";

        }
        Programm = Programm + "}\n";

        /////////////Loop Teil

        a = ProjectManager.getActProjectVar().blocks.get(1);

        Programm = Programm + ((ArduinoBlock) a.getBlocktype()).getCode() + "\n";
        while (a.getRight() != null) {

            //block.getRight().setX(block.getRight().getX() + block.getW());
            a = a.getRight();

            Programm = Programm + ((ArduinoBlock) a.getBlocktype()).getCode() + "//" + a.getIndex() + " \n";

        }
        Programm = Programm + "}\n";


        System.out.println(Programm);

        return Programm;
    }

    //OS tesster
    private static boolean isWindows() {

        return (OS.indexOf("win") >= 0);

    }

    private static boolean isMac() {

        return (OS.indexOf("mac") >= 0);

    }

    private static boolean isUnix() {

        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);

    }

    private static String runcommand(String command, boolean geterror) {

        Runtime rt = Runtime.getRuntime();
        pr = null;
        try {

            pr = rt.exec(command);

        } catch (IOException e) {
            e.printStackTrace();
        }


        BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        BufferedReader error = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
        String line = null;
        String output = null;
        String outputfromcli = "";
        String errorfromcli = "";

        String strCurrentLine;
        String strCurrenterrorLine;
        while (true) {
            try {
                if ((strCurrentLine = input.readLine()) == null) {

                    break;
                } else {
                    //  Programm.logger.config(strCurrentLine);
                    outputfromcli += strCurrentLine;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (geterror) {
            while (true) {
                try {
                    if ((strCurrenterrorLine = error.readLine()) == null) {

                        break;
                    } else {
                        Programm.logger.severe(strCurrenterrorLine);
                        errorstring.add(strCurrenterrorLine);
                        errorfromcli += strCurrenterrorLine;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


        return outputfromcli;

    }

    @Override
    public String compile() {

        errorstring.clear();
        return compilesketch();
    }

    @Override
    public boolean compileandrun() {

        UI.button_start.setDisable(true);
        UI.button_debugstart.setDisable(true);


        notification =  new Notification(AssetLoader.connector_offerd,"Umwandlung...","\nProjekt wird in Code umgewandelt").setCloseable(false).setAlivetime(9000);
        notification.setProgressbarvalue(0);
        NotificationManager.sendNotification(notification);


        JSONObject board = new JSONObject();
        board.put("address", ((ArrayList<String>) UI.runselection.getSelectedElement().getIdentifier()).get(1));
        board.put("boards",new JSONArray().put(new JSONObject().put("FQBN", ((ArrayList<String>) UI.runselection.getSelectedElement().getIdentifier()).get(0))));


        notification.setProgressbarvalue(20);


        compileandrun(board);

        return true;
    }




    @Override
    public String getCompilerVersion() {

        String commandgetVersion = "";
        if (isWindows()) {
            commandgetVersion = "libs\\arduino\\cli\\Windows\\arduino-cli.exe  version --format json";


        } else if (isMac()) {

        } else if (isUnix()) {

            commandgetVersion = "./libs/arduino/cli/linux/arduino-cli version --format json ";
        } else {
            Programm.logger.severe("You OS is not supported");
        }


        return new JSONObject( runcommand(commandgetVersion,false)).getString("VersionString");
    }

    private boolean compileandrun(JSONObject device) {

        String folder = System.currentTimeMillis() + "";
        String filename = folder + ".ino";

        new File(System.getProperty("user.home") + "/" + Data.foldername + "/temp/" + folder + "/").mkdir();


        try {
            new File(System.getProperty("user.home") + "/" + Data.foldername + "/temp/" + folder + "/" + filename).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (PrintWriter out = new PrintWriter(System.getProperty("user.home") + "/" + Data.foldername + "/temp/" + folder + "/" + filename)) {
            out.println(compile());
            notification.setProgressbarvalue(40);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        if (!new File(System.getProperty("user.home") + "/" + ".arduino15").exists()) {
            new File(System.getProperty("user.home") + "/" + ".arduino15").mkdir();

        } else if (!new File(System.getProperty("user.home") + "/" + ".arduino15/package_index.json").exists()) {

        }


        String compile_to_hex = "";
        String upload = "";

        if (isWindows()) {
            compile_to_hex = "libs\\arduino\\cli\\Windows\\arduino-cli.exe compile -b " + device.getJSONArray("boards").getJSONObject(0).getString("FQBN") + " " + (System.getProperty("user.home") + "/" + Data.foldername + "/temp/" + folder + "/" + filename).replace("/", "\\");

            upload = "libs\\arduino\\cli\\Windows\\arduino-cli.exe upload -b " + device.getJSONArray("boards").getJSONObject(0).getString("FQBN") + " " + (System.getProperty("user.home") + "/" + Data.foldername + "/temp/" + folder + "/").replace("/", "\\") + " -p " + device.getString("address") + " -v";

        } else if (isMac()) {

        } else if (isUnix()) {

            compile_to_hex = "./libs/arduino/cli/linux/arduino-cli compile -b " + device.getJSONArray("boards").getJSONObject(0).getString("FQBN") + " " + System.getProperty("user.home") + "/" + Data.foldername + "/temp/" + folder + "/" + filename + " ";

            upload = "./libs/arduino/cli/linux/arduino-cli upload -b " + device.getJSONArray("boards").getJSONObject(0).getString("FQBN") + " " + System.getProperty("user.home") + "/" + Data.foldername + "/temp/" + folder + "/" + " -p " + device.getString("address") + " -v";
        } else {
            Programm.logger.severe("You OS is not supported");
        }


        //Error Highlighting
        ProjectManager.getActProjectVar().Blockwitherrors.clear();
        errorstring.clear();
        notification.setTitle("Compilieren...");
        notification.setMessage("\nProjekt wird compiliert");

        runcommand(compile_to_hex, true);



        notification.setProgressbarvalue(60);


            if (errorstring.size() > 0) { //Geht Errors in an Array

                notification.setMessage("\nFehler beim Compilieren");
                notification.setProgressbarvalue(-1);
                notification.setCloseable(true);
                notification.setStayalive(true);
                try {

                    for (int i = 0; i < errorstring.size(); i++) {

                        if (errorstring.get(i).contains("^")) {

                            try {
                                if (!ProjectManager.getActProjectVar().Blockwitherrors.contains(Integer.parseInt(errorstring.get(i - 1).split("//")[1].replace(" ", "")))) {
                                    ProjectManager.getActProjectVar().Blockwitherrors.add(Integer.parseInt(errorstring.get(i - 1).split("//")[1].replace(" ", ""))); //Get Block Index
                                }

                                Programm.logger.severe("Fehler-Block: " + errorstring.get(i - 1).split("//")[1]);
                            } catch (Exception e) {

                            }


                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                new File(System.getProperty("user.home") + "/" + Data.foldername + "/temp/" + folder + "/" + filename).delete();
                UI.button_start.setDisable(false);
                UI.button_debugstart.setDisable(false);
                return false;
            }else{
                notification.setTitle("Wird hochgeladen");
                notification.setMessage("\nDas Projekt wird hochgeladen");
                notification.setProgressbarvalue(80);

                ExecutorService executor = Executors.newCachedThreadPool();
                String finalUpload = upload;
                Callable<Object> task = new Callable<Object>() {
                    public Object call() {
                        return  runcommand(finalUpload, true);
                    }
                };
                Future<Object> future = executor.submit(task);
                try {
                    Object result = future.get(10, TimeUnit.SECONDS);
                } catch (TimeoutException ex) {

                    notification.setTitle("Fehler beim Hochladen");
                    notification.setMessage("Trenne den Arduino\nund verbinde ihn neu!");
                    notification.setStayalive(true);
                    notification.setCloseable(true);
                    UI.button_start.setDisable(false);
                    UI.button_debugstart.setDisable(false);
                    return false;
                } catch (InterruptedException e) {
                    // handle the interrupts
                } catch (ExecutionException e) {
                    // handle other exceptions
                } finally {
                    future.cancel(true); // may or may not desire this
                }



                notification.setMessage(getAverdudeError());
                if(uploaderror) {
                    notification.setTitle("Fehler beim Hochladen");
                }else {
                    notification.setTitle("Hochladen abgeschlossen");
                }


                new File(System.getProperty("user.home") + "/" + Data.foldername + "/temp/" + folder + "/" + filename).delete();
                notification.setProgressbarvalue(100);
                notification.setStayalive(false);
                notification.setCloseable(true);
                UI.button_start.setDisable(false);
                UI.button_debugstart.setDisable(false);
                return true;
            }











    }

    public JSONArray getBoards() {

        String get_device = "";

        String update_index = "";
        String install_avr = "";

        if (isWindows()) {
            get_device = "libs\\arduino\\cli\\Windows\\arduino-cli.exe board list --format json";
            install_avr = "libs\\arduino\\cli\\Windows\\arduino-cli.exe core install arduino:avr@1.6.21";
            update_index = "libs\\arduino\\cli\\Windows\\arduino-cli.exe core update-index";
        } else if (isMac()) {

        } else if (isUnix()) {

            get_device = "./libs/arduino/cli/linux/arduino-cli board list --format json";
            install_avr = "./libs/arduino/cli/linux/arduino-cli core install arduino:avr@1.6.21";
            update_index = "./libs/arduino/cli/linux/arduino-cli core update-index";

        } else {
            Programm.logger.severe("You OS is not supported");
        }

        runcommand(install_avr, false);
        runcommand(update_index, false);
        try {
            return new JSONArray(runcommand(get_device, false));
        } catch (JSONException e) {
            return null;
        }
    }


    public JSONArray getInstalledBoards() {

        String get_device = "";

        String update_index = "";
        String install_avr = "";

        if (isWindows()) {
            get_device = "libs\\arduino\\cli\\Windows\\arduino-cli.exe board listall --format json";
            install_avr = "libs\\arduino\\cli\\Windows\\arduino-cli.exe core install arduino:avr@1.6.21";
            update_index = "libs\\arduino\\cli\\Windows\\arduino-cli.exe core update-index";
        } else if (isMac()) {

        } else if (isUnix()) {

            get_device = "./libs/arduino/cli/linux/arduino-cli board listall --format json";
            install_avr = "./libs/arduino/cli/linux/arduino-cli core install arduino:avr@1.6.21";
            update_index = "./libs/arduino/cli/linux/arduino-cli core update-index";

        } else {
            Programm.logger.severe("You OS is not supported");
        }

        runcommand(install_avr, false);
        runcommand(update_index, false);
        try {
            return new JSONObject(runcommand(get_device, false)).getJSONArray("boards");
        } catch (JSONException e) {
            return null;
        }
    }

    private String getAverdudeError() {
        String ausgabe;
        String response ="";
        for(int i=0;i<errorstring.size();i++) {
            response+="\n"+errorstring.get(i);

        }

        if (response.contains("written")) {
            ausgabe = ("\nProjekt wird bereinigt");
            uploaderror = false;


        } else {
            ausgabe = ("Ein unbekanter\nFehler ist aufgetreten");
            uploaderror = true;
        }

        if (response.contains("can't open device")) {
            ausgabe = ("Trenne den Arduino\nund verbinde ihn neu!");
            uploaderror = true;
        }

        if (response.contains("Expected signature")) {
            ausgabe = ("Falscher Board Typ ausgewählt!");
            uploaderror = true;
        }

        if (response.contains("not found")) {
            ausgabe = ("Falscher Board Typ ausgewählt!");
            uploaderror = true;
        }

        if (response.contains("programmer is not responding")) {
            ausgabe = ("Falscher Board Typ oder\nfalschen Port ausgewählt!");
            uploaderror = true;
        }

        if (response.contains("Permission denied")) {

            ausgabe = ("Keine Berechtigung auf diesem Port!");
            uploaderror = true;

        }

        return ausgabe;
    }


}
