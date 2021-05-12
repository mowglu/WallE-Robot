public class CompactObject implements Action {

    /*
    5. Compact an object: ensure the gripper holds an object, then compact the object
     */

    /**
     * The abstraction of an action representing an aspect of the Composite design pattern.
     * If it return true, then the action has been executed. If it is false, the action has not been executed.
     * @param pRobot is the Robot to which the Action is applied to. Actions can be executed on different robots, so robot must be
     *               specified via this parameter
     */
    @Override
    public boolean executeAction(Robot pRobot) {

        // If there are already 10 compacted items or the robot is not holding an object, then this basic action does nothing.
        if(pRobot.getCompactorLevel() == 10 || pRobot.getGripperState() != Robot.GripperState.HOLDING_OBJECT){
            return false;
        }
        // Only if there are lesser than 10 compacted items and the robot is holding an object, this basic action of compacting
        // is implemented
        pRobot.compact();
        return true;
    }

    /**
     * This method is an implementation of the Visitor design pattern, where for all the concrete actions,
     *
     * @param pVisitor the visitor of the concrete visitor classes, like ComputationDistance and ComputationCompactor
     */
    @Override
    public void acceptVisitor(Visitor pVisitor) {
        pVisitor.visitCompactObject(this);
    }

    @Override
    public String toString(){
        return "Compact";
    }

}
