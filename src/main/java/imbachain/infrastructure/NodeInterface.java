package imbachain.infrastructure;

import java.util.Collection;

public interface NodeInterface {

	Collection<Transaction> getTransactions();

	boolean addTransaction(Transaction transaction);

	Collection<Block> getBlocks();

	Block getBlock(int id) throws ArrayIndexOutOfBoundsException;

	Collection<BaseNode> getPeers();

	boolean addPeer(BaseNode newPeer);

	boolean addBlock(Block newBlock);

}
