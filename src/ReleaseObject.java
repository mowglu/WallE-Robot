public class ReleaseObject implements Action{

    /*
    4. Release an object: ensure the arm is retracted, then open the gripper
     */

    /**
     * The abstraction of an action representing an aspect of the Composite design pattern.
     * If it return true, then the action has been executed. If it is false, the action has not been executed.
     */
    @Override
    public boolean executeAction(Robot pRobot) {
        // First ensure that the arm is retracted!
        if (pRobot.getArmState() != Robot.ArmState.RETRACTED) {
            pRobot.retractArm();
        }

        // Need to make sure gripper isn't already open, to open it.
        if (pRobot.getGripperState() != Robot.GripperState.OPEN) {
            pRobot.openGripper();
        }
        // If gripper is already open, then nothing else to be done.
        return true;
    }

    /**
     * This method is an implementation of the Visitor design pattern, where for all the concrete actions, this method
     * is overridden and implemented.
     *
     * @param pVisitor the visitor of the concrete visitor classes, like ComputationDistance and ComputationCompactor
     */
    @Override
    public void acceptVisitor(Visitor pVisitor) {
        pVisitor.visitReleaseObject(this);
    }

    @Override
    public String toString(){
        return "Release";
    }
}
