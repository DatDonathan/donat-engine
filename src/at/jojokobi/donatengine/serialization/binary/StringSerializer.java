package at.jojokobi.donatengine.serialization.binary;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class StringSerializer implements BinarySerializer<String> {

	@Override
	public void serialize(String t, DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeUTF(t);
	}

	@Override
	public String deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		return buffer.readUTF();
	}

	@Override
	public Class<String> getSerializingClass() {
		return String.class;
	}

}
