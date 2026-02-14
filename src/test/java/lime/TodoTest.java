package lime;

import org.junit.jupiter.api.Test;

import lime.task.Todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for todo formatting.
 */
public class TodoTest {

    /**
     * Verifies a new todo renders correctly.
     */
    @Test
    public void toString_newTodo_success() {
        Todo todo = new Todo("read book");

        assertEquals("[T][ ] read book", todo.toString());
    }

    /**
     * Verifies a new todo serializes correctly.
     */
    @Test
    public void toFileString_newTodo_success() {
        Todo todo = new Todo("read book");
        assertEquals("T | 0 | read book", todo.toFileString());
    }
}
