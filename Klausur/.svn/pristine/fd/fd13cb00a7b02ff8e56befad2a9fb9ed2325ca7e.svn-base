package breakthroughPP.preset;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Player extends Remote {
	Move request() throws Exception;

	void confirm(Status boardStatus) throws Exception;

	void update(Move opponentMove, Status boardStatus) throws Exception;

	void init(int dimX, int dimY, int color) throws Exception;
}
