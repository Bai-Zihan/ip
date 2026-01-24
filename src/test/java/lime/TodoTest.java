package lime;

import lime.task.Todo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {

    @Test
    public void toString_newTodo_success() {
        Todo todo = new Todo("read book");

        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void toFileString_newTodo_success() {
        Todo todo = new Todo("read book");
        assertEquals("T | 0 | read book", todo.toFileString());
    }
}
