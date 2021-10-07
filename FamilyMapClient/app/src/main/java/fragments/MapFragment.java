package fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Icon;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.*;

import com.example.familymapclient.R;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.joanzapata.iconify.*;
import com.joanzapata.iconify.fonts.*;
import Model.*;
import async.*;
import async.UserTask;

import java.util.*;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private final int MARKER_SIZE = 100;
    private GoogleMap map = null;
    private View view;
    private ImageView genderView;
    private String eventID;
    private ArrayList<Events> userEvents = new ArrayList<>();
    private Map<Marker, Events> eventMarkers = new HashMap<>();
    private Set<Events> filtered = new HashSet<>();
    private Events selectedEvent;
    ArrayList<Polyline> polyLines = new ArrayList<>();

    public MapFragment() {
        // Empty public constructor //
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);
        Singleton.getSingleton().setAllEvents(Singleton.getSingleton().getAllEvents());
        userEvents = Singleton.getSingleton().getUserEventList();
        eventMarkers = Singleton.getSingleton().getEventMarkers();


        filteringEvents(Singleton.getSingleton().isMaleSwitch(), Singleton.getSingleton().isFemaleSwitch());
        setMarkerHelper(Singleton.getSingleton().getFilteredEvents());
    }


    private void displayEvent() {

    }

    private void setMarkerHelper(Set<Events> filteredEvents) {
        for (Events e : filteredEvents) {
            MarkerOptions markerOption = new MarkerOptions();
            Drawable color = null;
            Marker marker = null;
            LatLng currentEvent = new LatLng(e.getLatitude(), e.getLongitude());

            markerOption.position(currentEvent);
            color = setColorHelper(Singleton.getSingleton().getTypesNcolors().get(e.getEventType()));
            markerOption.icon(getMarkerIcon(color));
            marker = map.addMarker(markerOption);
            eventMarkers.put(marker, e);
        }
    }

    private Drawable setColorHelper(String color) {
        if (color == "green") {
            return new IconDrawable(getActivity(), FontAwesomeIcons.fa_map_marker).colorRes(R.color.green);
        }
        else if (color == "blue") {
            return new IconDrawable(getActivity(), FontAwesomeIcons.fa_map_marker).colorRes(R.color.blue);
        }
        else if (color == "orange") {
            return new IconDrawable(getActivity(), FontAwesomeIcons.fa_map_marker).colorRes(R.color.orange);
        }
        else if (color == "violet") {
            return new IconDrawable(getActivity(), FontAwesomeIcons.fa_map_marker).colorRes(R.color.violet);
        }
        else if (color == "magenta") {
            return new IconDrawable(getActivity(), FontAwesomeIcons.fa_map_marker).colorRes(R.color.magenta);
        }
        else if (color == "yellow") {
            return new IconDrawable(getActivity(), FontAwesomeIcons.fa_map_marker).colorRes(R.color.yellow);
        }
        else if (color == "red") {
            return new IconDrawable(getActivity(), FontAwesomeIcons.fa_map_marker).colorRes(R.color.red);
        }
        else if (color == "azure") {
            return new IconDrawable(getActivity(), FontAwesomeIcons.fa_map_marker).colorRes(R.color.azure);
        }
        else if (color == "cyan") {
            return new IconDrawable(getActivity(), FontAwesomeIcons.fa_map_marker).colorRes(R.color.cyan);
        }
        else if (color == "burgundy") {
            return new IconDrawable(getActivity(), FontAwesomeIcons.fa_map_marker).colorRes(R.color.burgundy);
        }
        else {   // Beige
            return new IconDrawable(getActivity(), FontAwesomeIcons.fa_map_marker).colorRes(R.color.beige);
        }
    }

    private BitmapDescriptor getMarkerIcon(Drawable var) {
        Bitmap bitmap = Bitmap.createBitmap(MARKER_SIZE, MARKER_SIZE, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(bitmap);
        var.setBounds(0, 0, MARKER_SIZE, MARKER_SIZE); //
        var.draw(canvas); //
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void filteringEvents(boolean mSwitch, boolean fSwitch) {
        if (!mSwitch) {
            Set<Events> filteredEvents = Singleton.getSingleton().getFilteredEvents();
            Set<Events> fEvents = new HashSet<>();
            for (Events e : filteredEvents) {
                String pId = e.getPersonID();
                Persons p = Singleton.getSingleton().getPerson(pId);
                if (p.getGender().equals("f")) {
                    fEvents.add(e);
                }
            }
            Singleton.getSingleton().setFilteredEvents(fEvents);
        }
        if (!fSwitch) {
            Set<Events> filteredEvents = Singleton.getSingleton().getFilteredEvents();
            Set<Events> mEvents = new HashSet<>();
            for (Events e : filteredEvents) {
                String pId = e.getPersonID();
                Persons p = Singleton.getSingleton().getPerson(pId);
                if (p.getGender().equals("m")) {
                    mEvents.add(e);
                }
            }
            Singleton.getSingleton().setFilteredEvents(mEvents);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Iconify.with(new FontAwesomeModule());
        filtered.addAll(Singleton.getSingleton().getUserEventList());
        Singleton.getSingleton().setFilteredEvents(filtered);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem search = menu.findItem(R.id.search);
        MenuItem setting = menu.findItem(R.id.setting);
        menu.clear();
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        genderView = (ImageView) view.findViewById(R.id.genderIcon);
        genderView.setImageDrawable(new IconDrawable(getActivity(), FontAwesomeIcons.fa_android).colorRes(R.color.green).sizeDp(40));
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Events e = eventMarkers.get(marker);
        selectedEvent = e;

        return false;
    }


}