public class SimpleSnakeModel {
    public static void main(String[] args) {
        Grid torus = new Grid();
        Food apple = new Food(torus);
        Snake worm = new Snake(torus);

    }
}
