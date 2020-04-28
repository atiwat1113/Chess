package Resource;


public class Resource {
	//background and icon
	public static final String BACKGROUND = ClassLoader.getSystemResource("Image\\background.jpg").toString();
	public static final String ICON = ClassLoader.getSystemResource("Image\\icon.png").toString();
	public static final String BUTTON_FRAME = ClassLoader.getSystemResource("Image\\buttonFrame.png").toString();
	public static final String HIGHLIGHT_BUTTON_FRAME = ClassLoader.getSystemResource("Image\\highlightButtonFrame.png").toString();
	
	//sound
	public static final String BUTTON_CLICK = ClassLoader.getSystemResource("Sound/buttonClick.mp3").toString();
	public static final String CLOCK_TICKING = ClassLoader.getSystemResource("Sound/clockTicking.wav").toString();
	public static final String ENTITY_SELECTED = ClassLoader.getSystemResource("Sound/selectedSound.mp3").toString();
	public static final String WRONG_SELECTED = ClassLoader.getSystemResource("Sound/wrongPieceSelectedSound.mp3").toString();
	public static final String GAME_MENU = ClassLoader.getSystemResource("Sound/gameMenuLoop.mp3").toString();
	public static final String PROMOTION_SOUND = ClassLoader.getSystemResource("Sound/promotionSound.mp3").toString();
	
	//font
	public static final String ROMAN_FONT = ClassLoader.getSystemResource("Font/Roman.ttf").toString();
	
}
