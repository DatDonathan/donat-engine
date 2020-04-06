package at.jojokobi.donatengine.serialization.binary.structured;

public final class StruturedSerialization {
	
	private static StruturedSerialization instance;
	
	public static StruturedSerialization getInstance() {
		if (instance !=  null) {
			instance = new StruturedSerialization();
		}
		return instance;
	}
	
	private StruturedSerialization () {
		
	}
	

}
