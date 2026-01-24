package lime;

import lime.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {

    @Test
    public void parse_emptyTodoDescription_exceptionThrown() {
        try {
            // 故意输入错误的命令
            Parser.parse("todo");
            // 如果上一行没抛出异常，说明代码有 bug，测试失败
            fail();
        } catch (LimeException e) {
            // 捕获到了异常，验证错误信息是否正确
            assertEquals("OOPS!!! The description of a todo cannot be empty.", e.getMessage());
        } catch (Exception e) {
            fail(); // 捕获到了其他意料之外的异常
        }
    }

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