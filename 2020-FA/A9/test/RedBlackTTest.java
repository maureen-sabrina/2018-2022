/**
 * @author Maureen Lynch
 * @version 1.0
 * @date Fall 2020
 */
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackTTest {

    @Test
    public void test1 () {
        AVL<Character> avl = new EmptyAVL<>();
        avl = avl.AVLinsert('J');
        avl = avl.AVLinsert('F');
        avl = avl.AVLinsert('P');
        avl = avl.AVLinsert('D');
        avl = avl.AVLinsert('G');
        avl = avl.AVLinsert('L');
        avl = avl.AVLinsert('V');
        avl = avl.AVLinsert('C');
        avl = avl.AVLinsert('N');
        avl = avl.AVLinsert('S');
        avl = avl.AVLinsert('X');
        avl = avl.AVLinsert('Q');
        avl = avl.AVLinsert('U');

        TreePrinter.print(avl);
    }

    @Test
    public void test2 () {
        RedBlackT<Character> rb;

        rb = new RBNode<>(
                'J',
                Color.BLACK,
                new RBNode<>(
                        'F',
                        Color.BLACK,
                        new RBNode<>(
                                'D',
                                Color.BLACK,
                                new RBNode<>(
                                        'C',
                                        Color.RED,
                                        new EmptyRB<>(),
                                        new EmptyRB<>()
                                ),
                                new EmptyRB<>()
                        ),
                        new RBNode<>(
                                'G',
                                Color.BLACK,
                                new EmptyRB<>(),
                                new EmptyRB<>()
                        )
                ),
                new RBNode<>(
                        'P',
                        Color.BLACK,
                        new RBNode<>(
                                'L',
                                Color.BLACK,
                                new EmptyRB<>(),
                                new RBNode<>(
                                        'N',
                                        Color.RED,
                                        new EmptyRB<>(),
                                        new EmptyRB<>()
                                )
                        ),
                        new RBNode<>(
                                'V',
                                Color.RED,
                                new RBNode<>(
                                        'S',
                                        Color.BLACK,
                                        new RBNode<>(
                                                'Q',
                                                Color.RED,
                                                new EmptyRB<>(),
                                                new EmptyRB<>()
                                        ),
                                        new RBNode<>(
                                                'U',
                                                Color.RED,
                                                new EmptyRB<>(),
                                                new EmptyRB<>()
                                        )
                                ),
                                new RBNode<>(
                                        'X',
                                        Color.BLACK,
                                        new EmptyRB<>(),
                                        new EmptyRB<>()
                                )
                        )
                ));

        TreePrinter.print(rb);

        Optional<Integer> opth = rb.isValidBlackTree();
        int h = opth.orElseThrow();
        assertEquals(4,h);
    }

    @Test
    public void test3 () {
        AVL<Character> avl = new EmptyAVL<>();
        avl = avl.AVLinsert('J');
        avl = avl.AVLinsert('F');
        avl = avl.AVLinsert('P');
        avl = avl.AVLinsert('D');
        avl = avl.AVLinsert('G');
        avl = avl.AVLinsert('L');
        avl = avl.AVLinsert('V');
        avl = avl.AVLinsert('C');
        avl = avl.AVLinsert('N');
        avl = avl.AVLinsert('S');
        avl = avl.AVLinsert('X');
        avl = avl.AVLinsert('Q');
        avl = avl.AVLinsert('U');

        RedBlackT<Character> rb = avl.toRB();

        TreePrinter.print(rb);

        Optional<Integer> opth = rb.isValidBlackTree();
        int h = opth.orElseThrow();
        assertEquals(4,h);

    }

    // PERSONAL TESTS BELOW

    @Test
    public void test4() {
        AVL<Integer> avl = new EmptyAVL<>();
        avl = avl.AVLinsert(50);
        avl = avl.AVLinsert(100);
        avl = avl.AVLinsert(-65);
        avl = avl.AVLinsert(4);
        avl = avl.AVLinsert(1000);
        avl = avl.AVLinsert(75);
        avl = avl.AVLinsert(-500);
        avl = avl.AVLinsert(84);
        avl = avl.AVLinsert(-300);
        avl = avl.AVLinsert(62);

        RedBlackT<Integer> rb = avl.toRB();

        TreePrinter.print(rb);

        Optional<Integer> opth = rb.isValidBlackTree();
        int h = opth.orElseThrow();
        assertEquals(3,h);

        RedBlackT<Integer> rb2left  = new RBNode<>(3, Color.RED, new EmptyRB<>(), new EmptyRB<>());
        RedBlackT<Integer> rb2right = new RBNode<>(10, Color.BLACK, new EmptyRB<>(), new EmptyRB<>());
        RedBlackT<Integer> rb2      = new RBNode<>(5, Color.BLACK, rb2left, rb2right);

        opth = rb2.isValidBlackTree();
        assertEquals(Optional.empty(), opth);

        RedBlackT<Integer> rb3 = new RBNode<>(20, Color.RED, new EmptyRB<>(), new EmptyRB<>());

        opth = rb3.isValidRedTree();
        h = opth.orElseThrow();
        assertEquals(1, h);

        RedBlackT<Character> rb4left  = new RBNode<>('A', Color.BLACK, new EmptyRB<>(), new EmptyRB<>());
        RedBlackT<Character> rb4right = new RBNode<>('Z', Color.RED, new EmptyRB<>(), new EmptyRB<>());
        RedBlackT<Character> rb4      = new RBNode<>('M', Color.RED, rb4left, rb4right);

        opth = rb4.isValidRedTree();
        assertEquals(Optional.empty(), opth);
    }
}