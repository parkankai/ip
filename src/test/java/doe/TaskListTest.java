package doe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/** Tests task-list mutations and the user-facing task lookup rules. */
class TaskListTest {

    @Test
    void constructor_nullBackingList_assertionErrorThrown() {
        assertThrows(AssertionError.class, () -> new TaskList(null));
    }

    @Test
    void addTask_nullTask_assertionErrorThrown() {
        TaskList taskList = new TaskList(new ArrayList<>());

        assertThrows(AssertionError.class, () -> taskList.addTask(null));
    }

    @Test
    void addGetRemoveAndSize_tasksUpdatedInExpectedOrder() {
        TaskList taskList = new TaskList(new ArrayList<>());
        Task firstTask = new Todo("read book");
        Task secondTask = new Todo("write report");

        taskList.addTask(firstTask);
        taskList.addTask(secondTask);

        assertEquals(2, taskList.size());
        assertSame(firstTask, taskList.getTask(0));
        assertSame(secondTask, taskList.removeTask(1));
        assertEquals(1, taskList.size());
        assertSame(firstTask, taskList.getTask(0));
    }

    @Test
    void findTaskIndex_numberAndDescription_firstMatchingIndexReturned() {
        TaskList taskList = new TaskList(new ArrayList<>());
        taskList.addTask(new Todo("read book"));
        taskList.addTask(new Todo("write report"));
        taskList.addTask(new Todo("read book"));

        assertEquals(0, taskList.findTaskIndex("1"));
        assertEquals(1, taskList.findTaskIndex("2"));
        assertEquals(0, taskList.findTaskIndex("read book"));
        assertEquals(-1, taskList.findTaskIndex("0"));
        assertEquals(-1, taskList.findTaskIndex("4"));
        assertEquals(-1, taskList.findTaskIndex("missing task"));
    }
}
