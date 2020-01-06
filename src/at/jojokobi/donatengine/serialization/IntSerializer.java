package at.jojokobi.donatengine.serialization;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class IntSerializer implements BinarySerializer<Integer> {

	@Override
	public void serialize(Integer t, DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeInt(t);
	}

	@Override
	public Integer deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		return buffer.readInt();
	}

	@Override
	public Class<Integer> getSerializingClass() {
		return Integer.class;
	}

}
