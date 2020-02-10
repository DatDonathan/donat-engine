package at.jojokobi.donatengine.ressources;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class RessourceHandler implements IRessourceHandler{
	
	private Map<String, Object> ressources = new HashMap<> ();

//	@Override
//	public Image getImage(String key) {
//		return getRessource(key, Image.class);
//	}
//
//	@Override
//	public Media getAudio(String key) {
//		return getRessource(key, Media.class);
//	}

	@Override
	public <T> T getRessource(String key, Class<T> clazz) {
		return clazz.isInstance(ressources.get(key)) ? clazz.cast(ressources.get(key)) : null;
	}

//	@Override
//	public boolean putImage(String key, String path) {
//		boolean success = false;
//		try (InputStream in = getClass().getResourceAsStream(path)) {
//			Image image = null;
//			if (in != null && (image = new Image(in)) != null) {
//				ressources.put(key, image);
//				success = true;
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return success;
//	}
//
//	@Override
//	public boolean putAudio(String key, String path) {
//		boolean success = false;
//		URL in = getClass().getResource(path);
//		Media media = null;
//		try {
//			if (in != null && (media = new Media(in.toURI().toString())) != null) {
//				ressources.put(key, media);
//				success = true;
//			}
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
//		return success;
//	}

	@Override
	public boolean putRessource(String key, String path, RessourceLoader loader) {
		boolean success = false;
		try (InputStream in = getClass().getResourceAsStream(path)) {
			Object obj = null;
			if (in != null && (obj = loader.loadRessource(in)) != null) {
				ressources.put(key, obj);
				success = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return success;
	}

}
