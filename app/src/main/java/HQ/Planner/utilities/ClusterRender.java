package HQ.Planner.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import HQ.Planner.GPS.ClusterMarker;
import HQ.Planner.R;

public class ClusterRender extends
        DefaultClusterRenderer<ClusterMarker> {

    private final IconGenerator iconGenerator;
    private ImageView imageView;
    private final int markerWidth;
    private final int markerHeight;

    public ClusterRender(Context context,
                         GoogleMap map,
                         ClusterManager<ClusterMarker> clusterManager,
                         IconGenerator iconGenerator,
                         ImageView imageView,
                         int markerWidth,
                         int markerHeight) {
        super(context, map, clusterManager);
        this.iconGenerator = iconGenerator;
        this.imageView = imageView;
        this.markerWidth = markerWidth;
        this.markerHeight = markerHeight;

        iconGenerator = new IconGenerator(
                context.getApplicationContext());

        imageView = new ImageView(
                context.getApplicationContext());
        markerWidth = (int) context.getResources()
                .getDimension(R.dimen.custom_marker_image);
        imageView.setLayoutParams(new ViewGroup
                .LayoutParams(markerWidth, markerHeight));
        int padding = (int) context.getResources()
                .getDimension(R.dimen.custome_maker_padding);
        imageView.setPadding(padding, padding, padding, padding);
    }

    @Override
    protected void onBeforeClusterItemRendered(
            ClusterMarker item,
            MarkerOptions markerOptions) {
        imageView.setImageResource(item.getInconPic());
        Bitmap icon = iconGenerator.makeIcon();
        markerOptions.icon(
                BitmapDescriptorFactory
                        .fromBitmap(icon))
                .title(item.getTitle());

    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<ClusterMarker> cluster){
        boolean k = false;

        return k;

    }

}

