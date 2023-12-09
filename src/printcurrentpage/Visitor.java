package printcurrentpage;

public interface Visitor {

    /**
     * Visits the home page
     *
     * @param homePage the home page to be visited
     */
    void visit(HomePage homePage);

    /**
     * Visits the liked content page
     *
     * @param likedContent the liked content page to be visited
     */
    void visit(LikedContent likedContent);

    /**
     * Visits the artist page
     *
     * @param artistPage the artist page to be visited
     */
    void visit(ArtistPage artistPage);

    /**
     * Visits the host page
     *
     * @param hostPage the host page to be visited
     */
    void visit(HostPage hostPage);
}
