import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CompactObjectTest {

    WallE robot;
    Action actionToTest;
    GrabObjectStub grabObjectStub;
    VisitorStub visitorStub;

    static class GrabObjectStub{


        public void executeAction(Robot pRobot) throws NoSuchFieldException, IllegalAccessException {
            // All that the grab object stub must do is it should hold an item and have its arm retracted

            // Reflection to force change the private attribute of arm state and gripper state
            PrivateFieldReflectionAccessor.changeStringFieldValue(pRobot,"armState", Robot.ArmState.RETRACTED);
            PrivateFieldReflectionAccessor.changeStringFieldValue(pRobot,"gripperState", Robot.GripperState.HOLDING_OBJECT);
        }
    }

    static class VisitorStub extends ComputationCompactor{
        private int compactedItemsComputed = 0;

        @Override
        public int getCompactedItemsComputed(){
            return compactedItemsComputed;
        }

        @Override
        public void visitCompactObject(CompactObject compactObject){
            compactedItemsComputed++;
        }
    }

    @BeforeEach
    void setUp(){
        //Arrange
        robot = new WallE();
        actionToTest = new CompactObject();
        grabObjectStub = new GrabObjectStub();
    }

    @Test
    void testDefaultCompactObject() throws NoSuchFieldException, IllegalAccessException {
        //Blackbox
        assertEquals(0, robot.getCompactorLevel());

        //Whitebox -> Reflection
        String check1 = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"compactedItems");
        assertEquals("compactedItems : 0",check1);

        //Blackbox
        assertEquals(Robot.GripperState.EMPTY, robot.getGripperState());

        //Whitebox -> Reflection
        String check2 = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"gripperState");
        assertEquals("gripperState : EMPTY",check2);
    }

    @Test
    void testValidExecuteAction() throws NoSuchFieldException, IllegalAccessException {

        //Grab's executeAction() is tested under different class. Hence, using a stub!
        grabObjectStub.executeAction(robot);

        //Act
        actionToTest.executeAction(robot);
        //Blackbox
        assertEquals(1, robot.getCompactorLevel());
        //Whitebox -> Reflection
        String check = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"compactedItems");
        assertEquals("compactedItems : 1",check);
    }

    @Test
    void testInvalidExecuteAction1() throws NoSuchFieldException, IllegalAccessException {
        //Edge case when there are in total 10 compacted items

        for (int i = 0; i< 10; i++){
            grabObjectStub.executeAction(robot);
            actionToTest.executeAction(robot);
        }

        //Act
        try{
            grabObjectStub.executeAction(robot);
            actionToTest.executeAction(robot);
            fail("This should not be reached. Invalid action executed because there are 10 compacted items, and program tried to compact");
        } catch (Error | NoSuchFieldException | IllegalAccessException e){
            // An assertion error has indeed occurred if compacted items is 10.
            assertTrue (e instanceof AssertionFailedError);
        }
    }

    @Test
    void testInvalidExecuteAction2() {
        //Edge case when the gripper is not holding any object, then it cant compact anything

        //Act
        try{
            actionToTest.executeAction(robot);
            fail("This should not be reached. Invalid action executed because nothing is held, and program tried to compact");
        } catch (Error e){
            // An assertion error has indeed occurred if compacted items is 10.
            assertTrue (e instanceof AssertionFailedError);
        }
    }

    @Test
    void testToString(){
        assertEquals( "Compact", actionToTest.toString());
    }


    @Test
    void testAcceptVisitor() {
        visitorStub = new VisitorStub();
        actionToTest.acceptVisitor(visitorStub);
        assertEquals(1,visitorStub.getCompactedItemsComputed());
    }
}

