public class Program extends CompoundAction {

    public ComplexAction programToComplexAction(){
        ComplexAction convertedComplexAction = new ComplexAction();
        convertedComplexAction.setListofActions(this.getListofActions());
        return convertedComplexAction;
    }

    public void executeProgram(Robot pRobot) {
        for (Action aAction : super.getListofActions()) {

            // If battery less equal to 5 units, then the robot is recharged
            if (pRobot.getBatteryCharge() <= 5) {
                pRobot.rechargeBattery();
            }

            // Then the action is executed
            boolean result = aAction.executeAction(pRobot);

            // And after the battery level is updated. If the action is executed only, the battery level is updated.
            // Otherwise, the battery level will not be updated. Also, the program stops execution to prevent breaking the robot.
            if (result) {
                // Additionally, only if the action is a basic action, the battery level is updated. If the action is complex, the
                // battery level will not be updated (only updated for each of the complex action's basic actions)
                // If the action is a force recharge decorator, then if the decorated item is a basic action, it is updated.
                // Otherwise, it is not updated.

                if (!(aAction instanceof ComplexAction)) {
                    if (aAction instanceof AbstractDecorator){
                        if(!(((AbstractDecorator) aAction).getDecoratedAction() instanceof ComplexAction)){
                            pRobot.updateBatteryLevel();
                            // After updating battery level, logger is called.
                            Logging.logger(pRobot, ((AbstractDecorator) aAction).getDecoratedAction());
                        }
                    }
                    else {
                        pRobot.updateBatteryLevel();
                        // After updating battery level, logger is called.
                        Logging.logger(pRobot, aAction);
                    }
                }
            }
            else{
                break;
            }
        }
    }

    public int getCompactComputation(){
        ComputationCompactor visitor = new ComputationCompactor();
        this.acceptVisitor(visitor);
        return visitor.getCompactedItemsComputed();
    }

    public double getTotalDistance(){
        ComputationDistance visitor = new ComputationDistance();
        this.acceptVisitor(visitor);
        return visitor.getMoveRobotComputed();
    }

    @Override
    public void acceptVisitor(Visitor pVisitor) {
        for (Action aAction:super.getListofActions()){
            aAction.acceptVisitor(pVisitor);
        }
    }
}
