package universalelectricity.api.net;

import java.util.Set;

/**
 * Implement this in your network class/interface if you plan to have your own network defined by
 * specific conductors and acceptors.
 * 
 * @author Calclavia, Aidancbrady
 * 
 * @param <N> - the class/interface Type value in which you implement this
 * @param <C> - the class/interface Type which makes up the network's connector set
 * @param <A> - the class/interface Type which makes up the network's node set
 */
public interface INetwork<N, C, A>
{
	public void addConnector(C connector);

	public void removeConnector(C connector);

	/**
	 * Gets the set of conductors that make up this network.
	 * 
	 * @return conductor set
	 */
	public Set<C> getConnectors();

	/**
	 * * @return The list of nodes in the network.
	 */
	public Set<A> getNodes();

	/**
	 * Can the network update?
	 * 
	 * @return True if the network can update, otherwise the network tick handler will remove the
	 * network from the tick list.
	 */
	public boolean canUpdate();

	/**
	 * @return True to leave the network in the ticker. False to remove the network from the ticker.
	 */
	public boolean continueUpdate();

	/**
	 * Updates the network. Called by the {NetworkTickHandler}.
	 */
	public void update();

	/**
	 * Creates a new network that makes up the current network and the network defined in the
	 * parameters. Be sure to refresh the new network inside this method.
	 * 
	 * @param network - network to merge
	 * @return The new network instance.
	 */
	public N merge(N network);

	/**
	 * Splits a network by removing a conductor referenced in the parameter. It will then create and
	 * refresh the new independent networks possibly created by this operation.
	 * 
	 * @param connection
	 */
	public void split(C connection);

	/**
	 * Splits the network between 2 connectors, separating their networks.
	 * 
	 * @param connectorA
	 * @param connectorB
	 */
	public void split(C connectorA, C connectorB);

}
