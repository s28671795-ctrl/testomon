/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Locale;
import org.h2.jdbc.JdbcConnection;
import org.h2.tools.Console;
import org.h2.util.Utils;

public class GUIConsole
extends Console
implements ActionListener,
MouseListener,
WindowListener {
    private long lastOpenNs;
    private boolean trayIconUsed;
    private Font font;
    private Frame statusFrame;
    private TextField urlText;
    private Button startBrowser;
    private Frame createFrame;
    private TextField pathField;
    private TextField userField;
    private TextField passwordField;
    private TextField passwordConfirmationField;
    private Button createButton;
    private TextArea errorArea;
    private Object tray;
    private Object trayIcon;

    @Override
    void show() {
        if (!GraphicsEnvironment.isHeadless()) {
            this.loadFont();
            try {
                if (!this.createTrayIcon()) {
                    this.showStatusWindow();
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private static Image loadImage(String string) {
        try {
            byte[] byArray = Utils.getResource(string);
            if (byArray == null) {
                return null;
            }
            return Toolkit.getDefaultToolkit().createImage(byArray);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return null;
        }
    }

    @Override
    public void shutdown() {
        super.shutdown();
        if (this.statusFrame != null) {
            this.statusFrame.dispose();
            this.statusFrame = null;
        }
        if (this.trayIconUsed) {
            try {
                Utils.callMethod(this.tray, "remove", this.trayIcon);
            }
            catch (Exception exception) {
            }
            finally {
                this.trayIcon = null;
                this.tray = null;
                this.trayIconUsed = false;
            }
            System.gc();
            String string = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if (string.contains("mac")) {
                for (Thread thread2 : Thread.getAllStackTraces().keySet()) {
                    if (!thread2.getName().startsWith("AWT-")) continue;
                    thread2.interrupt();
                }
            }
            Thread.currentThread().interrupt();
        }
    }

    private void loadFont() {
        this.font = this.isWindows ? new Font("Dialog", 0, 11) : new Font("Dialog", 0, 12);
    }

    private boolean createTrayIcon() {
        try {
            boolean bl = (Boolean)Utils.callStaticMethod("java.awt.SystemTray.isSupported", new Object[0]);
            if (!bl) {
                return false;
            }
            PopupMenu popupMenu = new PopupMenu();
            MenuItem menuItem = new MenuItem("H2 Console");
            menuItem.setActionCommand("console");
            menuItem.addActionListener(this);
            menuItem.setFont(this.font);
            popupMenu.add(menuItem);
            MenuItem menuItem2 = new MenuItem("Create a new database...");
            menuItem2.setActionCommand("showCreate");
            menuItem2.addActionListener(this);
            menuItem2.setFont(this.font);
            popupMenu.add(menuItem2);
            MenuItem menuItem3 = new MenuItem("Status");
            menuItem3.setActionCommand("status");
            menuItem3.addActionListener(this);
            menuItem3.setFont(this.font);
            popupMenu.add(menuItem3);
            MenuItem menuItem4 = new MenuItem("Exit");
            menuItem4.setFont(this.font);
            menuItem4.setActionCommand("exit");
            menuItem4.addActionListener(this);
            popupMenu.add(menuItem4);
            this.tray = Utils.callStaticMethod("java.awt.SystemTray.getSystemTray", new Object[0]);
            Dimension dimension = (Dimension)Utils.callMethod(this.tray, "getTrayIconSize", new Object[0]);
            String string = dimension.width >= 24 && dimension.height >= 24 ? "/org/h2/res/h2-24.png" : (dimension.width >= 22 && dimension.height >= 22 ? "/org/h2/res/h2-64-t.png" : "/org/h2/res/h2.png");
            Image image = GUIConsole.loadImage(string);
            this.trayIcon = Utils.newInstance("java.awt.TrayIcon", image, "H2 Database Engine", popupMenu);
            Utils.callMethod(this.trayIcon, "addMouseListener", this);
            Utils.callMethod(this.tray, "add", this.trayIcon);
            this.trayIconUsed = true;
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    private void showStatusWindow() {
        if (this.statusFrame != null) {
            return;
        }
        this.statusFrame = new Frame("H2 Console");
        this.statusFrame.addWindowListener(this);
        Image image = GUIConsole.loadImage("/org/h2/res/h2.png");
        if (image != null) {
            this.statusFrame.setIconImage(image);
        }
        this.statusFrame.setResizable(false);
        this.statusFrame.setBackground(SystemColor.control);
        GridBagLayout gridBagLayout = new GridBagLayout();
        this.statusFrame.setLayout(gridBagLayout);
        Panel panel = new Panel(gridBagLayout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.fill = 1;
        gridBagConstraints.insets = new Insets(0, 10, 0, 10);
        gridBagConstraints.gridy = 0;
        GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
        gridBagConstraints2.gridx = 0;
        gridBagConstraints2.gridwidth = 2;
        gridBagConstraints2.insets = new Insets(10, 0, 0, 0);
        gridBagConstraints2.gridy = 1;
        gridBagConstraints2.anchor = 13;
        GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
        gridBagConstraints3.fill = 2;
        gridBagConstraints3.gridy = 0;
        gridBagConstraints3.weightx = 1.0;
        gridBagConstraints3.insets = new Insets(0, 5, 0, 0);
        gridBagConstraints3.gridx = 1;
        GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
        gridBagConstraints4.gridx = 0;
        gridBagConstraints4.gridy = 0;
        Label label = new Label("H2 Console URL:", 0);
        label.setFont(this.font);
        panel.add((Component)label, gridBagConstraints4);
        this.urlText = new TextField();
        this.urlText.setEditable(false);
        this.urlText.setFont(this.font);
        this.urlText.setText(this.web.getURL());
        if (this.isWindows) {
            this.urlText.setFocusable(false);
        }
        panel.add((Component)this.urlText, gridBagConstraints3);
        this.startBrowser = new Button("Start Browser");
        this.startBrowser.setFocusable(false);
        this.startBrowser.setActionCommand("console");
        this.startBrowser.addActionListener(this);
        this.startBrowser.setFont(this.font);
        panel.add((Component)this.startBrowser, gridBagConstraints2);
        this.statusFrame.add((Component)panel, gridBagConstraints);
        int n = 300;
        int n2 = 120;
        this.statusFrame.setSize(n, n2);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.statusFrame.setLocation((dimension.width - n) / 2, (dimension.height - n2) / 2);
        try {
            this.statusFrame.setVisible(true);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        try {
            this.statusFrame.setAlwaysOnTop(true);
            this.statusFrame.setAlwaysOnTop(false);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private void startBrowser() {
        if (this.web != null) {
            String string = this.web.getURL();
            if (this.urlText != null) {
                this.urlText.setText(string);
            }
            long l = Utils.currentNanoTime();
            if (this.lastOpenNs == 0L || l - this.lastOpenNs > 100000000L) {
                this.lastOpenNs = l;
                this.openBrowser(string);
            }
        }
    }

    private void showCreateDatabase() {
        if (this.createFrame != null) {
            return;
        }
        this.createFrame = new Frame("H2 Console");
        this.createFrame.addWindowListener(this);
        Image image = GUIConsole.loadImage("/org/h2/res/h2.png");
        if (image != null) {
            this.createFrame.setIconImage(image);
        }
        this.createFrame.setResizable(false);
        this.createFrame.setBackground(SystemColor.control);
        GridBagLayout gridBagLayout = new GridBagLayout();
        this.createFrame.setLayout(gridBagLayout);
        Panel panel = new Panel(gridBagLayout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 2;
        gridBagConstraints.insets = new Insets(10, 0, 0, 0);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        Label label = new Label("Database path:", 0);
        label.setFont(this.font);
        panel.add((Component)label, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(10, 5, 0, 0);
        gridBagConstraints.gridx = 1;
        this.pathField = new TextField();
        this.pathField.setFont(this.font);
        this.pathField.setText("./test");
        panel.add((Component)this.pathField, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 2;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        Label label2 = new Label("Username:", 0);
        label2.setFont(this.font);
        panel.add((Component)label2, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(0, 5, 0, 0);
        gridBagConstraints.gridx = 1;
        this.userField = new TextField();
        this.userField.setFont(this.font);
        this.userField.setText("sa");
        panel.add((Component)this.userField, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 2;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        Label label3 = new Label("Password:", 0);
        label3.setFont(this.font);
        panel.add((Component)label3, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(0, 5, 0, 0);
        gridBagConstraints.gridx = 1;
        this.passwordField = new TextField();
        this.passwordField.setFont(this.font);
        this.passwordField.setEchoChar('*');
        panel.add((Component)this.passwordField, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 2;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        Label label4 = new Label("Password confirmation:", 0);
        label4.setFont(this.font);
        panel.add((Component)label4, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(0, 5, 0, 0);
        gridBagConstraints.gridx = 1;
        this.passwordConfirmationField = new TextField();
        this.passwordConfirmationField.setFont(this.font);
        this.passwordConfirmationField.setEchoChar('*');
        panel.add((Component)this.passwordConfirmationField, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new Insets(10, 0, 0, 0);
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = 13;
        this.createButton = new Button("Create");
        this.createButton.setFocusable(false);
        this.createButton.setActionCommand("create");
        this.createButton.addActionListener(this);
        this.createButton.setFont(this.font);
        panel.add((Component)this.createButton, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(10, 0, 0, 0);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        this.errorArea = new TextArea();
        this.errorArea.setFont(this.font);
        this.errorArea.setEditable(false);
        panel.add((Component)this.errorArea, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.fill = 1;
        gridBagConstraints.insets = new Insets(0, 10, 0, 10);
        gridBagConstraints.gridy = 0;
        this.createFrame.add((Component)panel, gridBagConstraints);
        int n = 400;
        int n2 = 400;
        this.createFrame.setSize(n, n2);
        this.createFrame.pack();
        this.createFrame.setLocationRelativeTo(null);
        try {
            this.createFrame.setVisible(true);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        try {
            this.createFrame.setAlwaysOnTop(true);
            this.createFrame.setAlwaysOnTop(false);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private void createDatabase() {
        String string;
        if (this.web == null || this.createFrame == null) {
            return;
        }
        String string2 = this.pathField.getText();
        String string3 = this.userField.getText();
        String string4 = this.passwordField.getText();
        if (!string4.equals(string = this.passwordConfirmationField.getText())) {
            this.errorArea.setForeground(Color.RED);
            this.errorArea.setText("Passwords don't match");
            return;
        }
        if (string4.isEmpty()) {
            this.errorArea.setForeground(Color.RED);
            this.errorArea.setText("Specify a password");
            return;
        }
        String string5 = "jdbc:h2:" + string2;
        try {
            new JdbcConnection(string5, null, string3, string4, false).close();
            this.errorArea.setForeground(new Color(0, 153, 0));
            this.errorArea.setText("Database was created successfully.\n\nJDBC URL for H2 Console:\n" + string5);
        }
        catch (Exception exception) {
            this.errorArea.setForeground(Color.RED);
            this.errorArea.setText(exception.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String string = actionEvent.getActionCommand();
        if ("exit".equals(string)) {
            this.shutdown();
        } else if ("console".equals(string)) {
            this.startBrowser();
        } else if ("showCreate".equals(string)) {
            this.showCreateDatabase();
        } else if ("status".equals(string)) {
            this.showStatusWindow();
        } else if (this.startBrowser == actionEvent.getSource()) {
            this.startBrowser();
        } else if (this.createButton == actionEvent.getSource()) {
            this.createDatabase();
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == 1) {
            this.startBrowser();
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        if (this.trayIconUsed) {
            Window window = windowEvent.getWindow();
            if (window == this.statusFrame) {
                this.statusFrame.dispose();
                this.statusFrame = null;
            } else if (window == this.createFrame) {
                this.createFrame.dispose();
                this.createFrame = null;
            }
        } else {
            this.shutdown();
        }
    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {
    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {
    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {
    }
}

