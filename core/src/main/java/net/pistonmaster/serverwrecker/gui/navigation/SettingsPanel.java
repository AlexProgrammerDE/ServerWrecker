/*
 * ServerWrecker
 *
 * Copyright (C) 2022 ServerWrecker
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package net.pistonmaster.serverwrecker.gui.navigation;

import net.pistonmaster.serverwrecker.common.GameVersion;
import net.pistonmaster.serverwrecker.common.Options;
import net.pistonmaster.serverwrecker.common.ProxyType;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

public class SettingsPanel extends NavigationItem {
    private final JTextField hostInput;
    private final JTextField portInput;
    private final JSpinner delay;
    private final JCheckBox disableWaitEstablished;
    private final JCheckBox autoRegister;
    private final JTextField registerCommand;
    private final JTextField loginCommand;
    private final JTextField captchaCommand;
    private final JTextField passwordFormat;
    private final JSpinner amount;
    private final JTextField nameFormat;
    private final JComboBox<GameVersion> versionBox;
    private final JSpinner readTimeout;
    private final JSpinner writeTimeout;
    private final JSpinner connectTimeout;
    private final JSpinner compressionThreshold;
    private final JCheckBox autoReconnect;
    private final JCheckBox autoRespawn;

    public SettingsPanel() {
        super();

        setLayout(new GridLayout(0, 2));

        add(new JLabel("Host: "));
        hostInput = new JTextField("127.0.0.1");
        add(hostInput);

        add(new JLabel("Port: "));
        portInput = new JTextField("25565");
        add(portInput);

        add(new JLabel("Join delay (ms): "));
        delay = new JSpinner();
        delay.setValue(1000);
        add(delay);

        add(new JLabel("Disable wait established: "));
        disableWaitEstablished = new JCheckBox();
        add(disableWaitEstablished);

        add(new JLabel("Auto Register: "));
        autoRegister = new JCheckBox();
        add(autoRegister);

        add(new JLabel("Register Command: "));
        registerCommand = new JTextField("/register %password% %password%");
        add(registerCommand);

        add(new JLabel("Login Command: "));
        loginCommand = new JTextField("/login %password%");
        add(loginCommand);

        add(new JLabel("Captcha Command: "));
        captchaCommand = new JTextField("/captcha %captcha%");
        add(captchaCommand);

        add(new JLabel("Password Format: "));
        passwordFormat = new JTextField("ServerWrecker");
        add(passwordFormat);

        add(new JLabel("Amount: "));
        amount = new JSpinner();
        amount.setValue(20);
        add(amount);

        add(new JLabel("Name Format: "));
        nameFormat = new JTextField("Bot%d");
        add(nameFormat);

        add(new JLabel("Version: "));
        versionBox = new JComboBox<>();
        Arrays.stream(GameVersion.values())
                .sorted(Comparator.reverseOrder())
                .forEach(versionBox::addItem);
        add(versionBox);

        add(new JLabel("Read Timeout: "));
        readTimeout = new JSpinner();
        readTimeout.setValue(30);
        add(readTimeout);

        add(new JLabel("Write Timeout: "));
        writeTimeout = new JSpinner();
        writeTimeout.setValue(0);
        add(writeTimeout);

        add(new JLabel("Connect Timeout: "));
        connectTimeout = new JSpinner();
        connectTimeout.setValue(30);
        add(connectTimeout);

        add(new JLabel("Compression Threshold: "));
        compressionThreshold = new JSpinner();
        compressionThreshold.setValue(-1);
        add(compressionThreshold);

        add(new JLabel("Auto Reconnect: "));
        autoReconnect = new JCheckBox();
        add(autoReconnect);

        add(new JLabel("Auto Respawn: "));
        autoRespawn = new JCheckBox();
        add(autoRespawn);
    }

    @Override
    public String getNavigationName() {
        return "Settings";
    }

    @Override
    public String getRightPanelContainerConstant() {
        return RightPanelContainer.SETTINGS_MENU;
    }

    public Options generateOptions() {
        return new Options(
                hostInput.getText(),
                Integer.parseInt(portInput.getText()),
                (int) amount.getValue(),
                (int) delay.getValue(),
                !disableWaitEstablished.isSelected(),
                nameFormat.getText(),
                (GameVersion) versionBox.getSelectedItem(),
                autoRegister.isSelected(),
                DeveloperPanel.debug.isSelected(),
                (ProxyType) AccountPanel.proxyTypeCombo.getSelectedItem(),
                (Integer) AccountPanel.accPerProxy.getValue(),
                (int) readTimeout.getValue(),
                (int) writeTimeout.getValue(),
                (int) connectTimeout.getValue(),
                (int) compressionThreshold.getValue(),
                registerCommand.getText(),
                loginCommand.getText(),
                captchaCommand.getText(),
                passwordFormat.getText(),
                autoReconnect.isSelected(),
                autoRespawn.isSelected());
    }
}
