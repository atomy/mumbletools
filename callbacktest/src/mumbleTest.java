import java.util.Map;
import Ice.ConnectionRefusedException;
import Ice.ObjectAdapter;
import Ice.ObjectPrx;
import Murmur.Channel;
import Murmur.InvalidCallbackException;
import Murmur.InvalidSecretException;
import Murmur.MetaPrx;
import Murmur.MetaPrxHelper;
import Murmur.ServerBootedException;
import Murmur.ServerCallbackPrx;
import Murmur.ServerCallbackPrxHelper;
import Murmur.ServerPrx;


public class mumbleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		gatherAndParseInfoToMumble();

	}



	public static void gatherAndParseInfoToMumble() throws Error {
		Ice.Communicator ic = null;
		try {
			// lets connect to the murmurs ice endpoint
			ic = Ice.Util.initialize();
			Ice.ObjectPrx base = ic.stringToProxy("Meta:tcp -h " + "127.0.0.1" + " -p " + "6502");
			MetaPrx meta = MetaPrxHelper.checkedCast(base);
			ObjectAdapter adapter = ic.createObjectAdapterWithEndpoints("Callback.Client", "tcp -h 127.0.0.1");
			adapter.activate();

			// didnt work
			if (meta == null) {
				System.out.println("could not create connection to the MetaProxy!");
			} else {
				System.out.println("Connection established!");
			}

			// get all running servers on the murmur instance
			ServerPrx servs[] = meta.getBootedServers();

			if (servs.length > 0) {
				ServerPrx serv = servs[0];
				ServCallback scb = new ServCallback(serv, adapter);
				ObjectPrx cbprx = adapter.addWithUUID(scb);
				ServerCallbackPrx cb = ServerCallbackPrxHelper.uncheckedCast(cbprx);
				try {
					serv.addCallback(cb);
				} catch (InvalidCallbackException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// get all channels
				Map<Integer, Channel> channels = serv.getChannels(null);

				// loop through channels
				for (Map.Entry<Integer, Channel> chanEntry : channels.entrySet()) {
					Channel chan = chanEntry.getValue();

					while(true) {
						Thread.sleep(1*1000);
					}
					// ///////////////////////////////////////
					// //
					// insert additional subsystems here //
					// //
					// ///////////////////////////////////////

				}
			}
		} catch (ConnectionRefusedException e) {
			System.out.println("Connection refused: Wrong port/ip information or server not running!");
		} catch (ServerBootedException e) {
			System.out.println("Server booted? Could not derive critical information!");
		} catch (InvalidSecretException e) {
			System.out.println("InvalidSecretException! Check murmur ice config!");
		} catch (InterruptedException e) {
			System.out.println("InterruptedException!");
			e.printStackTrace();
		}
	}	
	
}
