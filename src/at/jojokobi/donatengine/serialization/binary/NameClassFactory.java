package at.jojokobi.donatengine.serialization.binary;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NameClassFactory implements ClassFactory {
	
	private static final String NULL = "null";

	@Override
	public Class<?> readClass(DataInput in) throws IOException {
		String name = in.readUTF();
		if (NULL.equals(name)) {
			return null;
		}
		else {
			try {
				return Class.forName(in.readUTF());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void writeClass(Class<?> clazz, DataOutput  out) throws IOException {
		out.writeUTF(clazz == null ? NULL : clazz.getName());
	}

}
