package imbachain.infrastructure;

import java.util.ArrayList;

import imbachain.utils.CryptoUtils;

public class GenesisBlock extends Block{

	public GenesisBlock() {
		super(0,  CryptoUtils.toSha256("The genesis block hash!"), new ArrayList<>(), 1518358377L , 0 , 0);
	}

}
