package imbachain.infrastructure;

import java.util.List;

import imbachain.utils.CryptoUtils;
import imbachain.utils.JsonUtils;

public class Block extends UnconfirmedBlock {

	private final String hash;
	private final long timeStamp;
	private final long nonce;

	public Block(long blockIndex, String previousHash, List<Transaction> transactions, long timeStamp, int difficulty,
			long nonce) {
		super(blockIndex, previousHash, transactions, difficulty);
		this.timeStamp = timeStamp;
		this.nonce = nonce;
		// Leave hash calculation in the end
		this.hash = CryptoUtils.toSha256(toString());
	}

	public static Block confirmBlock(UnconfirmedBlock block, long timestamp, long nonce) {
		return new Block(block.getBlockIndex(), block.getPreviousHash(), block.getTransactions(), timestamp,
				block.getDifficulty(), nonce);

	}

	public String getHash() {
		return hash;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public long getNonce() {
		return nonce;
	}

	public String toString() {
		return "{" + timeStamp + ":" + getBlockIndex() + ":" + getPreviousHash() + ":" + getDifficulty() + ":" + nonce
				+ "}," + JsonUtils.toJson(getTransactions());
	}

}
