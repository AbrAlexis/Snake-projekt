public class Snake {
    private int headX;
    private int headY;
    private char direction;
    private int bodyLength;

    public Snake() {
        this.headX = getGridX();
        this.headY = getGridY();
        this.direction = 'w';
        this.bodyLength = 1;
    }

}
