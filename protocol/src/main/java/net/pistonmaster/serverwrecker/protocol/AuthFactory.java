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
package net.pistonmaster.serverwrecker.protocol;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.auth.service.AuthenticationService;
import com.github.steveice10.mc.auth.service.MojangAuthenticationService;
import com.github.steveice10.mc.auth.service.MsaAuthenticationService;
import net.pistonmaster.serverwrecker.common.GameVersion;
import net.pistonmaster.serverwrecker.common.IPacketWrapper;
import net.pistonmaster.serverwrecker.common.ServiceServer;
import net.pistonmaster.serverwrecker.version.v1_12.ProtocolWrapper1_12;
import net.pistonmaster.serverwrecker.version.v1_17.ProtocolWrapper1_17;
import net.pistonmaster.serverwrecker.version.v1_18.ProtocolWrapper1_18;
import net.pistonmaster.serverwrecker.version.v1_19.ProtocolWrapper1_19;
import net.pistonmaster.serverwrecker.version.v1_7.ProtocolWrapper1_7;
import net.pistonmaster.serverwrecker.version.v1_8.ProtocolWrapper1_8;
import net.pistonmaster.serverwrecker.version.v1_9.ProtocolWrapper1_9;

import java.net.Proxy;
import java.util.Map;

public class AuthFactory {
    public static IPacketWrapper authenticate(GameVersion gameVersion, String username) {
        return switch (gameVersion) {
            case VERSION_1_7 -> new ProtocolWrapper1_7(username);
            case VERSION_1_8 -> new ProtocolWrapper1_8(username);
            case VERSION_1_9 -> new ProtocolWrapper1_9(username);
            case VERSION_1_12 -> new ProtocolWrapper1_12(username);
            case VERSION_1_17 -> new ProtocolWrapper1_17(username);
            case VERSION_1_18 -> new ProtocolWrapper1_18(username);
            case VERSION_1_19 -> new ProtocolWrapper1_19(username);
        };
    }

    public static IPacketWrapper authenticate(GameVersion gameVersion, String username, String password, Proxy proxy, ServiceServer serviceServer, Map<String, String> serviceServerConfig) throws Exception {
        AuthenticationService authService = switch (serviceServer) {
            case OFFLINE -> new OfflineAuthenticationService();
            case MOJANG -> new MojangAuthenticationService();
            case MICROSOFT -> new MsaAuthenticationService(serviceServerConfig.get("clientId"));
        };

        authService.setUsername(username);
        authService.setPassword(password);
        authService.setProxy(proxy);

        authService.login();

        GameProfile profile = authService.getSelectedProfile();
        String accessToken = authService.getAccessToken();

        return switch (gameVersion) {
            case VERSION_1_7 -> new ProtocolWrapper1_7(profile, accessToken);
            case VERSION_1_8 -> new ProtocolWrapper1_8(profile, accessToken);
            case VERSION_1_9 -> new ProtocolWrapper1_9(profile, accessToken);
            case VERSION_1_12 -> new ProtocolWrapper1_12(profile, accessToken);
            case VERSION_1_17 -> new ProtocolWrapper1_17(profile, accessToken);
            case VERSION_1_18 -> new ProtocolWrapper1_18(profile, accessToken);
            case VERSION_1_19 -> new ProtocolWrapper1_19(profile, accessToken);
        };
    }
}
