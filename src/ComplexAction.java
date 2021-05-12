public class ComplexAction extends CompoundAction implements Action{

    /*
    First check the state of the battery
    If the charge of the battery is <= 5 units, then recharge the battery
    Perform the action and update the battery level
     */

    /**
     * The abstraction of an action representing an aspect of the Composite design pattern.
     * @param pRobot is the Robot to which the Action is applied to. Actions can be applied to different robots, so robot must be
     *        specified via this parameter
     */
    @Override
    public boolean executeAction(Robot pRobot) {
        for (Action aAction : super.getListofActions()) {

            // If battery less equal to 5 units, then the robot is recharged
            if (pRobot.getBatteryCharge() <= 5) {
                pRobot.rechargeBattery();
            }

            // Then the action is executed
            boolean result = aAction.executeAction(pRobot);

            // And after the battery level is updated. If the action is executed only, the battery level is updated.
            // Otherwise, the battery level will not be updated.
            if (result) {
                pRobot.updateBatteryLevel();
                // After updating the battery level, the basic actions are logged!
                Logging.logger(pRobot, aAction);
            }
            else{
                return false;
            }
        }
        // This is so that complex actions will not drain battery level!
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
        for (Action aAction:super.getListofActions()){
            aAction.acceptVisitor(pVisitor);
        }
    }
}
