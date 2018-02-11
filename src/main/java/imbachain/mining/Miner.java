package imbachain.mining;

import com.google.gson.Gson;

import imbachain.infrastructure.UnconfirmedBlock;
import imbachain.utils.CryptoUtils;

public class Miner {
	private UnconfirmedBlock blockToMine;
	private long nonce;
	private int difficulty;
	private String difficultyString;
	private String transactionsString;
	private long timeStamp;

	private boolean mining = false;

	public Miner(UnconfirmedBlock blockToMine, long nonce, int difficulty) {
		super();
		this.blockToMine = blockToMine;
		this.nonce = nonce;
		this.difficulty = difficulty;
		this.transactionsString = new Gson().toJson(blockToMine.getTransactions());
		difficultyString = String.format("%0" + difficulty + "d", 0);
		System.out.println("Created a miner. Difficulty is [" + difficultyString + "]");
	}


	public void setNonce(long nonce) {
		this.nonce = nonce;
		
	}

	public int getDifficulty() {
		return difficulty;
		
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
		this.difficultyString = String.format("%0" + difficulty + "d", 0);
	}

	public boolean isMining() {
		return mining;
	}

	public void setMining(boolean mining) {
		this.mining = mining;
	}

	public UnconfirmedBlock getBlockToMine() {
		return blockToMine;
	}

	public void setBlockToMine(UnconfirmedBlock blockToMine) {
		this.blockToMine = blockToMine;
		this.transactionsString = new Gson().toJson(blockToMine.getTransactions());
	}

	public long getInitialNonce() {
		return nonce;
	}

	public void setInitialNonce(long initialNonce) {
		this.nonce = initialNonce;
	}

	// Use string and json so we can separate these two later and have miner
	// separated from the node
	public Winner mine() {
		
		Winner result = null;
		while (mining && result == null) {
			result = findHash();
		}
		return result;
	}

	public void stopMining() {
		this.mining = false;
	}

	private Winner findHash() {
		timeStamp = System.currentTimeMillis();
		String foundHash = CryptoUtils.toSha256("{" + timeStamp + ":" + blockToMine.getBlockIndex() + ":"
				+ blockToMine.getPreviousHash() + ":" + difficulty + ":" + (++nonce) + "}," + transactionsString);
		if (foundHash.startsWith(difficultyString)) {
			return new Winner(nonce, timeStamp);
		}
		return null;
	}

	public class Winner {

		public Winner(long nonce, long timestamp) {
			super();
			this.nonce = nonce;
			this.timestamp = timestamp;
		}

		private final long nonce;
		private final long timestamp;

		public long getNonce() {
			return nonce;
		}

		public long getTimestamp() {
			return timestamp;
		}

	}
}
