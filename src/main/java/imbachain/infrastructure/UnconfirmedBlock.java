package imbachain.infrastructure;

import java.util.ArrayList;
import java.util.List;

public class UnconfirmedBlock {

	private final long blockIndex;
	private final String previousHash;
	private final List<Transaction> transactions;
	private final int difficulty;
	public UnconfirmedBlock(long blockIndex, String previousHash, List<Transaction> transactions, int difficulty) {
		super();
		this.blockIndex = blockIndex;
		this.previousHash = previousHash;
		this.transactions = transactions == null ? new ArrayList<>() : transactions;
		this.difficulty = difficulty;
	}
	public long getBlockIndex() {
		return blockIndex;
	}
	public String getPreviousHash() {
		return previousHash;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public int getDifficulty() {
		return difficulty;
	}

}