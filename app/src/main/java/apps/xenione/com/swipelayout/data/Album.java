package apps.xenione.com.swipelayout.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eugeni on 10/04/2016.
 */
public class Album {

    public static List<Album> getAlbum() {

        return new ArrayList<Album>() {{
            add(new Album("La Gossa Sorda", "Saó", 2008));
            add(new Album("ZOO", "Tempestes vénen del sud", 2014));
            add(new Album("Auxili", "Dolç Atac", 2013));
            add(new Album("La Raíz", "Así en el cielo como en la selva", 2013));
            add(new Album("Aspencat", "Essència", 2013));
            add(new Album("Bajoqueta rock", "A Pèl ", 1998));
            add(new Album("Diluvi", "Rareses i cançons soltes ", 2013));
        }};

    }

    public String name;

    public String bandName;

    public int releaseYear;

    public Album(String bandName,String name, int releaseYear) {
        this.bandName = bandName;
        this.name = name;
        this.releaseYear = releaseYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
}
