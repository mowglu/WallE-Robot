import java.util.ArrayList;
import java.util.List;

public abstract class CompoundAction {

    private List<Action> aElements = new ArrayList<>();

    public List<Action> getListofActions(){
        return aElements;
    }

    /**
     * Is used only once in the application for the extra functionality of converting programs to complex actions
     * @param pElements the list of Actions that the program has that is converted to the sequence of actions for the complex action
     */
    public void setListofActions(List<Action> pElements){
        aElements = pElements;
    }

    /*
    For programs, addAction assembled the actions to be executed.
    For complex actions, addAction sequences the basic actions and calls the group of basic actions a complex action.
     */

    /**
     *
     * @param pAction the action to add to the list of Actions. This action is added at the end of the list.
     */
    public void addAction(Action pAction) {
        aElements.add(pAction);
    }

    /*
    For programs, removeAction removes the first instance of the action in the to-be-executed action list.
    For complex actions, removeAction is an added utility for the client. If the client accidentally added a wrong action,
    they can now remove that action. The alternative would've been creating a whole new complex action just because of a mistake.
     */

    /**
     *
     * @param actionToRemove the action to remove from the list of Actions. The first action that matches this from the list is
     *                       the action that will be removed.
     *                       If action doesn't exist in the list, then nothing happens if this method is called.
     */
    public void removeAction(Action actionToRemove) {
        if (aElements.remove(actionToRemove)) {
            aElements.remove(actionToRemove);
        }
    }

    /*
    The accept Visitor method for visitor design pattern.
     */

    /**
     *
     * @param pVisitor the visitor of the concrete visitor classes, like ComputationDistance and ComputationCompactor
     */
    public abstract void acceptVisitor(Visitor pVisitor);

}
