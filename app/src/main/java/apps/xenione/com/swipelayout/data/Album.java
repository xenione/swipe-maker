package apps.xenione.com.swipelayout.data;

import java.util.ArrayList;
import java.util.List;

import apps.xenione.com.swipelayout.R;

/**
 * Created by Eugeni on 10/04/2016.
 */
public class Album {

    public static List<Album> getAlbum() {

        return new ArrayList<Album>() {{
            add(new Album(1, "La Gossa Sorda", "Saó", 2008, R.drawable.disc_gossa_sorda_sao));
            add(new Album(2, "ZOO", "Tempestes vénen del sud", 2014, R.drawable.disc_zoo_tempestes));
            add(new Album(3, "Auxili", "Dolç Atac", 2013, R.drawable.disc_auxili_dolc));
            add(new Album(4, "La Raíz", "Así en el cielo como en la selva", 2013, R.drawable.disc_raiz_asi_en));
            add(new Album(5, "Aspencat", "Essència", 2013, R.drawable.disc_aspencat_essencia));
            add(new Album(6, "Bajoqueta rock", "A Pèl ", 1998, R.drawable.disc_bajoqueta_apel));
            add(new Album(7, "Diluvi", "Motius", 2013, R.drawable.disc_diluvi_motius));
        }};
    }

    public long id;
    public String name;
    public String bandName;
    public int releaseYear;
    public int resId;

    public Album(long id, String bandName, String name, int releaseYear, int resId) {
        this.id = id;
        this.bandName = bandName;
        this.name = name;
        this.releaseYear = releaseYear;
        this.resId = resId;
    }

    public long getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBandName() {
        return bandName;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getResource(){
        return resId;
    }

}
