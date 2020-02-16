package at.jojokobi.donatengine.style;


public class FixedStyle {

	private Font font;
	private Color fill;
	private Color border;
	private Color fontColor;
	private Color fontBorder;
	private Double fontBorderStrength;
	private Double borderRadius;
	private Double borderStrength;
	private Double paddingLeft;
	private Double paddingRight;
	private Double paddingTop;
	private Double paddingBottom;
	private Double marginLeft;
	private Double marginRight;
	private Double marginTop;
	private Double marginBottom;

	public FixedStyle reset() {
		font = new Font("System", 12);
		fill = Color.TRANSPARENT;
		border = Color.TRANSPARENT;
		fontColor = Color.BLACK;
		fontBorder = Color.TRANSPARENT;
		fontBorderStrength = 0.0;
		borderRadius = 0.0;
		borderStrength = 0.0;
		setPadding(0);
		setMargin(0);
		return this;
	}

	public Font getFont() {
		return font;
	}

	public Color getFill() {
		return fill;
	}

	public Color getBorder() {
		return border;
	}

	public Double getBorderRadius() {
		return borderRadius;
	}

	public FixedStyle setFont(Font font) {
		this.font = font;
		return this;
	}

	public FixedStyle setFill(Color fill) {
		this.fill = fill;
		return this;
	}

	public FixedStyle setBorder(Color border) {
		this.border = border;
		return this;
	}

	public Double getBorderStrength() {
		return borderStrength;
	}

	public FixedStyle setBorderRadius(Double borderRadius) {
		this.borderRadius = borderRadius;
		return this;
	}

	public FixedStyle setBorderStrength(Double borderStrength) {
		this.borderStrength = borderStrength;
		return this;
	}

	public Color getFontColor() {
		return fontColor;
	}

	public Color getFontBorder() {
		return fontBorder;
	}

	public Double getFontBorderStrength() {
		return fontBorderStrength;
	}

	public FixedStyle setFontColor(Color fontColor) {
		this.fontColor = fontColor;
		return this;
	}

	public FixedStyle setFontBorder(Color fontBorder) {
		this.fontBorder = fontBorder;
		return this;
	}

	public FixedStyle setFontBorderStrength(Double fontBorderStrength) {
		this.fontBorderStrength = fontBorderStrength;
		return this;
	}
	
	public Double getMarginLeft() {
		return marginLeft;
	}

	public Double getMarginRight() {
		return marginRight;
	}

	public Double getMarginTop() {
		return marginTop;
	}

	public Double getMarginBottom() {
		return marginBottom;
	}

	public FixedStyle setMarginLeft(Double marginLeft) {
		this.marginLeft = marginLeft;
		return this;
	}

	public FixedStyle setMarginRight(Double marginRight) {
		this.marginRight = marginRight;
		return this;
	}

	public FixedStyle setMarginTop(Double marginTop) {
		this.marginTop = marginTop;
		return this;
	}

	public FixedStyle setMarginBottom(Double marginBottom) {
		this.marginBottom = marginBottom;
		return this;
	}

	public FixedStyle setMargin(double margin) {
		marginLeft = margin;
		marginRight = margin;
		marginTop = margin;
		marginBottom = margin;
		return this;
	}

	public Double getPaddingLeft() {
		return paddingLeft;
	}

	public Double getPaddingRight() {
		return paddingRight;
	}

	public Double getPaddingTop() {
		return paddingTop;
	}

	public Double getPaddingBottom() {
		return paddingBottom;
	}

	public FixedStyle setPaddingLeft(Double paddingLeft) {
		this.paddingLeft = paddingLeft;
		return this;
	}

	public FixedStyle setPaddingRight(Double paddingRight) {
		this.paddingRight = paddingRight;
		return this;
	}

	public FixedStyle setPaddingTop(Double paddingTop) {
		this.paddingTop = paddingTop;
		return this;
	}

	public FixedStyle setPaddingBottom(Double paddingBottom) {
		this.paddingBottom = paddingBottom;
		return this;
	}

	public FixedStyle setPadding(double padding) {
		paddingLeft = padding;
		paddingRight = padding;
		paddingTop = padding;
		paddingBottom = padding;
		return this;
	}

	public FixedStyle merge(FixedStyle style, boolean parent) {
		FixedStyle merged = new FixedStyle();
		merged.setFont(font == null ? style.getFont() : font);
		merged.setFontColor(fontColor == null ? style.getFontColor() : fontColor);
		merged.setFontBorder(fontBorder == null ? style.getFontBorder() : fontBorder);
		merged.setFontBorderStrength(fontBorderStrength == null ? style.getFontBorderStrength() : fontBorderStrength);

		//Parent
		merged.setFill(fill == null && !parent ? style.getFill() : fill);
		merged.setBorder(border == null && !parent ? style.getBorder() : border);
		merged.setBorderRadius(borderRadius == null && !parent ? style.getBorderRadius() : borderRadius);
		merged.setBorderStrength(borderStrength == null && !parent ? style.getBorderStrength() : borderStrength);
		merged.setPaddingLeft(paddingLeft == null && !parent ? style.getPaddingLeft() : paddingLeft);
		merged.setPaddingTop(paddingTop == null && !parent ? style.getPaddingTop() : paddingTop);
		merged.setPaddingRight(paddingRight == null && !parent ? style.getPaddingRight() : paddingRight);
		merged.setPaddingBottom(paddingBottom == null && !parent ? style.getPaddingBottom() : paddingBottom);
		merged.setMarginLeft(marginLeft == null && !parent ? style.getMarginLeft() : marginLeft);
		merged.setMarginTop(marginTop == null && !parent ? style.getMarginTop() : marginTop);
		merged.setMarginRight(marginRight == null && !parent ? style.getMarginRight() : marginRight);
		merged.setMarginBottom(marginBottom == null && !parent ? style.getMarginBottom() : marginBottom);

		return merged;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FixedStyle [font=");
		builder.append(font);
		builder.append(", fill=");
		builder.append(fill);
		builder.append(", border=");
		builder.append(border);
		builder.append(", fontColor=");
		builder.append(fontColor);
		builder.append(", fontBorder=");
		builder.append(fontBorder);
		builder.append(", fontBorderStrength=");
		builder.append(fontBorderStrength);
		builder.append(", borderRadius=");
		builder.append(borderRadius);
		builder.append(", borderStrength=");
		builder.append(borderStrength);
		builder.append(", paddingLeft=");
		builder.append(paddingLeft);
		builder.append(", paddingRight=");
		builder.append(paddingRight);
		builder.append(", paddingTop=");
		builder.append(paddingTop);
		builder.append(", paddingBottom=");
		builder.append(paddingBottom);
		builder.append("]");
		return builder.toString();
	}

}
