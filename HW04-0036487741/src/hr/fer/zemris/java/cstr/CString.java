package hr.fer.zemris.java.cstr;

import java.util.regex.Pattern;

/**
 * Class that represents my implementation of {@link java.util.String} but with
 * some properties different like complexity of substring method in this class
 * is O(1) instead of O(n), also this class doesn't create its own copy of
 * character array.
 * 
 * @author Ante Spajic
 *
 */
public class CString {

	private final char[] data;
	private int length;
	private int offset;

	/**
	 * 
	 * @param data
	 */
	public CString(char[] data) {
		this(data, 0, data.length);
	}

	/**
	 * 
	 * 
	 * @param original
	 */
	public CString(CString original) {
		if (original.data.length > original.length) {
			char[] newArray = new char[original.length];
			for (int i = 0; i < original.length; i++) {
				newArray[i] = original.data[original.offset + i];
			}
			this.data = newArray;
			this.length = original.length;
			this.offset = 0;
		} else {
			this.data = original.data;
			this.offset = original.offset;
			this.length = original.length;
		}
	}

	/**
	 * 
	 * 
	 * @param data
	 * @param offset
	 * @param length
	 */
	public CString(char[] data, int offset, int length) {
		if (offset < 0) {
			throw new StringIndexOutOfBoundsException(offset);
		}
		if (length < 0) {
			throw new StringIndexOutOfBoundsException(length);
		}
		if (offset > data.length - length) {
			throw new StringIndexOutOfBoundsException(offset + length);
		}
		this.data = data;
		this.offset = offset;
		this.length = length;
	}

	/**
	 * Returns new CString object which has the same character data as given
	 * Java's String object
	 * 
	 * @param string
	 * @return
	 */
	public static CString fromString(String string) {
		return new CString(string.toCharArray(), 0, string.length());
	}

	/**
	 * Returns this CStrings length
	 * 
	 * @return
	 */
	public int length() {
		return length;
	}

	/**
	 * Returns the character at given index from CString.
	 * 
	 * @param index
	 *            Index of character in CString we want to be returned.
	 * @return Character from CString at given index.
	 */
	public char charAt(int index) {
		if (index < 0 || index >= data.length) {
			throw new StringIndexOutOfBoundsException(index);
		}
		return data[index + offset];
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public char[] toCharArray() {
		char[] newData = new char[length - offset];
		for (int i = offset; i < length; i++) {
			newData[i] = data[i];
		}
		return newData;
	}

	@Override
	public String toString() {
		return new String(data, offset, length);
	}

	/**
	 * Returns the index within this string of the first occurrence of given
	 * character.
	 * 
	 * @param c
	 *            Character that we want to get index of.
	 * @return The index of the first occurrence of the character in the
	 *         character sequence represented by this CString, or {@code -1} if
	 *         the character does not occur.
	 */
	public int indexOf(char c) {
		for (int i = 0; i < data.length; i++) {
			if (data[i + offset] == c) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns true if this CString starts with sequence of characters same as
	 * in given CString 's', otherwise returns false if provided CString's
	 * length is greater or doesn't have same properties as this CString.
	 * 
	 * @param s
	 *            CString sequence of characters we check if this CString begins
	 *            with.
	 * @return True if this CString begins with all the characters from CString
	 *         parameter s.
	 */
	public boolean startsWith(CString s) {
		return startsWith(s, 0);
	}

	/**
	 * 
	 * 
	 * @param s
	 *            CString sequence of characters we check if this CString ends
	 *            with.
	 * @return True if this CString ends with all the characters from CString
	 *         parameter s.
	 */
	public boolean endsWith(CString s) {
		return startsWith(s, length - s.length);

	}

	private boolean startsWith(CString s, int index) {
		int start = offset + index;
		int sOffset = s.offset;
		int sLen = s.length;
		if ((index < 0) || (index > length - sLen)) {
			return false;
		}
		while (--sLen >= 0) {
			if (data[start++] != s.data[sOffset++]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public boolean contains(CString s) {
		if (s == null) {
			throw new NullPointerException();
		}
		// it contains an empty string only if its empty
		if (s.length == 0) {
			return (length == 0) ? true : false;
		}
		char first = s.data[0];
		int targetLen = s.data.length;
		int max = offset + (length - targetLen);

		for (int i = offset; i < data.length; i++) {
			// Find first character
			if (data[i] != first) {
				while (++i <= max && data[i] != first)
					;
			}
			// After finding first character look for rest of the word
			if (i <= max) {
				int j = i + 1;
				int end = j + targetLen - 1;
				for (int k = s.offset + 1; j < end && data[j] == s.data[k]; j++, k++)
					;

				if (j == end) {
					// Found whole string.
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 
	 * 
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public CString substring(int startIndex, int endIndex) {
		if (startIndex < 0) {
			throw new StringIndexOutOfBoundsException(startIndex);
		}
		if (endIndex > data.length) {
			throw new StringIndexOutOfBoundsException(endIndex);
		}
		int newLen = endIndex - startIndex;
		if (newLen < 0) {
			throw new StringIndexOutOfBoundsException(newLen);
		}
		return ((startIndex == 0 && endIndex == data.length) ? this
				: new CString(data, offset + startIndex, newLen));
	}

	/**
	 * 
	 * 
	 * @param n
	 * @return
	 */
	public CString left(int n) {
		if (n < 0 || n > length) {
			throw new StringIndexOutOfBoundsException(n);
		}
		return new CString(data, offset, n);
	}

	/**
	 * 
	 * 
	 * @param n
	 * @return
	 */
	public CString right(int n) {
		if (n < 0 || n > length) {
			throw new StringIndexOutOfBoundsException(n);
		}
		return new CString(data, offset + length - n, n);
	}

	/**
	 * 
	 * 
	 * @param s
	 * @return
	 */
	public CString add(CString s) {
		int newLen = s.length + length;
		char[] newArr = new char[newLen];
		System.arraycopy(data, offset, newArr, 0, length);
		System.arraycopy(s.data, s.offset, newArr, length, s.length);
		return new CString(newArr, 0, newLen);
	}

	/**
	 * 
	 * @param oldChar
	 * @param newChar
	 * @return
	 */
	public CString replaceAll(char oldChar, char newChar) {
		char[] newData = new char[length - offset];
		for (int i = offset; i < length; i++) {
			if (data[i] == oldChar) {
				newData[i] = newChar;
				continue;
			}
			newData[i] = data[i];
		}
		return new CString(newData, offset, length);
	}

	/**
	 * 
	 * @param oldStr
	 * @param newStr
	 * @return
	 */
	public CString replaceAll(CString oldStr, CString newStr) {
		String replacement = Pattern.compile(oldStr.toString())
				.matcher(this.toString()).replaceAll(newStr.toString());
		char[] newData = replacement.toCharArray();
		return new CString(newData);
	}
}
