package at.jojokobi.donatengine.presence;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class DiscordGamePresence implements GamePresencePlatform{

	private String clientId;
	private String steamId;
	private boolean running;
	
	
	public DiscordGamePresence(String clientId, String steamId) {
		super();
		this.clientId = clientId;
		this.steamId = steamId;
	}

	@Override
	public void init() {
		System.out.println("Init");
		running = true;
		DiscordRPC rpc = DiscordRPC.INSTANCE;
		DiscordEventHandlers handlers = new DiscordEventHandlers();
		handlers.ready = user -> {};
		rpc.Discord_Initialize(clientId, handlers, true, steamId);
		new Thread (() -> {
			while (running) {
				rpc.Discord_RunCallbacks();
				System.out.println("RPC_Update");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			rpc.Discord_Shutdown();
		}, "Discord-RPC-Thread").start();
	}

	@Override
	public void updatePresence(GamePresence presence) {
		DiscordRPC rpc = DiscordRPC.INSTANCE;
		if (presence == null) {
			rpc.Discord_ClearPresence();
		}
		else {
			DiscordRichPresence pre = convert(presence);
			rpc.Discord_UpdatePresence(pre);
		}
	}

	@Override
	public void end() {
		System.out.println("End presence");
		running = false;
	}
	
	private DiscordRichPresence convert (GamePresence presence) {
		DiscordRichPresence pre = new DiscordRichPresence();
		pre.state = presence.getState();
		pre.details = presence.getDetails();
		pre.startTimestamp = presence.getStartTimestamp();
		pre.endTimestamp = presence.getEndTimestamp();
		pre.largeImageKey = presence.getLargeImageKey();
		pre.largeImageText = presence.getLargeImageText();
		pre.smallImageKey = presence.getSmallImageKey();
		pre.smallImageText = presence.getSmallImageText();
		pre.partyId = presence.getPartyId();
		pre.partySize = presence.getPartySize();
		pre.partyMax = presence.getPartyMax();
		pre.matchSecret = presence.getMatchSecret();
		pre.joinSecret = presence.getJoinSecret();
		pre.spectateSecret = presence.getSpectateSecret();
		pre.instance = presence.isInstance() ? (byte) 1 : (byte) 0;
		
		return pre;
	}

	@Override
	public void setListeners(JoinListener join, JoinRequestListener joinRequest) {
		DiscordEventHandlers handlers = new DiscordEventHandlers();
		DiscordRPC rpc = DiscordRPC.INSTANCE;
		if (join != null) {
			handlers.joinGame = join::onJoin;
		}
		if (joinRequest != null ) {
			handlers.joinRequest = d -> {
				if (joinRequest.onJoinRequest(new GameJoinRequest(d.username + "#" + d.discriminator, "Discord"))) {
					rpc.Discord_Respond(d.userId, DiscordRPC.DISCORD_REPLY_YES);
				}
				else {
					rpc.Discord_Respond(d.userId, DiscordRPC.DISCORD_REPLY_NO);
				}
			};
		}
		rpc.Discord_UpdateHandlers(handlers);
	}
	
}
