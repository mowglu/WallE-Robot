public class EmptyCompactor implements Action{

    /*
    6. Empty the compactor: empty the content of the compactor
     */

    /**
     * The abstraction of an action representing an aspect of the Composite design pattern.
     * If it return true, then the action has been executed. If it is false, the action has not been executed.
     * @param pRobot the robot for which action is executed on
     */
    @Override
    public boolean executeAction(Robot pRobot) {

        // if the compacted items are zero (i.e. it is already emptied), this action is not implemented
        if (pRobot.getCompactorLevel() == 0){
            return false;
        }
        // if there are some compacted items, then this basic action is implemented and empties it!
        pRobot.emptyCompactor();
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
        pVisitor.visitEmptyCompactor(this);
    }

    @Override
    public String toString(){
        return "Empty";
    }
}
