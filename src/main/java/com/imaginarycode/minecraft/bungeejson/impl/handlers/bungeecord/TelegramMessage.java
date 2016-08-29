/**
 * This file is part of BungeeJSON.
 *
 * BungeeJSON is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BungeeJSON is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BungeeJSON.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.imaginarycode.minecraft.bungeejson.impl.handlers.bungeecord;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.imaginarycode.minecraft.bungeejson.BungeeJSONUtilities;
import com.imaginarycode.minecraft.bungeejson.api.ApiRequest;
import com.imaginarycode.minecraft.bungeejson.api.RequestHandler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

public class TelegramMessage implements RequestHandler {

    @Override
    public Object handle(ApiRequest request) {
        if (request.getParams().containsKey("user")) {
            if (request.getParams().containsKey("message")) {

                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("AdminChat");
                out.writeUTF("&7[&6Telegram&7]");
                out.writeUTF(("@".concat(request.getParams().get("user").get(0))));
                out.writeUTF(request.getParams().get("message").get(0));

                for(ServerInfo server : ProxyServer.getInstance().getServers().values()) {
                    server.sendData("MeriCore", out.toByteArray());
                }

                return BungeeJSONUtilities.ok();
            } else {
                return BungeeJSONUtilities.error("No message argument specified.");
            }
        } else {
            return BungeeJSONUtilities.error("No user argument specified.");
        }
    }

    @Override
    public boolean requiresAuthentication() {
        return true;
    }
}
