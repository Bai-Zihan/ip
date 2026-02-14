package lime;

import org.junit.jupiter.api.Test;

import lime.parser.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit tests for the parser.
 */
public class ParserTest {

    /**
     * Verifies a missing todo description triggers an error.
     */
    @Test
    public void parse_emptyTodoDescription_exceptionThrown() {
        try {
            Parser.parse("todo");
            fail();
        } catch (LimeException e) {
            assertEquals("OOPS!!! The description of a todo cannot be empty.", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * Verifies an invalid command triggers an error.
     */
    @Test
    public void parse_invalidCommand_exceptionThrown() {
        try {
            Parser.parse("blahblah");
            fail();
        } catch (LimeException e) {
            assertEquals("OOPS!!! I'm sorry, but I don't know what that means :-(", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }
}
