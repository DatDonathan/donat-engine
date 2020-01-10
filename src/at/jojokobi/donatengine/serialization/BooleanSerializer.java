package at.jojokobi.donatengine.serialization;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class BooleanSerializer implements BinarySerializer<Boolean> {

	@Override
	public void serialize(Boolean t, DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeBoolean(t);
	}

	@Override
	public Boolean deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		return buffer.readBoolean();
	}

	@Override
	public Class<Boolean> getSerializingClass() {
		return Boolean.class;
	}

}
