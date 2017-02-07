package breakthroughPP.preset;

public interface Viewer {
    int turn();

    int getDimX();

    int getDimY();
    
    /**
     * Get color at Position; starting at 0|0 A1 bottom left
     *
     * @param pos Position of the field
     * @return Color at position if exists
     * @see Setting
     */
    int getColor(Position pos);

	/**
     * Get color at letter, number; starting at 0|0 A1 bottom left, letter X, number Y
     *
     * @param letter the value of the letter
     * @param number the value of the number
     * @return Color at position if exists
     * @see Setting
     */
    int getColor(int letter, int number);
	
    /**
     * Get color at letter, number; starting at 1|1 A1 bottom left, letter X, number Y
     *
     * @param letter the value of the letter
     * @param number the value of the number
     * @return Color at position if exists
     * @see Setting
     */
    int getColorAt(int letter, int number);

    /**
     * Get color at x,y; starting at 0|0 top left
     *
     * @param x yValue of the field
     * @param y y value of the field
     * @return Color at position if exists
     * @see Setting
     */
    int getRawAtXY(int x, int y);

    /**
     * Gets the status
     *
     * @return the {@link Status}
     * @throws PresetException if the method is not implemented
     */
    Status getStatus() throws PresetException;
}
