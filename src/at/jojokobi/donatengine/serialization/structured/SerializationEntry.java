package at.jojokobi.donatengine.serialization.structured;

public interface SerializationEntry {
	
	public Class<?> getSerializedClass ();
	
	public void setSerializedClass (Class<?> clazz);
	
	public SerializedData getData();

}
