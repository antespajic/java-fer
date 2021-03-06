package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing a piece of textual data. It inherits from Node class.
 * 
 * @author Ante Spajic
 *
 */
public class TextNode extends Node {

	private String text;

	/**
	 * Constructor for TextNode, receives text as only parameter.
	 * 
	 * @param text
	 *            Value of the text node
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Getter for this nodes text property.
	 * 
	 * @return This nodes text
	 */
	public String getText() {
		return text;
	}
}
