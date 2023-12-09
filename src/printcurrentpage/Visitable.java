package printcurrentpage;

public interface Visitable {
    /**
     * accepts the visitor, the method helps with the design pattern visitor implementation
     *
     * @param visitor the visitor
     */
     void accept(Visitor visitor);
}
