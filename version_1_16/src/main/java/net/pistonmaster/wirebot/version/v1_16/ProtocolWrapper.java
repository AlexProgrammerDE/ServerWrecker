package net.pistonmaster.wirebot.version.v1_16;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.auth.service.AuthenticationService;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.Session;
import net.pistonmaster.wirebot.common.IPacketWrapper;

public class ProtocolWrapper extends MinecraftProtocol implements IPacketWrapper {
    private AuthenticationService cache = null;

    public ProtocolWrapper(String username) {
        super(username);
    }

    public ProtocolWrapper(GameProfile profile, String accessToken) {
        super(profile, accessToken);
    }

    @Override
    public void newClientSession(Client client, Session session) {
        super.newClientSession(session);
    }

    @Override
    public String getProfileName() {
        return getProfile().getName();
    }
}