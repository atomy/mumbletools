package org.dss.mumbleIceConnector;

import Ice.Current;
import Ice.ObjectAdapter;
import Murmur.Channel;
import Murmur.ServerPrx;
import Murmur.User;
import Murmur._ServerCallbackDisp;

class ServCallback extends _ServerCallbackDisp {
	private static final long serialVersionUID = 1L;

	private ServerPrx server;
	
	public ServCallback(ServerPrx serv, ObjectAdapter adapter) {
		super();
		this.server = serv;
	}

	@Override
	public void userConnected(User state, Current __current) {
		//System.out.println("userConnected() '" + state.name + "'");
		MumbleControlCenter.sendWelcomeMessageToUser(server, state);
	}

	@Override
	public void userDisconnected(User state, Current __current) {
		//System.out.println("userDisconnected() '" + state.name + "'");
	}

	@Override
	public void userStateChanged(User state, Current __current) {
		//System.out.println("userStateChanged() '" + state.name + "'");
	}

	@Override
	public void channelCreated(Channel state, Current __current) {
		//System.out.println("channelCreated");
	}

	@Override
	public void channelRemoved(Channel state, Current __current) {
		//System.out.println("channelRemoved");
	}

	@Override
	public void channelStateChanged(Channel state, Current __current) {
		//System.out.println("channelStateChanged() '" + state.name + "'");
	}
}
