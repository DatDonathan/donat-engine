package at.jojokobi.donatengine.ressources;

import java.io.IOException;
import java.io.InputStream;

public interface RessourceLoader {
	
	public Object loadRessource (InputStream in) throws IOException;

}
