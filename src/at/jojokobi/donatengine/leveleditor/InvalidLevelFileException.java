package at.jojokobi.donatengine.leveleditor;

public class InvalidLevelFileException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidLevelFileException() {
		super();
	}

	public InvalidLevelFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidLevelFileException(String message) {
		super(message);
	}

	public InvalidLevelFileException(Throwable cause) {
		super(cause);
	}

}
