import Runner.Game;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(new GameLibAdapter());
        game.run();
        System.exit(0);
    }
}
