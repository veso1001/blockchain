package imbachain.infrastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import imbachain.mining.Miner;
import imbachain.mining.Miner.Winner;
import imbachain.utils.CryptoUtils;

public class Node extends BaseNode implements NodeInterface {

	public Node(String address) {
		super(address);
	}

	public Node(String name, String address, int port) {
		super(name, address, port);
	}

	public Node(String address, int port) {
		super(address, port);
	}

	
	
	private Queue<Transaction> transactionsQueue = new PriorityQueue<Transaction>();
	private List<Block> blockchain = new LinkedList<>(Arrays.asList(new GenesisBlock()));
	private Set<BaseNode> peers = new HashSet<>();

	// TODO If this node wants to be a mining pool for example we should
	// Separate the miners from the node and keep a map of miners with their
	// addresses
	// so we can just give tasks to them and wait for the result
	private Miner miner;

	private Block createBlock() {
		List<Transaction> blockTransactions = new ArrayList<>(TRANSACTIONS_IN_BLOCK);
		for (int i = 0; i < TRANSACTIONS_IN_BLOCK; i++) {
			Transaction trans = transactionsQueue.poll();
			if (trans != null) {
				blockTransactions.add(trans);
			} else {
				break;
			}
		}
		int blockIndex = blockchain.size();
		// TODO use dynamic difficulty
		UnconfirmedBlock newBlock = new UnconfirmedBlock(blockIndex, blockchain.get(blockIndex - 1).getHash(),
				blockTransactions, DEFAULT_DIFFICULTY);

		Miner miner = new Miner(newBlock, 0, DEFAULT_DIFFICULTY);
		Winner winner = miner.mine();
		if (winner != null) {
			Block confirmedBlock = Block.confirmBlock(newBlock, winner.getTimestamp(), winner.getNonce());
			return confirmedBlock;
		}
		return null;
	}

	private boolean createAndAddBock() {
		Block newBlock = createBlock();
		return addBlock(newBlock);
	}


	private Block getLatestBlock() {
		return blockchain.get(blockchain.size() - 1);
	}

	private boolean isValidBlock(Block newBlock, Block previousBlock) {
		if (newBlock == null || previousBlock == null)
			return false;
		if (newBlock.getBlockIndex() != previousBlock.getBlockIndex() + 1)
			return false;
		if (!newBlock.getPreviousHash().equals(previousBlock.getHash()))
			return false;
		if (!newBlock.getHash().equals(CryptoUtils.toSha256(newBlock.toString())))
			return false;
		return true;
	}

	private boolean isValidChain(List<Block> blockchain) {
		if (blockchain == null || blockchain.isEmpty())
			return false;
		Block previousBlock = new GenesisBlock();
		for (int i = 1; i < blockchain.size(); i++)
			if (isValidBlock(blockchain.get(i), previousBlock)) {
				previousBlock = blockchain.get(i);
			} else {
				return false;
			}
		return true;
	}

	@Scheduled(fixedDelay = 5000)
	public void checkPeersBlockchain() {
		System.out.println("Synchronizing chain if it is too old:" + System.currentTimeMillis());
	}

	// NODE API starts here
	// -------------------------------------------------------------
	@Override
	public Collection<Transaction> getTransactions() {
		return transactionsQueue;
	}

	@Override
	public boolean addTransaction(Transaction transaction) {
		if (transaction != null && transaction.isValid()) {
			return transactionsQueue.offer(transaction);
		}
		return false;
	}

	@Override
	public Collection<Block> getBlocks() {
		return blockchain;
	}

	@Override
	public Block getBlock(int id) throws ArrayIndexOutOfBoundsException {
		return blockchain.get(id);
	}

	@Override
	public Collection<BaseNode> getPeers() {
		return peers;
	}

	@Override
	public boolean addPeer(BaseNode newPeer) {
		if (newPeer != null) {
			peers.add(newPeer);
			return true;
		}
		return false;
	}
	@Override
	public boolean addBlock(Block newBlock) {
		if (isValidBlock(newBlock, getLatestBlock())) {
			blockchain.add(newBlock);
			return true;
		}
		return false;
	}


}