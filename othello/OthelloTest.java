package othello;


/**
 * The test class OthelloTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class OthelloTest extends junit.framework.TestCase
{
    private othello.Board board4x4;
    private othello.Board board6x6;
    private othello.Board board8x8;
    private othello.Board board1;
    private jaima.game.strategies.Guess guess;
    private jaima.game.strategies.Greedy greedy;
    private jaima.game.strategies.Ask ask;
    private jaima.game.strategies.MiniMax minimax;
    private jaima.game.strategies.AlphaBeta alphabeta;

    /**
     * Default constructor for test class TicTacTest
     */
    public OthelloTest()
    {
    }

    /**
     * Sets up the test fixture for 8x8 board
     *
     * Called before every test case method.
     */
    protected void setUp()
    {
        board4x4 = new othello.Board("0000;0120;0210;0000;1");
        //board1 = new othello.Board("", 64);
        guess = new jaima.game.strategies.Guess();
        greedy = new jaima.game.strategies.Greedy();
        ask = new jaima.game.strategies.Ask("Bill");
        minimax = new jaima.game.strategies.MiniMax(6);
        alphabeta = new jaima.game.strategies.AlphaBeta(8);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    protected void tearDown()
    {
        //         System.out.println(board1.prettyPrint());
    }

    public void testAsk() {
        System.out.println(board1.prettyPrint());
        assertNotNull(ask.choose(board1));
    }

    public void testGuess() {
        assertNotNull(guess.choose(board1));
    }

    public void testMiniMax() {
        assertNotNull(minimax.choose(board1));
    }

    public void testAlphaBeta() {
        assertNotNull(alphabeta.choose(board1));
    }

    public void testAsk4x4()
    {
    }

    public void testGuess4x4()
    {
        int count = 0;
        othello.Move move1 = null;
        System.out.println(board4x4);
        System.out.println(board4x4.prettyPrint());
        do {
            move1 = (othello.Move)greedy.choose(board4x4);
            assertNotNull(move1);
            System.out.println(move1.prettyPrint());
            board4x4 = board4x4.result(move1);
            assertNotNull(board4x4);
            System.out.println(board4x4);
            System.out.println(board4x4.prettyPrint());
            count++;
        } while(count < 8);
    }
}

