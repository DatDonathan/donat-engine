package at.jojokobi.donatengine.serialization.binary;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IDClassFactory implements ClassFactory {
	
//	private int count = 0;
	
	private static final int NULL = -1;
	private List<Class<?>> classes = new ArrayList<>();
	
	public void addClass (Class<?> clazz) {
		classes.add(clazz);
	}

	@Override
	public Class<?> readClass(DataInput in) throws IOException {
		int index = in.readInt();
		Class<?> clazz = index == NULL ? null : classes.get(index);
//		System.out.println((count++) + ": recieved " + (clazz == null ? "null" : clazz.getSimpleName()) + " with index " + index + "!");
		return clazz;
	}

	@Override
	public void writeClass(Class<?> clazz, DataOutput out) throws IOException {
		int index = classes.indexOf(clazz);
		if (clazz == null) {
//			System.out.println((count++) + ": sent null with index " + index + "!");
			out.writeInt(NULL);
		}
		else if (index >= 0) {
//			System.out.println((count++) + ": sent " + clazz + " with index " + index + "!");
			out.writeInt(index);
		}
		else {
			throw new IllegalArgumentException("The given class " + clazz.getName() + " is not in the class id list.");
		}
		
	}

}
