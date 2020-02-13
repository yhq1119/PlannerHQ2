package HQ.Planner.GPS;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ClusterMarker implements ClusterItem {

    private LatLng position;
    private String title;
    private String snippet;
    private int inconPic;
//    private Event event;


    public ClusterMarker(
            LatLng position,
            String title,
            String snippet,
            int inconPic) {
        this.position = position;
        this.title = title;
        this.snippet = snippet;
        this.inconPic = inconPic;
    }

    public ClusterMarker(){

    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public void setInconPic(int inconPic) {
        this.inconPic = inconPic;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSnippet() {
        return snippet;
    }

    public int getInconPic() {
        return inconPic;
    }
}
