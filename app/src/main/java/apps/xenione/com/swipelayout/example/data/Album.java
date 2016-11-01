package apps.xenione.com.swipelayout.example.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import apps.xenione.com.swipelayout.R;

/**
 * Created by Eugeni on 10/04/2016.
 */
public class Album implements Serializable{

    public static List<Album> getAlbum() {

        return new ArrayList<Album>() {{
            add(new Album(1, "La Gossa Sorda", "Saó", 2008, R.drawable.disc_gossa_sorda_sao,
                    "<b>La Gossa Sorda</b> és un grup musical de Pego (Marina Alta, País Valencià). La seua música té ritmes de Ska, Rock, Reggae i Punk amb la utilització de molts ritmes mediterranis i altres influències. Fan servir instruments com la dolçaina. Les seves lletres en valencià ataquen els fonaments del sistema, la injustícia de l\'ordre social i mundial actual, la lluita per salvar el País Valencià, el consumisme, la corrupció política, la crisi econòmica i l\'integrisme religiós."));
            add(new Album(2, "ZOO", "Tempestes vénen del sud", 2014, R.drawable.disc_zoo_tempestes,
                    "<b>ZOO</b> és un col·lectiu musical valencià nascut el 2014 que se centra entre el rap, el hip-hop, el rock i ska però també mescla ritmes electrònics. Les lletres de les cançons són polítiques."));
            add(new Album(3, "Auxili", "Dolç Atac", 2013, R.drawable.disc_auxili_dolc,
                    ""));
            add(new Album(4, "La Raíz", "Así en el cielo como en la selva", 2013, R.drawable.disc_raiz_asi_en,
                    "<b>La Raíz</b> es un grupo de música de Gandía (Valencia)"));
            add(new Album(5, "Aspencat", "Essència", 2013, R.drawable.disc_aspencat_essencia,
                    "<b>Aspencat</b> és un grup musical valencià, més concretament de Xaló, a la Marina Alta. El seu estil s\'ha basat en l\'ska, el reggae i el drum and bass, però en l\'actualitat ha avançat cap a uns ritmes més electrònics on es pot veure la presència del dubstep"));
            add(new Album(6, "Bajoqueta rock", "A Pèl ", 1998, R.drawable.disc_bajoqueta_apel,
                    "<b>Bajoqueta Rock</b> és un grup de rock en valencià que es va formar l\'any 1988 a Riba-roja de Túria i que gaudix de molta popularitat al País Valencià per les seues actuacions excèntriques, les seues lletres rurals, un poc bèsties però sempre amb gran humor, i una gran varietat musical basada en el rock però que inclou tonades populars"));
            add(new Album(7, "Diluvi", "Motius", 2013, R.drawable.disc_diluvi_motius,
                    "<b>El diluvi</b> universal és un mite recurrent a diverses cultures, que indica que una gran inundació va destruir tota la vida (o gairebé) en temps remots"));
        }};
    }

    private long id;
    private String name;
    private String bandName;
    private int releaseYear;
    private int resId;
    private String description;

    public Album(long id, String bandName, String name, int releaseYear, int resId, String description) {
        this.id = id;
        this.bandName = bandName;
        this.name = name;
        this.releaseYear = releaseYear;
        this.resId = resId;
        this.description = description;
    }

    public long getId() {
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

    public String getDescription(){
        return description;
    }

}
