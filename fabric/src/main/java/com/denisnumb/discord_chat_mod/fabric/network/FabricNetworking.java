package com.denisnumb.discord_chat_mod.fabric.network;

import com.denisnumb.discord_chat_mod.network.PacketHandler;
import com.denisnumb.discord_chat_mod.network.emoji.DiscordEmojisPartPacket;
import com.denisnumb.discord_chat_mod.network.emoji.RequestDiscordEmojisPacket;
import com.denisnumb.discord_chat_mod.network.mentions.DiscordMentionsPartPacket;
import com.denisnumb.discord_chat_mod.network.mentions.RequestDiscordMentionsPacket;
import com.denisnumb.discord_chat_mod.network.screenshot.ScreenshotPartPacket;
import com.denisnumb.discord_chat_mod.network.sticker.DiscordStickersPartPacket;
import com.denisnumb.discord_chat_mod.network.sticker.RequestDiscordStickersPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class FabricNetworking {
    public static void init() {
        PayloadTypeRegistry.serverboundPlay().register(RequestDiscordMentionsPacket.TYPE, RequestDiscordMentionsPacket.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(DiscordMentionsPartPacket.TYPE, DiscordMentionsPartPacket.STREAM_CODEC);
        PayloadTypeRegistry.serverboundPlay().register(ScreenshotPartPacket.TYPE, ScreenshotPartPacket.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ScreenshotPartPacket.TYPE, ScreenshotPartPacket.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(DiscordEmojisPartPacket.TYPE, DiscordEmojisPartPacket.STREAM_CODEC);
        PayloadTypeRegistry.serverboundPlay().register(RequestDiscordEmojisPacket.TYPE, RequestDiscordEmojisPacket.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(DiscordStickersPartPacket.TYPE, DiscordStickersPartPacket.STREAM_CODEC);
        PayloadTypeRegistry.serverboundPlay().register(RequestDiscordStickersPacket.TYPE, RequestDiscordStickersPacket.STREAM_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(RequestDiscordMentionsPacket.TYPE, (packet, context) -> {
            context.server().execute(() -> PacketHandler.handleRequestDiscordMentionsPacket(context.player()));
        });

        ServerPlayNetworking.registerGlobalReceiver(RequestDiscordEmojisPacket.TYPE, (packet, context) -> {
            context.server().execute(() -> PacketHandler.handleRequestDiscordEmojisPacket(context.player()));
        });

        ServerPlayNetworking.registerGlobalReceiver(RequestDiscordStickersPacket.TYPE, (packet, context) -> {
            context.server().execute(() -> PacketHandler.handleRequestDiscordStickersPacket(context.player()));
        });

        ServerPlayNetworking.registerGlobalReceiver(ScreenshotPartPacket.TYPE, (packet, context) -> {
            context.server().execute(() -> PacketHandler.handleScreenshotPartPacketServerSide(packet, context.player()));
        });
    }

    public static void initClient(){
        ClientPlayNetworking.registerGlobalReceiver(DiscordMentionsPartPacket.TYPE, (packet, context) -> {
            context.client().execute(() -> PacketHandler.handleDiscordMentionsPacket(packet));
        });

        ClientPlayNetworking.registerGlobalReceiver(DiscordEmojisPartPacket.TYPE, (packet, context) -> {
            context.client().execute(() -> PacketHandler.handleDiscordEmojisPacket(packet));
        });

        ClientPlayNetworking.registerGlobalReceiver(DiscordStickersPartPacket.TYPE, (packet, context) -> {
            context.client().execute(() -> PacketHandler.handleDiscordStickersPacket(packet));
        });

        ClientPlayNetworking.registerGlobalReceiver(ScreenshotPartPacket.TYPE, (packet, context) -> {
            context.client().execute(() -> PacketHandler.handleScreenshotPartPacketClientSide(packet));
        });
    }
}
