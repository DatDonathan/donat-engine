package at.jojokobi.donatengine.serialization;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class BinarySerializationWrapper implements SerializationWrapper {
	
	private ClassFactory fact;

	public BinarySerializationWrapper(ClassFactory fact) {
		super();
		this.fact = fact;
	}

	@Override
	public void serialize(Object obj, DataOutput output) throws IOException {
		BinarySerialization.getInstance().serialize(obj, output, this, fact);
	}

	@Override
	public <T> T deserialize(Class<T> clazz, DataInput input) throws IOException {
		return BinarySerialization.getInstance().deserialize(clazz, input, this, fact);
	}
	
}
