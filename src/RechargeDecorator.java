public class RechargeDecorator extends AbstractDecorator implements Action{

    public RechargeDecorator(Action actionToBeDecorated) {
        super(actionToBeDecorated);
    }

    @Override
    public boolean executeAction(Robot pRobot) {
        pRobot.rechargeBattery();
        // Since the battery is recharged, and this is the action of the recharge decorator, we log it.
        Logging.logger(pRobot,this);
        return super.getDecoratedAction().executeAction(pRobot);
    }

    @Override
    public void acceptVisitor(Visitor pVisitor) {
        pVisitor.visitForceRecharge(this);
    }

    @Override
    public String toString(){
        return "Force Recharge";
    }

}
