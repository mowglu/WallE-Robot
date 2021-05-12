import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ComplexActionTest {

    WallE robot;
    ManyActionStub manyActionStub;
    ComplexAction actionToTest;

    static class ManyActionStub {

        public void executeAction(Robot pRobot) throws NoSuchFieldException, IllegalAccessException {
            // All that needs to be done is for the charge to be arbitrarily lower than 5 (i.e. sequencing a few actions).

            // Reflection to force change the private attribute of charge
            PrivateFieldReflectionAccessor.changeStringFieldValue(pRobot, "charge", 3);
        }
    }

    @BeforeEach
    void setUp() {
        robot = new WallE();
        manyActionStub = new ManyActionStub();
        actionToTest = new ComplexAction();

    }

    @Test
    void testExecuteAction() throws NoSuchFieldException, IllegalAccessException, IOException {

        //All the basic actions have been unit tested. Thus, the only thing to test is whether..
        // 1. Battery level recharges if less than 5, 2. Updates battery level after each basic action, 3. Logs action
        manyActionStub.executeAction(robot);

        //Action
        actionToTest.addAction(new ReleaseObject());
        actionToTest.executeAction(robot);

        //Assert
        //Blackbox
        assertTrue(robot.getBatteryCharge() < 100 && robot.getBatteryCharge() >= 95);

        //Whitebox -> Reflection
        String check = PrivateFieldReflectionAccessor.getStringFieldValue(robot, "charge");
        assertTrue("charge : 99".equals(check) || "charge : 98".equals(check) || "charge : 97".equals(check) || "charge : 96".equals(check) || "charge : 95".equals(check));

        //The above tests checks that it has indeed recharged if less than 5 charge, AND it updated the battery level.

        Path pathOfLog = Path.of(robot.toString() + ".txt");
        String strCurrentLine;
        String actualLog = null;

        BufferedReader objReader = new BufferedReader(new FileReader(String.valueOf(pathOfLog)));
        while ((strCurrentLine = objReader.readLine()) != null) {
            actualLog = strCurrentLine;
        }

        //Assert
        assertTrue(actualLog != null);
        //The above test checks that it has indeed logged the action.
    }

    @Test
    void testInvalidFullAction() {
        //The complex action stops if there is a basic action that "breaks". This is to prevent the robot from performing the program
        // and breaking.

        Action breakingAction = new GrabObject();

        //Action
        actionToTest.addAction(new GrabObject());
        actionToTest.addAction(breakingAction);
        actionToTest.addAction(new ReleaseObject());



        //Assert
        //Only the first action will perform. The rest is broken because the 2nd action is broken.
        if(actionToTest.executeAction(robot)) {
            fail("This should not be reached. Invalid action executed because there is a broken basic action within complex action");
        }
    }

    @Test
    void testAcceptVisitor(){
        //Action
        actionToTest.addAction(new GrabObject());
        actionToTest.addAction(new MoveRobot(10));
        actionToTest.addAction(new MoveRobot(20));
        actionToTest.addAction(new CompactObject());
        actionToTest.addAction(new GrabObject());
        actionToTest.addAction(new CompactObject());

        ComputationDistance visitor1 = new ComputationDistance();
        ComputationCompactor visitor2 = new ComputationCompactor();

        actionToTest.acceptVisitor(visitor1);
        actionToTest.acceptVisitor(visitor2);

        //Assert
        assertEquals(30,visitor1.getMoveRobotComputed());
        assertEquals(2,visitor2.getCompactedItemsComputed());
    }
}
