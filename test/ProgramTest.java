import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramTest {
    WallE robot;
    ManyActionStub manyActionStub;
    Program programToTest;

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
        programToTest = new Program();
    }

    @Test
    void testValidExecuteProgram1() throws NoSuchFieldException, IllegalAccessException, IOException {

        //All the basic actions have been unit tested. Thus, the only thing to test is whether..
        // 1. Battery level recharges if less than 5, 2. Updates battery level after each basic action, 3. Logs action
        manyActionStub.executeAction(robot);

        //Action
        programToTest.addAction(new ReleaseObject());
        programToTest.executeProgram(robot);

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
    void testValidExecuteProgram2() throws NoSuchFieldException, IllegalAccessException, IOException {

        //Now, let us have a program that executes with not just basic actions, but with basic actions, complex actions, and
        // decorated (basic & complex) actions

        //Arrange
        ComplexAction complexAction = new ComplexAction();
        complexAction.addAction(new MoveRobot(10));
        complexAction.addAction(new TurnRobot(Rotation.LEFT));
        complexAction.addAction(new MoveRobot(10));

        RechargeDecorator decoratedAction1 = new RechargeDecorator(complexAction);
        RechargeDecorator decoratedAction2 = new RechargeDecorator(new ReleaseObject());


        programToTest.addAction(new ReleaseObject());
        programToTest.addAction(complexAction);
        programToTest.addAction(decoratedAction1);
        programToTest.addAction(decoratedAction2);

        //Action
        programToTest.executeProgram(robot);

        //Assert -> Still asserting the 3 things a program does (1. recharge if lower than 5, 2. update, 3. log)
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
    void testProgramToComplexAction(){
        //Arrange
        programToTest.addAction(new GrabObject());
        programToTest.addAction(new MoveRobot(10));
        programToTest.addAction(new MoveRobot(20));
        programToTest.addAction(new CompactObject());

        //Act
        ComplexAction convertedComplexAction = programToTest.programToComplexAction();

        //Assert
        assertTrue(convertedComplexAction.getListofActions().equals(programToTest.getListofActions()));
    }

    @Test
    void testGetCompactComputation(){
        //Arrange
        ComputationCompactor visitor = new ComputationCompactor();
        programToTest.addAction(new GrabObject());
        programToTest.addAction(new MoveRobot(10));
        programToTest.addAction(new MoveRobot(20));
        programToTest.addAction(new CompactObject());

        //Act
        int result;
        result = programToTest.getCompactComputation();

        //Assert
        assertEquals(1,result);
    }

    @Test
    void testGetTotalDistance(){
        //Arrange
        ComputationCompactor visitor = new ComputationCompactor();
        programToTest.addAction(new GrabObject());
        programToTest.addAction(new MoveRobot(10));
        programToTest.addAction(new MoveRobot(20));
        programToTest.addAction(new CompactObject());

        //Act
        double result;
        result = programToTest.getTotalDistance();

        //Assert
        assertEquals(30,result);
    }

    @Test
    void testAcceptVisitor(){
        //Action
        programToTest.addAction(new GrabObject());
        programToTest.addAction(new MoveRobot(10));
        programToTest.addAction(new MoveRobot(20));
        programToTest.addAction(new CompactObject());
        programToTest.addAction(new GrabObject());
        programToTest.addAction(new CompactObject());

        ComputationDistance visitor1 = new ComputationDistance();
        ComputationCompactor visitor2 = new ComputationCompactor();

        programToTest.acceptVisitor(visitor1);
        programToTest.acceptVisitor(visitor2);

        //Assert
        assertEquals(30,visitor1.getMoveRobotComputed());
        assertEquals(2,visitor2.getCompactedItemsComputed());
    }
}
