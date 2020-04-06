package at.jojokobi.donatengine.serialization.binary;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class DoubleSerializer implements BinarySerializer<Double> {

	@Override
	public void serialize(Double t, DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeDouble(t);
	}

	@Override
	public Double deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		return buffer.readDouble();
	}

	@Override
	public Class<Double> getSerializingClass() {
		return Double.class;
	}

}
