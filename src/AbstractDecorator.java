public abstract class AbstractDecorator implements Action{

    private final Action decoratedAction;

    /**
     *
     * @param actionToBeDecorated the action that will be forced to recharge before executing
     */
    public AbstractDecorator(Action actionToBeDecorated){
        decoratedAction = actionToBeDecorated;
    }

    public Action getDecoratedAction(){
        return decoratedAction;
    }

    @Override
    public abstract boolean executeAction(Robot pRobot);

    @Override
    public abstract void acceptVisitor(Visitor pVisitor);
}
