/*
 * Decompiled with CFR 0.152.
 */
package de.onvif;

import de.onvif.soap.OnvifDevice;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.Iterator;
import java.util.List;
import javax.xml.soap.SOAPException;
import org.onvif.ver10.schema.Profile;

public class Main {
    private static final String INFO = "Commands:\n  \n  url: Get snapshort URL.\n  info: Get information about each valid command.\n  profiles: Get all profiles.\n  exit: Exit this application.";

    public static void main(String[] args2) {
        OnvifDevice cam;
        String password;
        String user;
        String cameraAddress;
        InputStreamReader inputStream2 = new InputStreamReader(System.in);
        BufferedReader keyboardInput = new BufferedReader(inputStream2);
        try {
            System.out.println("Please enter camera IP (with port if not 80):");
            cameraAddress = keyboardInput.readLine();
            System.out.println("Please enter camera username:");
            user = keyboardInput.readLine();
            System.out.println("Please enter camera password:");
            password = keyboardInput.readLine();
        }
        catch (IOException e1) {
            e1.printStackTrace();
            return;
        }
        System.out.println("Connect to camera, please wait ...");
        try {
            cam = new OnvifDevice(cameraAddress, user, password);
        }
        catch (ConnectException | SOAPException e1) {
            System.err.println("No connection to camera, please try again.");
            return;
        }
        System.out.println("Connection to camera successful!");
        block22: while (true) {
            try {
                while (true) {
                    String input;
                    System.out.println();
                    System.out.println("Enter a command (type \"info\" to get commands):");
                    switch (input = keyboardInput.readLine()) {
                        case "url": {
                            Profile p;
                            List<Profile> profiles = cam.getDevices().getProfiles();
                            Iterator<Profile> iterator2 = profiles.iterator();
                            while (true) {
                                if (!iterator2.hasNext()) continue block22;
                                p = iterator2.next();
                                try {
                                    System.out.println("URL from Profile '" + p.getName() + "': " + cam.getMedia().getSnapshotUri(p.getToken()));
                                }
                                catch (SOAPException e) {
                                    System.err.println("Cannot grab snapshot URL, got Exception " + e.getMessage());
                                }
                            }
                        }
                        case "profiles": {
                            Profile p;
                            List<Profile> profiles = cam.getDevices().getProfiles();
                            System.out.println("Number of profiles: " + profiles.size());
                            Iterator<Profile> iterator2 = profiles.iterator();
                            while (true) {
                                if (!iterator2.hasNext()) continue block22;
                                p = iterator2.next();
                                System.out.println("  Profile " + p.getName() + " token is: " + p.getToken());
                            }
                        }
                        case "info": {
                            System.out.println(INFO);
                            break;
                        }
                        case "quit": 
                        case "exit": 
                        case "end": {
                            return;
                        }
                        default: {
                            System.out.println("Unknown command!");
                            System.out.println();
                            System.out.println(INFO);
                        }
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            break;
        }
    }
}

