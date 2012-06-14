package org.dss.mumbleIceConnector;

import Ice.ObjectAdapter;
import Ice.ObjectPrx;
import Murmur.InvalidCallbackException;
import Murmur.InvalidSecretException;
import Murmur.ServerBootedException;
import Murmur.ServerCallbackPrx;
import Murmur.ServerCallbackPrxHelper;
import Murmur.ServerPrx;

public class CallbackManager {
	public static void installCallbacks(ObjectAdapter adapter, ServerPrx serv) throws InvalidCallbackException, InvalidSecretException, ServerBootedException {
		ServCallback scb = new ServCallback(serv, adapter);
		ObjectPrx cbprx = adapter.addWithUUID(scb);
		ServerCallbackPrx cb = ServerCallbackPrxHelper.uncheckedCast(cbprx);
		
		serv.addCallback(cb);
	}
}
