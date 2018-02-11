package imbachain.infrastructure;

public class Transaction implements Comparable<Transaction>{
	private final String from;
	private final String to;
	private final double value;
	private final double reward;
	
	

	public Transaction(String from, String to, double value, double reward) {
		super();
		this.from = from;
		this.to = to;
		this.value = value;
		this.reward = reward;
	}
	public Transaction(String from, String to, double value) {
		this(from,to,value,0);
	}


	@Override
	public int compareTo(Transaction t2) {
		if (reward < t2.reward)
			return -1;
		if (reward > t2.reward)
			return 1;
		return 0;
	}
	public boolean isValid() {
		// TODO Auto-generated method stub
		return true;
	}

}
