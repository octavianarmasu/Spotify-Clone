package printcurrentpage;

public interface Visitor {
    public void visit(HomePage homePage);
    public void visit(LikedContent likedContent);
    public void visit(ArtistPage artistPage);
    public void visit(HostPage hostPage);
}
