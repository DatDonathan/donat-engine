package at.jojokobi.donatengine.serialization.binary;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class LongSerializer implements BinarySerializer<Long> {

	@Override
	public void serialize(Long t, DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeLong(t);
	}

	@Override
	public Long deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		return buffer.readLong();
	}

	@Override
	public Class<Long> getSerializingClass() {
		return Long.class;
	}

}
