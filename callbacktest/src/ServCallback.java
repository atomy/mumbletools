import Ice.Current;
import Ice.ObjectAdapter;
import Murmur.Channel;
import Murmur.ServerPrx;
import Murmur.User;
import Murmur._ServerCallbackDisp;

class ServCallback extends _ServerCallbackDisp {
	private static final long serialVersionUID = 1L;

	public ServCallback(ServerPrx serv, ObjectAdapter adapter) {
		super();
	}

	@Override
	public void userConnected(User state, Current __current) {
		// TODO Auto-generated method stub
		System.out.println("userConnected");
	}

	@Override
	public void userDisconnected(User state, Current __current) {
		// TODO Auto-generated method stub
		System.out.println("userDisconnected");
	}

	@Override
	public void userStateChanged(User state, Current __current) {
		// TODO Auto-generated method stub
		System.out.println("userStateChanged");
	}

	@Override
	public void channelCreated(Channel state, Current __current) {
		// TODO Auto-generated method stub
		System.out.println("channelCreated");
	}

	@Override
	public void channelRemoved(Channel state, Current __current) {
		// TODO Auto-generated method stub
		System.out.println("channelRemoved");
	}

	@Override
	public void channelStateChanged(Channel state, Current __current) {
		// TODO Auto-generated method stub
		System.out.println("channelStateChanged");
	}
}
